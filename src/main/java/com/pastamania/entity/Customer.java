package com.pastamania.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class Customer extends  CreateModifyBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String city;

    private String region;

    private String postalCode;

    private String countryCode;

    private String note;

    private String customerCode;

    private String firstVisit;

    private String lastVisit;

    private Integer totalVisits;

    private String totalSpent;

    private String total_points;

    private String permanent_deletion_at;


}
