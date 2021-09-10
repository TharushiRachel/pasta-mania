package com.pastamania.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionSalesData {

    private String PropertyCode;

    private String POSInterfaceCode;

    private String ReceiptDate;

    private String ReceiptTime;

    private String ReceiptNo;

    private int NoOfItems;

    private String SalesCurrency;

    private BigDecimal TotalSalesAmtB4Tax;

    private BigDecimal TotalSalesAmtAfterTax;

    private BigDecimal SalesTaxRate;

    private BigDecimal ServiceChargeAmt;

    private BigDecimal PaymentAmt;

    private String PaymentCurrency;

    private String PaymentMethod;

    private String SalesType;

    private BigDecimal SalesDiscountAmt;
}
