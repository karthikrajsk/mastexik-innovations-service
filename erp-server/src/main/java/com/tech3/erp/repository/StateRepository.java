package com.tech3.erp.repository;

import com.tech3.erp.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    List<State> findByCountryId(Long countryId);

    Optional<State> findByCountryIdAndStateNameIgnoreCase(Long countryId, String stateName);

    Optional<State> findByCountryIdAndStateCodeIgnoreCase(Long countryId, String stateCode);

    Optional<State> findByStateNameIgnoreCase(String stateName);

    Optional<State> findByStateCodeIgnoreCase(String stateCode);
}
