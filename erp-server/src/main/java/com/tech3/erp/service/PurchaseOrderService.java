// package com.tech3.erp.service;
// import com.tech3.erp.entity.Company; // Assume you have a Company entity
// import com.tech3.erp.entity.ItemMaster;
// import com.tech3.erp.dto.PurchaseOrderDTO;
// import com.tech3.erp.dto.PurchaseOrderItemDTO;
// import com.tech3.erp.dto.VendorDTO;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.entity.PurchaseOrder;
// import com.tech3.erp.entity.PurchaseOrderItem;
// import com.tech3.erp.entity.SalesOrder;
// import com.tech3.erp.entity.SubCategory;
// import com.tech3.erp.entity.YarnMaster;
// import com.tech3.erp.repository.CompanyRepository; // Assume this exists
// import com.tech3.erp.repository.PurchaseOrderRepository;
// import com.tech3.erp.repository.VendorRepository;
// import com.tech3.erp.repository.YarnMasterRepository;
// import com.tech3.erp.repository.SubCategoryRepository;
// import com.tech3.erp.repository.PurchaseReturnRepository;
// import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
// import com.tech3.erp.repository.PurchaseInwardRepository;
// import com.tech3.erp.repository.ItemMasterRepository;
// import com.tech3.erp.repository.SalesOrderRepository;
// import com.tech3.erp.util.PaginationHelper;

// import jakarta.persistence.EntityNotFoundException;

// import com.tech3.erp.repository.PurchaseOrderItemRepository;
// import com.tech3.erp.service.ReferenceValidationService;

// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.tech3.erp.repository.PurchaseInwardItemRepository;
// import java.math.BigDecimal;


// import java.time.LocalDate;
// import java.time.ZoneId;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Objects;
// import java.util.Optional;
// import java.util.function.Function;
// import java.util.stream.Collectors;

// @Service
// public class PurchaseOrderService {

//     private final PurchaseOrderRepository purchaseOrderRepository;
//     private final PurchaseOrderItemRepository purchaseOrderItemRepository;
//     private final VendorRepository vendorRepository;
//     private final CompanyRepository companyRepository;
//     private final SubCategoryRepository subCategoryRepository;
    
//     private final YarnMasterRepository yarnMasterRepository;
//     private final PurchaseReturnRepository purchaseReturnRepository;
//     private final YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
//     private final PurchaseInwardRepository purchaseInwardRepository;
//     private final ItemMasterRepository itemMasterRepository;
//     private final ReferenceValidationService referenceValidationService;
//     private final SalesOrderRepository salesOrderRepository;

//     private final PurchaseInwardItemRepository purchaseInwardItemRepository;

    


//     public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
//                                 PurchaseOrderItemRepository purchaseOrderItemRepository, 
//                                 VendorRepository vendorRepository, 
//                                 CompanyRepository companyRepository,
//                                 SubCategoryRepository subCategoryRepository,    
//                                 YarnMasterRepository yarnMasterRepository,
//                                 PurchaseReturnRepository purchaseReturnRepository,
//                                 YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository,
//                                 PurchaseInwardRepository purchaseInwardRepository,
//                                 ItemMasterRepository itemMasterRepository,
//                                 ReferenceValidationService referenceValidationService,
//                                 PurchaseInwardItemRepository purchaseInwardItemRepository,
//                                 SalesOrderRepository salesOrderRepository
// ) {
//         this.purchaseOrderRepository = purchaseOrderRepository;
//         this.purchaseOrderItemRepository = purchaseOrderItemRepository;
//         this.vendorRepository = vendorRepository;
//         this.companyRepository = companyRepository;
//         this.subCategoryRepository = subCategoryRepository;
//         this.yarnMasterRepository = yarnMasterRepository;
//         this.purchaseReturnRepository = purchaseReturnRepository;
//         this.yarnInwardStockDetailsRepository = yarnInwardStockDetailsRepository;
//         this.purchaseInwardRepository = purchaseInwardRepository;
//         this.itemMasterRepository = itemMasterRepository;
//         this.referenceValidationService = referenceValidationService;
//         this.salesOrderRepository = salesOrderRepository;
//         this.purchaseInwardItemRepository = purchaseInwardItemRepository;
//     }
    
//     @Transactional
//     public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO dto) {
    	
//     	if (dto.getCompanyId() == null) {
//             throw new IllegalArgumentException("Company ID is required for purchase order generation");
//         }
    	
//         PurchaseOrder entity = new PurchaseOrder();
//         entity.setCompanyId(dto.getCompanyId());

//         // Generate poNo if not provided (for create mode)
//         // if (dto.getPoNo() == null || dto.getPoNo().trim().isEmpty()) {
//         //     String generatedPoNo = generatePoNo(dto.getCompanyId(), dto.getPoDate());
//         //     dto.setPoNo(generatedPoNo);
//         // }
        
//         mapDtoToEntity(dto, entity);

//         // Set items properly
//         if (dto.getPurchaseOrderItemsDtl() != null && !dto.getPurchaseOrderItemsDtl().isEmpty()) {
//             List<PurchaseOrderItem> items = dto.getPurchaseOrderItemsDtl().stream().map(i -> {
//                 ensureSalesOrderReferenceExists(i.getSalesOrderId(), i.getSalesOrderNo());
//                 // Resolve millName to millNameId if needed
//                 if (i.getMillName() != null && i.getMillNameId() == null) {
//                     Long millNameId = resolveMillNameToId(i.getMillName());
//                     i.setMillNameId(millNameId);
//                 }
//                 PurchaseOrderItem item = new PurchaseOrderItem(i);
//                 item.setPurchaseOrder(entity); // 🔥 Crucial: set back-reference
//                 return item;
//             }).collect(Collectors.toList());

//             entity.setItems(items); // 🔥 Crucial: attach to parent
//         }

//         // Only save parent entity
//         PurchaseOrder saved = purchaseOrderRepository.save(entity); // cascade saves items too
//         PurchaseOrderDTO response = new PurchaseOrderDTO(saved);
//         populateYarnNames(response);
//         return response;
        
//     }
   
//     public PurchaseOrderDTO getPurchaseOrderById(Long id) {
//         return getPurchaseOrderById(id, null);
//     }
//     public PurchaseOrderDTO getPurchaseOrderById(Long id ,Long excludeInwardId) {
//     PurchaseOrder entity = purchaseOrderRepository.findByIdWithItems(id);
//     if (entity == null) {
//         throw new IllegalArgumentException("PurchaseOrder not found with ID: " + id);
//     }
//     PurchaseOrderDTO dto = new PurchaseOrderDTO(entity);
//     populateYarnNames(dto);
//     populateReceivedQuantities(dto, excludeInwardId);
//     return dto;
//     }
    
//     private void populateReceivedQuantities(PurchaseOrderDTO dto, Long excludeInwardId) {
//         if (dto.getPurchaseOrderItemsDtl() == null) return;
//         for (PurchaseOrderItemDTO itemDto : dto.getPurchaseOrderItemsDtl()) {
//             if (itemDto.getId() != null) {
//                 BigDecimal received;
//                 if (excludeInwardId != null) {
//                     received = purchaseInwardItemRepository.sumQuantityReceivedByPoItemIdExcludingInward(itemDto.getId(), excludeInwardId);
//                 } else {
//                     received = purchaseInwardItemRepository.sumQuantityReceivedByPoItemId(itemDto.getId());
//                 }
//                 itemDto.setReceivedQuantity(received != null ? received : BigDecimal.ZERO);
//             }
//         }
//     }
    
    
//     public List<PurchaseOrderDTO> getPendingPurchaseOrdersByVendorId(Long vendorId, Long excludeInwardId) {
//         List<PurchaseOrder> orders = purchaseOrderRepository.findByVendorId(vendorId);
//         List<PurchaseOrderDTO> pendingOrders = new ArrayList<>();

