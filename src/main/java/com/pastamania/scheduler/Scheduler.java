package com.pastamania.scheduler;

import com.pastamania.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
@Component
@EnableScheduling
public class Scheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Autowired
//    CustomerService customerService;
//
//    @Autowired
//    CategoryService categoryService;
//
//    @Autowired
//    StoreService storeService;
//
//    @Autowired
//    DiscountService discountService;
//
//    @Autowired
//    ItemService itemService;

    @Autowired
    ReceiptService receiptService;

    @Scheduled(cron = "*/5 * * * * *")
    public void reportCurrentTime() {
        System.out.println("Current time = " + dateFormat.format(new Date()));
//        customerService.initialCustomerPersist();
//        categoryService.initialCategoryPersist();
//          storeService.initialStorePersist();
        //    discountService.initialStorePersist();

        //   itemService.initialStorePersist();

        //receiptService.initialPersist();

    }
}