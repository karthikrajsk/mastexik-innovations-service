package com.tech3.erp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.FinishFabricReceiveDTO;
import com.tech3.erp.dto.FinishFabricReceiveItemDTO;
import com.tech3.erp.dto.FinishYarnReceiveItemDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.DyeingWorkOrder;
import com.tech3.erp.entity.FinishFabricReceive;
import com.tech3.erp.entity.FinishFabricReceiveItem;
import com.tech3.erp.entity.FinishYarnReceiveItem;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.Warehouse;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.DyeingWorkOrderRepository;
import com.tech3.erp.repository.FinishFabricReceiveItemRepository;
import com.tech3.erp.repository.FinishFabricReceiveRepository;
import com.tech3.erp.repository.FinishYarnReceiveItemRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.WarehouseRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.util.ProcessTypeUtil;
import com.tech3.erp.service.ReferenceValidationService;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
public class FinishFabricReceiveService {

    private static final Logger logger = LoggerFactory.getLogger(FinishFabricReceiveService.class);

    private final FinishFabricReceiveRepository receiveRepository;
    private final FinishFabricReceiveItemRepository itemRepository;
    private final FinishYarnReceiveItemRepository yarnItemRepository;
    
    private final DyeingWorkOrderRepository dyeingWorkOrderRepository;
    private final YarnInventoryRepository yarnInventoryRepository;
    private final YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    private final ReferenceValidationService referenceValidationService;
    private final CodesDetailRepository codesDetailRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;
    
    @Autowired
    private PieceEntryRepository pieceEntryRepository;
    
    @Autowired
    private FabricInspectionService fabricInspectionService;

    @Autowired
    public FinishFabricReceiveService(FinishFabricReceiveRepository receiveRepository,
                                     FinishFabricReceiveItemRepository itemRepository,
                                     FinishYarnReceiveItemRepository yarnItemRepository,
                                     DyeingWorkOrderRepository dyeingWorkOrderRepository,
                                     YarnInventoryRepository yarnInventoryRepository,
                                     YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository,
                                     ReferenceValidationService referenceValidationService,
                                     CodesDetailRepository codesDetailRepository
                                     ) {
        this.receiveRepository = receiveRepository;
        this.itemRepository = itemRepository;
        this.yarnItemRepository = yarnItemRepository;
        this.dyeingWorkOrderRepository = dyeingWorkOrderRepository;
        this.yarnInventoryRepository = yarnInventoryRepository;
        this.yarnInwardStockDetailsRepository = yarnInwardStockDetailsRepository;
        this.referenceValidationService = referenceValidationService;
        this.codesDetailRepository = codesDetailRepository;
    }

    @Transactional
    public FinishFabricReceive createFinishFabricReceive(FinishFabricReceiveDTO dto) {
        validateFinishFabricReceiveDTO(dto);
        validateDuplicates(dto);

        FinishFabricReceive receive = mapDtoToEntity(dto);

        // Set and validate process type
        String processType = ProcessTypeUtil.getValidProcessType(dto.getProcessType());
        receive.setProcessType(processType);

        // Handle based on process type
        if (ProcessTypeUtil.isFabric(processType)) {
            List<FinishFabricReceiveItem> items = dto.getItems().stream()
                .map(itemDTO -> mapItemDtoToEntity(itemDTO, receive))
                .collect(Collectors.toList());
            receive.setItems(items);
        } else if (ProcessTypeUtil.isYarn(processType)) {
            if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
                List<FinishYarnReceiveItem> yarnItems = dto.getYarnItems().stream()
                    .map(itemDTO -> mapYarnItemDtoToEntity(itemDTO, receive))
                    .collect(Collectors.toList());
                receive.setYarnItems(yarnItems);
            }
        }

        FinishFabricReceive saved = receiveRepository.save(receive);
        syncPieceEntries(saved);

