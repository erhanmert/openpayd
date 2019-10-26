package com.openpayd.openpayd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not find account with id.")
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }

}
