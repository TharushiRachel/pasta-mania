package com.pastamania.report;

/**
 * @author Pasindu Lakmal
 */
public interface HourlySaleReport {

    String getCreatedAt();

    Integer getReceiptNo();

    Double getTotalMoney();

    String getLineItemId();

}
