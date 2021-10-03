package com.pastamania.repository;

import com.pastamania.entity.Company;
import com.pastamania.entity.Receipt;
import com.pastamania.enums.SyncStatus;
import com.pastamania.report.DailySalesMixReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query("select r from Receipt r where r.company=?1 and r.createdAt= (SELECT max(r.createdAt) from Receipt r where r.company=?1)")
    List<Receipt> findReceiptWithMaxCreatedDateAndCompany(Company company);

    @Query("select r from Receipt r where r.company=?1 and r.updatedAt= (SELECT max(r.updatedAt) from Receipt r where r.company=?1)")
    List<Receipt> findReceiptWithMaxUpdatedDateAndCompany(Company company);

    @Query("select r from Receipt r where r.company=?1 and r.createdAt= (SELECT min(r.createdAt) from Receipt r where r.company=?1)")
    List<Receipt> findReceiptWithMinCreatedDateAndCompany(Company company);

    List<Receipt> findAllBySyncStatus(SyncStatus syncStatus);

    List<Receipt> findAllByStoreIdInAndSyncStatusInAndErrorCountLessThanEqual(List<String> storeIds, List<SyncStatus> statuses, Integer errorCount);

    @Modifying
    @Query("UPDATE Receipt r SET r.syncStatus = :syncStatus, r.errorCount = :errorCount "
            + "WHERE r.receiptNo IN (:receiptIds)")
    void updateReceiptSyncStatus(
            @Param("syncStatus") SyncStatus syncStatus,
            @Param("errorCount") Integer errorCount,
            @Param("receiptIds") List<Long> receiptIds);


    @Query(value = "SELECT rli.item_id as itemId , i.item_name as itemName ,rli.variant_name as variantName, c.id as categoryId,c.`name` as category,r.created_at as createdAt , rli.gross_total_money as grossTotalMoney,r.receipt_type as receiptType ,lild.name as discountName,lild.money_amount as discountAmount\n" +
            "FROM receipt_line_item rli\n" +
            "LEFT JOIN  item i ON rli.item_id = i.id\n" +
            "LEFT JOIN category c ON i.category_id=c.id\n" +
            "LEFT JOIN receipt r ON rli.receipt_id=r.receipt_no\n" +
            "LEFT JOIN line_itm_line_discount lild ON rli.receipt_line_item_no=lild.receipt_total_discount_no\n" +
            "WHERE r.created_at BETWEEN '2021-09-11T00:00:00.000Z' AND '2021-09-12T00:00:00.000Z'", nativeQuery = true)
    List<DailySalesMixReport> findDataForDailySaleMixReport(String from, String to, Integer companyID);


}
