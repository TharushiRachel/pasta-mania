package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
public class Receipt extends CreateModifyBaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptNo;

    private String receiptNumber;

    private String note;

    private String receiptType;

    private String refundFor;

    private String _order;

    private String createdAt;

    private String receiptDate;

    private String updatedAt;

    private String cancelledAt;

    private String source;

    private Double totalMoney;

    private Double totalTax;

    private Double pointsEarned;

    private Double pointsDeducted;

    private Double pointBalance;

    private String customerId;

    private Double totalDiscount;

    private String employeeId;

    private String storeId;

    private String posDeviceId;

    private String diningOption;

    private Integer tip;

    private Integer surcharge;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ReceiptTotalDiscount> totalDiscounts;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ReceiptTotalTax> totalTaxes;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ReceiptLineItem> receiptLineItems;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ReceiptPayment> payments;

}
