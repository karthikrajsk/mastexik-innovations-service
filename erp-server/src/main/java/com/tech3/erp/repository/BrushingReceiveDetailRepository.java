package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tech3.erp.entity.BrushingReceiveDetail;

@Repository
public interface BrushingReceiveDetailRepository extends JpaRepository<BrushingReceiveDetail, Long> {
    java.util.List<BrushingReceiveDetail> findByPieceNumberIn(java.util.List<String> pieceNumbers);

    java.util.List<BrushingReceiveDetail> findByPieceNumberInAndBrushingReceiveCompanyId(java.util.List<String> pieceNumbers, Long companyId);

    java.util.List<BrushingReceiveDetail> findByHfplRollNoInAndBrushingReceiveCompanyId(java.util.List<String> hfplRollNos, Long companyId);

    @org.springframework.data.jpa.repository.Query("SELECT MAX(d.hfplRollNo) FROM BrushingReceiveDetail d WHERE d.hfplRollNo LIKE :prefix%")
    String findMaxHfplRollNoByPrefix(@org.springframework.data.repository.query.Param("prefix") String prefix);

    @org.springframework.data.jpa.repository.Query("SELECT MAX(d.hfplDcNo) FROM BrushingReceiveDetail d WHERE d.hfplDcNo LIKE :prefix%")
    String findMaxHfplDcNoByPrefix(@org.springframework.data.repository.query.Param("prefix") String prefix);

    @org.springframework.data.jpa.repository.Query(value =
        "SELECT " +
        "    TRIM(d.construction) AS construction, " +
        "    d.fabric_master_id, " +
        "    SUM(CASE " +
        "        WHEN COALESCE(d.is_brushed, 0) = 1 " +
        "         AND (CAST(:targetDate AS date) IS NULL OR COALESCE(d.rolling_date, d.brushing_date, r.receive_date) <= CAST(:targetDate AS date)) " +
        "         AND (CAST(:targetDate AS date) IS NULL OR d.dispatch_date IS NULL OR d.dispatch_date > CAST(:targetDate AS date)) " +
        "         AND COALESCE(d.is_dispatched, 0) = 0 " +
        "         AND COALESCE(d.is_rejected, 0) = 0 " +
        "         AND COALESCE(r.is_rejected, 0) = 0 " +
        "         AND UPPER(TRIM(COALESCE(d.unwind_status, ''))) <> 'REJECTED' " +
        "         AND COALESCE(d.is_return, 0) = 0 " +
        "        THEN COALESCE(NULLIF(d.pass_mtr, 0), NULLIF(d.brushed_meters, 0), NULLIF(d.unwound_meters, 0), d.greige_meters, 0) " +
        "        ELSE 0 " +
        "    END) AS brushed_fabric_stock, " +
        "    SUM(CASE " +
        "        WHEN (CAST(:targetDate AS date) IS NULL OR r.receive_date <= CAST(:targetDate AS date)) " +
        "         AND (COALESCE(d.is_brushed, 0) = 0 OR (CAST(:targetDate AS date) IS NOT NULL AND COALESCE(d.rolling_date, d.brushing_date) > CAST(:targetDate AS date))) " +
        "         AND (CAST(:targetDate AS date) IS NULL OR d.dispatch_date IS NULL OR d.dispatch_date > CAST(:targetDate AS date)) " +
        "         AND COALESCE(d.is_dispatched, 0) = 0 " +
        "         AND COALESCE(d.is_rejected, 0) = 0 " +
        "         AND COALESCE(r.is_rejected, 0) = 0 " +
        "         AND UPPER(TRIM(COALESCE(d.unwind_status, ''))) <> 'REJECTED' " +
        "         AND COALESCE(d.is_return, 0) = 0 " +
        "        THEN COALESCE(NULLIF(d.unwound_meters, 0), NULLIF(d.greige_meters, 0), d.pass_mtr, 0) " +
        "        ELSE 0 " +
        "    END) AS waiting_for_brushing, " +
        "    SUM(CASE " +
        "        WHEN (COALESCE(d.is_rejected, 0) = 1 OR COALESCE(r.is_rejected, 0) = 1 OR UPPER(TRIM(COALESCE(d.unwind_status, ''))) = 'REJECTED') " +
        "         AND (CAST(:targetDate AS date) IS NULL OR COALESCE(d.rejection_date, d.unwind_date, d.unwinded_date, r.receive_date) <= CAST(:targetDate AS date)) " +
        "        THEN COALESCE(NULLIF(d.unwound_meters, 0), NULLIF(d.greige_meters, 0), NULLIF(d.pass_mtr, 0), 0) " +
        "        ELSE 0 " +
        "    END) AS rejected_fabric " +
        "FROM masters.brushing_receive_detail d " +
        "INNER JOIN masters.brushing_receive r ON d.brushing_receive_id = r.id " +
        "WHERE r.company_id = :companyId " +
        "  AND r.active_flag = true " +
        "  AND d.active_flag = true " +
        "  AND TRIM(d.construction) IS NOT NULL AND TRIM(d.construction) <> '' " +
        "GROUP BY TRIM(d.construction), d.fabric_master_id " +
        "ORDER BY TRIM(d.construction) ASC",
        nativeQuery = true)
    java.util.List<Object[]> getBrushingUnitFabricStatus(
        @org.springframework.data.repository.query.Param("companyId") Long companyId,
        @org.springframework.data.repository.query.Param("targetDate") java.time.LocalDate targetDate
    );
}
