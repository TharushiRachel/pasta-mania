package com.pastamania.controller;

/*
import com.sunshinelaundry.cleaner.component.RestApiClient;
import com.sunshinelaundry.cleaner.domain.Invoice;
import com.sunshinelaundry.cleaner.domain.Payment;
import com.sunshinelaundry.cleaner.dto.request.SalesItemRequest;
import com.sunshinelaundry.cleaner.dto.response.ShangrillaResponse;
import com.sunshinelaundry.cleaner.service.InvoiceService;
import com.sunshinelaundry.cleaner.util.DateFormatterConstant;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
*/

import com.pastamania.component.RestApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ShangrilaSyncController {

    @Autowired
    private RestApiClient restApiClient;

    @Value("${shangrilaSync.appCode}")
    private String appCode;

    @Value("${shangrilaSync.propertyCode}")
    private String propertyCode;

    @Value("${shangrilaSync.clientID}")
    private String clientID;

    @Value("${shangrilaSync.clientSecret}")
    private String clientSecret;

    @Value("${shangrilaSync.pOSInterfaceCode}")
    private String pOSInterfaceCode;

    @Value("${shangrilaSync.url}")
    private String url;

    /*private static final Long SHANGRILA_BRANCH_ID=19L;

    public String token() {

        log.info("Scheduled started => {}" ,LocalDateTime.now());

        log.info("Date => {}" , LocalDate.now().format(DateFormatterConstant.localDate));
        log.info("Scheduled started => {}" ,(LocalTime.now().format( DateFormatterConstant.localTime)));



        List<Invoice> invoices = invoiceService.findByBranch(SHANGRILA_BRANCH_ID);
        log.info("invoices {}",invoices);

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal sumPayment = BigDecimal.ZERO;
        if(invoices != null && invoices.size() > 0) {

            log.info("invoices size {}",invoices.size());

            //filter not null sync status invoices
            //List<Invoice> invoiceList  = invoices.stream().filter(invoice -> invoice.getSync() != null).collect(Collectors.toList());


            for (Invoice invoice : invoices) {
                log.info("gross total {}",invoice.getGrossTotal());
                sum = sum.add(invoice.getGrossTotal());

                Set<Payment> payments = invoice.getPayments();

                for (Payment payment : payments) {
                    sumPayment = sumPayment.add(payment.getAmount());
                }
            }
            log.info("sum grosstotal {}",sum);
            log.info("sum sumPayment {}",sumPayment);

            //invoices.stream().reduce(BigDecimal,BigDecimal :: sumGrossTotal)


        SalesItemRequest salesItemRequest = new SalesItemRequest();
        salesItemRequest.setAppCode(appCode);
        salesItemRequest.setPropertyCode(propertyCode);
        salesItemRequest.setClientID(clientID);
        salesItemRequest.setClientSecret(clientSecret);
        try {
            log.info("time stamp : {}",localDateTimeFormat());
            salesItemRequest.setBatchCode(localDateTimeFormat());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        salesItemRequest.setPOSInterfaceCode(pOSInterfaceCode);

        List<SalesItemRequest.PosSalesData> posSalesDatas = new ArrayList<>();

        SalesItemRequest.PosSalesData posSalesData = new SalesItemRequest.PosSalesData();

        posSalesData.setPropertyCode(propertyCode);
        posSalesData.setPOSInterfaceCode(pOSInterfaceCode);




        posSalesData.setReceiptDate(LocalDate.now().format(DateFormatterConstant.localDate));
        posSalesData.setReceiptTime(LocalTime.now().format( DateFormatterConstant.localTime));
        //posSalesData.setReceiptNo("R0005");

        //posSalesData.setNoOfItems("2");

        posSalesData.setSalesCurrency("LKR");

        posSalesData.setTotalSalesAmtB4Tax(sumPayment.toString());

        posSalesData.setTotalSalesAmtAfterTax(sumPayment.toString());

        posSalesData.setSalesTaxRate("0.00");

        posSalesData.setServiceChargeAmt("0.00");
        posSalesData.setPaymentAmt(sumPayment.toString());
        posSalesData.setPaymentCurrency("LKR");
        posSalesData.setPaymentMethod("Cash");//need to discuss
        posSalesData.setSalesType("SALES");


        List<SalesItemRequest.PosSalesData.ItemsData> itemsDatas = new ArrayList<>();

        posSalesData.setItems(itemsDatas);

        posSalesDatas.add(posSalesData);

        salesItemRequest.setPosSales(posSalesDatas);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(salesItemRequest);
            log.info("JSON = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder urlBuilder = new StringBuilder();

        urlBuilder.append(url);

        HttpEntity<SalesItemRequest> request = new HttpEntity<>(salesItemRequest);

        log.info("urlBuilder :" + urlBuilder.toString());

        ResponseEntity<ShangrillaResponse> responseEntity = restApiClient.getRestTemplateWithAuth().exchange(urlBuilder.toString(),
                HttpMethod.POST, request, ShangrillaResponse.class);

        log.info("udp response :{}", responseEntity.toString());

        HttpStatus httpStatus = responseEntity.getStatusCode();

        log.info("httpStatus.getReasonPhrase :{}", httpStatus.getReasonPhrase());

        if(httpStatus.getReasonPhrase().equalsIgnoreCase(HttpStatus.OK.getReasonPhrase())) {

            log.info("submission success :" + responseEntity.toString());
            //update sync status as suubmitted
            for (Invoice invoice : invoices) {
                invoice.setSync(Boolean.TRUE);
            }

            invoiceService.savaList(invoices);

        }

        return "response : "+responseEntity.toString();

        }

        return null;
    }

    private String localDateTimeFormat() throws ParseException {

        LocalDateTime now = LocalDateTime.now();

        log.info("now : " + now);
        log.info("day : " + String.valueOf(String.format ("%02d", now.getDayOfMonth())));
        log.info("month : " + now.getMonth());
        log.info("year : " + now.getYear());
        log.info("hour : " + now.getHour());
        log.info("mintue : " + now.getMinute());
        log.info("second : " + now.getSecond());

        //ddmmyyyyhhmmss
        String formatedBatchCode = String.valueOf(String.format ("%02d", now.getDayOfMonth()))+String.valueOf(now.getMonthValue())+String.valueOf(now.getYear()+String.valueOf(now.getHour())+String.valueOf(now.getMinute())+String.valueOf(now.getSecond()));


        log.info("formatedBatchCode : " + formatedBatchCode);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String formatDateTime2 = now.format(formatter);

        log.info("formatDateTime2 : " + formatDateTime2);

        LocalDateTime formatDateTime = LocalDateTime.parse(formatDateTime2, formatter);

        log.info("After 1 : " + formatDateTime);

        log.info("After 2 : " + formatDateTime.format(formatter2));


        String date = "20191106121238";

        return formatDateTime.format(formatter2);
    }
*/



}
