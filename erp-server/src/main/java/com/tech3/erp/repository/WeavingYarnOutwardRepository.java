package com.tech3.erp.repository;

import com.tech3.erp.entity.WeavingYarnOutward;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeavingYarnOutwardRepository extends JpaRepository<WeavingYarnOutward, Long> {

    boolean existsByWeavingContractId(Long weavingContractId);

    List<WeavingYarnOutward> findByFactoryCompanyIdOrderByIdDesc(Long factoryCompanyId);

    Page<WeavingYarnOutward> findByFactoryCompanyId(Long factoryCompanyId, Pageable pageable);

    Optional<WeavingYarnOutward> findByIdAndFactoryCompanyId(Long id, Long factoryCompanyId);
}
