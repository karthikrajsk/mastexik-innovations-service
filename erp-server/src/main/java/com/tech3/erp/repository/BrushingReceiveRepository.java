package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.tech3.erp.entity.BrushingReceive;
import java.util.List;

@Repository
public interface BrushingReceiveRepository extends JpaRepository<BrushingReceive, Long> {
    List<BrushingReceive> findByCompanyId(Long companyId);
    List<BrushingReceive> findByCompanyIdAndActiveFlag(Long companyId, Boolean activeFlag);

    @Query(value =
        "SELECT " +
        "    CAST(DATE_TRUNC('day', event_date) AS DATE)              AS period_date, " +
        "    fabric_master_id, " +
        "    TRIM(construction)                                       AS construction, " +
        "    SUM(CASE WHEN event_type = 'RECEIVE'  THEN qty ELSE 0 END) AS received_mtr, " +
        "    SUM(CASE WHEN event_type = 'RETURN'   THEN qty ELSE 0 END) AS return_mtr, " +
        "    SUM(CASE WHEN event_type = 'DISPATCH' THEN qty ELSE 0 END) AS dispatch_mtr, " +
        "    SUM(excess)   AS excess_mtr, " +
        "    SUM(shortage) AS shortage_mtr " +
        "FROM ( " +
        "    -- 1. OVERALL MOVEMENTS (Status ALL / empty)\n" +
        "    SELECT br.receive_date AS event_date, 'RECEIVE' AS event_type, " +
        "           brd.fabric_master_id, TRIM(brd.construction) AS construction, " +
        "           COALESCE(brd.greige_meters, 0) AS qty, 0 AS excess, 0 AS shortage " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND br.company_id = :companyId " +
        "      AND (CAST(:status AS text) IS NULL OR CAST(:status AS text) = '' OR CAST(:status AS text) = 'ALL') " +
        "    UNION ALL " +
        "    SELECT COALESCE(brd.return_date, brd.rejection_date, br.receive_date) AS event_date, 'RETURN', " +
        "           brd.fabric_master_id, TRIM(brd.construction), " +
        "           COALESCE(brd.greige_meters, 0), 0, 0 " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND (COALESCE(brd.is_return, 0) = 1 OR COALESCE(brd.is_rejected, 0) = 1) " +
        "      AND br.company_id = :companyId " +
        "      AND (CAST(:status AS text) IS NULL OR CAST(:status AS text) = '' OR CAST(:status AS text) = 'ALL') " +
        "    UNION ALL " +
        "    SELECT COALESCE(brd.dispatch_date, br.receive_date) AS event_date, 'DISPATCH', " +
        "           brd.fabric_master_id, TRIM(brd.construction), " +
        "           COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), brd.greige_meters, 0), 0, 0 " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND (brd.is_dispatched = 1 OR brd.brushing_status = 'DISPATCHED') " +
        "      AND br.company_id = :companyId " +
        "      AND (CAST(:status AS text) IS NULL OR CAST(:status AS text) = '' OR CAST(:status AS text) = 'ALL') " +
        "    UNION ALL " +
        "    SELECT " +
        "        br.receive_date AS event_date, " +
        "        'VARIATION', " +
        "        brd.fabric_master_id, TRIM(brd.construction), " +
        "        0, " +
        "        CASE WHEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) > 0 " +
        "             THEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) ELSE 0 END, " +
        "        CASE WHEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) < 0 " +
        "             THEN ABS(COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0)) ELSE 0 END " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 " +
        "      AND br.company_id = :companyId " +
        "      AND (CAST(:status AS text) IS NULL OR CAST(:status AS text) = '' OR CAST(:status AS text) = 'ALL') " +
        "    UNION ALL " +
        "    -- 2. DISPATCHED FABRIC STATUS ('DP') - RECEIVE ON RECEIVE_DATE, DISPATCH ON DISPATCH_DATE\n" +
        "    SELECT " +
        "        br.receive_date AS event_date, " +
        "        'RECEIVE', " +
        "        brd.fabric_master_id, TRIM(brd.construction), " +
        "        COALESCE(brd.greige_meters, 0), " +
        "        0, 0 " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND (brd.is_dispatched = 1 OR brd.brushing_status = 'DISPATCHED') " +
        "      AND br.company_id = :companyId " +
        "      AND CAST(:status AS text) = 'DP' " +
        "    UNION ALL " +
        "    SELECT " +
        "        COALESCE(brd.dispatch_date, br.receive_date) AS event_date, " +
        "        'DISPATCH', " +
        "        brd.fabric_master_id, TRIM(brd.construction), " +
        "        COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), brd.greige_meters, 0), " +
        "        0, 0 " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND (brd.is_dispatched = 1 OR brd.brushing_status = 'DISPATCHED') " +
        "      AND br.company_id = :companyId " +
        "      AND CAST(:status AS text) = 'DP' " +
        "    UNION ALL " +
        "    SELECT " +
         "        br.receive_date AS event_date, " +
        "        'VARIATION', " +
        "        brd.fabric_master_id, TRIM(brd.construction), " +
        "        0, " +
        "        CASE WHEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) > 0 " +
        "             THEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) ELSE 0 END, " +
        "        CASE WHEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) < 0 " +
        "             THEN ABS(COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0)) ELSE 0 END " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND (brd.is_dispatched = 1 OR brd.brushing_status = 'DISPATCHED') " +
        "      AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 " +
        "      AND br.company_id = :companyId " +
        "      AND CAST(:status AS text) = 'DP' " +
        "    UNION ALL " +
        "    -- 3. READY FOR DISPATCH / IN-STOCK STATUS ('RD') - RECEIVE ON RECEIVE_DATE, VARIATION ON RECEIVE_DATE\n" +
        "    SELECT " +
        "        br.receive_date AS event_date, " +
        "        'RECEIVE', " +
        "        brd.fabric_master_id, TRIM(brd.construction), " +
        "        COALESCE(brd.greige_meters, 0), " +
        "        0, 0 " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND brd.is_brushed = 1 AND COALESCE(brd.is_dispatched, 0) = 0 " +
        "      AND br.company_id = :companyId " +
        "      AND CAST(:status AS text) = 'RD' " +
        "    UNION ALL " +
        "    SELECT " +
        "        br.receive_date AS event_date, " +
        "        'VARIATION', " +
        "        brd.fabric_master_id, TRIM(brd.construction), " +
        "        0, " +
        "        CASE WHEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) > 0 " +
        "             THEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) ELSE 0 END, " +
        "        CASE WHEN COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0) < 0 " +
        "             THEN ABS(COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) - COALESCE(brd.greige_meters, 0)) ELSE 0 END " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND brd.is_brushed = 1 AND COALESCE(brd.is_dispatched, 0) = 0 " +
        "      AND COALESCE(NULLIF(brd.pass_mtr, 0), NULLIF(brd.brushed_meters, 0), 0) > 0 " +
        "      AND br.company_id = :companyId " +
        "      AND CAST(:status AS text) = 'RD' " +
        "    UNION ALL " +
        "    -- 4. UNDER BRUSHING / PENDING STATUS ('UB') - DATED BY RECEIVE DATE\n" +
        "    SELECT " +
        "        br.receive_date AS event_date, " +
        "        'RECEIVE', " +
        "        brd.fabric_master_id, TRIM(brd.construction), " +
        "        COALESCE(brd.greige_meters, 0), " +
        "        0, 0 " +
        "    FROM masters.brushing_receive br " +
        "    INNER JOIN masters.brushing_receive_detail brd ON brd.brushing_receive_id = br.id " +
        "    WHERE br.active_flag = true AND brd.active_flag = true " +
        "      AND COALESCE(brd.is_brushed, 0) = 0 AND COALESCE(brd.is_dispatched, 0) = 0 " +
        "      AND br.company_id = :companyId " +
        "      AND CAST(:status AS text) = 'UB' " +
        ") all_events " +
        "WHERE event_date IS NOT NULL " +
        "GROUP BY CAST(DATE_TRUNC('day', event_date) AS DATE), fabric_master_id, TRIM(construction) " +
        "ORDER BY period_date ASC",
        nativeQuery = true)
    List<Object[]> getBrushingDailyMovements(
        @org.springframework.data.repository.query.Param("companyId") Long companyId,
        @org.springframework.data.repository.query.Param("status") String status
    );
}
