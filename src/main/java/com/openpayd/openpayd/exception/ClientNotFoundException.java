package com.openpayd.openpayd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not find client with id.")
public class ClientNotFoundException extends Exception {
    public ClientNotFoundException(String message) {
        super(message);
    }

}
