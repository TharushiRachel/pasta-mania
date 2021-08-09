package com.pastamania.entity;//package com.pastamania.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Pasindu Lakmal
 */
@Entity
@Data
@Table(name="line_item_line_modifier")
public class LineItemLineModifier extends CreateModifyBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lineItemLineModifierNo;

    @ManyToOne
    @JoinColumn(name = "receipt_line_item_id", nullable = false)
    private ReceiptLineItem receiptLineItem;

    private String id;

    private String modifierOptionId;

    private String name;

    private String options;

    private String price;

    private Double moneyAmount;
}
