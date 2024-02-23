package com.test.recruit.controller;

import com.test.recruit.data.common.ResponseData;
import com.test.recruit.data.dto.req.LogReq;
import com.test.recruit.data.dto.res.HostRes;
import com.test.recruit.data.dto.res.LogRes;
import com.test.recruit.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Log", description = "감사기록 관련 API")
@RequestMapping("/api/v1/log")
@RequiredArgsConstructor
@RestController
@Slf4j
public class LogController {

    private final LogService logService;

    @Operation(
            summary = "사용자 감사기록 확인",
            description = "관리자 전용"
    )
    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<ResponseData<List<LogRes.LogHistoryRes>>> getLog(@Schema(description = "페이지 번호(1~)") @RequestParam int page, @Schema(description = "한 페이지에 노출할 갯수") @RequestParam int size) {
        return new ResponseEntity<>(new ResponseData<>(logService.getLog(page, size)), HttpStatus.OK);
    }

}
