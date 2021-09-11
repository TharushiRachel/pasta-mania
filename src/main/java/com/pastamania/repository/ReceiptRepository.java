package com.pastamania.repository;

import com.pastamania.entity.Company;
import com.pastamania.entity.Item;
import com.pastamania.entity.Receipt;
import com.pastamania.enums.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

    @Query("select r from Receipt r where r.company=?1 and r.createdAt= (SELECT max(r.createdAt) from Receipt r where r.company=?1)")
    List<Receipt> findReceiptWithMaxCreatedDateAndCompany(Company company);

    @Query("select r from Receipt r where r.company=?1 and r.updatedAt= (SELECT max(r.updatedAt) from Receipt r where r.company=?1)")
    List<Receipt> findReceiptWithMaxUpdatedDateAndCompany(Company company);

    List<Receipt> findAllBySyncStatus(SyncStatus syncStatus);

    List<Receipt> findAllBySyncStatusInAndErrorCountLessThanEqual(List<SyncStatus> statuses, Integer errorCount);

    @Modifying
    @Query("UPDATE Receipt r SET r.syncStatus = :syncStatus, r.errorCount = :errorCount "
            + "WHERE r.receiptNo IN (:receiptIds)")
    void updateReceiptSyncStatus(
            @Param("syncStatus") SyncStatus syncStatus,
            @Param("errorCount") Integer errorCount,
            @Param("receiptIds") List<Long> receiptIds);
}
