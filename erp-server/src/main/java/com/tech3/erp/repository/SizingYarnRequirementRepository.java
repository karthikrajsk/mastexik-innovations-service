package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.SizingYarnRequirement;
import java.util.List;

@Repository
public interface SizingYarnRequirementRepository extends JpaRepository<SizingYarnRequirement, Long> {

    @Query("SELECT r FROM SizingYarnRequirement r WHERE r.sizingYarnIssueEntry.sizingPlanId = :sizingPlanId AND r.activeFlag = true")
    List<SizingYarnRequirement> findActiveBySizingPlanId(@Param("sizingPlanId") Long sizingPlanId);
}