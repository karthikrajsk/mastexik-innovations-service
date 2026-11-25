package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import com.tech3.erp.entity.WeavingWorkOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeavingWorkOrderRepository extends JpaRepository<WeavingWorkOrder, Long> {
    boolean existsByWeavingContractId(Long weavingContractId);

    Optional<WeavingWorkOrder> findFirstByWeavingContractIdOrderByIdDesc(Long weavingContractId);

    List<WeavingWorkOrder> findByWeavingContractId(Long weavingContractId);
    
    @Query("SELECT w FROM WeavingWorkOrder w WHERE w.weavingContractId IN (SELECT c.id FROM WeavingContract c WHERE c.vendorId = :vendorId)")
    List<WeavingWorkOrder> findByVendorId(@Param("vendorId") Long vendorId);

    Optional<WeavingWorkOrder> findByWeavingWorkOrderNo(String weavingWorkOrderNo);
    
    List<WeavingWorkOrder> findByFactoryCompanyId(Long factoryCompanyId, Sort sort);
}