        if (ProcessTypeUtil.isYarn(processType) && saved.getYarnItems() != null) {
            createYarnInventoryEntries(saved, dto);
        }
        return saved;
    }

    public List<FinishFabricReceive> getAllFinishFabricReceives() {
        return receiveRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public PaginatedResponse<FinishFabricReceive> getAllFinishFabricReceivesWithPagination(Long offset, Long limit) {
        return PaginationHelper.paginate(getAllFinishFabricReceives(), offset, limit);
    }

    public List<FinishFabricReceive> getAllFinishFabricReceivesByType(String processType) {
        if (processType != null && !processType.isEmpty() && ProcessTypeUtil.isValid(processType)) {
            return receiveRepository.findByProcessType(processType);
        }
        return getAllFinishFabricReceives();
    }

    public PaginatedResponse<FinishFabricReceive> getAllFinishFabricReceivesByTypeWithPagination(String processType, Long offset, Long limit) {
        return PaginationHelper.paginate(getAllFinishFabricReceivesByType(processType), offset, limit);
    }

    public FinishFabricReceive getFinishFabricReceiveById(Long id) {
        return receiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric Receive not found with id: " + id));
    }

    @Transactional
    public FinishFabricReceive updateFinishFabricReceive(Long id, FinishFabricReceiveDTO dto) {
        validateFinishFabricReceiveDTO(dto);
        validateDuplicates(dto);

        FinishFabricReceive receive = receiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric Receive not found with id: " + id));

        mapDtoToEntity(dto, receive);

        // Update and validate process type
        String processType = ProcessTypeUtil.getValidProcessType(dto.getProcessType());
        receive.setProcessType(processType);

        FabricItemUpdateResult fabricResult = null;
        if (ProcessTypeUtil.isFabric(processType)) {
            fabricResult = reconcileFabricItems(receive, dto.getItems());
            if (!fabricResult.removedItemIds.isEmpty()) {
                deletePieceEntriesForFinishItemIds(fabricResult.removedItemIds);
                itemRepository.deleteAllById(fabricResult.removedItemIds);
            }
            receive.setItems(fabricResult.updatedItems);
            // Clear yarn items if switching from yarn to fabric
            receive.setYarnItems(null);
        } else if (ProcessTypeUtil.isYarn(processType)) {
            List<FinishYarnReceiveItem> existingYarnItems = receive.getYarnItems() != null ? 
                new java.util.ArrayList<>(receive.getYarnItems()) : new java.util.ArrayList<>();
            
            if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
                List<FinishYarnReceiveItem> yarnItems = new java.util.ArrayList<>();
                for (int i = 0; i < dto.getYarnItems().size(); i++) {
                    FinishYarnReceiveItemDTO itemDTO = dto.getYarnItems().get(i);
                    FinishYarnReceiveItem item;
                    
                    if (i < existingYarnItems.size()) {
                        item = existingYarnItems.get(i);
                        item.setFinishedYarnCode(itemDTO.getFinishedYarnCode());
                        item.setFinishedYarn(itemDTO.getFinishedYarn());
                        Long finishedYarnId = null;
                        if (itemDTO.getFinishedYarn() != null && !itemDTO.getFinishedYarn().isEmpty()) {
                            try {
                                finishedYarnId = Long.parseLong(itemDTO.getFinishedYarn());
                            } catch (NumberFormatException e) {
                            }
                        }
                        item.setFinishedYarnItemId(finishedYarnId != null ? finishedYarnId : itemDTO.getFinishedYarnItemId());
                        item.setNoOfBags(itemDTO.getNoOfBags());
                        item.setKgsPerBag(itemDTO.getKgsPerBag());
                        item.setTotalKgs(itemDTO.getTotalKgs());
                        item.setConesPerBag(itemDTO.getConesPerBag());
                        item.setConesWeight(itemDTO.getConesWeight());
                        item.setLotNo(itemDTO.getLotNo());
                        item.setWarehouseId(itemDTO.getWarehouseId());
                        item.setActiveFlag(itemDTO.getActiveFlag());
                    } else {
                        item = mapYarnItemDtoToEntity(itemDTO, receive);
                    }
                    yarnItems.add(item);
                }
                receive.setYarnItems(yarnItems);
                
                if (existingYarnItems.size() > yarnItems.size()) {
                    Long inwardTypeId = getFinishFabricInwardTypeId();
                    for (int i = yarnItems.size(); i < existingYarnItems.size(); i++) {
                        FinishYarnReceiveItem itemToRemove = existingYarnItems.get(i);
                        if (itemToRemove.getId() != null) {
                            yarnInwardStockDetailsRepository.deleteByOrderItemIdAndInwardTypeId(itemToRemove.getId(), inwardTypeId);
                        }
                        yarnItemRepository.delete(itemToRemove);
                    }
                }
            } else {
                if (!existingYarnItems.isEmpty()) {
                    Long inwardTypeId = getFinishFabricInwardTypeId();
                    for (FinishYarnReceiveItem itemToRemove : existingYarnItems) {
                        if (itemToRemove.getId() != null) {
                            yarnInwardStockDetailsRepository.deleteByOrderItemIdAndInwardTypeId(itemToRemove.getId(), inwardTypeId);
                        }
                    }
                    yarnItemRepository.deleteAll(existingYarnItems);
                }
                receive.setYarnItems(null);
            }
            // Clear fabric items if switching from fabric to yarn
            receive.setItems(null);
        }

        FinishFabricReceive updatedReceive = receiveRepository.save(receive);
        if (ProcessTypeUtil.isFabric(processType) && fabricResult != null) {
            syncPieceEntriesAfterUpdate(updatedReceive, fabricResult.keptItemIds, fabricResult.newItems);
        }

        if (ProcessTypeUtil.isYarn(processType) && updatedReceive.getYarnItems() != null) {
            createYarnInventoryEntries(updatedReceive, dto);
        }
        return updatedReceive;
    }

    

    

    public List<FinishFabricReceive> getFinishFabricReceivesByCompany(Long companyId) {
        return receiveRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id"));
    }
    
    public PaginatedResponse<FinishFabricReceive> getFinishFabricReceivesByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        return PaginationHelper.paginate(getFinishFabricReceivesByCompany(companyId), offset, limit);
    }
    
    public PaginatedResponse<FinishFabricReceive> getFinishFabricReceivesByTypeAndCompanyWithPagination(String processType, Long companyId, Long offset, Long limit) {
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        List<FinishFabricReceive> receives = receiveRepository.findByCompanyIdAndProcessType(companyId, validProcessType);
        receives.sort(Comparator.comparing(FinishFabricReceive::getId).reversed());
        return PaginationHelper.paginate(receives, offset, limit);
    }
    
    public FinishFabricReceive getFinishFabricReceiveByIdAndCompany(Long id, Long companyId) {
        return receiveRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric Receive not found with id: " + id + " and companyId: " + companyId));
    }
    
    public FinishFabricReceive getFinishFabricReceiveByIdAndCompanyAndType(Long id, Long companyId, String processType) {
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        return receiveRepository.findByIdAndCompanyIdAndProcessType(id, companyId, validProcessType)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric Receive not found with id: " + id + ", companyId: " + companyId + " and processType: " + validProcessType));
    }
    
    @Transactional
    public FinishFabricReceive updateFinishFabricReceiveByCompany(Long id, Long companyId, FinishFabricReceiveDTO dto) {
        FinishFabricReceive receive = receiveRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric Receive not found with id: " + id + " and companyId: " + companyId));
        
        dto.setCompanyId(receive.getCompanyId());
        
        validateFinishFabricReceiveDTO(dto);
        mapDtoToEntity(dto, receive);
        String processType = ProcessTypeUtil.getValidProcessType(dto.getProcessType());
        receive.setProcessType(processType);
        
        FabricItemUpdateResult fabricResult = null;
        if (ProcessTypeUtil.isFabric(processType)) {
            fabricResult = reconcileFabricItems(receive, dto.getItems());
            if (!fabricResult.removedItemIds.isEmpty()) {
                deletePieceEntriesForFinishItemIds(fabricResult.removedItemIds);
                itemRepository.deleteAllById(fabricResult.removedItemIds);
            }
            receive.setItems(fabricResult.updatedItems);
            receive.setYarnItems(null);
        } else if (ProcessTypeUtil.isYarn(processType)) {
            List<FinishYarnReceiveItem> existingYarnItems = receive.getYarnItems() != null ? 
                new java.util.ArrayList<>(receive.getYarnItems()) : new java.util.ArrayList<>();
            
            if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
                List<FinishYarnReceiveItem> yarnItems = new java.util.ArrayList<>();
                for (int i = 0; i < dto.getYarnItems().size(); i++) {
                    FinishYarnReceiveItemDTO itemDTO = dto.getYarnItems().get(i);
                    FinishYarnReceiveItem item;
                    
                    if (i < existingYarnItems.size()) {
                        item = existingYarnItems.get(i);
                        item.setFinishedYarnCode(itemDTO.getFinishedYarnCode());
                        item.setFinishedYarn(itemDTO.getFinishedYarn());
                        Long finishedYarnId = null;
                        if (itemDTO.getFinishedYarn() != null && !itemDTO.getFinishedYarn().isEmpty()) {
                            try {
                                finishedYarnId = Long.parseLong(itemDTO.getFinishedYarn());
                            } catch (NumberFormatException e) {
                            }
                        }
                        item.setFinishedYarnItemId(finishedYarnId != null ? finishedYarnId : itemDTO.getFinishedYarnItemId());
                        item.setNoOfBags(itemDTO.getNoOfBags());
                        item.setKgsPerBag(itemDTO.getKgsPerBag());
                        item.setTotalKgs(itemDTO.getTotalKgs());
                        item.setConesPerBag(itemDTO.getConesPerBag());
                        item.setConesWeight(itemDTO.getConesWeight());
                        item.setLotNo(itemDTO.getLotNo());
                        item.setWarehouseId(itemDTO.getWarehouseId());
                        item.setActiveFlag(itemDTO.getActiveFlag());
                    } else {
                        item = mapYarnItemDtoToEntity(itemDTO, receive);
                    }
                    yarnItems.add(item);
                }
                receive.setYarnItems(yarnItems);
                
                if (existingYarnItems.size() > yarnItems.size()) {
                    Long inwardTypeId = getFinishFabricInwardTypeId();
                    for (int i = yarnItems.size(); i < existingYarnItems.size(); i++) {
                        FinishYarnReceiveItem itemToRemove = existingYarnItems.get(i);
                        if (itemToRemove.getId() != null) {
                            yarnInwardStockDetailsRepository.deleteByOrderItemIdAndInwardTypeId(itemToRemove.getId(), inwardTypeId);
                        }
                        yarnItemRepository.delete(itemToRemove);
                    }
                }
            } else {
                if (!existingYarnItems.isEmpty()) {
                    Long inwardTypeId = getFinishFabricInwardTypeId();
                    for (FinishYarnReceiveItem itemToRemove : existingYarnItems) {
                        if (itemToRemove.getId() != null) {
                            yarnInwardStockDetailsRepository.deleteByOrderItemIdAndInwardTypeId(itemToRemove.getId(), inwardTypeId);
                        }
                    }
                    yarnItemRepository.deleteAll(existingYarnItems);
                }
                receive.setYarnItems(null);
            }
            receive.setItems(null);
        }
        
        FinishFabricReceive updatedReceive = receiveRepository.save(receive);
        if (ProcessTypeUtil.isFabric(processType) && fabricResult != null) {
            syncPieceEntriesAfterUpdate(updatedReceive, fabricResult.keptItemIds, fabricResult.newItems);
        }
        
        if (ProcessTypeUtil.isYarn(processType) && updatedReceive.getYarnItems() != null) {
            createYarnInventoryEntries(updatedReceive, dto);
        }
        return updatedReceive;
    }
    
    @Transactional
    public FinishFabricReceive updateFinishFabricReceiveByCompanyAndType(Long id, Long companyId, String processType, FinishFabricReceiveDTO dto) {
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        FinishFabricReceive receive = receiveRepository.findByIdAndCompanyIdAndProcessType(id, companyId, validProcessType)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric Receive not found with id: " + id + ", companyId: " + companyId + " and processType: " + validProcessType));
        dto.setCompanyId(receive.getCompanyId());
        dto.setProcessType(validProcessType);
        return updateFinishFabricReceiveByCompany(id, companyId, dto);
    }
    
    @Transactional
    public void deleteFinishFabricReceiveByCompany(Long id, Long companyId) {
        FinishFabricReceive receive = receiveRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric Receive not found with id: " + id + " and companyId: " + companyId));
        try {
            deleteYarnInventoryAndStockDetails(receive);
            receiveRepository.delete(receive);
        } catch (DataIntegrityViolationException e) {
            String errorMsg = "Receive No " + receive.getReceiveNo() + " is already mapped and cannot be deleted";
            logger.warn(errorMsg);
            throw new IllegalStateException(errorMsg, e);
        }
    }

    @Transactional
    public void deleteFinishFabricReceiveByCompanyAndType(Long id, Long companyId, String processType) {
        String validProcessType = ProcessTypeUtil.getValidProcessType(processType);
        FinishFabricReceive receive = receiveRepository.findByIdAndCompanyIdAndProcessType(id, companyId, validProcessType)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric Receive not found with id: " + id + ", companyId: " + companyId + " and processType: " + validProcessType));
        deleteFinishFabricReceiveByCompany(id, companyId);
    }
    
    // ================= Helper methods =================

    private void validateFinishFabricReceiveDTO(FinishFabricReceiveDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("FinishFabricReceiveDTO cannot be null");
        }

        String processType = ProcessTypeUtil.getValidProcessType(dto.getProcessType());

        // Validate based on process type
        if (ProcessTypeUtil.isFabric(processType)) {
            if (dto.getItems() == null || dto.getItems().isEmpty()) {
                throw new IllegalArgumentException("At least one fabric item is required");
            }
        } else if (ProcessTypeUtil.isYarn(processType)) {
            if (dto.getYarnItems() == null || dto.getYarnItems().isEmpty()) {
                throw new IllegalArgumentException("At least one yarn item is required");
            }
        }
    }

    private void validateDuplicates(FinishFabricReceiveDTO dto) {
        Long companyId = dto.getCompanyId();
        if (companyId == null) return;

        // 1. Validate Piece Numbers (Fabric process)
        if (dto.getItems() != null) {
            for (FinishFabricReceiveItemDTO item : dto.getItems()) {
                if (org.springframework.util.StringUtils.hasText(item.getPieceRollNo())) {
                    String pieceNumber = item.getPieceRollNo().trim();
                    List<PieceEntry> existingPieces = pieceEntryRepository.findByPieceNumberAndCompanyId(pieceNumber, companyId);
                    for (PieceEntry existing : existingPieces) {
                        // If it's an update, skip if it belongs to the same receive item
                        if (item.getId() != null && existing.getFinishFabricReceiveItem() != null &&
                            item.getId().equals(existing.getFinishFabricReceiveItem().getId())) {
                            continue;
                        }
                        throw new IllegalArgumentException("Piece Number '" + pieceNumber + "' already exists for this company.");
                    }
                }
            }
        }

        // 2. Validate Lot Numbers (Yarn process)
        if (dto.getYarnItems() != null) {
            for (FinishYarnReceiveItemDTO yarnItem : dto.getYarnItems()) {
                if (org.springframework.util.StringUtils.hasText(yarnItem.getLotNo())) {
                    String lotNo = yarnItem.getLotNo().trim();
                    Long yarnId = yarnItem.getFinishedYarnItemId();
                    if (yarnId == null && yarnItem.getFinishedYarn() != null && !yarnItem.getFinishedYarn().isEmpty()) {
                        try {
                            yarnId = Long.parseLong(yarnItem.getFinishedYarn());
                        } catch (NumberFormatException e) {}
                    }
                    
                    if (yarnId != null) {
                        List<YarnInwardStockDetails> existingLots = yarnInwardStockDetailsRepository.findByCompanyIdAndItemIdAndLotNo(companyId, yarnId, lotNo);
                        for (YarnInwardStockDetails lot : existingLots) {
                            // If it's an update, skip if it belongs to the same receive (matching order_item_id which is FinishYarnReceiveItem id)
                            if (yarnItem.getId() != null && yarnItem.getId().equals(lot.getOrderItemId())) {
                                continue;
                            }
                            throw new IllegalArgumentException("Lot Number '" + lotNo + "' already exists for this company and item.");
                        }
                    }
                }
            }
        }
    }

    private FinishFabricReceive mapDtoToEntity(FinishFabricReceiveDTO dto) {
        FinishFabricReceive receive = new FinishFabricReceive();
        mapDtoToEntity(dto, receive);
        return receive;
    }
 @Transactional
    public void deleteFinishFabricReceive(Long id) {
        FinishFabricReceive receive = receiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric/Yarn Receive not found with id: " + id));

        validateDeletion(receive);

        receive.setActiveFlag(false);
        
        // Update PieceEntry records if it's fabric
        if (ProcessTypeUtil.isFabric(receive.getProcessType()) && receive.getItems() != null) {
            for (FinishFabricReceiveItem item : receive.getItems()) {
                item.setActiveFlag(false);
                List<PieceEntry> pieces = pieceEntryRepository.findAll().stream()
                        .filter(p -> p.getFinishFabricReceiveItem() != null && 
                                    p.getFinishFabricReceiveItem().getId().equals(item.getId()))
                        .collect(Collectors.toList());
                for (PieceEntry piece : pieces) {
                    piece.setActiveFlag(false);
                    pieceEntryRepository.save(piece);
                }
            }
        }
        
        // Update Yarn items if it's yarn
        if (ProcessTypeUtil.isYarn(receive.getProcessType()) && receive.getYarnItems() != null) {
            deleteYarnInventoryAndStockDetails(receive);
            for (FinishYarnReceiveItem yarnItem : receive.getYarnItems()) {
                yarnItem.setActiveFlag(false);
            }
        }

        receiveRepository.save(receive);
    }
    
    private void validateDeletion(FinishFabricReceive receive) {
        String processType = receive.getProcessType();
        Long inwardTypeId = getFinishFabricInwardTypeId();

        if (ProcessTypeUtil.isFabric(processType)) {
            List<String> usedPieceNumbers = new java.util.ArrayList<>();
            if (receive.getItems() != null) {
                for (FinishFabricReceiveItem item : receive.getItems()) {
                    List<PieceEntry> usedPieces = pieceEntryRepository.findUsedPiecesByFinishFabricReceiveItemId(item.getId());
                    for (PieceEntry pe : usedPieces) {
                        usedPieceNumbers.add(pe.getPieceNumber());
                    }
                }
            }
            if (!usedPieceNumbers.isEmpty()) {
                throw new IllegalStateException("Cannot delete: These fabric pieces are already inspected, packed, or invoiced: " 
                        + String.join(", ", usedPieceNumbers));
            }
        } else if (ProcessTypeUtil.isYarn(processType)) {
            List<String> usedYarnLots = new java.util.ArrayList<>();
            if (receive.getYarnItems() != null) {
                for (FinishYarnReceiveItem yarnItem : receive.getYarnItems()) {
                    yarnInwardStockDetailsRepository.findByOrderItemIdAndInwardTypeId(yarnItem.getId(), inwardTypeId)
                        .ifPresent(detail -> {
                            if (detail.getAvailableQty() != null && detail.getAcceptedQty() != null &&
                                detail.getAvailableQty().compareTo(detail.getAcceptedQty()) < 0) {
                                usedYarnLots.add(yarnItem.getLotNo() != null ? yarnItem.getLotNo() : "Unknown Lot");
                            }
                        });
                }
            }
            if (!usedYarnLots.isEmpty()) {
                throw new IllegalStateException("Cannot delete: Yarn from these lots has already been issued: " 
                        + String.join(", ", usedYarnLots));
            }
        }
    }
     private void createYarnInventoryEntries(FinishFabricReceive receive, FinishFabricReceiveDTO dto) {
        try {
            DyeingWorkOrder dyeingWorkOrder = dyeingWorkOrderRepository.findById(receive.getDyeingWorkOrderId())
                    .orElseThrow(() -> new EntityNotFoundException("Dyeing Work Order not found with id: " + receive.getDyeingWorkOrderId()));

            LocalDate receiveDate = receive.getFabricReceiveDate() != null ? receive.getFabricReceiveDate() : LocalDate.now();
            
            if (receive.getVendorId() == null && dyeingWorkOrder.getVendorId() != null) {
                receive.setVendorId(dyeingWorkOrder.getVendorId());
                receiveRepository.save(receive);
            }
            
            Long inwardTypeId = getFinishFabricInwardTypeId();
            YarnInventory yarnInventory = findLastYarnInventoryForInward(receive.getReceiveNo(), receive.getCompanyId(), inwardTypeId);
            
            if (yarnInventory == null) {
                yarnInventory = new YarnInventory();
                LocalDateTime inwardDateTime = receive.getFabricReceiveDate() != null 
                    ? receive.getFabricReceiveDate().atStartOfDay() 
                    : LocalDateTime.now();
                yarnInventory.setInwardDate(inwardDateTime);
                logger.info("Creating new YarnInventory for inwardNo: {} with date: {}", receive.getReceiveNo(), inwardDateTime);
            } else {
                if (receive.getFabricReceiveDate() != null) {
                    LocalDateTime inwardDateTime = receive.getFabricReceiveDate().atStartOfDay();
                    yarnInventory.setInwardDate(inwardDateTime);
                    logger.info("Updating YarnInventory for inwardNo: {} with date: {}", receive.getReceiveNo(), inwardDateTime);
                }
            }
            
            yarnInventory.setInwardTypeId(inwardTypeId);
            yarnInventory.setInwardNo(receive.getReceiveNo());
            yarnInventory.setInwardId(receive.getId());
            yarnInventory.setOrderId(receive.getDyeingWorkOrderId());
            yarnInventory.setOrderNo(dyeingWorkOrder.getDyeingWorkOrderNo());
            yarnInventory.setCompanyId(receive.getCompanyId());
            yarnInventory.setFinancialYear(getFinancialYearId(receiveDate));
            yarnInventory.setDcNo(receive.getDcNumber());
            yarnInventory.setDcDate(receive.getDcDate() != null ? receive.getDcDate() : receiveDate);
            yarnInventory.setVendorId(receive.getVendorId() != null ? receive.getVendorId() : dyeingWorkOrder.getVendorId());
            
            YarnInventory savedInventory = yarnInventoryRepository.save(yarnInventory);

            List<FinishYarnReceiveItem> yarnItems = receive.getYarnItems();
            if (yarnItems != null && !yarnItems.isEmpty()) {
                for (FinishYarnReceiveItem yarnItem : yarnItems) {
                    Long orderItemId = yarnItem.getId();
                    Optional<YarnInwardStockDetails> existingDetails = Optional.empty();
                    if (orderItemId != null) {
                        existingDetails = yarnInwardStockDetailsRepository
                                .findByOrderItemIdAndInwardTypeId(orderItemId, inwardTypeId);
                    }
                    YarnInwardStockDetails details = existingDetails.orElse(new YarnInwardStockDetails());

                    if (orderItemId != null) {
                        details.setOrderItemId(orderItemId);
                    }
                    details.setItemId(yarnItem.getFinishedYarnItemId());
                    details.setYarnInventory(savedInventory);
                    details.setInwardTypeId(inwardTypeId);
                    details.setLotNo(yarnItem.getLotNo());
                    details.setInwardBags(BigDecimal.valueOf(yarnItem.getNoOfBags()));
                    details.setKgsPerBag(BigDecimal.valueOf(yarnItem.getKgsPerBag()));
                    details.setConesPerBag(yarnItem.getConesPerBag());
                    details.setConesWeight(yarnItem.getConesWeight());
                    details.setInwardQty(BigDecimal.valueOf(yarnItem.getTotalKgs()));
                    details.setAcceptedQty(BigDecimal.valueOf(yarnItem.getTotalKgs()));
                    details.setAvailableQty(BigDecimal.valueOf(yarnItem.getTotalKgs()));
                    details.setAvailableBags(BigDecimal.valueOf(yarnItem.getNoOfBags()));
                    details.setWarehouseId(yarnItem.getWarehouseId());

                    yarnInwardStockDetailsRepository.save(details);
                }
            }
        } catch (EntityNotFoundException e) {
            throw e;
        }
    }

    private YarnInventory findLastYarnInventoryForInward(String inwardNo, Long companyId, Long inwardTypeId) {
        List<YarnInventory> inventories = yarnInventoryRepository
                .findByInwardNoAndCompanyIdAndInwardTypeId(inwardNo, companyId, inwardTypeId);
        
        if (inventories == null || inventories.isEmpty()) {
            return null;
        }
        
        return inventories.stream()
                .max(Comparator.comparing(YarnInventory::getId))
                .orElse(null);
    }

    private void syncPieceEntries(FinishFabricReceive receive) {
        if (!ProcessTypeUtil.isFabric(receive.getProcessType())) {
            return;
        }
        if (receive.getItems() == null || receive.getItems().isEmpty()) {
            return;
        }
        for (FinishFabricReceiveItem item : receive.getItems()) {
            createPieceEntryForItem(item, receive);
        }
    }

    private void syncPieceEntriesAfterUpdate(FinishFabricReceive receive, Set<Long> existingItemIds, List<FinishFabricReceiveItem> newItems) {
        if (!ProcessTypeUtil.isFabric(receive.getProcessType())) {
            return;
        }
        if (existingItemIds != null && !existingItemIds.isEmpty()) {
            List<PieceEntry> entries = pieceEntryRepository.findByFinishFabricReceiveItemIdIn(new ArrayList<>(existingItemIds));
            Map<Long, List<PieceEntry>> entriesByItemId = entries.stream()
                    .filter(entry -> entry.getFinishFabricReceiveItem() != null)
                    .collect(Collectors.groupingBy(entry -> entry.getFinishFabricReceiveItem().getId()));
            for (Long itemId : existingItemIds) {
                FinishFabricReceiveItem item = receive.getItems().stream()
                        .filter(fItem -> Objects.equals(fItem.getId(), itemId))
                        .findFirst()
                        .orElse(null);
                if (item == null) {
                    continue;
                }
                List<PieceEntry> itemEntries = entriesByItemId.get(itemId);
                if (itemEntries == null || itemEntries.isEmpty()) {
                    createPieceEntryForItem(item, receive);
                    continue;
                }
                item.setPieceEntries(itemEntries);
                for (PieceEntry piece : itemEntries) {
                    applyItemToPieceEntry(piece, item, receive);
                }
                pieceEntryRepository.saveAll(itemEntries);
            }
        }
        if (newItems != null && !newItems.isEmpty()) {
            for (FinishFabricReceiveItem newItem : newItems) {
                createPieceEntryForItem(newItem, receive);
            }
        }
    }

    private void createPieceEntryForItem(FinishFabricReceiveItem item, FinishFabricReceive receive) {
        PieceEntry piece = new PieceEntry();
        applyItemToPieceEntry(piece, item, receive);
        pieceEntryRepository.save(piece);
        if (item.getPieceEntries() == null) {
            item.setPieceEntries(new ArrayList<>());
        }
        item.getPieceEntries().add(piece);
        fabricInspectionService.createFabricInspectionFromPieceEntry(piece);
    }

    private void applyItemToPieceEntry(PieceEntry piece, FinishFabricReceiveItem item, FinishFabricReceive receive) {
        piece.setPieceNumber(item.getPieceRollNo());
        piece.setInwardDate(receive.getFabricReceiveDate());
        piece.setQuantity(item.getMeter() != null ? BigDecimal.valueOf(item.getMeter()) : null);
        piece.setWeight(item.getNetWeight() != null ? BigDecimal.valueOf(item.getNetWeight()) : null);
        piece.setRemarks(receive.getRemarks());
        piece.setActiveFlag(true);
        piece.setSourceType("PROCESS_RECEIVE");
        piece.setPoType("Process");
        piece.setOrderNo(receive.getDyeingWorkOrderNo());
        piece.setOrderId(receive.getDyeingWorkOrderId());
        piece.setCompanyId(receive.getCompanyId());
        piece.setInwardId(receive.getId());
        piece.setDcNumber(receive.getDcNumber());
        piece.setDcDate(receive.getDcDate());
        piece.setGradeId(item.getGradeId());
        piece.setFabricTypeId(2L);
        piece.setFabricMasterId(item.getFabricMasterId());
        piece.setFabricId(item.getFabricMasterId());
        piece.setFinishFabricReceiveItem(item);
        if (item.getWarehouseId() != null) {
            warehouseRepository.findById(item.getWarehouseId()).ifPresent(piece::setWarehouse);
        } else {
            piece.setWarehouse(null);
        }
    }

    private FabricItemUpdateResult reconcileFabricItems(FinishFabricReceive receive, List<FinishFabricReceiveItemDTO> dtoItems) {
        Map<Long, FinishFabricReceiveItem> existingItems = new HashMap<>();
        if (receive.getItems() != null) {
            for (FinishFabricReceiveItem item : receive.getItems()) {
                if (item != null && item.getId() != null) {
                    existingItems.put(item.getId(), item);
                }
            }
        }
        List<FinishFabricReceiveItem> updatedItems = new ArrayList<>();
        List<FinishFabricReceiveItem> newItems = new ArrayList<>();
        Set<Long> keptItemIds = new HashSet<>();
        if (dtoItems != null) {
            for (FinishFabricReceiveItemDTO itemDTO : dtoItems) {
                if (itemDTO.getId() != null && existingItems.containsKey(itemDTO.getId())) {
                    FinishFabricReceiveItem existingItem = existingItems.get(itemDTO.getId());
                    updateExistingItemFromDto(existingItem, itemDTO, receive);
                    updatedItems.add(existingItem);
                    keptItemIds.add(itemDTO.getId());
                } else {
                    FinishFabricReceiveItem newItem = mapItemDtoToEntity(itemDTO, receive);
                    updatedItems.add(newItem);
                    newItems.add(newItem);
                }
            }
        }
        List<Long> removedItemIds = existingItems.keySet().stream()
                .filter(id -> !keptItemIds.contains(id))
                .collect(Collectors.toList());
        return new FabricItemUpdateResult(updatedItems, newItems, keptItemIds, removedItemIds);
    }

    private void updateExistingItemFromDto(FinishFabricReceiveItem item, FinishFabricReceiveItemDTO dto, FinishFabricReceive receive) {
        item.setFinishedFabricCodeQuality(dto.getFinishedFabricCodeQuality());
        item.setPieceRollNo(dto.getPieceRollNo());
        item.setMeter(dto.getMeter());
        item.setYards(dto.getYards());
        item.setNetWeight(dto.getNetWeight());
        item.setAverageWeight(dto.getAverageWeight());
        item.setGradeId(dto.getGradeId());
        item.setWarehouseId(dto.getWarehouseId());
        item.setFabricMasterId(dto.getFabricMasterId());
        item.setActiveFlag(dto.getActiveFlag());
        item.setFinishFabricReceive(receive);
    }

    private static class FabricItemUpdateResult {
        final List<FinishFabricReceiveItem> updatedItems;
        final List<FinishFabricReceiveItem> newItems;
        final Set<Long> keptItemIds;
        final List<Long> removedItemIds;

        FabricItemUpdateResult(List<FinishFabricReceiveItem> updatedItems, List<FinishFabricReceiveItem> newItems,
                                Set<Long> keptItemIds, List<Long> removedItemIds) {
            this.updatedItems = updatedItems;
            this.newItems = newItems;
            this.keptItemIds = keptItemIds;
            this.removedItemIds = removedItemIds;
        }
    }

    private void deletePieceEntriesForFinishItemIds(List<Long> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            return;
        }
        pieceEntryRepository.deleteByFinishFabricReceiveItemIdIn(itemIds);
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

    private String generateUniqueInwardNo(Long companyId) {
        String financialYear = getFinancialYearId(LocalDate.now());
        String prefix = "YRN/" + financialYear + "/";
        
        List<YarnInventory> existingRecords = yarnInventoryRepository.findByInwardNoStartingWith(prefix);
        
        int maxNumber = 0;
        for (YarnInventory record : existingRecords) {
            try {
                String numStr = record.getInwardNo().substring(prefix.length());
                int num = Integer.parseInt(numStr);
                if (num > maxNumber) {
                    maxNumber = num;
                }
            } catch (NumberFormatException e) {
            }
        }
        
        int nextNumber = maxNumber + 1;
        return prefix + String.format("%03d", nextNumber);
    }

    // ✅ Updated mapping to include dcDate, dcNumber, and receiveNo
    private void mapDtoToEntity(FinishFabricReceiveDTO dto, FinishFabricReceive receive) {
        receive.setFabricReceiveDate(dto.getFabricReceiveDate());
        receive.setDcDate(dto.getDcDate());           // ✅ Added
        receive.setDcNumber(dto.getDcNumber());       // ✅ Added
        receive.setReceiveNo(dto.getReceiveNo());     // ✅ Added
        receive.setVendorId(dto.getVendorId());
        
        Long dyeingWorkOrderId = normalizeId(dto.getDyeingWorkOrderId());
        if (dyeingWorkOrderId != null) {
            referenceValidationService.requireReference(
                dyeingWorkOrderId,
                () -> dyeingWorkOrderRepository.findById(dyeingWorkOrderId),
                "Dyeing Work Order",
                null);
        }
        receive.setDyeingWorkOrderId(dyeingWorkOrderId);
        receive.setDyeingWorkOrderNo(dto.getDyeingWorkOrderNo());
        
        receive.setOrderQuantity(dto.getOrderQuantity());
        receive.setCostPerPound(dto.getCostPerPound());
        receive.setTotalAmount(dto.getTotalAmount());
        receive.setColorId(dto.getColorId());
        receive.setPantone(dto.getPantone());
        receive.setFinishingId(dto.getFinishingId());
        
        receive.setLotNumber(dto.getLotNumber());
        receive.setReceivedQuantity(dto.getReceivedQuantity());
        receive.setBalanceQuantity(dto.getBalanceQuantity());
        receive.setFinishedFabricCodeId(dto.getFinishedFabricCodeId());
        receive.setGreigeFabricCodeId(dto.getGreigeFabricCodeId());
        receive.setFinishedFabricCode(dto.getFinishedFabricCode());
        receive.setGreigeFabricCode(dto.getGreigeFabricCode());
        receive.setRemarks(dto.getRemarks());
        receive.setActiveFlag(dto.getActiveFlag());
        receive.setCompanyId(dto.getCompanyId());
    }

    private Long normalizeId(Object id) {
        if (id == null) return null;
        if (id instanceof Long) return (Long) id;
        if (id instanceof Integer) return ((Integer) id).longValue();
        if (id instanceof String) {
            String s = (String) id;
            if (s.isEmpty()) return null;
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private FinishFabricReceiveItem mapItemDtoToEntity(FinishFabricReceiveItemDTO itemDTO, FinishFabricReceive receive) {
        FinishFabricReceiveItem item = new FinishFabricReceiveItem();
        item.setFinishedFabricCodeQuality(itemDTO.getFinishedFabricCodeQuality());
        item.setPieceRollNo(itemDTO.getPieceRollNo());
        item.setMeter(itemDTO.getMeter());
        item.setYards(itemDTO.getYards());
        item.setNetWeight(itemDTO.getNetWeight());
        item.setAverageWeight(itemDTO.getAverageWeight());
        item.setGradeId(itemDTO.getGradeId());
        item.setWarehouseId(itemDTO.getWarehouseId());
        item.setFabricMasterId(itemDTO.getFabricMasterId());
        item.setActiveFlag(itemDTO.getActiveFlag());
        item.setFinishFabricReceive(receive);
        return item;
    }

    private FinishYarnReceiveItem mapYarnItemDtoToEntity(FinishYarnReceiveItemDTO itemDTO, FinishFabricReceive receive) {
        FinishYarnReceiveItem item = new FinishYarnReceiveItem();
        item.setFinishedYarnCode(itemDTO.getFinishedYarnCode());
        item.setFinishedYarn(itemDTO.getFinishedYarn());
        // Map finishedYarn to finishedYarnItemId (convert string to Long)
        Long finishedYarnId = null;
        if (itemDTO.getFinishedYarn() != null && !itemDTO.getFinishedYarn().isEmpty()) {
            try {
                finishedYarnId = Long.parseLong(itemDTO.getFinishedYarn());
            } catch (NumberFormatException e) {
                // If conversion fails, log or handle appropriately
            }
        }
        item.setFinishedYarnItemId(finishedYarnId != null ? finishedYarnId : itemDTO.getFinishedYarnItemId());
        
        item.setNoOfBags(itemDTO.getNoOfBags());
        item.setKgsPerBag(itemDTO.getKgsPerBag());
        item.setTotalKgs(itemDTO.getTotalKgs());
        item.setConesPerBag(itemDTO.getConesPerBag());
        item.setConesWeight(itemDTO.getConesWeight());
        item.setLotNo(itemDTO.getLotNo());
        item.setWarehouseId(itemDTO.getWarehouseId());
        item.setActiveFlag(itemDTO.getActiveFlag());
        item.setFinishFabricReceive(receive);
        return item;
    }

    @Transactional
    public void hardDeleteFinishFabricReceive(Long id) {
        FinishFabricReceive receive = receiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Finish Fabric/Yarn Receive not found with id: " + id));

        // 1. Validation
        validateDeletion(receive);

        // 2. Delete Dependent Records
        if (ProcessTypeUtil.isFabric(receive.getProcessType())) {
            if (receive.getItems() != null) {
                for (FinishFabricReceiveItem item : receive.getItems()) {
                    pieceEntryRepository.deleteByFinishFabricReceiveItemId(item.getId());
                }
            }
        } else if (ProcessTypeUtil.isYarn(receive.getProcessType())) {
            deleteYarnInventoryAndStockDetails(receive);
        }

        // 3. Delete Parent Record
        receiveRepository.delete(receive);
    }


    private void deleteYarnInventoryAndStockDetails(FinishFabricReceive receive) {
        if (receive == null || !ProcessTypeUtil.isYarn(receive.getProcessType())) {
            return;
        }

        Long inwardTypeId = getFinishFabricInwardTypeId();
        Set<YarnInventory> inventoriesToDelete = new HashSet<>();

        List<FinishYarnReceiveItem> yarnItems = receive.getYarnItems();
        if ((yarnItems == null || yarnItems.isEmpty()) && receive.getId() != null) {
            yarnItems = yarnItemRepository.findByFinishFabricReceiveId(receive.getId());
        }

        if (yarnItems != null && !yarnItems.isEmpty()) {
            for (FinishYarnReceiveItem yarnItem : yarnItems) {
                if (yarnItem.getId() == null) {
                    continue;
                }

                List<YarnInwardStockDetails> itemDetails = yarnInwardStockDetailsRepository
                        .findAllByOrderItemIdAndInwardTypeId(yarnItem.getId(), inwardTypeId);
                for (YarnInwardStockDetails detail : itemDetails) {
                    if (detail.getYarnInventory() != null) {
                        inventoriesToDelete.add(detail.getYarnInventory());
                    }
                }

                yarnInwardStockDetailsRepository.deleteByOrderItemIdAndInwardTypeId(yarnItem.getId(), inwardTypeId);
            }
        }

        if (receive.getReceiveNo() != null) {
            List<YarnInventory> directInventories = yarnInventoryRepository
                    .findByInwardNoAndCompanyIdAndInwardTypeId(receive.getReceiveNo(), receive.getCompanyId(), inwardTypeId);
            inventoriesToDelete.addAll(directInventories);
        }

        if (inventoriesToDelete.isEmpty()) {
            return;
        }

        List<Long> inventoryIds = inventoriesToDelete.stream()
                .map(YarnInventory::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!inventoryIds.isEmpty()) {
            yarnInwardStockDetailsRepository.deleteByYarnInventoryIdIn(inventoryIds);
        }

        yarnInventoryRepository.deleteAll(inventoriesToDelete);
    }

    private Long getFinishFabricInwardTypeId() {
        return codesDetailRepository.findByCode("PROCESSING_YARN_INWARD")
                .map(CodesDetail::getId)
                .orElse(4L); // Fallback to 3L to maintain existing functionality if code not found
    }
}


