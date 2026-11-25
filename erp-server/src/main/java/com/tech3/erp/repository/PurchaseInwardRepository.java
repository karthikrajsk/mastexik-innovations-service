// package com.tech3.erp.repository;

// import com.tech3.erp.entity.PurchaseInward;
// import com.tech3.erp.entity.YarnMaster;

// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
// public interface PurchaseInwardRepository extends JpaRepository<PurchaseInward, Long> {
// }
package com.tech3.erp.repository;

import com.tech3.erp.entity.PurchaseInward;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseInwardRepository extends JpaRepository<PurchaseInward, Long> {
    List<PurchaseInward> findByCompanyId(Long companyId, Sort sort);
    Optional<PurchaseInward> findByIdAndCompanyId(Long id, Long companyId);
    List<PurchaseInward> findByPoId(Long poId);
    List<PurchaseInward> findByPoIdAndActiveFlag(Long poId, Boolean activeFlag);
    Optional<PurchaseInward> findFirstByInwardNo(String inwardNo);
}

