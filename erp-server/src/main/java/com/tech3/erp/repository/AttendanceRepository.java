package com.tech3.erp.repository;

import com.tech3.erp.entity.Attendance;
import com.tech3.erp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);
    Optional<Attendance> findFirstByEmployeeAndDateAndTypeOrderByTimeDesc(Employee employee, LocalDate date, String type);
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Attendance> findByEmployeeAndDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
}
