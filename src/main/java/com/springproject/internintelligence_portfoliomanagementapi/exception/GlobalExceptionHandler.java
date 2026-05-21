package com.springproject.internintelligence_portfoliomanagementapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.springproject.internintelligence_portfoliomanagementapi.model.enums.ExceptionConstant.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse("Validation error");

        log.error("Validation error: {}", message);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse("VALIDATION_ERROR", message));
    }

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

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(FORBIDDEN)
    public ExceptionResponse handle(ForbiddenException ex){
        log.error("ForbiddenException ",ex);
        return new ExceptionResponse(ex.getCode(), ex.getMessage());
    }
}
