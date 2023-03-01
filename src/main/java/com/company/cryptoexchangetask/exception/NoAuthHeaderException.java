package com.company.cryptoexchangetask.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoAuthHeaderException extends BaseException {
    public NoAuthHeaderException(HttpStatus httpStatus, String description) {
        super(httpStatus, description);
    }
}
