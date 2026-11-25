package com.tech3.erp.repository;

import com.tech3.erp.entity.OpeningBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface OpeningBalanceRepository extends JpaRepository<OpeningBalanceEntity, Long> {
    
    @Query("SELECT DISTINCT ob FROM OpeningBalanceEntity ob LEFT JOIN FETCH ob.warehouse LEFT JOIN FETCH ob.fabricItems WHERE ob.id = :id")
    Optional<OpeningBalanceEntity> findByIdWithRelations(@Param("id") Long id);

    List<OpeningBalanceEntity> findAllByCompanyId(Long companyId);
}
