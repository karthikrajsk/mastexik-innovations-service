// package com.tech3.erp.repository;

// import com.tech3.erp.entity.YarnRequirement;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface YarnRequirementRepository extends JpaRepository<YarnRequirement, Long> {}

package com.tech3.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.WeavingYarnRequirement;

@Repository
public interface WeavingYarnRequirementRepository extends JpaRepository<WeavingYarnRequirement, Long> {
    List<WeavingYarnRequirement> findByWeavingYarnIssueId(Long weavingYarnIssueId);

    @Query("SELECT r FROM WeavingYarnRequirement r WHERE r.weavingYarnIssue.weavingContractId = :weavingContractId AND r.activeFlag = true")
    List<WeavingYarnRequirement> findActiveByWeavingContractId(@Param("weavingContractId") Long weavingContractId);
}