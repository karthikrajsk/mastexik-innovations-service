// package com.tech3.erp.service;

// import com.tech3.erp.dto.PurchaseInwardDTO;
// import com.tech3.erp.dto.PurchaseInwardItemDTO;
// import com.tech3.erp.dto.YarnInwardStockDetailsDto;
// import com.tech3.erp.dto.YarnStockDetailDTO;
// import com.tech3.erp.dto.PieceEntryDTO;
// import com.tech3.erp.dto.ItemInwardStockDetailsDto;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.entity.PurchaseInward;
// import com.tech3.erp.entity.PurchaseInwardItem;
// import com.tech3.erp.entity.PurchaseOrder;
// import com.tech3.erp.entity.PurchaseOrderItem;
// import com.tech3.erp.entity.YarnInventory;
// import com.tech3.erp.entity.YarnInwardStockDetails;
// import com.tech3.erp.entity.YarnMaster;
// import com.tech3.erp.entity.ItemInventory;
// import com.tech3.erp.entity.ItemInwardStockDetails;
// import com.tech3.erp.entity.ItemMaster;
// import com.tech3.erp.entity.PieceEntry;
// import com.tech3.erp.entity.Warehouse;
// import com.tech3.erp.entity.LotEntry;
// import com.tech3.erp.entity.YarnIssue;
// import com.tech3.erp.entity.SizingYarnIssue;
// import com.tech3.erp.repository.ApprovalRepository;
// import com.tech3.erp.repository.CompanyRepository;
// import com.tech3.erp.repository.PoTypeMasterRepository;
// import com.tech3.erp.repository.PurchaseInwardRepository;
// import com.tech3.erp.repository.PurchaseInwardItemRepository;
// import com.tech3.erp.repository.PurchaseOrderRepository;
// import com.tech3.erp.repository.PurchaseOrderItemRepository;
// import com.tech3.erp.repository.YarnInventoryRepository;
// import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
// import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
// import com.tech3.erp.repository.ItemInventoryRepository;
// import com.tech3.erp.repository.ItemInwardStockDetailsRepository;
// import com.tech3.erp.repository.ItemMasterRepository;
// import com.tech3.erp.repository.WarehouseRepository;
// import com.tech3.erp.repository.PieceEntryRepository;
// import com.tech3.erp.repository.YarnMasterRepository;
// import com.tech3.erp.repository.YarnItemRepository;
// import com.tech3.erp.repository.YarnIssueRepository;
// import com.tech3.erp.repository.SizingYarnIssueRepository;
// import com.tech3.erp.repository.UomRepository;
// import com.tech3.erp.entity.PoTypeMaster;
// import com.tech3.erp.entity.YarnItemEntity;
// import com.tech3.erp.entity.Uom;
// import com.tech3.erp.util.PaginationHelper;

// import jakarta.persistence.EntityNotFoundException;
// import jakarta.transaction.Transactional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class PurchaseInwardService {

//     private static final Logger logger = LoggerFactory.getLogger(PurchaseInwardService.class);

//     @Autowired
//     private PurchaseInwardRepository inwardRepository;

//     @Autowired
//     private PurchaseOrderRepository purchaseOrderRepository;

//     @Autowired
//     private PurchaseOrderItemRepository purchaseOrderItemRepository;

//     @Autowired
//     private YarnInventoryRepository yarnInventoryRepository;

//     @Autowired
//     private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;

//     @Autowired
//     private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;

//     @Autowired
//     private CompanyRepository companyRepository;

//     @Autowired
//     private WarehouseRepository warehouseRepository;

//     @Autowired
//     private PurchaseInwardItemRepository purchaseInwardItemRepository;

//     @Autowired
//     private PieceEntryRepository pieceEntryRepository;

//     @Autowired
//     private YarnMasterRepository yarnMasterRepository;

//     @Autowired
//     private FabricInspectionService fabricInspectionService;

//     @Autowired
//     private YarnItemRepository yarnItemRepository;

//     @Autowired
//     private YarnIssueRepository yarnIssueRepository;

//     @Autowired
//     private SizingYarnIssueRepository sizingYarnIssueRepository;

//     @Autowired
//     private UomRepository uomRepository;

//     @Autowired
//     private ItemInventoryRepository itemInventoryRepository;

//     @Autowired
//     private ItemInwardStockDetailsRepository itemInwardStockDetailsRepository;

//     @Autowired
//     private ItemMasterRepository itemMasterRepository;
    
//     @Autowired
//     private ApprovalRepository approvalRepository;

//     // PO Type constants - Match po_type_master table IDs
//     private static final Long PO_TYPE_YARN = 1L;
//     private static final Long PO_TYPE_WOVEN_FABRIC = 2L;
//     private static final Long PO_TYPE_KNITTED_FABRIC = 3L;
//     private static final Long PO_TYPE_ITEM = 11L;
   
    
//     @Autowired
//     private PoTypeMasterRepository poTypeMasterRepository;
    
//     // Source type constants
//     private static final String SOURCE_PURCHASE_INWARD = "purchase_inward";

//     /**
//      * Create a new Purchase Inward record (with company support)
//      */
//     @Transactional
//     public PurchaseInward createPurchaseInward(PurchaseInwardDTO dto) {
//         validateCompanyId(dto.getCompanyId());
        
//         PurchaseInward inward = new PurchaseInward();

//         // Map basic fields
//         inward.setPoTypeId(dto.getPoTypeId());
//         inward.setVendorId(dto.getVendorId());
//         inward.setPurchaseOrderId(normalizeId(dto.getPurchaseOrderId()));
//         inward.setRemarks(dto.getRemarks());
//         inward.setInwardDate(dto.getInwardDate() != null ? dto.getInwardDate() : LocalDate.now());
//         inward.setActiveFlag(true);
//         inward.setCompanyId(dto.getCompanyId()); // NEW: Company support

//         // ✅ DC fields (ensure correct mapping)
//         inward.setDcDate(dto.getDcDate() != null ? dto.getDcDate() : LocalDate.now());
//         inward.setDcNumber(dto.getDcNumber());
//         inward.setInwardNo(dto.getPurchaseInwardNo());

//         // ✅ Calculate and set financial year
//         inward.setFinancialYear(calculateFinancialYear(inward.getInwardDate()));

//         // Determine PO type
//         // boolean isYarn = PO_TYPE_YARN.equals(dto.getPoTypeId());
//          boolean isYarn = PO_TYPE_YARN.equals(dto.getPoTypeId()) || PO_TYPE_WOVEN_FABRIC.equals(dto.getPoTypeId()) || PO_TYPE_KNITTED_FABRIC.equals(dto.getPoTypeId());
//         boolean isItem = PO_TYPE_ITEM.equals(dto.getPoTypeId());
//         boolean isKnittedFabric = PO_TYPE_KNITTED_FABRIC.equals(dto.getPoTypeId());
//         boolean isWovenFabric = PO_TYPE_WOVEN_FABRIC.equals(dto.getPoTypeId());
//         boolean isFabric = isKnittedFabric || isWovenFabric;
        
//         String poTypeName = getPoTypeName(dto.getPoTypeId());
//         String purchaseOrderNo = getPurchaseOrderNo(dto.getPurchaseOrderId());
//         inward.setPurchaseOrderNo(purchaseOrderNo); // NEW: From old code

//         // ✅ Handle inward items
//         List<PurchaseInwardItem> inwardItems = new ArrayList<>();
//         if (dto.getPurchaseInwardItemsDtl() != null) {
//             for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
//                 PurchaseInwardItem item = new PurchaseInwardItem();
//                 Long purchaseOrderItemId = normalizeId(itemDto.getPoItemId());
//                 item.setPurchaseOrderItemId(purchaseOrderItemId);
//                 item.setQuantityReceived(itemDto.getQuantityReceived());
//                 item.setNoOfPiece(itemDto.getNoOfPiece());
//                 item.setPrice(itemDto.getPrice());
//                 item.setMillNameId(itemDto.getMillNameId());
//                 item.setPurchaseInward(inward);
//                 item.setActiveFlag(true);

//                 // Derive fabric code and mill name id from purchase order item
//                 String fabricCode = getFabricCodeFromPurchaseOrderItem(purchaseOrderItemId);
//                 item.setFabricCode(fabricCode);
//                 Long millNameId = itemDto.getMillNameId() != null ? itemDto.getMillNameId() : getMillNameIdFromPurchaseOrderItem(purchaseOrderItemId);
//                 logger.info("PO Item ID: {}, millNameId from payload: {}, millNameId fetched: {}", purchaseOrderItemId, itemDto.getMillNameId(), millNameId);
//                 item.setMillNameId(millNameId);

//                 // FABRIC FLOW - Use Piece Entries (merged from both old and new code)
//                 if (isFabric && itemDto.getPieceEntries() != null && !itemDto.getPieceEntries().isEmpty()) {
//                     List<PieceEntry> pieceEntries = itemDto.getPieceEntries().stream()
//                         .map(pieceDto -> mapPieceEntryDtoToEntity(null, pieceDto, item, poTypeName, purchaseOrderNo, dto.getCompanyId()))
//                         .collect(Collectors.toList());
//                     item.setPieceEntries(pieceEntries);
//                 }

//                 // NEW: Yarn Stock Details (from new code)
//                 if (itemDto.getYarnStockDetails() != null && !itemDto.getYarnStockDetails().isEmpty()) {
//                     // This will be handled in createYarnInventoryRecords method
//                 }

//                 // NEW: Item Stock Details
//                 if (itemDto.getItemStockDetails() != null && !itemDto.getItemStockDetails().isEmpty()) {
//                     // This will be handled in createItemInventoryRecords method
//                 }

//                 inwardItems.add(item);
//             }
//         }

//         inward.setItems(inwardItems);

//         // ✅ Set main fabric code (first item's fabric code)
//         if (!inwardItems.isEmpty() && inwardItems.get(0).getFabricCode() != null) {
//             inward.setFabricCode(inwardItems.get(0).getFabricCode());
//         }

//         // Save
//         PurchaseInward savedInward = inwardRepository.save(inward);

//         // Ensure child collections are initialized while we're still in the transaction
//         if (savedInward.getItems() != null) {
//             savedInward.getItems().forEach(it -> {
//                 if (it.getPieceEntries() != null) it.getPieceEntries().size();
//                 if (it.getLotEntries() != null) it.getLotEntries().size();
//             });
//         }

//         // NEW: Create fabric inspections for piece entries (from old code)
//         for (PurchaseInwardItem item : savedInward.getItems()) {
//             if (item.getPieceEntries() != null && !item.getPieceEntries().isEmpty()) {
//                 for (PieceEntry piece : item.getPieceEntries()) {
//                     fabricInspectionService.createFabricInspectionFromPieceEntry(piece);
//                 }
//             }
//         }

//         // ✅ Create yarn inventory records if PO type is yarn OR if yarn stock details are present
//         if (isYarn || hasYarnStockDetails(dto)) {
//             createYarnInventoryRecords(savedInward, dto);
//         }

//         // ✅ Create item inventory records if PO type is item OR if item stock details are present
//         if (isItem || hasItemStockDetails(dto)) {
//             createItemInventoryRecords(savedInward, dto);
//         }

//         return savedInward;
//     }

//     /**
//      * Get all purchase inwards (with company support)
//      */
//      public List<PurchaseInwardDTO> getAllPurchaseInwards() {
//         List<PurchaseInward> inwards = inwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

//         List<PurchaseInwardDTO> result = new ArrayList<>();
//         for (PurchaseInward inward : inwards) {
//             PurchaseInwardDTO dto = convertToDetailedDTO(inward);

//             // ✅ Ensure DC fields are mapped in response
//             dto.setDcDate(inward.getDcDate());
//             dto.setDcNumber(inward.getDcNumber());
//             dto.setPurchaseInwardNo(inward.getInwardNo());
//             dto.setCompanyId(inward.getCompanyId());

//             // Derive fabric code if missing
//             if (dto.getFabricCode() == null && inward.getItems() != null && !inward.getItems().isEmpty()) {
//                 String fabricCode = getFabricCodeFromPurchaseOrderItems(inward.getItems());
//                 dto.setFabricCode(fabricCode);
//             }

//             result.add(dto);
//         }
//         return result;
//     }
// //     
// //    private List<PurchaseInwardDTO> getAllPurchaseInwardsByFabricType(Long fabricTypeId) {
// //        List<PieceEntry> pieceEntries = pieceEntryRepository.findBySourceTypeAndFabricTypeId("purchase_inward", fabricTypeId);
// //        
// //        if (pieceEntries.isEmpty()) {
// //            return new ArrayList<>();
// //        }
// //        
// //        java.util.Set<Long> purchaseInwardItemIds = new java.util.HashSet<>();
// //        for (PieceEntry piece : pieceEntries) {
// //            if (piece.getPurchaseInwardItem() != null) {
// //                purchaseInwardItemIds.add(piece.getPurchaseInwardItem().getId());
// //            }
// //        }
// //        
// //        if (purchaseInwardItemIds.isEmpty()) {
// //            return new ArrayList<>();
// //        }
// //        
// //        List<PurchaseInward> inwards = inwardRepository.findAll().stream()
// //                .filter(inward -> inward.getItems() != null && 
// //                    inward.getItems().stream().anyMatch(item -> purchaseInwardItemIds.contains(item.getId())))
// //                .sorted(java.util.Comparator.comparing(PurchaseInward::getId).reversed())
// //                .collect(Collectors.toList());
// //
// //        List<PurchaseInwardDTO> result = new ArrayList<>();
// //        for (PurchaseInward inward : inwards) {
// //            PurchaseInwardDTO dto = convertToDetailedDTO(inward);
// //
// //            dto.setDcDate(inward.getDcDate());
// //            dto.setDcNumber(inward.getDcNumber());
// //            dto.setPurchaseInwardNo(inward.getInwardNo());
// //            dto.setCompanyId(inward.getCompanyId());
// //
// //            if (dto.getFabricCode() == null && inward.getItems() != null && !inward.getItems().isEmpty()) {
// //                String fabricCode = getFabricCodeFromPurchaseOrderItems(inward.getItems());
// //                dto.setFabricCode(fabricCode);
// //            }
// //
// //            result.add(dto);
// //        }
// //        return result;
// //    }

//     // public List<PurchaseInwardDTO> getAllPurchaseInwards() {
//     //     List<PurchaseInward> inwards = inwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

//     //     return inwards.stream()
//     //             .map(this::convertToDto)
//     //             .collect(Collectors.toList());
//     // }

//     /**
//      * Get all purchase inwards by company (from old code)
//      */
//     public List<PurchaseInwardDTO> getPurchaseInwardsByCompany(Long companyId) {
//         validateCompanyId(companyId);
        
//         return inwardRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id")).stream()
//                 .map(this::convertToDto)
//                 .collect(Collectors.toList());
//     }

//     /**
//      * Get all purchase inwards with pagination (with company filter option)
//      */
//     public PaginatedResponse<PurchaseInwardDTO> getAllPurchaseInwardsWithPagination(Long offset, Long limit, Long companyId) {
//         List<PurchaseInwardDTO> all;
        
//         if (companyId != null) {
//             validateCompanyId(companyId);
//             all = getPurchaseInwardsByCompany(companyId);
//         } else {
//             all = getAllPurchaseInwards();
//         }
        
//         return PaginationHelper.paginate(all, offset, limit);
//     }

//     public PaginatedResponse<PurchaseInwardDTO> getAllPurchaseInwardsWithPaginationAndCompany(Long offset, Long limit, Long fabricTypeId, Long companyId) {
//         validateCompanyId(companyId);
//         List<PurchaseInward> inwards = inwardRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id"));
//         List<PurchaseInwardDTO> dtos = inwards.stream()
//                 .map(this::convertToDetailedDTO)
//                 .collect(Collectors.toList());
        
//         if (fabricTypeId != null) {
//             dtos = dtos.stream()
//                     .filter(dto -> matchesFabricType(dto, fabricTypeId))
//                     .collect(Collectors.toList());
//         }
        
//         return PaginationHelper.paginate(dtos, offset, limit);
//     }

//     /**
//      * Get a single purchase inward by ID
//      */
//     public PurchaseInward getPurchaseInwardById(Long id) {
//         return inwardRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Purchase Inward not found with id: " + id));
//     }

//     /**
//      * Get purchase inward by ID and company (from old code)
//      */
//     public PurchaseInward getPurchaseInwardByIdAndCompany(Long id, Long companyId) {
//         validateCompanyId(companyId);
        
//         return inwardRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException(
//                         "Purchase Inward not found with id: " + id + " and companyId: " + companyId));
//     }

//     /**
//      * Update purchase inward record (generic)
//      */
//     @Transactional
//     public PurchaseInward updatePurchaseInward(Long id, PurchaseInwardDTO dto) {
//         PurchaseInward inward = getPurchaseInwardById(id);
//         return updatePurchaseInwardInternal(inward, dto);
//     }

//     /**
//      * Update purchase inward record and return as DTO with yarn stock details.
//      * This is the new API to add.
//      */
//     @Transactional
//     public PurchaseInwardDTO updatePurchaseInwardWithDetails(Long id, PurchaseInwardDTO dto) {
//         PurchaseInward updatedInward = updatePurchaseInward(id, dto);
//         inwardRepository.flush();
//         yarnInwardStockDetailsRepository.flush();
        
//          PurchaseInwardDTO resultDto = convertToDetailedDTO(updatedInward);
//         // PurchaseInwardDTO resultDto = convertToDto(updatedInward);
//         resultDto.setDcDate(updatedInward.getDcDate());
//         resultDto.setDcNumber(updatedInward.getDcNumber());
//         resultDto.setPurchaseInwardNo(updatedInward.getInwardNo());
//         resultDto.setCompanyId(updatedInward.getCompanyId());
        
//         return resultDto;
//     }

//     /**
//      * Update purchase inward by company (from old code)
//      */
//     @Transactional
//     public PurchaseInward updatePurchaseInwardByCompany(Long id, Long companyId, PurchaseInwardDTO dto) {
//         validateCompanyId(companyId);
        
//         if (dto.getCompanyId() == null) {
//             dto.setCompanyId(companyId);
//         } else if (!dto.getCompanyId().equals(companyId)) {
//             throw new IllegalArgumentException("Company ID mismatch");
//         }
        
//         PurchaseInward existing = getPurchaseInwardByIdAndCompany(id, companyId);
//         return updatePurchaseInwardInternal(existing, dto);
//     }

//     /**
//      * Update purchase inward by company and return as DTO with yarn stock details.
//      * Company-specific version of the new API.
//      */
//     @Transactional
//     public PurchaseInwardDTO updatePurchaseInwardWithDetailsByCompany(Long id, Long companyId, PurchaseInwardDTO dto) {
//         validateCompanyId(companyId);
        
//         if (dto.getCompanyId() == null) {
//             dto.setCompanyId(companyId);
//         } else if (!dto.getCompanyId().equals(companyId)) {
//             throw new IllegalArgumentException("Company ID mismatch");
//         }
        
//         PurchaseInward updatedInward = updatePurchaseInwardByCompany(id, companyId, dto);
//         inwardRepository.flush();
//         yarnInwardStockDetailsRepository.flush();
        
//         PurchaseInwardDTO resultDto = convertToDto(updatedInward);
//         resultDto.setDcDate(updatedInward.getDcDate());
//         resultDto.setDcNumber(updatedInward.getDcNumber());
//         resultDto.setPurchaseInwardNo(updatedInward.getInwardNo());
//         resultDto.setCompanyId(updatedInward.getCompanyId());
        
//         return resultDto;
//     }

//     /**
//      * Internal update method
//      */
//     private PurchaseInward updatePurchaseInwardInternal(PurchaseInward inward, PurchaseInwardDTO dto) {
//         // Update basic fields
//         if (dto.getPoTypeId() != null) {
//             inward.setPoTypeId(dto.getPoTypeId());
//         }
//         if (dto.getVendorId() != null) {
//             inward.setVendorId(dto.getVendorId());
//         }
//         if (dto.getRemarks() != null) {
//             inward.setRemarks(dto.getRemarks());
//         }
//         if (dto.getActiveFlag() != null) {
//             inward.setActiveFlag(dto.getActiveFlag());
//         }
//         if (dto.getDcDate() != null) {
//             inward.setDcDate(dto.getDcDate());
//         }
//         if (dto.getDcNumber() != null) {
//             inward.setDcNumber(dto.getDcNumber());
//         }
//         if (dto.getFabricCode() != null) {
//             inward.setFabricCode(dto.getFabricCode());
//         }
//         if (dto.getPurchaseInwardNo() != null) {
//             inward.setInwardNo(dto.getPurchaseInwardNo());
//         }
//         if (dto.getCompanyId() != null) {
//             inward.setCompanyId(dto.getCompanyId());
//         }
//         if (dto.getPurchaseOrderNo() != null) {
//             inward.setPurchaseOrderNo(dto.getPurchaseOrderNo());
//         }

//         PurchaseInward savedInward = inwardRepository.save(inward);

//         Long savedPoTypeId = savedInward.getPoTypeId();
//         boolean isYarn = PO_TYPE_YARN.equals(savedInward.getPoTypeId()) || PO_TYPE_WOVEN_FABRIC.equals(savedInward.getPoTypeId()) || PO_TYPE_KNITTED_FABRIC.equals(savedInward.getPoTypeId());
//         // boolean isYarn = PO_TYPE_YARN.equals(savedPoTypeId);
//         boolean isItem = PO_TYPE_ITEM.equals(savedPoTypeId);
//         boolean isKnittedFabric = PO_TYPE_KNITTED_FABRIC.equals(savedPoTypeId);
//         boolean isWovenFabric = PO_TYPE_WOVEN_FABRIC.equals(savedPoTypeId);
//         boolean isFabric = isKnittedFabric || isWovenFabric;
//         String poTypeName = getPoTypeName(savedPoTypeId);
//         String purchaseOrderNo = savedInward.getPurchaseOrderNo();
//         if (purchaseOrderNo == null) {
//             purchaseOrderNo = getPurchaseOrderNo(savedInward.getPurchaseOrderId());
//             savedInward.setPurchaseOrderNo(purchaseOrderNo);
//         }
//         Long defaultCompanyId = savedInward.getCompanyId();

//         if (dto.getPurchaseInwardItemsDtl() != null) {
//             for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
//                 if (itemDto.getId() != null && itemDto.getId() > 0) {
//                     Optional<PurchaseInwardItem> existingItem = purchaseInwardItemRepository.findById(itemDto.getId());
//                     if (existingItem.isPresent()) {
//                         PurchaseInwardItem item = existingItem.get();
                        
//                         if (itemDto.getQuantityReceived() != null) {
//                             item.setQuantityReceived(itemDto.getQuantityReceived());
//                         }
//                         if (itemDto.getNoOfPiece() != null) {
//                             item.setNoOfPiece(itemDto.getNoOfPiece());
//                         }
//                         if (itemDto.getPrice() != null) {
//                             item.setPrice(itemDto.getPrice());
//                         }
//                         if (itemDto.getMillNameId() != null) {
//                             item.setMillNameId(itemDto.getMillNameId());
//                         }
//                         if (itemDto.getActiveFlag() != null) {
//                             item.setActiveFlag(itemDto.getActiveFlag());
//                         }
                        
//                         purchaseInwardItemRepository.save(item);
//                         logger.info("Updated PurchaseInwardItem ID: {}, quantityReceived: {}, price: {}", 
//                             itemDto.getId(), itemDto.getQuantityReceived(), itemDto.getPrice());
                        
//                         if (isFabric && itemDto.getPieceEntries() != null && !itemDto.getPieceEntries().isEmpty()) {
//                             if (item.getPieceEntries() == null) {
//                                 item.setPieceEntries(new ArrayList<>());
//                             }
//                             for (PieceEntryDTO pieceDto : itemDto.getPieceEntries()) {
//                                 PieceEntry existingPiece = null;
//                                 if (pieceDto.getId() != null) {
//                                     existingPiece = pieceEntryRepository.findById(pieceDto.getId()).orElse(null);
//                                 }
//                                 PieceEntry mappedPiece = mapPieceEntryDtoToEntity(existingPiece, pieceDto, item, poTypeName, purchaseOrderNo, defaultCompanyId);
//                                 if (existingPiece == null) {
//                                     item.getPieceEntries().add(mappedPiece);
//                                 }
//                                 pieceEntryRepository.save(mappedPiece);
//                                 logger.info("Upserted PieceEntry for PurchaseInwardItem ID: {}, PieceEntry ID: {}", item.getId(), mappedPiece.getId());
//                             }
//                         }
                        
//                         if (itemDto.getYarnStockDetails() != null && !itemDto.getYarnStockDetails().isEmpty()) {
//                             for (YarnStockDetailDTO yarnDetailDto : itemDto.getYarnStockDetails()) {
//                                 if (item.getPurchaseOrderItemId() != null) {
//                                     List<YarnInwardStockDetails> existingYarnDetails = yarnInwardStockDetailsRepository.findAllByOrderItemIdAndInwardTypeId(item.getPurchaseOrderItemId(), inward.getPoTypeId());
                                    
//                                     if (!existingYarnDetails.isEmpty()) {
//                                         for (YarnInwardStockDetails yarnDetail : existingYarnDetails) {
//                                             if (yarnDetailDto.getLotNo() != null) {
//                                                 yarnDetail.setLotNo(yarnDetailDto.getLotNo());
//                                             }
//                                             if (yarnDetailDto.getInwardQty() != null) {
//                                                 yarnDetail.setInwardQty(yarnDetailDto.getInwardQty());
//                                             }
//                                             if (yarnDetailDto.getInwardBags() != null) {
//                                                 yarnDetail.setInwardBags(yarnDetailDto.getInwardBags());
//                                             }
//                                             if (yarnDetailDto.getKgsPerBag() != null) {
//                                                 yarnDetail.setKgsPerBag(yarnDetailDto.getKgsPerBag());
//                                             }
//                                             if (yarnDetailDto.getConesPerBag() != null) {
//                                                 yarnDetail.setConesPerBag(yarnDetailDto.getConesPerBag());
//                                             }
//                                             if (yarnDetailDto.getConesWeight() != null) {
//                                                 yarnDetail.setConesWeight(yarnDetailDto.getConesWeight());
//                                             }
//                                             if (yarnDetailDto.getAcceptedQty() != null) {
//                                                 yarnDetail.setAcceptedQty(yarnDetailDto.getAcceptedQty());
//                                             }
//                                             if (yarnDetailDto.getRejectedQty() != null) {
//                                                 yarnDetail.setRejectedQty(yarnDetailDto.getRejectedQty());
//                                             }
//                                             if (yarnDetailDto.getWarehouseId() != null) {
//                                                 yarnDetail.setWarehouseId(yarnDetailDto.getWarehouseId());
//                                             }
//                                             if (yarnDetailDto.getRemark() != null) {
//                                                 yarnDetail.setRemark(yarnDetailDto.getRemark());
//                                             }
//                                             if (yarnDetailDto.getUomId() != null) {
//                                                 yarnDetail.setUomId(yarnDetailDto.getUomId());
//                                             } else if (yarnDetail.getUomId() == null && yarnDetail.getItemId() != null) {
//                                                 Long resolvedUomId = resolveUomIdForYarn(yarnDetail.getItemId());
//                                                 yarnDetail.setUomId(resolvedUomId);
//                                             }
                                            
