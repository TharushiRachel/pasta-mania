package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
@Table(name = "item")
public class Item extends  CreateModifyBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemNo;

    private String id;

    private String handle;

    private String referenceId;

    private String itemName;

    private String trackStock;

    private Boolean soldByWeight;

    private Boolean isComposite;

    private Boolean useProduction;

    private String categoryId;

    private String primary_supplier_id;

    private String taxIds;

    private String modifierIds;

    private String form;

    private String color;

    private String image_url;

    private String option1_name;

    private String option2_name;

    private String option3_name;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<ItemVariant> variants;

}
