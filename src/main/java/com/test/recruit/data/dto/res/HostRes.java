package com.test.recruit.data.dto.res;

import com.test.recruit.data.enumval.ActiveStatus;
import com.test.recruit.data.enumval.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class HostRes {

    @Getter
    @Builder
    public static class HostItemRes {
        private int hostNo;
        private String name;
        private String ip;
        private LocalDateTime registered;
        private LocalDateTime updated;
    }

    @Getter
    @Builder
    public static class HostStatusRes {

        private int hostNo;
        private String reason;
        private Status status;
        private LocalDateTime checked;
    }

    @Getter
    @Builder
    public static class HostMonitorRes {
        private int hostNo;
        private String name;
        private String ip;
        private Status status;
        private String reason;
        private LocalDateTime lastChecked;
    }

}
