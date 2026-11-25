package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeamStockRepository extends JpaRepository<BeamStock, Long> {
    List<BeamStock> findByCompanyIdOrderByIdDesc(Long companyId);
    Page<BeamStock> findByCompanyIdOrderByIdDesc(Long companyId, Pageable pageable);
    Page<BeamStock> findAllByOrderByIdDesc(Pageable pageable);
    Optional<BeamStock> findByIdAndCompanyId(Long id, Long companyId);
    boolean existsBySizedBeamNoAndCompanyId(String sizedBeamNo, Long companyId);
    List<BeamStock> findBySizedBeamNo(String sizedBeamNo);
    List<BeamStock> findBySizedBeamNoAndCompanyId(String sizedBeamNo, Long companyId);
}
