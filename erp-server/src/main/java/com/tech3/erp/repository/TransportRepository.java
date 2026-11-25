package com.tech3.erp.repository;

import com.tech3.erp.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, Long>, JpaSpecificationExecutor<Transport> {
    Optional<Transport> findByTransportName(String transportName);
    Optional<Transport> findByGstNo(String gstNo);
}
