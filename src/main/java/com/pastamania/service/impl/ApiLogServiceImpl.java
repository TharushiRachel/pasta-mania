package com.pastamania.service.impl;

import com.pastamania.entity.Receipt;
import com.pastamania.entity.ApiLog;
import com.pastamania.repository.SyncApiLogRepository;
import com.pastamania.service.ApiLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApiLogServiceImpl implements ApiLogService {

    @Autowired
    private SyncApiLogRepository repository;

    @Override
    public ApiLog recordLog(List<Receipt> receipt, String apiName, LocalDateTime callDate, LocalDateTime responseDate, String result,
                            String errorDetails, String requestURL, String requestBody, String responseContent) {
        ApiLog apiLog = new ApiLog(receipt, apiName, callDate, responseDate, result, errorDetails, requestURL,
                requestBody, responseContent);

        return repository.save(apiLog);
    }
}
