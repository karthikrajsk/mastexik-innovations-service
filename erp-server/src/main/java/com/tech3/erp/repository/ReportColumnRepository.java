package com.tech3.erp.repository;

import com.tech3.erp.entity.ReportColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportColumnRepository extends JpaRepository<ReportColumn, Long> {
}