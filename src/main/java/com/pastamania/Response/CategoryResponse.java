package com.pastamania.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Data
public class CategoryResponse {

    List<Category> categories;

    @Data
    public static class Category {

        private String id;

        private String name;

        private String color;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("deleted_at")
        private String deletedAt;


    }
}
