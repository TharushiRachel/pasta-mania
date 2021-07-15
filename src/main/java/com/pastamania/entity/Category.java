package com.pastamania.entity;

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
public class Category extends  CreateModifyBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String id;

    private String name;

    private String color;
}
