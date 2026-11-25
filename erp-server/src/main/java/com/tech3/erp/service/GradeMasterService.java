package com.tech3.erp.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.GradeMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.GradeMaster;
import com.tech3.erp.entity.Warehouse;
import com.tech3.erp.repository.GradeMasterRepository;
import com.tech3.erp.util.PaginationHelper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeMasterService {

    private final GradeMasterRepository gradeMasterRepository;

    @Autowired
    public GradeMasterService(GradeMasterRepository gradeMasterRepository) {
        this.gradeMasterRepository = gradeMasterRepository;
    }

    @Transactional
    public GradeMasterDTO createGradeMaster(GradeMasterDTO dto) {
        String normalizedCode = normalizeText(dto.getGradeCode());
        if (normalizedCode == null) {
            throw new IllegalArgumentException("Grade code cannot be blank");
        }
        String normalizedName = normalizeText(dto.getGradeName());
        if (normalizedName == null) {
            throw new IllegalArgumentException("Grade name cannot be blank");
        }

        dto.setGradeCode(normalizedCode);
        dto.setGradeName(normalizedName);
        ensureGradeCodeUnique(normalizedCode, null);
        ensureGradeNameUnique(normalizedName, null);

        GradeMaster gradeMaster = new GradeMaster();
        mapDtoToEntity(dto, gradeMaster);
        try {
            gradeMaster = gradeMasterRepository.save(gradeMaster);
            return mapEntityToDto(gradeMaster);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Grade code or name already exists");
        }
    }

    public List<GradeMasterDTO> getAllGradeMasters() {
        return gradeMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(GradeMaster ::getId, Comparator.reverseOrder()))
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

      public PaginatedResponse<GradeMasterDTO> getAllGradeMastersWithPagination(Long offset, Long limit) {
        List<GradeMasterDTO> allGradeMasters = gradeMasterRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(GradeMaster ::getId, Comparator.reverseOrder()))
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allGradeMasters, offset, limit);
    }


    // public PaginatedResponse<GradeMasterDTO> getAllGradeMastersWithPagination(Long offset, Long limit) {
    //     List<GradeMasterDTO> all = getAllGradeMasters();
    //     return PaginationHelper.paginate(all, offset, limit);
    // }

    public GradeMasterDTO getGradeMasterById(Long id) {
        GradeMaster gradeMaster = gradeMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade Master not found with id: " + id));
        return mapEntityToDto(gradeMaster);
    }

    @Transactional
    public GradeMasterDTO updateGradeMaster(Long id, GradeMasterDTO dto) {
        GradeMaster gradeMaster = gradeMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade Master not found with id: " + id));

        String normalizedCode = normalizeText(dto.getGradeCode());
        if (normalizedCode == null) {
            throw new IllegalArgumentException("Grade code cannot be blank");
        }
        String normalizedName = normalizeText(dto.getGradeName());
        if (normalizedName == null) {
            throw new IllegalArgumentException("Grade name cannot be blank");
        }

        dto.setGradeCode(normalizedCode);
        dto.setGradeName(normalizedName);
        ensureGradeCodeUnique(normalizedCode, id);
        ensureGradeNameUnique(normalizedName, id);

        mapDtoToEntity(dto, gradeMaster);
        try {
            gradeMaster = gradeMasterRepository.save(gradeMaster);
            return mapEntityToDto(gradeMaster);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Grade code or name already exists");
        }
    }

//    @Transactional
//    public void deleteGradeMaster(Long id) {
//        if (!gradeMasterRepository.existsById(id)) {
//            throw new EntityNotFoundException("Grade Master not found with id: " + id);
//        }
//        gradeMasterRepository.deleteById(id);
//    }
    public void deleteGradeMaster(Long id) {
        // Check if category exists first
        if (!gradeMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Grade not found with id: " + id);
        }
        
        try {
        	gradeMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Grade because it is referenced by other");
        }
    }

    private void ensureGradeCodeUnique(String gradeCode, Long currentId) {
        if (gradeCode == null) {
            return;
        }
        gradeMasterRepository.findByGradeCode(gradeCode)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Grade code '" + gradeCode + "' already exists");
                });
    }

    private void ensureGradeNameUnique(String gradeName, Long currentId) {
        if (gradeName == null) {
            return;
        }
        gradeMasterRepository.findByGradeName(gradeName)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Grade name '" + gradeName + "' already exists");
                });
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private GradeMasterDTO mapEntityToDto(GradeMaster entity) {
        GradeMasterDTO dto = new GradeMasterDTO();
        dto.setId(entity.getId());
        dto.setGradeCode(entity.getGradeCode());
        dto.setGradeName(entity.getGradeName());
        dto.setMinPoint(entity.getMinPoint());
        dto.setMaxPoint(entity.getMaxPoint());
        dto.setDescription(entity.getDescription());
        dto.setActiveFlag(entity.getActiveFlag());
        return dto;
    }

    private void mapDtoToEntity(GradeMasterDTO dto, GradeMaster entity) {
        entity.setGradeCode(dto.getGradeCode());
        entity.setGradeName(dto.getGradeName());
        entity.setMinPoint(dto.getMinPoint());
        entity.setMaxPoint(dto.getMaxPoint());
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
    }
}
