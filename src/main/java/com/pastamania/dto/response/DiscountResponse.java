package com.pastamania.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Data
public class DiscountResponse {

    List<Discount> discounts;

    @Data
    public static class Discount {

        private String id;

        private String type;

        private String name;

        private List<String> stores;

        @JsonProperty("discount_percent")
        private Double discountPercent;

        @JsonProperty("restricted_access")
        private Boolean restrictedAccess;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("deleted_at")
        private String deletedAt;


    }
}
