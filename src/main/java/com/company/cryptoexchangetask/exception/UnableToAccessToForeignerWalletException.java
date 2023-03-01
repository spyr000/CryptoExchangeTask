package com.company.cryptoexchangetask.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnableToAccessToForeignerWalletException extends BaseException {
    public UnableToAccessToForeignerWalletException(HttpStatus httpStatus, String description) {
        super(httpStatus, description);
    }
}
