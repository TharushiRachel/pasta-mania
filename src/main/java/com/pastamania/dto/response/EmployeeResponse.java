package com.pastamania.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Data
public class EmployeeResponse {

    List<Employee> employees;

    @Data
    public static class Employee {

        private String id;

        private String name;

        private String email;

        @JsonProperty("phone_number")
        private String phoneNumber;

        @JsonProperty("is_owner")
        private Boolean isOwner;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("deleted_at")
        private String deletedAt;


    }
}
