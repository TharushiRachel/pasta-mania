package com.pastamania.service.impl;

import com.pastamania.component.RestApiClient;
import com.pastamania.configuration.ConfigProperties;
import com.pastamania.dto.response.ItemResponse;
import com.pastamania.dto.response.ReceiptResponse;
import com.pastamania.entity.*;
import com.pastamania.enums.SyncStatus;
import com.pastamania.repository.*;
import com.pastamania.service.ReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Pasindu Lakmal
 */
@Service
@Transactional
@Slf4j
public class ReceiptServiceImpl implements ReceiptService {


    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    ItemVariantRepository itemVariantRepository;

    @Autowired
    VariantStoreRepository variantStoreRepository;

    @Autowired
    ReceiptTotalDiscountRepository receiptTotalDiscountRepository;

    @Autowired
    ReceiptTotalTaxRepository receiptTotalTaxRepository;

    @Autowired
    ReceiptPaymentRepository receiptPaymentRepository;

    @Autowired
    ReceiptLineItemRepository receiptLineItemRepository;

    @Autowired
    LineItemLineDiscountRepository lineItemLineDiscountRepository;

    @Autowired
    LineItemLineTaxRepository lineItemLineTaxRepository;

    @Autowired
    LineItemLineModifierRepository lineItemLineModifierRepository;


    @Autowired
    private RestApiClient restApiClient;

    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private ConfigProperties configProperties;


    @Override
    public void retrieveReceiptAndPersist(Date date, Company company) throws ParseException {


        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz); // get the time zone
        String nowAsISO = df.format(date);

