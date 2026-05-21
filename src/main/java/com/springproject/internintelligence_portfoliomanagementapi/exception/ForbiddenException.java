package com.springproject.internintelligence_portfoliomanagementapi.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

    private final String code;

    public ForbiddenException(String message, String code) {
        super(message);
        this.code = code;
    }
}