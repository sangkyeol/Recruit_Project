package com.test.recruit.service.impl;

import com.test.recruit.data.dto.req.HostReq;
import com.test.recruit.data.dto.res.HostRes;
import com.test.recruit.data.dto.security.CustomUserDetails;
import com.test.recruit.data.entity.HostCheck;
import com.test.recruit.data.entity.HostList;
import com.test.recruit.data.enumval.Status;
import com.test.recruit.exception.DefaultException;
import com.test.recruit.repository.HostRepository;
import com.test.recruit.service.HostService;
import com.test.recruit.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.test.recruit.constant.Constant.REASON_HOST_NOT_FOUND;
import static com.test.recruit.constant.Constant.REASON_TIMEOUT;

@Slf4j
@RequiredArgsConstructor
@Service
public class HostServiceImpl implements HostService {
    private final HostRepository hostRepository;

    private final AsyncService asyncService;
    private final Util util;

    @Value("${com.test.recruit.timeout}")
    private int timeout;

    @Override
    public void postHost(HostReq.NewHostReq req) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) loggedInUser.getPrincipal();

        if (hostRepository.selectHostListCnt() == 100) {
            util.writeLog("POST_HOST", Status.N);
            throw new DefaultException("Full Count", HttpStatus.BAD_REQUEST);
        }

        if (hostRepository.selectHostListCntByNameAndIp(req.getName(), req.getIp()) > 0) {
            util.writeLog("POST_HOST", Status.N);
            throw new DefaultException("Duplicated Value", HttpStatus.BAD_REQUEST);
        }

        if (!util.checkIp(req.getIp())) {
            util.writeLog("POST_HOST", Status.N);
            throw new DefaultException("Not IPV4 format", HttpStatus.BAD_REQUEST);
        }

        try {
            hostRepository.insertHostList(HostList.builder()
                    .memberNo(userDetails.getMemberNo())
                    .ip(req.getIp())
                    .name(req.getName())
                    .build());
            util.writeLog("POST_HOST", Status.Y);
        } catch (Exception e) {
            util.writeLog("POST_HOST", Status.N);
            throw new DefaultException("Insert Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<HostRes.HostItemRes> getHost() {
        List<HostList> hostLists = hostRepository.selectHostList();
        List<HostRes.HostItemRes> hostItemResList = hostLists.stream().map(item -> HostRes.HostItemRes.builder()
                .hostNo(item.getHostNo())
                .name(item.getName())
                .ip(item.getIp())
                .registered(item.getRegistered())
                .updated(item.getRegistered())
                .build()).collect(Collectors.toList());

        util.writeLog("GET_HOST", Status.Y);

        return hostItemResList;
    }

    @Override
    public void deleteHost(int hostNo) {
        //validation
        HostList check = hostRepository.selectHostListByHostNo(hostNo);
        if (check == null) {
            util.writeLog("DELETE_HOST", Status.N);
            throw new DefaultException("Host not found.", HttpStatus.NOT_FOUND);
        }

        try {
            hostRepository.updateHostListActiveStatusDead(hostNo);
            util.writeLog("DELETE_HOST", Status.Y);
        } catch (Exception e) {
            util.writeLog("DELETE_HOST", Status.N);
            throw new DefaultException("Insert Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void putHost(HostReq.PutHostReq req) {
        HostList hostList = hostRepository.selectHostListByHostNo(req.getHostNo());
        if (hostList == null) {
            util.writeLog("PUT_HOST", Status.N);
            throw new DefaultException("Host not found.", HttpStatus.NOT_FOUND);
        }

        boolean nameChanged = false, ipChanged = false;

        if (StringUtils.hasText(req.getName()) && !req.getName().equals(hostList.getName())) {
            hostList.setName(req.getName());
            nameChanged = true;
        }

        if (StringUtils.hasText(req.getIp()) && !req.getIp().equals(hostList.getIp())) {
            if (!util.checkIp(req.getIp())) {
                util.writeLog("PUT_HOST", Status.N);
                throw new DefaultException("Not IPV4 format", HttpStatus.BAD_REQUEST);
            }
            hostList.setIp(req.getIp());
            ipChanged = true;
        }

        //validation
        if (nameChanged == true) {
            if (hostRepository.selectHostListCntByName(req.getName()) > 0) {
                util.writeLog("PUT_HOST", Status.N);
                throw new DefaultException("Duplicated Name", HttpStatus.BAD_REQUEST);
            }
        }

        if (ipChanged == true) {
            if (hostRepository.selectHostListCntByIp(req.getIp()) > 0) {
                util.writeLog("PUT_HOST", Status.N);
                throw new DefaultException("Duplicated IP", HttpStatus.BAD_REQUEST);
            }
        }

        try {
            hostRepository.updateHostList(hostList);
            util.writeLog("PUT_HOST", Status.Y);
        } catch (Exception e) {
            util.writeLog("PUT_HOST", Status.N);
            throw new DefaultException("Insert Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public HostRes.HostStatusRes getHostStatus(int hostNo) {
        HostList hostList = hostRepository.selectHostListByHostNo(hostNo);
        if (hostList == null) {
            util.writeLog("GET_HOST_STATUS", Status.N);
            throw new DefaultException("Host not found.", HttpStatus.NOT_FOUND);
        }

        HostCheck hostCheck = HostCheck.builder()
                .hostNo(hostList.getHostNo())
                .build();

        try {
            InetAddress inetAddress = InetAddress.getByName(hostList.getIp());
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
            util.writeLog("GET_HOST_STATUS", Status.N);
            throw new DefaultException("Insert Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        util.writeLog("GET_HOST_STATUS", Status.Y);
        return HostRes.HostStatusRes.builder()
                .hostNo(hostCheck.getHostNo())
                .reason(hostCheck.getReason())
                .status(hostCheck.getStatus())
                .checked(hostCheck.getRegistered())
                .build();
    }


    @Override
    public void checkHostStatusAsync() {
        List<Integer> targetHostNoList = new ArrayList<>();

        List<HostList> hostLists = hostRepository.selectHostList();
        targetHostNoList = hostLists.stream().map(item -> {
            return item.getHostNo();
        }).collect(Collectors.toList());

        List<HostList> targetList = hostRepository.selectHostListByMultiHostNo(targetHostNoList);
        if (targetList.size() == 0) {
            return;
        }

        for (HostList target : targetList) {
            asyncService.updateAfterCheckNW(target);
        }
    }


    @Override
    public List<HostRes.HostMonitorRes> getHostMonitor(Integer hostNo) {
        if (hostNo != null) {
            HostList hostList = hostRepository.selectHostListByHostNo(hostNo);
            if (hostList == null) {
                util.writeLog("GET_HOST_MONITOR", Status.N);
                throw new DefaultException("Host not found.", HttpStatus.NOT_FOUND);
            }
        }

        List<HostList> monitorList = hostRepository.selectHostListWithCheck(hostNo);
        util.writeLog("GET_HOST_MONITOR", Status.Y);
        return monitorList.stream().map(item -> {
            return HostRes.HostMonitorRes.builder()
                    .hostNo(item.getHostNo())
                    .name(item.getName())
                    .ip(item.getIp())
                    .status(item.getStatus())
                    .reason(item.getReason())
                    .lastChecked(item.getLastChecked())
                    .build();
        }).collect(Collectors.toList());
    }
}
