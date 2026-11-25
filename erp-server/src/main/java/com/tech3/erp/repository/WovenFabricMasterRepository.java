package com.tech3.erp.repository;

import com.tech3.erp.entity.WovenFabricMaster;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WovenFabricMasterRepository extends JpaRepository<WovenFabricMaster, Long> {

    @Query(value = "SELECT masters.get_fabric_details_by_fabric_type_id(:fabricId)", nativeQuery = true)
    String getFabricDetailsById(@Param("fabricId") Long fabricId);

    Optional<WovenFabricMaster> findFirstByFabricCode(String fabricCode);
    
//    List<WovenFabricMaster> findByFabricDetailsFabricTypeId(Short fabricTypeId);    
}