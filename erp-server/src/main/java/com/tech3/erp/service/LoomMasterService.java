package com.tech3.erp.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tech3.erp.dto.LoomMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.LoomMaster;
import com.tech3.erp.repository.LoomMasterRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LoomMasterService {

    private final LoomMasterRepository repository;

    public LoomMasterService(LoomMasterRepository repository) {
        this.repository = repository;
    }

    public LoomMasterDTO create(LoomMasterDTO dto) {
        String normalizedLoomNo = normalizeLoomNo(dto.getLoomNo());
        if (normalizedLoomNo == null) {
            throw new IllegalArgumentException("Loom number cannot be blank");
        }
        dto.setLoomNo(normalizedLoomNo);
        ensureLoomUnique(dto.getCompanyId(), normalizedLoomNo, null);
        LoomMaster entity = new LoomMaster();
        mapDtoToEntity(dto, entity);
        LoomMaster saved = repository.save(entity);
        return new LoomMasterDTO(saved);
    }

    public LoomMasterDTO getById(Long id) {
        return repository.findById(id).map(LoomMasterDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Loom not found"));
    }

    public List<LoomMasterDTO> getAll() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(LoomMaster::getId, Comparator.reverseOrder()))
                .map(LoomMasterDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<LoomMasterDTO> getAllWithPagination(Long offset, Long limit) {
        List<LoomMasterDTO> looms = getAll();
        return PaginationHelper.paginate(looms, offset, limit);
    }

    public List<LoomMasterDTO> getByCompanyId(Long companyId) {
        return repository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(LoomMasterDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<LoomMasterDTO> getByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<LoomMasterDTO> list = getByCompanyId(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public LoomMasterDTO update(Long id, LoomMasterDTO dto) {
        LoomMaster entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loom not found"));
        String normalizedLoomNo = normalizeLoomNo(dto.getLoomNo());
        if (normalizedLoomNo == null) {
            throw new IllegalArgumentException("Loom number cannot be blank");
        }
        dto.setLoomNo(normalizedLoomNo);
        Long companyIdForCheck = dto.getCompanyId() != null ? dto.getCompanyId() : entity.getCompanyId();
        ensureLoomUnique(companyIdForCheck, normalizedLoomNo, id);
        mapDtoToEntity(dto, entity);
        return new LoomMasterDTO(repository.save(entity));
    }

    public LoomMasterDTO updateByCompanyId(Long id, Long companyId, LoomMasterDTO dto) {
        LoomMaster entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Loom not found"));
        String normalizedLoomNo = normalizeLoomNo(dto.getLoomNo());
        if (normalizedLoomNo == null) {
            throw new IllegalArgumentException("Loom number cannot be blank");
        }
        dto.setLoomNo(normalizedLoomNo);
        Long companyIdForCheck = dto.getCompanyId() != null ? dto.getCompanyId() : companyId;
        ensureLoomUnique(companyIdForCheck, normalizedLoomNo, id);
        mapDtoToEntity(dto, entity);
        return new LoomMasterDTO(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Loom not found with id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Loom because it is referenced by other");
        }
    }

    public void deleteByCompanyId(Long id, Long companyId) {
        LoomMaster entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Loom not found with id: " + id));
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Loom because it is referenced by other");
        }
    }

    private void ensureLoomUnique(Long companyId, String loomNo, Long currentId) {
        if (companyId == null || loomNo == null) {
            return;
        }
        repository.findByCompanyIdAndLoomNo(companyId, loomNo)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Loom number '" + loomNo + "' already exists");
                });
    }

    private String normalizeLoomNo(String loomNo) {
        if (loomNo == null) {
            return null;
        }

        String trimmed = loomNo.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private void mapDtoToEntity(LoomMasterDTO dto, LoomMaster entity) {
        if (dto.getDate() != null) {
            entity.setDate(dto.getDate());
        }
        if (dto.getCompanyName() != null) {
            entity.setCompanyName(dto.getCompanyName());
        }
        if (dto.getCompanyId() != null) {
            entity.setCompanyId(dto.getCompanyId());
        }
        entity.setLoomNo(dto.getLoomNo());
        if (dto.getLoomTypeId() != null) {
            entity.setLoomTypeId(dto.getLoomTypeId());
        }
        if (dto.getMachineTypeId() != null) {
            entity.setMachineTypeId(dto.getMachineTypeId());
        }
        if (dto.getBeamWidth() != null) {
            entity.setBeamWidth(dto.getBeamWidth());
        }
        if (dto.getPickMultiplicationValue() != null) {
            entity.setPickMultiplicationValue(dto.getPickMultiplicationValue());
        }
        if (dto.getMachineSerialNo() != null) {
            entity.setMachineSerialNo(dto.getMachineSerialNo());
        }
        if (dto.getManufactureYearModel() != null) {
            entity.setManufactureYearModel(dto.getManufactureYearModel());
        }
        if (dto.getCamBoxNo() != null) {
            entity.setCamBoxNo(dto.getCamBoxNo());
        }
        if (dto.getCamBoxModel() != null) {
            entity.setCamBoxModel(dto.getCamBoxModel());
        }
        if (dto.getIsBeamGating() != null) {
            entity.setIsBeamGating(dto.getIsBeamGating());
        }
        if (dto.getShedId() != null) {
            entity.setShedId(dto.getShedId());
        }
        if (dto.getGroupId() != null) {
            entity.setGroupId(dto.getGroupId());
        }
        if (dto.getRemarks() != null) {
            entity.setRemarks(dto.getRemarks());
        }
        if (dto.getActiveFlag() != null) {
            entity.setActiveFlag(dto.getActiveFlag());
        }
    }
}
