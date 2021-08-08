package com.pastamania.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class LineItemLineDiscount extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptTotalDiscountNo;

    private String id;

    private String pricingType;

    private String type;

    private String name;

    private Integer percentage;

    private Double moneyAmount;

}