        List<Receipt> lastUpdatedReceipt = receiptRepository.findReceiptWithMaxUpdatedDateAndCompany(company);
        List<Receipt> finalCreatedReceipt = receiptRepository.findReceiptWithMinCreatedDateAndCompany(company);


        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + company.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        var val = "";
        if (finalCreatedReceipt.isEmpty()) {
            val = nowAsISO;
        } else {
            String updatedAt = finalCreatedReceipt.get(0).getCreatedAt();
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date convertedDate = sourceFormat.parse(updatedAt);
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);
            c.add(Calendar.MILLISECOND, -1);
            val = sourceFormat.format(c.getTime());

        }
        ResponseEntity<ReceiptResponse> itemResponseResponseEntity = restApiClient.getRestTemplate().exchange(configProperties.getLoyvers().getBaseUrl() + "receipts?created_at_min=2021-09-01T00:00:00.962Z&created_at_max=" + val + "&limit=250", HttpMethod.GET, entity, ReceiptResponse.class);
        Company companyOp = companyRepository.findById(company.getId()).get();
        saveReceiptWithMappedValues(modelMapper, itemResponseResponseEntity, companyOp);

        if (itemResponseResponseEntity.getBody().getReceipts().isEmpty()) {
            String updatedAt = lastUpdatedReceipt.get(0).getUpdatedAt();
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date convertedDate = sourceFormat.parse(updatedAt);
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);
            c.add(Calendar.MILLISECOND, 1);
            String oneSecondAddedDate = sourceFormat.format(c.getTime());
            ResponseEntity<ItemResponse> itemResponseResponseEntityUpdated = restTemplate.exchange("https://api.loyverse.com/v1.0/receipts?updated_at_min=" + oneSecondAddedDate + "&updated_at_max=" + nowAsISO + "", HttpMethod.GET, entity, ItemResponse.class);
            if (itemResponseResponseEntityUpdated.getBody().getItems() != null) {
                saveReceiptWithMappedValues(modelMapper, itemResponseResponseEntity, companyOp);
            }
        }

    }

    private void saveReceiptWithMappedValues(ModelMapper modelMapper, ResponseEntity<ReceiptResponse> receiptResponseResponseEntity, Company companyOp) {
        receiptResponseResponseEntity.getBody().getReceipts().forEach(receipt -> {
            Receipt receipt1 = modelMapper.map(receipt, Receipt.class);
            receipt1.setCompany(companyOp);
            if (receipt1.getTotalDiscounts() != null) receipt1.getTotalDiscounts().clear();
            if (receipt1.getTotalTaxes() != null) receipt1.getTotalTaxes().clear();
            if (receipt1.getReceiptLineItems() != null) receipt1.getReceiptLineItems().clear();
            if (receipt1.getPayments() != null) receipt1.getPayments().clear();
            Receipt saveReceipt = receiptRepository.save(receipt1);
            if (receipt.getTotal_discounts() != null) {
                receipt.getTotal_discounts().forEach(totalDiscount -> {
                    ReceiptTotalDiscount receiptTotalDiscount = modelMapper.map(totalDiscount, ReceiptTotalDiscount.class);
                    receiptTotalDiscount.setReceipt(saveReceipt);
                    receiptTotalDiscountRepository.save(receiptTotalDiscount);
                });
            }
            if (receipt.getTotal_taxes() != null) {
                receipt.getTotal_taxes().forEach(totalTax -> {
                    ReceiptTotalTax receiptTotalTax = modelMapper.map(totalTax, ReceiptTotalTax.class);
                    receiptTotalTax.setReceipt(saveReceipt);
                    receiptTotalTaxRepository.save(receiptTotalTax);
                });
            }

            if (receipt.getPayments() != null) {
                receipt.getPayments().forEach(payment -> {
                    ReceiptPayment receiptPayment = modelMapper.map(payment, ReceiptPayment.class);
                    receiptPayment.setReceipt(saveReceipt);
                    receiptPaymentRepository.save(receiptPayment);
                });
            }

            if (receipt.getLine_items() != null) {

                receipt.getLine_items().forEach(lineItem -> {

                    ReceiptLineItem receiptLineItem = modelMapper.map(lineItem, ReceiptLineItem.class);
                    receiptLineItem.setReceipt(saveReceipt);
                    if (receiptLineItem.getLineDiscounts() != null) receiptLineItem.getLineDiscounts().clear();
                    if (receiptLineItem.getLineTaxes() != null) receiptLineItem.getLineTaxes().clear();
                    if (receiptLineItem.getLineModifiers() != null) receiptLineItem.getLineModifiers().clear();
                    ReceiptLineItem saveReceiptLineItem = receiptLineItemRepository.save(receiptLineItem);

                    if (lineItem.getLineTaxes() != null) {
                        lineItem.getLineTaxes().forEach(lineTax -> {
                            LineItemLineTax lineItemLineTax = modelMapper.map(lineTax, LineItemLineTax.class);
                            lineItemLineTax.setReceiptLineItem(saveReceiptLineItem);
                            lineItemLineTaxRepository.save(lineItemLineTax);
                        });
                    }

                    if (lineItem.getLineDiscounts() != null) {
                        lineItem.getLineDiscounts().forEach(discount -> {
                            LineItemLineDiscount lineItemLineDiscount = modelMapper.map(discount, LineItemLineDiscount.class);
                            lineItemLineDiscount.setReceiptLineItem(saveReceiptLineItem);
                            lineItemLineDiscountRepository.save(lineItemLineDiscount);
                        });
                    }

                    if (lineItem.getLineModifiers() != null) {
                        lineItem.getLineModifiers().forEach(modifier -> {
                            LineItemLineModifier lineItemLineModifier = modelMapper.map(modifier, LineItemLineModifier.class);
                            lineItemLineModifier.setReceiptLineItem(saveReceiptLineItem);
                            lineItemLineModifierRepository.save(lineItemLineModifier);
                        });
                    }


                });


            }
        });

    }

    @Transactional(readOnly = true)
    @Override
    public List<Receipt> searchBySyncStatus(SyncStatus syncStatus) {
        List<Receipt> receipts = receiptRepository.findAllBySyncStatus(syncStatus);
        for (Receipt receipt : receipts) {
            receipt.getPayments().size();
            receipt.getReceiptLineItems().size();
            receipt.getTotalTaxes().size();
        }

        return receipts;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Receipt> getPendingSyncReceipts() {
        List<Receipt> pendingReceipts = receiptRepository.findAllBySyncStatusInAndErrorCountLessThanEqual(
                Arrays.asList(SyncStatus.PENDING, SyncStatus.FAILED), 3);
        for (Receipt receipt : pendingReceipts) {
            receipt.getPayments().size();
            receipt.getReceiptLineItems().size();
            receipt.getTotalTaxes().size();
        }

        return pendingReceipts;
    }

    @Transactional
    @Override
    public void updateSyncStatus(SyncStatus syncStatus, Integer errorCount, List<Receipt> receipts) {
        receiptRepository.updateReceiptSyncStatus(syncStatus, errorCount, receipts.stream().map(Receipt::getReceiptNo).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public Receipt update(Receipt receipt) {
        Optional<Receipt> currentReceipt = receiptRepository.findById(receipt.getReceiptNo());
        if (currentReceipt.isEmpty()) {
            return null;
        }
        Receipt persistedReceipt = currentReceipt.get();
        if (receipt.getSyncStatus() != null) {
            persistedReceipt.setSyncStatus(receipt.getSyncStatus());
        }
        if (receipt.getErrorCount() != null) {
            persistedReceipt.setErrorCount(receipt.getErrorCount());
        }

        receiptRepository.save(persistedReceipt);
        return persistedReceipt;
    }
}

