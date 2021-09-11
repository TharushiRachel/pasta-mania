package com.pastamania.repository;

import com.pastamania.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface DiscountRepository extends JpaRepository<Discount, Long> {


//    @Query("select max(c.createdAt) from Category c ")
//    Category findCustomerWithMaxCreatedDate();
//
//
//    @Query("select max(c.updatedAt) from Category c ")
//    Category findCustomerWithMaxUpdatedDate();

}
