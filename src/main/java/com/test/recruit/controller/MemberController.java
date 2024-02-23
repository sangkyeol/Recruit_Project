package com.test.recruit.controller;

import com.test.recruit.data.common.ResponseData;
import com.test.recruit.data.dto.req.MemberReq;
import com.test.recruit.data.dto.res.TokenRes;
import com.test.recruit.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "회원 관련 API")
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "회원 등록",
            description = "과제 내용에 따로 없어 권한 구분 하지 않음"
    )
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseData> signUp(@RequestBody @Validated MemberReq.SignUpReq req) {
        memberService.signUp(req);
        return new ResponseEntity<>(new ResponseData<>(), HttpStatus.OK);
    }

    @Operation(
            summary = "로그인",
            description = "Security 예외처리됨(Public)"
    )
    @PostMapping("/login")
    public ResponseEntity<ResponseData<TokenRes>> login(@RequestBody @Validated MemberReq.SignInReq req) {
        return new ResponseEntity<>(new ResponseData<>(memberService.login(req)), HttpStatus.OK);
    }

}
