package com.tech3.erp.repository;

import com.tech3.erp.entity.Vendor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("select v.vendorName from Vendor v")
    List<String> findAllVendorNames();

    List<Vendor> findByVendorTypeId(String vendorTypeId);
}
