package com.tech3.erp.repository;

import com.tech3.erp.entity.SalesReturn;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesReturnRepository extends JpaRepository<SalesReturn, Long> {
    
    Optional<SalesReturn> findByIdAndCompanyId(Long id, Long companyId);
    
    void deleteByIdAndCompanyId(Long id, Long companyId);
    
    @Query("SELECT sr.id FROM SalesReturn sr WHERE sr.companyId = :companyId ORDER BY sr.id DESC")
    List<Long> findIdsByCompanyId(@Param("companyId") Long companyId);
    
    @EntityGraph(attributePaths = "items")
    @Query("SELECT sr FROM SalesReturn sr WHERE sr.companyId = :companyId AND sr.id IN :ids ORDER BY sr.id DESC")
    List<SalesReturn> findByCompanyIdAndIds(@Param("companyId") Long companyId, @Param("ids") List<Long> ids);
}
