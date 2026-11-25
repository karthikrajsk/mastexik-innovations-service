package com.tech3.erp.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tech3.erp.dto.UomDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Consignee;
import com.tech3.erp.entity.Uom;
import com.tech3.erp.repository.UomRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UomService {

    private final UomRepository repository;

    public UomService(UomRepository repository) {
        this.repository = repository;
    }

    public UomDTO createUom(UomDTO dto) {
        String trimmedUomName = dto.getUomName().trim();
        String trimmedUomCode = dto.getUomCode().trim();
        
        boolean duplicateNameExists = repository.findAll().stream()
                .anyMatch(term -> term.getUomName().trim().equalsIgnoreCase(trimmedUomName));
        
        boolean duplicateCodeExists = repository.findAll().stream()
                .anyMatch(term -> term.getUomCode().trim().equalsIgnoreCase(trimmedUomCode));
        
        if (duplicateNameExists) {
            throw new IllegalArgumentException("UOM with name '" + trimmedUomName + "' already exists");
        }
        
        if (duplicateCodeExists) {
            throw new IllegalArgumentException("UOM with code '" + trimmedUomCode + "' already exists");
        }
        
        Uom entity = new Uom();
        entity.setUomName(trimmedUomName);
        entity.setUomCode(trimmedUomCode);
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
        return new UomDTO(repository.save(entity));
    }

    public UomDTO getUomById(Long id) {
        return repository.findById(id)
                .map(UomDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Uom not found"));
    }

    public List<UomDTO> getAllUom() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Uom ::getId, Comparator.reverseOrder()))
        		.map(UomDTO::new).collect(Collectors.toList());
    }

    public PaginatedResponse<UomDTO> getAllUomWithPagination(Long offset, Long limit) {
        List<UomDTO> all = getAllUom();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public UomDTO updateUom(Long id, UomDTO dto) {
    	Uom entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Uom not found"));
        
        String trimmedUomName = dto.getUomName().trim();
        String trimmedUomCode = dto.getUomCode().trim();
        
        boolean duplicateNameExists = repository.findAll().stream()
                .filter(term -> !term.getId().equals(id))
                .anyMatch(term -> term.getUomName().trim().equalsIgnoreCase(trimmedUomName));
        
        boolean duplicateCodeExists = repository.findAll().stream()
                .filter(term -> !term.getId().equals(id))
                .anyMatch(term -> term.getUomCode().trim().equalsIgnoreCase(trimmedUomCode));
        
        if (duplicateNameExists) {
            throw new IllegalArgumentException("UOM with name '" + trimmedUomName + "' already exists");
        }
        
        if (duplicateCodeExists) {
            throw new IllegalArgumentException("UOM with code '" + trimmedUomCode + "' already exists");
        }
        
        entity.setUomName(trimmedUomName);
        entity.setUomCode(trimmedUomCode);
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
        return new UomDTO(repository.save(entity));
    }

//    public void deleteUom(Long id) {
//        if (!repository.existsById(id)) {
//            throw new IllegalArgumentException("uom not found");
//        }
//        repository.deleteById(id);
//    }
    public void deleteUom(Long id) {
        // Check if category exists first
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Uom not found with id: " + id);
        }
        
        try {
        	repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Uom because it is referenced by other");
        }
    }
}
