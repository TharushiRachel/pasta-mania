package com.pastamania.service;

import java.io.ByteArrayInputStream;
import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface CustomerService {

    void retrieveCustomersAndPersist(Date date);

    void initialCustomerPersist();

    ByteArrayInputStream generateCustomerReportPDF( );

}
