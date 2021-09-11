package com.pastamania.scheduler;

import com.pastamania.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(cron = "*/5 * * * * *")
    public void reportCurrentTime() {
        log.info("Current time = " + dateFormat.format(new Date()));

        if(companyService.findAll().isEmpty()){
            companyService.createInitialCompanies();
        }
        companyService.findAll().stream().forEach(company -> {
           categoryService.retrieveCategoryAndPersist(new Date(), company);
           itemService.retrieveItemAndPersist(new Date(),company);
           receiptService.retrieveReceiptAndPersist(new Date(),company);
        });
        //customerService.initialCustomerPersist();
        //storeService.initialStorePersist();
        //discountService.initialStorePersist();

    }
}