package com.pastamania.repository;

import com.pastamania.entity.Company;
import com.pastamania.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Pasindu Lakmal
 */
public interface ItemRepository extends JpaRepository<Item, Long> {


    Item findById(String id);


    @Query("select i from Item i where i.company=?1 and i.createdAt= (SELECT max(i.createdAt) from Item i where i.company=?1)")
    List<Item> findItemWithMaxCreatedDateAndCompany(Company company);

    @Query("select i from Item i where i.company=?1 and i.createdAt= (SELECT min(i.createdAt) from Item i where i.company=?1)")
    List<Item> findItemWithMinCreatedDateAndCompany(Company company);


    @Query("select i from Item i where i.company=?1 and i.updatedAt= (SELECT max(i.updatedAt) from Item i where i.company=?1)")
    List<Item> findItemWithMaxUpdatedDateAndCompany(Company company);

}
