package com.tech3.erp.repository;

import com.tech3.erp.entity.PieceSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PieceSplitRepository extends JpaRepository<PieceSplit, Long> {
    List<PieceSplit> findByCompanyId(Long companyId);
    Optional<PieceSplit> findByIdAndCompanyId(Long id, Long companyId);
    void deleteByIdAndCompanyId(Long id, Long companyId);
}
