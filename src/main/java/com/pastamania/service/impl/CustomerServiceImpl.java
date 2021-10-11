package com.pastamania.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pastamania.component.RestApiClient;
import com.pastamania.configuration.ConfigProperties;
import com.pastamania.dto.CustomerDto;
import com.pastamania.dto.response.CategoryResponse;
import com.pastamania.dto.response.CustomerResponse;
import com.pastamania.entity.Category;
import com.pastamania.entity.Company;
import com.pastamania.entity.Customer;
import com.pastamania.repository.CompanyRepository;
import com.pastamania.repository.CustomerRepository;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Pasindu Lakmal
 */
@Service
@Transactional
@Slf4j
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    CustomerRepository customerRepository;

  @Autowired
    private RestApiClient restApiClient;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private com.pastamania.modelmapper.ModelMapper modelMapperLocal;

    @Override
    public void retrieveCustomerAndPersist(Date date, Company company) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz); // get the time zone
        String nowAsISO = df.format(date);

        Customer lastUpdatedCategory = customerRepository.findCustomerWithMaxCreatedDateAndCompany(company);

        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + company.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        if (lastUpdatedCategory == null) {
            ResponseEntity<CustomerResponse> createdResponse = restApiClient.getRestTemplate().exchange(configProperties.getLoyvers().getBaseUrl() + "customers", HttpMethod.GET, entity, CustomerResponse.class);
            List<Customer> createdCustomers = createdResponse.getBody().getCustomers().stream().map(c ->
                    modelMapper.map(c, Customer.class)).collect(Collectors.toList());

            Company companyOp = companyRepository.findById(company.getId()).get();
            createdCustomers.forEach(customer -> customer.setCompany(companyOp));
            customerRepository.saveAll(createdCustomers);
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
                ResponseEntity<CustomerResponse> updatedResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers?created_at_min=" + oneSecondAddedDate + "&created_at_max=" + nowAsISO + "", HttpMethod.GET, entity, CustomerResponse.class);

                if (updatedResponse.getBody().getCustomers() != null) {
                    List<Customer> customers = updatedResponse.getBody().getCustomers().stream().map(customer ->
                            modelMapper.map(customer, Customer.class)).collect(Collectors.toList());
                    customerRepository.saveAll(customers);
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public String parseThymeleafTemplate() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
         Integer totalTotalVisits = 0;
         Double totalPointBalance=0.0;
         Double totalTotalSpent=0.0;
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();;
        CustomerDto customerDto = new CustomerDto();
        List<CustomerDto.Customer> customers = modelMapperLocal.map(customerRepository.findAll(), CustomerDto.Customer.class);
        for (CustomerDto.Customer customer : customers) {
            try {
                customer.setFirstVisit(convertToNewFormat(customer.getFirstVisit()));
                customer.setLastVisit(convertToNewFormat(customer.getLastVisit()));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            totalTotalVisits += Integer.parseInt(customer.getTotalVisits());
            totalPointBalance += Double.parseDouble(customer.getTotal_points()==null?"0":customer.getTotal_points());
            totalTotalSpent += Double.parseDouble(customer.getTotalSpent());

        }
        customerDto.setCustomers(customers);
        context.setVariable("customerList",customerDto.getCustomers());
        context.setVariable("totalTotalVisits",totalTotalVisits.toString());
        context.setVariable("totalPointBalance",totalPointBalance.toString());
        context.setVariable("totalTotalSpent",totalTotalSpent.toString());

        return templateEngine.process("thymeleaf_template", context);
    }

    public static String convertToNewFormat(String dateStr) throws ParseException {
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat destFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sourceFormat.setTimeZone(utc);
        Date convertedDate = sourceFormat.parse(dateStr);
        return destFormat.format(convertedDate);
    }



}
