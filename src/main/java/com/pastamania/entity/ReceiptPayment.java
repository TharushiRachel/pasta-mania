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
public class ReceiptPayment extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptPaymentNo;

    private String paymentTypeId;

    private String name;

    private String type;

    private String moneyAmount;

    private String paidAt;

    private String paymentDetails;

}
