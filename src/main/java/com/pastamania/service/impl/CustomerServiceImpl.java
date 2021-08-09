package com.pastamania.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pastamania.dto.CustomerDto;
import com.pastamania.entity.Customer;
import com.pastamania.repository.CustomerRepository;
import com.pastamania.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @Override
    public void retrieveCustomersAndPersist(Date date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz); // get the time zone
        String nowAsISO = df.format(date);

        Customer latestCreatedCustomer = customerRepository.findCustomerWithMaxCreatedDate();
        Customer lastUpdatedCustomer = customerRepository.findCustomerWithMaxUpdatedDate();

        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "d22e68278c144eb8b22c50a2623bccc9");
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        //newly created
        ResponseEntity<com.pastamania.dto.Response.CustomerResponse> createdResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers?created_at_min=" + latestCreatedCustomer.getCreatedAt() + "&created_at_max=" + nowAsISO + "", HttpMethod.GET, entity, com.pastamania.dto.Response.CustomerResponse.class);
        List<Customer> createdCustomers = createdResponse.getBody().getCustomers().stream().map(customer ->
                modelMapper.map(customer, Customer.class)).collect(Collectors.toList());

        customerRepository.saveAll(createdCustomers);

        //newly updated
        ResponseEntity<com.pastamania.dto.Response.CustomerResponse> updatedResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers?updated_at_min=" + lastUpdatedCustomer.getUpdatedAt() + "&updated_at_max=" + nowAsISO + "", HttpMethod.GET, entity, com.pastamania.dto.Response.CustomerResponse.class);
        List<Customer> updatedCustomers = updatedResponse.getBody().getCustomers().stream().map(customer ->
                modelMapper.map(customer, Customer.class)).collect(Collectors.toList());

        customerRepository.saveAll(updatedCustomers);

    }

    @Override
    public void initialCustomerPersist() {

        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "d22e68278c144eb8b22c50a2623bccc9");
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        //newly created
        ResponseEntity<com.pastamania.dto.Response.CustomerResponse> createdResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/customers", HttpMethod.GET, entity, com.pastamania.dto.Response.CustomerResponse.class);
        List<Customer> createdCustomers = createdResponse.getBody().getCustomers().stream().map(customer ->
                modelMapper.map(customer, Customer.class)).collect(Collectors.toList());

        customerRepository.saveAll(createdCustomers);

    }

    @Override
    public ByteArrayInputStream generateCustomerReportPDF() {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            CustomerDto result = viewCustomer();

            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font headerFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.BLACK);
            Font bodyLabelFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 12, BaseColor.BLACK);
            Font bodyMainFont = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);

            //set header
            Paragraph headerElement = new Paragraph(" CUSTOMER REPORT", headerFont);
            headerElement.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(headerElement);

            //set main details
            if (result.getCustomers().isEmpty()) {
                throw new Exception("Customer Report PDF Customers not found.");
            } else {
                document.add(new Paragraph("" + result.getCompanyName(), bodyMainFont));
                document.add(new Paragraph("", bodyLabelFont));
                document.add(new Paragraph("" + result.getHeaderName(), bodyMainFont));
                document.add(new Paragraph("DATE              : " + LocalDate.now(), bodyMainFont));
                document.add(new Paragraph("Brand      : " + result.getBrand(), bodyMainFont));

                document.add(new Paragraph(" ", bodyMainFont));
            }

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            Phrase defaultPhrase = new Phrase();
            defaultPhrase.setFont(bodyMainFont);
            table.getDefaultCell().setPhrase(defaultPhrase);
            addSalesReportTableHeader(table, bodyMainFont);
            addSalesReportTableRows(table, result, bodyMainFont);
            document.add(table);

            document.add(new Paragraph(" ", bodyMainFont));
            document.add(new Paragraph(" ", bodyMainFont));


            document.close();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("Sales report pdf generation error", e);
        }

        return null;
    }

    CustomerDto viewCustomer() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomers(customerRepository.findAll());

        return customerDto;
    }


    private void addSalesReportTableHeader(PdfPTable table, Font bodyMainFont) throws DocumentException {
        Stream.of("Customer Name", "Email", "Phone", "First Visit", "Last Visit", "Total Visits", "Points balance", "Total spent")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, bodyMainFont));
                    header.setNoWrap(false);
                    table.addCell(header);
                });
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidths(new float[]{7, 7, 8, 8, 8, 10, 8, 8});
    }

    private void addSalesReportTableRows(PdfPTable table, CustomerDto result, Font bodyMainFont) {
        Phrase phrase = new Phrase();
        phrase.setFont(bodyMainFont);
        result.getCustomers().forEach(customer -> {

            table.addCell(new Phrase(customer.getName(), bodyMainFont));
            table.addCell(new Phrase(customer.getEmail(), bodyMainFont));
            table.addCell(new Phrase(customer.getPhoneNumber(), bodyMainFont));
            table.addCell(new Phrase(customer.getFirstVisit(), bodyMainFont));
            table.addCell(new Phrase(customer.getLastVisit(), bodyMainFont));
            table.addCell(new Phrase(customer.getTotalVisits(), bodyMainFont));
            table.addCell(new Phrase(customer.getTotal_points(), bodyMainFont));
            table.addCell(new Phrase(customer.getTotalSpent(), bodyMainFont));
        });
        PdfPCell emptyCell = new PdfPCell(new Phrase());
        emptyCell.setBorderWidth(0);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
        table.addCell(emptyCell);
//        table.addCell(new Phrase(Optional.ofNullable(result.getTotalCommission()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_EVEN).toString(), bodyMainFont));
//        table.addCell(new Phrase(Optional.ofNullable(result.getTotalQuantity()).orElse(BigDecimal.ZERO).toString(), bodyMainFont));
//        table.addCell(new Phrase(Optional.ofNullable(result.getTotalValue()).orElse(BigDecimal.ZERO).toString(), bodyMainFont));

    }


}
