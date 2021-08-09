package com.pastamania.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Data
public class ItemResponse {

    List<Item> items;

    @Data
    public static class Item {

        private String id;

        private String handle;

        @JsonProperty("reference_id")
        private String referenceId;

        @JsonProperty("item_name")
        private String itemName;

        @JsonProperty("track_stock")
        private Boolean trackStock;

        @JsonProperty("sold_by_weight")
        private Boolean soldByWeight;

        @JsonProperty("is_composite")
        private Boolean isComposite;

        @JsonProperty("category_id")
        private String categoryId;

        private List<ComponentData> components;


        @Data
        public static class ComponentData {

            @JsonProperty("variant_id")
            private String variantId;

            private Integer quantity;

        }

        @JsonProperty("primary_supplier_id")
        private String primarySupplierId;

        @JsonProperty("tax_ids")
        private List<String> tax_ids;

        @JsonProperty("modifier_ids")
        private List<String> modifierIds;

        private String from;

        private String color;

        @JsonProperty
        private String imageUrl;

        private String image_url;

        private String option1Name;

        private String option2Name;

        private String option3Name;

        private Set<ItemVariant> variants;

        @Data
        public static class ItemVariant {

            @JsonProperty("variant_id")
            private String variantId;

            @JsonProperty("item_id")
            private String itemId;

            private String sku;

            @JsonProperty("reference_variant_id")
            private String referenceVariantId;

            @JsonProperty("option1_value")
            private String option1Value;

            @JsonProperty("option2_value")
            private String option2Value;

            @JsonProperty("option3_value")
            private String option3Value;

            private String barcode;

            private Double cost;

            @JsonProperty("purchase_cost")
            private Double purchaseCost;

            @JsonProperty("default_pricing_type")
            private String defaultPricingType;

            @JsonProperty("default_price")
            private Double defaultPrice;

            private Set<Store> stores;

            @Data
            public static class Store {

                @JsonProperty("store_id")
                private String storeId;

                @JsonProperty("pricing_type")
                private String pricingType;

                private Double price;

                @JsonProperty("available_for_sale")
                private String availableForSale;

                @JsonProperty("optimal_stock")
                private String optimalStock;

                @JsonProperty("low_stock")
                private String lowStock;

            }


        }


    }
}
