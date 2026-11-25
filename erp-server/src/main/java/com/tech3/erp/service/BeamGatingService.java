//package com.tech3.erp.service;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.StringUtils;
//
//import com.tech3.erp.dto.BeamGatingDTO;
//import com.tech3.erp.dto.BeamGatingDetailsDTO;
//import com.tech3.erp.dto.PaginatedResponse;
//import com.tech3.erp.entity.BeamGating;
//import com.tech3.erp.entity.BeamGatingDetails;
//import com.tech3.erp.entity.WeavingContract;
//import com.tech3.erp.entity.WeavingWorkOrder;
//import com.tech3.erp.repository.BeamGatingRepository;
//import com.tech3.erp.repository.BeamGatingDetailsRepository;
//import com.tech3.erp.repository.LoomMasterRepository;
//import com.tech3.erp.repository.FlangeRepository;
//import com.tech3.erp.repository.WeavingContractRepository;
//import com.tech3.erp.repository.WeavingWorkOrderRepository;
//import com.tech3.erp.repository.WeavingProductionRepository;
//import com.tech3.erp.service.WeavingWorkOrderService;
//import com.tech3.erp.util.PaginationHelper;
//import com.tech3.erp.exception.ApiException;
//import org.springframework.http.HttpStatus;
//
//import jakarta.persistence.EntityNotFoundException;
//
//@Service
//public class BeamGatingService {
//
//    private final BeamGatingRepository repository;
//    private final BeamGatingDetailsRepository detailsRepository;
//    private final LoomMasterRepository loomMasterRepository;
//    private final FlangeRepository flangeRepository;
//    private final ReferenceValidationService referenceValidationService;
//    private final WeavingContractRepository weavingContractRepository;
//    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
//    private final WeavingProductionRepository weavingProductionRepository;
//    private final WeavingWorkOrderService weavingWorkOrderService;
//
//    public BeamGatingService(BeamGatingRepository repository,
//                             BeamGatingDetailsRepository detailsRepository,
//                             LoomMasterRepository loomMasterRepository,
//                             FlangeRepository flangeRepository,
//                             ReferenceValidationService referenceValidationService,
//                             WeavingContractRepository weavingContractRepository,
//                             WeavingWorkOrderRepository weavingWorkOrderRepository,
//                             WeavingProductionRepository weavingProductionRepository,
//                             WeavingWorkOrderService weavingWorkOrderService) {
//        this.repository = repository;
//        this.detailsRepository = detailsRepository;
//        this.loomMasterRepository = loomMasterRepository;
//        this.flangeRepository = flangeRepository;
//        this.referenceValidationService = referenceValidationService;
//        this.weavingContractRepository = weavingContractRepository;
//        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
//        this.weavingProductionRepository = weavingProductionRepository;
//        this.weavingWorkOrderService = weavingWorkOrderService;
//    }
//
//    @Transactional
//    public BeamGatingDTO create(BeamGatingDTO dto) {
//        BeamGating entity = mapDtoToEntity(dto, new BeamGating());
//        requireDetailsReferences(dto.getBeamGatingDetails());
//        BeamGating saved = repository.save(entity);
//        List<Long> flangeIds = null;
//        if (dto.getBeamGatingDetails() != null && !dto.getBeamGatingDetails().isEmpty()) {
//            List<BeamGatingDetails> dets = dto.getBeamGatingDetails().stream().map(d -> {
//                BeamGatingDetails e = new BeamGatingDetails();
//                e.setBeamGating(saved);
//                e.setBeamNo(d.getBeamNo());
//                e.setFlangeId(d.getFlangeId());
//                e.setFlangeNo(d.getFlangeNo());
//                e.setFabricCodeQuality(d.getFabricCodeQuality());
//                e.setWeavingContractId(d.getWeavingContractId());
//                e.setWeavingWorkOrderId(d.getWeavingWorkOrderId());
//                e.setOrderTypeId(d.getOrderTypeId());
//                e.setBeamMeter(d.getBeamMeter());
//                e.setShrinkage(d.getShrinkage());
//                e.setExpectedFabricMeter(d.getExpectedFabricMeter());
//                e.setGatingPicks(d.getGatingPicks());
//                e.setRemainingMeters(d.getRemainingMeters());
//                e.setBeamId(d.getBeamId());
//                e.setFabricMasterId(d.getFabricMasterId());
//                e.setWeavingContractNo(d.getWeavingContractNo());
//                e.setWeavingWorkOrderNo(d.getWeavingWorkOrderNo());
//                if (d.getIsBeamCut() != null) {
//                    e.setIsBeamCut(d.getIsBeamCut());
//                }
//                if (d.getIsDoffedBeam() != null) {
//                    e.setIsDoffedBeam(d.getIsDoffedBeam());
//                }
//                e.setChangedFabricMasterId(d.getChangedFabricMasterId());
//                e.setChangedFabricCodeQuality(d.getChangedFabricCodeQuality());
//                return e;
//            }).collect(Collectors.toList());
//            detailsRepository.saveAll(dets);
//            Set<Long> workOrderIds = dets.stream()
//                    .map(BeamGatingDetails::getWeavingWorkOrderId)
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toSet());
//            refreshWeavingWorkOrderReferences(workOrderIds);
//            flangeIds = dto.getBeamGatingDetails().stream().map(BeamGatingDetailsDTO::getFlangeId).filter(id2 -> id2 != null).distinct().collect(Collectors.toList());
//        }
//        ensureLoomBeamGatingFlag(saved.getLoomId());
//        ensureFlangeBeamGatingFlag(flangeIds);
//        return getById(saved.getId());
//    }
//
//    public BeamGatingDTO getById(Long id) {
//        BeamGating entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("BeamGating not found"));
//        return mapEntityToDto(entity);
//    }
//
//    public List<BeamGatingDTO> getAll() {
//        return repository.findAll().stream()
//                .sorted(Comparator.comparing(BeamGating::getId).reversed())
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//    }
//
//    public PaginatedResponse<BeamGatingDTO> getAllWithPagination(Long offset, Long limit) {
//        List<BeamGatingDTO> list = getAll();
//        return PaginationHelper.paginate(list, offset, limit);
//    }
//
//    public List<BeamGatingDTO> getByCompanyId(Long companyId) {
//        return repository.findByCompanyId(companyId).stream()
//                .sorted(Comparator.comparing(BeamGating::getId).reversed())
//                .map(this::mapEntityToDto)
//                .collect(Collectors.toList());
//    }
//
//    public PaginatedResponse<BeamGatingDTO> getByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
//        List<BeamGatingDTO> list = getByCompanyId(companyId);
//        return PaginationHelper.paginate(list, offset, limit);
//    }
//
//    @Transactional
//    public BeamGatingDTO update(Long id, BeamGatingDTO dto) {
//        BeamGating entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("BeamGating not found"));
//        mapDtoToEntity(dto, entity);
//        requireDetailsReferences(dto.getBeamGatingDetails());
//        repository.save(entity);
//        ensureLoomBeamGatingFlag(entity.getLoomId());
//        List<Long> flangeIds = null;
//        List<BeamGatingDetails> existingDetails = detailsRepository.findByBeamGatingId(id);
//        Set<Long> previousWorkOrderIds = extractWeavingWorkOrderIds(existingDetails);
//        if (dto.getBeamGatingDetails() != null) {
//            List<BeamGatingDetails> toSave = new ArrayList<>();
//            for (BeamGatingDetailsDTO d : dto.getBeamGatingDetails()) {
//                BeamGatingDetails e = null;
//                if (d.getId() != null) {
//                    for (BeamGatingDetails ex : existingDetails) {
//                        if (d.getId().equals(ex.getId())) { e = ex; break; }
//                    }
//                }
//                if (e == null) {
//                    e = new BeamGatingDetails();
//                    e.setBeamGating(entity);
//                }
//                if (d.getBeamNo() != null) e.setBeamNo(d.getBeamNo());
//                if (d.getFlangeId() != null) e.setFlangeId(d.getFlangeId());
//                if (d.getFlangeNo() != null) e.setFlangeNo(d.getFlangeNo());
//                if (d.getFabricCodeQuality() != null) e.setFabricCodeQuality(d.getFabricCodeQuality());
//                if (d.getWeavingContractId() != null) e.setWeavingContractId(d.getWeavingContractId());
//                if (d.getWeavingWorkOrderId() != null) e.setWeavingWorkOrderId(d.getWeavingWorkOrderId());
//                if (d.getOrderTypeId() != null) e.setOrderTypeId(d.getOrderTypeId());
//                if (d.getBeamMeter() != null) e.setBeamMeter(d.getBeamMeter());
//                if (d.getShrinkage() != null) e.setShrinkage(d.getShrinkage());
//                if (d.getExpectedFabricMeter() != null) e.setExpectedFabricMeter(d.getExpectedFabricMeter());
//                if (d.getGatingPicks() != null) e.setGatingPicks(d.getGatingPicks());
//                if (d.getRemainingMeters() != null) e.setRemainingMeters(d.getRemainingMeters());
//                if (d.getBeamId() != null) e.setBeamId(d.getBeamId());
//                if (d.getFabricMasterId() != null) e.setFabricMasterId(d.getFabricMasterId());
//                if (d.getWeavingContractNo() != null) e.setWeavingContractNo(d.getWeavingContractNo());
//                if (d.getWeavingWorkOrderNo() != null) e.setWeavingWorkOrderNo(d.getWeavingWorkOrderNo());
//                if (d.getIsBeamCut() != null) e.setIsBeamCut(d.getIsBeamCut());
//                if (d.getIsDoffedBeam() != null) e.setIsDoffedBeam(d.getIsDoffedBeam());
//                e.setChangedFabricMasterId(d.getChangedFabricMasterId());
//                e.setChangedFabricCodeQuality(d.getChangedFabricCodeQuality());
//                toSave.add(e);
//            }
//            detailsRepository.saveAll(toSave);
//            Set<Long> currentWorkOrderIds = extractWeavingWorkOrderIds(toSave);
//            Set<Long> workOrderIds = new HashSet<>(previousWorkOrderIds);
//            workOrderIds.addAll(currentWorkOrderIds);
//            refreshWeavingWorkOrderReferences(workOrderIds);
//            flangeIds = toSave.stream().map(BeamGatingDetails::getFlangeId).filter(fid -> fid != null).distinct().collect(Collectors.toList());
//        } else {
//            flangeIds = existingDetails.stream().map(BeamGatingDetails::getFlangeId).filter(fid -> fid != null).distinct().collect(Collectors.toList());
//            refreshWeavingWorkOrderReferences(previousWorkOrderIds);
//        }
//        ensureFlangeBeamGatingFlag(flangeIds);
//        return getById(id);
//    }
//
//    @Transactional
//    public BeamGatingDTO updateByCompanyId(Long id, Long companyId, BeamGatingDTO dto) {
//        BeamGating entity = repository.findByIdAndCompanyId(id, companyId)
//                .orElseThrow(() -> new IllegalArgumentException("BeamGating not found"));
//        mapDtoToEntity(dto, entity);
//        requireDetailsReferences(dto.getBeamGatingDetails());
//        repository.save(entity);
//        ensureLoomBeamGatingFlag(entity.getLoomId());
//        List<Long> flangeIds = null;
//        List<BeamGatingDetails> existingDetails = detailsRepository.findByBeamGatingId(id);
//        Set<Long> previousWorkOrderIds = extractWeavingWorkOrderIds(existingDetails);
//        if (dto.getBeamGatingDetails() != null) {
//            List<BeamGatingDetails> toSave = new ArrayList<>();
//            for (BeamGatingDetailsDTO d : dto.getBeamGatingDetails()) {
//                BeamGatingDetails e = null;
//                if (d.getId() != null) {
//                    for (BeamGatingDetails ex : existingDetails) {
//                        if (d.getId().equals(ex.getId())) { e = ex; break; }
//                    }
//                }
//                if (e == null) {
//                    e = new BeamGatingDetails();
//                    e.setBeamGating(entity);
//                }
//                if (d.getBeamNo() != null) e.setBeamNo(d.getBeamNo());
//                if (d.getFlangeId() != null) e.setFlangeId(d.getFlangeId());
//                if (d.getFlangeNo() != null) e.setFlangeNo(d.getFlangeNo());
//                if (d.getFabricCodeQuality() != null) e.setFabricCodeQuality(d.getFabricCodeQuality());
//                if (d.getWeavingContractId() != null) e.setWeavingContractId(d.getWeavingContractId());
//                if (d.getWeavingWorkOrderId() != null) e.setWeavingWorkOrderId(d.getWeavingWorkOrderId());
//                if (d.getOrderTypeId() != null) e.setOrderTypeId(d.getOrderTypeId());
//                if (d.getBeamMeter() != null) e.setBeamMeter(d.getBeamMeter());
//                if (d.getShrinkage() != null) e.setShrinkage(d.getShrinkage());
//                if (d.getExpectedFabricMeter() != null) e.setExpectedFabricMeter(d.getExpectedFabricMeter());
//                if (d.getGatingPicks() != null) e.setGatingPicks(d.getGatingPicks());
//                if (d.getRemainingMeters() != null) e.setRemainingMeters(d.getRemainingMeters());
//                if (d.getBeamId() != null) e.setBeamId(d.getBeamId());
//                if (d.getFabricMasterId() != null) e.setFabricMasterId(d.getFabricMasterId());
//                if (d.getWeavingContractNo() != null) e.setWeavingContractNo(d.getWeavingContractNo());
//                if (d.getWeavingWorkOrderNo() != null) e.setWeavingWorkOrderNo(d.getWeavingWorkOrderNo());
//                if (d.getIsBeamCut() != null) e.setIsBeamCut(d.getIsBeamCut());
//                if (d.getIsDoffedBeam() != null) e.setIsDoffedBeam(d.getIsDoffedBeam());
//                e.setChangedFabricMasterId(d.getChangedFabricMasterId());
//                e.setChangedFabricCodeQuality(d.getChangedFabricCodeQuality());
//                toSave.add(e);
//            }
//            detailsRepository.saveAll(toSave);
//            Set<Long> currentWorkOrderIds = extractWeavingWorkOrderIds(toSave);
//            Set<Long> workOrderIds = new HashSet<>(previousWorkOrderIds);
//            workOrderIds.addAll(currentWorkOrderIds);
//            refreshWeavingWorkOrderReferences(workOrderIds);
//            flangeIds = toSave.stream().map(BeamGatingDetails::getFlangeId).filter(fid -> fid != null).distinct().collect(Collectors.toList());
//        } else {
//            flangeIds = existingDetails.stream().map(BeamGatingDetails::getFlangeId).filter(fid -> fid != null).distinct().collect(Collectors.toList());
//            refreshWeavingWorkOrderReferences(previousWorkOrderIds);
//        }
//        ensureFlangeBeamGatingFlag(flangeIds);
//        return getById(id);
//    }
//
//    @Transactional
//    public void deleteByCompanyId(Long id, Long companyId) {
//        BeamGating entity = repository.findByIdAndCompanyId(id, companyId)
//                .orElseThrow(() -> new EntityNotFoundException("BeamGating not found with id: " + id));
//        
//        // Check if any beam from this gating is already used in production
//        List<BeamGatingDetails> details = detailsRepository.findByBeamGatingId(id);
//        Set<Long> referencedWorkOrderIds = extractWeavingWorkOrderIds(details);
//        checkWeavingProductionReferences(details);
//
//        // Unmark related flanges before deleting (isBeamGating = 0)
//        unmarkFlangesAsBeamGating(details);
//        
//        // Unmark loom's isBeamGating flag (1 to 0)
//        if (entity.getLoomId() != null) {
//            loomMasterRepository.resetBeamGatingById(entity.getLoomId());
//        }
//        
//        try {
//            repository.delete(entity);
//        } catch (DataIntegrityViolationException e) {
//            throw buildDeleteReferenceException(entity.getId(), e);
//        }
//        refreshWeavingWorkOrderReferences(referencedWorkOrderIds);
//    }
//
//    @Transactional
//    public void delete(Long id) {
//        BeamGating entity = repository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("BeamGating not found with id: " + id));
//        
//        // Check if any beam from this gating is already used in production
//        List<BeamGatingDetails> details = detailsRepository.findByBeamGatingId(id);
//        Set<Long> referencedWorkOrderIds = extractWeavingWorkOrderIds(details);
//        checkWeavingProductionReferences(details);
//
//        // Unmark related flanges before deleting (isBeamGating = 0)
//        unmarkFlangesAsBeamGating(details);
//        
//        // Unmark loom's isBeamGating flag (1 to 0)
//        if (entity.getLoomId() != null) {
//            loomMasterRepository.resetBeamGatingById(entity.getLoomId());
//        }
//        
//        try {
//            repository.delete(entity);
//        } catch (DataIntegrityViolationException e) {
//            throw buildDeleteReferenceException(entity.getId(), e);
//        }
//        refreshWeavingWorkOrderReferences(referencedWorkOrderIds);
//    }
//
//    private ApiException buildDeleteReferenceException(Long beamGatingId, DataIntegrityViolationException exception) {
//        String rootMessage = extractRootCauseMessage(exception);
//        String message = "Cannot delete Beam Gating because it is mapped in another transaction";
//        if (rootMessage != null && !rootMessage.isBlank()) {
//            String lowerRoot = rootMessage.toLowerCase();
//            if (lowerRoot.contains("weaving_production")) {
//                message = "Cannot delete Beam Gating because it is already mapped in Weaving Production";
//            } else if (lowerRoot.contains("beam_gating_details")) {
//                message = "Cannot delete Beam Gating because Beam Details are mapped in another transaction";
//            }
//        }
//        if (beamGatingId != null) {
//            message = message + " (Beam Gating ID: " + beamGatingId + ")";
//        }
//        return new ApiException(HttpStatus.CONFLICT, message);
//    }
//
//    private String extractRootCauseMessage(Throwable throwable) {
//        Throwable root = throwable;
//        while (root != null && root.getCause() != null) {
//            root = root.getCause();
//        }
//        return root != null ? root.getMessage() : null;
//    }
//
//    private void requireDetailsReferences(List<BeamGatingDetailsDTO> details) {
//        if (details == null || details.isEmpty()) {
//            return;
//        }
//        Set<Long> contractIds = details.stream()
//                .map(BeamGatingDetailsDTO::getWeavingContractId)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        Set<Long> workOrderIds = details.stream()
//                .map(BeamGatingDetailsDTO::getWeavingWorkOrderId)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//        contractIds.forEach(this::requireWeavingContractReference);
//        workOrderIds.forEach(this::requireWeavingWorkOrderReference);
//    }
//
//    private void requireWeavingContractReference(Long weavingContractId) {
//        if (weavingContractId == null) {
//            return;
//        }
//        Optional<WeavingContract> contractOpt = weavingContractRepository.findById(weavingContractId);
//        referenceValidationService.requireReference(
//                weavingContractId,
//                () -> contractOpt,
//                "Weaving Contract",
//                contractOpt.map(WeavingContract::getWeavingContractNo).filter(StringUtils::hasText).orElse(null));
//    }
//
//    private void requireWeavingWorkOrderReference(Long weavingWorkOrderId) {
//        if (weavingWorkOrderId == null) {
//            return;
//        }
//        Optional<WeavingWorkOrder> workOrderOpt = weavingWorkOrderRepository.findById(weavingWorkOrderId);
//        referenceValidationService.requireReference(
//                weavingWorkOrderId,
//                () -> workOrderOpt,
//                "Weaving Work Order",
//                workOrderOpt.map(WeavingWorkOrder::getWeavingWorkOrderNo).filter(StringUtils::hasText).orElse(null));
//    }
//
//    private Set<Long> extractWeavingWorkOrderIds(List<BeamGatingDetails> details) {
//        if (details == null || details.isEmpty()) {
//            return Collections.emptySet();
//        }
//        return details.stream()
//                .map(BeamGatingDetails::getWeavingWorkOrderId)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toSet());
//    }
//
//    private void refreshWeavingWorkOrderReferences(Set<Long> workOrderIds) {
//        if (workOrderIds == null || workOrderIds.isEmpty()) {
//            return;
//        }
//        weavingWorkOrderService.refreshOrderIdMappedStatus(workOrderIds.toArray(new Long[0]));
//    }
//
//    private BeamGating mapDtoToEntity(BeamGatingDTO dto, BeamGating entity) {
//        if (dto.getGaitingDate() != null) entity.setGaitingDate(dto.getGaitingDate());
//        if (dto.getLoomId() != null) entity.setLoomId(dto.getLoomId());
//        if (dto.getGaitingType() != null) entity.setGaitingType(dto.getGaitingType());
//        if (dto.getNoOfBeamsInput() != null) entity.setNoOfBeamsInput(dto.getNoOfBeamsInput());
//        if (dto.getNoOfFabricOutput() != null) entity.setNoOfFabricOutput(dto.getNoOfFabricOutput());
//        if (dto.getFabricPanel() != null) entity.setFabricPanel(dto.getFabricPanel());
//        if (dto.getIsBeamProduction() != null) entity.setIsBeamProduction(dto.getIsBeamProduction());
//        if (dto.getCompanyId() != null) entity.setCompanyId(dto.getCompanyId());
//        if (dto.getBeamGatingNo() != null) entity.setBeamGatingNo(dto.getBeamGatingNo());
//        return entity;
//    }
//
//    private void ensureLoomBeamGatingFlag(Long loomId) {
//        if (loomId == null) return;
//        loomMasterRepository.findById(loomId).ifPresent(l -> {
//            if (l.getIsBeamGating() == null || l.getIsBeamGating() == 0) {
//                l.setIsBeamGating(1);
//                loomMasterRepository.save(l);
//            }
//        });
//    }
//
//    private void ensureFlangeBeamGatingFlag(List<Long> flangeIds) {
//        if (flangeIds == null || flangeIds.isEmpty()) return;
//        flangeRepository.markBeamGatingByIds(flangeIds);
//    }
//
//    private void unmarkFlangesAsBeamGating(List<BeamGatingDetails> details) {
//        if (details == null || details.isEmpty()) return;
//        List<Long> flangeIds = details.stream()
//                .map(BeamGatingDetails::getFlangeId)
//                .filter(id -> id != null)
//                .distinct()
//                .collect(Collectors.toList());
//        if (!flangeIds.isEmpty()) {
//            flangeRepository.unmarkBeamGatingByIds(flangeIds);
//        }
//    }
//
//    private BeamGatingDTO mapEntityToDto(BeamGating entity) {
//        BeamGatingDTO dto = new BeamGatingDTO();
//        dto.setId(entity.getId());
//        dto.setGaitingDate(entity.getGaitingDate());
//        dto.setLoomId(entity.getLoomId());
//        dto.setGaitingType(entity.getGaitingType());
//        dto.setNoOfBeamsInput(entity.getNoOfBeamsInput());
//        dto.setBeamGatingNo(entity.getBeamGatingNo());
//        dto.setNoOfFabricOutput(entity.getNoOfFabricOutput());
//        dto.setFabricPanel(entity.getFabricPanel());
//        dto.setIsBeamProduction(entity.getIsBeamProduction());
//        dto.setCompanyId(entity.getCompanyId());
//        var dets = detailsRepository.findByBeamGatingId(entity.getId()).stream().map(b -> {
//            BeamGatingDetailsDTO d = new BeamGatingDetailsDTO();
//            d.setId(b.getId());
//            d.setBeamNo(b.getBeamNo());
//            d.setFlangeId(b.getFlangeId());
//            d.setFlangeNo(b.getFlangeNo());
//            d.setBeamId(b.getBeamId());
//            d.setFabricMasterId(b.getFabricMasterId());
//            d.setFabricCodeQuality(b.getFabricCodeQuality());
//            d.setWeavingContractId(b.getWeavingContractId());
//            d.setWeavingWorkOrderId(b.getWeavingWorkOrderId());
//            d.setOrderTypeId(b.getOrderTypeId());
//            d.setWeavingContractNo(b.getWeavingContractNo());
//            d.setWeavingWorkOrderNo(b.getWeavingWorkOrderNo());
//            d.setBeamMeter(b.getBeamMeter());
//            d.setShrinkage(b.getShrinkage());
//            d.setExpectedFabricMeter(b.getExpectedFabricMeter());
//            d.setGatingPicks(b.getGatingPicks());
//            d.setRemainingMeters(b.getRemainingMeters());
//            d.setIsProductionCompleted(b.getIsProductionCompleted());
//            d.setIsBeamCut(b.getIsBeamCut());
//            d.setIsDoffedBeam(b.getIsDoffedBeam());
//            d.setChangedFabricMasterId(b.getChangedFabricMasterId());
//            d.setChangedFabricCodeQuality(b.getChangedFabricCodeQuality());
//            return d;
//        }).collect(Collectors.toList());
//        dto.setBeamGatingDetails(dets);
//        return dto;
//    }
//
//    private void checkWeavingProductionReferences(List<BeamGatingDetails> details) {
//        if (details == null || details.isEmpty()) return;
//        for (BeamGatingDetails detail : details) {
//            boolean mappedByBeamId = detail.getBeamId() != null && weavingProductionRepository.existsByBeamId(detail.getBeamId());
//            String beamNo = detail.getBeamNo();
//            boolean mappedByBeamNo = beamNo != null && !beamNo.isBlank()
//                    && (weavingProductionRepository.existsByBeamNo(beamNo)
//                    || weavingProductionRepository.existsByBeamNoContainingIgnoreCase(beamNo));
//            if (mappedByBeamId || mappedByBeamNo) {
//                throw new ApiException(HttpStatus.CONFLICT, "Cannot delete Beam Gating because Beam No " + (detail.getBeamNo() != null ? detail.getBeamNo() : detail.getBeamId()) + " is already used in Weaving Production");
//            }
//        }
//    }
//}