//                                             yarnInwardStockDetailsRepository.save(yarnDetail);
//                                             logger.info("Updated YarnInwardStockDetails for orderItemId: {}, lotNo: {}", 
//                                                 item.getPurchaseOrderItemId(), yarnDetailDto.getLotNo());
//                                         }
//                                     }
//                                 }
//                             }
//                         }
//                     }
//                 }
//             }
//         }

// //        boolean isYarn = PO_TYPE_YARN.equals(savedInward.getPoTypeId());
//         if (isYarn) {
//             updateYarnInventoryRecords(savedInward, dto);
//         }

//         if (isItem) {
//             updateItemInventoryRecords(savedInward, dto);
//         }

//         return savedInward;
//     }


    

// private void updateYarnInventoryRecords(PurchaseInward purchaseInward, PurchaseInwardDTO dto) {
//         List<YarnInventory> existingYarnInventories = yarnInventoryRepository.findByOrderId(purchaseInward.getPurchaseOrderId());
        
//         YarnInventory yarnInventory;
//         if (!existingYarnInventories.isEmpty()) {
//             yarnInventory = existingYarnInventories.get(0);
//         } else {
//             yarnInventory = new YarnInventory();
//             yarnInventory.setInwardTypeId(purchaseInward.getPoTypeId());
//             yarnInventory.setCompanyId(getCompanyIdFromPurchaseOrder(purchaseInward.getPurchaseOrderId()));
//             yarnInventory.setFinancialYear(calculateFinancialYear(purchaseInward.getInwardDate()));
//             yarnInventory.setOrderId(purchaseInward.getPurchaseOrderId());
//         }

//         yarnInventory.setInwardNo(purchaseInward.getInwardNo());
//         yarnInventory.setDcNo(purchaseInward.getDcNumber());
//         yarnInventory.setDcDate(purchaseInward.getDcDate());
//         yarnInventory.setInwardDate(purchaseInward.getInwardDate() != null ?
//                 purchaseInward.getInwardDate().atStartOfDay() : LocalDate.now().atStartOfDay());

//         String poNumber = getPONumberFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
//         yarnInventory.setOrderNo(poNumber != null ? poNumber : String.valueOf(purchaseInward.getPurchaseOrderId()));

//         yarnInventory.setVendorId(purchaseInward.getVendorId());

//         YarnInventory savedYarnInventory = yarnInventoryRepository.save(yarnInventory);

//         if (dto.getPurchaseInwardItemsDtl() != null) {
//             for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
//                 if (itemDto.getYarnStockDetails() == null || itemDto.getYarnStockDetails().isEmpty()) {
//                     continue;
//                 }

//                 for (YarnStockDetailDTO stockDto : itemDto.getYarnStockDetails()) {
//                     if (stockDto.getItemId() == null) {
//                         continue;
//                     }

//                     List<YarnInwardStockDetails> existingDetails = yarnInwardStockDetailsRepository.findByYarnInventoryAndItemIdAndLotNo(savedYarnInventory, stockDto.getItemId(), stockDto.getLotNo());
//                     logger.info("Updating yarn stock detail - ItemId: {}, LotNo: {}, AcceptedQty: {}, Found existing records: {}", 
//                         stockDto.getItemId(), stockDto.getLotNo(), stockDto.getAcceptedQty(), !existingDetails.isEmpty());
                    
//                     if (!existingDetails.isEmpty()) {
//                         YarnInwardStockDetails detail = existingDetails.get(0);

//                         detail.setInwardTypeId(purchaseInward.getPoTypeId());
//                         detail.setItemId(stockDto.getItemId());
//                         Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForYarn(stockDto.getItemId());
//                         detail.setUomId(resolvedUomId);
//                         detail.setMillId(itemDto.getMillNameId());
//                         detail.setLotNo(stockDto.getLotNo());
//                         detail.setInwardQty(stockDto.getInwardQty());
//                         detail.setInwardBags(stockDto.getInwardBags());
//                         detail.setKgsPerBag(stockDto.getKgsPerBag());
//                         detail.setConesPerBag(stockDto.getConesPerBag());
//                         detail.setConesWeight(stockDto.getConesWeight());
//                         detail.setAcceptedQty(stockDto.getAcceptedQty());
//                         detail.setAvailableQty(stockDto.getAcceptedQty());
//                         detail.setAvailableBags(stockDto.getInwardBags());
//                         detail.setRejectedQty(stockDto.getRejectedQty());
//                         detail.setWarehouseId(stockDto.getWarehouseId());
//                         detail.setOrderItemId(itemDto.getPoItemId());
//                         detail.setRemark(stockDto.getRemark());

//                         yarnInwardStockDetailsRepository.save(detail);
//                         logger.info("Successfully updated YarnInwardStockDetails ID: {} with acceptedQty: {}", detail.getId(), stockDto.getAcceptedQty());
//                     } else {
//                         logger.warn("No existing yarn stock detail found for ItemId: {}, LotNo: {}, skipping update", stockDto.getItemId(), stockDto.getLotNo());
//                     }
//                 }
//             }
//         }
//     }

//     private void updateItemInventoryRecords(PurchaseInward purchaseInward, PurchaseInwardDTO dto) {
//         List<ItemInventory> existingItemInventories = itemInventoryRepository.findByOrderId(purchaseInward.getPurchaseOrderId());
        
//         ItemInventory itemInventory;
//         if (!existingItemInventories.isEmpty()) {
//             itemInventory = existingItemInventories.get(0);
//         } else {
//             itemInventory = new ItemInventory();
//             itemInventory.setOrderItemId(purchaseInward.getPoTypeId());
//             itemInventory.setCompanyId(getCompanyIdFromPurchaseOrder(purchaseInward.getPurchaseOrderId()));
//             itemInventory.setFinancialYear(calculateFinancialYear(purchaseInward.getInwardDate()));
//             itemInventory.setOrderId(purchaseInward.getPurchaseOrderId());
//         }

//         itemInventory.setInwardNo(purchaseInward.getInwardNo());
//         itemInventory.setDcNo(purchaseInward.getDcNumber());
//         itemInventory.setDcDate(purchaseInward.getDcDate());
//         itemInventory.setInwardDate(purchaseInward.getInwardDate() != null ?
//                 purchaseInward.getInwardDate().atStartOfDay() : LocalDate.now().atStartOfDay());

//         String poNumber = getPONumberFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
//         itemInventory.setOrderNo(poNumber != null ? poNumber : String.valueOf(purchaseInward.getPurchaseOrderId()));

//         itemInventory.setVendorId(purchaseInward.getVendorId());

//         ItemInventory savedItemInventory = itemInventoryRepository.save(itemInventory);

//         if (dto.getPurchaseInwardItemsDtl() != null) {
//             for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
//                 if (itemDto.getItemStockDetails() == null || itemDto.getItemStockDetails().isEmpty()) {
//                     continue;
//                 }

//                 for (ItemInwardStockDetailsDto stockDto : itemDto.getItemStockDetails()) {
//                     // Get itemId from PurchaseOrderItem if not provided in stockDto
//                     Long itemId = stockDto.getItemId();
//                     if (itemId == null && itemDto.getPoItemId() != null) {
//                         itemId = getItemIdFromPurchaseOrderItem(itemDto.getPoItemId());
//                     }
//                     if (itemId == null) {
//                         continue;
//                     }

//                     List<ItemInwardStockDetails> existingDetails = itemInwardStockDetailsRepository.findByItemInventoryAndItemIdAndLotNo(savedItemInventory, itemId, stockDto.getLotNo());
//                     logger.info("Updating item stock detail - ItemId: {}, LotNo: {}, AcceptedQty: {}, Found existing records: {}", 
//                         itemId, stockDto.getLotNo(), stockDto.getAcceptedQty(), !existingDetails.isEmpty());
                    
//                     if (!existingDetails.isEmpty()) {
//                         ItemInwardStockDetails detail = existingDetails.get(0);

//                         detail.setOrderItemId(purchaseInward.getPoTypeId());
//                         detail.setItemId(itemId);
//                         Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForItem(itemId);
//                         detail.setUomId(resolvedUomId);
//                         detail.setMillId(itemDto.getMillNameId());
//                         detail.setLotNo(stockDto.getLotNo());
//                         detail.setInwardQty(stockDto.getInwardQty());
//                         detail.setInwardBags(stockDto.getInwardBags());
//                         detail.setKgsPerBag(stockDto.getKgsPerBag());
//                         detail.setConesPerBag(stockDto.getConesPerBag());
//                         detail.setConesWeight(stockDto.getConesWeight());
//                         detail.setAcceptedQty(stockDto.getAcceptedQty());
//                         detail.setAvailableQty(stockDto.getAcceptedQty());
//                         detail.setAvailableBags(stockDto.getInwardBags());
//                         detail.setRejectedQty(stockDto.getRejectedQty());
//                         detail.setWarehouseId(stockDto.getWarehouseId());
//                         detail.setRemark(stockDto.getRemark());

//                         itemInwardStockDetailsRepository.save(detail);
//                         logger.info("Successfully updated ItemInwardStockDetails ID: {} with acceptedQty: {}", detail.getId(), stockDto.getAcceptedQty());
//                     } else {
//                         logger.warn("No existing item stock detail found for ItemId: {}, LotNo: {}, skipping update", stockDto.getItemId(), stockDto.getLotNo());
//                     }
//                 }
//             }
//         }
//     }
    
//     /**
//      * Delete purchase inward safely (generic)
//      */
//     // public void deletePurchaseInward(Long id) {
//     //     PurchaseInward inward = getPurchaseInwardById(id);
//     //     checkPurchaseInwardReferences(inward);
        
//     //     try {
//     //         inwardRepository.deleteById(id);
//     //     } catch (DataIntegrityViolationException e) {
//     //         throw new IllegalStateException("Cannot delete Purchase Inward because it is referenced by other entities");
//     //     }
//     // }

//     /**
//      * Delete purchase inward by company (from old code)
//      */
// //    @Transactional
// //    public void deletePurchaseInwardByCompany(Long id, Long companyId) {
// //        validateCompanyId(companyId);
// //        
// //        PurchaseInward existing = getPurchaseInwardByIdAndCompany(id, companyId);
// //        checkPurchaseInwardReferences(existing);
// //        
// //        try {
// //            inwardRepository.delete(existing);
// //        } catch (DataIntegrityViolationException e) {
// //            throw new IllegalStateException("Cannot delete Purchase Inward because it is referenced by other entities");
// //        }
// //    }

//     // ============ HELPER METHODS ============

//     /**
//      * Convert PurchaseInward entity to DTO (merged from old and new)
//      */
//     private PurchaseInwardDTO convertToDto(PurchaseInward inward) {
//         PurchaseInwardDTO dto = new PurchaseInwardDTO(inward);
        
//         // Ensure DC fields are mapped
//         dto.setDcDate(inward.getDcDate());
//         dto.setDcNumber(inward.getDcNumber());
//         dto.setPurchaseInwardNo(inward.getInwardNo());
//         dto.setCompanyId(inward.getCompanyId());
        
//         // Derive fabric code if missing
//         if (dto.getFabricCode() == null && inward.getItems() != null && !inward.getItems().isEmpty()) {
//             String fabricCode = getFabricCodeFromPurchaseOrderItems(inward.getItems());
//             dto.setFabricCode(fabricCode);
//         }
        
//         // Add yarn stock details if applicable (from new code)
//         if (PO_TYPE_YARN.equals(inward.getPoTypeId()) && inward.getItems() != null) {
//             List<PurchaseInwardItemDTO> itemDTOs = new ArrayList<>();
            
//             for (PurchaseInwardItem item : inward.getItems()) {
//                 List<YarnStockDetailDTO> yarnStockDetails = null;
                
//                 if (inward.getPurchaseOrderId() != null) {
//                     List<YarnInwardStockDetails> yarnDetails = yarnInwardStockDetailsRepository.findByPurchaseOrderId(inward.getPurchaseOrderId());
//                     if (!yarnDetails.isEmpty()) {
//                         yarnStockDetails = yarnDetails.stream()
//                             .map(detail -> {
//                                 YarnStockDetailDTO stockDto = new YarnStockDetailDTO();
//                                 stockDto.setId(detail.getId());
//                                 stockDto.setInwardId(detail.getInwardId());
//                                 stockDto.setItemId(detail.getItemId());
//                                 stockDto.setOrderItemId(detail.getOrderItemId());
//                                 stockDto.setInwardTypeId(detail.getInwardTypeId());
//                                 stockDto.setUomId(detail.getUomId());
//                                 stockDto.setLotNo(detail.getLotNo());
//                                 stockDto.setInwardQty(detail.getInwardQty());
//                                 stockDto.setInwardBags(detail.getInwardBags());
//                                 stockDto.setKgsPerBag(detail.getKgsPerBag());
//                                 stockDto.setConesPerBag(detail.getConesPerBag());
//                                 stockDto.setConesWeight(detail.getConesWeight());
//                                 stockDto.setAcceptedQty(detail.getAcceptedQty());
//                                 stockDto.setAvailableQty(detail.getAvailableQty());
//                                 stockDto.setRejectedQty(detail.getRejectedQty());
//                                 stockDto.setWarehouseId(detail.getWarehouseId());
//                                 stockDto.setRemark(detail.getRemark());

//                                 Long yarnId = getYarnIdFromPurchaseOrderItem(item.getPurchaseOrderItemId());
//                                 if (yarnId != null) {
//                                     Optional<YarnMaster> yarnMaster = yarnMasterRepository.findById(yarnId);
//                                     if (yarnMaster.isPresent()) {
//                                         stockDto.setYarnCode(yarnMaster.get().getYarnName());
//                                     }
//                                 }
                                
//                                 return stockDto;
//                             })
//                             .collect(Collectors.toList());
//                     }
//                 }
                
//                 PurchaseInwardItemDTO itemDTO = new PurchaseInwardItemDTO(item);
//                 if (yarnStockDetails != null && !yarnStockDetails.isEmpty()) {
//                     itemDTO.setYarnStockDetails(yarnStockDetails);
//                 }
                
//                 itemDTOs.add(itemDTO);
//             }
            
//             dto.setPurchaseInwardItemsDtl(itemDTOs);
//         }

//         // Add item stock details if applicable
//         if (PO_TYPE_ITEM.equals(inward.getPoTypeId()) && inward.getItems() != null) {
//             List<PurchaseInwardItemDTO> itemDTOs = new ArrayList<>();
            
//             for (PurchaseInwardItem item : inward.getItems()) {
//                 List<ItemInwardStockDetailsDto> itemStockDetails = null;
                
//                 if (inward.getPurchaseOrderId() != null) {
//                     List<ItemInwardStockDetails> itemDetails = itemInwardStockDetailsRepository.findByPurchaseOrderId(inward.getPurchaseOrderId());
//                     if (!itemDetails.isEmpty()) {
//                         itemStockDetails = itemDetails.stream()
//                             .map(detail -> {
//                                 ItemInwardStockDetailsDto stockDto = new ItemInwardStockDetailsDto(detail);
//                                 stockDto.setItemInwardItemId(item.getPurchaseOrderItemId());

//                                 if (detail.getItemId() != null) {
//                                     Optional<ItemMaster> itemMaster = itemMasterRepository.findById(detail.getItemId());
//                                     if (itemMaster.isPresent()) {
//                                         stockDto.setItemName(itemMaster.get().getItemName());
//                                     }
//                                 }
                                
//                                 return stockDto;
//                             })
//                             .collect(Collectors.toList());
//                     }
//                 }
                
//                 PurchaseInwardItemDTO itemDTO = new PurchaseInwardItemDTO(item);
//                 if (itemStockDetails != null && !itemStockDetails.isEmpty()) {
//                     itemDTO.setItemStockDetails(itemStockDetails);
//                 }
                
//                 itemDTOs.add(itemDTO);
//             }
            
//             dto.setPurchaseInwardItemsDtl(itemDTOs);
//         }
        
//         return dto;
//     }

//     private boolean matchesFabricType(PurchaseInwardDTO dto, Long fabricTypeId) {
//         if (dto == null || dto.getPurchaseInwardItemsDtl() == null) {
//             return false;
//         }
        
//         for (PurchaseInwardItemDTO item : dto.getPurchaseInwardItemsDtl()) {
//             if (item.getPieceEntries() == null) {
//                 continue;
//             }
//             for (PieceEntryDTO piece : item.getPieceEntries()) {
//                 if (piece.getFabricTypeId() != null && piece.getFabricTypeId().equals(fabricTypeId)) {
//                     return true;
//                 }
//             }
//         }
        
//         return false;
//     }

//     /**
//      * Helper method to get PO Type name from ID (from old code)
//      */
//     private String getPoTypeName(Long poTypeId) {
//         if (PO_TYPE_YARN.equals(poTypeId)) {
//             return "Yarn";
//         } else if (PO_TYPE_KNITTED_FABRIC.equals(poTypeId)) {
//             return "Knitted Fabric";
//         } else if (PO_TYPE_WOVEN_FABRIC.equals(poTypeId)) {
//             return "Woven Fabric";
//         } else if (PO_TYPE_ITEM.equals(poTypeId)) {
//             return "Item";
//         }
//         return "Unknown";
//     }

//     private PieceEntry mapPieceEntryDtoToEntity(PieceEntry existingPiece, PieceEntryDTO pieceDto, PurchaseInwardItem parentItem,
//                                                 String poTypeName, String purchaseOrderNo, Long defaultCompanyId) {
//         PieceEntry piece = existingPiece != null ? existingPiece : new PieceEntry();

//         piece.setPurchaseInwardItem(parentItem);
//         if (parentItem != null && parentItem.getPurchaseInward() != null) {
//             piece.setInwardDate(parentItem.getPurchaseInward().getInwardDate());
//         }
//         piece.setSourceType(SOURCE_PURCHASE_INWARD);
//         piece.setPoType(poTypeName);
//         piece.setOrderNo(purchaseOrderNo);
//         piece.setCompanyId(pieceDto.getCompanyId() != null ? pieceDto.getCompanyId() : defaultCompanyId);

//         if (pieceDto.getPieceNumber() != null) {
//             piece.setPieceNumber(pieceDto.getPieceNumber());
//         }
//         piece.setQuantity(pieceDto.getQuantity());
//         piece.setWeight(pieceDto.getWeight());
//         piece.setCost(pieceDto.getCost());
//         piece.setRemarks(pieceDto.getRemarks());
//         Long fabricTypeId = pieceDto.getFabricTypeId();
//         if (fabricTypeId == null && parentItem != null && parentItem.getPurchaseOrderItemId() != null) {
//             fabricTypeId = getFabricTypeIdFromPurchaseOrderItem(parentItem.getPurchaseOrderItemId());
//         }
//         piece.setFabricTypeId(fabricTypeId);
//         piece.setFabricMasterId(pieceDto.getFabricMasterId());
//         piece.setIsFactoryFabric(pieceDto.getIsFactoryFabric());
//         piece.setIsLastPiece(pieceDto.getIsLastPiece());
//         piece.setLoomId(pieceDto.getLoomId());
//         piece.setBeamNo(pieceDto.getBeamNo());
//         piece.setFlangeId(pieceDto.getFlangeId());
//         piece.setDoffingPicks(pieceDto.getDoffingPicks());
//         piece.setDoffingDate(pieceDto.getDoffingDate());
//         piece.setDoffingNo(pieceDto.getDoffingNo());
//         piece.setPieceMeter(pieceDto.getPieceMeter());
//         piece.setActiveFlag(pieceDto.getActiveFlag() != null ? pieceDto.getActiveFlag() : (piece.getActiveFlag() != null ? piece.getActiveFlag() : true));
//         piece.setFabricId(pieceDto.getFabricId());
//         piece.setInspectionStatus(pieceDto.getInspectionStatus() != null ? pieceDto.getInspectionStatus() : (piece.getInspectionStatus() != null ? piece.getInspectionStatus() : "PENDING"));
//         piece.setIsInspected(pieceDto.getIsInspected());
//         piece.setInspectionId(pieceDto.getInspectionId());
//         piece.setGradeId(pieceDto.getGradeId());
//         piece.setInspectedMtr(pieceDto.getInspectedMtr());
//         piece.setInspectedNtWt(pieceDto.getInspectedNtWt());
//         piece.setInspectedAvgWt(pieceDto.getInspectedAvgWt());
//         piece.setDefectType(pieceDto.getDefectType());
//         piece.setDefectPoints(pieceDto.getDefectPoints());
//         piece.setPieceNo(pieceDto.getPieceNo());
//         piece.setPiecePrefix(pieceDto.getPiecePrefix());
//         piece.setInspectorId(pieceDto.getInspectorId());
//         piece.setIsPacked(pieceDto.getIsPacked());
//         piece.setPackingId(pieceDto.getPackingId());
//         piece.setIsDispatched(pieceDto.getIsDispatched());
//         piece.setDispatchId(pieceDto.getDispatchId());
//         piece.setIsInvoice(pieceDto.getIsInvoice());
//         piece.setInvoiceId(pieceDto.getInvoiceId());
//         piece.setOrderNo(purchaseOrderNo);
//         piece.setLoomId(pieceDto.getLoomId());
//         piece.setBeamId(pieceDto.getBeamId());
//         piece.setDoffingPicks(pieceDto.getDoffingPicks());
//         piece.setCompanyId(pieceDto.getCompanyId() != null ? pieceDto.getCompanyId() : defaultCompanyId);
//         piece.setPieceMeter(pieceDto.getPieceMeter());
//         piece.setIsFactoryFabric(pieceDto.getIsFactoryFabric());
//         piece.setParentPieceId(pieceDto.getParentPieceId());
//         piece.setJoinedPieceId(pieceDto.getJoinedPieceId());
//         piece.setIsParentPiece(pieceDto.getIsParentPiece());
//         piece.setIsJoined(pieceDto.getIsJoined());
//         piece.setOperationId(pieceDto.getOperationId());
//         piece.setModeId(pieceDto.getModeId());
//         piece.setOrderTypeId(pieceDto.getOrderTypeId());
//         piece.setIsStockTransfer(pieceDto.getIsStockTransfer());
//         piece.setStockTransferId(pieceDto.getStockTransferId());

//         if (pieceDto.getWarehouseId() != null) {
//             Warehouse warehouse = warehouseRepository.findById(pieceDto.getWarehouseId())
//                     .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + pieceDto.getWarehouseId()));
//             piece.setWarehouse(warehouse);
//         } else if (pieceDto.getWarehouseId() == null && existingPiece == null) {
//             piece.setWarehouse(null);
//         }

//         return piece;
//     }

//     private Long normalizeId(Long id) {
//         return (id == null || id <= 0) ? null : id;
//     }

//     private String getFabricCodeFromPurchaseOrderItem(Long poItemId) {
//         if (poItemId == null || poItemId <= 0) return null;
//         Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
//         return poItem.map(PurchaseOrderItem::getFabricCode).orElse(null);
//     }

//     private Long getFabricTypeIdFromPurchaseOrderItem(Long poItemId) {
//         if (poItemId == null || poItemId <= 0) return null;
//         Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
//         return poItem.map(PurchaseOrderItem::getFabricTypeId).orElse(null);
//     }

//     private Long getMillNameIdFromPurchaseOrderItem(Long poItemId) {
//         if (poItemId == null || poItemId <= 0) return null;
//         Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
//         Long millNameId = poItem.map(PurchaseOrderItem::getMillNameId).orElse(null);
//         logger.info("Fetched PurchaseOrderItem ID: {}, millNameId: {}", poItemId, millNameId);
//         return millNameId;
//     }

//     private Long getYarnIdFromPurchaseOrderItem(Long poItemId) {
//         if (poItemId == null || poItemId <= 0) return null;
//         Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
//         return poItem.map(PurchaseOrderItem::getYarnId).orElse(null);
//     }

//     private Long getItemIdFromPurchaseOrderItem(Long poItemId) {
//         if (poItemId == null || poItemId <= 0) return null;
//         Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
//         return poItem.map(PurchaseOrderItem::getItemId).orElse(null);
//     }

//     private String getPurchaseOrderNo(Long purchaseOrderId) {
//         if (purchaseOrderId == null) {
//             return null;
//         }
        
//         Optional<PurchaseOrder> po = purchaseOrderRepository.findById(purchaseOrderId);
//         if (po.isPresent()) {
//             return po.get().getPoNo();
//         }
//         return null;
//     }

//     private String getFabricCodeFromPurchaseOrderItems(List<PurchaseInwardItem> items) {
//         for (PurchaseInwardItem item : items) {
//             if (item.getPurchaseOrderItemId() != null) {
//                 String fabricCode = getFabricCodeFromPurchaseOrderItem(item.getPurchaseOrderItemId());
//                 if (fabricCode != null) return fabricCode;
//             }
//         }
//         return null;
//     }

//     private void validateCompanyId(Long companyId) {
//         if (companyId == null || companyId <= 0) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//     }

//     // ============ REMAINING METHODS FROM NEW CODE ============
    
//     /**
//      * Create yarn inventory records for yarn purchase inward via yarn stock details.
//      */
//     private void createYarnInventoryRecords(PurchaseInward purchaseInward, PurchaseInwardDTO dto) {
//         YarnInventory yarnInventory = new YarnInventory();

//         yarnInventory.setInwardTypeId(purchaseInward.getPoTypeId());

