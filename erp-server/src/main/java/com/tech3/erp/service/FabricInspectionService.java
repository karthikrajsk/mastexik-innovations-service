package com.tech3.erp.service;

import com.tech3.erp.dto.FabricInspectionDTO;
import com.tech3.erp.dto.BaleRollPackingDTO;
import com.tech3.erp.dto.BaleRollPackingItemDTO;
import com.tech3.erp.service.BaleRollPackingService;
import com.tech3.erp.dto.InspectionDetailDTO;
import com.tech3.erp.dto.InspectionEntryDTO;
import com.tech3.erp.dto.PieceEntryResponseDTO;
import com.tech3.erp.entity.FabricInspection;
import com.tech3.erp.entity.FabricItemEntity;
import com.tech3.erp.entity.GradeMaster;
import com.tech3.erp.entity.InspectionDetail;
import com.tech3.erp.entity.InspectionEntry;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.entity.WovenFabricMaster;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.exception.ApiException;
import com.tech3.erp.repository.FabricInspectionRepository;
import com.tech3.erp.repository.FabricItemRepository;
import com.tech3.erp.repository.GradeMasterRepository;
import com.tech3.erp.repository.InspectionDetailRepository;
import com.tech3.erp.repository.InspectionEntryRepository;
import com.tech3.erp.repository.OpeningBalanceRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.WovenFabricMasterRepository;
import com.tech3.erp.repository.SalesOrderRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class FabricInspectionService {

    @Autowired
    private FabricInspectionRepository fabricInspectionRepository;
    
    @Autowired
    private FabricItemRepository fabricItemRepository;
    
    @Autowired
    private InspectionDetailRepository inspectionDetailRepository;
    
    @Autowired
    private InspectionEntryRepository inspectionEntryRepository;
    
    @Autowired
    private PieceEntryRepository pieceEntryRepository;
    
    @Autowired
    private OpeningBalanceRepository openingBalanceRepository;
    
    @Autowired
    private GradeMasterRepository gradeMasterRepository;
    
    @Autowired
    private WeavingWorkOrderRepository weavingWorkOrderRepository;

    @Autowired
    private WovenFabricMasterRepository wovenFabricMasterRepository;

    @Autowired
    private WeavingWorkOrderService weavingWorkOrderService;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Autowired
    private BaleRollPackingService baleRollPackingService;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Transactional
    public FabricInspection createFabricInspection(FabricInspectionDTO dto) {
        FabricInspection inspection = new FabricInspection();
        inspection.setInspectionDate(dto.getInspectionDate() != null ? dto.getInspectionDate() : LocalDate.now());
        inspection.setLoomNo(dto.getLoomNo());
        inspection.setVendorId(dto.getVendorId());
        inspection.setInspectorId(dto.getInspectorId());
        inspection.setCompanyId(dto.getCompanyId());
        inspection.setIsFactoryFabric(dto.getIsFactoryFabric());
        inspection.setFabricQuality(dto.getFabricQuality());
        inspection.setPieceMeters(dto.getPieceMeters());
        inspection.setNetWeight(dto.getNetWeight());
        inspection.setActiveFlag(true);
        
        // Set source tracking fields
        inspection.setSourceType(dto.getSourceType());
        inspection.setPurchaseInwardId(dto.getPurchaseInwardId());
        inspection.setJobworkFabricReceiveId(dto.getJobworkFabricReceiveId());
        inspection.setBrushingReceiveId(dto.getBrushingReceiveId());
        inspection.setPieceNo(dto.getPieceNo());
        inspection.setFabricCode(dto.getFabricCode());
        inspection.setFabricTypeId(dto.getFabricTypeId());

        // New IDs and flags
        inspection.setLoomId(dto.getLoomId());
        inspection.setFlangeId(dto.getFlangeId());
        inspection.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
        inspection.setBuyerId(dto.getBuyerId());
        inspection.setFabricMasterId(dto.getFabricMasterId());
        inspection.setBeamNo(dto.getBeamNo());
        inspection.setIsNoDefectedPieces(dto.getIsNoDefectedPieces() != null ? dto.getIsNoDefectedPieces() : 0);
        inspection.setOrderNo(dto.getOrderNo());
        inspection.setContractNo(dto.getContractNo());
        inspection.setTotalPoints(dto.getTotalPoints());
        inspection.setPointsPer100Mtr(dto.getPointsPer100Mtr());

        inspection.setDoffDate(dto.getDoffDate());
        inspection.setPoNo(dto.getPoNo());
        inspection.setWarpLotNo(dto.getWarpLotNo());
        inspection.setWeftLotNo(dto.getWeftLotNo());
        inspection.setWarpSetNo(dto.getWarpSetNo());
        inspection.setSelvedgeThread(dto.getSelvedgeThread());
        inspection.setWarpCount(dto.getWarpCount());
        inspection.setWeftCount(dto.getWeftCount());
        inspection.setSelvedgeWidthPs(dto.getSelvedgeWidthPs());
        inspection.setSelvedgeWidthRs(dto.getSelvedgeWidthRs());
        inspection.setFringeWidthPs(dto.getFringeWidthPs());
        inspection.setFringeWidthRs(dto.getFringeWidthRs());
        inspection.setWeave(dto.getWeave());
        inspection.setEpi(dto.getEpi());
        inspection.setPpi(dto.getPpi());
        inspection.setWidth(dto.getWidth());
        inspection.setInspectedEpi(dto.getInspectedEpi());
        inspection.setInspectedPpi(dto.getInspectedPpi());
        inspection.setInspectedWidth(dto.getInspectedWidth());
        inspection.setBuyerInspectorId(dto.getBuyerInspectorId());
        inspection.setBuyerInspector(dto.getBuyerInspector());
        if (dto.getBuyerInspectorName() != null) {
            inspection.setBuyerInspectorName(dto.getBuyerInspectorName());
        }
        inspection.setPackLaterReason(dto.getPackLaterReason());
        inspection.setRejectedReason(dto.getRejectedReason());
        
        WeavingWorkOrder weavingWorkOrder = requireWeavingWorkOrder(dto.getWeavingWorkOrderId());
        if (weavingWorkOrder != null) {
            inspection.setOrderTypeId(weavingWorkOrder.getOrderTypeId());
        } else if (dto.getOrderTypeId() != null) {
            inspection.setOrderTypeId(dto.getOrderTypeId());
        }

        FabricInspection savedInspection = fabricInspectionRepository.save(inspection);

        if (dto.getInspectionDetails() != null && !dto.getInspectionDetails().isEmpty()) {
            List<InspectionDetail> details = dto.getInspectionDetails().stream()
                    .map(detailDto -> {
                        InspectionDetail detail = new InspectionDetail();
                        detail.setFabricInspection(savedInspection);
                        
                        String pieceNo = detailDto.getPieceNo();
                        if (pieceNo == null || pieceNo.trim().isEmpty()) {
                            throw new IllegalArgumentException("Piece No is required for inspection details");
                        }
                        detail.setPieceNo(pieceNo);
                        
                        detail.setPieceMeters(detailDto.getPieceMeters());
                        detail.setRollDoffMeters(detailDto.getRollDoffMeters());
                        detail.setInspectedMeters(detailDto.getInspectedMeters());
                        detail.setActualNetWeight(detailDto.getActualNetWeight());
                        
                        // Calculate avg weight (using pieceMeters or falling back to rollDoffMeters)
                        Double meterForAvg = (detailDto.getPieceMeters() != null && detailDto.getPieceMeters() > 0)
                                ? detailDto.getPieceMeters()
                                : detailDto.getRollDoffMeters();
                        if (meterForAvg != null && meterForAvg > 0 && detailDto.getActualNetWeight() != null) {
                            detail.setAvgWeight(detailDto.getActualNetWeight() / meterForAvg);
                        }
                        
                        detail.setTotalDefectPoints(detailDto.getTotalDefectPoints());
                        detail.setTotalPoints(detailDto.getTotalPoints());
                        detail.setPointsPer100Mtr(detailDto.getPointsPer100Mtr());
                        detail.setDefectCounts(detailDto.getDefectCounts());
                              // CRITICAL FIX: Properly set gradeId
                        // Long gradeId = detailDto.getGradeId();
                        // if (gradeId == null && detailDto.getGrade() != null) {
                        //     // Fallback: Try to get gradeId from grade name if not provided
                        //     gradeId = getGradeIdFromGradeName(detailDto.getGrade());
                        // }
                        // detail.setGradeId(gradeId);
                        detail.setGradeId(detailDto.getGradeId());
                        detail.setActiveFlag(true);
                        return detail;
                    })
                    .collect(Collectors.toList());
            
            inspectionDetailRepository.saveAll(details);
            savedInspection.setInspectionDetails(details);
            
            // Link first matching piece entry id for tracing
            try {
                List<String> pieceNumbers = dto.getInspectionDetails().stream()
                        .map(InspectionDetailDTO::getPieceNo)
                        .filter(pn -> pn != null && !pn.isEmpty())
                        .collect(Collectors.toList());
                requirePieceEntries(pieceNumbers);
                if (!pieceNumbers.isEmpty()) {
                    List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
                    if (!pieces.isEmpty()) {
                        savedInspection.setPieceEntryId(pieces.get(0).getId());
                        savedInspection.setIsInspected(pieces.get(0).getIsInspected());
                        fabricInspectionRepository.save(savedInspection);
                    }
                }
            } catch (ApiException ex) {
                throw ex;
            } catch (Exception ignore) { }
            
            // Mark pieces as inspected with grade ID lookup
            markPiecesAsInspected(savedInspection.getId(), dto.getInspectionDetails(), dto.getInspectionEntries(), dto.getInspectorId(), dto.getCompanyId(), dto.getPackingOption(), dto.getRejectedReason(), dto.getPackLaterReason());
            try {
                savedInspection.setIsInspected(1);
                fabricInspectionRepository.save(savedInspection);
            } catch (Exception ignore) { }

        }

        if (dto.getInspectionEntries() != null && !dto.getInspectionEntries().isEmpty()) {
            List<InspectionEntry> entries = dto.getInspectionEntries().stream()
                    .map(entryDto -> {
                        InspectionEntry entry = new InspectionEntry();
                        entry.setFabricInspection(savedInspection);
                        entry.setDefectedMeters(entryDto.getDefectedMeters());
                        entry.setFromMeters(entryDto.getFromMeters());
                        entry.setToMeters(entryDto.getToMeters());
                        entry.setDefectTypeId(entryDto.getDefectTypeId());
                        entry.setDefectPoints(entryDto.getDefectPoints());
                        entry.setInspectionId(entryDto.getInspectionId());
                        entry.setActiveFlag(true);
                        return entry;
                    })
                    .collect(Collectors.toList());
            
            inspectionEntryRepository.saveAll(entries);
            savedInspection.setInspectionEntries(entries);
        }

        createImmediatePacking(savedInspection, dto);

        return savedInspection;
    }
    
    private List<PieceEntryResponseDTO> getPieceEntriesByInspectionId(Long inspectionId) {
        if (inspectionId == null) {
            return List.of();
        }
        
        List<PieceEntry> pieces = pieceEntryRepository.findByInspectionId(inspectionId);
        return pieces.stream()
                .map(PieceEntryResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public FabricInspection updateFabricInspection(Long id, FabricInspectionDTO dto) {
        FabricInspection inspection = fabricInspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FabricInspection not found with id: " + id));
        
        updateIfPresent(inspection::setInspectionDate, dto.getInspectionDate());
        inspection.setLoomNo(dto.getLoomNo());
        updateIfPresent(inspection::setVendorId, dto.getVendorId());
        updateIfPresent(inspection::setInspectorId, dto.getInspectorId());
        updateIfPresent(inspection::setCompanyId, dto.getCompanyId());
        updateIfPresent(inspection::setIsFactoryFabric, dto.getIsFactoryFabric());
        updateIfHasText(inspection::setFabricQuality, dto.getFabricQuality());
        updateIfPresent(inspection::setPieceMeters, dto.getPieceMeters());
        updateIfPresent(inspection::setNetWeight, dto.getNetWeight());
        updateIfPresent(inspection::setActiveFlag, dto.getActiveFlag());
        
        updateIfHasText(inspection::setSourceType, dto.getSourceType());
        updateIfPresent(inspection::setPurchaseInwardId, dto.getPurchaseInwardId());
        updateIfPresent(inspection::setJobworkFabricReceiveId, dto.getJobworkFabricReceiveId());
        updateIfPresent(inspection::setBrushingReceiveId, dto.getBrushingReceiveId());
        updateIfHasText(inspection::setPieceNo, dto.getPieceNo());
        updateIfHasText(inspection::setFabricCode, dto.getFabricCode());
        updateIfPresent(inspection::setFabricTypeId, dto.getFabricTypeId());

        updateIfPresent(inspection::setLoomId, dto.getLoomId());
        updateIfPresent(inspection::setFlangeId, dto.getFlangeId());
        updateIfPresent(inspection::setWeavingWorkOrderId, dto.getWeavingWorkOrderId());
        updateIfPresent(inspection::setBuyerId, dto.getBuyerId());
        updateIfPresent(inspection::setFabricMasterId, dto.getFabricMasterId());
        updateIfHasText(inspection::setBeamNo, dto.getBeamNo());
        updateIfPresent(inspection::setIsNoDefectedPieces, dto.getIsNoDefectedPieces());
        updateIfHasText(inspection::setOrderNo, dto.getOrderNo());
        updateIfHasText(inspection::setContractNo, dto.getContractNo());
        updateIfPresent(inspection::setTotalPoints, dto.getTotalPoints());
        updateIfPresent(inspection::setPointsPer100Mtr, dto.getPointsPer100Mtr());

        updateIfPresent(inspection::setDoffDate, dto.getDoffDate());
        updateIfHasText(inspection::setPoNo, dto.getPoNo());
        updateIfHasText(inspection::setWarpLotNo, dto.getWarpLotNo());
        updateIfHasText(inspection::setWeftLotNo, dto.getWeftLotNo());
        updateIfHasText(inspection::setWarpSetNo, dto.getWarpSetNo());
        updateIfHasText(inspection::setSelvedgeThread, dto.getSelvedgeThread());
        updateIfHasText(inspection::setWarpCount, dto.getWarpCount());
        updateIfHasText(inspection::setWeftCount, dto.getWeftCount());
        updateIfPresent(inspection::setSelvedgeWidthPs, dto.getSelvedgeWidthPs());
        updateIfPresent(inspection::setSelvedgeWidthRs, dto.getSelvedgeWidthRs());
        updateIfPresent(inspection::setFringeWidthPs, dto.getFringeWidthPs());
        updateIfPresent(inspection::setFringeWidthRs, dto.getFringeWidthRs());
        updateIfHasText(inspection::setWeave, dto.getWeave());
        updateIfHasText(inspection::setEpi, dto.getEpi());
        updateIfHasText(inspection::setPpi, dto.getPpi());
        updateIfHasText(inspection::setWidth, dto.getWidth());
        updateIfHasText(inspection::setInspectedEpi, dto.getInspectedEpi());
        updateIfHasText(inspection::setInspectedPpi, dto.getInspectedPpi());
        updateIfHasText(inspection::setInspectedWidth, dto.getInspectedWidth());
        updateIfPresent(inspection::setBuyerInspectorId, dto.getBuyerInspectorId());
        updateIfHasText(inspection::setBuyerInspector, dto.getBuyerInspector());
        if (dto.getBuyerInspectorName() != null) {
            inspection.setBuyerInspectorName(dto.getBuyerInspectorName());
        }
        inspection.setPackLaterReason(dto.getPackLaterReason());
        inspection.setRejectedReason(dto.getRejectedReason());
        
        Long weavingWorkOrderId = dto.getWeavingWorkOrderId();
        if (weavingWorkOrderId != null) {
            WeavingWorkOrder weavingWorkOrder = requireWeavingWorkOrder(weavingWorkOrderId);
            if (weavingWorkOrder != null) {
                inspection.setOrderTypeId(weavingWorkOrder.getOrderTypeId());
            }
        } else {
            updateIfPresent(inspection::setOrderTypeId, dto.getOrderTypeId());
        }

        boolean hasNewDetails = dto.getInspectionDetails() != null && !dto.getInspectionDetails().isEmpty();
        boolean hasNewEntries = dto.getInspectionEntries() != null && !dto.getInspectionEntries().isEmpty();

        if (hasNewDetails) {
            if (inspection.getInspectionDetails() != null) {
                inspection.getInspectionDetails().clear();
            }
            final FabricInspection resolvedInspection = inspection;
            List<InspectionDetail> details = dto.getInspectionDetails().stream()
                    .map(detailDto -> {
                        InspectionDetail detail = new InspectionDetail();
                        detail.setFabricInspection(resolvedInspection);
                        detail.setPieceNo(detailDto.getPieceNo());
                        detail.setPieceMeters(detailDto.getPieceMeters());
                        detail.setRollDoffMeters(detailDto.getRollDoffMeters());
                        detail.setInspectedMeters(detailDto.getInspectedMeters());
                        detail.setActualNetWeight(detailDto.getActualNetWeight());
                        Double meterForAvg = (detailDto.getPieceMeters() != null && detailDto.getPieceMeters() > 0)
                                ? detailDto.getPieceMeters()
                                : detailDto.getRollDoffMeters();
                        if (meterForAvg != null && meterForAvg > 0 && detailDto.getActualNetWeight() != null) {
                            detail.setAvgWeight(detailDto.getActualNetWeight() / meterForAvg);
                        }
                        detail.setTotalDefectPoints(detailDto.getTotalDefectPoints());
                        detail.setTotalPoints(detailDto.getTotalPoints());
                        detail.setPointsPer100Mtr(detailDto.getPointsPer100Mtr());
                        detail.setDefectCounts(detailDto.getDefectCounts());
                        // // detail.setGradeId(detailDto.getGradeId());
                        //    Long gradeId = detailDto.getGradeId();
                        // if (gradeId == null && detailDto.getGrade() != null) {
                        //     // Fallback: Try to get gradeId from grade name if not provided
                        //     gradeId = getGradeIdFromGradeName(detailDto.getGrade());
                        // }
                        // detail.setGradeId(gradeId);
                         detail.setGradeId(detailDto.getGradeId());
                        detail.setActiveFlag(true);
                        return detail;
                    })
                    .collect(Collectors.toList());
            if (inspection.getInspectionDetails() == null) {
                inspection.setInspectionDetails(new java.util.ArrayList<>());
            }
            inspection.getInspectionDetails().addAll(details);
            
            try {
                List<String> pieceNumbers = dto.getInspectionDetails().stream()
                        .map(InspectionDetailDTO::getPieceNo)
                        .filter(pn -> pn != null && !pn.isEmpty())
                        .collect(Collectors.toList());
                requirePieceEntries(pieceNumbers);
                if (!pieceNumbers.isEmpty()) {
                    List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
                    if (!pieces.isEmpty()) {
                        inspection.setPieceEntryId(pieces.get(0).getId());
                        inspection.setIsInspected(pieces.get(0).getIsInspected());
                    }
                }
            } catch (ApiException ex) {
                throw ex;
            } catch (Exception ignore) { }
            
            markPiecesAsInspected(inspection.getId(), dto.getInspectionDetails(), dto.getInspectionEntries(), dto.getInspectorId(), dto.getCompanyId(), dto.getPackingOption(), dto.getRejectedReason(), dto.getPackLaterReason());
            try {
                inspection.setIsInspected(1);
            } catch (Exception ignore) { }
        }

        if (hasNewEntries) {
            if (inspection.getInspectionEntries() != null) {
                inspection.getInspectionEntries().clear();
            }
            final FabricInspection resolvedInspection = inspection;
            List<InspectionEntry> entries = dto.getInspectionEntries().stream()
                    .map(entryDto -> {
                        InspectionEntry entry = new InspectionEntry();
                        entry.setFabricInspection(resolvedInspection);
                        entry.setDefectedMeters(entryDto.getDefectedMeters());
                        entry.setFromMeters(entryDto.getFromMeters());
                        entry.setToMeters(entryDto.getToMeters());
                        entry.setDefectTypeId(entryDto.getDefectTypeId());
                        entry.setDefectPoints(entryDto.getDefectPoints());
                        entry.setInspectionId(entryDto.getInspectionId());
                        entry.setActiveFlag(true);
                        return entry;
                    })
                    .collect(Collectors.toList());
            if (inspection.getInspectionEntries() == null) {
                inspection.setInspectionEntries(new java.util.ArrayList<>());
            }
            inspection.getInspectionEntries().addAll(entries);
        }

        FabricInspection savedInspection = fabricInspectionRepository.save(inspection);
        
        syncFabricInspectionToPieceEntry(savedInspection.getId());
        markWeavingWorkOrderReference(savedInspection.getWeavingWorkOrderId());
        
        createImmediatePacking(savedInspection, dto);
        
        return savedInspection;
    }

    /**
     * NEW METHOD: Lookup Grade ID from Grade Name
     * Searches GradeMaster by grade name and returns the ID
     */
    private Long getGradeIdFromGradeName(String gradeName) {
        if (gradeName == null || gradeName.trim().isEmpty()) {
            return null;
        }
        
        // Find grade by name (case-insensitive)
        Optional<GradeMaster> gradeOpt = gradeMasterRepository.findAll().stream()
                .filter(g -> g.getGradeName() != null && 
                            g.getGradeName().equalsIgnoreCase(gradeName.trim()))
                .findFirst();
        
        return gradeOpt.map(GradeMaster::getId).orElse(null);
    }

    private WeavingWorkOrder requireWeavingWorkOrder(Long weavingWorkOrderId) {
        if (weavingWorkOrderId == null) {
            return null;
        }
        Optional<WeavingWorkOrder> workOrderOpt = weavingWorkOrderRepository.findById(weavingWorkOrderId);
        return referenceValidationService.requireReference(
                weavingWorkOrderId,
                () -> workOrderOpt,
                "Weaving Work Order",
                workOrderOpt.map(WeavingWorkOrder::getWeavingWorkOrderNo).filter(StringUtils::hasText).orElse(null));
    }

    private void requirePieceEntries(List<String> pieceNumbers) {
        if (pieceNumbers == null || pieceNumbers.isEmpty()) {
            return;
        }
        Set<String> existingNumbers = pieceEntryRepository.findByPieceNumberIn(pieceNumbers).stream()
                .map(PieceEntry::getPieceNumber)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());
        for (String pieceNumber : pieceNumbers) {
            if (pieceNumber == null || pieceNumber.trim().isEmpty()) {
                continue;
            }
            if (!existingNumbers.contains(pieceNumber)) {
                throw new ApiException(HttpStatus.CONFLICT, "This " + pieceNumber + " is currently not present in Piece Entry");
            }
        }
    }

    @Transactional
    private void markPiecesAsInspected(Long inspectionId, List<InspectionDetailDTO> inspectionDetails, 
                                      List<InspectionEntryDTO> inspectionEntries, Long inspectorId, Long companyId, String packingOption, String rejectedReason) {
        markPiecesAsInspected(inspectionId, inspectionDetails, inspectionEntries, inspectorId, companyId, packingOption, rejectedReason, null);
    }

    @Transactional
    private void markPiecesAsInspected(Long inspectionId, List<InspectionDetailDTO> inspectionDetails, 
                                      List<InspectionEntryDTO> inspectionEntries, Long inspectorId, Long companyId, String packingOption, String rejectedReason, String packLaterReason) {
        if (inspectionDetails == null || inspectionDetails.isEmpty()) {
            return;
        }
        
        List<String> pieceNumbers = inspectionDetails.stream()
                .map(InspectionDetailDTO::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                .collect(Collectors.toList());
        
        if (pieceNumbers.isEmpty()) {
            return;
        }
        
        List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers)
                .stream()
                .filter(p -> belongsToCompany(p, companyId) || isOpeningBalanceSource(p))
                .collect(Collectors.toList());
        if (pieces.isEmpty()) {
            return;
        }
        
        // Aggregate defect data
        double totalDefectPointsDouble = inspectionEntries != null ? inspectionEntries.stream()
                .mapToDouble(InspectionEntryDTO::getDefectPoints)
                .sum() : 0.0;
        BigDecimal totalDefectPoints = BigDecimal.valueOf(totalDefectPointsDouble);
        String defectType = inspectionEntries != null ? inspectionEntries.stream()
                .map(entry -> entry.getDefectTypeId().toString())
                .collect(Collectors.joining(",")) : "";
        
        // Sequential Piece_No: Get max per inspectionId
        Long maxPieceNoLong = pieceEntryRepository.findMaxPieceNoByInspectionId(inspectionId);
        Integer currentNo = (maxPieceNoLong != null && maxPieceNoLong > 0) ? maxPieceNoLong.intValue() + 1 : 1;
        
        for (int i = 0; i < pieces.size(); i++) {
            PieceEntry piece = pieces.get(i);
            InspectionDetailDTO detail = inspectionDetails.get(i % inspectionDetails.size());
            
            piece.setIsInspected(1);
            piece.setInspectionId(inspectionId);
            piece.setInspectionStatus("COMPLETED");
            if (packingOption != null && packingOption.equalsIgnoreCase("REJECT")) {
                piece.setIsRejected(1);
                piece.setRejectedReason(rejectedReason);
            } else {
                piece.setIsRejected(0);
                piece.setRejectedReason(null);
            }
            if (packingOption != null && packingOption.equalsIgnoreCase("PACK_LATER")) {
                piece.setPackLaterReason(packLaterReason);
            } else {
                piece.setPackLaterReason(null);
            }
           
           Long gradeId = detail.getGradeId();
            if (gradeId == null) {
                String gradeName = detail.getGrade();
                gradeId = getGradeIdFromGradeName(gradeName);
                if (gradeId == null && gradeName != null && !gradeName.trim().isEmpty()) {
                    System.err.println("Warning: Grade not found for name: " + gradeName + " for piece: " + piece.getPieceNumber());
                }
            }
            if (gradeId != null) {
                piece.setGradeId(gradeId);
            }
            
            piece.setInspectedMtr(BigDecimal.valueOf(detail.getInspectedMeters()));
            piece.setInspectedNtWt(BigDecimal.valueOf(detail.getActualNetWeight()));
            piece.setInspectedAvgWt(detail.getAvgWeight() != null ? BigDecimal.valueOf(detail.getAvgWeight()) : null);
            piece.setDefectType(defectType);
            piece.setDefectPoints(totalDefectPoints);
            piece.setPieceNo(currentNo + i);
            piece.setPiecePrefix(piece.getSourceType() != null ? 
                (piece.getPoType() != null && piece.getPoType().toLowerCase().contains("greige") ? "GFP" : "FFP") : "FFP");
            piece.setInspectorId(inspectorId);

            // Update corresponding FabricItemEntity if it's an opening balance piece
            if (isOpeningBalanceSource(piece) && piece.getOpeningBalanceId() != null) {
                List<FabricItemEntity> fabricItems = fabricItemRepository.findByOpeningBalanceId(piece.getOpeningBalanceId());
                for (FabricItemEntity fabricItem : fabricItems) {
                    if (fabricItem.getPieceNo() != null && fabricItem.getPieceNo().equals(piece.getPieceNumber())) {
                        fabricItem.setIsInspected(true);
                        fabricItemRepository.save(fabricItem);
                        System.out.println("✅ Updated FabricItemEntity isInspected status for Opening Balance piece: " + piece.getPieceNumber());
                    }
                }
            }
        }
        
        pieceEntryRepository.saveAll(pieces);
    }

    private void createImmediatePacking(FabricInspection inspection, FabricInspectionDTO dto) {
        if (dto.getPackingOption() != null && dto.getPackingOption().equalsIgnoreCase("PACK_IMMEDIATELY")) {
            BaleRollPackingDTO packingDto = new BaleRollPackingDTO();
            packingDto.setCompanyId(inspection.getCompanyId());
            if (dto.getInspectionDate() != null) {
                packingDto.setPackingDate(java.sql.Date.valueOf(dto.getInspectionDate()));
            } else {
                packingDto.setPackingDate(new java.util.Date());
            }
            packingDto.setPackingType(dto.getPackingType() != null ? dto.getPackingType() : "Roll");
            
            Long buyerId = inspection.getBuyerId();
            Long weavingWorkOrderId = inspection.getWeavingWorkOrderId();
            Long salesOrderId = dto.getSalesOrderId();
            Long orderTypeId = inspection.getOrderTypeId();
            
            if (salesOrderId != null && buyerId == null) {
                Optional<SalesOrder> so = salesOrderRepository.findById(salesOrderId);
                if (so.isPresent()) {
                    buyerId = so.get().getBuyerCustomerId();
                }
            }
            
            // Try to resolve order info from PieceEntry if not set
            if (weavingWorkOrderId == null && salesOrderId == null) {
                if (inspection.getInspectionDetails() != null && !inspection.getInspectionDetails().isEmpty()) {
                    String pieceNo = inspection.getInspectionDetails().get(0).getPieceNo();
                    List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(List.of(pieceNo));
                    if (!pieces.isEmpty()) {
                        PieceEntry piece = pieces.get(0);
                        if (orderTypeId == null) {
                            orderTypeId = piece.getOrderTypeId();
                        }
                        if (piece.getOrderId() != null && piece.getOrderId() > 0) {
                            if (piece.getSourceType() != null && piece.getSourceType().equalsIgnoreCase("jobwork_fabric_receive")) {
                                weavingWorkOrderId = piece.getOrderId();
                                if (buyerId == null) {
                                    Optional<WeavingWorkOrder> wo = weavingWorkOrderRepository.findById(weavingWorkOrderId);
                                    if (wo.isPresent()) {
                                        buyerId = wo.get().getTradingCompanyId() != null ? wo.get().getTradingCompanyId() : wo.get().getBuyerId();
                                    }
                                }
                            } else {
                                salesOrderId = piece.getOrderId();
                                if (buyerId == null) {
                                    Optional<SalesOrder> so = salesOrderRepository.findById(salesOrderId);
                                    if (so.isPresent()) {
                                        buyerId = so.get().getBuyerCustomerId();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            packingDto.setOrderTypeId(orderTypeId);
            packingDto.setBuyerId(buyerId);
            packingDto.setWeavingWorkOrderId(weavingWorkOrderId);
            packingDto.setIsJobwork(weavingWorkOrderId != null && weavingWorkOrderId > 0 ? 1 : 0);
            packingDto.setSalesOrderId(salesOrderId);
            
            packingDto.setFabricCodeId(inspection.getFabricMasterId());
            packingDto.setFabricQuality(inspection.getFabricQuality());
            packingDto.setUomId(dto.getUomId());
            packingDto.setWarehouseId(dto.getWarehouseId());
            
            if (inspection.getInspectionDetails() != null && !inspection.getInspectionDetails().isEmpty()) {
                packingDto.setGradeId(inspection.getInspectionDetails().get(0).getGradeId());
            }
            
            packingDto.setRemarks(dto.getPackingRemarks());
            packingDto.setRollOrBaleNo(dto.getRollOrBaleNo());
            packingDto.setIsFactoryRoll(inspection.getIsFactoryFabric() != null ? inspection.getIsFactoryFabric() : 1);
            
            if (inspection.getInspectionDetails() != null && !inspection.getInspectionDetails().isEmpty()) {
                List<BaleRollPackingItemDTO> itemsList = inspection.getInspectionDetails().stream()
                    .map(detail -> {
                        BaleRollPackingItemDTO itemDto = new BaleRollPackingItemDTO();
                        itemDto.setPieceNo(detail.getPieceNo());
                        
                        double mtr = detail.getInspectedMeters() != null ? detail.getInspectedMeters() : 0.0;
                        itemDto.setMeters(BigDecimal.valueOf(mtr));
                        itemDto.setYards(BigDecimal.valueOf(mtr * 1.094).setScale(3, java.math.RoundingMode.HALF_UP));
                        
                        double wt = detail.getActualNetWeight() != null ? detail.getActualNetWeight() : 0.0;
                        itemDto.setNetWeight(BigDecimal.valueOf(wt));
                        
                        double avg = detail.getAvgWeight() != null ? detail.getAvgWeight() : 0.0;
                        itemDto.setAvgWeight(BigDecimal.valueOf(avg));
                        itemDto.setGrossWeight(BigDecimal.valueOf(wt));
                        
                        return itemDto;
                    }).collect(Collectors.toList());
                
                packingDto.setItems(itemsList);
                packingDto.setTotalPieces((long) itemsList.size());
                BigDecimal totalMeters = itemsList.stream().map(BaleRollPackingItemDTO::getMeters).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalYards = itemsList.stream().map(BaleRollPackingItemDTO::getYards).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalNet = itemsList.stream().map(BaleRollPackingItemDTO::getNetWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalAvg = itemsList.isEmpty() ? BigDecimal.ZERO : totalNet.divide(BigDecimal.valueOf(itemsList.size()), 3, java.math.RoundingMode.HALF_UP);
                
                packingDto.setTotalMeters(totalMeters);
                packingDto.setTotalYards(totalYards);
                packingDto.setTotalNet(totalNet);
                packingDto.setTotalAvg(totalAvg);
                packingDto.setTotalGross(totalNet);
            }
            
            baleRollPackingService.create(packingDto);
        }
    }

    private void markWeavingWorkOrderReference(Long weavingWorkOrderId) {
        if (weavingWorkOrderId != null) {
            weavingWorkOrderService.markAsOrderIdMapped(weavingWorkOrderId);
        }
    }

    private boolean belongsToCompany(PieceEntry piece, Long companyId) {
        return companyId != null && piece.getCompanyId() != null && piece.getCompanyId().equals(companyId);
    }

    private boolean isOpeningBalanceSource(PieceEntry piece) {
        return piece.getSourceType() != null && piece.getSourceType().equalsIgnoreCase("opening_balance");
    }

    private <T> void updateIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private void updateIfHasText(Consumer<String> setter, String value) {
        if (StringUtils.hasText(value)) {
            setter.accept(value);
        }
    }

    @Transactional
    public void updatePackingStatusForPieces(Long packingId, List<String> pieceNumbers) {
        if (pieceNumbers == null || pieceNumbers.isEmpty()) {
            return;
        }
        
        List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
        if (pieces.isEmpty()) {
            throw new EntityNotFoundException("No pieces found with numbers: " + pieceNumbers);
        }
        
        pieces.forEach(piece -> {
            piece.setIsPacked(1);
            piece.setPackingId(packingId);
        });
        pieceEntryRepository.saveAll(pieces);
    }

    @Transactional
    public void updateDispatchStatusForPieces(Long dispatchId, List<String> pieceNumbers) {
        if (pieceNumbers == null || pieceNumbers.isEmpty()) {
            return;
        }
        
        List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
        if (pieces.isEmpty()) {
            throw new EntityNotFoundException("No pieces found with numbers: " + pieceNumbers);
        }
        
        pieces.forEach(piece -> {
            piece.setIsDispatched(1);
            piece.setDispatchId(dispatchId);
        });
        pieceEntryRepository.saveAll(pieces);
    }

    @Transactional
    public void updateInvoiceStatusForPieces(Long invoiceId, List<String> pieceNumbers) {
        List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
        pieces.forEach(piece -> {
            piece.setInvoiceId(invoiceId);
        });
        pieceEntryRepository.saveAll(pieces);
    }

    public List<FabricInspectionDTO> getAllFabricInspections() {
        return fabricInspectionRepository.findAll().stream()
                .sorted(Comparator.comparing(FabricInspection::getId).reversed())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<FabricInspectionDTO> getFabricInspectionsByCompanyId(Long companyId) {
        return fabricInspectionRepository.findByCompanyId(companyId).stream()
                .sorted(Comparator.comparing(FabricInspection::getId).reversed())
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public FabricInspection getFabricInspectionById(Long id) {
        Optional<FabricInspection> inspection = fabricInspectionRepository.findById(id);
        return inspection.orElse(null);
    }

    public boolean deleteFabricInspection(Long id) {
        Optional<FabricInspection> inspectionOpt = fabricInspectionRepository.findById(id);
        if (inspectionOpt.isPresent()) {
            FabricInspection inspection = inspectionOpt.get();
            inspection.setActiveFlag(false);
            fabricInspectionRepository.save(inspection);
            return true;
        }
        return false;
    }

    @Transactional
    public FabricInspection updateFabricInspectionByCompany(Long companyId, Long id, FabricInspectionDTO dto) {
        FabricInspection inspection = fabricInspectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FabricInspection not found with id: " + id));
        if (inspection.getCompanyId() == null || !inspection.getCompanyId().equals(companyId)) {
            throw new RuntimeException("FabricInspection not found for companyId: " + companyId);
        }

        updateIfPresent(inspection::setInspectionDate, dto.getInspectionDate());
        inspection.setLoomNo(dto.getLoomNo());
        updateIfPresent(inspection::setVendorId, dto.getVendorId());
        updateIfPresent(inspection::setInspectorId, dto.getInspectorId());
        updateIfPresent(inspection::setCompanyId, dto.getCompanyId());
        updateIfPresent(inspection::setIsFactoryFabric, dto.getIsFactoryFabric());
        updateIfHasText(inspection::setFabricQuality, dto.getFabricQuality());
        updateIfPresent(inspection::setPieceMeters, dto.getPieceMeters());
        updateIfPresent(inspection::setNetWeight, dto.getNetWeight());
        updateIfPresent(inspection::setActiveFlag, dto.getActiveFlag());

        updateIfHasText(inspection::setSourceType, dto.getSourceType());
        updateIfPresent(inspection::setPurchaseInwardId, dto.getPurchaseInwardId());
        updateIfPresent(inspection::setJobworkFabricReceiveId, dto.getJobworkFabricReceiveId());
        updateIfPresent(inspection::setBrushingReceiveId, dto.getBrushingReceiveId());
        updateIfHasText(inspection::setPieceNo, dto.getPieceNo());
        updateIfHasText(inspection::setFabricCode, dto.getFabricCode());
        updateIfPresent(inspection::setFabricTypeId, dto.getFabricTypeId());

        updateIfPresent(inspection::setLoomId, dto.getLoomId());
        updateIfPresent(inspection::setFlangeId, dto.getFlangeId());
        updateIfPresent(inspection::setWeavingWorkOrderId, dto.getWeavingWorkOrderId());
        updateIfPresent(inspection::setBuyerId, dto.getBuyerId());
        updateIfPresent(inspection::setFabricMasterId, dto.getFabricMasterId());
        updateIfHasText(inspection::setBeamNo, dto.getBeamNo());
        updateIfPresent(inspection::setIsNoDefectedPieces, dto.getIsNoDefectedPieces());
        updateIfHasText(inspection::setOrderNo, dto.getOrderNo());
        updateIfHasText(inspection::setContractNo, dto.getContractNo());
        updateIfPresent(inspection::setTotalPoints, dto.getTotalPoints());
        updateIfPresent(inspection::setPointsPer100Mtr, dto.getPointsPer100Mtr());

        updateIfPresent(inspection::setDoffDate, dto.getDoffDate());
        updateIfHasText(inspection::setPoNo, dto.getPoNo());
        updateIfHasText(inspection::setWarpLotNo, dto.getWarpLotNo());
        updateIfHasText(inspection::setWeftLotNo, dto.getWeftLotNo());
        updateIfHasText(inspection::setWarpSetNo, dto.getWarpSetNo());
        updateIfHasText(inspection::setSelvedgeThread, dto.getSelvedgeThread());
        updateIfHasText(inspection::setWarpCount, dto.getWarpCount());
        updateIfHasText(inspection::setWeftCount, dto.getWeftCount());
        updateIfPresent(inspection::setSelvedgeWidthPs, dto.getSelvedgeWidthPs());
        updateIfPresent(inspection::setSelvedgeWidthRs, dto.getSelvedgeWidthRs());
        updateIfPresent(inspection::setFringeWidthPs, dto.getFringeWidthPs());
        updateIfPresent(inspection::setFringeWidthRs, dto.getFringeWidthRs());
        updateIfHasText(inspection::setWeave, dto.getWeave());
        updateIfHasText(inspection::setEpi, dto.getEpi());
        updateIfHasText(inspection::setPpi, dto.getPpi());
        updateIfHasText(inspection::setWidth, dto.getWidth());
        updateIfHasText(inspection::setInspectedEpi, dto.getInspectedEpi());
        updateIfHasText(inspection::setInspectedPpi, dto.getInspectedPpi());
        updateIfHasText(inspection::setInspectedWidth, dto.getInspectedWidth());
        updateIfPresent(inspection::setBuyerInspectorId, dto.getBuyerInspectorId());
        updateIfHasText(inspection::setBuyerInspector, dto.getBuyerInspector());
        if (dto.getBuyerInspectorName() != null) {
            inspection.setBuyerInspectorName(dto.getBuyerInspectorName());
        }
        inspection.setPackLaterReason(dto.getPackLaterReason());
        inspection.setRejectedReason(dto.getRejectedReason());

        boolean hasNewDetails = dto.getInspectionDetails() != null && !dto.getInspectionDetails().isEmpty();
        boolean hasNewEntries = dto.getInspectionEntries() != null && !dto.getInspectionEntries().isEmpty();

        if (hasNewDetails) {
            if (inspection.getInspectionDetails() != null) {
                inspection.getInspectionDetails().clear();
            }
            final FabricInspection resolvedInspection = inspection;
            List<InspectionDetail> details = dto.getInspectionDetails().stream()
                    .map(detailDto -> {
                        InspectionDetail detail = new InspectionDetail();
                        detail.setFabricInspection(resolvedInspection);
                        detail.setPieceNo(detailDto.getPieceNo());
                        detail.setPieceMeters(detailDto.getPieceMeters());
                        detail.setRollDoffMeters(detailDto.getRollDoffMeters());
                        detail.setInspectedMeters(detailDto.getInspectedMeters());
                        detail.setActualNetWeight(detailDto.getActualNetWeight());
                        Double meterForAvg = (detailDto.getPieceMeters() != null && detailDto.getPieceMeters() > 0)
                                ? detailDto.getPieceMeters()
                                : detailDto.getRollDoffMeters();
                        if (meterForAvg != null && meterForAvg > 0 && detailDto.getActualNetWeight() != null) {
                            detail.setAvgWeight(detailDto.getActualNetWeight() / meterForAvg);
                        }
                        detail.setTotalDefectPoints(detailDto.getTotalDefectPoints());
                        detail.setTotalPoints(detailDto.getTotalPoints());
                        detail.setPointsPer100Mtr(detailDto.getPointsPer100Mtr());
                        detail.setDefectCounts(detailDto.getDefectCounts());
                        detail.setGradeId(detailDto.getGradeId());
                        detail.setActiveFlag(true);
                        return detail;
                    })
                    .collect(Collectors.toList());
            if (inspection.getInspectionDetails() == null) {
                inspection.setInspectionDetails(new java.util.ArrayList<>());
            }
            inspection.getInspectionDetails().addAll(details);

            try {
                List<String> pieceNumbers = dto.getInspectionDetails().stream()
                        .map(InspectionDetailDTO::getPieceNo)
                        .filter(pn -> pn != null && !pn.isEmpty())
                        .collect(Collectors.toList());
                requirePieceEntries(pieceNumbers);
                if (!pieceNumbers.isEmpty()) {
                    List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
                    if (!pieces.isEmpty()) {
                        inspection.setPieceEntryId(pieces.get(0).getId());
                        inspection.setIsInspected(pieces.get(0).getIsInspected());
                    }
                }
            } catch (ApiException ex) {
                throw ex;
            } catch (Exception ignore) { }

            markPiecesAsInspected(inspection.getId(), dto.getInspectionDetails(), dto.getInspectionEntries(), dto.getInspectorId(), companyId, dto.getPackingOption(), dto.getRejectedReason(), dto.getPackLaterReason());
            inspection.setIsInspected(1);
        }

        if (hasNewEntries) {
            if (inspection.getInspectionEntries() != null) {
                inspection.getInspectionEntries().clear();
            }
            final FabricInspection resolvedInspection = inspection;
            List<InspectionEntry> entries = dto.getInspectionEntries().stream()
                    .map(entryDto -> {
                        InspectionEntry entry = new InspectionEntry();
                        entry.setFabricInspection(resolvedInspection);
                        entry.setDefectedMeters(entryDto.getDefectedMeters());
                        entry.setFromMeters(entryDto.getFromMeters());
                        entry.setToMeters(entryDto.getToMeters());
                        entry.setDefectTypeId(entryDto.getDefectTypeId());
                        entry.setDefectPoints(entryDto.getDefectPoints());
                        entry.setInspectionId(entryDto.getInspectionId());
                        entry.setActiveFlag(true);
                        return entry;
                    })
                    .collect(Collectors.toList());
            if (inspection.getInspectionEntries() == null) {
                inspection.setInspectionEntries(new java.util.ArrayList<>());
            }
            inspection.getInspectionEntries().addAll(entries);
        }

        FabricInspection savedInspection = fabricInspectionRepository.save(inspection);
        
        syncFabricInspectionToPieceEntry(savedInspection.getId());
        markWeavingWorkOrderReference(savedInspection.getWeavingWorkOrderId());
        
        createImmediatePacking(savedInspection, dto);
        
        return savedInspection;
    }

    public boolean deleteFabricInspectionByCompany(Long companyId, Long id) {
        Optional<FabricInspection> inspectionOpt = fabricInspectionRepository.findById(id);
        if (inspectionOpt.isPresent() && inspectionOpt.get().getCompanyId() != null && inspectionOpt.get().getCompanyId().equals(companyId)) {
            FabricInspection inspection = inspectionOpt.get();
            inspection.setActiveFlag(false);
            fabricInspectionRepository.save(inspection);
            return true;
        }
        return false;
    }

    @Transactional
    public void resetPackingStatusForPieces(Long packingId) {
        pieceEntryRepository.resetPackingStatus(packingId);
    }

    @Transactional
    public void resetDispatchStatusForPieces(Long dispatchId) {
        pieceEntryRepository.resetDispatchStatus(dispatchId);
    }
    
    private FabricInspectionDTO mapToDTO(FabricInspection inspection) {
        FabricInspectionDTO dto = new FabricInspectionDTO();
        dto.setId(inspection.getId());
        dto.setInspectionDate(inspection.getInspectionDate());
        dto.setLoomNo(inspection.getLoomNo());
        dto.setVendorId(inspection.getVendorId());
        dto.setInspectorId(inspection.getInspectorId());
        dto.setCompanyId(inspection.getCompanyId());
        dto.setIsFactoryFabric(inspection.getIsFactoryFabric());
        dto.setFabricQuality(inspection.getFabricQuality());
        dto.setPieceMeters(inspection.getPieceMeters());
        dto.setNetWeight(inspection.getNetWeight());
        dto.setActiveFlag(inspection.getActiveFlag());
        
        dto.setSourceType(inspection.getSourceType());
        dto.setPurchaseInwardId(inspection.getPurchaseInwardId());
        dto.setJobworkFabricReceiveId(inspection.getJobworkFabricReceiveId());
        dto.setBrushingReceiveId(inspection.getBrushingReceiveId());
        dto.setPieceNo(inspection.getPieceNo());
        dto.setFabricCode(inspection.getFabricCode());
        dto.setFabricTypeId(inspection.getFabricTypeId());
        dto.setLoomId(inspection.getLoomId());
        dto.setFlangeId(inspection.getFlangeId());
        dto.setWeavingWorkOrderId(inspection.getWeavingWorkOrderId());
        dto.setBuyerId(inspection.getBuyerId());
        dto.setFabricMasterId(inspection.getFabricMasterId());
        dto.setBeamNo(inspection.getBeamNo());
        dto.setIsNoDefectedPieces(inspection.getIsNoDefectedPieces());
        dto.setPieceEntryId(inspection.getPieceEntryId());
        dto.setIsInspected(inspection.getIsInspected());
        dto.setOrderNo(inspection.getOrderNo());
        dto.setContractNo(inspection.getContractNo());
        dto.setOrderTypeId(inspection.getOrderTypeId());
        dto.setTotalPoints(inspection.getTotalPoints());
        dto.setPointsPer100Mtr(inspection.getPointsPer100Mtr());

        dto.setDoffDate(inspection.getDoffDate());
        dto.setPoNo(inspection.getPoNo());
        dto.setWarpLotNo(inspection.getWarpLotNo());
        dto.setWeftLotNo(inspection.getWeftLotNo());
        dto.setWarpSetNo(inspection.getWarpSetNo());
        dto.setSelvedgeThread(inspection.getSelvedgeThread());
        dto.setWarpCount(inspection.getWarpCount());
        dto.setWeftCount(inspection.getWeftCount());
        dto.setSelvedgeWidthPs(inspection.getSelvedgeWidthPs());
        dto.setSelvedgeWidthRs(inspection.getSelvedgeWidthRs());
        dto.setFringeWidthPs(inspection.getFringeWidthPs());
        dto.setFringeWidthRs(inspection.getFringeWidthRs());
        dto.setWeave(inspection.getWeave());
        dto.setEpi(inspection.getEpi());
        dto.setPpi(inspection.getPpi());
        dto.setWidth(inspection.getWidth());
        dto.setInspectedEpi(inspection.getInspectedEpi());
        dto.setInspectedPpi(inspection.getInspectedPpi());
        dto.setInspectedWidth(inspection.getInspectedWidth());
        dto.setBuyerInspectorId(inspection.getBuyerInspectorId());
        dto.setBuyerInspector(inspection.getBuyerInspector());
        dto.setBuyerInspectorName(inspection.getBuyerInspectorName());
        dto.setPackLaterReason(inspection.getPackLaterReason());
        dto.setRejectedReason(inspection.getRejectedReason());

        if (inspection.getInspectionDetails() != null) {
            List<InspectionDetailDTO> detailDTOs = inspection.getInspectionDetails().stream()
                    .map(this::mapDetailToDTO)
                    .collect(Collectors.toList());
            dto.setInspectionDetails(detailDTOs);
        }

        if (inspection.getInspectionEntries() != null) {
            List<InspectionEntryDTO> entryDTOs = inspection.getInspectionEntries().stream()
                    .map(this::mapEntryToDTO)
                    .collect(Collectors.toList());
            dto.setInspectionEntries(entryDTOs);
        }

        // NEW: Add piece entries to the DTO
        List<PieceEntryResponseDTO> pieceEntries = getPieceEntriesByInspectionId(inspection.getId());
        dto.setPieceEntries(pieceEntries);

        if (pieceEntries != null && !pieceEntries.isEmpty()) {
            PieceEntryResponseDTO firstPiece = pieceEntries.get(0);
            dto.setIsRejected(firstPiece.getIsRejected());
            if (firstPiece.getIsRejected() != null && firstPiece.getIsRejected() == 1) {
                dto.setPackingOption("REJECT");
                dto.setRejectedReason(firstPiece.getRejectedReason() != null ? firstPiece.getRejectedReason() : inspection.getRejectedReason());
            } else if (firstPiece.getPackingId() != null && firstPiece.getPackingId() > 0) {
                dto.setPackingOption("PACK_IMMEDIATELY");
                try {
                    BaleRollPackingDTO packing = baleRollPackingService.getById(firstPiece.getPackingId());
                    if (packing != null) {
                        dto.setPackingType(packing.getPackingType());
                        dto.setWarehouseId(packing.getWarehouseId());
                        dto.setUomId(packing.getUomId());
                        dto.setPackingRemarks(packing.getRemarks());
                        dto.setRollOrBaleNo(packing.getRollOrBaleNo());
                        dto.setSalesOrderId(packing.getSalesOrderId());
                    }
                } catch (Exception ignore) { }
            } else {
                dto.setPackingOption("PACK_LATER");
                dto.setPackLaterReason(firstPiece.getPackLaterReason() != null ? firstPiece.getPackLaterReason() : inspection.getPackLaterReason());
            }
        }

        return dto;
    }
    
    private InspectionDetailDTO mapDetailToDTO(InspectionDetail detail) {
        InspectionDetailDTO dto = new InspectionDetailDTO();
        dto.setId(detail.getId());
        dto.setFabricInspectionId(detail.getFabricInspection().getId());
        dto.setPieceNo(detail.getPieceNo());
        dto.setPieceMeters(detail.getPieceMeters());
        dto.setRollDoffMeters(detail.getRollDoffMeters());
        dto.setInspectedMeters(detail.getInspectedMeters());
        dto.setActualNetWeight(detail.getActualNetWeight());
        dto.setAvgWeight(detail.getAvgWeight());
        dto.setTotalDefectPoints(detail.getTotalDefectPoints());
        dto.setTotalPoints(detail.getTotalPoints());
        dto.setPointsPer100Mtr(detail.getPointsPer100Mtr());
        dto.setDefectCounts(detail.getDefectCounts());
        dto.setGradeId(detail.getGradeId());
          // Optional: Also set grade name if needed
        // if (detail.getGradeId() != null) {
        //     Optional<GradeMaster> gradeOpt = gradeMasterRepository.findById(detail.getGradeId());
        //     if (gradeOpt.isPresent()) {
        //         dto.setGrade(gradeOpt.get().getGradeName());
        //     }
        // }
        dto.setActiveFlag(detail.getActiveFlag());
        return dto;
    }

    private InspectionEntryDTO mapEntryToDTO(InspectionEntry entry) {
        InspectionEntryDTO dto = new InspectionEntryDTO();
        dto.setId(entry.getId());
        dto.setFabricInspectionId(entry.getFabricInspection().getId());
        dto.setDefectedMeters(entry.getDefectedMeters());
        dto.setFromMeters(entry.getFromMeters());
        dto.setToMeters(entry.getToMeters());
        dto.setDefectTypeId(entry.getDefectTypeId());
        dto.setDefectPoints(entry.getDefectPoints());
        dto.setInspectionId(entry.getInspectionId());
        dto.setActiveFlag(entry.getActiveFlag());
        return dto;
    }

    @Transactional
    public FabricInspection createFabricInspectionFromPieceEntry(PieceEntry piece) {
        if (piece == null) {
            throw new IllegalArgumentException("PieceEntry cannot be null");
        }

        FabricInspection inspection = new FabricInspection();
        inspection.setInspectionDate(LocalDate.now());
        inspection.setCompanyId(piece.getCompanyId());
        inspection.setPieceEntryId(piece.getId());
        inspection.setPieceNo(piece.getPieceNumber());
        inspection.setFabricTypeId(piece.getFabricTypeId());
        inspection.setFabricMasterId(piece.getFabricMasterId());

        String resolvedFabricCode = null;
        String resolvedFabricQuality = null;
        if (piece.getFabricMasterId() != null) {
            Optional<WovenFabricMaster> fabricMasterOpt = wovenFabricMasterRepository.findById(piece.getFabricMasterId());
            if (fabricMasterOpt.isPresent()) {
                resolvedFabricCode = fabricMasterOpt.get().getFabricCode();
                resolvedFabricQuality = fabricMasterOpt.get().getFabricQuality();
            }
        }

        // If it's opening balance, try to find fabricCode from the entity (override if found)
        if (piece.getOpeningBalanceId() != null) {
            openingBalanceRepository.findByIdWithRelations(piece.getOpeningBalanceId()).ifPresent(ob -> {
                ob.getFabricItems().stream()
                    .filter(fi -> fi.getPieceNo() != null && fi.getPieceNo().equals(piece.getPieceNumber()))
                    .findFirst()
                    .ifPresent(fi -> {
                        if (fi.getFabricCode() != null && !fi.getFabricCode().isEmpty()) {
                            inspection.setFabricCode(fi.getFabricCode());
                        }
                    });
            });
        }

        if (inspection.getFabricCode() == null) {
            inspection.setFabricCode(resolvedFabricCode);
        }
        inspection.setFabricQuality(resolvedFabricQuality != null ? resolvedFabricQuality : piece.getRemarks());
        inspection.setOpeningBalanceId(piece.getOpeningBalanceId());
        inspection.setIsFactoryFabric(piece.getIsFactoryFabric());
        inspection.setLoomId(piece.getLoomId());
        inspection.setFlangeId(piece.getFlangeId());
        inspection.setBeamNo(piece.getBeamNo());
        inspection.setIsInspected(piece.getIsInspected() != null ? piece.getIsInspected() : 0);
        inspection.setActiveFlag(true);
        inspection.setOperationId(piece.getOperationId());
        inspection.setOrderNo(piece.getOrderNo());
        inspection.setOrderTypeId(piece.getOrderTypeId());
        
        if (piece.getQuantity() != null) {
            inspection.setPieceMeters(piece.getQuantity().doubleValue());
        }
        if (piece.getWeight() != null) {
            inspection.setNetWeight(piece.getWeight().doubleValue());
        }
        
        if ("purchase_inward".equalsIgnoreCase(piece.getSourceType())) {
            inspection.setSourceType("PURCHASE_INWARD");
            if (piece.getPurchaseInwardItem() != null && piece.getPurchaseInwardItem().getPurchaseInward() != null) {
                inspection.setPurchaseInwardId(piece.getPurchaseInwardItem().getPurchaseInward().getId());
                inspection.setVendorId(piece.getPurchaseInwardItem().getPurchaseInward().getVendorId());
            }
        } else if ("jobwork_fabric_receive".equalsIgnoreCase(piece.getSourceType())) {
            inspection.setSourceType("JOBWORK_FABRIC");
            if (piece.getJobworkFabricReceiveItem() != null && piece.getJobworkFabricReceiveItem().getJobworkFabricReceive() != null) {
                inspection.setJobworkFabricReceiveId(piece.getJobworkFabricReceiveItem().getJobworkFabricReceive().getId());
                inspection.setVendorId(piece.getJobworkFabricReceiveItem().getJobworkFabricReceive().getVendorId());
            }
        } else if ("process_receive".equalsIgnoreCase(piece.getSourceType()) || "PROCESS_RECEIVE".equalsIgnoreCase(piece.getSourceType())) {
            inspection.setSourceType("PROCESS_RECEIVE");
            if (piece.getFinishFabricReceiveItem() != null && piece.getFinishFabricReceiveItem().getFinishFabricReceive() != null) {
                inspection.setVendorId(piece.getFinishFabricReceiveItem().getFinishFabricReceive().getVendorId());
            }
        } else if ("opening_balance".equalsIgnoreCase(piece.getSourceType())) {
            inspection.setSourceType("OPENING_BALANCE");
            inspection.setVendorId(null);
        } else if ("brushed_receive".equalsIgnoreCase(piece.getSourceType()) || "BRUSHED_RECEIVE".equalsIgnoreCase(piece.getSourceType()) || "brushed_fabric".equalsIgnoreCase(piece.getSourceType()) || "BRUSHED_FABRIC".equalsIgnoreCase(piece.getSourceType()) || piece.getBrushingReceiveId() != null) {
            inspection.setSourceType("BRUSHED_FABRIC");
            inspection.setBrushingReceiveId(piece.getBrushingReceiveId());
        }
        
        FabricInspection savedInspection = fabricInspectionRepository.save(inspection);
        
        piece.setInspectionId(savedInspection.getId());
        pieceEntryRepository.save(piece);
        
        return savedInspection;
    }

    @Transactional
    public void syncFabricInspectionToPieceEntry(Long inspectionId) {
        FabricInspection inspection = fabricInspectionRepository.findById(inspectionId)
                .orElseThrow(() -> new EntityNotFoundException("FabricInspection not found with id: " + inspectionId));
        
        if (inspection.getPieceEntryId() == null) {
            return;
        }
        
        PieceEntry piece = pieceEntryRepository.findById(inspection.getPieceEntryId())
                .orElseThrow(() -> new EntityNotFoundException("PieceEntry not found with id: " + inspection.getPieceEntryId()));
        
        piece.setInspectionId(inspection.getId());
        piece.setIsInspected(inspection.getIsInspected() != null ? inspection.getIsInspected() : 0);
        if (inspection.getFabricTypeId() != null) {
            piece.setFabricTypeId(inspection.getFabricTypeId());
        }
        if (inspection.getFabricMasterId() != null) {
            piece.setFabricMasterId(inspection.getFabricMasterId());
            piece.setFabricId(inspection.getFabricMasterId());
        }
        piece.setIsFactoryFabric(inspection.getIsFactoryFabric());
        piece.setLoomId(inspection.getLoomId());
        piece.setFlangeId(inspection.getFlangeId());
        piece.setBeamNo(inspection.getBeamNo());
        if (inspection.getOperationId() != null) {
            piece.setOperationId(inspection.getOperationId());
        }
        piece.setInspectorId(inspection.getInspectorId());
        if (inspection.getOrderNo() != null) {
            piece.setOrderNo(inspection.getOrderNo());
        }
        if (inspection.getOrderTypeId() != null) {
            piece.setOrderTypeId(inspection.getOrderTypeId());
        }
        
        if (inspection.getPieceMeters() != null) {
            piece.setQuantity(BigDecimal.valueOf(inspection.getPieceMeters()));
        }
        if (inspection.getNetWeight() != null) {
            piece.setWeight(BigDecimal.valueOf(inspection.getNetWeight()));
        }
        
        pieceEntryRepository.save(piece);

        // ✅ Update corresponding FabricItemEntity if it's an opening balance piece
        if (isOpeningBalanceSource(piece) && piece.getOpeningBalanceId() != null && piece.getIsInspected() != null && piece.getIsInspected() == 1) {
            List<FabricItemEntity> fabricItems = fabricItemRepository.findByOpeningBalanceId(piece.getOpeningBalanceId());
            for (FabricItemEntity fabricItem : fabricItems) {
                if (fabricItem.getPieceNo() != null && fabricItem.getPieceNo().equals(piece.getPieceNumber())) {
                    fabricItem.setIsInspected(true);
                    fabricItemRepository.save(fabricItem);
                    System.out.println("✅ Synced FabricItemEntity isInspected status for Opening Balance piece: " + piece.getPieceNumber());
                }
            }
        }
    }

    @Transactional
    public void deleteFabricInspectionsByPieceEntryIds(List<Long> pieceEntryIds) {
        if (pieceEntryIds != null && !pieceEntryIds.isEmpty()) {
            fabricInspectionRepository.deleteByPieceEntryIdIn(pieceEntryIds);
        }
    }
}