//         for (PurchaseOrder order : orders) {
//             boolean isPending = false;
//             for (PurchaseOrderItem item : order.getItems()) {
//                 BigDecimal orderedQty = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;
//                 BigDecimal receivedQty;
                
//                 if (excludeInwardId != null) {
//                     receivedQty = purchaseInwardItemRepository.sumQuantityReceivedByPoItemIdExcludingInward(item.getId(), excludeInwardId);
//                 } else {
//                     receivedQty = purchaseInwardItemRepository.sumQuantityReceivedByPoItemId(item.getId());
//                 }

//                 if (receivedQty == null) receivedQty = BigDecimal.ZERO;

//                 if (receivedQty.compareTo(orderedQty) < 0) {
//                     isPending = true;
//                     break;
//                 }
//             }
//             if (isPending) {
//                 pendingOrders.add(new PurchaseOrderDTO(order));
//             }
//         }

//         populateYarnNames(pendingOrders);
//         return pendingOrders;
//     }


//     private void populateReceivedQuantities(PurchaseOrderDTO dto) {
//         populateReceivedQuantities(dto, null);
//     }

//     private void populateReceivedQuantities(List<PurchaseOrderDTO> dtos) {
//         for (PurchaseOrderDTO dto : dtos) {
//             populateReceivedQuantities(dto);
//         }
//     }

//     public List<PurchaseOrderDTO> getAllPurchaseOrders() {
//         List<Long> ids = purchaseOrderRepository.findAllIds();

// List<PurchaseOrder> orders = purchaseOrderRepository.findByIdsWithItems(ids);
//     if (ids.isEmpty()) {
//     return new ArrayList<>();
// }

// List<PurchaseOrderDTO> dtos = orders.stream()
//         .map(PurchaseOrderDTO::new)
//         .collect(Collectors.toList());
// populateYarnNames(dtos);
// return dtos;
// }

//  public PaginatedResponse<PurchaseOrderDTO> getAllPurchaseOrdersWithPagination(Long offset, Long limit) {
//     return buildPaginatedResponse(
//             purchaseOrderRepository.findAllIds(),
//             offset,
//             limit,
//             ids -> ids.isEmpty() ? new ArrayList<>() : purchaseOrderRepository.findByIdsWithItems(ids)
//     );
// }

//     public PaginatedResponse<PurchaseOrderDTO> getAllPurchaseOrdersByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         return buildPaginatedResponse(
//                 purchaseOrderRepository.findIdsByCompanyId(companyId),
//                 offset,
//                 limit,
//                 ids -> ids.isEmpty() ? new ArrayList<>() : purchaseOrderRepository.findByCompanyIdAndIds(companyId, ids)
//         );
//     }

//     public PurchaseOrderDTO getPurchaseOrderByIdAndCompany(Long id, Long companyId) {
//         return getPurchaseOrderByIdAndCompany(id, companyId, null);
//     }

//     public PurchaseOrderDTO getPurchaseOrderByIdAndCompany(Long id, Long companyId, Long excludeInwardId) {
//         PurchaseOrder entity = purchaseOrderRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found with ID: " + id + " for company: " + companyId));
//         PurchaseOrderDTO dto = new PurchaseOrderDTO(entity);
//         populateYarnNames(dto);
//         populateReceivedQuantities(dto, excludeInwardId);
//         return dto;
//     }

//     public List<PurchaseOrderDTO> getPendingPurchaseOrdersByVendorIdAndCompany(Long vendorId, Long companyId, Long excludeInwardId) {
//         List<PurchaseOrder> orders = purchaseOrderRepository.findByCompanyIdAndVendorId(companyId, vendorId);
//         List<PurchaseOrderDTO> pendingOrders = new ArrayList<>();

//         for (PurchaseOrder order : orders) {
//             boolean isPending = false;
//             for (PurchaseOrderItem item : order.getItems()) {
//                 BigDecimal orderedQty = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;
//                 BigDecimal receivedQty;
                
//                 if (excludeInwardId != null) {
//                     receivedQty = purchaseInwardItemRepository.sumQuantityReceivedByPoItemIdExcludingInward(item.getId(), excludeInwardId);
//                 } else {
//                     receivedQty = purchaseInwardItemRepository.sumQuantityReceivedByPoItemId(item.getId());
//                 }

//                 if (receivedQty == null) receivedQty = BigDecimal.ZERO;

//                 if (receivedQty.compareTo(orderedQty) < 0) {
//                     isPending = true;
//                     break;
//                 }
//             }
//             if (isPending) {
//                 pendingOrders.add(new PurchaseOrderDTO(order));
//             }
//         }

//         populateYarnNames(pendingOrders);
//         return pendingOrders;
//     }

//     public PurchaseOrderDTO updatePurchaseOrder(Long id, PurchaseOrderDTO dto) {
//         PurchaseOrder existing = purchaseOrderRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found with ID: " + id));
        
        
//      // Preserve existing poNo during update
//         dto.setPoNo(existing.getPoNo());

//         // Set companyId if provided (for consistency)
//         if (dto.getCompanyId() != null) {
//             existing.setCompanyId(dto.getCompanyId());
//         }
        
//         // Update main entity fields
//         mapDtoToEntity(dto, existing);
        
//         updatePurchaseOrderItems(existing, dto.getPurchaseOrderItemsDtl());
        
//         PurchaseOrder updated = purchaseOrderRepository.save(existing);
//         PurchaseOrderDTO response = new PurchaseOrderDTO(updated);
//         populateYarnNames(response);
//         return response;
//     }
    
//     public PurchaseOrderDTO updatePurchaseOrderByCompany(Long id, Long companyId, PurchaseOrderDTO dto) {
//         PurchaseOrder existing = purchaseOrderRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found with ID: " + id + " for company: " + companyId));
//         dto.setPoNo(existing.getPoNo());
//         dto.setCompanyId(existing.getCompanyId());
//         mapDtoToEntity(dto, existing);
//         updatePurchaseOrderItems(existing, dto.getPurchaseOrderItemsDtl());
//         PurchaseOrder updated = purchaseOrderRepository.save(existing);
//         PurchaseOrderDTO response = new PurchaseOrderDTO(updated);
//         populateYarnNames(response);
//         return response;
//     }

//     @Transactional
//     public void deletePurchaseOrder(Long id) {
//         PurchaseOrder order = purchaseOrderRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with id: " + id));

//         checkPurchaseOrderReferences(id);
        
//         try {
//             purchaseOrderRepository.deleteById(id);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete Purchase Order because it is referenced by other entities");
//         }
//     }
    
//     @Transactional
//     public void deletePurchaseOrderByCompany(Long id, Long companyId) {
//         PurchaseOrder order = purchaseOrderRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with id: " + id + " for company: " + companyId));
//         checkPurchaseOrderReferences(order.getId());
//         try {
//             purchaseOrderRepository.deleteByIdAndCompanyId(id, companyId);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete Purchase Order because it is referenced by other entities");
//         }
//     }
    
