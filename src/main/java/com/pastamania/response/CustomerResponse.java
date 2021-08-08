package com.pastamania.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Data
public class CustomerResponse {

    List<Customer> customers;

    @Data
    public static class Customer {

        private String id;

        private String name;

        private String email;

        @JsonProperty("phone_number")
        private String phoneNumber;

        private String address;

        private String city;

        private String region;

        @JsonProperty("postal_code")
        private String postalCode;

        @JsonProperty("country_code")
        private String countryCode;

        private String note;

        @JsonProperty("customer_code")
        private String customerCode;

        @JsonProperty("first_visit")
        private String firstVisit;

        @JsonProperty("last_visit")
        private String lastVisit;

        @JsonProperty("total_visits")
        private Integer totalVisits;

        @JsonProperty("total_spent")
        private String totalSpent;

        @JsonProperty("total_points")
        private String totalPoints;

        @JsonProperty("permanent_deletion_at")
        private String permanentDeletionAt;

    }
}
