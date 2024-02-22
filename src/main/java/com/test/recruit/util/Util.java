package com.test.recruit.util;


import com.test.recruit.constant.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Util {
    @Value("${spring.profiles.active}")
    private String profiles;

    public void alertException(String msg) {
        if (profiles.equalsIgnoreCase("local")) {
            return;
        }

        log.error(msg);

        // TODO 추후 서비스 운영시 Exception 내용을 Slack/Telegram 등으로 실시간 전송
    }

    /**
     * 토큰값 에서 "Bearer " 제거
     */
    public String removeBearerPrefix(String token) {
        return token.replace(Constant.AUTHORIZATION_BEARER, "");
    }

}
