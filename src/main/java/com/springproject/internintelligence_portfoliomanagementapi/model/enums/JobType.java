package com.springproject.internintelligence_portfoliomanagementapi.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum JobType {
    INTERNSHIP,
    PART_TIME,
    FULL_TIME,
    FREELANCE;

    public static Map<String ,JobType> MAP=new HashMap<>();

    static {
        for (JobType type: values()){
            MAP.put(type.name().toLowerCase(),type);
        }
    }

    public static JobType fromString(String value){
        if (value==null){
            return null;
        }
        JobType type = MAP.get(value.toLowerCase());

        if (type==null){
            throw new IllegalArgumentException("Invalid job type: " + value);
        }
        return type;

    }
}
