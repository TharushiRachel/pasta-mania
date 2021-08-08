package com.pastamania.repository;

import com.pastamania.entity.LineItemLineDiscount;
import com.pastamania.entity.ReceiptTotalTax;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface LineItemLineDiscountRepository extends JpaRepository<LineItemLineDiscount,Long> {


}
