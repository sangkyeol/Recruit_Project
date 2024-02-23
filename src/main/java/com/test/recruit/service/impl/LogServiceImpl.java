package com.test.recruit.service.impl;

import com.test.recruit.data.dto.req.LogReq;
import com.test.recruit.data.dto.res.LogRes;
import com.test.recruit.data.entity.MemberLog;
import com.test.recruit.data.enumval.Status;
import com.test.recruit.exception.DefaultException;
import com.test.recruit.repository.LogRepository;
import com.test.recruit.service.LogService;
import com.test.recruit.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    private final Util util;


    @Override
    public List<LogRes.LogHistoryRes> getLog(int page, int size) {
        List<MemberLog> memberLogList = logRepository.selectMemberLog(((page - 1) * size), (page * size));

        util.writeLog("GET_LOG", Status.Y);
        return memberLogList.stream().map(item -> {
            return LogRes.LogHistoryRes.builder()
                    .id(item.getId())
                    .type(item.getType())
                    .result(item.getResult())
                    .registered(item.getRegistered())
                    .build();
        }).collect(Collectors.toList());

    }
}
