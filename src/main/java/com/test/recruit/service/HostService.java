package com.test.recruit.service;

import com.test.recruit.data.dto.req.HostReq;

public interface HostService {
    void postHost(HostReq.NewHostReq req);
}
