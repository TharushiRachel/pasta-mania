package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
@Table(name = "receipt_payment")
public class ReceiptPayment extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptPaymentNo;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    private String paymentTypeId;

    private String name;

    private String type;

    private String moneyAmount;

    private String paidAt;

    private String paymentDetails;

}
