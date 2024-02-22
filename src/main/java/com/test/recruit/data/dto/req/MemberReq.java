package com.test.recruit.data.dto.req;

import com.test.recruit.data.common.ValidEnum;
import com.test.recruit.data.enumval.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class MemberReq {

    @Getter
    @Setter
    public static class SignUpReq {
        @NotBlank
        private String id;
        @NotBlank
        private String password;
        @ValidEnum(enumClass = Role.class)
        private Role role;
    }

    @Getter
    @Setter
    public static class SignInReq {
        @NotBlank
        private String id;
        @NotBlank
        private String password;
    }

}
