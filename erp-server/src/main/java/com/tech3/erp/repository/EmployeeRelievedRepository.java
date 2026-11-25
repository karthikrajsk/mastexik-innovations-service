package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeeRelieved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRelievedRepository extends JpaRepository<EmployeeRelieved, Long> {

    Optional<EmployeeRelieved> findByEmployeeId(Long employeeId);

    List<EmployeeRelieved> findByIsRelieved(Integer isRelieved);

    @Query("SELECT e FROM EmployeeRelieved e WHERE e.employeeId = :employeeId AND e.isRelieved = :isRelieved")
    Optional<EmployeeRelieved> findByEmployeeIdAndIsRelieved(@Param("employeeId") Long employeeId, @Param("isRelieved") Integer isRelieved);

    @Query("SELECT e FROM EmployeeRelieved e WHERE LOWER(e.reason) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(e.remarks) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<EmployeeRelieved> findByReasonOrRemarksContaining(@Param("search") String search);

    List<EmployeeRelieved> findByEmployeeIdIn(List<Long> employeeIds);
}
