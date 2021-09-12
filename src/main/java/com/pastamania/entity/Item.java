package com.pastamania.entity;

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
@Table(name = "item")
public class Item {

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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "item")
    private Set<ItemVariant> variants;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

}