//     private void checkPurchaseOrderReferences(Long purchaseOrderId) {
//         Long purchaseReturnCount = purchaseReturnRepository.countByPurchaseOrderId(purchaseOrderId);
//         if (purchaseReturnCount > 0) {
//             throw new IllegalStateException("The data is already exceeded to other modules - cannot perform operation.");
//         }
        
//         long inwardsForThisOrder = purchaseInwardRepository.findAll().stream()
//                 .filter(pi -> purchaseOrderId.equals(pi.getPurchaseOrderId()))
//                 .count();
//         if (inwardsForThisOrder > 0) {
//             throw new IllegalStateException("The data is already exceeded to other modules - cannot perform operation.");
//         }
        
//         Long yarnStockCount = yarnInwardStockDetailsRepository.countByPurchaseOrderId(purchaseOrderId);
//         if (yarnStockCount > 0) {
//             throw new IllegalStateException("The data is already exceeded to other modules - cannot perform operation.");
//         }
//     }
    
//     public boolean canDeletePurchaseOrder(Long purchaseOrderId) {
//         Long purchaseReturnCount = purchaseReturnRepository.countByPurchaseOrderId(purchaseOrderId);
//         if (purchaseReturnCount > 0) {
//             return false;
//         }
        
//         long inwardsForThisOrder = purchaseInwardRepository.findAll().stream()
//                 .filter(pi -> purchaseOrderId.equals(pi.getPurchaseOrderId()))
//                 .count();
//         if (inwardsForThisOrder > 0) {
//             return false;
//         }
        
//         Long yarnStockCount = yarnInwardStockDetailsRepository.countByPurchaseOrderId(purchaseOrderId);
//         if (yarnStockCount > 0) {
//             return false;
//         }
        
//         return true;
//     }
    
//     public Map<Long, Boolean> canDeletePurchaseOrderItems(Long purchaseOrderId) {
//         Map<Long, Boolean> itemsDeletionStatus = new HashMap<>();
        
//         PurchaseOrder order = purchaseOrderRepository.findById(purchaseOrderId)
//                 .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with id: " + purchaseOrderId));
        
//         if (order.getItems() == null) {
//             return itemsDeletionStatus;
//         }
        
//         for (PurchaseOrderItem item : order.getItems()) {
//             boolean canDelete = true;
            
//             long yarnStockCount = yarnInwardStockDetailsRepository.findByItemId(item.getId()).size();
//             if (yarnStockCount > 0) {
//                 canDelete = false;
//             }
            
//             itemsDeletionStatus.put(item.getId(), canDelete);
//         }
        
//         return itemsDeletionStatus;
//     }
    
//     public boolean canEditPurchaseOrder(Long purchaseOrderId) {
//         Long purchaseReturnCount = purchaseReturnRepository.countByPurchaseOrderId(purchaseOrderId);
//         if (purchaseReturnCount > 0) {
//             return false;
//         }
        
//         long inwardsForThisOrder = purchaseInwardRepository.findAll().stream()
//                 .filter(pi -> purchaseOrderId.equals(pi.getPurchaseOrderId()))
//                 .count();
//         if (inwardsForThisOrder > 0) {
//             return false;
//         }
        
//         Long yarnStockCount = yarnInwardStockDetailsRepository.countByPurchaseOrderId(purchaseOrderId);
//         if (yarnStockCount > 0) {
//             return false;
//         }
        
//         return true;
//     }

//     public List<PurchaseOrderDTO> getPurchaseOrdersByVendorId(Long vendorId) {
//         List<PurchaseOrder> orders = purchaseOrderRepository.findByVendorId(vendorId);
//         if (orders.isEmpty()) {
//             throw new IllegalArgumentException("No purchase orders found for Vendor ID: " + vendorId);
//         }
//         List<PurchaseOrderDTO> dtos = orders.stream().map(PurchaseOrderDTO::new).toList();
//         populateYarnNames(dtos);
//         return dtos;
//     }

//     public List<PurchaseOrderDTO> getPurchaseOrdersByVendorIdAndCompany(Long vendorId, Long companyId) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         List<PurchaseOrder> orders = purchaseOrderRepository.findByCompanyIdAndVendorId(companyId, vendorId);
//         if (orders.isEmpty()) {
//             throw new IllegalArgumentException("No purchase orders found for Vendor ID: " + vendorId + " within company ID: " + companyId);
//         }
//         List<PurchaseOrderDTO> dtos = orders.stream().map(PurchaseOrderDTO::new).toList();
//         populateYarnNames(dtos);
//         return dtos;
//     }

    
    
//     private PaginatedResponse<PurchaseOrderDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit, Function<List<Long>, List<PurchaseOrder>> fetcher) {
//         long off = offset != null && offset > 0 ? offset : 0L;
//         long lim = (limit != null && limit > 0) ? limit : 10L;
//         long total = allIds.size();
//         if (off >= total) {
//             return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, new ArrayList<>());
//         }
//         int start = (int) off;
//         int end = (int) Math.min(off + lim, total);
//         List<Long> pageIds = allIds.subList(start, end);
//         List<PurchaseOrder> orders = pageIds.isEmpty() ? new ArrayList<>() : fetcher.apply(pageIds);
//         List<PurchaseOrderDTO> content = orders.stream()
//                 .map(PurchaseOrderDTO::new)
//                 .collect(Collectors.toList());
//         populateYarnNames(content);
//         return new PaginatedResponse<>(total, end < total, off > 0, off, lim, content);
//     }

//     private void mapDtoToEntity(PurchaseOrderDTO dto, PurchaseOrder entity) {
//         entity.setPoTypeId(dto.getPoTypeId());
//         entity.setPoNo(dto.getPoNo());
//         entity.setPoDate(dto.getPoDate());
//         entity.setVendorId(dto.getVendorId());
//         entity.setTaxId(dto.getTaxId());
//         entity.setActiveFlag(dto.getActiveFlag());
//         entity.setIsApproved(dto.getIsApproved());
//         entity.setIsComplete(dto.getIsComplete());
//         entity.setUserId(dto.getUserId());
//         entity.setComments(dto.getComments());
//         if (dto.getApproveDate() != null) {
//             entity.setApproveDate(dto.getApproveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//         }
//     }
    
//     private void ensureSalesOrderReferenceExists(Long salesOrderId) {
//         ensureSalesOrderReferenceExists(salesOrderId, null);
//     }

//     private void ensureSalesOrderReferenceExists(Long salesOrderId, String displayValue) {
//         if (salesOrderId == null) {
//             return;
//         }
//         Optional<SalesOrder> salesOrderOptional = salesOrderRepository.findById(salesOrderId);
//         String displayIdentifier = displayValue;
//         if (displayIdentifier == null || displayIdentifier.isBlank()) {
//             displayIdentifier = salesOrderOptional
//                     .map(SalesOrder::getSalesOrderNo)
//                     .filter(no -> no != null && !no.isBlank())
//                     .orElse("Sales Order ID " + salesOrderId);
//         }
//         referenceValidationService.requireReference(
//                 salesOrderId,
//                 () -> salesOrderOptional,
//                 "Sales Order",
//                 displayIdentifier);
//     }
    
