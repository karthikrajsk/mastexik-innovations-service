// package com.tech3.erp.repository;

// import com.tech3.erp.entity.YarnRequirement;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface YarnRequirementRepository extends JpaRepository<YarnRequirement, Long> {}

package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.WeavingYarnIssue;

@Repository
public interface WeavingYarnIssueRepository extends JpaRepository<WeavingYarnIssue, Long> {
    boolean existsByWeavingContractId(Long weavingContractId);

    List<WeavingYarnIssue> findByTradingCompanyIdOrderByIdDesc(Long tradingCompanyId);
    Page<WeavingYarnIssue> findByTradingCompanyId(Long tradingCompanyId, Pageable pageable);
    Optional<WeavingYarnIssue> findByIdAndTradingCompanyId(Long id, Long tradingCompanyId);
}
