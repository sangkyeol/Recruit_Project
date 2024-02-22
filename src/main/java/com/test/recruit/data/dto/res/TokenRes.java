package com.test.recruit.data.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TokenRes {
    private String accessToken;
    private String refreshToken;
    private String userId;
}
