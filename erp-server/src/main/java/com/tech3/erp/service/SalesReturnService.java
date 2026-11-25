// package com.tech3.erp.service;
// import com.tech3.erp.dto.SalesReturnDTO;
// import com.tech3.erp.dto.SalesReturnItemDTO;
// import com.tech3.erp.dto.SalesYarnReturnDTO;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.dto.SalesReturnBeamDTO;
// import com.tech3.erp.dto.SalesReturnSizingDTO;
// import com.tech3.erp.entity.CodesDetail;
// import com.tech3.erp.entity.SalesReturn;
// import com.tech3.erp.entity.SalesReturnSizing;
// import com.tech3.erp.entity.SizingBeamOutward;
// import com.tech3.erp.entity.SizingBeamOutwardDetail;
// import com.tech3.erp.repository.CodesDetailRepository;
// import com.tech3.erp.repository.SalesReturnSizingRepository;
// import com.tech3.erp.repository.YarnInventoryRepository;
// import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
// import com.tech3.erp.repository.YarnMasterRepository;
// import com.tech3.erp.entity.YarnInventory;
// import com.tech3.erp.entity.YarnInwardStockDetails;
// import com.tech3.erp.entity.YarnMaster;
// import com.tech3.erp.entity.SizingProductionDetail;
// import com.tech3.erp.repository.SizingProductionDetailRepository;
// import com.tech3.erp.repository.SizingBeamOutwardRepository;
// import com.tech3.erp.repository.SizingBeamOutwardDetailRepository;
// import com.tech3.erp.entity.SalesReturnItem;
// import com.tech3.erp.entity.SalesYarnReturn;
// import com.tech3.erp.entity.SalesReturnBeamEntity;
// import com.tech3.erp.entity.BeamOutward;
// import com.tech3.erp.entity.BeamInwardBeamDetails;
// import com.tech3.erp.entity.BeamOutwardDetail;
// import com.tech3.erp.entity.BaleRollPacking;
// import com.tech3.erp.entity.BaleRollPackingItem;
// import com.tech3.erp.entity.PieceEntry;
// import com.tech3.erp.entity.Warehouse;
// import com.tech3.erp.entity.WeavingContract;
// import com.tech3.erp.repository.SalesReturnRepository;
// import com.tech3.erp.repository.SalesReturnItemRepository;
// import com.tech3.erp.repository.SalesYarnReturnRepository;
// import com.tech3.erp.repository.SalesReturnBeamRepository;
// import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
// import com.tech3.erp.repository.BeamOutwardDetailRepository;
// import com.tech3.erp.repository.BeamOutwardRepository;
// import com.tech3.erp.repository.PieceEntryRepository;
// import com.tech3.erp.repository.WarehouseRepository;
// import com.tech3.erp.repository.WeavingContractRepository;
// import com.tech3.erp.repository.YarnChallanRepository;
// import com.tech3.erp.repository.InvoiceItemRepository;
// import com.tech3.erp.repository.GenerateInvoiceRepository;
// import com.tech3.erp.repository.WovenFabricMasterRepository;
// import com.tech3.erp.entity.WovenFabricMaster;
// import com.tech3.erp.repository.GeneratePackingRepository;
// import com.tech3.erp.repository.BaleRollPackingRepository;
// import com.tech3.erp.repository.BaleRollPackingItemRepository;
// import com.tech3.erp.service.ReferenceValidationService;
// import com.tech3.erp.util.PaginationHelper;
// import com.tech3.erp.exception.ApiException;
// import jakarta.persistence.EntityNotFoundException;
// import jakarta.transaction.Transactional;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.ZoneId;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Objects;
// import java.util.Optional;
// import java.util.function.Function;
// import java.util.stream.Collectors;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// @Service
// public class SalesReturnService {

//     private static final Logger logger = LoggerFactory.getLogger(SalesReturnService.class);

//     @Autowired
//     private SalesReturnRepository salesReturnRepository;

//     @Autowired
//     private SalesReturnItemRepository salesReturnItemRepository;

//     @Autowired
//     private SalesYarnReturnRepository salesYarnReturnRepository;

//     @Autowired
//     private PieceEntryRepository pieceEntryRepository;

//     @Autowired
//     private WarehouseRepository warehouseRepository;

//     @Autowired
//     private SalesReturnBeamRepository salesReturnBeamRepository;

//     @Autowired
//     private BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository;

//     @Autowired
//     private BeamOutwardDetailRepository beamOutwardDetailRepository;

//     @Autowired
//     private BeamOutwardRepository beamOutwardRepository;

//     @Autowired
//     private WeavingContractRepository weavingContractRepository;

//     @Autowired
//     private YarnChallanRepository yarnChallanRepository;

//     @Autowired
//     private InvoiceItemRepository invoiceItemRepository;

  
//  @Autowired
//     private GenerateInvoiceRepository generateInvoiceRepository;

//     @Autowired
//     private GeneratePackingRepository generatePackingRepository;

//     @Autowired
//     private ReferenceValidationService referenceValidationService;

//     @Autowired
//     private BaleRollPackingRepository baleRollPackingRepository;

//     @Autowired
//     private BaleRollPackingItemRepository baleRollPackingItemRepository;

//     @Autowired
//     private SalesReturnSizingRepository salesReturnSizingRepository;

//     @Autowired
//     private SizingProductionDetailRepository sizingProductionDetailRepository;

//     @Autowired
//     private SizingBeamOutwardRepository sizingBeamOutwardRepository;

//     @Autowired
//     private SizingBeamOutwardDetailRepository sizingBeamOutwardDetailRepository;

//     @Autowired
//     private YarnInventoryRepository yarnInventoryRepository;

//     @Autowired
//     private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;

//     @Autowired
//     private YarnMasterRepository yarnMasterRepository;

//     @Autowired
//     private CodesDetailRepository codesDetailRepository;

//     @Autowired
//     private WovenFabricMasterRepository wovenFabricMasterRepository;

   

//     @Transactional
//     public SalesReturn create(SalesReturnDTO dto) {
//         validateDuplicates(dto, null);
//         SalesReturn entity = new SalesReturn();
//         mapDtoToEntity(dto, entity);
        
//         SalesReturn savedReturn = salesReturnRepository.save(entity);
        
//         if (!"Yarn Sales".equalsIgnoreCase(dto.getInvoiceType()) && dto.getItems() != null && !dto.getItems().isEmpty()) {
//             List<SalesReturnItem> items = dto.getItems().stream()
//                     .map(itemDto -> toItemEntity(itemDto, savedReturn))
//                     .collect(Collectors.toList());
//             salesReturnItemRepository.saveAll(items);
//             savedReturn.setItems(items);
            
//             createPieceEntries(items, savedReturn);
//         }

//         if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
//             List<SalesYarnReturn> yarnItems = dto.getYarnItems().stream()
//                     .map(yarnDto -> toYarnEntity(yarnDto, savedReturn))
//                     .collect(Collectors.toList());
//             salesYarnReturnRepository.saveAll(yarnItems);
//             savedReturn.setYarnItems(yarnItems);

//             if (isYarnSales(dto.getInvoiceType())) {
//                 handleYarnSalesReturn(savedReturn);
//             }
//         }

//         if (!"Yarn Sales".equalsIgnoreCase(dto.getInvoiceType())) {
//             if (!isSizingSales(dto.getInvoiceType())) {
//                 if (dto.getBeamItems() != null && !dto.getBeamItems().isEmpty()) {
//                     handleBeamReturn(dto.getBeamItems(), savedReturn);
//                 }
//             } else {
//                 if (dto.getSizingItems() != null && !dto.getSizingItems().isEmpty()) {
//                     handleSizingReturn(dto.getSizingItems(), savedReturn);
//                 }
//             }
//         }
        
//         return savedReturn;
//     }

//     @Transactional
//     public SalesReturnDTO update(Long id, SalesReturnDTO dto, Long userId) {
//         validateDuplicates(dto, id);
//         SalesReturn entity = salesReturnRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Sales Return not found with id: " + id));
        
//         // Clean up existing Beam flow movements if any
//         if (!isYarnSales(entity.getInvoiceType())) {
//             if (isSizingSales(entity.getInvoiceType())) {
//                 cleanupSizingMovement(entity);
//             } else {
//                 cleanupBeamMovement(entity);
//             }
//         }

//         mapDtoToEntity(dto, entity);
    
//     // Instead of clearing and replacing the collection, modify the existing one
//     updateItemsCollection(entity, dto.getItems());
//     updateYarnItemsCollection(entity, dto.getYarnItems());
    
//     SalesReturn updatedEntity = salesReturnRepository.save(entity);
    
//     // Handle Yarn Sales Return for inventory
//     if (isYarnSales(dto.getInvoiceType())) {
//         if (updatedEntity.getYarnItems() != null && !updatedEntity.getYarnItems().isEmpty()) {
//             handleYarnSalesReturn(updatedEntity);
//         } else {
//             // Clean up if no yarn items but it is Yarn Sales type (or items were removed)
//             cleanupYarnInventory(id, getYarnSalesReturnInwardTypeId());
//         }
//     } else {
//         // Clean up if it's no longer Yarn Sales type
//         cleanupYarnInventory(id, getYarnSalesReturnInwardTypeId());
//     }
    
//     // Create piece entries and handle beam returns (only if not Yarn Sales)
//     if (!isYarnSales(dto.getInvoiceType())) {
//         if (dto.getItems() != null && !dto.getItems().isEmpty()) {
//             cleanupPieceEntriesAndPacking(updatedEntity);
//             createPieceEntries(updatedEntity.getItems(), updatedEntity);
//         }
//         if (!isSizingSales(dto.getInvoiceType())) {
//             if (dto.getBeamItems() != null && !dto.getBeamItems().isEmpty()) {
//                 salesReturnBeamRepository.deleteBySalesReturnId(id);
//                 handleBeamReturn(dto.getBeamItems(), updatedEntity);
//             }
//             // Clear sizing items if it's not Sizing Sales
//             updatedEntity.getSizingItems().clear();
//         } else {
//             if (dto.getSizingItems() != null && !dto.getSizingItems().isEmpty()) {
//                 salesReturnSizingRepository.deleteBySalesReturnId(id);
//                 handleSizingReturn(dto.getSizingItems(), updatedEntity);
//             }
//             // Clear beam items if it IS Sizing Sales
//             updatedEntity.getBeams().clear();
//         }
//     }
    
//     return get(id);
// }

//     @Transactional
//     public SalesReturnDTO update(Long id, SalesReturnDTO dto) {
//         return update(id, dto, null);
//     }
    
//     private void updateItemsCollection(SalesReturn salesReturn, List<SalesReturnItemDTO> itemDTOs) {
//         // Get the current items
//         List<SalesReturnItem> currentItems = salesReturn.getItems();
        
//         if (currentItems == null) {
//             currentItems = new ArrayList<>();
//             salesReturn.setItems(currentItems);
//         }
        
//         if (itemDTOs == null || itemDTOs.isEmpty()) {
//             // Clear all items if no items in DTO
//             currentItems.clear();
//             return;
//         }
        
//         // Create maps for efficient lookup
//         Map<Long, SalesReturnItem> existingItemsMap = currentItems.stream()
//                 .filter(item -> item.getId() != null)
//                 .collect(Collectors.toMap(SalesReturnItem::getId, Function.identity()));
        
//         Map<Long, SalesReturnItemDTO> dtoItemsMap = itemDTOs.stream()
//                 .filter(dto -> dto.getId() != null)
//                 .collect(Collectors.toMap(SalesReturnItemDTO::getId, Function.identity()));
        
//         // Remove items that are not in the DTO
//         List<SalesReturnItem> itemsToRemove = new ArrayList<>();
//         for (SalesReturnItem existingItem : currentItems) {
//             if (existingItem.getId() != null && !dtoItemsMap.containsKey(existingItem.getId())) {
//                 itemsToRemove.add(existingItem);
//             }
//         }
//         currentItems.removeAll(itemsToRemove);
        
//         // Update existing items and add new ones
//         for (SalesReturnItemDTO itemDto : itemDTOs) {
//             if (itemDto.getId() != null && existingItemsMap.containsKey(itemDto.getId())) {
//                 // Update existing item
//                 SalesReturnItem existingItem = existingItemsMap.get(itemDto.getId());
//                 updateItemFromDto(existingItem, itemDto);
//             } else {
//                 // Add new item
//                 SalesReturnItem newItem = toItemEntity(itemDto, salesReturn);
//                 currentItems.add(newItem);
//             }
//         }
//     }

//     private void updateItemFromDto(SalesReturnItem item, SalesReturnItemDTO dto) {
//         ensureItemFieldLengths(dto);
//         item.setRollNo(dto.getRollNo());
//         item.setPieceNo(dto.getPieceNo());
//         item.setPackingListNo(dto.getPackingListNo());
//         item.setMeter(dto.getMeter());
//         item.setNetWeight(dto.getNetWeight());
//     }

//     private void updateYarnItemsCollection(SalesReturn salesReturn, List<SalesYarnReturnDTO> itemDTOs) {
//         List<SalesYarnReturn> currentItems = salesReturn.getYarnItems();
//         if (currentItems == null) {
//             currentItems = new ArrayList<>();
//             salesReturn.setYarnItems(currentItems);
//         }
//         if (itemDTOs == null || itemDTOs.isEmpty()) {
//             currentItems.clear();
//             return;
//         }
//         Map<Long, SalesYarnReturn> existingItemsMap = currentItems.stream()
//                 .filter(item -> item.getId() != null)
//                 .collect(Collectors.toMap(SalesYarnReturn::getId, Function.identity()));
//         Map<Long, SalesYarnReturnDTO> dtoItemsMap = itemDTOs.stream()
//                 .filter(dto -> dto.getId() != null)
//                 .collect(Collectors.toMap(SalesYarnReturnDTO::getId, Function.identity()));
//         List<SalesYarnReturn> itemsToRemove = new ArrayList<>();
//         for (SalesYarnReturn existingItem : currentItems) {
//             if (existingItem.getId() != null && !dtoItemsMap.containsKey(existingItem.getId())) {
//                 itemsToRemove.add(existingItem);
//             }
//         }
//         currentItems.removeAll(itemsToRemove);
//         for (SalesYarnReturnDTO itemDto : itemDTOs) {
//             if (itemDto.getId() != null && existingItemsMap.containsKey(itemDto.getId())) {
//                 SalesYarnReturn existingItem = existingItemsMap.get(itemDto.getId());
//                 updateYarnItemFromDto(existingItem, itemDto);
//             } else {
//                 SalesYarnReturn newItem = toYarnEntity(itemDto, salesReturn);
//                 currentItems.add(newItem);
//             }
//         }
//     }

//     private void updateYarnItemFromDto(SalesYarnReturn item, SalesYarnReturnDTO dto) {
//         item.setYarnName(dto.getYarnName());
//         // item.setAvailableQty(dto.getAvailableQty());
//         item.setIssuedQty(dto.getIssuedQty());
//         item.setLotNo(dto.getLotNo());
//         item.setWarehouseId(dto.getWarehouseId());
//         item.setNoOfBags(dto.getNoOfBags());
//         item.setIssuedBags(dto.getIssuedBags());
//         item.setConesPerBag(dto.getConesPerBag());
//         item.setConesWeight(dto.getConesWeight());
//         item.setMillId(dto.getMillId());
//     }

//     public List<SalesReturnDTO> listAll() {
//         return salesReturnRepository.findAll().stream()
//                 .map(this::toDto)
//                 .collect(Collectors.toList());
//     }

//     public PaginatedResponse<SalesReturnDTO> listAllWithPagination(Long offset, Long limit) {
//         List<SalesReturnDTO> allReturns = listAll();
//         return PaginationHelper.paginate(allReturns, offset, limit);
//     }

//     public SalesReturnDTO get(Long id) {
//         SalesReturn entity = salesReturnRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Sales Return not found with id: " + id));
//         return toDto(entity);
//     }

//     @Transactional
//     public void delete(Long id) {
//         SalesReturn entity = salesReturnRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Sales Return not found with id: " + id));
        
//         // Delete related YarnInventory and YarnInwardStockDetails if it's Yarn Sales
//         if (isYarnSales(entity.getInvoiceType())) {
//             cleanupYarnInventory(id, getYarnSalesReturnInwardTypeId());
//         }

//         // Cleanup PieceEntry and BaleRollPacking records created during save
//         cleanupPieceEntriesAndPacking(entity);

//         // Cleanup Sizing flow related records
//         if (isSizingSales(entity.getInvoiceType())) {
//             cleanupSizingMovement(entity);
//         }

//         // Cleanup Beam flow related records
//         if (!isYarnSales(entity.getInvoiceType()) && !isSizingSales(entity.getInvoiceType())) {
//             cleanupBeamMovement(entity);
//         }
        
//         salesReturnItemRepository.findBySalesReturnId(id).forEach(item -> 
//             salesReturnItemRepository.delete(item)
//         );

//         salesYarnReturnRepository.findBySalesReturnId(id).forEach(item ->
//             salesYarnReturnRepository.delete(item)
//         );

//         salesReturnSizingRepository.deleteBySalesReturnId(id);
//         salesReturnBeamRepository.deleteBySalesReturnId(id);
        
//         salesReturnRepository.delete(entity);
//     }

//     private void cleanupPieceEntriesAndPacking(SalesReturn salesReturn) {
//         // Find and delete PieceEntry records created by this SalesReturn
//         List<SalesReturnItem> items = salesReturnItemRepository.findBySalesReturnId(salesReturn.getId());
//         for (SalesReturnItem item : items) {
//             if (item.getPieceNo() != null) {
//                 String pieceNumberForInspection = item.getPieceNo() + "-R";
//                 List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumber(pieceNumberForInspection);
//                 for (PieceEntry piece : pieces) {
//                     // Only delete pieces that were created by Sales Return
//                     if ("Sales Return".equals(piece.getSourceType()) || "Fabric Return".equals(piece.getSourceType())) {
//                         // First delete any associated BaleRollPackingItem records
//                         List<BaleRollPackingItem> packingItems = baleRollPackingItemRepository.findByPieceId(piece.getId());
//                         if (!packingItems.isEmpty()) {
//                             // Get the BaleRollPacking record to check if it has other items
//                             BaleRollPacking baleRollPacking = packingItems.get(0).getBaleRollPacking();
//                             baleRollPackingItemRepository.deleteAll(packingItems);
                            
//                             // Check if BaleRollPacking has no more items, then delete it too
//                             List<BaleRollPackingItem> remainingItems = baleRollPackingItemRepository.findByBaleRollPackingId(baleRollPacking.getId());
//                             if (remainingItems.isEmpty()) {
//                                 baleRollPackingRepository.delete(baleRollPacking);
//                             }
//                         }
                        
//                         // Delete the PieceEntry
//                         pieceEntryRepository.delete(piece);
//                     }
//                 }
//             }
//         }
//     }

//     private void cleanupSizingMovement(SalesReturn salesReturn) {
//         try {
//             String returnNo = salesReturn.getSalesReturnNo();
//             if (returnNo == null || returnNo.isEmpty()) return;

//             // First, collect all records to be deleted to avoid cascade issues
//             List<SizingProductionDetail> toDeleteProduction = new ArrayList<>();
//             List<SizingBeamOutward> toDeleteOutward = new ArrayList<>();
//             List<SizingBeamOutwardDetail> toDeleteDetails = new ArrayList<>();

//             // Cleanup SizingProductionDetail
//             List<SizingProductionDetail> productionDetails = sizingProductionDetailRepository.findByReturnNo(returnNo);
//             for (SizingProductionDetail detail : productionDetails) {
//                 if (detail.getIsReturn() != null && detail.getIsReturn() == 1) {
//                     // This is an original record that was marked as returned - revert it
//                     detail.setIsReturn(0);
//                     detail.setReturnNo(null);
//                     detail.setReturnDate(null);
//                     sizingProductionDetailRepository.save(detail);
//                 } else {
//                     // This is a new record created for the return - mark for deletion
//                     toDeleteProduction.add(detail);
//                 }
//             }

//             // Cleanup SizingBeamOutward and SizingBeamOutwardDetail
//             List<SizingBeamOutward> outwardRecords = sizingBeamOutwardRepository.findByReturnNo(returnNo);
//             for (SizingBeamOutward outward : outwardRecords) {
//                 if (outward.getIsReturn() != null && outward.getIsReturn() == 1) {
//                     // This is an original record that was marked as returned - revert it
//                     outward.setIsReturn(0);
//                     outward.setReturnNo(null);
//                     outward.setReturnDate(null);
//                     sizingBeamOutwardRepository.save(outward);
//                 } else {
//                     // This is a new record created for the return - collect details for deletion
//                     List<SizingBeamOutwardDetail> details = sizingBeamOutwardDetailRepository.findBySizingBeamOutwardId(outward.getId());
//                     toDeleteDetails.addAll(details);
//                     toDeleteOutward.add(outward);
//                 }
//             }
            
//             // Delete in proper order to avoid foreign key constraints
//             // 1. Delete SizingBeamOutwardDetail records first
//             if (!toDeleteDetails.isEmpty()) {
//                 sizingBeamOutwardDetailRepository.deleteAll(toDeleteDetails);
//             }
            
//             // 2. Delete SizingBeamOutward records
//             if (!toDeleteOutward.isEmpty()) {
//                 sizingBeamOutwardRepository.deleteAll(toDeleteOutward);
//             }
            
//             // 3. Delete SizingProductionDetail records
//             if (!toDeleteProduction.isEmpty()) {
//                 sizingProductionDetailRepository.deleteAll(toDeleteProduction);
//             }
            
//             // Additional cleanup: Find any orphaned SizingBeamOutwardDetail records
//             List<SizingBeamOutwardDetail> remainingDetails = sizingBeamOutwardDetailRepository.findAll();
//             List<SizingBeamOutwardDetail> orphanedDetails = new ArrayList<>();
//             for (SizingBeamOutwardDetail detail : remainingDetails) {
//                 if (detail.getSizingBeamOutward() != null) {
//                     boolean parentExists = sizingBeamOutwardRepository.existsById(detail.getSizingBeamOutward().getId());
//                     if (!parentExists) {
//                         // Parent outward record was deleted, so delete this orphaned detail
//                         orphanedDetails.add(detail);
//                     }
//                 }
//             }
            
//             if (!orphanedDetails.isEmpty()) {
//                 sizingBeamOutwardDetailRepository.deleteAll(orphanedDetails);
//             }
//         } catch (Exception e) {
//             logger.error("Error cleaning up sizing movement for sales return: {}", salesReturn.getId(), e);
//             throw new RuntimeException("Failed to cleanup sizing movement records", e);
//         }
//     }

//     private void cleanupBeamMovement(SalesReturn salesReturn) {
//         try {
//             String returnNo = salesReturn.getSalesReturnNo();
//             if (returnNo == null || returnNo.isEmpty()) return;

