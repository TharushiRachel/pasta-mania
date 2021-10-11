package com.pastamania.service.impl;

import com.pastamania.component.RestApiClient;
import com.pastamania.configuration.ConfigProperties;
import com.pastamania.dto.response.EmployeeResponse;
import com.pastamania.entity.Company;
import com.pastamania.entity.Employee;
import com.pastamania.repository.CompanyRepository;
import com.pastamania.repository.EmployeeRepository;
import com.pastamania.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RestApiClient restApiClient;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private CompanyRepository companyRepository;


    @Override
    public void retrieveEmployeeAndPersist(Date date, Company company) {

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz); // get the time zone
        String nowAsISO = df.format(date);

        Employee lastUpdatedEmployee = employeeRepository.findEmployeeWithMaxUpdatedDateAndCompany(company);

        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + company.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        if (lastUpdatedEmployee == null) {
            ResponseEntity<EmployeeResponse> createdResponse = restApiClient.getRestTemplate().exchange(configProperties.getLoyvers().getBaseUrl() + "employees", HttpMethod.GET, entity, EmployeeResponse.class);
            List<Employee> createdEmployees = createdResponse.getBody().getEmployees().stream().map(e ->
                    modelMapper.map(e, Employee.class)).collect(Collectors.toList());

            Company companyOp = companyRepository.findById(company.getId()).get();
            createdEmployees.forEach(customer -> customer.setCompany(companyOp));
            employeeRepository.saveAll(createdEmployees);
        } else {
            String updatedAt = lastUpdatedEmployee.getCreatedAt();
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date convertedDate = sourceFormat.parse(updatedAt);
                Calendar c = Calendar.getInstance();
                c.setTime(convertedDate);
                c.add(Calendar.MILLISECOND, 1);
                String oneSecondAddedDate = sourceFormat.format(c.getTime());
                ResponseEntity<EmployeeResponse> updatedResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/employees?created_at_min=" + oneSecondAddedDate + "&created_at_max=" + nowAsISO + "", HttpMethod.GET, entity, EmployeeResponse.class);

                if (updatedResponse.getBody().getEmployees() != null) {
                    List<Employee> employees = updatedResponse.getBody().getEmployees().stream().map(employee ->
                            modelMapper.map(employee, Employee.class)).collect(Collectors.toList());
                    employeeRepository.saveAll(employees);
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }
}
