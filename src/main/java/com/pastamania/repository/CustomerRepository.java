package com.pastamania.repository;

import com.pastamania.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Pasindu Lakmal
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {


    @Query("select max(c.createdAt) from Customer c ")
    Customer findCustomerWithMaxCreatedDate();


    @Query("select max(c.updatedAt) from Customer c ")
    Customer findCustomerWithMaxUpdatedDate();

}
