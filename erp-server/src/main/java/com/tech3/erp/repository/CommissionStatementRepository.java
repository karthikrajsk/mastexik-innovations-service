package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tech3.erp.entity.CommissionStatement;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommissionStatementRepository extends JpaRepository<CommissionStatement, Long> {
    Optional<CommissionStatement> findByStatementNo(String statementNo);
    List<CommissionStatement> findBySeller(String seller);
    List<CommissionStatement> findByStatus(String status);
    List<CommissionStatement> findByCompanyId(Long companyId);
}
