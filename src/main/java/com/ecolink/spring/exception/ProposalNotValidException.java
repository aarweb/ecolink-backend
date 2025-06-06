package com.ecolink.spring.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProposalNotValidException extends RuntimeException {
    public ProposalNotValidException(String message) {
        super(message);
    }
}
