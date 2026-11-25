package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeeAttendanceIncentive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeAttendanceIncentiveRepository extends JpaRepository<EmployeeAttendanceIncentive, Long> {

    List<EmployeeAttendanceIncentive> findByMonth(String month);

    @Query("SELECT e FROM EmployeeAttendanceIncentive e WHERE e.employee.id = :employeeId")
    List<EmployeeAttendanceIncentive> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT e FROM EmployeeAttendanceIncentive e WHERE e.employee.id = :employeeId AND e.month = :month")
    List<EmployeeAttendanceIncentive> findByEmployeeIdAndMonth(@Param("employeeId") Long employeeId, @Param("month") String month);

    @Query("SELECT e FROM EmployeeAttendanceIncentive e WHERE e.employee.id = :employeeId AND e.month = :month ORDER BY e.id DESC")
    List<EmployeeAttendanceIncentive> findByEmployeeIdAndMonthList(@Param("employeeId") Long employeeId, @Param("month") String month);

    @Query("SELECT e FROM EmployeeAttendanceIncentive e WHERE e.employee.id = :employeeId AND e.month = :month ORDER BY e.id DESC")
    Optional<EmployeeAttendanceIncentive> findFirstByEmployeeIdAndMonthOrderByIdDesc(@Param("employeeId") Long employeeId, @Param("month") String month);
}
