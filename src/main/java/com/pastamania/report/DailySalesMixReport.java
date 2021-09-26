package com.pastamania.report;

import org.springframework.data.jpa.repository.Query;

/**
 * @author Pasindu Lakmal
 */
public interface DailySalesMixReport {
     String getItemId();
     String getItemName();
     String getVariantName();
     String getCategoryId();
     String getCategory();
     String getCreatedAt();
     Double getGrossTotalMoney();
     String getReceiptType();
     String getDiscountName();
     Double getDiscountAmount();
}
