package com.company.cryptoexchangetask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UnableToCountTransactionsException extends BaseException {
    public UnableToCountTransactionsException(LocalDateTime from, LocalDateTime to) {
        httpStatus = HttpStatus.NOT_FOUND;
        description = "Could not count transactions for the time interval from " +
                from.toString() + " to " + to.toString();
    }
}
