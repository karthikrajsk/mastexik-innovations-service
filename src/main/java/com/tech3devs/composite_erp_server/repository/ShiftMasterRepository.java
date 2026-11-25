package com.tech3devs.composite_erp_server.repository;

import com.tech3devs.composite_erp_server.entity.ShiftMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShiftMasterRepository extends JpaRepository<ShiftMaster, Long> {

    Optional<ShiftMaster> findByShiftCodeIgnoreCase(String shiftCode);

    boolean existsByShiftCodeIgnoreCase(String shiftCode);

    boolean existsByShiftCodeIgnoreCaseAndIdNot(String shiftCode, Long id);

    @Query("SELECT s FROM ShiftMaster s WHERE " +
           "(:search IS NULL OR LOWER(s.shiftCode) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(s.shiftName) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(s.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:shiftType IS NULL OR s.shiftType = :shiftType) AND " +
           "(:activeFlag IS NULL OR s.activeFlag = :activeFlag)")
    Page<ShiftMaster> searchShifts(
            @Param("search") String search,
            @Param("shiftType") String shiftType,
            @Param("activeFlag") Boolean activeFlag,
            Pageable pageable
    );
}
