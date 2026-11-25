package com.tech3.erp.repository;

import com.tech3.erp.entity.DyeingWorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DyeingWorkOrderRepository extends JpaRepository<DyeingWorkOrder, Long> {
    // boolean existsBySalesOrderId(Long salesOrderId);
    boolean existsBySalesOrderNo(Long salesOrderNo);
    List<DyeingWorkOrder> findByCompanyId(Long companyId);
    Optional<DyeingWorkOrder> findByIdAndCompanyId(Long id, Long companyId);
    List<DyeingWorkOrder> findByCompanyIdAndProcessType(Long companyId, String processType);
    Optional<DyeingWorkOrder> findByIdAndCompanyIdAndProcessType(Long id, Long companyId, String processType);
}
