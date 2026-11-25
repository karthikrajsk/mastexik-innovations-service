// package com.tech3.erp.service;

// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.dto.YarnReturnDTO;
// import com.tech3.erp.dto.YarnReturnItemDTO;
// import com.tech3.erp.entity.YarnReturn;
// import com.tech3.erp.entity.YarnReturnItem;
// import com.tech3.erp.repository.YarnReturnRepository;
// import com.tech3.erp.repository.YarnReturnItemRepository;
// import com.tech3.erp.util.PaginationHelper;

// import jakarta.persistence.EntityNotFoundException;
// import jakarta.transaction.Transactional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.stereotype.Service;

// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;
// import org.springframework.data.domain.Sort;
// import org.springframework.util.StringUtils;

// @Service
// public class YarnReturnService {

//     @Autowired
//     private YarnReturnRepository yarnReturnRepository;

//     @Autowired
//     private YarnReturnItemRepository yarnReturnItemRepository;

//     @Transactional
//     public YarnReturn createYarnReturn(YarnReturnDTO dto) {
//         // Generate inward number if not provided
//         String inwardNumber = dto.getInwardNumber();
//         if (inwardNumber == null || inwardNumber.trim().isEmpty()) {
//             inwardNumber = generateInwardNumber();
//         } else {
//             // Check if inward number already exists
//             Optional<YarnReturn> existing = yarnReturnRepository.findByInwardNumber(inwardNumber);
//             if (existing.isPresent()) {
//                 throw new IllegalArgumentException("Inward number already exists: " + inwardNumber);
//             }
//         }

//         YarnReturn yarnReturn = new YarnReturn();
//         mapDtoToEntity(dto, yarnReturn, inwardNumber);

//         // Set items
//         List<YarnReturnItem> yarnReturnItems = dto.getYarnReturnItems().stream().map(itemDto -> {
//             YarnReturnItem item = new YarnReturnItem();
//             mapItemDtoToEntity(itemDto, item);
//             item.setYarnReturn(yarnReturn);
//             return item;
//         }).collect(Collectors.toList());

//         yarnReturn.setYarnReturnItems(yarnReturnItems);

//         return yarnReturnRepository.save(yarnReturn);
//     }

//     public List<YarnReturnDTO> getAllYarnReturns() {
//         List<YarnReturn> yarnReturns = yarnReturnRepository.findAll(Sort.by(Sort.Direction.DESC, "inwardDate"));
//         return yarnReturns.stream()
//                 .map(YarnReturnDTO::new)
//                 .collect(Collectors.toList());
//     }

//     public PaginatedResponse<YarnReturnDTO> getAllYarnReturnsWithPagination(Long offset, Long limit) {
//         return PaginationHelper.paginate(getAllYarnReturns(), offset, limit);
//     }

//     public YarnReturnDTO getYarnReturnById(Long id) {
//         Optional<YarnReturn> yarnReturn = yarnReturnRepository.findById(id);
//         if (yarnReturn.isPresent()) {
//             return new YarnReturnDTO(yarnReturn.get());
//         } else {
//             throw new EntityNotFoundException("Yarn Return not found with id: " + id);
//         }
//     }

//     public List<YarnReturnDTO> getYarnReturnsByCompany(Long companyId) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         return yarnReturnRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id")).stream()
//                 .map(YarnReturnDTO::new)
//                 .collect(Collectors.toList());
//     }

//     public PaginatedResponse<YarnReturnDTO> getYarnReturnsByCompanyWithPagination(Long companyId, Long offset, Long limit) {
//         return PaginationHelper.paginate(getYarnReturnsByCompany(companyId), offset, limit);
//     }

//     public YarnReturnDTO getYarnReturnByIdAndCompany(Long id, Long companyId) {
//         YarnReturn yarnReturn = yarnReturnRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id + " and companyId: " + companyId));
//         return new YarnReturnDTO(yarnReturn);
//     }

//     @Transactional
//     public YarnReturn updateYarnReturn(Long id, YarnReturnDTO dto) {
//         YarnReturn yarnReturn = yarnReturnRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id));
//         dto.setCompanyId(yarnReturn.getCompanyId());
//         return applyUpdate(yarnReturn, dto);
//     }

//     @Transactional
//     public YarnReturn updateYarnReturnByCompany(Long id, Long companyId, YarnReturnDTO dto) {
//         YarnReturn yarnReturn = yarnReturnRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id + " and companyId: " + companyId));
//         dto.setCompanyId(yarnReturn.getCompanyId());
//         return applyUpdate(yarnReturn, dto);
//     }

//     public void deleteYarnReturn(Long id) {
//         if (!yarnReturnRepository.existsById(id)) {
//             throw new EntityNotFoundException("Yarn Return not found with id: " + id);
//         }
//         try {
//             yarnReturnRepository.deleteById(id);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete Yarn Return because it is referenced by other entities");
//         }
//     }

//     @Transactional
//     public void deleteYarnReturnByCompany(Long id, Long companyId) {
//         YarnReturn yarnReturn = yarnReturnRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id + " and companyId: " + companyId));
//         try {
//             yarnReturnRepository.delete(yarnReturn);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete Yarn Return because it is referenced by other entities");
//         }
//     }

