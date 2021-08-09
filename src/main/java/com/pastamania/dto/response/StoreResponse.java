package com.pastamania.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Data
public class StoreResponse {

    List<Store> stores;

    @Data
    public static class Store {

        private String id;

        private String name;

        private String address;

        private String description;

        @JsonProperty("phone_number")
        private String phoneNumber;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("deleted_at")
        private String deletedAt;


    }
}
