package com.tech3.erp.repository;

import com.tech3.erp.entity.FlangeBeamInward;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlangeBeamInwardRepository extends JpaRepository<FlangeBeamInward, Long> {

    List<FlangeBeamInward> findAll(Sort sort);
    List<FlangeBeamInward> findByCompanyIdOrderByIdDesc(Long companyId);
    Optional<FlangeBeamInward> findByIdAndCompanyId(Long id, Long companyId);
    List<FlangeBeamInward> findByFactoryCompanyIdOrderByIdDesc(Long factoryCompanyId);
    Optional<FlangeBeamInward> findByIdAndFactoryCompanyId(Long id, Long factoryCompanyId);
}
