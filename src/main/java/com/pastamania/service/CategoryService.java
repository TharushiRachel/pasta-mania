package com.pastamania.service;

import com.pastamania.entity.Company;

import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface CategoryService {

    void retrieveCategoryAndPersist(Date date, Company company);


}
