package com.tech3.erp.service;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.KnittedFabricMasterDTO;
import com.tech3.erp.entity.KnittedFabricMaster;
import com.tech3.erp.entity.WovenFabricMaster;
import com.tech3.erp.repository.KnittedFabricMasterRepository;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnittedFabricMasterService {

    private final KnittedFabricMasterRepository knittedFabricMasterRepository;

    public KnittedFabricMasterService(KnittedFabricMasterRepository knittedFabricMasterRepository) {
        this.knittedFabricMasterRepository = knittedFabricMasterRepository;
    }

    @Transactional
    public KnittedFabricMasterDTO createFabricMaster(KnittedFabricMasterDTO dto) {
    	
        calculateGstValues(dto);

    	
        KnittedFabricMaster entity = new KnittedFabricMaster();
        mapDtoToEntity(dto, entity);
        KnittedFabricMaster saved = knittedFabricMasterRepository.save(entity);

        // Ensure ID is generated and flushed to DB
        knittedFabricMasterRepository.flush();

        return new KnittedFabricMasterDTO(saved);
    }

    public KnittedFabricMasterDTO getFabricMasterById(Long id) {
    	KnittedFabricMaster entity = knittedFabricMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FabricMaster not found with ID: " + id));
        return new KnittedFabricMasterDTO(entity);
    }

    public List<KnittedFabricMasterDTO> getAllFabricMasters() {
        return knittedFabricMasterRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(KnittedFabricMaster ::getId, Comparator.reverseOrder()))
                .map(KnittedFabricMasterDTO::new)
                .collect(Collectors.toList());
    }

    public KnittedFabricMasterDTO updateFabricMaster(Long id, KnittedFabricMasterDTO dto) {
    	
    	calculateGstValues(dto);
    	
    	KnittedFabricMaster existing = knittedFabricMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FabricMaster not found with ID: " + id));
        mapDtoToEntity(dto, existing);
        KnittedFabricMaster updated = knittedFabricMasterRepository.save(existing);

        // Optional: Update warp and weft details if needed

        return new KnittedFabricMasterDTO(updated);
    }
    
    private void calculateGstValues(KnittedFabricMasterDTO dto) {
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

//    public void deleteFabricMaster(Long id) {
//        if (!knittedFabricMasterRepository.existsById(id)) {
//            throw new IllegalArgumentException("FabricMaster not found with ID: " + id);
//        }
//        knittedFabricMasterRepository.deleteById(id);
//    }
    
    public void deleteFabricMaster(Long id) {
        // Check if category exists first
        if (!knittedFabricMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("FabricMaster not found with id: " + id);
        }
        
        try {
        	knittedFabricMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete FabricMaster because it is referenced by other");
        }
    }

    private void mapDtoToEntity(KnittedFabricMasterDTO dto, KnittedFabricMaster entity) {
        entity.setFabricType(dto.getFabricType());
        entity.setFabricCategoryId(dto.getFabricCategoryId());
        entity.setKnittedFabricId(dto.getKnittedFabricId());
        entity.setFabricCode(dto.getFabricCode());
        entity.setFabricName(dto.getFabricName());
        entity.setGsm(dto.getGsm());
        entity.setWidth(dto.getWidth());
        entity.setComposition(dto.getComposition());
        entity.setShrinkage(dto.getShrinkage ());
        entity.setRemarks(dto.getRemarks());
        
        entity.setTaxType(dto.getTaxType());
        entity.setGstPercent(dto.getGstPercent());
        
        // Use calculated values or provided values (backward compatible)
        entity.setIgst(dto.getIgst() != null ? dto.getIgst() : BigDecimal.ZERO);
        entity.setCgst(dto.getCgst() != null ? dto.getCgst() : BigDecimal.ZERO);
        entity.setSgst(dto.getSgst() != null ? dto.getSgst() : BigDecimal.ZERO);
    }  

}

