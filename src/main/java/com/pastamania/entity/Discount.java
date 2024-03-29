package com.pastamania.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Getter
@Setter
public class Discount extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountNo;

    private String id;

    private String type;

    private String name;

    private Double discountPercent;

    private Boolean restrictedAccess;

    private String storesIds;

}
