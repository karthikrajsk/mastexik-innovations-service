package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tech3.erp.entity.WeftInward;

@Repository
public interface WeftInwardRepository extends JpaRepository<WeftInward, Long> {
    boolean existsByWeavingContractId(Long weavingContractId);

    boolean existsByWeavingYarnIssueId(Long weavingYarnIssueId);

    boolean existsByWeavingJobWorkOrderId(Long weavingJobWorkOrderId);

    Optional<WeftInward> findByWeavingYarnIssueId(Long weavingYarnIssueId);

    List<WeftInward> findByDestinationCompanyId(Long destinationCompanyId);
    List<WeftInward> findByLinkedBeamInwardId(Long linkedBeamInwardId);
    List<WeftInward> findByCompanyIdOrderByIdDesc(Long companyId);
    Page<WeftInward> findByCompanyId(Long companyId, Pageable pageable);
    Optional<WeftInward> findByIdAndCompanyId(Long id, Long companyId);
    Optional<WeftInward> findByInwardNo(String inwardNo);
}
