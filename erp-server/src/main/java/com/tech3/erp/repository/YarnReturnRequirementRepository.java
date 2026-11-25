package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnReturnRequirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YarnReturnRequirementRepository extends JpaRepository<YarnReturnRequirement, Long> {
}
