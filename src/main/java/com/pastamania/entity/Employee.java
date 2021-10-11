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
public class Employee extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeNo;

    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private Boolean isOwner;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

}
