package com.tech3.erp.service;

import com.tech3.erp.dto.CityDTO;
import com.tech3.erp.dto.StateDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.City;
import com.tech3.erp.entity.Country;
import com.tech3.erp.entity.State;
import com.tech3.erp.repository.CityRepository;
import com.tech3.erp.repository.StateRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public CityService(CityRepository cityRepository, StateRepository stateRepository) {
        this.cityRepository = cityRepository;
        this.stateRepository = stateRepository;
    }

    public CityDTO createCity(CityDTO cityDTO) {
        State state = stateRepository.findById(cityDTO.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("State not found with ID: " + cityDTO.getStateId()));

        String normalizedCityName = normalize(cityDTO.getCityName());
        if (normalizedCityName.isEmpty()) {
            throw new IllegalArgumentException("City name cannot be blank");
        }
        ensureUniqueCity(state, normalizedCityName, null);

        City city = new City();
        city.setState(state);
        city.setCityName(normalizedCityName);
        city.setActiveFlag(cityDTO.getActiveFlag());
        
        // Set country from the state
        city.setCountry(state.getCountry()); 

        return new CityDTO(cityRepository.save(city));
    }

    public CityDTO getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found with ID: " + id));
        return new CityDTO(city);
    }

    public List<CityDTO> getAllCities() {
        return cityRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(City::getId, Comparator.reverseOrder()))
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CityDTO> getAllCitiesWithPagination(Long offset, Long limit) {
        List<CityDTO> allCities = cityRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(City::getId, Comparator.reverseOrder()))
                .map(CityDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allCities, offset, limit);
    }
    
    public CityDTO updateCity(Long id, CityDTO cityDTO) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found with ID: " + id));

        State state = stateRepository.findById(cityDTO.getStateId())
                .orElseThrow(() -> new IllegalArgumentException("State not found with ID: " + cityDTO.getStateId()));

        String normalizedCityName = normalize(cityDTO.getCityName());
        if (normalizedCityName.isEmpty()) {
            throw new IllegalArgumentException("City name cannot be blank");
        }
        ensureUniqueCity(state, normalizedCityName, city.getId());

        city.setState(state);
        city.setCityName(normalizedCityName);
        city.setActiveFlag(cityDTO.getActiveFlag());

        return new CityDTO(cityRepository.save(city));
    }

//    public void deleteCity(Long id) {
//        if (!cityRepository.existsById(id)) {
//            throw new IllegalArgumentException("City not found with ID: " + id);
//        }
//        cityRepository.deleteById(id);
//    }
    public void deleteCity(Long id) {
        // Check if category exists first
        if (!cityRepository.existsById(id)) {
            throw new EntityNotFoundException("City not found with id: " + id);
        }
        
        try {
        	cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete City because it is referenced by other entities");
        }
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private void ensureUniqueCity(State state, String cityName, Long currentId) {
        Optional<City> existing = cityRepository.findByStateIdAndCountryIdAndCityNameIgnoreCase(
                state.getId(), state.getCountry().getId(), cityName);
        if (existing.isPresent() && (currentId == null || !existing.get().getId().equals(currentId))) {
            throw new IllegalArgumentException("City '" + cityName + "' already mapped with "
                    + state.getStateName() + " in country " + state.getCountry().getCountryName() + ".");
        }
    }
}
