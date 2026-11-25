package com.tech3.erp.service;

import com.tech3.erp.dto.JobworkFabricReceiveDTO;
import com.tech3.erp.dto.JobworkFabricReceiveItemDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.PieceEntryDTO;
import com.tech3.erp.util.PaginationHelper;

import org.springframework.data.domain.Sort;

import com.tech3.erp.entity.JobworkFabricReceive;
import com.tech3.erp.entity.JobworkFabricReceiveItem;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.Warehouse;
import com.tech3.erp.repository.JobworkFabricReceiveRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.WarehouseRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JobworkFabricReceiveService {

    @Autowired
    private JobworkFabricReceiveRepository jobworkFabricReceiveRepository;
    
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    @Autowired
    private WeavingContractRepository weavingContractRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Autowired
    private FabricInspectionService fabricInspectionService;

    // Source type constant
    private static final String SOURCE_JOBWORK = "jobwork_fabric_receive";
    private static final String PO_TYPE_PROCESS = "Process";

    @Transactional
    public JobworkFabricReceive createJobworkFabricReceive(JobworkFabricReceiveDTO dto) {
        validateDuplicates(dto, null);
        requireWeavingContractReference(dto.getWeavingContractId(), dto.getWeavingContractNo());
        JobworkFabricReceive jobReceive = new JobworkFabricReceive();
        jobReceive.setWeavingContractId(dto.getWeavingContractId());
        jobReceive.setWeavingContractNo(dto.getWeavingContractNo());
        jobReceive.setVendorId(dto.getVendorId());
        jobReceive.setFabricCode(dto.getFabricCode());
        jobReceive.setFabricDetails(dto.getFabricDetails());
        jobReceive.setQuality(dto.getQuality());
        jobReceive.setRemarks(dto.getRemarks());
        jobReceive.setJobFabricReceiveDate(LocalDate.now());
        jobReceive.setActiveFlag(true);
        jobReceive.setCompanyId(dto.getCompanyId());
        jobReceive.setReceiveNo(dto.getReceiveNo());
        jobReceive.setDcNumber(dto.getDcNumber());
        jobReceive.setDcDate(dto.getDcDate() != null ? dto.getDcDate().atStartOfDay() : LocalDateTime.now());

        List<JobworkFabricReceiveItem> jobReceiveItems = dto.getJobworkFabricReceiveItemsDtl().stream().map(itemDto -> {
            JobworkFabricReceiveItem item = new JobworkFabricReceiveItem();
            item.setWeavingContractItemId(itemDto.getWeavingContractItemId());
            item.setFabricCode(itemDto.getFabricCode());
            item.setFabricDetails(itemDto.getFabricDetails());
            item.setQuality(itemDto.getQuality());
            item.setQuantityReceived(itemDto.getQuantityReceived());
            item.setPrice(itemDto.getPrice());
            item.setJobworkFabricReceive(jobReceive);
            item.setActiveFlag(true);

            List<PieceEntry> pieceEntries = itemDto.getPieceEntries().stream().map(pieceDto -> {
                PieceEntry piece = new PieceEntry();
                piece.setPieceNumber(pieceDto.getPieceNumber());
                piece.setQuantity(pieceDto.getQuantity());
                piece.setWeight(pieceDto.getWeight());
                piece.setCost(pieceDto.getCost());
                piece.setRemarks(pieceDto.getRemarks());
                
                piece.setSourceType(SOURCE_JOBWORK);
                piece.setPoType(PO_TYPE_PROCESS);
                piece.setOrderNo(dto.getWeavingContractNo());
                piece.setOrderId(dto.getWeavingContractId());
                piece.setCompanyId(dto.getCompanyId());
                piece.setDcNumber(dto.getDcNumber());
                piece.setDcDate(dto.getDcDate());
                
                // Set fabricMasterId and gradeId from DTO
                if (pieceDto.getFabricMasterId() != null) {
                    piece.setFabricMasterId(pieceDto.getFabricMasterId());
                    piece.setFabricId(pieceDto.getFabricMasterId());
                }
                if (pieceDto.getGradeId() != null) {
                    piece.setGradeId(pieceDto.getGradeId());
                }
                
                if (pieceDto.getWarehouseId() != null) {
                    Warehouse wh = warehouseRepository.findById(pieceDto.getWarehouseId())
                        .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + pieceDto.getWarehouseId()));
                    piece.setWarehouse(wh);
                }
                piece.setInwardDate(jobReceive.getJobFabricReceiveDate());
                piece.setJobworkFabricReceiveItem(item);
                piece.setActiveFlag(true);
                return piece;
            }).collect(Collectors.toList());

            item.setPieceEntries(pieceEntries);
            return item;
        }).collect(Collectors.toList());

        jobReceive.setItems(jobReceiveItems);
        JobworkFabricReceive savedJobReceive = jobworkFabricReceiveRepository.save(jobReceive);
        
        List<PieceEntry> piecesToUpdate = new ArrayList<>();
        for (JobworkFabricReceiveItem item : savedJobReceive.getItems()) {
            if (item.getPieceEntries() != null && !item.getPieceEntries().isEmpty()) {
                for (PieceEntry piece : item.getPieceEntries()) {
                    piece.setInwardId(savedJobReceive.getId());
                    piecesToUpdate.add(piece);
                    fabricInspectionService.createFabricInspectionFromPieceEntry(piece);
                }
            }
        }
        if (!piecesToUpdate.isEmpty()) {
            pieceEntryRepository.saveAll(piecesToUpdate);
        }
        
        return savedJobReceive;
    }

    public List<JobworkFabricReceiveDTO> getAllJobworkFabricReceive(Long companyId) {
        return jobworkFabricReceiveRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(JobworkFabricReceiveDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<JobworkFabricReceiveDTO> getAllJobworkFabricReceiveWithPagination(Long companyId, Long offset, Long limit) {
        List<JobworkFabricReceiveDTO> list = getAllJobworkFabricReceive(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public JobworkFabricReceive getJobworkFabricReceiveById(Long id, Long companyId) {
        Optional<JobworkFabricReceive> jobReceive = jobworkFabricReceiveRepository.findByIdAndCompanyId(id, companyId);
        return jobReceive.orElse(null);
    }

@Transactional
public JobworkFabricReceive updateJobworkFabricReceive(Long id, JobworkFabricReceiveDTO dto, Long companyId) {
    validateDuplicates(dto, id);
    JobworkFabricReceive jobReceive = jobworkFabricReceiveRepository.findByIdAndCompanyId(id, companyId)
        .orElseThrow(() -> new EntityNotFoundException(
                "JobworkFabricReceive not found with id: " + id + " and companyId: " + companyId));
    requireWeavingContractReference(dto.getWeavingContractId(), dto.getWeavingContractNo());

    jobReceive.setWeavingContractId(dto.getWeavingContractId());
    jobReceive.setWeavingContractNo(dto.getWeavingContractNo());
    jobReceive.setVendorId(dto.getVendorId());
    jobReceive.setFabricCode(dto.getFabricCode());
    jobReceive.setFabricDetails(dto.getFabricDetails());
    jobReceive.setQuality(dto.getQuality());
    jobReceive.setRemarks(dto.getRemarks());
    jobReceive.setDcNumber(dto.getDcNumber());
    jobReceive.setDcDate(dto.getDcDate() != null ? dto.getDcDate().atStartOfDay() : null);

    // Process items
    Map<Long, JobworkFabricReceiveItem> existingItemMap = jobReceive.getItems().stream()
        .collect(Collectors.toMap(JobworkFabricReceiveItem::getId, item -> item));

    // Track which items should remain
    Set<Long> dtoItemIds = dto.getJobworkFabricReceiveItemsDtl().stream()
        .map(JobworkFabricReceiveItemDTO::getId)
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());

    // Remove items that are no longer in the DTO
    List<JobworkFabricReceiveItem> itemsToRemove = new ArrayList<>();
    for (JobworkFabricReceiveItem item : jobReceive.getItems()) {
        if (item.getId() != null && !dtoItemIds.contains(item.getId())) {
            itemsToRemove.add(item);
        }
    }
    jobReceive.getItems().removeAll(itemsToRemove);

    // Process each item from DTO
    for (JobworkFabricReceiveItemDTO itemDto : dto.getJobworkFabricReceiveItemsDtl()) {
        JobworkFabricReceiveItem item;
        
        if (itemDto.getId() != null && existingItemMap.containsKey(itemDto.getId())) {
            // Update existing item
            item = existingItemMap.get(itemDto.getId());
            item.setWeavingContractItemId(itemDto.getWeavingContractItemId());
            item.setFabricCode(itemDto.getFabricCode());
            item.setFabricDetails(itemDto.getFabricDetails());
            item.setQuality(itemDto.getQuality());
            item.setQuantityReceived(itemDto.getQuantityReceived());
            item.setPrice(itemDto.getPrice());
            item.setActiveFlag(true);
        } else {
            // Create new item
            item = new JobworkFabricReceiveItem();
            item.setWeavingContractItemId(itemDto.getWeavingContractItemId());
            item.setFabricCode(itemDto.getFabricCode());
            item.setFabricDetails(itemDto.getFabricDetails());
            item.setQuality(itemDto.getQuality());
            item.setQuantityReceived(itemDto.getQuantityReceived());
            item.setPrice(itemDto.getPrice());
            item.setJobworkFabricReceive(jobReceive);
            item.setActiveFlag(true);
            jobReceive.getItems().add(item);
        }

        // Handle piece entries - CRITICAL FIX HERE
        if (item.getId() != null) {
            // For existing items, work with detached copy of pieces
            List<PieceEntry> existingPieces = pieceEntryRepository.findByJobworkFabricReceiveItem(item);
            Map<Long, PieceEntry> existingPieceMap = existingPieces.stream()
                .collect(Collectors.toMap(PieceEntry::getId, p -> p));
            
            Set<Long> dtoPieceIds = itemDto.getPieceEntries().stream()
                .map(PieceEntryDTO::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            
            // Clear current piece entries from item
            item.getPieceEntries().clear();
            
            // Process each piece from DTO
            for (PieceEntryDTO pieceDto : itemDto.getPieceEntries()) {
                PieceEntry piece;
                
                if (pieceDto.getId() != null && existingPieceMap.containsKey(pieceDto.getId())) {
                    // Update existing piece
                    piece = existingPieceMap.get(pieceDto.getId());
                    piece.setPieceNumber(pieceDto.getPieceNumber());
                    piece.setQuantity(pieceDto.getQuantity());
                    piece.setWeight(pieceDto.getWeight());
                    piece.setCost(pieceDto.getCost());
                    piece.setRemarks(pieceDto.getRemarks());
                    
                    if (pieceDto.getFabricMasterId() != null) {
                        piece.setFabricMasterId(pieceDto.getFabricMasterId());
                        piece.setFabricId(pieceDto.getFabricMasterId());
                    }
                    if (pieceDto.getGradeId() != null) {
                        piece.setGradeId(pieceDto.getGradeId());
                    }
                    
                    if (pieceDto.getWarehouseId() != null) {
                        Warehouse wh = warehouseRepository.findById(pieceDto.getWarehouseId())
                            .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + pieceDto.getWarehouseId()));
                        piece.setWarehouse(wh);
                    }
                    piece.setOrderNo(dto.getWeavingContractNo());
                    piece.setOrderId(dto.getWeavingContractId());
                    piece.setInwardDate(jobReceive.getJobFabricReceiveDate());
                    piece.setInwardId(jobReceive.getId());
                    piece.setDcNumber(dto.getDcNumber());
                    piece.setDcDate(dto.getDcDate());
                    piece.setJobworkFabricReceiveItem(item);
                    item.getPieceEntries().add(piece);
                } else {
                    // Create new piece
                    piece = new PieceEntry();
                    piece.setInwardDate(jobReceive.getJobFabricReceiveDate());
                    piece.setPieceNumber(pieceDto.getPieceNumber());
                    piece.setQuantity(pieceDto.getQuantity());
                    piece.setWeight(pieceDto.getWeight());
                    piece.setCost(pieceDto.getCost());
                    piece.setRemarks(pieceDto.getRemarks());
                    
                    piece.setSourceType(SOURCE_JOBWORK);
                    piece.setPoType(PO_TYPE_PROCESS);
                    piece.setOrderNo(dto.getWeavingContractNo());
                    piece.setOrderId(dto.getWeavingContractId());
                    piece.setCompanyId(companyId);
                    piece.setInwardId(jobReceive.getId());
                    piece.setDcNumber(dto.getDcNumber());
                    piece.setDcDate(dto.getDcDate());
                    
                    if (pieceDto.getFabricMasterId() != null) {
                        piece.setFabricMasterId(pieceDto.getFabricMasterId());
                        piece.setFabricId(pieceDto.getFabricMasterId());
                    }
                    if (pieceDto.getGradeId() != null) {
                        piece.setGradeId(pieceDto.getGradeId());
                    }
                    
                    if (pieceDto.getWarehouseId() != null) {
                        Warehouse wh = warehouseRepository.findById(pieceDto.getWarehouseId())
                            .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + pieceDto.getWarehouseId()));
                        piece.setWarehouse(wh);
                    }
                    
                    piece.setJobworkFabricReceiveItem(item);
                    piece.setActiveFlag(true);
                    item.getPieceEntries().add(piece);
                    
                    // Create inspection for new pieces only
                    fabricInspectionService.createFabricInspectionFromPieceEntry(piece);
                }
            }
            
            // Remove pieces that are no longer in DTO
            for (PieceEntry existingPiece : existingPieces) {
                if (!dtoPieceIds.contains(existingPiece.getId())) {
                    pieceEntryRepository.delete(existingPiece);
                }
            }
        } else {
            // For new items, just create pieces
            List<PieceEntry> pieces = itemDto.getPieceEntries().stream().map(pieceDto -> {
                PieceEntry piece = new PieceEntry();
                piece.setPieceNumber(pieceDto.getPieceNumber());
                piece.setQuantity(pieceDto.getQuantity());
                piece.setWeight(pieceDto.getWeight());
                piece.setCost(pieceDto.getCost());
                piece.setRemarks(pieceDto.getRemarks());
                
                piece.setSourceType(SOURCE_JOBWORK);
                piece.setPoType(PO_TYPE_PROCESS);
                piece.setOrderNo(dto.getWeavingContractNo());
                piece.setOrderId(dto.getWeavingContractId());
                piece.setCompanyId(companyId);
                piece.setInwardId(jobReceive.getId());
                piece.setDcNumber(dto.getDcNumber());
                piece.setDcDate(dto.getDcDate());
                
                if (pieceDto.getFabricMasterId() != null) {
                    piece.setFabricMasterId(pieceDto.getFabricMasterId());
                    piece.setFabricId(pieceDto.getFabricMasterId());
                }
                if (pieceDto.getGradeId() != null) {
                    piece.setGradeId(pieceDto.getGradeId());
                }
                
                if (pieceDto.getWarehouseId() != null) {
                    Warehouse wh = warehouseRepository.findById(pieceDto.getWarehouseId())
                        .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + pieceDto.getWarehouseId()));
                    piece.setWarehouse(wh);
                }
                
                piece.setJobworkFabricReceiveItem(item);
                piece.setActiveFlag(true);
                
                // Create inspection for all pieces of new items
                fabricInspectionService.createFabricInspectionFromPieceEntry(piece);
                
                return piece;
            }).collect(Collectors.toList());
            
            item.setPieceEntries(pieces);
        }
    }

    JobworkFabricReceive savedJobReceive = jobworkFabricReceiveRepository.save(jobReceive);
    
    return savedJobReceive;
}

    @Transactional
    public void deleteJobworkFabricReceive(Long id, Long companyId) {
        JobworkFabricReceive existing = jobworkFabricReceiveRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "JobworkFabricReceive not found with id: " + id + " and companyId: " + companyId));
        try {
            jobworkFabricReceiveRepository.delete(existing);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete JobworkFabricReceive because it is referenced by other entities");
        }
    }

    @Transactional
    public void hardDeleteJobworkFabricReceive(Long id,Long companyId) {
        JobworkFabricReceive jobReceive = jobworkFabricReceiveRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("JobworkFabricReceive not found with id: " + id));

        // Check for dependencies: Pieces might have been inspected or packed
        if (jobReceive.getItems() != null) {
            for (JobworkFabricReceiveItem item : jobReceive.getItems()) {
                if (item.getPieceEntries() != null) {
                    for (PieceEntry piece : item.getPieceEntries()) {
                        if (Boolean.TRUE.equals(piece.getIsInspected())) {
                            throw new IllegalStateException("Cannot permanently delete: Piece " + piece.getPieceNumber() + " has already been inspected.");
                        }
                        if (Boolean.TRUE.equals(piece.getIsPacked())) {
                            throw new IllegalStateException("Cannot permanently delete: Piece " + piece.getPieceNumber() + " has already been packed.");
                        }
                    }
                }
            }
        }

        jobworkFabricReceiveRepository.delete(jobReceive);
    }

    private void requireWeavingContractReference(Long weavingContractId, String displayValue) {
        if (weavingContractId == null) {
            return;
        }
        Optional<WeavingContract> weavingContract = weavingContractRepository.findById(weavingContractId);
        String displayIdentifier = displayValue;
        if (displayIdentifier == null || displayIdentifier.isBlank()) {
            displayIdentifier = weavingContract
                    .map(WeavingContract::getWeavingContractNo)
                    .filter(no -> no != null && !no.isBlank())
                    .orElse("Weaving Contract ID " + weavingContractId);
        }
        referenceValidationService.requireReference(
                weavingContractId,
                () -> weavingContract,
                "Weaving Contract",
                displayIdentifier);
    }

    private void validateDuplicates(JobworkFabricReceiveDTO dto, Long currentId) {
        if (dto.getJobworkFabricReceiveItemsDtl() == null) {
            return;
        }

        java.util.Set<String> requestPieceNumbers = new java.util.HashSet<>();

        for (JobworkFabricReceiveItemDTO itemDto : dto.getJobworkFabricReceiveItemsDtl()) {
            if (itemDto.getPieceEntries() == null) {
                continue;
            }

            for (PieceEntryDTO pieceDto : itemDto.getPieceEntries()) {
                String pieceNo = pieceDto.getPieceNumber();
                if (pieceNo == null || pieceNo.trim().isEmpty()) {
                    continue;
                }

                // Check for duplicates within the request
                if (!requestPieceNumbers.add(pieceNo)) {
                    throw new IllegalArgumentException("Duplicate piece number found in request: " + pieceNo);
                }

                // Check for duplicates in database for the same company
                List<PieceEntry> existingPieces = pieceEntryRepository.findByPieceNumberAndCompanyId(pieceNo, dto.getCompanyId());
                for (PieceEntry existingPiece : existingPieces) {
                    // During update, exclude pieces that belong to the current JobworkFabricReceive record
                    if (currentId != null && existingPiece.getJobworkFabricReceiveItem() != null &&
                        existingPiece.getJobworkFabricReceiveItem().getJobworkFabricReceive() != null &&
                        existingPiece.getJobworkFabricReceiveItem().getJobworkFabricReceive().getId().equals(currentId)) {
                        continue;
                    }
                    throw new IllegalArgumentException("Piece number '" + pieceNo + "' already exists for this company");
                }
            }
        }
    }
}