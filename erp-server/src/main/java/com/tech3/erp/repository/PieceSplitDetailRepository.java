package com.tech3.erp.repository;

import com.tech3.erp.entity.PieceSplitDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceSplitDetailRepository extends JpaRepository<PieceSplitDetail, Long> {
    void deleteByPieceSplitId(Long pieceSplitId);
}
