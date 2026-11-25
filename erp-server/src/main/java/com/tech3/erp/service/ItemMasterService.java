package com.tech3.erp.service;

import com.tech3.erp.dto.ItemMasterDto;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.ItemMaster;
import com.tech3.erp.entity.Uom;
import com.tech3.erp.repository.ItemMasterRepository;
import com.tech3.erp.repository.PurchaseOrderItemRepository;
import com.tech3.erp.repository.UomRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemMasterService {

    private final ItemMasterRepository itemMasterRepository;
    private final UomRepository uomRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    public ItemMasterService(ItemMasterRepository itemMasterRepository, UomRepository uomRepository, PurchaseOrderItemRepository purchaseOrderItemRepository) {
        this.itemMasterRepository = itemMasterRepository;
        this.uomRepository = uomRepository;
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
    }

    public ItemMasterDto createItemMaster(ItemMasterDto dto) {
        String trimmedItemCode = dto.getItemCode() != null && !dto.getItemCode().trim().isEmpty() ? dto.getItemCode().trim() : null;
        
        if (trimmedItemCode != null) {
            boolean duplicateExists = itemMasterRepository.findAll().stream()
                    .filter(item -> item.getItemCode() != null && !item.getItemCode().trim().isEmpty())
                    .anyMatch(item -> item.getItemCode().trim().equalsIgnoreCase(trimmedItemCode));
            
            if (duplicateExists) {
                throw new IllegalArgumentException("Item code already exists: " + trimmedItemCode);
            }
        }

        // Validate UOM exists and get the UOM name
        Uom uom = uomRepository.findByUomName(dto.getUomName())
                .orElseThrow(() -> new IllegalArgumentException("UOM not found with name: " + dto.getUomName()));

        ItemMaster itemMaster = new ItemMaster();
        itemMaster.setItemName(dto.getItemName());
        itemMaster.setItemCode(trimmedItemCode);
        itemMaster.setDepartment(dto.getDepartment());
        itemMaster.setItemCategory(dto.getItemCategory() != null && !dto.getItemCategory().trim().isEmpty() ? dto.getItemCategory().trim() : null);
        itemMaster.setHsnCode(dto.getHsnCode() != null && !dto.getHsnCode().trim().isEmpty() ? dto.getHsnCode().trim() : null);
        itemMaster.setUomName(uom.getUomName()); // Store only the UOM name
        itemMaster.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        itemMaster.setIgst(dto.getIgst());
        itemMaster.setCgst(dto.getCgst());
        itemMaster.setSgst(dto.getSgst());
        itemMaster.setImageUrl(dto.getImageUrl());

        ItemMaster savedItemMaster = itemMasterRepository.save(itemMaster);
        return new ItemMasterDto(savedItemMaster);
    }

    public ItemMasterDto getItemMasterById(Long id) {
        ItemMaster itemMaster = itemMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item Master not found with id: " + id));
        return new ItemMasterDto(itemMaster);
    }

    public List<ItemMasterDto> getAllItemMasters() {
        return itemMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(ItemMaster::getId, Comparator.reverseOrder()))
                .map(ItemMasterDto::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<ItemMasterDto> getAllItemMastersWithPagination(Long offset, Long limit) {
        List<ItemMasterDto> allItemMasters = getAllItemMasters();
        return PaginationHelper.paginate(allItemMasters, offset, limit);
    }

    public ItemMasterDto updateItemMaster(Long id, ItemMasterDto dto) {
        ItemMaster itemMaster = itemMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item Master not found with id: " + id));

        String trimmedItemCode = dto.getItemCode() != null && !dto.getItemCode().trim().isEmpty() ? dto.getItemCode().trim() : null;
        
        if (trimmedItemCode != null) {
            boolean duplicateExists = itemMasterRepository.findAll().stream()
                    .filter(item -> !item.getId().equals(id) && item.getItemCode() != null && !item.getItemCode().trim().isEmpty())
                    .anyMatch(item -> item.getItemCode().trim().equalsIgnoreCase(trimmedItemCode));
            
            if (duplicateExists) {
                throw new IllegalArgumentException("Item code already exists: " + trimmedItemCode);
            }
        }

        // Validate UOM exists if it's being changed
        if (!itemMaster.getUomName().equals(dto.getUomName())) {
            Uom uom = uomRepository.findByUomName(dto.getUomName())
                    .orElseThrow(() -> new IllegalArgumentException("UOM not found with name: " + dto.getUomName()));
            itemMaster.setUomName(uom.getUomName());
        }

        itemMaster.setItemName(dto.getItemName());
        itemMaster.setItemCode(trimmedItemCode);
        itemMaster.setDepartment(dto.getDepartment());
        itemMaster.setItemCategory(dto.getItemCategory() != null && !dto.getItemCategory().trim().isEmpty() ? dto.getItemCategory().trim() : null);
        itemMaster.setHsnCode(dto.getHsnCode() != null && !dto.getHsnCode().trim().isEmpty() ? dto.getHsnCode().trim() : null);
        itemMaster.setIgst(dto.getIgst());
        itemMaster.setCgst(dto.getCgst());
        itemMaster.setSgst(dto.getSgst());
        if (dto.getImageUrl() != null) {
            itemMaster.setImageUrl(dto.getImageUrl());
        }
        if (dto.getActiveFlag() != null) {
            itemMaster.setActiveFlag(dto.getActiveFlag());
        }

        ItemMaster updatedItemMaster = itemMasterRepository.save(itemMaster);
        return new ItemMasterDto(updatedItemMaster);
    }

    public void deleteItemMaster(Long id) {
        ItemMaster itemMaster = itemMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item Master not found with id: " + id));

        boolean isMappedInPo = purchaseOrderItemRepository.existsByItemId(id);
        if (isMappedInPo) {
            throw new IllegalStateException("This " + itemMaster.getItemName() + " already mapped in purchase order");
        }

        try {
            itemMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Item Master because it is referenced by other records");
        }
    }
}
