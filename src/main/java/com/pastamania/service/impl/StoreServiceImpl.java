package com.pastamania.service.impl;

import com.pastamania.component.RestApiClient;
import com.pastamania.configuration.ConfigProperties;
import com.pastamania.dto.response.StoreResponse;
import com.pastamania.entity.Store;
import com.pastamania.repository.StoreRepository;
import com.pastamania.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

//import com.pastamania.entity.Category;
//import com.pastamania.repository.CategoryRepository;
//import com.pastamania.service.CategoryService;

/**
 * @author Pasindu Lakmal
 */
@Service
@Transactional
@Slf4j
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private com.pastamania.modelmapper.ModelMapper modelMapperLocal;

    @Autowired
    private RestApiClient restApiClient;
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

    @Override
    public void retrieveCategoryAndPersist(Date date) {

    }

    @Override
    public void initialStorePersist() {

        log.info("get initial store details ");
        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "d22e68278c144eb8b22c50a2623bccc9");
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity <> (requestJson, headers);

        //newly created
        ResponseEntity<StoreResponse> createdResponse = restTemplate.exchange(configProperties.getLoyvers().getBaseUrl()+"stores", HttpMethod.GET, entity, StoreResponse.class);

        log.info("get initial store details createdResponse {}",createdResponse);

        List<Store> stores = createdResponse.getBody().getStores().stream().map(store ->
                modelMapper.map(store, Store.class)).collect(Collectors.toList());

        log.info("get initial store details stores {}",stores);

        storeRepository.saveAll(stores);

    }

    @Override
    public Integer getAllStoreByToken(String token) {
        HttpEntity<String> entity = getEntity(token);
        //newly created
        ResponseEntity<StoreResponse> createdResponse = restApiClient.getRestTemplate().exchange(configProperties.getLoyvers().getBaseUrl()+"stores", HttpMethod.GET, entity, StoreResponse.class);

        log.info("get initial store details createdResponse {}",createdResponse);

        List<Store> stores = modelMapperLocal.map(createdResponse.getBody().getStores(),Store.class);

        storeRepository.saveAll(stores);

        log.info("get initial store details stores {}",stores);

        return 0;
    }

    private HttpEntity<String> getEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity <> (requestJson, headers);
        return  entity;
    }
}
