package com.pastamania.controller;

import com.pastamania.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Pasindu Lakmal
 */
@Slf4j
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;


    @GetMapping("${app.endpoint.customerPDFView}")
    public void getPendingLCReportPDF(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String dateTime = LocalDateTime.now().format(formatter);
        String contentDisposition = "attachment; filename=pending-lc-report-" + dateTime + ".pdf";
        response.setHeader("Content-Disposition", contentDisposition);
        ByteArrayInputStream stream = customerService.generateCustomerReportPDF();
        try {
            IOUtils.copy(stream, response.getOutputStream());
        } catch (IOException e) {
            log.error("Error generating pending lc report pdf ", e);
        }
    }

}
