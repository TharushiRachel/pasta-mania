package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
@Table(name = "receipt_total_discount")
public class ReceiptTotalDiscount extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptTotalDiscountNo;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    private String id;

    private String pricingType;

    private String type;

    private String name;

    private Integer percentage;

    private Double moneyAmount;

}
