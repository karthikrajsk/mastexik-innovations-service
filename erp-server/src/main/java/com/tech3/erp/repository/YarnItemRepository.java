package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface YarnItemRepository extends JpaRepository<YarnItemEntity, Long> {

    List<YarnItemEntity> findByOpeningBalanceId(Long openingBalanceId);

    List<YarnItemEntity> findByYarnId(Long yarnId);

    // Custom query to get max sNo for a given openingBalanceId
    @Query("SELECT MAX(y.sNo) FROM YarnItemEntity y WHERE y.openingBalanceId = :openingBalanceId")
    Integer findMaxSNoByOpeningBalanceId(@Param("openingBalanceId") Long openingBalanceId);
}
