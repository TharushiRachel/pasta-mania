package com.pastamania.service;

import com.pastamania.entity.Company;

import java.text.ParseException;
import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface ItemService {

    void retrieveItemAndPersist(Date date, Company company) throws ParseException;

}
