package com.test.recruit.util;


import com.test.recruit.constant.Constant;
import com.test.recruit.data.dto.req.LogReq;
import com.test.recruit.data.dto.security.CustomUserDetails;
import com.test.recruit.data.enumval.Status;
import com.test.recruit.service.LogService;
import com.test.recruit.service.impl.AsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class Util {
    @Value("${spring.profiles.active}")
    private String profiles;

    private final AsyncService asyncService;

    public void writeLog(String type, Status result) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) loggedInUser.getPrincipal();

        asyncService.saveLog(LogReq.NewLogReq.builder()
                .type(type)
                .memberNo(userDetails.getMemberNo())
                .result(result)
                .build());
    }

    public boolean checkIp(String str) {
        return Pattern.matches("([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})", str);
    }

    public void alertException(String msg) {
        if (profiles.equalsIgnoreCase("local")) {
            return;
        }

        log.error(msg);

        // TODO 추후 서비스 운영시 Exception 내용을 Slack/Telegram 등으로 실시간 전송
    }
}
