package com.tech3.erp.repository;

import com.tech3.erp.entity.SizingYarnOutwardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizingYarnOutwardDetailRepository extends JpaRepository<SizingYarnOutwardDetail, Long> {
    boolean existsBySizingYarnInwardId(Long sizingYarnInwardId);
    boolean existsBySizingYarnInwardNo(String sizingYarnInwardNo);
}
