package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
@Table(name = "receipt_total_tax")
public class ReceiptTotalTax extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptTotalTaxNo;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    private String id;

    private String type;

    private String name;

    private Boolean rate;

    private String moneyAmount;

}
