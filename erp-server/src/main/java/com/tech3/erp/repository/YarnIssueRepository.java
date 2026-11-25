package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.YarnIssue;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface YarnIssueRepository extends JpaRepository<YarnIssue, Long> {
    List<YarnIssue> findByOrderItemId(Long orderItemId);
    boolean existsByOrderItemId(Long orderItemId);
    boolean existsByInwardId(Long inwardId);
    Optional<YarnIssue> findFirstByOrderItemId(Long orderItemId);
    Optional<YarnIssue> findFirstByInwardId(Long inwardId);

    @Query("SELECT COALESCE(SUM(yi.issueQty), 0) FROM YarnIssue yi " +
           "JOIN yi.weavingYarnRequirement wyr " +
           "JOIN wyr.weavingYarnIssue wyi " +
           "WHERE wyi.weavingContractId = :contractId " +
           "AND (yi.actualYarnId = :itemId OR yi.yarnId = :itemId) " +
           "AND wyi.vendorId = :vendorId")
    BigDecimal getTotalIssuedQtyByContractItemAndVendor(@Param("contractId") Long contractId, 
                                                       @Param("itemId") Long itemId, 
                                                       @Param("vendorId") Long vendorId);

    boolean existsByInwardIdAndYarnName(Long inwardId, String yarnName);
    
    @Query("SELECT COUNT(yi) FROM YarnIssue yi WHERE yi.inwardId = :inwardId AND yi.yarnName = :yarnName AND (yi.activeFlag = true OR yi.activeFlag IS NULL)")
    Long countActiveByInwardIdAndYarnName(@Param("inwardId") Long inwardId, @Param("yarnName") String yarnName);
}
