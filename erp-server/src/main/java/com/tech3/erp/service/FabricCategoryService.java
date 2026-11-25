package com.tech3.erp.service;

import com.tech3.erp.dto.FabricCategoryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.FabricCategory;
import com.tech3.erp.entity.FabricType;
import com.tech3.erp.repository.FabricCategoryRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class FabricCategoryService {

    private final FabricCategoryRepository fabricCategoryRepository;

    public FabricCategoryService(FabricCategoryRepository fabricCategoryRepository) {
        this.fabricCategoryRepository = fabricCategoryRepository;
    }

    public FabricCategoryDTO createFabricCategory(FabricCategoryDTO fabricCategoryDTO) {
        String trimmedName = fabricCategoryDTO.getFabricCategoryName().trim();
        
        boolean duplicateExists = fabricCategoryRepository.findAll().stream()
                .anyMatch(fabric -> fabric.getFabricCategoryName().trim().equalsIgnoreCase(trimmedName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Fabric category with name '" + trimmedName + "' already exists");
        }
        
        FabricCategory fabricCategory = new FabricCategory();
        fabricCategory.setFabricCategoryName(trimmedName);
        fabricCategory.setActiveFlag(fabricCategoryDTO.getActiveFlag());

		return new FabricCategoryDTO(fabricCategoryRepository.save(fabricCategory));
    }

    public FabricCategoryDTO getFabricCategoryById(Long id) {
        FabricCategory fabricCategory = fabricCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fabric Category not found"));
        return new FabricCategoryDTO(fabricCategory);
    }

    public List<FabricCategoryDTO> getAllFabricCategory() {
        return fabricCategoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(FabricCategory ::getId, Comparator.reverseOrder()))
                .map(FabricCategoryDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FabricCategoryDTO> getAllFabricCategoryWithPagination(Long offset, Long limit) {
        List<FabricCategoryDTO> all = getAllFabricCategory();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public FabricCategoryDTO updateFabricCategory(Long id, FabricCategoryDTO fabricCategoryDTO) {
        FabricCategory fabricCategory = fabricCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fabric category not found"));

        String trimmedName = fabricCategoryDTO.getFabricCategoryName().trim();
        
        boolean duplicateExists = fabricCategoryRepository.findAll().stream()
                .filter(fabric -> !fabric.getId().equals(id))
                .anyMatch(fabric -> fabric.getFabricCategoryName().trim().equalsIgnoreCase(trimmedName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Fabric category with name '" + trimmedName + "' already exists");
        }

        fabricCategory.setFabricCategoryName(trimmedName);
        fabricCategory.setActiveFlag(fabricCategoryDTO.getActiveFlag());

        return new FabricCategoryDTO(fabricCategoryRepository.save(fabricCategory));
    }
    
//    public void deleteFabricCategory(Long id) {
//        if (!fabricCategoryRepository.existsById(id)) {
//            throw new IllegalArgumentException("Fabric type not found");
//        }
//        fabricCategoryRepository.deleteById(id);
//    }
    
    public void deleteFabricCategory(Long id) {
        // Check if category exists first
        if (!fabricCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Fabric Category not found with id: " + id);
        }
        
        try {
        	fabricCategoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Fabric Category because it is referenced by other");
        }
    }
    
}
