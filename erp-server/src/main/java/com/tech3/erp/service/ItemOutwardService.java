package com.tech3.erp.service;

import com.tech3.erp.dto.ItemOutwardDTO;
import com.tech3.erp.dto.ItemOutwardStockDetailsDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.*;
import com.tech3.erp.repository.*;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemOutwardService {

    @Autowired
    private ItemOutwardRepository itemOutwardRepository;

    @Autowired
    private ItemInventoryRepository itemInventoryRepository;

    @Autowired
    private ItemInwardStockDetailsRepository itemInwardStockDetailsRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ItemMasterRepository itemMasterRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private NumberFormatConfigurationRepository numberFormatConfigurationRepository;

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private ItemOutwardStockDetailsRepository itemOutwardStockDetailsRepository;

    @Transactional
    public ItemOutwardDTO createItemOutward(ItemOutwardDTO dto) {
        ItemOutward entity = new ItemOutward();
        
        // Set outward date (system date if not provided)
        if (dto.getOutwardDate() != null) {
            entity.setOutwardDate(dto.getOutwardDate());
        } else {
            entity.setOutwardDate(LocalDate.now());
        }

        // Generate outward number (use provided outwardNo if available, otherwise generate)
        if (dto.getOutwardNo() != null && !dto.getOutwardNo().isEmpty()) {
            entity.setOutwardNo(dto.getOutwardNo());
        } else {
            String outwardNo = generateOutwardNo(dto.getCompanyId());
            entity.setOutwardNo(outwardNo);
        }

        // Set financial year
        entity.setFinancialYearId(getFinancialYearId(entity.getOutwardDate()));

        // Fetch and set vendor name from Vendor Master
        if (dto.getVendorId() != null) {
            Vendor vendor = vendorRepository.findById(dto.getVendorId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendor not found with ID: " + dto.getVendorId()));
            entity.setVendorId(dto.getVendorId());
            entity.setItemVendorName(vendor.getVendorName());
        }

        // Handle machineName - combine machineNo and machineName if provided
        if (dto.getMachineNo() != null || dto.getMachineName() != null) {
            String combinedMachineName = "";
            if (dto.getMachineNo() != null) {
                combinedMachineName = dto.getMachineNo();
            }
            if (dto.getMachineName() != null) {
                if (!combinedMachineName.isEmpty()) {
                    combinedMachineName += " " + dto.getMachineName();
                } else {
                    combinedMachineName = dto.getMachineName();
                }
            }
            entity.setMachineName(combinedMachineName.trim());
        } else if (dto.getMachineName() != null) {
            entity.setMachineName(dto.getMachineName());
        }

        // Fetch and set warehouse name from Warehouse Master using warehouseId
        if (dto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with ID: " + dto.getWarehouseId()));
            entity.setWarehouse(warehouse.getWarehouseName());
        } else if (dto.getWarehouse() != null) {
            entity.setWarehouse(dto.getWarehouse());
        } else {
            throw new IllegalArgumentException("Warehouse is required. Please provide either warehouseId or warehouse.");
        }

        // Set department - use provided department or fetch from Item Master
        if (dto.getDepartment() != null && !dto.getDepartment().isEmpty()) {
            entity.setDepartment(dto.getDepartment());
        } else {
            // Fetch department from Item Master (from first stock detail if available)
            List<ItemOutwardStockDetailsDTO> stockDetailsList = dto.getItems() != null ? dto.getItems() : dto.getItemOutwardStockDetails();
            if (stockDetailsList != null && !stockDetailsList.isEmpty()) {
                ItemOutwardStockDetailsDTO firstDetail = stockDetailsList.get(0);
                if (firstDetail.getItemId() != null) {
                    ItemMaster itemMaster = itemMasterRepository.findById(firstDetail.getItemId())
                            .orElse(null);
                    if (itemMaster != null && itemMaster.getDepartment() != null) {
                        entity.setDepartment(itemMaster.getDepartment());
                    }
                }
            }
        }

        entity.setCompanyId(dto.getCompanyId() != null ? dto.getCompanyId() : 1L);
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);

        // Handle child entities - support both "items" and "itemOutwardStockDetails" field names
        List<ItemOutwardStockDetailsDTO> stockDetailsList = dto.getItems() != null ? dto.getItems() : dto.getItemOutwardStockDetails();
        if (stockDetailsList != null && !stockDetailsList.isEmpty()) {
            List<ItemOutwardStockDetails> stockDetails = new ArrayList<>();
            for (ItemOutwardStockDetailsDTO detailDto : stockDetailsList) {
                ItemOutwardStockDetails detail = mapStockDetailDtoToEntity(detailDto);
                detail.setItemOutward(entity);
                stockDetails.add(detail);
            }
            entity.setItemOutwardStockDetails(stockDetails);
        }

        ItemOutward saved = itemOutwardRepository.save(entity);
        
        // Create response DTO and preserve original input values for fields not in entity
        ItemOutwardDTO responseDto = new ItemOutwardDTO(saved);
        
        // Preserve warehouseId from input
        if (dto.getWarehouseId() != null) {
            responseDto.setWarehouseId(dto.getWarehouseId());
        }
        
        // Preserve machineNo and machineName from input
        if (dto.getMachineNo() != null) {
            responseDto.setMachineNo(dto.getMachineNo());
        }
        if (dto.getMachineName() != null) {
            responseDto.setMachineName(dto.getMachineName());
        }
        
        // Preserve items array structure and original field values
        if (stockDetailsList != null && !stockDetailsList.isEmpty() && saved.getItemOutwardStockDetails() != null) {
            List<ItemOutwardStockDetailsDTO> responseStockDetails = new ArrayList<>();
            for (int i = 0; i < saved.getItemOutwardStockDetails().size() && i < stockDetailsList.size(); i++) {
                ItemOutwardStockDetails entityDetail = saved.getItemOutwardStockDetails().get(i);
                ItemOutwardStockDetailsDTO originalDto = stockDetailsList.get(i);
                ItemOutwardStockDetailsDTO responseDetailDto = new ItemOutwardStockDetailsDTO(entityDetail);
                
                // Preserve original DTO values that are not in entity
                responseDetailDto.setIssuingBags(originalDto.getIssuingBags());
                responseDetailDto.setIssuingQuantity(originalDto.getIssuingQuantity());
                responseDetailDto.setWarehouseId(originalDto.getWarehouseId());
                if (responseDetailDto.getMachineId() == null && originalDto.getMachineId() != null) {
                    responseDetailDto.setMachineId(originalDto.getMachineId());
                }
                
                // Map itemStockDetailId from itemInwardStockDetailsId (support both field names)
                if (originalDto.getItemStockDetailId() != null) {
                    responseDetailDto.setItemStockDetailId(originalDto.getItemStockDetailId());
                } else if (originalDto.getItemInwardStockDetailsId() != null) {
                    responseDetailDto.setItemStockDetailId(originalDto.getItemInwardStockDetailsId());
                } else if (responseDetailDto.getItemInwardStockDetailsId() != null) {
                    responseDetailDto.setItemStockDetailId(responseDetailDto.getItemInwardStockDetailsId());
                }
                
                // Map unitPerBags from unitPerBag (support both field names)
                if (originalDto.getUnitPerBags() != null) {
                    responseDetailDto.setUnitPerBags(originalDto.getUnitPerBags());
                } else if (originalDto.getUnitPerBag() != null) {
                    responseDetailDto.setUnitPerBags(originalDto.getUnitPerBag());
                } else if (responseDetailDto.getUnitPerBag() != null) {
                    responseDetailDto.setUnitPerBags(responseDetailDto.getUnitPerBag());
                }
                
                responseStockDetails.add(responseDetailDto);
            }
            responseDto.setItemOutwardStockDetails(responseStockDetails);
            // Also set items for consistency
            responseDto.setItems(responseStockDetails);
        }
        
        // Update available quantities in ItemInwardStockDetails after saving
        updateAvailableQuantitiesInInwardStockDetails(saved.getItemOutwardStockDetails());
        
        return responseDto;
    }

    public ItemOutwardDTO getItemOutwardById(Long id) {
        ItemOutward entity = itemOutwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item Outward not found with ID: " + id));
        return enrichItemOutwardDTO(new ItemOutwardDTO(entity));
    }

    public ItemOutwardDTO getItemOutwardByIdAndCompany(Long id, Long companyId) {
        ItemOutward entity = itemOutwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Item Outward not found with ID: " + id + " for company: " + companyId));
        return enrichItemOutwardDTO(new ItemOutwardDTO(entity));
    }
    
    // Helper method to enrich DTO with derived values for GET responses
    private ItemOutwardDTO enrichItemOutwardDTO(ItemOutwardDTO dto) {
        // Derive warehouseId from warehouse name
        if (dto.getWarehouse() != null && dto.getWarehouseId() == null) {
            warehouseRepository.findAll().stream()
                    .filter(w -> w.getWarehouseName().equals(dto.getWarehouse()))
                    .findFirst()
                    .ifPresent(warehouse -> dto.setWarehouseId(warehouse.getId()));
        }
        
        // Enrich child stock details
        if (dto.getItemOutwardStockDetails() != null && !dto.getItemOutwardStockDetails().isEmpty()) {
            List<ItemOutwardStockDetailsDTO> enrichedDetails = new ArrayList<>();
            for (ItemOutwardStockDetailsDTO detailDto : dto.getItemOutwardStockDetails()) {
                ItemOutwardStockDetailsDTO enriched = enrichStockDetailDTO(detailDto);
                enrichedDetails.add(enriched);
            }
            dto.setItemOutwardStockDetails(enrichedDetails);
            dto.setItems(enrichedDetails);
        }
        
        return dto;
    }
    
    // Helper method to enrich stock detail DTO with derived values
    private ItemOutwardStockDetailsDTO enrichStockDetailDTO(ItemOutwardStockDetailsDTO dto) {
        // Derive warehouseId from warehouse name
        if (dto.getWarehouse() != null && dto.getWarehouseId() == null) {
            warehouseRepository.findAll().stream()
                    .filter(w -> w.getWarehouseName().equals(dto.getWarehouse()))
                    .findFirst()
                    .ifPresent(warehouse -> dto.setWarehouseId(warehouse.getId()));
        }
        
        // Map itemInwardStockDetailsId to itemStockDetailId
        if (dto.getItemInwardStockDetailsId() != null && dto.getItemStockDetailId() == null) {
            dto.setItemStockDetailId(dto.getItemInwardStockDetailsId());
        }
        
        // Map unitPerBag to unitPerBags
        if (dto.getUnitPerBag() != null && dto.getUnitPerBags() == null) {
            dto.setUnitPerBags(dto.getUnitPerBag());
        }
        
        // issuingBags and issuingQuantity are now stored in entity, so they will be populated from entity
        
        return dto;
    }

    public List<ItemOutwardDTO> getAllItemOutwards() {
        return itemOutwardRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ItemOutward::getId).reversed())
                .map(ItemOutwardDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<ItemOutwardDTO> getAllItemOutwardsWithPagination(Long offset, Long limit) {
        List<ItemOutwardDTO> all = itemOutwardRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ItemOutward::getId).reversed())
                .map(ItemOutwardDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(all, offset, limit);
    }

    public PaginatedResponse<ItemOutwardDTO> getAllItemOutwardsByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        List<ItemOutwardDTO> all = itemOutwardRepository.findByCompanyIdOrderByIdDesc(companyId)
                .stream()
                .map(ItemOutwardDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(all, offset, limit);
    }

    @Transactional
    public ItemOutwardDTO updateItemOutward(Long id, ItemOutwardDTO dto) {
        ItemOutward existing = itemOutwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item Outward not found with ID: " + id));

        // Update fields
        if (dto.getOutwardDate() != null) {
            existing.setOutwardDate(dto.getOutwardDate());
            existing.setFinancialYearId(getFinancialYearId(dto.getOutwardDate()));
        }

        // Update vendor name if vendor ID changed
        if (dto.getVendorId() != null && !dto.getVendorId().equals(existing.getVendorId())) {
            Vendor vendor = vendorRepository.findById(dto.getVendorId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendor not found with ID: " + dto.getVendorId()));
            existing.setVendorId(dto.getVendorId());
            existing.setItemVendorName(vendor.getVendorName());
        }

        // Handle child entities - support both "items" and "itemOutwardStockDetails" field names
        List<ItemOutwardStockDetailsDTO> stockDetailsList = dto.getItems() != null ? dto.getItems() : dto.getItemOutwardStockDetails();

        // Update department - use provided department or fetch from Item Master
        if (dto.getDepartment() != null && !dto.getDepartment().isEmpty()) {
            existing.setDepartment(dto.getDepartment());
        } else {
            // Update department if stock details changed
            if (stockDetailsList != null && !stockDetailsList.isEmpty()) {
                ItemOutwardStockDetailsDTO firstDetail = stockDetailsList.get(0);
                if (firstDetail.getItemId() != null) {
                    ItemMaster itemMaster = itemMasterRepository.findById(firstDetail.getItemId())
                            .orElse(null);
                    if (itemMaster != null && itemMaster.getDepartment() != null) {
                        existing.setDepartment(itemMaster.getDepartment());
                    }
                }
            }
        }

        // Handle machineName - combine machineNo and machineName if provided
        if (dto.getMachineNo() != null || dto.getMachineName() != null) {
            String combinedMachineName = "";
            if (dto.getMachineNo() != null) {
                combinedMachineName = dto.getMachineNo();
            }
            if (dto.getMachineName() != null) {
                if (!combinedMachineName.isEmpty()) {
                    combinedMachineName += " " + dto.getMachineName();
                } else {
                    combinedMachineName = dto.getMachineName();
                }
            }
            existing.setMachineName(combinedMachineName.trim());
        } else if (dto.getMachineName() != null) {
            existing.setMachineName(dto.getMachineName());
        }

        if (dto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with ID: " + dto.getWarehouseId()));
            existing.setWarehouse(warehouse.getWarehouseName());
        } else if (dto.getWarehouse() != null) {
            existing.setWarehouse(dto.getWarehouse());
        }

        if (dto.getCompanyId() != null) {
            existing.setCompanyId(dto.getCompanyId());
        }
        if (dto.getActiveFlag() != null) {
            existing.setActiveFlag(dto.getActiveFlag());
        }

        // Update child entities
        if (stockDetailsList != null) {
            // Capture old stock details to update their available quantities after they are removed/updated
            List<ItemOutwardStockDetails> oldDetails = new ArrayList<>(existing.getItemOutwardStockDetails());
            
            // Remove existing child entities
            existing.getItemOutwardStockDetails().clear();
            
            // Add new child entities
            for (ItemOutwardStockDetailsDTO detailDto : stockDetailsList) {
                ItemOutwardStockDetails detail = mapStockDetailDtoToEntity(detailDto);
                detail.setItemOutward(existing);
                existing.getItemOutwardStockDetails().add(detail);
            }
            
            ItemOutward updated = itemOutwardRepository.save(existing);
            itemOutwardRepository.flush(); // Ensure orphans are deleted before recalculation
            
            // Collect all affected inward stock details (both from old and new items)
            List<ItemOutwardStockDetails> allAffected = new ArrayList<>(oldDetails);
            allAffected.addAll(updated.getItemOutwardStockDetails());
            
            // Update available quantities in ItemInwardStockDetails
            updateAvailableQuantitiesInInwardStockDetails(allAffected);
            
            return createResponseDto(updated, dto, stockDetailsList);
        }

        ItemOutward updated = itemOutwardRepository.save(existing);
        return createResponseDto(updated, dto, stockDetailsList);
    }

    private ItemOutwardDTO createResponseDto(ItemOutward saved, ItemOutwardDTO originalInputDto, List<ItemOutwardStockDetailsDTO> stockDetailsList) {
        ItemOutwardDTO responseDto = new ItemOutwardDTO(saved);
        
        // Preserve warehouseId from input
        if (originalInputDto.getWarehouseId() != null) {
            responseDto.setWarehouseId(originalInputDto.getWarehouseId());
        }
        
        // Preserve machineNo and machineName from input
        if (originalInputDto.getMachineNo() != null) {
            responseDto.setMachineNo(originalInputDto.getMachineNo());
        }
        if (originalInputDto.getMachineName() != null) {
            responseDto.setMachineName(originalInputDto.getMachineName());
        }
        
        // Preserve items array structure and original field values
        if (stockDetailsList != null && !stockDetailsList.isEmpty() && saved.getItemOutwardStockDetails() != null) {
            List<ItemOutwardStockDetailsDTO> responseStockDetails = new ArrayList<>();
            for (int i = 0; i < saved.getItemOutwardStockDetails().size() && i < stockDetailsList.size(); i++) {
                ItemOutwardStockDetails entityDetail = saved.getItemOutwardStockDetails().get(i);
                ItemOutwardStockDetailsDTO originalDto = stockDetailsList.get(i);
                ItemOutwardStockDetailsDTO responseDetailDto = new ItemOutwardStockDetailsDTO(entityDetail);
                
                // Preserve original DTO values that are not in entity
                responseDetailDto.setIssuingBags(originalDto.getIssuingBags());
                responseDetailDto.setIssuingQuantity(originalDto.getIssuingQuantity());
                responseDetailDto.setWarehouseId(originalDto.getWarehouseId());
                if (responseDetailDto.getMachineId() == null && originalDto.getMachineId() != null) {
                    responseDetailDto.setMachineId(originalDto.getMachineId());
                }
                
                // Map itemStockDetailId from itemInwardStockDetailsId (support both field names)
                if (originalDto.getItemStockDetailId() != null) {
                    responseDetailDto.setItemStockDetailId(originalDto.getItemStockDetailId());
                } else if (originalDto.getItemInwardStockDetailsId() != null) {
                    responseDetailDto.setItemStockDetailId(originalDto.getItemInwardStockDetailsId());
                } else if (responseDetailDto.getItemInwardStockDetailsId() != null) {
                    responseDetailDto.setItemStockDetailId(responseDetailDto.getItemInwardStockDetailsId());
                }
                
                // Map unitPerBags from unitPerBag (support both field names)
                if (originalDto.getUnitPerBags() != null) {
                    responseDetailDto.setUnitPerBags(originalDto.getUnitPerBags());
                } else if (originalDto.getUnitPerBag() != null) {
                    responseDetailDto.setUnitPerBags(originalDto.getUnitPerBag());
                } else if (responseDetailDto.getUnitPerBag() != null) {
                    responseDetailDto.setUnitPerBags(responseDetailDto.getUnitPerBag());
                }
                
                responseStockDetails.add(responseDetailDto);
            }
            responseDto.setItemOutwardStockDetails(responseStockDetails);
            // Also set items for consistency
            responseDto.setItems(responseStockDetails);
        }
        
        return responseDto;
    }

    @Transactional
    public ItemOutwardDTO updateItemOutwardByCompany(Long id, Long companyId, ItemOutwardDTO dto) {
        ItemOutward existing = itemOutwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Item Outward not found with ID: " + id + " for company: " + companyId));

        // Update fields
        if (dto.getOutwardDate() != null) {
            existing.setOutwardDate(dto.getOutwardDate());
            existing.setFinancialYearId(getFinancialYearId(dto.getOutwardDate()));
        }

        // Update vendor name if vendor ID changed
        if (dto.getVendorId() != null && !dto.getVendorId().equals(existing.getVendorId())) {
            Vendor vendor = vendorRepository.findById(dto.getVendorId())
                    .orElseThrow(() -> new EntityNotFoundException("Vendor not found with ID: " + dto.getVendorId()));
            existing.setVendorId(dto.getVendorId());
            existing.setItemVendorName(vendor.getVendorName());
        }

        // Handle child entities - support both "items" and "itemOutwardStockDetails" field names
        List<ItemOutwardStockDetailsDTO> stockDetailsList = dto.getItems() != null ? dto.getItems() : dto.getItemOutwardStockDetails();

        // Update department - use provided department or fetch from Item Master
        if (dto.getDepartment() != null && !dto.getDepartment().isEmpty()) {
            existing.setDepartment(dto.getDepartment());
        } else {
            // Update department if stock details changed
            if (stockDetailsList != null && !stockDetailsList.isEmpty()) {
                ItemOutwardStockDetailsDTO firstDetail = stockDetailsList.get(0);
                if (firstDetail.getItemId() != null) {
                    ItemMaster itemMaster = itemMasterRepository.findById(firstDetail.getItemId())
                            .orElse(null);
                    if (itemMaster != null && itemMaster.getDepartment() != null) {
                        existing.setDepartment(itemMaster.getDepartment());
                    }
                }
            }
        }

        // Handle machineName - combine machineNo and machineName if provided
        if (dto.getMachineNo() != null || dto.getMachineName() != null) {
            String combinedMachineName = "";
            if (dto.getMachineNo() != null) {
                combinedMachineName = dto.getMachineNo();
            }
            if (dto.getMachineName() != null) {
                if (!combinedMachineName.isEmpty()) {
                    combinedMachineName += " " + dto.getMachineName();
                } else {
                    combinedMachineName = dto.getMachineName();
                }
            }
            existing.setMachineName(combinedMachineName.trim());
        } else if (dto.getMachineName() != null) {
            existing.setMachineName(dto.getMachineName());
        }

        if (dto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with ID: " + dto.getWarehouseId()));
            existing.setWarehouse(warehouse.getWarehouseName());
        } else if (dto.getWarehouse() != null) {
            existing.setWarehouse(dto.getWarehouse());
        }

        if (dto.getCompanyId() != null) {
            existing.setCompanyId(dto.getCompanyId());
        }
        if (dto.getActiveFlag() != null) {
            existing.setActiveFlag(dto.getActiveFlag());
        }

        // Update child entities
        if (stockDetailsList != null) {
            // Capture old stock details to update their available quantities after they are removed/updated
            List<ItemOutwardStockDetails> oldDetails = new ArrayList<>(existing.getItemOutwardStockDetails());
            
            // Remove existing child entities
            existing.getItemOutwardStockDetails().clear();
            
            // Add new child entities
            for (ItemOutwardStockDetailsDTO detailDto : stockDetailsList) {
                ItemOutwardStockDetails detail = mapStockDetailDtoToEntity(detailDto);
                detail.setItemOutward(existing);
                existing.getItemOutwardStockDetails().add(detail);
            }
            
            ItemOutward updated = itemOutwardRepository.save(existing);
            itemOutwardRepository.flush(); // Ensure orphans are deleted before recalculation
            
            // Collect all affected inward stock details (both from old and new items)
            List<ItemOutwardStockDetails> allAffected = new ArrayList<>(oldDetails);
            allAffected.addAll(updated.getItemOutwardStockDetails());
            
            // Update available quantities in ItemInwardStockDetails
            updateAvailableQuantitiesInInwardStockDetails(allAffected);
            
            return createResponseDto(updated, dto, stockDetailsList);
        }

        ItemOutward updated = itemOutwardRepository.save(existing);
        return createResponseDto(updated, dto, stockDetailsList);
    }

    @Transactional
    public void deleteItemOutward(Long id) {
        ItemOutward existing = itemOutwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item Outward not found with ID: " + id));
        
        // Capture stock details to update available quantities after deletion
        List<ItemOutwardStockDetails> stockDetails = new ArrayList<>(existing.getItemOutwardStockDetails());
        
        try {
            itemOutwardRepository.delete(existing);
            itemOutwardRepository.flush(); // Ensure records are deleted before recalculation
            
            // Update available quantities in ItemInwardStockDetails
            updateAvailableQuantitiesInInwardStockDetails(stockDetails);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Item Outward because it is referenced by other entities");
        }
    }

    @Transactional
    public void deleteItemOutwardByCompany(Long id, Long companyId) {
        ItemOutward existing = itemOutwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Item Outward not found with ID: " + id + " for company: " + companyId));
        
        // Capture stock details to update available quantities after deletion
        List<ItemOutwardStockDetails> stockDetails = new ArrayList<>(existing.getItemOutwardStockDetails());
        
        try {
            itemOutwardRepository.delete(existing);
            itemOutwardRepository.flush(); // Ensure records are deleted before recalculation
            
            // Update available quantities in ItemInwardStockDetails
            updateAvailableQuantitiesInInwardStockDetails(stockDetails);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Item Outward because it is referenced by other entities");
        }
    }

    // Get machine name suggestions
    public List<String> getMachineNameSuggestions() {
        return itemOutwardRepository.findDistinctMachineNames();
    }

    // Get item stock details by filter (vendorId, warehouseId, companyId)
    public List<java.util.Map<String, Object>> getItemStockDetailsByFilter(Long vendorId, Long warehouseId, Long companyId) {
        List<ItemInwardStockDetails> stockDetails = itemInwardStockDetailsRepository.findByFilters(vendorId, warehouseId, companyId);
        
        return stockDetails.stream()
                .map(detail -> {
                    java.util.Map<String, Object> result = new java.util.HashMap<>();
                    
                    // Add itemInwardStockDetails (appears once at top level)
                    result.put("itemInwardStockDetails", new com.tech3.erp.dto.ItemInwardStockDetailsDto(detail));
                    
                    // Add itemInventory without nested itemInwardStockDetails array
                    if (detail.getItemInventory() != null) {
                        ItemInventory inventory = detail.getItemInventory();
                        java.util.Map<String, Object> inventoryMap = new java.util.HashMap<>();
                        inventoryMap.put("id", inventory.getId());
                        inventoryMap.put("orderItemId", inventory.getOrderItemId());
                        inventoryMap.put("inwardNo", inventory.getInwardNo());
                        inventoryMap.put("companyId", inventory.getCompanyId());
                        inventoryMap.put("inwardDate", inventory.getInwardDate());
                        inventoryMap.put("financialYear", inventory.getFinancialYear());
                        inventoryMap.put("dcNo", inventory.getDcNo());
                        inventoryMap.put("dcDate", inventory.getDcDate());
                        inventoryMap.put("orderId", inventory.getOrderId());
                        inventoryMap.put("orderNo", inventory.getOrderNo());
                        inventoryMap.put("vendorId", inventory.getVendorId());
                        // Note: itemInwardStockDetails is NOT included in itemInventory to avoid duplication
                        result.put("itemInventory", inventoryMap);
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }

    // Helper method to map stock detail DTO to entity
    private ItemOutwardStockDetails mapStockDetailDtoToEntity(ItemOutwardStockDetailsDTO dto) {
        ItemOutwardStockDetails detail = new ItemOutwardStockDetails();
        
        detail.setInwardNo(dto.getInwardNo());
        detail.setItemName(dto.getItemName());
        detail.setItemCode(dto.getItemCode());
        
        // Handle warehouse - fetch from Warehouse Master if warehouseId is provided
        if (dto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with ID: " + dto.getWarehouseId()));
            detail.setWarehouse(warehouse.getWarehouseName());
        } else if (dto.getWarehouse() != null) {
            detail.setWarehouse(dto.getWarehouse());
        } else {
            throw new IllegalArgumentException("Warehouse is required for item stock details. Please provide either warehouseId or warehouse.");
        }
        
        detail.setAvailableBags(dto.getAvailableBags());
        // Handle unitPerBag - support both unitPerBag and unitPerBags field names
        if (dto.getUnitPerBags() != null) {
            detail.setUnitPerBag(dto.getUnitPerBags());
        } else {
            detail.setUnitPerBag(dto.getUnitPerBag());
        }
        detail.setAvailableQuantity(dto.getAvailableQuantity());
        
        // Handle itemInwardStockDetailsId - support both itemInwardStockDetailsId and itemStockDetailId field names
        if (dto.getItemStockDetailId() != null) {
            detail.setItemInwardStockDetailsId(dto.getItemStockDetailId());
        } else {
            detail.setItemInwardStockDetailsId(dto.getItemInwardStockDetailsId());
        }
        detail.setItemId(dto.getItemId());
        
        // Set issuingBags and issuingQuantity
        detail.setIssuingBags(dto.getIssuingBags());
        detail.setIssuingQuantity(dto.getIssuingQuantity());
        detail.setMachineId(dto.getMachineId());
        
        // Fetch return_item_id from codes_details where id = 55
        if (dto.getReturnItemId() != null) {
            detail.setReturnItemId(dto.getReturnItemId());
        } else {
            // Fetch from codes_details with ID 55
            Optional<CodesDetail> codesDetail = codesDetailRepository.findById(55L);
            if (codesDetail.isPresent()) {
                detail.setReturnItemId(codesDetail.get().getId());
            }
        }

        // Fetch data from Item Inventory if inward_no is provided
        if (dto.getInwardNo() != null) {
            Optional<ItemInventory> itemInventory = itemInventoryRepository.findByInwardNo(dto.getInwardNo());
            
            if (itemInventory.isPresent()) {
                // Fetch item name and code from Item Inward Stock Details
                if (dto.getItemInwardStockDetailsId() != null) {
                    Optional<ItemInwardStockDetails> stockDetail = itemInwardStockDetailsRepository.findById(dto.getItemInwardStockDetailsId());
                    if (stockDetail.isPresent()) {
                        ItemInwardStockDetails inwardStockDetail = stockDetail.get();
                        if (detail.getItemName() == null) {
                            // Get item name from Item Master
                            if (inwardStockDetail.getItemId() != null) {
                                Optional<ItemMaster> itemMaster = itemMasterRepository.findById(inwardStockDetail.getItemId());
                                if (itemMaster.isPresent()) {
                                    detail.setItemName(itemMaster.get().getItemName());
                                    detail.setItemCode(itemMaster.get().getItemCode());
                                }
                            }
                        }
                        detail.setAvailableBags(inwardStockDetail.getAvailableBags() != null ? inwardStockDetail.getAvailableBags().intValue() : null);
                        detail.setAvailableQuantity(inwardStockDetail.getAvailableQty() != null ? inwardStockDetail.getAvailableQty().intValue() : null);
                        detail.setUnitPerBag(inwardStockDetail.getKgsPerBag() != null ? inwardStockDetail.getKgsPerBag().intValue() : null);
                    }
                }
            }
        }

        return detail;
    }

    // Generate outward number based on number format configuration
    private String generateOutwardNo(Long companyId) {
        // Try to find configuration for Item Outward
        Optional<NumberFormatConfiguration> config = Optional.empty();
        if (companyId != null) {
            config = numberFormatConfigurationRepository.findByScreenNameAndTabNameAndCompanyId("Item Outward", null, companyId);
        }
        
        if (config.isEmpty()) {
            config = numberFormatConfigurationRepository.findByScreenNameAndTabName("Item Outward", null);
        }
        
        if (config.isEmpty()) {
            // Fallback: generate simple number
            List<ItemOutward> existing = itemOutwardRepository.findByOutwardNoStartingWith("IO-");
            int maxNumber = 0;
            for (ItemOutward record : existing) {
                try {
                    String numStr = record.getOutwardNo().substring(3);
                    int num = Integer.parseInt(numStr);
                    if (num > maxNumber) {
                        maxNumber = num;
                    }
                } catch (Exception e) {}
            }
            return String.format("IO-%04d", maxNumber + 1);
        }

        NumberFormatConfiguration configuration = config.get();
        String prefix = configuration.getPrefix();
        String separator = configuration.getSeparator();
        
        // Use financial year from configuration if available, otherwise calculate
        String financialYear = configuration.getFinancialYear();
        if (financialYear == null || financialYear.trim().isEmpty()) {
            financialYear = getFinancialYearId(LocalDate.now());
        }
        
        // Find existing numbers with same prefix and financial year
        String searchPrefix = prefix + separator + financialYear + separator;
        List<ItemOutward> existing = itemOutwardRepository.findByOutwardNoStartingWith(searchPrefix);
        
        int maxNumber = 0;
        for (ItemOutward record : existing) {
            try {
                String outwardNo = record.getOutwardNo();
                if (outwardNo != null && outwardNo.length() > searchPrefix.length()) {
                    String numStr = outwardNo.substring(searchPrefix.length());
                    int num = Integer.parseInt(numStr.trim());
                    if (num > maxNumber) {
                        maxNumber = num;
                    }
                }
            } catch (NumberFormatException e) {
                // Ignore invalid numbers
            }
        }
        
        int nextNumber = maxNumber + 1;
        
        // Use sequence number length from configuration
        int seqLength = 4;
        if (configuration.getSequenceNumber() != null && !configuration.getSequenceNumber().trim().isEmpty()) {
            seqLength = configuration.getSequenceNumber().trim().length();
        }
        
        String sequenceNumber = String.format("%0" + seqLength + "d", nextNumber);
        
        return prefix + separator + financialYear + separator + sequenceNumber;
    }

    // Calculate financial year (Indian FY: April to March)
    private String getFinancialYearId(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        
        int year = date.getYear();
        int month = date.getMonthValue();
        int startYear;
        int endYear;
        
        if (month >= 4) {
            startYear = year;
            endYear = year + 1;
        } else {
            startYear = year - 1;
            endYear = year;
        }
        
        String endYearLastTwo = String.format("%02d", endYear % 100);
        return startYear + "-" + endYearLastTwo;
    }

    /**
     * Update available quantities in ItemInwardStockDetails after issuing items
     * Formula: accepted_qty - totalIssuedQty = available_qty
     * Formula: inwardBags - totalIssuedBags = available_bags
     */
    private void updateAvailableQuantitiesInInwardStockDetails(List<ItemOutwardStockDetails> outwardDetails) {
        if (outwardDetails == null || outwardDetails.isEmpty()) {
            return;
        }

        // Collect unique itemInwardStockDetailsIds
        Set<Long> inwardStockDetailsIds = new HashSet<>();
        for (ItemOutwardStockDetails outwardDetail : outwardDetails) {
            if (outwardDetail.getItemInwardStockDetailsId() != null) {
                inwardStockDetailsIds.add(outwardDetail.getItemInwardStockDetailsId());
            } else if (outwardDetail.getInwardNo() != null && outwardDetail.getItemId() != null) {
                List<ItemInwardStockDetails> matches = itemInwardStockDetailsRepository.findByInwardNoAndItemId(outwardDetail.getInwardNo(), outwardDetail.getItemId());
                if (matches != null) {
                    for (ItemInwardStockDetails match : matches) {
                        inwardStockDetailsIds.add(match.getId());
                    }
                }
            }
        }

        // Update each ItemInwardStockDetails
        for (Long inwardStockDetailsId : inwardStockDetailsIds) {
            Optional<ItemInwardStockDetails> inwardDetailOpt = itemInwardStockDetailsRepository.findById(inwardStockDetailsId);
            if (inwardDetailOpt.isPresent()) {
                ItemInwardStockDetails inwardDetail = inwardDetailOpt.get();
                
                // Calculate total issued quantity and bags
                BigDecimal totalIssuedQty = calculateTotalIssuedQuantity(inwardStockDetailsId);
                BigDecimal totalIssuedBags = calculateTotalIssuedBags(inwardStockDetailsId);
                
                // Base starting qty = acceptedQty if available, else inwardQty
                BigDecimal baseQty = inwardDetail.getAcceptedQty() != null ? inwardDetail.getAcceptedQty() 
                                    : (inwardDetail.getInwardQty() != null ? inwardDetail.getInwardQty() : BigDecimal.ZERO);
                BigDecimal newAvailableQty = baseQty.subtract(totalIssuedQty);
                if (newAvailableQty.compareTo(BigDecimal.ZERO) < 0) {
                    newAvailableQty = BigDecimal.ZERO;
                }
                inwardDetail.setAvailableQty(newAvailableQty);
                
                // Base starting bags = inwardBags
                BigDecimal baseBags = inwardDetail.getInwardBags() != null ? inwardDetail.getInwardBags() : BigDecimal.ZERO;
                BigDecimal newAvailableBags = baseBags.subtract(totalIssuedBags);
                if (newAvailableBags.compareTo(BigDecimal.ZERO) < 0) {
                    newAvailableBags = BigDecimal.ZERO;
                }
                inwardDetail.setAvailableBags(newAvailableBags);
                
                itemInwardStockDetailsRepository.save(inwardDetail);
            }
        }
    }

    /**
     * Calculate total issued quantity for an ItemInwardStockDetails by summing all ItemOutwardStockDetails
     */
    private BigDecimal calculateTotalIssuedQuantity(Long itemInwardStockDetailsId) {
        if (itemInwardStockDetailsId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalIssued = itemOutwardStockDetailsRepository.getTotalIssuedQuantityByItemInwardStockDetailsId(itemInwardStockDetailsId);
        return totalIssued != null ? totalIssued : BigDecimal.ZERO;
    }

    /**
     * Calculate total issued bags for an ItemInwardStockDetails by summing all ItemOutwardStockDetails
     */
    private BigDecimal calculateTotalIssuedBags(Long itemInwardStockDetailsId) {
        if (itemInwardStockDetailsId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalIssued = itemOutwardStockDetailsRepository.getTotalIssuedBagsByItemInwardStockDetailsId(itemInwardStockDetailsId);
        return totalIssued != null ? totalIssued : BigDecimal.ZERO;
    }
}

