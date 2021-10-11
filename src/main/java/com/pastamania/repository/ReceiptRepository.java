package com.pastamania.repository;

import com.pastamania.entity.Company;
import com.pastamania.entity.Receipt;
import com.pastamania.enums.SyncStatus;
import com.pastamania.report.DailySalesMixReport;
import com.pastamania.report.HourlySaleReport;
import com.pastamania.report.SaleSummaryReport;
import com.pastamania.report.SettlementModeWiseReport;
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



    @Query(value = "SELECT\n" +
            "\tcount( receipt_no ) AS quantity,\n" +
            "\tdining_option AS category,\n" +
            "\tSUM( total_money ) AS grossSale,\n" +
            "\tSUM( total_discount ) AS totalDiscount,\n" +
            "\tSUM( total_money )- SUM( total_discount ) AS grossTotal,\n" +
            "\tSUM( total_tax ) AS tax,\n" +
            "\tSUM( surcharge ) AS serviceCharge,\n" +
            "\t(\n" +
            "\t\tSUM( total_money )- SUM( total_discount ))-(\n" +
            "\tSUM( total_tax )+ SUM( surcharge )) AS netTotal \n" +
            "FROM\n" +
            "\treceipt r \n" +
            "WHERE\n" +
            "\tr.created_at BETWEEN '2021-09-11T00:00:00.000Z' \n" +
            "\tAND '2021-09-12T00:00:00.000Z' \n" +
            "GROUP BY\n" +
            "\tr.dining_option", nativeQuery = true)
    List<SaleSummaryReport> findDataForSaleSummaryReportDiningType(String from, String to, Integer companyID);


    @Query(value = "SELECT\n" +
            "\tc.`name`as category,\n" +
            "\tCOUNT( rli.item_id ) as quantity,\n" +
            "\tROUND( SUM( rli.gross_total_money ), 2 ) as grossTotal,\n" +
            "\tSUM( r.total_discount ) as totalDiscount,\n" +
            "\tSUM(r.total_tax) as tax,\n" +
            "\tSUM(r.surcharge) as surviceCharge,\n" +
            "\tROUND( SUM( rli.gross_total_money ), 2 ) - (SUM( r.total_discount )+SUM(r.surcharge)) as netTotal\n" +
            "FROM\n" +
            "\treceipt_line_item rli\n" +
            "\tLEFT JOIN item item ON rli.item_id = item.id\n" +
            "\tLEFT JOIN category c ON item.category_id = c.id\n" +
            "\tLEFT JOIN receipt r ON rli.receipt_id = r.receipt_no\n" +
            "\tLEFT JOIN receipt_total_tax rtt ON r.receipt_no=rtt.receipt_id\n" +
            "GROUP BY\n" +
            "\tc.`name`", nativeQuery = true)
    List<SaleSummaryReport> findDataForSaleSummaryReportCategory(String from, String to, Integer companyID);


    @Query(value = "SELECT\n" +
            "\trp.`name`\tas category,\n" +
            "\tCOUNT( rli.item_id ) as quantity,\n" +
            "\tROUND( SUM( rli.gross_total_money ), 2 ) as grossTotal,\n" +
            "\tSUM( r.total_discount ) as totalDiscount,\n" +
            "\tSUM(r.total_tax) as tax,\n" +
            "\tSUM(r.surcharge) as surviceCharge,\n" +
            "\tROUND( SUM( rli.gross_total_money ), 2 ) - (SUM( r.total_discount )+SUM(r.surcharge)) as netTotal\n" +
            "FROM\n" +
            "\treceipt_line_item rli\n" +
            "\tLEFT JOIN item item ON rli.item_id = item.id\n" +
            "\tLEFT JOIN category c ON item.category_id = c.id\n" +
            "\tLEFT JOIN receipt r ON rli.receipt_id = r.receipt_no\n" +
            "\tLEFT JOIN receipt_total_tax rtt ON r.receipt_no=rtt.receipt_id\n" +
            "\tLEFT JOIN receipt_payment rp ON r.receipt_no=rp.receipt_id\n" +
            "GROUP BY rp.`name`\t", nativeQuery = true)
    List<SaleSummaryReport> findDataForSaleSummaryReportPaymentMode(String from, String to, Integer companyID);



    @Query(value = "SELECT count( DISTINCT r.receipt_no) as receiptNo ,r.created_at as createdAt , sum( DISTINCT r.total_money) as totalMoney , count(rli.id) as lineItemId\n" +
            "FROM receipt r\n" +
            "LEFT JOIN receipt_line_item  rli ON r.receipt_no=rli.receipt_id\n" +
            "WHERE r.created_at BETWEEN '2021-09-11T00:03:00.000Z' AND '2021-09-12T00:02:59.000Z'\n" +
            "GROUP BY  r.created_at\n" +
            "ORDER BY r.created_at", nativeQuery = true)
    List<HourlySaleReport> findDataForHourlySaleReport(String from, String to, Integer companyID);



    @Query(value = "SELECT rp.name as settlement , r._order as orderNo  , r.dining_option  as orderType , r.created_at as  createdAt , e.`name` as userName, r.total_money as sale\n" +
            "FROM receipt r\n" +
            "LEFT JOIN receipt_payment rp ON rp.receipt_id=r.receipt_number\n" +
            "LEFT JOIN employee e ON e.id = r.employee_id\n" +
            "ORDER BY rp.`name`", nativeQuery = true)
    List<SettlementModeWiseReport>findDataForSettlementModeViewReport(String from, String to, Integer companyID);






}
