package com.pastamania.repository;

import com.pastamania.entity.Item;
import com.pastamania.entity.ItemVariant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface ItemVariantRepository extends JpaRepository<ItemVariant,Long> {




//    @Query("select max(c.createdAt) from Category c ")
//    Category findCustomerWithMaxCreatedDate();
//
//
//    @Query("select max(c.updatedAt) from Category c ")
//    Category findCustomerWithMaxUpdatedDate();

}
