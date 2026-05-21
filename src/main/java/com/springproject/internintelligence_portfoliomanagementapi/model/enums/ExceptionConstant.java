package com.springproject.internintelligence_portfoliomanagementapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionConstant {

    EDUCATION_NOT_FOUND("EDUCATION_NOT_FOUND", "Education with id: %s not found"),
    USER_NOT_FOUND("USER_NOT_FOUND", "User with id: %s not found"),
    EXPERIENCE_NOT_FOUND("EXPERIENCE_NOT_FOUND", "Experience with id: %s not found"),
    PROJECT_NOT_FOUND("PROJECT_NOT_FOUND", "Project with id: %s not found"),
    PORTFOLIO_NOT_FOUND("PORTFOLIO_NOT_FOUND", "Portfolio with id: %s not found"),
    SKILL_NOT_FOUND("SKILL_NOT_FOUND", "Skill with id: %s not found"),


    UNEXPECTED_EXCEPTION("UNEXPECTED_EXCEPTION", "Unexpected exception occurred"),


    EDUCATION_ALREADY_EXISTS("EDUCATION_ALREADY_EXISTS",
            "Education already exists for this portfolio"),

    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS",
            "User already exists"),

    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS",
            "Email already exists"),

    SKILL_ALREADY_EXISTS("SKILL_ALREADY_EXISTS",
            "Skill already exists for this portfolio"),


    INVALID_INPUT("INVALID_INPUT", "Invalid input data"),
    INVALID_DATE_RANGE("INVALID_DATE_RANGE", "Start date cannot be after end date"),


    FORBIDDEN("FORBIDDEN", "You do not have permission to perform this action");


    String code;
    String message;
}
