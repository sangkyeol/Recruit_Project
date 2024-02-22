package com.test.recruit.data.dto.res;

import com.test.recruit.data.entity.Member;
import com.test.recruit.data.enumval.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class LogRes {

    @Getter
    @Builder
    public static class LogHistoryRes {
        private Member member;
        private String type;
        private Status result;
        private String data;
        private LocalDateTime registered;
    }

}
