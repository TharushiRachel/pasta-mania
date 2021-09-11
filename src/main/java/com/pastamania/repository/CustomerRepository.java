package com.pastamania.repository;

import com.pastamania.entity.Company;
import com.pastamania.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Pasindu Lakmal
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where c.company=?1 and c.createdAt= (SELECT max(c.createdAt) from Customer c where c.company=?1)")
    Customer findCustomerWithMaxCreatedDateAndCompany(Company company);


    @Query("select c from Customer c where c.company=?1 and c.updatedAt= (SELECT max(c.updatedAt) from Customer c where c.company=?1)")
    Customer findCustomerWithMaxUpdatedDateAndCompany(Company company);


}