//         Long companyId = getCompanyIdFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
//         yarnInventory.setCompanyId(companyId);
//         yarnInventory.setInwardDate(purchaseInward.getInwardDate() != null ?
//                 purchaseInward.getInwardDate().atStartOfDay() : LocalDate.now().atStartOfDay());

//         LocalDate inwardDate = purchaseInward.getInwardDate();

//         String financialYear = calculateFinancialYear(inwardDate);
//         yarnInventory.setFinancialYear(financialYear);

//         yarnInventory.setInwardNo(purchaseInward.getInwardNo());
//         yarnInventory.setDcNo(purchaseInward.getDcNumber());
//         yarnInventory.setDcDate(purchaseInward.getDcDate());
//         yarnInventory.setOrderId(purchaseInward.getPurchaseOrderId());

//         String poNumber = getPONumberFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
//         yarnInventory.setOrderNo(poNumber != null ? poNumber : String.valueOf(purchaseInward.getPurchaseOrderId()));

//         yarnInventory.setVendorId(purchaseInward.getVendorId());

//         YarnInventory savedYarnInventory = yarnInventoryRepository.save(yarnInventory);

//         List<YarnInwardStockDetails> details = new ArrayList<>();

//         if (dto.getPurchaseInwardItemsDtl() != null) {
//             for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
//                 if (itemDto.getYarnStockDetails() == null || itemDto.getYarnStockDetails().isEmpty()) {
//                     continue;
//                 }

//                 for (YarnStockDetailDTO stockDto : itemDto.getYarnStockDetails()) {
//                     if (stockDto.getItemId() == null) {
//                         continue;
//                     }

//                     YarnInwardStockDetails detail = new YarnInwardStockDetails();

//                     detail.setInwardTypeId(purchaseInward.getPoTypeId());
//                     detail.setItemId(stockDto.getItemId());
//                     Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForYarn(stockDto.getItemId());
//                     detail.setUomId(resolvedUomId);
//                     detail.setMillId(itemDto.getMillNameId());
//                     detail.setLotNo(stockDto.getLotNo());
//                     detail.setInwardQty(stockDto.getInwardQty());
//                     detail.setInwardBags(stockDto.getInwardBags());
//                     detail.setKgsPerBag(stockDto.getKgsPerBag());
//                     detail.setConesPerBag(stockDto.getConesPerBag());
//                     detail.setConesWeight(stockDto.getConesWeight());
//                     detail.setAcceptedQty(stockDto.getAcceptedQty());
//                     detail.setAvailableQty(stockDto.getAcceptedQty());
//                     detail.setAvailableBags(stockDto.getInwardBags());
//                     detail.setRejectedQty(stockDto.getRejectedQty());
//                     detail.setWarehouseId(stockDto.getWarehouseId());
//                     detail.setOrderItemId(itemDto.getPoItemId());
//                     detail.setRemark(stockDto.getRemark());
//                     detail.setYarnInventory(savedYarnInventory);

//                     details.add(detail);
//                 }
//             }
//         }

//         if (!details.isEmpty()) {
//             savedYarnInventory.setDetails(details);
//             yarnInventoryRepository.save(savedYarnInventory);
//         }
//     }

//     /**
//      * Create item inventory records for item purchase inward via item stock details.
//      */
//     private void createItemInventoryRecords(PurchaseInward purchaseInward, PurchaseInwardDTO dto) {
//         ItemInventory itemInventory = new ItemInventory();

//         itemInventory.setOrderItemId(purchaseInward.getPoTypeId());

//         Long companyId = getCompanyIdFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
//         itemInventory.setCompanyId(companyId);
//         itemInventory.setInwardDate(purchaseInward.getInwardDate() != null ?
//                 purchaseInward.getInwardDate().atStartOfDay() : LocalDate.now().atStartOfDay());

//         LocalDate inwardDate = purchaseInward.getInwardDate();

//         String financialYear = calculateFinancialYear(inwardDate);
//         itemInventory.setFinancialYear(financialYear);

//         itemInventory.setInwardNo(generateItemInwardNo(companyId, inwardDate));
//         itemInventory.setDcNo(purchaseInward.getDcNumber());
//         itemInventory.setDcDate(purchaseInward.getDcDate());
//         itemInventory.setOrderId(purchaseInward.getPurchaseOrderId());

//         String poNumber = getPONumberFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
//         itemInventory.setOrderNo(poNumber != null ? poNumber : String.valueOf(purchaseInward.getPurchaseOrderId()));

//         itemInventory.setVendorId(purchaseInward.getVendorId());

//         ItemInventory savedItemInventory = itemInventoryRepository.save(itemInventory);

//         List<ItemInwardStockDetails> details = new ArrayList<>();

//         if (dto.getPurchaseInwardItemsDtl() != null) {
//             for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
//                 if (itemDto.getItemStockDetails() == null || itemDto.getItemStockDetails().isEmpty()) {
//                     continue;
//                 }

//                 for (ItemInwardStockDetailsDto stockDto : itemDto.getItemStockDetails()) {
//                     // Get itemId from PurchaseOrderItem if not provided in stockDto
//                     Long itemId = stockDto.getItemId();
//                     if (itemId == null && itemDto.getPoItemId() != null) {
//                         itemId = getItemIdFromPurchaseOrderItem(itemDto.getPoItemId());
//                     }
//                     if (itemId == null) {
//                         continue;
//                     }

//                     ItemInwardStockDetails detail = new ItemInwardStockDetails();

//                     detail.setOrderItemId(purchaseInward.getPoTypeId());
//                     detail.setItemId(itemId);
//                     Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForItem(itemId);
//                     detail.setUomId(resolvedUomId);
//                     detail.setMillId(itemDto.getMillNameId());
//                     detail.setLotNo(stockDto.getLotNo());
//                     detail.setInwardQty(stockDto.getInwardQty());
//                     detail.setInwardBags(stockDto.getInwardBags());
//                     detail.setKgsPerBag(stockDto.getKgsPerBag());
//                     detail.setConesPerBag(stockDto.getConesPerBag());
//                     detail.setConesWeight(stockDto.getConesWeight());
//                     detail.setAcceptedQty(stockDto.getAcceptedQty());
//                     detail.setAvailableQty(stockDto.getAcceptedQty());
//                     detail.setAvailableBags(stockDto.getInwardBags());
//                     detail.setRejectedQty(stockDto.getRejectedQty());
//                     detail.setWarehouseId(stockDto.getWarehouseId());
//                     detail.setRemark(stockDto.getRemark());
//                     detail.setItemInventory(savedItemInventory);

//                     details.add(detail);
//                 }
//             }
//         }

//         if (!details.isEmpty()) {
//             savedItemInventory.setDetails(details);
//             itemInventoryRepository.save(savedItemInventory);
//         }
//     }

//     /**
//      * Calculate financial year based on the date (Indian FY: Apr-Mar).
//      * Returns the fiscal year as String (e.g., "2024-25" for FY 2024-25).
//      */
//     private String calculateFinancialYear(LocalDate date) {
//         if (date == null) {
//             date = LocalDate.now();
//         }
        
//         int year = date.getYear();
//         int month = date.getMonthValue();
        
//         // Indian financial year: April to March
//         // If month is April (4) or later, FY is currentYear-nextYear
//         // If month is January-March, FY is previousYear-currentYear
//         if (month >= 4) {
//             // FY: currentYear - nextYear (e.g., 2024-25 for dates between Apr 2024 - Mar 2025)
//             int nextYear = year + 1;
//             return String.format("%d-%d", year, nextYear % 100);
//         } else {
//             // FY: previousYear - currentYear (e.g., 2023-24 for dates between Jan 2024 - Mar 2024)
//             int previousYear = year - 1;
//             return String.format("%d-%d", previousYear, year % 100);
//         }
//     }

//     /**
//      * Generate yarn inward number (YI/CMP/FY/0001 format).
//      */
//     private String generateYarnInwardNo(Long companyId, LocalDate inwardDate) {
//         var company = companyRepository.findById(companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
//         String cmpCode = company.getCompanyCode();

//         String fy = calculateFinancialYear(inwardDate);
//         String prefix = "YI/" + cmpCode + "/" + fy + "/";

//         Integer maxNum = yarnInventoryRepository.findMaxOrderNumberByPrefix(prefix, companyId);
//         int nextNum = (maxNum != null ? maxNum : 0) + 1;

//         return prefix + String.format("%04d", nextNum);
//     }

//     /**
//      * Generate item inward number (II/CMP/FY/0001 format).
//      */
//     private String generateItemInwardNo(Long companyId, LocalDate inwardDate) {
//         var company = companyRepository.findById(companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
//         String cmpCode = company.getCompanyCode();

//         String fy = calculateFinancialYear(inwardDate);
//         String prefix = "II/" + cmpCode + "/" + fy + "/";

//         Integer maxNum = itemInventoryRepository.findMaxOrderNumberByPrefix(prefix, companyId);
//         int nextNum = (maxNum != null ? maxNum : 0) + 1;

//         return prefix + String.format("%04d", nextNum);
//     }

//     /**
//      * Helper: Fetch company ID from purchase order.
//      */
//     private Long getCompanyIdFromPurchaseOrder(Long purchaseOrderId) {
//         if (purchaseOrderId == null || purchaseOrderId <= 0) return null;
//         Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
//         return purchaseOrder.map(PurchaseOrder::getCompanyId).orElse(null);
//     }

//     /**
//      * Helper: Fetch PO number from purchase order.
//      * Since getOrderNo() is not available, we'll use the ID to generate a PO number
//      */
//     private String getPONumberFromPurchaseOrder(Long purchaseOrderId) {
//         if (purchaseOrderId == null || purchaseOrderId <= 0) return null;
//         Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
//         if (purchaseOrder.isPresent()) {
//             PurchaseOrder po = purchaseOrder.get();
            
//             // Try to find if there's any field that contains the PO number
//             // Check common field names in your PurchaseOrder entity
//             String poNumber = null;
            
//             // Option 1: Check if there's a poNumber field
//             try {
//                 java.lang.reflect.Method getPoNumber = po.getClass().getMethod("getPoNumber");
//                 poNumber = (String) getPoNumber.invoke(po);
//             } catch (Exception e) {
//                 // Method doesn't exist, continue
//             }
            
//             // Option 2: Check if there's a purchaseOrderNo field
//             if (poNumber == null) {
//                 try {
//                     java.lang.reflect.Method getPurchaseOrderNo = po.getClass().getMethod("getPurchaseOrderNo");
//                     poNumber = (String) getPurchaseOrderNo.invoke(po);
//                 } catch (Exception e) {
//                     // Method doesn't exist, continue
//                 }
//             }
            
//             // Option 3: Check if there's a orderNumber field
//             if (poNumber == null) {
//                 try {
//                     java.lang.reflect.Method getOrderNumber = po.getClass().getMethod("getOrderNumber");
//                     poNumber = (String) getOrderNumber.invoke(po);
//                 } catch (Exception e) {
//                     // Method doesn't exist, continue
//                 }
//             }
            
//             // If we found a PO number field, return it
//             if (poNumber != null) {
//                 return poNumber;
//             }
            
//             // Option 4: Fallback - Generate a formatted PO number based on ID and company
//             String financialYear = calculateFinancialYear(LocalDate.now());
//             return String.format("PO/%s/%s/%04d", 
//                 getCompanyCodeFromPurchaseOrder(purchaseOrderId),
//                 financialYear, 
//                 po.getId());
//         }
//         return null;
//     }

//     /**
//      * Helper: Fetch company code from purchase order.
//      */
//     private String getCompanyCodeFromPurchaseOrder(Long purchaseOrderId) {
//         if (purchaseOrderId == null || purchaseOrderId <= 0) return "CMP";
//         Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
//         if (purchaseOrder.isPresent()) {
//             Long companyId = purchaseOrder.get().getCompanyId();
//             if (companyId != null) {
//                 Optional<com.tech3.erp.entity.Company> company = companyRepository.findById(companyId);
//                 return company.map(com.tech3.erp.entity.Company::getCompanyCode).orElse("CMP");
//             }
//         }
//         return "CMP";
//     }

//     //    private void checkPurchaseInwardReferences(Long purchaseInwardId) {
//     //     List<PurchaseInwardItem> inwardItems = purchaseInwardItemRepository.findByPurchaseInwardId(purchaseInwardId);
        
//     //     if (inwardItems != null && !inwardItems.isEmpty()) {
//     //         for (PurchaseInwardItem item : inwardItems) {
//     //             Long pieceEntryCount = pieceEntryRepository.countByPurchaseInwardItemId(item.getId());
//     //             if (pieceEntryCount > 0) {
//     //                 throw new IllegalStateException("The data is already exceeded to other modules - cannot perform operation.");
//     //             }
//     //         }
//     //     }
//     // }
    
//     public boolean canDeletePurchaseInward(Long purchaseInwardId) {
//         List<PurchaseInwardItem> inwardItems = purchaseInwardItemRepository.findByPurchaseInwardId(purchaseInwardId);
        
//         if (inwardItems != null && !inwardItems.isEmpty()) {
//             for (PurchaseInwardItem item : inwardItems) {
//                 Long pieceEntryCount = pieceEntryRepository.countByPurchaseInwardItemId(item.getId());
//                 if (pieceEntryCount > 0) {
//                     return false;
//                 }
//             }
//         }
        
//         return true;
//     }
    
//     public java.util.Map<Long, Boolean> canDeletePurchaseInwardItems(Long purchaseInwardId) {
//         java.util.Map<Long, Boolean> itemsDeletionStatus = new java.util.HashMap<>();
        
//         PurchaseInward inward = inwardRepository.findById(purchaseInwardId)
//                 .orElseThrow(() -> new EntityNotFoundException("Purchase Inward not found with id: " + purchaseInwardId));
        
//         List<PurchaseInwardItem> inwardItems = inward.getItems();
        
//         if (inwardItems != null && !inwardItems.isEmpty()) {
//             for (PurchaseInwardItem item : inwardItems) {
//                 boolean canDelete = true;
                
//                 Long pieceEntryCount = pieceEntryRepository.countByPurchaseInwardItemId(item.getId());
//                 if (pieceEntryCount > 0) {
//                     canDelete = false;
//                 }
                
//                 itemsDeletionStatus.put(item.getId(), canDelete);
//             }
//         }
        
//         return itemsDeletionStatus;
//     }
    
//     public boolean canEditPurchaseInward(Long purchaseInwardId) {
//         List<PurchaseInwardItem> inwardItems = purchaseInwardItemRepository.findByPurchaseInwardId(purchaseInwardId);
        
//         if (inwardItems != null && !inwardItems.isEmpty()) {
//             for (PurchaseInwardItem item : inwardItems) {
//                 Long pieceEntryCount = pieceEntryRepository.countByPurchaseInwardItemId(item.getId());
//                 if (pieceEntryCount > 0) {
//                     return false;
//                 }
//             }
//         }
        
//         return true;
//     }


//     /**
//      * Convert PurchaseInward entity to DTO with yarn stock details included.
//      */
//     public PurchaseInwardDTO convertToDetailedDTO(PurchaseInward inward) {
//         PurchaseInwardDTO dto = new PurchaseInwardDTO(inward);
        
//         if (inward.getItems() != null) {
//             List<PurchaseInwardItemDTO> itemDTOs = new ArrayList<>();
            
//             for (PurchaseInwardItem item : inward.getItems()) {
//                 List<YarnStockDetailDTO> yarnStockDetails = null;
//                 List<ItemInwardStockDetailsDto> itemStockDetails = null;
//                 // Handle Yarn Stock Details - Filter by specific PurchaseInward (inward number and order ID)
//                 List<YarnInwardStockDetails> yarnDetails = new ArrayList<>();
//                 if (item.getPurchaseOrderItemId() != null && inward.getInwardNo() != null && inward.getPurchaseOrderId() != null) {
//                     yarnDetails = yarnInwardStockDetailsRepository.findByInwardNoAndOrderIdAndOrderItemIdAndInwardTypeId(
//                         inward.getInwardNo(),
//                         inward.getPurchaseOrderId(),
//                         item.getPurchaseOrderItemId(),
//                         inward.getPoTypeId()
//                     );
//                 }
                
//                 if (!yarnDetails.isEmpty()) {
//                     yarnStockDetails = yarnDetails.stream()
//                         .map(detail -> {
//                             YarnStockDetailDTO stockDto = new YarnStockDetailDTO();
//                             stockDto.setId(detail.getId());
//                             stockDto.setInwardId(inward.getId());
//                             stockDto.setItemId(detail.getItemId());
//                             stockDto.setOrderItemId(detail.getOrderItemId());
//                             stockDto.setInwardTypeId(detail.getInwardTypeId());
//                             stockDto.setUomId(detail.getUomId());
//                             stockDto.setLotNo(detail.getLotNo());
//                             stockDto.setInwardQty(detail.getInwardQty());
//                             stockDto.setInwardBags(detail.getInwardBags());
//                             stockDto.setKgsPerBag(detail.getKgsPerBag());
//                             stockDto.setConesPerBag(detail.getConesPerBag());
//                             stockDto.setConesWeight(detail.getConesWeight());
//                             stockDto.setAcceptedQty(detail.getAcceptedQty());
//                             stockDto.setRejectedQty(detail.getRejectedQty());
//                             stockDto.setWarehouseId(detail.getWarehouseId());
//                             stockDto.setRemark(detail.getRemark());
                           
//                             Long yarnId = getYarnIdFromPurchaseOrderItem(item.getPurchaseOrderItemId());
//                             if (yarnId != null) {
//                                 Optional<YarnMaster> yarnMaster = yarnMasterRepository.findById(yarnId);
//                                 if (yarnMaster.isPresent()) {
//                                     stockDto.setYarnCode(yarnMaster.get().getYarnName());
//                                 }
//                             }
                            
//                             return stockDto;
//                         })
//                         .collect(Collectors.toList());
//                 }

//                 // Handle Item Stock Details
//                 List<ItemInwardStockDetails> itemDetails = new ArrayList<>();
//                 if (inward.getPurchaseOrderId() != null) {
//                     itemDetails = itemInwardStockDetailsRepository.findByPurchaseOrderIdAndPoTypeId(inward.getPurchaseOrderId(), inward.getPoTypeId());
//                 }
                
//                 final Long targetItemId = getItemIdFromPurchaseOrderItem(item.getPurchaseOrderItemId());
//                 if (!itemDetails.isEmpty() && targetItemId != null) {
//                     itemStockDetails = itemDetails.stream()
//                         .filter(detail -> targetItemId.equals(detail.getItemId()))
//                         .map(detail -> {
//                             ItemInwardStockDetailsDto stockDto = new ItemInwardStockDetailsDto(detail);
//                             stockDto.setItemInwardItemId(item.getPurchaseOrderItemId());

//                             if (detail.getItemId() != null) {
//                                 Optional<ItemMaster> itemMaster = itemMasterRepository.findById(detail.getItemId());
//                                 if (itemMaster.isPresent()) {
//                                     stockDto.setItemName(itemMaster.get().getItemName());
//                                 }
//                             }
                            
//                             return stockDto;
//                         })
//                         .collect(Collectors.toList());
//                 }
                
//                 PurchaseInwardItemDTO itemDTO = new PurchaseInwardItemDTO(item);
//                 if (yarnStockDetails != null && !yarnStockDetails.isEmpty()) {
//                     itemDTO.setYarnStockDetails(yarnStockDetails);
//                 } else if (itemStockDetails != null && !itemStockDetails.isEmpty()) {
//                     itemDTO.setItemStockDetails(itemStockDetails);
//                 }
                
//                 itemDTOs.add(itemDTO);
//             }
            
//             dto.setPurchaseInwardItemsDtl(itemDTOs);
//         }
        
//         return dto;
//     }


//     /**
//      * Get YarnInwardStockDetails filtered by various criteria.
//      * Supports filtering by itemId/inwardTypeId and/or purchaseOrderId/poTypeId.
//      * Calculates availableQty as acceptedQty - issuedQty for accurate stock levels.
//      */
//     public List<YarnInwardStockDetailsDto> getYarnInwardStockDetailsByFilters(Long itemId, Long inwardTypeId, Long purchaseOrderId, Long poTypeId, Long companyId) {
//         List<YarnInwardStockDetails> entities;

//         // Determine filtering strategy based on provided parameters
//         if (itemId != null && inwardTypeId != null) {
//             entities = yarnInwardStockDetailsRepository.findByItemIdAndInwardTypeId(itemId, inwardTypeId);
//         } else if (purchaseOrderId != null && poTypeId != null) {
//             entities = yarnInwardStockDetailsRepository.findByPurchaseOrderIdAndPoTypeId(purchaseOrderId, poTypeId);
//         } else if (itemId != null) {
//             entities = yarnInwardStockDetailsRepository.findByItemId(itemId);
//         } else if (purchaseOrderId != null) {
//             entities = yarnInwardStockDetailsRepository.findByPurchaseOrderId(purchaseOrderId);
//         } else {
//             entities = java.util.Collections.emptyList();
//         }

//         List<YarnInwardStockDetailsDto> results = new ArrayList<>();

//         for (YarnInwardStockDetails entity : entities) {
//             if (companyId != null) {
//                 Long detailCompanyId = entity.getYarnInventory() != null ? entity.getYarnInventory().getCompanyId() : null;
//                 if (!companyId.equals(detailCompanyId)) {
//                     continue;
//                 }
//             }

//             YarnInwardStockDetailsDto dto = new YarnInwardStockDetailsDto(entity);

//             BigDecimal acceptedQty = entity.getAcceptedQty() != null ? entity.getAcceptedQty() : BigDecimal.ZERO;
//             BigDecimal totalIssuedQty = calculateTotalIssuedQuantity(entity.getOrderItemId());
//             BigDecimal availableQty = acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
//             if (availableQty.compareTo(BigDecimal.ZERO) < 0) {
//                 availableQty = BigDecimal.ZERO;
//             }

//             BigDecimal inwardBags = entity.getInwardBags() != null ? entity.getInwardBags() : BigDecimal.ZERO;
//             BigDecimal totalIssuedBags = calculateTotalIssuedBags(entity.getOrderItemId());
//             BigDecimal availableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);
//             if (availableBags.compareTo(BigDecimal.ZERO) < 0) {
//                 availableBags = BigDecimal.ZERO;
//             }

//             dto.setAvailableQty(availableQty);
//             dto.setAvailableBags(availableBags);

//             boolean updated = false;
//             if (entity.getAvailableQty() == null || entity.getAvailableQty().compareTo(availableQty) != 0) {
//                 entity.setAvailableQty(availableQty);
//                 updated = true;
//             }
//             if (entity.getAvailableBags() == null || entity.getAvailableBags().compareTo(availableBags) != 0) {
//                 entity.setAvailableBags(availableBags);
//                 updated = true;
//             }
//             if (updated) {
//                 yarnInwardStockDetailsRepository.save(entity);
//             }

//             results.add(dto);
//         }

//         return results;
//     }

//     /**
//      * Calculate total issued quantity for a yarn inward stock detail by summing all outward stock details.
//      */
//     private BigDecimal calculateTotalIssuedQuantity(Long orderItemId) {
//         if (orderItemId == null) {
//             return BigDecimal.ZERO;
//         }
//         BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByOrderItemId(orderItemId);
//         return totalIssued != null ? totalIssued : BigDecimal.ZERO;
//     }

//     private BigDecimal calculateTotalIssuedBags(Long orderItemId) {
//         if (orderItemId == null) {
//             return BigDecimal.ZERO;
//         }
//         BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByOrderItemId(orderItemId);
//         return totalIssued != null ? totalIssued : BigDecimal.ZERO;
//     }

//     private Long resolveUomIdForYarn(Long yarnId) {
//         if (yarnId == null) {
//             return getDefaultUomId();
//         }

//         List<YarnItemEntity> yarnItems = yarnItemRepository.findByYarnId(yarnId);
//         if (!yarnItems.isEmpty() && yarnItems.get(0).getUomId() != null) {
//             return yarnItems.get(0).getUomId();
//         }

//         return getDefaultUomId();
//     }

//     private Long getDefaultUomId() {
//         List<Uom> allUoms = uomRepository.findAll();
//         if (!allUoms.isEmpty()) {
//             return allUoms.get(0).getId();
//         }
//         return null;
//     }

//     private Long resolveUomIdForItem(Long itemId) {
//         if (itemId == null) {
//             return getDefaultUomId();
//         }

//         Optional<ItemMaster> itemMaster = itemMasterRepository.findById(itemId);
//         if (itemMaster.isPresent() && itemMaster.get().getUomName() != null) {
//             Optional<Uom> uom = uomRepository.findByUomName(itemMaster.get().getUomName());
//             if (uom.isPresent()) {
//                 return uom.get().getId();
//             }
//         }

//         return getDefaultUomId();
//     }


//  /**
//      * Permanently delete a purchase inward entry and all related stock records
//      */
//     @Transactional
//     public void hardDeletePurchaseInward(Long id) {
//         PurchaseInward inward = inwardRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Purchase Inward not found with id: " + id));

//         performHardDelete(inward);
//     }

//     @Transactional
//     public void hardDeletePurchaseInwardByCompany(Long id, Long companyId) {
//         validateCompanyId(companyId);

//         PurchaseInward inward = getPurchaseInwardByIdAndCompany(id, companyId);
//         performHardDelete(inward);
//     }

//     private void performHardDelete(PurchaseInward inward) {
//         if (inward == null) {
//             throw new EntityNotFoundException("Purchase Inward not found");
//         }

//         Long inwardId = inward.getId();
//         List<PurchaseInwardItem> items = purchaseInwardItemRepository.findByPurchaseInwardId(inwardId);
//         if (items != null) {
//             for (PurchaseInwardItem item : items) {
//                 List<PieceEntry> usedPieces = pieceEntryRepository.findUsedPiecesByPurchaseInwardItemId(item.getId());
//                 if (!usedPieces.isEmpty()) {
//                     String pieceNumbers = usedPieces.stream()
//                             .map(PieceEntry::getPieceNumber)
//                             .collect(Collectors.joining(", "));
//                     throw new IllegalStateException("Cannot permanently delete: The following pieces are already inspected, packed, or invoiced: " + pieceNumbers);
//                 }
//             }
//         }

