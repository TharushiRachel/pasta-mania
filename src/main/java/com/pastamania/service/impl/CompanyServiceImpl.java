package com.pastamania.service.impl;

import com.pastamania.component.RestApiClient;
import com.pastamania.configuration.ConfigProperties;
import com.pastamania.dto.CustomerDto;
import com.pastamania.dto.response.CustomerResponse;
import com.pastamania.entity.Company;
import com.pastamania.entity.Customer;
import com.pastamania.repository.CompanyRepository;
import com.pastamania.repository.CustomerRepository;
import com.pastamania.service.CompanyService;
import com.pastamania.service.CustomerService;
import com.pastamania.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author Pasindu Lakmal
 */
@Service
@Transactional
@Slf4j
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    CompanyRepository companyRepository;


    @Override
    public void createInitialCompanies() {
        List<Company> companies =  new ArrayList<>();
        Company companyOne =  new Company();
        companyOne.setName("Island Wraps");
        companyOne.setToken("3e405b31626349b699a44984f8861c5b");
        Company companyTwo =  new Company();
        companyTwo.setName("PastaMania");
        companyTwo.setToken("d22e68278c144eb8b22c50a2623bccc9");
        companies.add(companyOne);
        companies.add(companyTwo);
        companyRepository.saveAll(companies);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
