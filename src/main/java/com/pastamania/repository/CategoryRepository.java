package com.pastamania.repository;

import com.pastamania.entity.Category;
import com.pastamania.entity.Company;
import com.pastamania.entity.Customer;
import com.pastamania.service.CategoryService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Pasindu Lakmal
 */
public interface CategoryRepository extends JpaRepository<Category,Long> {


    @Query("select c from Category c where c.company=?1 and c.createdAt= (SELECT max(c.createdAt) from Category c where c.company=?1)")
    Category findCustomerWithMaxCreatedDateAndCompany(Company company);


    @Query("select max(c.updatedAt) from Category c where c.company=?1")
    Category findCustomerWithMaxUpdatedDateAndCompany(Company company);

}
