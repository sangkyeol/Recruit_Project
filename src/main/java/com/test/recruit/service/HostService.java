package com.test.recruit.service;

import com.test.recruit.data.dto.req.HostReq;
import com.test.recruit.data.dto.res.HostRes;
import com.test.recruit.data.entity.HostList;

import java.util.List;

public interface HostService {
    void postHost(HostReq.NewHostReq req);

    List<HostRes.HostItemRes> getHost();

    void deleteHost(int hostNo);

    void putHost(HostReq.PutHostReq req);

    HostRes.HostStatusRes getHostStatus(int hostNo);

    void checkHostStatusAsync();

   List< HostRes.HostMonitorRes> getHostMonitor(Integer hostNo);
}
