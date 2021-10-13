package com.pastamania.report;

/**
 * @author Pasindu Lakmal
 */
public interface VoidRefundDetailReport {
    String getType();

    String getReceiptType();

    String getReason();

    String getOrderNo();

    String getCreatedAt();

    String getUserName();

    Double getSale();
}
