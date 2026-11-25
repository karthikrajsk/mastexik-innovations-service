package com.tech3.erp.repository;

import com.tech3.erp.entity.ShiftMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShiftMasterRepository extends JpaRepository<ShiftMaster, Long>, JpaSpecificationExecutor<ShiftMaster> {

    Optional<ShiftMaster> findByShiftCodeIgnoreCase(String shiftCode);

    boolean existsByShiftCodeIgnoreCase(String shiftCode);

    boolean existsByShiftCodeIgnoreCaseAndIdNot(String shiftCode, Long id);
}
