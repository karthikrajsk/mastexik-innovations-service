package com.tech3.erp.service;

import com.tech3.erp.dto.FabricTypeDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.FabricType;
import com.tech3.erp.entity.SubCategory;
import com.tech3.erp.repository.FabricTypeRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FabricTypeService {

    private final FabricTypeRepository fabricTypeRepository;

    public FabricTypeService(FabricTypeRepository fabricTypeRepository) {
        this.fabricTypeRepository = fabricTypeRepository;
    }

    public FabricTypeDTO createFabricType(FabricTypeDTO fabricTypeDTO) {
        String trimmedName = fabricTypeDTO.getFabricTypeName().trim();
        
        boolean duplicateExists = fabricTypeRepository.findAll().stream()
                .anyMatch(fabric -> fabric.getFabricTypeName().trim().equalsIgnoreCase(trimmedName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Fabric type with name '" + trimmedName + "' already exists");
        }
        
        FabricType fabricType = new FabricType();
        fabricType.setFabricTypeName(trimmedName);
        fabricType.setActiveFlag(fabricTypeDTO.getActiveFlag());

        return new FabricTypeDTO(fabricTypeRepository.save(fabricType));
    }

    public FabricTypeDTO getFabricTypeById(Long id) {
        FabricType fabricType = fabricTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fabric type not found"));
        return new FabricTypeDTO(fabricType);
    }

    public List<FabricTypeDTO> getAllFabricTypes() {
        return fabricTypeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(FabricType ::getId, Comparator.reverseOrder()))
                .map(FabricTypeDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FabricTypeDTO> getAllFabricTypesWithPagination(Long offset, Long limit) {
        List<FabricTypeDTO> all = getAllFabricTypes();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public FabricTypeDTO updateFabricType(Long id, FabricTypeDTO fabricTypeDTO) {
        FabricType fabricType = fabricTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fabric type not found"));

        String trimmedName = fabricTypeDTO.getFabricTypeName().trim();
        
        boolean duplicateExists = fabricTypeRepository.findAll().stream()
                .filter(fabric -> !fabric.getId().equals(id))
                .anyMatch(fabric -> fabric.getFabricTypeName().trim().equalsIgnoreCase(trimmedName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Fabric type with name '" + trimmedName + "' already exists");
        }

        fabricType.setFabricTypeName(trimmedName);
        fabricType.setActiveFlag(fabricTypeDTO.getActiveFlag());

        return new FabricTypeDTO(fabricTypeRepository.save(fabricType));
    }
    
//    public void deleteFabricType(Long id) {
//        if (!fabricTypeRepository.existsById(id)) {
//            throw new IllegalArgumentException("Fabric type not found");
//        }
//        fabricTypeRepository.deleteById(id);
//    }
    
    public void deleteFabricType(Long id) {
        // Check if category exists first
        if (!fabricTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("FabricType not found with id: " + id);
        }
        
        try {
        	fabricTypeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete FabricType because it is referenced by other");
        }
    }
}
