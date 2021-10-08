package com.pastamania.report;

/**
 * @author Pasindu Lakmal
 */
public interface SaleSummaryReport {
    String getCategory();

    Integer getQuantity();

    Double getGrossSale();

    Double getRefunds();

    Double getTotalDiscount();

    Double getGrossTotal();

    Double getTax();

    Double getServiceCharge();

    Double getNetTotal();


}
