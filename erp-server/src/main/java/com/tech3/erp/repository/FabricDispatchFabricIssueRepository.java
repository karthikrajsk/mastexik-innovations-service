package com.tech3.erp.repository;

import com.tech3.erp.entity.FabricDispatchFabricIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FabricDispatchFabricIssueRepository extends JpaRepository<FabricDispatchFabricIssue, Long> {
    
    @Modifying
    @Query("DELETE FROM FabricDispatchFabricIssue f WHERE f.fabricDispatch.id = :fabricDispatchId")
    void deleteByFabricDispatchId(@Param("fabricDispatchId") Long fabricDispatchId);
}