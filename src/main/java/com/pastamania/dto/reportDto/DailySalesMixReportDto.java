package com.pastamania.dto.reportDto;

import lombok.Data;

/**
 * @author Pasindu Lakmal
 */
@Data
public class DailySalesMixReportDto {

    String ItemId;
    String ItemName;
    String VariantName;
    String CategoryId;
    String Category;
    String CreatedAt;
    Double GrossTotalMoney;
    String ReceiptType;
    String DiscountName;
    Double DiscountAmount;
}
