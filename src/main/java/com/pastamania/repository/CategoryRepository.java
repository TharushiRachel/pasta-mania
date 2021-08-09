package com.pastamania.repository;

import com.pastamania.entity.Category;
import com.pastamania.entity.Customer;
import com.pastamania.service.CategoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Pasindu Lakmal
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {


    @Query("select max(c.createdAt) from Category c ")
    Category findCustomerWithMaxCreatedDate();


    @Query("select max(c.updatedAt) from Category c ")
    Category findCustomerWithMaxUpdatedDate();

}
