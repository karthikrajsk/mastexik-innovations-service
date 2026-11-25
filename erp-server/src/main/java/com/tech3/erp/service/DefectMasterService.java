package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.DefectMasterDTO;
import com.tech3.erp.entity.DefectMaster;
import com.tech3.erp.entity.GradeMaster;
import com.tech3.erp.repository.DefectMasterRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefectMasterService {

    private final DefectMasterRepository defectMasterRepository;

    @Autowired
    public DefectMasterService(DefectMasterRepository defectMasterRepository) {
        this.defectMasterRepository = defectMasterRepository;
    }

    @Transactional
    public DefectMasterDTO createDefectMaster(DefectMasterDTO dto) {
        String normalizedCode = normalizeText(dto.getDefectCode());
        if (normalizedCode == null) {
            throw new IllegalArgumentException("Defect code cannot be blank");
        }
        String normalizedName = normalizeText(dto.getDefectName());
        if (normalizedName == null) {
            throw new IllegalArgumentException("Defect name cannot be blank");
        }

        dto.setDefectCode(normalizedCode);
        dto.setDefectName(normalizedName);
        ensureDefectCodeUnique(normalizedCode, null);
        ensureDefectNameUnique(normalizedName, null);

        DefectMaster defectMaster = new DefectMaster();
        mapDtoToEntity(dto, defectMaster);
        try {
            defectMaster = defectMasterRepository.save(defectMaster);
            return mapEntityToDto(defectMaster);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Defect code or name already exists");
        }
    }

    public List<DefectMasterDTO> getAllDefectMasters() {
        return defectMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(DefectMaster ::getId, Comparator.reverseOrder()))
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
    public PaginatedResponse<DefectMasterDTO> getAllDefectMastersWithPagination(Long offset, Long limit) {
        List<DefectMasterDTO> allDefects = defectMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(DefectMaster::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allDefects, offset, limit);
    }

    public DefectMasterDTO getDefectMasterById(Long id) {
        DefectMaster defectMaster = defectMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Defect Master not found with id: " + id));
        return mapEntityToDto(defectMaster);
    }

    @Transactional
    public DefectMasterDTO updateDefectMaster(Long id, DefectMasterDTO dto) {
        DefectMaster defectMaster = defectMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Defect Master not found with id: " + id));

        String normalizedCode = normalizeText(dto.getDefectCode());
        if (normalizedCode == null) {
            throw new IllegalArgumentException("Defect code cannot be blank");
        }
        String normalizedName = normalizeText(dto.getDefectName());
        if (normalizedName == null) {
            throw new IllegalArgumentException("Defect name cannot be blank");
        }

        dto.setDefectCode(normalizedCode);
        dto.setDefectName(normalizedName);
        ensureDefectCodeUnique(normalizedCode, id);
        ensureDefectNameUnique(normalizedName, id);

        mapDtoToEntity(dto, defectMaster);
        try {
            defectMaster = defectMasterRepository.save(defectMaster);
            return mapEntityToDto(defectMaster);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Defect code or name already exists");
        }
    }

//    @Transactional
//    public void deleteDefectMaster(Long id) {
//        if (!defectMasterRepository.existsById(id)) {
//            throw new EntityNotFoundException("Defect Master not found with id: " + id);
//        }
//        defectMasterRepository.deleteById(id);
//    }

    public void deleteDefectMaster(Long id) {
        // Check if category exists first
        if (!defectMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Defect not found with id: " + id);
        }
        
        try {
        	defectMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Defect because it is referenced by other");
        }
    }
    
    private void ensureDefectCodeUnique(String defectCode, Long currentId) {
        if (defectCode == null) {
            return;
        }
        defectMasterRepository.findByDefectCode(defectCode)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Defect code '" + defectCode + "' already exists");
                });
    }

    private void ensureDefectNameUnique(String defectName, Long currentId) {
        if (defectName == null) {
            return;
        }
        defectMasterRepository.findByDefectName(defectName)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Defect name '" + defectName + "' already exists");
                });
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private DefectMasterDTO mapEntityToDto(DefectMaster entity) {
        DefectMasterDTO dto = new DefectMasterDTO();
        dto.setId(entity.getId());
        dto.setDefectCode(entity.getDefectCode());
        dto.setDefectName(entity.getDefectName());
        dto.setDefectPoint(entity.getDefectPoint());
        dto.setDescription(entity.getDescription());
        dto.setActiveFlag(entity.getActiveFlag());
        return dto;
    }

    private void mapDtoToEntity(DefectMasterDTO dto, DefectMaster entity) {
        entity.setDefectCode(dto.getDefectCode());
        entity.setDefectName(dto.getDefectName());
        entity.setDefectPoint(dto.getDefectPoint());
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
    }
}
