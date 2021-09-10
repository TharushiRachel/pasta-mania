package com.pastamania.entity;

import com.pastamania.enums.SyncStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
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

    private BigDecimal totalMoney;

    private BigDecimal totalTax;

    private BigDecimal pointsEarned;

    private BigDecimal pointsDeducted;

    private BigDecimal pointBalance;

    private String customerId;

    private BigDecimal totalDiscount;

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

    @Column(columnDefinition = "varchar(50) not null default 'PENDING'")
    private SyncStatus syncStatus = SyncStatus.PENDING;

    @Column(columnDefinition = "int not null default 0")
    private Integer errorCount = 0;

    @ManyToOne
    @JoinColumn(name = "api_log_id")
    private ApiLog apiLog;

}
