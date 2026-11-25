// package com.tech3.erp.repository;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.domain.Sort;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import com.tech3.erp.entity.JobworkFabricReceive;

// @Repository
// public interface JobworkFabricReceiveRepository extends JpaRepository<JobworkFabricReceive, Long> {
//     List<JobworkFabricReceive> findByCompanyId(Long companyId, Sort sort);
//     Optional<JobworkFabricReceive> findByIdAndCompanyId(Long id, Long companyId);
//     void deleteByIdAndCompanyId(Long id, Long companyId);
// }

package com.tech3.erp.repository;

import com.tech3.erp.entity.JobworkFabricReceive;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobworkFabricReceiveRepository extends JpaRepository<JobworkFabricReceive, Long> {
    boolean existsByWeavingContractId(Long weavingContractId);

    List<JobworkFabricReceive> findByCompanyId(Long companyId, Sort sort);

    Optional<JobworkFabricReceive> findByIdAndCompanyId(Long id, Long companyId);
}

