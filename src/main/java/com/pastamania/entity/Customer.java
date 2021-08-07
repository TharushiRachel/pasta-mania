package com.pastamania.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class Customer extends  CreateModifyBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerNo;

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

    private String totalVisits;

    private String totalSpent;

    private String total_points;

    private String permanent_deletion_at;

    private String createdAt;

    private String updatedAt;

    private String deletedAt;

}
