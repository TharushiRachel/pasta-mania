package com.pastamania.service.impl;

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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    @Override
    public void initialPersist() {

        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "d22e68278c144eb8b22c50a2623bccc9");
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        //newly created
        ResponseEntity<ReceiptResponse> createdResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/receipts", HttpMethod.GET, entity, ReceiptResponse.class);

        createdResponse.getBody().getReceipts().forEach(receipt -> {
            Receipt receipt1 = modelMapper.map(receipt, Receipt.class);
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


        System.out.println("=============================");

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

