package com.tech3.erp.repository;

import com.tech3.erp.entity.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortRepository extends JpaRepository<Port, Long>, JpaSpecificationExecutor<Port> {
    Optional<Port> findByPortNameAndCountryId(String portName, Long countryId);
}
