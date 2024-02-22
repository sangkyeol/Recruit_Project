package com.test.recruit.interceptor;


import com.test.recruit.config.security.JwtTokenProvider;
import com.test.recruit.data.entity.Member;
import com.test.recruit.exception.DefaultException;
import com.test.recruit.repository.MemberRepository;
import com.test.recruit.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.test.recruit.constant.Constant.AUTHORIZATION_HEADER;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    private final Util util;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String authorization = request.getHeader(AUTHORIZATION_HEADER);

        if (authorization != null) {
            String token = util.removeBearerPrefix(authorization);

            // token 유효성 검증
            if (jwtTokenProvider.validateToken(token)) {

                // jwt 토큰으로 id 가져오기
                String id = jwtTokenProvider.getMemberIdByToken(token);

                // member select
                Member member = memberRepository.selectMemberById(id);
                if (member == null) {
                    throw new DefaultException("The member not exist.", HttpStatus.BAD_REQUEST);
                }

                // request 객체에 member 적재
                request.setAttribute("member", member);
                return true;
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
    }

}