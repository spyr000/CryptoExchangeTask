package com.company.cryptoexchangetask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToWithdrawMoneyException extends BaseException {
    public UnableToWithdrawMoneyException(String secretKey) {
        httpStatus = HttpStatus.BAD_REQUEST;
        description = "Unable to withdraw more money than is on the "
                + secretKey
                + " wallet";
    }
}
