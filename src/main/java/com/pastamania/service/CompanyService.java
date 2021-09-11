package com.pastamania.service;

import com.pastamania.entity.Company;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
public interface CompanyService {

     void createInitialCompanies();

     List<Company> findAll();
}
