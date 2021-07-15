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
public class Varient extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sku;

    private String referenceVariantId;

    private String option1_value;

    private String option2_value;

    private String option3_value;

    private Double cost;

    private String barcode;

    private Double purchaseCost;

    private String defaultPricingType;

    private Double defaultPrice;


}
