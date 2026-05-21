package com.springproject.internintelligence_portfoliomanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PortfolioManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioManagementApiApplication.class, args);
    }

}
