package com.test.recruit.data.dto.req;

import com.test.recruit.data.common.ValidEnum;
import com.test.recruit.data.enumval.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class HostReq {

    @Getter
    @Setter
    @Schema(title = "호스트 등록용 DTO")
    public static class NewHostReq {
        @Schema(description = "새로 등록할 호스트 이름", example = "SampleHost", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        private String name;
        @Schema(description = "새로 등록할 호스트 IP", example = "127.0.0.1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        private String ip;
    }

    @Getter
    @Setter
    @Schema(title = "호스트 수정용 DTO / name과 ip중 하나 이상 있어야 함")
    public static class PutHostReq {
        @Schema(description = "수정할 호스트 식별자", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @Min(1)
        private int hostNo;
        @Schema(description = "수정할 호스트 이름", example = "SampleHost", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String name;
        @Schema(description = "수정할 호스트 IP", example = "127.0.0.1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String ip;
    }

}
