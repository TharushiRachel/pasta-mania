package com.pastamania.service;

import com.pastamania.entity.Receipt;
import com.pastamania.entity.ApiLog;

import java.time.LocalDateTime;
import java.util.List;

public interface ApiLogService {

    ApiLog recordLog(List<Receipt> receipts, String apiName, LocalDateTime callDate, LocalDateTime responseDate, String result,
                     String errorDetails, String requestURL, String requestBody, String responseContent);
}
