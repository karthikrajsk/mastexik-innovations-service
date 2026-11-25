package com.tech3.erp.repository;

import com.tech3.erp.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);

    List<LeaveRequest> findByEmployeeIdAndStatus(Long employeeId, String status);

    List<LeaveRequest> findByStatus(String status);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.employeeId = :employeeId AND " +
           "((lr.startDate <= :startDate AND lr.endDate >= :startDate) OR " +
           "(lr.startDate <= :endDate AND lr.endDate >= :endDate) OR " +
           "(lr.startDate >= :startDate AND lr.endDate <= :endDate))")
    List<LeaveRequest> findOverlappingLeaveRequests(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.startDate BETWEEN :startDate AND :endDate " +
           "OR lr.endDate BETWEEN :startDate AND :endDate " +
           "OR (lr.startDate <= :startDate AND lr.endDate >= :endDate)")
    List<LeaveRequest> findLeaveRequestsInDateRange(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT lr FROM LeaveRequest lr WHERE " +
           "LOWER(lr.reason) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(lr.leaveType) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(lr.status) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<LeaveRequest> searchLeaveRequests(@Param("search") String search);

    List<LeaveRequest> findByEmployeeIdIn(List<Long> employeeIds);
}
