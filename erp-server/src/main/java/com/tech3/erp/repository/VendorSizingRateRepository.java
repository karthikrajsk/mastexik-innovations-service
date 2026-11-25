package com.tech3.erp.repository;

import com.tech3.erp.entity.VendorSizingRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorSizingRateRepository extends JpaRepository<VendorSizingRate, Long> {

    List<VendorSizingRate> findByVendorIdAndActiveFlagTrueOrderByYarnCountAsc(Long vendorId);

    List<VendorSizingRate> findByVendorIdOrderByCreatedAtDesc(Long vendorId);

    List<VendorSizingRate> findByVendorIdAndYarnCountOrderByCreatedAtDesc(Long vendorId, String yarnCount);

    Optional<VendorSizingRate> findByVendorIdAndYarnCountAndActiveFlagTrue(Long vendorId, String yarnCount);

    List<VendorSizingRate> findByVendorIdAndYarnCount(Long vendorId, String yarnCount);
}
