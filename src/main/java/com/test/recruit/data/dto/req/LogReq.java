package com.test.recruit.data.dto.req;

import com.test.recruit.data.common.ValidEnum;
import com.test.recruit.data.enumval.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class LogReq {

    @Getter
    @Builder
    public static class NewLogReq {
        @NotBlank
        private int memberNo;
        @NotBlank
        private String type;
        @ValidEnum(enumClass = Status.class)
        private Status result;
        private String data;
    }

}
