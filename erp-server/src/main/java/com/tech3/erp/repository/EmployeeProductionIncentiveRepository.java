package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeeProductionIncentive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeProductionIncentiveRepository extends JpaRepository<EmployeeProductionIncentive, Long> {

    List<EmployeeProductionIncentive> findByMonth(String month);

    @Query("SELECT e FROM EmployeeProductionIncentive e WHERE e.employee.id = :employeeId")
    List<EmployeeProductionIncentive> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT e FROM EmployeeProductionIncentive e WHERE e.employee.id = :employeeId AND e.month = :month")
    List<EmployeeProductionIncentive> findByEmployeeIdAndMonth(@Param("employeeId") Long employeeId, @Param("month") String month);

    @Query("SELECT e FROM EmployeeProductionIncentive e WHERE e.employee.id = :employeeId AND e.month = :month ORDER BY e.id DESC")
    Optional<EmployeeProductionIncentive> findFirstByEmployeeIdAndMonthOrderByIdDesc(@Param("employeeId") Long employeeId, @Param("month") String month);
}