//             // Cleanup BeamInwardBeamDetails
//             List<BeamInwardBeamDetails> inwardDetails = beamInwardBeamDetailsRepository.findByReturnNo(returnNo);
//             for (BeamInwardBeamDetails detail : inwardDetails) {
//                 if (detail.getIsReturn() != null && detail.getIsReturn() == 1) {
//                     // Revert original record that was marked as returned
//                     detail.setIsReturn(0);
//                     detail.setReturnNo(null);
//                     detail.setReturnDate(null);
//                     detail.setReturnWeaverId(null);
//                     detail.setIsBeamOutwarded(1);
//                     beamInwardBeamDetailsRepository.save(detail);
//                 } else {
//                     // Delete new record created for the return (isReturn was 0)
//                     beamInwardBeamDetailsRepository.delete(detail);
//                 }
//             }

//             // Cleanup BeamOutwardDetail
//             List<BeamOutwardDetail> beamOutwardDetailRecords = beamOutwardDetailRepository.findByReturnNo(returnNo);
//             for (BeamOutwardDetail detail : beamOutwardDetailRecords) {
//                 if (detail.getIsReturn() != null && detail.getIsReturn() == 1) {
//                     // Revert original record that was marked as returned
//                     detail.setIsReturn(0);
//                     detail.setReturnNo(null);
//                     detail.setReturnDate(null);
//                     detail.setReturnWeaverId(null);
//                     beamOutwardDetailRepository.save(detail);
//                 } else {
//                     // Delete new record created for the return (isReturn was 0)
//                     beamOutwardDetailRepository.delete(detail);
//                 }
//             }
//         } catch (Exception e) {
//             logger.error("Error cleaning up beam movement for sales return: {}", salesReturn.getId(), e);
//             throw new RuntimeException("Failed to cleanup beam movement records", e);
//         }
//     }

//     private void mapDtoToEntity(SalesReturnDTO dto, SalesReturn entity) {
//         if (dto.getDate() != null && !dto.getDate().isEmpty()) {
//             entity.setDate(LocalDate.parse(dto.getDate()));
//         } else {
//             entity.setDate(LocalDate.now());
//         }
//         entity.setSalesReturnNo(limit(dto.getSalesReturnNo(), 100, "salesReturnNo"));
//         entity.setInvoiceNo(limit(dto.getInvoiceNo(), 100, "invoiceNo"));

//          Long invoiceId = normalizeId(dto.getInvoiceId());
//         // if (invoiceId != null) {
//         //     referenceValidationService.requireReference(
//         //             invoiceId,
//         //             () -> generateInvoiceRepository.findById(invoiceId),
//         //             "Invoice",
//         //             dto.getInvoiceNo());
//         // }
//         entity.setInvoiceId(invoiceId);
//         entity.setOrderNo(limit(dto.getOrderNo(), 100, "orderNo"));
//         entity.setOrderId(dto.getOrderId());
//         entity.setBuyerName(limit(dto.getBuyerName(), 255, "buyerName"));
//         entity.setCustomerId(dto.getCustomerId());
        
//         String fCode = dto.getFabricCode();
//         String fQuality = dto.getFabricQuality();
        
//         // Clean up repeated data in quality
//         if (fCode != null && fQuality != null) {
//             String trimmedCode = fCode.trim();
//             String trimmedQuality = fQuality.trim();
//             if (trimmedQuality.equalsIgnoreCase(trimmedCode)) {
//                 fQuality = "";
//             } else if (trimmedQuality.toUpperCase().startsWith(trimmedCode.toUpperCase() + " - ")) {
//                 String subQuality = trimmedQuality.substring(trimmedCode.length() + 3).trim();
//                 if (subQuality.equalsIgnoreCase(trimmedCode)) {
//                     fQuality = "";
//                 } else {
//                     fQuality = subQuality;
//                 }
//             }
//         }
        
//         entity.setFabricCode(limit(fCode, 100, "fabricCode"));
//         entity.setFabricQuality(limit(fQuality, 100, "fabricQuality"));
//         entity.setInvoiceType(limit(dto.getInvoiceType(), 50, "invoiceType"));
//         entity.setRemarks(dto.getRemarks());
//         entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
//         entity.setCompanyId(dto.getCompanyId());
//         entity.setWarehouseId(dto.getWarehouseId());
//         entity.setEditNote(dto.getEditNote());

//         entity.setSizingVendorId(dto.getSizingVendorId());
//         entity.setSizingPlanId(dto.getSizingPlanId());
//         entity.setWeaverId(dto.getWeaverId());
//         entity.setBeamInwardNo(limit(dto.getBeamInwardNo(), 100, "beamInwardNo"));
//         entity.setSizingBeamOutwardNo(limit(dto.getSizingBeamOutwardNo(), 100, "sizingBeamOutwardNo"));
//         entity.setTotalRolls(dto.getTotalRolls());

//         String packingListNo = normalizeString(dto.getPackingListNo());
//         if (packingListNo != null && !"-".equals(packingListNo)) {
//             boolean foundInPacking = generatePackingRepository.findByPackingSlipNo(packingListNo).isPresent();
//             if (!foundInPacking) {
//                 boolean foundInChallan = yarnChallanRepository.findByChallanNo(packingListNo) != null;
//                 if (!foundInChallan) {
//                     boolean foundInInvoice = invoiceItemRepository.existsByPackingListNoAndActiveFlagTrue(packingListNo);
//                     if (!foundInInvoice) {
//                         foundInInvoice = generateInvoiceRepository.existsByInvoiceNoAndActiveFlagTrue(packingListNo);
//                     }
//                     if (!foundInInvoice) {
//                         boolean foundInBeamOutward = !isYarnSales(dto.getInvoiceType()) && !isSizingSales(dto.getInvoiceType())
//                                 && beamOutwardRepository.existsByOutwardNo(packingListNo);
//                         boolean foundInSizingBeamOutward = !foundInBeamOutward && isSizingSales(dto.getInvoiceType())
//                                 && sizingBeamOutwardRepository.existsBySizingBeamOutwardNo(packingListNo);
//                         if (!foundInBeamOutward && !foundInSizingBeamOutward) {
//                             referenceValidationService.requireReference(
//                                     packingListNo,
//                                     Optional::empty,
//                                     "Packing List",
//                                     packingListNo);
//                         }
//                     }
//                 }
//             }
//         }
//         entity.setPackingListNo(limit(packingListNo, 100, "packingListNo"));
//     }

//     private SalesReturnItem toItemEntity(SalesReturnItemDTO dto, SalesReturn salesReturn) {
//         ensureItemFieldLengths(dto);
//         SalesReturnItem item = new SalesReturnItem();
//         item.setSalesReturn(salesReturn);
//         item.setRollNo(dto.getRollNo());
//         item.setPieceNo(dto.getPieceNo());
//         item.setPackingListNo(dto.getPackingListNo());
//         item.setMeter(dto.getMeter());
//         item.setNetWeight(dto.getNetWeight());
//         return item;
//     }


//     private SalesYarnReturn toYarnEntity(SalesYarnReturnDTO dto, SalesReturn salesReturn) {
//         SalesYarnReturn item = new SalesYarnReturn();
//         item.setSalesReturn(salesReturn);
//         item.setYarnName(dto.getYarnName());
//         // item.setAvailableQty(dto.getAvailableQty());
//         item.setIssuedQty(dto.getIssuedQty());
//         item.setLotNo(dto.getLotNo());
//         item.setWarehouseId(dto.getWarehouseId());
//         item.setNoOfBags(dto.getNoOfBags());
//         item.setIssuedBags(dto.getIssuedBags());
//         item.setConesPerBag(dto.getConesPerBag());
//         item.setConesWeight(dto.getConesWeight());
//         item.setMillId(dto.getMillId());
//         return item;
//     }

//     private SalesReturnSizing toSizingEntity(SalesReturnSizingDTO dto, SalesReturn salesReturn) {
//         SalesReturnSizing item = new SalesReturnSizing();
//         item.setSalesReturn(salesReturn);
//         item.setSizedBeamNo(dto.getSizedBeamNo());
//         item.setFlangeNo(dto.getFlangeNo());
//         item.setBeamMeter(dto.getBeamMeter());
//         item.setNetWeight(dto.getNetWeight());
//         item.setWidth(dto.getWidth());
//         item.setExpectedFabricMeter(dto.getExpectedFabricMeter());
//         item.setTotalEnds(dto.getTotalEnds());
//         return item;
//     }

//     private SalesReturnDTO toDto(SalesReturn entity) {
//         SalesReturnDTO dto = new SalesReturnDTO(entity);
        
//         // Explicitly set these fields to ensure they are passed to the response
//         dto.setSizingVendorId(entity.getSizingVendorId());
//         dto.setSizingPlanId(entity.getSizingPlanId());
//         dto.setWeaverId(entity.getWeaverId());
//         dto.setBeamInwardNo(entity.getBeamInwardNo());
//         dto.setSizingBeamOutwardNo(entity.getSizingBeamOutwardNo());
//         dto.setTotalRolls(entity.getTotalRolls());

//         List<SalesReturnItem> items = salesReturnItemRepository.findBySalesReturnId(entity.getId());
//         dto.setItems(items != null ? items.stream()
//                 .map(SalesReturnItemDTO::new)
//                 .collect(Collectors.toList()) : new ArrayList<>());

//         List<SalesYarnReturn> yarnItems = salesYarnReturnRepository.findBySalesReturnId(entity.getId());
//         dto.setYarnItems(yarnItems != null ? yarnItems.stream()
//                 .map(SalesYarnReturnDTO::new)
//                 .collect(Collectors.toList()) : new ArrayList<>());

//         List<SalesReturnBeamEntity> beamItems = salesReturnBeamRepository.findBySalesReturnId(entity.getId());
//         dto.setBeamItems(beamItems != null ? beamItems.stream()
//                 .map(SalesReturnBeamDTO::new)
//                 .collect(Collectors.toList()) : new ArrayList<>());

//         List<SalesReturnSizing> sizingItems = salesReturnSizingRepository.findBySalesReturnId(entity.getId());
//         dto.setSizingItems(sizingItems != null ? sizingItems.stream()
//                 .map(SalesReturnSizingDTO::new)
//                 .collect(Collectors.toList()) : new ArrayList<>());
        
// //        enrichDtoWithMediaDetails(dto);
//         return dto;
//     }

// //    private void enrichDtoWithMediaDetails(SalesReturnDTO dto) {
// //        if (mediaService == null) {
// //            return;
// //        }
// //        
// //        if (dto.getDcUpload() != null) {
// //            try {
// //                dto.setDcUploadDetail(mediaService.getFirstMediaDetailByMediaSno(dto.getDcUpload().longValue()));
// //            } catch (Exception e) {
// //                logger.debug("Failed to fetch media details for dcUpload mediaSno: {}", dto.getDcUpload(), e);
// //            }
// //        }
// //        
// //        if (dto.getInvoiceUpload() != null) {
// //            try {
// //                dto.setInvoiceUploadDetail(mediaService.getFirstMediaDetailByMediaSno(dto.getInvoiceUpload().longValue()));
// //            } catch (Exception e) {
// //                logger.debug("Failed to fetch media details for invoiceUpload mediaSno: {}", dto.getInvoiceUpload(), e);
// //            }
// //        }
// //    }

//     @Transactional
//     private void createPieceEntries(List<SalesReturnItem> items, SalesReturn salesReturn) {
//         String invoiceType = salesReturn.getInvoiceType();
//         boolean isSpecialInvoice = "Weaving jobwork".equalsIgnoreCase(invoiceType) || 
//                                   "Domestic".equalsIgnoreCase(invoiceType) || 
//                                   "Export".equalsIgnoreCase(invoiceType);

//         Long resolvedFabricMasterId = null;
//         if (salesReturn.getFabricCode() != null && !salesReturn.getFabricCode().isEmpty()) {
//             resolvedFabricMasterId = wovenFabricMasterRepository
//                     .findFirstByFabricCode(salesReturn.getFabricCode())
//                     .map(WovenFabricMaster::getId)
//                     .orElse(null);
//         }

//         BaleRollPacking baleRollPacking = null;
//         if (isSpecialInvoice) {
//             baleRollPacking = new BaleRollPacking();
//             baleRollPacking.setPackingDate(Date.from(salesReturn.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//             baleRollPacking.setBuyerId(salesReturn.getCustomerId());
//             baleRollPacking.setWarehouseId(salesReturn.getWarehouseId());
//             baleRollPacking.setCompanyId(salesReturn.getCompanyId());
//             baleRollPacking.setIsDispatched(0);
//             baleRollPacking.setTotalPieces((long) items.size());
            
//             BigDecimal totalMeters = items.stream()
//                 .map(SalesReturnItem::getMeter)
//                 .filter(m -> m != null)
//                 .reduce(BigDecimal.ZERO, BigDecimal::add);
//             baleRollPacking.setTotalMeters(totalMeters);
            
//             BigDecimal totalNet = items.stream()
//                 .map(SalesReturnItem::getNetWeight)
//                 .filter(w -> w != null)
//                 .reduce(BigDecimal.ZERO, BigDecimal::add);
//             baleRollPacking.setTotalNet(totalNet);
//             baleRollPacking.setPackingType("Roll");
            
//             baleRollPacking = baleRollPackingRepository.save(baleRollPacking);
//         }

//         for (SalesReturnItem item : items) {
//             PieceEntry pieceEntry = new PieceEntry();
            
//             pieceEntry.setIsInspected(null);
//             pieceEntry.setIsPacked(null);
//             pieceEntry.setIsDispatched(null);
//             pieceEntry.setInvoiceId(0L);
            
//             if (isSpecialInvoice) {
//                 pieceEntry.setSourceType("Sales Return");
//                 pieceEntry.setInspectionStatus("INSPECTED");
//             } else {
//                 pieceEntry.setSourceType("Fabric Return");
//             }
            
//             String pieceNumberForInspection = item.getPieceNo() + "-R";
//             pieceEntry.setPieceNumber(pieceNumberForInspection);
            
//             pieceEntry.setQuantity(item.getMeter());
//             pieceEntry.setWeight(item.getNetWeight());
            
//             pieceEntry.setActiveFlag(true);
//             pieceEntry.setCompanyId(salesReturn.getCompanyId());
//             pieceEntry.setFabricMasterId(resolvedFabricMasterId);
            
//             if (salesReturn.getWarehouseId() != null) {
//                 Warehouse warehouse = warehouseRepository.findById(salesReturn.getWarehouseId()).orElse(null);
//                 if (warehouse != null) {
//                     pieceEntry.setWarehouse(warehouse);
//                 }
//             }
            
//             pieceEntry = pieceEntryRepository.save(pieceEntry);

//             if (isSpecialInvoice && baleRollPacking != null) {
//                 BaleRollPackingItem packingItem = new BaleRollPackingItem();
//                 packingItem.setBaleRollPacking(baleRollPacking);
//                 packingItem.setPieceId(pieceEntry.getId());
//                 packingItem.setPieceNo(pieceEntry.getPieceNumber());
//                 packingItem.setMeters(item.getMeter());
//                 packingItem.setNetWeight(item.getNetWeight());
//                 baleRollPackingItemRepository.save(packingItem);
//             }
//         }
//     }

//     private void handleBeamReturn(List<SalesReturnBeamDTO> beamItems, SalesReturn salesReturn) {
//         List<SalesReturnBeamEntity> entities = new ArrayList<>();
//         for (SalesReturnBeamDTO beamDto : beamItems) {
//             SalesReturnBeamEntity beamEntity = toBeamEntity(beamDto, salesReturn);
//             entities.add(salesReturnBeamRepository.save(beamEntity));

//             Long weavingContractId = null;
//             if (beamDto.getWeavingContractNo() != null && !beamDto.getWeavingContractNo().isEmpty()) {
//                 Optional<WeavingContract> contract = weavingContractRepository.findByWeavingContractNo(beamDto.getWeavingContractNo());
//                 if (contract.isPresent()) {
//                     weavingContractId = contract.get().getId();
//                 }
//             }

//             List<BeamInwardBeamDetails> existingInwardDetails = findMatchingInwardDetails(beamDto, weavingContractId);
//             List<BeamOutwardDetail> existingOutwards = findMatchingOutwardDetails(beamDto);

//             boolean isEdited = checkIfBeamEdited(beamDto, existingInwardDetails, existingOutwards, weavingContractId);
//             if (isEdited) {
//                 createNewBeamMovement(beamDto, salesReturn, existingInwardDetails, existingOutwards, weavingContractId);
//             } else {
//                 updateExistingBeamMovement(beamDto, salesReturn, existingInwardDetails, existingOutwards);
//             }
//         }
//         if (salesReturn.getBeams() == null) {
//             salesReturn.setBeams(new ArrayList<>());
//         }
//         salesReturn.getBeams().clear();
//         salesReturn.getBeams().addAll(entities);
//     }

//     private void handleSizingReturn(List<SalesReturnSizingDTO> sizingItems, SalesReturn salesReturn) {
//         List<SalesReturnSizing> entities = new ArrayList<>();
//         boolean isSizingSalesType = isSizingSales(salesReturn.getInvoiceType());
//         for (SalesReturnSizingDTO sizingDto : sizingItems) {
//             SalesReturnSizing sizingEntity = toSizingEntity(sizingDto, salesReturn);
//             entities.add(salesReturnSizingRepository.save(sizingEntity));

//             String searchBeamNo = normalizeSizedBeamNo(sizingDto.getSizedBeamNo());
//             List<SizingProductionDetail> productionDetails = sizingProductionDetailRepository.findBySizedBeamNoAndIsReturn(searchBeamNo, 0);
//             List<SizingBeamOutwardDetail> outwardDetails = sizingBeamOutwardDetailRepository.findBySizedBeamNoAndSizingBeamOutwardIsReturn(searchBeamNo, 0);

//             boolean isEdited = checkIfSizingEdited(sizingDto, productionDetails, outwardDetails);

//             if (isEdited) {
//                 createNewSizingMovement(sizingDto, salesReturn, productionDetails, outwardDetails, isSizingSalesType);
//             } else {
//                 updateExistingSizingMovement(sizingDto, salesReturn, productionDetails, outwardDetails, isSizingSalesType);
//             }
//         }
//         if (salesReturn.getSizingItems() == null) {
//             salesReturn.setSizingItems(new ArrayList<>());
//         }
//         salesReturn.getSizingItems().clear();
//         salesReturn.getSizingItems().addAll(entities);
//     }

//     private boolean checkIfSizingEdited(SalesReturnSizingDTO dto, List<SizingProductionDetail> productionDetails, List<SizingBeamOutwardDetail> outwardDetails) {
//         if (productionDetails.isEmpty() && outwardDetails.isEmpty()) {
//             return true;
//         }

//         boolean productionEdited = false;
//         if (!productionDetails.isEmpty()) {
//             SizingProductionDetail production = productionDetails.get(0);
//             productionEdited = !isEqual(dto.getBeamMeter(), production.getBeamMeters()) ||
//                              !isEqual(dto.getExpectedFabricMeter(), production.getExpectedFabricMeter()) ||
//                              !isEqual(dto.getNetWeight(), production.getNetWt()) ||
//                              !isEqual(dto.getTotalEnds(), production.getTotalEnds());
//         }

//         boolean outwardEdited = false;
//         if (!outwardDetails.isEmpty()) {
//             SizingBeamOutwardDetail outward = outwardDetails.get(0);
//             outwardEdited = !isEqual(dto.getBeamMeter(), outward.getBeamMeters()) ||
//                             !isEqual(dto.getExpectedFabricMeter(), outward.getExpectedFabricMeter()) ||
//                             !isEqual(dto.getTotalEnds(), outward.getTotalEnds());
//         }

//         return productionEdited || outwardEdited;
//     }

//     private void updateExistingSizingMovement(SalesReturnSizingDTO dto, SalesReturn salesReturn, List<SizingProductionDetail> productionDetails, List<SizingBeamOutwardDetail> outwardDetails, boolean isSizingSalesType) {
//         if (productionDetails.isEmpty()) {
//             productionDetails = sizingProductionDetailRepository.findBySizedBeamNo(normalizeSizedBeamNo(dto.getSizedBeamNo()));
//         }
//         for (SizingProductionDetail production : productionDetails) {
//             production.setIsReturn(1);
//             production.setReturnDate(salesReturn.getDate());
//             production.setReturnNo(salesReturn.getSalesReturnNo());
            
//             sizingProductionDetailRepository.save(production);
//         }

//         for (SizingBeamOutwardDetail detail : outwardDetails) {
//             SizingBeamOutward outward = detail.getSizingBeamOutward();
//             if (outward != null) {
//                 outward.setIsReturn(1);
//                 outward.setReturnDate(salesReturn.getDate());
//                 outward.setReturnNo(salesReturn.getSalesReturnNo());
//                 sizingBeamOutwardRepository.save(outward);
//             }
//         }
        
//     }

//     private void createNewSizingMovement(SalesReturnSizingDTO dto, SalesReturn salesReturn, List<SizingProductionDetail> productionDetails, List<SizingBeamOutwardDetail> outwardDetails, boolean isSizingSalesType) {
//         if (productionDetails.isEmpty()) {
//             productionDetails = sizingProductionDetailRepository.findBySizedBeamNo(normalizeSizedBeamNo(dto.getSizedBeamNo()));
//         }
//         if (!productionDetails.isEmpty()) {
//             SizingProductionDetail original = productionDetails.get(0);
//             SizingProductionDetail newProduction = new SizingProductionDetail();
            
//             newProduction.setSizingProduction(original.getSizingProduction());
//             newProduction.setShiftName(original.getShiftName());
//             newProduction.setFlangeNo(original.getFlangeNo());
//             newProduction.setFlangeId(original.getFlangeId());
//             newProduction.setSizedBeamNo(isSizingSalesType ? appendReturnSuffix(original.getSizedBeamNo()) : original.getSizedBeamNo());
//             newProduction.setSizingPickup(original.getSizingPickup());
//             newProduction.setBreakages(original.getBreakages());
//             newProduction.setRpm(original.getRpm());
//             newProduction.setTareWt(original.getTareWt());
//             newProduction.setGrossWt(original.getGrossWt());
//             newProduction.setFabricPanelWidth(original.getFabricPanelWidth());
//             newProduction.setEmployeeId(original.getEmployeeId());
//             newProduction.setOrderTypeId(original.getOrderTypeId());
//             newProduction.setIssuedTradingCompanyId(original.getIssuedTradingCompanyId());
//             newProduction.setIsTradingInward(original.getIsTradingInward());
//             newProduction.setIsIssuedToWeaving(original.getIsIssuedToWeaving());
//             newProduction.setIsFactoryInward(original.getIsFactoryInward());
//             newProduction.setIsSizingBeamOutward(0);

