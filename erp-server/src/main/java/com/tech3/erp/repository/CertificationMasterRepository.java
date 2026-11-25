package com.tech3.erp.repository;

import com.tech3.erp.entity.CertificationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificationMasterRepository extends JpaRepository<CertificationMaster, Long> {
    Optional<CertificationMaster> findByCertificationNo(String certificationNo);
    boolean existsByCertificationNo(String certificationNo);
}
