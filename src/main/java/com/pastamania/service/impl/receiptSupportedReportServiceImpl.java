package com.pastamania.service.impl;

import com.pastamania.entity.Receipt;
import com.pastamania.repository.ReceiptRepository;
import com.pastamania.service.receiptSupportedReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Service
@Transactional
@Slf4j
public class receiptSupportedReportServiceImpl implements receiptSupportedReportService {

    @Autowired
    ReceiptRepository receiptRepository;


    @Override
    public String parseThymeleafTemplateForDailySaleMixReport() {

        List<Receipt> receiptList = receiptRepository.findAll();


        return null;
    }
}
