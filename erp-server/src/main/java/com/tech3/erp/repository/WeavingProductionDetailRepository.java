package com.tech3.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.WeavingProductionDetail;

public interface WeavingProductionDetailRepository extends JpaRepository<WeavingProductionDetail, Long> {
    List<WeavingProductionDetail> findByWeavingProductionId(Long weavingProductionId);

    void deleteByWeavingProductionId(Long weavingProductionId);
}
