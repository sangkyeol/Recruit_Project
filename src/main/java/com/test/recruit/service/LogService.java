package com.test.recruit.service;

import com.test.recruit.data.dto.req.LogReq;

public interface LogService {

    void saveLog(LogReq.NewLogReq newLogReq);

}
