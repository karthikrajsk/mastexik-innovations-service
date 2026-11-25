package com.tech3.erp.repository;

import com.tech3.erp.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByStateId(Long stateId);

    Optional<City> findByStateIdAndCountryIdAndCityNameIgnoreCase(Long stateId, Long countryId, String cityName);
}