//     private YarnReturn applyUpdate(YarnReturn yarnReturn, YarnReturnDTO dto) {
//         if (dto.getInwardNumber() != null &&
//                 !dto.getInwardNumber().equals(yarnReturn.getInwardNumber()) &&
//                 yarnReturnRepository.existsByInwardNumberAndIdNot(dto.getInwardNumber(), yarnReturn.getId())) {
//             throw new IllegalArgumentException("Inward number already exists: " + dto.getInwardNumber());
//         }

//         String inwardNumber = dto.getInwardNumber() != null ? dto.getInwardNumber() : yarnReturn.getInwardNumber();
//         mapDtoToEntity(dto, yarnReturn, inwardNumber);

//         yarnReturnItemRepository.deleteAll(yarnReturn.getYarnReturnItems());

//         List<YarnReturnItem> updatedItems = dto.getYarnReturnItems().stream().map(itemDto -> {
//             YarnReturnItem item = new YarnReturnItem();
//             mapItemDtoToEntity(itemDto, item);
//             item.setYarnReturn(yarnReturn);
//             return item;
//         }).collect(Collectors.toList());

//         yarnReturn.setYarnReturnItems(updatedItems);

//         return yarnReturnRepository.save(yarnReturn);
//     }

//     private void mapDtoToEntity(YarnReturnDTO dto, YarnReturn entity, String inwardNumber) {
//         if (dto.getInwardDate() != null) {
//             entity.setInwardDate(dto.getInwardDate());
//         } else if (entity.getInwardDate() == null) {
//             entity.setInwardDate(LocalDate.now());
//         }
//         if (StringUtils.hasText(inwardNumber)) {
//             entity.setInwardNumber(inwardNumber);
//         }
//         if (dto.getInwardType() != null) {
//             entity.setInwardType(dto.getInwardType());
//         }
//         if (dto.getOrderNo() != null) {
//             entity.setOrderNo(dto.getOrderNo());
//         }
//         if (dto.getVendorId() != null) {
//             entity.setVendorId(dto.getVendorId());
//         }
//         if (dto.getVendorName() != null) {
//             entity.setVendorName(dto.getVendorName());
//         }
//         if (dto.getMillId() != null) {
//             entity.setMillId(dto.getMillId());
//         }
//         if (dto.getWarehouseId() != null) {
//             entity.setWarehouseId(dto.getWarehouseId());
//         }
//         if (dto.getIssuedQty() != null) {
//             entity.setIssuedQty(dto.getIssuedQty());
//         }
//         if (dto.getRemarks() != null) {
//             entity.setRemarks(dto.getRemarks());
//         }
//         if (dto.getDcNumber() != null) {
//             entity.setDcNumber(dto.getDcNumber());
//         }
//         if (dto.getDcDate() != null) {
//             entity.setDcDate(dto.getDcDate());
//         }
//         if (dto.getCompanyId() != null) {
//             entity.setCompanyId(dto.getCompanyId());
//         }
//         if (dto.getActiveFlag() != null) {
//             entity.setActiveFlag(dto.getActiveFlag());
//         } else if (entity.getActiveFlag() == null) {
//             entity.setActiveFlag(true);
//         }
//     }

//     private void mapItemDtoToEntity(YarnReturnItemDTO itemDto, YarnReturnItem item) {
//         item.setYarnId(itemDto.getYarnId());
//         item.setYarnName(itemDto.getYarnName());
//         item.setActualYarnId(itemDto.getActualYarnId());
//         item.setActualYarnName(itemDto.getActualYarnName());
//         item.setNoOfBags(itemDto.getNoOfBags());
//         item.setTotalKgs(itemDto.getTotalKgs());
//         item.setConesPerBag(itemDto.getConesPerBag());
//         item.setConesWeight(itemDto.getConesWeight());
//         item.setLotNo(itemDto.getLotNo());
//         item.setActiveFlag(itemDto.getActiveFlag() != null ? itemDto.getActiveFlag() : true);
//     }

//     private String generateInwardNumber() {
//         String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//         long count = yarnReturnRepository.count() + 1;
//         return "YRN-" + datePart + "-" + String.format("%04d", count);
//     }
// }

