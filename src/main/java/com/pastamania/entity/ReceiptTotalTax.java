package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class ReceiptTotalTax extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long variantStoreNo;

    @ManyToOne
    @JoinColumn(name="item_variant_id", nullable = false)
    private ItemVariant itemVariant;

    private String storeId;

    private String pricingType;

    private String price;

    private Boolean availableForSale;

    private String optimalStock;

    private String low_stock;

}
