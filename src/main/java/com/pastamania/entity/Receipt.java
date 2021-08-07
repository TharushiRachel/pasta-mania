package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class Receipt extends  CreateModifyBaseEntity{


//
//    receipt_number : "2-1008"
//    note : null
//    receipt_type : "SALE"
//    refund_for : null
//    order : null
//    created_at : "2020-06-23T08:35:47.047Z"
//    receipt_date : "2020-06-23T08:35:47.047Z"
//    updated_at : "2020-06-23T08:35:47.047Z"
//    cancelled_at : null
//    source : "My app"
//    total_money : 17.52
//    total_tax : 0.92
//    points_earned : 1.75
//    points_deducted : 0
//    points_balance : 332.32
//    customer_id : "c71758a2-79bf-11ea-bde9-1269e7c5a22d"
//    total_discount : 7.4
//    employee_id : "58f53835-7a17-11ea-bde9-1269e7c5a22d"
//    store_id : "42dc2cec-6f40-11ea-bde9-1269e7c5a22d"
//    pos_device_id : "1cce2f2e-8033-4b67-ad2a-b9d1c749ec26"
//    dining_option : "Dine in"
//    total_discounts
//            total_taxes
//    tip : 0
//    surcharge : 0
//    line_items
//            payments

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptNo;

    private String receiptNumber;

    private String receiptType;

    private String refundFor;

    private String order;

    private String created_at;

    private String receipt_date;

    private String updated_at;

    private String cancelled_at;

    private String source;

    private Double totalMoney;

    private Double totalTax;

    private Double pointsEarned;

    private Double pointsDeducted;

    private Double pointBalance;

    private String customerId;

    private Double totalDiscount;

    private String employeeId;

    private String storeId;

    private String posDeviceId;

    private String dining_option;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<ItemVariant> variants;

}
