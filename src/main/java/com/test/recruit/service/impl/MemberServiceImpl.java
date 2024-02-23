package com.test.recruit.service.impl;

import com.test.recruit.config.security.JwtTokenProvider;
import com.test.recruit.data.dto.req.LogReq;
import com.test.recruit.data.dto.req.MemberReq;
import com.test.recruit.data.dto.res.TokenRes;
import com.test.recruit.data.dto.security.CustomUserDetails;
import com.test.recruit.data.entity.Member;
import com.test.recruit.data.enumval.Status;
import com.test.recruit.exception.DefaultException;
import com.test.recruit.repository.MemberRepository;
import com.test.recruit.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    @Value("${spring.profiles.active}")
    private String profiles;

    private final MemberRepository memberRepository;

    private final AsyncService asyncService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public void signUp(MemberReq.SignUpReq req) {
        // Local 환경에서만 동작하도록 설정함.
        if (!profiles.equalsIgnoreCase("local")) {
            throw new DefaultException("Invalid env.", HttpStatus.BAD_REQUEST);
        }

        // 아이디 중복 확인
        Member checkExist = memberRepository.selectMemberById(req.getId());
        if (checkExist != null) {
            throw new DefaultException("Duplicated Id.", HttpStatus.BAD_REQUEST);
        }

        Member member = Member.builder()
                .id(req.getId())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(req.getRole())
                .build();

        try {
            memberRepository.insertMember(member);
        } catch (Exception e) {
            throw new DefaultException("Insert Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public TokenRes login(MemberReq.SignInReq req) {

        TokenRes tokenRes = getTokenResponse(req.getId(), req.getPassword());
        CustomUserDetails userDetails = (CustomUserDetails) jwtTokenProvider.getAuthentication(tokenRes.getAccessToken()).getPrincipal();

        //로그 기록
        asyncService.saveLog(LogReq.NewLogReq.builder()
                .type("LOGIN")
                .memberNo(userDetails.getMemberNo())
                .result(Status.Y)
                .build());

        return tokenRes;
    }

    /**
     * JWT 토큰 발급
     */
    private TokenRes getTokenResponse(String id, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(id, password);

        // 사용자 인증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 사용자 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // jwt 토큰 발급
        return jwtTokenProvider.createToken(authentication);
    }

}