//         Optional<YarnInventory> inventoryOpt = yarnInventoryRepository.findByInwardNoAndOrderId(inward.getInwardNo(), inward.getPurchaseOrderId());
//         ensureNoLinkedYarnOrSizingIssues(items, inward, inventoryOpt);
//         inventoryOpt.ifPresent(inventory -> {
//             List<YarnInwardStockDetails> details = yarnInwardStockDetailsRepository.findByYarnInventoryId(inventory.getId());
//             for (YarnInwardStockDetails detail : details) {
//                 if (detail.getAvailableQty() != null && detail.getAcceptedQty() != null &&
//                     detail.getAvailableQty().compareTo(detail.getAcceptedQty()) < 0) {

//                     String yarnCode = "ID: " + detail.getItemId();
//                     if (detail.getItemId() != null) {
//                         yarnCode = yarnMasterRepository.findById(detail.getItemId())
//                                 .map(YarnMaster::getYarnName)
//                                 .orElse("ID: " + detail.getItemId());
//                     }

//                     throw new IllegalStateException("Cannot permanently delete: Some yarn (" + yarnCode + ") has already been issued from this inward.");
//                 }
//             }
//         });

//         if (items != null) {
//             for (PurchaseInwardItem item : items) {
//                 pieceEntryRepository.deleteByPurchaseInwardItemId(item.getId());
//             }
//         }

//         inventoryOpt.ifPresent(yarnInventoryRepository::delete);
//         approvalRepository.deleteByDataIdAndScreenName(inward.getId(), "Purchase Inward");
//         inwardRepository.delete(inward);
//     }

//     private void ensureNoLinkedYarnOrSizingIssues(List<PurchaseInwardItem> items, PurchaseInward inward, Optional<YarnInventory> inventoryOpt) {
//         String inwardReference = inward.getInwardNo() != null ? "inward no " + inward.getInwardNo() : "inward id " + inward.getId();

//         if (items != null) {
//             for (PurchaseInwardItem item : items) {
//                 Long inwardItemId = item.getId();
//                 if (inwardItemId == null) {
//                     continue;
//                 }

//                 yarnIssueRepository.findFirstByInwardItemId(inwardItemId).ifPresent(issue -> {
//                     String yarnCode = describeYarnIssue(issue);
//                     throw new IllegalStateException("Cannot permanently delete purchase inward " + inwardReference + ": Yarn issue records exist for " + yarnCode + " (inward item " + inwardItemId + ")");
//                 });

//                 sizingYarnIssueRepository.findFirstByInwardItemId(inwardItemId).ifPresent(issue -> {
//                     String yarnCode = describeSizingYarnIssue(issue);
//                     throw new IllegalStateException("Cannot permanently delete purchase inward " + inwardReference + ": Sizing yarn issue records exist for " + yarnCode + " (inward item " + inwardItemId + ")");
//                 });
//             }
//         }

//         inventoryOpt.ifPresent(inventory -> {
//             yarnIssueRepository.findFirstByInwardId(inventory.getId()).ifPresent(issue -> {
//                 String yarnCode = describeYarnIssue(issue);
//                 throw new IllegalStateException("Cannot permanently delete purchase inward " + inwardReference + ": Yarn issue records exist for " + yarnCode + " (yarn inward id " + inventory.getId() + ")");
//             });
//             sizingYarnIssueRepository.findFirstByInwardId(inventory.getId()).ifPresent(issue -> {
//                 String yarnCode = describeSizingYarnIssue(issue);
//                 throw new IllegalStateException("Cannot permanently delete purchase inward " + inwardReference + ": Sizing yarn issue records exist for " + yarnCode + " (yarn inward id " + inventory.getId() + ")");
//             });
//         });
//     }

//     private String describeYarnIssue(YarnIssue issue) {
//         if (issue == null) {
//             return "unknown yarn";
//         }
//         if (issue.getYarnId() != null) {
//             Optional<YarnMaster> yarnMaster = yarnMasterRepository.findById(issue.getYarnId());
//             if (yarnMaster.isPresent() && yarnMaster.get().getYarnName() != null) {
//                 return yarnMaster.get().getYarnName();
//             }
//             return "ID: " + issue.getYarnId();
//         }
//         if (issue.getYarnName() != null) {
//             return issue.getYarnName();
//         }
//         if (issue.getActualYarn() != null) {
//             return issue.getActualYarn();
//         }
//         return "Yarn issue #" + issue.getId();
//     }

//     private String describeSizingYarnIssue(SizingYarnIssue issue) {
//         if (issue == null) {
//             return "unknown sizing yarn";
//         }
//         if (issue.getYarnId() != null) {
//             Optional<YarnMaster> yarnMaster = yarnMasterRepository.findById(issue.getYarnId());
//             if (yarnMaster.isPresent() && yarnMaster.get().getYarnName() != null) {
//                 return yarnMaster.get().getYarnName();
//             }
//             return "ID: " + issue.getYarnId();
//         }
//         if (issue.getYarnName() != null) {
//             return issue.getYarnName();
//         }
//         if (issue.getAlternateYarn() != null) {
//             return issue.getAlternateYarn();
//         }
//         if (issue.getActualItemId() != null) {
//             Optional<ItemMaster> master = itemMasterRepository.findById(issue.getActualItemId());
//             if (master.isPresent() && master.get().getItemCode() != null) {
//                 return master.get().getItemCode();
//             }
//             return "Item ID " + issue.getActualItemId();
//         }
//         return "Sizing yarn issue #" + issue.getId();
//     }

//     private boolean hasYarnStockDetails(PurchaseInwardDTO dto) {
//         if (dto.getPurchaseInwardItemsDtl() == null) return false;
//         return dto.getPurchaseInwardItemsDtl().stream()
//                 .anyMatch(item -> item.getYarnStockDetails() != null && !item.getYarnStockDetails().isEmpty());
//     }

//     private boolean hasItemStockDetails(PurchaseInwardDTO dto) {
//         if (dto.getPurchaseInwardItemsDtl() == null) return false;
//         return dto.getPurchaseInwardItemsDtl().stream()
//                 .anyMatch(item -> item.getItemStockDetails() != null && !item.getItemStockDetails().isEmpty());
//     }
// }

package com.tech3.erp.service;

import com.tech3.erp.dto.PurchaseInwardDTO;
import com.tech3.erp.dto.PurchaseInwardItemDTO;
import com.tech3.erp.dto.YarnInwardStockDetailsDto;
import com.tech3.erp.dto.YarnStockDetailDTO;
import com.tech3.erp.dto.PieceEntryDTO;
import com.tech3.erp.dto.ItemInwardStockDetailsDto;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.PurchaseInward;
import com.tech3.erp.entity.PurchaseInwardItem;
import com.tech3.erp.entity.PurchaseOrder;
import com.tech3.erp.entity.PurchaseOrderItem;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnMaster;
import com.tech3.erp.entity.YarnIssue;
import com.tech3.erp.entity.SizingYarnIssue;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.ItemInventory;
import com.tech3.erp.entity.ItemInwardStockDetails;
import com.tech3.erp.entity.ItemMaster;
import com.tech3.erp.entity.LotEntry;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.Warehouse;
import com.tech3.erp.repository.ApprovalRepository;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.PoTypeMasterRepository;
import com.tech3.erp.repository.PurchaseInwardRepository;
import com.tech3.erp.repository.PurchaseInwardItemRepository;
import com.tech3.erp.repository.PurchaseOrderRepository;
import com.tech3.erp.repository.PurchaseOrderItemRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
import com.tech3.erp.repository.ItemInventoryRepository;
import com.tech3.erp.repository.ItemInwardStockDetailsRepository;
import com.tech3.erp.repository.ItemMasterRepository;
import com.tech3.erp.repository.WarehouseRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.FabricInspectionRepository;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.repository.YarnItemRepository;
import com.tech3.erp.repository.YarnIssueRepository;
import com.tech3.erp.repository.SizingYarnIssueRepository;
import com.tech3.erp.repository.UomRepository;
import com.tech3.erp.entity.PoTypeMaster;
import com.tech3.erp.entity.YarnItemEntity;
import com.tech3.erp.entity.Uom;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;



@Service
public class PurchaseInwardService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseInwardService.class);

    @Autowired
    private PurchaseInwardRepository inwardRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private YarnInventoryRepository yarnInventoryRepository;

    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;


    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private PurchaseInwardItemRepository purchaseInwardItemRepository;

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    @Autowired
    private YarnMasterRepository yarnMasterRepository;

    @Autowired
    private FabricInspectionService fabricInspectionService;

    @Autowired
    private FabricInspectionRepository fabricInspectionRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private YarnItemRepository yarnItemRepository;

    @Autowired
    private UomRepository uomRepository;

    @Autowired
    private YarnIssueRepository yarnIssueRepository;

    @Autowired
    private SizingYarnIssueRepository sizingYarnIssueRepository;

    @Autowired
    private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;

    @Autowired
    private ItemInventoryRepository itemInventoryRepository;

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private ItemInwardStockDetailsRepository itemInwardStockDetailsRepository;

    @Autowired
    private ItemMasterRepository itemMasterRepository;
    
    @Autowired
    private ApprovalRepository approvalRepository;

    // PO Type constants - Match po_type_master table IDs
    private static final Long PO_TYPE_YARN = 1L;
    private static final Long PO_TYPE_WOVEN_FABRIC = 2L;
    private static final Long PO_TYPE_KNITTED_FABRIC = 11L;
    private static final Long PO_TYPE_ITEM = 3L;
   
    
    @Autowired
    private PoTypeMasterRepository poTypeMasterRepository;
    
    // Source type constants
    private static final String SOURCE_PURCHASE_INWARD = "purchase_inward";

    /**
     * Create a new Purchase Inward record (with company support)
     */
    @Transactional
    public PurchaseInward createPurchaseInward(PurchaseInwardDTO dto) {
        validateCompanyId(dto.getCompanyId());
        PurchaseOrder purchaseOrder = referenceValidationService.requireReference(
                dto.getPurchaseOrderId(),
                () -> purchaseOrderRepository.findById(dto.getPurchaseOrderId()),
                "Purchase Order",
                dto.getPurchaseOrderNo());
        validateInwardQuantity(null, dto.getPurchaseInwardItemsDtl());
        validateDuplicateLotAndPieceNumbers(dto);

        PurchaseInward inward = new PurchaseInward();

        // Map basic fields
        inward.setPoTypeId(dto.getPoTypeId());
        inward.setVendorId(dto.getVendorId());
        inward.setPurchaseOrderId(normalizeId(dto.getPurchaseOrderId()));
        inward.setRemarks(dto.getRemarks());
        inward.setInwardDate(dto.getInwardDate() != null ? dto.getInwardDate() : LocalDate.now());
        inward.setActiveFlag(true);
        inward.setCompanyId(dto.getCompanyId()); // NEW: Company support
        inward.setYarnMillId(dto.getYarnMillId());

        // ✅ DC fields (ensure correct mapping)
        inward.setDcDate(dto.getDcDate() != null ? dto.getDcDate() : LocalDate.now());
        inward.setDcNumber(dto.getDcNumber());
        inward.setInwardNo(dto.getPurchaseInwardNo());
        inward.setMillInvNo(dto.getMillInvNo());
        inward.setMillDate(dto.getMillDate());
        inward.setIcilInvNo(dto.getIcilInvNo());
        inward.setIcilInvDate(dto.getIcilInvDate());

        // Accounting fields mapping
        if (dto.getIsAccounted() != null) inward.setIsAccounted(dto.getIsAccounted());
        if (dto.getInvoiceNumber() != null) inward.setInvoiceNumber(dto.getInvoiceNumber());
        if (dto.getInvoiceDate() != null) inward.setInvoiceDate(dto.getInvoiceDate());
        if (dto.getPurchaseAccount() != null) inward.setPurchaseAccount(dto.getPurchaseAccount());
        if (dto.getSupplierAccount() != null) inward.setSupplierAccount(dto.getSupplierAccount());
        if (dto.getTaxAmount() != null) inward.setTaxAmount(dto.getTaxAmount());
        if (dto.getTotalAmount() != null) inward.setTotalAmount(dto.getTotalAmount());
        if (dto.getAccountingRemarks() != null) inward.setAccountingRemarks(dto.getAccountingRemarks());

        // ✅ Calculate and set financial year
        inward.setFinancialYear(calculateFinancialYear(inward.getInwardDate()));

        // Determine PO type
        // boolean isYarn = PO_TYPE_YARN.equals(dto.getPoTypeId());
         boolean isYarn = PO_TYPE_YARN.equals(dto.getPoTypeId()) || PO_TYPE_WOVEN_FABRIC.equals(dto.getPoTypeId()) || PO_TYPE_KNITTED_FABRIC.equals(dto.getPoTypeId());
        boolean isItem = PO_TYPE_ITEM.equals(dto.getPoTypeId());
        boolean isKnittedFabric = PO_TYPE_KNITTED_FABRIC.equals(dto.getPoTypeId());
        boolean isWovenFabric = PO_TYPE_WOVEN_FABRIC.equals(dto.getPoTypeId());
        boolean isFabric = isKnittedFabric || isWovenFabric;
        
        String poTypeName = getPoTypeName(dto.getPoTypeId());
        String candidatePurchaseOrderNo = dto.getPurchaseOrderNo();
        String purchaseOrderNo = candidatePurchaseOrderNo != null
                ? candidatePurchaseOrderNo
                : (purchaseOrder != null ? purchaseOrder.getPoNo() : null);
        inward.setPurchaseOrderNo(purchaseOrderNo);

        // ✅ Handle inward items
        List<PurchaseInwardItem> inwardItems = new ArrayList<>();
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                PurchaseInwardItem item = new PurchaseInwardItem();
                Long purchaseOrderItemId = normalizeId(itemDto.getPoItemId());
                item.setPurchaseOrderItemId(purchaseOrderItemId);
                item.setQuantityReceived(itemDto.getQuantityReceived());
                item.setNoOfPiece(itemDto.getNoOfPiece());
                item.setYarnTypeId(itemDto.getYarnTypeId());
                item.setPrice(itemDto.getPrice());
                item.setMillNameId(itemDto.getMillNameId());
                item.setPurchaseInward(inward);
                item.setActiveFlag(true);
                item.setRemarks(itemDto.getRemarks());
                
                // Set calculated amount fields
                item.setTotalAmount(itemDto.getTotalAmount());
                item.setGstAmount(itemDto.getGstAmount());
                item.setFinalAmount(itemDto.getFinalAmount());
                item.setBalanceQty(itemDto.getBalanceQty());

                // Derive fabric code and mill name id from purchase order item
                String fabricCode = getFabricCodeFromPurchaseOrderItem(purchaseOrderItemId);
                item.setFabricCode(fabricCode);
                Long millNameId = itemDto.getMillNameId() != null ? itemDto.getMillNameId() : getMillNameIdFromPurchaseOrderItem(purchaseOrderItemId);
                logger.info("PO Item ID: {}, millNameId from payload: {}, millNameId fetched: {}", purchaseOrderItemId, itemDto.getMillNameId(), millNameId);
                item.setMillNameId(millNameId);

                // FABRIC FLOW - Use Piece Entries (merged from both old and new code)
                if (isFabric && itemDto.getPieceEntries() != null && !itemDto.getPieceEntries().isEmpty()) {
                    List<PieceEntry> pieceEntries = itemDto.getPieceEntries().stream()
                        .map(pieceDto -> mapPieceEntryDtoToEntity(null, pieceDto, item, poTypeName, purchaseOrderNo, dto.getCompanyId()))
                        .collect(Collectors.toList());
                    item.setPieceEntries(pieceEntries);
                }

                // NEW: Yarn Stock Details (from new code)
                if (itemDto.getYarnStockDetails() != null && !itemDto.getYarnStockDetails().isEmpty()) {
                    // This will be handled in createYarnInventoryRecords method
                }

                // NEW: Item Stock Details
                if (itemDto.getItemStockDetails() != null && !itemDto.getItemStockDetails().isEmpty()) {
                    // This will be handled in createItemInventoryRecords method
                }

                inwardItems.add(item);
            }
        }

        inward.setItems(inwardItems);

        // ✅ Set main fabric code (first item's fabric code)
        if (!inwardItems.isEmpty() && inwardItems.get(0).getFabricCode() != null) {
            inward.setFabricCode(inwardItems.get(0).getFabricCode());
        }

        // Save
        PurchaseInward savedInward = inwardRepository.save(inward);

        // Ensure child collections are initialized while we're still in the transaction
        if (savedInward.getItems() != null) {
            savedInward.getItems().forEach(it -> {
                if (it.getPieceEntries() != null) it.getPieceEntries().size();
                if (it.getLotEntries() != null) it.getLotEntries().size();
            });
        }

        // NEW: Create fabric inspections for piece entries (from old code)
        List<PieceEntry> piecesToUpdate = new ArrayList<>();
        for (PurchaseInwardItem item : savedInward.getItems()) {
            if (item.getPieceEntries() != null && !item.getPieceEntries().isEmpty()) {
                for (PieceEntry piece : item.getPieceEntries()) {
                    piece.setInwardId(savedInward.getId());
                    piecesToUpdate.add(piece);
                    fabricInspectionService.createFabricInspectionFromPieceEntry(piece);
                }
            }
        }
        if (!piecesToUpdate.isEmpty()) {
            pieceEntryRepository.saveAll(piecesToUpdate);
        }

        // ✅ Create yarn inventory records if PO type is yarn OR if yarn stock details are present
        if (isYarn || hasYarnStockDetails(dto)) {
            createYarnInventoryRecords(savedInward, dto);
        }

        // ✅ Create item inventory records if PO type is item OR if item stock details are present
        if (isItem || hasItemStockDetails(dto)) {
            createItemInventoryRecords(savedInward, dto);
        }

        fireBalanceZeroNotificationsIfNeeded(savedInward);

        return savedInward;
    }

    /**
     * Get all purchase inwards (with company support)
     */
     public List<PurchaseInwardDTO> getAllPurchaseInwards() {
        List<PurchaseInward> inwards = inwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<PurchaseInwardDTO> result = new ArrayList<>();
        for (PurchaseInward inward : inwards) {
            PurchaseInwardDTO dto = convertToDetailedDTO(inward);

            // ✅ Ensure DC fields are mapped in response
            dto.setDcDate(inward.getDcDate());
            dto.setDcNumber(inward.getDcNumber());
            dto.setPurchaseInwardNo(inward.getInwardNo());
            dto.setCompanyId(inward.getCompanyId());

            // Derive fabric code if missing
            if (dto.getFabricCode() == null && inward.getItems() != null && !inward.getItems().isEmpty()) {
                String fabricCode = getFabricCodeFromPurchaseOrderItems(inward.getItems());
                dto.setFabricCode(fabricCode);
            }

            result.add(dto);
        }
        return result;
    }
