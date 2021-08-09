package com.pastamania.dto;

import com.pastamania.entity.Customer;
import lombok.Data;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
@Data
public class CustomerDto {

    private String companyName;
    private String headerName;
    private String Date;
    private String brand;
    private List<Customer> customers;
}
