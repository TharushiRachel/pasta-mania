package com.pastamania.service;

import com.pastamania.entity.Company;

import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface CustomerService {

    void retrieveCategoryAndPersist(Date date, Company company);

    String parseThymeleafTemplate();


}
