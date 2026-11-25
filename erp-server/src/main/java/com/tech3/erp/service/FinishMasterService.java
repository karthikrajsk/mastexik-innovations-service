package com.tech3.erp.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tech3.erp.dto.FinishMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.FabricCategory;
import com.tech3.erp.entity.FinishMaster;
import com.tech3.erp.repository.FinishMasterRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinishMasterService {

    private final FinishMasterRepository repository;

    public FinishMasterService(FinishMasterRepository repository) {
        this.repository = repository;
    }

    public FinishMasterDTO createFinishMaster(FinishMasterDTO dto) {
        String trimmedName = dto.getFinishName().trim();
        String trimmedCode = dto.getFinishCode().trim();
        
        boolean duplicateNameExists = repository.findAll().stream()
                .anyMatch(finish -> finish.getFinishName().trim().equalsIgnoreCase(trimmedName));
        
        boolean duplicateCodeExists = repository.findAll().stream()
                .anyMatch(finish -> finish.getFinishCode().trim().equalsIgnoreCase(trimmedCode));
        
        if (duplicateNameExists) {
            throw new IllegalArgumentException("Finish with name '" + trimmedName + "' already exists");
        }
        
        if (duplicateCodeExists) {
            throw new IllegalArgumentException("Finish with code '" + trimmedCode + "' already exists");
        }
        
        FinishMaster entity = new FinishMaster();
        entity.setFinishName(trimmedName);
        entity.setFinishCode(trimmedCode);
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
        return new FinishMasterDTO(repository.save(entity));
    }

    public FinishMasterDTO getFinishMasterById(Long id) {
        return repository.findById(id)
                .map(FinishMasterDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("FinishMaster not found"));
    }

    public List<FinishMasterDTO> getAllFinishMaster() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(FinishMaster ::getId, Comparator.reverseOrder()))
        		.map(FinishMasterDTO::new).collect(Collectors.toList());
    }

    public PaginatedResponse<FinishMasterDTO> getAllFinishMasterWithPagination(Long offset, Long limit) {
        List<FinishMasterDTO> all = getAllFinishMaster();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public FinishMasterDTO updateFinishMaster(Long id, FinishMasterDTO dto) {
    	FinishMaster entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FinishMaster not found"));
        
        String trimmedName = dto.getFinishName().trim();
        String trimmedCode = dto.getFinishCode().trim();
        
        boolean duplicateNameExists = repository.findAll().stream()
                .filter(finish -> !finish.getId().equals(id))
                .anyMatch(finish -> finish.getFinishName().trim().equalsIgnoreCase(trimmedName));
        
        boolean duplicateCodeExists = repository.findAll().stream()
                .filter(finish -> !finish.getId().equals(id))
                .anyMatch(finish -> finish.getFinishCode().trim().equalsIgnoreCase(trimmedCode));
        
        if (duplicateNameExists) {
            throw new IllegalArgumentException("Finish with name '" + trimmedName + "' already exists");
        }
        
        if (duplicateCodeExists) {
            throw new IllegalArgumentException("Finish with code '" + trimmedCode + "' already exists");
        }
        
        entity.setFinishName(trimmedName);
        entity.setFinishCode(trimmedCode);
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
        return new FinishMasterDTO(repository.save(entity));
    }

//    public void deleteFinishMaster(Long id) {
//        if (!repository.existsById(id)) {
//            throw new IllegalArgumentException("FinishMaster not found");
//        }
//        repository.deleteById(id);
//    }
    
    public void deleteFinishMaster(Long id) {
        // Check if category exists first
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Finish not found with id: " + id);
        }
        
        try {
        	repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Finish because it is referenced by other");
        }
    }
    
}
