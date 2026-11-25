package com.tech3.erp.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.FactoryBeamInwardBeamDetailsDTO;
import com.tech3.erp.dto.FactoryBeamInwardDTO;
import com.tech3.erp.dto.FactoryBeamInwardQualityDetailsDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.FactoryBeamInward;
import com.tech3.erp.entity.FactoryBeamInwardBeamDetails;
import com.tech3.erp.entity.FactoryBeamInwardQualityDetails;
import com.tech3.erp.entity.SizingBeamDetails;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.SizingQualityDetails;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.repository.FactoryBeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.FactoryBeamInwardQualityDetailsRepository;
import com.tech3.erp.repository.FactoryBeamInwardRepository;
import com.tech3.erp.repository.SizingBeamDetailsRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.SizingQualityDetailsRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.BeamGatingDetailsRepository;
import com.tech3.erp.repository.BeamCutBeamDetailsRepository;
import com.tech3.erp.repository.WeavingProductionRepository;
import com.tech3.erp.repository.FlangeRepository;
import com.tech3.erp.service.WeavingWorkOrderService;
import com.tech3.erp.util.PaginationHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

@Service
public class FactoryBeamInwardService {

    private final FactoryBeamInwardRepository beamInwardRepository;
    private final FactoryBeamInwardBeamDetailsRepository beamDetailsRepository;
    private final FactoryBeamInwardQualityDetailsRepository qualityDetailsRepository;
    private final SizingBeamDetailsRepository sizingBeamDetailsRepository;
    private final SizingQualityDetailsRepository sizingQualityDetailsRepository;
    private final FlangeRepository flangeRepository;
    private final BeamGatingDetailsRepository beamGatingDetailsRepository;
    private final BeamCutBeamDetailsRepository beamCutBeamDetailsRepository;
    private final WeavingProductionRepository weavingProductionRepository;
    private final ReferenceValidationService referenceValidationService;
    private final SizingPlanRepository sizingPlanRepository;
    private final WeavingContractRepository weavingContractRepository;
    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
    private final WeavingWorkOrderService weavingWorkOrderService;

    @Lazy
    @Autowired
    private BeamOutwardService beamOutwardService;

    public FactoryBeamInwardService(
            FactoryBeamInwardRepository beamInwardRepository,
            FactoryBeamInwardBeamDetailsRepository beamDetailsRepository,
            FactoryBeamInwardQualityDetailsRepository qualityDetailsRepository,
            SizingBeamDetailsRepository sizingBeamDetailsRepository,
            SizingQualityDetailsRepository sizingQualityDetailsRepository,
            FlangeRepository flangeRepository,
            BeamGatingDetailsRepository beamGatingDetailsRepository,
            BeamCutBeamDetailsRepository beamCutBeamDetailsRepository,
            WeavingProductionRepository weavingProductionRepository,
            ReferenceValidationService referenceValidationService,
            SizingPlanRepository sizingPlanRepository,
            WeavingContractRepository weavingContractRepository,
            WeavingWorkOrderRepository weavingWorkOrderRepository,
            WeavingWorkOrderService weavingWorkOrderService
    ) {
        this.beamInwardRepository = beamInwardRepository;
        this.beamDetailsRepository = beamDetailsRepository;
        this.qualityDetailsRepository = qualityDetailsRepository;
        this.sizingBeamDetailsRepository = sizingBeamDetailsRepository;
        this.sizingQualityDetailsRepository = sizingQualityDetailsRepository;
        this.flangeRepository = flangeRepository;
        this.beamGatingDetailsRepository = beamGatingDetailsRepository;
        this.beamCutBeamDetailsRepository = beamCutBeamDetailsRepository;
        this.weavingProductionRepository = weavingProductionRepository;
        this.referenceValidationService = referenceValidationService;
        this.sizingPlanRepository = sizingPlanRepository;
        this.weavingContractRepository = weavingContractRepository;
        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
        this.weavingWorkOrderService = weavingWorkOrderService;
    }

