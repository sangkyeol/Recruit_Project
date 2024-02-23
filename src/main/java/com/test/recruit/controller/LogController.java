package com.test.recruit.controller;

import com.test.recruit.data.common.ResponseData;
import com.test.recruit.data.dto.req.LogReq;
import com.test.recruit.data.dto.res.HostRes;
import com.test.recruit.data.dto.res.LogRes;
import com.test.recruit.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/log")
@RequiredArgsConstructor
@RestController
@Slf4j
public class LogController {

    private final LogService logService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<ResponseData<List<LogRes.LogHistoryRes>>> getLog(@RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(new ResponseData<>(logService.getLog(page, size)), HttpStatus.OK);
    }

}
