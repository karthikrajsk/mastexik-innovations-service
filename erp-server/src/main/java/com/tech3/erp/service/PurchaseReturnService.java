package com.tech3.erp.service;

import com.tech3.erp.dto.PurchaseReturnDTO;
import com.tech3.erp.dto.PurchaseReturnFabricItemDTO;
import com.tech3.erp.dto.PurchaseReturnFabricSummaryDTO;
import com.tech3.erp.dto.PurchaseReturnItemDTO;
import com.tech3.erp.dto.PurchaseReturnYarnItemDTO;
import com.tech3.erp.dto.PurchaseReturnYarnSummaryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.*;
import com.tech3.erp.repository.*;
import com.tech3.erp.util.JsonUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PurchaseReturnService {

    @Autowired
    private PurchaseReturnRepository purchaseReturnRepository;

    @Autowired
    private PurchaseReturnYarnItemRepository yarnItemRepository;

    @Autowired
    private PurchaseReturnFabricItemRepository fabricItemRepository;

    @Autowired
    private PurchaseReturnItemRepository purchaseReturnItemRepository;

    @Autowired
    private PurchaseInwardRepository purchaseInwardRepository;

    @Transactional
    public PurchaseReturn create(PurchaseReturnDTO dto) {
        // Enhance DTO with Purchase Inward IDs before processing
        enhanceYarnItemsWithPurchaseInwardIds(dto);
        enhanceFabricItemsWithPurchaseInwardIds(dto);
        enhanceItemsWithPurchaseInwardIds(dto);
        
        PurchaseReturn entity = new PurchaseReturn();
        entity.setReturnNo(dto.getPurchaseReturnId()); // Added from your code
        entity.setReturnDate(dto.getReturnDate() != null && !dto.getReturnDate().isEmpty() ? LocalDate.parse(dto.getReturnDate()) : null);
        entity.setPurchaseType(dto.getPurchaseType());
        entity.setVendorId(parseLongSafe(dto.getVendorId()));
        entity.setPurchaseOrderId(dto.getPurchaseOrderId());
        entity.setCompanyId(dto.getCompanyId()); // Added from your code
        entity.setTransportId(parseLongSafe(dto.getTransportId()));
        entity.setRemark(dto.getRemark());
        entity.setVehicleNo(dto.getVehicleNo());
        entity.setGrossWt(parseBigDecimalSafe(dto.getGrossWt()));

        entity.setYarnSummaryJson(JsonUtils.toJson(dto.getYarnSummary()));
        entity.setFabricSummaryJson(JsonUtils.toJson(dto.getFabricSummary()));

        if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
            Set<PurchaseReturnYarnItem> yarnItems = dto.getYarnItems().stream()
                .map(this::toYarnEntity)
                .collect(Collectors.toCollection(HashSet::new));
            entity.setYarnItems(yarnItems);
        }
        if (dto.getFabricItems() != null && !dto.getFabricItems().isEmpty()) {
            Set<PurchaseReturnFabricItem> fabricItems = dto.getFabricItems().stream()
                .map(this::toFabricEntity)
                .collect(Collectors.toCollection(HashSet::new));
            entity.setFabricItems(fabricItems);
        }
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            Set<PurchaseReturnItem> items = dto.getItems().stream()
                .map(this::toItemEntity)
                .collect(Collectors.toCollection(HashSet::new));
            entity.setItems(items);
        }
        PurchaseReturn savedEntity = purchaseReturnRepository.save(entity);

        return savedEntity;
    }

    public List<PurchaseReturnDTO> listAll() {
        return purchaseReturnRepository.findAllWithItems().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<PurchaseReturnDTO> listAllWithPagination(Long offset, Long limit) {
        long finalLimit = (limit != null && limit > 0) ? limit : 10L;
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        Page<PurchaseReturn> page = purchaseReturnRepository.findAllWithItemsPaginated(
                PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit))
        );
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    public List<PurchaseReturnDTO> listAllByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return purchaseReturnRepository.findAllWithItemsByCompanyId(companyId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<PurchaseReturnDTO> listAllWithPaginationByCompany(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        long finalLimit = (limit != null && limit > 0) ? limit : 10L;
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        Page<PurchaseReturn> page = purchaseReturnRepository.findAllWithItemsByCompanyIdPaginated(
                companyId,
                PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit))
        );
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    public PurchaseReturnDTO get(Long id) {
        PurchaseReturn pr = purchaseReturnRepository.findByIdWithItems(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Return not found"));
        return mapToDto(pr);
    }
    
    public PurchaseReturnDTO getByCompany(Long id, Long companyId) {
        PurchaseReturn pr = purchaseReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Return not found"));
        return mapToDto(pr);
    }

    @Transactional
    public PurchaseReturnDTO update(Long id, PurchaseReturnDTO dto) {
        PurchaseReturn pr = purchaseReturnRepository.findByIdWithItems(id).orElseThrow(() -> new EntityNotFoundException("Purchase Return not found")); // Changed to findByIdWithItems()
        
        // Enhance DTO with Purchase Inward IDs before processing
        enhanceYarnItemsWithPurchaseInwardIds(dto);
        enhanceFabricItemsWithPurchaseInwardIds(dto);
        enhanceItemsWithPurchaseInwardIds(dto);
        
        // Added additional field updates from your code
        if (dto.getPurchaseReturnId() != null) pr.setReturnNo(dto.getPurchaseReturnId());
        if (dto.getReturnDate() != null) pr.setReturnDate(LocalDate.parse(dto.getReturnDate()));
        if (dto.getPurchaseType() != null) pr.setPurchaseType(dto.getPurchaseType());
        if (dto.getVendorId() != null) pr.setVendorId(parseLongSafe(dto.getVendorId()));
        if (dto.getPurchaseOrderId() != null) pr.setPurchaseOrderId(dto.getPurchaseOrderId());
        if (dto.getCompanyId() != null) pr.setCompanyId(dto.getCompanyId());
        if (dto.getTransportId() != null) pr.setTransportId(parseLongSafe(dto.getTransportId()));
        if (dto.getRemark() != null) pr.setRemark(dto.getRemark());
        if (dto.getVehicleNo() != null) pr.setVehicleNo(dto.getVehicleNo());
        if (dto.getGrossWt() != null) pr.setGrossWt(parseBigDecimalSafe(dto.getGrossWt()));

        // Replace children (simple approach)
        if (dto.getYarnItems() != null) {
            Set<PurchaseReturnYarnItem> updatedYarnItems = dto.getYarnItems().stream()
                .map(this::toYarnEntity)
                .collect(Collectors.toCollection(HashSet::new));
            pr.getYarnItems().clear();
            pr.getYarnItems().addAll(updatedYarnItems);
            pr.getYarnItems().forEach(y -> y.setPurchaseReturn(pr));
        }
        if (dto.getFabricItems() != null) {
            Set<PurchaseReturnFabricItem> updatedFabricItems = dto.getFabricItems().stream()
                .map(this::toFabricEntity)
                .collect(Collectors.toCollection(HashSet::new));
            pr.getFabricItems().clear();
            pr.getFabricItems().addAll(updatedFabricItems);
            pr.getFabricItems().forEach(f -> f.setPurchaseReturn(pr));
        }
        if (dto.getItems() != null) {
            Set<PurchaseReturnItem> updatedItems = dto.getItems().stream()
                .map(this::toItemEntity)
                .collect(Collectors.toCollection(HashSet::new));
            pr.getItems().clear();
            pr.getItems().addAll(updatedItems);
            pr.getItems().forEach(i -> i.setPurchaseReturn(pr));
        }

        purchaseReturnRepository.save(pr);

        return get(id);
    }
    
    @Transactional
    public PurchaseReturnDTO updateByCompany(Long id, Long companyId, PurchaseReturnDTO dto) {
        PurchaseReturn pr = purchaseReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Return not found"));
        
        // Enhance DTO with Purchase Inward IDs before processing
        enhanceYarnItemsWithPurchaseInwardIds(dto);
        enhanceFabricItemsWithPurchaseInwardIds(dto);
        enhanceItemsWithPurchaseInwardIds(dto);
        
        if (dto.getPurchaseReturnId() != null) pr.setReturnNo(dto.getPurchaseReturnId());
        if (dto.getReturnDate() != null) pr.setReturnDate(LocalDate.parse(dto.getReturnDate()));
        if (dto.getPurchaseType() != null) pr.setPurchaseType(dto.getPurchaseType());
        if (dto.getVendorId() != null) pr.setVendorId(parseLongSafe(dto.getVendorId()));
        if (dto.getPurchaseOrderId() != null) pr.setPurchaseOrderId(dto.getPurchaseOrderId());
        if (dto.getCompanyId() != null) pr.setCompanyId(dto.getCompanyId());
        if (dto.getTransportId() != null) pr.setTransportId(parseLongSafe(dto.getTransportId()));
        if (dto.getRemark() != null) pr.setRemark(dto.getRemark());
        if (dto.getVehicleNo() != null) pr.setVehicleNo(dto.getVehicleNo());
        if (dto.getGrossWt() != null) pr.setGrossWt(parseBigDecimalSafe(dto.getGrossWt()));
        if (dto.getYarnItems() != null) {
            Set<PurchaseReturnYarnItem> updatedYarnItems = dto.getYarnItems().stream()
                .map(this::toYarnEntity)
                .collect(Collectors.toCollection(HashSet::new));
            pr.getYarnItems().clear();
            pr.getYarnItems().addAll(updatedYarnItems);
            pr.getYarnItems().forEach(y -> y.setPurchaseReturn(pr));
        }
        if (dto.getFabricItems() != null) {
            Set<PurchaseReturnFabricItem> updatedFabricItems = dto.getFabricItems().stream()
                .map(this::toFabricEntity)
                .collect(Collectors.toCollection(HashSet::new));
            pr.getFabricItems().clear();
            pr.getFabricItems().addAll(updatedFabricItems);
            pr.getFabricItems().forEach(f -> f.setPurchaseReturn(pr));
        }
        if (dto.getItems() != null) {
            Set<PurchaseReturnItem> updatedItems = dto.getItems().stream()
                .map(this::toItemEntity)
                .collect(Collectors.toCollection(HashSet::new));
            pr.getItems().clear();
            pr.getItems().addAll(updatedItems);
            pr.getItems().forEach(i -> i.setPurchaseReturn(pr));
        }
        purchaseReturnRepository.save(pr);

        return getByCompany(id, companyId);
    }

    @Transactional
    public void delete(Long id) {
        PurchaseReturn pr = purchaseReturnRepository.findByIdWithItems(id)
            .orElseThrow(() -> new EntityNotFoundException("Purchase Return not found"));
        
        // Soft delete from your code instead of hard delete
        pr.setActiveFlag(false);
        if (pr.getYarnItems() != null) {
            pr.getYarnItems().forEach(item -> {
                item.setIsReturn(0);
                item.setActiveFlag(false);
            });
        }
        if (pr.getFabricItems() != null) {
            pr.getFabricItems().forEach(item -> {
                item.setIsReturn(0);
                item.setActiveFlag(false);
            });
        }
        if (pr.getItems() != null) {
            pr.getItems().forEach(item -> {
                item.setIsReturn(0);
                item.setActiveFlag(false);
            });
        }
        purchaseReturnRepository.save(pr);
    }
    
    @Transactional
    public void deleteByCompany(Long id, Long companyId) {
        PurchaseReturn pr = purchaseReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Return not found"));
        pr.setActiveFlag(false);
        if (pr.getYarnItems() != null) {
            pr.getYarnItems().forEach(item -> {
                item.setIsReturn(0);
                item.setActiveFlag(false);
            });
        }
        if (pr.getFabricItems() != null) {
            pr.getFabricItems().forEach(item -> {
                item.setIsReturn(0);
                item.setActiveFlag(false);
            });
        }
        if (pr.getItems() != null) {
            pr.getItems().forEach(item -> {
                item.setIsReturn(0);
                item.setActiveFlag(false);
            });
        }
        purchaseReturnRepository.save(pr);
    }
    
    private PaginatedResponse<PurchaseReturnDTO> buildPaginatedResponse(Page<PurchaseReturn> page, Long offset, Long limit) {
        List<PurchaseReturnDTO> content = page.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(
                page.getTotalElements(),
                !page.isLast(),
                page.getNumber() > 0,
                offset,
                limit,
                content
        );
    }
    
    private PurchaseReturnDTO mapToDto(PurchaseReturn pr) {
        PurchaseReturnDTO dto = new PurchaseReturnDTO(pr);
        dto.setYarnItems(pr.getYarnItems() != null ? pr.getYarnItems().stream().map(PurchaseReturnYarnItemDTO::new).collect(Collectors.toList()) : null);
        dto.setFabricItems(pr.getFabricItems() != null ? pr.getFabricItems().stream().map(PurchaseReturnFabricItemDTO::new).collect(Collectors.toList()) : null);
        dto.setItems(pr.getItems() != null ? pr.getItems().stream().map(PurchaseReturnItemDTO::new).collect(Collectors.toList()) : null);
        dto.setYarnSummary(JsonUtils.fromJsonObject(pr.getYarnSummaryJson(), PurchaseReturnYarnSummaryDTO.class));
        dto.setFabricSummary(JsonUtils.fromJsonObject(pr.getFabricSummaryJson(), PurchaseReturnFabricSummaryDTO.class));
        return dto;
    }

    private PurchaseReturnYarnItem toYarnEntity(PurchaseReturnYarnItemDTO d) {
        PurchaseReturnYarnItem e = new PurchaseReturnYarnItem();
        e.setYarnId(parseLongSafe(d.getYarnId()));
        e.setYarnName(d.getYarnName());
        e.setRejectedQty(d.getRejectedQty());
        e.setReturnQuantity(d.getReturnQuantity());
        e.setKgsPerBag(d.getKgsPerBag());
        e.setConesPerBag(parseBigDecimalSafe(d.getConesPerBag()));
        e.setConesWeight(parseBigDecimalSafe(d.getConesWeight()));
        e.setLotNo(d.getLotNo());
        e.setMillNameId(parseLongSafe(d.getMillNameId()));
        e.setWarehouseId(parseLongSafe(d.getWarehouseId()));
        e.setInwardId(parseLongSafe(d.getInwardId())); // From coworker's code
        e.setOrderItemId(parseLongSafe(d.getOrderItemId())); // From coworker's code
        e.setPurchaseInwardId(d.getPurchaseInwardId()); // New field for Purchase Inward ID mapping
        e.setRatePerUnit(parseBigDecimalSafe(d.getRatePerUnit()));
        e.setGstPercent(parseBigDecimalSafe(d.getGstPercent()));
        e.setGstAmount(parseBigDecimalSafe(d.getGstAmount()));
        e.setTotalAmount(parseBigDecimalSafe(d.getTotalAmount()));
        e.setIsReturn(1);
        return e;
    }

    private PurchaseReturnFabricItem toFabricEntity(PurchaseReturnFabricItemDTO d) {
        PurchaseReturnFabricItem e = new PurchaseReturnFabricItem();
        e.setFabricMasterId(parseLongSafe(d.getFabricMasterId()));
        e.setFabricCode(d.getFabricCode());
        e.setQuality(d.getQuality());
        e.setReturnQuantity(d.getReturnQuantity());
        e.setPieceNo(d.getPieceNo());
        e.setPieceMeter(d.getPieceMeter());
        e.setNetWeight(d.getNetWeight());
        e.setWarehouseId(parseLongSafe(d.getWarehouseId()));
        e.setIsPacked(d.getIsPacked());
        e.setPurchaseInwardId(d.getPurchaseInwardId()); // New field for Purchase Inward ID mapping
        e.setIsReturn(1);
        return e;
    }

    private PurchaseReturnItem toItemEntity(PurchaseReturnItemDTO d) {
        PurchaseReturnItem e = new PurchaseReturnItem();
        e.setInwardNo(d.getInwardNo());
        e.setItemId(parseLongSafe(d.getItemId()));
        e.setItemName(d.getItemName());
        e.setItemCode(d.getItemCode());
        e.setWarehouseId(parseLongSafe(d.getWarehouseId()));
        e.setUnitPerBags(d.getUnitPerBags());
        e.setRejectedQty(d.getRejectedQty());
        e.setReturnQuantity(d.getReturnQuantity());
        e.setInwardId(parseLongSafe(d.getInwardId()));
        e.setOrderItemId(parseLongSafe(d.getOrderItemId()));
        e.setPurchaseInwardId(d.getPurchaseInwardId()); // New field for Purchase Inward ID mapping
        e.setIsReturn(1);
        return e;
    }

    private Long parseLongSafe(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private BigDecimal parseBigDecimalSafe(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Fetch Purchase Inward IDs for a given Purchase Order ID
     * Returns only active purchase inwards (activeFlag = true or null)
     */
    private List<Long> getPurchaseInwardIdsByPurchaseOrderId(Long purchaseOrderId) {
        if (purchaseOrderId == null) {
            return Collections.emptyList();
        }
        
        List<PurchaseInward> purchaseInwards = purchaseInwardRepository.findByPoIdAndActiveFlag(purchaseOrderId, true);
        return purchaseInwards.stream()
                .map(PurchaseInward::getId)
                .collect(Collectors.toList());
    }

    /**
     * Enhance yarn items with Purchase Inward IDs based on Purchase Order
     */
    private void enhanceYarnItemsWithPurchaseInwardIds(PurchaseReturnDTO dto) {
        if (dto.getPurchaseOrderId() == null || dto.getYarnItems() == null || dto.getYarnItems().isEmpty()) {
            return;
        }
        
        List<Long> purchaseInwardIds = getPurchaseInwardIdsByPurchaseOrderId(dto.getPurchaseOrderId());
        if (purchaseInwardIds.isEmpty()) {
            return;
        }
        
        // For each yarn item, set the first available purchase inward ID
        // This can be enhanced further to match specific items to specific inwards
        for (PurchaseReturnYarnItemDTO yarnItem : dto.getYarnItems()) {
            if (yarnItem.getPurchaseInwardId() == null && !purchaseInwardIds.isEmpty()) {
                // Set the first available purchase inward ID
                // This logic can be refined based on specific business rules
                yarnItem.setPurchaseInwardId(purchaseInwardIds.get(0));
            }
        }
    }

    /**
     * Enhance fabric items with Purchase Inward IDs based on Purchase Order
     */
    private void enhanceFabricItemsWithPurchaseInwardIds(PurchaseReturnDTO dto) {
        if (dto.getPurchaseOrderId() == null || dto.getFabricItems() == null || dto.getFabricItems().isEmpty()) {
            return;
        }
        
        List<Long> purchaseInwardIds = getPurchaseInwardIdsByPurchaseOrderId(dto.getPurchaseOrderId());
        if (purchaseInwardIds.isEmpty()) {
            return;
        }
        
        // For each fabric item, set the first available purchase inward ID
        for (PurchaseReturnFabricItemDTO fabricItem : dto.getFabricItems()) {
            if (fabricItem.getPurchaseInwardId() == null && !purchaseInwardIds.isEmpty()) {
                fabricItem.setPurchaseInwardId(purchaseInwardIds.get(0));
            }
        }
    }

    /**
     * Enhance regular items with Purchase Inward IDs based on Purchase Order
     */
    private void enhanceItemsWithPurchaseInwardIds(PurchaseReturnDTO dto) {
        if (dto.getPurchaseOrderId() == null || dto.getItems() == null || dto.getItems().isEmpty()) {
            return;
        }
        
        List<Long> purchaseInwardIds = getPurchaseInwardIdsByPurchaseOrderId(dto.getPurchaseOrderId());
        if (purchaseInwardIds.isEmpty()) {
            return;
        }
        
        // For each item, set the first available purchase inward ID
        for (PurchaseReturnItemDTO item : dto.getItems()) {
            if (item.getPurchaseInwardId() == null && !purchaseInwardIds.isEmpty()) {
                item.setPurchaseInwardId(purchaseInwardIds.get(0));
            }
        }
    }

}
