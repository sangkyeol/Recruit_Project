package com.test.recruit.service;

import com.test.recruit.data.dto.req.MemberReq;
import com.test.recruit.data.dto.res.TokenRes;
import com.test.recruit.data.entity.Member;

public interface MemberService {
    void signUp(MemberReq.SignUpReq req);

    TokenRes login(MemberReq.SignInReq req);

}
