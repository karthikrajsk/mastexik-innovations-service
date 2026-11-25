package com.tech3.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.FabricDispatchForDyeingDTO;
import com.tech3.erp.dto.FabricDispatchYarnIssueDTO;
import com.tech3.erp.dto.FabricDispatchFabricIssueDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.FabricDispatchForDyeing;
import com.tech3.erp.entity.FabricDispatchYarnIssue;
import com.tech3.erp.entity.FabricDispatchFabricIssue;
import com.tech3.erp.entity.LotEntry;
import com.tech3.erp.entity.LotOutward;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnOutward;
import com.tech3.erp.entity.YarnOutwardStockDetails;
import com.tech3.erp.entity.YarnMaster;
import com.tech3.erp.entity.DyeingWorkOrder;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.FabricDispatchForDyeingRepository;
import com.tech3.erp.repository.FabricDispatchYarnIssueRepository;
import com.tech3.erp.repository.FabricDispatchFabricIssueRepository;
import com.tech3.erp.repository.LotEntryRepository;
import com.tech3.erp.repository.LotOutwardRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnOutwardRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.repository.DyeingWorkOrderRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.util.ProcessTypeUtil;
import com.tech3.erp.service.ReferenceValidationService;

import jakarta.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class FabricDispatchForDyeingService {

    @Autowired
    private FabricDispatchForDyeingRepository repository;
    
    @Autowired
    private FabricDispatchYarnIssueRepository yarnIssueRepository;
    
    @Autowired
    private FabricDispatchFabricIssueRepository fabricIssueRepository;
    
    @Autowired
    private LotEntryRepository lotEntryRepository;
    
    @Autowired
    private LotOutwardRepository lotOutwardRepository;
    
    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    
    @Autowired
    private YarnOutwardRepository yarnOutwardRepository;
    
    @Autowired
    private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;
    
    @Autowired
    private YarnMasterRepository yarnMasterRepository;
    
    @Autowired
    private DyeingWorkOrderRepository dyeingWorkOrderRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    private FabricDispatchForDyeingDTO convertToDTO(FabricDispatchForDyeing entity) {
        FabricDispatchForDyeingDTO dto = new FabricDispatchForDyeingDTO(entity);
        
        if (entity.getFabricIssues() != null && !entity.getFabricIssues().isEmpty()) {
            List<FabricDispatchFabricIssueDTO> fabricIssueDTOs = entity.getFabricIssues().stream()
                    .map(this::convertFabricIssueToDTO)
                    .collect(Collectors.toList());
            dto.setFabricIssues(fabricIssueDTOs);
        }
        
        if (entity.getYarnIssues() != null && !entity.getYarnIssues().isEmpty()) {
            List<FabricDispatchYarnIssueDTO> yarnIssueDTOs = entity.getYarnIssues().stream()
                    .map(this::convertYarnIssueToDTO)
                    .collect(Collectors.toList());
            dto.setYarnIssues(yarnIssueDTOs);
        }
        
        return dto;
    }

    private FabricDispatchYarnIssueDTO convertYarnIssueToDTO(FabricDispatchYarnIssue entity) {
        FabricDispatchYarnIssueDTO dto = new FabricDispatchYarnIssueDTO(
                entity.getId(),
                entity.getYarnName(),
                entity.getAvailableReqQty(),
                entity.getIssueQty(),
                entity.getActiveFlag()
        );
        dto.setMillId(entity.getMillId());
        dto.setWarehouseId(entity.getWarehouseId());
        dto.setNoOfBags(entity.getNoOfBags());
        dto.setKgsPerBag(entity.getKgsPerBag());
        dto.setConesPerBag(entity.getConesPerBag());
        dto.setConesWeight(entity.getConesWeight());
        dto.setLotNumber(entity.getLotNumber());
        dto.setAvailableBags(entity.getAvailableBags());
        dto.setYarnInwardStockDetailsId(entity.getYarnInwardStockDetailsId());
        dto.setInwardId(entity.getInwardId());
        dto.setInwardTypeId(entity.getInwardTypeId());
        dto.setOrderItemId(entity.getOrderItemId());
        dto.setRemark(entity.getRemark());
        dto.setVehicleNo(entity.getVehicleNo());
        dto.setGrossWeight(entity.getGrossWeight());
        dto.setFinishedYarnId(entity.getFinishedYarnId());
        dto.setFinishedYarnName(entity.getFinishedYarnName());
        dto.setGreigeYarnId(entity.getGreigeYarnId());
        dto.setGreigeYarn(entity.getGreigeYarn());
        return dto;
    }

    private FabricDispatchFabricIssueDTO convertFabricIssueToDTO(FabricDispatchFabricIssue entity) {
        return new FabricDispatchFabricIssueDTO(
                entity.getId(),
                entity.getPieceNo(),
                entity.getFabricCode(),
                entity.getFabricName(),
                entity.getAvailableQty(),
                entity.getIssueQty(),
                entity.getIssueType(),
                entity.getActiveFlag(),
                entity.getMeters(),
                entity.getNetWeight(),
                entity.getAvgWeight(),
                entity.getGradeLabel() 
        );
    }

    private FabricDispatchForDyeing convertToEntity(FabricDispatchForDyeingDTO dto) {
        FabricDispatchForDyeing entity = new FabricDispatchForDyeing();
        entity.setId(dto.getId());
        updateEntityFields(entity, dto);
        return entity;
    }

    public FabricDispatchForDyeingDTO createDispatch(FabricDispatchForDyeingDTO dto) {
        System.out.println("=== CREATE DISPATCH START ===");
        System.out.println("Creating dispatch with process type: " + dto.getProcessType());
        
        validateDispatch(dto);
        
        FabricDispatchForDyeing entity = convertToEntity(dto);
        
        String processType = ProcessTypeUtil.getValidProcessType(dto.getProcessType());
        System.out.println("Processing as: " + processType);
        
        if (ProcessTypeUtil.isFabric(processType) && dto.getFabricIssues() != null) {
            System.out.println("Processing " + dto.getFabricIssues().size() + " fabric issues");
            
            List<FabricDispatchFabricIssue> fabricIssues = dto.getFabricIssues().stream().map(i -> {
                System.out.println("=== FABRIC ISSUE DTO ===");
                System.out.println("PieceNo: " + i.getPieceNo());
                System.out.println("FabricCode from DTO: " + i.getFabricCode());
                System.out.println("FabricName from DTO: " + i.getFabricName());
                System.out.println("IssueQty: " + i.getIssueQty());
                System.out.println("IssueType: " + i.getIssueType());
                
                FabricDispatchFabricIssue issue = convertFabricIssueDTOToEntity(i);
                
                System.out.println("=== AFTER CONVERSION TO ENTITY ===");
                System.out.println("FabricCode in Entity: " + issue.getFabricCode());
                System.out.println("FabricName in Entity: " + issue.getFabricName());
                
                issue.setFabricDispatch(entity);
                return issue;
            }).collect(Collectors.toList());
            
            entity.setFabricIssues(fabricIssues);
            System.out.println("Added " + fabricIssues.size() + " fabric issues to entity");
        } else if (ProcessTypeUtil.isYarn(processType) && dto.getYarnIssues() != null) {
            System.out.println("Processing " + dto.getYarnIssues().size() + " yarn issues");
            List<FabricDispatchYarnIssue> yarnIssues = dto.getYarnIssues().stream().map(i -> {
                FabricDispatchYarnIssue issue = convertYarnIssueDTOToEntity(i);
                issue.setFabricDispatch(entity);
                return issue;
            }).collect(Collectors.toList());
            entity.setYarnIssues(yarnIssues);
            System.out.println("Added " + yarnIssues.size() + " yarn issues to entity");
        }
        
        System.out.println("=== SAVING ENTITY ===");
        FabricDispatchForDyeing savedEntity = repository.save(entity);
        System.out.println("Saved dispatch with ID: " + savedEntity.getId());
        
        if (savedEntity.getFabricIssues() != null) {
            System.out.println("=== AFTER SAVING - CHECKING FABRIC ISSUES ===");
            savedEntity.getFabricIssues().forEach(issue -> {
                System.out.println("PieceNo: " + issue.getPieceNo());
                System.out.println("FabricCode in Saved Entity: " + issue.getFabricCode());
                System.out.println("FabricName in Saved Entity: " + issue.getFabricName());
            });
        }
        
        processLotOutwardEntries(savedEntity);
        
        FabricDispatchForDyeingDTO responseDTO = convertToDTO(savedEntity);
        
        System.out.println("=== FINAL RESPONSE DTO ===");
        if (responseDTO.getFabricIssues() != null) {
            responseDTO.getFabricIssues().forEach(issue -> {
                System.out.println("PieceNo: " + issue.getPieceNo());
                System.out.println("FabricCode in Response: " + issue.getFabricCode());
                System.out.println("FabricName in Response: " + issue.getFabricName());
            });
        }
        
        System.out.println("=== CREATE DISPATCH END ===");
        return responseDTO;
    }
    
    public List<FabricDispatchForDyeingDTO> getAllDispatches() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(FabricDispatchForDyeing::getId).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FabricDispatchForDyeingDTO> getAllDispatchesWithPagination(Long offset, Long limit) {
        return PaginationHelper.paginate(getAllDispatches(), offset, limit);
    }

    public List<FabricDispatchForDyeingDTO> getAllDispatchesByType(String processType) {
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        return repository.findAll().stream()
                .filter(dispatch -> validProcessType.equals(dispatch.getProcessType()))
                .sorted(Comparator.comparing(FabricDispatchForDyeing::getId).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FabricDispatchForDyeingDTO> getAllDispatchesByTypeWithPagination(String processType, Long offset, Long limit) {
        return PaginationHelper.paginate(getAllDispatchesByType(processType), offset, limit);
    }

    public PaginatedResponse<FabricDispatchForDyeingDTO> getDispatchesByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        List<FabricDispatchForDyeingDTO> dispatches = repository.findByCompanyId(companyId).stream()
                .sorted(Comparator.comparing(FabricDispatchForDyeing::getId).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return PaginationHelper.paginate(dispatches, offset, limit);
    }

    public PaginatedResponse<FabricDispatchForDyeingDTO> getDispatchesByCompanyAndTypeWithPagination(Long companyId, String processType, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        List<FabricDispatchForDyeingDTO> dispatches = repository.findByCompanyIdAndProcessType(companyId, validProcessType).stream()
                .sorted(Comparator.comparing(FabricDispatchForDyeing::getId).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return PaginationHelper.paginate(dispatches, offset, limit);
    }

    public Optional<FabricDispatchForDyeingDTO> getDispatchById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    public FabricDispatchForDyeingDTO getDispatchByIdAndCompany(Long id, Long companyId) {
        FabricDispatchForDyeing dispatch = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Fabric Dispatch not found with id: " + id + " and companyId: " + companyId));
        return convertToDTO(dispatch);
    }

    public FabricDispatchForDyeingDTO getDispatchByIdAndCompanyAndType(Long id, Long companyId, String processType) {
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        FabricDispatchForDyeing dispatch = repository.findByIdAndCompanyIdAndProcessType(id, companyId, validProcessType)
                .orElseThrow(() -> new EntityNotFoundException("Fabric Dispatch not found with id: " + id + ", companyId: " + companyId + " and processType: " + validProcessType));
        return convertToDTO(dispatch);
    }

    public FabricDispatchForDyeingDTO updateDispatch(Long id, FabricDispatchForDyeingDTO dto) {
        System.out.println("Updating dispatch ID: " + id + " with process type: " + dto.getProcessType());
        
        FabricDispatchForDyeing existingDispatch = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Fabric Dispatch not found"));
        
        // 1. Reverse stock movements
        reverseLotEntries(existingDispatch);
        
        // 2. Validate new data
        validateDispatch(dto);
        
        // 3. Update main entity fields
        updateEntityFields(existingDispatch, dto);
        
        // 4. Sync issues collections
        String processType = ProcessTypeUtil.getValidProcessType(dto.getProcessType());
        if (ProcessTypeUtil.isFabric(processType)) {
            existingDispatch.getYarnIssues().clear();
            existingDispatch.getFabricIssues().clear();
            if (dto.getFabricIssues() != null) {
                for (FabricDispatchFabricIssueDTO i : dto.getFabricIssues()) {
                    FabricDispatchFabricIssue issue = convertFabricIssueDTOToEntity(i);
                    issue.setFabricDispatch(existingDispatch);
                    existingDispatch.getFabricIssues().add(issue);
                }
            }
        } else if (ProcessTypeUtil.isYarn(processType)) {
            existingDispatch.getFabricIssues().clear();
            existingDispatch.getYarnIssues().clear();
            if (dto.getYarnIssues() != null) {
                for (FabricDispatchYarnIssueDTO i : dto.getYarnIssues()) {
                    FabricDispatchYarnIssue issue = convertYarnIssueDTOToEntity(i);
                    issue.setFabricDispatch(existingDispatch);
                    existingDispatch.getYarnIssues().add(issue);
                }
            }
        }
        
        // 5. Save the updated entity
        FabricDispatchForDyeing savedEntity = repository.save(existingDispatch);
        
        // 6. Process new stock movements - ensure yarn issues are properly processed
        String savedProcessType = ProcessTypeUtil.getValidProcessType(savedEntity.getProcessType());
        if (ProcessTypeUtil.isYarn(savedProcessType) && savedEntity.getYarnIssues() != null && !savedEntity.getYarnIssues().isEmpty()) {
            processYarnLotOutward(savedEntity);
        } else {
            processLotOutwardEntries(savedEntity);
        }
        
        return convertToDTO(savedEntity);
    }

    private void updateEntityFields(FabricDispatchForDyeing entity, FabricDispatchForDyeingDTO dto) {
        entity.setFabricDispatchDate(dto.getFabricDispatchDate());
        entity.setVendorId(dto.getVendorId() != null ? dto.getVendorId() : 0L);
        entity.setDyeingWorkOrderId(dto.getDyeingWorkOrderId() != null ? dto.getDyeingWorkOrderId() : 0L);
        entity.setOrderQuantity(dto.getOrderQuantity() != null ? dto.getOrderQuantity() : 0.0);
        entity.setDispatchedQuantity(dto.getDispatchedQuantity() != null ? dto.getDispatchedQuantity() : 0.0);
        entity.setReceivedQuantity(dto.getReceivedQuantity() != null ? dto.getReceivedQuantity() : 0.0);
        entity.setBalanceQuantity(dto.getBalanceQuantity() != null ? dto.getBalanceQuantity() : 0.0);
        entity.setCostPerPound(dto.getCostPerPound() != null ? dto.getCostPerPound() : 0.0);
        entity.setTotalAmount(dto.getTotalAmount() != null ? dto.getTotalAmount() : 0.0);
        entity.setColorId(dto.getColorId() != null ? dto.getColorId() : 0L);
        entity.setPantone(dto.getPantone());
        entity.setFinishingId(dto.getFinishingId() != null ? dto.getFinishingId() : 0L);
        entity.setSalesOrderId(dto.getSalesOrderId() != null ? dto.getSalesOrderId() : 0L);
        entity.setShipmentModeId(dto.getShipmentModeId() != null ? dto.getShipmentModeId() : 0L);
        entity.setTransportId(dto.getTransportId());
        entity.setTransportName(dto.getTransportName());
        entity.setVehicleNo(dto.getVehicleNo());
        entity.setGrossWt(dto.getGrossWt());
        entity.setLotNumber(dto.getLotNumber());
        entity.setRemarks(dto.getRemarks());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        entity.setCompanyId(dto.getCompanyId());
        entity.setProcessType(ProcessTypeUtil.getValidProcessType(dto.getProcessType()));
        if (dto.getFabricDispatchId() != null) {
            entity.setDispatchNo(dto.getFabricDispatchId());
        }
        entity.setIssueType(dto.getIssueType());
        entity.setFinishedFabricCode(dto.getFinishedFabricCode());
        entity.setFinishedFabricName(dto.getFinishedFabricName());
        entity.setGreigeFabricCode(dto.getGreigeFabricCode());
        entity.setGreigeFabricName(dto.getGreigeFabricName());
    }

    public FabricDispatchForDyeingDTO updateDispatchByCompany(Long id, Long companyId, FabricDispatchForDyeingDTO dto) {
        FabricDispatchForDyeing dispatch = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Fabric Dispatch not found with id: " + id + " and companyId: " + companyId));
        dto.setCompanyId(dispatch.getCompanyId());
        return updateDispatch(id, dto);
    }

    public FabricDispatchForDyeingDTO updateDispatchByCompanyAndType(Long id, Long companyId, String processType, FabricDispatchForDyeingDTO dto) {
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        FabricDispatchForDyeing dispatch = repository.findByIdAndCompanyIdAndProcessType(id, companyId, validProcessType)
                .orElseThrow(() -> new EntityNotFoundException("Fabric Dispatch not found with id: " + id + ", companyId: " + companyId + " and processType: " + validProcessType));
        dto.setCompanyId(dispatch.getCompanyId());
        dto.setProcessType(validProcessType);
        return updateDispatch(id, dto);
    }

    public void deleteDispatch(Long id) {
        System.out.println("Deleting dispatch ID: " + id);
        
        FabricDispatchForDyeing dispatch = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Fabric Dispatch not found with id: " + id));
        
        reverseLotEntries(dispatch);
        
        try {
            repository.deleteById(id);
            System.out.println("Successfully deleted dispatch ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Fabric Dispatch because it is referenced by other");
        }
    }

    public void deleteDispatchByCompany(Long id, Long companyId) {
        repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Fabric Dispatch not found with id: " + id + " and companyId: " + companyId));
        deleteDispatch(id);
    }

    public void deleteDispatchByCompanyAndType(Long id, Long companyId, String processType) {
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        repository.findByIdAndCompanyIdAndProcessType(id, companyId, validProcessType)
                .orElseThrow(() -> new EntityNotFoundException("Fabric Dispatch not found with id: " + id + ", companyId: " + companyId + " and processType: " + validProcessType));
        deleteDispatch(id);
    }

    private FabricDispatchYarnIssue convertYarnIssueDTOToEntity(FabricDispatchYarnIssueDTO dto) {
        FabricDispatchYarnIssue entity = new FabricDispatchYarnIssue();
        entity.setId(dto.getId());
        entity.setYarnName(dto.getYarnName());
        entity.setAvailableReqQty(dto.getAvailableReqQty());
        entity.setIssueQty(dto.getIssueQty());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        entity.setMillId(dto.getMillId());
        entity.setWarehouseId(dto.getWarehouseId());
        entity.setNoOfBags(dto.getNoOfBags());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setConesPerBag(dto.getConesPerBag());
        entity.setConesWeight(dto.getConesWeight());
        entity.setLotNumber(dto.getLotNumber());
        entity.setAvailableBags(dto.getAvailableBags());
        entity.setYarnInwardStockDetailsId(dto.getYarnInwardStockDetailsId());
        entity.setInwardId(dto.getInwardId());
        entity.setInwardTypeId(dto.getInwardTypeId());
        entity.setOrderItemId(dto.getOrderItemId());
        entity.setRemark(dto.getRemark());
        entity.setVehicleNo(dto.getVehicleNo());
        entity.setGrossWeight(dto.getGrossWeight());
        entity.setFinishedYarnId(dto.getFinishedYarnId());
        entity.setFinishedYarnName(dto.getFinishedYarnName());
        
        // Fetch greigeYarnId from yarnMaster if not already set
        Long greigeYarnId = dto.getGreigeYarnId();
        System.out.println("DEBUG: Initial greigeYarnId: " + greigeYarnId + ", yarnName: " + dto.getYarnName());
        if (greigeYarnId == null && dto.getYarnName() != null && !dto.getYarnName().trim().isEmpty()) {
            Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(dto.getYarnName());
            System.out.println("DEBUG: YarnMaster found for yarnName '" + dto.getYarnName() + "': " + yarnMaster.isPresent());
            if (yarnMaster.isPresent()) {
                greigeYarnId = yarnMaster.get().getId();
                System.out.println("DEBUG: Fetched greigeYarnId from YarnMaster: " + greigeYarnId);
            } else {
                System.out.println("DEBUG: No YarnMaster found for yarnName: " + dto.getYarnName());
            }
        }
        entity.setGreigeYarnId(greigeYarnId);
        entity.setGreigeYarn(dto.getGreigeYarn());
        System.out.println("DEBUG: Final greigeYarnId set on entity: " + entity.getGreigeYarnId());
        return entity;
    }

    private FabricDispatchFabricIssue convertFabricIssueDTOToEntity(FabricDispatchFabricIssueDTO dto) {
        FabricDispatchFabricIssue entity = new FabricDispatchFabricIssue();
        entity.setId(dto.getId());
        entity.setPieceNo(dto.getPieceNo());
        entity.setFabricCode(dto.getFabricCode());
        entity.setFabricName(dto.getFabricName());
        entity.setAvailableQty(dto.getAvailableQty());
        entity.setIssueQty(dto.getIssueQty());
        entity.setIssueType(dto.getIssueType());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        
        entity.setMeters(dto.getMeters());
        entity.setNetWeight(dto.getNetWeight());
        entity.setAvgWeight(dto.getAvgWeight());
        entity.setGradeLabel(dto.getGradeLabel());
        return entity;
    }

    private void processLotOutwardEntries(FabricDispatchForDyeing dispatch) {
        String processType = ProcessTypeUtil.getValidProcessType(dispatch.getProcessType());
        
        // ... (rest of the code remains the same)
        if (ProcessTypeUtil.isYarn(processType) && dispatch.getYarnIssues() != null) {
            processYarnLotOutward(dispatch);
        } else if (ProcessTypeUtil.isFabric(processType) && dispatch.getFabricIssues() != null) {
            processFabricLotOutward(dispatch);
        }
        
        if (dispatch.getLotNumber() != null && !dispatch.getLotNumber().isEmpty() && 
            dispatch.getDispatchedQuantity() != null && dispatch.getDispatchedQuantity() > 0) {
            processMainLotOutward(dispatch);
        }
    }

    // private void processYarnLotOutward(FabricDispatchForDyeing dispatch) {
    //     List<YarnOutwardStockDetails> allOutwardDetails = new java.util.ArrayList<>();
        
    //     Long orderId = dispatch.getDyeingWorkOrderId();
    //     String orderNo = null;
    //     String financialYearId = null;
        
    //     if (orderId != null) {
    //         Optional<DyeingWorkOrder> dyeingWorkOrder = dyeingWorkOrderRepository.findById(orderId);
    //         if (dyeingWorkOrder.isPresent()) {
    //             orderNo = dyeingWorkOrder.get().getDyeingWorkOrderNo();
    //         }
    //     }
        
    //     if (dispatch.getFabricDispatchDate() != null) {
    //         financialYearId = getFinancialYearId(dispatch.getFabricDispatchDate());
    //     }

    //     // Map to track available quantities across multiple yarn issues in the same dispatch
    //     Map<Long, BigDecimal> tempAvailableQtyMap = new HashMap<>();
        
    //     for (FabricDispatchYarnIssue yarnIssue : dispatch.getYarnIssues()) {
    //         try {
    //             BigDecimal remainingToIssue = BigDecimal.valueOf(yarnIssue.getIssueQty() != null ? yarnIssue.getIssueQty() : 0.0);
    //             Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(yarnIssue.getYarnName());
    //             if (!yarnMaster.isPresent()) {
    //                 throw new EntityNotFoundException("Yarn not found with name: " + yarnIssue.getYarnName());
    //             }
                
    //             // Get yarn inward stock details - USING THE OLD CODE APPROACH
    //             List<YarnInwardStockDetails> inwardStockDetails;
    //             if (yarnIssue.getOrderItemId() != null) {
    //                 // Use the approach from old code - findByOrderItemId
    //                 Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository.findFirstByOrderItemId(yarnIssue.getOrderItemId());
    //                 inwardStockDetails = inwardDetailOpt.map(List::of).orElse(new java.util.ArrayList<>());
    //             } else if (yarnIssue.getYarnInwardStockDetailsId() != null) {
    //                 inwardStockDetails = new java.util.ArrayList<>();
    //                 yarnInwardStockDetailsRepository.findById(yarnIssue.getYarnInwardStockDetailsId())
    //                     .ifPresent(inwardStockDetails::add);
    //             } else {
    //                 inwardStockDetails = yarnInwardStockDetailsRepository.findByItemId(yarnMaster.get().getId());
    //                 inwardStockDetails.sort(Comparator.comparing(YarnInwardStockDetails::getId));
    //             }

    //             if (!inwardStockDetails.isEmpty()) {
    //                 YarnOutward yarnOutward = new YarnOutward();
    //                 yarnOutward.setOutwardTypeId(54L);
    //                 yarnOutward.setOutwardNo(generateYarnOutwardNo(dispatch));
    //                 yarnOutward.setCompanyId(1L);
    //                 LocalDate outwardDate = dispatch.getFabricDispatchDate() != null ? 
    //                     dispatch.getFabricDispatchDate() : LocalDate.now();
    //                 yarnOutward.setOutwardDate(outwardDate);
    //                 yarnOutward.setOrderId(orderId);
    //                 yarnOutward.setOrderNo(orderNo);
    //                 yarnOutward.setFinancialYearId(financialYearId);
    //                 yarnOutward.setVendorId(dispatch.getVendorId());
    //                 yarnOutward.setRemarks(null);
    //                 yarnOutward.setActiveFlag(true);
                    
    //                 YarnOutward savedYarnOutward = yarnOutwardRepository.save(yarnOutward);
                    
    //                 for (YarnInwardStockDetails inwardDetail : inwardStockDetails) {
    //                     if (remainingToIssue.compareTo(BigDecimal.ZERO) <= 0) break;

    //                     // Get available quantity using the old code approach
    //                     BigDecimal available = inwardDetail.getAvailableQty() != null ? inwardDetail.getAvailableQty() : BigDecimal.ZERO;
                        
    //                     if (available.compareTo(BigDecimal.ZERO) <= 0) continue;

    //                     BigDecimal toTake = remainingToIssue.min(available);
                        
    //                     YarnOutwardStockDetails outwardDetail = new YarnOutwardStockDetails();
    //                     outwardDetail.setInwardId(inwardDetail.getYarnInventory() != null ? 
    //                         inwardDetail.getYarnInventory().getId() : null);
    //                     outwardDetail.setOrderItemId(inwardDetail.getOrderItemId());
    //                     outwardDetail.setYarnInwardStockDetailsId(inwardDetail.getId());
    //                     outwardDetail.setOutwardTypeId(54L);
    //                     outwardDetail.setActualItemId(inwardDetail.getItemId());
    //                     outwardDetail.setUomId(inwardDetail.getUomId());
    //                     outwardDetail.setMillId(inwardDetail.getMillId());
    //                     outwardDetail.setWarehouseId(inwardDetail.getWarehouseId());
    //                     outwardDetail.setLotNo(inwardDetail.getLotNo());
    //                     outwardDetail.setIssuedQty(toTake);
                        
    //                     // Handle bags distribution proportionally for splits
    //                     BigDecimal bagsToAssign = BigDecimal.ZERO;
    //                     if (yarnIssue.getNoOfBags() != null && yarnIssue.getIssueQty() != null && yarnIssue.getIssueQty() > 0) {
    //                         bagsToAssign = BigDecimal.valueOf(yarnIssue.getNoOfBags())
    //                             .multiply(toTake)
    //                             .divide(BigDecimal.valueOf(yarnIssue.getIssueQty()), 4, RoundingMode.HALF_UP);
    //                     } else if (yarnIssue.getNoOfBags() != null) {
    //                         bagsToAssign = BigDecimal.valueOf(yarnIssue.getNoOfBags());
    //                     }
    //                     outwardDetail.setIssuedBags(bagsToAssign);

    //                     outwardDetail.setKgsPerBag(yarnIssue.getKgsPerBag());
    //                     outwardDetail.setConesPerBag(yarnIssue.getConesPerBag());
    //                     outwardDetail.setConesWeight(yarnIssue.getConesWeight());
    //                     outwardDetail.setYarnOutward(savedYarnOutward);
                        
    //                     YarnOutwardStockDetails savedOutwardDetail = yarnOutwardStockDetailsRepository.save(outwardDetail);
    //                     allOutwardDetails.add(savedOutwardDetail);
                        
    //                     // Update available quantity using the OLD CODE APPROACH
    //                     updateAvailableQuantityForYarnIssue(inwardDetail.getId(), toTake);
                        
    //                     remainingToIssue = remainingToIssue.subtract(toTake);
    //                 }
                    
    //                 if (remainingToIssue.compareTo(BigDecimal.ZERO) > 0) {
    //                     throw new RuntimeException("Insufficient stock for yarn: " + yarnIssue.getYarnName() + 
    //                             ". Shortfall: " + remainingToIssue);
    //                 }
    //             }
    //         } catch (Exception e) {
    private void processYarnLotOutward(FabricDispatchForDyeing dispatch) {
        if (dispatch.getYarnIssues() == null || dispatch.getYarnIssues().isEmpty()) {
            return;
        }

        // 1. Create ONE YarnOutward for the entire dispatch
        YarnOutward yarnOutward = createYarnOutwardFromDispatch(dispatch);
        
        // Maps to track available quantities and consolidated outward details across the dispatch
        Map<Long, BigDecimal> localAvailableQtyMap = new HashMap<>();
        Map<Long, BigDecimal> localAvailableBagsMap = new HashMap<>();
        Map<Long, YarnOutwardStockDetails> consolidatedOutwardMap = new HashMap<>();

        for (FabricDispatchYarnIssue yarnIssue : dispatch.getYarnIssues()) {
            if (yarnIssue.getIssueQty() == null || yarnIssue.getIssueQty() <= 0) {
                continue;
            }

            try {
                BigDecimal remainingToIssue = BigDecimal.valueOf(yarnIssue.getIssueQty());
                BigDecimal totalIssueBags = yarnIssue.getNoOfBags() != null ? 
                    BigDecimal.valueOf(yarnIssue.getNoOfBags()) : BigDecimal.ZERO;
                
                // Get the Yarn Item ID to prevent cross-yarn stock matching
                Long itemId = null;
                Optional<YarnMaster> ym = yarnMasterRepository.findByYarnName(yarnIssue.getYarnName());
                if (ym.isPresent()) {
                    itemId = ym.get().getId();
                    
                    // Set greigeYarnId from yarnMaster if not already set
                    if (yarnIssue.getGreigeYarnId() == null) {
                        yarnIssue.setGreigeYarnId(itemId);
                    }
                }

                // 2. Find eligible stock records
                List<YarnInwardStockDetails> stocks = new java.util.ArrayList<>();
                
                // First try to find by inwardId if provided in payload
                if (yarnIssue.getInwardId() != null && yarnIssue.getInwardId() > 0) {
                    List<YarnInwardStockDetails> stocksByInwardId = yarnInwardStockDetailsRepository.findByYarnInventoryId(yarnIssue.getInwardId());
                    if (itemId != null) {
                        final Long finalItemId = itemId;
                        stocksByInwardId.stream()
                            .filter(s -> finalItemId.equals(s.getItemId()))
                            .forEach(stocks::add);
                    }
                }
                
                // If no stocks found by inwardId, try yarnInwardStockDetailsId
                if (stocks.isEmpty() && yarnIssue.getYarnInwardStockDetailsId() != null) {
                    yarnInwardStockDetailsRepository.findById(yarnIssue.getYarnInwardStockDetailsId()).ifPresent(stocks::add);
                }
                
                // Add fallback stocks for the same yarn if not enough specifically identified
                if (itemId != null && stocks.isEmpty()) {
                    final Long finalItemId = itemId;
                    // Filter by greigeYarnId or orderItemId if valid, otherwise by itemId
                    List<YarnInwardStockDetails> additionalStocks = new java.util.ArrayList<>();
                    if (yarnIssue.getGreigeYarnId() != null && yarnIssue.getGreigeYarnId() > 0) {
                        additionalStocks = yarnInwardStockDetailsRepository.findAllByOrderItemId(yarnIssue.getGreigeYarnId());
                    } else if (yarnIssue.getOrderItemId() != null && yarnIssue.getOrderItemId() > 0) {
                        additionalStocks = yarnInwardStockDetailsRepository.findAllByOrderItemId(yarnIssue.getOrderItemId());
                    } else {
                        additionalStocks = yarnInwardStockDetailsRepository.findByItemId(finalItemId);
                    }
                    
                    for (YarnInwardStockDetails s : additionalStocks) {
                        if (finalItemId.equals(s.getItemId()) && stocks.stream().noneMatch(e -> e.getId().equals(s.getId()))) {
                            stocks.add(s);
                        }
                    }
                }
                stocks.sort(java.util.Comparator.comparing(YarnInwardStockDetails::getId));

                boolean firstInwardAssignedForThisLine = false;

                for (YarnInwardStockDetails yarnStock : stocks) {
                    if (remainingToIssue.compareTo(BigDecimal.ZERO) <= 0) break;
                    
                    BigDecimal lotAvailable = localAvailableQtyMap.getOrDefault(yarnStock.getId(), 
                        yarnStock.getAvailableQty() != null ? yarnStock.getAvailableQty() : BigDecimal.ZERO);
                    BigDecimal lotBagsAvailable = localAvailableBagsMap.getOrDefault(yarnStock.getId(),
                        yarnStock.getAvailableBags() != null ? yarnStock.getAvailableBags() : BigDecimal.ZERO);
                    
                    if (lotAvailable.compareTo(BigDecimal.ZERO) <= 0) continue;
                    
                    BigDecimal toTake = remainingToIssue.min(lotAvailable);
                    
                    // Calculate bags proportionally for this split
                    BigDecimal bagsToTake;
                    if (toTake.compareTo(remainingToIssue) == 0) {
                        bagsToTake = totalIssueBags.min(lotBagsAvailable);
                    } else {
                        bagsToTake = totalIssueBags.multiply(toTake)
                            .divide(BigDecimal.valueOf(yarnIssue.getIssueQty()), 4, RoundingMode.HALF_UP).min(lotBagsAvailable);
                    }
                    
                    // 3. Consolidate into ONE YarnOutwardStockDetails per inward source used
                    YarnOutwardStockDetails outwardDetail = consolidatedOutwardMap.get(yarnStock.getId());
                    if (outwardDetail == null) {
                        outwardDetail = new YarnOutwardStockDetails();
                        outwardDetail.setYarnOutward(yarnOutward);
                        outwardDetail.setOutwardTypeId(getFabricDispatchOutwardTypeId());
                        outwardDetail.setWarehouseId(yarnStock.getWarehouseId());
                        outwardDetail.setActualItemId(yarnStock.getItemId());
                        outwardDetail.setInwardId(yarnStock.getInwardId());
                        outwardDetail.setOrderItemId(yarnStock.getOrderItemId());
                        outwardDetail.setYarnInwardStockDetailsId(yarnStock.getId());
                        outwardDetail.setMillId(yarnStock.getMillId());
                        outwardDetail.setUomId(yarnStock.getUomId());
                        outwardDetail.setLotNo(yarnStock.getLotNo());
                        outwardDetail.setIssuedQty(BigDecimal.ZERO);
                        outwardDetail.setIssuedBags(BigDecimal.ZERO);
                        outwardDetail.setKgsPerBag(yarnIssue.getKgsPerBag());
                        outwardDetail.setConesPerBag(yarnIssue.getConesPerBag());
                        outwardDetail.setConesWeight(yarnIssue.getConesWeight());
                        
                        consolidatedOutwardMap.put(yarnStock.getId(), outwardDetail);
                        if (yarnOutward.getYarnOutwardStockDetails() == null) {
                            yarnOutward.setYarnOutwardStockDetails(new java.util.ArrayList<>());
                        }
                        yarnOutward.getYarnOutwardStockDetails().add(outwardDetail);
                    }
                    
                    // Add to existing consolidated detail
                    outwardDetail.setIssuedQty(outwardDetail.getIssuedQty().add(toTake));
                    outwardDetail.setIssuedBags(outwardDetail.getIssuedBags().add(bagsToTake));
                    
                    // 4. Update the yarnIssue entity with the actual stock record used
                    if (!firstInwardAssignedForThisLine) {
                        yarnIssue.setYarnInwardStockDetailsId(yarnStock.getId());
                        yarnIssue.setInwardId(yarnStock.getInwardId());
                        firstInwardAssignedForThisLine = true;
                    }
                    
                    // 5. Update state for next iterations
                    updateAvailableQuantity(yarnStock.getId(), toTake, bagsToTake);
                    localAvailableQtyMap.put(yarnStock.getId(), lotAvailable.subtract(toTake));
                    localAvailableBagsMap.put(yarnStock.getId(), lotBagsAvailable.subtract(bagsToTake));
                    
                    remainingToIssue = remainingToIssue.subtract(toTake);
                    totalIssueBags = totalIssueBags.subtract(bagsToTake);
                }
                
                // Save updated issue line with correct inward mappings
                yarnIssueRepository.save(yarnIssue);
                
                if (remainingToIssue.compareTo(BigDecimal.ZERO) > 0) {
                    System.out.println("Warning: Insufficient available quantity for yarn: " + yarnIssue.getYarnName() +
                            ". Shortfall: " + remainingToIssue);
                }
                
            } catch (Exception e) {
                System.err.println("Error processing yarn lot outward for issue " + yarnIssue.getYarnName() + ": " + e.getMessage());
                throw new RuntimeException("Failed to process yarn lot outward: " + e.getMessage(), e);
            }
        }
        
        // Final flush to ensure all consolidated details are saved via YarnOutward cascade
        yarnOutwardRepository.saveAndFlush(yarnOutward);
    }

private YarnOutward createYarnOutwardFromDispatch(FabricDispatchForDyeing dispatch) {
    YarnOutward yarnOutward = new YarnOutward();

    yarnOutward.setOutwardTypeId(getFabricDispatchOutwardTypeId()); // Fabric dispatch yarn issue type
    
    String baseOutwardNo = (dispatch.getDispatchNo() != null && !dispatch.getDispatchNo().trim().isEmpty()) ?
        dispatch.getDispatchNo().trim() : "FDY-" + dispatch.getId() + "-" + System.currentTimeMillis();
    
    String outwardNo = baseOutwardNo;
    int counter = 1;
    while (yarnOutwardRepository.existsByOutwardNo(outwardNo)) {
        outwardNo = baseOutwardNo + "-" + counter;
        counter++;
    }
    yarnOutward.setOutwardNo(outwardNo);
    yarnOutward.setCompanyId(dispatch.getCompanyId() != null ? dispatch.getCompanyId() : 1L); 

    LocalDate outwardDate = dispatch.getFabricDispatchDate() != null ?
        dispatch.getFabricDispatchDate() : LocalDate.now();
    yarnOutward.setOutwardDate(outwardDate);

    // Set financial year
    int year = outwardDate.getYear();
    int month = outwardDate.getMonthValue();
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
    yarnOutward.setFinancialYearId(startYear + "-" + endYearLastTwo);

    yarnOutward.setOrderId(dispatch.getDyeingWorkOrderId() != null ? dispatch.getDyeingWorkOrderId() : dispatch.getId());
    
    if (dispatch.getDyeingWorkOrderId() != null) {
        dyeingWorkOrderRepository.findById(dispatch.getDyeingWorkOrderId())
            .ifPresent(dwo -> yarnOutward.setOrderNo(dwo.getDyeingWorkOrderNo()));
    }
    
    if (yarnOutward.getOrderNo() == null) {
        yarnOutward.setOrderNo("FABRIC_DISPATCH_" + dispatch.getId());
    }
    
    yarnOutward.setVendorId(dispatch.getVendorId() != null ? dispatch.getVendorId() : 0L);
    yarnOutward.setRemarks(null);
    yarnOutward.setActiveFlag(true);
    yarnOutward.setCreatedBy(dispatch.getCreatedBy());

    return yarnOutwardRepository.save(yarnOutward);
}
    private void processFabricLotOutward(FabricDispatchForDyeing dispatch) {
        LotEntry lot = null;
        if (dispatch.getLotNumber() != null && !dispatch.getLotNumber().isEmpty()) {
            lot = lotEntryRepository.findByLotNumber(dispatch.getLotNumber()).orElse(null);
        }
        Long lotId = (lot != null) ? lot.getId() : 0L;

        for (FabricDispatchFabricIssue fabricIssue : dispatch.getFabricIssues()) {
            try {
                BigDecimal issueQty = BigDecimal.valueOf(fabricIssue.getIssueQty());
                
                LotOutward outward = new LotOutward();
                outward.setLotEntryId(lotId);
                outward.setOutwardType("FABRIC_DISPATCH_ISSUE");
                outward.setReferenceType("FABRIC_DISPATCH");
                outward.setReferenceTypeId("FABRIC_DISPATCH - " + dispatch.getId());
                outward.setReferenceId(dispatch.getId());
                outward.setQuantity(issueQty);
                
                if (dispatch.getFabricDispatchDate() != null) {
                    outward.setOutwardDate(dispatch.getFabricDispatchDate());
                } else {
                    outward.setOutwardDate(LocalDate.now());
                }
                
                outward.setRemarks("Fabric Dispatch " + fabricIssue.getIssueType() + " - " + fabricIssue.getPieceNo());
                outward.setActiveFlag(true);
                outward.setCreatedAt(LocalDateTime.now());
                
                lotOutwardRepository.save(outward);
                
            } catch (Exception e) {
                System.err.println("Error processing fabric lot outward: " + e.getMessage());
                throw new RuntimeException("Failed to process fabric lot outward: " + e.getMessage(), e);
            }
        }
    }

    private void processMainLotOutward(FabricDispatchForDyeing dispatch) {
        try {
            BigDecimal dispatchedQty = BigDecimal.valueOf(dispatch.getDispatchedQuantity());
            LotEntry lot = reduceLotQuantity(dispatch.getLotNumber(), dispatchedQty);
            
            LotOutward outward = new LotOutward();
            outward.setLotEntryId(lot.getId());
            outward.setOutwardType("FABRIC_DISPATCH_MAIN");
            outward.setReferenceType("FABRIC_DISPATCH");
            outward.setReferenceTypeId("FABRIC_DISPATCH - " + dispatch.getId());
            outward.setReferenceId(dispatch.getId());
            outward.setQuantity(dispatchedQty);
            
            if (dispatch.getFabricDispatchDate() != null) {
                outward.setOutwardDate(dispatch.getFabricDispatchDate());
            } else {
                outward.setOutwardDate(LocalDate.now());
            }
            
            outward.setRemarks("Fabric dispatch for dyeing" + 
                (dispatch.getDyeingWorkOrderId() != null ? ", WO: " + dispatch.getDyeingWorkOrderId() : ""));
            outward.setActiveFlag(true);
            outward.setCreatedAt(LocalDateTime.now());
            
            lotOutwardRepository.save(outward);
            
        } catch (Exception e) {
            System.err.println("Error processing main lot outward: " + e.getMessage());
            throw new RuntimeException("Failed to process main lot outward: " + e.getMessage(), e);
        }
    }

   private void reverseLotEntries(FabricDispatchForDyeing dispatch) {
    // 1. Reverse lot entries
    List<LotOutward> outwardEntries = lotOutwardRepository.findByReferenceTypeAndReferenceId(
        "FABRIC_DISPATCH", dispatch.getId());
    
    if (!outwardEntries.isEmpty()) {
        for (LotOutward outward : outwardEntries) {
            try {
                restoreLotQuantity(outward.getLotEntryId(), outward.getQuantity());
            } catch (Exception e) {
                System.err.println("Error reversing lot entry: " + e.getMessage());
            }
        }
        lotOutwardRepository.deleteAll(outwardEntries);
    }
    
    // 2. Reverse yarn stock using OLD CODE APPROACH (direct restoration)
    // First try to find by exact outwardNo generated in createYarnOutwardFromDispatch
    String outwardNo = (dispatch.getDispatchNo() != null && !dispatch.getDispatchNo().trim().isEmpty()) ?
        dispatch.getDispatchNo().trim() : "FDY-" + dispatch.getId();
    
    // Also try to find by pattern in case there are multiple
    List<YarnOutward> yarnOutwardEntries = yarnOutwardRepository.findByOutwardNoContaining(outwardNo);
    if (yarnOutwardEntries.isEmpty()) {
        // Fallback to original approach with generated outwardNo
        outwardNo = generateYarnOutwardNo(dispatch);
        yarnOutwardRepository.findByOutwardNo(outwardNo)
            .ifPresent(yarnOutwardEntries::add);
    }
    
    if (!yarnOutwardEntries.isEmpty()) {
        for (YarnOutward yarnOutward : yarnOutwardEntries) {
            List<YarnOutwardStockDetails> outwardDetails = yarnOutwardStockDetailsRepository.findByOutwardId(yarnOutward.getId());
            
            if (!outwardDetails.isEmpty()) {
                // Restore stock for each outward detail using OLD CODE APPROACH
                for (YarnOutwardStockDetails outwardDetail : outwardDetails) {
                    if (outwardDetail.getYarnInwardStockDetailsId() != null) {
                        // Use the direct restoration method from old code
                        restoreAvailableQuantity(outwardDetail.getYarnInwardStockDetailsId(), 
                                               outwardDetail.getIssuedQty(),
                                               outwardDetail.getIssuedBags());
                    } else if (outwardDetail.getOrderItemId() != null) {
                        // Fallback: Find the stock details by orderItemId
                        Optional<YarnInwardStockDetails> stockDetails = yarnInwardStockDetailsRepository
                            .findFirstByOrderItemId(outwardDetail.getOrderItemId());
                        if (stockDetails.isPresent()) {
                            restoreAvailableQuantity(stockDetails.get().getId(), 
                                                   outwardDetail.getIssuedQty(),
                                                   outwardDetail.getIssuedBags());
                        }
                    }
                }
                
                // Clean up the outward records
                yarnOutwardStockDetailsRepository.deleteAll(outwardDetails);
                yarnOutwardRepository.delete(yarnOutward);
            }
        }
        yarnOutwardRepository.flush();
    }
}
    private LotEntry findLotById(String lotIdString) {
        try {
            try {
                Long lotId = Long.parseLong(lotIdString);
                return lotEntryRepository.findById(lotId)
                    .orElseThrow(() -> new EntityNotFoundException("Lot not found with ID: " + lotIdString));
            } catch (NumberFormatException e) {
                return lotEntryRepository.findByLotNumber(lotIdString)
                    .orElseThrow(() -> new EntityNotFoundException("Lot not found with number: " + lotIdString));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid lot ID format: " + lotIdString);
        }
    }
    
    private LotEntry reduceLotQuantity(String lotNumber, BigDecimal quantity) {
        LotEntry lot = lotEntryRepository.findByLotNumber(lotNumber)
            .orElseThrow(() -> new EntityNotFoundException("Lot not found with number: " + lotNumber));
        
        if (lot.getQuantity().compareTo(quantity) < 0) {
            throw new IllegalArgumentException("Not enough quantity in lot " + lot.getLotNumber());
        }
        
        lot.setQuantity(lot.getQuantity().subtract(quantity));
        return lotEntryRepository.save(lot);
    }

    private void restoreLotQuantity(Long lotId, BigDecimal quantityToRestore) {
        LotEntry lot = lotEntryRepository.findById(lotId)
            .orElseThrow(() -> new EntityNotFoundException("Lot not found with ID: " + lotId));
        
        lot.setQuantity(lot.getQuantity().add(quantityToRestore));
        lotEntryRepository.save(lot);
    }

    private void validateDispatch(FabricDispatchForDyeingDTO dto) {
        validateReferences(dto);
        String processType = ProcessTypeUtil.getValidProcessType(dto.getProcessType());
        
        if (ProcessTypeUtil.isYarn(processType) && dto.getYarnIssues() != null) {
            // Track total requested quantity per stock detail or order item
            Map<Long, BigDecimal> stockRequestMap = new HashMap<>();
            Map<Long, BigDecimal> orderItemRequestMap = new HashMap<>();
            Map<Long, BigDecimal> itemRequestMap = new HashMap<>();

            for (FabricDispatchYarnIssueDTO yarnIssue : dto.getYarnIssues()) {
                if (yarnIssue.getIssueQty() != null && yarnIssue.getIssueQty() > 0) {
                    Optional<YarnMaster> yarnMaster = yarnMasterRepository.findByYarnName(yarnIssue.getYarnName());
                    if (!yarnMaster.isPresent()) {
                        throw new EntityNotFoundException("Yarn not found: " + yarnIssue.getYarnName());
                    }
                    
                    BigDecimal issueQty = BigDecimal.valueOf(yarnIssue.getIssueQty());
                    
                    if (yarnIssue.getYarnInwardStockDetailsId() != null) {
                        stockRequestMap.merge(yarnIssue.getYarnInwardStockDetailsId(), issueQty, BigDecimal::add);
                    } else if (yarnIssue.getOrderItemId() != null) {
                        orderItemRequestMap.merge(yarnIssue.getOrderItemId(), issueQty, BigDecimal::add);
                    } else {
                        itemRequestMap.merge(yarnMaster.get().getId(), issueQty, BigDecimal::add);
                    }
                }
            }

            // Validate stock requests
            for (Map.Entry<Long, BigDecimal> entry : stockRequestMap.entrySet()) {
                BigDecimal availableQty = yarnInwardStockDetailsRepository.findById(entry.getKey())
                    .map(s -> s.getAvailableQty() != null ? s.getAvailableQty() : BigDecimal.ZERO)
                    .orElse(BigDecimal.ZERO);
                if (availableQty.compareTo(entry.getValue()) < 0) {
                    throw new IllegalArgumentException("Insufficient available quantity for stock detail ID: " + 
                        entry.getKey() + ". Available: " + availableQty + ", Total Requested: " + entry.getValue());
                }
            }

            // Validate order item requests
            for (Map.Entry<Long, BigDecimal> entry : orderItemRequestMap.entrySet()) {
                List<YarnInwardStockDetails> stocks = yarnInwardStockDetailsRepository.findAllByOrderItemId(entry.getKey());
                BigDecimal availableQty = stocks.stream()
                    .map(s -> s.getAvailableQty() != null ? s.getAvailableQty() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (availableQty.compareTo(entry.getValue()) < 0) {
                    throw new IllegalArgumentException("Insufficient available quantity for order item ID: " + 
                        entry.getKey() + ". Available: " + availableQty + ", Total Requested: " + entry.getValue());
                }
            }

            // Validate item requests
            for (Map.Entry<Long, BigDecimal> entry : itemRequestMap.entrySet()) {
                BigDecimal availableQty = yarnInwardStockDetailsRepository.sumAvailableQtyByItemId(entry.getKey());
                if (availableQty.compareTo(entry.getValue()) < 0) {
                    throw new IllegalArgumentException("Insufficient available quantity for item ID: " + 
                        entry.getKey() + ". Available: " + availableQty + ", Total Requested: " + entry.getValue());
                }
            }
        }
        System.out.println("Dispatch validation passed for process type: " + processType);
    }

    // ===== METHODS FROM OLD CODE FOR AVAILABLE QTY CALCULATION =====
    
    /**
     * Updates the available quantity for yarn inward stock (From Old Code)
     */
   private void updateAvailableQuantity(Long yarnInwardStockDetailsId, BigDecimal issuedQty, BigDecimal issuedBags) {
    if (yarnInwardStockDetailsId == null || issuedQty == null) {
        System.out.println("Cannot update available quantity - yarnInwardStockDetailsId or issuedQty is null");
        return;
    }
    
    try {
        Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository.findById(yarnInwardStockDetailsId);
        
        if (!inwardDetailOpt.isPresent()) {
            System.out.println("Yarn inward stock details not found with id: " + yarnInwardStockDetailsId);
            return;
        }
        
        YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
        System.out.println("Before update - AvailableQty: " + inwardDetail.getAvailableQty() + 
                         ", AcceptedQty: " + inwardDetail.getAcceptedQty() + 
                         ", IssuedQty: " + issuedQty +
                         ", IssuedBags: " + issuedBags);
        
        BigDecimal currentAvailableQty = inwardDetail.getAvailableQty() != null ? 
            inwardDetail.getAvailableQty() : BigDecimal.ZERO;
        BigDecimal availableQty = currentAvailableQty.subtract(issuedQty);
        
        if (availableQty.compareTo(BigDecimal.ZERO) < 0) {
            System.err.println("Warning: Insufficient available quantity. Current: " + 
                currentAvailableQty + ", Trying to subtract: " + issuedQty);
            availableQty = BigDecimal.ZERO;
        }
        
        inwardDetail.setAvailableQty(availableQty);
        
        // Handle available bags update
        if (inwardDetail.getAvailableBags() != null) {
            BigDecimal currentAvailableBags = inwardDetail.getAvailableBags();
            BigDecimal bagsToSubtract = issuedBags;
            
            // If issuedBags is not provided, calculate proportionally (fallback)
            if (bagsToSubtract == null || bagsToSubtract.compareTo(BigDecimal.ZERO) == 0) {
                if (inwardDetail.getAcceptedQty() != null && 
                    inwardDetail.getAcceptedQty().compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal reductionRatio = issuedQty.divide(inwardDetail.getAcceptedQty(), 4, RoundingMode.HALF_UP);
                    bagsToSubtract = currentAvailableBags.multiply(reductionRatio);
                } else {
                    bagsToSubtract = BigDecimal.ZERO;
                }
            }
            
            BigDecimal newAvailableBags = currentAvailableBags.subtract(bagsToSubtract);
            if (newAvailableBags.compareTo(BigDecimal.ZERO) < 0) {
                newAvailableBags = BigDecimal.ZERO;
            }
            inwardDetail.setAvailableBags(newAvailableBags);
        }
        
        YarnInwardStockDetails updatedDetail = yarnInwardStockDetailsRepository.save(inwardDetail);
        
        System.out.println("After update - AvailableQty: " + updatedDetail.getAvailableQty() + 
                         ", AvailableBags: " + updatedDetail.getAvailableBags());
        
    } catch (Exception e) {
        System.err.println("Warning: Error updating available quantity for ID: " + 
            yarnInwardStockDetailsId + " - " + e.getMessage());
        throw new RuntimeException("Failed to update available quantity", e);
    }
}
    /**
     * Restores available quantity when reversing a transaction (From Old Code approach)
     */
    private void restoreAvailableQuantity(Long yarnInwardStockDetailsId, BigDecimal restoredQty, BigDecimal restoredBags) {
    if (yarnInwardStockDetailsId == null || restoredQty == null) {
        return;
    }
    
    try {
        Optional<YarnInwardStockDetails> inwardDetailOpt = yarnInwardStockDetailsRepository
            .findById(yarnInwardStockDetailsId);
        
        if (!inwardDetailOpt.isPresent()) {
            return;
        }
        
        YarnInwardStockDetails inwardDetail = inwardDetailOpt.get();
        BigDecimal currentAvailableQty = inwardDetail.getAvailableQty() != null ? 
            inwardDetail.getAvailableQty() : BigDecimal.ZERO;
        BigDecimal availableQty = currentAvailableQty.add(restoredQty);
        
        // Cap at accepted quantity
        BigDecimal acceptedQty = inwardDetail.getAcceptedQty() != null ? 
            inwardDetail.getAcceptedQty() : BigDecimal.ZERO;
        if (availableQty.compareTo(acceptedQty) > 0) {
            availableQty = acceptedQty;
        }
        
        inwardDetail.setAvailableQty(availableQty);
        
        // Restore bags
        if (inwardDetail.getAvailableBags() != null) {
            BigDecimal currentAvailableBags = inwardDetail.getAvailableBags();
            BigDecimal bagsToRestore = restoredBags;
            
            // If restoredBags is not provided, calculate proportionally (fallback)
            if (bagsToRestore == null || bagsToRestore.compareTo(BigDecimal.ZERO) == 0) {
                if (acceptedQty.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal restorationRatio = restoredQty.divide(acceptedQty, 4, RoundingMode.HALF_UP);
                    BigDecimal totalBagsForCalc = inwardDetail.getInwardBags() != null ?
                        inwardDetail.getInwardBags() : currentAvailableBags;
                    bagsToRestore = totalBagsForCalc.multiply(restorationRatio);
                } else {
                    bagsToRestore = BigDecimal.ZERO;
                }
            }
            
            BigDecimal newAvailableBags = currentAvailableBags.add(bagsToRestore);
            // Cap at inwardBags if available, otherwise don't cap (prevents overcorrection)
            if (inwardDetail.getInwardBags() != null && newAvailableBags.compareTo(inwardDetail.getInwardBags()) > 0) {
                newAvailableBags = inwardDetail.getInwardBags();
            }
            inwardDetail.setAvailableBags(newAvailableBags);
        }
        
        yarnInwardStockDetailsRepository.save(inwardDetail);
        
    } catch (Exception e) {
        System.err.println("Warning: Error restoring available quantity: " + e.getMessage());
    }
}

    private String generateYarnOutwardNo(FabricDispatchForDyeing dispatch) {
        int year = dispatch.getFabricDispatchDate() != null ? 
            dispatch.getFabricDispatchDate().getYear() : LocalDate.now().getYear();
        return "FDO-" + year + "-" + dispatch.getId();
    }

    private String getFinancialYearId(LocalDate date) {
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

    private Long normalizeId(Long id) {
        return (id == null || id <= 0) ? null : id;
    }

    private void validateReferences(FabricDispatchForDyeingDTO dto) {
        Long dyeingWorkOrderId = normalizeId(dto.getDyeingWorkOrderId());
        if (dyeingWorkOrderId != null) {
            referenceValidationService.requireReference(
                dyeingWorkOrderId,
                () -> dyeingWorkOrderRepository.findById(dyeingWorkOrderId),
                "Dyeing Work Order",
                null);
        }

        Long salesOrderId = normalizeId(dto.getSalesOrderId());
        if (salesOrderId != null) {
            referenceValidationService.requireReference(
                salesOrderId,
                () -> salesOrderRepository.findById(salesOrderId),
                "Sales Order",
                null);
        }
    }

    private Long getFabricDispatchOutwardTypeId() {
        return codesDetailRepository.findByCode("PROCESSING_ISSUE")
                .map(CodesDetail::getId)
                .orElse(59L); // Fallback to 54L to maintain existing functionality if code not found
    }
}