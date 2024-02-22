package com.test.recruit.service;

import com.test.recruit.data.dto.req.HostReq;
import com.test.recruit.data.dto.res.HostRes;

import java.util.List;

public interface HostService {
    void postHost(HostReq.NewHostReq req);

    List<HostRes.HostItemRes> getHost();
}
