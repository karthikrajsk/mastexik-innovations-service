package com.tech3.erp.repository;

import com.tech3.erp.entity.SizingYarnInward;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizingYarnInwardRepository extends JpaRepository<SizingYarnInward, Long> {

    List<SizingYarnInward> findByFactoryCompanyId(Long factoryCompanyId, Sort sort);

    Optional<SizingYarnInward> findByIdAndFactoryCompanyId(Long id, Long factoryCompanyId);

    Optional<SizingYarnInward> findFirstByInwardNoEndingWith(String suffix);

    Optional<SizingYarnInward> findByInwardNo(String inwardNo);

    boolean existsBySizingJobWorkOrderId(Long sizingJobWorkOrderId);

    boolean existsBySizingYarnIssueId(Long sizingYarnIssueId);

}
