package com.test.recruit.service.impl;

import com.test.recruit.data.dto.req.LogReq;
import com.test.recruit.data.entity.MemberLog;
import com.test.recruit.exception.DefaultException;
import com.test.recruit.repository.LogRepository;
import com.test.recruit.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Async
    @Override
    public void saveLog(LogReq.NewLogReq newLogReq) {
        MemberLog memberLog = MemberLog.builder()
                .memberNo(newLogReq.getMemberNo())
                .type(newLogReq.getType())
                .result(newLogReq.getResult())
                .data(newLogReq.getData())
                .build();

        try {
            logRepository.insertMemberLog(memberLog);
        } catch (Exception e) {
            throw new DefaultException("Insert Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
