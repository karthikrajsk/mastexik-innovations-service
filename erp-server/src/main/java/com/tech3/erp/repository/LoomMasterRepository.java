package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tech3.erp.entity.LoomMaster;

import java.util.Optional;

public interface LoomMasterRepository extends JpaRepository<LoomMaster, Long> {
    java.util.List<LoomMaster> findByCompanyIdOrderByIdDesc(Long companyId);
    java.util.Optional<LoomMaster> findByIdAndCompanyId(Long id, Long companyId);
    Optional<LoomMaster> findByCompanyIdAndLoomNo(Long companyId, String loomNo);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE LoomMaster l SET l.isBeamGating = 0 WHERE l.id = :id")
    int resetBeamGatingById(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE LoomMaster l SET l.isBeamGating = 1 WHERE l.id = :id")
    int setBeamGatingById(@Param("id") Long id);
}
