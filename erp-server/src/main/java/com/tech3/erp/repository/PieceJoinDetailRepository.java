package com.tech3.erp.repository;

import com.tech3.erp.entity.PieceJoinDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PieceJoinDetailRepository extends JpaRepository<PieceJoinDetail, Long> {
    void deleteByPieceJoinId(Long pieceJoinId);
    Optional<PieceJoinDetail> findFirstByPieceNoIn(List<String> pieceNos);
    Optional<PieceJoinDetail> findFirstByPieceNoInAndPieceJoin_CompanyId(List<String> pieceNos, Long companyId);
}
