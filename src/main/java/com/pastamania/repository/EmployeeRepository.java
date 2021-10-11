package com.pastamania.repository;

import com.pastamania.entity.Company;
import com.pastamania.entity.Customer;
import com.pastamania.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Pasindu Lakmal
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where e.company=?1 and e.createdAt= (SELECT max(e.createdAt) from Employee e where e.company=?1)")
    Employee findEmployeeWithMaxCreatedDateAndCompany(Company company);


    @Query("select e from Employee e where e.company=?1 and e.updatedAt= (SELECT max(e.updatedAt) from Employee e where e.company=?1)")
    Employee findEmployeeWithMaxUpdatedDateAndCompany(Company company);


}
