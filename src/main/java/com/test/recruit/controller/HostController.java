package com.test.recruit.controller;

import com.test.recruit.data.common.ResponseData;
import com.test.recruit.data.dto.req.HostReq;
import com.test.recruit.data.dto.req.MemberReq;
import com.test.recruit.data.dto.res.HostRes;
import com.test.recruit.data.entity.Member;
import com.test.recruit.service.HostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/host")
@RequiredArgsConstructor
@RestController
@Slf4j
public class HostController {
    private final HostService hostService;

    // ----- 호스트 등록 관리 ------
    @Secured("ROLE_ADMIN")
    @PostMapping("/manage")
    public ResponseEntity<ResponseData> postHost(@RequestBody @Validated HostReq.NewHostReq req) {
        hostService.postHost(req);
        return new ResponseEntity<>(new ResponseData<>(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/manage")
    public ResponseEntity<ResponseData<List<HostRes.HostItemRes>>> getHost() {
        return new ResponseEntity<>(new ResponseData<>(hostService.getHost()), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/manage/{hostNo}")
    public ResponseEntity<ResponseData> deleteHost(@PathVariable("hostNo") int hostNo) {
        hostService.deleteHost(hostNo);
        return new ResponseEntity<>(new ResponseData<>(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/manage")
    public ResponseEntity<ResponseData> putHost(@RequestBody @Validated HostReq.PutHostReq req) {
        hostService.putHost(req);
        return new ResponseEntity<>(new ResponseData<>(), HttpStatus.OK);
    }

    // ----- 호스트 등록 관리 ------

    // ----- 호스트 현재 상태 조회 ------
    @GetMapping("/status/{hostNo}")
    public ResponseEntity<ResponseData<HostRes.HostStatusRes>> getHostStatus(@PathVariable("hostNo") int hostNo) {
        return new ResponseEntity<>(new ResponseData<>(hostService.getHostStatus(hostNo)), HttpStatus.OK);
    }
    // ----- 호스트 현재 상태 조회 ------

    // ----- 호스트 상태 모니터링 ------
    @GetMapping("/monitor")
    public ResponseEntity<ResponseData<List<HostRes.HostMonitorRes>>> getHostMonitor(@RequestParam(required = false) Integer hostNo) {
        return new ResponseEntity<>(new ResponseData<>(hostService.getHostMonitor(hostNo)), HttpStatus.OK);
    }
    // ----- 호스트 상태 모니터링 ------

}