//     
//    private List<PurchaseInwardDTO> getAllPurchaseInwardsByFabricType(Long fabricTypeId) {
//        List<PieceEntry> pieceEntries = pieceEntryRepository.findBySourceTypeAndFabricTypeId("purchase_inward", fabricTypeId);
//        
//        if (pieceEntries.isEmpty()) {
//            return new ArrayList<>();
//        }
//        
//        java.util.Set<Long> purchaseInwardItemIds = new java.util.HashSet<>();
//        for (PieceEntry piece : pieceEntries) {
//            if (piece.getPurchaseInwardItem() != null) {
//                purchaseInwardItemIds.add(piece.getPurchaseInwardItem().getId());
//            }
//        }
//        
//        if (purchaseInwardItemIds.isEmpty()) {
//            return new ArrayList<>();
//        }
//        
//        List<PurchaseInward> inwards = inwardRepository.findAll().stream()
//                .filter(inward -> inward.getItems() != null && 
//                    inward.getItems().stream().anyMatch(item -> purchaseInwardItemIds.contains(item.getId())))
//                .sorted(java.util.Comparator.comparing(PurchaseInward::getId).reversed())
//                .collect(Collectors.toList());
//
//        List<PurchaseInwardDTO> result = new ArrayList<>();
//        for (PurchaseInward inward : inwards) {
//            PurchaseInwardDTO dto = convertToDetailedDTO(inward);
//
//            dto.setDcDate(inward.getDcDate());
//            dto.setDcNumber(inward.getDcNumber());
//            dto.setPurchaseInwardNo(inward.getInwardNo());
//            dto.setCompanyId(inward.getCompanyId());
//
//            if (dto.getFabricCode() == null && inward.getItems() != null && !inward.getItems().isEmpty()) {
//                String fabricCode = getFabricCodeFromPurchaseOrderItems(inward.getItems());
//                dto.setFabricCode(fabricCode);
//            }
//
//            result.add(dto);
//        }
//        return result;
//    }

    // public List<PurchaseInwardDTO> getAllPurchaseInwards() {
    //     List<PurchaseInward> inwards = inwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

    //     return inwards.stream()
    //             .map(this::convertToDto)
    //             .collect(Collectors.toList());
    // }

    /**
     * Get all purchase inwards by company (from old code)
     */
    public List<PurchaseInwardDTO> getPurchaseInwardsByCompany(Long companyId) {
        validateCompanyId(companyId);
        
        return inwardRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Get all purchase inwards with pagination (with company filter option)
     */
    public PaginatedResponse<PurchaseInwardDTO> getAllPurchaseInwardsWithPagination(Long offset, Long limit, Long companyId) {
        List<PurchaseInwardDTO> all;
        
        if (companyId != null) {
            validateCompanyId(companyId);
            all = getPurchaseInwardsByCompany(companyId);
        } else {
            all = getAllPurchaseInwards();
        }
        
        return PaginationHelper.paginate(all, offset, limit);
    }

    public PaginatedResponse<PurchaseInwardDTO> getAllPurchaseInwardsWithPaginationAndCompany(Long offset, Long limit, Long fabricTypeId, Long companyId) {
        validateCompanyId(companyId);
        List<PurchaseInward> inwards = inwardRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id"));
        List<PurchaseInwardDTO> dtos = inwards.stream()
                .map(this::convertToDetailedDTO)
                .collect(Collectors.toList());
        
        if (fabricTypeId != null) {
            dtos = dtos.stream()
                    .filter(dto -> matchesFabricType(dto, fabricTypeId))
                    .collect(Collectors.toList());
        }
        
        return PaginationHelper.paginate(dtos, offset, limit);
    }

    /**
     * Get a single purchase inward by ID
     */
    public PurchaseInward getPurchaseInwardById(Long id) {
        return inwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Inward not found with id: " + id));
    }

    /**
     * Get purchase inward by ID and company (from old code)
     */
    public PurchaseInward getPurchaseInwardByIdAndCompany(Long id, Long companyId) {
        validateCompanyId(companyId);
        
        return inwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Purchase Inward not found with id: " + id + " and companyId: " + companyId));
    }

    /**
     * Update purchase inward record (generic)
     */
    @Transactional
    public PurchaseInward updatePurchaseInward(Long id, PurchaseInwardDTO dto) {
        PurchaseInward inward = getPurchaseInwardById(id);
        return updatePurchaseInwardInternal(inward, dto);
    }

    /**
     * Update purchase inward record and return as DTO with yarn stock details.
     * This is the new API to add.
     */
    @Transactional
    public PurchaseInwardDTO updatePurchaseInwardWithDetails(Long id, PurchaseInwardDTO dto) {
        PurchaseInward updatedInward = updatePurchaseInward(id, dto);
        inwardRepository.flush();
        yarnInwardStockDetailsRepository.flush();
        
         PurchaseInwardDTO resultDto = convertToDetailedDTO(updatedInward);
        // PurchaseInwardDTO resultDto = convertToDto(updatedInward);
        resultDto.setDcDate(updatedInward.getDcDate());
        resultDto.setDcNumber(updatedInward.getDcNumber());
        resultDto.setPurchaseInwardNo(updatedInward.getInwardNo());
        resultDto.setCompanyId(updatedInward.getCompanyId());
        
        return resultDto;
    }

    /**
     * Update purchase inward by company (from old code)
     */
    @Transactional
    public PurchaseInward updatePurchaseInwardByCompany(Long id, Long companyId, PurchaseInwardDTO dto) {
        validateCompanyId(companyId);
        
        if (dto.getCompanyId() == null) {
            dto.setCompanyId(companyId);
        } else if (!dto.getCompanyId().equals(companyId)) {
            throw new IllegalArgumentException("Company ID mismatch");
        }
        
        PurchaseInward existing = getPurchaseInwardByIdAndCompany(id, companyId);
        return updatePurchaseInwardInternal(existing, dto);
    }

    /**
     * Update purchase inward by company and return as DTO with yarn stock details.
     * Company-specific version of the new API.
     */
    @Transactional
    public PurchaseInwardDTO updatePurchaseInwardWithDetailsByCompany(Long id, Long companyId, PurchaseInwardDTO dto) {
        validateCompanyId(companyId);
        
        if (dto.getCompanyId() == null) {
            dto.setCompanyId(companyId);
        } else if (!dto.getCompanyId().equals(companyId)) {
            throw new IllegalArgumentException("Company ID mismatch");
        }
        
        PurchaseInward updatedInward = updatePurchaseInwardByCompany(id, companyId, dto);
        inwardRepository.flush();
        yarnInwardStockDetailsRepository.flush();
        
        PurchaseInwardDTO resultDto = convertToDto(updatedInward);
        resultDto.setDcDate(updatedInward.getDcDate());
        resultDto.setDcNumber(updatedInward.getDcNumber());
        resultDto.setPurchaseInwardNo(updatedInward.getInwardNo());
        resultDto.setCompanyId(updatedInward.getCompanyId());
        
        return resultDto;
    }

    /**
     * Update accounting details for a Purchase Inward entry and mark it as accounted
     */
    @Transactional
    public PurchaseInwardDTO updateAccountingDetails(Long id, PurchaseInwardDTO dto) {
        PurchaseInward inward = getPurchaseInwardById(id);
        return updateAccountingDetailsInternal(inward, dto);
    }

    @Transactional
    public PurchaseInwardDTO updateAccountingDetailsByCompany(Long id, Long companyId, PurchaseInwardDTO dto) {
        validateCompanyId(companyId);
        PurchaseInward inward = getPurchaseInwardByIdAndCompany(id, companyId);
        return updateAccountingDetailsInternal(inward, dto);
    }

    private PurchaseInwardDTO updateAccountingDetailsInternal(PurchaseInward inward, PurchaseInwardDTO dto) {
        if (dto.getIsAccounted() != null) {
            inward.setIsAccounted(dto.getIsAccounted());
        } else {
            inward.setIsAccounted(true);
        }
        if (dto.getInvoiceNumber() != null) inward.setInvoiceNumber(dto.getInvoiceNumber());
        if (dto.getInvoiceDate() != null) inward.setInvoiceDate(dto.getInvoiceDate());
        if (dto.getPurchaseAccount() != null) inward.setPurchaseAccount(dto.getPurchaseAccount());
        if (dto.getSupplierAccount() != null) inward.setSupplierAccount(dto.getSupplierAccount());
        if (dto.getTaxAmount() != null) inward.setTaxAmount(dto.getTaxAmount());
        if (dto.getTotalAmount() != null) inward.setTotalAmount(dto.getTotalAmount());
        if (dto.getAccountingRemarks() != null) inward.setAccountingRemarks(dto.getAccountingRemarks());

        PurchaseInward savedInward = inwardRepository.save(inward);
        return convertToDetailedDTO(savedInward);
    }

    /**
     * Internal update method
     */

     private void validateInwardQuantity(Long inwardId, List<PurchaseInwardItemDTO> itemDtos) {
        if (itemDtos == null || itemDtos.isEmpty()) return;

        // Group by poItemId and sum up quantities in THIS DTO
        java.util.Map<Long, BigDecimal> dtoQuantities = new java.util.HashMap<>();

        for (PurchaseInwardItemDTO itemDto : itemDtos) {
            Long poItemId = normalizeId(itemDto.getPoItemId());

            if (poItemId == null && itemDto.getId() != null && itemDto.getId() > 0) {
                poItemId = purchaseInwardItemRepository.findById(itemDto.getId())
                        .map(PurchaseInwardItem::getPurchaseOrderItemId).orElse(null);
            }

            if (poItemId == null || poItemId <= 0) continue;

            BigDecimal currentInwardQty = itemDto.getQuantityReceived();
            if (currentInwardQty == null && itemDto.getId() != null && itemDto.getId() > 0) {
                currentInwardQty = purchaseInwardItemRepository.findById(itemDto.getId())
                        .map(PurchaseInwardItem::getQuantityReceived).orElse(BigDecimal.ZERO);
            }

            if (currentInwardQty == null) currentInwardQty = BigDecimal.ZERO;

            dtoQuantities.put(poItemId, dtoQuantities.getOrDefault(poItemId, BigDecimal.ZERO).add(currentInwardQty));
        }

        for (java.util.Map.Entry<Long, BigDecimal> entry : dtoQuantities.entrySet()) {
            Long poItemId = entry.getKey();
            BigDecimal currentTotalInwardForThisDto = entry.getValue();

            PurchaseOrderItem poItem = purchaseOrderItemRepository.findById(poItemId)
                    .orElseThrow(() -> new BusinessException("Invalid Purchase Order Item reference."));

            BigDecimal orderedQty = poItem.getQuantity() != null ? poItem.getQuantity() : BigDecimal.ZERO;
            BigDecimal alreadyReceivedFromOthers;

            if (inwardId == null) {
                alreadyReceivedFromOthers = purchaseInwardItemRepository.sumQuantityReceivedByPoItemId(poItemId);
            } else {
                alreadyReceivedFromOthers = purchaseInwardItemRepository.sumQuantityReceivedByPoItemIdExcludingInward(poItemId, inwardId);
            }

            BigDecimal totalInward = alreadyReceivedFromOthers.add(currentTotalInwardForThisDto);

            if (totalInward.compareTo(orderedQty) > 0) {
                BigDecimal remaining = orderedQty.subtract(alreadyReceivedFromOthers);
                if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new BusinessException("All quantities for this Purchase Order item have already been received. " +
                            "Ordered: " + orderedQty + ", Received: " + alreadyReceivedFromOthers);
                } else {
                    throw new BusinessException("Entered quantity (" + currentTotalInwardForThisDto + ") exceeds the remaining Purchase Order quantity (" + remaining + "). " +
                            "Total Ordered: " + orderedQty + ", Already Received: " + alreadyReceivedFromOthers);
                }
            }
        }
    }

    private void validateDuplicateLotAndPieceNumbers(PurchaseInwardDTO dto) {
        if (dto.getPurchaseInwardItemsDtl() == null) return;
        Long companyId = dto.getCompanyId();
        Long currentInwardId = dto.getId();
        if (companyId == null && currentInwardId != null) {
             PurchaseInward existingInward = inwardRepository.findById(currentInwardId).orElse(null);
             if (existingInward != null) companyId = existingInward.getCompanyId();
        }

        Set<String> requestPieceNumbers = new HashSet<>();

        for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
            // 1. Validate Piece Entries
            if (itemDto.getPieceEntries() != null) {
                for (PieceEntryDTO pieceDto : itemDto.getPieceEntries()) {
                    if (StringUtils.hasText(pieceDto.getPieceNumber())) {
                        String pieceNumber = pieceDto.getPieceNumber().trim();
                        
                        // Internal check (case-insensitive)
                        if (requestPieceNumbers.contains(pieceNumber.toLowerCase())) {
                            throw new IllegalArgumentException("Duplicate Piece Number '" + pieceNumber + "' found in request.");
                        }
                        requestPieceNumbers.add(pieceNumber.toLowerCase());

                        // DB check (case-insensitive)
                        List<PieceEntry> existingPieces = (companyId != null) 
                            ? pieceEntryRepository.findByPieceNumberIgnoreCaseAndCompanyId(pieceNumber, companyId)
                            : pieceEntryRepository.findByPieceNumberIgnoreCase(pieceNumber);
                        for (PieceEntry existing : existingPieces) {
                            // During update, exclude pieces that belong to the current PurchaseInward record
                            if (currentInwardId != null && existing.getPurchaseInwardItem() != null &&
                                existing.getPurchaseInwardItem().getPurchaseInward() != null &&
                                existing.getPurchaseInwardItem().getPurchaseInward().getId().equals(currentInwardId)) {
                                continue;
                            }
                            if (pieceDto.getId() == null || !existing.getId().equals(pieceDto.getId())) {
                                throw new IllegalArgumentException("Piece Number '" + pieceNumber + "' already exists in database for this company.");
                            }
                        }
                    }
                }
            }

        }
    }

    private void validateIssuedYarnLotsNotModified(PurchaseInward inward, PurchaseInwardDTO dto) {
        if (inward == null || dto == null || dto.getPurchaseInwardItemsDtl() == null) {
            return;
        }

        YarnInventory inventory = null;
        if (inward.getId() != null && inward.getPoTypeId() != null) {
            List<YarnInventory> list = yarnInventoryRepository.findByInwardIdAndInwardTypeId(inward.getId(), inward.getPoTypeId());
            if (!list.isEmpty()) {
                inventory = list.get(0);
            }
        }
        if (inventory == null && inward.getInwardNo() != null && inward.getPurchaseOrderId() != null) {
            List<YarnInventory> list = yarnInventoryRepository.findByInwardNoAndOrderId(inward.getInwardNo(), inward.getPurchaseOrderId());
            if (!list.isEmpty()) {
                inventory = list.get(0);
            }
        }

        if (inventory == null) {
            return;
        }

        List<YarnInwardStockDetails> existingDetails = yarnInwardStockDetailsRepository.findByYarnInventoryId(inventory.getId());

        if (existingDetails.isEmpty()) {
            return;
        }

        Map<Long, YarnStockDetailDTO> incomingMapById = new HashMap<>();
        Map<String, YarnStockDetailDTO> incomingMapByLotKey = new HashMap<>();

        for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
            if (itemDto.getYarnStockDetails() != null) {
                for (YarnStockDetailDTO stockDto : itemDto.getYarnStockDetails()) {
                    if (stockDto.getId() != null && stockDto.getId() > 0) {
                        incomingMapById.put(stockDto.getId(), stockDto);
                    }
                    if (stockDto.getItemId() != null && StringUtils.hasText(stockDto.getLotNo())) {
                        String key = stockDto.getItemId() + "_" + stockDto.getLotNo().trim();
                        incomingMapByLotKey.put(key, stockDto);
                    }
                }
            }
        }

        for (YarnInwardStockDetails detail : existingDetails) {
            BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : BigDecimal.ZERO;
            BigDecimal availableQty = detail.getAvailableQty() != null ? detail.getAvailableQty() : BigDecimal.ZERO;

            BigDecimal totalIssued = calculateTotalIssuedQuantity(detail.getOrderItemId());
            boolean isIssued = acceptedQty.compareTo(availableQty) > 0 || (totalIssued != null && totalIssued.compareTo(BigDecimal.ZERO) > 0);

            if (!isIssued) {
                continue;
            }

            YarnStockDetailDTO matchingDto = null;
            if (detail.getId() != null) {
                matchingDto = incomingMapById.get(detail.getId());
            }
            if (matchingDto == null && detail.getItemId() != null && StringUtils.hasText(detail.getLotNo())) {
                String key = detail.getItemId() + "_" + detail.getLotNo().trim();
                matchingDto = incomingMapByLotKey.get(key);
            }

            String lotNo = detail.getLotNo() != null ? detail.getLotNo() : "";
            String yarnName = "Yarn";
            if (detail.getItemId() != null) {
                yarnName = yarnMasterRepository.findById(detail.getItemId())
                        .map(YarnMaster::getYarnName)
                        .orElse("Yarn");
            }

            String errorMessage = "The " + yarnName + " from the Lot " + lotNo + " already issued. please contact admin";

            if (matchingDto == null || isLotDetailModified(detail, matchingDto)) {
                throw new IllegalStateException(errorMessage);
            }
        }
    }

    private boolean isLotDetailModified(YarnInwardStockDetails detail, YarnStockDetailDTO dto) {
        if (!Objects.equals(detail.getLotNo(), dto.getLotNo())) return true;
        if (!Objects.equals(detail.getWarehouseId(), dto.getWarehouseId())) return true;
        if (dto.getMillId() != null && !Objects.equals(detail.getMillId(), dto.getMillId())) return true;
        if (compareBigDecimal(detail.getAcceptedQty(), dto.getAcceptedQty()) != 0) return true;
        if (compareBigDecimal(detail.getInwardQty(), dto.getInwardQty()) != 0) return true;
        if (compareBigDecimal(detail.getRejectedQty(), dto.getRejectedQty()) != 0) return true;
        if (compareBigDecimal(detail.getInwardBags(), dto.getInwardBags()) != 0) return true;
        if (compareBigDecimal(detail.getKgsPerBag(), dto.getKgsPerBag()) != 0) return true;
        if (compareBigDecimal(detail.getConesPerBag(), dto.getConesPerBag()) != 0) return true;
        if (compareBigDecimal(detail.getConesWeight(), dto.getConesWeight()) != 0) return true;
        return false;
    }

    private int compareBigDecimal(BigDecimal a, BigDecimal b) {
        BigDecimal valA = a != null ? a : BigDecimal.ZERO;
        BigDecimal valB = b != null ? b : BigDecimal.ZERO;
        return valA.compareTo(valB);
    }

    private PurchaseInward updatePurchaseInwardInternal(PurchaseInward inward, PurchaseInwardDTO dto) {
        validateInwardQuantity(inward.getId(), dto.getPurchaseInwardItemsDtl());
        validateDuplicateLotAndPieceNumbers(dto);
        validateIssuedYarnLotsNotModified(inward, dto);
        // Update basic fields
        if (dto.getInwardDate() != null) {
            inward.setInwardDate(dto.getInwardDate());
        }
        if (dto.getPoTypeId() != null) {
            inward.setPoTypeId(dto.getPoTypeId());
        }
        if (dto.getVendorId() != null) {
            inward.setVendorId(dto.getVendorId());
        }
        if (dto.getRemarks() != null) {
            inward.setRemarks(dto.getRemarks());
        }
        if (dto.getActiveFlag() != null) {
            inward.setActiveFlag(dto.getActiveFlag());
        }
        if (dto.getDcDate() != null) {
            inward.setDcDate(dto.getDcDate());
        }
        if (dto.getDcNumber() != null) {
            inward.setDcNumber(dto.getDcNumber());
        }
        if (dto.getFabricCode() != null) {
            inward.setFabricCode(dto.getFabricCode());
        }
        if (dto.getPurchaseInwardNo() != null) {
            inward.setInwardNo(dto.getPurchaseInwardNo());
        }
        if (dto.getMillInvNo() != null) {
            inward.setMillInvNo(dto.getMillInvNo());
        }
        if (dto.getMillDate() != null) {
            inward.setMillDate(dto.getMillDate());
        }
        if (dto.getIcilInvNo() != null) {
            inward.setIcilInvNo(dto.getIcilInvNo());
        }
        if (dto.getIcilInvDate() != null) {
            inward.setIcilInvDate(dto.getIcilInvDate());
        }
        if (dto.getCompanyId() != null) {
            inward.setCompanyId(dto.getCompanyId());
        }
        if (dto.getPurchaseOrderNo() != null) {
            inward.setPurchaseOrderNo(dto.getPurchaseOrderNo());
        }
        if (dto.getYarnMillId() != null) {
            inward.setYarnMillId(dto.getYarnMillId());
        }

        // Accounting fields mapping
        if (dto.getIsAccounted() != null) inward.setIsAccounted(dto.getIsAccounted());
        if (dto.getInvoiceNumber() != null) inward.setInvoiceNumber(dto.getInvoiceNumber());
        if (dto.getInvoiceDate() != null) inward.setInvoiceDate(dto.getInvoiceDate());
        if (dto.getPurchaseAccount() != null) inward.setPurchaseAccount(dto.getPurchaseAccount());
        if (dto.getSupplierAccount() != null) inward.setSupplierAccount(dto.getSupplierAccount());
        if (dto.getTaxAmount() != null) inward.setTaxAmount(dto.getTaxAmount());
        if (dto.getTotalAmount() != null) inward.setTotalAmount(dto.getTotalAmount());
        if (dto.getAccountingRemarks() != null) inward.setAccountingRemarks(dto.getAccountingRemarks());

        PurchaseInward savedInward = inwardRepository.save(inward);

        Long savedPoTypeId = savedInward.getPoTypeId();
        boolean isYarn = PO_TYPE_YARN.equals(savedInward.getPoTypeId()) || PO_TYPE_WOVEN_FABRIC.equals(savedInward.getPoTypeId()) || PO_TYPE_KNITTED_FABRIC.equals(savedInward.getPoTypeId());
        // boolean isYarn = PO_TYPE_YARN.equals(savedPoTypeId);
        boolean isItem = PO_TYPE_ITEM.equals(savedPoTypeId);
        boolean isKnittedFabric = PO_TYPE_KNITTED_FABRIC.equals(savedPoTypeId);
        boolean isWovenFabric = PO_TYPE_WOVEN_FABRIC.equals(savedPoTypeId);
        boolean isFabric = isKnittedFabric || isWovenFabric;
        String poTypeName = getPoTypeName(savedPoTypeId);
        String purchaseOrderNo = savedInward.getPurchaseOrderNo();
        if (purchaseOrderNo == null) {
            purchaseOrderNo = getPurchaseOrderNo(savedInward.getPurchaseOrderId());
            savedInward.setPurchaseOrderNo(purchaseOrderNo);
        }
        Long defaultCompanyId = savedInward.getCompanyId();

        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                PurchaseInwardItem existingItem = null;
                if (itemDto.getId() != null && itemDto.getId() > 0) {
                    existingItem = purchaseInwardItemRepository.findById(itemDto.getId()).orElse(null);
                }
                if (existingItem == null && itemDto.getPoItemId() != null && savedInward.getItems() != null) {
                    Long targetPoItemId = normalizeId(itemDto.getPoItemId());
                    existingItem = savedInward.getItems().stream()
                        .filter(it -> it.getPurchaseOrderItemId() != null && it.getPurchaseOrderItemId().equals(targetPoItemId))
                        .findFirst().orElse(null);
                }

                if (existingItem != null) {
                    PurchaseInwardItem item = existingItem;
                        
                    if (itemDto.getQuantityReceived() != null) {
                        item.setQuantityReceived(itemDto.getQuantityReceived());
                    }
                    if (itemDto.getNoOfPiece() != null) {
                        item.setNoOfPiece(itemDto.getNoOfPiece());
                    }
                    if (itemDto.getYarnTypeId() != null) {
                        item.setYarnTypeId(itemDto.getYarnTypeId());
                    }
                    if (itemDto.getPrice() != null) {
                        item.setPrice(itemDto.getPrice());
                    }
                    if (itemDto.getMillNameId() != null) {
                        item.setMillNameId(itemDto.getMillNameId());
                    }
                    if (itemDto.getActiveFlag() != null) {
                        item.setActiveFlag(itemDto.getActiveFlag());
                    }
                    if (itemDto.getRemarks() != null) {
                        item.setRemarks(itemDto.getRemarks());
                    }
                    
                    // Set calculated amount fields
                    if (itemDto.getTotalAmount() != null) {
                        item.setTotalAmount(itemDto.getTotalAmount());
                    }
                    if (itemDto.getGstAmount() != null) {
                        item.setGstAmount(itemDto.getGstAmount());
                    }
                    if (itemDto.getFinalAmount() != null) {
                        item.setFinalAmount(itemDto.getFinalAmount());
                    }
                    if (itemDto.getBalanceQty() != null) {
                        item.setBalanceQty(itemDto.getBalanceQty());
                    }
                    
                    purchaseInwardItemRepository.save(item);
                    logger.info("Updated PurchaseInwardItem ID: {}, quantityReceived: {}, price: {}", 
                        itemDto.getId(), itemDto.getQuantityReceived(), itemDto.getPrice());
                    
                    if (isFabric && itemDto.getPieceEntries() != null) {
                        syncPurchaseInwardPieceEntries(item, itemDto.getPieceEntries(), poTypeName, purchaseOrderNo, defaultCompanyId);
                    }
                    
                    // Yarn and Item stock details are handled by updateYarnInventoryRecords / updateItemInventoryRecords below
                } else {
                    // CREATE new item for those added during update
                    PurchaseInwardItem newItem = new PurchaseInwardItem();
                    newItem.setPurchaseInward(savedInward);
                    newItem.setPurchaseOrderItemId(normalizeId(itemDto.getPoItemId()));
                    newItem.setQuantityReceived(itemDto.getQuantityReceived());
                    newItem.setNoOfPiece(itemDto.getNoOfPiece());
                    newItem.setYarnTypeId(itemDto.getYarnTypeId());
                    newItem.setPrice(itemDto.getPrice());
                    newItem.setMillNameId(itemDto.getMillNameId());
                    newItem.setFabricCode(itemDto.getFabricCode());
                    newItem.setActiveFlag(true);
                    newItem.setRemarks(itemDto.getRemarks());
                    
                    // Set calculated amount fields
                    newItem.setTotalAmount(itemDto.getTotalAmount());
                    newItem.setGstAmount(itemDto.getGstAmount());
                    newItem.setFinalAmount(itemDto.getFinalAmount());
                    newItem.setBalanceQty(itemDto.getBalanceQty());
                    
                    purchaseInwardItemRepository.save(newItem);
                    logger.info("Created new PurchaseInwardItem for PO Item ID: {}, quantityReceived: {}", 
                        itemDto.getPoItemId(), itemDto.getQuantityReceived());
                        
                    if (isFabric && itemDto.getPieceEntries() != null) {
                        syncPurchaseInwardPieceEntries(newItem, itemDto.getPieceEntries(), poTypeName, purchaseOrderNo, defaultCompanyId);
                    }
                }
            }
        }

//        boolean isYarn = PO_TYPE_YARN.equals(savedInward.getPoTypeId());
        if (isYarn || hasYarnStockDetails(dto)) {
            updateYarnInventoryRecords(savedInward, dto);
        }

        if (isItem || hasItemStockDetails(dto)) {
            updateItemInventoryRecords(savedInward, dto);
        }

        inwardRepository.flush();
        yarnInwardStockDetailsRepository.flush();

        fireBalanceZeroNotificationsIfNeeded(savedInward);

        return savedInward;
    }


    

