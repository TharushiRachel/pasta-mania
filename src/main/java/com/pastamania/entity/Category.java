package com.pastamania.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Getter
@Setter
public class Category extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryNo;

    private String id;

    private String name;

    private String color;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;
}
