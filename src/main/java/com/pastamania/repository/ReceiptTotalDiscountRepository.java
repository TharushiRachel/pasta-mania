package com.pastamania.repository;

import com.pastamania.entity.Item;
import com.pastamania.entity.Receipt;
import com.pastamania.entity.ReceiptTotalDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptTotalDiscountRepository extends JpaRepository<ReceiptTotalDiscount,Long> {

}
