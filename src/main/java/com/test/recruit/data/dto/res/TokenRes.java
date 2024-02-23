package com.test.recruit.data.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TokenRes {
    @Schema(description = "JWT 토큰(AccessToken)")
    private String accessToken;
    @Schema(description = "로그인된 사용자 아이디")
    private String userId;
}
