package org.nikdev.financialoperations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = {"org.nikdev.entityservice"})
public class FinancialOperationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialOperationsApplication.class, args);
    }

}
