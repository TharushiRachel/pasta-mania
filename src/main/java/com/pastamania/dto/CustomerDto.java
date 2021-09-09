package com.pastamania.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Data
public class CustomerDto {

    private String companyName;

    private String headerName;

    private String Date;

    private String brand;

    private Integer totalTotalVisits;

    private Double totalPointBalance;

    private Double totalTotalSpent;

    private List<Customer> customers;

    @Data
    public static class Customer {
        private String name;

        private String email;

        private String phoneNumber;

        private String firstVisit;

        private String lastVisit;

        private String totalVisits;

        private String totalSpent;

        private String total_points;

    }
}