//             newProduction.setBeamMeters(dto.getBeamMeter() != null ? dto.getBeamMeter() : original.getBeamMeters());
//             newProduction.setExpectedFabricMeter(dto.getExpectedFabricMeter() != null ? dto.getExpectedFabricMeter() : original.getExpectedFabricMeter());
//             newProduction.setNetWt(dto.getNetWeight() != null ? dto.getNetWeight() : original.getNetWt());
//             newProduction.setTotalEnds(dto.getTotalEnds() != null ? dto.getTotalEnds() : original.getTotalEnds());

//             newProduction.setIsReturn(0);
//             newProduction.setReturnDate(null);
//             newProduction.setReturnNo(null);
            
//             sizingProductionDetailRepository.save(newProduction);

//             for (SizingProductionDetail detail : productionDetails) {
//                 detail.setIsReturn(1);
//                 detail.setReturnDate(salesReturn.getDate());
//                 detail.setReturnNo(salesReturn.getSalesReturnNo());
//                 sizingProductionDetailRepository.save(detail);
//             }
//         }

//        if (outwardDetails.isEmpty()) {
//            outwardDetails = sizingBeamOutwardDetailRepository.findBySizedBeamNo(normalizeSizedBeamNo(dto.getSizedBeamNo()));
//        }
//        for (SizingBeamOutwardDetail detail : outwardDetails) {
//            SizingBeamOutward outward = detail.getSizingBeamOutward();
//            if (outward != null) {
//                outward.setIsReturn(1);
//                outward.setReturnDate(salesReturn.getDate());
//                outward.setReturnNo(salesReturn.getSalesReturnNo());
//                outward.setTotalBeamMeters(dto.getBeamMeter() != null ? dto.getBeamMeter() : outward.getTotalBeamMeters());
//                sizingBeamOutwardRepository.save(outward);
//            }
//        }
//    }

//     private boolean checkIfBeamEdited(SalesReturnBeamDTO dto, List<BeamInwardBeamDetails> inwardDetails, List<BeamOutwardDetail> outwardDetails, Long weavingContractId) {
//         if (inwardDetails.isEmpty() && outwardDetails.isEmpty()) {
//             return true;
//         }

//         boolean inwardEdited = false;
//         if (!inwardDetails.isEmpty()) {
//             BeamInwardBeamDetails inward = inwardDetails.get(0);
//             inwardEdited = !isEqual(dto.getBeamMeters(), inward.getWrapMeters()) ||
//                            !isEqual(dto.getExpectedFabricMeter(), inward.getExpectedFabricMeter()) ||
//                            !isEqual(dto.getShrinkage(), inward.getShrinkage()) ||
//                            (weavingContractId != null && !weavingContractId.equals(inward.getWeavingContractId()));
//         }

//         boolean outwardEdited = false;
//         if (!outwardDetails.isEmpty()) {
//             BeamOutwardDetail outwardDetail = outwardDetails.get(0);
//             outwardEdited = !isEqual(dto.getBeamMeters(), outwardDetail.getWrapMeters()) ||
//                             !isEqual(dto.getExpectedFabricMeter(), outwardDetail.getExpectedFabricMeter()) ||
//                             !isEqual(dto.getShrinkage(), outwardDetail.getShrinkage()) ||
//                             (weavingContractId != null && !weavingContractId.equals(outwardDetail.getWeavingContractId()));
//         }

//         return inwardEdited || outwardEdited;
//     }

//     private List<BeamInwardBeamDetails> findMatchingInwardDetails(SalesReturnBeamDTO dto, Long weavingContractId) {
//         String searchBeamNo = normalizeSizedBeamNo(dto.getSizedBeamNo());
//         List<BeamInwardBeamDetails> matches = new ArrayList<>();
//         if (dto.getBeamInwardId() != null && searchBeamNo != null && !searchBeamNo.isEmpty()) {
//             matches = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNoAndIsReturn(dto.getBeamInwardId(), searchBeamNo);
//         }
//         if (!matches.isEmpty()) {
//             return matches;
//         }
//         if (weavingContractId != null && searchBeamNo != null && !searchBeamNo.isEmpty()) {
//             matches = beamInwardBeamDetailsRepository.findByWeavingContractIdAndSizedBeamNoAndIsReturn(weavingContractId, searchBeamNo);
//         }
//         if (!matches.isEmpty()) {
//             return matches;
//         }
//         if (searchBeamNo != null && !searchBeamNo.isEmpty()) {
//             matches = beamInwardBeamDetailsRepository.findBySizedBeamNoAndIsReturn(searchBeamNo);
//         }
//         return matches;
//     }

//     private List<BeamOutwardDetail> findMatchingOutwardDetails(SalesReturnBeamDTO dto) {
//         String searchBeamNo = normalizeSizedBeamNo(dto.getSizedBeamNo());
//         List<BeamOutwardDetail> matches = new ArrayList<>();
//         if (dto.getBeamOutwardId() != null && searchBeamNo != null && !searchBeamNo.isEmpty()) {
//             matches = beamOutwardDetailRepository.findByBeamOutwardIdAndSizedBeamNoAndIsReturn(dto.getBeamOutwardId(), searchBeamNo);
//         }
//         if (!matches.isEmpty()) {
//             return matches;
//         }
//         if (dto.getBeamOutwardId() != null) {
//             matches = beamOutwardDetailRepository.findByBeamOutwardIdAndIsReturn(dto.getBeamOutwardId());
//             if (searchBeamNo != null && !searchBeamNo.isEmpty()) {
//                 final String sizedBeamNo = searchBeamNo;
//                 matches = matches.stream()
//                         .filter(detail -> sizedBeamNo.equals(detail.getSizedBeamNo()))
//                         .collect(Collectors.toList());
//             }
//         }
//         if (!matches.isEmpty()) {
//             return matches;
//         }
//         if (searchBeamNo != null && !searchBeamNo.isEmpty()) {
//             matches = beamOutwardDetailRepository.findBySizedBeamNoAndIsReturn(searchBeamNo);
//         }
//         return matches;
//     }

//     private void updateExistingBeamMovement(SalesReturnBeamDTO dto, SalesReturn salesReturn, List<BeamInwardBeamDetails> inwardDetails, List<BeamOutwardDetail> outwardDetails) {
//         String searchBeamNo = normalizeSizedBeamNo(dto.getSizedBeamNo());
//         List<BeamInwardBeamDetails> inwardToUpdate = inwardDetails;
//         if (inwardToUpdate.isEmpty() && searchBeamNo != null) {
//             inwardToUpdate = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(Arrays.asList(searchBeamNo));
//         }
//         for (BeamInwardBeamDetails inward : inwardToUpdate) {
//             inward.setIsReturn(1);
//             inward.setReturnDate(salesReturn.getDate());
//             inward.setReturnNo(salesReturn.getSalesReturnNo());
//             inward.setReturnWeaverId(dto.getWeaverId());
//             inward.setIsBeamOutwarded(0);
//             beamInwardBeamDetailsRepository.save(inward);
//         }

//         List<BeamOutwardDetail> outwardToUpdate = outwardDetails;
//         if (outwardToUpdate.isEmpty() && searchBeamNo != null) {
//             outwardToUpdate = beamOutwardDetailRepository.findBySizedBeamNo(searchBeamNo);
//         }
//         for (BeamOutwardDetail detail : outwardToUpdate) {
//             detail.setIsReturn(1);
//             detail.setReturnDate(salesReturn.getDate());
//             detail.setReturnNo(salesReturn.getSalesReturnNo());
//             detail.setReturnWeaverId(dto.getWeaverId());
//             beamOutwardDetailRepository.save(detail);
//         }
//     }

//     private void createNewBeamMovement(SalesReturnBeamDTO dto, SalesReturn salesReturn, List<BeamInwardBeamDetails> inwardDetails, List<BeamOutwardDetail> outwardDetails, Long weavingContractId) {
//         String searchBeamNo = normalizeSizedBeamNo(dto.getSizedBeamNo());
//         if (inwardDetails.isEmpty()) {
//             inwardDetails = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(Arrays.asList(searchBeamNo));
//         }
        
//         BeamInwardBeamDetails newInward = new BeamInwardBeamDetails();
//         if (!inwardDetails.isEmpty()) {
//             BeamInwardBeamDetails original = inwardDetails.get(0);
//             Double originalMeters = original.getWrapMeters();
//             Double returnedMeters = dto.getBeamMeters() != null ? dto.getBeamMeters().doubleValue() : originalMeters;
//             Double remainingMeters = calculateRemainingMeters(originalMeters, returnedMeters);
            
//             newInward.setWeavingContractId(weavingContractId != null ? weavingContractId : original.getWeavingContractId());
//             newInward.setSalesOrderId(original.getSalesOrderId());
//             newInward.setShrinkage(original.getShrinkage());
//             newInward.setTotalEnds(original.getTotalEnds());
//             newInward.setSizedBeamNo(searchBeamNo != null ? searchBeamNo : normalizeSizedBeamNo(original.getSizedBeamNo()));
//             newInward.setFlangeNo(original.getFlangeNo());
//             newInward.setFlangeId(original.getFlangeId());
//             newInward.setWeavingVendorId(original.getWeavingVendorId());
//             newInward.setWeaverVendorName(original.getWeaverVendorName());
//             newInward.setSizingPlanId(original.getSizingPlanId());
//             newInward.setFabricId(original.getFabricId());
//             newInward.setFabricPanelWidth(original.getFabricPanelWidth());
//             newInward.setBeamInward(original.getBeamInward());
            
//             // Mark all matching original records as returned
//             for (BeamInwardBeamDetails detail : inwardDetails) {
//                 detail.setIsReturn(1);
//                 detail.setReturnDate(salesReturn.getDate());
//                 detail.setReturnNo(salesReturn.getSalesReturnNo());
//                 detail.setReturnWeaverId(dto.getWeaverId());
//                 detail.setIsBeamOutwarded(0);
//                 if (remainingMeters != null) {
//                     detail.setWrapMeters(remainingMeters);
//                 }
//                 if (dto.getExpectedFabricMeter() != null) {
//                     detail.setExpectedFabricMeter(dto.getExpectedFabricMeter().doubleValue());
//                 }
//                 if (dto.getShrinkage() != null) {
//                     detail.setShrinkage(dto.getShrinkage().doubleValue());
//                 }
//                 beamInwardBeamDetailsRepository.save(detail);
//             }
//             newInward.setWrapMeters(returnedMeters);
//         } else {
//             // Mapping other beam reference fields from the selected beam in DTO
//             newInward.setSizedBeamNo(searchBeamNo);
//             newInward.setFlangeNo(dto.getFlangeNo());
//             newInward.setWeavingContractId(weavingContractId);
//             newInward.setWeaverVendorName(dto.getWeaverName());
//             newInward.setWeavingVendorId(dto.getWeaverId());
//             if (dto.getFabricPanelWidth() != null) {
//                 try {
//                     newInward.setFabricPanelWidth(Double.parseDouble(dto.getFabricPanelWidth()));
//                 } catch (Exception e) {}
//             }
//         }

//         newInward.setIsBeamOutwarded(0);
//         newInward.setReturnWeaverId(null);

//         if (dto.getShrinkage() != null) newInward.setShrinkage(dto.getShrinkage().doubleValue());
//         else if (newInward.getShrinkage() == null && !inwardDetails.isEmpty()) newInward.setShrinkage(inwardDetails.get(0).getShrinkage());

//         if (newInward.getWrapMeters() == null && dto.getBeamMeters() != null) newInward.setWrapMeters(dto.getBeamMeters().doubleValue());
//         else if (newInward.getWrapMeters() == null && !inwardDetails.isEmpty()) newInward.setWrapMeters(inwardDetails.get(0).getWrapMeters());
        
//         if (dto.getExpectedFabricMeter() != null) newInward.setExpectedFabricMeter(dto.getExpectedFabricMeter().doubleValue());
//         else if (newInward.getExpectedFabricMeter() == null && !inwardDetails.isEmpty()) newInward.setExpectedFabricMeter(inwardDetails.get(0).getExpectedFabricMeter());

//         newInward.setIsReturn(0);
//         newInward.setReturnDate(null);
//         newInward.setReturnNo(null);
//         newInward.setSizedBeamNo(ensureReturnSuffix(normalizeSizedBeamNo(newInward.getSizedBeamNo())));
//         beamInwardBeamDetailsRepository.save(newInward);

//         if (outwardDetails.isEmpty() && searchBeamNo != null) {
//             outwardDetails = beamOutwardDetailRepository.findBySizedBeamNo(searchBeamNo);
//         }

//         for (BeamOutwardDetail detail : outwardDetails) {
//             Double originalMeters = detail.getWrapMeters();
//             Double returnedMeters = dto.getBeamMeters() != null ? dto.getBeamMeters().doubleValue() : originalMeters;
//             Double remainingMeters = calculateRemainingMeters(originalMeters, returnedMeters);

//             detail.setIsReturn(1);
//             detail.setReturnDate(salesReturn.getDate());
//             detail.setReturnNo(salesReturn.getSalesReturnNo());
//             detail.setReturnWeaverId(dto.getWeaverId());
//             detail.setWrapMeters(remainingMeters);
//             if (dto.getExpectedFabricMeter() != null) {
//                 detail.setExpectedFabricMeter(dto.getExpectedFabricMeter().doubleValue());
//             }
//             if (dto.getShrinkage() != null) {
//                 detail.setShrinkage(dto.getShrinkage().doubleValue());
//             }
//             beamOutwardDetailRepository.save(detail);
//         }
//     }

//     private String ensureReturnSuffix(String sizedBeamNo) {
//         if (sizedBeamNo == null || sizedBeamNo.isEmpty()) {
//             return sizedBeamNo;
//         }
//         return sizedBeamNo.endsWith("-R") ? sizedBeamNo : sizedBeamNo + "-R";
//     }

//     private Double calculateRemainingMeters(Double originalMeters, Double returnedMeters) {
//         if (originalMeters == null) {
//             return returnedMeters;
//         }
//         if (returnedMeters == null) {
//             return originalMeters;
//         }
//         return Math.max(0d, originalMeters - returnedMeters);
//     }
    

//     private boolean isEqual(Double d1, Double d2) {
//         if (d1 == null && d2 == null) return true;
//         if (d1 == null || d2 == null) return false;
//         return d1.equals(d2);
//     }

//     private boolean isEqual(Integer i1, Integer i2) {
//         if (i1 == null && i2 == null) return true;
//         if (i1 == null || i2 == null) return false;
//         return i1.equals(i2);
//     }

//     private boolean isEqual(BigDecimal bd, Double d) {
//         if (bd == null && d == null) return true;
//         if (bd == null || d == null) return false;
//         return bd.stripTrailingZeros().equals(BigDecimal.valueOf(d).stripTrailingZeros());
//     }

//     private boolean isEqual(BigDecimal bd, Long l) {
//         if (bd == null && l == null) return true;
//         if (bd == null || l == null) return false;
//         return bd.stripTrailingZeros().equals(BigDecimal.valueOf(l).stripTrailingZeros());
//     }

//     private SalesReturnBeamEntity toBeamEntity(SalesReturnBeamDTO dto, SalesReturn salesReturn) {
//         SalesReturnBeamEntity entity = new SalesReturnBeamEntity();
//         entity.setSalesReturn(salesReturn);
//         entity.setBeamOutwardNo(dto.getBeamOutwardNo());
//         entity.setBeamOutwardId(dto.getBeamOutwardId());
//         entity.setBeamInwardId(dto.getBeamInwardId());
        
//         // Fetch beamOutwardNo based on beamOutwardId if provided
//         if (dto.getBeamOutwardId() != null && (dto.getBeamOutwardNo() == null || dto.getBeamOutwardNo().isEmpty())) {
//             Optional<BeamOutward> beamOutward = beamOutwardRepository.findById(dto.getBeamOutwardId());
//             if (beamOutward.isPresent()) {
//                 entity.setBeamOutwardNo(beamOutward.get().getOutwardNo());
//             }
//         }
        
//         // Fetch beamOutwardNo based on beamInwardId if provided
//         if (dto.getBeamInwardId() != null && (dto.getBeamOutwardNo() == null || dto.getBeamOutwardNo().isEmpty())) {
//             List<BeamOutward> beamOutwards = beamOutwardRepository.findByBeamInwardId(dto.getBeamInwardId());
//             if (!beamOutwards.isEmpty()) {
//                 entity.setBeamOutwardNo(beamOutwards.get(0).getOutwardNo());
//             }
//         }
        
//         if (dto.getOutwardDate() != null && !dto.getOutwardDate().isEmpty()) {
//             entity.setOutwardDate(LocalDate.parse(dto.getOutwardDate()));
//         }
//         entity.setWeavingContractNo(dto.getWeavingContractNo());
//         entity.setWeaverName(dto.getWeaverName());
//         entity.setSizingPlanNo(dto.getSizingPlanNo());
//         entity.setSalesOrderNo(dto.getSalesOrderNo());
//         entity.setFlangeNo(dto.getFlangeNo());
//         entity.setSizedBeamNo(dto.getSizedBeamNo());
//         entity.setBeamMeters(dto.getBeamMeters());
//         entity.setExpectedFabricMeter(dto.getExpectedFabricMeter());
//         entity.setTotalBeams(dto.getTotalBeams());
//         entity.setWeaverId(dto.getWeaverId());
//         entity.setFabricPanelWidth(dto.getFabricPanelWidth());
//         entity.setShrinkage(dto.getShrinkage());
//         entity.setIsReturn(dto.getIsReturn() != null ? dto.getIsReturn() : 1);

//         entity.setReturnDate(salesReturn.getDate());
//         entity.setReturnNo(salesReturn.getSalesReturnNo());
//         entity.setReturnWeaverId(dto.getWeaverId());
//         return entity;
//     }

//     private void ensureItemFieldLengths(SalesReturnItemDTO dto) {
//         if (dto == null) {
//             return;
//         }
//         dto.setRollNo(limit(dto.getRollNo(), 100, "rollNo"));
//         dto.setPieceNo(limit(dto.getPieceNo(), 100, "pieceNo"));
//         dto.setPackingListNo(limit(dto.getPackingListNo(), 100, "packingListNo"));
//     }

//     private String limit(String value, int maxLength, String fieldName) {
//         if (value == null) {
//             return null;
//         }
//         if (value.length() <= maxLength) {
//             return value;
//         }
//         logger.warn("Truncating {} to {} characters", fieldName, maxLength);
//         return value.substring(0, maxLength);
//     }

//     public PaginatedResponse<SalesReturnDTO> getAllSalesReturnsByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         return buildPaginatedResponse(
//                 salesReturnRepository.findIdsByCompanyId(companyId),
//                 offset,
//                 limit,
//                 ids -> ids.isEmpty() ? new ArrayList<>() : salesReturnRepository.findByCompanyIdAndIds(companyId, ids)
//         );
//     }

//     public SalesReturnDTO getSalesReturnByIdAndCompany(Long id, Long companyId) {
//         SalesReturn entity = salesReturnRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("SalesReturn not found with id: " + id + " and companyId: " + companyId));
//         return toDto(entity);
//     }

//     @Transactional
//     public SalesReturnDTO updateSalesReturnByCompany(Long id, Long companyId, SalesReturnDTO dto) {
//         SalesReturn entity = salesReturnRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("SalesReturn not found with id: " + id + " and companyId: " + companyId));
        
//         mapDtoToEntity(dto, entity);
//         updateItemsCollection(entity, dto.getItems());
//         updateYarnItemsCollection(entity, dto.getYarnItems());
        
//         SalesReturn updatedEntity = salesReturnRepository.save(entity);
        
//         // Handle Yarn Sales Return for inventory
//         if (isYarnSales(dto.getInvoiceType()) && updatedEntity.getYarnItems() != null && !updatedEntity.getYarnItems().isEmpty()) {
//             handleYarnSalesReturn(updatedEntity);
//         }
        
//         // Create piece entries and handle beam returns (only if not Yarn Sales)
//         if (!isYarnSales(dto.getInvoiceType())) {
//             if (dto.getItems() != null && !dto.getItems().isEmpty()) {
//                 cleanupPieceEntriesAndPacking(updatedEntity);
//                 createPieceEntries(updatedEntity.getItems(), updatedEntity);
//             }
//             if (!isSizingSales(dto.getInvoiceType())) {
//                 if (dto.getBeamItems() != null && !dto.getBeamItems().isEmpty()) {
//                     salesReturnBeamRepository.deleteBySalesReturnId(id);
//                     handleBeamReturn(dto.getBeamItems(), updatedEntity);
//                 }
//                 // Clear sizing items if it's not Sizing Sales
//                 updatedEntity.getSizingItems().clear();
//             } else {
//                 if (dto.getSizingItems() != null && !dto.getSizingItems().isEmpty()) {
//                     salesReturnSizingRepository.deleteBySalesReturnId(id);
//                     handleSizingReturn(dto.getSizingItems(), updatedEntity);
//                 }
//                 // Clear beam items if it IS Sizing Sales
//                 updatedEntity.getBeams().clear();
//             }
//         }
//         return getSalesReturnByIdAndCompany(id, companyId);
//     }

//     @Transactional
//     public void deleteSalesReturnByCompany(Long id, Long companyId) {
//         SalesReturn entity = salesReturnRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("SalesReturn not found with id: " + id + " and companyId: " + companyId));
        
//         // Delete related YarnInventory and YarnInwardStockDetails if it's Yarn Sales
//         if (isYarnSales(entity.getInvoiceType())) {
//             cleanupYarnInventory(id, getYarnSalesReturnInwardTypeId());
//         }

//         // Cleanup PieceEntry and BaleRollPacking records created during save
//         cleanupPieceEntriesAndPacking(entity);

//         // Cleanup Sizing flow related records
//         if (isSizingSales(entity.getInvoiceType())) {
//             cleanupSizingMovement(entity);
//         }

//         // Cleanup Beam flow related records
//         if (!isYarnSales(entity.getInvoiceType()) && !isSizingSales(entity.getInvoiceType())) {
//             cleanupBeamMovement(entity);
//         }
        
//         salesReturnItemRepository.findBySalesReturnId(id).forEach(item ->
//             salesReturnItemRepository.delete(item)
//         );

//         salesYarnReturnRepository.findBySalesReturnId(id).forEach(item ->
//             salesYarnReturnRepository.delete(item)
//         );

//         salesReturnBeamRepository.deleteBySalesReturnId(id);
//         salesReturnSizingRepository.deleteBySalesReturnId(id);
        
//         salesReturnRepository.deleteByIdAndCompanyId(id, companyId);
//     }

//     private PaginatedResponse<SalesReturnDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit, java.util.function.Function<List<Long>, List<SalesReturn>> fetcher) {
//         long off = offset != null ? Math.max(0, offset) : 0;
//         long lim = (limit != null && limit > 0) ? limit : 10;
//         long total = allIds.size();
//         int start = (int) Math.min(off, total);
//         if (start >= total) {
//             return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, new ArrayList<>());
//         }
//         int end = (int) Math.min(off + lim, total);
//         List<Long> pageIds = allIds.subList(start, end);
//         List<SalesReturn> returns = pageIds.isEmpty() ? new ArrayList<>() : fetcher.apply(pageIds);
//         List<SalesReturnDTO> dtos = returns.stream()
//                 .map(this::toDto)
//                 .collect(Collectors.toList());
//         return new PaginatedResponse<>(
//                 total,
//                 end < total,
//                 off > 0,
//                 off,
//                 lim,
//                 dtos
//         );
//     }

