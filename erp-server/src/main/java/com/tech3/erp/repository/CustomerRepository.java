package com.tech3.erp.repository;

import com.tech3.erp.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where (:tradeTypeId is null or c.tradeType.id = :tradeTypeId)")
    Page<Customer> findAllWithOptionalTradeType(@Param("tradeTypeId") Long tradeTypeId, Pageable pageable);

    @Query("select count(c) from Customer c where (:tradeTypeId is null or c.tradeType.id = :tradeTypeId)")
    long countWithOptionalTradeType(@Param("tradeTypeId") Long tradeTypeId);
}
