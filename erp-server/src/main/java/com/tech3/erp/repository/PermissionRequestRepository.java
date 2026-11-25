package com.tech3.erp.repository;

import com.tech3.erp.entity.PermissionRequest;
import com.tech3.erp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface PermissionRequestRepository extends JpaRepository<PermissionRequest, Long> {

    List<PermissionRequest> findByEmployeeOrderByDateDesc(Employee employee);

    List<PermissionRequest> findByEmployeeIdOrderByDateDesc(Long employeeId);

    @Query("SELECT pr FROM PermissionRequest pr WHERE pr.date = :date " +
           "AND pr.employee.id = :employeeId " +
           "AND ((pr.fromTime <= :fromTime AND pr.toTime > :fromTime) " +
           "OR (pr.fromTime < :toTime AND pr.toTime >= :toTime) " +
           "OR (pr.fromTime >= :fromTime AND pr.toTime <= :toTime))")
    List<PermissionRequest> findOverlappingPermissions(
            @Param("employeeId") Long employeeId,
            @Param("date") LocalDate date,
            @Param("fromTime") LocalTime fromTime,
            @Param("toTime") LocalTime toTime);

    @Query("SELECT pr FROM PermissionRequest pr WHERE pr.date = :date " +
           "AND pr.employee.id = :employeeId " +
           "AND ((pr.fromTime <= :fromTime AND pr.toTime > :fromTime) " +
           "OR (pr.fromTime < :toTime AND pr.toTime >= :toTime) " +
           "OR (pr.fromTime >= :fromTime AND pr.toTime <= :toTime)) " +
           "AND pr.id != :excludeId")
    List<PermissionRequest> findOverlappingPermissionsExcludingCurrent(
            @Param("employeeId") Long employeeId,
            @Param("date") LocalDate date,
            @Param("fromTime") LocalTime fromTime,
            @Param("toTime") LocalTime toTime,
            @Param("excludeId") Long excludeId);

    @Query("SELECT pr FROM PermissionRequest pr WHERE pr.status = 'PENDING' ORDER BY pr.date ASC")
    List<PermissionRequest> findPendingPermissions();

    @Query("SELECT pr FROM PermissionRequest pr WHERE pr.date BETWEEN :startDate AND :endDate ORDER BY pr.date DESC")
    List<PermissionRequest> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(pr) FROM PermissionRequest pr WHERE pr.employee.id = :employeeId AND pr.date = :date")
    Long countPermissionsByEmployeeAndDate(@Param("employeeId") Long employeeId, @Param("date") LocalDate date);
}
