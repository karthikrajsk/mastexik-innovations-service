package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.entity.SizingYarnIssueEntry;

@Repository
public interface SizingYarnIssueEntryRepository extends JpaRepository<SizingYarnIssueEntry, Long> {

    List<SizingYarnIssueEntry> findByCompanyIdOrderByIdDesc(Long companyId);

    Optional<SizingYarnIssueEntry> findByIdAndCompanyId(Long id, Long companyId);

    boolean existsBySizingPlanId(Long sizingPlanId);

    List<SizingYarnIssueEntry> findBySizingPlanId(Long sizingPlanId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SizingYarnIssueEntry e WHERE e.sizingPlanId = :sizingPlanId")
    void deleteBySizingPlanId(@Param("sizingPlanId") Long sizingPlanId);


    void deleteByIdAndCompanyId(Long id, Long companyId);


    @Query("SELECT entry.id FROM SizingYarnIssueEntry entry ORDER BY entry.id DESC")
    List<Long> findAllIds();

    @Query("SELECT entry.id FROM SizingYarnIssueEntry entry WHERE entry.companyId = :companyId ORDER BY entry.id DESC")
    List<Long> findIdsByCompanyId(@Param("companyId") Long companyId);

    @Query("SELECT entry FROM SizingYarnIssueEntry entry WHERE entry.id IN :ids ORDER BY entry.id DESC")
    List<SizingYarnIssueEntry> findByIdsWithDetails(@Param("ids") List<Long> ids);

    @Query("SELECT entry FROM SizingYarnIssueEntry entry WHERE entry.companyId = :companyId AND entry.id IN :ids ORDER BY entry.id DESC")
    List<SizingYarnIssueEntry> findByCompanyIdAndIds(@Param("companyId") Long companyId, @Param("ids") List<Long> ids);
}
