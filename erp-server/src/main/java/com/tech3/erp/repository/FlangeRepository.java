package com.tech3.erp.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tech3.erp.entity.Flange;

import java.util.Optional;
import java.util.List;

@Repository
public interface FlangeRepository extends JpaRepository<Flange, Long> {
    
    List<Flange> findByCompanyId(Long companyId, Sort sort);
    Optional<Flange> findByFlangeNo(String flangeNo);
    
    List<Flange> findByIssuedCompanyIdAndIsIssued(Long issuedCompanyId, Integer isIssued);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Flange f SET f.isIssued = 1, f.isSizingReceive = 1, f.issuedCompanyId = :issuedCompanyId, f.issuedToInternal = :issuedToInternal WHERE f.id IN :ids")
    int markIssuedByIds(@Param("ids") List<Long> ids, @Param("issuedCompanyId") Long issuedCompanyId, @Param("issuedToInternal") boolean issuedToInternal);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Flange f SET f.isInwarded = 1 WHERE f.id IN :ids")
    int markInwardedByIds(@Param("ids") List<Long> ids);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Flange f SET f.isBeamGating = 1 WHERE f.id IN :ids AND (f.isBeamGating IS NULL OR f.isBeamGating = 0)")
    int markBeamGatingByIds(@Param("ids") List<Long> ids);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Flange f SET f.isBeamGating = 0 WHERE f.id = :id")
    int resetBeamGatingById(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Flange f SET f.isIssued = 0, f.isSizingReceive = 0, f.isBeamGating = 0, f.isInwarded = 0, f.issuedCompanyId = null, f.issuedToInternal = false WHERE f.id = :id")
    int resetFlagsById(@Param("id") Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Flange f SET f.isIssued = 0, f.isSizingReceive = 0, f.issuedCompanyId = null, f.issuedToInternal = false WHERE f.id IN :ids")
    int unmarkIssuedByIds(@Param("ids") List<Long> ids);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Flange f SET f.isInwarded = 0 WHERE f.id IN :ids")
    int unmarkInwardedByIds(@Param("ids") List<Long> ids);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Flange f SET f.isBeamGating = 0 WHERE f.id IN :ids")
    int unmarkBeamGatingByIds(@Param("ids") List<Long> ids);
}
