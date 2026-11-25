package com.tech3.erp.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tech3.erp.dto.MachineMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.MachineMaster;
import com.tech3.erp.repository.MachineMasterRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MachineMasterService {

    private final MachineMasterRepository repository;

    public MachineMasterService(MachineMasterRepository repository) {
        this.repository = repository;
    }

    public MachineMasterDTO create(MachineMasterDTO dto) {
        if (repository.existsByMachineNo(dto.getMachineNo())) {
            throw new IllegalArgumentException("Machine number already exists");
        }
        MachineMaster entity = new MachineMaster();
        mapDtoToEntity(dto, entity);
        MachineMaster saved = repository.save(entity);
        return new MachineMasterDTO(saved);
    }

    public MachineMasterDTO getById(Long id) {
        return repository.findById(id)
                .map(MachineMasterDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Machine not found"));
    }

    public List<MachineMasterDTO> getAll() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(MachineMaster::getId, Comparator.reverseOrder()))
                .map(MachineMasterDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<MachineMasterDTO> getAllWithPagination(Long offset, Long limit) {
        List<MachineMasterDTO> machines = getAll();
        return PaginationHelper.paginate(machines, offset, limit);
    }

    public MachineMasterDTO update(Long id, MachineMasterDTO dto) {
        if (repository.existsByMachineNoAndIdNot(dto.getMachineNo(), id)) {
            throw new IllegalArgumentException("Machine number already exists");
        }
        MachineMaster entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Machine not found"));
        mapDtoToEntity(dto, entity);
        return new MachineMasterDTO(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Machine not found with id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Machine because it is referenced by other");
        }
    }

    private void mapDtoToEntity(MachineMasterDTO dto, MachineMaster entity) {
        entity.setMachineNo(dto.getMachineNo());
        entity.setMachineName(dto.getMachineName());
        entity.setDepartmentId(dto.getDepartmentId());
        entity.setMachineTypeId(dto.getMachineTypeId());
        entity.setManufactureYearModel(dto.getManufactureYearModel());
        entity.setSerialNo(dto.getSerialNo());
        entity.setActiveFlag(dto.isActiveFlag());
    }
}
