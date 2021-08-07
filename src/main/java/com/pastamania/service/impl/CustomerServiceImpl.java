//package com.pastamania.service.impl;
//
//import com.pastamania.Response.CustomerResponse;
//import com.pastamania.entity.Customer;
//import com.pastamania.repository.CustomerRepository;
//import com.pastamania.service.CustomerService;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.TimeZone;
//import java.util.stream.Collectors;
//
///**
// * @author Pasindu Lakmal
// */
//@Service
//@Transactional
//@Slf4j
//public class CustomerServiceImpl implements CustomerService {
//
//
//    @Autowired
//    CustomerRepository customerRepository;
//
//    @Override
//    public void retrieveCustomersAndPersist(Date date) {
//        TimeZone tz = TimeZone.getTimeZone("UTC");
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//        df.setTimeZone(tz); // get the time zone
//        String nowAsISO = df.format(date);
//
//        Customer latestCreatedCustomer = customerRepository.findCustomerWithMaxCreatedDate();
//        Customer lastUpdatedCustomer = customerRepository.findCustomerWithMaxUpdatedDate();
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
//        ResponseEntity<CustomerResponse> createdResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers?created_at_min="+latestCreatedCustomer.getCreatedAt()+"&created_at_max="+nowAsISO+"", HttpMethod.GET, entity, CustomerResponse.class);
//        List<Customer> createdCustomers = createdResponse.getBody().getCustomers().stream().map(customer ->
//                modelMapper.map(customer, Customer.class)).collect(Collectors.toList());
//
//        customerRepository.saveAll(createdCustomers);
//
//        //newly updated
//        ResponseEntity<CustomerResponse> updatedResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers?updated_at_min="+lastUpdatedCustomer.getUpdatedAt()+"&updated_at_max="+nowAsISO+"", HttpMethod.GET, entity, CustomerResponse.class);
//        List<Customer> updatedCustomers = updatedResponse.getBody().getCustomers().stream().map(customer ->
//                modelMapper.map(customer, Customer.class)).collect(Collectors.toList());
//
//        customerRepository.saveAll(updatedCustomers);
//
//    }
//
//    @Override
//    public void initialCustomerPersist() {
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
//        ResponseEntity<CustomerResponse> createdResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers", HttpMethod.GET, entity, CustomerResponse.class);
//        List<Customer> createdCustomers = createdResponse.getBody().getCustomers().stream().map(customer ->
//                modelMapper.map(customer, Customer.class)).collect(Collectors.toList());
//
//        customerRepository.saveAll(createdCustomers);
//
//    }
//
//
//}
