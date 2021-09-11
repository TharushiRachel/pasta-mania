package com.pastamania.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Getter
@Setter
@Table(name = "item_variant")
public class ItemVariant extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemVariantNo;

    @OneToMany(mappedBy = "itemVariant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<VariantStore> stores;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private String variantId;

//    private String itemId;

    private String sku;

    private String referenceVariantId;

    private String option1Value;

    private String option2Value;

    private String option3Value;

    private String barcode;

    private Double cost;

    private Double purchaseCost;

    private String defaultPricingType;

    private Double defaultPrice;


}
