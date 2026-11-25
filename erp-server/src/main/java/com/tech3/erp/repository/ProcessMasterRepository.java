package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.ProcessMaster;

import java.util.Optional;

public interface ProcessMasterRepository extends JpaRepository<ProcessMaster, Long>{
    Optional<ProcessMaster> findByProcessName(String processName);
}
