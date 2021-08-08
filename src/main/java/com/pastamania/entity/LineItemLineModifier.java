package com.pastamania.entity;//package com.pastamania.entity;

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
public class LineItemLineModifier extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineItemLineModifierNo;

    private String id;

    private String modifierOptionId;

    private String name;

    private String option;

    private String price;

    private Double moneyAmount;
}
