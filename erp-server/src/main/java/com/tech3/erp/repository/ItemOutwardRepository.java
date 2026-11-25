package com.tech3.erp.repository;

import com.tech3.erp.entity.ItemOutward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemOutwardRepository extends JpaRepository<ItemOutward, Long> {
    
    Optional<ItemOutward> findByOutwardNo(String outwardNo);
    
    List<ItemOutward> findByOutwardNoStartingWith(String prefix);
    
    @Query("SELECT DISTINCT io.machineName FROM ItemOutward io WHERE io.machineName IS NOT NULL AND io.machineName != ''")
    List<String> findDistinctMachineNames();
    
    @Query("SELECT io FROM ItemOutward io WHERE io.companyId = :companyId ORDER BY io.id DESC")
    List<ItemOutward> findByCompanyIdOrderByIdDesc(@Param("companyId") Long companyId);
    
    Optional<ItemOutward> findByIdAndCompanyId(Long id, Long companyId);
}

