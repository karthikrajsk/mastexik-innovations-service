package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.GradeMaster;

import java.util.Optional;

@Repository
public interface GradeMasterRepository extends JpaRepository<GradeMaster, Long> {
    Optional<GradeMaster> findByGradeCode(String gradeCode);
    Optional<GradeMaster> findByGradeName(String gradeName);
}
