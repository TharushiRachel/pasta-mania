package com.pastamania.service;

import com.pastamania.entity.Company;

import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface VariantService {

    void retrieveVariantAndPersist(Date date, Company company);


}
