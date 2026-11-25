package com.tech3.erp.repository;

import com.tech3.erp.entity.SizingJobWorkOrder;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizingJobWorkOrderRepository extends JpaRepository<SizingJobWorkOrder, Long> {
    Page<SizingJobWorkOrder> findByFactoryCompanyId(Long factoryCompanyId, Pageable pageable);
    Optional<SizingJobWorkOrder> findByIdAndFactoryCompanyId(Long id, Long factoryCompanyId);
    Optional<SizingJobWorkOrder> findBySizingPlanId(Long sizingPlanId);
    Optional<SizingJobWorkOrder> findBySizingWorkOrderNo(String sizingWorkOrderNo);
    boolean existsBySizingPlanId(Long sizingPlanId);
    void deleteBySizingPlanId(Long sizingPlanId);
}
