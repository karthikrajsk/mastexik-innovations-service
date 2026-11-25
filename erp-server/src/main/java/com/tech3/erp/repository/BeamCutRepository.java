package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamCut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeamCutRepository extends JpaRepository<BeamCut, Long> {
    List<BeamCut> findByCompanyId(Long companyId);
    Optional<BeamCut> findByIdAndCompanyId(Long id, Long companyId);
}
