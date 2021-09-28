package com.pastamania.service.impl;

import com.pastamania.component.RestApiClient;
import com.pastamania.configuration.ConfigProperties;
import com.pastamania.dto.response.CategoryResponse;
import com.pastamania.entity.Category;
import com.pastamania.entity.Company;
import com.pastamania.repository.CategoryRepository;
import com.pastamania.repository.CompanyRepository;
import com.pastamania.service.CategoryService;
import com.pastamania.service.VariantService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class VariantServiceImpl implements VariantService {


    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    private RestApiClient restApiClient;

    @Autowired
    private ConfigProperties configProperties;


    @Override
    public void retrieveVariantAndPersist(Date date, Company company) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz); // get the time zone
        String nowAsISO = df.format(date);

        Category lastUpdatedCategory = categoryRepository.findCustomerWithMaxCreatedDateAndCompany(company);

        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + company.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        if (lastUpdatedCategory == null) {
            ResponseEntity<CategoryResponse> createdResponse = restApiClient.getRestTemplate().exchange(configProperties.getLoyvers().getBaseUrl() + "variants", HttpMethod.GET, entity, CategoryResponse.class);
            List<Category> createdCategories = createdResponse.getBody().getCategories().stream().map(category ->
                    modelMapper.map(category, Category.class)).collect(Collectors.toList());

            Company companyOp = companyRepository.findById(company.getId()).get();
            createdCategories.forEach(category -> category.setCompany(companyOp));
            categoryRepository.saveAll(createdCategories);
        } else {
            String updatedAt = lastUpdatedCategory.getCreatedAt();
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date convertedDate = sourceFormat.parse(updatedAt);
                Calendar c = Calendar.getInstance();
                c.setTime(convertedDate);
                c.add(Calendar.MILLISECOND, 1);
                String oneSecondAddedDate = sourceFormat.format(c.getTime());
                ResponseEntity<CategoryResponse> updatedResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/variants?created_at_min=" + oneSecondAddedDate + "&created_at_max=" + nowAsISO + "", HttpMethod.GET, entity, CategoryResponse.class);

                if (updatedResponse.getBody().getCategories() != null) {
                    List<Category> updatedCategories = updatedResponse.getBody().getCategories().stream().map(category ->
                            modelMapper.map(category, Category.class)).collect(Collectors.toList());
                    categoryRepository.saveAll(updatedCategories);
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }

}
