package com.tech3.erp.repository;

import java.util.List;

import com.tech3.erp.entity.YarnSales;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YarnSalesRepository extends JpaRepository<YarnSales, Long> {
    List<YarnSales> findByCompanyId(Long companyId, Sort sort);
}