package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.YarnReturnDTO;
import com.tech3.erp.dto.YarnReturnItemDTO;
import com.tech3.erp.dto.YarnReturnRequirementDTO;
import com.tech3.erp.entity.YarnReturn;
import com.tech3.erp.entity.YarnReturnItem;
import com.tech3.erp.entity.YarnReturnRequirement;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.repository.YarnReturnRepository;
import com.tech3.erp.repository.YarnReturnItemRepository;
import com.tech3.erp.repository.YarnReturnRequirementRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.SizingBeamDetailsRepository;
import com.tech3.erp.repository.DyeingWorkOrderRepository;
import com.tech3.erp.repository.MediaDetailRepository;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.SizingBeamDetails;
import com.tech3.erp.entity.DyeingWorkOrder;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.exception.ApiException;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class YarnReturnService {

    private static final Logger logger = LoggerFactory.getLogger(YarnReturnService.class);

    @Autowired
    private YarnReturnRepository yarnReturnRepository;

    @Autowired
    private YarnReturnItemRepository yarnReturnItemRepository;

    @Autowired
    private YarnReturnRequirementRepository yarnReturnRequirementRepository;

    @Autowired
    private YarnInventoryRepository yarnInventoryRepository;

    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private SizingPlanRepository sizingPlanRepository;

    @Autowired
    private WeavingContractRepository weavingContractRepository;

    @Autowired
    private SizingBeamDetailsRepository sizingBeamDetailsRepository;

    @Autowired
    private DyeingWorkOrderRepository dyeingWorkOrderRepository;

    @Autowired
    private MediaDetailRepository mediaDetailRepository;

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Autowired(required = false)
    private ApprovalWorkflowService approvalWorkflowService;

    @Autowired(required = false)
    private MediaService mediaService;

    private Short resolveMediaDetailSnoToMediaSno(Short mediaDetailSno) {
        if (mediaDetailSno == null) {
            return null;
        }
        try {
            Optional<Long> mediaSno = mediaDetailRepository.findMediaSnoByMediaDetailSno(mediaDetailSno.longValue());
            if (mediaSno.isPresent()) {
                return mediaSno.get().shortValue();
            } else {
                logger.warn("Media mapping not found for mediaDetailSno: {}", mediaDetailSno);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error resolving mediaDetailSno to mediaSno for mediaDetailSno: {}", mediaDetailSno, e);
            return null;
        }
    }

    @Transactional
    public YarnReturn createYarnReturn(YarnReturnDTO dto) {
        // Generate inward number if not provided
        String inwardNumber = dto.getInwardNumber();
        if (inwardNumber == null || inwardNumber.trim().isEmpty()) {
            inwardNumber = generateInwardNumber();
        } else {
            // Check if inward number already exists
            Optional<YarnReturn> existing = yarnReturnRepository.findByInwardNumber(inwardNumber);
            if (existing.isPresent()) {
                throw new IllegalArgumentException("Inward number already exists: " + inwardNumber);
            }
        }

        YarnReturn yarnReturn = new YarnReturn();
        mapDtoToEntity(dto, yarnReturn, inwardNumber);

        // Set items
        List<YarnReturnItem> yarnReturnItems = dto.getYarnReturnItems().stream().map(itemDto -> {
            YarnReturnItem item = new YarnReturnItem();
            mapItemDtoToEntity(itemDto, item);
            item.setYarnReturn(yarnReturn);
            return item;
        }).collect(Collectors.toList());

        yarnReturn.setYarnReturnItems(yarnReturnItems);
        yarnReturn.setYarnReturnRequirements(mapRequirementDtoList(dto.getYarnReturnRequirements(), yarnReturn));

        // Set transport details
     
        // Save yarn return first
        YarnReturn savedYarnReturn = yarnReturnRepository.save(yarnReturn);

        // Create entries in YarnInventory and YarnInwardStockDetails
        createYarnInventoryAndStockDetails(savedYarnReturn);

        return savedYarnReturn;
    }

    private void createYarnInventoryAndStockDetails(YarnReturn yarnReturn) {
        // Create YarnInventory entry
        YarnInventory yarnInventory = new YarnInventory();
        
        yarnInventory.setInwardId(yarnReturn.getId());
        // Fetch inwardTypeId from CodesDetail based on InwardType from YarnReturn
        Long inwardTypeId = 2L; // Default fallback for Yarn Return
        if (yarnReturn.getInwardType() != null) {
            Optional<CodesDetail> codeDetail = codesDetailRepository.findByCodeOrValue(yarnReturn.getInwardType(), yarnReturn.getInwardType());
            if (codeDetail.isPresent()) {
                inwardTypeId = codeDetail.get().getId();
            }
        }
        yarnInventory.setInwardTypeId(inwardTypeId);
        yarnInventory.setInwardNo(yarnReturn.getInwardNumber());
        yarnInventory.setCompanyId(yarnReturn.getCompanyId() != null ? yarnReturn.getCompanyId() : 1L);
        yarnInventory.setInwardDate(LocalDateTime.now());
        yarnInventory.setFinancialYear(getCurrentFinancialYear());
        yarnInventory.setDcNo(yarnReturn.getDcNumber());
        yarnInventory.setDcDate(yarnReturn.getDcDate());
        yarnInventory.setOrderNo(yarnReturn.getOrderNo());
        
        // Look up order_id based on order_no (SizingPlanNo, WeavingContractNo, DyeingWorkOrderNo)
        Long orderId = null;
        String orderNo = yarnReturn.getOrderNo();
        if (orderNo != null && !orderNo.trim().isEmpty()) {
            // First try to find SizingPlan by sizingPlanNo
            Optional<SizingPlan> sizingPlan = sizingPlanRepository.findAll().stream()
                .filter(sp -> orderNo.equals(sp.getSizingPlanNo()))
                .findFirst();
            
            if (sizingPlan.isPresent()) {
                Long finalOrderId = sizingPlan.get().getId();
                orderId = finalOrderId;
                referenceValidationService.requireReference(finalOrderId, () -> sizingPlanRepository.findById(finalOrderId), "Sizing Plan", orderNo);
            } else {
                // Try WeavingContract lookup
                Optional<WeavingContract> weavingContract = weavingContractRepository.findAll().stream()
                    .filter(wc -> orderNo.equals(wc.getWeavingContractNo()))
                    .findFirst();
                
                if (weavingContract.isPresent()) {
                    Long finalOrderId = weavingContract.get().getId();
                    orderId = finalOrderId;
                    referenceValidationService.requireReference(finalOrderId, () -> weavingContractRepository.findById(finalOrderId), "Weaving Contract", orderNo);
                } else {
                    // Try DyeingWorkOrder lookup
                    Optional<DyeingWorkOrder> dyeingWorkOrder = dyeingWorkOrderRepository.findAll().stream()
                        .filter(dwo -> orderNo.equals(dwo.getDyeingWorkOrderNo()))
                        .findFirst();
                    
                    if (dyeingWorkOrder.isPresent()) {
                        Long finalOrderId = dyeingWorkOrder.get().getId();
                        orderId = finalOrderId;
                        referenceValidationService.requireReference(finalOrderId, () -> dyeingWorkOrderRepository.findById(finalOrderId), "Dyeing Work Order", orderNo);
                    } else {
                        throw new ApiException(HttpStatus.CONFLICT, "This Order No " + orderNo + " is currently not present in Sizing Plan, Weaving Contract or Dyeing Work Order");
                    }
                }
            }
        }
        yarnInventory.setOrderId(orderId);
        
        yarnInventory.setVendorId(yarnReturn.getVendorId());

        // Save YarnInventory
        YarnInventory savedYarnInventory = yarnInventoryRepository.save(yarnInventory);

        // Create YarnInwardStockDetails entries for each return item
        List<YarnInwardStockDetails> stockDetailsList = new java.util.ArrayList<>();
        
        for (YarnReturnItem returnItem : yarnReturn.getYarnReturnItems()) {
            YarnInwardStockDetails stockDetails = new YarnInwardStockDetails();
            stockDetails.setYarnInventory(savedYarnInventory);
            stockDetails.setInwardTypeId(savedYarnInventory.getInwardTypeId());
            stockDetails.setItemId(returnItem.getYarnId());
            stockDetails.setOrderItemId(resolveYarnOrderItemId(returnItem.getOrderItemId()));
            stockDetails.setUomId(1L);
            stockDetails.setMillId(yarnReturn.getMillId());
            stockDetails.setLotNo(returnItem.getLotNo());
            stockDetails.setInwardQty(BigDecimal.valueOf(returnItem.getTotalKgs() != null ? returnItem.getTotalKgs() : 0.0));
            stockDetails.setInwardBags(returnItem.getNoOfBags() != null ? returnItem.getNoOfBags() : BigDecimal.ZERO);
            // For yarn return, available bags should match returned bags by default.
            // (available is further reduced by issue flows elsewhere.)
            stockDetails.setAvailableBags(returnItem.getNoOfBags() != null ? returnItem.getNoOfBags() : BigDecimal.ZERO);
            
            if (returnItem.getNoOfBags() != null && returnItem.getNoOfBags().compareTo(BigDecimal.ZERO) > 0 && 
                returnItem.getTotalKgs() != null && returnItem.getTotalKgs() > 0) {
                double kgsPerBag = returnItem.getTotalKgs() / returnItem.getNoOfBags().doubleValue();
                stockDetails.setKgsPerBag(BigDecimal.valueOf(kgsPerBag));
            } else {
                stockDetails.setKgsPerBag(BigDecimal.ZERO);
            }
            
            stockDetails.setAcceptedQty(BigDecimal.valueOf(returnItem.getTotalKgs() != null ? returnItem.getTotalKgs() : 0.0));
            stockDetails.setRejectedQty(BigDecimal.ZERO);
            stockDetails.setAvailableQty(BigDecimal.valueOf(returnItem.getTotalKgs() != null ? returnItem.getTotalKgs() : 0.0));
            stockDetails.setWarehouseId(yarnReturn.getWarehouseId());

            stockDetailsList.add(stockDetails);
        }

        // Save all stock details
        List<YarnInwardStockDetails> savedStockDetails = yarnInwardStockDetailsRepository.saveAll(stockDetailsList);

        // Set inwardId and orderItemId back to YarnReturnItem and save them
        List<YarnReturnItem> itemsToUpdate = new java.util.ArrayList<>();
        for (int i = 0; i < yarnReturn.getYarnReturnItems().size(); i++) {
            YarnReturnItem returnItem = yarnReturn.getYarnReturnItems().get(i);
            YarnInwardStockDetails stockDetail = savedStockDetails.get(i);
            
            returnItem.setYarnInventoryId(savedYarnInventory.getId());
            returnItem.setOrderItemId(stockDetail.getOrderItemId());
            itemsToUpdate.add(returnItem);
        }
        
        yarnReturnItemRepository.saveAll(itemsToUpdate);

        // Set the details back to yarn inventory
        savedYarnInventory.setDetails(savedStockDetails);
    }

    private void updateYarnInventoryAndStockDetails(YarnReturn yarnReturn) {
        Optional<YarnInventory> existingInventory = yarnInventoryRepository.findByInwardNo(yarnReturn.getInwardNumber());
        
        if (existingInventory.isEmpty()) {
            createYarnInventoryAndStockDetails(yarnReturn);
            return;
        }

        YarnInventory yarnInventory = existingInventory.get();
        
        yarnInventory.setInwardDate(LocalDateTime.now());
        yarnInventory.setFinancialYear(getCurrentFinancialYear());
        yarnInventory.setDcNo(yarnReturn.getDcNumber());
        yarnInventory.setDcDate(yarnReturn.getDcDate());
        yarnInventory.setOrderNo(yarnReturn.getOrderNo());
        
        Long orderId = null;
        String orderNo = yarnReturn.getOrderNo();
        if (orderNo != null && !orderNo.trim().isEmpty()) {
            Optional<SizingPlan> sizingPlan = sizingPlanRepository.findAll().stream()
                .filter(sp -> orderNo.equals(sp.getSizingPlanNo()))
                .findFirst();
            
            if (sizingPlan.isPresent()) {
                Long finalOrderId = sizingPlan.get().getId();
                orderId = finalOrderId;
                referenceValidationService.requireReference(finalOrderId, () -> sizingPlanRepository.findById(finalOrderId), "Sizing Plan", orderNo);
            } else {
                Optional<WeavingContract> weavingContract = weavingContractRepository.findAll().stream()
                    .filter(wc -> orderNo.equals(wc.getWeavingContractNo()))
                    .findFirst();
                
                if (weavingContract.isPresent()) {
                    Long finalOrderId = weavingContract.get().getId();
                    orderId = finalOrderId;
                    referenceValidationService.requireReference(finalOrderId, () -> weavingContractRepository.findById(finalOrderId), "Weaving Contract", orderNo);
                } else {
                    Optional<DyeingWorkOrder> dyeingWorkOrder = dyeingWorkOrderRepository.findAll().stream()
                        .filter(dwo -> orderNo.equals(dwo.getDyeingWorkOrderNo()))
                        .findFirst();
                    
                    if (dyeingWorkOrder.isPresent()) {
                        Long finalOrderId = dyeingWorkOrder.get().getId();
                        orderId = finalOrderId;
                        referenceValidationService.requireReference(finalOrderId, () -> dyeingWorkOrderRepository.findById(finalOrderId), "Dyeing Work Order", orderNo);
                    }
                }
            }
        }
        yarnInventory.setOrderId(orderId);
        
        yarnInventory.setVendorId(yarnReturn.getVendorId());
        
        YarnInventory savedYarnInventory = yarnInventoryRepository.save(yarnInventory);

        // Match existing stock details to preserve IDs and avoid stale references
        Map<Long, YarnInwardStockDetails> existingDetailsMap = new java.util.HashMap<>();
        if (savedYarnInventory.getDetails() != null) {
            savedYarnInventory.getDetails().forEach(d -> {
                if (d.getOrderItemId() != null) {
                    existingDetailsMap.put(d.getOrderItemId(), d);
                }
            });
        }

        List<YarnInwardStockDetails> updatedStockDetails = new java.util.ArrayList<>();
        
        for (YarnReturnItem returnItem : yarnReturn.getYarnReturnItems()) {
            Long orderItemId = resolveYarnOrderItemId(returnItem.getOrderItemId());
            YarnInwardStockDetails stockDetails = existingDetailsMap.remove(orderItemId);
            if (stockDetails == null) {
                stockDetails = new YarnInwardStockDetails();
                stockDetails.setYarnInventory(savedYarnInventory);
            }
            
            stockDetails.setInwardTypeId(savedYarnInventory.getInwardTypeId());
            stockDetails.setItemId(returnItem.getYarnId());
            stockDetails.setOrderItemId(orderItemId);
            stockDetails.setUomId(1L);
            stockDetails.setMillId(yarnReturn.getMillId());
            stockDetails.setLotNo(returnItem.getLotNo());
            stockDetails.setInwardQty(BigDecimal.valueOf(returnItem.getTotalKgs() != null ? returnItem.getTotalKgs() : 0.0));
            stockDetails.setInwardBags(returnItem.getNoOfBags() != null ? returnItem.getNoOfBags() : BigDecimal.ZERO);
            // Keep available bags in sync with the return quantity.
            stockDetails.setAvailableBags(returnItem.getNoOfBags() != null ? returnItem.getNoOfBags() : BigDecimal.ZERO);
            
            if (returnItem.getNoOfBags() != null && returnItem.getNoOfBags().compareTo(BigDecimal.ZERO) > 0 && 
                returnItem.getTotalKgs() != null && returnItem.getTotalKgs() > 0) {
                double kgsPerBag = returnItem.getTotalKgs() / returnItem.getNoOfBags().doubleValue();
                stockDetails.setKgsPerBag(BigDecimal.valueOf(kgsPerBag));
            } else {
                stockDetails.setKgsPerBag(BigDecimal.ZERO);
            }
            
            stockDetails.setAcceptedQty(BigDecimal.valueOf(returnItem.getTotalKgs() != null ? returnItem.getTotalKgs() : 0.0));
            stockDetails.setRejectedQty(BigDecimal.ZERO);
            stockDetails.setAvailableQty(BigDecimal.valueOf(returnItem.getTotalKgs() != null ? returnItem.getTotalKgs() : 0.0));
            stockDetails.setWarehouseId(yarnReturn.getWarehouseId());

            updatedStockDetails.add(stockDetails);
        }

        List<YarnInwardStockDetails> savedStockDetails = yarnInwardStockDetailsRepository.saveAll(updatedStockDetails);

        // Handle deletions of removed details if necessary (orphan removal usually handles this if configured)
        if (!existingDetailsMap.isEmpty()) {
            yarnInwardStockDetailsRepository.deleteAll(existingDetailsMap.values());
            savedYarnInventory.getDetails().removeAll(existingDetailsMap.values());
        }

        List<YarnReturnItem> itemsToUpdate = new java.util.ArrayList<>();
        for (int i = 0; i < yarnReturn.getYarnReturnItems().size(); i++) {
            YarnReturnItem returnItem = yarnReturn.getYarnReturnItems().get(i);
            YarnInwardStockDetails stockDetail = savedStockDetails.get(i);
            
            returnItem.setYarnInventoryId(savedYarnInventory.getId());
            returnItem.setOrderItemId(stockDetail.getOrderItemId());
            itemsToUpdate.add(returnItem);
        }
        
        yarnReturnItemRepository.saveAll(itemsToUpdate);

        savedYarnInventory.setDetails(savedStockDetails);
    }

    private Long resolveYarnOrderItemId(Long requestedId) {
        return requestedId != null ? requestedId : yarnInwardStockDetailsRepository.getNextOrderItemIdValue();
    }

    private String getCurrentFinancialYear() {
        LocalDate now = LocalDate.now();
        int year = now.getMonthValue() >= 4 ? now.getYear() : now.getYear() - 1;
        return year + "-" + (year + 1);
    }

    

    public List<YarnReturnDTO> getAllYarnReturns() {
        List<YarnReturn> yarnReturns = yarnReturnRepository.findAll(Sort.by(Sort.Direction.DESC, "inwardDate"));
        return yarnReturns.stream()
                .map(YarnReturnDTO::new)
  
                .collect(Collectors.toList());
    }

    public PaginatedResponse<YarnReturnDTO> getAllYarnReturnsWithPagination(Long offset, Long limit) {
        return PaginationHelper.paginate(getAllYarnReturns(), offset, limit);
    }

    public YarnReturnDTO getYarnReturnById(Long id) {
        Optional<YarnReturn> yarnReturn = yarnReturnRepository.findById(id);
        if (yarnReturn.isPresent()) {
            YarnReturnDTO dto = new YarnReturnDTO(yarnReturn.get());
           
            return dto;
        } else {
            throw new EntityNotFoundException("Yarn Return not found with id: " + id);
        }
    }

    public List<YarnReturnDTO> getYarnReturnsByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return yarnReturnRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(YarnReturnDTO::new)
               
                .collect(Collectors.toList());
    }

    public PaginatedResponse<YarnReturnDTO> getYarnReturnsByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        return PaginationHelper.paginate(getYarnReturnsByCompany(companyId), offset, limit);
    }

    public YarnReturnDTO getYarnReturnByIdAndCompany(Long id, Long companyId) {
        YarnReturn yarnReturn = yarnReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id + " and companyId: " + companyId));
        YarnReturnDTO dto = new YarnReturnDTO(yarnReturn);
      
        return dto;
    }

    @Transactional
    public YarnReturn updateYarnReturn(Long id, YarnReturnDTO dto) {
        YarnReturn yarnReturn = yarnReturnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id));
        dto.setCompanyId(yarnReturn.getCompanyId());
        return applyUpdate(yarnReturn, dto);
    }

    @Transactional
    public YarnReturn updateYarnReturnByCompany(Long id, Long companyId, YarnReturnDTO dto) {
        YarnReturn yarnReturn = yarnReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id + " and companyId: " + companyId));
        dto.setCompanyId(yarnReturn.getCompanyId());
        return applyUpdate(yarnReturn, dto);
    }

    @Transactional
    public void deleteYarnReturn(Long id) {
        YarnReturn yarnReturn = yarnReturnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id));
        
        // Find and delete related YarnInventory and YarnInwardStockDetails
        Optional<YarnInventory> inventory = yarnInventoryRepository.findByInwardNo(yarnReturn.getInwardNumber());
        inventory.ifPresent(inv -> {
            if (inv.getDetails() != null) {
                yarnInwardStockDetailsRepository.deleteAll(inv.getDetails());
            }
            yarnInventoryRepository.delete(inv);
        });

        yarnReturnRepository.delete(yarnReturn);
    }

    @Transactional
    public void deleteYarnReturnByCompany(Long id, Long companyId) {
        YarnReturn yarnReturn = yarnReturnRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id + " and companyId: " + companyId));
        
        // Find and delete related YarnInventory and YarnInwardStockDetails
        Optional<YarnInventory> inventory = yarnInventoryRepository.findByInwardNo(yarnReturn.getInwardNumber());
        inventory.ifPresent(inv -> {
            if (inv.getDetails() != null) {
                yarnInwardStockDetailsRepository.deleteAll(inv.getDetails());
            }
            yarnInventoryRepository.delete(inv);
        });

        yarnReturnRepository.delete(yarnReturn);
    }

    @Transactional
    public void hardDeleteYarnReturn(Long id) {
        YarnReturn yarnReturn = yarnReturnRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Yarn Return not found with id: " + id));

        // Find and delete related YarnInventory and YarnInwardStockDetails
        Optional<YarnInventory> inventory = yarnInventoryRepository.findByInwardNo(yarnReturn.getInwardNumber());
        inventory.ifPresent(inv -> {
            // Delete stock details first (orphan removal might handle it, but being explicit is safer)
            if (inv.getDetails() != null) {
                yarnInwardStockDetailsRepository.deleteAll(inv.getDetails());
            }
            yarnInventoryRepository.delete(inv);
        });

        // Physically delete from database (cascades to items and transports)
        yarnReturnRepository.delete(yarnReturn);
    }

    private YarnReturn applyUpdate(YarnReturn yarnReturn, YarnReturnDTO dto) {
        if (dto.getInwardNumber() != null &&
                !dto.getInwardNumber().equals(yarnReturn.getInwardNumber()) &&
                yarnReturnRepository.existsByInwardNumberAndIdNot(dto.getInwardNumber(), yarnReturn.getId())) {
            throw new IllegalArgumentException("Inward number already exists: " + dto.getInwardNumber());
        }

        String inwardNumber = dto.getInwardNumber() != null ? dto.getInwardNumber() : yarnReturn.getInwardNumber();
        mapDtoToEntity(dto, yarnReturn, inwardNumber);

        // Update items - delete existing and create new ones
        yarnReturnItemRepository.deleteAll(yarnReturn.getYarnReturnItems());
        yarnReturnRequirementRepository.deleteAll(yarnReturn.getYarnReturnRequirements());

        List<YarnReturnItem> updatedItems = dto.getYarnReturnItems().stream().map(itemDto -> {
            YarnReturnItem item = new YarnReturnItem();
            mapItemDtoToEntity(itemDto, item);
            item.setYarnReturn(yarnReturn);
            return item;
        }).collect(Collectors.toList());

        yarnReturn.setYarnReturnItems(updatedItems);
        yarnReturn.setYarnReturnRequirements(mapRequirementDtoList(dto.getYarnReturnRequirements(), yarnReturn));

     

        YarnReturn savedEntity = yarnReturnRepository.save(yarnReturn);

        // Update YarnInwardStockDetails
        updateYarnInventoryAndStockDetails(savedEntity);

        // Handle approval workflow if necessary
       
        return savedEntity;
    }

    private void mapDtoToEntity(YarnReturnDTO dto, YarnReturn entity, String inwardNumber) {
        if (dto.getInwardDate() != null) {
            entity.setInwardDate(dto.getInwardDate());
        } else if (entity.getInwardDate() == null) {
            entity.setInwardDate(LocalDate.now());
        }
        if (StringUtils.hasText(inwardNumber)) {
            entity.setInwardNumber(inwardNumber);
        }
        if (dto.getInwardType() != null) {
            entity.setInwardType(dto.getInwardType());
        }
        if (dto.getOrderNo() != null) {
            entity.setOrderNo(dto.getOrderNo());
            validateOrderNo(dto.getOrderNo());
        }
        if (dto.getVendorId() != null) {
            entity.setVendorId(dto.getVendorId());
        }
        if (dto.getVendorName() != null) {
            entity.setVendorName(dto.getVendorName());
        }
        if (dto.getMillId() != null) {
            entity.setMillId(dto.getMillId());
        }
        if (dto.getWarehouseId() != null) {
            entity.setWarehouseId(dto.getWarehouseId());
        }
        if (dto.getIssuedQty() != null) {
            entity.setIssuedQty(dto.getIssuedQty());
        }
        if (dto.getRemarks() != null) {
            entity.setRemarks(dto.getRemarks());
        }
        if (dto.getDcNumber() != null) {
            entity.setDcNumber(dto.getDcNumber());
        }
        if (dto.getDcDate() != null) {
            entity.setDcDate(dto.getDcDate());
        }
        
       
        // New DC fields from reference
       
        
        if (dto.getCompanyId() != null) {
            entity.setCompanyId(dto.getCompanyId());
        }
        
        if (dto.getActiveFlag() != null) {
            entity.setActiveFlag(dto.getActiveFlag());
        } else if (entity.getActiveFlag() == null) {
            entity.setActiveFlag(true);
        }
    }

    private void mapItemDtoToEntity(YarnReturnItemDTO itemDto, YarnReturnItem item) {
        item.setYarnId(itemDto.getYarnId());
        item.setYarnName(itemDto.getYarnName());
        item.setActualYarnId(itemDto.getActualYarnId());
        item.setActualYarnName(itemDto.getActualYarnName());
        item.setNoOfBags(itemDto.getNoOfBags());
        item.setTotalKgs(itemDto.getTotalKgs());
        item.setConesPerBag(itemDto.getConesPerBag());
        item.setConesWeight(itemDto.getConesWeight());
        item.setLotNo(itemDto.getLotNo());
        item.setActiveFlag(itemDto.getActiveFlag() != null ? itemDto.getActiveFlag() : true);
    }

    private List<YarnReturnRequirement> mapRequirementDtoList(List<YarnReturnRequirementDTO> requirementDTOs, YarnReturn yarnReturn) {
        if (requirementDTOs == null) {
            return java.util.Collections.emptyList();
        }
        return requirementDTOs.stream().map(reqDto -> {
            YarnReturnRequirement requirement = new YarnReturnRequirement();
            requirement.setYarnReturn(yarnReturn);
            requirement.setYarnName(reqDto.getYarnName());
            requirement.setActualYarnId(reqDto.getActualYarnId());
            requirement.setActualYarnName(reqDto.getActualYarnName());
            requirement.setGreigeYarnId(reqDto.getGreigeYarnId());
            requirement.setGreigeYarnName(reqDto.getGreigeYarnName());
            requirement.setFinishedYarnId(reqDto.getFinishedYarnId());
            requirement.setFinishedYarnName(reqDto.getFinishedYarnName());
            requirement.setIssuedQty(reqDto.getIssuedQty());
            return requirement;
        }).collect(Collectors.toList());
    }

   

    private void validateOrderNo(String orderNo) {
        if (!StringUtils.hasText(orderNo)) {
            return;
        }

        // Try SizingPlan lookup
        Optional<SizingPlan> sizingPlan = sizingPlanRepository.findAll().stream()
                .filter(sp -> orderNo.equals(sp.getSizingPlanNo()))
                .findFirst();
        if (sizingPlan.isPresent()) {
            referenceValidationService.requireReference(sizingPlan.get().getId(), () -> sizingPlanRepository.findById(sizingPlan.get().getId()), "Sizing Plan", orderNo);
            return;
        }

        // Try WeavingContract lookup
        Optional<WeavingContract> weavingContract = weavingContractRepository.findAll().stream()
                .filter(wc -> orderNo.equals(wc.getWeavingContractNo()))
                .findFirst();
        if (weavingContract.isPresent()) {
            referenceValidationService.requireReference(weavingContract.get().getId(), () -> weavingContractRepository.findById(weavingContract.get().getId()), "Weaving Contract", orderNo);
            return;
        }

        // Try DyeingWorkOrder lookup
        Optional<DyeingWorkOrder> dyeingWorkOrder = dyeingWorkOrderRepository.findAll().stream()
                .filter(dwo -> orderNo.equals(dwo.getDyeingWorkOrderNo()))
                .findFirst();
        if (dyeingWorkOrder.isPresent()) {
            referenceValidationService.requireReference(dyeingWorkOrder.get().getId(), () -> dyeingWorkOrderRepository.findById(dyeingWorkOrder.get().getId()), "Dyeing Work Order", orderNo);
            return;
        }

        throw new ApiException(HttpStatus.CONFLICT, "This Order No " + orderNo + " is currently not present in Sizing Plan, Weaving Contract or Dyeing Work Order");
    }

    private String extractSalesOrderNoFromYarnReturn(YarnReturn yarnReturn) {
        if (yarnReturn == null || yarnReturn.getOrderNo() == null || yarnReturn.getOrderNo().trim().isEmpty()) {
            return null;
        }

        String orderNo = yarnReturn.getOrderNo().trim();

        // Try SizingPlan lookup
        List<SizingPlan> sizingPlans = sizingPlanRepository.findAll();
        Optional<SizingPlan> sizingPlan = sizingPlans.stream()
            .filter(sp -> orderNo.equals(sp.getSizingPlanNo()))
            .findFirst();

        if (sizingPlan.isPresent()) {
            try {
                List<SizingBeamDetails> beamDetails = sizingBeamDetailsRepository.findBySizingPlanId(sizingPlan.get().getId());
                if (!beamDetails.isEmpty() && beamDetails.get(0).getSalesOrderId() != null) {
                    com.tech3.erp.entity.SalesOrder salesOrder = salesOrderRepository.findById(beamDetails.get(0).getSalesOrderId()).orElse(null);
                    if (salesOrder != null) {
                        return salesOrder.getSalesOrderNo();
                    }
                }
            } catch (Exception e) {
                logger.warn("Error extracting sales order from sizing plan: {}", e.getMessage());
            }
        }

        // Try WeavingContract lookup
        List<WeavingContract> weavingContracts = weavingContractRepository.findAll();
        Optional<WeavingContract> weavingContract = weavingContracts.stream()
            .filter(wc -> orderNo.equals(wc.getWeavingContractNo()))
            .findFirst();

        if (weavingContract.isPresent()) {
            try {
                Long salesOrderId = weavingContract.get().getSalesOrderNo();
                if (salesOrderId != null && salesOrderId > 0) {
                    com.tech3.erp.entity.SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).orElse(null);
                    if (salesOrder != null) {
                        return salesOrder.getSalesOrderNo();
                    }
                }
            } catch (Exception e) {
                logger.warn("Error extracting sales order from weaving contract: {}", e.getMessage());
            }
        }

        // Try DyeingWorkOrder lookup (Process Contract)
        List<DyeingWorkOrder> dyeingWorkOrders = dyeingWorkOrderRepository.findAll();
        Optional<DyeingWorkOrder> dyeingWorkOrder = dyeingWorkOrders.stream()
            .filter(dwo -> orderNo.equals(dwo.getDyeingWorkOrderNo()))
            .findFirst();

        if (dyeingWorkOrder.isPresent()) {
            try {
                Long salesOrderId = dyeingWorkOrder.get().getSalesOrderNo();
                if (salesOrderId != null && salesOrderId > 0) {
                    com.tech3.erp.entity.SalesOrder salesOrder = salesOrderRepository.findById(salesOrderId).orElse(null);
                    if (salesOrder != null) {
                        return salesOrder.getSalesOrderNo();
                    }
                }
            } catch (Exception e) {
                logger.warn("Error extracting sales order from dyeing work order: {}", e.getMessage());
            }
        }

        return null;
    }

    private String generateInwardNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        long count = yarnReturnRepository.count() + 1;
        return "YRN-" + datePart + "-" + String.format("%04d", count);
    }
}