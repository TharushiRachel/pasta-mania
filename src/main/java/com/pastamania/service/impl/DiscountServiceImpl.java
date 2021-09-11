package com.pastamania.service.impl;

import com.pastamania.repository.DiscountRepository;
import com.pastamania.repository.StoreRepository;
import com.pastamania.service.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Pasindu Lakmal
 */
@Service
@Transactional
@Slf4j
public class DiscountServiceImpl implements DiscountService {


    @Autowired
    DiscountRepository discountRepository;

    @Autowired
    StoreRepository storeRepository;

    @Override
    public void retrieveCategoryAndPersist(Date date) {

    }

    @Override
    public void initialStorePersist() {

    }

//    @Override
//    public void retrieveCategoryAndPersist(Date date) {
//        TimeZone tz = TimeZone.getTimeZone("UTC");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        df.setTimeZone(tz); // get the time zone
//        String nowAsISO = df.format(date);
//
//        Category latestCreatedCategory = categoryRepository.findCustomerWithMaxCreatedDate();
//        Category lastUpdatedCategory = categoryRepository.findCustomerWithMaxUpdatedDate();
//
//        RestTemplate restTemplate = new RestTemplate();
//        ModelMapper modelMapper = new ModelMapper();
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + "d22e68278c144eb8b22c50a2623bccc9");
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        String requestJson = "{}";
//        HttpEntity<String> entity = new HttpEntity <> (requestJson, headers);
//
//        //newly created
//        ResponseEntity<CategoryResponse> createdResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers?created_at_min="+latestCreatedCategory.getCreatedAt()+"&created_at_max="+nowAsISO+"", HttpMethod.GET, entity, CategoryResponse.class);
//        List<Category> createdCategories = createdResponse.getBody().getCategories().stream().map(category ->
//                modelMapper.map(category, Category.class)).collect(Collectors.toList());
//
//        categoryRepository.saveAll(createdCategories);
//
//        //newly updated
//        ResponseEntity<CategoryResponse> updatedResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers?updated_at_min="+lastUpdatedCategory.getUpdatedAt()+"&updated_at_max="+nowAsISO+"", HttpMethod.GET, entity, CategoryResponse.class);
//        List<Category> updatedCategories = updatedResponse.getBody().getCategories().stream().map(category ->
//                modelMapper.map(category, Category.class)).collect(Collectors.toList());
//
//        categoryRepository.saveAll(updatedCategories);
//
//    }


}
