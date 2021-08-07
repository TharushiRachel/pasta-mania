package com.pastamania.repository;

import com.pastamania.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface ItemRepository extends JpaRepository<Item,Long> {


    Item findById(String id);


//    @Query("select max(c.createdAt) from Category c ")
//    Category findCustomerWithMaxCreatedDate();
//
//
//    @Query("select max(c.updatedAt) from Category c ")
//    Category findCustomerWithMaxUpdatedDate();

}