//  // Add this new method
//     public List<VendorDTO> getVendorsByPoType(Long poTypeId) {
//         List<Long> vendorIds = purchaseOrderRepository.findVendorIdsByPoTypeId(poTypeId);
//         if (vendorIds.isEmpty()) {
//             throw new IllegalArgumentException("No vendors found for PO Type ID: " + poTypeId);
//         }
//         return vendorRepository.findAllById(vendorIds)
//                 .stream()
//                 .map(VendorDTO::new)
//                 .collect(Collectors.toList());
//     }
    
//     private void updatePurchaseOrderItems(PurchaseOrder purchaseOrder, List<PurchaseOrderItemDTO> itemDTOs) {
//         if (itemDTOs == null) {
//             return;
//         }
        
//         // Get existing items
//         List<PurchaseOrderItem> existingItems = purchaseOrder.getItems();
//         Map<Long, PurchaseOrderItem> existingItemsMap = existingItems.stream()
//                 .collect(Collectors.toMap(PurchaseOrderItem::getId, item -> item));
        
//         // Clear and rebuild the collection properly
//         existingItems.clear(); // Clear the managed collection
        
//         for (PurchaseOrderItemDTO itemDTO : itemDTOs) {
//             if (itemDTO.getId() != null && existingItemsMap.containsKey(itemDTO.getId())) {
//                 // Update existing item
//                 PurchaseOrderItem existingItem = existingItemsMap.get(itemDTO.getId());
//                 updateItemFromDTO(existingItem, itemDTO);
//                 existingItems.add(existingItem); // Add back to the managed collection
//             } else {
//                 // Add new item
//                 ensureSalesOrderReferenceExists(itemDTO.getSalesOrderId(), itemDTO.getSalesOrderNo());
//                 PurchaseOrderItem newItem = new PurchaseOrderItem(itemDTO);
//                 newItem.setPurchaseOrder(purchaseOrder);
//                 existingItems.add(newItem); // Add to the managed collection
//             }
//         }
        
//         // Delete orphaned items that are no longer in the DTO
//         List<PurchaseOrderItem> itemsToDelete = existingItemsMap.values().stream()
//                 .filter(item -> !existingItems.contains(item))
//                 .collect(Collectors.toList());
        
//         if (!itemsToDelete.isEmpty()) {
//             purchaseOrderItemRepository.deleteAll(itemsToDelete);
//         }
//     }
    
    
//     private void updateItemFromDTO(PurchaseOrderItem item, PurchaseOrderItemDTO dto) {
//         item.setProductCategoryId(dto.getProductCategoryId());
//         item.setPoTypeId(dto.getPoTypeId());
//         item.setYarnId(dto.getYarnId());
//         item.setYarnTypeId(dto.getYarnTypeId());
//         item.setFabricTypeId(dto.getFabricTypeId());
//         item.setFabricId(dto.getFabricId());
//         item.setFabricCategoryId(dto.getFabricCategoryId());
//         item.setFabricMasterId(dto.getFabricMasterId());
        
//         // Resolve millName to millNameId if needed
//         if (dto.getMillName() != null && dto.getMillNameId() == null) {
//             Long millNameId = resolveMillNameToId(dto.getMillName());
//             item.setMillNameId(millNameId);
//         } else {
//             item.setMillNameId(dto.getMillNameId());
//         }
        
//         item.setQuantity(dto.getQuantity());
//         item.setUnit(dto.getUnit());
//         item.setPrice(dto.getPrice());
//         item.setNetAmount(dto.getNetAmount());
//         item.setDeliveryDate(dto.getDeliveryDate());
//         item.setRemarks(dto.getRemarks());
//         item.setActiveFlag(dto.getActiveFlag());
//         item.setCgstPercent(dto.getCgstPercent());
//         item.setSgstPercent(dto.getSgstPercent());
//         item.setIgstPercent(dto.getIgstPercent());
//         item.setGstAmount(dto.getGstAmount());
//         item.setFinalAmount(dto.getFinalAmount());
//         item.setFabricCode(dto.getFabricCode());
//         item.setQuality(dto.getQuality());
//         ensureSalesOrderReferenceExists(dto.getSalesOrderId(), dto.getSalesOrderNo());
//         item.setSalesOrderNo(dto.getSalesOrderNo());
//         item.setSalesOrderId(dto.getSalesOrderId());
//         item.setGstPercent(dto.getGstPercent());
//         item.setProductType(dto.getProductType());
//         item.setItemId(dto.getItemId());
//     }
    
//     // private String generatePoNo(Long companyId, LocalDate poDate) {
//     //     Company company = companyRepository.findById(companyId)
//     //             .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
//     //     String cmpCode = company.getCompanyCode(); // Assume Company has getCompanyCode()

//     //     String fy = getFinancialYear(poDate);

//     //     String prefix = "PO/" + cmpCode + "/" + fy + "/";

//     //     Integer maxNum = purchaseOrderRepository.findMaxOrderNumberByPrefix(prefix, companyId);
//     //     int nextNum = (maxNum != null ? maxNum : 0) + 1;

//     //     return prefix + String.format("%04d", nextNum);
//     // }

//     // Helper for financial year (Indian FY: Apr-Mar)
//     private String getFinancialYear(LocalDate date) {
//         int year = date.getYear();
//         int month = date.getMonthValue();
//         int fyStartYear = (month >= 4) ? year : year - 1;
//         int fy1 = fyStartYear % 100;
//         int fy2 = (fyStartYear + 1) % 100;
//         return String.format("%02d-%02d", fy1, fy2);
//     }
    
//     private Long resolveMillNameToId(String millName) {
//         if (millName == null || millName.trim().isEmpty()) {
//             return null;
//         }
//         SubCategory subCategory = subCategoryRepository.findBySubCategoryName(millName)
//                 .orElseThrow(() -> new IllegalArgumentException("Mill Name not found: " + millName));
//         return subCategory.getId();
//     }
    
//     private void populateYarnNames(PurchaseOrderDTO dto) {
//         if (dto == null) {
//             return;
//         }
//         populateYarnNames(Collections.singletonList(dto));
//     }
    
//     private void populateYarnNames(List<PurchaseOrderDTO> purchaseOrders) {
//         if (purchaseOrders == null || purchaseOrders.isEmpty()) {
//             return;
//         }
//         List<PurchaseOrderItemDTO> allItems = purchaseOrders.stream()
//                 .map(PurchaseOrderDTO::getPurchaseOrderItemsDtl)
//                 .filter(Objects::nonNull)
//                 .flatMap(List::stream)
//                 .collect(Collectors.toList());
//         populateYarnNamesForItems(allItems);
//         populateItemNamesForItems(allItems);
//     }
    
    
//     private void populateYarnNamesForItems(List<PurchaseOrderItemDTO> items) {
//         if (items == null || items.isEmpty()) {
//             return;
//         }
//         List<Long> yarnIds = items.stream()
//                 .map(PurchaseOrderItemDTO::getYarnId)
//                 .filter(Objects::nonNull)
//                 .distinct()
//                 .collect(Collectors.toList());
//         if (yarnIds.isEmpty()) {
//             return;
//         }
//         Map<Long, String> yarnNameMap = yarnMasterRepository.findAllById(yarnIds).stream()
//                 .collect(Collectors.toMap(YarnMaster::getId, YarnMaster::getYarnName));
//         items.forEach(item -> {
//             if (item.getYarnId() != null) {
//                 item.setYarnName(yarnNameMap.get(item.getYarnId()));
//             }
//         });
//     }
    
