package com.pastamania.dto.reportDto;

import lombok.Data;

/**
 * @author Pasindu Lakmal
 */
@Data
public class HourlySaleReportDataDto {

    Integer transactions;
    Integer items;
    Double avgSale;
    Double sale;
    Double percentageSale;


}
