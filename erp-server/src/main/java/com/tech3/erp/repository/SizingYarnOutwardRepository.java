package com.tech3.erp.repository;

import com.tech3.erp.entity.SizingYarnOutward;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizingYarnOutwardRepository extends JpaRepository<SizingYarnOutward, Long> {

    List<SizingYarnOutward> findByFactoryCompanyIdOrderByIdDesc(Long factoryCompanyId);

    boolean existsBySizingPlanId(Long sizingPlanId);

    boolean existsBySizingOrderId(Long sizingOrderId);

    Page<SizingYarnOutward> findByFactoryCompanyId(Long factoryCompanyId, Pageable pageable);

    Optional<SizingYarnOutward> findByIdAndFactoryCompanyId(Long id, Long factoryCompanyId);
}
