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

        /**
         * 추후 커스텀 header 검증 등에서 사용
         */
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