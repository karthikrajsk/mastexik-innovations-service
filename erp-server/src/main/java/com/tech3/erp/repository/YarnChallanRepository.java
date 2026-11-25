package com.tech3.erp.repository;

import java.util.List;

import com.tech3.erp.entity.YarnChallan;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface YarnChallanRepository extends JpaRepository<YarnChallan, Long> {
    List<YarnChallan> findByCompanyId(Long companyId, Sort sort);

    @Query("SELECT y FROM YarnChallan y WHERE y.challanNo = :challanNo")
    YarnChallan findByChallanNo(@Param("challanNo") String challanNo);

    boolean existsByYarnSalesOrderId(Long yarnSalesOrderId);

    boolean existsByYarnSalesOrderIdAndActiveFlagTrue(Long yarnSalesOrderId);

    @Transactional
    @Modifying
    @Query("UPDATE YarnChallan y SET y.invoiceId = :invoiceId, y.isInvoice = 1 WHERE y.id IN :yarnChallanIds")
    void markInvoiced(@Param("yarnChallanIds") List<Long> yarnChallanIds, @Param("invoiceId") Long invoiceId);

    @Transactional
    @Modifying
    @Query("UPDATE YarnChallan y SET y.invoiceId = NULL, y.isInvoice = 0 WHERE y.invoiceId = :invoiceId")
    void resetInvoiced(@Param("invoiceId") Long invoiceId);

    List<YarnChallan> findByInvoiceId(Long invoiceId);
    List<YarnChallan> findByYarnSalesOrderId(Long yarnSalesOrderId);
}
