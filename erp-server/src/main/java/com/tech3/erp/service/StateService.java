package com.tech3.erp.service;

import com.tech3.erp.dto.StateDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Country;
import com.tech3.erp.entity.State;
import com.tech3.erp.repository.CountryRepository;
import com.tech3.erp.repository.StateRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StateService {

    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;

    public StateService(StateRepository stateRepository, CountryRepository countryRepository) {
        this.stateRepository = stateRepository;
        this.countryRepository = countryRepository;
    }

    public StateDTO createState(StateDTO stateDTO) {
        Country country = countryRepository.findById(stateDTO.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found with ID: " + stateDTO.getCountryId()));

        String normalizedName = normalizeValue(stateDTO.getStateName());
        String normalizedCode = normalizeValue(stateDTO.getStateCode());
        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("State name cannot be blank");
        }
        if (normalizedCode.isEmpty()) {
            throw new IllegalArgumentException("State code cannot be blank");
        }

        validateStateUniqueness(country.getId(), normalizedName, normalizedCode, null, country.getCountryName());

        State state = new State();
        state.setCountry(country);
        state.setStateName(normalizedName);
        state.setStateCode(normalizedCode);
        state.setActiveFlag(stateDTO.getActiveFlag());

        return new StateDTO(stateRepository.save(state));
    }

    public StateDTO getStateById(Long id) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("State not found with ID: " + id));
        return new StateDTO(state);
    }

    public List<StateDTO> getAllStates() {
        return stateRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(State::getId, Comparator.reverseOrder()))
                .map(StateDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<StateDTO> getAllStatesWithPagination(Long offset, Long limit) {
        List<StateDTO> all = getAllStates();
        return PaginationHelper.paginate(all, offset, limit);
    }

    
    public List<StateDTO> getStatesByCountryId(Long countryId) {
        return stateRepository.findByCountryId(countryId)
                .stream()
                .map(StateDTO::new)
                .collect(Collectors.toList());
    }

    public StateDTO updateState(Long id, StateDTO stateDTO) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("State not found with ID: " + id));

        Country country = countryRepository.findById(stateDTO.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found with ID: " + stateDTO.getCountryId()));

        String normalizedName = normalizeValue(stateDTO.getStateName());
        String normalizedCode = normalizeValue(stateDTO.getStateCode());
        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("State name cannot be blank");
        }
        if (normalizedCode.isEmpty()) {
            throw new IllegalArgumentException("State code cannot be blank");
        }

        validateStateUniqueness(country.getId(), normalizedName, normalizedCode, state.getId(), country.getCountryName());

        state.setCountry(country);
        state.setStateName(normalizedName);
        state.setStateCode(normalizedCode);
        state.setActiveFlag(stateDTO.getActiveFlag());

        return new StateDTO(stateRepository.save(state));
    }

//    public void deleteState(Long id) {
//        if (!stateRepository.existsById(id)) {
//            throw new IllegalArgumentException("State not found with ID: " + id);
//        }
//        stateRepository.deleteById(id);
//    }
    public void deleteState(Long id) {
        // Check if category exists first
        if (!stateRepository.existsById(id)) {
            throw new EntityNotFoundException("State not found with id: " + id);
        }
        
        try {
        	stateRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete State because it is referenced by other");
        }
    }
    
    private String normalizeValue(String value) {
        return value == null ? "" : value.trim();
    }

    private void validateStateUniqueness(Long countryId, String stateName, String stateCode, Long currentId, String countryName) {
        List<String> validationErrors = new ArrayList<>();
        Optional<State> globalStateNameConflict = stateRepository.findByStateNameIgnoreCase(stateName);
        if (globalStateNameConflict.isPresent() && (currentId == null || !globalStateNameConflict.get().getId().equals(currentId))) {
            validationErrors.add("State name '" + stateName + "' already exists.");
        }

        Optional<State> globalStateCodeConflict = stateRepository.findByStateCodeIgnoreCase(stateCode);
        if (globalStateCodeConflict.isPresent() && (currentId == null || !globalStateCodeConflict.get().getId().equals(currentId))) {
            validationErrors.add("State code '" + stateCode + "' already exists.");
        }

        List<State> statesInCountry = stateRepository.findByCountryId(countryId);

        for (State existing : statesInCountry) {
            if (currentId != null && existing.getId().equals(currentId)) {
                continue;
            }
            if (existing.getStateName() != null && existing.getStateName().equalsIgnoreCase(stateName)) {
                validationErrors.add("Duplicate stateName error: State name '" + stateName + "' already mapped with country '" + countryName + "'.");
            }
            if (existing.getStateCode() != null && existing.getStateCode().equalsIgnoreCase(stateCode)) {
                validationErrors.add("Duplicate stateCode error: State code '" + stateCode + "' already mapped with country '" + countryName + "'.");
            }
        }

        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException(String.join(" ", validationErrors));
        }
    }
}
