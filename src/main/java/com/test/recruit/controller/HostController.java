package com.test.recruit.controller;

import com.test.recruit.data.common.ResponseData;
import com.test.recruit.data.dto.req.HostReq;
import com.test.recruit.data.dto.req.MemberReq;
import com.test.recruit.data.entity.Member;
import com.test.recruit.service.HostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/host")
@RequiredArgsConstructor
@RestController
@Slf4j
public class HostController {
    private final HostService hostService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/manage")
    public ResponseEntity<ResponseData> postHost(@RequestBody @Validated HostReq.NewHostReq req) {
        hostService.postHost(req);
        return new ResponseEntity<>(new ResponseData<>(), HttpStatus.OK);
    }
}
