package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class ModifierOption extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modifierOptionId;

    private String id;

    private String name;

    private Double price;

    private Integer position;

}
