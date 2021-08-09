package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
@Table(name = "receipt_line_item")
public class ReceiptLineItem extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptLineItemNo;

    private String id;

    private String itemId;

    private String variantId;

    private String variantName;

    private String sku;

    private Integer quantity;

    private Double price;

    private Double grossTotalMoney;

    private Double totalMoney;

    private Double cost;

    private Double costTotal;

    private String lineNote;

    private Double totalDiscount;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<LineItemLineDiscount> lineDiscounts;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<LineItemLineTax> lineTaxes;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<LineItemLineModifier> lineModifiers;

}
