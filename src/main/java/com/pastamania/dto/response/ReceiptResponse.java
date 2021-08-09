package com.pastamania.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Data
public class ReceiptResponse {

    List<Receipt> receipts;

    @Data
    public static class Receipt {

        @JsonProperty("receipt_number")
        private String receiptNumber;

        private String note;

        @JsonProperty("receipt_type")
        private String receiptType;

        @JsonProperty("refund_for")
        private String refundFor;

        private String order;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("receipt_date")
        private String receiptDate;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("cancelled_at")
        private String cancelledAt;

        private String source;

        @JsonProperty("total_money")
        private Double totalMoney;

        @JsonProperty("total_tax")
        private Double totalTax;

        @JsonProperty("points_earned")
        private Double pointsEarned;

        @JsonProperty("points_deducted")
        private Double pointsDeducted;

        @JsonProperty("point_balance")
        private Double pointBalance;

        @JsonProperty("customer_id")
        private String customerId;

        @JsonProperty("total_discount")
        private Double totalDiscount;

        @JsonProperty("employee_id")
        private String employeeId;

        @JsonProperty("store_id")
        private String storeId;

        @JsonProperty("pos_device_id")
        private String posDeviceId;

        @JsonProperty("dining_option")
        private String diningOption;

        private Integer tip;

        private Integer surcharge;

        @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private Set<ReceiptTotalDiscount> total_discounts;

        @Data
        public static class ReceiptTotalDiscount {

            private String id;

            @JsonProperty("pricing_type")
            private String pricingType;

            private String type;

            private String name;

            private Integer percentage;

            @JsonProperty("money_amount")
            private Double moneyAmount;
        }


        @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private Set<ReceiptTotalTax> total_taxes;

        @Data
        public static class ReceiptTotalTax {

            private String id;

            private String type;

            private String name;

            private Boolean rate;

            @JsonProperty("money_amount")
            private String moneyAmount;
        }


        @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private Set<ReceiptLineItem> line_items;

        @Data
        public static class ReceiptLineItem {

            private String id;

            @JsonProperty("item_id")
            private String itemId;

            @JsonProperty("variant_id")
            private String variantId;

            @JsonProperty("variant_name")
            private String variantName;

            private String sku;

            private Integer quantity;

            private Double price;

            @JsonProperty("gross_total_money")
            private Double grossTotalMoney;

            @JsonProperty("total_money")
            private Double totalMoney;

            private Double cost;

            @JsonProperty("cost_total")
            private Double costTotal;

            @JsonProperty("line_note")
            private String line_note;

            @JsonProperty("total_discount")
            private Double totalDiscount;

            @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
            private Set<LineItemLineDiscount> lineDiscounts;

            @Data
            public static class LineItemLineDiscount {

                private String id;

                @JsonProperty("pricing_type")
                private String pricingType;

                private String type;

                private String name;

                private Integer percentage;

                @JsonProperty("money_amount")
                private Double moneyAmount;
            }


            @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
            private Set<LineItemLineTax> lineTaxes;

            @Data
            public static class LineItemLineTax {

                private String id;

                private String type;

                private String name;

                private Boolean rate;

                @JsonProperty("money_amount")
                private String moneyAmount;
            }

            @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
            private Set<LineItemLineModifier> lineModifiers;


            @Data
            public static class LineItemLineModifier {

                private String id;

                @JsonProperty("modifier_option_id")
                private String modifierOptionId;

                private String name;

                private String option;

                private String price;

                @JsonProperty("money_amount")
                private Double moneyAmount;
            }


        }

        @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
        private Set<ReceiptPayment> payments;

        @Data
        public static class ReceiptPayment {

            @JsonProperty("payment_type_id")
            private String paymentTypeId;

            private String name;

            private String type;

            @JsonProperty("money_amount")
            private String moneyAmount;

            @JsonProperty("paid_at")
            private String paidAt;

            @JsonProperty("payment_details")
            private String paymentDetails;


        }
    }
}