//     private String normalizeSizedBeamNo(String sizedBeamNo) {
//         if (sizedBeamNo != null && sizedBeamNo.endsWith("-R")) {
//             return sizedBeamNo.substring(0, sizedBeamNo.length() - 2);
//         }
//         return sizedBeamNo;
//     }

//     private boolean isSizingSales(String invoiceType) {
//         if (invoiceType == null) return false;
//         String type = invoiceType.trim();
//         return "Sizing sales".equalsIgnoreCase(type) || "204".equals(type);
//     }

//     private String appendReturnSuffix(String sizedBeamNo) {
//         if (sizedBeamNo == null || sizedBeamNo.trim().isEmpty()) {
//             return sizedBeamNo;
//         }
//         String trimmedBeamNo = sizedBeamNo.trim();
//         if (trimmedBeamNo.endsWith("-R")) {
//             return trimmedBeamNo;
//         }
//         return trimmedBeamNo + "-R";
//     }

//     private boolean isYarnSales(String invoiceType) {
//         if (invoiceType == null) return false;
//         String type = invoiceType.trim();
//         return "Yarn Sales".equalsIgnoreCase(type) || "205".equals(type);
//     }

//     private Long normalizeId(Long id) {
//         return (id == null || id <= 0) ? null : id;
//     }

//     private String normalizeString(String value) {
//         return (value == null || value.trim().isEmpty()) ? null : value.trim();
//     }

//     private void handleYarnSalesReturn(SalesReturn salesReturn) {
//         Long outwardTypeId = getYarnSalesReturnInwardTypeId();

//         Map<Long, Long> preservedMillIds = new HashMap<>();
//         List<YarnInventory> existingInventories = yarnInventoryRepository.findByInwardIdAndInwardTypeId(salesReturn.getId(), outwardTypeId);
//         if (!existingInventories.isEmpty()) {
//             YarnInventory inv = existingInventories.get(0);
//             if (inv.getDetails() != null) {
//                 for (YarnInwardStockDetails detail : inv.getDetails()) {
//                     if (detail.getOrderItemId() != null && detail.getMillId() != null && detail.getMillId() > 0) {
//                         preservedMillIds.put(detail.getOrderItemId(), detail.getMillId());
//                     }
//                 }
//             }
//         }

//         // Find and delete existing YarnInventory and YarnInwardStockDetails if they exist
//         cleanupYarnInventory(salesReturn.getId(), outwardTypeId);

//         YarnInventory yarnInventory = new YarnInventory();
        
//         yarnInventory.setInwardId(salesReturn.getId());
//         yarnInventory.setInwardTypeId(outwardTypeId);

//         yarnInventory.setInwardNo(salesReturn.getSalesReturnNo());
//         yarnInventory.setCompanyId(salesReturn.getCompanyId());
//         yarnInventory.setInwardDate(LocalDateTime.now());
//         yarnInventory.setFinancialYear(getFinancialYearId(salesReturn.getDate()));
//         yarnInventory.setDcNo(salesReturn.getInvoiceNo());
//         yarnInventory.setDcDate(salesReturn.getDate());
//         yarnInventory.setOrderId(salesReturn.getOrderId());
//         yarnInventory.setOrderNo(salesReturn.getOrderNo());
//         yarnInventory.setVendorId(salesReturn.getCustomerId());
//         yarnInventory.setWarehouseId(salesReturn.getWarehouseId());

//         YarnInventory savedYarnInventory = yarnInventoryRepository.save(yarnInventory);

//         List<YarnInwardStockDetails> stockDetailsList = new ArrayList<>();
        
//         for (SalesYarnReturn yarnReturnItem : salesReturn.getYarnItems()) {
//             YarnInwardStockDetails stockDetails = new YarnInwardStockDetails();
//             stockDetails.setYarnInventory(savedYarnInventory);
//             stockDetails.setInwardTypeId(outwardTypeId);
            
//             // Find Yarn ID from Yarn Name
//             Long yarnId = null;
//             if (yarnReturnItem.getYarnName() != null) {
//                 Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(yarnReturnItem.getYarnName());
//                 if (yarnMaster.isPresent()) {
//                     yarnId = yarnMaster.get().getId();
//                 }
//             }
//             stockDetails.setItemId(yarnId);
//             stockDetails.setOrderItemId(yarnReturnItem.getId());
//             stockDetails.setUomId(1L); // Default UOM
//             stockDetails.setLotNo(yarnReturnItem.getLotNo());
//             stockDetails.setInwardQty(yarnReturnItem.getIssuedQty());
//             stockDetails.setInwardBags(yarnReturnItem.getIssuedBags());
            
//             if (yarnReturnItem.getIssuedBags() != null && yarnReturnItem.getIssuedBags().compareTo(BigDecimal.ZERO) > 0 && 
//                 yarnReturnItem.getIssuedQty() != null && yarnReturnItem.getIssuedQty().compareTo(BigDecimal.ZERO) > 0) {
//                 BigDecimal kgsPerBag = yarnReturnItem.getIssuedQty().divide(yarnReturnItem.getIssuedBags(), 3, java.math.RoundingMode.HALF_UP);
//                 stockDetails.setKgsPerBag(kgsPerBag);
//             } else {
//                 stockDetails.setKgsPerBag(BigDecimal.ZERO);
//             }
            
//             stockDetails.setAcceptedQty(yarnReturnItem.getIssuedQty());
//             stockDetails.setRejectedQty(BigDecimal.ZERO);
//             stockDetails.setAvailableQty(yarnReturnItem.getIssuedQty());
//             stockDetails.setAvailableBags(yarnReturnItem.getIssuedBags());
//             stockDetails.setWarehouseId(yarnReturnItem.getWarehouseId() != null ? yarnReturnItem.getWarehouseId() : salesReturn.getWarehouseId());
//             Long millId = (yarnReturnItem.getMillId() != null && yarnReturnItem.getMillId() > 0)
//                     ? yarnReturnItem.getMillId()
//                     : preservedMillIds.get(yarnReturnItem.getId());
//             stockDetails.setMillId(millId);
//             stockDetails.setConesPerBag(yarnReturnItem.getConesPerBag());
//             stockDetails.setConesWeight(yarnReturnItem.getConesWeight());

//             stockDetailsList.add(stockDetails);
//         }

//         yarnInwardStockDetailsRepository.saveAll(stockDetailsList);
//         savedYarnInventory.setDetails(stockDetailsList);
//     }

//     private void cleanupYarnInventory(Long inwardId, Long inwardTypeId) {
//         try {
//             List<YarnInventory> existingInventories = yarnInventoryRepository.findByInwardIdAndInwardTypeId(inwardId, inwardTypeId);
//             for (YarnInventory inv : existingInventories) {
//                 // First delete all associated stock details
//                 if (inv.getDetails() != null && !inv.getDetails().isEmpty()) {
//                     yarnInwardStockDetailsRepository.deleteAll(inv.getDetails());
//                 }
//                 // Then delete the inventory record
//                 yarnInventoryRepository.delete(inv);
//             }
            
//             // Additional cleanup: Find any orphaned YarnInwardStockDetails that might reference this sales return
//             List<YarnInwardStockDetails> orphanedDetails = yarnInwardStockDetailsRepository.findAll().stream()
//                 .filter(detail -> detail.getYarnInventory() != null 
//                     && inwardId.equals(detail.getYarnInventory().getInwardId())
//                     && inwardTypeId.equals(detail.getYarnInventory().getInwardTypeId()))
//                 .collect(Collectors.toList());
            
//             for (YarnInwardStockDetails detail : orphanedDetails) {
//                 // Check if the parent inventory still exists
//                 Optional<YarnInventory> parentInv = yarnInventoryRepository.findById(detail.getYarnInventory().getId());
//                 if (!parentInv.isPresent()) {
//                     // Parent inventory was deleted, so delete this orphaned detail
//                     yarnInwardStockDetailsRepository.delete(detail);
//                 }
//             }
//         } catch (Exception e) {
//             logger.error("Error cleaning up yarn inventory for inwardId: {}, inwardTypeId: {}", inwardId, inwardTypeId, e);
//             throw new RuntimeException("Failed to cleanup yarn inventory records", e);
//         }
//     }

//     private Long getYarnSalesReturnInwardTypeId() {
//         return codesDetailRepository.findByValue("YARN_SALES_RETURN")
//                 .map(CodesDetail::getId)
//                 .orElseGet(() -> codesDetailRepository.findByValue("yarn-sales-return")
//                         .map(CodesDetail::getId)
//                         .orElse(8L));
//     }

//     private String getFinancialYearId(LocalDate date) {
//         if (date == null) return null;
//         int year = date.getYear();
//         int month = date.getMonthValue();
//         int startYear;
//         int endYear;
//         if (month >= 4) {
//             startYear = year;
//             endYear = year + 1;
//         } else {
//             startYear = year - 1;
//             endYear = year;
//         }
//         String endYearLastTwo = String.format("%02d", endYear % 100);
//         return startYear + "-" + endYearLastTwo;
//     }

//     private void validateDuplicates(SalesReturnDTO dto, Long currentId) {
//         // 1. Validate Fabric Piece Numbers
//         if (dto.getItems() != null && !dto.getItems().isEmpty()) {
//             java.util.Set<String> requestPieceNumbers = new java.util.HashSet<>();
//             for (SalesReturnItemDTO itemDto : dto.getItems()) {
//                 String pieceNo = itemDto.getPieceNo();
//                 if (pieceNo == null || pieceNo.trim().isEmpty()) {
//                     continue;
//                 }
                
//                 String pieceNumberWithR = pieceNo + "-R";

//                 // Check for duplicates within the request
//                 if (!requestPieceNumbers.add(pieceNumberWithR)) {
//                     throw new IllegalArgumentException("Duplicate piece number found in request: " + pieceNumberWithR);
//                 }

//                 // Check for duplicates in database for the same company
//                 List<PieceEntry> existingPieces = pieceEntryRepository.findByPieceNumberAndCompanyId(pieceNumberWithR, dto.getCompanyId());
//                 for (PieceEntry existingPiece : existingPieces) {
//                     // During update, exclude pieces that belong to the current SalesReturn record
//                     // We assume pieces created by SalesReturn have sourceType "Sales Return" or "Fabric Return"
//                     // but more importantly we check if they are linked back to this sales return.
//                     // PieceEntry doesn't have a direct link to SalesReturn, but it might have a pieceNumber pattern.
//                     // For now, let's just check if it exists at all for this company.
//                     // If it belongs to CURRENT record, we should find a way to identify it.
//                     // SalesReturn piece entries are recreated on update, so we need to be careful.
                    
//                     if (currentId != null) {
//                         // SalesReturn doesn't store PieceEntry IDs directly in its items.
//                         // But createPieceEntries is called which saves them.
//                         // On update, we might be checking against pieces we already created.
//                         // This is tricky because PieceEntry doesn't have a sales_return_id.
//                         // However, we can check if it was created by THIS return by searching for its pieceNumber.
//                         // If we are updating, we should probably allow it if it's already there.
//                         // Actually, PieceEntryService.java also doesn't link them back.
//                         continue; // Skip for now to avoid false positives on update until better linking is established
//                     }
//                     throw new IllegalArgumentException("Piece number '" + pieceNumberWithR + "' already exists for this company");
//                 }
//             }
//         }

//         // 2. Validate Yarn Lot Numbers
//         if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
//             java.util.Set<String> requestLotNumbers = new java.util.HashSet<>();
//             for (SalesYarnReturnDTO yarnDto : dto.getYarnItems()) {
//                 String lotNo = yarnDto.getLotNo();
//                 if (lotNo == null || lotNo.trim().isEmpty()) {
//                     continue;
//                 }

//                 // Find Yarn ID from Yarn Name
//                 Long yarnId = null;
//                 if (yarnDto.getYarnName() != null) {
//                     Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(yarnDto.getYarnName());
//                     if (yarnMaster.isPresent()) {
//                         yarnId = yarnMaster.get().getId();
//                     }
//                 }

//                 if (yarnId == null) continue;

//                 // Check for duplicates within the request
//                 if (!requestLotNumbers.add(lotNo)) {
//                     throw new IllegalArgumentException("Duplicate lot number found in request: " + lotNo);
//                 }

//                 // Check for duplicates in database for the same company
//                 List<YarnInwardStockDetails> existingLots = yarnInwardStockDetailsRepository.findByItemIdAndLotNo(yarnId, lotNo);
//                 for (YarnInwardStockDetails existingLot : existingLots) {
//                     if (existingLot.getYarnInventory() != null) {
//                         Long companyId = existingLot.getYarnInventory().getCompanyId();
//                         if (companyId != null && companyId.equals(dto.getCompanyId())) {
//                             if (currentId != null && existingLot.getYarnInventory().getInwardId() != null &&
//                                 existingLot.getYarnInventory().getInwardId().equals(currentId) &&
//                                 Objects.equals(existingLot.getYarnInventory().getInwardTypeId(), getYarnSalesReturnInwardTypeId())) {
//                                 continue;
//                             }
//                             throw new IllegalArgumentException("Lot number '" + lotNo + "' already exists for this company");
//                         }
//                     }
//                 }
//             }
//         }
//     }
// }


package com.tech3.erp.service;

