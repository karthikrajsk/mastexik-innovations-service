package com.tech3.erp.service;

import com.tech3.erp.dto.TermsConditionsDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.ShipmentTerms;
import com.tech3.erp.entity.TermsConditions;
import com.tech3.erp.repository.TermsConditionsRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TermsConditionsService {

    private final TermsConditionsRepository repository;

    public TermsConditionsService(TermsConditionsRepository repository) {
        this.repository = repository;
    }

    public TermsConditionsDTO createTermsCondition(TermsConditionsDTO dto) {
        String trimmedName = dto.getTermsConditionsName().trim();
        
        boolean duplicateExists = repository.findAll().stream()
                .anyMatch(term -> term.getTermsConditionsName().trim().equalsIgnoreCase(trimmedName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Terms & Conditions with name '" + trimmedName + "' already exists");
        }
        
        TermsConditions entity = new TermsConditions();
        entity.setTermsConditionsName(trimmedName);
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
        return new TermsConditionsDTO(repository.save(entity));
    }

    public TermsConditionsDTO getTermsConditionById(Long id) {
        return repository.findById(id)
                .map(TermsConditionsDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Terms & Conditions not found"));
    }

    public List<TermsConditionsDTO> getAllTermsConditions() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(TermsConditions ::getId, Comparator.reverseOrder()))
        		.map(TermsConditionsDTO::new).collect(Collectors.toList());
    }

    public PaginatedResponse<TermsConditionsDTO> getAllTermsConditionsWithPagination(Long offset, Long limit) {
        List<TermsConditionsDTO> all = getAllTermsConditions();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public TermsConditionsDTO updateTermsCondition(Long id, TermsConditionsDTO dto) {
        TermsConditions entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Terms & Conditions not found"));
        
        String trimmedName = dto.getTermsConditionsName().trim();
        
        boolean duplicateExists = repository.findAll().stream()
                .filter(term -> !term.getId().equals(id))
                .anyMatch(term -> term.getTermsConditionsName().trim().equalsIgnoreCase(trimmedName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Terms & Conditions with name '" + trimmedName + "' already exists");
        }
        
        entity.setTermsConditionsName(trimmedName);
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
        return new TermsConditionsDTO(repository.save(entity));
    }

//    public void deleteTermsCondition(Long id) {
//        if (!repository.existsById(id)) {
//            throw new IllegalArgumentException("Terms & Conditions not found");
//        }
//        repository.deleteById(id);
//    }
    public void deleteTermsCondition(Long id) {
        // Check if category exists first
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Terms & Conditions not found with id: " + id);
        }
        
        try {
        	repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Terms & Conditions because it is referenced by other");
        }
    }
    
}

