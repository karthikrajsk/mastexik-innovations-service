package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.DefectMaster;

import java.util.Optional;

@Repository
public interface DefectMasterRepository extends JpaRepository<DefectMaster, Long> {
    Optional<DefectMaster> findByDefectCode(String defectCode);
    Optional<DefectMaster> findByDefectName(String defectName);
}