package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class ReceiptLineItem extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptLineItemNo;

    private String id;

    private String item_id;

    private String variant_id;

    private Boolean variant_name;

    private String sku;

    private Integer quantity;

    private Double price;

    private Double grossTotalMoney;

    private Double totalMoney;

    private Double cost;

    private Double costTotal;

    private String line_note;

    private Double totalDiscount;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<LineItemLineDiscount> lineDiscounts;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<LineItemLineTax> lineTaxes;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<LineItemLineModifier> lineModifiers;

}
