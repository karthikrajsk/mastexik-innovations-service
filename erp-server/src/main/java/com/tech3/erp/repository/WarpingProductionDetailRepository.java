package com.tech3.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech3.erp.entity.WarpingProductionDetail;

public interface WarpingProductionDetailRepository extends JpaRepository<WarpingProductionDetail, Long> {
    List<WarpingProductionDetail> findByWarpingProductionId(Long warpingProductionId);

    void deleteByWarpingProductionId(Long warpingProductionId);
}
