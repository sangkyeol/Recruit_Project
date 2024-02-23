package com.test.recruit.data.dto.req;

import com.test.recruit.data.common.ValidEnum;
import com.test.recruit.data.enumval.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class MemberReq {

    @Getter
    @Setter
    @Schema(title = "회원가입용 DTO")
    public static class SignUpReq {
        @Schema(description = "아이디", example = "testid", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        private String id;
        @Schema(description = "비밀번호", example = "passw0rd", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        private String password;
        @Schema(description = "사용지 권한", example = "ROLE_MEMBER / ROLE_ADMIN", requiredMode = Schema.RequiredMode.REQUIRED)
        @ValidEnum(enumClass = Role.class)
        private Role role;
    }

    @Getter
    @Setter
    @Schema(title = "로그인용 DTO")
    public static class SignInReq {
        @Schema(description = "아이디", example = "testid", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        private String id;
        @Schema(description = "비밀번호", example = "passw0rd", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        private String password;
    }

}
