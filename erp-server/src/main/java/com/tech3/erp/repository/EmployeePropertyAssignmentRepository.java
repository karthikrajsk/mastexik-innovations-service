package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeePropertyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePropertyAssignmentRepository extends JpaRepository<EmployeePropertyAssignment, Long> {

    @Query("SELECT e FROM EmployeePropertyAssignment e WHERE e.employee.id = :employeeId")
    List<EmployeePropertyAssignment> findByEmployeeId(@Param("employeeId") Long employeeId);

    List<EmployeePropertyAssignment> findByStatus(String status);

    List<EmployeePropertyAssignment> findByCompanyId(Long companyId);

    List<EmployeePropertyAssignment> findByCompanyIdAndStatus(Long companyId, String status);

    @Query("SELECT e FROM EmployeePropertyAssignment e WHERE e.item.id = :itemId")
    List<EmployeePropertyAssignment> findByItemId(@Param("itemId") Long itemId);

    @Query("SELECT e FROM EmployeePropertyAssignment e WHERE " +
           "(:companyId IS NULL OR e.companyId = :companyId) AND " +
           "(:status IS NULL OR e.status = :status) AND " +
           "(:department IS NULL OR LOWER(e.department) = LOWER(:department)) AND " +
           "(:employeeId IS NULL OR e.employee.id = :employeeId) " +
           "ORDER BY e.assignedDate DESC, e.id DESC")
    List<EmployeePropertyAssignment> searchAssignments(
            @Param("companyId") Long companyId,
            @Param("status") String status,
            @Param("department") String department,
            @Param("employeeId") Long employeeId
    );
}
