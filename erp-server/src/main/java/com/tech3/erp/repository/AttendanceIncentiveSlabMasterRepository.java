package com.tech3.erp.repository;

import com.tech3.erp.entity.AttendanceIncentiveSlabMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AttendanceIncentiveSlabMasterRepository extends JpaRepository<AttendanceIncentiveSlabMaster, Long> {

    List<AttendanceIncentiveSlabMaster> findByIsActiveTrueOrderByWorkingHoursAscMonthTypeAscMinDaysAsc();

    List<AttendanceIncentiveSlabMaster> findByWorkingHoursAndMonthTypeAndIsActiveTrueOrderByMinDaysAsc(BigDecimal workingHours, String monthType);

    @Query("SELECT s FROM AttendanceIncentiveSlabMaster s WHERE s.isActive = true " +
           "AND s.workingHours = :workingHours " +
           "AND s.monthType = :monthType " +
           "AND (s.month IS NULL OR s.month = '' OR s.month = :targetMonth) " +
           "AND s.minDays <= :workedDays " +
           "AND (s.maxDays IS NULL OR s.maxDays >= :workedDays) " +
           "ORDER BY CASE WHEN s.month = :targetMonth THEN 0 ELSE 1 END, s.minDays DESC")
    List<AttendanceIncentiveSlabMaster> findMatchingSlab(
            @Param("workingHours") BigDecimal workingHours,
            @Param("monthType") String monthType,
            @Param("targetMonth") String targetMonth,
            @Param("workedDays") BigDecimal workedDays
    );
}
