package com.test.recruit.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DefaultException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private HttpStatus resCode;

	public DefaultException(String message, HttpStatus resCode) {
		super(message);
		this.resCode = resCode;
	}

}
