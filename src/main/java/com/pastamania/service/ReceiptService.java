package com.pastamania.service;

import com.pastamania.entity.Company;
import com.pastamania.entity.Receipt;
import com.pastamania.enums.SyncStatus;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptService {

    void retrieveReceiptAndPersist(Date date, Company company) throws ParseException;

    List<Receipt> searchBySyncStatus(SyncStatus syncStatus);

    List<Receipt> getPendingSyncReceipts();

    void updateSyncStatus(SyncStatus syncStatus, Integer errorCount, List<Receipt> receipts);

    Receipt update(Receipt receipt);
}
