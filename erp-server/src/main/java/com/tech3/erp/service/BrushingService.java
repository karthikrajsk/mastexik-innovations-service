package com.tech3.erp.service;

import com.tech3.erp.dto.BrushingReceiveDTO;
import com.tech3.erp.dto.BrushingReceiveDetailDTO;
import com.tech3.erp.dto.BrushingReceiveStatusUpdateDTO;
import com.tech3.erp.dto.BrushingStockReportDTO;
import com.tech3.erp.dto.BrushingUnitFabricStatusDTO;
import java.time.LocalDate;
import com.tech3.erp.entity.BrushingReceive;
import com.tech3.erp.entity.BrushingReceiveDetail;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.repository.BrushingReceiveDetailRepository;
import com.tech3.erp.repository.BrushingReceiveRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.entity.WovenFabricMaster;
import com.tech3.erp.repository.WovenFabricMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrushingService {

    private final BrushingReceiveRepository brushingReceiveRepository;
    private final BrushingReceiveDetailRepository brushingReceiveDetailRepository;
    private final PieceEntryRepository pieceEntryRepository;

    @Autowired
    private WovenFabricMasterRepository wovenFabricMasterRepository;

    @Autowired
    public BrushingService(BrushingReceiveRepository brushingReceiveRepository,
                           BrushingReceiveDetailRepository brushingReceiveDetailRepository,
                           PieceEntryRepository pieceEntryRepository) {
        this.brushingReceiveRepository = brushingReceiveRepository;
        this.brushingReceiveDetailRepository = brushingReceiveDetailRepository;
        this.pieceEntryRepository = pieceEntryRepository;
    }



    @Transactional
    public BrushingReceiveDTO createBrushingReceive(BrushingReceiveDTO dto) {
        BrushingReceive receive = new BrushingReceive();
        receive.setReceiveChallanNo(dto.getReceiveChallanNo());
        receive.setReceiveDate(dto.getReceiveDate());
        receive.setRemarks(dto.getRemarks());
        receive.setCompanyId(dto.getCompanyId());
        receive.setActiveFlag(true);
        
        receive.setOrderType(dto.getOrderType());
        receive.setBuyerId(dto.getBuyerId());
        receive.setFabricMasterId(dto.getFabricMasterId());
        receive.setStatus("PENDING");
        
        BigDecimal totalGreigeMeters = BigDecimal.ZERO;
        if (dto.getDetails() != null) {
            for (BrushingReceiveDetailDTO detailDto : dto.getDetails()) {
                BigDecimal greigeMeters = detailDto.getGreigeMeters() != null ? detailDto.getGreigeMeters() : BigDecimal.ZERO;
                totalGreigeMeters = totalGreigeMeters.add(greigeMeters);
            }
        }
        receive.setPassMtr(totalGreigeMeters);
        
        receive.setIsBrushed(0);
        receive.setIsRejected(dto.getIsRejected() != null ? dto.getIsRejected() : 0);
        receive.setWeaverId(dto.getWeaverId());
        receive.setUnrollingMeter(dto.getUnrollingMeter());
        receive.setUnrollingDate(dto.getUnrollingDate());
        receive.setBrushingCompletedDate(dto.getBrushingCompletedDate());
        receive.setRefInvoiceNo(dto.getRefInvoiceNo());
        receive.setRefPoNo(dto.getRefPoNo());

        BrushingReceive savedReceive = brushingReceiveRepository.save(receive);
        List<BrushingReceiveDetail> savedDetails = new ArrayList<>();

        int rollNoOffset = 0;
        if (dto.getDetails() != null) {
            for (BrushingReceiveDetailDTO detailDto : dto.getDetails()) {
                BigDecimal greigeMeters = detailDto.getGreigeMeters() != null ? detailDto.getGreigeMeters() : BigDecimal.ZERO;
                BigDecimal brushedMeters = detailDto.getBrushedMeters() != null ? detailDto.getBrushedMeters() : BigDecimal.ZERO;
                BigDecimal shrinkagePercent = BigDecimal.ZERO;
                if (greigeMeters.compareTo(BigDecimal.ZERO) > 0) {
                    shrinkagePercent = brushedMeters.subtract(greigeMeters)
                            .multiply(BigDecimal.valueOf(100))
                            .divide(greigeMeters, 2, RoundingMode.HALF_UP);
                }

                BigDecimal greigeWeight = detailDto.getGreigeWeight() != null ? detailDto.getGreigeWeight() : BigDecimal.ZERO;
                BigDecimal brushedWeight = detailDto.getBrushedWeight() != null ? detailDto.getBrushedWeight() : BigDecimal.ZERO;
                BigDecimal weightLossPercent = BigDecimal.ZERO;
                if (greigeWeight.compareTo(BigDecimal.ZERO) > 0) {
                    weightLossPercent = brushedWeight.subtract(greigeWeight)
                            .multiply(BigDecimal.valueOf(100))
                            .divide(greigeWeight, 2, RoundingMode.HALF_UP);
                }

                BrushingReceiveDetail detail = new BrushingReceiveDetail();
                detail.setBrushingReceive(savedReceive);
                detail.setPieceNumber(detailDto.getPieceNumber() != null ? detailDto.getPieceNumber().trim() : "");
                detail.setGreigeMeters(greigeMeters);
                detail.setGreigeWeight(greigeWeight);
                detail.setBrushedMeters(brushedMeters);
                detail.setBrushedWeight(brushedWeight);
                detail.setShrinkagePercentage(shrinkagePercent);
                detail.setWeightLossPercentage(weightLossPercent);
                int isBrushedVal = detailDto.getIsBrushed() != null ? detailDto.getIsBrushed() : 0;
                detail.setIsBrushed(isBrushedVal);
                detail.setCostPerUnit(detailDto.getCostPerUnit());
                detail.setTotalAmount(detailDto.getTotalAmount());
                detail.setGreigeGrossWt(detailDto.getGreigeGrossWt());
                detail.setBrushedGrossWt(detailDto.getBrushedGrossWt());
                detail.setTc(detailDto.getTc());
                detail.setWeave(detailDto.getWeave());
                detail.setDate(detailDto.getDate());
                detail.setPartyName(detailDto.getPartyName());
                detail.setInvoiceNo(detailDto.getInvoiceNo());
                detail.setConstruction(detailDto.getConstruction());
                detail.setGlm(detailDto.getGlm());
                detail.setVendorId(detailDto.getVendorId());
                detail.setFabricMasterId(detailDto.getFabricMasterId());
                detail.setPassMtr(detailDto.getPassMtr());
                detail.setHfplRollNo(detailDto.getHfplRollNo());
                detail.setHfplInvoiceNo(detailDto.getHfplInvoiceNo());
                detail.setIsPacked(isBrushedVal == 1 ? 1 : 0);
                detail.setIsDispatched(detailDto.getIsDispatched() != null ? detailDto.getIsDispatched() : 0);
                detail.setIsInvoice(detailDto.getIsInvoice() != null ? detailDto.getIsInvoice() : 0);
                detail.setDispatchDate(detailDto.getDispatchDate());
                if (isBrushedVal == 1) {
                    if (detail.getHfplRollNo() == null || detail.getHfplRollNo().isEmpty()) {
                        detail.setHfplRollNo(generateNextHfplRollNo(rollNoOffset));
                        rollNoOffset++;
                    }
                    if (detailDto.getRollingDate() != null) {
                        detail.setRollingDate(detailDto.getRollingDate());
                    } else if (detail.getRollingDate() == null) {
                        detail.setRollingDate(java.time.LocalDate.now());
                    }
                } else {
                    detail.setHfplRollNo(null);
                    detail.setRollingDate(null);
                }
                detail.setActiveFlag(true);

                // Set unwinding and brushing process fields
                detail.setUnwoundMeters(detailDto.getUnwoundMeters());
                detail.setUnwoundWeight(detailDto.getUnwoundWeight());
                detail.setUnwoundGrossWt(detailDto.getUnwoundGrossWt());
                detail.setUnwindDate(detailDto.getUnwindDate());
                detail.setUnwindedDate(detailDto.getUnwindedDate());
                detail.setUnwindStatus(detailDto.getUnwindStatus() != null ? detailDto.getUnwindStatus() : "PENDING");
                detail.setUnwindRemarks(detailDto.getUnwindRemarks());
                detail.setIsUnwound(detailDto.getIsUnwound() != null ? detailDto.getIsUnwound() : 0);
                int newIsRejected = detailDto.getIsRejected() != null ? detailDto.getIsRejected() : 0;
                detail.setIsRejected(newIsRejected);
                int newIsReturn = detailDto.getIsReturn() != null ? detailDto.getIsReturn() : 0;
                detail.setIsReturn(newIsReturn);
                if (newIsRejected == 1) {
                    if (detail.getRejectionDate() == null) {
                        detail.setRejectionDate(detailDto.getRejectionDate() != null ? detailDto.getRejectionDate() : java.time.LocalDate.now());
                    }
                    detail.setRejectedReason(detailDto.getRejectedReason() != null ? detailDto.getRejectedReason() : detail.getRejectedReason());
                    if (detail.getHfplDcNo() == null || detail.getHfplDcNo().isEmpty()) {
                        detail.setHfplDcNo(generateNextHfplDcNo());
                    }
                    detail.setDcNo(detailDto.getDcNo());
                    detail.setReturnType(detailDto.getReturnType() != null ? detailDto.getReturnType() : "Returnable");
                } else if (newIsReturn == 1) {
                    if (detail.getReturnDate() == null) {
                        detail.setReturnDate(detailDto.getReturnDate() != null ? detailDto.getReturnDate() : (detailDto.getRejectionDate() != null ? detailDto.getRejectionDate() : java.time.LocalDate.now()));
                    }
                    detail.setReturnReason(detailDto.getReturnReason() != null ? detailDto.getReturnReason() : (detailDto.getRejectedReason() != null ? detailDto.getRejectedReason() : detail.getReturnReason()));
                    if (detail.getHfplDcNo() == null || detail.getHfplDcNo().isEmpty()) {
                        detail.setHfplDcNo(generateNextHfplDcNo());
                    }
                    detail.setDcNo(detailDto.getDcNo());
                    detail.setReturnType(detailDto.getReturnType() != null ? detailDto.getReturnType() : "Returnable");
                } else {
                    detail.setRejectionDate(null);
                    detail.setRejectedReason(null);
                    detail.setReturnDate(null);
                    detail.setReturnReason(null);
                    detail.setHfplDcNo(null);
                    detail.setDcNo(null);
                    detail.setReturnType(null);
                }
                detail.setBrushingDate(detailDto.getBrushingDate());
                detail.setBrushingMachineNo(detailDto.getBrushingMachineNo());
                detail.setMachineId(detailDto.getMachineId());
                detail.setBrushingStatus(detailDto.getBrushingStatus() != null ? detailDto.getBrushingStatus() : "PENDING");

                if (detailDto.getUnwindStatus() != null && !"PENDING".equalsIgnoreCase(detailDto.getUnwindStatus())) {
                    detail.setUnwindUpdatedAt(java.time.LocalDateTime.now());
                    detail.setUnwindUpdatedBy(detailDto.getUnwindUpdatedBy());
                }
                if (detailDto.getBrushingStatus() != null && !"PENDING".equalsIgnoreCase(detailDto.getBrushingStatus())) {
                    detail.setBrushingUpdatedAt(java.time.LocalDateTime.now());
                    detail.setBrushingUpdatedBy(detailDto.getBrushingUpdatedBy());
                }

                savedDetails.add(brushingReceiveDetailRepository.save(detail));
            }
        }
        savedReceive.setDetails(savedDetails);
        return toBrushingReceiveDTO(savedReceive);
    }

    public List<BrushingReceiveDTO> getBrushingReceivesByCompany(Long companyId) {
        return brushingReceiveRepository.findByCompanyIdAndActiveFlag(companyId, true)
                .stream()
                .map(this::toBrushingReceiveDTO)
                .collect(Collectors.toList());
    }

    public BrushingReceiveDTO getBrushingReceiveById(Long id) {
        BrushingReceive receive = brushingReceiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brushing Receive not found: " + id));
        return toBrushingReceiveDTO(receive);
    }

    // ==========================================
    // UTILITY PIECE QUERIES
    // ==========================================

    public List<PieceEntry> getPiecesAvailableForBrushingInspection(Long companyId) {
        // Fetch pieces received from brushing but not yet inspected
        return pieceEntryRepository.findAll().stream()
                .filter(p -> p.getCompanyId() != null && p.getCompanyId().equals(companyId))
                .filter(p -> "RECEIVED".equals(p.getBrushingStatus()))
                .filter(p -> p.getIsInspected() == null || p.getIsInspected() == 0)
                .filter(p -> p.getActiveFlag() != null && p.getActiveFlag())
                .collect(Collectors.toList());
    }

@Transactional
public void deleteBrushingReceive(Long id) {
    BrushingReceive receive = brushingReceiveRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Brushing Receive not found: " + id));

    // Permanently delete associated details
    if (receive.getDetails() != null) {
        brushingReceiveDetailRepository.deleteAll(receive.getDetails());
    }

    // Permanently delete the receive
    brushingReceiveRepository.delete(receive);
}

    @Transactional
    public BrushingReceiveDTO updateBrushingReceive(Long id, BrushingReceiveDTO dto) {
        BrushingReceive receive = brushingReceiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brushing Receive not found: " + id));

        java.util.Map<Long, BrushingReceiveDetail> existingDetailsMap = new java.util.HashMap<>();
        if (receive.getDetails() != null) {
            for (BrushingReceiveDetail detail : receive.getDetails()) {
                if (detail.getId() != null) {
                    existingDetailsMap.put(detail.getId(), detail);
                }
            }
        }

        // Update receive header
        receive.setReceiveChallanNo(dto.getReceiveChallanNo());
        receive.setReceiveDate(dto.getReceiveDate());
        receive.setRemarks(dto.getRemarks());
        
        receive.setOrderType(dto.getOrderType());
        receive.setBuyerId(dto.getBuyerId());
        receive.setFabricMasterId(dto.getFabricMasterId());
        receive.setWeaverId(dto.getWeaverId());
        receive.setUnrollingMeter(dto.getUnrollingMeter());
        receive.setUnrollingDate(dto.getUnrollingDate());
        receive.setBrushingCompletedDate(dto.getBrushingCompletedDate());
        receive.setRefInvoiceNo(dto.getRefInvoiceNo());
        receive.setRefPoNo(dto.getRefPoNo());
        receive.setIsRejected(dto.getIsRejected() != null ? dto.getIsRejected() : 0);
        // Update parent status and isBrushed flag if supplied by the caller
        if (dto.getStatus() != null) {
            receive.setStatus(dto.getStatus());
        }
        if (dto.getIsBrushed() != null) {
            receive.setIsBrushed(dto.getIsBrushed());
        }

        BigDecimal totalGreigeMeters = BigDecimal.ZERO;
        if (dto.getDetails() != null) {
            for (BrushingReceiveDetailDTO detailDto : dto.getDetails()) {
                BigDecimal greigeMeters = detailDto.getGreigeMeters() != null ? detailDto.getGreigeMeters() : BigDecimal.ZERO;
                totalGreigeMeters = totalGreigeMeters.add(greigeMeters);
            }
        }
        receive.setPassMtr(totalGreigeMeters);

        BrushingReceive savedReceive = brushingReceiveRepository.saveAndFlush(receive);

        // Remove details that are not in the DTO list anymore
        if (savedReceive.getDetails() != null) {
            java.util.Set<Long> dtoIds = dto.getDetails() != null 
                ? dto.getDetails().stream()
                    .map(BrushingReceiveDetailDTO::getId)
                    .filter(java.util.Objects::nonNull)
                    .collect(java.util.stream.Collectors.toSet())
                : java.util.Collections.emptySet();

            savedReceive.getDetails().removeIf(detail -> detail.getId() != null && !dtoIds.contains(detail.getId()));
        }

        int rollNoOffset = 0;
        if (dto.getDetails() != null) {
            for (BrushingReceiveDetailDTO detailDto : dto.getDetails()) {
                BigDecimal greigeMeters = detailDto.getGreigeMeters() != null ? detailDto.getGreigeMeters() : BigDecimal.ZERO;
                BigDecimal greigeWeight = detailDto.getGreigeWeight() != null ? detailDto.getGreigeWeight() : BigDecimal.ZERO;
                BigDecimal brushedMeters = detailDto.getBrushedMeters() != null ? detailDto.getBrushedMeters() : BigDecimal.ZERO;
                BigDecimal brushedWeight = detailDto.getBrushedWeight() != null ? detailDto.getBrushedWeight() : BigDecimal.ZERO;

                BigDecimal shrinkagePercent = BigDecimal.ZERO;
                if (greigeMeters.compareTo(BigDecimal.ZERO) > 0) {
                    shrinkagePercent = brushedMeters.subtract(greigeMeters)
                            .multiply(BigDecimal.valueOf(100))
                            .divide(greigeMeters, 2, RoundingMode.HALF_UP);
                }

                BigDecimal weightLossPercent = BigDecimal.ZERO;
                if (greigeWeight.compareTo(BigDecimal.ZERO) > 0) {
                    weightLossPercent = brushedWeight.subtract(greigeWeight)
                            .multiply(BigDecimal.valueOf(100))
                            .divide(greigeWeight, 2, RoundingMode.HALF_UP);
                }

                BrushingReceiveDetail detail = null;
                if (detailDto.getId() != null) {
                    detail = existingDetailsMap.get(detailDto.getId());
                }

                boolean isNew = (detail == null);
                if (isNew) {
                    detail = new BrushingReceiveDetail();
                    detail.setBrushingReceive(savedReceive);
                    detail.setIsPacked(0);
                    detail.setIsDispatched(0);
                    detail.setIsInvoice(0);
                }

                String brushingStatusVal = detailDto.getBrushingStatus() != null ? detailDto.getBrushingStatus() : (detail.getBrushingStatus() != null ? detail.getBrushingStatus() : "PENDING");
                int isBrushedVal = "READY_FOR_DISPATCH".equalsIgnoreCase(brushingStatusVal) || (detailDto.getIsBrushed() != null && detailDto.getIsBrushed() == 1) ? 1 : 0;

                detail.setPieceNumber(detailDto.getPieceNumber() != null ? detailDto.getPieceNumber().trim() : "");
                detail.setGreigeMeters(greigeMeters);
                detail.setGreigeWeight(greigeWeight);
                detail.setBrushedMeters(brushedMeters);
                detail.setBrushedWeight(brushedWeight);
                detail.setShrinkagePercentage(shrinkagePercent);
                detail.setWeightLossPercentage(weightLossPercent);
                detail.setIsBrushed(isBrushedVal);
                detail.setIsPacked(isBrushedVal == 1 ? 1 : 0);
                detail.setCostPerUnit(detailDto.getCostPerUnit());
                detail.setTotalAmount(detailDto.getTotalAmount());
                detail.setGreigeGrossWt(detailDto.getGreigeGrossWt());
                detail.setBrushedGrossWt(detailDto.getBrushedGrossWt());
                detail.setTc(detailDto.getTc());
                detail.setWeave(detailDto.getWeave());
                detail.setDate(detailDto.getDate());
                detail.setPartyName(detailDto.getPartyName());
                detail.setInvoiceNo(detailDto.getInvoiceNo());
                detail.setConstruction(detailDto.getConstruction());
                detail.setGlm(detailDto.getGlm());
                detail.setVendorId(detailDto.getVendorId());
                detail.setFabricMasterId(detailDto.getFabricMasterId());
                BigDecimal passMtrVal = detailDto.getPassMtr();
                if (brushedMeters != null && brushedMeters.compareTo(BigDecimal.ZERO) > 0) {
                    passMtrVal = brushedMeters;
                }
                detail.setPassMtr(passMtrVal);
                detail.setIsDispatched(detailDto.getIsDispatched() != null ? detailDto.getIsDispatched() : (detail.getIsDispatched() != null ? detail.getIsDispatched() : 0));
                detail.setIsInvoice(detailDto.getIsInvoice() != null ? detailDto.getIsInvoice() : (detail.getIsInvoice() != null ? detail.getIsInvoice() : 0));
                if (isBrushedVal == 1) {
                    if (detailDto.getHfplRollNo() != null && !detailDto.getHfplRollNo().trim().isEmpty()) {
                        detail.setHfplRollNo(detailDto.getHfplRollNo().trim());
                    } else if (detail.getHfplRollNo() == null || detail.getHfplRollNo().isEmpty()) {
                        detail.setHfplRollNo(generateNextHfplRollNo(rollNoOffset));
                        rollNoOffset++;
                    }
                    if (detailDto.getRollingDate() != null) {
                        detail.setRollingDate(detailDto.getRollingDate());
                    } else if (detail.getRollingDate() == null) {
                        detail.setRollingDate(java.time.LocalDate.now());
                    }
                } else {
                    if (detailDto.getHfplRollNo() != null && !detailDto.getHfplRollNo().trim().isEmpty()) {
                        detail.setHfplRollNo(detailDto.getHfplRollNo().trim());
                    }
                    detail.setRollingDate(null);
                }
                detail.setActiveFlag(true);

                // Set unwinding and brushing process fields
                detail.setUnwoundMeters(detailDto.getUnwoundMeters() != null ? detailDto.getUnwoundMeters() : detail.getUnwoundMeters());
                detail.setUnwoundWeight(detailDto.getUnwoundWeight() != null ? detailDto.getUnwoundWeight() : detail.getUnwoundWeight());
                detail.setUnwoundGrossWt(detailDto.getUnwoundGrossWt() != null ? detailDto.getUnwoundGrossWt() : detail.getUnwoundGrossWt());
                detail.setUnwindDate(detailDto.getUnwindDate() != null ? detailDto.getUnwindDate() : detail.getUnwindDate());
                
                // Handle setting unwindedDate if transition to ACCEPTED occurs
                if ("ACCEPTED".equalsIgnoreCase(detailDto.getUnwindStatus()) || "ACCEPTED".equalsIgnoreCase(detail.getUnwindStatus())) {
                    if (detailDto.getUnwindedDate() != null) {
                        detail.setUnwindedDate(detailDto.getUnwindedDate());
                    } else if (detail.getUnwindedDate() == null) {
                        detail.setUnwindedDate(java.time.LocalDate.now());
                    }
                } else {
                    detail.setUnwindedDate(detailDto.getUnwindedDate() != null ? detailDto.getUnwindedDate() : detail.getUnwindedDate());
                }
                String newUnwindStatus = detailDto.getUnwindStatus() != null ? detailDto.getUnwindStatus() : (detail.getUnwindStatus() != null ? detail.getUnwindStatus() : "PENDING");
                if (!newUnwindStatus.equals(detail.getUnwindStatus()) || detailDto.getUnwindUpdatedAt() != null) {
                    detail.setUnwindStatus(newUnwindStatus);
                    if (detailDto.getUnwindUpdatedAt() != null) {
                        detail.setUnwindUpdatedAt(detailDto.getUnwindUpdatedAt());
                    } else {
                        detail.setUnwindUpdatedAt(java.time.LocalDateTime.now());
                    }
                    if (detailDto.getUnwindUpdatedBy() != null && !detailDto.getUnwindUpdatedBy().isEmpty()) {
                        detail.setUnwindUpdatedBy(detailDto.getUnwindUpdatedBy());
                    }
                } else {
                    detail.setUnwindStatus(newUnwindStatus);
                }
                detail.setUnwindRemarks(detailDto.getUnwindRemarks() != null ? detailDto.getUnwindRemarks() : detail.getUnwindRemarks());
                detail.setIsUnwound(detailDto.getIsUnwound() != null ? detailDto.getIsUnwound() : (detail.getIsUnwound() != null ? detail.getIsUnwound() : 0));
                int newIsRejected = detailDto.getIsRejected() != null ? detailDto.getIsRejected() : (detail.getIsRejected() != null ? detail.getIsRejected() : 0);
                detail.setIsRejected(newIsRejected);
                int newIsReturn = detailDto.getIsReturn() != null ? detailDto.getIsReturn() : (detail.getIsReturn() != null ? detail.getIsReturn() : 0);
                detail.setIsReturn(newIsReturn);
                if (newIsRejected == 1) {
                    if (detail.getRejectionDate() == null) {
                        detail.setRejectionDate(detailDto.getRejectionDate() != null ? detailDto.getRejectionDate() : java.time.LocalDate.now());
                    }
                    detail.setRejectedReason(detailDto.getRejectedReason() != null ? detailDto.getRejectedReason() : detail.getRejectedReason());
                    if (detail.getHfplDcNo() == null || detail.getHfplDcNo().isEmpty()) {
                        detail.setHfplDcNo(generateNextHfplDcNo());
                    }
                    detail.setDcNo(detailDto.getDcNo() != null ? detailDto.getDcNo() : detail.getDcNo());
                    detail.setReturnType(detailDto.getReturnType() != null ? detailDto.getReturnType() : detail.getReturnType());
                } else if (newIsReturn == 1) {
                    if (detailDto.getReturnDate() != null) {
                        detail.setReturnDate(detailDto.getReturnDate());
                    } else if (detail.getReturnDate() == null) {
                        detail.setReturnDate(detailDto.getRejectionDate() != null ? detailDto.getRejectionDate() : java.time.LocalDate.now());
                    }
                    if (detailDto.getReturnReason() != null) {
                        detail.setReturnReason(detailDto.getReturnReason());
                    } else if (detailDto.getRejectedReason() != null) {
                        detail.setReturnReason(detailDto.getRejectedReason());
                    }
                    if (detailDto.getHfplDcNo() != null && !detailDto.getHfplDcNo().trim().isEmpty()) {
                        detail.setHfplDcNo(detailDto.getHfplDcNo().trim());
                    } else if (detail.getHfplDcNo() == null || detail.getHfplDcNo().isEmpty()) {
                        detail.setHfplDcNo(generateNextHfplDcNo());
                    }
                    if (detailDto.getDcNo() != null) {
                        detail.setDcNo(detailDto.getDcNo());
                    }
                    if (detailDto.getReturnType() != null) {
                        detail.setReturnType(detailDto.getReturnType());
                    }
                } else {
                    detail.setRejectionDate(null);
                    detail.setRejectedReason(null);
                    detail.setReturnDate(null);
                    detail.setReturnReason(null);
                    detail.setHfplDcNo(null);
                    detail.setDcNo(null);
                    detail.setReturnType(null);
                }
                detail.setBrushingDate(detailDto.getBrushingDate() != null ? detailDto.getBrushingDate() : detail.getBrushingDate());
                detail.setBrushingMachineNo(detailDto.getBrushingMachineNo() != null ? detailDto.getBrushingMachineNo() : detail.getBrushingMachineNo());
                detail.setMachineId(detailDto.getMachineId() != null ? detailDto.getMachineId() : detail.getMachineId());
                detail.setDispatchDate(detailDto.getDispatchDate() != null ? detailDto.getDispatchDate() : detail.getDispatchDate());
                
                String newBrushingStatus = detailDto.getBrushingStatus() != null ? detailDto.getBrushingStatus() : (detail.getBrushingStatus() != null ? detail.getBrushingStatus() : "PENDING");
                if (!newBrushingStatus.equals(detail.getBrushingStatus()) || detailDto.getBrushingUpdatedAt() != null) {
                    detail.setBrushingStatus(newBrushingStatus);
                    if (detailDto.getBrushingUpdatedAt() != null) {
                        detail.setBrushingUpdatedAt(detailDto.getBrushingUpdatedAt());
                    } else {
                        detail.setBrushingUpdatedAt(java.time.LocalDateTime.now());
                    }
                    if (detailDto.getBrushingUpdatedBy() != null && !detailDto.getBrushingUpdatedBy().isEmpty()) {
                        detail.setBrushingUpdatedBy(detailDto.getBrushingUpdatedBy());
                    }
                } else {
                    detail.setBrushingStatus(newBrushingStatus);
                }

                if (isNew) {
                    if (savedReceive.getDetails() == null) {
                        savedReceive.setDetails(new java.util.ArrayList<>());
                    }
                    savedReceive.getDetails().add(detail);
                }
            }
        }
        BrushingReceive finalReceive = brushingReceiveRepository.save(savedReceive);
        return toBrushingReceiveDTO(finalReceive);
    }

    private BrushingReceiveDTO toBrushingReceiveDTO(BrushingReceive receive) {
        BrushingReceiveDTO dto = new BrushingReceiveDTO();
        dto.setId(receive.getId());
        dto.setReceiveChallanNo(receive.getReceiveChallanNo());
        dto.setReceiveDate(receive.getReceiveDate());
        dto.setRemarks(receive.getRemarks());
        dto.setCompanyId(receive.getCompanyId());
        
        dto.setOrderType(receive.getOrderType());
        dto.setBuyerId(receive.getBuyerId());
        dto.setFabricMasterId(receive.getFabricMasterId());
        dto.setStatus(receive.getStatus());
        dto.setPassMtr(receive.getPassMtr());
        dto.setIsBrushed(receive.getIsBrushed());
        dto.setWeaverId(receive.getWeaverId());
        dto.setUnrollingMeter(receive.getUnrollingMeter());
        dto.setUnrollingDate(receive.getUnrollingDate());
        dto.setBrushingCompletedDate(receive.getBrushingCompletedDate());
        dto.setRefInvoiceNo(receive.getRefInvoiceNo());
        dto.setRefPoNo(receive.getRefPoNo());
        dto.setIsRejected(receive.getIsRejected());

        if (receive.getDetails() != null) {
            dto.setDetails(receive.getDetails().stream().map(detail -> {
                BrushingReceiveDetailDTO rDto = new BrushingReceiveDetailDTO();
                rDto.setId(detail.getId());
                rDto.setPieceEntryId(null);
                rDto.setPieceNumber(detail.getPieceNumber() != null ? detail.getPieceNumber() : "");
                rDto.setBrushedMeters(detail.getBrushedMeters());
                rDto.setBrushedWeight(detail.getBrushedWeight());
                rDto.setShrinkagePercentage(detail.getShrinkagePercentage());
                rDto.setWeightLossPercentage(detail.getWeightLossPercentage());
                rDto.setIsBrushed(detail.getIsBrushed());
                rDto.setCostPerUnit(detail.getCostPerUnit());
                rDto.setTotalAmount(detail.getTotalAmount());
                
                rDto.setGreigeMeters(detail.getGreigeMeters());
                rDto.setGreigeWeight(detail.getGreigeWeight());
                rDto.setGreigeGrossWt(detail.getGreigeGrossWt());
                rDto.setBrushedGrossWt(detail.getBrushedGrossWt());
                rDto.setTc(detail.getTc());
                rDto.setWeave(detail.getWeave());
                rDto.setDate(detail.getDate());
                rDto.setPartyName(detail.getPartyName());
                rDto.setInvoiceNo(detail.getInvoiceNo());
                rDto.setConstruction(detail.getConstruction());
                rDto.setGlm(detail.getGlm());
                rDto.setVendorId(detail.getVendorId());
                rDto.setFabricMasterId(detail.getFabricMasterId());
                rDto.setPassMtr(detail.getPassMtr());
                rDto.setIsPacked(detail.getIsPacked());
                rDto.setDispatchDate(detail.getDispatchDate());
                rDto.setIsDispatched(detail.getIsDispatched());
                rDto.setIsInvoice(detail.getIsInvoice());

                // New fields
                rDto.setUnwoundMeters(detail.getUnwoundMeters());
                rDto.setUnwoundWeight(detail.getUnwoundWeight());
                rDto.setUnwoundGrossWt(detail.getUnwoundGrossWt());
                rDto.setUnwindDate(detail.getUnwindDate());
                rDto.setUnwindedDate(detail.getUnwindedDate());
                rDto.setUnwindStatus(detail.getUnwindStatus());
                rDto.setUnwindRemarks(detail.getUnwindRemarks());
                rDto.setIsUnwound(detail.getIsUnwound());
                rDto.setIsRejected(detail.getIsRejected());
                rDto.setRejectionDate(detail.getRejectionDate());
                rDto.setRejectedReason(detail.getRejectedReason());
                rDto.setIsReturn(detail.getIsReturn());
                rDto.setReturnDate(detail.getReturnDate());
                rDto.setReturnReason(detail.getReturnReason());
                rDto.setHfplDcNo(detail.getHfplDcNo());
                rDto.setDcNo(detail.getDcNo());
                rDto.setReturnType(detail.getReturnType());
                rDto.setBrushingDate(detail.getBrushingDate());
                rDto.setBrushingMachineNo(detail.getBrushingMachineNo());
                rDto.setMachineId(detail.getMachineId());
                rDto.setBrushingStatus(detail.getBrushingStatus());
                rDto.setHfplRollNo(detail.getHfplRollNo());
                rDto.setHfplInvoiceNo(detail.getHfplInvoiceNo());
                rDto.setRollingDate(detail.getRollingDate());
                rDto.setUnwindUpdatedAt(detail.getUnwindUpdatedAt());
                rDto.setUnwindUpdatedBy(detail.getUnwindUpdatedBy());
                rDto.setBrushingUpdatedAt(detail.getBrushingUpdatedAt());
                rDto.setBrushingUpdatedBy(detail.getBrushingUpdatedBy());
                return rDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    @Transactional
    public BrushingReceiveDTO updateBrushingReceiveStatus(Long id, BrushingReceiveStatusUpdateDTO statusDto) {
        BrushingReceive receive = brushingReceiveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brushing Receive not found: " + id));

        String status = statusDto.getStatus();
        receive.setStatus(status);

        int isBrushedVal = "COMPLETE".equalsIgnoreCase(status) ? 1 : 0;
        receive.setIsBrushed(isBrushedVal);
        if ("COMPLETE".equalsIgnoreCase(status)) {
            receive.setBrushingCompletedDate(statusDto.getBrushingCompletedDate());
        } else {
            receive.setBrushingCompletedDate(null);
        }

        // Update isBrushed for all details simultaneously ONLY if no details are passed in the DTO
        if (statusDto.getDetails() == null && receive.getDetails() != null) {
            int statusRollNoOffset1 = 0;
            for (BrushingReceiveDetail detail : receive.getDetails()) {
                detail.setIsBrushed(isBrushedVal);
                if (isBrushedVal == 1) {
                    if (detail.getBrushingDate() == null) {
                        detail.setBrushingDate(java.time.LocalDate.now());
                    }
                    detail.setBrushingStatus("COMPLETED");
                    detail.setIsPacked(1);
                    if (detail.getHfplRollNo() == null || detail.getHfplRollNo().isEmpty()) {
                        detail.setHfplRollNo(generateNextHfplRollNo());
                    }
                    if (detail.getRollingDate() == null) {
                        detail.setRollingDate(java.time.LocalDate.now());
                    }
                    if (detail.getBrushedMeters() != null && detail.getBrushedMeters().compareTo(BigDecimal.ZERO) > 0) {
                        detail.setPassMtr(detail.getBrushedMeters());
                    }
                } else {
                    detail.setBrushingStatus("PENDING");
                    detail.setIsPacked(0);
                }
            }
        }

        BigDecimal totalPassMtr = BigDecimal.ZERO;

        int statusRollNoOffset2 = 0;
        if (statusDto.getDetails() != null && receive.getDetails() != null) {
            for (BrushingReceiveDetailDTO detailDto : statusDto.getDetails()) {
                // Find matching detail in receive
                BrushingReceiveDetail detail = receive.getDetails().stream()
                        .filter(d -> (detailDto.getId() != null && detailDto.getId().equals(d.getId())) || 
                                     (detailDto.getPieceNumber() != null && d.getPieceNumber() != null && detailDto.getPieceNumber().equalsIgnoreCase(d.getPieceNumber())))
                        .findFirst()
                        .orElseThrow(() -> new EntityNotFoundException("Brushing Receive Detail not found for piece number: " + detailDto.getPieceNumber()));

                BigDecimal greigeMeters = detail.getGreigeMeters();
                if (greigeMeters == null) greigeMeters = BigDecimal.ZERO;
                BigDecimal brushedMeters = detailDto.getBrushedMeters() != null ? detailDto.getBrushedMeters() : BigDecimal.ZERO;

                BigDecimal shrinkagePercent = BigDecimal.ZERO;
                if (greigeMeters.compareTo(BigDecimal.ZERO) > 0) {
                    shrinkagePercent = brushedMeters.subtract(greigeMeters)
                            .multiply(BigDecimal.valueOf(100))
                            .divide(greigeMeters, 2, RoundingMode.HALF_UP);
                }

                BigDecimal greigeWeight = detail.getGreigeWeight();
                if (greigeWeight == null) greigeWeight = BigDecimal.ZERO;
                BigDecimal brushedWeight = detailDto.getBrushedWeight() != null ? detailDto.getBrushedWeight() : BigDecimal.ZERO;

                BigDecimal weightLossPercent = BigDecimal.ZERO;
                if (greigeWeight.compareTo(BigDecimal.ZERO) > 0) {
                    weightLossPercent = brushedWeight.subtract(greigeWeight)
                            .multiply(BigDecimal.valueOf(100))
                            .divide(greigeWeight, 2, RoundingMode.HALF_UP);
                }

                // Update detail entity
                detail.setBrushedMeters(brushedMeters);
                detail.setBrushedWeight(brushedWeight);
                detail.setShrinkagePercentage(shrinkagePercent);
                detail.setWeightLossPercentage(weightLossPercent);
                detail.setCostPerUnit(detailDto.getCostPerUnit());
                detail.setTotalAmount(detailDto.getTotalAmount());
                detail.setBrushedGrossWt(detailDto.getBrushedGrossWt());
                detail.setTc(detailDto.getTc());
                detail.setWeave(detailDto.getWeave());
                detail.setDate(detailDto.getDate());
                detail.setPartyName(detailDto.getPartyName());
                detail.setInvoiceNo(detailDto.getInvoiceNo());
                detail.setConstruction(detailDto.getConstruction());
                detail.setGlm(detailDto.getGlm());
                detail.setVendorId(detailDto.getVendorId());
                detail.setFabricMasterId(detailDto.getFabricMasterId());
                BigDecimal passMtrVal = detailDto.getPassMtr();
                if (brushedMeters != null && brushedMeters.compareTo(BigDecimal.ZERO) > 0) {
                    passMtrVal = brushedMeters;
                }
                detail.setPassMtr(passMtrVal);

                if (detailDto.getUnwindedDate() != null) {
                    detail.setUnwindedDate(detailDto.getUnwindedDate());
                }

                if (detailDto.getUnwindStatus() != null) {
                    String newUnwindStatus = detailDto.getUnwindStatus();
                    if (!newUnwindStatus.equals(detail.getUnwindStatus())) {
                        detail.setUnwindStatus(newUnwindStatus);
                        detail.setUnwindUpdatedAt(java.time.LocalDateTime.now());
                        if (detailDto.getUnwindUpdatedBy() != null && !detailDto.getUnwindUpdatedBy().isEmpty()) {
                            detail.setUnwindUpdatedBy(detailDto.getUnwindUpdatedBy());
                        }
                    }
                    if ("REJECTED".equalsIgnoreCase(newUnwindStatus)) {
                        detail.setIsRejected(1);
                        detail.setIsReturn(0);
                        if (detail.getRejectionDate() == null) {
                            detail.setRejectionDate(detailDto.getRejectionDate() != null ? detailDto.getRejectionDate() : java.time.LocalDate.now());
                        }
                        detail.setRejectedReason(detailDto.getRejectedReason() != null ? detailDto.getRejectedReason() : detail.getRejectedReason());
                        if (detail.getHfplDcNo() == null || detail.getHfplDcNo().isEmpty()) {
                            detail.setHfplDcNo(generateNextHfplDcNo());
                        }
                        detail.setDcNo(detailDto.getDcNo() != null ? detailDto.getDcNo() : detail.getDcNo());
                        detail.setReturnType(detailDto.getReturnType() != null ? detailDto.getReturnType() : detail.getReturnType());
                    } else if ("RETURN".equalsIgnoreCase(newUnwindStatus)) {
                        detail.setIsReturn(1);
                        detail.setIsRejected(0);
                        if (detail.getReturnDate() == null) {
                            detail.setReturnDate(detailDto.getReturnDate() != null ? detailDto.getReturnDate() : (detailDto.getRejectionDate() != null ? detailDto.getRejectionDate() : java.time.LocalDate.now()));
                        }
                        detail.setReturnReason(detailDto.getReturnReason() != null ? detailDto.getReturnReason() : (detailDto.getRejectedReason() != null ? detailDto.getRejectedReason() : detail.getReturnReason()));
                        if (detail.getHfplDcNo() == null || detail.getHfplDcNo().isEmpty()) {
                            detail.setHfplDcNo(generateNextHfplDcNo());
                        }
                        detail.setDcNo(detailDto.getDcNo() != null ? detailDto.getDcNo() : detail.getDcNo());
                        detail.setReturnType(detailDto.getReturnType() != null ? detailDto.getReturnType() : detail.getReturnType());
                    } else {
                        detail.setIsRejected(0);
                        detail.setIsReturn(0);
                        detail.setRejectionDate(null);
                        detail.setRejectedReason(null);
                        detail.setReturnDate(null);
                        detail.setReturnReason(null);
                        detail.setHfplDcNo(null);
                        detail.setDcNo(null);
                        detail.setReturnType(null);
                    }
                }

                // Update brushing process status and fields from the DTO
                detail.setIsBrushed(detailDto.getIsBrushed() != null ? detailDto.getIsBrushed() : 0);
                detail.setBrushingMachineNo(detailDto.getBrushingMachineNo());
                detail.setMachineId(detailDto.getMachineId());
                
                String newBrushingStatus = detailDto.getBrushingStatus() != null ? detailDto.getBrushingStatus() : "PENDING";
                if (!newBrushingStatus.equals(detail.getBrushingStatus())) {
                    detail.setBrushingStatus(newBrushingStatus);
                    detail.setBrushingUpdatedAt(java.time.LocalDateTime.now());
                    if (detailDto.getBrushingUpdatedBy() != null && !detailDto.getBrushingUpdatedBy().isEmpty()) {
                        detail.setBrushingUpdatedBy(detailDto.getBrushingUpdatedBy());
                    }
                } else {
                    detail.setBrushingStatus(newBrushingStatus);
                }
                if (detail.getIsBrushed() == 1 || "READY_FOR_DISPATCH".equalsIgnoreCase(detail.getBrushingStatus())) {
                    detail.setBrushingDate(detailDto.getBrushingDate() != null ? detailDto.getBrushingDate() : java.time.LocalDate.now());
                    detail.setIsPacked(1);
                    if (detailDto.getHfplRollNo() != null && !detailDto.getHfplRollNo().trim().isEmpty()) {
                        detail.setHfplRollNo(detailDto.getHfplRollNo().trim());
                    } else if (detail.getHfplRollNo() == null || detail.getHfplRollNo().isEmpty()) {
                        detail.setHfplRollNo(generateNextHfplRollNo(statusRollNoOffset2));
                        statusRollNoOffset2++;
                    }
                    if (detail.getRollingDate() == null) {
                        detail.setRollingDate(java.time.LocalDate.now());
                    }
                } else {
                    detail.setBrushingDate(detailDto.getBrushingDate());
                    detail.setIsPacked(0);
                    if (detailDto.getHfplRollNo() != null && !detailDto.getHfplRollNo().trim().isEmpty()) {
                        detail.setHfplRollNo(detailDto.getHfplRollNo().trim());
                    }
                    detail.setRollingDate(null);
                }

                brushingReceiveDetailRepository.save(detail);

                totalPassMtr = totalPassMtr.add(brushedMeters);
            }
        } else if (receive.getDetails() != null) {
            for (BrushingReceiveDetail detail : receive.getDetails()) {
                BigDecimal bMtr = detail.getBrushedMeters();
                if (bMtr != null) {
                    totalPassMtr = totalPassMtr.add(bMtr);
                }
            }
        }

        BigDecimal totalGreigeMeters = BigDecimal.ZERO;
        if (receive.getDetails() != null) {
            for (BrushingReceiveDetail detail : receive.getDetails()) {
                BigDecimal greigeMeters = detail.getGreigeMeters();
                if (greigeMeters != null) {
                    totalGreigeMeters = totalGreigeMeters.add(greigeMeters);
                }
            }
        }

        receive.setPassMtr(totalGreigeMeters);
        BrushingReceive saved = brushingReceiveRepository.saveAndFlush(receive);
        return toBrushingReceiveDTO(saved);
    }

    public String generateNextHfplRollNo() {
        return generateNextHfplRollNo(0);
    }

    public String generateNextHfplRollNo(int offset) {
        java.time.LocalDate now = java.time.LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int startYear = (month >= 4) ? year : year - 1;
        int endYear = startYear + 1;
        String fiscalYearPart = String.format("%02d-%02d", startYear % 100, endYear % 100);
        
        String prefix = "HFPLB/R/";
        String suffix = "/" + fiscalYearPart;
        
        // 1. Try to find the highest sequence from new format 'HFPLB/R/[sequence]/[financialYear]'
        String maxRollNoNewFormat = brushingReceiveDetailRepository.findMaxHfplRollNoByPrefix(prefix);
        int nextSeq = 8303; // Default starting sequence as a fallback
        
        if (maxRollNoNewFormat != null && maxRollNoNewFormat.startsWith(prefix)) {
            try {
                String rest = maxRollNoNewFormat.substring(prefix.length()).trim();
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^(\\d+)");
                java.util.regex.Matcher matcher = pattern.matcher(rest);
                if (matcher.find()) {
                    nextSeq = Integer.parseInt(matcher.group(1)) + 1;
                } else {
                    int slashIdx = rest.indexOf('/');
                    if (slashIdx > 0) {
                        String seqStr = rest.substring(0, slashIdx).trim();
                        nextSeq = Integer.parseInt(seqStr) + 1;
                    } else {
                        nextSeq = Integer.parseInt(rest) + 1;
                    }
                }
                return prefix + (nextSeq + offset) + suffix;
            } catch (Exception e) {
                // Ignore and try other fallbacks
            }
        }
        
        // 2. If no new format matches, check if there are raw numeric roll numbers (e.g. "8302")
        try {
            String rawMax = brushingReceiveDetailRepository.findMaxHfplRollNoByPrefix(""); // Get overall max hfplRollNo
            if (rawMax != null) {
                if (rawMax.matches("\\d+")) {
                    nextSeq = Integer.parseInt(rawMax) + 1;
                } else if (!rawMax.startsWith("HFPL")) {
                    // Try to parse sequence numbers in other custom strings
                    java.util.regex.Pattern p = java.util.regex.Pattern.compile("(\\d+)");
                    java.util.regex.Matcher m = p.matcher(rawMax);
                    int highestParsed = 0;
                    while (m.find()) {
                        try {
                            int val = Integer.parseInt(m.group(1));
                            if (val > highestParsed && val < 100000) {
                                highestParsed = val;
                            }
                        } catch (Exception ex) {}
                    }
                    if (highestParsed > 0) {
                        nextSeq = highestParsed + 1;
                    }
                }
            }
        } catch (Exception e) {
            // Fallback to default
        }
        
        return prefix + (nextSeq + offset) + suffix;
    }
    private String generateNextHfplDcNo() {
        java.time.LocalDate now = java.time.LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int startYear = (month >= 4) ? year : year - 1;
        int endYear = startYear + 1;
        String fiscalYearPart = String.format("%02d-%02d", startYear % 100, endYear % 100);
        String prefix = "HFPL/" + fiscalYearPart + "/DC";
        
        String maxDcNo = brushingReceiveDetailRepository.findMaxHfplDcNoByPrefix(prefix);
        int nextSeq = 1;
        if (maxDcNo != null && maxDcNo.startsWith(prefix)) {
            try {
                String seqStr = maxDcNo.substring(prefix.length());
                nextSeq = Integer.parseInt(seqStr) + 1;
            } catch (Exception e) {
                // Ignore
            }
        }
        return prefix + String.format("%03d", nextSeq);
    }

    public List<BrushingStockReportDTO> getBrushingStockReport(Long companyId) {
        return getBrushingStockReport(companyId, null);
    }

    public List<BrushingStockReportDTO> getBrushingStockReport(Long companyId, String status) {
        List<Object[]> rows = brushingReceiveRepository.getBrushingDailyMovements(companyId, status);
        List<BrushingStockReportDTO> report = new ArrayList<>();
        
        java.util.Map<Long, String> fabricCodeMap = wovenFabricMasterRepository.findAll().stream()
            .filter(f -> f.getId() != null && f.getFabricCode() != null)
            .collect(Collectors.toMap(WovenFabricMaster::getId, WovenFabricMaster::getFabricCode, (a, b) -> a));

        java.util.Map<String, BigDecimal> runningBalances = new java.util.HashMap<>();

        for (Object[] row : rows) {
            LocalDate date = null;
            if (row[0] instanceof java.sql.Date) {
                date = ((java.sql.Date) row[0]).toLocalDate();
            } else if (row[0] instanceof java.time.LocalDate) {
                date = (java.time.LocalDate) row[0];
            } else if (row[0] instanceof java.util.Date) {
                date = new java.sql.Date(((java.util.Date) row[0]).getTime()).toLocalDate();
            }

            Long fabricMasterId = row[1] != null ? ((Number) row[1]).longValue() : null;
            String construction = row[2] != null ? row[2].toString().trim() : "";
            BigDecimal received = toBigDecimal(row[3]);
            BigDecimal returned = toBigDecimal(row[4]);
            BigDecimal dispatch = toBigDecimal(row[5]);
            BigDecimal excess   = toBigDecimal(row[6]);
            BigDecimal shortage = toBigDecimal(row[7]);

            String key = (fabricMasterId != null ? fabricMasterId.toString() : "null") + "_" + construction;
            BigDecimal running = runningBalances.getOrDefault(key, BigDecimal.ZERO);
            BigDecimal opening = running;
            BigDecimal closing = opening.add(received).add(excess).subtract(returned).subtract(dispatch).subtract(shortage);
            if (closing.compareTo(BigDecimal.ZERO) < 0) {
                closing = BigDecimal.ZERO;
            }
            runningBalances.put(key, closing);

            String sortNo = fabricMasterId != null ? fabricCodeMap.getOrDefault(fabricMasterId, "") : "";

            BrushingStockReportDTO dto = new BrushingStockReportDTO(
                date,
                opening.setScale(3, RoundingMode.HALF_UP),
                received.setScale(3, RoundingMode.HALF_UP),
                returned.setScale(3, RoundingMode.HALF_UP),
                dispatch.setScale(3, RoundingMode.HALF_UP),
                excess.setScale(3, RoundingMode.HALF_UP),
                shortage.setScale(3, RoundingMode.HALF_UP),
                closing.setScale(3, RoundingMode.HALF_UP)
            );
            dto.setFabricMasterId(fabricMasterId);
            dto.setSortNo(sortNo);
            dto.setConstruction(construction);
            report.add(dto);
        }

        return report;
    }

    private BigDecimal toBigDecimal(Object val) {
        if (val == null) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(val.toString());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    public List<BrushingUnitFabricStatusDTO> getBrushingUnitFabricStatusReport(Long companyId, LocalDate targetDate, String constructionFilter) {
        List<Object[]> rows = brushingReceiveDetailRepository.getBrushingUnitFabricStatus(companyId, targetDate);
        List<BrushingUnitFabricStatusDTO> report = new ArrayList<>();
        int sNo = 1;

        for (Object[] row : rows) {
            String construction = row[0] != null ? row[0].toString().trim() : "";
            Long fabricMasterId = row[1] != null ? ((Number) row[1]).longValue() : null;
            BigDecimal brushedStock = toBigDecimal(row[2]).setScale(3, RoundingMode.HALF_UP);
            BigDecimal waitingStock = toBigDecimal(row[3]).setScale(3, RoundingMode.HALF_UP);
            BigDecimal rejectedStock = row.length > 4 ? toBigDecimal(row[4]).setScale(3, RoundingMode.HALF_UP) : BigDecimal.ZERO;

            if (constructionFilter != null && !constructionFilter.trim().isEmpty() && !"ALL".equalsIgnoreCase(constructionFilter.trim())) {
                if (!construction.equalsIgnoreCase(constructionFilter.trim())) {
                    continue;
                }
            } else {
                // Filter out rows where Brushed Fabric Stock, Waiting for Brushing, and Rejected Fabric are all 0
                if (brushedStock.compareTo(BigDecimal.ZERO) == 0 && waitingStock.compareTo(BigDecimal.ZERO) == 0 && rejectedStock.compareTo(BigDecimal.ZERO) == 0) {
                    continue;
                }
            }

            BrushingUnitFabricStatusDTO dto = new BrushingUnitFabricStatusDTO(construction, fabricMasterId, brushedStock, waitingStock, rejectedStock);
            dto.setsNo(sNo++);
            report.add(dto);
        }

        return report;
    }
}
