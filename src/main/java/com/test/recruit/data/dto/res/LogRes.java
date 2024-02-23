package com.test.recruit.data.dto.res;

import com.test.recruit.data.entity.Member;
import com.test.recruit.data.enumval.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class LogRes {

    @Getter
    @Builder
    public static class LogHistoryRes {
        @Schema(description = "사용자 아이디")
        private String id;
        @Schema(description = "사용자 액션 유형")
        private String type;
        @Schema(description = "사용자 액션 성공여부")
        private Status result;
        @Schema(description = "사용자 액션 일시")
        private LocalDateTime registered;
    }

}
