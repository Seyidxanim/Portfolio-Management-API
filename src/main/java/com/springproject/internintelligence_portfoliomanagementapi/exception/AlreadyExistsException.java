package com.springproject.internintelligence_portfoliomanagementapi.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {

    private String code;

    public AlreadyExistsException(String code, String message) {
        super(message);
        this.code = code;
    }
}
