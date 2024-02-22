package com.test.recruit.service;

import com.test.recruit.data.dto.req.MemberReq;
import com.test.recruit.data.dto.res.TokenRes;

public interface MemberService {
    void signUp(MemberReq.SignUpReq req);

    TokenRes login(MemberReq.SignInReq req);
}
