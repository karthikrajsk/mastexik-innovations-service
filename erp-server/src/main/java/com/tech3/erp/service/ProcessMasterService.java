package com.tech3.erp.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.ProcessMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.DefectMaster;
import com.tech3.erp.entity.ProcessMaster;
import com.tech3.erp.repository.ProcessMasterRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProcessMasterService {
	
	private final ProcessMasterRepository processMasterRepository;

    @Autowired
    public ProcessMasterService(ProcessMasterRepository processMasterRepository) {
        this.processMasterRepository = processMasterRepository;
    }

    @Transactional
    public ProcessMasterDTO createProcessMaster(ProcessMasterDTO dto) {
        String normalizedName = normalizeText(dto.getProcessName());
        if (normalizedName == null) {
            throw new IllegalArgumentException("Process name cannot be blank");
        }

        dto.setProcessName(normalizedName);
        ensureProcessNameUnique(normalizedName, null);

        ProcessMaster processMaster = new ProcessMaster();
        mapDtoToEntity(dto, processMaster);
        try {
            processMaster = processMasterRepository.save(processMaster);
            return mapEntityToDto(processMaster);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Process name already exists");
        }
    }

    public List<ProcessMasterDTO> getAllProcessMasters() {
        return processMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(ProcessMaster ::getId, Comparator.reverseOrder()))
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

  public PaginatedResponse<ProcessMasterDTO> getAllProcessMastersWithPagination(Long offset, Long limit) {
        List<ProcessMasterDTO> allProcessMasters = processMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(ProcessMaster::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allProcessMasters, offset, limit);
    }

    // public PaginatedResponse<ProcessMasterDTO> getAllProcessMastersWithPagination(Long offset, Long limit) {
    //     List<ProcessMasterDTO> all = getAllProcessMasters();
    //     return PaginationHelper.paginate(all, offset, limit);
    // }

    public ProcessMasterDTO getProcessMasterById(Long id) {
        ProcessMaster processMaster = processMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Process Master not found with id: " + id));
        return mapEntityToDto(processMaster);
    }

    @Transactional
    public ProcessMasterDTO updateProcessMaster(Long id, ProcessMasterDTO dto) {
    	ProcessMaster processMaster = processMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Process Master not found with id: " + id));

        String normalizedName = normalizeText(dto.getProcessName());
        if (normalizedName == null) {
            throw new IllegalArgumentException("Process name cannot be blank");
        }

        dto.setProcessName(normalizedName);
        ensureProcessNameUnique(normalizedName, id);

        mapDtoToEntity(dto, processMaster);
        try {
            processMaster = processMasterRepository.save(processMaster);
            return mapEntityToDto(processMaster);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Process name already exists");
        }
    }

//    @Transactional
//    public void deleteDefectMaster(Long id) {
//        if (!defectMasterRepository.existsById(id)) {
//            throw new EntityNotFoundException("Defect Master not found with id: " + id);
//        }
//        defectMasterRepository.deleteById(id);
//    }

    public void deleteProcessMaster(Long id) {
        // Check if category exists first
        if (!processMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Process not found with id: " + id);
        }
        
        try {
        	processMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Process because it is referenced by other");
        }
    }
    
    private void ensureProcessNameUnique(String processName, Long currentId) {
        if (processName == null) {
            return;
        }
        processMasterRepository.findByProcessName(processName)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Process name '" + processName + "' already exists");
                });
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private ProcessMasterDTO mapEntityToDto(ProcessMaster entity) {
    	ProcessMasterDTO dto = new ProcessMasterDTO();
        dto.setId(entity.getId());
        dto.setProcessName(entity.getProcessName());
        dto.setDescription(entity.getDescription());
        dto.setActiveFlag(entity.getActiveFlag());
        dto.setGoodsType(entity.getGoodsType());
        return dto;
    }

    private void mapDtoToEntity(ProcessMasterDTO dto, ProcessMaster entity) {
        entity.setProcessName(dto.getProcessName());
        entity.setDescription(dto.getDescription());
        entity.setActiveFlag(dto.getActiveFlag());
        entity.setGoodsType(dto.getGoodsType());
    }
    

}