import com.tech3.erp.dto.SalesReturnDTO;
import com.tech3.erp.dto.SalesReturnItemDTO;
import com.tech3.erp.dto.SalesYarnReturnDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SalesReturnBeamDTO;
import com.tech3.erp.dto.SalesReturnSizingDTO;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.SalesReturn;
import com.tech3.erp.entity.SalesReturnSizing;
import com.tech3.erp.entity.SizingBeamOutward;
import com.tech3.erp.entity.SizingBeamOutwardDetail;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.SalesReturnSizingRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnMaster;
import com.tech3.erp.entity.SizingProductionDetail;
import com.tech3.erp.repository.SizingProductionDetailRepository;
import com.tech3.erp.repository.SizingBeamOutwardRepository;
import com.tech3.erp.repository.SizingBeamOutwardDetailRepository;
import com.tech3.erp.entity.SalesReturnItem;
import com.tech3.erp.entity.SalesYarnReturn;
import com.tech3.erp.entity.SalesReturnBeamEntity;
import com.tech3.erp.entity.BeamOutward;
import com.tech3.erp.entity.BeamInward;
import com.tech3.erp.entity.BeamInwardBeamDetails;
import com.tech3.erp.repository.BeamInwardRepository;
import com.tech3.erp.entity.BeamOutwardDetail;
import com.tech3.erp.entity.BaleRollPacking;
import com.tech3.erp.entity.BaleRollPackingItem;
import com.tech3.erp.entity.GeneratePacking;
import com.tech3.erp.entity.GeneratePackingItem;
import com.tech3.erp.entity.InspectionDetail;
import com.tech3.erp.entity.FabricInspection;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.Warehouse;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.repository.SalesReturnRepository;
import com.tech3.erp.repository.SalesReturnItemRepository;
import com.tech3.erp.repository.SalesYarnReturnRepository;
import com.tech3.erp.repository.SalesReturnBeamRepository;
import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.BeamOutwardDetailRepository;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.WarehouseRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.YarnChallanRepository;
import com.tech3.erp.repository.InvoiceItemRepository;
import com.tech3.erp.repository.GenerateInvoiceRepository;
import com.tech3.erp.repository.WovenFabricMasterRepository;
import com.tech3.erp.entity.WovenFabricMaster;
import com.tech3.erp.repository.GeneratePackingRepository;
import com.tech3.erp.repository.BaleRollPackingRepository;
import com.tech3.erp.repository.BaleRollPackingItemRepository;
import com.tech3.erp.repository.InspectionDetailRepository;
import com.tech3.erp.repository.FabricInspectionRepository;
import com.tech3.erp.service.ReferenceValidationService;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.exception.ApiException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SalesReturnService {

    private static final Logger logger = LoggerFactory.getLogger(SalesReturnService.class);

    @Autowired
    private SalesReturnRepository salesReturnRepository;

    @Autowired
    private SalesReturnItemRepository salesReturnItemRepository;

    @Autowired
    private SalesYarnReturnRepository salesYarnReturnRepository;

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private SalesReturnBeamRepository salesReturnBeamRepository;

    @Autowired
    private BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository;

    @Autowired
    private BeamOutwardDetailRepository beamOutwardDetailRepository;

    @Autowired
    private BeamOutwardRepository beamOutwardRepository;

    @Autowired
    private WeavingContractRepository weavingContractRepository;

    @Autowired
    private YarnChallanRepository yarnChallanRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

  
 @Autowired
    private GenerateInvoiceRepository generateInvoiceRepository;

    @Autowired
    private GeneratePackingRepository generatePackingRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Autowired
    private BaleRollPackingRepository baleRollPackingRepository;

    @Autowired
    private BaleRollPackingItemRepository baleRollPackingItemRepository;

    @Autowired
    private InspectionDetailRepository inspectionDetailRepository;

    @Autowired
    private FabricInspectionRepository fabricInspectionRepository;

    @Autowired
    private SalesReturnSizingRepository salesReturnSizingRepository;

    @Autowired
    private SizingProductionDetailRepository sizingProductionDetailRepository;

    @Autowired
    private SizingBeamOutwardRepository sizingBeamOutwardRepository;

    @Autowired
    private SizingBeamOutwardDetailRepository sizingBeamOutwardDetailRepository;

    @Autowired
    private YarnInventoryRepository yarnInventoryRepository;

    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;

    @Autowired
    private YarnMasterRepository yarnMasterRepository;

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private BeamInwardRepository beamInwardRepository;

    @Autowired
    private WovenFabricMasterRepository wovenFabricMasterRepository;

   

    @Transactional
    public SalesReturn create(SalesReturnDTO dto) {
        validateDuplicates(dto, null);
        SalesReturn entity = new SalesReturn();
        mapDtoToEntity(dto, entity);
        
        SalesReturn savedReturn = salesReturnRepository.save(entity);
        
        if (!"Yarn Sales".equalsIgnoreCase(dto.getInvoiceType()) && dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<SalesReturnItem> items = dto.getItems().stream()
                    .map(itemDto -> toItemEntity(itemDto, savedReturn))
                    .collect(Collectors.toList());
            salesReturnItemRepository.saveAll(items);
            savedReturn.setItems(items);
            
            createPieceEntries(items, savedReturn);
        }

        if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
            List<SalesYarnReturn> yarnItems = dto.getYarnItems().stream()
                    .map(yarnDto -> toYarnEntity(yarnDto, savedReturn))
                    .collect(Collectors.toList());
            salesYarnReturnRepository.saveAll(yarnItems);
            savedReturn.setYarnItems(yarnItems);

            if (isYarnSales(dto.getInvoiceType())) {
                handleYarnSalesReturn(savedReturn);
            }
        }

        if (!"Yarn Sales".equalsIgnoreCase(dto.getInvoiceType())) {
            if (!isSizingSales(dto.getInvoiceType())) {
                if (dto.getBeamItems() != null && !dto.getBeamItems().isEmpty()) {
                    handleBeamReturn(dto.getBeamItems(), savedReturn);
                }
            } else {
                if (dto.getSizingItems() != null && !dto.getSizingItems().isEmpty()) {
                    handleSizingReturn(dto.getSizingItems(), savedReturn);
                }
            }
        }
        
        return savedReturn;
    }

    @Transactional
    public SalesReturnDTO update(Long id, SalesReturnDTO dto, Long userId) {
        validateDuplicates(dto, id);
        SalesReturn entity = salesReturnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sales Return not found with id: " + id));
        
        // Clean up existing Beam flow movements if any
        if (!isYarnSales(entity.getInvoiceType())) {
            if (isSizingSales(entity.getInvoiceType())) {
                cleanupSizingMovement(entity);
            } else {
                cleanupBeamMovement(entity);
            }
        }

        mapDtoToEntity(dto, entity);
    
    // Instead of clearing and replacing the collection, modify the existing one
    updateItemsCollection(entity, dto.getItems());
    updateYarnItemsCollection(entity, dto.getYarnItems());
    
    SalesReturn updatedEntity = salesReturnRepository.save(entity);
    
    // Handle Yarn Sales Return for inventory
    if (isYarnSales(dto.getInvoiceType())) {
        if (updatedEntity.getYarnItems() != null && !updatedEntity.getYarnItems().isEmpty()) {
            handleYarnSalesReturn(updatedEntity);
        } else {
            // Clean up if no yarn items but it is Yarn Sales type (or items were removed)
            cleanupYarnInventory(id, getYarnSalesReturnInwardTypeId());
        }
    } else {
        // Clean up if it's no longer Yarn Sales type
        cleanupYarnInventory(id, getYarnSalesReturnInwardTypeId());
    }
    
    // Create piece entries and handle beam returns (only if not Yarn Sales)
    if (!isYarnSales(dto.getInvoiceType())) {
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            createPieceEntries(updatedEntity.getItems(), updatedEntity);
        }
        if (!isSizingSales(dto.getInvoiceType())) {
            if (dto.getBeamItems() != null && !dto.getBeamItems().isEmpty()) {
                salesReturnBeamRepository.deleteBySalesReturnId(id);
                handleBeamReturn(dto.getBeamItems(), updatedEntity);
            }
            // Clear sizing items if it's not Sizing Sales
            updatedEntity.getSizingItems().clear();
        } else {
            if (dto.getSizingItems() != null && !dto.getSizingItems().isEmpty()) {
                salesReturnSizingRepository.deleteBySalesReturnId(id);
                handleSizingReturn(dto.getSizingItems(), updatedEntity);
            }
            // Clear beam items if it IS Sizing Sales
            updatedEntity.getBeams().clear();
        }
    }
    
    return get(id);
}

    @Transactional
    public SalesReturnDTO update(Long id, SalesReturnDTO dto) {
        return update(id, dto, null);
    }
    
    private void updateItemsCollection(SalesReturn salesReturn, List<SalesReturnItemDTO> itemDTOs) {
        // Get the current items
        List<SalesReturnItem> currentItems = salesReturn.getItems();
        
        if (currentItems == null) {
            currentItems = new ArrayList<>();
            salesReturn.setItems(currentItems);
        }
        
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            // Clear all items if no items in DTO
            currentItems.clear();
            return;
        }
        
        // Create maps for efficient lookup
        Map<Long, SalesReturnItem> existingItemsMap = currentItems.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(SalesReturnItem::getId, Function.identity()));
        
        Map<Long, SalesReturnItemDTO> dtoItemsMap = itemDTOs.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(SalesReturnItemDTO::getId, Function.identity()));
        
        // Remove items that are not in the DTO
        List<SalesReturnItem> itemsToRemove = new ArrayList<>();
        for (SalesReturnItem existingItem : currentItems) {
            if (existingItem.getId() != null && !dtoItemsMap.containsKey(existingItem.getId())) {
                itemsToRemove.add(existingItem);
            }
        }
        currentItems.removeAll(itemsToRemove);
        
        // Update existing items and add new ones
        for (SalesReturnItemDTO itemDto : itemDTOs) {
            if (itemDto.getId() != null && existingItemsMap.containsKey(itemDto.getId())) {
                // Update existing item
                SalesReturnItem existingItem = existingItemsMap.get(itemDto.getId());
                updateItemFromDto(existingItem, itemDto);
            } else {
                // Add new item
                SalesReturnItem newItem = toItemEntity(itemDto, salesReturn);
                currentItems.add(newItem);
            }
        }
    }

    private void updateItemFromDto(SalesReturnItem item, SalesReturnItemDTO dto) {
        ensureItemFieldLengths(dto);
        item.setRollNo(dto.getRollNo());
        item.setPieceNo(dto.getPieceNo());
        item.setPackingListNo(dto.getPackingListNo());
        item.setMeter(dto.getMeter());
        item.setNetWeight(dto.getNetWeight());
    }

    private void updateYarnItemsCollection(SalesReturn salesReturn, List<SalesYarnReturnDTO> itemDTOs) {
        List<SalesYarnReturn> currentItems = salesReturn.getYarnItems();
        if (currentItems == null) {
            currentItems = new ArrayList<>();
            salesReturn.setYarnItems(currentItems);
        }
        if (itemDTOs == null || itemDTOs.isEmpty()) {
            currentItems.clear();
            return;
        }
        Map<Long, SalesYarnReturn> existingItemsMap = currentItems.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(SalesYarnReturn::getId, Function.identity()));
        Map<Long, SalesYarnReturnDTO> dtoItemsMap = itemDTOs.stream()
                .filter(dto -> dto.getId() != null)
                .collect(Collectors.toMap(SalesYarnReturnDTO::getId, Function.identity()));
        List<SalesYarnReturn> itemsToRemove = new ArrayList<>();
        for (SalesYarnReturn existingItem : currentItems) {
            if (existingItem.getId() != null && !dtoItemsMap.containsKey(existingItem.getId())) {
                itemsToRemove.add(existingItem);
            }
        }
        currentItems.removeAll(itemsToRemove);
        for (SalesYarnReturnDTO itemDto : itemDTOs) {
            if (itemDto.getId() != null && existingItemsMap.containsKey(itemDto.getId())) {
                SalesYarnReturn existingItem = existingItemsMap.get(itemDto.getId());
                updateYarnItemFromDto(existingItem, itemDto);
            } else {
                SalesYarnReturn newItem = toYarnEntity(itemDto, salesReturn);
                currentItems.add(newItem);
            }
        }
    }

    private void updateYarnItemFromDto(SalesYarnReturn item, SalesYarnReturnDTO dto) {
        item.setYarnName(dto.getYarnName());
        // item.setAvailableQty(dto.getAvailableQty());
        item.setIssuedQty(dto.getIssuedQty());
        item.setLotNo(dto.getLotNo());
        item.setWarehouseId(dto.getWarehouseId());
        item.setNoOfBags(dto.getNoOfBags());
        item.setIssuedBags(dto.getIssuedBags());
        item.setConesPerBag(dto.getConesPerBag());
        item.setConesWeight(dto.getConesWeight());
        item.setMillId(dto.getMillId());
    }

    public List<SalesReturnDTO> listAll() {
        return salesReturnRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<SalesReturnDTO> listAllWithPagination(Long offset, Long limit) {
        List<SalesReturnDTO> allReturns = listAll();
        return PaginationHelper.paginate(allReturns, offset, limit);
    }

    public SalesReturnDTO get(Long id) {
        SalesReturn entity = salesReturnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sales Return not found with id: " + id));
        return toDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        SalesReturn entity = salesReturnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sales Return not found with id: " + id));
        
        validateDeletionNotBlocked(entity);
        
        // Delete related YarnInventory and YarnInwardStockDetails if it's Yarn Sales
        if (isYarnSales(entity.getInvoiceType())) {
            cleanupYarnInventory(id, getYarnSalesReturnInwardTypeId());
        }

        // Cleanup PieceEntry and BaleRollPacking records created during save
        cleanupPieceEntriesAndPacking(entity);

        // Cleanup Sizing flow related records
        if (isSizingSales(entity.getInvoiceType())) {
            cleanupSizingMovement(entity);
        }

        // Cleanup Beam flow related records
        if (!isYarnSales(entity.getInvoiceType()) && !isSizingSales(entity.getInvoiceType())) {
            cleanupBeamMovement(entity);
        }
        
        salesReturnItemRepository.findBySalesReturnId(id).forEach(item -> 
            salesReturnItemRepository.delete(item)
        );

        salesYarnReturnRepository.findBySalesReturnId(id).forEach(item ->
            salesYarnReturnRepository.delete(item)
        );

        salesReturnSizingRepository.deleteBySalesReturnId(id);
        salesReturnBeamRepository.deleteBySalesReturnId(id);
        
        salesReturnRepository.delete(entity);
    }

    private void validateDeletionNotBlocked(SalesReturn salesReturn) {
        String invoiceType = salesReturn.getInvoiceType();
        boolean isSpecialInvoice = "Weaving jobwork".equalsIgnoreCase(invoiceType) || 
                                  "Domestic".equalsIgnoreCase(invoiceType) || 
                                  "Export".equalsIgnoreCase(invoiceType);
                                  
        if (!isSpecialInvoice) {
            return;
        }

        List<SalesReturnItem> items = salesReturnItemRepository.findBySalesReturnId(salesReturn.getId());
        for (SalesReturnItem item : items) {
            if (item.getPieceNo() != null) {
                List<String> individualPieceNos = new ArrayList<>();
                for (String part : item.getPieceNo().split(",")) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        individualPieceNos.add(trimmed);
                    }
                }
                if (individualPieceNos.isEmpty()) {
                    individualPieceNos.add(item.getPieceNo().trim());
                }
                for (String singlePieceNo : individualPieceNos) {
                    String pieceNumberForInspection = singlePieceNo + "-R";
                    List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberAndCompanyId(pieceNumberForInspection, salesReturn.getCompanyId());
                    for (PieceEntry piece : pieces) {
                        // Check if the piece is dispatched or invoiced
                        if (piece.getIsDispatched() != null && piece.getIsDispatched() == 1) {
                            throw new IllegalArgumentException("Cannot delete Sales Return because returned pieces are already used in Packing/Invoice flow.");
                        }
                        if (piece.getDispatchId() != null) {
                            throw new IllegalArgumentException("Cannot delete Sales Return because returned pieces are already used in Packing/Invoice flow.");
                        }
                        if (piece.getIsInvoice() != null && piece.getIsInvoice() == 1) {
                            throw new IllegalArgumentException("Cannot delete Sales Return because returned pieces are already used in Packing/Invoice flow.");
                        }
                        if (piece.getInvoiceId() != null && piece.getInvoiceId() != 0) {
                            throw new IllegalArgumentException("Cannot delete Sales Return because returned pieces are already used in Packing/Invoice flow.");
                        }
                        
                        // Check if it has been repacked in a different flow
                        List<BaleRollPackingItem> packingItems = baleRollPackingItemRepository.findByPieceId(piece.getId());
                        if (packingItems.size() > 1) {
                            throw new IllegalArgumentException("Cannot delete Sales Return because returned pieces are already used in Packing/Invoice flow.");
                        }
                        for (BaleRollPackingItem pi : packingItems) {
                            BaleRollPacking brp = pi.getBaleRollPacking();
                            if (brp != null) {
                                if (brp.getIsDispatched() != null && brp.getIsDispatched() == 1) {
                                    throw new IllegalArgumentException("Cannot delete Sales Return because returned pieces are already used in Packing/Invoice flow.");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void cleanupPieceEntriesAndPacking(SalesReturn salesReturn) {
        List<PieceEntry> pieces = pieceEntryRepository.findByInwardId(salesReturn.getId());
        for (PieceEntry piece : pieces) {
            // Only delete pieces that were created by Sales Return
            if ("Sales Return".equals(piece.getSourceType()) || "Fabric Return".equals(piece.getSourceType())) {
                
                // 1. Delete any associated BaleRollPackingItem records first
                List<BaleRollPackingItem> packingItems = baleRollPackingItemRepository.findByPieceId(piece.getId());
                BaleRollPacking baleRollPackingToCleanup = null;
                if (!packingItems.isEmpty()) {
                    baleRollPackingToCleanup = packingItems.get(0).getBaleRollPacking();
                    baleRollPackingItemRepository.deleteAll(packingItems);
                }
                
                // 2. Delete corresponding InspectionDetail record
                try {
                    InspectionDetail inspectionDetail = inspectionDetailRepository.findTopByPieceNoOrderByIdDesc(piece.getPieceNumber());
                    if (inspectionDetail != null) {
                        inspectionDetailRepository.delete(inspectionDetail);
                    }
                } catch (Exception e) {
                    logger.error("Error deleting inspection detail for piece {}: {}", piece.getPieceNumber(), e.getMessage());
                }
                
                // 3. Delete the PieceEntry
                pieceEntryRepository.delete(piece);
                
                // 4. Delete the parent BaleRollPacking if empty and has no dispatch/invoice linkage
                if (baleRollPackingToCleanup != null) {
                    List<BaleRollPackingItem> remainingItems = baleRollPackingItemRepository.findByBaleRollPackingId(baleRollPackingToCleanup.getId());
                    if (remainingItems.isEmpty()) {
                        boolean hasLinkage = (baleRollPackingToCleanup.getIsDispatched() != null && baleRollPackingToCleanup.getIsDispatched() == 1);
                        if (!hasLinkage) {
                            baleRollPackingRepository.delete(baleRollPackingToCleanup);
                        }
                    }
                }
            }
        }
    }

    private void cleanupSizingMovement(SalesReturn salesReturn) {
        try {
            String returnNo = salesReturn.getSalesReturnNo();
            if (returnNo == null || returnNo.isEmpty()) return;

            // First, collect all records to be deleted to avoid cascade issues
            List<SizingProductionDetail> toDeleteProduction = new ArrayList<>();
            List<SizingBeamOutward> toDeleteOutward = new ArrayList<>();
            List<SizingBeamOutwardDetail> toDeleteDetails = new ArrayList<>();

            // Cleanup SizingProductionDetail
            List<SizingProductionDetail> productionDetails = sizingProductionDetailRepository.findByReturnNo(returnNo);
            for (SizingProductionDetail detail : productionDetails) {
                if (detail.getIsReturn() != null && detail.getIsReturn() == 1) {
                    // This is an original record that was marked as returned - revert it
                    detail.setIsReturn(0);
                    detail.setReturnNo(null);
                    detail.setReturnDate(null);
                    sizingProductionDetailRepository.save(detail);
                } else {
                    // This is a new record created for the return - mark for deletion
                    toDeleteProduction.add(detail);
                }
            }

            // Cleanup SizingBeamOutward and SizingBeamOutwardDetail
            List<SizingBeamOutward> outwardRecords = sizingBeamOutwardRepository.findByReturnNo(returnNo);
            for (SizingBeamOutward outward : outwardRecords) {
                if (outward.getIsReturn() != null && outward.getIsReturn() == 1) {
                    // This is an original record that was marked as returned - revert it
                    outward.setIsReturn(0);
                    outward.setReturnNo(null);
                    outward.setReturnDate(null);
                    sizingBeamOutwardRepository.save(outward);
                } else {
                    // This is a new record created for the return - collect details for deletion
                    List<SizingBeamOutwardDetail> details = sizingBeamOutwardDetailRepository.findBySizingBeamOutwardId(outward.getId());
                    toDeleteDetails.addAll(details);
                    toDeleteOutward.add(outward);
                }
            }
            
            // Delete in proper order to avoid foreign key constraints
            // 1. Delete SizingBeamOutwardDetail records first
            if (!toDeleteDetails.isEmpty()) {
                sizingBeamOutwardDetailRepository.deleteAll(toDeleteDetails);
            }
            
            // 2. Delete SizingBeamOutward records
            if (!toDeleteOutward.isEmpty()) {
                sizingBeamOutwardRepository.deleteAll(toDeleteOutward);
            }
            
            // 3. Delete SizingProductionDetail records
            if (!toDeleteProduction.isEmpty()) {
                sizingProductionDetailRepository.deleteAll(toDeleteProduction);
            }
            
            // Additional cleanup: Find any orphaned SizingBeamOutwardDetail records
            List<SizingBeamOutwardDetail> remainingDetails = sizingBeamOutwardDetailRepository.findAll();
            List<SizingBeamOutwardDetail> orphanedDetails = new ArrayList<>();
            for (SizingBeamOutwardDetail detail : remainingDetails) {
                if (detail.getSizingBeamOutward() != null) {
                    boolean parentExists = sizingBeamOutwardRepository.existsById(detail.getSizingBeamOutward().getId());
                    if (!parentExists) {
                        // Parent outward record was deleted, so delete this orphaned detail
                        orphanedDetails.add(detail);
                    }
                }
            }
            
            if (!orphanedDetails.isEmpty()) {
                sizingBeamOutwardDetailRepository.deleteAll(orphanedDetails);
            }
        } catch (Exception e) {
            logger.error("Error cleaning up sizing movement for sales return: {}", salesReturn.getId(), e);
            throw new RuntimeException("Failed to cleanup sizing movement records", e);
        }
    }

    private void cleanupBeamMovement(SalesReturn salesReturn) {
        try {
            String returnNo = salesReturn.getSalesReturnNo();
            if (returnNo == null || returnNo.isEmpty()) return;

            // Cleanup BeamInwardBeamDetails
            List<BeamInwardBeamDetails> inwardDetails = beamInwardBeamDetailsRepository.findByReturnNo(returnNo);
            for (BeamInwardBeamDetails detail : inwardDetails) {
                if (detail.getIsReturn() != null && detail.getIsReturn() == 1) {
                    // Revert original record that was marked as returned
                    detail.setIsReturn(0);
                    detail.setReturnNo(null);
                    detail.setReturnDate(null);
                    detail.setReturnWeaverId(null);
                    beamInwardBeamDetailsRepository.save(detail);
                } else {
                    // Delete new record created for the return (isReturn was 0)
                    beamInwardBeamDetailsRepository.delete(detail);
                }
            }

            // Cleanup BeamOutwardDetail
            List<BeamOutwardDetail> beamOutwardDetailRecords = beamOutwardDetailRepository.findByReturnNo(returnNo);
            for (BeamOutwardDetail detail : beamOutwardDetailRecords) {
                if (detail.getIsReturn() != null && detail.getIsReturn() == 1) {
                    // Revert original record that was marked as returned
                    detail.setIsReturn(0);
                    detail.setReturnNo(null);
                    detail.setReturnDate(null);
                    detail.setReturnWeaverId(null);
                    beamOutwardDetailRepository.save(detail);
                } else {
                    // Delete new record created for the return (isReturn was 0)
                    beamOutwardDetailRepository.delete(detail);
                }
            }
        } catch (Exception e) {
            logger.error("Error cleaning up beam movement for sales return: {}", salesReturn.getId(), e);
            throw new RuntimeException("Failed to cleanup beam movement records", e);
        }
    }

    private void mapDtoToEntity(SalesReturnDTO dto, SalesReturn entity) {
        if (dto.getDate() != null && !dto.getDate().isEmpty()) {
            entity.setDate(LocalDate.parse(dto.getDate()));
        } else {
            entity.setDate(LocalDate.now());
        }
        entity.setSalesReturnNo(limit(dto.getSalesReturnNo(), 100, "salesReturnNo"));
        entity.setInvoiceNo(limit(dto.getInvoiceNo(), 100, "invoiceNo"));

         Long invoiceId = normalizeId(dto.getInvoiceId());
        // if (invoiceId != null) {
        //     referenceValidationService.requireReference(
        //             invoiceId,
        //             () -> generateInvoiceRepository.findById(invoiceId),
        //             "Invoice",
        //             dto.getInvoiceNo());
        // }
        entity.setInvoiceId(invoiceId);
        entity.setOrderNo(limit(dto.getOrderNo(), 100, "orderNo"));
        entity.setOrderId(dto.getOrderId());
        entity.setBuyerName(limit(dto.getBuyerName(), 255, "buyerName"));
        entity.setCustomerId(dto.getCustomerId());
        
        String fCode = dto.getFabricCode();
        String fQuality = dto.getFabricQuality();
        
        // Clean up repeated data in quality
        if (fCode != null && fQuality != null) {
            String trimmedCode = fCode.trim();
            String trimmedQuality = fQuality.trim();
            if (trimmedQuality.equalsIgnoreCase(trimmedCode)) {
                fQuality = "";
            } else if (trimmedQuality.toUpperCase().startsWith(trimmedCode.toUpperCase() + " - ")) {
                String subQuality = trimmedQuality.substring(trimmedCode.length() + 3).trim();
                if (subQuality.equalsIgnoreCase(trimmedCode)) {
                    fQuality = "";
                } else {
                    fQuality = subQuality;
                }
            }
        }
        
        entity.setFabricCode(limit(fCode, 100, "fabricCode"));
        entity.setFabricQuality(limit(fQuality, 100, "fabricQuality"));
        entity.setInvoiceType(limit(dto.getInvoiceType(), 50, "invoiceType"));
        entity.setRemarks(dto.getRemarks());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        entity.setCompanyId(dto.getCompanyId());
        entity.setWarehouseId(dto.getWarehouseId());
        entity.setEditNote(dto.getEditNote());
        
        entity.setDcNo(limit(dto.getDcNo(), 100, "dcNo"));
        if (dto.getDcDate() != null && !dto.getDcDate().isEmpty()) {
            entity.setDcDate(LocalDate.parse(dto.getDcDate()));
        } else {
            entity.setDcDate(null);
        }

        entity.setSizingVendorId(dto.getSizingVendorId());
        entity.setSizingPlanId(dto.getSizingPlanId());
        entity.setWeaverId(dto.getWeaverId());
        entity.setBeamInwardNo(limit(dto.getBeamInwardNo(), 100, "beamInwardNo"));
        entity.setSizingBeamOutwardNo(limit(dto.getSizingBeamOutwardNo(), 100, "sizingBeamOutwardNo"));
        entity.setTotalRolls(dto.getTotalRolls());

        String packingListNo = normalizeString(dto.getPackingListNo());
        if (packingListNo != null && !"-".equals(packingListNo)) {
            boolean foundInPacking = generatePackingRepository.findByPackingSlipNo(packingListNo).isPresent();
            if (!foundInPacking) {
                boolean foundInChallan = yarnChallanRepository.findByChallanNo(packingListNo) != null;
                if (!foundInChallan) {
                    boolean foundInInvoice = invoiceItemRepository.existsByPackingListNoAndActiveFlagTrue(packingListNo);
                    if (!foundInInvoice) {
                        foundInInvoice = generateInvoiceRepository.existsByInvoiceNoAndActiveFlagTrue(packingListNo);
                    }
                    if (!foundInInvoice) {
                        boolean foundInBeamOutward = !isYarnSales(dto.getInvoiceType()) && !isSizingSales(dto.getInvoiceType())
                                && beamOutwardRepository.existsByOutwardNo(packingListNo);
                        boolean foundInSizingBeamOutward = !foundInBeamOutward && isSizingSales(dto.getInvoiceType())
                                && sizingBeamOutwardRepository.existsBySizingBeamOutwardNo(packingListNo);
                        if (!foundInBeamOutward && !foundInSizingBeamOutward) {
                            referenceValidationService.requireReference(
                                    packingListNo,
                                    Optional::empty,
                                    "Packing List",
                                    packingListNo);
                        }
                    }
                }
            }
        }
        entity.setPackingListNo(limit(packingListNo, 100, "packingListNo"));
    }

    private SalesReturnItem toItemEntity(SalesReturnItemDTO dto, SalesReturn salesReturn) {
        ensureItemFieldLengths(dto);
        SalesReturnItem item = new SalesReturnItem();
        item.setSalesReturn(salesReturn);
        item.setRollNo(dto.getRollNo());
        item.setPieceNo(dto.getPieceNo());
        item.setPackingListNo(dto.getPackingListNo());
        item.setMeter(dto.getMeter());
        item.setNetWeight(dto.getNetWeight());
        return item;
    }


    private SalesYarnReturn toYarnEntity(SalesYarnReturnDTO dto, SalesReturn salesReturn) {
        SalesYarnReturn item = new SalesYarnReturn();
        item.setSalesReturn(salesReturn);
        item.setYarnName(dto.getYarnName());
        // item.setAvailableQty(dto.getAvailableQty());
        item.setIssuedQty(dto.getIssuedQty());
        item.setLotNo(dto.getLotNo());
        item.setWarehouseId(dto.getWarehouseId());
        item.setNoOfBags(dto.getNoOfBags());
        item.setIssuedBags(dto.getIssuedBags());
        item.setConesPerBag(dto.getConesPerBag());
        item.setConesWeight(dto.getConesWeight());
        item.setMillId(dto.getMillId());
        return item;
    }

    private SalesReturnSizing toSizingEntity(SalesReturnSizingDTO dto, SalesReturn salesReturn) {
        SalesReturnSizing item = new SalesReturnSizing();
        item.setSalesReturn(salesReturn);
        item.setSizedBeamNo(dto.getSizedBeamNo());
        item.setFlangeNo(dto.getFlangeNo());
        item.setBeamMeter(dto.getBeamMeter());
        item.setNetWeight(dto.getNetWeight());
        item.setWidth(dto.getWidth());
        item.setExpectedFabricMeter(dto.getExpectedFabricMeter());
        item.setTotalEnds(dto.getTotalEnds());
        return item;
    }

    private SalesReturnDTO toDto(SalesReturn entity) {
        SalesReturnDTO dto = new SalesReturnDTO(entity);
        
        // Explicitly set these fields to ensure they are passed to the response
        dto.setSizingVendorId(entity.getSizingVendorId());
        dto.setSizingPlanId(entity.getSizingPlanId());
        dto.setWeaverId(entity.getWeaverId());
        dto.setBeamInwardNo(entity.getBeamInwardNo());
        dto.setSizingBeamOutwardNo(entity.getSizingBeamOutwardNo());
        dto.setTotalRolls(entity.getTotalRolls());

        List<SalesReturnItem> items = salesReturnItemRepository.findBySalesReturnId(entity.getId());
        dto.setItems(items != null ? items.stream()
                .map(SalesReturnItemDTO::new)
                .collect(Collectors.toList()) : new ArrayList<>());

        List<SalesYarnReturn> yarnItems = salesYarnReturnRepository.findBySalesReturnId(entity.getId());
        dto.setYarnItems(yarnItems != null ? yarnItems.stream()
                .map(SalesYarnReturnDTO::new)
                .collect(Collectors.toList()) : new ArrayList<>());

        List<SalesReturnBeamEntity> beamItems = salesReturnBeamRepository.findBySalesReturnId(entity.getId());
        dto.setBeamItems(beamItems != null ? beamItems.stream()
                .map(SalesReturnBeamDTO::new)
                .collect(Collectors.toList()) : new ArrayList<>());

        List<SalesReturnSizing> sizingItems = salesReturnSizingRepository.findBySalesReturnId(entity.getId());
        dto.setSizingItems(sizingItems != null ? sizingItems.stream()
                .map(SalesReturnSizingDTO::new)
                .collect(Collectors.toList()) : new ArrayList<>());
        
//        enrichDtoWithMediaDetails(dto);
        return dto;
    }

//    private void enrichDtoWithMediaDetails(SalesReturnDTO dto) {
//        if (mediaService == null) {
//            return;
//        }
//        
//        if (dto.getDcUpload() != null) {
//            try {
//                dto.setDcUploadDetail(mediaService.getFirstMediaDetailByMediaSno(dto.getDcUpload().longValue()));
//            } catch (Exception e) {
//                logger.debug("Failed to fetch media details for dcUpload mediaSno: {}", dto.getDcUpload(), e);
//            }
//        }
//        
//        if (dto.getInvoiceUpload() != null) {
//            try {
//                dto.setInvoiceUploadDetail(mediaService.getFirstMediaDetailByMediaSno(dto.getInvoiceUpload().longValue()));
//            } catch (Exception e) {
//                logger.debug("Failed to fetch media details for invoiceUpload mediaSno: {}", dto.getInvoiceUpload(), e);
//            }
//        }
//    }

    @Transactional
    private void createPieceEntries(List<SalesReturnItem> items, SalesReturn salesReturn) {
        String invoiceType = salesReturn.getInvoiceType();
        boolean isSpecialInvoice = "Weaving jobwork".equalsIgnoreCase(invoiceType) || 
                                  "Domestic".equalsIgnoreCase(invoiceType) || 
                                  "Export".equalsIgnoreCase(invoiceType);

        Long resolvedFabricMasterId = null;
        if (salesReturn.getFabricCode() != null && !salesReturn.getFabricCode().isEmpty()) {
            resolvedFabricMasterId = wovenFabricMasterRepository
                    .findFirstByFabricCode(salesReturn.getFabricCode())
                    .map(WovenFabricMaster::getId)
                    .orElse(null);
        }

        // Find existing pieces created by this sales return
        List<PieceEntry> existingPieces = pieceEntryRepository.findByInwardId(salesReturn.getId());
        List<PieceEntry> piecesToKeep = new java.util.ArrayList<>();

        // 1. Try to find/re-use the original packing/source entity
        BaleRollPacking originalBaleRollPacking = null;
        for (SalesReturnItem item : items) {
            String rawPieceNo = item.getPieceNo();
            if (rawPieceNo != null && !rawPieceNo.trim().isEmpty()) {
                for (String part : rawPieceNo.split(",")) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        List<PieceEntry> existingEntries = pieceEntryRepository.findByPieceNumberIgnoreCaseAndCompanyId(trimmed, salesReturn.getCompanyId());
                        if (!existingEntries.isEmpty()) {
                            PieceEntry existingPiece = existingEntries.get(0);
                            List<BaleRollPackingItem> originalPackingItems = baleRollPackingItemRepository.findByPieceId(existingPiece.getId());
                            if (!originalPackingItems.isEmpty()) {
                                originalBaleRollPacking = originalPackingItems.get(0).getBaleRollPacking();
                                break;
                            }
                        }
                    }
                }
            }
            if (originalBaleRollPacking != null) {
                break;
            }
        }

        if (originalBaleRollPacking == null && salesReturn.getPackingListNo() != null && !salesReturn.getPackingListNo().isEmpty()) {
            originalBaleRollPacking = baleRollPackingRepository.findByPackingNo(salesReturn.getPackingListNo()).orElse(null);
            if (originalBaleRollPacking == null) {
                originalBaleRollPacking = baleRollPackingRepository.findByRollOrBaleNo(salesReturn.getPackingListNo()).orElse(null);
            }
        }

        GeneratePacking originalPacking = null;
        if (salesReturn.getPackingListNo() != null && !salesReturn.getPackingListNo().isEmpty()) {
            originalPacking = generatePackingRepository.findByPackingSlipNo(salesReturn.getPackingListNo()).orElse(null);
        }

        // Find existing BaleRollPacking for this return if we are in an update flow
        BaleRollPacking baleRollPacking = null;
        if (isSpecialInvoice) {
            for (PieceEntry pe : existingPieces) {
                if (pe.getPackingId() != null) {
                    baleRollPacking = baleRollPackingRepository.findById(pe.getPackingId()).orElse(null);
                    if (baleRollPacking != null) {
                        break;
                    }
                }
            }
            if (baleRollPacking == null) {
                baleRollPacking = new BaleRollPacking();
            }
            baleRollPacking.setPackingDate(Date.from(salesReturn.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            baleRollPacking.setIsDispatched(0);
            baleRollPacking.setTotalPieces((long) items.size());
            baleRollPacking.setPackingType("Roll");

            if (originalBaleRollPacking != null) {
                baleRollPacking.setOrderTypeId(originalBaleRollPacking.getOrderTypeId());
                baleRollPacking.setBuyerId(originalBaleRollPacking.getBuyerId());
                baleRollPacking.setWeavingWorkOrderId(originalBaleRollPacking.getWeavingWorkOrderId());
                baleRollPacking.setIsJobwork(originalBaleRollPacking.getIsJobwork());
                baleRollPacking.setSalesOrderId(originalBaleRollPacking.getSalesOrderId());
                baleRollPacking.setFabricCodeId(originalBaleRollPacking.getFabricCodeId());
                baleRollPacking.setFabricQualityId(originalBaleRollPacking.getFabricQualityId());
                baleRollPacking.setUomId(originalBaleRollPacking.getUomId());
                baleRollPacking.setWarehouseId(originalBaleRollPacking.getWarehouseId());
                baleRollPacking.setGradeId(originalBaleRollPacking.getGradeId());
                baleRollPacking.setCompanyId(originalBaleRollPacking.getCompanyId());
                baleRollPacking.setRemarks(originalBaleRollPacking.getRemarks());
                baleRollPacking.setPackingType(originalBaleRollPacking.getPackingType() != null ? originalBaleRollPacking.getPackingType() : "Roll");
                baleRollPacking.setIsFactoryRoll(originalBaleRollPacking.getIsFactoryRoll());
                baleRollPacking.setRollOrBaleNo(originalBaleRollPacking.getRollOrBaleNo());
            } else if (originalPacking != null) {
                baleRollPacking.setOrderTypeId(originalPacking.getOrderTypeId());
                baleRollPacking.setBuyerId(originalPacking.getBuyerId());
                baleRollPacking.setWeavingWorkOrderId(originalPacking.getWeavingWorkOrderId());
                baleRollPacking.setIsJobwork(originalPacking.getIsJobwork());
                baleRollPacking.setSalesOrderId(originalPacking.getSalesOrderId());
                baleRollPacking.setWarehouseId(originalPacking.getWarehouseId());
                baleRollPacking.setCompanyId(originalPacking.getCompanyId());
                baleRollPacking.setRemarks(originalPacking.getRemarks());
                baleRollPacking.setFabricCodeId(resolvedFabricMasterId);
            } else {
                baleRollPacking.setBuyerId(salesReturn.getCustomerId());
                baleRollPacking.setWarehouseId(salesReturn.getWarehouseId());
                baleRollPacking.setCompanyId(salesReturn.getCompanyId());
                baleRollPacking.setSalesOrderId(salesReturn.getOrderId());
                baleRollPacking.setFabricCodeId(resolvedFabricMasterId);
            }

            // Final fallbacks for mandatory header identifiers
            if (baleRollPacking.getBuyerId() == null) {
                baleRollPacking.setBuyerId(salesReturn.getCustomerId());
            }
            if (baleRollPacking.getWarehouseId() == null) {
                baleRollPacking.setWarehouseId(salesReturn.getWarehouseId());
            }
            if (baleRollPacking.getCompanyId() == null) {
                baleRollPacking.setCompanyId(salesReturn.getCompanyId());
            }

            BigDecimal totalMeters = items.stream()
                .map(SalesReturnItem::getMeter)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            baleRollPacking.setTotalMeters(totalMeters);
            
            BigDecimal totalNet = items.stream()
                .map(SalesReturnItem::getNetWeight)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            baleRollPacking.setTotalNet(totalNet);

            baleRollPacking = baleRollPackingRepository.save(baleRollPacking);
        }

        Warehouse resolvedWarehouse = null;
        if (salesReturn.getWarehouseId() != null) {
            resolvedWarehouse = warehouseRepository.findById(salesReturn.getWarehouseId()).orElse(null);
        }

        for (SalesReturnItem item : items) {
            String rawPieceNo = item.getPieceNo();
            List<String> individualPieceNos = new ArrayList<>();
            if (rawPieceNo != null && !rawPieceNo.trim().isEmpty()) {
                for (String part : rawPieceNo.split(",")) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        individualPieceNos.add(trimmed);
                    }
                }
            }
            if (individualPieceNos.isEmpty()) {
                individualPieceNos.add(rawPieceNo != null ? rawPieceNo.trim() : "");
            }

            for (String singlePieceNo : individualPieceNos) {
                String pieceNumberForInspection = singlePieceNo + "-R";

                // Look for existing piece entry among the ones created by this sales return
                PieceEntry pieceEntry = null;
                for (PieceEntry pe : existingPieces) {
                    if (pieceNumberForInspection.equalsIgnoreCase(pe.getPieceNumber())) {
                        pieceEntry = pe;
                        break;
                    }
                }

                if (pieceEntry == null) {
                    pieceEntry = new PieceEntry();
                }
                piecesToKeep.add(pieceEntry);

                pieceEntry.setIsInspected(1);
                pieceEntry.setIsPacked(1);
                pieceEntry.setIsDispatched(null);
                pieceEntry.setInvoiceId(0L);

                if (isSpecialInvoice) {
                    pieceEntry.setSourceType("Sales Return");
                    pieceEntry.setInspectionStatus("INSPECTED");
                } else {
                    pieceEntry.setSourceType("Fabric Return");
                }

                pieceEntry.setPieceNumber(pieceNumberForInspection);

                List<PieceEntry> originalEntries = pieceEntryRepository.findByPieceNumberIgnoreCaseAndCompanyId(singlePieceNo, salesReturn.getCompanyId());
                PieceEntry existingPiece = originalEntries.isEmpty() ? null : originalEntries.get(0);
                
                // Fallback: try to find individual meters/weights from the original packing slip details if existingPiece is null
                BigDecimal originalPieceMeters = null;
                BigDecimal originalPieceNetWeight = null;
                BigDecimal originalPieceYards = null;
                BigDecimal originalPieceAvgWeight = null;
                BigDecimal originalPieceGrossWeight = null;

                if (originalPacking != null) {
                    java.util.Optional<GeneratePackingItem> originalGpItem = originalPacking.getItems().stream()
                        .filter(gpItem -> gpItem.getPieceNo() != null && gpItem.getPieceNo().equalsIgnoreCase(singlePieceNo))
                        .findFirst();
                    if (originalGpItem.isPresent()) {
                        GeneratePackingItem gpItem = originalGpItem.get();
                        if (gpItem.getLength() != null) originalPieceMeters = BigDecimal.valueOf(gpItem.getLength());
                        if (gpItem.getPounds() != null) originalPieceNetWeight = BigDecimal.valueOf(gpItem.getPounds());
                        if (gpItem.getYards() != null) originalPieceYards = BigDecimal.valueOf(gpItem.getYards());
                        if (gpItem.getAvgWt() != null) originalPieceAvgWeight = BigDecimal.valueOf(gpItem.getAvgWt());
                        if (gpItem.getGrossWt() != null) originalPieceGrossWeight = BigDecimal.valueOf(gpItem.getGrossWt());
                    }
                }

                if (existingPiece != null) {
                    pieceEntry.setQuantity(existingPiece.getQuantity());
                    pieceEntry.setWeight(existingPiece.getWeight());
                    pieceEntry.setCost(existingPiece.getCost());
                } else if (originalPieceMeters != null) {
                    pieceEntry.setQuantity(originalPieceMeters);
                    pieceEntry.setWeight(originalPieceNetWeight);
                } else {
                    int totalPiecesCount = individualPieceNos.size();
                    if (totalPiecesCount > 1) {
                        pieceEntry.setQuantity(item.getMeter() != null ? item.getMeter().divide(BigDecimal.valueOf(totalPiecesCount), 4, java.math.RoundingMode.HALF_UP) : null);
                        pieceEntry.setWeight(item.getNetWeight() != null ? item.getNetWeight().divide(BigDecimal.valueOf(totalPiecesCount), 4, java.math.RoundingMode.HALF_UP) : null);
                    } else {
                        pieceEntry.setQuantity(item.getMeter());
                        pieceEntry.setWeight(item.getNetWeight());
                    }
                }

                pieceEntry.setActiveFlag(true);
                pieceEntry.setCompanyId(salesReturn.getCompanyId());
                pieceEntry.setInwardId(salesReturn.getId());
                pieceEntry.setOrderId(salesReturn.getOrderId());
                pieceEntry.setOrderNo(salesReturn.getOrderNo());
                pieceEntry.setFabricMasterId(resolvedFabricMasterId);
                pieceEntry.setDcNumber(salesReturn.getDcNo());
                pieceEntry.setDcDate(salesReturn.getDcDate());

                if (resolvedWarehouse != null) {
                    pieceEntry.setWarehouse(resolvedWarehouse);
                }

                pieceEntry = pieceEntryRepository.save(pieceEntry);

                if (baleRollPacking != null) {
                    List<BaleRollPackingItem> packingItems = baleRollPackingItemRepository.findByPieceId(pieceEntry.getId());
                    BaleRollPackingItem packingItem = packingItems.isEmpty() ? new BaleRollPackingItem() : packingItems.get(0);
                    
                    packingItem.setBaleRollPacking(baleRollPacking);
                    packingItem.setPieceId(pieceEntry.getId());
                    packingItem.setPieceNo(pieceEntry.getPieceNumber());
                    packingItem.setisReturnedPiece(1);
                    
                    // Assign resolved individual/fallback values instead of aggregated totals
                    packingItem.setMeters(pieceEntry.getQuantity());
                    packingItem.setNetWeight(pieceEntry.getWeight());

                    // Map other details from original packing item/slip details if available
                    if (existingPiece != null) {
                        List<BaleRollPackingItem> originalPackingItems = baleRollPackingItemRepository.findByPieceId(existingPiece.getId());
                        if (!originalPackingItems.isEmpty()) {
                            BaleRollPackingItem originalPackingItem = originalPackingItems.get(0);
                            packingItem.setYards(originalPackingItem.getYards());
                            packingItem.setAvgWeight(originalPackingItem.getAvgWeight());
                            packingItem.setGrossWeight(originalPackingItem.getGrossWeight());
                            if (originalPackingItem.getMeters() != null) {
                                packingItem.setMeters(originalPackingItem.getMeters());
                            }
                            if (originalPackingItem.getNetWeight() != null) {
                                packingItem.setNetWeight(originalPackingItem.getNetWeight());
                            }
                        }
                    }
                    
                    // If still missing yards/weights/meters, fill from GeneratePackingItem
                    if (originalPieceYards != null && packingItem.getYards() == null) {
                        packingItem.setYards(originalPieceYards);
                    }
                    if (originalPieceAvgWeight != null && packingItem.getAvgWeight() == null) {
                        packingItem.setAvgWeight(originalPieceAvgWeight);
                    }
                    if (originalPieceGrossWeight != null && packingItem.getGrossWeight() == null) {
                        packingItem.setGrossWeight(originalPieceGrossWeight);
                    }
                    if (originalPieceMeters != null && packingItem.getMeters() == null) {
                        packingItem.setMeters(originalPieceMeters);
                    }
                    if (originalPieceNetWeight != null && packingItem.getNetWeight() == null) {
                        packingItem.setNetWeight(originalPieceNetWeight);
                    }

                    baleRollPackingItemRepository.save(packingItem);

                    // Also attach to parent packing entity to keep relationship in-sync
                    try {
                        if (baleRollPacking.getItems() == null) {
                            baleRollPacking.setItems(new java.util.ArrayList<>());
                        }
                        if (!baleRollPacking.getItems().contains(packingItem)) {
                            baleRollPacking.getItems().add(packingItem);
                        }
                        // update totals if desired
                        baleRollPacking.setTotalPieces(baleRollPacking.getTotalPieces() != null ? baleRollPacking.getTotalPieces() : 0L);
                        baleRollPackingRepository.save(baleRollPacking);

                        // Link packing info back to piece entry (non-breaking enhancement)
                        pieceEntry.setPackingId(baleRollPacking.getId());
                        pieceEntry.setIsPacked(1);
                        pieceEntryRepository.save(pieceEntry);
                    } catch (Exception e) {
                        logger.debug("Failed to link packing to pieceEntry {}: {}", pieceEntry.getId(), e.getMessage());
                    }
                }

                // Create corresponding InspectionDetail for returned piece if not exists
                try {
                    String returnPieceNo = pieceEntry.getPieceNumber();
                    InspectionDetail inspectionDetail = inspectionDetailRepository.findTopByPieceNoOrderByIdDesc(returnPieceNo);
                    if (inspectionDetail == null) {
                        inspectionDetail = new InspectionDetail();
                        inspectionDetail.setPieceNo(returnPieceNo);
                    }
                    
                    InspectionDetail originalDetail = null;
                    if (singlePieceNo != null && !singlePieceNo.isEmpty()) {
                        originalDetail = inspectionDetailRepository.findTopByPieceNoOrderByIdDesc(singlePieceNo);
                    }

                    if (originalDetail != null) {
                        inspectionDetail.setFabricInspection(originalDetail.getFabricInspection());
                        inspectionDetail.setPieceMeters(originalDetail.getPieceMeters());
                        inspectionDetail.setInspectedMeters(originalDetail.getInspectedMeters());
                        inspectionDetail.setActualNetWeight(originalDetail.getActualNetWeight());
                        inspectionDetail.setAvgWeight(originalDetail.getAvgWeight());
                        inspectionDetail.setTotalDefectPoints(originalDetail.getTotalDefectPoints());
                        inspectionDetail.setDefectCounts(originalDetail.getDefectCounts());
                        inspectionDetail.setGradeId(originalDetail.getGradeId());
                        inspectionDetail.setActiveFlag(originalDetail.getActiveFlag() != null ? originalDetail.getActiveFlag() : true);
                    } else {
                        // Minimal fallback when original inspection detail not found (use individual pieceEntry values, not item's totals)
                        inspectionDetail.setPieceMeters(pieceEntry.getQuantity() != null ? pieceEntry.getQuantity().doubleValue() : null);
                        inspectionDetail.setInspectedMeters(pieceEntry.getQuantity() != null ? pieceEntry.getQuantity().doubleValue() : null);
                        inspectionDetail.setActualNetWeight(pieceEntry.getWeight() != null ? pieceEntry.getWeight().doubleValue() : null);
                        inspectionDetail.setActiveFlag(true);
                    }
                    inspectionDetailRepository.save(inspectionDetail);
                } catch (Exception e) {
                    logger.error("Error creating/updating inspection detail for sales return piece {}: {}", pieceEntry.getPieceNumber(), e.getMessage());
                }
            }
        }

        // Remove any old pieces that are no longer part of this sales return
        for (PieceEntry pe : existingPieces) {
            if (!piecesToKeep.contains(pe)) {
                // 1. Delete associated packing items
                List<BaleRollPackingItem> packingItems = baleRollPackingItemRepository.findByPieceId(pe.getId());
                if (!packingItems.isEmpty()) {
                    baleRollPackingItemRepository.deleteAll(packingItems);
                }

                // 2. Delete associated inspection detail
                try {
                    InspectionDetail inspectionDetail = inspectionDetailRepository.findTopByPieceNoOrderByIdDesc(pe.getPieceNumber());
                    if (inspectionDetail != null) {
                        inspectionDetailRepository.delete(inspectionDetail);
                    }
                } catch (Exception e) {
                    logger.error("Error deleting inspection detail during sync of piece {}: {}", pe.getPieceNumber(), e.getMessage());
                }

                // 3. Delete the piece entry
                pieceEntryRepository.delete(pe);
            }
        }

        // Cleanup empty BaleRollPacking if any
        if (baleRollPacking != null && baleRollPacking.getId() != null) {
            List<BaleRollPackingItem> remainingPackingItems = baleRollPackingItemRepository.findByBaleRollPackingId(baleRollPacking.getId());
            if (remainingPackingItems.isEmpty()) {
                boolean hasLinkage = (baleRollPacking.getIsDispatched() != null && baleRollPacking.getIsDispatched() == 1);
                if (!hasLinkage) {
                    baleRollPackingRepository.delete(baleRollPacking);
                }
            }
        }
    }

    private void handleBeamReturn(List<SalesReturnBeamDTO> beamItems, SalesReturn salesReturn) {
        List<SalesReturnBeamEntity> entities = new ArrayList<>();
        for (SalesReturnBeamDTO beamDto : beamItems) {
            SalesReturnBeamEntity beamEntity = toBeamEntity(beamDto, salesReturn);
            entities.add(salesReturnBeamRepository.save(beamEntity));

            Long weavingContractId = null;
            if (beamDto.getWeavingContractNo() != null && !beamDto.getWeavingContractNo().isEmpty()) {
                Optional<WeavingContract> contract = weavingContractRepository.findByWeavingContractNo(beamDto.getWeavingContractNo());
                if (contract.isPresent()) {
                    weavingContractId = contract.get().getId();
                }
            }

            List<BeamInwardBeamDetails> existingInwardDetails = findMatchingInwardDetails(beamDto, weavingContractId);
            List<BeamOutwardDetail> existingOutwards = findMatchingOutwardDetails(beamDto);

            boolean isEdited = checkIfBeamEdited(beamDto, existingInwardDetails, existingOutwards, weavingContractId);
            if (isEdited) {
                createNewBeamMovement(beamDto, salesReturn, existingInwardDetails, existingOutwards, weavingContractId);
            } else {
                updateExistingBeamMovement(beamDto, salesReturn, existingInwardDetails, existingOutwards);
            }
        }
        if (salesReturn.getBeams() == null) {
            salesReturn.setBeams(new ArrayList<>());
        }
        salesReturn.getBeams().clear();
        salesReturn.getBeams().addAll(entities);
    }

    private void handleSizingReturn(List<SalesReturnSizingDTO> sizingItems, SalesReturn salesReturn) {
        List<SalesReturnSizing> entities = new ArrayList<>();
        boolean isSizingSalesType = isSizingSales(salesReturn.getInvoiceType());
        for (SalesReturnSizingDTO sizingDto : sizingItems) {
            SalesReturnSizing sizingEntity = toSizingEntity(sizingDto, salesReturn);
            entities.add(salesReturnSizingRepository.save(sizingEntity));

            String searchBeamNo = normalizeSizedBeamNo(sizingDto.getSizedBeamNo());
            List<SizingProductionDetail> productionDetails = sizingProductionDetailRepository.findBySizedBeamNoAndIsReturn(searchBeamNo, 0);
            List<SizingBeamOutwardDetail> outwardDetails = sizingBeamOutwardDetailRepository.findBySizedBeamNoAndSizingBeamOutwardIsReturn(searchBeamNo, 0);

            boolean isEdited = checkIfSizingEdited(sizingDto, productionDetails, outwardDetails);

            if (isEdited) {
                createNewSizingMovement(sizingDto, salesReturn, productionDetails, outwardDetails, isSizingSalesType);
            } else {
                updateExistingSizingMovement(sizingDto, salesReturn, productionDetails, outwardDetails, isSizingSalesType);
            }
        }
        if (salesReturn.getSizingItems() == null) {
            salesReturn.setSizingItems(new ArrayList<>());
        }
        salesReturn.getSizingItems().clear();
        salesReturn.getSizingItems().addAll(entities);
    }

    private boolean checkIfSizingEdited(SalesReturnSizingDTO dto, List<SizingProductionDetail> productionDetails, List<SizingBeamOutwardDetail> outwardDetails) {
        if (productionDetails.isEmpty() && outwardDetails.isEmpty()) {
            return true;
        }

        boolean productionEdited = false;
        if (!productionDetails.isEmpty()) {
            SizingProductionDetail production = productionDetails.get(0);
            productionEdited = !isEqual(dto.getBeamMeter(), production.getBeamMeters()) ||
                             !isEqual(dto.getExpectedFabricMeter(), production.getExpectedFabricMeter()) ||
                             !isEqual(dto.getNetWeight(), production.getNetWt()) ||
                             !isEqual(dto.getTotalEnds(), production.getTotalEnds());
        }

        boolean outwardEdited = false;
        if (!outwardDetails.isEmpty()) {
            SizingBeamOutwardDetail outward = outwardDetails.get(0);
            outwardEdited = !isEqual(dto.getBeamMeter(), outward.getBeamMeters()) ||
                            !isEqual(dto.getExpectedFabricMeter(), outward.getExpectedFabricMeter()) ||
                            !isEqual(dto.getTotalEnds(), outward.getTotalEnds());
        }

        return productionEdited || outwardEdited;
    }

    private void updateExistingSizingMovement(SalesReturnSizingDTO dto, SalesReturn salesReturn, List<SizingProductionDetail> productionDetails, List<SizingBeamOutwardDetail> outwardDetails, boolean isSizingSalesType) {
        if (productionDetails.isEmpty()) {
            productionDetails = sizingProductionDetailRepository.findBySizedBeamNo(normalizeSizedBeamNo(dto.getSizedBeamNo()));
        }
        for (SizingProductionDetail production : productionDetails) {
            production.setIsReturn(1);
            production.setReturnDate(salesReturn.getDate());
            production.setReturnNo(salesReturn.getSalesReturnNo());
            
            sizingProductionDetailRepository.save(production);
        }

        for (SizingBeamOutwardDetail detail : outwardDetails) {
            SizingBeamOutward outward = detail.getSizingBeamOutward();
            if (outward != null) {
                outward.setIsReturn(1);
                outward.setReturnDate(salesReturn.getDate());
                outward.setReturnNo(salesReturn.getSalesReturnNo());
                sizingBeamOutwardRepository.save(outward);
            }
        }
        
    }

    private void createNewSizingMovement(SalesReturnSizingDTO dto, SalesReturn salesReturn, List<SizingProductionDetail> productionDetails, List<SizingBeamOutwardDetail> outwardDetails, boolean isSizingSalesType) {
        if (productionDetails.isEmpty()) {
            productionDetails = sizingProductionDetailRepository.findBySizedBeamNo(normalizeSizedBeamNo(dto.getSizedBeamNo()));
        }
        if (!productionDetails.isEmpty()) {
            SizingProductionDetail original = productionDetails.get(0);
            SizingProductionDetail newProduction = new SizingProductionDetail();
            
            newProduction.setSizingProduction(original.getSizingProduction());
            newProduction.setShiftName(original.getShiftName());
            newProduction.setFlangeNo(original.getFlangeNo());
            newProduction.setFlangeId(original.getFlangeId());
            newProduction.setSizedBeamNo(isSizingSalesType ? appendReturnSuffix(original.getSizedBeamNo()) : original.getSizedBeamNo());
            newProduction.setSizingPickup(original.getSizingPickup());
            newProduction.setBreakages(original.getBreakages());
            newProduction.setRpm(original.getRpm());
            newProduction.setTareWt(original.getTareWt());
            newProduction.setGrossWt(original.getGrossWt());
            newProduction.setFabricPanelWidth(original.getFabricPanelWidth());
            newProduction.setEmployeeId(original.getEmployeeId());
            newProduction.setOrderTypeId(original.getOrderTypeId());
            newProduction.setIssuedTradingCompanyId(original.getIssuedTradingCompanyId());
            newProduction.setIsTradingInward(original.getIsTradingInward());
            newProduction.setIsIssuedToWeaving(original.getIsIssuedToWeaving());
            newProduction.setIsFactoryInward(original.getIsFactoryInward());
            newProduction.setIsSizingBeamOutward(0);

            newProduction.setBeamMeters(dto.getBeamMeter() != null ? dto.getBeamMeter() : original.getBeamMeters());
            newProduction.setExpectedFabricMeter(dto.getExpectedFabricMeter() != null ? dto.getExpectedFabricMeter() : original.getExpectedFabricMeter());
            newProduction.setNetWt(dto.getNetWeight() != null ? dto.getNetWeight() : original.getNetWt());
            newProduction.setTotalEnds(dto.getTotalEnds() != null ? dto.getTotalEnds() : original.getTotalEnds());

            newProduction.setIsReturn(0);
            newProduction.setReturnDate(null);
            newProduction.setReturnNo(null);
            
            sizingProductionDetailRepository.save(newProduction);

            for (SizingProductionDetail detail : productionDetails) {
                detail.setIsReturn(1);
                detail.setReturnDate(salesReturn.getDate());
                detail.setReturnNo(salesReturn.getSalesReturnNo());
                sizingProductionDetailRepository.save(detail);
            }
        }

        if (outwardDetails.isEmpty()) {
            outwardDetails = sizingBeamOutwardDetailRepository.findBySizedBeamNo(normalizeSizedBeamNo(dto.getSizedBeamNo()));
        }
        for (SizingBeamOutwardDetail detail : outwardDetails) {
            SizingBeamOutward outward = detail.getSizingBeamOutward();
            if (outward != null) {
                outward.setIsReturn(1);
                outward.setReturnDate(salesReturn.getDate());
                outward.setReturnNo(salesReturn.getSalesReturnNo());
                // outward.setTotalBeamMeters(dto.getBeamMeter() != null ? dto.getBeamMeter() : outward.getTotalBeamMeters());
                sizingBeamOutwardRepository.save(outward);
            }
        }
    }

    private boolean checkIfBeamEdited(SalesReturnBeamDTO dto, List<BeamInwardBeamDetails> inwardDetails, List<BeamOutwardDetail> outwardDetails, Long weavingContractId) {
        if (inwardDetails.isEmpty() && outwardDetails.isEmpty()) {
            return true;
        }

        boolean inwardEdited = false;
        if (!inwardDetails.isEmpty()) {
            BeamInwardBeamDetails inward = inwardDetails.get(0);
            inwardEdited = !isEqual(dto.getBeamMeters(), inward.getWrapMeters()) ||
                           !isEqual(dto.getExpectedFabricMeter(), inward.getExpectedFabricMeter()) ||
                           !isEqual(dto.getShrinkage(), inward.getShrinkage()) ||
                           (weavingContractId != null && !weavingContractId.equals(inward.getWeavingContractId()));
        }

        boolean outwardEdited = false;
        if (!outwardDetails.isEmpty()) {
            BeamOutwardDetail outwardDetail = outwardDetails.get(0);
            outwardEdited = !isEqual(dto.getBeamMeters(), outwardDetail.getWrapMeters()) ||
                            !isEqual(dto.getExpectedFabricMeter(), outwardDetail.getExpectedFabricMeter()) ||
                            !isEqual(dto.getShrinkage(), outwardDetail.getShrinkage()) ||
                            (weavingContractId != null && !weavingContractId.equals(outwardDetail.getWeavingContractId()));
        }

        return inwardEdited || outwardEdited;
    }

    private List<BeamInwardBeamDetails> findMatchingInwardDetails(SalesReturnBeamDTO dto, Long weavingContractId) {
        String searchBeamNo = normalizeSizedBeamNo(dto.getSizedBeamNo());
        List<BeamInwardBeamDetails> matches = new ArrayList<>();
        if (dto.getBeamInwardId() != null && searchBeamNo != null && !searchBeamNo.isEmpty()) {
            matches = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNoAndIsReturn(dto.getBeamInwardId(), searchBeamNo);
        }
        if (!matches.isEmpty()) {
            return matches;
        }
        if (weavingContractId != null && searchBeamNo != null && !searchBeamNo.isEmpty()) {
            matches = beamInwardBeamDetailsRepository.findByWeavingContractIdAndSizedBeamNoAndIsReturn(weavingContractId, searchBeamNo);
        }
        if (!matches.isEmpty()) {
            return matches;
        }
        if (searchBeamNo != null && !searchBeamNo.isEmpty()) {
            matches = beamInwardBeamDetailsRepository.findBySizedBeamNoAndIsReturn(searchBeamNo);
        }
        return matches;
    }

    private List<BeamOutwardDetail> findMatchingOutwardDetails(SalesReturnBeamDTO dto) {
        String searchBeamNo = normalizeSizedBeamNo(dto.getSizedBeamNo());
        List<BeamOutwardDetail> matches = new ArrayList<>();
        if (dto.getBeamOutwardId() != null && searchBeamNo != null && !searchBeamNo.isEmpty()) {
            matches = beamOutwardDetailRepository.findByBeamOutwardIdAndSizedBeamNoAndIsReturn(dto.getBeamOutwardId(), searchBeamNo);
        }
        if (!matches.isEmpty()) {
            return matches;
        }
        if (dto.getBeamOutwardId() != null) {
            matches = beamOutwardDetailRepository.findByBeamOutwardIdAndIsReturn(dto.getBeamOutwardId());
            if (searchBeamNo != null && !searchBeamNo.isEmpty()) {
                final String sizedBeamNo = searchBeamNo;
                matches = matches.stream()
                        .filter(detail -> sizedBeamNo.equals(detail.getSizedBeamNo()))
                        .collect(Collectors.toList());
            }
        }
        if (!matches.isEmpty()) {
            return matches;
        }
        if (searchBeamNo != null && !searchBeamNo.isEmpty()) {
            matches = beamOutwardDetailRepository.findBySizedBeamNoAndIsReturn(searchBeamNo);
        }
        return matches;
    }

    private Long getInwardTypeId(Long orderTypeId) {
        if (orderTypeId == null) {
            return null;
        }
        String targetCode = orderTypeId.equals(1L) ? "206" : 
                            orderTypeId.equals(2L) ? "206" : null;
        if (targetCode != null) {
            Optional<CodesDetail> cd = codesDetailRepository.findByCode(targetCode);
            if (cd.isEmpty()) {
                cd = codesDetailRepository.findByValue(orderTypeId.equals(1L) ? "Inhouse Beam" : "Trading Beam");
            }
            if (cd.isEmpty()) {
                cd = codesDetailRepository.findByValue(orderTypeId.equals(1L) ? "INHOUSE BEAM" : "TRADING BEAM");
            }
            return cd.map(CodesDetail::getId).orElse(null);
        }
        return null;
    }

    private void updateExistingBeamMovement(SalesReturnBeamDTO dto, SalesReturn salesReturn, List<BeamInwardBeamDetails> inwardDetails, List<BeamOutwardDetail> outwardDetails) {
        String searchBeamNo = normalizeSizedBeamNo(dto.getSizedBeamNo());
        List<BeamInwardBeamDetails> inwardToUpdate = inwardDetails;
        if (inwardToUpdate.isEmpty() && searchBeamNo != null) {
            inwardToUpdate = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(Arrays.asList(searchBeamNo));
        }
        for (BeamInwardBeamDetails inward : inwardToUpdate) {
            inward.setIsReturn(1);
            inward.setReturnDate(salesReturn.getDate());
            inward.setReturnNo(salesReturn.getSalesReturnNo());
            inward.setReturnWeaverId(dto.getWeaverId());
            
            if (inward.getInwardTypeId() == null) {
                Long inwardTypeId = null;
                if (inward.getBeamInward() != null && inward.getBeamInward().getOrderTypeId() != null) {
                    inwardTypeId = getInwardTypeId(inward.getBeamInward().getOrderTypeId());
                } else if (dto.getBeamInwardId() != null) {
                    Optional<BeamInward> bi = beamInwardRepository.findById(dto.getBeamInwardId());
                    if (bi.isPresent() && bi.get().getOrderTypeId() != null) {
                        inwardTypeId = getInwardTypeId(bi.get().getOrderTypeId());
                    }
                }
                if (inwardTypeId != null) {
                    inward.setInwardTypeId(inwardTypeId);
                }
            }
            
            beamInwardBeamDetailsRepository.save(inward);
        }

        List<BeamOutwardDetail> outwardToUpdate = outwardDetails;
        if (outwardToUpdate.isEmpty() && searchBeamNo != null) {
            outwardToUpdate = beamOutwardDetailRepository.findBySizedBeamNo(searchBeamNo);
        }
        for (BeamOutwardDetail detail : outwardToUpdate) {
            detail.setIsReturn(1);
            detail.setReturnDate(salesReturn.getDate());
            detail.setReturnNo(salesReturn.getSalesReturnNo());
            detail.setReturnWeaverId(dto.getWeaverId());
            beamOutwardDetailRepository.save(detail);
        }
    }

    private void createNewBeamMovement(SalesReturnBeamDTO dto, SalesReturn salesReturn, List<BeamInwardBeamDetails> inwardDetails, List<BeamOutwardDetail> outwardDetails, Long weavingContractId) {
        String searchBeamNo = normalizeSizedBeamNo(dto.getSizedBeamNo());
        if (inwardDetails.isEmpty()) {
            inwardDetails = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(Arrays.asList(searchBeamNo));
        }
        
        BeamInwardBeamDetails newInward = new BeamInwardBeamDetails();
        Long inwardTypeId = null;
        if (!inwardDetails.isEmpty()) {
            BeamInwardBeamDetails original = inwardDetails.get(0);
            Double originalMeters = original.getWrapMeters();
            Double returnedMeters = dto.getBeamMeters() != null ? dto.getBeamMeters().doubleValue() : originalMeters;
            Double remainingMeters = calculateRemainingMeters(originalMeters, returnedMeters);
            
            newInward.setWeavingContractId(weavingContractId != null ? weavingContractId : original.getWeavingContractId());
            newInward.setSalesOrderId(original.getSalesOrderId());
            newInward.setShrinkage(original.getShrinkage());
            newInward.setTotalEnds(original.getTotalEnds());
            newInward.setSizedBeamNo(searchBeamNo != null ? searchBeamNo : normalizeSizedBeamNo(original.getSizedBeamNo()));
            newInward.setFlangeNo(original.getFlangeNo());
            newInward.setFlangeId(original.getFlangeId());
            newInward.setWeavingVendorId(original.getWeavingVendorId());
            newInward.setWeaverVendorName(original.getWeaverVendorName());
            newInward.setSizingPlanId(original.getSizingPlanId());
            newInward.setFabricId(original.getFabricId());
            newInward.setFabricPanelWidth(original.getFabricPanelWidth());
            newInward.setBeamInward(original.getBeamInward());
            
            if (original.getInwardTypeId() != null) {
                inwardTypeId = original.getInwardTypeId();
            } else if (original.getBeamInward() != null && original.getBeamInward().getOrderTypeId() != null) {
                inwardTypeId = getInwardTypeId(original.getBeamInward().getOrderTypeId());
            }
            
            // Mark all matching original records as returned
            for (BeamInwardBeamDetails detail : inwardDetails) {
                detail.setIsReturn(1);
                detail.setReturnDate(salesReturn.getDate());
                detail.setReturnNo(salesReturn.getSalesReturnNo());
                detail.setReturnWeaverId(dto.getWeaverId());
                if (dto.getExpectedFabricMeter() != null) {
                    detail.setExpectedFabricMeter(dto.getExpectedFabricMeter().doubleValue());
                }
                if (dto.getShrinkage() != null) {
                    detail.setShrinkage(dto.getShrinkage().doubleValue());
                }
                if (detail.getInwardTypeId() == null && inwardTypeId != null) {
                    detail.setInwardTypeId(inwardTypeId);
                }
                beamInwardBeamDetailsRepository.save(detail);
            }
            newInward.setWrapMeters(returnedMeters);
        } else {
            // Mapping other beam reference fields from the selected beam in DTO
            newInward.setSizedBeamNo(searchBeamNo);
            newInward.setFlangeNo(dto.getFlangeNo());
            newInward.setWeavingContractId(weavingContractId);
            newInward.setWeaverVendorName(dto.getWeaverName());
            newInward.setWeavingVendorId(dto.getWeaverId());
            if (dto.getFabricPanelWidth() != null) {
                try {
                    newInward.setFabricPanelWidth(Double.parseDouble(dto.getFabricPanelWidth()));
                } catch (Exception e) {}
            }
        }
        
        if (inwardTypeId == null && dto.getBeamInwardId() != null) {
            Optional<BeamInward> bi = beamInwardRepository.findById(dto.getBeamInwardId());
            if (bi.isPresent() && bi.get().getOrderTypeId() != null) {
                inwardTypeId = getInwardTypeId(bi.get().getOrderTypeId());
            }
        }
        
        if (inwardTypeId != null) {
            newInward.setInwardTypeId(inwardTypeId);
        }

        newInward.setIsBeamOutwarded(0);
        newInward.setReturnWeaverId(null);

        if (dto.getShrinkage() != null) newInward.setShrinkage(dto.getShrinkage().doubleValue());
        else if (newInward.getShrinkage() == null && !inwardDetails.isEmpty()) newInward.setShrinkage(inwardDetails.get(0).getShrinkage());

        if (newInward.getWrapMeters() == null && dto.getBeamMeters() != null) newInward.setWrapMeters(dto.getBeamMeters().doubleValue());
        else if (newInward.getWrapMeters() == null && !inwardDetails.isEmpty()) newInward.setWrapMeters(inwardDetails.get(0).getWrapMeters());
        
        if (dto.getExpectedFabricMeter() != null) newInward.setExpectedFabricMeter(dto.getExpectedFabricMeter().doubleValue());
        else if (newInward.getExpectedFabricMeter() == null && !inwardDetails.isEmpty()) newInward.setExpectedFabricMeter(inwardDetails.get(0).getExpectedFabricMeter());

        newInward.setIsReturn(2);
        newInward.setReturnDate(salesReturn.getDate());
        newInward.setReturnNo(salesReturn.getSalesReturnNo());
        newInward.setSizedBeamNo(ensureReturnSuffix(normalizeSizedBeamNo(newInward.getSizedBeamNo())));
        beamInwardBeamDetailsRepository.save(newInward);

        if (outwardDetails.isEmpty() && searchBeamNo != null) {
            outwardDetails = beamOutwardDetailRepository.findBySizedBeamNo(searchBeamNo);
        }

        for (BeamOutwardDetail detail : outwardDetails) {
            Double originalMeters = detail.getWrapMeters();
            Double returnedMeters = dto.getBeamMeters() != null ? dto.getBeamMeters().doubleValue() : originalMeters;
            Double remainingMeters = calculateRemainingMeters(originalMeters, returnedMeters);

            detail.setIsReturn(1);
            detail.setReturnDate(salesReturn.getDate());
            detail.setReturnNo(salesReturn.getSalesReturnNo());
            detail.setReturnWeaverId(dto.getWeaverId());
            if (dto.getExpectedFabricMeter() != null) {
                detail.setExpectedFabricMeter(dto.getExpectedFabricMeter().doubleValue());
            }
            if (dto.getShrinkage() != null) {
                detail.setShrinkage(dto.getShrinkage().doubleValue());
            }
            beamOutwardDetailRepository.save(detail);
        }
    }

    private String ensureReturnSuffix(String sizedBeamNo) {
        if (sizedBeamNo == null || sizedBeamNo.isEmpty()) {
            return sizedBeamNo;
        }
        return sizedBeamNo.endsWith("-R") ? sizedBeamNo : sizedBeamNo + "-R";
    }

    private Double calculateRemainingMeters(Double originalMeters, Double returnedMeters) {
        if (originalMeters == null) {
            return returnedMeters;
        }
        if (returnedMeters == null) {
            return originalMeters;
        }
        return Math.max(0d, originalMeters - returnedMeters);
    }
    

    private boolean isEqual(Double d1, Double d2) {
        if (d1 == null && d2 == null) return true;
        if (d1 == null || d2 == null) return false;
        return d1.equals(d2);
    }

    private boolean isEqual(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) return true;
        if (i1 == null || i2 == null) return false;
        return i1.equals(i2);
    }

    private boolean isEqual(BigDecimal bd, Double d) {
        if (bd == null && d == null) return true;
        if (bd == null || d == null) return false;
        return bd.stripTrailingZeros().equals(BigDecimal.valueOf(d).stripTrailingZeros());
    }

    private boolean isEqual(BigDecimal bd, Long l) {
        if (bd == null && l == null) return true;
        if (bd == null || l == null) return false;
        return bd.stripTrailingZeros().equals(BigDecimal.valueOf(l).stripTrailingZeros());
    }

    private SalesReturnBeamEntity toBeamEntity(SalesReturnBeamDTO dto, SalesReturn salesReturn) {
        SalesReturnBeamEntity entity = new SalesReturnBeamEntity();
        entity.setSalesReturn(salesReturn);
        entity.setBeamOutwardNo(dto.getBeamOutwardNo());
        entity.setBeamOutwardId(dto.getBeamOutwardId());
        entity.setBeamInwardId(dto.getBeamInwardId());
        
        // Fetch beamOutwardNo based on beamOutwardId if provided
        if (dto.getBeamOutwardId() != null && (dto.getBeamOutwardNo() == null || dto.getBeamOutwardNo().isEmpty())) {
            Optional<BeamOutward> beamOutward = beamOutwardRepository.findById(dto.getBeamOutwardId());
            if (beamOutward.isPresent()) {
                entity.setBeamOutwardNo(beamOutward.get().getOutwardNo());
            }
        }
        
        // Fetch beamOutwardNo based on beamInwardId if provided
        if (dto.getBeamInwardId() != null && (dto.getBeamOutwardNo() == null || dto.getBeamOutwardNo().isEmpty())) {
            List<BeamOutward> beamOutwards = beamOutwardRepository.findByBeamInwardId(dto.getBeamInwardId());
            if (!beamOutwards.isEmpty()) {
                entity.setBeamOutwardNo(beamOutwards.get(0).getOutwardNo());
            }
        }
        
        if (dto.getOutwardDate() != null && !dto.getOutwardDate().isEmpty()) {
            entity.setOutwardDate(LocalDate.parse(dto.getOutwardDate()));
        }
        entity.setWeavingContractNo(dto.getWeavingContractNo());
        entity.setWeaverName(dto.getWeaverName());
        entity.setSizingPlanNo(dto.getSizingPlanNo());
        entity.setSalesOrderNo(dto.getSalesOrderNo());
        entity.setFlangeNo(dto.getFlangeNo());
        entity.setSizedBeamNo(dto.getSizedBeamNo());
        entity.setBeamMeters(dto.getBeamMeters());
        entity.setExpectedFabricMeter(dto.getExpectedFabricMeter());
        entity.setTotalBeams(dto.getTotalBeams());
        entity.setWeaverId(dto.getWeaverId());
        entity.setFabricPanelWidth(dto.getFabricPanelWidth());
        entity.setShrinkage(dto.getShrinkage());
        entity.setIsReturn(dto.getIsReturn() != null ? dto.getIsReturn() : 1);

        entity.setReturnDate(salesReturn.getDate());
        entity.setReturnNo(salesReturn.getSalesReturnNo());
        entity.setReturnWeaverId(dto.getWeaverId());
        return entity;
    }

    private void ensureItemFieldLengths(SalesReturnItemDTO dto) {
        if (dto == null) {
            return;
        }
        dto.setRollNo(limit(dto.getRollNo(), 100, "rollNo"));
        dto.setPieceNo(limit(dto.getPieceNo(), 100, "pieceNo"));
        dto.setPackingListNo(limit(dto.getPackingListNo(), 100, "packingListNo"));
    }

    private String limit(String value, int maxLength, String fieldName) {
        if (value == null) {
            return null;
        }
        if (value.length() <= maxLength) {
            return value;
        }
        logger.warn("Truncating {} to {} characters", fieldName, maxLength);
        return value.substring(0, maxLength);
    }

    public PaginatedResponse<SalesReturnDTO> getAllSalesReturnsByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return buildPaginatedResponse(
                salesReturnRepository.findIdsByCompanyId(companyId),
                offset,
                limit,
                ids -> ids.isEmpty() ? new ArrayList<>() : salesReturnRepository.findByCompanyIdAndIds(companyId, ids)
        );
    }

    public SalesReturnDTO getSalesReturnByIdAndCompany(Long id, Long companyId) {
        SalesReturn entity = salesReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("SalesReturn not found with id: " + id + " and companyId: " + companyId));
        return toDto(entity);
    }

    @Transactional
    public SalesReturnDTO updateSalesReturnByCompany(Long id, Long companyId, SalesReturnDTO dto) {
        SalesReturn entity = salesReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("SalesReturn not found with id: " + id + " and companyId: " + companyId));
        
        mapDtoToEntity(dto, entity);
        updateItemsCollection(entity, dto.getItems());
        updateYarnItemsCollection(entity, dto.getYarnItems());
        
        SalesReturn updatedEntity = salesReturnRepository.save(entity);
        
        // Handle Yarn Sales Return for inventory
        if (isYarnSales(dto.getInvoiceType()) && updatedEntity.getYarnItems() != null && !updatedEntity.getYarnItems().isEmpty()) {
            handleYarnSalesReturn(updatedEntity);
        }
        
        // Create piece entries and handle beam returns (only if not Yarn Sales)
        if (!isYarnSales(dto.getInvoiceType())) {
            if (dto.getItems() != null && !dto.getItems().isEmpty()) {
                createPieceEntries(updatedEntity.getItems(), updatedEntity);
            }
            if (!isSizingSales(dto.getInvoiceType())) {
                if (dto.getBeamItems() != null && !dto.getBeamItems().isEmpty()) {
                    salesReturnBeamRepository.deleteBySalesReturnId(id);
                    handleBeamReturn(dto.getBeamItems(), updatedEntity);
                }
                // Clear sizing items if it's not Sizing Sales
                updatedEntity.getSizingItems().clear();
            } else {
                if (dto.getSizingItems() != null && !dto.getSizingItems().isEmpty()) {
                    salesReturnSizingRepository.deleteBySalesReturnId(id);
                    handleSizingReturn(dto.getSizingItems(), updatedEntity);
                }
                // Clear beam items if it IS Sizing Sales
                updatedEntity.getBeams().clear();
            }
        }
        return getSalesReturnByIdAndCompany(id, companyId);
    }

    @Transactional
    public void deleteSalesReturnByCompany(Long id, Long companyId) {
        SalesReturn entity = salesReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("SalesReturn not found with id: " + id + " and companyId: " + companyId));
        
        validateDeletionNotBlocked(entity);
        
        // Delete related YarnInventory and YarnInwardStockDetails if it's Yarn Sales
        if (isYarnSales(entity.getInvoiceType())) {
            cleanupYarnInventory(id, getYarnSalesReturnInwardTypeId());
        }

        // Cleanup PieceEntry and BaleRollPacking records created during save
        cleanupPieceEntriesAndPacking(entity);

        // Cleanup Sizing flow related records
        if (isSizingSales(entity.getInvoiceType())) {
            cleanupSizingMovement(entity);
        }

        // Cleanup Beam flow related records
        if (!isYarnSales(entity.getInvoiceType()) && !isSizingSales(entity.getInvoiceType())) {
            cleanupBeamMovement(entity);
        }
        
        salesReturnItemRepository.findBySalesReturnId(id).forEach(item ->
            salesReturnItemRepository.delete(item)
        );

        salesYarnReturnRepository.findBySalesReturnId(id).forEach(item ->
            salesYarnReturnRepository.delete(item)
        );

        salesReturnBeamRepository.deleteBySalesReturnId(id);
        salesReturnSizingRepository.deleteBySalesReturnId(id);
        
        salesReturnRepository.deleteByIdAndCompanyId(id, companyId);
    }

    private PaginatedResponse<SalesReturnDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit, java.util.function.Function<List<Long>, List<SalesReturn>> fetcher) {
        long off = offset != null ? Math.max(0, offset) : 0;
        long lim = (limit != null && limit > 0) ? limit : 10;
        long total = allIds.size();
        int start = (int) Math.min(off, total);
        if (start >= total) {
            return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, new ArrayList<>());
        }
        int end = (int) Math.min(off + lim, total);
        List<Long> pageIds = allIds.subList(start, end);
        List<SalesReturn> returns = pageIds.isEmpty() ? new ArrayList<>() : fetcher.apply(pageIds);
        List<SalesReturnDTO> dtos = returns.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(
                total,
                end < total,
                off > 0,
                off,
                lim,
                dtos
        );
    }

    private String normalizeSizedBeamNo(String sizedBeamNo) {
        if (sizedBeamNo != null && sizedBeamNo.endsWith("-R")) {
            return sizedBeamNo.substring(0, sizedBeamNo.length() - 2);
        }
        return sizedBeamNo;
    }

    private boolean isSizingSales(String invoiceType) {
        if (invoiceType == null) return false;
        String type = invoiceType.trim();
        return "Sizing sales".equalsIgnoreCase(type) || "204".equals(type);
    }

    private boolean isBeamSales(String invoiceType) {
        if (invoiceType == null) return false;
        String type = invoiceType.trim();
        return "Beam".equalsIgnoreCase(type) || "206".equals(type);
    }

    private String appendReturnSuffix(String sizedBeamNo) {
        if (sizedBeamNo == null || sizedBeamNo.trim().isEmpty()) {
            return sizedBeamNo;
        }
        String trimmedBeamNo = sizedBeamNo.trim();
        if (trimmedBeamNo.endsWith("-R")) {
            return trimmedBeamNo;
        }
        return trimmedBeamNo + "-R";
    }

    private boolean isYarnSales(String invoiceType) {
        if (invoiceType == null) return false;
        String type = invoiceType.trim();
        return "Yarn Sales".equalsIgnoreCase(type) || "205".equals(type);
    }

    private Long normalizeId(Long id) {
        return (id == null || id <= 0) ? null : id;
    }

    private String normalizeString(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    private void handleYarnSalesReturn(SalesReturn salesReturn) {
        Long outwardTypeId = getYarnSalesReturnInwardTypeId();

        Map<Long, Long> preservedMillIds = new HashMap<>();
        List<YarnInventory> existingInventories = yarnInventoryRepository.findByInwardIdAndInwardTypeId(salesReturn.getId(), outwardTypeId);
        if (!existingInventories.isEmpty()) {
            YarnInventory inv = existingInventories.get(0);
            if (inv.getDetails() != null) {
                for (YarnInwardStockDetails detail : inv.getDetails()) {
                    if (detail.getOrderItemId() != null && detail.getMillId() != null && detail.getMillId() > 0) {
                        preservedMillIds.put(detail.getOrderItemId(), detail.getMillId());
                    }
                }
            }
        }

        // Find and delete existing YarnInventory and YarnInwardStockDetails if they exist
        cleanupYarnInventory(salesReturn.getId(), outwardTypeId);

        YarnInventory yarnInventory = new YarnInventory();
        
        yarnInventory.setInwardId(salesReturn.getId());
        yarnInventory.setInwardTypeId(outwardTypeId);

        yarnInventory.setInwardNo(salesReturn.getSalesReturnNo());
        yarnInventory.setCompanyId(salesReturn.getCompanyId());
        yarnInventory.setInwardDate(LocalDateTime.now());
        yarnInventory.setFinancialYear(getFinancialYearId(salesReturn.getDate()));
        yarnInventory.setDcNo(salesReturn.getDcNo());
        yarnInventory.setDcDate(salesReturn.getDcDate());
        // yarnInventory.setDcNo(salesReturn.getInvoiceNo());
        // yarnInventory.setDcDate(salesReturn.getDate());
        yarnInventory.setOrderId(salesReturn.getOrderId());
        yarnInventory.setOrderNo(salesReturn.getOrderNo());
        yarnInventory.setVendorId(salesReturn.getCustomerId());
        yarnInventory.setWarehouseId(salesReturn.getWarehouseId());

        YarnInventory savedYarnInventory = yarnInventoryRepository.save(yarnInventory);

        List<YarnInwardStockDetails> stockDetailsList = new ArrayList<>();
        
        for (SalesYarnReturn yarnReturnItem : salesReturn.getYarnItems()) {
            YarnInwardStockDetails stockDetails = new YarnInwardStockDetails();
            stockDetails.setYarnInventory(savedYarnInventory);
            stockDetails.setInwardTypeId(outwardTypeId);
            
            // Find Yarn ID from Yarn Name
            Long yarnId = null;
            if (yarnReturnItem.getYarnName() != null) {
                Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(yarnReturnItem.getYarnName());
                if (yarnMaster.isPresent()) {
                    yarnId = yarnMaster.get().getId();
                }
            }
            stockDetails.setItemId(yarnId);
            stockDetails.setOrderItemId(yarnReturnItem.getId());
            stockDetails.setUomId(1L); // Default UOM
            stockDetails.setLotNo(yarnReturnItem.getLotNo());
            stockDetails.setInwardQty(yarnReturnItem.getIssuedQty());
            stockDetails.setInwardBags(yarnReturnItem.getIssuedBags());
            
            if (yarnReturnItem.getIssuedBags() != null && yarnReturnItem.getIssuedBags().compareTo(BigDecimal.ZERO) > 0 && 
                yarnReturnItem.getIssuedQty() != null && yarnReturnItem.getIssuedQty().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal kgsPerBag = yarnReturnItem.getIssuedQty().divide(yarnReturnItem.getIssuedBags(), 3, java.math.RoundingMode.HALF_UP);
                stockDetails.setKgsPerBag(kgsPerBag);
            } else {
                stockDetails.setKgsPerBag(BigDecimal.ZERO);
            }
            
            stockDetails.setAcceptedQty(yarnReturnItem.getIssuedQty());
            stockDetails.setRejectedQty(BigDecimal.ZERO);
            stockDetails.setAvailableQty(yarnReturnItem.getIssuedQty());
            stockDetails.setAvailableBags(yarnReturnItem.getIssuedBags());
            stockDetails.setWarehouseId(yarnReturnItem.getWarehouseId() != null ? yarnReturnItem.getWarehouseId() : salesReturn.getWarehouseId());
            Long millId = (yarnReturnItem.getMillId() != null && yarnReturnItem.getMillId() > 0)
                    ? yarnReturnItem.getMillId()
                    : preservedMillIds.get(yarnReturnItem.getId());
            stockDetails.setMillId(millId);
            stockDetails.setConesPerBag(yarnReturnItem.getConesPerBag());
            stockDetails.setConesWeight(yarnReturnItem.getConesWeight());

            stockDetailsList.add(stockDetails);
        }

        yarnInwardStockDetailsRepository.saveAll(stockDetailsList);
        savedYarnInventory.setDetails(stockDetailsList);
    }

    private void cleanupYarnInventory(Long inwardId, Long inwardTypeId) {
        try {
            List<YarnInventory> existingInventories = yarnInventoryRepository.findByInwardIdAndInwardTypeId(inwardId, inwardTypeId);
            for (YarnInventory inv : existingInventories) {
                // First delete all associated stock details
                if (inv.getDetails() != null && !inv.getDetails().isEmpty()) {
                    yarnInwardStockDetailsRepository.deleteAll(inv.getDetails());
                }
                // Then delete the inventory record
                yarnInventoryRepository.delete(inv);
            }
            
            // Additional cleanup: Find any orphaned YarnInwardStockDetails that might reference this sales return
            List<YarnInwardStockDetails> orphanedDetails = yarnInwardStockDetailsRepository.findAll().stream()
                .filter(detail -> detail.getYarnInventory() != null 
                    && inwardId.equals(detail.getYarnInventory().getInwardId())
                    && inwardTypeId.equals(detail.getYarnInventory().getInwardTypeId()))
                .collect(Collectors.toList());
            
            for (YarnInwardStockDetails detail : orphanedDetails) {
                // Check if the parent inventory still exists
                Optional<YarnInventory> parentInv = yarnInventoryRepository.findById(detail.getYarnInventory().getId());
                if (!parentInv.isPresent()) {
                    // Parent inventory was deleted, so delete this orphaned detail
                    yarnInwardStockDetailsRepository.delete(detail);
                }
            }
        } catch (Exception e) {
            logger.error("Error cleaning up yarn inventory for inwardId: {}, inwardTypeId: {}", inwardId, inwardTypeId, e);
            throw new RuntimeException("Failed to cleanup yarn inventory records", e);
        }
    }

    private Long getYarnSalesReturnInwardTypeId() {
        return codesDetailRepository.findByValue("YARN_SALES_RETURN")
                .map(CodesDetail::getId)
                .orElseGet(() -> codesDetailRepository.findByValue("yarn-sales-return")
                        .map(CodesDetail::getId)
                        .orElse(8L));
    }

    private String getFinancialYearId(LocalDate date) {
        if (date == null) return null;
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

    private void validateDuplicates(SalesReturnDTO dto, Long currentId) {
        // 1. Validate Fabric Piece Numbers
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            java.util.Set<String> requestPieceNumbers = new java.util.HashSet<>();
            for (SalesReturnItemDTO itemDto : dto.getItems()) {
                String rawPieceNo = itemDto.getPieceNo();
                if (rawPieceNo == null || rawPieceNo.trim().isEmpty()) {
                    continue;
                }
                List<String> singlePieceNos = new ArrayList<>();
                for (String part : rawPieceNo.split(",")) {
                    String trimmed = part.trim();
                    if (!trimmed.isEmpty()) {
                        singlePieceNos.add(trimmed);
                    }
                }
                if (singlePieceNos.isEmpty()) {
                    singlePieceNos.add(rawPieceNo.trim());
                }
                for (String pieceNo : singlePieceNos) {
                    String pieceNumberWithR = pieceNo + "-R";

                    // Check for duplicates within the request
                    if (!requestPieceNumbers.add(pieceNumberWithR)) {
                        throw new IllegalArgumentException("Duplicate piece number found in request: " + pieceNumberWithR);
                    }

                    // Check for duplicates in database for the same company
                    List<PieceEntry> existingPieces = pieceEntryRepository.findByPieceNumberAndCompanyId(pieceNumberWithR, dto.getCompanyId());
                    for (PieceEntry existingPiece : existingPieces) {
                        if (currentId != null) {
                            continue; // Skip for now to avoid false positives on update until better linking is established
                        }
                        throw new IllegalArgumentException("Piece number '" + pieceNumberWithR + "' already exists for this company");
                    }
                }
            }
        }

        // 2. Validate Yarn Lot Numbers
        if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
            java.util.Set<String> requestLotNumbers = new java.util.HashSet<>();
            Long companyId = dto.getCompanyId();
            for (SalesYarnReturnDTO yarnDto : dto.getYarnItems()) {
                String lotNo = yarnDto.getLotNo();
                if (lotNo == null || lotNo.trim().isEmpty()) {
                    continue;
                }
                lotNo = lotNo.trim();

                // Find Yarn ID from Yarn Name
                Long yarnId = null;
                if (yarnDto.getYarnName() != null) {
                    Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(yarnDto.getYarnName());
                    if (yarnMaster.isPresent()) {
                        yarnId = yarnMaster.get().getId();
                    }
                }

                if (yarnId == null || companyId == null) continue;

                // Check for duplicates within the request
                if (!requestLotNumbers.add(lotNo)) {
                    throw new IllegalArgumentException("Duplicate lot number found in request: " + lotNo);
                }

                // For Yarn Sales returns, existing lot numbers are allowed because the return can replenish the same lot.
                if (!isYarnSales(dto.getInvoiceType())) {
                    List<YarnInwardStockDetails> existingLots = yarnInwardStockDetailsRepository.findByCompanyIdAndItemIdAndLotNoIgnoreCase(companyId, yarnId, lotNo);
                    for (YarnInwardStockDetails existingLot : existingLots) {
                        if (yarnDto.getId() != null && yarnDto.getId().equals(existingLot.getOrderItemId())) {
                            continue;
                        }
                        if (currentId != null && existingLot.getYarnInventory() != null &&
                            existingLot.getYarnInventory().getInwardId() != null &&
                            existingLot.getYarnInventory().getInwardId().equals(currentId) &&
                            Objects.equals(existingLot.getYarnInventory().getInwardTypeId(), getYarnSalesReturnInwardTypeId())) {
                            continue;
                        }
                        throw new IllegalArgumentException("Lot number '" + lotNo + "' already exists for this company");
                    }
                }
            }
        }
    }
}

