package com.test.recruit.exception;

import com.test.recruit.data.common.ResponseData;
import com.test.recruit.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

    @Autowired
    Util util;

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<ResponseData<?>> handleDefaultException(DefaultException e) {
        if (e.getResCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            util.alertException(e.getMessage());
        }

        return new ResponseEntity<>(new ResponseData<>(e.getMessage()), e.getResCode());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseData<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new ResponseEntity<>(new ResponseData<>("Request Param is missing"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseData<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new ResponseData<>("Request Body is missing"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public HttpEntity<ResponseData<?>> handleInvalidTokenException(InvalidTokenException e) {
        return new ResponseEntity<>(new ResponseData<>(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public HttpEntity<ResponseData<?>> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>(new ResponseData<>("Not Authorized"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<?>> handleException(Exception e) {
        util.alertException(e.getMessage());
        return new ResponseEntity<>(new ResponseData<>(e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}