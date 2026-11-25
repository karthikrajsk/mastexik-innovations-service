package com.tech3.erp.repository;

import com.tech3.erp.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByHolidayDateBetween(LocalDate startDate, LocalDate endDate);
    List<Holiday> findByIsFestivalWageApplicableTrue();
    List<Holiday> findByHolidayDate(LocalDate holidayDate);
}
