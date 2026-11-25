package com.tech3.erp.repository;

import com.tech3.erp.entity.SalesOrder;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
	
	@Query("SELECT COALESCE(MAX(CAST(SUBSTRING(so.salesOrderNo, LENGTH(:prefix) + 1) AS integer)), 0) " +
		       "FROM SalesOrder so " +
		       "WHERE so.companyId = :companyId AND so.salesOrderNo LIKE CONCAT(:prefix, '%')")
		Integer findMaxOrderNumberByPrefix(@Param("prefix") String prefix, @Param("companyId") Long companyId);
	

	List<SalesOrder> findByBuyerCustomerId(Long buyerCustomerId);
	
	List<SalesOrder> findByCompanyId(Long companyId);
	
	java.util.Optional<SalesOrder> findByIdAndCompanyId(Long id, Long companyId);
	
	void deleteByIdAndCompanyId(Long id, Long companyId);
	
	@Query("SELECT so.id FROM SalesOrder so ORDER BY so.id DESC")
	List<Long> findAllIds();
	
	@EntityGraph(attributePaths = "items")
	@Query("SELECT so FROM SalesOrder so WHERE so.id IN :ids ORDER BY so.id DESC")
	List<SalesOrder> findByIdsWithItems(@Param("ids") List<Long> ids);
	
	@Query("SELECT so.id FROM SalesOrder so WHERE so.companyId = :companyId ORDER BY so.id DESC")
	List<Long> findIdsByCompanyId(@Param("companyId") Long companyId);
	
	@EntityGraph(attributePaths = "items")
	@Query("SELECT so FROM SalesOrder so WHERE so.companyId = :companyId AND so.id IN :ids ORDER BY so.id DESC")
	List<SalesOrder> findByCompanyIdAndIds(@Param("companyId") Long companyId, @Param("ids") List<Long> ids);
	
	java.util.Optional<SalesOrder> findBySalesOrderNo(String salesOrderNo);
	
	@Query("SELECT so.id FROM SalesOrder so WHERE LOWER(so.department) = LOWER(:department) ORDER BY so.id DESC")
	List<Long> findIdsByDepartment(@Param("department") String department);

	@Query("SELECT so.id FROM SalesOrder so WHERE so.companyId = :companyId AND LOWER(so.department) = LOWER(:department) ORDER BY so.id DESC")
	List<Long> findIdsByCompanyIdAndDepartment(@Param("companyId") Long companyId, @Param("department") String department);
	
	@Query("SELECT so FROM SalesOrder so WHERE so.companyId = :companyId AND LOWER(so.department) = LOWER(:department) ORDER BY so.id DESC")
	List<SalesOrder> findByCompanyIdAndDepartment(@Param("companyId") Long companyId, @Param("department") String department);
	

	

	
	
	
	

	
	
	
	
	

}
