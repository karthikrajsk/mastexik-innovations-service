package com.tech3.erp.repository;

import com.tech3.erp.entity.BeamGating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeamGatingRepository extends JpaRepository<BeamGating, Long> {
    java.util.List<BeamGating> findByCompanyId(Long companyId);
    java.util.Optional<BeamGating> findByIdAndCompanyId(Long id, Long companyId);
}
