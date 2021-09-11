package com.pastamania.repository;

import com.pastamania.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Pasindu Lakmal
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {


}
