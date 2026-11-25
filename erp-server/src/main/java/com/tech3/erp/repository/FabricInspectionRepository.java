package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.FabricInspection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FabricInspectionRepository extends JpaRepository<FabricInspection, Long> {
    List<FabricInspection> findByCompanyId(Long companyId);
    Optional<FabricInspection> findFirstByPieceEntryId(Long pieceEntryId);
    List<FabricInspection> findAllByPieceEntryIdIn(List<Long> pieceEntryIds);
    void deleteByPieceEntryId(Long pieceEntryId);
    boolean existsByWeavingWorkOrderId(Long weavingWorkOrderId);
    boolean existsByOrderNo(String orderNo);
    void deleteByPieceEntryIdIn(List<Long> pieceEntryIds);
}
