package com.tech3.erp.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingProductionDTO;
import com.tech3.erp.dto.SizingProductionDetailDTO;
import com.tech3.erp.dto.SizingProductionSummaryDTO;
import com.tech3.erp.entity.SizingJobWorkOrder;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.SizingProduction;
import com.tech3.erp.entity.SizingProductionDetail;
import com.tech3.erp.repository.FlangeBeamInwardDetailRepository;
import com.tech3.erp.repository.SizingJobWorkOrderRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.SizingProductionDetailRepository;
import com.tech3.erp.repository.SizingProductionRepository;
import com.tech3.erp.repository.BeamInwardRepository;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.FactoryBeamInwardRepository;
import com.tech3.erp.repository.SizingBeamOutwardRepository;
import com.tech3.erp.service.ReferenceValidationService;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SizingProductionService {

    private final SizingProductionRepository repository;
    private final SizingProductionDetailRepository detailRepository;
    private final FlangeBeamInwardDetailRepository flangeBeamInwardDetailRepository;
    private final SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    private final SizingPlanRepository sizingPlanRepository;
    private final ReferenceValidationService referenceValidationService;
    private final BeamInwardRepository beamInwardRepository;
    private final BeamOutwardRepository beamOutwardRepository;
    private final FactoryBeamInwardRepository factoryBeamInwardRepository;
    private final SizingBeamOutwardRepository sizingBeamOutwardRepository;

    @Lazy
    @Autowired
    private BeamInwardService beamInwardService;

    @Lazy
    @Autowired
    private FactoryBeamInwardService factoryBeamInwardService;

    @Lazy
    @Autowired
    private BeamOutwardService beamOutwardService;

    @Lazy
    @Autowired
    private SizingBeamOutwardService sizingBeamOutwardService;

    public SizingProductionService(SizingProductionRepository repository,
            SizingProductionDetailRepository detailRepository,
            FlangeBeamInwardDetailRepository flangeBeamInwardDetailRepository,
            SizingJobWorkOrderRepository sizingJobWorkOrderRepository,
            SizingPlanRepository sizingPlanRepository,
            ReferenceValidationService referenceValidationService,
            BeamInwardRepository beamInwardRepository,
            BeamOutwardRepository beamOutwardRepository,
            FactoryBeamInwardRepository factoryBeamInwardRepository,
            SizingBeamOutwardRepository sizingBeamOutwardRepository) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.flangeBeamInwardDetailRepository = flangeBeamInwardDetailRepository;
        this.sizingJobWorkOrderRepository = sizingJobWorkOrderRepository;
        this.sizingPlanRepository = sizingPlanRepository;
        this.referenceValidationService = referenceValidationService;
        this.beamInwardRepository = beamInwardRepository;
        this.beamOutwardRepository = beamOutwardRepository;
        this.factoryBeamInwardRepository = factoryBeamInwardRepository;
        this.sizingBeamOutwardRepository = sizingBeamOutwardRepository;
    }

    @Transactional
    public SizingProductionDTO create(SizingProductionDTO dto) {
        requireSizingOrderReference(dto.getSizingOrderId());
        requireSizingPlanReference(dto.getSizingPlanId());
        SizingProduction entity = new SizingProduction();
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        SizingProduction saved = repository.save(entity);
        updateFlangeIssuedStatuses(Collections.emptyList(), collectFlangeIds(entity.getProductionDetails()),
                Collections.emptyList(), collectFlangeNos(entity.getProductionDetails()));
        
        SizingProductionDTO savedDto = mapEntityToDto(saved);
        if (!SyncContext.isInProgress()) {
            try {
                SyncContext.setInProgress(true);
                syncToRelatedEntities(saved.getId());
            } finally {
                SyncContext.clear();
            }
        }
        return savedDto;
    }

    public SizingProductionDTO getById(Long id) {
        SizingProduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sizing production not found"));
        return mapEntityToDto(entity);
    }

    public List<SizingProductionDTO> getAll() {
        return repository.findAllByOrderByIdDesc().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<SizingProductionDTO> getAllWithPagination(Long offset, Long limit) {
        List<SizingProductionDTO> list = getAll();
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<SizingProductionDTO> getByCompanyId(Long companyId) {
        return repository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<SizingProductionDTO> getByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<SizingProductionDTO> list = getByCompanyId(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    @Transactional
    public SizingProductionDTO update(Long id, SizingProductionDTO dto) {
        if (SyncContext.isInProgress()) {
            return doUpdate(id, dto);
        }
        try {
            SyncContext.setInProgress(true);
            SizingProductionDTO updated = doUpdate(id, dto);
            syncToRelatedEntities(id);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private SizingProductionDTO doUpdate(Long id, SizingProductionDTO dto) {
        SizingProduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sizing production not found"));
        List<Long> previousFlangeIds = collectFlangeIds(entity.getProductionDetails());
        List<String> previousFlangeNos = collectFlangeNos(entity.getProductionDetails());
        requireSizingOrderReference(dto.getSizingOrderId());
        requireSizingPlanReference(dto.getSizingPlanId());
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        repository.save(entity);
        updateFlangeIssuedStatuses(previousFlangeIds, collectFlangeIds(entity.getProductionDetails()),
                previousFlangeNos, collectFlangeNos(entity.getProductionDetails()));
        return mapEntityToDto(entity);
    }

    @Transactional
    public SizingProductionDTO updateByCompanyId(Long id, Long companyId, SizingProductionDTO dto) {
        if (SyncContext.isInProgress()) {
            return doUpdateByCompanyId(id, companyId, dto);
        }
        try {
            SyncContext.setInProgress(true);
            SizingProductionDTO updated = doUpdateByCompanyId(id, companyId, dto);
            syncToRelatedEntities(id);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private SizingProductionDTO doUpdateByCompanyId(Long id, Long companyId, SizingProductionDTO dto) {
        SizingProduction entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Sizing production not found"));
        List<Long> previousFlangeIds = collectFlangeIds(entity.getProductionDetails());
        List<String> previousFlangeNos = collectFlangeNos(entity.getProductionDetails());
        requireSizingOrderReference(dto.getSizingOrderId());
        requireSizingPlanReference(dto.getSizingPlanId());
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        repository.save(entity);
        updateFlangeIssuedStatuses(previousFlangeIds, collectFlangeIds(entity.getProductionDetails()),
                previousFlangeNos, collectFlangeNos(entity.getProductionDetails()));
        return mapEntityToDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        SizingProduction entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sizing production not found with id: " + id));
        List<Long> previousFlangeIds = collectFlangeIds(entity.getProductionDetails());
        List<String> previousFlangeNos = collectFlangeNos(entity.getProductionDetails());
        try {
            repository.delete(entity);
            updateFlangeIssuedStatuses(previousFlangeIds, Collections.emptyList(),
                    previousFlangeNos, Collections.emptyList());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Sizing production because it is referenced by other");
        }
    }

    @Transactional
    public void deleteByCompanyId(Long id, Long companyId) {
        SizingProduction entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Sizing production not found with id: " + id));
        List<Long> previousFlangeIds = collectFlangeIds(entity.getProductionDetails());
        List<String> previousFlangeNos = collectFlangeNos(entity.getProductionDetails());
        try {
            repository.delete(entity);
            updateFlangeIssuedStatuses(previousFlangeIds, Collections.emptyList(),
                    previousFlangeNos, Collections.emptyList());
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Sizing production because it is referenced by other");
        }
    }

    private void mapDtoToEntity(SizingProductionDTO dto, SizingProduction entity) {
        entity.setSizingDate(dto.getSizingDate());
        entity.setSizingOrderId(dto.getSizingOrderId());
        entity.setOrderTypeId(dto.getOrderTypeId());
        entity.setSizingPlanId(dto.getSizingPlanId());
        entity.setSizingPlanNo(dto.getSizingPlanNo());
        entity.setBuyerId(dto.getBuyerId());
        entity.setBuyerName(dto.getBuyerName());
        entity.setManualSetNo(dto.getManualSetNo());
        entity.setSizingProductionNo(dto.getSizingProductionNo());
        entity.setYarnId(dto.getYarnId());
        entity.setYarnName(dto.getYarnName());
        entity.setMachineId(dto.getMachineId());
        entity.setMachineName(dto.getMachineName());
        entity.setCompanyId(dto.getCompanyId());

        Integer noOfBeams = dto.getNoOfBeams();
        Double totalMeters = dto.getTotalMeters();
        Double totalNetWeight = dto.getTotalNetWeight();
        Integer sizingCount = dto.getSizingCount();

        SizingProductionSummaryDTO summary = dto.getSummary();
        if (summary != null) {
            if (summary.getNoOfBeams() != null) {
                noOfBeams = summary.getNoOfBeams();
            }
            if (summary.getTotalMeters() != null) {
                totalMeters = summary.getTotalMeters();
            }
            if (summary.getTotalNetWeight() != null) {
                totalNetWeight = summary.getTotalNetWeight();
            }
            if (summary.getSizingCount() != null) {
                sizingCount = summary.getSizingCount();
            }
        }

        entity.setNoOfBeams(noOfBeams);
        entity.setTotalMeters(totalMeters);
        entity.setTotalNetWeight(totalNetWeight);
        entity.setSizingCount(sizingCount);
    }

    private void replaceDetails(SizingProduction entity, List<SizingProductionDetailDTO> details) {
        Map<Long, SizingProductionDetail> existingMap = new HashMap<>();
        if (entity.getProductionDetails() != null) {
            for (SizingProductionDetail d : entity.getProductionDetails()) {
                if (d.getId() != null) {
                    existingMap.put(d.getId(), d);
                }
            }
        }
        
        entity.getProductionDetails().clear();
        if (details == null || details.isEmpty()) {
            return;
        }
        for (SizingProductionDetailDTO dto : details) {
            SizingProductionDetail detail = new SizingProductionDetail();
            detail.setSizingProduction(entity);
            detail.setShiftName(dto.getShiftName());
            detail.setFlangeNo(dto.getFlangeNo());
            detail.setFlangeId(dto.getFlangeId());
            detail.setTotalEnds(dto.getTotalEnds());
            detail.setSizedBeamNo(dto.getSizedBeamNo());
            detail.setBeamMeters(dto.getBeamMeters());
            detail.setExpectedFabricMeter(dto.getExpectedFabricMeter());
            detail.setSizingPickup(dto.getSizingPickup());
            detail.setBreakages(dto.getBreakages());
            detail.setRpm(dto.getRpm());
            detail.setTareWt(dto.getTareWt());
            detail.setGrossWt(dto.getGrossWt());
            detail.setNetWt(dto.getNetWt());
            detail.setFabricPanelWidth(dto.getFabricPanelWidth());
            detail.setEmployeeId(dto.getEmployeeId());
            detail.setOrderTypeId(dto.getOrderTypeId());
            
            SizingProductionDetail existing = (dto.getId() != null) ? existingMap.get(dto.getId()) : null;
            if (existing != null) {
                detail.setIssuedTradingCompanyId(existing.getIssuedTradingCompanyId());
                detail.setIsTradingInward(existing.getIsTradingInward());
                detail.setIsIssuedToWeaving(existing.getIsIssuedToWeaving());
                detail.setIsFactoryInward(existing.getIsFactoryInward());
                detail.setIsSizingBeamOutward(existing.getIsSizingBeamOutward());
                detail.setIsReturn(existing.getIsReturn());
                detail.setReturnDate(existing.getReturnDate());
                detail.setReturnNo(existing.getReturnNo());
            } else {
                detail.setIssuedTradingCompanyId(dto.getIssuedTradingCompanyId());
                detail.setIsTradingInward(dto.getIsTradingInward() != null ? dto.getIsTradingInward() : 0L);
                detail.setIsIssuedToWeaving(dto.getIsIssuedToWeaving() != null ? dto.getIsIssuedToWeaving() : 0L);
                detail.setIsFactoryInward(dto.getIsFactoryInward() != null ? dto.getIsFactoryInward() : 0L);
                detail.setIsSizingBeamOutward(0);
                detail.setIsReturn(dto.getIsReturn() != null ? dto.getIsReturn() : 0);
                detail.setReturnDate(dto.getReturnDate());
                detail.setReturnNo(dto.getReturnNo());
            }
            entity.getProductionDetails().add(detail);
        }
    }

    private SizingProductionDTO mapEntityToDto(SizingProduction entity) {
        SizingProductionDTO dto = new SizingProductionDTO();
        dto.setId(entity.getId());
        dto.setSizingDate(entity.getSizingDate());
        dto.setSizingOrderId(entity.getSizingOrderId());
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setSizingPlanId(entity.getSizingPlanId());
        dto.setSizingPlanNo(entity.getSizingPlanNo());
        dto.setBuyerId(entity.getBuyerId());
        dto.setBuyerName(entity.getBuyerName());
        dto.setManualSetNo(entity.getManualSetNo());
        dto.setSizingProductionNo(entity.getSizingProductionNo());
        dto.setYarnId(entity.getYarnId());
        dto.setYarnName(entity.getYarnName());
        dto.setMachineId(entity.getMachineId());
        dto.setMachineName(entity.getMachineName());
        dto.setNoOfBeams(entity.getNoOfBeams());
        dto.setTotalMeters(entity.getTotalMeters());
        dto.setTotalNetWeight(entity.getTotalNetWeight());
        dto.setSizingCount(entity.getSizingCount());
        dto.setCompanyId(entity.getCompanyId());

        dto.setSummary(buildSummary(entity));

        List<SizingProductionDetailDTO> detailDtos = detailRepository.findBySizingProductionId(entity.getId()).stream()
                .sorted(Comparator.comparing(SizingProductionDetail::getId))
                .map(this::mapDetailEntityToDto)
                .collect(Collectors.toList());
        dto.setProductionDetails(detailDtos);
        return dto;
    }

    private SizingProductionSummaryDTO buildSummary(SizingProduction entity) {
        SizingProductionSummaryDTO summary = new SizingProductionSummaryDTO();
        summary.setNoOfBeams(entity.getNoOfBeams());
        summary.setTotalMeters(entity.getTotalMeters());
        summary.setTotalNetWeight(entity.getTotalNetWeight());
        summary.setSizingCount(entity.getSizingCount());

        boolean hasData = summary.getNoOfBeams() != null
                || summary.getTotalMeters() != null
                || summary.getTotalNetWeight() != null
                || summary.getSizingCount() != null;
        return hasData ? summary : null;
    }

    private SizingProductionDetailDTO mapDetailEntityToDto(SizingProductionDetail entity) {
        SizingProductionDetailDTO dto = new SizingProductionDetailDTO();
        dto.setId(entity.getId());
        dto.setShiftName(entity.getShiftName());
        dto.setFlangeNo(entity.getFlangeNo());
        dto.setFlangeId(entity.getFlangeId());
        dto.setTotalEnds(entity.getTotalEnds());
        dto.setSizedBeamNo(entity.getSizedBeamNo());
        dto.setBeamMeters(entity.getBeamMeters());
        dto.setExpectedFabricMeter(entity.getExpectedFabricMeter());
        dto.setSizingPickup(entity.getSizingPickup());
        dto.setBreakages(entity.getBreakages());
        dto.setRpm(entity.getRpm());
        dto.setTareWt(entity.getTareWt());
        dto.setGrossWt(entity.getGrossWt());
        dto.setNetWt(entity.getNetWt());
        dto.setFabricPanelWidth(entity.getFabricPanelWidth());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setIssuedTradingCompanyId(entity.getIssuedTradingCompanyId());
        dto.setIsTradingInward(entity.getIsTradingInward());
        dto.setIsIssuedToWeaving(entity.getIsIssuedToWeaving());
        dto.setIsFactoryInward(entity.getIsFactoryInward());
        dto.setIsSizingBeamOutward(entity.getIsSizingBeamOutward());
        dto.setIsReturn(entity.getIsReturn());
        dto.setReturnDate(entity.getReturnDate());
        dto.setReturnNo(entity.getReturnNo());
        return dto;
    }

    private List<Long> collectFlangeIds(List<SizingProductionDetail> details) {
        if (details == null) {
            return Collections.emptyList();
        }
        return details.stream()
                .map(SizingProductionDetail::getFlangeId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<String> collectFlangeNos(List<SizingProductionDetail> details) {
        if (details == null) {
            return Collections.emptyList();
        }
        return details.stream()
                .filter(d -> d.getFlangeId() == null && d.getFlangeNo() != null && !d.getFlangeNo().isBlank())
                .map(SizingProductionDetail::getFlangeNo)
                .distinct()
                .collect(Collectors.toList());
    }

    private void requireSizingOrderReference(Long sizingOrderId) {
        if (sizingOrderId == null) {
            return;
        }
        Optional<SizingJobWorkOrder> sizingOrder = sizingJobWorkOrderRepository.findById(sizingOrderId);
        String displayIdentifier = sizingOrder
                .map(SizingJobWorkOrder::getSizingWorkOrderNo)
                .filter(StringUtils::hasText)
                .orElse("Sizing Order ID " + sizingOrderId);
        referenceValidationService.requireReference(
                sizingOrderId,
                () -> sizingOrder,
                "Sizing Order",
                displayIdentifier);
    }

    private void requireSizingPlanReference(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return;
        }
        Optional<SizingPlan> sizingPlan = sizingPlanRepository.findById(sizingPlanId);
        String displayIdentifier = sizingPlan
                .map(SizingPlan::getSizingPlanNo)
                .filter(StringUtils::hasText)
                .orElse("Sizing Plan ID " + sizingPlanId);
        referenceValidationService.requireReference(
                sizingPlanId,
                () -> sizingPlan,
                "Sizing Plan",
                displayIdentifier);
    }

    private void syncToRelatedEntities(Long sizingProductionId) {
        if (sizingProductionId == null) return;
        SizingProduction saved = repository.findById(sizingProductionId).orElse(null);
        if (saved == null || saved.getSizingPlanId() == null) return;
        
        try {
            // 1. Sync to BeamInward (External)
            List<com.tech3.erp.entity.BeamInward> inwards = beamInwardRepository.findBySizingPlanId(saved.getSizingPlanId());
            for (com.tech3.erp.entity.BeamInward inward : inwards) {
                com.tech3.erp.dto.BeamInwardDTO inwardDTO = beamInwardService.getBeamInwardById(inward.getId());
                boolean changed = false;
                if (inwardDTO.getBeamInwardBeamDetails() != null && saved.getProductionDetails() != null) {
                    for (com.tech3.erp.dto.BeamInwardBeamDetailsDTO bd : inwardDTO.getBeamInwardBeamDetails()) {
                        for (SizingProductionDetail pd : saved.getProductionDetails()) {
                            if (pd.getSizedBeamNo() != null && pd.getSizedBeamNo().equals(bd.getSizedBeamNo())) {
                                bd.setWrapMeters(pd.getBeamMeters());
                                bd.setExpectedFabricMeter(pd.getExpectedFabricMeter());
                                bd.setFabricPanelWidth(pd.getFabricPanelWidth() != null ? pd.getFabricPanelWidth() : null);
                                bd.setTotalEnds(pd.getTotalEnds());
                                changed = true;
                            }
                        }
                    }
                }
                if (changed) {
                    beamInwardService.updateBeamInward(inwardDTO.getId(), inwardDTO);
                }
            }

            // 2. Sync to FactoryBeamInward (Internal)
            List<com.tech3.erp.entity.FactoryBeamInward> factoryInwards = factoryBeamInwardRepository.findBySizingPlanId(saved.getSizingPlanId());
            for (com.tech3.erp.entity.FactoryBeamInward fi : factoryInwards) {
                com.tech3.erp.dto.FactoryBeamInwardDTO fiDTO = factoryBeamInwardService.getBeamInwardById(fi.getId());
                boolean changed = false;
                if (fiDTO.getBeamInwardBeamDetails() != null && saved.getProductionDetails() != null) {
                    for (com.tech3.erp.dto.FactoryBeamInwardBeamDetailsDTO bd : fiDTO.getBeamInwardBeamDetails()) {
                        for (SizingProductionDetail pd : saved.getProductionDetails()) {
                            if (pd.getSizedBeamNo() != null && pd.getSizedBeamNo().equals(bd.getSizedBeamNo())) {
                                bd.setWrapMeters(pd.getBeamMeters());
                                bd.setExpectedFabricMeter(pd.getExpectedFabricMeter());
                                bd.setFabricPanelWidth(pd.getFabricPanelWidth() != null ? pd.getFabricPanelWidth().toString() : null);
                                bd.setActualEnds(pd.getTotalEnds() != null ? pd.getTotalEnds().longValue() : null);
                                changed = true;
                            }
                        }
                    }
                }
                if (changed) {
                    factoryBeamInwardService.updateBeamInward(fiDTO.getId(), fiDTO);
                }
            }

            // 3. Sync to BeamOutward
            List<com.tech3.erp.entity.BeamOutward> outwards = beamOutwardRepository.findBySizingPlanId(saved.getSizingPlanId());
            for (com.tech3.erp.entity.BeamOutward outward : outwards) {
                com.tech3.erp.dto.BeamOutwardDTO outwardDTO = beamOutwardService.getById(outward.getId());
                boolean changed = false;
                for (SizingProductionDetail pd : saved.getProductionDetails()) {
                    if (pd.getSizedBeamNo() != null && pd.getSizedBeamNo().equals(outwardDTO.getSizedBeamNo())) {
                        outwardDTO.setWrapMeters(pd.getBeamMeters());
                        outwardDTO.setExpectedFabricMeter(pd.getExpectedFabricMeter());
                        outwardDTO.setFabricPanelWidth(pd.getFabricPanelWidth() != null ? pd.getFabricPanelWidth().toString() : null);
                        outwardDTO.setTotalEnds(pd.getTotalEnds() != null ? pd.getTotalEnds().longValue() : null);
                        changed = true;
                    }
                }
                if (changed) {
                    beamOutwardService.update(outwardDTO.getId(), outwardDTO);
                }
            }

            // 4. Sync to SizingBeamOutward
            List<com.tech3.erp.entity.SizingBeamOutward> sizingOutwards = sizingBeamOutwardRepository.findBySizingPlanId(saved.getSizingPlanId());
            for (com.tech3.erp.entity.SizingBeamOutward sizingOutward : sizingOutwards) {
                com.tech3.erp.dto.SizingBeamOutwardDTO sizingOutwardDTO = sizingBeamOutwardService.getById(sizingOutward.getId());
                boolean changed = false;
                if (sizingOutwardDTO.getBeamDetails() != null && saved.getProductionDetails() != null) {
                    for (com.tech3.erp.dto.SizingBeamOutwardDetailDTO bd : sizingOutwardDTO.getBeamDetails()) {
                        for (SizingProductionDetail pd : saved.getProductionDetails()) {
                            if (pd.getSizedBeamNo() != null && pd.getSizedBeamNo().equals(bd.getSizedBeamNo())) {
                                bd.setBeamMeters(pd.getBeamMeters());
                                bd.setExpectedFabricMeter(pd.getExpectedFabricMeter());
                                bd.setWidth(pd.getFabricPanelWidth());
                                bd.setTotalEnds(pd.getTotalEnds());
                                changed = true;
                            }
                        }
                    }
                }
                if (changed) {
                    sizingBeamOutwardService.update(sizingOutwardDTO.getId(), sizingOutwardDTO);
                }
            }
        } catch (Exception e) {
            // Log or skip
        }
    }

    private void updateFlangeIssuedStatuses(List<Long> previousFlangeIds, List<Long> currentFlangeIds) {
        updateFlangeIssuedStatuses(previousFlangeIds, currentFlangeIds, Collections.emptyList(), Collections.emptyList());
    }

    private void updateFlangeIssuedStatuses(List<Long> previousFlangeIds, List<Long> currentFlangeIds,
            List<String> previousFlangeNos, List<String> currentFlangeNos) {
        Set<Long> previous = previousFlangeIds != null
                ? previousFlangeIds.stream().filter(Objects::nonNull).collect(Collectors.toSet())
                : Collections.emptySet();
        Set<Long> current = currentFlangeIds != null
                ? currentFlangeIds.stream().filter(Objects::nonNull).collect(Collectors.toSet())
                : Collections.emptySet();
        Set<String> previousNos = previousFlangeNos != null
                ? previousFlangeNos.stream().filter(Objects::nonNull).collect(Collectors.toSet())
                : Collections.emptySet();
        Set<String> currentNos = currentFlangeNos != null
                ? currentFlangeNos.stream().filter(Objects::nonNull).collect(Collectors.toSet())
                : Collections.emptySet();

        List<Long> toEnable = current.stream().filter(id -> !previous.contains(id)).collect(Collectors.toList());
        List<Long> toDisable = previous.stream().filter(id -> !current.contains(id)).collect(Collectors.toList());
        List<String> toEnableNos = currentNos.stream().filter(no -> !previousNos.contains(no)).collect(Collectors.toList());
        List<String> toDisableNos = previousNos.stream().filter(no -> !currentNos.contains(no)).collect(Collectors.toList());

        if (!toEnable.isEmpty()) {
            flangeBeamInwardDetailRepository.updateIssuedStatusByFlangeIds(toEnable, 1);
        }
        if (!toDisable.isEmpty()) {
            flangeBeamInwardDetailRepository.updateIssuedStatusByFlangeIds(toDisable, 0);
        }
        if (!toEnableNos.isEmpty()) {
            flangeBeamInwardDetailRepository.updateIssuedStatusByFlangeNos(toEnableNos, 1);
        }
        if (!toDisableNos.isEmpty()) {
            flangeBeamInwardDetailRepository.updateIssuedStatusByFlangeNos(toDisableNos, 0);
        }
    }
}
