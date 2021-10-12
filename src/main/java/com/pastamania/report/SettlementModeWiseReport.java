package com.pastamania.report;

/**
 * @author Pasindu Lakmal
 */
public interface SettlementModeWiseReport {
    String getSettlement();

    String getOrderNo();

    String getOrderType();

    String getCreatedAt();

    String getUserName();

    Double getSale();
}
