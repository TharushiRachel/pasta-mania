package com.pastamania.repository;

import com.pastamania.entity.ReceiptLineItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptLineItemRepository extends JpaRepository<ReceiptLineItem, Long> {


}
