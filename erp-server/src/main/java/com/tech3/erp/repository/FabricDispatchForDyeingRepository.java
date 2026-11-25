package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.FabricDispatchForDyeing;

import java.util.List;
import java.util.Optional;

@Repository
public interface FabricDispatchForDyeingRepository extends JpaRepository<FabricDispatchForDyeing, Long> {
        boolean existsBySalesOrderId(Long salesOrderId);

    List<FabricDispatchForDyeing> findByCompanyId(Long companyId);
    List<FabricDispatchForDyeing> findByCompanyIdAndProcessType(Long companyId, String processType);
    Optional<FabricDispatchForDyeing> findByIdAndCompanyId(Long id, Long companyId);
    Optional<FabricDispatchForDyeing> findByIdAndCompanyIdAndProcessType(Long id, Long companyId, String processType);
        List<FabricDispatchForDyeing> findByDyeingWorkOrderId(Long dyeingWorkOrderId);

    boolean existsByDyeingWorkOrderId(Long dyeingWorkOrderId);
}
