package com.test.recruit.controller;

import com.test.recruit.data.common.ResponseData;
import com.test.recruit.data.dto.req.HostReq;
import com.test.recruit.data.dto.req.MemberReq;
import com.test.recruit.data.dto.res.HostRes;
import com.test.recruit.data.entity.Member;
import com.test.recruit.service.HostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Host", description = "호스트 관련 API")
@RequestMapping("/api/v1/host")
@RequiredArgsConstructor
@RestController
@Slf4j
public class HostController {
    private final HostService hostService;

    // ----- 호스트 등록 관리 ------
    @Operation(
            summary = "새로운 호스트 추가",
            description = "관리자 전용"
    )
    @Secured("ROLE_ADMIN")
    @PostMapping("/manage")
    public ResponseEntity<ResponseData> postHost(@RequestBody @Validated HostReq.NewHostReq req) {
        hostService.postHost(req);
        return new ResponseEntity<>(new ResponseData<>(), HttpStatus.OK);
    }

    @Operation(
            summary = "호스트 목록 조회",
            description = "관리자 전용"
    )
    @Secured("ROLE_ADMIN")
    @GetMapping("/manage")
    public ResponseEntity<ResponseData<List<HostRes.HostItemRes>>> getHost() {
        return new ResponseEntity<>(new ResponseData<>(hostService.getHost()), HttpStatus.OK);
    }

    @Operation(
            summary = "호스트 삭제",
            description = "관리자 전용"
    )
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/manage/{hostNo}")
    public ResponseEntity<ResponseData> deleteHost(@Schema(description = "삭제할 호스트 식별자") @PathVariable("hostNo") int hostNo) {
        hostService.deleteHost(hostNo);
        return new ResponseEntity<>(new ResponseData<>(), HttpStatus.OK);
    }

    @Operation(
            summary = "호스트 수정",
            description = "관리자 전용"
    )
    @Secured("ROLE_ADMIN")
    @PutMapping("/manage")
    public ResponseEntity<ResponseData> putHost(@RequestBody @Validated HostReq.PutHostReq req) {
        hostService.putHost(req);
        return new ResponseEntity<>(new ResponseData<>(), HttpStatus.OK);
    }

    // ----- 호스트 등록 관리 ------

    // ----- 호스트 현재 상태 조회 ------
    @Operation(
            summary = "호스트 현재 상태 전용",
            description = "권한구분 없이 가능"
    )
    @GetMapping("/status/{hostNo}")
    public ResponseEntity<ResponseData<HostRes.HostStatusRes>> getHostStatus(@Schema(description = "조회할 호스트 식별자") @PathVariable("hostNo") int hostNo) {
        return new ResponseEntity<>(new ResponseData<>(hostService.getHostStatus(hostNo)), HttpStatus.OK);
    }
    // ----- 호스트 현재 상태 조회 ------

    // ----- 호스트 상태 모니터링 ------
    @Operation(
            summary = "호스트 상태 모니터링",
            description = "권한구분 없이 가능"
    )
    @GetMapping("/monitor")
    public ResponseEntity<ResponseData<List<HostRes.HostMonitorRes>>> getHostMonitor(@Schema(description = "조회할 호스트 식별자 (옵션)") @RequestParam(required = false) Integer hostNo) {
        return new ResponseEntity<>(new ResponseData<>(hostService.getHostMonitor(hostNo)), HttpStatus.OK);
    }
    // ----- 호스트 상태 모니터링 ------

}
