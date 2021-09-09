package com.pastamania.service;

import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface CustomerService {

    void retrieveCustomersAndPersist(Date date);

    void initialCustomerPersist();

    String parseThymeleafTemplate();


}
