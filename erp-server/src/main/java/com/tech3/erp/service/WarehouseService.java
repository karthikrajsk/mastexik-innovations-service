package com.tech3.erp.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tech3.erp.dto.WarehouseDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Flange;
import com.tech3.erp.entity.Warehouse;
import com.tech3.erp.repository.WarehouseRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
    	String normalizedName = normalizeWarehouseName(warehouseDTO.getWarehouseName());
    	if (normalizedName == null) {
    	    throw new IllegalArgumentException("Warehouse name cannot be blank");
    	}
    	ensureWarehouseNameUnique(normalizedName, null);
    	Warehouse warehouse = new Warehouse();
    	warehouse.setWarehouseName(normalizedName);
 warehouse.setDescription(warehouseDTO.getDescription()); // New
    warehouse.setOwnerType(warehouseDTO.getOwnerType());     // New
    warehouse.setOwnerId(warehouseDTO.getOwnerId());         // New
    warehouse.setWarehouseType(warehouseDTO.getWarehouseType()); // New
    warehouse.setActiveFlag(warehouseDTO.getActiveFlag());
         if (Boolean.TRUE.equals(warehouseDTO.getIsDefault())) {
            validateUniqueDefault(warehouseDTO);
        }

        warehouse.setIsDefault(warehouseDTO.getIsDefault());

        return new WarehouseDTO(warehouseRepository.save(warehouse));
    }

    public WarehouseDTO getWarehouseById(Long id) {
    	Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse not found"));
        return new WarehouseDTO(warehouse);
    }

    public List<WarehouseDTO> getAllWarehouse() {
        return warehouseRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Warehouse ::getId, Comparator.reverseOrder()))
                .map(WarehouseDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WarehouseDTO> getAllWarehouseWithPagination(Long offset, Long limit) {
        List<WarehouseDTO> all = getAllWarehouse();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) {
    	Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flang not found"));

    	String normalizedName = normalizeWarehouseName(warehouseDTO.getWarehouseName());
    	if (normalizedName == null) {
    	    throw new IllegalArgumentException("Warehouse name cannot be blank");
    	}
    	ensureWarehouseNameUnique(normalizedName, id);

    	warehouse.setWarehouseName(normalizedName);
    	warehouse.setActiveFlag(warehouseDTO.getActiveFlag());
    	warehouse.setDescription(warehouseDTO.getDescription());
    	warehouse.setOwnerType(warehouseDTO.getOwnerType());
    	warehouse.setOwnerId(warehouseDTO.getOwnerId());
    	warehouse.setWarehouseType(warehouseDTO.getWarehouseType());
      if (Boolean.TRUE.equals(warehouseDTO.getIsDefault())) {
            validateUniqueDefault(warehouseDTO);
        }

        warehouse.setIsDefault(warehouseDTO.getIsDefault());

        return new WarehouseDTO(warehouseRepository.save(warehouse));
    }

    private void validateUniqueDefault(WarehouseDTO warehouseDTO) {
    
    Optional<Warehouse> existingDefault = warehouseRepository.findByOwnerTypeAndOwnerIdAndWarehouseTypeAndIsDefaultTrue(
            warehouseDTO.getOwnerType(), warehouseDTO.getOwnerId(), warehouseDTO.getWarehouseType());

    if (existingDefault.isPresent() && !existingDefault.get().getId().equals(warehouseDTO.getId())) {
        throw new IllegalArgumentException("A default warehouse already exists for this " + 
            warehouseDTO.getOwnerType() + " and Warehouse Type (" + warehouseDTO.getWarehouseType() + ").");
    }
}

        
    private void ensureWarehouseNameUnique(String warehouseName, Long currentId) {
        if (warehouseName == null) {
            return;
        }
        warehouseRepository.findByWarehouseName(warehouseName)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Warehouse name '" + warehouseName + "' already exists");
                });
    }

    private String normalizeWarehouseName(String warehouseName) {
        if (warehouseName == null) {
            return null;
        }
        String trimmed = warehouseName.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public void deleteWarehouse(Long id) {
        // Check if category exists first
        if (!warehouseRepository.existsById(id)) {
            throw new EntityNotFoundException("Warehouse not found with id: " + id);
        }
        
        try {
        	warehouseRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Warehouse because it is referenced by other");
        }
    }
    

}
