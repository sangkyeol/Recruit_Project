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
        private Status lastStatus;
        private LocalDateTime lastChecked;
        private ActiveStatus activeStatus;
        private LocalDateTime registered;
        private LocalDateTime updated;
    }

    @Getter
    @Builder
    public static class HostStatusRes {

        private int hostNo;
        private String name;
        private String ip;
        private Status status;
        private LocalDateTime lastSuccessTime;
        private LocalDateTime lastCheckedTime;
    }

    @Getter
    @Builder
    public static class HostHistoryRes {

        private Status status;
        private LocalDateTime lastCheckedTime;
    }

}