//     private void populateItemNamesForItems(List<PurchaseOrderItemDTO> items) {
//         if (items == null || items.isEmpty()) {
//             return;
//         }
//         List<Long> itemIds = items.stream()
//                 .map(PurchaseOrderItemDTO::getItemId)
//                 .filter(Objects::nonNull)
//                 .distinct()
//                 .collect(Collectors.toList());
//         if (itemIds.isEmpty()) {
//             return;
//         }
//         Map<Long, String> itemNameMap = itemMasterRepository.findAllById(itemIds).stream()
//                 .collect(Collectors.toMap(ItemMaster::getId, ItemMaster::getItemName));
//         items.forEach(item -> {
//             if (item.getItemId() != null) {
//                 item.setItemName(itemNameMap.get(item.getItemId()));
//             }
//         });
//     }
    
// }

package com.tech3.erp.service;
import com.tech3.erp.entity.Company; // Assume you have a Company entity
import com.tech3.erp.entity.ItemMaster;
import com.tech3.erp.dto.PurchaseOrderDTO;
import com.tech3.erp.dto.PurchaseOrderItemDTO;
import com.tech3.erp.dto.VendorDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.PurchaseOrder;
import com.tech3.erp.entity.PurchaseOrderItem;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.entity.SubCategory;
import com.tech3.erp.entity.YarnMaster;
import com.tech3.erp.repository.CompanyRepository; // Assume this exists
import com.tech3.erp.repository.PurchaseOrderRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.repository.SubCategoryRepository;
import com.tech3.erp.repository.PurchaseReturnRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.PurchaseInwardRepository;
import com.tech3.erp.repository.ItemMasterRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import com.tech3.erp.repository.PurchaseOrderItemRepository;
import com.tech3.erp.service.ReferenceValidationService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.repository.PurchaseInwardItemRepository;
import java.math.BigDecimal;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final VendorRepository vendorRepository;
    private final CompanyRepository companyRepository;
    private final SubCategoryRepository subCategoryRepository;
    
    private final YarnMasterRepository yarnMasterRepository;
    private final PurchaseReturnRepository purchaseReturnRepository;
    private final YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    private final PurchaseInwardRepository purchaseInwardRepository;
    private final ItemMasterRepository itemMasterRepository;
    private final ReferenceValidationService referenceValidationService;
    private final SalesOrderRepository salesOrderRepository;

    private final PurchaseInwardItemRepository purchaseInwardItemRepository;

    


    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                PurchaseOrderItemRepository purchaseOrderItemRepository, 
                                VendorRepository vendorRepository, 
                                CompanyRepository companyRepository,
                                SubCategoryRepository subCategoryRepository,    
                                YarnMasterRepository yarnMasterRepository,
                                PurchaseReturnRepository purchaseReturnRepository,
                                YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository,
                                PurchaseInwardRepository purchaseInwardRepository,
                                ItemMasterRepository itemMasterRepository,
                                ReferenceValidationService referenceValidationService,
                                PurchaseInwardItemRepository purchaseInwardItemRepository,
                                SalesOrderRepository salesOrderRepository
) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.vendorRepository = vendorRepository;
        this.companyRepository = companyRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.yarnMasterRepository = yarnMasterRepository;
        this.purchaseReturnRepository = purchaseReturnRepository;
        this.yarnInwardStockDetailsRepository = yarnInwardStockDetailsRepository;
        this.purchaseInwardRepository = purchaseInwardRepository;
        this.itemMasterRepository = itemMasterRepository;
        this.referenceValidationService = referenceValidationService;
        this.salesOrderRepository = salesOrderRepository;
        this.purchaseInwardItemRepository = purchaseInwardItemRepository;
    }
    
    @Transactional
    public PurchaseOrderDTO createPurchaseOrder(PurchaseOrderDTO dto) {
    	
    	if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required for purchase order generation");
        }
    	
        PurchaseOrder entity = new PurchaseOrder();
        entity.setCompanyId(dto.getCompanyId());

        // Generate poNo if not provided (for create mode)
        // if (dto.getPoNo() == null || dto.getPoNo().trim().isEmpty()) {
        //     String generatedPoNo = generatePoNo(dto.getCompanyId(), dto.getPoDate());
        //     dto.setPoNo(generatedPoNo);
        // }
        
        mapDtoToEntity(dto, entity);

        // Set items properly
        if (dto.getPurchaseOrderItemsDtl() != null && !dto.getPurchaseOrderItemsDtl().isEmpty()) {
            List<PurchaseOrderItem> items = dto.getPurchaseOrderItemsDtl().stream().map(i -> {
                ensureSalesOrderReferenceExists(i.getSalesOrderId(), i.getSalesOrderNo());
                // Resolve millName to millNameId if needed
                if (i.getMillName() != null && i.getMillNameId() == null) {
                    Long millNameId = resolveMillNameToId(i.getMillName());
                    i.setMillNameId(millNameId);
                }
                PurchaseOrderItem item = new PurchaseOrderItem(i);
                item.setPurchaseOrder(entity); // 🔥 Crucial: set back-reference
                return item;
            }).collect(Collectors.toList());

            entity.setItems(items); // 🔥 Crucial: attach to parent
        }

        // Only save parent entity
        PurchaseOrder saved = purchaseOrderRepository.save(entity); // cascade saves items too
        PurchaseOrderDTO response = new PurchaseOrderDTO(saved);
        populateYarnNames(response);
        return response;
        
    }
   
    public PurchaseOrderDTO getPurchaseOrderById(Long id) {
        return getPurchaseOrderById(id, null);
    }
    public PurchaseOrderDTO getPurchaseOrderById(Long id ,Long excludeInwardId) {
    PurchaseOrder entity = purchaseOrderRepository.findByIdWithItems(id);
    if (entity == null) {
        throw new IllegalArgumentException("PurchaseOrder not found with ID: " + id);
    }
    PurchaseOrderDTO dto = new PurchaseOrderDTO(entity);
    populateYarnNames(dto);
    populateReceivedQuantities(dto, excludeInwardId);
    return dto;
    }
    
    private void populateReceivedQuantities(PurchaseOrderDTO dto, Long excludeInwardId) {
        if (dto.getPurchaseOrderItemsDtl() == null) return;
        for (PurchaseOrderItemDTO itemDto : dto.getPurchaseOrderItemsDtl()) {
            if (itemDto.getId() != null) {
                BigDecimal received;
                if (excludeInwardId != null) {
                    received = purchaseInwardItemRepository.sumQuantityReceivedByPoItemIdExcludingInward(itemDto.getId(), excludeInwardId);
                } else {
                    received = purchaseInwardItemRepository.sumQuantityReceivedByPoItemId(itemDto.getId());
                }
                itemDto.setReceivedQuantity(received != null ? received : BigDecimal.ZERO);
            }
        }
    }
    
    
    public List<PurchaseOrderDTO> getPendingPurchaseOrdersByVendorId(Long vendorId, Long excludeInwardId) {
        List<PurchaseOrder> orders = purchaseOrderRepository.findByVendorId(vendorId);
        List<PurchaseOrderDTO> pendingOrders = new ArrayList<>();

        for (PurchaseOrder order : orders) {
            boolean isPending = false;
            for (PurchaseOrderItem item : order.getItems()) {
                BigDecimal orderedQty = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;
                BigDecimal receivedQty;
                
                if (excludeInwardId != null) {
                    receivedQty = purchaseInwardItemRepository.sumQuantityReceivedByPoItemIdExcludingInward(item.getId(), excludeInwardId);
                } else {
                    receivedQty = purchaseInwardItemRepository.sumQuantityReceivedByPoItemId(item.getId());
                }

                if (receivedQty == null) receivedQty = BigDecimal.ZERO;

                if (receivedQty.compareTo(orderedQty) < 0) {
                    isPending = true;
                    break;
                }
            }
            if (isPending) {
                pendingOrders.add(new PurchaseOrderDTO(order));
            }
        }

        populateYarnNames(pendingOrders);
        return pendingOrders;
    }


    private void populateReceivedQuantities(PurchaseOrderDTO dto) {
        populateReceivedQuantities(dto, null);
    }

    private void populateReceivedQuantities(List<PurchaseOrderDTO> dtos) {
        for (PurchaseOrderDTO dto : dtos) {
            populateReceivedQuantities(dto);
        }
    }

    public List<PurchaseOrderDTO> getAllPurchaseOrders() {
        List<Long> ids = purchaseOrderRepository.findAllIds();

List<PurchaseOrder> orders = purchaseOrderRepository.findByIdsWithItems(ids);
    if (ids.isEmpty()) {
    return new ArrayList<>();
}

List<PurchaseOrderDTO> dtos = orders.stream()
        .map(PurchaseOrderDTO::new)
        .collect(Collectors.toList());
populateYarnNames(dtos);
return dtos;
}

 public PaginatedResponse<PurchaseOrderDTO> getAllPurchaseOrdersWithPagination(Long offset, Long limit) {
    return buildPaginatedResponse(
            purchaseOrderRepository.findAllIds(),
            offset,
            limit,
            ids -> ids.isEmpty() ? new ArrayList<>() : purchaseOrderRepository.findByIdsWithItems(ids)
    );
}

    public PaginatedResponse<PurchaseOrderDTO> getAllPurchaseOrdersByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return buildPaginatedResponse(
                purchaseOrderRepository.findIdsByCompanyId(companyId),
                offset,
                limit,
                ids -> ids.isEmpty() ? new ArrayList<>() : purchaseOrderRepository.findByCompanyIdAndIds(companyId, ids)
        );
    }

    public PurchaseOrderDTO getPurchaseOrderByIdAndCompany(Long id, Long companyId) {
        return getPurchaseOrderByIdAndCompany(id, companyId, null);
    }

    public PurchaseOrderDTO getPurchaseOrderByIdAndCompany(Long id, Long companyId, Long excludeInwardId) {
        PurchaseOrder entity = purchaseOrderRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found with ID: " + id + " for company: " + companyId));
        PurchaseOrderDTO dto = new PurchaseOrderDTO(entity);
        populateYarnNames(dto);
        populateReceivedQuantities(dto, excludeInwardId);
        return dto;
    }

    public List<PurchaseOrderDTO> getPendingPurchaseOrdersByVendorIdAndCompany(Long vendorId, Long companyId, Long excludeInwardId) {
        List<PurchaseOrder> orders = purchaseOrderRepository.findByCompanyIdAndVendorId(companyId, vendorId);
        List<PurchaseOrderDTO> pendingOrders = new ArrayList<>();

        for (PurchaseOrder order : orders) {
            boolean isPending = false;
            for (PurchaseOrderItem item : order.getItems()) {
                BigDecimal orderedQty = item.getQuantity() != null ? item.getQuantity() : BigDecimal.ZERO;
                BigDecimal receivedQty;
                
                if (excludeInwardId != null) {
                    receivedQty = purchaseInwardItemRepository.sumQuantityReceivedByPoItemIdExcludingInward(item.getId(), excludeInwardId);
                } else {
                    receivedQty = purchaseInwardItemRepository.sumQuantityReceivedByPoItemId(item.getId());
                }

                if (receivedQty == null) receivedQty = BigDecimal.ZERO;

                if (receivedQty.compareTo(orderedQty) < 0) {
                    isPending = true;
                    break;
                }
            }
            if (isPending) {
                pendingOrders.add(new PurchaseOrderDTO(order));
            }
        }

        populateYarnNames(pendingOrders);
        return pendingOrders;
    }

    public PurchaseOrderDTO updatePurchaseOrder(Long id, PurchaseOrderDTO dto) {
        PurchaseOrder existing = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found with ID: " + id));
        
        
     // Preserve existing poNo during update
        dto.setPoNo(existing.getPoNo());

        // Set companyId if provided (for consistency)
        if (dto.getCompanyId() != null) {
            existing.setCompanyId(dto.getCompanyId());
        }

    
        // Update main entity fields
        mapDtoToEntity(dto, existing);

        updatePurchaseOrderItems(existing, dto.getPurchaseOrderItemsDtl());
        
        PurchaseOrder updated = purchaseOrderRepository.save(existing);
        PurchaseOrderDTO response = new PurchaseOrderDTO(updated);
        populateYarnNames(response);
        return response;
    }
    
    public PurchaseOrderDTO updatePurchaseOrderByCompany(Long id, Long companyId, PurchaseOrderDTO dto) {
        PurchaseOrder existing = purchaseOrderRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found with ID: " + id + " for company: " + companyId));
        dto.setPoNo(existing.getPoNo());
        dto.setCompanyId(existing.getCompanyId());

        mapDtoToEntity(dto, existing);

        updatePurchaseOrderItems(existing, dto.getPurchaseOrderItemsDtl());
        PurchaseOrder updated = purchaseOrderRepository.save(existing);
        PurchaseOrderDTO response = new PurchaseOrderDTO(updated);
        populateYarnNames(response);
        return response;
    }

    @Transactional
    public void deletePurchaseOrder(Long id) {
        PurchaseOrder order = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with id: " + id));

        checkPurchaseOrderReferences(id, null);
        
        try {
            // Delete child items first to avoid false FK violations on parent delete
            if (order.getItems() != null && !order.getItems().isEmpty()) {
                purchaseOrderItemRepository.deleteAll(order.getItems());
            }
            purchaseOrderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Purchase Order because it is referenced by other entities");
        }
    }
    
    @Transactional
    public void deletePurchaseOrderByCompany(Long id, Long companyId) {
        PurchaseOrder order = purchaseOrderRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with id: " + id + " for company: " + companyId));
        checkPurchaseOrderReferences(order.getId(), companyId);
        try {
            // Delete child items first to avoid false FK violations on parent delete
            if (order.getItems() != null && !order.getItems().isEmpty()) {
                purchaseOrderItemRepository.deleteAll(order.getItems());
            }
            purchaseOrderRepository.deleteByIdAndCompanyId(id, companyId);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Purchase Order because it is referenced by other entities");
        }
    }
    
    private void checkPurchaseOrderReferences(Long purchaseOrderId, Long companyId) {
        Long purchaseReturnCount = companyId != null
                ? purchaseReturnRepository.countByPurchaseOrderIdAndCompanyId(purchaseOrderId, companyId)
                : purchaseReturnRepository.countByPurchaseOrderId(purchaseOrderId);
        if (purchaseReturnCount > 0) {
            throw new IllegalStateException("The data is already exceeded to other modules - cannot perform operation.");
        }
        
        long inwardsForThisOrder = purchaseInwardRepository.findAll().stream()
                .filter(pi -> purchaseOrderId.equals(pi.getPurchaseOrderId()))
                .filter(pi -> companyId == null || companyId.equals(pi.getCompanyId()))
                .filter(pi -> pi.getActiveFlag() == null || pi.getActiveFlag())
                .count();
        if (inwardsForThisOrder > 0) {
            throw new IllegalStateException("The data is already exceeded to other modules - cannot perform operation.");
        }
        
        Long yarnStockCount = companyId != null
                ? yarnInwardStockDetailsRepository.countByPurchaseOrderIdAndCompanyId(purchaseOrderId, companyId)
                : yarnInwardStockDetailsRepository.countByPurchaseOrderId(purchaseOrderId);
        if (yarnStockCount > 0) {
            throw new IllegalStateException("The data is already exceeded to other modules - cannot perform operation.");
        }
    }
    
    public boolean canDeletePurchaseOrder(Long purchaseOrderId) {
        Long purchaseReturnCount = purchaseReturnRepository.countByPurchaseOrderId(purchaseOrderId);
        if (purchaseReturnCount > 0) {
            return false;
        }
        
        long inwardsForThisOrder = purchaseInwardRepository.findAll().stream()
                .filter(pi -> purchaseOrderId.equals(pi.getPurchaseOrderId()))
                .count();
        if (inwardsForThisOrder > 0) {
            return false;
        }
        
        Long yarnStockCount = yarnInwardStockDetailsRepository.countByPurchaseOrderId(purchaseOrderId);
        if (yarnStockCount > 0) {
            return false;
        }
        
        return true;
    }
    
    public Map<Long, Boolean> canDeletePurchaseOrderItems(Long purchaseOrderId) {
        Map<Long, Boolean> itemsDeletionStatus = new HashMap<>();
        
        PurchaseOrder order = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Order not found with id: " + purchaseOrderId));
        
        if (order.getItems() == null) {
            return itemsDeletionStatus;
        }
        
        for (PurchaseOrderItem item : order.getItems()) {
            boolean canDelete = true;
            
            long yarnStockCount = yarnInwardStockDetailsRepository.findByItemId(item.getId()).size();
            if (yarnStockCount > 0) {
                canDelete = false;
            }
            
            itemsDeletionStatus.put(item.getId(), canDelete);
        }
        
        return itemsDeletionStatus;
    }
    
    public boolean canEditPurchaseOrder(Long purchaseOrderId) {
        Long purchaseReturnCount = purchaseReturnRepository.countByPurchaseOrderId(purchaseOrderId);
        if (purchaseReturnCount > 0) {
            return false;
        }
        
        long inwardsForThisOrder = purchaseInwardRepository.findAll().stream()
                .filter(pi -> purchaseOrderId.equals(pi.getPurchaseOrderId()))
                .count();
        if (inwardsForThisOrder > 0) {
            return false;
        }
        
        Long yarnStockCount = yarnInwardStockDetailsRepository.countByPurchaseOrderId(purchaseOrderId);
        if (yarnStockCount > 0) {
            return false;
        }
        
        return true;
    }

    public List<PurchaseOrderDTO> getPurchaseOrdersByVendorId(Long vendorId) {
        List<PurchaseOrder> orders = purchaseOrderRepository.findByVendorId(vendorId);
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("No purchase orders found for Vendor ID: " + vendorId);
        }
        List<PurchaseOrderDTO> dtos = orders.stream().map(PurchaseOrderDTO::new).toList();
        populateYarnNames(dtos);
        return dtos;
    }

    public List<PurchaseOrderDTO> getPurchaseOrdersByVendorIdAndCompany(Long vendorId, Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        List<PurchaseOrder> orders = purchaseOrderRepository.findByCompanyIdAndVendorId(companyId, vendorId);
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("No purchase orders found for Vendor ID: " + vendorId + " within company ID: " + companyId);
        }
        List<PurchaseOrderDTO> dtos = orders.stream().map(PurchaseOrderDTO::new).toList();
        populateYarnNames(dtos);
        return dtos;
    }

    
    
    private PaginatedResponse<PurchaseOrderDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit, Function<List<Long>, List<PurchaseOrder>> fetcher) {
        long off = offset != null && offset > 0 ? offset : 0L;
        long lim = (limit != null && limit > 0) ? limit : 10L;
        long total = allIds.size();
        if (off >= total) {
            return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, new ArrayList<>());
        }
        int start = (int) off;
        int end = (int) Math.min(off + lim, total);
        List<Long> pageIds = allIds.subList(start, end);
        List<PurchaseOrder> orders = pageIds.isEmpty() ? new ArrayList<>() : fetcher.apply(pageIds);
        List<PurchaseOrderDTO> content = orders.stream()
                .map(PurchaseOrderDTO::new)
                .collect(Collectors.toList());
        populateYarnNames(content);
        return new PaginatedResponse<>(total, end < total, off > 0, off, lim, content);
    }

    private void mapDtoToEntity(PurchaseOrderDTO dto, PurchaseOrder entity) {
        entity.setPoTypeId(dto.getPoTypeId());
        entity.setPoNo(dto.getPoNo());
        entity.setPoDate(dto.getPoDate());
        entity.setVendorId(dto.getVendorId());
        entity.setTaxId(dto.getTaxId());
        entity.setActiveFlag(dto.getActiveFlag());
        entity.setIsApproved(dto.getIsApproved());
        entity.setIsComplete(dto.getIsComplete());
        entity.setUserId(dto.getUserId());
        entity.setComments(dto.getComments());
        entity.setFabricMasterId(dto.getFabricMasterId());
        entity.setPoCategoryId(dto.getPoCategoryId());
        entity.setNote(dto.getNote());
        if (dto.getApproveDate() != null) {
            entity.setApproveDate(dto.getApproveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
    }
    
    private void ensureSalesOrderReferenceExists(Long salesOrderId) {
        ensureSalesOrderReferenceExists(salesOrderId, null);
    }

    private void ensureSalesOrderReferenceExists(Long salesOrderId, String displayValue) {
        if (salesOrderId == null) {
            return;
        }
        Optional<SalesOrder> salesOrderOptional = salesOrderRepository.findById(salesOrderId);
        String displayIdentifier = displayValue;
        if (displayIdentifier == null || displayIdentifier.isBlank()) {
            displayIdentifier = salesOrderOptional
                    .map(SalesOrder::getSalesOrderNo)
                    .filter(no -> no != null && !no.isBlank())
                    .orElse("Sales Order ID " + salesOrderId);
        }
        referenceValidationService.requireReference(
                salesOrderId,
                () -> salesOrderOptional,
                "Sales Order",
                displayIdentifier);
    }
    
 // Add this new method
    @Transactional(readOnly = true)
    public List<VendorDTO> getVendorsByPoType(Long poTypeId) {
        List<Long> vendorIds = purchaseOrderRepository.findVendorIdsByPoTypeId(poTypeId);
        if (vendorIds.isEmpty()) {
            throw new IllegalArgumentException("No vendors found for PO Type ID: " + poTypeId);
        }
        return vendorRepository.findAllById(vendorIds)
                .stream()
                .map(VendorDTO::new)
                .collect(Collectors.toList());
    }
    
    private void updatePurchaseOrderItems(PurchaseOrder purchaseOrder, List<PurchaseOrderItemDTO> itemDTOs) {
        if (itemDTOs == null) {
            return;
        }
        
        // Get existing items
        List<PurchaseOrderItem> existingItems = purchaseOrder.getItems();
        Map<Long, PurchaseOrderItem> existingItemsMap = existingItems.stream()
                .collect(Collectors.toMap(PurchaseOrderItem::getId, item -> item));
        
        // Clear and rebuild the collection properly
        existingItems.clear(); // Clear the managed collection
        
        for (PurchaseOrderItemDTO itemDTO : itemDTOs) {
            if (itemDTO.getId() != null && existingItemsMap.containsKey(itemDTO.getId())) {
                // Update existing item
                PurchaseOrderItem existingItem = existingItemsMap.get(itemDTO.getId());
                updateItemFromDTO(existingItem, itemDTO);
                existingItems.add(existingItem); // Add back to the managed collection
            } else {
                // Add new item
                ensureSalesOrderReferenceExists(itemDTO.getSalesOrderId(), itemDTO.getSalesOrderNo());
                PurchaseOrderItem newItem = new PurchaseOrderItem(itemDTO);
                newItem.setPurchaseOrder(purchaseOrder);
                existingItems.add(newItem); // Add to the managed collection
            }
        }
        
        // Delete orphaned items that are no longer in the DTO
        List<PurchaseOrderItem> itemsToDelete = existingItemsMap.values().stream()
                .filter(item -> !existingItems.contains(item))
                .collect(Collectors.toList());
        
        if (!itemsToDelete.isEmpty()) {
            purchaseOrderItemRepository.deleteAll(itemsToDelete);
        }
    }
    
    
    private void updateItemFromDTO(PurchaseOrderItem item, PurchaseOrderItemDTO dto) {
        item.setProductCategoryId(dto.getProductCategoryId());
        item.setPoTypeId(dto.getPoTypeId());
        item.setYarnId(dto.getYarnId());
        item.setYarnTypeId(dto.getYarnTypeId());
        item.setFabricTypeId(dto.getFabricTypeId());
        item.setFabricId(dto.getFabricId());
        item.setFabricCategoryId(dto.getFabricCategoryId());
        item.setFabricMasterId(dto.getFabricMasterId());
        
        // Resolve millName to millNameId if needed
        if (dto.getMillName() != null && dto.getMillNameId() == null) {
            Long millNameId = resolveMillNameToId(dto.getMillName());
            item.setMillNameId(millNameId);
        } else {
            item.setMillNameId(dto.getMillNameId());
        }
        
        item.setQuantity(dto.getQuantity());
        item.setUnit(dto.getUnit());
        item.setPrice(dto.getPrice());
        item.setNetAmount(dto.getNetAmount());
        item.setDeliveryDate(dto.getDeliveryDate());
        item.setRemarks(dto.getRemarks());
        item.setActiveFlag(dto.getActiveFlag());
        item.setCgstPercent(dto.getCgstPercent());
        item.setSgstPercent(dto.getSgstPercent());
        item.setIgstPercent(dto.getIgstPercent());
        item.setGstAmount(dto.getGstAmount());
        item.setFinalAmount(dto.getFinalAmount());
        item.setFabricCode(dto.getFabricCode());
        item.setQuality(dto.getQuality());
        ensureSalesOrderReferenceExists(dto.getSalesOrderId(), dto.getSalesOrderNo());
        item.setSalesOrderNo(dto.getSalesOrderNo());
        item.setSalesOrderId(dto.getSalesOrderId());
        item.setGstPercent(dto.getGstPercent());
        item.setProductType(dto.getProductType());
        item.setItemId(dto.getItemId());
        item.setPerformaNo(dto.getPerformaNo());
        item.setDiscountType(dto.getDiscountType() != null ? dto.getDiscountType() : "Percentage");
        item.setDiscountValue(dto.getDiscountValue());
        item.setDiscountAmount(dto.getDiscountAmount());
        item.setNetPricePerUnit(dto.getNetPricePerUnit());
        item.setPackingAndForwarding(dto.getPackingAndForwarding());
        item.setOthers(dto.getOthers());
        item.setNote(dto.getNote());
    }
    
    // private String generatePoNo(Long companyId, LocalDate poDate) {
    //     Company company = companyRepository.findById(companyId)
    //             .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
    //     String cmpCode = company.getCompanyCode(); // Assume Company has getCompanyCode()

    //     String fy = getFinancialYear(poDate);

    //     String prefix = "PO/" + cmpCode + "/" + fy + "/";

    //     Integer maxNum = purchaseOrderRepository.findMaxOrderNumberByPrefix(prefix, companyId);
    //     int nextNum = (maxNum != null ? maxNum : 0) + 1;

    //     return prefix + String.format("%04d", nextNum);
    // }

    // Helper for financial year (Indian FY: Apr-Mar)
    private String getFinancialYear(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int fyStartYear = (month >= 4) ? year : year - 1;
        int fy1 = fyStartYear % 100;
        int fy2 = (fyStartYear + 1) % 100;
        return String.format("%02d-%02d", fy1, fy2);
    }
    
    private Long resolveMillNameToId(String millName) {
        if (millName == null || millName.trim().isEmpty()) {
            return null;
        }
        SubCategory subCategory = subCategoryRepository.findBySubCategoryName(millName)
                .orElseThrow(() -> new IllegalArgumentException("Mill Name not found: " + millName));
        return subCategory.getId();
    }
    
    private void populateYarnNames(PurchaseOrderDTO dto) {
        if (dto == null) {
            return;
        }
        populateYarnNames(Collections.singletonList(dto));
    }
    
    private void populateYarnNames(List<PurchaseOrderDTO> purchaseOrders) {
        if (purchaseOrders == null || purchaseOrders.isEmpty()) {
            return;
        }
        List<PurchaseOrderItemDTO> allItems = purchaseOrders.stream()
                .map(PurchaseOrderDTO::getPurchaseOrderItemsDtl)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        populateYarnNamesForItems(allItems);
        populateItemNamesForItems(allItems);
    }
    
    
    private void populateYarnNamesForItems(List<PurchaseOrderItemDTO> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        List<Long> yarnIds = items.stream()
                .map(PurchaseOrderItemDTO::getYarnId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (yarnIds.isEmpty()) {
            return;
        }
        Map<Long, String> yarnNameMap = yarnMasterRepository.findAllById(yarnIds).stream()
                .collect(Collectors.toMap(YarnMaster::getId, YarnMaster::getYarnName));
        items.forEach(item -> {
            if (item.getYarnId() != null) {
                item.setYarnName(yarnNameMap.get(item.getYarnId()));
            }
        });
    }
    
    private void populateItemNamesForItems(List<PurchaseOrderItemDTO> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        List<Long> itemIds = items.stream()
                .map(PurchaseOrderItemDTO::getItemId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (itemIds.isEmpty()) {
            return;
        }
        Map<Long, String> itemNameMap = itemMasterRepository.findAllById(itemIds).stream()
                .collect(Collectors.toMap(ItemMaster::getId, ItemMaster::getItemName));
        items.forEach(item -> {
            if (item.getItemId() != null) {
                item.setItemName(itemNameMap.get(item.getItemId()));
            }
        });
    }
    
}

