package com.pastamania.dto.response;

import lombok.Data;

@Data
public class SalesDataSendResponse {

    private String batchCode;

    private String returnStatus;

    private Integer recordsReceived;

    private Integer recordsImported;

    private String errorDetails;

    private String defectiveRowNos;

    @Override
    public String toString() {
        return "SalesDataSendResponse{" +
                "batchCode='" + batchCode + '\'' +
                ", returnStatus='" + returnStatus + '\'' +
                ", recordsReceived=" + recordsReceived +
                ", recordsImported=" + recordsImported +
                ", errorDetails='" + errorDetails + '\'' +
                ", defectiveRowNos='" + defectiveRowNos + '\'' +
                '}';
    }
}