    @Transactional
    public FactoryBeamInwardDTO saveBeamInward(FactoryBeamInwardDTO dto) {
        FactoryBeamInward plan = mapDtoToEntity(dto, new FactoryBeamInward());
        requireSizingPlanReference(plan.getSizingPlanId());
        requireWeavingContractReference(plan.getWeavingContractId());
        requireWeavingWorkOrderReference(toIdentifier(plan.getWeavingWorkOrderId()));
        
        boolean isExternalOrder = plan.getOrderTypeId() != null && plan.getOrderTypeId().longValue() == 2L;
        plan.setIsBeamPending(isExternalOrder ? 0 : 1);
        
        final FactoryBeamInward savedBeamInward = beamInwardRepository.save(plan);

        if (dto.getBeamInwardBeamDetails() != null && !dto.getBeamInwardBeamDetails().isEmpty()) {
            List<FactoryBeamInwardBeamDetails> beamEntities = dto.getBeamInwardBeamDetails().stream().map(b -> {
                FactoryBeamInwardBeamDetails beam = new FactoryBeamInwardBeamDetails();
                beam.setBeamInward(savedBeamInward);
                beam.setSizedBeamNo(b.getSizedBeamNo());
                beam.setWrapMeters(b.getWrapMeters());
                beam.setShrinkage(b.getShrinkage());
                beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
                beam.setSizedBeamNo(b.getSizedBeamNo());
                beam.setFlangeNo(b.getFlangeNo());
                beam.setFlangeId(b.getFlangeId());
                beam.setActualEnds(b.getActualEnds());
                beam.setFabricPanelWidth(b.getFabricPanelWidth());
                beam.setWarpCount(b.getWarpCount());
                beam.setWarpYarnMillName(b.getWarpYarnMillName());
                beam.setWarpYarnLotNo(b.getWarpYarnLotNo());
                return beam;
            }).collect(Collectors.toList());
            beamDetailsRepository.saveAll(beamEntities);
            List<Long> flangeIds = dto.getBeamInwardBeamDetails().stream()
                    .map(FactoryBeamInwardBeamDetailsDTO::getFlangeId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            if (!flangeIds.isEmpty()) {
                flangeRepository.markInwardedByIds(flangeIds);
            }
        }

        if (dto.getBeamInwardQualityDetails() != null && !dto.getBeamInwardQualityDetails().isEmpty()) {
            List<FactoryBeamInwardQualityDetails> qualityEntities = dto.getBeamInwardQualityDetails().stream().map(q -> {
                FactoryBeamInwardQualityDetails quality = new FactoryBeamInwardQualityDetails();
                quality.setBeamInward(savedBeamInward);
                quality.setQuality(q.getQuality());
                quality.setYarnId(q.getYarnId());
                quality.setSordEnds(q.getSordEnds());
                quality.setActualEnds(q.getActualEnds());
                quality.setParts(q.getParts());
                quality.setEndsPerPart(q.getEndsPerPart());
                quality.setWrapMeters(q.getWrapMeters());
                quality.setFabricCode(q.getFabricCode());
                quality.setActualYarn(q.getActualYarn());
                quality.setWarpEnds(q.getWarpEnds());
                quality.setActualWarpEnds(q.getActualWarpEnds());
             
                return quality;
            }).collect(Collectors.toList());
            qualityDetailsRepository.saveAll(qualityEntities);
        }

        markWeavingWorkOrderReference(savedBeamInward.getWeavingWorkOrderId());
        FactoryBeamInwardDTO savedDto = getBeamInwardById(savedBeamInward.getId());
        if (!SyncContext.isInProgress()) {
            try {
                SyncContext.setInProgress(true);
                syncToBeamOutward(savedBeamInward.getId());
            } finally {
                SyncContext.clear();
            }
        }
        return savedDto;
    }

    private void updateSizingBeamStatus(Long sizingPlanId, FactoryBeamInwardBeamDetailsDTO beamDto) {
        // No-op: identifiers removed from beam details; preserved for compatibility
    }

    public List<FactoryBeamInwardDTO> getAllBeamInward() {
        List<FactoryBeamInward> plans = beamInwardRepository.findAll();
        return plans.stream()
                .sorted(Comparator.comparing(FactoryBeamInward::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FactoryBeamInwardDTO> getAllBeamInwardWithPagination(Long offset, Long limit, Long factoryCompanyId) {
        List<FactoryBeamInwardDTO> list = factoryCompanyId == null
                ? getAllBeamInward()
                : getByFactoryCompanyId(factoryCompanyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<FactoryBeamInwardDTO> getByFactoryCompanyId(Long factoryCompanyId) {
        List<FactoryBeamInward> plans = beamInwardRepository.findByFactoryCompanyId(factoryCompanyId);
        return plans.stream()
                .sorted(Comparator.comparing(FactoryBeamInward::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FactoryBeamInwardDTO> getByFactoryCompanyIdWithPagination(Long factoryCompanyId, Long offset, Long limit) {
        List<FactoryBeamInwardDTO> list = getByFactoryCompanyId(factoryCompanyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public FactoryBeamInwardDTO getBeamInwardById(Long id) {
        FactoryBeamInward plan = beamInwardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("FactoryBeamInward not found with id: " + id));
        return mapEntityToDto(plan);
    }

    @Transactional
    public FactoryBeamInwardDTO updateBeamInward(Long id, FactoryBeamInwardDTO dto) {
        return updateBeamInward(id, dto, null);
    }

    @Transactional
    public FactoryBeamInwardDTO updateBeamInward(Long id, FactoryBeamInwardDTO dto, Long factoryCompanyId) {
        if (SyncContext.isInProgress()) {
            return doUpdateBeamInward(id, dto, factoryCompanyId);
        }
        try {
            SyncContext.setInProgress(true);
            FactoryBeamInwardDTO updated = doUpdateBeamInward(id, dto, factoryCompanyId);
            syncToBeamOutward(id);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private FactoryBeamInwardDTO doUpdateBeamInward(Long id, FactoryBeamInwardDTO dto, Long factoryCompanyId) {
        FactoryBeamInward existingPlan = beamInwardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("FactoryBeamInward not found with id: " + id));
        if (factoryCompanyId != null && (existingPlan.getFactoryCompanyId() == null || !factoryCompanyId.equals(existingPlan.getFactoryCompanyId()))) {
            throw new EntityNotFoundException("FactoryBeamInward not found for the specified factoryCompanyId");
        }
        mapDtoToEntity(dto, existingPlan);
        requireSizingPlanReference(existingPlan.getSizingPlanId());
        requireWeavingContractReference(existingPlan.getWeavingContractId());
        requireWeavingWorkOrderReference(toIdentifier(existingPlan.getWeavingWorkOrderId()));
        
        boolean isExternalOrder = existingPlan.getOrderTypeId() != null && existingPlan.getOrderTypeId().longValue() == 2L;
        if (isExternalOrder) {
            existingPlan.setIsBeamPending(0);
        } else {
            existingPlan.setIsBeamPending(1);
        }
        
        beamInwardRepository.save(existingPlan);
        markWeavingWorkOrderReference(existingPlan.getWeavingWorkOrderId());

        // Update beam details selectively - only update fields that are provided in the DTO
        if (dto.getBeamInwardBeamDetails() != null && !dto.getBeamInwardBeamDetails().isEmpty()) {
            List<FactoryBeamInwardBeamDetails> existingBeamDetails = beamDetailsRepository.findByBeamInwardId(id);
            
            // Create a map of existing beam details by ID for easy lookup
            Map<Long, FactoryBeamInwardBeamDetails> existingBeamMap = existingBeamDetails.stream()
                    .collect(Collectors.toMap(FactoryBeamInwardBeamDetails::getId, Function.identity()));
            
            List<FactoryBeamInwardBeamDetails> beamEntitiesToSave = new ArrayList<>();
            
            for (FactoryBeamInwardBeamDetailsDTO beamDto : dto.getBeamInwardBeamDetails()) {
                FactoryBeamInwardBeamDetails beam;
                
                if (beamDto.getId() != null && existingBeamMap.containsKey(beamDto.getId())) {
                    // Update existing beam detail
                    beam = existingBeamMap.get(beamDto.getId());
                    if (beamDto.getWrapMeters() != null) beam.setWrapMeters(beamDto.getWrapMeters());
                    if (beamDto.getShrinkage() != null) beam.setShrinkage(beamDto.getShrinkage());
                    if (beamDto.getExpectedFabricMeter() != null) beam.setExpectedFabricMeter(beamDto.getExpectedFabricMeter());
                    if (beamDto.getSizedBeamNo() != null) beam.setSizedBeamNo(beamDto.getSizedBeamNo());
                    if (beamDto.getFlangeNo() != null) beam.setFlangeNo(beamDto.getFlangeNo());
                    if (beamDto.getFlangeId() != null) beam.setFlangeId(beamDto.getFlangeId());
                    if (beamDto.getActualEnds() != null) beam.setActualEnds(beamDto.getActualEnds());
                    if (beamDto.getFabricPanelWidth() != null) beam.setFabricPanelWidth(beamDto.getFabricPanelWidth());
                    if (beamDto.getWarpCount() != null) beam.setWarpCount(beamDto.getWarpCount());
                    if (beamDto.getWarpYarnMillName() != null) beam.setWarpYarnMillName(beamDto.getWarpYarnMillName());
                    if (beamDto.getWarpYarnLotNo() != null) beam.setWarpYarnLotNo(beamDto.getWarpYarnLotNo());
                } else {
                    // Create new beam detail
                    beam = new FactoryBeamInwardBeamDetails();
                    beam.setBeamInward(existingPlan);
                    beam.setWrapMeters(beamDto.getWrapMeters());
                    beam.setShrinkage(beamDto.getShrinkage());
                    beam.setExpectedFabricMeter(beamDto.getExpectedFabricMeter());
                    beam.setSizedBeamNo(beamDto.getSizedBeamNo());
                    beam.setFlangeNo(beamDto.getFlangeNo());
                    beam.setFlangeId(beamDto.getFlangeId());
                    beam.setActualEnds(beamDto.getActualEnds());
                    beam.setFabricPanelWidth(beamDto.getFabricPanelWidth());
                    beam.setWarpCount(beamDto.getWarpCount());
                    beam.setWarpYarnMillName(beamDto.getWarpYarnMillName());
                    beam.setWarpYarnLotNo(beamDto.getWarpYarnLotNo());
                }
                beamEntitiesToSave.add(beam);
            }
            
            beamDetailsRepository.saveAll(beamEntitiesToSave);
            List<Long> flangeIds = dto.getBeamInwardBeamDetails().stream()
                    .map(FactoryBeamInwardBeamDetailsDTO::getFlangeId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            if (!flangeIds.isEmpty()) {
                flangeRepository.markInwardedByIds(flangeIds);
            }
        }

        // Update quality details selectively - only update fields that are provided in the DTO
        if (dto.getBeamInwardQualityDetails() != null && !dto.getBeamInwardQualityDetails().isEmpty()) {
            List<FactoryBeamInwardQualityDetails> existingQualityDetails = qualityDetailsRepository.findByBeamInwardId(id);
            
            // Create a map of existing quality details by ID for easy lookup
            Map<Long, FactoryBeamInwardQualityDetails> existingQualityMap = existingQualityDetails.stream()
                    .collect(Collectors.toMap(FactoryBeamInwardQualityDetails::getId, Function.identity()));
            
            List<FactoryBeamInwardQualityDetails> qualityEntitiesToSave = new ArrayList<>();
            
            for (FactoryBeamInwardQualityDetailsDTO qualityDto : dto.getBeamInwardQualityDetails()) {
                FactoryBeamInwardQualityDetails quality;
                
                if (qualityDto.getId() != null && existingQualityMap.containsKey(qualityDto.getId())) {
                    // Update existing quality detail
                    quality = existingQualityMap.get(qualityDto.getId());
                    if (qualityDto.getQuality() != null) quality.setQuality(qualityDto.getQuality());
                    if (qualityDto.getYarnId() != null) quality.setYarnId(qualityDto.getYarnId());
                    if (qualityDto.getSordEnds() != null) quality.setSordEnds(qualityDto.getSordEnds());
                    if (qualityDto.getActualEnds() != null) quality.setActualEnds(qualityDto.getActualEnds());
                    if (qualityDto.getParts() != null) quality.setParts(qualityDto.getParts());
                    if (qualityDto.getEndsPerPart() != null) quality.setEndsPerPart(qualityDto.getEndsPerPart());
                    if (qualityDto.getWrapMeters() != null) quality.setWrapMeters(qualityDto.getWrapMeters());
                    if (qualityDto.getFabricCode() != null) quality.setFabricCode(qualityDto.getFabricCode());
                    if (qualityDto.getActualYarn() != null) quality.setActualYarn(qualityDto.getActualYarn());
                    if (qualityDto.getWarpEnds() != null) quality.setWarpEnds(qualityDto.getWarpEnds());
                    if (qualityDto.getActualWarpEnds() != null) quality.setActualWarpEnds(qualityDto.getActualWarpEnds());
                } else {
                    // Create new quality detail
                    quality = new FactoryBeamInwardQualityDetails();
                    quality.setBeamInward(existingPlan);
                    quality.setQuality(qualityDto.getQuality());
                    quality.setYarnId(qualityDto.getYarnId());
                    quality.setSordEnds(qualityDto.getSordEnds());
                    quality.setActualEnds(qualityDto.getActualEnds());
                    quality.setParts(qualityDto.getParts());
                    quality.setEndsPerPart(qualityDto.getEndsPerPart());
                    quality.setWrapMeters(qualityDto.getWrapMeters());
                    quality.setFabricCode(qualityDto.getFabricCode());
                    quality.setActualYarn(qualityDto.getActualYarn());
                    quality.setWarpEnds(qualityDto.getWarpEnds());
                    quality.setActualWarpEnds(qualityDto.getActualWarpEnds());
                }
                qualityEntitiesToSave.add(quality);
            }
            
            qualityDetailsRepository.saveAll(qualityEntitiesToSave);
        }

        return getBeamInwardById(existingPlan.getId());
    }

    private void syncToBeamOutward(Long factoryInwardId) {
        if (factoryInwardId == null) return;
        FactoryBeamInward saved = beamInwardRepository.findById(factoryInwardId).orElse(null);
        if (saved == null || saved.getBeamOutwardId() == null) return;

        try {
            com.tech3.erp.dto.BeamOutwardDTO outward = beamOutwardService.getById(saved.getBeamOutwardId());
            if (outward != null) {
                List<FactoryBeamInwardBeamDetails> beams = beamDetailsRepository.findByBeamInwardId(saved.getId());
                if (!beams.isEmpty()) {
                    FactoryBeamInwardBeamDetails fb = beams.get(0);
                    outward.setWrapMeters(fb.getWrapMeters());
                    outward.setShrinkage(fb.getShrinkage());
                    outward.setExpectedFabricMeter(fb.getExpectedFabricMeter());
                    outward.setFabricPanelWidth(fb.getFabricPanelWidth());
                    outward.setSizedBeamNo(fb.getSizedBeamNo());
                    outward.setFlangeNo(fb.getFlangeNo());
                    outward.setFlangeId(fb.getFlangeId());
                    
                    beamOutwardService.update(outward.getId(), outward);
                    beamOutwardService.syncToBeamInward(outward.getId());
                }
            }
        } catch (Exception e) {
            // Log or skip
        }
    }

    @Transactional
    public void deleteBeamInward(Long id) {
        FactoryBeamInward entity = beamInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FactoryBeamInward not found with id: " + id));
        deleteBeamInwardEntity(entity);
    }

    @Transactional
    public void deleteBeamInward(Long id, Long factoryCompanyId) {
        FactoryBeamInward entity = beamInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FactoryBeamInward not found with id: " + id));
        if (factoryCompanyId != null && (entity.getFactoryCompanyId() == null || !factoryCompanyId.equals(entity.getFactoryCompanyId()))) {
            throw new EntityNotFoundException("FactoryBeamInward not found for the specified factoryCompanyId");
        }
        deleteBeamInwardEntity(entity);
    }

    private void requireSizingPlanReference(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return;
        }
        Optional<SizingPlan> sizingPlanOpt = sizingPlanRepository.findById(sizingPlanId);
        referenceValidationService.requireReference(
                sizingPlanId,
                () -> sizingPlanOpt,
                "Sizing Plan",
                sizingPlanOpt.map(SizingPlan::getSizingPlanNo).filter(StringUtils::hasText).orElse(null));
    }

    private void requireWeavingContractReference(String weavingContractId) {
        if (!StringUtils.hasText(weavingContractId)) {
            return;
        }
        Optional<WeavingContract> weavingContractOpt = findWeavingContract(weavingContractId);
        referenceValidationService.requireReference(
                weavingContractId,
                () -> weavingContractOpt,
                "Weaving Contract",
                weavingContractOpt.map(WeavingContract::getWeavingContractNo).filter(StringUtils::hasText).orElse(weavingContractId));
    }

    private Optional<WeavingContract> findWeavingContract(String weavingContractIdentifier) {
        Long parsedId = tryParseLong(weavingContractIdentifier);
        if (parsedId != null) {
            Optional<WeavingContract> found = weavingContractRepository.findById(parsedId);
            if (found.isPresent()) {
                return found;
            }
        }
        return weavingContractRepository.findByWeavingContractNo(weavingContractIdentifier);
    }

    private Long tryParseLong(String value) {
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private void requireWeavingWorkOrderReference(String weavingWorkOrderIdentifier) {
        if (!StringUtils.hasText(weavingWorkOrderIdentifier)) {
            return;
        }
        Optional<WeavingWorkOrder> weavingWorkOrderOpt = findWeavingWorkOrder(weavingWorkOrderIdentifier);
        referenceValidationService.requireReference(
                weavingWorkOrderIdentifier,
                () -> weavingWorkOrderOpt,
                "Weaving Work Order",
                weavingWorkOrderOpt.map(WeavingWorkOrder::getWeavingWorkOrderNo).filter(StringUtils::hasText).orElse(null));
    }

    private Optional<WeavingWorkOrder> findWeavingWorkOrder(String weavingWorkOrderIdentifier) {
        Long parsedId = tryParseLong(weavingWorkOrderIdentifier);
        if (parsedId != null) {
            Optional<WeavingWorkOrder> found = weavingWorkOrderRepository.findById(parsedId);
            if (found.isPresent()) {
                return found;
            }
        }
        return weavingWorkOrderRepository.findByWeavingWorkOrderNo(weavingWorkOrderIdentifier);
    }

    private String toIdentifier(Long value) {
        return value != null ? value.toString() : null;
    }

    private void deleteBeamInwardEntity(FactoryBeamInward entity) {
        Long weavingWorkOrderId = entity.getWeavingWorkOrderId();
        ensureNoFactoryBeamInwardReferences(entity);
        List<FactoryBeamInwardBeamDetails> beamDetails = beamDetailsRepository.findByBeamInwardId(entity.getId());
        unmarkFlangesAsInwarded(beamDetails);
        try {
            beamInwardRepository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete FactoryBeamInward because it is referenced by other");
        }
        refreshWeavingWorkOrderReference(weavingWorkOrderId);
    }

    private void unmarkFlangesAsInwarded(List<FactoryBeamInwardBeamDetails> beamDetails) {
        if (beamDetails == null || beamDetails.isEmpty()) return;
        List<Long> flangeIds = beamDetails.stream()
                .map(FactoryBeamInwardBeamDetails::getFlangeId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (!flangeIds.isEmpty()) {
            flangeRepository.unmarkInwardedByIds(flangeIds);
        }
    }

    private void ensureNoFactoryBeamInwardReferences(FactoryBeamInward entity) {
        if (entity == null) {
            return;
        }
        Long beamId = entity.getBeamInwardId();
        String beamNo = hasText(entity.getBeamInwardNo()) ? entity.getBeamInwardNo().trim() : null;
        if (beamId != null) {
            String displayRef = beamNo != null ? beamNo : beamId.toString();
            if (beamGatingDetailsRepository.existsByBeamId(beamId)) {
                throw new IllegalStateException("Cannot delete FactoryBeamInward because beam_gating_details references beam_no " + displayRef);
            }
            if (beamCutBeamDetailsRepository.existsByBeamId(beamId)) {
                throw new IllegalStateException("Cannot delete FactoryBeamInward because beam_cut_beam_details references beam_no " + displayRef);
            }
            if (weavingProductionRepository.existsByBeamId(beamId)) {
                throw new IllegalStateException("Cannot delete FactoryBeamInward because weaving_production references beam_no " + displayRef);
            }
        }
        
        if (beamNo != null) {
            if (beamGatingDetailsRepository.existsByBeamNo(beamNo)) {
                throw new IllegalStateException("Cannot delete FactoryBeamInward because beam_gating_details references beam_no " + beamNo);
            }
            if (beamCutBeamDetailsRepository.existsByBeamNo(beamNo)) {
                throw new IllegalStateException("Cannot delete FactoryBeamInward because beam_cut_beam_details references beam_no " + beamNo);
            }
            if (weavingProductionRepository.existsByBeamNo(beamNo)) {
                throw new IllegalStateException("Cannot delete FactoryBeamInward because weaving_production references beam_no " + beamNo);
            }
        }

        if (entity.getBeamDetails() == null || entity.getBeamDetails().isEmpty()) {
            return;
        }

        for (FactoryBeamInwardBeamDetails detail : entity.getBeamDetails()) {
            if (detail == null) continue;
            Long detailId = detail.getId();
            String detailBeamNo = StringUtils.hasText(detail.getSizedBeamNo()) ? detail.getSizedBeamNo().trim() : null;
            if (detailId != null) {
                String detailRef = detailBeamNo != null ? detailBeamNo : detailId.toString();
                if (beamGatingDetailsRepository.existsByBeamId(detailId)) {
                    throw new IllegalStateException("Cannot delete FactoryBeamInward because beam_gating_details references beam_no " + detailRef);
                }
                if (beamCutBeamDetailsRepository.existsByBeamId(detailId)) {
                    throw new IllegalStateException("Cannot delete FactoryBeamInward because beam_cut_beam_details references beam_no " + detailRef);
                }
                if (weavingProductionRepository.existsByBeamId(detailId)) {
                    throw new IllegalStateException("Cannot delete FactoryBeamInward because weaving_production references beam_no " + detailRef);
                }
            }
            if (detailBeamNo != null) {
                if (beamGatingDetailsRepository.existsByBeamNo(detailBeamNo)) {
                    throw new IllegalStateException(detailBeamNo + " already mapped in beam_gating_details");
                }
                if (beamCutBeamDetailsRepository.existsByBeamNo(detailBeamNo)) {
                    throw new IllegalStateException(detailBeamNo + " already mapped in beam_cut_beam_details");
                }
                if (weavingProductionRepository.existsByBeamNo(detailBeamNo)) {
                    throw new IllegalStateException(detailBeamNo + " already mapped in weaving_production");
                }
            }
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private FactoryBeamInward mapDtoToEntity(FactoryBeamInwardDTO dto, FactoryBeamInward plan) {
        if (dto.getSizingVendorId() != null) plan.setSizingVendorId(dto.getSizingVendorId());
        if (dto.getSizingSetNo() != null) plan.setSizingSetNo(dto.getSizingSetNo());
        if (dto.getDcNo() != null) plan.setDcNo(dto.getDcNo());
        if (dto.getDcDate() != null) plan.setDcDate(dto.getDcDate());
        if (dto.getWeavingContractId() != null) plan.setWeavingContractId(dto.getWeavingContractId());
        if (dto.getWeavingWorkOrderId() != null) plan.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
        if (dto.getOrderTypeId() != null) plan.setOrderTypeId(dto.getOrderTypeId());
        if (dto.getRemarks() != null) plan.setRemarks(dto.getRemarks());
        if (dto.getBeamInwardNo() != null) plan.setBeamInwardNo(dto.getBeamInwardNo());
        if (dto.getBeamInwardDate() != null) plan.setBeamInwardDate(dto.getBeamInwardDate());
        if (dto.getSizingPlanId() != null) plan.setSizingPlanId(dto.getSizingPlanId());
        if (dto.getBeamInwardId() != null) plan.setBeamInwardId(dto.getBeamInwardId());
        if (dto.getBeamOutwardId() != null) plan.setBeamOutwardId(dto.getBeamOutwardId());
        if (dto.getIsBeamPending() != null) plan.setIsBeamPending(dto.getIsBeamPending());
        if (dto.getFabricCodeQuality() != null) plan.setFabricCodeQuality(dto.getFabricCodeQuality());
        if (dto.getTradingCompanyId() != null) plan.setTradingCompanyId(dto.getTradingCompanyId());
        if (dto.getFactoryCompanyId() != null) plan.setFactoryCompanyId(dto.getFactoryCompanyId());
        return plan;
    }

    private FactoryBeamInwardDTO mapEntityToDto(FactoryBeamInward plan) {
        FactoryBeamInwardDTO dto = new FactoryBeamInwardDTO();
        dto.setId(plan.getId());
        dto.setSizingVendorId(plan.getSizingVendorId());
        dto.setSizingSetNo(plan.getSizingSetNo());
        dto.setDcNo(plan.getDcNo());
        dto.setDcDate(plan.getDcDate());
        dto.setWeavingContractId(plan.getWeavingContractId());
        dto.setWeavingWorkOrderId(plan.getWeavingWorkOrderId());
        dto.setOrderTypeId(plan.getOrderTypeId());
        dto.setRemarks(plan.getRemarks());
        dto.setBeamInwardNo(plan.getBeamInwardNo());
        dto.setBeamInwardDate(plan.getBeamInwardDate());
        dto.setSizingPlanId(plan.getSizingPlanId());
        dto.setBeamInwardId(plan.getBeamInwardId());
        dto.setBeamOutwardId(plan.getBeamOutwardId());
        dto.setIsBeamPending(plan.getIsBeamPending());
        dto.setFabricCodeQuality(plan.getFabricCodeQuality());
        dto.setTradingCompanyId(plan.getTradingCompanyId());
        dto.setFactoryCompanyId(plan.getFactoryCompanyId());

        var beamDtos = beamDetailsRepository.findByBeamInwardId(plan.getId()).stream().map(b -> {
            FactoryBeamInwardBeamDetailsDTO bdto = new FactoryBeamInwardBeamDetailsDTO();
            bdto.setId(b.getId());
            bdto.setWrapMeters(b.getWrapMeters());
            bdto.setShrinkage(b.getShrinkage());
            bdto.setExpectedFabricMeter(b.getExpectedFabricMeter());
            bdto.setSizedBeamNo(b.getSizedBeamNo());
            bdto.setFlangeNo(b.getFlangeNo());
            bdto.setFlangeId(b.getFlangeId());
            bdto.setActualEnds(b.getActualEnds());
            bdto.setFabricPanelWidth(b.getFabricPanelWidth());
            bdto.setIsProductionCompleted(b.getIsProductionCompleted());
            bdto.setIsBeamCut(b.getIsBeamCut() == null ? 0 : b.getIsBeamCut());
            bdto.setWarpCount(b.getWarpCount());
            bdto.setWarpYarnMillName(b.getWarpYarnMillName());
            bdto.setWarpYarnLotNo(b.getWarpYarnLotNo());
            return bdto;
        }).collect(Collectors.toList());

        var qualityDtos = qualityDetailsRepository.findByBeamInwardId(plan.getId()).stream().map(q -> {
            FactoryBeamInwardQualityDetailsDTO qdto = new FactoryBeamInwardQualityDetailsDTO();
            qdto.setId(q.getId());
            qdto.setQuality(q.getQuality());
            qdto.setYarnId(q.getYarnId());
            qdto.setSordEnds(q.getSordEnds());
            qdto.setActualEnds(q.getActualEnds());
            qdto.setParts(q.getParts());
            qdto.setEndsPerPart(q.getEndsPerPart());
            qdto.setWrapMeters(q.getWrapMeters());
            qdto.setFabricCode(q.getFabricCode());
            qdto.setActualYarn(q.getActualYarn());
            qdto.setWarpEnds(q.getWarpEnds());
            qdto.setActualWarpEnds(q.getActualWarpEnds());
            return qdto;
        }).collect(Collectors.toList());

        if (plan.getSizingPlanId() != null && !qualityDtos.isEmpty()) {
            List<SizingQualityDetails> sizingQ = sizingQualityDetailsRepository.findBySizingPlanId(plan.getSizingPlanId());
            Map<String, SizingQualityDetails> byQuality = sizingQ.stream().filter(s -> s.getQuality() != null).collect(Collectors.toMap(SizingQualityDetails::getQuality, Function.identity(), (a,b) -> a));
            for (FactoryBeamInwardQualityDetailsDTO qdto : qualityDtos) {
                SizingQualityDetails match = byQuality.get(qdto.getQuality());
                if (match != null) {
                    if (qdto.getFabricCode() == null) qdto.setFabricCode(match.getFabricCode());
                    if (qdto.getActualYarn() == null) qdto.setActualYarn(match.getActualYarn());
                    if (qdto.getWarpEnds() == null) qdto.setWarpEnds(match.getWarpEnds());
                    if (qdto.getActualWarpEnds() == null) qdto.setActualWarpEnds(match.getActualWarpEnds());
                }
            }
        }

        dto.setBeamInwardBeamDetails(beamDtos);
        dto.setBeamInwardQualityDetails(qualityDtos);
        return dto;
    }

    private void markWeavingWorkOrderReference(Long weavingWorkOrderId) {
        if (weavingWorkOrderId != null) {
            weavingWorkOrderService.markAsOrderIdMapped(weavingWorkOrderId);
        }
    }

    private void refreshWeavingWorkOrderReference(Long weavingWorkOrderId) {
        if (weavingWorkOrderId != null) {
            weavingWorkOrderService.refreshOrderIdMappedStatus(weavingWorkOrderId);
        }
    }
}
