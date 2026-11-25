package com.tech3.erp.repository;

import com.tech3.erp.entity.RequestForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestFormRepository extends JpaRepository<RequestForm, Long> {
    Optional<RequestForm> findByRequestNo(String requestNo);
    Page<RequestForm> findByActiveFlagTrue(Pageable pageable);
}
