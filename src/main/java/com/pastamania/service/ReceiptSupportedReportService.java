package com.pastamania.service;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptSupportedReportService {

    String parseThymeleafTemplateForDailySaleReport();

    String parseThymeleafTemplateForDailySaleMixReport();
}
