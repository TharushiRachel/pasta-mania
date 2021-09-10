package com.pastamania.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SalesDataSendRequest {

    private String AppCode;

    private String PropertyCode;

    private String ClientID;

    private String ClientSecret;

    private String POSInterfaceCode;

    private String BatchCode;

    private List<TransactionSalesData> PosSales;
}
