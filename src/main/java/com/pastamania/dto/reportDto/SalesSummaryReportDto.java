package com.pastamania.dto.reportDto;

import lombok.Data;

/**
 * @author Pasindu Lakmal
 */
@Data
public class SalesSummaryReportDto {

    String category;
    Integer quantity;
    Double grossSale;
    Double refunds;
    Double totalDiscount;
    Double grossTotal;
    Double tax;
    Double serviceCharge;
    Double netTotal;

}
