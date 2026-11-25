package com.tech3.erp.repository;

import com.tech3.erp.entity.DyeingWorkOrderYarnItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DyeingWorkOrderYarnItemRepository extends JpaRepository<DyeingWorkOrderYarnItem, Long> {
    
    @Modifying
    @Query("DELETE FROM DyeingWorkOrderYarnItem y WHERE y.dyeingWorkOrder.id = :dyeingWorkOrderId")
    void deleteByDyeingWorkOrderId(@Param("dyeingWorkOrderId") Long dyeingWorkOrderId);
}