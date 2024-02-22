package com.test.recruit.service.impl;

import com.test.recruit.data.dto.req.HostReq;
import com.test.recruit.data.dto.res.HostRes;
import com.test.recruit.data.dto.security.CustomUserDetails;
import com.test.recruit.data.entity.HostList;
import com.test.recruit.data.entity.Member;
import com.test.recruit.exception.DefaultException;
import com.test.recruit.repository.HostRepository;
import com.test.recruit.service.HostService;
import com.test.recruit.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class HostServiceImpl implements HostService {
    private final HostRepository hostRepository;

    @Override
    public void postHost(HostReq.NewHostReq req) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) loggedInUser.getPrincipal();

        if(hostRepository.selectHostListCnt() == 100) {
            throw new DefaultException("Full Count", HttpStatus.BAD_REQUEST);
        }

        if(hostRepository.selectHostListCntByNameAndIp(req.getName(), req.getIp()) > 0) {
            throw new DefaultException("Duplicated Value", HttpStatus.BAD_REQUEST);
        }

        try {
            hostRepository.insertHostList(HostList.builder()
                            .memberNo(userDetails.getMemberNo())
                            .ip(req.getIp())
                            .name(req.getName())
                    .build());
        } catch (Exception e) {
            throw new DefaultException("Insert Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<HostRes.HostItemRes> getHost() {
        return null;
    }
}
