package com.springproject.internintelligence_portfoliomanagementapi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum EducationDegree {
    ASSOCIATE,
    BACHELOR,
    MASTER,
    DOCTORATE;

    public static final Map<String, EducationDegree> MAP = new HashMap<>();

    static {
        for (EducationDegree degree : values()) {
            MAP.put(degree.name().toLowerCase(), degree);
        }
    }

    @JsonCreator
    public static EducationDegree fromString(String value) {
        if (value == null) {
            return null;
        }
        EducationDegree degree = MAP.get(value.toLowerCase());

        if (degree == null) {
            throw new IllegalArgumentException("Invalid education degree: " + value);
        }
        return degree;
    }


}
