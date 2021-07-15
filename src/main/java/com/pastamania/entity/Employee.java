package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class Employee extends  CreateModifyBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private Boolean isOwner;

    @OneToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Store> stores;

}
