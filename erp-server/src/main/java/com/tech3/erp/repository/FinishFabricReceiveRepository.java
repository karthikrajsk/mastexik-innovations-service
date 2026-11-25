package com.tech3.erp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.FinishFabricReceive;

import java.util.List;
import java.util.Optional;

@Repository

public interface FinishFabricReceiveRepository extends JpaRepository<FinishFabricReceive, Long> {
        
    @Query("SELECT f FROM FinishFabricReceive f WHERE f.processType = :processType ORDER BY f.id DESC")
    List<FinishFabricReceive> findByProcessType(@Param("processType") String processType);
    
    List<FinishFabricReceive> findByCompanyId(Long companyId, Sort sort);
    
    Optional<FinishFabricReceive> findByIdAndCompanyId(Long id, Long companyId);
    
    List<FinishFabricReceive> findByCompanyIdAndProcessType(Long companyId, String processType);
    
    Optional<FinishFabricReceive> findByIdAndCompanyIdAndProcessType(Long id, Long companyId, String processType);
    
    List<FinishFabricReceive> findByDyeingWorkOrderId(Long dyeingWorkOrderId);

    boolean existsByDyeingWorkOrderId(Long dyeingWorkOrderId);
}