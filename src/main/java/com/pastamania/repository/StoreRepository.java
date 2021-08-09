package com.pastamania.repository;

import com.pastamania.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface StoreRepository extends JpaRepository<Store,Long> {


    Store findById(String id);

//    @Query("select max(c.createdAt) from Category c ")
//    Category findCustomerWithMaxCreatedDate();
//
//
//    @Query("select max(c.updatedAt) from Category c ")
//    Category findCustomerWithMaxUpdatedDate();

}
