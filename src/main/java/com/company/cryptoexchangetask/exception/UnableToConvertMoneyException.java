package com.company.cryptoexchangetask.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnableToConvertMoneyException extends BaseException {
    public UnableToConvertMoneyException(
            String inputCurrencyName,
            String outputCurrencyName,
            String secretKey
    ) {
        httpStatus = HttpStatus.BAD_REQUEST;
        description = "Unable to convert to "
                + secretKey + " " + outputCurrencyName
                + " wallet more money than is on the "
                + secretKey + " " + secretKey + " wallet";
    }
}
