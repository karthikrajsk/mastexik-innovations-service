package com.tech3.erp.repository;

import com.tech3.erp.entity.BrushingStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrushingStockRepository extends JpaRepository<BrushingStock, Long> {
    List<BrushingStock> findByCompanyId(Long companyId);
    Page<BrushingStock> findByCompanyId(Long companyId, Pageable pageable);
    Page<BrushingStock> findByCompanyIdAndFabricStatus(Long companyId, String fabricStatus, Pageable pageable);
}
