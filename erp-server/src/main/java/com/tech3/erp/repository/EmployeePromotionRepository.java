package com.tech3.erp.repository;

import com.tech3.erp.entity.EmployeePromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeePromotionRepository extends JpaRepository<EmployeePromotion, Long> {
    List<EmployeePromotion> findByEmployeeId(Long employeeId);
}
