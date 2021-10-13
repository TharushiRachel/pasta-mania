package com.pastamania.scheduler;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.*;
import com.lowagie.text.DocumentException;
import com.pastamania.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
@Slf4j
@Component
@EnableScheduling
public class Scheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    CustomerService customerService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StoreService storeService;

    @Autowired
    DiscountService discountService;

    @Autowired
    ItemService itemService;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    HTMLService htmlService;

    @Autowired
    ReceiptSupportedReportService receiptSupportedReportService;

    @Autowired
    EmployeeService employeeService;


    public class Rotate extends PdfPageEventHelper {

        protected PdfNumber orientation = PdfPage.PORTRAIT;

        public void setOrientation(PdfNumber orientation) {
            this.orientation = orientation;
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            writer.addPageDictEntry(PdfName.ROTATE, orientation);
        }
    }

//    @Scheduled(cron = "*/5 * * * * *")
//    public void reportCurrentTime() throws IOException, DocumentException, com.itextpdf.text.DocumentException {
//        log.info("Current time = " + dateFormat.format(new Date()));
//
//        if (companyService.findAll().isEmpty()) {
//            companyService.createInitialCompanies();
//        }
//        companyService.findAll().stream().forEach(company -> {
//            try {
//                customerService.retrieveCustomerAndPersist(new Date(), company);
//                categoryService.retrieveCategoryAndPersist(new Date(), company);
//                itemService.retrieveItemAndPersist(new Date(), company);
//                receiptService.retrieveReceiptAndPersist(new Date(), company);
//                employeeService.retrieveEmployeeAndPersist(new Date(), company);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//        });
//        //storeService.initialStorePersist();
//        //discountService.initialStorePersist();
//
//    }

    @Scheduled(cron = "*/5 * * * * *")
    public void reportCurrentTime() throws IOException, DocumentException, com.itextpdf.text.DocumentException {
        log.info("Current time = " + dateFormat.format(new Date()));

        String template =  receiptSupportedReportService.parseThymeleafTemplateForSettlementModeWiseReport();
        ByteArrayOutputStream byteArrayOutputStream = htmlService.generatePdfOutputStreamFromHtml(template);
        OutputStream out = new FileOutputStream("settlement-mode-wise-report.pdf");
//        Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(document, out);
//        document.newPage();
//        Rotate event = new Rotate();
//        writer.setPageEvent(event);
//        document.open();
//        event.setOrientation(PdfPage.LANDSCAPE);
//        document.close();
        out.write(byteArrayOutputStream.toByteArray());
        out.close();

    }

}