package com.tech3.erp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tech3.erp.dto.BeamGatingDTO;
import com.tech3.erp.dto.BeamGatingDetailsDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.BeamGating;
import com.tech3.erp.entity.BeamGatingDetails;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.repository.BeamGatingRepository;
import com.tech3.erp.repository.BeamGatingDetailsRepository;
import com.tech3.erp.repository.LoomMasterRepository;
import com.tech3.erp.repository.FlangeRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.WeavingProductionRepository;
import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.FactoryBeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.BeamOutwardDetailRepository;
import com.tech3.erp.service.WeavingWorkOrderService;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.exception.ApiException;
import org.springframework.http.HttpStatus;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BeamGatingService {

    private final BeamGatingRepository repository;
    private final BeamGatingDetailsRepository detailsRepository;
    private final LoomMasterRepository loomMasterRepository;
    private final FlangeRepository flangeRepository;
    private final ReferenceValidationService referenceValidationService;
    private final WeavingContractRepository weavingContractRepository;
    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
    private final WeavingProductionRepository weavingProductionRepository;
    private final WeavingWorkOrderService weavingWorkOrderService;
    private final BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository;
    private final FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository;
    private final BeamOutwardDetailRepository beamOutwardDetailRepository;

    public BeamGatingService(BeamGatingRepository repository,
                             BeamGatingDetailsRepository detailsRepository,
                             LoomMasterRepository loomMasterRepository,
                             FlangeRepository flangeRepository,
                             ReferenceValidationService referenceValidationService,
                             WeavingContractRepository weavingContractRepository,
                             WeavingWorkOrderRepository weavingWorkOrderRepository,
                             WeavingProductionRepository weavingProductionRepository,
                             WeavingWorkOrderService weavingWorkOrderService,
                             BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository,
                             FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository,
                             BeamOutwardDetailRepository beamOutwardDetailRepository) {
        this.repository = repository;
        this.detailsRepository = detailsRepository;
        this.loomMasterRepository = loomMasterRepository;
        this.flangeRepository = flangeRepository;
        this.referenceValidationService = referenceValidationService;
        this.weavingContractRepository = weavingContractRepository;
        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
        this.weavingProductionRepository = weavingProductionRepository;
        this.weavingWorkOrderService = weavingWorkOrderService;
        this.beamInwardBeamDetailsRepository = beamInwardBeamDetailsRepository;
        this.factoryBeamInwardBeamDetailsRepository = factoryBeamInwardBeamDetailsRepository;
        this.beamOutwardDetailRepository = beamOutwardDetailRepository;
    }

    @Transactional
    public BeamGatingDTO create(BeamGatingDTO dto) {
        BeamGating entity = mapDtoToEntity(dto, new BeamGating());
        requireDetailsReferences(dto.getBeamGatingDetails());
        BeamGating saved = repository.save(entity);
        List<Long> flangeIds = null;
        if (dto.getBeamGatingDetails() != null && !dto.getBeamGatingDetails().isEmpty()) {
            List<BeamGatingDetails> dets = dto.getBeamGatingDetails().stream().map(d -> {
                BeamGatingDetails e = new BeamGatingDetails();
                e.setBeamGating(saved);
                e.setBeamNo(d.getBeamNo());
                e.setFlangeId(d.getFlangeId());
                e.setFlangeNo(d.getFlangeNo());
                e.setFabricCodeQuality(d.getFabricCodeQuality());
                e.setWeavingContractId(d.getWeavingContractId());
                e.setWeavingWorkOrderId(d.getWeavingWorkOrderId());
                e.setOrderTypeId(d.getOrderTypeId());
                e.setBeamMeter(d.getBeamMeter());
                e.setOnLoomMtr(d.getOnLoomMtr());
                e.setShrinkage(d.getShrinkage());
                e.setExpectedFabricMeter(d.getExpectedFabricMeter());
                e.setGatingPicks(d.getGatingPicks());
                e.setRemainingMeters(d.getRemainingMeters());
                e.setBeamId(d.getBeamId());
                e.setFabricMasterId(d.getFabricMasterId());
                e.setWeavingContractNo(d.getWeavingContractNo());
                e.setWeavingWorkOrderNo(d.getWeavingWorkOrderNo());
                if (d.getIsBeamCut() != null) {
                    e.setIsBeamCut(d.getIsBeamCut());
                }
                if (d.getIsDoffedBeam() != null) {
                    e.setIsDoffedBeam(d.getIsDoffedBeam());
                }
                e.setChangedFabricMasterId(d.getChangedFabricMasterId());
                e.setChangedFabricCodeQuality(d.getChangedFabricCodeQuality());
                e.setSizingSetNo(d.getSizingSetNo());
                e.setWarpCount(d.getWarpCount());
                e.setWarpYarnMillName(d.getWarpYarnMillName());
                e.setWarpYarnLotNo(d.getWarpYarnLotNo());
                e.setWeftCount(d.getWeftCount());
                e.setWeftYarnMillName(d.getWeftYarnMillName());
                e.setWeftYarnLotNo(d.getWeftYarnLotNo());
                return e;
            }).collect(Collectors.toList());
            detailsRepository.saveAll(dets);
            
            // Synchronize changed fabric data to related tables
            synchronizeChangedFabricData(dets);

            List<String> beamNosCreate = dets.stream().map(BeamGatingDetails::getBeamNo).filter(Objects::nonNull).distinct().collect(Collectors.toList());
            markBeamGated(beamNosCreate);

            Set<Long> workOrderIds = dets.stream()
                    .map(BeamGatingDetails::getWeavingWorkOrderId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            refreshWeavingWorkOrderReferences(workOrderIds);
            flangeIds = dto.getBeamGatingDetails().stream().map(BeamGatingDetailsDTO::getFlangeId).filter(id2 -> id2 != null).distinct().collect(Collectors.toList());
        }
        ensureLoomBeamGatingFlag(saved.getLoomId());
        ensureFlangeBeamGatingFlag(flangeIds);
        return getById(saved.getId());
    }

    public BeamGatingDTO getById(Long id) {
        BeamGating entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("BeamGating not found"));
        return mapEntityToDto(entity);
    }

    public List<BeamGatingDTO> getAll() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(BeamGating::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BeamGatingDTO> getAllWithPagination(Long offset, Long limit) {
        List<BeamGatingDTO> list = getAll();
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<BeamGatingDTO> getByCompanyId(Long companyId) {
        return repository.findByCompanyId(companyId).stream()
                .sorted(Comparator.comparing(BeamGating::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BeamGatingDTO> getByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<BeamGatingDTO> list = getByCompanyId(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    @Transactional
    public BeamGatingDTO update(Long id, BeamGatingDTO dto) {
        BeamGating entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("BeamGating not found"));
        mapDtoToEntity(dto, entity);
        requireDetailsReferences(dto.getBeamGatingDetails());
        repository.save(entity);
        ensureLoomBeamGatingFlag(entity.getLoomId());
        List<Long> flangeIds = null;
        List<BeamGatingDetails> existingDetails = detailsRepository.findByBeamGatingId(id);
        Set<Long> previousWorkOrderIds = extractWeavingWorkOrderIds(existingDetails);
        if (dto.getBeamGatingDetails() != null) {
            List<BeamGatingDetails> toSave = new ArrayList<>();
            for (BeamGatingDetailsDTO d : dto.getBeamGatingDetails()) {
                BeamGatingDetails e = null;
                if (d.getId() != null) {
                    for (BeamGatingDetails ex : existingDetails) {
                        if (d.getId().equals(ex.getId())) { e = ex; break; }
                    }
                }
                if (e == null) {
                    e = new BeamGatingDetails();
                    e.setBeamGating(entity);
                }
                if (d.getBeamNo() != null) e.setBeamNo(d.getBeamNo());
                if (d.getFlangeId() != null) e.setFlangeId(d.getFlangeId());
                if (d.getFlangeNo() != null) e.setFlangeNo(d.getFlangeNo());
                if (d.getFabricCodeQuality() != null) e.setFabricCodeQuality(d.getFabricCodeQuality());
                if (d.getWeavingContractId() != null) e.setWeavingContractId(d.getWeavingContractId());
                if (d.getWeavingWorkOrderId() != null) e.setWeavingWorkOrderId(d.getWeavingWorkOrderId());
                if (d.getOrderTypeId() != null) e.setOrderTypeId(d.getOrderTypeId());
                if (d.getBeamMeter() != null) e.setBeamMeter(d.getBeamMeter());
                if (d.getOnLoomMtr() != null) e.setOnLoomMtr(d.getOnLoomMtr());
                if (d.getShrinkage() != null) e.setShrinkage(d.getShrinkage());
                if (d.getExpectedFabricMeter() != null) e.setExpectedFabricMeter(d.getExpectedFabricMeter());
                if (d.getGatingPicks() != null) e.setGatingPicks(d.getGatingPicks());
                if (d.getRemainingMeters() != null) e.setRemainingMeters(d.getRemainingMeters());
                if (d.getBeamId() != null) e.setBeamId(d.getBeamId());
                if (d.getFabricMasterId() != null) e.setFabricMasterId(d.getFabricMasterId());
                if (d.getWeavingContractNo() != null) e.setWeavingContractNo(d.getWeavingContractNo());
                if (d.getWeavingWorkOrderNo() != null) e.setWeavingWorkOrderNo(d.getWeavingWorkOrderNo());
                if (d.getIsBeamCut() != null) e.setIsBeamCut(d.getIsBeamCut());
                if (d.getIsDoffedBeam() != null) e.setIsDoffedBeam(d.getIsDoffedBeam());
                e.setChangedFabricMasterId(d.getChangedFabricMasterId());
                e.setChangedFabricCodeQuality(d.getChangedFabricCodeQuality());
                if (d.getSizingSetNo() != null) e.setSizingSetNo(d.getSizingSetNo());
                if (d.getWarpCount() != null) e.setWarpCount(d.getWarpCount());
                if (d.getWarpYarnMillName() != null) e.setWarpYarnMillName(d.getWarpYarnMillName());
                if (d.getWarpYarnLotNo() != null) e.setWarpYarnLotNo(d.getWarpYarnLotNo());
                if (d.getWeftCount() != null) e.setWeftCount(d.getWeftCount());
                if (d.getWeftYarnMillName() != null) e.setWeftYarnMillName(d.getWeftYarnMillName());
                if (d.getWeftYarnLotNo() != null) e.setWeftYarnLotNo(d.getWeftYarnLotNo());
                toSave.add(e);
            }
            detailsRepository.saveAll(toSave);
            Set<Long> savedIds = toSave.stream().map(BeamGatingDetails::getId).filter(Objects::nonNull).collect(Collectors.toSet());
            List<BeamGatingDetails> toDelete = existingDetails.stream().filter(ex -> ex.getId() != null && !savedIds.contains(ex.getId())).collect(Collectors.toList());
            if (!toDelete.isEmpty()) detailsRepository.deleteAll(toDelete);
            Set<String> savedBeamNoSet = toSave.stream().map(BeamGatingDetails::getBeamNo).filter(Objects::nonNull).collect(Collectors.toSet());
            List<String> removedBeamNos = existingDetails.stream().map(BeamGatingDetails::getBeamNo).filter(bn -> bn != null && !savedBeamNoSet.contains(bn)).distinct().collect(Collectors.toList());
            unmarkBeamGated(removedBeamNos);
            markBeamGated(new ArrayList<>(savedBeamNoSet));
            Set<Long> currentWorkOrderIds = extractWeavingWorkOrderIds(toSave);
            Set<Long> workOrderIds = new HashSet<>(previousWorkOrderIds);
            workOrderIds.addAll(currentWorkOrderIds);
            refreshWeavingWorkOrderReferences(workOrderIds);
            flangeIds = toSave.stream().map(BeamGatingDetails::getFlangeId).filter(fid -> fid != null).distinct().collect(Collectors.toList());
        } else {
            flangeIds = existingDetails.stream().map(BeamGatingDetails::getFlangeId).filter(fid -> fid != null).distinct().collect(Collectors.toList());
            refreshWeavingWorkOrderReferences(previousWorkOrderIds);
        }
        ensureFlangeBeamGatingFlag(flangeIds);
        return getById(id);
    }

    @Transactional
    public BeamGatingDTO updateByCompanyId(Long id, Long companyId, BeamGatingDTO dto) {
        BeamGating entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("BeamGating not found"));
        mapDtoToEntity(dto, entity);
        requireDetailsReferences(dto.getBeamGatingDetails());
        repository.save(entity);
        ensureLoomBeamGatingFlag(entity.getLoomId());
        List<Long> flangeIds = null;
        List<BeamGatingDetails> existingDetails = detailsRepository.findByBeamGatingId(id);
        Set<Long> previousWorkOrderIds = extractWeavingWorkOrderIds(existingDetails);
        if (dto.getBeamGatingDetails() != null) {
            List<BeamGatingDetails> toSave = new ArrayList<>();
            for (BeamGatingDetailsDTO d : dto.getBeamGatingDetails()) {
                BeamGatingDetails e = null;
                if (d.getId() != null) {
                    for (BeamGatingDetails ex : existingDetails) {
                        if (d.getId().equals(ex.getId())) { e = ex; break; }
                    }
                }
                if (e == null) {
                    e = new BeamGatingDetails();
                    e.setBeamGating(entity);
                }
                if (d.getBeamNo() != null) e.setBeamNo(d.getBeamNo());
                if (d.getFlangeId() != null) e.setFlangeId(d.getFlangeId());
                if (d.getFlangeNo() != null) e.setFlangeNo(d.getFlangeNo());
                if (d.getFabricCodeQuality() != null) e.setFabricCodeQuality(d.getFabricCodeQuality());
                if (d.getWeavingContractId() != null) e.setWeavingContractId(d.getWeavingContractId());
                if (d.getWeavingWorkOrderId() != null) e.setWeavingWorkOrderId(d.getWeavingWorkOrderId());
                if (d.getOrderTypeId() != null) e.setOrderTypeId(d.getOrderTypeId());
                if (d.getBeamMeter() != null) e.setBeamMeter(d.getBeamMeter());
                if (d.getOnLoomMtr() != null) e.setOnLoomMtr(d.getOnLoomMtr());
                if (d.getShrinkage() != null) e.setShrinkage(d.getShrinkage());
                if (d.getExpectedFabricMeter() != null) e.setExpectedFabricMeter(d.getExpectedFabricMeter());
                if (d.getGatingPicks() != null) e.setGatingPicks(d.getGatingPicks());
                if (d.getRemainingMeters() != null) e.setRemainingMeters(d.getRemainingMeters());
                if (d.getBeamId() != null) e.setBeamId(d.getBeamId());
                if (d.getFabricMasterId() != null) e.setFabricMasterId(d.getFabricMasterId());
                if (d.getWeavingContractNo() != null) e.setWeavingContractNo(d.getWeavingContractNo());
                if (d.getWeavingWorkOrderNo() != null) e.setWeavingWorkOrderNo(d.getWeavingWorkOrderNo());
                if (d.getIsBeamCut() != null) e.setIsBeamCut(d.getIsBeamCut());
                if (d.getIsDoffedBeam() != null) e.setIsDoffedBeam(d.getIsDoffedBeam());
                e.setChangedFabricMasterId(d.getChangedFabricMasterId());
                e.setChangedFabricCodeQuality(d.getChangedFabricCodeQuality());
                if (d.getSizingSetNo() != null) e.setSizingSetNo(d.getSizingSetNo());
                if (d.getWarpCount() != null) e.setWarpCount(d.getWarpCount());
                if (d.getWarpYarnMillName() != null) e.setWarpYarnMillName(d.getWarpYarnMillName());
                if (d.getWarpYarnLotNo() != null) e.setWarpYarnLotNo(d.getWarpYarnLotNo());
                if (d.getWeftCount() != null) e.setWeftCount(d.getWeftCount());
                if (d.getWeftYarnMillName() != null) e.setWeftYarnMillName(d.getWeftYarnMillName());
                if (d.getWeftYarnLotNo() != null) e.setWeftYarnLotNo(d.getWeftYarnLotNo());
                toSave.add(e);
            }
detailsRepository.saveAll(toSave);

// Synchronize changed fabric data to related tables
synchronizeChangedFabricData(toSave);

Set<Long> savedIds = toSave.stream().map(BeamGatingDetails::getId).filter(Objects::nonNull).collect(Collectors.toSet());
            List<BeamGatingDetails> toDelete = existingDetails.stream().filter(ex -> ex.getId() != null && !savedIds.contains(ex.getId())).collect(Collectors.toList());
            if (!toDelete.isEmpty()) detailsRepository.deleteAll(toDelete);
            Set<String> savedBeamNoSetByCompany = toSave.stream().map(BeamGatingDetails::getBeamNo).filter(Objects::nonNull).collect(Collectors.toSet());
            List<String> removedBeamNosByCompany = existingDetails.stream().map(BeamGatingDetails::getBeamNo).filter(bn -> bn != null && !savedBeamNoSetByCompany.contains(bn)).distinct().collect(Collectors.toList());
            unmarkBeamGated(removedBeamNosByCompany);
            markBeamGated(new ArrayList<>(savedBeamNoSetByCompany));
            Set<Long> currentWorkOrderIds = extractWeavingWorkOrderIds(toSave);
            Set<Long> workOrderIds = new HashSet<>(previousWorkOrderIds);
            workOrderIds.addAll(currentWorkOrderIds);
            refreshWeavingWorkOrderReferences(workOrderIds);
            flangeIds = toSave.stream().map(BeamGatingDetails::getFlangeId).filter(fid -> fid != null).distinct().collect(Collectors.toList());
        } else {
            flangeIds = existingDetails.stream().map(BeamGatingDetails::getFlangeId).filter(fid -> fid != null).distinct().collect(Collectors.toList());
            refreshWeavingWorkOrderReferences(previousWorkOrderIds);
        }
        ensureFlangeBeamGatingFlag(flangeIds);
        return getById(id);
    }

    @Transactional
    public void deleteByCompanyId(Long id, Long companyId) {
        BeamGating entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("BeamGating not found with id: " + id));
        
        // Check if any beam from this gating is already used in production
        List<BeamGatingDetails> details = detailsRepository.findByBeamGatingId(id);
        Set<Long> referencedWorkOrderIds = extractWeavingWorkOrderIds(details);
        checkWeavingProductionReferences(details);

        // Unmark related flanges before deleting (isBeamGating = 0)
        unmarkFlangesAsBeamGating(details);

        // Revert isBeamGated = 0 for all beams in this gating
        List<String> beamNosToUngate = details.stream().map(BeamGatingDetails::getBeamNo).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        unmarkBeamGated(beamNosToUngate);

        // Unmark loom's isBeamGating flag (1 to 0)
        if (entity.getLoomId() != null) {
            loomMasterRepository.resetBeamGatingById(entity.getLoomId());
        }
        
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw buildDeleteReferenceException(entity.getId(), e);
        }
        refreshWeavingWorkOrderReferences(referencedWorkOrderIds);
    }

    @Transactional
    public void delete(Long id) {
        BeamGating entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BeamGating not found with id: " + id));
        
        // Check if any beam from this gating is already used in production
        List<BeamGatingDetails> details = detailsRepository.findByBeamGatingId(id);
        Set<Long> referencedWorkOrderIds = extractWeavingWorkOrderIds(details);
        checkWeavingProductionReferences(details);

        // Unmark related flanges before deleting (isBeamGating = 0)
        unmarkFlangesAsBeamGating(details);

        // Revert isBeamGated = 0 for all beams in this gating
        List<String> beamNosToUngateOnDelete = details.stream().map(BeamGatingDetails::getBeamNo).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        unmarkBeamGated(beamNosToUngateOnDelete);

        // Unmark loom's isBeamGating flag (1 to 0)
        if (entity.getLoomId() != null) {
            loomMasterRepository.resetBeamGatingById(entity.getLoomId());
        }
        
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw buildDeleteReferenceException(entity.getId(), e);
        }
        refreshWeavingWorkOrderReferences(referencedWorkOrderIds);
    }

    private ApiException buildDeleteReferenceException(Long beamGatingId, DataIntegrityViolationException exception) {
        String rootMessage = extractRootCauseMessage(exception);
        String message = "Cannot delete Beam Gating because it is mapped in another transaction";
        if (rootMessage != null && !rootMessage.isBlank()) {
            String lowerRoot = rootMessage.toLowerCase();
            if (lowerRoot.contains("weaving_production")) {
                message = "Cannot delete Beam Gating because it is already mapped in Weaving Production";
            } else if (lowerRoot.contains("beam_gating_details")) {
                message = "Cannot delete Beam Gating because Beam Details are mapped in another transaction";
            }
        }
        if (beamGatingId != null) {
            message = message + " (Beam Gating ID: " + beamGatingId + ")";
        }
        return new ApiException(HttpStatus.CONFLICT, message);
    }

    private String extractRootCauseMessage(Throwable throwable) {
        Throwable root = throwable;
        while (root != null && root.getCause() != null) {
            root = root.getCause();
        }
        return root != null ? root.getMessage() : null;
    }

    private void requireDetailsReferences(List<BeamGatingDetailsDTO> details) {
        if (details == null || details.isEmpty()) {
            return;
        }
        Set<Long> contractIds = details.stream()
                .map(BeamGatingDetailsDTO::getWeavingContractId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<Long> workOrderIds = details.stream()
                .map(BeamGatingDetailsDTO::getWeavingWorkOrderId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        contractIds.forEach(this::requireWeavingContractReference);
        workOrderIds.forEach(this::requireWeavingWorkOrderReference);
    }

    private void requireWeavingContractReference(Long weavingContractId) {
        if (weavingContractId == null) {
            return;
        }
        Optional<WeavingContract> contractOpt = weavingContractRepository.findById(weavingContractId);
        referenceValidationService.requireReference(
                weavingContractId,
                () -> contractOpt,
                "Weaving Contract",
                contractOpt.map(WeavingContract::getWeavingContractNo).filter(StringUtils::hasText).orElse(null));
    }

    private void requireWeavingWorkOrderReference(Long weavingWorkOrderId) {
        if (weavingWorkOrderId == null) {
            return;
        }
        Optional<WeavingWorkOrder> workOrderOpt = weavingWorkOrderRepository.findById(weavingWorkOrderId);
        referenceValidationService.requireReference(
                weavingWorkOrderId,
                () -> workOrderOpt,
                "Weaving Work Order",
                workOrderOpt.map(WeavingWorkOrder::getWeavingWorkOrderNo).filter(StringUtils::hasText).orElse(null));
    }

    private Set<Long> extractWeavingWorkOrderIds(List<BeamGatingDetails> details) {
        if (details == null || details.isEmpty()) {
            return Collections.emptySet();
        }
        return details.stream()
                .map(BeamGatingDetails::getWeavingWorkOrderId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    private void refreshWeavingWorkOrderReferences(Set<Long> workOrderIds) {
        if (workOrderIds == null || workOrderIds.isEmpty()) {
            return;
        }
        weavingWorkOrderService.refreshOrderIdMappedStatus(workOrderIds.toArray(new Long[0]));
    }

    private BeamGating mapDtoToEntity(BeamGatingDTO dto, BeamGating entity) {
        if (dto.getGaitingDate() != null) entity.setGaitingDate(dto.getGaitingDate());
        if (dto.getLoomId() != null) entity.setLoomId(dto.getLoomId());
        if (dto.getGaitingType() != null) entity.setGaitingType(dto.getGaitingType());
        if (dto.getNoOfBeamsInput() != null) entity.setNoOfBeamsInput(dto.getNoOfBeamsInput());
        if (dto.getNoOfFabricOutput() != null) entity.setNoOfFabricOutput(dto.getNoOfFabricOutput());
        if (dto.getFabricPanel() != null) entity.setFabricPanel(dto.getFabricPanel());
        if (dto.getIsBeamProduction() != null) entity.setIsBeamProduction(dto.getIsBeamProduction());
        if (dto.getCompanyId() != null) entity.setCompanyId(dto.getCompanyId());
        if (dto.getBeamGatingNo() != null) entity.setBeamGatingNo(dto.getBeamGatingNo());
        if (dto.getShed() != null) entity.setShed(dto.getShed());
        if (dto.getShedId() != null) entity.setShedId(dto.getShedId());
        if (dto.getGroupId() != null) entity.setGroupId(dto.getGroupId());
        if (dto.getGroup() != null) entity.setGroup(dto.getGroup());
        if (dto.getEffectFrom() != null) entity.setEffectFrom(dto.getEffectFrom());
        if (dto.getEffectiveDate() != null) {
            entity.setEffectiveDate(dto.getEffectiveDate());
        } else if (dto.getEffectFrom() != null && !"Time".equalsIgnoreCase(dto.getEffectFrom())) {
            entity.setEffectiveDate(null);
        }
        return entity;
    }

    private void ensureLoomBeamGatingFlag(Long loomId) {
        if (loomId == null) return;
        loomMasterRepository.findById(loomId).ifPresent(l -> {
            if (l.getIsBeamGating() == null || l.getIsBeamGating() == 0) {
                l.setIsBeamGating(1);
                loomMasterRepository.save(l);
            }
        });
    }

    private void ensureFlangeBeamGatingFlag(List<Long> flangeIds) {
        if (flangeIds == null || flangeIds.isEmpty()) return;
        flangeRepository.markBeamGatingByIds(flangeIds);
    }

    private void unmarkFlangesAsBeamGating(List<BeamGatingDetails> details) {
        if (details == null || details.isEmpty()) return;
        List<Long> flangeIds = details.stream()
                .map(BeamGatingDetails::getFlangeId)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
        if (!flangeIds.isEmpty()) {
            flangeRepository.unmarkBeamGatingByIds(flangeIds);
        }
    }

    private BeamGatingDTO mapEntityToDto(BeamGating entity) {
        BeamGatingDTO dto = new BeamGatingDTO();
        dto.setId(entity.getId());
        dto.setGaitingDate(entity.getGaitingDate());
        dto.setLoomId(entity.getLoomId());
        dto.setGaitingType(entity.getGaitingType());
        dto.setNoOfBeamsInput(entity.getNoOfBeamsInput());
        dto.setBeamGatingNo(entity.getBeamGatingNo());
        dto.setNoOfFabricOutput(entity.getNoOfFabricOutput());
        dto.setFabricPanel(entity.getFabricPanel());
        dto.setIsBeamProduction(entity.getIsBeamProduction());
        dto.setCompanyId(entity.getCompanyId());
        dto.setShed(entity.getShed());
        dto.setShedId(entity.getShedId());
        dto.setGroupId(entity.getGroupId());
        dto.setGroup(entity.getGroup());
        dto.setEffectFrom(entity.getEffectFrom());
        dto.setEffectiveDate(entity.getEffectiveDate());
        var dets = detailsRepository.findByBeamGatingId(entity.getId()).stream().map(b -> {
            BeamGatingDetailsDTO d = new BeamGatingDetailsDTO();
            d.setId(b.getId());
            d.setBeamNo(b.getBeamNo());
            d.setFlangeId(b.getFlangeId());
            d.setFlangeNo(b.getFlangeNo());
            d.setBeamId(b.getBeamId());
            d.setFabricMasterId(b.getFabricMasterId());
            d.setFabricCodeQuality(b.getFabricCodeQuality());
            d.setWeavingContractId(b.getWeavingContractId());
            d.setWeavingWorkOrderId(b.getWeavingWorkOrderId());
            d.setOrderTypeId(b.getOrderTypeId());
            d.setWeavingContractNo(b.getWeavingContractNo());
            d.setWeavingWorkOrderNo(b.getWeavingWorkOrderNo());
            d.setBeamMeter(b.getBeamMeter());
            d.setOnLoomMtr(b.getOnLoomMtr());
            d.setShrinkage(b.getShrinkage());
            d.setExpectedFabricMeter(b.getExpectedFabricMeter());
            d.setGatingPicks(b.getGatingPicks());
            d.setRemainingMeters(b.getRemainingMeters());
            d.setIsProductionCompleted(b.getIsProductionCompleted());
            d.setIsBeamCut(b.getIsBeamCut());
            d.setIsDoffedBeam(b.getIsDoffedBeam());
            d.setChangedFabricMasterId(b.getChangedFabricMasterId());
            d.setChangedFabricCodeQuality(b.getChangedFabricCodeQuality());
            d.setSizingSetNo(b.getSizingSetNo());
            d.setWarpCount(b.getWarpCount());
            d.setWarpYarnMillName(b.getWarpYarnMillName());
            d.setWarpYarnLotNo(b.getWarpYarnLotNo());
            d.setWeftCount(b.getWeftCount());
            d.setWeftYarnMillName(b.getWeftYarnMillName());
            d.setWeftYarnLotNo(b.getWeftYarnLotNo());
            return d;
        }).collect(Collectors.toList());
        dto.setBeamGatingDetails(dets);
        return dto;
    }

    private void checkWeavingProductionReferences(List<BeamGatingDetails> details) {
        if (details == null || details.isEmpty()) return;
        for (BeamGatingDetails detail : details) {
            // Check if beam is doffed - if so, prevent deletion
            if (detail.getIsDoffedBeam() != null && detail.getIsDoffedBeam() == 1) {
                throw new ApiException(HttpStatus.CONFLICT, "Cannot delete Beam Gating because beam '" + detail.getBeamNo() + "' is already doffed and cannot be deleted.");
            }
            
            boolean mappedByBeamId = detail.getBeamId() != null && weavingProductionRepository.existsByBeamId(detail.getBeamId());
            String beamNo = detail.getBeamNo();
            boolean mappedByBeamNo = beamNo != null && !beamNo.isBlank()
                    && (weavingProductionRepository.existsByBeamNo(beamNo)
                    || weavingProductionRepository.existsByBeamNoContainingIgnoreCase(beamNo));
            if (mappedByBeamId || mappedByBeamNo) {
                throw new ApiException(HttpStatus.CONFLICT, "Cannot delete Beam Gating because Beam No " + (detail.getBeamNo() != null ? detail.getBeamNo() : detail.getBeamId()) + " is already used in Weaving Production");
            }
        }
    }

    private void markBeamGated(List<String> beamNos) {
        if (beamNos == null || beamNos.isEmpty()) return;
        List<String> filtered = beamNos.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (filtered.isEmpty()) return;
        try { beamInwardBeamDetailsRepository.updateIsBeamGatedBySizedBeamNoIn(filtered, 1); } catch (Exception e) {
            System.err.println("Error marking isBeamGated in BeamInwardBeamDetails: " + e.getMessage());
        }
        try { beamOutwardDetailRepository.updateIsBeamGatedBySizedBeamNoIn(filtered, 1); } catch (Exception e) {
            System.err.println("Error marking isBeamGated in BeamOutwardDetail: " + e.getMessage());
        }
    }

    private void unmarkBeamGated(List<String> beamNos) {
        if (beamNos == null || beamNos.isEmpty()) return;
        List<String> filtered = beamNos.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (filtered.isEmpty()) return;
        try { beamInwardBeamDetailsRepository.updateIsBeamGatedBySizedBeamNoIn(filtered, 0); } catch (Exception e) {
            System.err.println("Error unmarking isBeamGated in BeamInwardBeamDetails: " + e.getMessage());
        }
        try { beamOutwardDetailRepository.updateIsBeamGatedBySizedBeamNoIn(filtered, 0); } catch (Exception e) {
            System.err.println("Error unmarking isBeamGated in BeamOutwardDetail: " + e.getMessage());
        }
    }

    private void synchronizeChangedFabricData(List<BeamGatingDetails> beamGatingDetails) {
        for (BeamGatingDetails detail : beamGatingDetails) {
            if (detail.getBeamNo() != null && 
                (detail.getChangedFabricMasterId() != null || detail.getChangedFabricCodeQuality() != null)) {
                
                String sizedBeamNo = detail.getBeamNo();
                Long changedFabricMasterId = detail.getChangedFabricMasterId();
                String changedFabricCodeQuality = detail.getChangedFabricCodeQuality();
                
                try {
                    // Update BeamInwardBeamDetails
                    beamInwardBeamDetailsRepository.updateChangedFabricDataBySizedBeamNo(
                        sizedBeamNo, changedFabricMasterId, changedFabricCodeQuality);
                } catch (Exception e) {
                    // Log error but continue with other updates
                    System.err.println("Error updating BeamInwardBeamDetails for beamNo: " + sizedBeamNo + ", error: " + e.getMessage());
                }
                
                try {
                    // Update FactoryBeamInwardBeamDetails
                    factoryBeamInwardBeamDetailsRepository.updateChangedFabricDataBySizedBeamNo(
                        sizedBeamNo, changedFabricMasterId, changedFabricCodeQuality);
                } catch (Exception e) {
                    // Log error but continue with other updates
                    System.err.println("Error updating FactoryBeamInwardBeamDetails for beamNo: " + sizedBeamNo + ", error: " + e.getMessage());
                }
                
try {
                    // Update BeamOutwardDetail
                    beamOutwardDetailRepository.updateChangedFabricDataBySizedBeamNo(
                        sizedBeamNo, changedFabricMasterId, changedFabricCodeQuality);
                } catch (Exception e) {
                    // Log error but continue with other updates
                    System.err.println("Error updating BeamOutwardDetail for beamNo: " + sizedBeamNo + ", error: " + e.getMessage());
                }
            }
        }
    }
}