private void updateYarnInventoryRecords(PurchaseInward purchaseInward, PurchaseInwardDTO dto) {
        Long poCategoryId = null;
        if (purchaseInward.getPurchaseOrderId() != null) {
            poCategoryId = purchaseOrderRepository.findById(purchaseInward.getPurchaseOrderId())
                .map(PurchaseOrder::getPoCategoryId).orElse(null);
        }
        // CRITICAL FIX: Find YarnInventory by BOTH orderId AND inwardNo to prevent cross-inward contamination
        // When multiple inwards have the same orderId, we need to match the specific inward
        YarnInventory yarnInventory = null;
        
        if (purchaseInward.getInwardNo() != null && purchaseInward.getPurchaseOrderId() != null) {
            List<YarnInventory> existingList = yarnInventoryRepository.findByInwardNoAndOrderId(
                purchaseInward.getInwardNo(),
                purchaseInward.getPurchaseOrderId()
            );
            if (!existingList.isEmpty()) {
                yarnInventory = existingList.get(0);
            }
        }
        
        // If not found by inward-specific lookup, create new one
        if (yarnInventory == null) {
            yarnInventory = new YarnInventory();
            yarnInventory.setInwardTypeId(purchaseInward.getPoTypeId());
            yarnInventory.setCompanyId(getCompanyIdFromPurchaseOrder(purchaseInward.getPurchaseOrderId()));
            yarnInventory.setFinancialYear(calculateFinancialYear(purchaseInward.getInwardDate()));
            yarnInventory.setOrderId(purchaseInward.getPurchaseOrderId());
        }

        yarnInventory.setInwardId(purchaseInward.getId());
        yarnInventory.setInwardNo(purchaseInward.getInwardNo());
        yarnInventory.setDcNo(purchaseInward.getDcNumber());
        yarnInventory.setDcDate(purchaseInward.getDcDate());
        yarnInventory.setInwardDate(purchaseInward.getInwardDate() != null ?
                purchaseInward.getInwardDate().atStartOfDay() : LocalDate.now().atStartOfDay());
        yarnInventory.setFinancialYear(calculateFinancialYear(purchaseInward.getInwardDate()));

         String poNumber = getPONumberFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
         yarnInventory.setOrderNo(poNumber != null ? poNumber : String.valueOf(purchaseInward.getPurchaseOrderId()));

//        yarnInventory.setOrderNo(purchaseOrder.getPoNumber());
        yarnInventory.setVendorId(purchaseInward.getVendorId());

        // Set warehouseId from first available stock detail
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                if (itemDto.getYarnStockDetails() != null) {
                    for (YarnStockDetailDTO stockDto : itemDto.getYarnStockDetails()) {
                        if (stockDto.getWarehouseId() != null) {
                            yarnInventory.setWarehouseId(stockDto.getWarehouseId());
                            break;
                        }
                    }
                }
                if (yarnInventory.getWarehouseId() != null) break;
            }
        }

        YarnInventory savedYarnInventory = yarnInventoryRepository.save(yarnInventory);

        // SYNC: Find and remove any existing details that are NOT in the incoming DTO
        List<YarnInwardStockDetails> currentDetails = yarnInwardStockDetailsRepository.findByInwardNoAndOrderId(
            purchaseInward.getInwardNo(),
            purchaseInward.getPurchaseOrderId()
        );
        
        Set<Long> incomingIds = new HashSet<>();
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                if (itemDto.getYarnStockDetails() != null) {
                    for (YarnStockDetailDTO stockDto : itemDto.getYarnStockDetails()) {
                        if (stockDto.getId() != null && stockDto.getId() > 0) {
                            incomingIds.add(stockDto.getId());
                        }
                    }
                }
            }
        }
        
        for (YarnInwardStockDetails detail : currentDetails) {
            if (!incomingIds.contains(detail.getId())) {
                logger.info("Removing deleted YarnInwardStockDetails ID: {} (not in update payload)", detail.getId());
                yarnInwardStockDetailsRepository.delete(detail);
            }
        }

        Set<String> generatedLotNos = new HashSet<>();
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                if (itemDto.getYarnStockDetails() == null || itemDto.getYarnStockDetails().isEmpty()) {
                    continue;
                }

                for (YarnStockDetailDTO stockDto : itemDto.getYarnStockDetails()) {
                    if (stockDto.getItemId() == null) {
                        continue;
                    }
                    stockDto.setLotNo(ensureLotNo(stockDto.getLotNo(), purchaseInward.getCompanyId(), generatedLotNos));

                    // Update by ID if ID is provided (for edit mode)
                    YarnInwardStockDetails detail = null;
                    if (stockDto.getId() != null && stockDto.getId() > 0) {
                        Optional<YarnInwardStockDetails> existingById = yarnInwardStockDetailsRepository.findById(stockDto.getId());
                        if (existingById.isPresent()) {
                            detail = existingById.get();
                            logger.info("Found existing yarn stock detail by ID: {} for ItemId: {}, LotNo: {}", 
                                stockDto.getId(), stockDto.getItemId(), stockDto.getLotNo());
                        }
                    }
                    
                    if (detail != null) {
                        // UPDATE existing yarn stock detail

                        detail.setInwardTypeId(purchaseInward.getPoTypeId());
                        detail.setItemId(stockDto.getItemId());
                        Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForYarn(stockDto.getItemId());
                        detail.setUomId(resolvedUomId);
                        detail.setMillId(stockDto.getMillId() != null ? stockDto.getMillId() : itemDto.getMillNameId());
                        detail.setLotNo(stockDto.getLotNo());
                        detail.setInwardQty(stockDto.getInwardQty());
                        detail.setInwardBags(stockDto.getInwardBags());
                        detail.setKgsPerBag(stockDto.getKgsPerBag());
                        detail.setConesPerBag(stockDto.getConesPerBag());
                        detail.setConesWeight(stockDto.getConesWeight());
                        detail.setAcceptedQty(stockDto.getAcceptedQty());
                        BigDecimal yarnTotalIssuedQty = calculateTotalIssuedQuantity(itemDto.getPoItemId());
                        BigDecimal yarnTotalIssuedBags = calculateTotalIssuedBags(itemDto.getPoItemId());
                        BigDecimal yarnNewAcceptedQty = stockDto.getAcceptedQty() != null ? stockDto.getAcceptedQty() : BigDecimal.ZERO;
                        BigDecimal yarnNewInwardBags = stockDto.getInwardBags() != null ? stockDto.getInwardBags() : BigDecimal.ZERO;
                        BigDecimal yarnComputedAvailableQty = yarnNewAcceptedQty.subtract(yarnTotalIssuedQty);
                        if (yarnComputedAvailableQty.compareTo(BigDecimal.ZERO) < 0) yarnComputedAvailableQty = BigDecimal.ZERO;
                        BigDecimal yarnComputedAvailableBags = yarnNewInwardBags.subtract(yarnTotalIssuedBags);
                        if (yarnComputedAvailableBags.compareTo(BigDecimal.ZERO) < 0) yarnComputedAvailableBags = BigDecimal.ZERO;
                        detail.setAvailableQty(yarnComputedAvailableQty);
                        detail.setAvailableBags(yarnComputedAvailableBags);
                        detail.setRejectedQty(stockDto.getRejectedQty());
                        detail.setWarehouseId(stockDto.getWarehouseId());
                        detail.setOrderItemId(itemDto.getPoItemId());
                        detail.setYarnTypeId(itemDto.getYarnTypeId());
                        detail.setRemark(stockDto.getRemark());
                        detail.setPoCategoryId(poCategoryId);
                        detail.setMillInvNo(purchaseInward.getMillInvNo());
                        detail.setMillDate(purchaseInward.getMillDate());

                        yarnInwardStockDetailsRepository.save(detail);
                        logger.info("Successfully updated YarnInwardStockDetails ID: {} with acceptedQty: {}, availableQty: {}", detail.getId(), stockDto.getAcceptedQty(), yarnComputedAvailableQty);
                    } else {
                        // CREATE new yarn stock detail if it doesn't exist (for newly added lots in edit mode)
                        YarnInwardStockDetails newDetail = new YarnInwardStockDetails();
                        newDetail.setYarnInventory(savedYarnInventory);
                        newDetail.setInwardTypeId(purchaseInward.getPoTypeId());
                        newDetail.setItemId(stockDto.getItemId());
                        Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForYarn(stockDto.getItemId());
                        newDetail.setUomId(resolvedUomId);
                        newDetail.setMillId(stockDto.getMillId() != null ? stockDto.getMillId() : itemDto.getMillNameId());
                        newDetail.setLotNo(stockDto.getLotNo());
                        newDetail.setInwardQty(stockDto.getInwardQty());
                        newDetail.setInwardBags(stockDto.getInwardBags());
                        newDetail.setKgsPerBag(stockDto.getKgsPerBag());
                        newDetail.setConesPerBag(stockDto.getConesPerBag());
                        newDetail.setConesWeight(stockDto.getConesWeight());
                        newDetail.setAcceptedQty(stockDto.getAcceptedQty());
                        newDetail.setAvailableQty(stockDto.getAcceptedQty());
                        newDetail.setAvailableBags(stockDto.getInwardBags());
                        newDetail.setRejectedQty(stockDto.getRejectedQty());
                        newDetail.setWarehouseId(stockDto.getWarehouseId());
                        newDetail.setOrderItemId(itemDto.getPoItemId());
                        newDetail.setYarnTypeId(itemDto.getYarnTypeId());
                        newDetail.setRemark(stockDto.getRemark());
                        newDetail.setPoCategoryId(poCategoryId);
                        newDetail.setMillInvNo(purchaseInward.getMillInvNo());
                        newDetail.setMillDate(purchaseInward.getMillDate());

                        yarnInwardStockDetailsRepository.save(newDetail);
                        logger.info("Successfully created new YarnInwardStockDetails for ItemId: {}, LotNo: {}, AcceptedQty: {}", 
                            stockDto.getItemId(), stockDto.getLotNo(), stockDto.getAcceptedQty());
                    }
                }
            }
        }
    }

    private void updateItemInventoryRecords(PurchaseInward purchaseInward, PurchaseInwardDTO dto) {
        Long poCategoryId = null;
        if (purchaseInward.getPurchaseOrderId() != null) {
            poCategoryId = purchaseOrderRepository.findById(purchaseInward.getPurchaseOrderId())
                .map(PurchaseOrder::getPoCategoryId).orElse(null);
        }
        // Find ItemInventory by inwardId
        ItemInventory itemInventory = null;
        List<ItemInventory> inventoryByIdList = itemInventoryRepository.findByInwardId(purchaseInward.getId());
        if (!inventoryByIdList.isEmpty()) {
            itemInventory = inventoryByIdList.get(0);
        }
        
        // Fetch poNo
        String poNumber = purchaseInward.getPurchaseOrderNo();
        if (poNumber == null) {
            poNumber = getPONumberFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
        }

        // If not found by inwardId, try finding by BOTH orderId AND inwardNo (prevents cross-inward contamination)
        if (itemInventory == null && purchaseInward.getInwardNo() != null && purchaseInward.getPurchaseOrderId() != null) {
            List<ItemInventory> existingList = itemInventoryRepository.findByInwardNoAndOrderId(
                purchaseInward.getInwardNo(),
                purchaseInward.getPurchaseOrderId()
            );
            if (!existingList.isEmpty()) {
                itemInventory = existingList.get(0);
            }
        }
        
        // Fallback for legacy records where inwardNo might have been the poNo
        if (itemInventory == null && poNumber != null && purchaseInward.getPurchaseOrderId() != null) {
            List<ItemInventory> existingList = itemInventoryRepository.findByInwardNoAndOrderId(
                poNumber,
                purchaseInward.getPurchaseOrderId()
            );
            if (!existingList.isEmpty()) {
                itemInventory = existingList.get(0);
            }
        }
        
        // Fetch inwardItemId from CodesDetail (codeType = 'PURCHASE_INWARD', codeValue = 1)
        Long inwardItemId = codesDetailRepository.findByCodesHeaderCodeGroupAndIsActiveTrue("PURCHASE_INWARD")
                .stream()
                .filter(cd -> "1".equals(cd.getCode()))
                .map(CodesDetail::getId)
                .findFirst()
                .orElse(1L);

        // If not found, create new one
        if (itemInventory == null) {
            itemInventory = new ItemInventory();
            itemInventory.setOrderItemId(purchaseInward.getPoTypeId());
            itemInventory.setCompanyId(getCompanyIdFromPurchaseOrder(purchaseInward.getPurchaseOrderId()));
            itemInventory.setFinancialYear(calculateFinancialYear(purchaseInward.getInwardDate()));
            itemInventory.setOrderId(purchaseInward.getPurchaseOrderId());
        }

        itemInventory.setInwardId(purchaseInward.getId());
        itemInventory.setInwardNo(purchaseInward.getInwardNo());
        itemInventory.setInwardItemId(inwardItemId);
        itemInventory.setDcNo(purchaseInward.getDcNumber());
        itemInventory.setDcDate(purchaseInward.getDcDate());
        itemInventory.setInwardDate(purchaseInward.getInwardDate() != null ?
                purchaseInward.getInwardDate().atStartOfDay() : LocalDate.now().atStartOfDay());
        itemInventory.setFinancialYear(calculateFinancialYear(purchaseInward.getInwardDate()));

        itemInventory.setOrderNo(poNumber != null ? poNumber : String.valueOf(purchaseInward.getPurchaseOrderId()));
        itemInventory.setVendorId(purchaseInward.getVendorId());

        ItemInventory savedItemInventory = itemInventoryRepository.save(itemInventory);

        // SYNC: Find and remove any existing details that are NOT in the incoming DTO
        List<ItemInwardStockDetails> currentItemDetails = itemInwardStockDetailsRepository.findByInwardNoAndOrderId(
            purchaseInward.getInwardNo(),
            purchaseInward.getPurchaseOrderId()
        );

        Set<Long> incomingItemIds = new HashSet<>();
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                if (itemDto.getItemStockDetails() != null) {
                    for (ItemInwardStockDetailsDto stockDto : itemDto.getItemStockDetails()) {
                        if (stockDto.getId() != null && stockDto.getId() > 0) {
                            incomingItemIds.add(stockDto.getId());
                        }
                    }
                }
            }
        }

        for (ItemInwardStockDetails detail : currentItemDetails) {
            if (!incomingItemIds.contains(detail.getId())) {
                logger.info("Removing deleted ItemInwardStockDetails ID: {} (not in update payload)", detail.getId());
                itemInwardStockDetailsRepository.delete(detail);
            }
        }

        Set<String> generatedItemLotNos = new HashSet<>();
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                if (itemDto.getItemStockDetails() == null || itemDto.getItemStockDetails().isEmpty()) {
                    continue;
                }

                for (ItemInwardStockDetailsDto stockDto : itemDto.getItemStockDetails()) {
                    // Get itemId from PurchaseOrderItem if not provided in stockDto
                    Long itemId = stockDto.getItemId();
                    if (itemId == null && itemDto.getPoItemId() != null) {
                        itemId = getItemIdFromPurchaseOrderItem(itemDto.getPoItemId());
                    }
                    if (itemId == null) {
                        continue;
                    }
                    stockDto.setLotNo(ensureLotNo(stockDto.getLotNo(), purchaseInward.getCompanyId(), generatedItemLotNos));

                    // Check if we have an ID in the DTO (updating existing record)
                    ItemInwardStockDetails existingDetail = null;
                    if (stockDto.getId() != null && stockDto.getId() > 0) {
                        existingDetail = itemInwardStockDetailsRepository.findById(stockDto.getId()).orElse(null);
                        logger.info("Looking for existing record by ID: {}, Found: {}", stockDto.getId(), existingDetail != null);
                    }
                    
                    if (existingDetail != null) {
                        // UPDATE existing item stock detail
                        ItemInwardStockDetails detail = existingDetail;

                        detail.setOrderItemId(itemDto.getPoItemId());
                        detail.setInwardItemId(inwardItemId);
                        detail.setItemId(itemId);
                        Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForItem(itemId);
                        detail.setUomId(resolvedUomId);
                        detail.setMillId(stockDto.getMillId() != null ? stockDto.getMillId() : itemDto.getMillNameId());
                        detail.setLotNo(stockDto.getLotNo());
                        detail.setRackNo(stockDto.getRackNo());
                        detail.setInwardQty(stockDto.getInwardQty());
                        detail.setInwardBags(stockDto.getInwardBags());
                        detail.setKgsPerBag(stockDto.getKgsPerBag());
                        detail.setConesPerBag(stockDto.getConesPerBag());
                        detail.setConesWeight(stockDto.getConesWeight());
                        detail.setAcceptedQty(stockDto.getAcceptedQty());
                        detail.setAvailableQty(stockDto.getAcceptedQty());
                        detail.setAvailableBags(stockDto.getInwardBags());
                        detail.setRejectedQty(stockDto.getRejectedQty());
                        detail.setWarehouseId(stockDto.getWarehouseId());
                        if (stockDto.getRemark() != null) {
                            detail.setRemark(stockDto.getRemark());
                        }
                        detail.setPoCategoryId(poCategoryId);

                        itemInwardStockDetailsRepository.save(detail);
                        logger.info("Successfully updated ItemInwardStockDetails ID: {} with acceptedQty: {}", detail.getId(), stockDto.getAcceptedQty());
                    } else {
                        // CREATE new item stock detail if it doesn't exist (for newly added items in edit mode)
                        ItemInwardStockDetails newDetail = new ItemInwardStockDetails();
                        newDetail.setItemInventory(savedItemInventory);
                        newDetail.setOrderItemId(itemDto.getPoItemId());
                        newDetail.setInwardItemId(inwardItemId);
                        newDetail.setItemId(itemId);
                        Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForItem(itemId);
                        newDetail.setUomId(resolvedUomId);
                        newDetail.setMillId(stockDto.getMillId() != null ? stockDto.getMillId() : itemDto.getMillNameId());
                        newDetail.setLotNo(stockDto.getLotNo());
                        newDetail.setRackNo(stockDto.getRackNo());
                        newDetail.setInwardQty(stockDto.getInwardQty());
                        newDetail.setInwardBags(stockDto.getInwardBags());
                        newDetail.setKgsPerBag(stockDto.getKgsPerBag());
                        newDetail.setConesPerBag(stockDto.getConesPerBag());
                        newDetail.setConesWeight(stockDto.getConesWeight());
                        newDetail.setAcceptedQty(stockDto.getAcceptedQty());
                        newDetail.setAvailableQty(stockDto.getAcceptedQty());
                        newDetail.setAvailableBags(stockDto.getInwardBags());
                        newDetail.setRejectedQty(stockDto.getRejectedQty());
                        newDetail.setWarehouseId(stockDto.getWarehouseId());
                        if (stockDto.getRemark() != null) {
                            newDetail.setRemark(stockDto.getRemark());
                        }
                        newDetail.setPoCategoryId(poCategoryId);

                        itemInwardStockDetailsRepository.save(newDetail);
                        logger.info("Successfully created new ItemInwardStockDetails for ItemId: {}, LotNo: {}, AcceptedQty: {}", 
                            itemId, stockDto.getLotNo(), stockDto.getAcceptedQty());
                    }
                }
            }
        }
    }
    
    /**
     * Delete purchase inward safely (generic)
     */
    @Transactional
    public void deletePurchaseInward(Long id) {
        PurchaseInward inward = getPurchaseInwardById(id);
        if (!canDeletePurchaseInward(id)) {
            throw new IllegalStateException("Cannot delete Purchase Inward because it is referenced by other entities");
        }
        
        // Delete related inventory records
        deleteRelatedInventoryRecords(id);
        
        try {
            inwardRepository.delete(inward);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Purchase Inward because it is referenced by other entities");
        }
    }

    /**
     * Delete purchase inward by company (from old code)
     */
    @Transactional
    public void deletePurchaseInwardByCompany(Long id, Long companyId) {
        validateCompanyId(companyId);
        
        PurchaseInward existing = getPurchaseInwardByIdAndCompany(id, companyId);
        if (!canDeletePurchaseInward(id)) {
            throw new IllegalStateException("Cannot delete Purchase Inward because it is referenced by other entities");
        }

        // Delete related inventory records
        deleteRelatedInventoryRecords(id);
        
        try {
            inwardRepository.delete(existing);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Purchase Inward because it is referenced by other entities");
        }
    }

    /**
     * Helper to delete related inventory records
     */
    private void deleteRelatedInventoryRecords(Long inwardId) {
        // Delete YarnInventory
        List<YarnInventory> yarnInventories = yarnInventoryRepository.findByInwardIdAndInwardTypeId(inwardId, PO_TYPE_YARN);
        if (!yarnInventories.isEmpty()) {
            yarnInventoryRepository.deleteAll(yarnInventories);
            logger.info("Deleted {} related YarnInventory for Purchase Inward ID: {}", yarnInventories.size(), inwardId);
        }

        // Delete ItemInventory
        List<ItemInventory> itemInventories = itemInventoryRepository.findByInwardId(inwardId);
        if (!itemInventories.isEmpty()) {
            itemInventoryRepository.deleteAll(itemInventories);
            logger.info("Deleted {} related ItemInventory for Purchase Inward ID: {}", itemInventories.size(), inwardId);
        }
    }

    // ============ HELPER METHODS ============

    /**
     * Convert PurchaseInward entity to DTO (merged from old and new)
     */
    private PurchaseInwardDTO convertToDto(PurchaseInward inward) {
        PurchaseInwardDTO dto = new PurchaseInwardDTO(inward);
        
        // Ensure DC fields are mapped
        dto.setDcDate(inward.getDcDate());
        dto.setDcNumber(inward.getDcNumber());
        dto.setPurchaseInwardNo(inward.getInwardNo());
        dto.setCompanyId(inward.getCompanyId());
        
        // Derive fabric code if missing
        if (dto.getFabricCode() == null && inward.getItems() != null && !inward.getItems().isEmpty()) {
            String fabricCode = getFabricCodeFromPurchaseOrderItems(inward.getItems());
            dto.setFabricCode(fabricCode);
        }
        
        // Add yarn stock details if applicable (from new code)
        if (PO_TYPE_YARN.equals(inward.getPoTypeId()) && inward.getItems() != null) {
            List<PurchaseInwardItemDTO> itemDTOs = new ArrayList<>();
            
            for (PurchaseInwardItem item : inward.getItems()) {
                List<YarnStockDetailDTO> yarnStockDetails = null;
                
                if (inward.getInwardNo() != null && inward.getPurchaseOrderId() != null && item.getPurchaseOrderItemId() != null) {
                    List<YarnInwardStockDetails> yarnDetails = yarnInwardStockDetailsRepository.findByInwardNoAndOrderIdWithEagerLoad(
                        inward.getInwardNo(),
                        inward.getPurchaseOrderId(),
                        item.getPurchaseOrderItemId(),
                        inward.getPoTypeId()
                    );
                    if (!yarnDetails.isEmpty()) {
                        yarnStockDetails = yarnDetails.stream()
                            .map(detail -> {
                                YarnStockDetailDTO stockDto = new YarnStockDetailDTO();
                                stockDto.setId(detail.getId());
                                stockDto.setInwardId(inward.getId()); // Use current inward ID
                                stockDto.setItemId(detail.getItemId());
                                stockDto.setOrderItemId(detail.getOrderItemId());
                                stockDto.setInwardTypeId(detail.getInwardTypeId());
                                stockDto.setUomId(detail.getUomId());
                                stockDto.setLotNo(detail.getLotNo());
                                stockDto.setInwardQty(detail.getInwardQty());
                                stockDto.setInwardBags(detail.getInwardBags());
                                stockDto.setKgsPerBag(detail.getKgsPerBag());
                                stockDto.setConesPerBag(detail.getConesPerBag());
                                stockDto.setConesWeight(detail.getConesWeight());
                                stockDto.setAcceptedQty(detail.getAcceptedQty());
                                stockDto.setAvailableQty(detail.getAvailableQty());
                                stockDto.setRejectedQty(detail.getRejectedQty());
                                stockDto.setWarehouseId(detail.getWarehouseId());
                                stockDto.setMillId(detail.getMillId());
                                stockDto.setRemark(detail.getRemark());

                                Long yarnId = getYarnIdFromPurchaseOrderItem(item.getPurchaseOrderItemId());
                                if (yarnId != null) {
                                    Optional<YarnMaster> yarnMaster = yarnMasterRepository.findById(yarnId);
                                    if (yarnMaster.isPresent()) {
                                        stockDto.setYarnCode(yarnMaster.get().getYarnName());
                                    }
                                }
                                
                                return stockDto;
                            })
                            .collect(Collectors.toList());
                    }
                }
                
                PurchaseInwardItemDTO itemDTO = new PurchaseInwardItemDTO(item);
                if (yarnStockDetails != null && !yarnStockDetails.isEmpty()) {
                    itemDTO.setYarnStockDetails(yarnStockDetails);
                }
                
                itemDTOs.add(itemDTO);
            }
            
            dto.setPurchaseInwardItemsDtl(itemDTOs);
        }

        // Add item stock details if applicable
        if (PO_TYPE_ITEM.equals(inward.getPoTypeId()) && inward.getItems() != null) {
            List<PurchaseInwardItemDTO> itemDTOs = new ArrayList<>();
            
            for (PurchaseInwardItem item : inward.getItems()) {
                List<ItemInwardStockDetailsDto> itemStockDetails = null;
                final Long targetItemId = getItemIdFromPurchaseOrderItem(item.getPurchaseOrderItemId());

                if (inward.getInwardNo() != null && inward.getPurchaseOrderId() != null && item.getPurchaseOrderItemId() != null && targetItemId != null) {
                    List<ItemInwardStockDetails> itemDetails = itemInwardStockDetailsRepository.findByInwardNoAndOrderIdAndItemId(
                        inward.getInwardNo(),
                        inward.getPurchaseOrderId(),
                        targetItemId,
                        item.getPurchaseOrderItemId()
                    );
                    if (!itemDetails.isEmpty()) {
                        itemStockDetails = itemDetails.stream()
                            .map(detail -> {
                                ItemInwardStockDetailsDto stockDto = new ItemInwardStockDetailsDto(detail);
                                stockDto.setItemOrderItemId(item.getPurchaseOrderItemId());
                                stockDto.setItemInwardItemId(item.getPurchaseOrderItemId());
                                stockDto.setInwardId(inward.getId());

                                if (detail.getItemId() != null) {
                                    Optional<ItemMaster> itemMaster = itemMasterRepository.findById(detail.getItemId());
                                    if (itemMaster.isPresent()) {
                                        stockDto.setItemName(itemMaster.get().getItemName());
                                    }
                                }
                                
                                return stockDto;
                            })
                            .collect(Collectors.toList());
                    }
                }
                
                PurchaseInwardItemDTO itemDTO = new PurchaseInwardItemDTO(item);
                if (itemStockDetails != null && !itemStockDetails.isEmpty()) {
                    itemDTO.setItemStockDetails(itemStockDetails);
                }
                
                itemDTOs.add(itemDTO);
            }
            
            dto.setPurchaseInwardItemsDtl(itemDTOs);
        }
        
        return dto;
    }

    private boolean matchesFabricType(PurchaseInwardDTO dto, Long fabricTypeId) {
        if (dto == null || dto.getPurchaseInwardItemsDtl() == null) {
            return false;
        }
        
        for (PurchaseInwardItemDTO item : dto.getPurchaseInwardItemsDtl()) {
            if (item.getPieceEntries() == null) {
                continue;
            }
            for (PieceEntryDTO piece : item.getPieceEntries()) {
                if (piece.getFabricTypeId() != null && piece.getFabricTypeId().equals(fabricTypeId)) {
                    return true;
                }
            }
        }
        
        return false;
    }

    /**
     * Helper method to get PO Type name from ID (from old code)
     */
    private String getPoTypeName(Long poTypeId) {
        if (PO_TYPE_YARN.equals(poTypeId)) {
            return "Yarn";
        } else if (PO_TYPE_KNITTED_FABRIC.equals(poTypeId)) {
            return "Knitted Fabric";
        } else if (PO_TYPE_WOVEN_FABRIC.equals(poTypeId)) {
            return "Woven Fabric";
        } else if (PO_TYPE_ITEM.equals(poTypeId)) {
            return "Item";
        }
        return "Unknown";
    }

    private void syncPurchaseInwardPieceEntries(PurchaseInwardItem item,
                                                List<PieceEntryDTO> pieceDtos,
                                                String poTypeName,
                                                String purchaseOrderNo,
                                                Long defaultCompanyId) {
        List<PieceEntry> existingPieces = item.getId() != null
                ? new ArrayList<>(pieceEntryRepository.findByPurchaseInwardItemId(item.getId()))
                : new ArrayList<>();
        List<PieceEntryDTO> requestedPieces = pieceDtos != null ? pieceDtos : Collections.emptyList();
        Set<Long> requestedIds = requestedPieces.stream()
                .map(PieceEntryDTO::getId)
                .filter(id -> id != null && id > 0)
                .collect(Collectors.toSet());
        Set<String> requestedPieceNumbers = requestedPieces.stream()
                .map(PieceEntryDTO::getPieceNumber)
                .filter(pieceNumber -> pieceNumber != null && !pieceNumber.isEmpty())
                .collect(Collectors.toSet());
        List<PieceEntry> retainedPieces = new ArrayList<>();
        List<PieceEntry> managedPieces = item.getPieceEntries();
        if (managedPieces == null) {
            managedPieces = new ArrayList<>();
            item.setPieceEntries(managedPieces);
        }

        for (PieceEntry existingPiece : existingPieces) {
            boolean keepPiece = (existingPiece.getId() != null && requestedIds.contains(existingPiece.getId()))
                    || (existingPiece.getPieceNumber() != null && requestedPieceNumbers.contains(existingPiece.getPieceNumber()));
            if (!keepPiece) {
                validatePieceEntryCanBeRemoved(existingPiece);
                Long existingPieceId = existingPiece.getId();
                if (existingPieceId != null) {
                    fabricInspectionRepository.deleteByPieceEntryId(existingPieceId);
                    managedPieces.removeIf(piece -> piece != null && existingPieceId.equals(piece.getId()));
                }
                pieceEntryRepository.delete(existingPiece);
                logger.info("Removed PieceEntry ID: {} from PurchaseInwardItem ID: {}", existingPiece.getId(), item.getId());
                continue;
            }
            retainedPieces.add(existingPiece);
        }

        for (PieceEntryDTO pieceDto : requestedPieces) {
            PieceEntry existingPiece = null;
            if (pieceDto.getId() != null && pieceDto.getId() > 0) {
                existingPiece = retainedPieces.stream()
                        .filter(piece -> pieceDto.getId().equals(piece.getId()))
                        .findFirst()
                        .orElse(null);
            }
            if (existingPiece == null && pieceDto.getPieceNumber() != null && !pieceDto.getPieceNumber().isEmpty()) {
                existingPiece = retainedPieces.stream()
                        .filter(piece -> pieceDto.getPieceNumber().equals(piece.getPieceNumber()))
                        .findFirst()
                        .orElse(null);
            }
            PieceEntry mappedPiece = mapPieceEntryDtoToEntity(existingPiece, pieceDto, item, poTypeName, purchaseOrderNo, defaultCompanyId);
            PieceEntry savedPiece = pieceEntryRepository.save(mappedPiece);
            if (existingPiece == null) {
                managedPieces.add(savedPiece);
            }
            logger.info("Upserted PieceEntry for PurchaseInwardItem ID: {}, PieceEntry ID: {}", item.getId(), savedPiece.getId());
        }
    }

    private void validatePieceEntryCanBeRemoved(PieceEntry pieceEntry) {
        if (pieceEntry == null) {
            return;
        }
        if ((pieceEntry.getIsInspected() != null && pieceEntry.getIsInspected() == 1)
                || (pieceEntry.getIsPacked() != null && pieceEntry.getIsPacked() == 1)
                || (pieceEntry.getIsDispatched() != null && pieceEntry.getIsDispatched() == 1)
                || (pieceEntry.getIsInvoice() != null && pieceEntry.getIsInvoice() == 1)
                || pieceEntry.getInvoiceId() != null) {
            String pieceNumber = pieceEntry.getPieceNumber() != null ? pieceEntry.getPieceNumber() : "Piece";
            throw new IllegalStateException(pieceNumber + " already inspected, packed, dispatched or invoiced");
        }
    }

    private PieceEntry mapPieceEntryDtoToEntity(PieceEntry existingPiece, PieceEntryDTO pieceDto, PurchaseInwardItem parentItem,
                                                String poTypeName, String purchaseOrderNo, Long defaultCompanyId) {
        PieceEntry piece = existingPiece != null ? existingPiece : new PieceEntry();
        Long poCategoryId = null;
        if (parentItem != null && parentItem.getPurchaseInward() != null && parentItem.getPurchaseInward().getPurchaseOrderId() != null) {
            poCategoryId = purchaseOrderRepository.findById(parentItem.getPurchaseInward().getPurchaseOrderId())
                .map(PurchaseOrder::getPoCategoryId).orElse(null);
        }
        piece.setPoCategoryId(poCategoryId);

        piece.setPurchaseInwardItem(parentItem);
        if (parentItem != null && parentItem.getPurchaseInward() != null) {
            piece.setInwardDate(parentItem.getPurchaseInward().getInwardDate());
            if (parentItem.getPurchaseInward().getId() != null) {
                piece.setInwardId(parentItem.getPurchaseInward().getId());
            }
        }
        piece.setSourceType(SOURCE_PURCHASE_INWARD);
        piece.setPoType(poTypeName);
        piece.setOrderNo(purchaseOrderNo);
        if (parentItem != null && parentItem.getPurchaseInward() != null && parentItem.getPurchaseInward().getPurchaseOrderId() != null) {
            piece.setOrderId(parentItem.getPurchaseInward().getPurchaseOrderId());
        }
        piece.setCompanyId(pieceDto.getCompanyId() != null ? pieceDto.getCompanyId() : defaultCompanyId);

        if (pieceDto.getPieceNumber() != null) {
            piece.setPieceNumber(pieceDto.getPieceNumber());
        }
        piece.setQuantity(pieceDto.getQuantity());
        piece.setWeight(pieceDto.getWeight());
        piece.setCost(pieceDto.getCost());
        piece.setRemarks(pieceDto.getRemarks());
        Long fabricTypeId = pieceDto.getFabricTypeId();
        if (fabricTypeId == null && parentItem != null && parentItem.getPurchaseOrderItemId() != null) {
            fabricTypeId = getFabricTypeIdFromPurchaseOrderItem(parentItem.getPurchaseOrderItemId());
        }
        piece.setFabricTypeId(fabricTypeId);
        piece.setFabricMasterId(pieceDto.getFabricMasterId());
        piece.setIsFactoryFabric(pieceDto.getIsFactoryFabric() != null ? pieceDto.getIsFactoryFabric() : 0);
        piece.setIsLastPiece(pieceDto.getIsLastPiece() != null ? pieceDto.getIsLastPiece() : 0);
        piece.setLoomId(pieceDto.getLoomId());
        piece.setBeamNo(pieceDto.getBeamNo());
        piece.setFlangeId(pieceDto.getFlangeId());
        piece.setDoffingPicks(pieceDto.getDoffingPicks());
        piece.setDoffingDate(pieceDto.getDoffingDate());
        piece.setDoffingNo(pieceDto.getDoffingNo());
        piece.setPieceMeter(pieceDto.getPieceMeter());
        piece.setBeamId(pieceDto.getBeamId());
        piece.setActiveFlag(pieceDto.getActiveFlag() != null ? pieceDto.getActiveFlag() : (piece.getActiveFlag() != null ? piece.getActiveFlag() : true));
        piece.setFabricId(piece.getFabricMasterId() != null ? piece.getFabricMasterId() : pieceDto.getFabricId());
        piece.setInspectionStatus(pieceDto.getInspectionStatus() != null ? pieceDto.getInspectionStatus() : (piece.getInspectionStatus() != null ? piece.getInspectionStatus() : "PENDING"));
        piece.setIsInspected(pieceDto.getIsInspected() != null ? pieceDto.getIsInspected() : 0);
        piece.setInspectionId(pieceDto.getInspectionId());
        piece.setGradeId(pieceDto.getGradeId());
        piece.setInspectedMtr(pieceDto.getInspectedMtr());
        piece.setInspectedNtWt(pieceDto.getInspectedNtWt());
        piece.setInspectedAvgWt(pieceDto.getInspectedAvgWt());
        piece.setDefectType(pieceDto.getDefectType());
        piece.setDefectPoints(pieceDto.getDefectPoints());
        piece.setPieceNo(pieceDto.getPieceNo());
        piece.setPiecePrefix(pieceDto.getPiecePrefix());
        piece.setInspectorId(pieceDto.getInspectorId());
        piece.setIsPacked(pieceDto.getIsPacked() != null ? pieceDto.getIsPacked() : 0);
        piece.setPackingId(pieceDto.getPackingId());
        piece.setIsDispatched(pieceDto.getIsDispatched() != null ? pieceDto.getIsDispatched() : 0);
        piece.setDispatchId(pieceDto.getDispatchId());
        piece.setIsInvoice(pieceDto.getIsInvoice() != null ? pieceDto.getIsInvoice() : 0);
        piece.setInvoiceId(pieceDto.getInvoiceId());
        piece.setParentPieceId(pieceDto.getParentPieceId());
        piece.setJoinedPieceId(pieceDto.getJoinedPieceId());
        piece.setIsParentPiece(pieceDto.getIsParentPiece() != null ? pieceDto.getIsParentPiece() : 0);
        piece.setIsJoined(pieceDto.getIsJoined() != null ? pieceDto.getIsJoined() : 0);
        piece.setOperationId(pieceDto.getOperationId() != null ? pieceDto.getOperationId() : 0L);
        piece.setModeId(pieceDto.getModeId() != null ? pieceDto.getModeId() : 0L);
        piece.setOrderTypeId(pieceDto.getOrderTypeId());
        piece.setIsStockTransfer(pieceDto.getIsStockTransfer() != null ? pieceDto.getIsStockTransfer() : false);
        piece.setStockTransferId(pieceDto.getStockTransferId());
        piece.setGrossWt(pieceDto.getGrossWt());
        piece.setDcNumber(pieceDto.getDcNumber() != null ? pieceDto.getDcNumber() : (parentItem != null && parentItem.getPurchaseInward() != null ? parentItem.getPurchaseInward().getDcNumber() : null));
        piece.setDcDate(pieceDto.getDcDate() != null ? pieceDto.getDcDate() : (parentItem != null && parentItem.getPurchaseInward() != null ? parentItem.getPurchaseInward().getDcDate() : null));

        if (pieceDto.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(pieceDto.getWarehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + pieceDto.getWarehouseId()));
            piece.setWarehouse(warehouse);
        } else if (pieceDto.getWarehouseId() == null && existingPiece == null) {
            piece.setWarehouse(null);
        }

        return piece;
    }

    private Long normalizeId(Long id) {
        return (id == null || id <= 0) ? null : id;
    }

    private String getFabricCodeFromPurchaseOrderItem(Long poItemId) {
        if (poItemId == null || poItemId <= 0) return null;
        Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
        if (poItem.isPresent()) {
            String fabricCode = poItem.get().getFabricCode();
            if (fabricCode == null || fabricCode.isEmpty()) {
                // If fabric code is null, check if it's an Item and fetch from ItemMaster
                Long itemId = poItem.get().getItemId();
                if (itemId != null) {
                    return itemMasterRepository.findById(itemId)
                            .map(ItemMaster::getItemCode)
                            .orElse(null);
                }
            }
            return fabricCode;
        }
        return null;
    }

    private Long getFabricTypeIdFromPurchaseOrderItem(Long poItemId) {
        if (poItemId == null || poItemId <= 0) return null;
        Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
        return poItem.map(PurchaseOrderItem::getFabricTypeId).orElse(null);
    }

    private Long getMillNameIdFromPurchaseOrderItem(Long poItemId) {
        if (poItemId == null || poItemId <= 0) return null;
        Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
        Long millNameId = poItem.map(PurchaseOrderItem::getMillNameId).orElse(null);
        logger.info("Fetched PurchaseOrderItem ID: {}, millNameId: {}", poItemId, millNameId);
        return millNameId;
    }

    private Long getYarnIdFromPurchaseOrderItem(Long poItemId) {
        if (poItemId == null || poItemId <= 0) return null;
        Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
        return poItem.map(PurchaseOrderItem::getYarnId).orElse(null);
    }

    private Long getItemIdFromPurchaseOrderItem(Long poItemId) {
        if (poItemId == null || poItemId <= 0) return null;
        Optional<PurchaseOrderItem> poItem = purchaseOrderItemRepository.findById(poItemId);
        return poItem.map(PurchaseOrderItem::getItemId).orElse(null);
    }

    private String getPurchaseOrderNo(Long purchaseOrderId) {
        if (purchaseOrderId == null) {
            return null;
        }
        
        Optional<PurchaseOrder> po = purchaseOrderRepository.findById(purchaseOrderId);
        if (po.isPresent()) {
            return po.get().getPoNo();
        }
        return null;
    }

    private String getFabricCodeFromPurchaseOrderItems(List<PurchaseInwardItem> items) {
        for (PurchaseInwardItem item : items) {
            if (item.getPurchaseOrderItemId() != null) {
                String fabricCode = getFabricCodeFromPurchaseOrderItem(item.getPurchaseOrderItemId());
                if (fabricCode != null) return fabricCode;
            }
        }
        return null;
    }

    private void validateCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new IllegalArgumentException("Company ID is required");
        }
    }

    // ============ REMAINING METHODS FROM NEW CODE ============
    
    /**
     * Create yarn inventory records for yarn purchase inward via yarn stock details.
     */
    private void createYarnInventoryRecords(PurchaseInward purchaseInward, PurchaseInwardDTO dto) {
        Long poCategoryId = null;
        if (purchaseInward.getPurchaseOrderId() != null) {
            poCategoryId = purchaseOrderRepository.findById(purchaseInward.getPurchaseOrderId())
                .map(PurchaseOrder::getPoCategoryId).orElse(null);
        }
        YarnInventory yarnInventory = new YarnInventory();
        yarnInventory.setInwardId(purchaseInward.getId());

        yarnInventory.setInwardTypeId(purchaseInward.getPoTypeId());

        Long companyId = getCompanyIdFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
        yarnInventory.setCompanyId(companyId);
        yarnInventory.setInwardDate(purchaseInward.getInwardDate() != null ?
                purchaseInward.getInwardDate().atStartOfDay() : LocalDate.now().atStartOfDay());

        LocalDate inwardDate = purchaseInward.getInwardDate();

        String financialYear = calculateFinancialYear(inwardDate);
        yarnInventory.setFinancialYear(financialYear);

        yarnInventory.setInwardNo(purchaseInward.getInwardNo());
        yarnInventory.setDcNo(purchaseInward.getDcNumber());
        yarnInventory.setDcDate(purchaseInward.getDcDate());
        yarnInventory.setOrderId(purchaseInward.getPurchaseOrderId());

        String poNumber = getPONumberFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
        yarnInventory.setOrderNo(poNumber != null ? poNumber : String.valueOf(purchaseInward.getPurchaseOrderId()));

        yarnInventory.setVendorId(purchaseInward.getVendorId());

        // Set warehouseId from first available stock detail
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                if (itemDto.getYarnStockDetails() != null) {
                    for (YarnStockDetailDTO stockDto : itemDto.getYarnStockDetails()) {
                        if (stockDto.getWarehouseId() != null) {
                            yarnInventory.setWarehouseId(stockDto.getWarehouseId());
                            break;
                        }
                    }
                }
                if (yarnInventory.getWarehouseId() != null) break;
            }
        }

        YarnInventory savedYarnInventory = yarnInventoryRepository.save(yarnInventory);

        List<YarnInwardStockDetails> details = new ArrayList<>();

        Set<String> generatedLotNos = new HashSet<>();
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                if (itemDto.getYarnStockDetails() == null || itemDto.getYarnStockDetails().isEmpty()) {
                    continue;
                }

                for (YarnStockDetailDTO stockDto : itemDto.getYarnStockDetails()) {
                    if (stockDto.getItemId() == null) {
                        continue;
                    }
                    stockDto.setLotNo(ensureLotNo(stockDto.getLotNo(), purchaseInward.getCompanyId(), generatedLotNos));

                    YarnInwardStockDetails detail = new YarnInwardStockDetails();

                    detail.setInwardTypeId(purchaseInward.getPoTypeId());
                    detail.setItemId(stockDto.getItemId());
                    Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForYarn(stockDto.getItemId());
                    detail.setUomId(resolvedUomId);
                    detail.setMillId(stockDto.getMillId() != null ? stockDto.getMillId() : itemDto.getMillNameId());
                    detail.setLotNo(stockDto.getLotNo());
                    detail.setInwardQty(stockDto.getInwardQty());
                    detail.setInwardBags(stockDto.getInwardBags());
                    detail.setKgsPerBag(stockDto.getKgsPerBag());
                    detail.setConesPerBag(stockDto.getConesPerBag());
                    detail.setConesWeight(stockDto.getConesWeight());
                    detail.setAcceptedQty(stockDto.getAcceptedQty());
                    detail.setAvailableQty(stockDto.getAcceptedQty());
                    detail.setAvailableBags(stockDto.getInwardBags());
                    BigDecimal inwardBagsForCones = stockDto.getInwardBags() != null ? stockDto.getInwardBags() : BigDecimal.ZERO;
                    BigDecimal conesPerBagForCones = stockDto.getConesPerBag() != null ? stockDto.getConesPerBag() : BigDecimal.ZERO;
                    detail.setAvailableCones(inwardBagsForCones.multiply(conesPerBagForCones));
                    detail.setRejectedQty(stockDto.getRejectedQty());
                    detail.setWarehouseId(stockDto.getWarehouseId());
                    detail.setOrderItemId(itemDto.getPoItemId());
                    detail.setYarnTypeId(itemDto.getYarnTypeId());
                    detail.setRemark(stockDto.getRemark());
                    detail.setYarnInventory(savedYarnInventory);
                    detail.setPoCategoryId(poCategoryId);
                    detail.setMillInvNo(purchaseInward.getMillInvNo());
                    detail.setMillDate(purchaseInward.getMillDate());

                    details.add(detail);
                }
            }
        }

        if (!details.isEmpty()) {
            savedYarnInventory.setDetails(details);
            yarnInventoryRepository.save(savedYarnInventory);
        }
    }

    /**
     * Create item inventory records for item purchase inward via item stock details.
     */
    private void createItemInventoryRecords(PurchaseInward purchaseInward, PurchaseInwardDTO dto) {
        Long poCategoryId = null;
        if (purchaseInward.getPurchaseOrderId() != null) {
            poCategoryId = purchaseOrderRepository.findById(purchaseInward.getPurchaseOrderId())
                .map(PurchaseOrder::getPoCategoryId).orElse(null);
        }
        ItemInventory itemInventory = new ItemInventory();

        itemInventory.setInwardId(purchaseInward.getId());
        itemInventory.setOrderItemId(purchaseInward.getPoTypeId());
        
        // Fetch inwardItemId from CodesDetail (codeType = 'PURCHASE_INWARD', codeValue = 1)
        Long inwardItemId = codesDetailRepository.findByCodesHeaderCodeGroupAndIsActiveTrue("PURCHASE_INWARD")
                .stream()
                .filter(cd -> "1".equals(cd.getCode()))
                .map(CodesDetail::getId)
                .findFirst()
                .orElse(1L);
        itemInventory.setInwardItemId(inwardItemId);

        Long companyId = getCompanyIdFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
        itemInventory.setCompanyId(companyId);
        itemInventory.setInwardDate(purchaseInward.getInwardDate() != null ?
                purchaseInward.getInwardDate().atStartOfDay() : LocalDate.now().atStartOfDay());

        LocalDate inwardDate = purchaseInward.getInwardDate();

        String financialYear = calculateFinancialYear(inwardDate);
        itemInventory.setFinancialYear(financialYear);

        // Store inwardNo from PurchaseInward
        String poNumber = purchaseInward.getPurchaseOrderNo();
        if (poNumber == null) {
            poNumber = getPONumberFromPurchaseOrder(purchaseInward.getPurchaseOrderId());
        }
        itemInventory.setInwardNo(purchaseInward.getInwardNo());
        
        itemInventory.setDcNo(purchaseInward.getDcNumber());
        itemInventory.setDcDate(purchaseInward.getDcDate());
        itemInventory.setOrderId(purchaseInward.getPurchaseOrderId());
        itemInventory.setOrderNo(poNumber != null ? poNumber : String.valueOf(purchaseInward.getPurchaseOrderId()));
        itemInventory.setVendorId(purchaseInward.getVendorId());

        ItemInventory savedItemInventory = itemInventoryRepository.save(itemInventory);

        List<ItemInwardStockDetails> details = new ArrayList<>();

        Set<String> generatedItemLotNos = new HashSet<>();
        if (dto.getPurchaseInwardItemsDtl() != null) {
            for (PurchaseInwardItemDTO itemDto : dto.getPurchaseInwardItemsDtl()) {
                if (itemDto.getItemStockDetails() == null || itemDto.getItemStockDetails().isEmpty()) {
                    continue;
                }

                for (ItemInwardStockDetailsDto stockDto : itemDto.getItemStockDetails()) {
                    // Get itemId from PurchaseOrderItem if not provided in stockDto
                    Long itemId = stockDto.getItemId();
                    if (itemId == null && itemDto.getPoItemId() != null) {
                        itemId = getItemIdFromPurchaseOrderItem(itemDto.getPoItemId());
                    }
                    if (itemId == null) {
                        continue;
                    }
                    stockDto.setLotNo(ensureLotNo(stockDto.getLotNo(), purchaseInward.getCompanyId(), generatedItemLotNos));

                    ItemInwardStockDetails detail = new ItemInwardStockDetails();
                    detail.setOrderItemId(itemDto.getPoItemId());
                    detail.setInwardItemId(inwardItemId);
                    detail.setItemId(itemId);
                    Long resolvedUomId = stockDto.getUomId() != null ? stockDto.getUomId() : resolveUomIdForItem(itemId);
                    detail.setUomId(resolvedUomId);
                    detail.setMillId(itemDto.getMillNameId());
                    detail.setLotNo(stockDto.getLotNo());
                    detail.setInwardQty(stockDto.getInwardQty());
                    detail.setInwardBags(stockDto.getInwardBags());
                    detail.setKgsPerBag(stockDto.getKgsPerBag());
                    detail.setConesPerBag(stockDto.getConesPerBag());
                    detail.setConesWeight(stockDto.getConesWeight());
                    detail.setAcceptedQty(stockDto.getAcceptedQty());
                    detail.setAvailableQty(stockDto.getAcceptedQty());
                    detail.setAvailableBags(stockDto.getInwardBags());
                    detail.setRejectedQty(stockDto.getRejectedQty());
                    detail.setWarehouseId(stockDto.getWarehouseId());
                    if (stockDto.getRemark() != null) {
                        detail.setRemark(stockDto.getRemark());
                    }
                    detail.setItemInventory(savedItemInventory);
                    detail.setPoCategoryId(poCategoryId);

                    details.add(detail);
                }
            }
        }

        if (!details.isEmpty()) {
            savedItemInventory.setDetails(details);
            itemInventoryRepository.save(savedItemInventory);
        }
    }

    /**
     * Calculate financial year based on the date (Indian FY: Apr-Mar).
     * Returns the fiscal year as String (e.g., "2024-25" for FY 2024-25).
     */
    private String calculateFinancialYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        
        int year = date.getYear();
        int month = date.getMonthValue();
        
        // Indian financial year: April to March
        // If month is April (4) or later, FY is currentYear-nextYear
        // If month is January-March, FY is previousYear-currentYear
        if (month >= 4) {
            // FY: currentYear - nextYear (e.g., 2024-25 for dates between Apr 2024 - Mar 2025)
            int nextYear = year + 1;
            return String.format("%d-%d", year, nextYear % 100);
        } else {
            // FY: previousYear - currentYear (e.g., 2023-24 for dates between Jan 2024 - Mar 2024)
            int previousYear = year - 1;
            return String.format("%d-%d", previousYear, year % 100);
        }
    }

    private String ensureLotNo(String incomingLotNo, Long companyId, Set<String> reservedLotNos) {
        if (StringUtils.hasText(incomingLotNo)) {
            String trimmed = incomingLotNo.trim();
            reservedLotNos.add(trimmed.toUpperCase(Locale.ROOT));
            return trimmed;
        }
        return generateUniqueLotNo(companyId, reservedLotNos);
    }

    private String generateUniqueLotNo(Long companyId, Set<String> reservedLotNos) {
        int sequence = 1;
        while (sequence <= 999999) {
            String candidateLotNo = String.format("XX-LOT-%03d", sequence++);
            String normalized = candidateLotNo.toUpperCase(Locale.ROOT);
            if (reservedLotNos.contains(normalized)) {
                continue;
            }

            boolean exists = companyId != null
                    ? !yarnInwardStockDetailsRepository.findByCompanyIdAndLotNoIgnoreCase(companyId, candidateLotNo).isEmpty()
                    || !itemInwardStockDetailsRepository.findByCompanyIdAndLotNoIgnoreCase(companyId, candidateLotNo).isEmpty()
                    : !yarnInwardStockDetailsRepository.findByLotNoIgnoreCase(candidateLotNo).isEmpty()
                    || !itemInwardStockDetailsRepository.findByLotNoIgnoreCase(candidateLotNo).isEmpty();

            if (!exists) {
                reservedLotNos.add(normalized);
                logger.info("Auto-generated lotNo: {}", candidateLotNo);
                return candidateLotNo;
            }
        }
        throw new IllegalStateException("Unable to generate unique lot number in format XX-LOT-###");
    }

    /**
     * Generate yarn inward number (YI/CMP/FY/0001 format).
     */
    private String generateYarnInwardNo(Long companyId, LocalDate inwardDate) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
        String cmpCode = company.getCompanyCode();

        String fy = calculateFinancialYear(inwardDate);
        String prefix = "YI/" + cmpCode + "/" + fy + "/";

        Integer maxNum = yarnInventoryRepository.findMaxOrderNumberByPrefix(prefix, companyId);
        int nextNum = (maxNum != null ? maxNum : 0) + 1;

        return prefix + String.format("%04d", nextNum);
    }

    /**
     * Generate item inward number (II/CMP/FY/0001 format).
     */
    private String generateItemInwardNo(Long companyId, LocalDate inwardDate) {
        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
        String cmpCode = company.getCompanyCode();

        String fy = calculateFinancialYear(inwardDate);
        String prefix = "II/" + cmpCode + "/" + fy + "/";

        Integer maxNum = itemInventoryRepository.findMaxOrderNumberByPrefix(prefix, companyId);
        int nextNum = (maxNum != null ? maxNum : 0) + 1;

        return prefix + String.format("%04d", nextNum);
    }

    /**
     * Helper: Fetch company ID from purchase order.
     */
    private Long getCompanyIdFromPurchaseOrder(Long purchaseOrderId) {
        if (purchaseOrderId == null || purchaseOrderId <= 0) return null;
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
        return purchaseOrder.map(PurchaseOrder::getCompanyId).orElse(null);
    }

    /**
     * Helper: Fetch PO number from purchase order.
     * Since getOrderNo() is not available, we'll use the ID to generate a PO number
     */
    private String getPONumberFromPurchaseOrder(Long purchaseOrderId) {
        if (purchaseOrderId == null || purchaseOrderId <= 0) return null;
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
        if (purchaseOrder.isPresent()) {
            PurchaseOrder po = purchaseOrder.get();
            
            // Return actual PO number from entity
            if (po.getPoNo() != null && !po.getPoNo().isEmpty()) {
                return po.getPoNo();
            }
            
            // Fallback - Generate a formatted PO number based on ID and company
            String financialYear = calculateFinancialYear(LocalDate.now());
            return String.format("PO/%s/%s/%04d", 
                getCompanyCodeFromPurchaseOrder(purchaseOrderId),
                financialYear, 
                po.getId());
        }
        return null;
    }

    /**
     * Helper: Fetch company code from purchase order.
     */
    private String getCompanyCodeFromPurchaseOrder(Long purchaseOrderId) {
        if (purchaseOrderId == null || purchaseOrderId <= 0) return "CMP";
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId);
        if (purchaseOrder.isPresent()) {
            Long companyId = purchaseOrder.get().getCompanyId();
            if (companyId != null) {
                Optional<com.tech3.erp.entity.Company> company = companyRepository.findById(companyId);
                return company.map(com.tech3.erp.entity.Company::getCompanyCode).orElse("CMP");
            }
        }
        return "CMP";
    }

    //    private void checkPurchaseInwardReferences(Long purchaseInwardId) {
    //     List<PurchaseInwardItem> inwardItems = purchaseInwardItemRepository.findByPurchaseInwardId(purchaseInwardId);
        
    //     if (inwardItems != null && !inwardItems.isEmpty()) {
    //         for (PurchaseInwardItem item : inwardItems) {
    //             Long pieceEntryCount = pieceEntryRepository.countByPurchaseInwardItemId(item.getId());
    //             if (pieceEntryCount > 0) {
    //                 throw new IllegalStateException("The data is already exceeded to other modules - cannot perform operation.");
    //             }
    //         }
    //     }
    // }
    
    public boolean canDeletePurchaseInward(Long purchaseInwardId) {
        List<PurchaseInwardItem> inwardItems = purchaseInwardItemRepository.findByPurchaseInwardId(purchaseInwardId);
        
        if (inwardItems != null && !inwardItems.isEmpty()) {
            for (PurchaseInwardItem item : inwardItems) {
                // Existing validation: Check piece entries
                Long pieceEntryCount = pieceEntryRepository.countByPurchaseInwardItemId(item.getId());
                if (pieceEntryCount > 0) {
                    return false;
                }
                
                // Enhanced validation: Check yarn issues for both purchaseInwardId and yarnName combination
                // Get yarn names from lot entries associated with this purchase inward item
                List<LotEntry> lotEntries = item.getLotEntries();
                if (lotEntries != null && !lotEntries.isEmpty()) {
                    for (LotEntry lotEntry : lotEntries) {
                        if (lotEntry.getYarnId() != null) {
                            // Get yarn name from YarnMaster
                            Optional<YarnMaster> yarnMaster = yarnMasterRepository.findById(lotEntry.getYarnId());
                            if (yarnMaster.isPresent() && yarnMaster.get().getYarnName() != null) {
                                String yarnName = yarnMaster.get().getYarnName();
                                
                                // Check YarnIssue records
                                Long yarnIssueCount = yarnIssueRepository.countActiveByInwardIdAndYarnName(
                                    purchaseInwardId, yarnName);
                                if (yarnIssueCount > 0) {
                                    return false;
                                }
                                
                                // Check SizingYarnIssue records
                                Long sizingYarnIssueCount = sizingYarnIssueRepository.countActiveByInwardIdAndYarnName(
                                    purchaseInwardId, yarnName);
                                if (sizingYarnIssueCount > 0) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    public java.util.Map<Long, Boolean> canDeletePurchaseInwardItems(Long purchaseInwardId) {
        java.util.Map<Long, Boolean> itemsDeletionStatus = new java.util.HashMap<>();
        
        PurchaseInward inward = inwardRepository.findById(purchaseInwardId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Inward not found with id: " + purchaseInwardId));
        
        List<PurchaseInwardItem> inwardItems = inward.getItems();
        
        if (inwardItems != null && !inwardItems.isEmpty()) {
            for (PurchaseInwardItem item : inwardItems) {
                boolean canDelete = true;
                
                Long pieceEntryCount = pieceEntryRepository.countByPurchaseInwardItemId(item.getId());
                if (pieceEntryCount > 0) {
                    canDelete = false;
                }
                
                itemsDeletionStatus.put(item.getId(), canDelete);
            }
        }
        
        return itemsDeletionStatus;
    }
    
    public boolean canEditPurchaseInward(Long purchaseInwardId) {
        List<PurchaseInwardItem> inwardItems = purchaseInwardItemRepository.findByPurchaseInwardId(purchaseInwardId);
        
        if (inwardItems != null && !inwardItems.isEmpty()) {
            for (PurchaseInwardItem item : inwardItems) {
                Long pieceEntryCount = pieceEntryRepository.countByPurchaseInwardItemId(item.getId());
                if (pieceEntryCount > 0) {
                    return false;
                }
            }
        }
        
        return true;
    }


    /**
     * Convert PurchaseInward entity to DTO with yarn stock details included.
     */
    public PurchaseInwardDTO convertToDetailedDTO(PurchaseInward inward) {
        PurchaseInwardDTO dto = new PurchaseInwardDTO(inward);
        
        if (inward.getItems() != null) {
            List<PurchaseInwardItemDTO> itemDTOs = new ArrayList<>();
            
            for (PurchaseInwardItem item : inward.getItems()) {
                List<YarnStockDetailDTO> yarnStockDetails = null;
                List<ItemInwardStockDetailsDto> itemStockDetails = null;
                // Handle Yarn Stock Details - Filter by specific PurchaseInward (inward number and order ID)
                // Use INNER JOIN query to ensure proper relationship loading and prevent cross-inward data
                List<YarnInwardStockDetails> yarnDetails = new ArrayList<>();
                if (item.getPurchaseOrderItemId() != null && inward.getInwardNo() != null && inward.getPurchaseOrderId() != null) {
                    // Use eager-loaded query with INNER JOIN to prevent null YarnInventory issues
                    yarnDetails = yarnInwardStockDetailsRepository.findByInwardNoAndOrderIdWithEagerLoad(
                        inward.getInwardNo(),
                        inward.getPurchaseOrderId(),
                        item.getPurchaseOrderItemId(),
                        inward.getPoTypeId()
                    );
                }
                // NO FALLBACK - If primary query returns empty, it means there's no data for this specific inward
                // This prevents cross-inward contamination
                
                if (!yarnDetails.isEmpty()) {
                    yarnStockDetails = yarnDetails.stream()
                        .map(detail -> {
                            YarnStockDetailDTO stockDto = new YarnStockDetailDTO();
                            stockDto.setId(detail.getId());
                            stockDto.setInwardId(inward.getId());
                            stockDto.setItemId(detail.getItemId());
                            stockDto.setOrderItemId(detail.getOrderItemId());
                            stockDto.setInwardTypeId(detail.getInwardTypeId());
                            stockDto.setUomId(detail.getUomId());
                            stockDto.setLotNo(detail.getLotNo());
                            stockDto.setInwardQty(detail.getInwardQty());
                            stockDto.setInwardBags(detail.getInwardBags());
                            stockDto.setKgsPerBag(detail.getKgsPerBag());
                            stockDto.setConesPerBag(detail.getConesPerBag());
                            stockDto.setConesWeight(detail.getConesWeight());
                            stockDto.setAcceptedQty(detail.getAcceptedQty());
                            stockDto.setRejectedQty(detail.getRejectedQty());
                            stockDto.setWarehouseId(detail.getWarehouseId());
                            stockDto.setYarnTypeId(detail.getYarnTypeId());
                            stockDto.setRemark(detail.getRemark());
                            stockDto.setMillId(detail.getMillId());
                           
                            Long yarnId = getYarnIdFromPurchaseOrderItem(item.getPurchaseOrderItemId());
                            if (yarnId != null) {
                                Optional<YarnMaster> yarnMaster = yarnMasterRepository.findById(yarnId);
                                if (yarnMaster.isPresent()) {
                                    stockDto.setYarnCode(yarnMaster.get().getYarnName());
                                }
                            }
                            
                            return stockDto;
                        })
                        .collect(Collectors.toList());
                }

                // Handle Item Stock Details
                List<ItemInwardStockDetails> itemDetails = new ArrayList<>();
                final Long targetItemId = getItemIdFromPurchaseOrderItem(item.getPurchaseOrderItemId());
                
                if (inward.getInwardNo() != null && inward.getPurchaseOrderId() != null && item.getPurchaseOrderItemId() != null) {
                    itemDetails = itemInwardStockDetailsRepository.findByInwardNoAndOrderIdAndItemId(
                        inward.getInwardNo(),
                        inward.getPurchaseOrderId(),
                        targetItemId,
                        item.getPurchaseOrderItemId()
                    );
                }
                
                if (!itemDetails.isEmpty()) {
                    itemStockDetails = itemDetails.stream()
                        .map(detail -> {
                            ItemInwardStockDetailsDto stockDto = new ItemInwardStockDetailsDto(detail);
                            stockDto.setItemOrderItemId(item.getPurchaseOrderItemId());
                            stockDto.setItemInwardItemId(item.getPurchaseOrderItemId()); // Align with frontend expectations
                            stockDto.setInwardId(inward.getId());

                            if (detail.getItemId() != null) {
                                Optional<ItemMaster> itemMaster = itemMasterRepository.findById(detail.getItemId());
                                if (itemMaster.isPresent()) {
                                    stockDto.setItemName(itemMaster.get().getItemName());
                                }
                            }
                            
                            return stockDto;
                        })
                        .collect(Collectors.toList());
                }
                
                PurchaseInwardItemDTO itemDTO = new PurchaseInwardItemDTO(item);
                if (yarnStockDetails != null && !yarnStockDetails.isEmpty()) {
                    itemDTO.setYarnStockDetails(yarnStockDetails);
                } else if (itemStockDetails != null && !itemStockDetails.isEmpty()) {
                    itemDTO.setItemStockDetails(itemStockDetails);
                }
                
                itemDTOs.add(itemDTO);
            }
            
            dto.setPurchaseInwardItemsDtl(itemDTOs);
        }
        
        return dto;
    }


    /**
     * Get YarnInwardStockDetails filtered by various criteria.
     * Supports filtering by itemId/inwardTypeId and/or purchaseOrderId/poTypeId.
     * Calculates availableQty as acceptedQty - issuedQty for accurate stock levels.
     */
    public List<YarnInwardStockDetailsDto> getYarnInwardStockDetailsByFilters(Long itemId, Long inwardTypeId, Long purchaseOrderId, Long poTypeId, Long companyId) {
        List<YarnInwardStockDetails> entities;

        // Determine filtering strategy based on provided parameters
        if (itemId != null && inwardTypeId != null) {
            entities = yarnInwardStockDetailsRepository.findByItemIdAndInwardTypeId(itemId, inwardTypeId);
        } else if (purchaseOrderId != null && poTypeId != null) {
            entities = yarnInwardStockDetailsRepository.findByPurchaseOrderIdAndPoTypeId(purchaseOrderId, poTypeId);
        } else if (itemId != null) {
            entities = yarnInwardStockDetailsRepository.findByItemId(itemId);
        } else if (purchaseOrderId != null) {
            entities = yarnInwardStockDetailsRepository.findByPurchaseOrderId(purchaseOrderId);
        } else {
            entities = java.util.Collections.emptyList();
        }

        List<YarnInwardStockDetailsDto> results = new ArrayList<>();

        for (YarnInwardStockDetails entity : entities) {
            if (companyId != null) {
                Long detailCompanyId = entity.getYarnInventory() != null ? entity.getYarnInventory().getCompanyId() : null;
                if (!companyId.equals(detailCompanyId)) {
                    continue;
                }
            }

            YarnInwardStockDetailsDto dto = new YarnInwardStockDetailsDto(entity);

            BigDecimal acceptedQty = entity.getAcceptedQty() != null ? entity.getAcceptedQty() : BigDecimal.ZERO;
            BigDecimal totalIssuedQty = calculateTotalIssuedQuantity(entity.getOrderItemId());
            BigDecimal availableQty = acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
            if (availableQty.compareTo(BigDecimal.ZERO) < 0) {
                availableQty = BigDecimal.ZERO;
            }

            BigDecimal inwardBags = entity.getInwardBags() != null ? entity.getInwardBags() : BigDecimal.ZERO;
            BigDecimal totalIssuedBags = calculateTotalIssuedBags(entity.getOrderItemId());
            BigDecimal availableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);
            if (availableBags.compareTo(BigDecimal.ZERO) < 0) {
                availableBags = BigDecimal.ZERO;
            }

            dto.setAvailableQty(availableQty);
            dto.setAvailableBags(availableBags);

            results.add(dto);
        }

        return results;
    }

    /**
     * Calculate total issued quantity for a yarn inward stock detail by summing all outward stock details.
     */
    private BigDecimal calculateTotalIssuedQuantity(Long orderItemId) {
        if (orderItemId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByOrderItemId(orderItemId);
        return totalIssued != null ? totalIssued : BigDecimal.ZERO;
    }

    private BigDecimal calculateTotalIssuedBags(Long orderItemId) {
        if (orderItemId == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal totalIssued = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByOrderItemId(orderItemId);
        return totalIssued != null ? totalIssued : BigDecimal.ZERO;
    }

    private Long resolveUomIdForYarn(Long yarnId) {
        if (yarnId == null) {
            return getDefaultUomId();
        }

        List<YarnItemEntity> yarnItems = yarnItemRepository.findByYarnId(yarnId);
        if (!yarnItems.isEmpty() && yarnItems.get(0).getUomId() != null) {
            return yarnItems.get(0).getUomId();
        }

        return getDefaultUomId();
    }

    private Long getDefaultUomId() {
        List<Uom> allUoms = uomRepository.findAll();
        if (!allUoms.isEmpty()) {
            return allUoms.get(0).getId();
        }
        return null;
    }

    private Long resolveUomIdForItem(Long itemId) {
        if (itemId == null) {
            return getDefaultUomId();
        }

        Optional<ItemMaster> itemMaster = itemMasterRepository.findById(itemId);
        if (itemMaster.isPresent() && itemMaster.get().getUomName() != null) {
            Optional<Uom> uom = uomRepository.findByUomName(itemMaster.get().getUomName());
            if (uom.isPresent()) {
                return uom.get().getId();
            }
        }

        return getDefaultUomId();
    }


 /**
     * Permanently delete a purchase inward entry and all related stock records
     */
    @Transactional
    public void hardDeletePurchaseInward(Long id) {
        PurchaseInward inward = inwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Inward not found with id: " + id));

        performHardDelete(inward);
    }

    @Transactional
    public void hardDeletePurchaseInwardByCompany(Long id, Long companyId) {
        validateCompanyId(companyId);

        PurchaseInward inward = getPurchaseInwardByIdAndCompany(id, companyId);
        performHardDelete(inward);
    }

    // private void performHardDelete(PurchaseInward inward) {
    //     if (inward == null) {
    //         throw new EntityNotFoundException("Purchase Inward not found");
    //     }

    //     Long inwardId = inward.getId();
    //     List<PurchaseInwardItem> items = purchaseInwardItemRepository.findByPurchaseInwardId(inwardId);
    //     if (items != null) {
    //         for (PurchaseInwardItem item : items) {
    //             List<PieceEntry> usedPieces = pieceEntryRepository.findUsedPiecesByPurchaseInwardItemId(item.getId());
    //             if (!usedPieces.isEmpty()) {
    //                 String pieceNumbers = usedPieces.stream()
    //                         .map(PieceEntry::getPieceNumber)
    //                         .collect(Collectors.joining(", "));
    //                 throw new IllegalStateException("Cannot permanently delete: The following pieces are already inspected, packed, or invoiced: " + pieceNumbers);
    //             }
    //         }
    //     }

    //     Optional<YarnInventory> inventoryOpt = yarnInventoryRepository.findByInwardIdAndInwardTypeId(inward.getId(), inward.getPoTypeId());
    //     if (inventoryOpt.isEmpty()) {
    //         inventoryOpt = yarnInventoryRepository.findByInwardNoAndOrderId(inward.getInwardNo(), inward.getPurchaseOrderId());
    //     }
        
    //     inventoryOpt.ifPresent(inventory -> {
    //         List<YarnInwardStockDetails> details = yarnInwardStockDetailsRepository.findByYarnInventoryId(inventory.getId());
    //         for (YarnInwardStockDetails detail : details) {
    //             if (detail.getAvailableQty() != null && detail.getAcceptedQty() != null &&
    //                 detail.getAvailableQty().compareTo(detail.getAcceptedQty()) < 0) {

    //                 String yarnCode = "ID: " + detail.getItemId();
    //                 if (detail.getItemId() != null) {
    //                     yarnCode = yarnMasterRepository.findById(detail.getItemId())
    //                             .map(YarnMaster::getYarnName)
    //                             .orElse("ID: " + detail.getItemId());
    //                 }

    //                 throw new IllegalStateException("Cannot permanently delete: Some yarn (" + yarnCode + ") has already been issued from this inward.");
    //             }
    //         }
    //     });

    //     Optional<ItemInventory> itemInventoryOpt = Optional.empty();
    //     if (PO_TYPE_ITEM.equals(inward.getPoTypeId())) {
    //         itemInventoryOpt = itemInventoryRepository.findByInwardId(inward.getId());
    //         // Fallback to inwardNo and orderId if inwardId not found (for old records)
    //         if (itemInventoryOpt.isEmpty()) {
    //             itemInventoryOpt = itemInventoryRepository.findByInwardNoAndOrderId(inward.getInwardNo(), inward.getPurchaseOrderId());
    //         }
            
    //         itemInventoryOpt.ifPresent(inventory -> {
    //             List<ItemInwardStockDetails> details = itemInwardStockDetailsRepository.findByItemInventoryId(inventory.getId());
    //             for (ItemInwardStockDetails detail : details) {
    //                 if (detail.getAvailableQty() != null && detail.getAcceptedQty() != null &&
    //                     detail.getAvailableQty().compareTo(detail.getAcceptedQty()) < 0) {

    //                     String itemCode = "ID: " + detail.getItemId();
    //                     if (detail.getItemId() != null) {
    //                         itemCode = itemMasterRepository.findById(detail.getItemId())
    //                                 .map(ItemMaster::getItemName)
    //                                 .orElse("ID: " + detail.getItemId());
    //                     }

    //                     throw new IllegalStateException("Cannot permanently delete: Some item (" + itemCode + ") has already been issued from this inward.");
    //                 }
    //             }
    //         });

    //         // Specific Delete Order for Items (Requirement)
            
    //         // 1. Delete ItemInwardStockDetails
    //         itemInventoryOpt.ifPresent(inventory -> {
    //             itemInwardStockDetailsRepository.deleteByItemInventoryId(inventory.getId());
    //             logger.info("Deleted ItemInwardStockDetails for ItemInventory ID: {}", inventory.getId());
    //         });

    //         // 2. Delete ItemInventory
    //         itemInventoryOpt.ifPresent(inventory -> {
    //             itemInventoryRepository.delete(inventory);
    //             logger.info("Deleted ItemInventory ID: {} for Purchase Inward ID: {}", inventory.getId(), inward.getId());
    //         });

    //         // 3. Delete PurchaseInwardItems (and their PieceEntries/LotEntries if any)
    //         if (items != null) {
    //             for (PurchaseInwardItem item : items) {
    //                 pieceEntryRepository.deleteByPurchaseInwardItemId(item.getId());
    //             }
    //         }
    //         purchaseInwardItemRepository.deleteByPurchaseInwardId(inward.getId());
    //         logger.info("Deleted PurchaseInwardItems for Purchase Inward ID: {}", inward.getId());

    //         // 4. Finally delete PurchaseInward
    //         approvalRepository.deleteByDataIdAndScreenName(inward.getId(), "Purchase Inward");
            
    //         // Clear items to avoid cascade issues since we already deleted them
    //         inward.setItems(new ArrayList<>());
    //         inwardRepository.delete(inward);
    //         logger.info("Deleted PurchaseInward ID: {}", inward.getId());
            
    //     } else {
    //         // Existing logic for Fabric/Yarn
    //         if (items != null) {
    //             for (PurchaseInwardItem item : items) {
    //                 pieceEntryRepository.deleteByPurchaseInwardItemId(item.getId());
    //             }
    //         }

    //         inventoryOpt.ifPresent(yarnInventoryRepository::delete);
    //         itemInventoryOpt.ifPresent(itemInventoryRepository::delete);
    //         approvalRepository.deleteByDataIdAndScreenName(inward.getId(), "Purchase Inward");
    //         inwardRepository.delete(inward);
    //     }
    // }

    private void performHardDelete(PurchaseInward inward) {
    if (inward == null) {
        throw new EntityNotFoundException("Purchase Inward not found");
    }

    Long inwardId = inward.getId();
    List<PurchaseInwardItem> items = purchaseInwardItemRepository.findByPurchaseInwardId(inwardId);
    
    // Check for used pieces (for fabric type)
    if (items != null) {
        for (PurchaseInwardItem item : items) {
            List<PieceEntry> usedPieces = pieceEntryRepository.findUsedPiecesByPurchaseInwardItemId(item.getId());
            if (!usedPieces.isEmpty()) {
                String pieceNumbers = usedPieces.stream()
                        .map(PieceEntry::getPieceNumber)
                        .collect(Collectors.joining(", "));
                throw new IllegalStateException("Cannot permanently delete: The following pieces are already inspected, packed, or invoiced: " + pieceNumbers);
            }
        }
    }

    // Handle ITEM type deletion (PO_TYPE_ITEM = 3L)
    if (PO_TYPE_ITEM.equals(inward.getPoTypeId())) {
        // 1. Find and delete ItemInventory and its details
        List<ItemInventory> itemInventories = itemInventoryRepository.findByInwardId(inward.getId());
        
        // Fallback to inwardNo and orderId if inwardId not found (for old records)
        if (itemInventories.isEmpty()) {
            itemInventories = itemInventoryRepository.findByInwardNoAndOrderId(inward.getInwardNo(), inward.getPurchaseOrderId());
        }
        
        // Check if any items have been issued from any of the matching inventories
        for (ItemInventory inventory : itemInventories) {
            List<ItemInwardStockDetails> details = itemInwardStockDetailsRepository.findByItemInventoryId(inventory.getId());
            
            for (ItemInwardStockDetails detail : details) {
                if (detail.getAvailableQty() != null && detail.getAcceptedQty() != null &&
                    detail.getAvailableQty().compareTo(detail.getAcceptedQty()) < 0) {

                    String itemCode = "ID: " + detail.getItemId();
                    if (detail.getItemId() != null) {
                        itemCode = itemMasterRepository.findById(detail.getItemId())
                                .map(ItemMaster::getItemName)
                                .orElse("ID: " + detail.getItemId());
                    }

                    throw new IllegalStateException("Cannot permanently delete: Some item (" + itemCode + ") has already been issued from this inward.");
                }
            }
            
            // Delete ItemInwardStockDetails
            itemInwardStockDetailsRepository.deleteByItemInventoryId(inventory.getId());
            logger.info("Deleted ItemInwardStockDetails for ItemInventory ID: {}", inventory.getId());
            
            // Delete ItemInventory
            itemInventoryRepository.delete(inventory);
            logger.info("Deleted ItemInventory ID: {} for Purchase Inward ID: {}", inventory.getId(), inward.getId());
        }
        
        // Delete PurchaseInwardItems and their PieceEntries/LotEntries
        if (items != null) {
            for (PurchaseInwardItem item : items) {
                pieceEntryRepository.deleteByPurchaseInwardItemId(item.getId());
            }
        }
        purchaseInwardItemRepository.deleteByPurchaseInwardId(inward.getId());
        logger.info("Deleted PurchaseInwardItems for Purchase Inward ID: {}", inward.getId());
        
        // Delete approvals
        approvalRepository.deleteByDataIdAndScreenName(inward.getId(), "Purchase Inward");
        
        // Finally delete PurchaseInward
        inward.setItems(new ArrayList<>()); // Clear items to avoid cascade issues
        inwardRepository.delete(inward);
        logger.info("Deleted PurchaseInward ID: {}", inward.getId());
        
    } else {
        // Handle YARN and FABRIC type deletion (existing logic)
        List<YarnInventory> yarnInventories = yarnInventoryRepository.findByInwardIdAndInwardTypeId(inward.getId(), inward.getPoTypeId());
        if (yarnInventories.isEmpty()) {
            yarnInventories = yarnInventoryRepository.findByInwardNoAndOrderId(inward.getInwardNo(), inward.getPurchaseOrderId());
        }
        
        // Enhanced validation: Check yarn issues for both purchaseInwardId and yarnName combination
        // Get yarn names from lot entries associated with this purchase inward
        for (PurchaseInwardItem item : items) {
            List<LotEntry> lotEntries = item.getLotEntries();
            if (lotEntries != null && !lotEntries.isEmpty()) {
                for (LotEntry lotEntry : lotEntries) {
                    if (lotEntry.getYarnId() != null) {
                        // Get yarn name from YarnMaster
                        Optional<YarnMaster> yarnMaster = yarnMasterRepository.findById(lotEntry.getYarnId());
                        if (yarnMaster.isPresent() && yarnMaster.get().getYarnName() != null) {
                            String yarnName = yarnMaster.get().getYarnName();
                            
                            // Check YarnIssue records
                            Long yarnIssueCount = yarnIssueRepository.countActiveByInwardIdAndYarnName(
                                inwardId, yarnName);
                            if (yarnIssueCount > 0) {
                                throw new IllegalStateException("Cannot permanently delete: Some yarn (" + yarnName + ") has already been issued from this inward.");
                            }
                            
                            // Check SizingYarnIssue records
                            Long sizingYarnIssueCount = sizingYarnIssueRepository.countActiveByInwardIdAndYarnName(
                                inwardId, yarnName);
                            if (sizingYarnIssueCount > 0) {
                                throw new IllegalStateException("Cannot permanently delete: Some yarn (" + yarnName + ") has already been issued from this inward.");
                            }
                        }
                    }
                }
            }
        }
        
        // Additional validation: Check for yarn outward stock references
        for (YarnInventory inventory : yarnInventories) {
            List<YarnInwardStockDetails> details = yarnInwardStockDetailsRepository.findByYarnInventoryId(inventory.getId());
            for (YarnInwardStockDetails detail : details) {
                // Check if this yarn inward stock detail is referenced by any yarn outward stock details
                if (yarnOutwardStockDetailsRepository.existsByYarnInwardStockDetailsId(detail.getId())) {
                    String yarnCode = "ID: " + detail.getItemId();
                    if (detail.getItemId() != null) {
                        yarnCode = yarnMasterRepository.findById(detail.getItemId())
                                .map(YarnMaster::getYarnName)
                                .orElse("ID: " + detail.getItemId());
                    }
                    throw new IllegalStateException("Cannot permanently delete: Some yarn (" + yarnCode + ") has already been issued from this inward.");
                }
            }
        }
        
        // Delete piece entries
        if (items != null) {
            for (PurchaseInwardItem item : items) {
                pieceEntryRepository.deleteByPurchaseInwardItemId(item.getId());
            }
        }
        
        // Delete yarn inventory if exists
        if (!yarnInventories.isEmpty()) {
            yarnInventoryRepository.deleteAll(yarnInventories);
        }
        
        // Delete approvals
        approvalRepository.deleteByDataIdAndScreenName(inward.getId(), "Purchase Inward");
        
        // Finally delete PurchaseInward
        inwardRepository.delete(inward);
        logger.info("Deleted PurchaseInward ID: {}", inward.getId());
    }
    }

    private void fireBalanceZeroNotificationsIfNeeded(PurchaseInward savedInward) {
        if (savedInward == null || savedInward.getItems() == null) return;
        Long companyId = savedInward.getCompanyId();
        if (companyId == null) return;

        boolean anyBalanceZero = savedInward.getItems().stream()
                .anyMatch(item -> item.getBalanceQty() != null
                        && item.getBalanceQty().compareTo(BigDecimal.ZERO) <= 0);

        if (anyBalanceZero) {
            notificationService.createAndPush(
                    companyId,
                    null,
                    NotificationService.EVENT_PURCHASE_INWARD_BALANCE_ZERO,
                    savedInward.getPurchaseOrderId(),
                    null,
                    savedInward.getPurchaseOrderNo()
            );
            logger.info("Balance-zero notification sent for Purchase Inward ID: {}", savedInward.getId());
        }
    }

    private boolean hasYarnStockDetails(PurchaseInwardDTO dto) {
        if (dto.getPurchaseInwardItemsDtl() == null) return false;
        return dto.getPurchaseInwardItemsDtl().stream()
                .anyMatch(item -> item.getYarnStockDetails() != null && !item.getYarnStockDetails().isEmpty());
    }

    private boolean hasItemStockDetails(PurchaseInwardDTO dto) {
        if (dto.getPurchaseInwardItemsDtl() == null) return false;
        return dto.getPurchaseInwardItemsDtl().stream()
                .anyMatch(item -> item.getItemStockDetails() != null && !item.getItemStockDetails().isEmpty());
    }
}