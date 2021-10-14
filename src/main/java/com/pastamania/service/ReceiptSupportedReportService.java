package com.pastamania.service;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptSupportedReportService {

    String parseThymeleafTemplateForDailySaleReport();

    String parseThymeleafTemplateForDailySaleMixReport();

    String parseThymeleafTemplateForSalesSummaryReport();

    String parseThymeleafTemplateForHourlySaleReport();

    String parseThymeleafTemplateForSettlementModeWiseReport();

    String parseThymeleafTemplateForVoidRefundDetailReport();
}
