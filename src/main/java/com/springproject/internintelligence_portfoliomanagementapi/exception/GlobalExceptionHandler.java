package com.springproject.internintelligence_portfoliomanagementapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(Exception.class)
   @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception ex) {
       log.error("Exception {}", String.valueOf(ex));
        return new ExceptionResponse(UNEXPECTED_EXCEPTION.getCode(), UNEXPECTED_EXCEPTION.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handle(NotFoundException ex){
       log.error("NotFoundException ",ex);
       return new ExceptionResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(CONFLICT)
    public ExceptionResponse handle(AlreadyExistsException ex){
       log.error("AlreadyExistsException ",ex);
       return new ExceptionResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(BadRequestException ex){
        log.error("BadRequestException ",ex);
        return new ExceptionResponse(ex.getCode(), ex.getMessage());
    }
}
