package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.MachineMaster;

public interface MachineMasterRepository extends JpaRepository<MachineMaster, Long> {
    boolean existsByMachineNo(String machineNo);

    boolean existsByMachineNoAndIdNot(String machineNo, Long id);
}
