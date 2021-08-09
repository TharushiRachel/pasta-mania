package com.pastamania.repository;

import com.pastamania.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptRepository extends JpaRepository<Receipt,Long> {

}
