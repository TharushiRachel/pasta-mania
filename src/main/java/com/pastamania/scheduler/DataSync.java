package com.pastamania.scheduler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pastamania.dto.request.SalesDataSendRequest;
import com.pastamania.dto.request.TransactionSalesData;
import com.pastamania.dto.response.SalesDataSendResponse;
import com.pastamania.entity.Receipt;
import com.pastamania.entity.ReceiptPayment;
import com.pastamania.entity.ReceiptTotalTax;
import com.pastamania.enums.SyncStatus;
import com.pastamania.service.ReceiptService;
import com.pastamania.service.ApiLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataSync {

    private static final String SALES_CURRENCY = "LKR";
    private static final String PAYMENT_CURRENCY = "LKR";
    private static final String SALES_TYPE_SALES = "Sales";
    private static final String SALES_TYPE_REFUND = "Refund";
    private static final String SALES_TYPE_RETURN = "Return";
    private static final String SALES_TYPE_EXCHANGE = "Exchange";
    private static final String SALES_TYPE_VOID = "Void";
    private static final String BATCH_CODE_FORMAT = "yyyyMMddHHssmm";
    private static final String RECEIPT_DATE_FORMAT = "dd/MM/uuuu";

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

    @Autowired
    private WebClient webClient;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private ApiLogService apiLogService;

    @Scheduled(fixedRate = 360000)
    public void syncPosTransactionSalesData() {

        log.info("SyncSalesData started => {}" , LocalDateTime.now());

        List<Receipt> pendingReceipts = receiptService.getPendingSyncReceipts();
        if (!pendingReceipts.isEmpty()) {

            LocalDateTime callingDateTime = LocalDateTime.now();
            SalesDataSendRequest request = createRequest(getSalesData(pendingReceipts), callingDateTime);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

            webClient.post()
                    .uri(url)
                    .body(Mono.just(request), SalesDataSendRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError, response -> {
                        receiptService.updateSyncStatus(SyncStatus.PENDING, 0, pendingReceipts);
                        return null;
                    })
                    .onStatus(HttpStatus::is5xxServerError, response -> {
                        receiptService.updateSyncStatus(SyncStatus.PENDING, 0, pendingReceipts);
                        return null;
                    })
                    .bodyToMono(SalesDataSendResponse.class)
                    .onErrorMap(throwable -> {
                        receiptService.updateSyncStatus(SyncStatus.PENDING, 0, pendingReceipts);
                        return null;
                    })
                    .map(response -> {
                        if (response.getReturnStatus().equalsIgnoreCase("Success")) { //TODO: Need to have a Enum
                            receiptService.updateSyncStatus(SyncStatus.COMPLETED, 0, pendingReceipts);
                        } else {
                            pendingReceipts.forEach(receipt -> {
                                receipt.setSyncStatus(SyncStatus.FAILED);
                                receipt.setErrorCount(receipt.getErrorCount() + 1);
                                receiptService.update(receipt);
                            });
                        }

                        try {
                            apiLogService.recordLog(pendingReceipts, "Sync Sales Data", callingDateTime,
                                    LocalDateTime.now(), response.getReturnStatus(), response.getErrorDetails(), url,
                                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request),
                                    objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
                        } catch (JsonProcessingException e) {
                            log.error("API log creation failed : {}", e.getMessage());
                            e.printStackTrace();
                        }

                        return "SyncSalesData response : [" + response.getReturnStatus() + "] :: " + response;
                    })
                    .subscribe(log::info);
        } else {
            log.info("No sales data to be sync up.");
        }

        log.info("SyncSalesData end => {}" , LocalDateTime.now());
    }

    private SalesDataSendRequest createRequest(List<TransactionSalesData> salesData, LocalDateTime sendingDateTime) {
        SalesDataSendRequest request = new SalesDataSendRequest();
        request.setAppCode(appCode);
        request.setPropertyCode(propertyCode);
        request.setClientID(clientID);
        request.setClientSecret(clientSecret);
        request.setPOSInterfaceCode(pOSInterfaceCode);
        request.setBatchCode(sendingDateTime.format(DateTimeFormatter.ofPattern(BATCH_CODE_FORMAT)));
        request.setPosSales(salesData);

        return request;
    }

    private List<TransactionSalesData> getSalesData(List<Receipt> receipts) {
        List<TransactionSalesData> salesDataList = new ArrayList<>();
        for (Receipt receipt : receipts) {
            TransactionSalesData salesData = new TransactionSalesData();
            salesData.setPropertyCode(propertyCode);
            salesData.setPOSInterfaceCode(pOSInterfaceCode);
            if (receipt.getReceiptDate() != null && !receipt.getReceiptDate().isEmpty()) {
                Instant instant = Instant.parse(receipt.getReceiptDate());
                LocalDateTime receiptDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
                salesData.setReceiptDate(receiptDateTime.toLocalDate().format(DateTimeFormatter.ofPattern(RECEIPT_DATE_FORMAT)));
                salesData.setReceiptTime(receiptDateTime.toLocalTime().toString());
            }
            salesData.setReceiptNo(receipt.getReceiptNumber());
            if (receipt.getReceiptLineItems() != null) {
                salesData.setNoOfItems(receipt.getReceiptLineItems().size());
            } else {
                salesData.setNoOfItems(0);
            }
            salesData.setSalesCurrency(SALES_CURRENCY); //TODO: Where can we get it?
            salesData.setTotalSalesAmtB4Tax(receipt.getTotalMoney().subtract(receipt.getTotalTax())); //TODO: Is this correct?
            salesData.setTotalSalesAmtAfterTax(receipt.getTotalMoney()); //TODO: Is this correct?
            if (receipt.getTotalTaxes() != null && !receipt.getTotalTaxes().isEmpty())  {
                salesData.setSalesTaxRate(receipt.getTotalTaxes().stream()
                        .map(ReceiptTotalTax::getRate)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)); //TODO: Is this correct?
            } else {
                salesData.setSalesTaxRate(BigDecimal.ZERO);
            }
            salesData.setServiceChargeAmt(BigDecimal.ZERO); //TODO: How can we get it?
            if (receipt.getPayments() != null && !receipt.getPayments().isEmpty()) {
                salesData.setPaymentAmt(receipt.getPayments().stream()
                        .map(ReceiptPayment::getMoneyAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
                salesData.setPaymentMethod(receipt.getPayments().stream().map(ReceiptPayment::getName).collect(Collectors.joining(",")));
            } else {
                salesData.setPaymentAmt(BigDecimal.ZERO);
                salesData.setPaymentMethod("Cash"); //TODO: Can we have this situation? i.e empty payments?
            }
            salesData.setPaymentCurrency(PAYMENT_CURRENCY); //TODO:
            salesData.setSalesType(receipt.getReceiptType().equals("SALE") ? SALES_TYPE_SALES : SALES_TYPE_SALES); //TODO: Other sales type from loyverse?
            salesData.setSalesDiscountAmt(receipt.getTotalDiscount());

            salesDataList.add(salesData);
        }

        return salesDataList;
    }

    private SalesDataSendRequest createMockRequest() {
        SalesDataSendRequest request = new SalesDataSendRequest();
        request.setAppCode(appCode);
        request.setPropertyCode(propertyCode);
        request.setClientID(clientID);
        request.setClientSecret(clientSecret);
        request.setPOSInterfaceCode(pOSInterfaceCode);
        request.setBatchCode("20210906113028");
        List<TransactionSalesData> posSales = new ArrayList<>();
        posSales.add(createMockData());
        request.setPosSales(posSales);

        return request;
    }

    private TransactionSalesData createMockData() {
        TransactionSalesData salesData = new TransactionSalesData();
        salesData.setPropertyCode(propertyCode);
        salesData.setPOSInterfaceCode(pOSInterfaceCode);
        salesData.setReceiptDate("06/09/2021");
        salesData.setReceiptTime("11:00:00");
        salesData.setReceiptNo("R003002");
        salesData.setNoOfItems(1);
        salesData.setSalesCurrency("LKR");
        salesData.setTotalSalesAmtB4Tax(new BigDecimal(1000.00));
        salesData.setTotalSalesAmtAfterTax(new BigDecimal(1050.00));
        salesData.setSalesTaxRate(new BigDecimal(5.00));
        salesData.setServiceChargeAmt(new BigDecimal(0.00));
        salesData.setPaymentAmt(new BigDecimal(1050.00));
        salesData.setPaymentCurrency("LKR");
        salesData.setPaymentMethod("Cash");
        salesData.setSalesType("Sales");
        salesData.setSalesDiscountAmt(new BigDecimal(0.00));

        return salesData;
    }
}