@Aspect
@org.springframework.stereotype.Component
class SalesReturnStatusAspect {

    private static final Logger logger = LoggerFactory.getLogger(SalesReturnStatusAspect.class);

    @Autowired
    private SalesReturnItemRepository salesReturnItemRepository;

    @Autowired
    private SalesReturnBeamRepository salesReturnBeamRepository;

    @AfterReturning(pointcut = "execution(* com.tech3.erp.repository.PieceEntryRepository.save*(..))", returning = "result")
    public void afterPieceEntrySave(Object result) {
        if (result == null) return;
        if (result instanceof PieceEntry) {
            handlePieceEntry((PieceEntry) result);
        } else if (result instanceof Iterable) {
            for (Object obj : (Iterable<?>) result) {
                if (obj instanceof PieceEntry) {
                    handlePieceEntry((PieceEntry) obj);
                }
            }
        }
    }

    private void handlePieceEntry(PieceEntry pieceEntry) {
        String pieceNumber = pieceEntry.getPieceNumber();
        if (pieceNumber == null || pieceNumber.trim().isEmpty()) {
            return;
        }

        if (pieceNumber.endsWith("-R") || 
            "Sales Return".equalsIgnoreCase(pieceEntry.getSourceType()) || 
            "Fabric Return".equalsIgnoreCase(pieceEntry.getSourceType())) {
            return;
        }

        try {
            List<SalesReturnItem> items = salesReturnItemRepository.findByPieceNoAndCompanyId(pieceNumber, pieceEntry.getCompanyId());
            if (items != null && !items.isEmpty()) {
                for (SalesReturnItem item : items) {
                    if (item.getIsDispatch() != 1) {
                        item.setIsDispatch(1);
                        salesReturnItemRepository.save(item);
                        logger.info("Automatically marked SalesReturnItem ID {} as dispatched for PieceEntry {}", item.getId(), pieceNumber);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error updating SalesReturnItem status for PieceEntry {}", pieceNumber, e);
        }
    }

    @AfterReturning(pointcut = "execution(* com.tech3.erp.repository.BeamInwardBeamDetailsRepository.save*(..))", returning = "result")
    public void afterBeamInwardDetailSave(Object result) {
        if (result == null) return;
        if (result instanceof BeamInwardBeamDetails) {
            handleBeamInwardDetail((BeamInwardBeamDetails) result);
        } else if (result instanceof Iterable) {
            for (Object obj : (Iterable<?>) result) {
                if (obj instanceof BeamInwardBeamDetails) {
                    handleBeamInwardDetail((BeamInwardBeamDetails) obj);
                }
            }
        }
    }

    private void handleBeamInwardDetail(BeamInwardBeamDetails detail) {
        if (detail.getIsBeamOutwarded() != null && detail.getIsBeamOutwarded() == 1) {
            String sizedBeamNo = detail.getSizedBeamNo();
            if (sizedBeamNo == null || sizedBeamNo.trim().isEmpty()) {
                return;
            }

            Long companyId = null;
            if (detail.getBeamInward() != null) {
                companyId = detail.getBeamInward().getCompanyId();
            }

            if (companyId != null) {
                try {
                    String baseSizedBeamNo = sizedBeamNo;
                    if (sizedBeamNo.endsWith("-R")) {
                        baseSizedBeamNo = sizedBeamNo.substring(0, sizedBeamNo.length() - 2);
                    }

                    List<SalesReturnBeamEntity> matchingBeams = salesReturnBeamRepository.findMatchingBeams(sizedBeamNo, baseSizedBeamNo, companyId);
                    if (matchingBeams != null && !matchingBeams.isEmpty()) {
                        for (SalesReturnBeamEntity beam : matchingBeams) {
                            if (beam.getIsOutwarded() != 1) {
                                beam.setIsOutwarded(1);
                                salesReturnBeamRepository.save(beam);
                                logger.info("Automatically marked SalesReturnBeamEntity ID {} as outwarded for BeamInwardBeamDetails {}", beam.getId(), sizedBeamNo);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error updating SalesReturnBeamEntity status for BeamInwardBeamDetails {}", sizedBeamNo, e);
                }
            }
        }
    }
}