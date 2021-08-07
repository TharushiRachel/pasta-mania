package com.pastamania.repository;

import com.pastamania.entity.Item;
import com.pastamania.entity.VariantStore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface VariantStoreRepository extends JpaRepository<VariantStore,Long> {




//    @Query("select max(c.createdAt) from Category c ")
//    Category findCustomerWithMaxCreatedDate();
//
//
//    @Query("select max(c.updatedAt) from Category c ")
//    Category findCustomerWithMaxUpdatedDate();

}
