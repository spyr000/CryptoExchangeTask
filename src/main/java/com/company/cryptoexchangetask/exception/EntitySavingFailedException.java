package com.company.cryptoexchangetask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntitySavingFailedException extends BaseException{
    public EntitySavingFailedException(Class<?> clazz) {
        httpStatus = HttpStatus.NOT_FOUND;
        description = "Could not save " + clazz.getSimpleName() + " entity in database";
    }
}
