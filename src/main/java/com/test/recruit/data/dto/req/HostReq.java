package com.test.recruit.data.dto.req;

import com.test.recruit.data.common.ValidEnum;
import com.test.recruit.data.enumval.MemberStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class HostReq {

    @Getter
    @Setter
    public static class NewHostReq {
        @NotBlank
        private String name;
        @NotBlank
        private String ip;
    }

    @Getter
    @Setter
    public static class SearchHostReq {
        private String name;
        private String ip;
    }

    @Getter
    @Setter
    public static class PutHostReq {
        @Min(1)
        private int hostNo;
        private String name;
        private String ip;
    }

    @Getter
    @Setter
    public static class MonitorHostReq {
        private Integer hostNo;
    }


}
