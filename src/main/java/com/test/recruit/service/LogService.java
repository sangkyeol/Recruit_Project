package com.test.recruit.service;

import com.test.recruit.data.dto.res.LogRes;

import java.util.List;

public interface LogService {

    List<LogRes.LogHistoryRes> getLog(int page, int size);

}
