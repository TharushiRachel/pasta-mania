package com.pastamania.repository;

import com.pastamania.entity.Item;
import com.pastamania.entity.Receipt;
import com.pastamania.entity.ReceiptPayment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface ReceiptPaymentRepository extends JpaRepository<ReceiptPayment,Long> {

}
