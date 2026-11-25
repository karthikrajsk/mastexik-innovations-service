package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeeCanteenDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeCanteenDeductionRepository extends JpaRepository<EmployeeCanteenDeduction, Long> {

    List<EmployeeCanteenDeduction> findByMonth(String month);

    @Query("SELECT e FROM EmployeeCanteenDeduction e WHERE e.employee.id = :employeeId")
    List<EmployeeCanteenDeduction> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT e FROM EmployeeCanteenDeduction e WHERE e.employee.id = :employeeId AND e.month = :month")
    List<EmployeeCanteenDeduction> findByEmployeeIdAndMonth(@Param("employeeId") Long employeeId, @Param("month") String month);

    @Query("SELECT e FROM EmployeeCanteenDeduction e WHERE e.employee.id = :employeeId AND e.month = :month ORDER BY e.id DESC")
    Optional<EmployeeCanteenDeduction> findFirstByEmployeeIdAndMonthOrderByIdDesc(@Param("employeeId") Long employeeId, @Param("month") String month);
}
