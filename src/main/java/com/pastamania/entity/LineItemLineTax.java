package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
@Table(name = "line_item_line_tax")
public class LineItemLineTax extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiptTotalTaxNo;

    @ManyToOne
    @JoinColumn(name = "receipt_line_item_id", nullable = false)
    private ReceiptLineItem receiptLineItem;

    private String id;

    private String type;

    private String name;

    private BigDecimal rate;

    private String moneyAmount;

}
