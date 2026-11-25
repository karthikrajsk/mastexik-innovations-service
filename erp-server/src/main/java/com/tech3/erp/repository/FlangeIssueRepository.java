package com.tech3.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech3.erp.entity.FlangeIssue;

@Repository
public interface FlangeIssueRepository extends JpaRepository<FlangeIssue, Long> {
    List<FlangeIssue> findByCompanyId(Long companyId, Sort sort);

    @EntityGraph(attributePaths = {"items"})
    Optional<FlangeIssue> findWithItemsById(Long id);

    @Query("SELECT fi FROM FlangeIssue fi JOIN fi.items item WHERE item.flangeId = :flangeId AND fi.companyId = :companyId")
    Optional<FlangeIssue> findByItemsFlangeIdAndCompanyId(@Param("flangeId") Long flangeId, @Param("companyId") Long companyId);
}
