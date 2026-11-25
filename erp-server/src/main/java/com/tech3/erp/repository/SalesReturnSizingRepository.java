package com.tech3.erp.repository;

import com.tech3.erp.entity.SalesReturnSizing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesReturnSizingRepository extends JpaRepository<SalesReturnSizing, Long> {
    List<SalesReturnSizing> findBySalesReturnId(Long salesReturnId);
    void deleteBySalesReturnId(Long salesReturnId);
}
