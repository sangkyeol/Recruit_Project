package com.test.recruit.service.impl;

import com.test.recruit.data.dto.req.LogReq;
import com.test.recruit.data.entity.HostCheck;
import com.test.recruit.data.entity.HostList;
import com.test.recruit.data.entity.MemberLog;
import com.test.recruit.data.enumval.Status;
import com.test.recruit.exception.DefaultException;
import com.test.recruit.repository.HostRepository;
import com.test.recruit.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import static com.test.recruit.constant.Constant.REASON_HOST_NOT_FOUND;
import static com.test.recruit.constant.Constant.REASON_TIMEOUT;

@Slf4j
@RequiredArgsConstructor
@Service
public class AsyncService {
    private final HostRepository hostRepository;
    private final LogRepository logRepository;

    @Value("${com.test.recruit.timeout}")
    private int timeout;

    @Async("NetPoolTaskExecutor")
    @Transactional
    public void updateAfterCheckNW(HostList target) {
        HostCheck hostCheck = HostCheck.builder()
                .hostNo(target.getHostNo())
                .build();

        try {
            InetAddress inetAddress = InetAddress.getByName(target.getIp());
            try {
                if (inetAddress.isReachable(timeout)) {
                    hostCheck.setStatus(Status.Y);
                } else {
                    hostCheck.setStatus(Status.N);
                    hostCheck.setReason(REASON_TIMEOUT);
                }
            } catch (IOException e) {
                hostCheck.setStatus(Status.N);
                hostCheck.setReason(e.getMessage());
            }

        } catch (UnknownHostException e) {
            hostCheck.setStatus(Status.N);
            hostCheck.setReason(REASON_HOST_NOT_FOUND);
        }

        hostCheck.setRegistered(LocalDateTime.now());

        //insert
        try {
            hostRepository.insertHostCheck(hostCheck);
        } catch (Exception e) {
            throw new DefaultException("Insert Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async("LogPoolTaskExecutor")
    public void saveLog(LogReq.NewLogReq newLogReq) {
        MemberLog memberLog = MemberLog.builder()
                .memberNo(newLogReq.getMemberNo())
                .type(newLogReq.getType())
                .result(newLogReq.getResult())
                .build();

        logRepository.insertMemberLog(memberLog);

    }
}
