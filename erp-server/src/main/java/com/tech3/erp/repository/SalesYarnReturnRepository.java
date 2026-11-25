package com.tech3.erp.repository;

import com.tech3.erp.entity.SalesYarnReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SalesYarnReturnRepository extends JpaRepository<SalesYarnReturn, Long> {
    List<SalesYarnReturn> findBySalesReturnId(Long salesReturnId);
}
