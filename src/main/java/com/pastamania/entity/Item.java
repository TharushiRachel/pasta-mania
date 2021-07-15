package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class Item extends  CreateModifyBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String id;

    private String handle;

    private String referenceId;

    private String itemName;

    private String trackStock;

    private Boolean soldByWeight;

    private Boolean isComposite;

    private Boolean useProduction;

    @OneToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    private String primary_supplier_id;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Tax> taxIds;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Modifier> modifierIds;

    private String form;

    private String color;

    private String image_url;

    private String option1_name;

    private String option2_name;

    private String option3_name;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Varient> varients;

}
