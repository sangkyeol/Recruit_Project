package com.test.recruit.service.impl;


import com.test.recruit.data.dto.req.LogReq;
import com.test.recruit.data.dto.security.CustomUserDetails;
import com.test.recruit.data.entity.Member;
import com.test.recruit.data.enumval.MemberStatus;
import com.test.recruit.data.enumval.Status;
import com.test.recruit.exception.DefaultException;
import com.test.recruit.repository.LogRepository;
import com.test.recruit.repository.MemberRepository;
import com.test.recruit.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final LogService logService;

    @Override
    @Transactional
    public CustomUserDetails loadUserByUsername(final String username) {
        Member member = memberRepository.selectMemberById(username);
        if (member == null) {
            throw new DefaultException("This member does not exist.", HttpStatus.UNAUTHORIZED);
        }

        logService.saveLog(LogReq.NewLogReq.builder()
                .type("LOGIN")
                .memberNo(member.getMemberNo())
                .result(Status.Y)
                .build());

        return createUser(member);
    }

    private CustomUserDetails createUser(Member member) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(member.getRole().name()));

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .id(member.getId())
                .memberNo(member.getMemberNo())
                .password(member.getPassword())
                .authorities(grantedAuthorities)
                .memberStatus(MemberStatus.ALIVE)
                .build();

        return customUserDetails;
    }
}
