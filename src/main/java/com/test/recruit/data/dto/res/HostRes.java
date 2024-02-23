package com.test.recruit.data.dto.res;

import com.test.recruit.data.enumval.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class HostRes {

    @Getter
    @Builder
    public static class HostItemRes {
        @Schema(description = "호스트 고유식별자", example = "1")
        private int hostNo;
        @Schema(description = "호스트 이름", example = "testName")
        private String name;
        @Schema(description = "호스트 IP", example = "127.0.0.1")
        private String ip;
        @Schema(description = "등록일시")
        private LocalDateTime registered;
        @Schema(description = "최종 수정일시")
        private LocalDateTime updated;
    }

    @Getter
    @Builder
    public static class HostStatusRes {

        @Schema(description = "호스트 고유식별자", example = "1")
        private int hostNo;
        @Schema(description = "성공이 아닌 경우 사유")
        private Status status;
        @Schema(description = "상태 현황")
        private String reason;
        @Schema(description = "최종 체크일시")
        private LocalDateTime checked;
    }

    @Getter
    @Builder
    public static class HostMonitorRes {
        @Schema(description = "호스트 고유식별자", example = "1")
        private int hostNo;
        @Schema(description = "호스트 이름", example = "testName")
        private String name;
        @Schema(description = "호스트 IP", example = "127.0.0.1")
        private String ip;
        @Schema(description = "상태 현황")
        private Status status;
        @Schema(description = "성공이 아닌 경우 사유")
        private String reason;
        @Schema(description = "최종 체크일시")
        private LocalDateTime lastChecked;
    }

}
