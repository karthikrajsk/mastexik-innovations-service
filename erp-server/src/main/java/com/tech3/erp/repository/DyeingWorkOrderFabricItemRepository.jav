package com.tech3.erp.repository;

import com.tech3.erp.entity.DyeingWorkOrderFabricItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DyeingWorkOrderFabricItemRepository extends JpaRepository<DyeingWorkOrderFabricItem, Long> {
    
    @Modifying
    @Query("DELETE FROM DyeingWorkOrderFabricItem f WHERE f.dyeingWorkOrder.id = :dyeingWorkOrderId")
    void deleteByDyeingWorkOrderId(@Param("dyeingWorkOrderId") Long dyeingWorkOrderId);
}