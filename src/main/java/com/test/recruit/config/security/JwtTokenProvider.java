package com.test.recruit.config.security;

import com.test.recruit.data.dto.res.TokenRes;
import com.test.recruit.data.dto.security.CustomUserDetails;
import com.test.recruit.service.impl.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import static com.test.recruit.constant.Constant.AUTHORITIES_KEY;
import static com.test.recruit.constant.Constant.AUTHORIZATION_BEARER;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider implements InitializingBean {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token-validity-in-seconds}")
    private long accessTokenValidityInSeconds;

    private Key key;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * JWT 토큰 신규 발급 (access)
     */
    public TokenRes createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String name = authentication.getName();
        String accessToken = createAccessToken(name, authorities);

        return TokenRes.builder()
                .accessToken(AUTHORIZATION_BEARER + accessToken)
                .userId(name)
                .build();
    }

    private String createAccessToken(String name, String authorities) {
        return createAccessToken(name, authorities, this.accessTokenValidityInSeconds * 1000);
    }

    private String createAccessToken(String name, String authorities, long expiration) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(name)
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    /**
     * JWT 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Token format is malformed.");
        } catch (ExpiredJwtException e) {
            log.error("Token is Expired.");
        } catch (UnsupportedJwtException e) {
            log.error("Token is not supported.");
        } catch (IllegalArgumentException e) {
            log.error("Token data is weired");
        }

        return false;
    }
}