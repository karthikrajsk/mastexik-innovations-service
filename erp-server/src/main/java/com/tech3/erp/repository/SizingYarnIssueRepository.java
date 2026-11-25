package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.SizingYarnIssue;
import java.util.Optional;

@Repository
public interface SizingYarnIssueRepository extends JpaRepository<SizingYarnIssue, Long> {
    boolean existsByOrderItemId(Long orderItemId);
    boolean existsByInwardId(Long inwardId);
    Optional<SizingYarnIssue> findFirstByOrderItemId(Long orderItemId);
    Optional<SizingYarnIssue> findFirstByInwardId(Long inwardId);
    
    boolean existsByInwardIdAndYarnName(Long inwardId, String yarnName);
    
    @Query("SELECT COUNT(syi) FROM SizingYarnIssue syi WHERE syi.inwardId = :inwardId AND syi.yarnName = :yarnName AND (syi.activeFlag = true OR syi.activeFlag IS NULL)")
    Long countActiveByInwardIdAndYarnName(@Param("inwardId") Long inwardId, @Param("yarnName") String yarnName);
}
