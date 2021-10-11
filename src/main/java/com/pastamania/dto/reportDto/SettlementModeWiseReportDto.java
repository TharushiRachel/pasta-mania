package com.pastamania.dto.reportDto;

import lombok.Data;

/**
 * @author Pasindu Lakmal
 */
@Data
public class SettlementModeWiseReportDto {


    String settlement;

    String orderNo;

    String orderType;

    String createdAt;

    String userName;

    Double sale;


}
