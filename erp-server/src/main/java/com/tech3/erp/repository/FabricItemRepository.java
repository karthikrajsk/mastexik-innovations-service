package com.tech3.erp.repository;

import com.tech3.erp.entity.FabricItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FabricItemRepository extends JpaRepository<FabricItemEntity, Long> {

    // Find all Fabric Items by OpeningBalance ID
    List<FabricItemEntity> findByOpeningBalanceId(Long openingBalanceId);

}
