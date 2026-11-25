package com.tech3.erp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.FinishYarnReceiveItem;

@Repository
public interface FinishYarnReceiveItemRepository extends JpaRepository<FinishYarnReceiveItem, Long> {

    // CORRECTED: This MUST reference finishFabricReceive for combined approach
    @Modifying
    @Query("DELETE FROM FinishYarnReceiveItem y WHERE y.finishFabricReceive.id = :finishFabricReceiveId")
    void deleteByFinishFabricReceiveId(@Param("finishFabricReceiveId") Long finishFabricReceiveId);

    List<FinishYarnReceiveItem> findByFinishFabricReceiveId(Long finishFabricReceiveId);
}
