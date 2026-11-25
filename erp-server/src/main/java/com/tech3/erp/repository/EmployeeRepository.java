package com.tech3.erp.repository;

import com.tech3.erp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // For suggestions functionality
    @Query("SELECT DISTINCT e.employeeName FROM Employee e WHERE e.employeeName IS NOT NULL")
    List<String> findDistinctEmployeeNames();
    
    @Query("SELECT DISTINCT e.designation FROM Employee e WHERE e.designation IS NOT NULL")
    List<String> findDistinctDesignations();
    
    @Query("SELECT DISTINCT e.pf FROM Employee e WHERE e.pf IS NOT NULL")
    List<String> findDistinctPfValues();
    
    @Query("SELECT DISTINCT e.tax FROM Employee e WHERE e.tax IS NOT NULL")
    List<String> findDistinctTaxValues();
    
    // Check if employee name exists (for validation)
    boolean existsByEmployeeName(String employeeName);
    
    // Check if employee code exists
    boolean existsByEmployeeCode(String employeeCode);

    Optional<Employee> findByEmployeeCode(String employeeCode);
    
    // Find by employee name
    Optional<Employee> findByEmployeeName(String employeeName);
    
    // Fetch employees with all associated entities eagerly to avoid lazy loading issues
    @Query("SELECT DISTINCT e FROM Employee e " +
           "LEFT JOIN FETCH e.salaryDetails " +
           "LEFT JOIN FETCH e.shiftDetails " +
           "LEFT JOIN FETCH e.employmentDetails " +
           "LEFT JOIN FETCH e.bankDetails")
    List<Employee> findAllWithAssociations();
}