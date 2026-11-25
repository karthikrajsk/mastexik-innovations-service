package com.tech3.erp.repository;

import com.tech3.erp.entity.PieceJoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PieceJoinRepository extends JpaRepository<PieceJoin, Long> {
    List<PieceJoin> findByCompanyId(Long companyId);
    Optional<PieceJoin> findByIdAndCompanyId(Long id, Long companyId);
    void deleteByIdAndCompanyId(Long id, Long companyId);
}
