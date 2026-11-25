package com.tech3.erp.repository;

import com.tech3.erp.entity.CodesHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CodesHeaderRepository extends JpaRepository<CodesHeader, Long> {
    Optional<CodesHeader> findByCodeGroup(String codeGroup);
    
    @Query("SELECT DISTINCT ch FROM CodesHeader ch LEFT JOIN FETCH ch.details")
    List<CodesHeader> findAllWithDetails();
}
