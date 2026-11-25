package com.tech3.erp.repository;

import com.tech3.erp.entity.WeavingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeavingContractRepository extends JpaRepository<WeavingContract, Long> {
	boolean existsBySalesOrderNo(Long salesOrderNo);
	
	@Query("SELECT COALESCE(MAX(CAST(SUBSTRING(w.weavingContractNo, LENGTH(:prefix) + 1) AS INTEGER)), 0) " +
	           "FROM WeavingContract w WHERE w.companyId = :companyId AND w.weavingContractNo LIKE CONCAT(:prefix, '%')")
	
	Integer findMaxOrderNumberByPrefix(@Param("prefix") String prefix, @Param("companyId") Long companyId);

	List<WeavingContract> findByCompanyIdOrderByIdDesc(Long companyId);

	Optional<WeavingContract> findByIdAndCompanyId(Long id, Long companyId);

	Optional<WeavingContract> findByWeavingContractNo(String weavingContractNo);

	boolean existsByIdAndCompanyId(Long id, Long companyId);

	void deleteByIdAndCompanyId(Long id, Long companyId);
}
