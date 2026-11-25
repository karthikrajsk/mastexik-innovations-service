package com.tech3.erp.service;

import com.tech3.erp.dto.YarnMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.FinishMaster;
import com.tech3.erp.entity.YarnMaster;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YarnMasterService {

    private final YarnMasterRepository yarnMasterRepository;

    public YarnMasterService(YarnMasterRepository yarnMasterRepository) {
        this.yarnMasterRepository = yarnMasterRepository;
    }

    // Create a new YarnMaster
    public YarnMasterDTO createYarnMaster(YarnMasterDTO yarnMasterDTO) {
        calculateGstValues(yarnMasterDTO);
        String normalizedYarnName = normalizeYarnName(yarnMasterDTO.getYarnName());
        if (normalizedYarnName == null) {
            throw new IllegalArgumentException("Yarn name cannot be blank");
        }
        yarnMasterDTO.setYarnName(normalizedYarnName);
        validateYarnNameUnique(normalizedYarnName, null);
        
        YarnMaster yarnMaster = new YarnMaster();
        mapDtoToEntity(yarnMasterDTO, yarnMaster);

        try {
            YarnMaster savedYarnMaster = yarnMasterRepository.save(yarnMaster);
            return new YarnMasterDTO(savedYarnMaster);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "Yarn name '" + normalizedYarnName + "' already exists"
            );
        }
    }

    // Get YarnMaster by ID
    public YarnMasterDTO getYarnMasterById(Long id) {
        YarnMaster yarnMaster = yarnMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("YarnMaster not found with ID: " + id));
        return new YarnMasterDTO(yarnMaster);
    }

    // Get all YarnMasters
    public List<YarnMasterDTO> getAllYarnMasters() {
        return yarnMasterRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(YarnMaster ::getId, Comparator.reverseOrder()))
                .map(YarnMasterDTO::new)
                .collect(Collectors.toList());
    }

    // Get all YarnMasters with Pagination
    public PaginatedResponse<YarnMasterDTO> getAllYarnMastersWithPagination(Long offset, Long limit) {
        List<YarnMasterDTO> all = getAllYarnMasters();
        return PaginationHelper.paginate(all, offset, limit);
    }

    // Update YarnMaster by ID
    public YarnMasterDTO updateYarnMaster(Long id, YarnMasterDTO yarnMasterDTO) {
        calculateGstValues(yarnMasterDTO);
        
        YarnMaster yarnMaster = yarnMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("YarnMaster not found with ID: " + id));

        String normalizedYarnName = normalizeYarnName(yarnMasterDTO.getYarnName());
        if (normalizedYarnName == null) {
            throw new IllegalArgumentException("Yarn name cannot be blank");
        }
        yarnMasterDTO.setYarnName(normalizedYarnName);
        validateYarnNameUnique(normalizedYarnName, id);
        mapDtoToEntity(yarnMasterDTO, yarnMaster);

        try {
            YarnMaster updatedYarnMaster = yarnMasterRepository.save(yarnMaster);
            return new YarnMasterDTO(updatedYarnMaster);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "Yarn name '" + normalizedYarnName + "' already exists"
            );
        }
    }

    // Delete YarnMaster by ID
//    public void deleteYarnMaster(Long id) {
//        if (!yarnMasterRepository.existsById(id)) {
//            throw new IllegalArgumentException("YarnMaster not found with ID: " + id);
//        }
//        yarnMasterRepository.deleteById(id);
//    }
    public void deleteYarnMaster(Long id) {
        // Check if category exists first
        if (!yarnMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Yarn not found with id: " + id);
        }
        
        try {
        	yarnMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Yarn because it is referenced by other");
        }
    }
    
    private void validateYarnNameUnique(String yarnName, Long currentId) {
        if (yarnName == null) {
            return;
        }
        yarnMasterRepository.findByYarnName(yarnName)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Yarn name '" + yarnName + "' already exists");
                });
    }
    
    private String normalizeYarnName(String yarnName) {
        if (yarnName == null) {
            return null;
        }
        String trimmed = yarnName.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    
    private void calculateGstValues(YarnMasterDTO dto) {
        if (dto.getTaxType() != null && dto.getGstPercent() != null) {
            BigDecimal gstPercent = dto.getGstPercent();
            
            // Check if manual values are already set
            boolean hasManualIgst = dto.getIgst() != null && dto.getIgst().compareTo(BigDecimal.ZERO) != 0;
            boolean hasManualCgst = dto.getCgst() != null && dto.getCgst().compareTo(BigDecimal.ZERO) != 0;
            boolean hasManualSgst = dto.getSgst() != null && dto.getSgst().compareTo(BigDecimal.ZERO) != 0;
            
            // Only calculate if no manual values are provided (backward compatible)
            if (!hasManualIgst && !hasManualCgst && !hasManualSgst) {
                if (dto.getTaxType() == 2) { // IGST
                    dto.setIgst(gstPercent);
                    dto.setCgst(BigDecimal.ZERO);
                    dto.setSgst(BigDecimal.ZERO);
                } else if (dto.getTaxType() == 1) { // CGST/SGST
                    BigDecimal halfPercent = gstPercent.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                    dto.setCgst(halfPercent);
                    dto.setSgst(halfPercent);
                    dto.setIgst(BigDecimal.ZERO);
                }
            }
        }
    }
    
    private void mapDtoToEntity(YarnMasterDTO dto, YarnMaster entity) {
        entity.setYarnName(dto.getYarnName());
        entity.setCountSno(dto.getCountSno());
        entity.setUnitSno(dto.getUnitSno());
        entity.setTypes(dto.getTypes());
        entity.setColorSno(dto.getColorSno());
        entity.setConversion(dto.getConversion());
        entity.setContent(dto.getContent());
        entity.setActiveFlag(dto.getActiveFlag());
        
        entity.setPlySno(dto.getPlySno());
        entity.setFilaments(dto.getFilaments());
        
        // Map GST fields
        entity.setTaxType(dto.getTaxType());
        entity.setGstPercent(dto.getGstPercent());
        
        // Use calculated values or provided values (backward compatible)
        entity.setIgst(dto.getIgst() != null ? dto.getIgst() : BigDecimal.ZERO);
        entity.setCgst(dto.getCgst() != null ? dto.getCgst() : BigDecimal.ZERO);
        entity.setSgst(dto.getSgst() != null ? dto.getSgst() : BigDecimal.ZERO);
    }
    
}
