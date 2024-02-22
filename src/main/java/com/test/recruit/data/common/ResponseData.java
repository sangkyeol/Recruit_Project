package com.test.recruit.data.common;

import lombok.Getter;
import lombok.Setter;

import static com.test.recruit.constant.Constant.FAIL;
import static com.test.recruit.constant.Constant.SUCCESS;


@Getter
@Setter
public class ResponseData<T> {
    private String status;
    private String message;
    private T data;

    public ResponseData(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseData(String message) {
        this.status = FAIL;
        this.message = message;
    }

    public ResponseData(T data) {
        this.status = SUCCESS;
        this.data = data;
    }

    public ResponseData() {
        this.status = SUCCESS;
    }

    public ResponseData(boolean status, String message) {
        if (status) {
            this.status = SUCCESS;
        } else {
            this.status = FAIL;
        }

        this.message = message;
    }

    public ResponseData(boolean status, String message, T data) {
        if (status) {
            this.status = SUCCESS;
        } else {
            this.status = FAIL;
        }

        this.message = message;
        this.data = data;
    }
}
