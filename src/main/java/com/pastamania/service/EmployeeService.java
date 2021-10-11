package com.pastamania.service;

import com.pastamania.entity.Company;

import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
public interface EmployeeService {

    void retrieveEmployeeAndPersist(Date date, Company company);

}
