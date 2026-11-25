package com.tech3.erp.repository;

import com.tech3.erp.entity.YarnReturn;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YarnReturnRepository extends JpaRepository<YarnReturn, Long> {
    
    Optional<YarnReturn> findByInwardNumber(String inwardNumber);
    
    List<YarnReturn> findByCompanyId(Long companyId, Sort sort);
    
    Optional<YarnReturn> findByIdAndCompanyId(Long id, Long companyId);
    
    @Query("SELECT COUNT(yr) > 0 FROM YarnReturn yr WHERE yr.inwardNumber = :inwardNumber AND yr.id != :id")
    boolean existsByInwardNumberAndIdNot(@Param("inwardNumber") String inwardNumber, @Param("id") Long id);

    boolean existsByOrderNo(String orderNo);
}
