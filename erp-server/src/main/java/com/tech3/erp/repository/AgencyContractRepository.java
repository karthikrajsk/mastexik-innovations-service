package com.tech3.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tech3.erp.entity.AgencyContract;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyContractRepository extends JpaRepository<AgencyContract, Long> {
    Optional<AgencyContract> findByContractNo(String contractNo);
    boolean existsByContractNo(String contractNo);
    boolean existsByContractNoAndIdNot(String contractNo, Long id);
    List<AgencyContract> findByCompanyId(Long companyId);
    List<AgencyContract> findByStatus(String status);
}
