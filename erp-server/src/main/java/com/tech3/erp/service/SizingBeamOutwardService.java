package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingBeamOutwardDTO;
import com.tech3.erp.dto.SizingBeamOutwardDetailDTO;
import com.tech3.erp.dto.SizingBeamOutwardSummaryDTO;
import com.tech3.erp.entity.BeamInward;
import com.tech3.erp.entity.BeamInwardBeamDetails;
import com.tech3.erp.entity.BeamInwardQualityDetails;
import com.tech3.erp.entity.BeamOutward;
import com.tech3.erp.entity.BeamOutwardDetail;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.FactoryBeamInward;
import com.tech3.erp.entity.FactoryBeamInwardBeamDetails;
import com.tech3.erp.entity.FactoryBeamInwardQualityDetails;
import com.tech3.erp.entity.SizingBeamDetails;
import com.tech3.erp.entity.SizingBeamOutward;
import com.tech3.erp.entity.SizingBeamOutwardDetail;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.SizingProductionDetail;
import com.tech3.erp.entity.SizingQualityDetails;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.BeamInwardQualityDetailsRepository;
import com.tech3.erp.repository.BeamInwardRepository;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.FactoryBeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.FactoryBeamInwardQualityDetailsRepository;
import com.tech3.erp.repository.FactoryBeamInwardRepository;
import com.tech3.erp.repository.SizingBeamDetailsRepository;
import com.tech3.erp.repository.SizingBeamOutwardDetailRepository;
import com.tech3.erp.repository.SizingBeamOutwardRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.SizingProductionDetailRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SizingBeamOutwardService {

    private static final String NOT_FOUND_MESSAGE = "Sizing beam outward not found";

    private final SizingBeamOutwardRepository repository;
    private final SizingBeamOutwardDetailRepository detailRepository;
    private final SizingProductionDetailRepository sizingProductionDetailRepository;
    private final BeamInwardRepository beamInwardRepository;
    private final BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository;
    private final BeamInwardQualityDetailsRepository beamInwardQualityDetailsRepository;
    private final BeamOutwardRepository beamOutwardRepository;
    private final VendorRepository vendorRepository;
    private final CompanyRepository companyRepository;
    private final SizingPlanRepository sizingPlanRepository;
    private final SizingBeamDetailsRepository sizingBeamDetailsRepository;
    private final FactoryBeamInwardRepository factoryBeamInwardRepository;
    private final FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository;
    private final FactoryBeamInwardQualityDetailsRepository factoryBeamInwardQualityDetailsRepository;
    private final ReferenceValidationService referenceValidationService;
    private final WeavingContractRepository weavingContractRepository;
    private static final Logger logger = LoggerFactory.getLogger(SizingBeamOutwardService.class);

    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
    private final CodesDetailRepository codesDetailRepository;
    private final Map<String, Long> companyNameLookup = new ConcurrentHashMap<>();

    public SizingBeamOutwardService(SizingBeamOutwardRepository repository,
                                    SizingBeamOutwardDetailRepository detailRepository,
                                    SizingProductionDetailRepository sizingProductionDetailRepository,
                                    BeamInwardRepository beamInwardRepository,
                                    BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository,
                                    BeamInwardQualityDetailsRepository beamInwardQualityDetailsRepository,
                                    BeamOutwardRepository beamOutwardRepository,
                                    VendorRepository vendorRepository,
                                    CompanyRepository companyRepository,
                                    SizingPlanRepository sizingPlanRepository,
                                    SizingBeamDetailsRepository sizingBeamDetailsRepository,
                                    FactoryBeamInwardRepository factoryBeamInwardRepository,
                                    FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository,
                                    FactoryBeamInwardQualityDetailsRepository factoryBeamInwardQualityDetailsRepository,
                                    WeavingWorkOrderRepository weavingWorkOrderRepository,
                                    ReferenceValidationService referenceValidationService,
                                    WeavingContractRepository weavingContractRepository,
                                    CodesDetailRepository codesDetailRepository) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.sizingProductionDetailRepository = sizingProductionDetailRepository;
        this.beamInwardRepository = beamInwardRepository;
        this.beamInwardBeamDetailsRepository = beamInwardBeamDetailsRepository;
        this.beamInwardQualityDetailsRepository = beamInwardQualityDetailsRepository;
        this.beamOutwardRepository = beamOutwardRepository;
        this.vendorRepository = vendorRepository;
        this.companyRepository = companyRepository;
        this.sizingPlanRepository = sizingPlanRepository;
        this.sizingBeamDetailsRepository = sizingBeamDetailsRepository;
        this.factoryBeamInwardRepository = factoryBeamInwardRepository;
        this.factoryBeamInwardBeamDetailsRepository = factoryBeamInwardBeamDetailsRepository;
        this.factoryBeamInwardQualityDetailsRepository = factoryBeamInwardQualityDetailsRepository;
        this.referenceValidationService = referenceValidationService;
        this.weavingContractRepository = weavingContractRepository;
        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
        this.codesDetailRepository = codesDetailRepository;
    }

    @Transactional
    public SizingBeamOutwardDTO create(SizingBeamOutwardDTO dto) {
        ensureBeamDetails(dto.getBeamDetails());
        validateReferences(dto);
        SizingBeamOutward entity = new SizingBeamOutward();
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getBeamDetails());
        SizingBeamOutward saved = repository.save(entity);
        updateSizingProductionFlags(saved.getBeamDetails(), 1);
        updateSizingBeamDetailsFlags(saved.getSizingPlanId(), saved.getBeamDetails(), 1);
        Map<Long, Long> beamInwardIds = syncBeamInwardRecords(saved);
        syncBeamOutwardRecords(saved, beamInwardIds);
        updateBeamInwardOutwardStatus(saved.getBeamDetails(), 1);
        return mapEntityToDto(saved);
    }

    public SizingBeamOutwardDTO getById(Long id) {
        return repository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
    }

    public PaginatedResponse<SizingBeamOutwardDTO> getAllWithPagination(Long offset, Long limit) {
        List<SizingBeamOutwardDTO> data = repository.findAllByOrderByIdDesc().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return PaginationHelper.paginate(data, offset, limit);
    }

    public PaginatedResponse<SizingBeamOutwardDTO> getByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<SizingBeamOutwardDTO> data = repository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return PaginationHelper.paginate(data, offset, limit);
    }

    @Transactional
    public SizingBeamOutwardDTO update(Long id, SizingBeamOutwardDTO dto) {
        ensureBeamDetails(dto.getBeamDetails());
        validateReferences(dto);
        SizingBeamOutward entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
        updateSizingProductionFlags(new ArrayList<>(entity.getBeamDetails()), 0);
        updateBeamInwardOutwardStatus(new ArrayList<>(entity.getBeamDetails()), 0);
        updateSizingBeamDetailsFlags(entity.getSizingPlanId(), new ArrayList<>(entity.getBeamDetails()), 0);
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getBeamDetails());
        repository.save(entity);
        updateSizingProductionFlags(entity.getBeamDetails(), 1);
        updateSizingBeamDetailsFlags(entity.getSizingPlanId(), entity.getBeamDetails(), 1);
        Map<Long, Long> beamInwardIds = syncBeamInwardRecords(entity);
        syncBeamOutwardRecords(entity, beamInwardIds);
        updateBeamInwardOutwardStatus(entity.getBeamDetails(), 1);
        return mapEntityToDto(entity);
    }

    @Transactional
    public SizingBeamOutwardDTO updateByCompanyId(Long companyId, Long id, SizingBeamOutwardDTO dto) {
        ensureBeamDetails(dto.getBeamDetails());
        validateReferences(dto);
        SizingBeamOutward entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_MESSAGE));
        updateSizingProductionFlags(new ArrayList<>(entity.getBeamDetails()), 0);
        updateBeamInwardOutwardStatus(new ArrayList<>(entity.getBeamDetails()), 0);
        updateSizingBeamDetailsFlags(entity.getSizingPlanId(), new ArrayList<>(entity.getBeamDetails()), 0);
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getBeamDetails());
        repository.save(entity);
        updateSizingProductionFlags(entity.getBeamDetails(), 1);
        updateSizingBeamDetailsFlags(entity.getSizingPlanId(), entity.getBeamDetails(), 1);
        Map<Long, Long> beamInwardIdsByCompany = syncBeamInwardRecords(entity);
        syncBeamOutwardRecords(entity, beamInwardIdsByCompany);
        updateBeamInwardOutwardStatus(entity.getBeamDetails(), 1);
        return mapEntityToDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        SizingBeamOutward entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sizing beam outward not found with id: " + id));
        updateSizingProductionFlags(entity.getBeamDetails(), 0);
        updateBeamInwardOutwardStatus(entity.getBeamDetails(), 0);
        updateSizingBeamDetailsFlags(entity.getSizingPlanId(), entity.getBeamDetails(), 0);
        repository.delete(entity);
        
        // Clear details to trigger deletion of linked records in sync methods
        entity.getBeamDetails().clear();
        Map<Long, Long> beamInwardIds = syncBeamInwardRecords(entity);
        syncBeamOutwardRecords(entity, beamInwardIds);
    }

    @Transactional
    public void deleteByCompanyId(Long companyId, Long id) {
        SizingBeamOutward entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Sizing beam outward not found with id: " + id));
        updateSizingProductionFlags(entity.getBeamDetails(), 0);
        updateBeamInwardOutwardStatus(entity.getBeamDetails(), 0);
        updateSizingBeamDetailsFlags(entity.getSizingPlanId(), entity.getBeamDetails(), 0);
        repository.delete(entity);
        
        // Clear details to trigger deletion of linked records in sync methods
        entity.getBeamDetails().clear();
        Map<Long, Long> beamInwardIdsByCompany = syncBeamInwardRecords(entity);
        syncBeamOutwardRecords(entity, beamInwardIdsByCompany);
    }

    private void ensureBeamDetails(List<SizingBeamOutwardDetailDTO> details) {
        if (details == null || details.isEmpty()) {
            throw new IllegalArgumentException("At least one beam detail is required");
        }
    }

    private void validateReferences(SizingBeamOutwardDTO dto) {
        if (dto == null) {
            return;
        }
        requireSizingPlanReference(dto.getSizingPlanId());
        List<SizingBeamOutwardDetailDTO> details = dto.getBeamDetails();
        if (details == null || details.isEmpty()) {
            return;
        }
        for (SizingBeamOutwardDetailDTO detail : details) {
            requireWeavingContractReference(detail.getWeavingContractId(), detail.getWeavingContractNo());
        }
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

    private void requireWeavingContractReference(Long weavingContractId, String weavingContractNo) {
        if (weavingContractId == null && !StringUtils.hasText(weavingContractNo)) {
            return;
        }
        if (weavingContractId != null) {
            Optional<WeavingContract> weavingContractOpt = weavingContractRepository.findById(weavingContractId);
            referenceValidationService.requireReference(
                    weavingContractId,
                    () -> weavingContractOpt,
                    "Weaving Contract",
                    weavingContractOpt.map(WeavingContract::getWeavingContractNo).filter(StringUtils::hasText)
                            .orElse(StringUtils.hasText(weavingContractNo) ? weavingContractNo : null));
            return;
        }
        Optional<WeavingContract> weavingContractOpt = weavingContractRepository.findByWeavingContractNo(weavingContractNo);
        referenceValidationService.requireReference(
                weavingContractNo,
                () -> weavingContractOpt,
                "Weaving Contract",
                weavingContractOpt.map(WeavingContract::getWeavingContractNo).filter(StringUtils::hasText).orElse(weavingContractNo));
    }

    private void mapDtoToEntity(SizingBeamOutwardDTO dto, SizingBeamOutward entity) {
        entity.setOrderType(dto.getOrderType());
        entity.setSizingBeamOutwardNo(dto.getSizingBeamOutwardNo());
        entity.setSizingOrderId(dto.getSizingOrderId());
        entity.setSizingOrderNo(dto.getSizingOrderNo());
        entity.setSizingPlanId(dto.getSizingPlanId());
        entity.setSizingPlanNo(dto.getSizingPlanNo());
        entity.setOrderTypeId(dto.getOrderTypeId());
        entity.setBuyerName(dto.getBuyerName());
        entity.setBuyerId(dto.getBuyerId());
        entity.setYarnName(dto.getYarnName());
        entity.setYarnId(dto.getYarnId());
        if (dto.getCompanyId() != null) {
            entity.setCompanyId(dto.getCompanyId());
        }
        List<String> deliveryAt = dto.getDeliveryAt();
        List<String> targetDelivery = entity.getDeliveryAt();
        if (targetDelivery == null) {
            targetDelivery = new ArrayList<>();
            entity.setDeliveryAt(targetDelivery);
        }
        targetDelivery.clear();
        if (deliveryAt != null && !deliveryAt.isEmpty()) {
            targetDelivery.addAll(deliveryAt);
        }
        entity.setOutwardDate(dto.getOutwardDate());
        SizingBeamOutwardSummaryDTO summary = dto.getSummary();
        entity.setNoOfBeams(summary != null ? summary.getNoOfBeams() : null);
        entity.setTotalBeamMeters(summary != null ? summary.getTotalBeamMeters() : null);
        entity.setTotalEnds(summary != null ? summary.getTotalEnds() : null);
        entity.setIsReturn(dto.getIsReturn() != null ? dto.getIsReturn() : 0);
        entity.setReturnDate(dto.getReturnDate());
        entity.setReturnNo(dto.getReturnNo());
    }

    private void replaceDetails(SizingBeamOutward entity, List<SizingBeamOutwardDetailDTO> details) {
        List<SizingBeamOutwardDetail> current = entity.getBeamDetails();
        if (current == null) {
            current = new ArrayList<>();
            entity.setBeamDetails(current);
        }
        current.clear();
        if (details == null || details.isEmpty()) {
            return;
        }
        for (SizingBeamOutwardDetailDTO dto : details) {
            SizingBeamOutwardDetail detail = new SizingBeamOutwardDetail();
            detail.setSizingBeamOutward(entity);
            detail.setSizedBeamNo(dto.getSizedBeamNo());
            detail.setWidth(dto.getWidth());
            detail.setTotalEnds(dto.getTotalEnds());
            detail.setBeamMeters(dto.getBeamMeters());
            detail.setExpectedFabricMeter(dto.getExpectedFabricMeter());
            detail.setFlangeNo(dto.getFlangeNo());
            detail.setFlangeId(dto.getFlangeId());
            detail.setWeavingContractNo(dto.getWeavingContractNo());
            detail.setSalesOrderNo(dto.getSalesOrderNo());
            detail.setWeavingContractId(dto.getWeavingContractId());
            detail.setWeavingVendorId(dto.getWeavingVendorId());
            detail.setSalesOrderId(dto.getSalesOrderId());
            detail.setIssuedTradingCompanyId(dto.getIssuedTradingCompanyId());
            detail.setIsBeamOutward(1);
            current.add(detail);
        }
    }

    private void updateSizingProductionFlags(List<SizingBeamOutwardDetail> details, int flag) {
        if (details == null || details.isEmpty()) {
            return;
        }
        if (flag == 0) {
            updateIssuedToWeavingStatus(details, 0L);
            updateFactoryInwardStatus(details, 0L);
        }
        Map<String, SizingBeamOutwardDetail> detailByBeam = details.stream()
                .filter(d -> d.getSizedBeamNo() != null)
                .collect(Collectors.toMap(SizingBeamOutwardDetail::getSizedBeamNo, d -> d, (existing, replacement) -> replacement));
        if (detailByBeam.isEmpty()) {
            return;
        }
        List<SizingProductionDetail> productionDetails = sizingProductionDetailRepository
                .findBySizedBeamNoIn(detailByBeam.keySet().stream().collect(Collectors.toList()));
        if (productionDetails == null || productionDetails.isEmpty()) {
            return;
        }
        for (SizingProductionDetail detail : productionDetails) {
            detail.setIsSizingBeamOutward(flag);
            detail.setIsTradingInward(flag == 1 ? 1L : 0L);
            if (flag == 0) {
                detail.setIsIssuedToWeaving(0L);
            }
            SizingBeamOutwardDetail matched = detailByBeam.get(detail.getSizedBeamNo());
            Long issuedTradingCompanyId = flag == 1 && matched != null ? matched.getIssuedTradingCompanyId() : null;
            detail.setIssuedTradingCompanyId(issuedTradingCompanyId);
        }
        sizingProductionDetailRepository.saveAll(productionDetails);
    }

    private void updateBeamInwardOutwardStatus(List<SizingBeamOutwardDetail> details, int status) {
        if (details == null || details.isEmpty()) {
            return;
        }
        List<String> sizedBeamNos = details.stream()
                .map(SizingBeamOutwardDetail::getSizedBeamNo)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (sizedBeamNos.isEmpty()) {
            return;
        }
        List<BeamInwardBeamDetails> beamInwardDetails = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(sizedBeamNos);
        if (beamInwardDetails == null || beamInwardDetails.isEmpty()) {
            return;
        }

        Map<Long, Integer> vendorInternalStatusMap = new HashMap<>();
        Map<String, Long> beamNoToVendorIdMap = details.stream()
                .filter(d -> d.getSizedBeamNo() != null)
                .collect(Collectors.toMap(SizingBeamOutwardDetail::getSizedBeamNo, d -> d.getWeavingVendorId() != null ? d.getWeavingVendorId() : 0L, (a, b) -> a));

        if (status == 1) {
            List<Long> vendorIds = details.stream()
                    .map(SizingBeamOutwardDetail::getWeavingVendorId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            if (!vendorIds.isEmpty()) {
                vendorInternalStatusMap = vendorRepository.findAllById(vendorIds).stream()
                        .collect(Collectors.toMap(Vendor::getId, v -> v.getIsInternalVendor() != null ? v.getIsInternalVendor() : 0));
            }
        }

        for (BeamInwardBeamDetails beamDetail : beamInwardDetails) {
            if (status == 0) {
                beamDetail.setIsBeamOutwarded(0);
            } else {
                Long vendorId = beamNoToVendorIdMap.get(beamDetail.getSizedBeamNo());
                int isInternal = vendorInternalStatusMap.getOrDefault(vendorId, 0);
                beamDetail.setIsBeamOutwarded(isInternal == 1 ? 1 : 0);
            }
        }
        beamInwardBeamDetailsRepository.saveAll(beamInwardDetails);
    }

    private void updateSizingBeamDetailsFlags(Long sizingPlanId, List<SizingBeamOutwardDetail> details, int flag) {
        if (sizingPlanId == null) {
            return;
        }
        
        // Get all SizingBeamDetails for the given sizingPlanId
        List<SizingBeamDetails> sizingBeamDetailsList = sizingBeamDetailsRepository.findBySizingPlanId(sizingPlanId);
        if (sizingBeamDetailsList == null || sizingBeamDetailsList.isEmpty()) {
            return;
        }
        
        // If we have specific details with weavingContractIds, try to match them first
        if (details != null && !details.isEmpty()) {
            List<Long> weavingContractIds = details.stream()
                    .map(SizingBeamOutwardDetail::getWeavingContractId)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
            
            if (!weavingContractIds.isEmpty()) {
                List<SizingBeamDetails> matchedDetails = sizingBeamDetailsRepository.findBySizingPlanIdAndWeavingContractIdIn(sizingPlanId, weavingContractIds);
                if (matchedDetails != null && !matchedDetails.isEmpty()) {
                    for (SizingBeamDetails beamDetail : matchedDetails) {
                        beamDetail.setIsBeamOutward(flag);
                    }
                    sizingBeamDetailsRepository.saveAll(matchedDetails);
                    return;
                }
            }
        }
        
        // If no specific matches found, update all records for the sizing plan
        for (SizingBeamDetails beamDetail : sizingBeamDetailsList) {
            beamDetail.setIsBeamOutward(flag);
        }
        sizingBeamDetailsRepository.saveAll(sizingBeamDetailsList);
    }

    private void updateIssuedToWeavingStatus(List<SizingBeamOutwardDetail> details, Long status) {
        if (details == null || details.isEmpty()) {
            return;
        }
        List<String> sizedBeamNos = details.stream()
                .map(SizingBeamOutwardDetail::getSizedBeamNo)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (sizedBeamNos.isEmpty()) {
            return;
        }
        List<SizingProductionDetail> productionDetails = sizingProductionDetailRepository.findBySizedBeamNoIn(sizedBeamNos);
        if (productionDetails == null || productionDetails.isEmpty()) {
            return;
        }
        for (SizingProductionDetail detail : productionDetails) {
            detail.setIsIssuedToWeaving(status);
        }
        sizingProductionDetailRepository.saveAll(productionDetails);
    }

    private void updateFactoryInwardStatus(List<SizingBeamOutwardDetail> details, Long status) {
        if (details == null || details.isEmpty()) {
            return;
        }
        List<String> sizedBeamNos = details.stream()
                .map(SizingBeamOutwardDetail::getSizedBeamNo)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (sizedBeamNos.isEmpty()) {
            return;
        }
        List<SizingProductionDetail> productionDetails = sizingProductionDetailRepository.findBySizedBeamNoIn(sizedBeamNos);
        if (productionDetails == null || productionDetails.isEmpty()) {
            return;
        }
        for (SizingProductionDetail detail : productionDetails) {
            detail.setIsFactoryInward(status);
        }
        sizingProductionDetailRepository.saveAll(productionDetails);
    }

    private boolean syncFactoryBeamInwardRecord(BeamOutward beamOutward, SizingPlan sizingPlan, Vendor weavingVendor, Long buyerId) {
        if (beamOutward == null || beamOutward.getId() == null) {
            return false;
        }
        if (sizingPlan == null || sizingPlan.getOrderTypeId() == null || sizingPlan.getOrderTypeId().longValue() != 1L) {
            return false;
        }
        if (!isInternalVendor(weavingVendor)) {
            return false;
        }
        // FIND EXISTING FactoryBeamInward or CREATE NEW
        FactoryBeamInward factory = factoryBeamInwardRepository.findFirstByBeamOutwardId(beamOutward.getId())
                .orElseGet(FactoryBeamInward::new);

        factory.setSizingVendorId(sizingPlan.getVendorId());
        factory.setSizingPlanId(beamOutward.getSizingPlanId());
        factory.setSizingSetNo(sizingPlan.getSizingPlanNo());
        factory.setOrderTypeId(sizingPlan.getOrderTypeId());
        factory.setWeavingContractId(beamOutward.getWeavingContractId() != null ? String.valueOf(beamOutward.getWeavingContractId()) : null);
        Long tradingCompanyId = buyerId != null ? buyerId : beamOutward.getTradingCompanyId();
        factory.setTradingCompanyId(tradingCompanyId);
        factory.setFactoryCompanyId(beamOutward.getFactoryCompanyId());
        factory.setWeavingWorkOrderId(beamOutward.getWeavingWorkOrderId());
        if (factory.getId() == null) {
            factory.setBeamInwardDate(LocalDate.now());
        }
        factory.setBeamInwardNo(resolveBeamInwardNo(beamOutward.getBeamInwardId()));
        factory.setBeamInwardId(beamOutward.getBeamInwardId());
        factory.setBeamOutwardId(beamOutward.getId());
        factory.setIsBeamPending(1);

        List<BeamInwardBeamDetails> beamInwardDetails = resolveBeamInwardDetails(beamOutward);
        List<BeamInwardQualityDetails> qualities = resolveBeamInwardQualities(beamOutward, beamInwardDetails);
        factory.setFabricCodeQuality(buildFabricCodeQuality(qualities, sizingPlan));

        // Clear existing details if they exist to prevent duplicates
        if (factory.getQualityDetails() != null) {
            factory.getQualityDetails().clear();
        }
        
        List<FactoryBeamInwardBeamDetails> factoryDetailsList = factory.getBeamDetails();
        if (factoryDetailsList == null) {
            factoryDetailsList = new ArrayList<>();
            factory.setBeamDetails(factoryDetailsList);
        }
        
        Map<String, FactoryBeamInwardBeamDetails> existingDetailMap = factoryDetailsList.stream()
                .filter(d -> d.getSizedBeamNo() != null)
                .collect(Collectors.toMap(FactoryBeamInwardBeamDetails::getSizedBeamNo, d -> d, (e, r) -> e));

        FactoryBeamInward savedFactory = factoryBeamInwardRepository.save(factory);

        if (!qualities.isEmpty()) {
            List<FactoryBeamInwardQualityDetails> qualityEntities = qualities.stream().map(q -> {
                FactoryBeamInwardQualityDetails quality = new FactoryBeamInwardQualityDetails();
                quality.setBeamInward(savedFactory);
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
            
            if (savedFactory.getQualityDetails() == null) {
                savedFactory.setQualityDetails(new ArrayList<>());
            }
            savedFactory.getQualityDetails().addAll(qualityEntities);
            factoryBeamInwardQualityDetailsRepository.saveAll(qualityEntities);
        }

        Long aggregatedActualEnds = aggregateActualEnds(qualities, beamInwardDetails);
        List<FactoryBeamInwardBeamDetails> sourceFactoryDetails = buildFactoryBeamDetails(savedFactory, beamOutward, aggregatedActualEnds, beamInwardDetails);
        
        List<FactoryBeamInwardBeamDetails> updatedFactoryDetails = new ArrayList<>();
        
        // Unique details by sizedBeamNo from sourceFactoryDetails
        Map<String, FactoryBeamInwardBeamDetails> uniqueSourceDetails = sourceFactoryDetails.stream()
                .filter(d -> d.getSizedBeamNo() != null)
                .collect(Collectors.toMap(FactoryBeamInwardBeamDetails::getSizedBeamNo, d -> d, (e, r) -> r));

        for (FactoryBeamInwardBeamDetails detail : uniqueSourceDetails.values()) {
            FactoryBeamInwardBeamDetails factoryDetail = existingDetailMap.get(detail.getSizedBeamNo());
            if (factoryDetail == null) {
                factoryDetail = detail;
                factoryDetail.setBeamInward(savedFactory);
            } else {
                factoryDetail.setWrapMeters(detail.getWrapMeters());
                factoryDetail.setShrinkage(detail.getShrinkage());
                factoryDetail.setExpectedFabricMeter(detail.getExpectedFabricMeter());
                factoryDetail.setFlangeNo(detail.getFlangeNo());
                factoryDetail.setFlangeId(detail.getFlangeId());
                factoryDetail.setActualEnds(detail.getActualEnds());
                factoryDetail.setFabricPanelWidth(detail.getFabricPanelWidth());
            }
            updatedFactoryDetails.add(factoryDetail);
        }

        factoryDetailsList.clear();
        factoryDetailsList.addAll(updatedFactoryDetails);
        if (!factoryDetailsList.isEmpty()) {
            factoryBeamInwardBeamDetailsRepository.saveAll(factoryDetailsList);
        }
        return true;
    }

    private boolean syncFactoryBeamInwardForContract(BeamOutward beamOutward, SizingPlan sizingPlan, Vendor weavingVendor,
                                                     Long buyerId, Long weavingContractId,
                                                     List<SizingBeamOutwardDetail> contractDetails) {
        if (beamOutward == null || beamOutward.getId() == null) return false;
        if (sizingPlan == null || sizingPlan.getOrderTypeId() == null || sizingPlan.getOrderTypeId().longValue() != 1L) return false;
        if (!isInternalVendor(weavingVendor)) return false;

        String weavingContractIdStr = weavingContractId != null ? String.valueOf(weavingContractId) : null;

        FactoryBeamInward factory = (weavingContractIdStr != null
                ? factoryBeamInwardRepository.findFirstByBeamOutwardIdAndWeavingContractId(beamOutward.getId(), weavingContractIdStr)
                : factoryBeamInwardRepository.findFirstByBeamOutwardId(beamOutward.getId()))
                .orElseGet(FactoryBeamInward::new);

        factory.setSizingVendorId(sizingPlan.getVendorId());
        factory.setSizingPlanId(beamOutward.getSizingPlanId());
        factory.setSizingSetNo(sizingPlan.getSizingPlanNo());
        factory.setOrderTypeId(sizingPlan.getOrderTypeId());
        factory.setWeavingContractId(weavingContractIdStr);
        Long tradingCompanyId = buyerId != null ? buyerId : beamOutward.getTradingCompanyId();
        factory.setTradingCompanyId(tradingCompanyId);
        factory.setFactoryCompanyId(beamOutward.getFactoryCompanyId());
        factory.setWeavingWorkOrderId(resolveWeavingWorkOrderId(weavingContractId, null));
        if (factory.getId() == null) {
            factory.setBeamInwardDate(LocalDate.now());
        }
        factory.setBeamInwardNo(resolveBeamInwardNo(beamOutward.getBeamInwardId()));
        factory.setBeamInwardId(beamOutward.getBeamInwardId());
        factory.setBeamOutwardId(beamOutward.getId());
        factory.setIsBeamPending(1);

        List<BeamInwardBeamDetails> beamInwardDetails = resolveBeamInwardDetails(beamOutward);
        List<BeamInwardQualityDetails> qualities = resolveBeamInwardQualities(beamOutward, beamInwardDetails);
        factory.setFabricCodeQuality(buildFabricCodeQuality(qualities, sizingPlan));

        if (factory.getQualityDetails() != null) {
            factory.getQualityDetails().clear();
        }

        List<FactoryBeamInwardBeamDetails> factoryDetailsList = factory.getBeamDetails();
        if (factoryDetailsList == null) {
            factoryDetailsList = new ArrayList<>();
            factory.setBeamDetails(factoryDetailsList);
        }
        Map<String, FactoryBeamInwardBeamDetails> existingDetailMap = factoryDetailsList.stream()
                .filter(d -> d.getSizedBeamNo() != null)
                .collect(Collectors.toMap(FactoryBeamInwardBeamDetails::getSizedBeamNo, d -> d, (e, r) -> e));

        FactoryBeamInward savedFactory = factoryBeamInwardRepository.save(factory);

        if (!qualities.isEmpty()) {
            List<FactoryBeamInwardQualityDetails> qualityEntities = qualities.stream().map(q -> {
                FactoryBeamInwardQualityDetails quality = new FactoryBeamInwardQualityDetails();
                quality.setBeamInward(savedFactory);
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
            if (savedFactory.getQualityDetails() == null) {
                savedFactory.setQualityDetails(new ArrayList<>());
            }
            savedFactory.getQualityDetails().addAll(qualityEntities);
            factoryBeamInwardQualityDetailsRepository.saveAll(qualityEntities);
        }

        Long aggregatedActualEnds = aggregateActualEnds(qualities, beamInwardDetails);

        Map<String, BeamInwardBeamDetails> bySizedBeam = beamInwardDetails.stream()
                .filter(d -> d.getSizedBeamNo() != null && !d.getSizedBeamNo().isBlank())
                .collect(Collectors.toMap(BeamInwardBeamDetails::getSizedBeamNo, d -> d, (e, r) -> e));
        Map<String, BeamInwardBeamDetails> byFlange = beamInwardDetails.stream()
                .filter(d -> d.getFlangeNo() != null && !d.getFlangeNo().isBlank())
                .collect(Collectors.toMap(BeamInwardBeamDetails::getFlangeNo, d -> d, (e, r) -> e));

        List<FactoryBeamInwardBeamDetails> updatedFactoryDetails = new ArrayList<>();
        for (SizingBeamOutwardDetail detail : contractDetails) {
            if (detail.getSizedBeamNo() == null) continue;
            FactoryBeamInwardBeamDetails factoryDetail = existingDetailMap.get(detail.getSizedBeamNo());
            if (factoryDetail == null) {
                factoryDetail = new FactoryBeamInwardBeamDetails();
                factoryDetail.setBeamInward(savedFactory);
            }
            factoryDetail.setSizedBeamNo(detail.getSizedBeamNo());
            factoryDetail.setFlangeNo(detail.getFlangeNo());
            factoryDetail.setFlangeId(detail.getFlangeId());
            BeamInwardBeamDetails matched = selectMatchingBeamInwardDetail(bySizedBeam, byFlange, detail.getSizedBeamNo(), detail.getFlangeNo());
            if (factoryDetail.getFlangeId() == null && matched != null) {
                factoryDetail.setFlangeId(matched.getFlangeId());
            }
            factoryDetail.setWrapMeters(resolveWrapMeters(matched, detail.getBeamMeters()));
            factoryDetail.setShrinkage(resolveShrinkage(matched, null));
            factoryDetail.setExpectedFabricMeter(resolveExpectedFabricMeters(matched, detail.getExpectedFabricMeter()));
            factoryDetail.setFabricPanelWidth(resolveFabricPanelWidth(matched, formatWidth(detail.getWidth())));
            factoryDetail.setActualEnds(matched != null && matched.getTotalEnds() != null
                    ? matched.getTotalEnds().longValue()
                    : (detail.getTotalEnds() != null ? detail.getTotalEnds().longValue() : aggregatedActualEnds));
            updatedFactoryDetails.add(factoryDetail);
        }

        factoryDetailsList.clear();
        factoryDetailsList.addAll(updatedFactoryDetails);
        if (!factoryDetailsList.isEmpty()) {
            factoryBeamInwardBeamDetailsRepository.saveAll(factoryDetailsList);
        }
        return true;
    }

    private Long aggregateActualEnds(List<BeamInwardQualityDetails> qualities,
                                     List<BeamInwardBeamDetails> beamInwardDetails) {
        Long fromQualities = aggregateFromQualityDetails(qualities);
        if (fromQualities != null) {
            return fromQualities;
        }
        if (beamInwardDetails == null || beamInwardDetails.isEmpty()) {
            return null;
        }
        long sum = 0L;
        boolean hasValue = false;
        for (BeamInwardBeamDetails detail : beamInwardDetails) {
            if (detail.getTotalEnds() != null) {
                sum += detail.getTotalEnds();
                hasValue = true;
            }
        }
        return hasValue ? sum : null;
    }

    private Long aggregateFromQualityDetails(List<BeamInwardQualityDetails> qualities) {
        if (qualities == null || qualities.isEmpty()) {
            return null;
        }
        long sum = 0L;
        boolean hasValue = false;
        for (BeamInwardQualityDetails quality : qualities) {
            if (quality.getActualEnds() != null) {
                sum += quality.getActualEnds();
                hasValue = true;
            }
        }
        return hasValue ? sum : null;
    }

    private List<FactoryBeamInwardBeamDetails> buildFactoryBeamDetails(FactoryBeamInward factory,
                                                                       BeamOutward beamOutward,
                                                                       Long aggregatedActualEnds,
                                                                       List<BeamInwardBeamDetails> beamInwardDetails) {
        List<FactoryBeamInwardBeamDetails> results = new ArrayList<>();
        Map<String, BeamInwardBeamDetails> bySizedBeam = beamInwardDetails != null && !beamInwardDetails.isEmpty()
                ? beamInwardDetails.stream()
                        .filter(detail -> detail.getSizedBeamNo() != null && !detail.getSizedBeamNo().isBlank())
                        .collect(Collectors.toMap(BeamInwardBeamDetails::getSizedBeamNo,
                                detail -> detail,
                                (existing, replacement) -> existing))
                : Collections.emptyMap();
        Map<String, BeamInwardBeamDetails> byFlange = beamInwardDetails != null && !beamInwardDetails.isEmpty()
                ? beamInwardDetails.stream()
                        .filter(detail -> detail.getFlangeNo() != null && !detail.getFlangeNo().isBlank())
                        .collect(Collectors.toMap(BeamInwardBeamDetails::getFlangeNo,
                                detail -> detail,
                                (existing, replacement) -> existing))
                : Collections.emptyMap();

        List<BeamOutwardDetail> outwardDetails = beamOutward.getOutwardDetails();
        if (outwardDetails == null || outwardDetails.isEmpty()) {
            FactoryBeamInwardBeamDetails detail = new FactoryBeamInwardBeamDetails();
            detail.setBeamInward(factory);
            detail.setSizedBeamNo(beamOutward.getSizedBeamNo());
            detail.setFlangeNo(beamOutward.getFlangeNo());
            BeamInwardBeamDetails matched = selectMatchingBeamInwardDetail(bySizedBeam, byFlange,
                    beamOutward.getSizedBeamNo(), beamOutward.getFlangeNo());
            detail.setFlangeId(matched != null ? matched.getFlangeId() : null);
            detail.setWrapMeters(resolveWrapMeters(matched, beamOutward.getWrapMeters()));
            detail.setShrinkage(resolveShrinkage(matched, beamOutward.getShrinkage()));
            detail.setExpectedFabricMeter(resolveExpectedFabricMeters(matched, beamOutward.getExpectedFabricMeter()));
            detail.setFabricPanelWidth(resolveFabricPanelWidth(matched, beamOutward.getFabricPanelWidth()));
            detail.setActualEnds(resolveActualEnds(matched, aggregatedActualEnds));
            results.add(detail);
            return results;
        }
        for (BeamOutwardDetail detail : outwardDetails) {
            FactoryBeamInwardBeamDetails factoryDetail = new FactoryBeamInwardBeamDetails();
            factoryDetail.setBeamInward(factory);
            factoryDetail.setSizedBeamNo(detail.getSizedBeamNo());
            factoryDetail.setFlangeNo(detail.getFlangeNo());
            factoryDetail.setFlangeId(detail.getFlangeId());
            BeamInwardBeamDetails matched = selectMatchingBeamInwardDetail(bySizedBeam, byFlange,
                    detail.getSizedBeamNo(), detail.getFlangeNo());
            if (factoryDetail.getFlangeId() == null && matched != null) {
                factoryDetail.setFlangeId(matched.getFlangeId());
            }
            factoryDetail.setWrapMeters(resolveWrapMeters(matched, detail.getWrapMeters()));
            factoryDetail.setShrinkage(resolveShrinkage(matched, detail.getShrinkage()));
            factoryDetail.setExpectedFabricMeter(resolveExpectedFabricMeters(matched, detail.getExpectedFabricMeter()));
            factoryDetail.setFabricPanelWidth(resolveFabricPanelWidth(matched, detail.getFabricPanelWidth()));
            factoryDetail.setActualEnds(matched != null && matched.getTotalEnds() != null
                    ? matched.getTotalEnds().longValue()
                    : (detail.getTotalEnds() != null ? detail.getTotalEnds().longValue() : aggregatedActualEnds));
            results.add(factoryDetail);
        }
        return results;
    }

    private List<BeamInwardBeamDetails> resolveBeamInwardDetails(BeamOutward beamOutward) {
        if (beamOutward == null) {
            return Collections.emptyList();
        }
        if (beamOutward.getBeamInwardId() != null) {
            List<BeamInwardBeamDetails> details = beamInwardBeamDetailsRepository.findByBeamInwardId(beamOutward.getBeamInwardId());
            if (details != null && !details.isEmpty()) {
                return details;
            }
        }
        List<String> sizedBeamNos;
        List<BeamOutwardDetail> outwardDetails = beamOutward.getOutwardDetails();
        if (outwardDetails != null && !outwardDetails.isEmpty()) {
            sizedBeamNos = outwardDetails.stream()
                    .map(BeamOutwardDetail::getSizedBeamNo)
                    .filter(Objects::nonNull)
                    .distinct()
                    .collect(Collectors.toList());
        } else if (beamOutward.getSizedBeamNo() != null) {
            sizedBeamNos = Collections.singletonList(beamOutward.getSizedBeamNo());
        } else {
            sizedBeamNos = Collections.emptyList();
        }
        if (sizedBeamNos.isEmpty()) {
            return Collections.emptyList();
        }
        List<BeamInwardBeamDetails> details = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(sizedBeamNos);
        return details != null ? details : Collections.emptyList();
    }

    private List<BeamInwardQualityDetails> resolveBeamInwardQualities(BeamOutward beamOutward,
                                                                      List<BeamInwardBeamDetails> beamInwardDetails) {
        List<BeamInwardQualityDetails> qualities = Collections.emptyList();
        if (beamOutward != null && beamOutward.getBeamInwardId() != null) {
            List<BeamInwardQualityDetails> direct = beamInwardQualityDetailsRepository.findByBeamInwardId(beamOutward.getBeamInwardId());
            if (direct != null && !direct.isEmpty()) {
                qualities = direct;
            }
        }
        if ((qualities == null || qualities.isEmpty()) && beamInwardDetails != null && !beamInwardDetails.isEmpty()) {
            Set<Long> beamInwardIds = beamInwardDetails.stream()
                    .map(detail -> {
                        BeamInward beamInward = detail.getBeamInward();
                        return beamInward != null ? beamInward.getId() : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            if (!beamInwardIds.isEmpty()) {
                List<BeamInwardQualityDetails> fallback = beamInwardQualityDetailsRepository.findByBeamInwardIdIn(beamInwardIds);
                if (fallback != null && !fallback.isEmpty()) {
                    qualities = fallback;
                }
            }
        }
        return qualities == null ? Collections.emptyList() : qualities;
    }

    private BeamInwardBeamDetails selectMatchingBeamInwardDetail(Map<String, BeamInwardBeamDetails> bySizedBeam,
                                                                 Map<String, BeamInwardBeamDetails> byFlange,
                                                                 String sizedBeamNo,
                                                                 String flangeNo) {
        if (sizedBeamNo != null && !sizedBeamNo.isBlank()) {
            BeamInwardBeamDetails matched = bySizedBeam.get(sizedBeamNo);
            if (matched != null) {
                return matched;
            }
        }
        if (flangeNo != null && !flangeNo.isBlank()) {
            return byFlange.get(flangeNo);
        }
        return null;
    }

    private Double resolveWrapMeters(BeamInwardBeamDetails matched, Double fallback) {
        if (matched != null && matched.getWrapMeters() != null) {
            return matched.getWrapMeters();
        }
        return fallback;
    }

    private Double resolveShrinkage(BeamInwardBeamDetails matched, Double fallback) {
        if (matched != null && matched.getShrinkage() != null) {
            return matched.getShrinkage();
        }
        return fallback;
    }

    private Double resolveExpectedFabricMeters(BeamInwardBeamDetails matched, Double fallback) {
        if (matched != null && matched.getExpectedFabricMeter() != null) {
            return matched.getExpectedFabricMeter();
        }
        return fallback;
    }

    private String resolveFabricPanelWidth(BeamInwardBeamDetails matched, String fallback) {
        String fromMatched = matched != null ? formatWidth(matched.getFabricPanelWidth()) : null;
        return fromMatched != null ? fromMatched : fallback;
    }

    private Long resolveActualEnds(BeamInwardBeamDetails matched, Long aggregatedActualEnds) {
        if (matched != null && matched.getTotalEnds() != null) {
            return matched.getTotalEnds().longValue();
        }
        return aggregatedActualEnds;
    }

    private String formatWidth(Double width) {
        return width != null ? width.toString() : null;
    }

    private Map<String, Double> loadPlanShrinkageMap(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return Collections.emptyMap();
        }
        List<SizingBeamDetails> beamDetails = sizingBeamDetailsRepository.findBySizingPlanId(sizingPlanId);
        if (beamDetails == null || beamDetails.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Double> shrinkageMap = new HashMap<>();
        for (SizingBeamDetails detail : beamDetails) {
            if (detail == null || detail.getShrinkage() == null) {
                continue;
            }
            String key = buildPlanShrinkageKey(detail.getWeavingContractId(), detail.getSalesOrderId(), detail.getWeavingVendorId());
            if (key != null && !shrinkageMap.containsKey(key)) {
                shrinkageMap.put(key, detail.getShrinkage());
            }
        }
        return shrinkageMap;
    }

    private Double resolvePlanShrinkage(Map<String, Double> planShrinkageMap,
                                        Long weavingContractId,
                                        Long salesOrderId,
                                        Long weavingVendorId) {
        if (planShrinkageMap == null || planShrinkageMap.isEmpty()) {
            return null;
        }
        List<String> keys = new ArrayList<>();
        keys.add(buildPlanShrinkageKey(weavingContractId, salesOrderId, weavingVendorId));
        keys.add(buildPlanShrinkageKey(weavingContractId, salesOrderId, null));
        keys.add(buildPlanShrinkageKey(weavingContractId, null, weavingVendorId));
        keys.add(buildPlanShrinkageKey(weavingContractId, null, null));
        keys.add(buildPlanShrinkageKey(null, salesOrderId, weavingVendorId));
        keys.add(buildPlanShrinkageKey(null, salesOrderId, null));
        keys.add(buildPlanShrinkageKey(null, null, weavingVendorId));
        for (String key : keys) {
            if (key != null) {
                Double value = planShrinkageMap.get(key);
                if (value != null) {
                    return value;
                }
            }
        }
        return null;
    }

    private String buildPlanShrinkageKey(Long weavingContractId, Long salesOrderId, Long weavingVendorId) {
        if (weavingContractId == null && salesOrderId == null && weavingVendorId == null) {
            return null;
        }
        long contractKey = weavingContractId != null ? weavingContractId : -1L;
        long salesOrderKey = salesOrderId != null ? salesOrderId : -1L;
        long vendorKey = weavingVendorId != null ? weavingVendorId : -1L;
        return contractKey + "|" + salesOrderKey + "|" + vendorKey;
    }

    private String buildFabricCodeQuality(List<BeamInwardQualityDetails> qualities, SizingPlan sizingPlan) {
        Set<String> codes = new LinkedHashSet<>();
        if (qualities != null) {
            for (BeamInwardQualityDetails quality : qualities) {
                String code = normalizeCode(quality.getFabricCode());
                if (code != null) {
                    codes.add(code);
                }
            }
        }
        if (codes.isEmpty() && sizingPlan != null) {
            List<SizingQualityDetails> sizingQualities = sizingPlan.getQualityDetails();
            if (sizingQualities != null) {
                for (SizingQualityDetails detail : sizingQualities) {
                    String code = normalizeCode(detail.getFabricCode());
                    if (code != null) {
                        codes.add(code);
                    }
                }
            }
        }
        if (codes.isEmpty()) {
            return null;
        }
        return String.join(", ", codes);
    }

    private String normalizeCode(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String normalizeCompanyName(String value) {
        if (value == null) {
            return null;
        }
        String canonical = value.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        return canonical.isEmpty() ? null : canonical;
    }

    private Map<Long, Long> syncBeamInwardRecords(SizingBeamOutward entity) {
        Long sizingPlanId = entity.getSizingPlanId();
        if (sizingPlanId == null) {
            return Collections.emptyMap();
        }
        
        // Group details by issuedTradingCompanyId
        final Map<Long, List<SizingBeamOutwardDetail>> detailsByCompany;

        if (entity.getOrderTypeId() != null && entity.getOrderTypeId() == 1L && entity.getBuyerId() != null) {
            // For internal orders, attribute all details to buyerId (trading company)
            detailsByCompany = new HashMap<>();
            detailsByCompany.put(entity.getBuyerId(), entity.getBeamDetails());
        } else {
            // Otherwise group by issuedTradingCompanyId
            detailsByCompany = entity.getBeamDetails().stream()
                    .filter(detail -> detail.getIssuedTradingCompanyId() != null)
                    .collect(Collectors.groupingBy(SizingBeamOutwardDetail::getIssuedTradingCompanyId));
        }

        // Handle deletions: Find existing BeamInward records for this SizingBeamOutwardId and delete those not in detailsByCompany
        List<BeamInward> existingInwards = beamInwardRepository.findBySizingBeamOutwardId(entity.getId());
        if (existingInwards != null) {
            List<BeamInward> toDelete = existingInwards.stream()
                    .filter(bi -> !detailsByCompany.containsKey(bi.getCompanyId()))
                    .collect(Collectors.toList());
            if (!toDelete.isEmpty()) {
                beamInwardRepository.deleteAll(toDelete);
            }
        }

        if (detailsByCompany.isEmpty()) {
            return Collections.emptyMap();
        }

        SizingPlan sizingPlan = fetchSizingPlan(sizingPlanId);
        Long planVendorId = sizingPlan != null ? sizingPlan.getVendorId() : null;
        Double planSizingRate = sizingPlan != null ? sizingPlan.getSizingRate() : null;
        Map<String, Double> planShrinkageMap = loadPlanShrinkageMap(sizingPlanId);

        final Long inwardTypeId;
        if (entity.getOrderTypeId() != null) {
            String targetCode = entity.getOrderTypeId().equals(1L) ? "251" : 
                                entity.getOrderTypeId().equals(2L) ? "252" : null;
            if (targetCode != null) {
                Optional<CodesDetail> cd = codesDetailRepository.findByCode(targetCode);
                if (cd.isEmpty()) {
                    cd = codesDetailRepository.findByValue(entity.getOrderTypeId().equals(1L) ? "Inhouse Beam" : "Trading Beam");
                }
                if (cd.isEmpty()) {
                    cd = codesDetailRepository.findByValue(entity.getOrderTypeId().equals(1L) ? "INHOUSE BEAM" : "TRADING BEAM");
                }
                inwardTypeId = cd.map(CodesDetail::getId).orElse(null);
            } else {
                inwardTypeId = null;
            }
        } else {
            inwardTypeId = null;
        }

        Map<Long, Long> beamInwardIds = new HashMap<>();
        for (Map.Entry<Long, List<SizingBeamOutwardDetail>> entry : detailsByCompany.entrySet()) {
            Long tradingCompanyId = entry.getKey();
            if (tradingCompanyId == null) {
                continue;
            }

            // FIND EXISTING BeamInward by SizingBeamOutwardId or CREATE NEW
            BeamInward beamInward = beamInwardRepository.findFirstBySizingBeamOutwardIdAndCompanyId(entity.getId(), tradingCompanyId)
                    .orElseGet(BeamInward::new);

            beamInward.setSizingBeamOutwardId(entity.getId());
            beamInward.setSizingPlanId(sizingPlanId);
            beamInward.setSizingVendorId(planVendorId != null ? planVendorId : entity.getCompanyId());
            beamInward.setSizingRate(planSizingRate);
            beamInward.setOrderTypeId(entity.getOrderTypeId());
            if (beamInward.getId() == null) {
                beamInward.setBeamInwardDate(LocalDate.now());
            }
            beamInward.setCompanyId(tradingCompanyId);
            
            if (beamInward.getBeamInwardNo() == null || beamInward.getBeamInwardNo().isBlank()) {
                beamInward.setBeamInwardNo(generateBeamInwardNo(tradingCompanyId));
            }

            // Handle Quality Details
            if (sizingPlan != null && sizingPlan.getQualityDetails() != null) {
                List<BeamInwardQualityDetails> existingQualities = beamInward.getQualityDetails();
                if (existingQualities == null) {
                    existingQualities = new ArrayList<>();
                    beamInward.setQualityDetails(existingQualities);
                }
                existingQualities.clear();
                
                for (SizingQualityDetails sq : sizingPlan.getQualityDetails()) {
                    BeamInwardQualityDetails bq = new BeamInwardQualityDetails();
                    bq.setBeamInward(beamInward);
                    bq.setQuality(sq.getQuality());
                    bq.setFabricCode(sq.getFabricCode());
                    bq.setYarnId(sq.getYarnId());
                    bq.setActualYarn(sq.getActualYarn());
                    bq.setSordEnds(sq.getSordEnds());
                    bq.setActualEnds(sq.getActualEnds());
                    bq.setWarpEnds(sq.getWarpEnds());
                    bq.setActualWarpEnds(sq.getActualWarpEnds());
                    bq.setParts(sq.getParts());
                    bq.setEndsPerPart(sq.getEndsPerPart());
                    bq.setWrapMeters(sq.getWarpMeters());
                    bq.setFabricPanelWidth(sq.getFabricPanelWidth());
                    existingQualities.add(bq);
                }
            }

            // Handle Beam Details
            List<BeamInwardBeamDetails> targetDetails = beamInward.getBeamDetails();
            if (targetDetails == null) {
                targetDetails = new ArrayList<>();
                beamInward.setBeamDetails(targetDetails);
            }
            
            Map<String, BeamInwardBeamDetails> existingDetailMap = targetDetails.stream()
                    .filter(d -> d.getSizedBeamNo() != null)
                    .collect(Collectors.toMap(BeamInwardBeamDetails::getSizedBeamNo, d -> d, (e, r) -> e));

            List<BeamInwardBeamDetails> updatedDetails = new ArrayList<>();
            
            // Unique details by sizedBeamNo from entry.getValue()
            Map<String, SizingBeamOutwardDetail> uniqueSourceDetails = entry.getValue().stream()
                    .filter(d -> d.getSizedBeamNo() != null)
                    .collect(Collectors.toMap(SizingBeamOutwardDetail::getSizedBeamNo, d -> d, (e, r) -> r));

            for (SizingBeamOutwardDetail detail : uniqueSourceDetails.values()) {
                BeamInwardBeamDetails beamDetail = existingDetailMap.get(detail.getSizedBeamNo());
                if (beamDetail == null) {
                    beamDetail = new BeamInwardBeamDetails();
                    beamDetail.setBeamInward(beamInward);
                }
                
                beamDetail.setWeavingContractId(detail.getWeavingContractId());
                beamDetail.setSalesOrderId(detail.getSalesOrderId());
                beamDetail.setSizedBeamNo(detail.getSizedBeamNo());
                beamDetail.setFlangeNo(detail.getFlangeNo());
                beamDetail.setFlangeId(detail.getFlangeId());
                beamDetail.setWeavingVendorId(detail.getWeavingVendorId());
                beamDetail.setSizingPlanId(sizingPlanId);
                beamDetail.setTotalEnds(detail.getTotalEnds());
                beamDetail.setWrapMeters(detail.getBeamMeters());
                beamDetail.setExpectedFabricMeter(detail.getExpectedFabricMeter());
                beamDetail.setFabricPanelWidth(detail.getWidth());
                beamDetail.setInwardTypeId(inwardTypeId);
                Double planShrinkage = resolvePlanShrinkage(planShrinkageMap,
                        detail.getWeavingContractId(),
                        detail.getSalesOrderId(),
                        detail.getWeavingVendorId());
                if (planShrinkage != null) {
                    beamDetail.setShrinkage(planShrinkage);
                }
                updatedDetails.add(beamDetail);
            }
            
            targetDetails.clear();
            targetDetails.addAll(updatedDetails);
            beamInward.setTotalBeams(targetDetails.size());
            BeamInward saved = beamInwardRepository.save(beamInward);
            beamInwardIds.put(tradingCompanyId, saved.getId());
        }
        return beamInwardIds;
    }

    private List<BeamInwardBeamDetails> resolveVendorBeamDetails(Long beamInwardId, List<SizingBeamOutwardDetail> vendorDetails) {
        if (beamInwardId != null) {
            List<BeamInwardBeamDetails> direct = beamInwardBeamDetailsRepository.findByBeamInwardId(beamInwardId);
            if (direct != null && !direct.isEmpty()) {
                return direct;
            }
        }
        if (vendorDetails == null || vendorDetails.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> sizedBeamNos = vendorDetails.stream()
                .map(SizingBeamOutwardDetail::getSizedBeamNo)
                .filter(code -> code != null && !code.isBlank())
                .distinct()
                .collect(Collectors.toList());
        if (sizedBeamNos.isEmpty()) {
            return Collections.emptyList();
        }
        List<BeamInwardBeamDetails> fallback = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(sizedBeamNos);
        return fallback != null ? fallback : Collections.emptyList();
    }

    private Long resolveFactoryCompanyId(Vendor vendor) {
        if (vendor == null) {
            logFactoryCompanyResolution(null, null, null);
            return null;
        }
        String vendorName = normalizeCode(vendor.getVendorName());
        if (vendorName != null) {
            Company matchedCompany = companyRepository.findFirstByCompanyNameIgnoreCase(vendorName)
                    .orElse(null);
            if (matchedCompany != null) {
                String directKey = normalizeNameKey(matchedCompany.getCompanyName());
                if (directKey != null) {
                    companyNameLookup.putIfAbsent(directKey, matchedCompany.getId());
                }
                logFactoryCompanyResolution(vendorName, matchedCompany.getCompanyName(), matchedCompany.getId());
                return matchedCompany.getId();
            }
        }
        String normalizedKey = normalizeNameKey(vendor.getVendorName());
        if (normalizedKey == null) {
            logFactoryCompanyResolution(vendorName, null, null);
            return null;
        }
        Long cached = companyNameLookup.get(normalizedKey);
        if (cached != null) {
            logFactoryCompanyResolution(vendorName, resolveCompanyNameById(cached), cached);
            return cached;
        }
        refreshCompanyNameLookup();
        Long resolved = companyNameLookup.get(normalizedKey);
        logFactoryCompanyResolution(vendorName, resolveCompanyNameById(resolved), resolved);
        return resolved;
    }

    private void refreshCompanyNameLookup() {
        companyNameLookup.clear();
        List<Company> companies = companyRepository.findAll();
        if (companies == null || companies.isEmpty()) {
            return;
        }
        for (Company company : companies) {
            String key = normalizeNameKey(company.getCompanyName());
            if (key != null) {
                companyNameLookup.put(key, company.getId());
            }
        }
    }

    private String normalizeNameKey(String value) {
        return normalizeCompanyName(value);
    }

    private String resolveCompanyNameById(Long companyId) {
        if (companyId == null) {
            return null;
        }
        return companyRepository.findById(companyId)
                .map(Company::getCompanyName)
                .orElse(null);
    }

    private void logFactoryCompanyResolution(String vendorName, String companyName, Long companyId) {
        if (logger.isInfoEnabled()) {
            logger.info("factoryCompanyId resolution -> vendorName: {}, companyName: {}, factoryCompanyId: {}", vendorName, companyName, companyId);
        }
    }

    private boolean isInternalVendor(Vendor vendor) {
        return vendor != null && vendor.getIsInternalVendor() != null && vendor.getIsInternalVendor() == 1;
    }

    private void syncBeamOutwardRecords(SizingBeamOutward entity, Map<Long, Long> beamInwardIdsByCompany) {
        Long sizingPlanId = entity.getSizingPlanId();
        if (sizingPlanId == null) {
            return;
        }

        // Group details by weavingVendorId from the current entity ONLY
        Map<Long, List<SizingBeamOutwardDetail>> detailsByVendor = entity.getBeamDetails().stream()
                .filter(detail -> detail.getWeavingVendorId() != null)
                .collect(Collectors.groupingBy(SizingBeamOutwardDetail::getWeavingVendorId));

        // Handle deletions: Find existing BeamOutward records for this SizingBeamOutwardId and delete those not in detailsByVendor
        List<BeamOutward> existingOutwards = beamOutwardRepository.findBySizingBeamOutwardId(entity.getId());
        if (existingOutwards != null) {
            List<BeamOutward> toDelete = existingOutwards.stream()
                    .filter(bo -> !detailsByVendor.containsKey(bo.getWeavingVendorId()))
                    .collect(Collectors.toList());
            if (!toDelete.isEmpty()) {
                for (BeamOutward bo : toDelete) {
                    List<FactoryBeamInward> factoryInwards = factoryBeamInwardRepository.findByBeamOutwardId(bo.getId());
                    if (factoryInwards != null && !factoryInwards.isEmpty()) {
                        factoryBeamInwardRepository.deleteAll(factoryInwards);
                    }
                }
                beamOutwardRepository.deleteAll(toDelete);
            }
        }

        if (detailsByVendor.isEmpty()) {
            return;
        }

        SizingPlan sizingPlan = fetchSizingPlan(sizingPlanId);
        Long planVendorId = sizingPlan != null ? sizingPlan.getVendorId() : null;
        Map<String, Double> planShrinkageMap = loadPlanShrinkageMap(sizingPlanId);

        List<Long> vendorIds = new ArrayList<>(detailsByVendor.keySet());
        Map<Long, Vendor> vendorMap = vendorRepository.findAllById(vendorIds).stream()
                .collect(Collectors.toMap(Vendor::getId, vendor -> vendor));
        for (Map.Entry<Long, List<SizingBeamOutwardDetail>> entry : detailsByVendor.entrySet()) {
            Long vendorId = entry.getKey();
            List<SizingBeamOutwardDetail> vendorDetails = entry.getValue();
            if (vendorDetails == null || vendorDetails.isEmpty()) {
                continue;
            }
            Vendor vendor = vendorMap.get(vendorId);
            if (!isInternalVendor(vendor)) {
                continue;
            }
            // FIND EXISTING BeamOutward by SizingBeamOutwardId or CREATE NEW
            BeamOutward beamOutward = beamOutwardRepository.findFirstBySizingBeamOutwardIdAndWeavingVendorId(entity.getId(), vendorId)
                    .orElseGet(BeamOutward::new);

            beamOutward.setSizingBeamOutwardId(entity.getId());
            beamOutward.setSizingPlanId(sizingPlanId);
            beamOutward.setWeavingVendorId(vendorId);
            beamOutward.setSizingVendorId(planVendorId != null ? planVendorId : entity.getCompanyId());
            beamOutward.setOrderTypeId(entity.getOrderTypeId());
            if (beamOutward.getId() == null) {
                beamOutward.setOutwardDate(LocalDate.now());
                beamOutward.setFinancialYear(calculateFinancialYear(beamOutward.getOutwardDate()));
            }

            SizingBeamOutwardDetail firstDetail = vendorDetails.get(0);
            beamOutward.setWeavingContractId(firstDetail.getWeavingContractId());
            beamOutward.setSalesOrderId(firstDetail.getSalesOrderId());
            beamOutward.setSizedBeamNo(firstDetail.getSizedBeamNo());
            beamOutward.setFlangeNo(firstDetail.getFlangeNo());
            beamOutward.setWrapMeters(firstDetail.getBeamMeters());
            beamOutward.setExpectedFabricMeter(firstDetail.getBeamMeters());
            beamOutward.setFabricPanelWidth(formatWidth(firstDetail.getWidth()));

            Long tradingCompanyId = firstDetail.getIssuedTradingCompanyId();
            if (entity.getOrderTypeId() != null && entity.getOrderTypeId().longValue() == 1L && entity.getBuyerId() != null) {
                tradingCompanyId = entity.getBuyerId();
            }
            beamOutward.setCompanyId(entity.getBuyerId());
            beamOutward.setTradingCompanyId(tradingCompanyId);
            Long resolvedFactoryCompanyId = resolveFactoryCompanyId(vendor);
            beamOutward.setFactoryCompanyId(resolvedFactoryCompanyId);
            beamOutward.setBeamInwardId(tradingCompanyId != null ? beamInwardIdsByCompany.get(tradingCompanyId) : null);
            beamOutward.setBeamInwardNo(resolveBeamInwardNo(beamOutward.getBeamInwardId()));
            beamOutward.setWeavingWorkOrderId(resolveWeavingWorkOrderId(firstDetail.getWeavingContractId(), beamOutward.getWeavingWorkOrderId()));
            if (beamOutward.getOutwardNo() == null || beamOutward.getOutwardNo().isBlank()) {
                Long outwardCompanyId = beamOutward.getTradingCompanyId() != null ? beamOutward.getTradingCompanyId() : beamOutward.getCompanyId();
                beamOutward.setOutwardNo(generateBeamOutwardNo(outwardCompanyId));
            }
            beamOutward.setIsFactoryInward(1L);

            List<BeamInwardBeamDetails> matchingBeamDetails = resolveVendorBeamDetails(beamOutward.getBeamInwardId(), vendorDetails);
            Map<String, BeamInwardBeamDetails> beamDetailsBySized = matchingBeamDetails.isEmpty()
                    ? Collections.emptyMap()
                    : matchingBeamDetails.stream()
                            .filter(detail -> detail.getSizedBeamNo() != null && !detail.getSizedBeamNo().isBlank())
                            .collect(Collectors.toMap(BeamInwardBeamDetails::getSizedBeamNo,
                                    detail -> detail,
                                    (existing, duplicate) -> existing));
            Map<String, BeamInwardBeamDetails> beamInwardByFlange = matchingBeamDetails.isEmpty()
                    ? Collections.emptyMap()
                    : matchingBeamDetails.stream()
                            .filter(detail -> detail.getFlangeNo() != null && !detail.getFlangeNo().isBlank())
                            .collect(Collectors.toMap(BeamInwardBeamDetails::getFlangeNo,
                                    detail -> detail,
                                    (existing, duplicate) -> existing));
            BeamInwardBeamDetails headerMatch = selectMatchingBeamInwardDetail(beamDetailsBySized, beamInwardByFlange,
                    firstDetail.getSizedBeamNo(), firstDetail.getFlangeNo());
            Double planShrinkage = resolvePlanShrinkage(planShrinkageMap,
                    firstDetail.getWeavingContractId(),
                    firstDetail.getSalesOrderId(),
                    firstDetail.getWeavingVendorId());
            Double headerShrinkage = headerMatch != null && headerMatch.getShrinkage() != null
                    ? headerMatch.getShrinkage()
                    : planShrinkage;
            beamOutward.setShrinkage(headerShrinkage);

            // Handle Details
            List<BeamOutwardDetail> outwardDetailsList = beamOutward.getOutwardDetails();
            if (outwardDetailsList == null) {
                outwardDetailsList = new ArrayList<>();
                beamOutward.setOutwardDetails(outwardDetailsList);
            }
            
            Map<String, BeamOutwardDetail> existingDetailMap = outwardDetailsList.stream()
                    .filter(d -> d.getSizedBeamNo() != null)
                    .collect(Collectors.toMap(BeamOutwardDetail::getSizedBeamNo, d -> d, (e, r) -> e));

            List<BeamOutwardDetail> updatedDetails = new ArrayList<>();
            
            // Unique details by sizedBeamNo from vendorDetails
            Map<String, SizingBeamOutwardDetail> uniqueSourceDetails = vendorDetails.stream()
                    .filter(d -> d.getSizedBeamNo() != null)
                    .collect(Collectors.toMap(SizingBeamOutwardDetail::getSizedBeamNo, d -> d, (e, r) -> r));

            for (SizingBeamOutwardDetail detail : uniqueSourceDetails.values()) {
                BeamOutwardDetail outwardDetail = existingDetailMap.get(detail.getSizedBeamNo());
                if (outwardDetail == null) {
                    outwardDetail = new BeamOutwardDetail();
                    outwardDetail.setBeamOutward(beamOutward);
                }
                
                outwardDetail.setWeavingContractId(detail.getWeavingContractId());
                outwardDetail.setWeavingVendorId(detail.getWeavingVendorId());
                outwardDetail.setSalesOrderId(detail.getSalesOrderId());
                outwardDetail.setSizedBeamNo(detail.getSizedBeamNo());
                outwardDetail.setFlangeNo(detail.getFlangeNo());
                outwardDetail.setFlangeId(detail.getFlangeId());
                
                BeamInwardBeamDetails matched = selectMatchingBeamInwardDetail(beamDetailsBySized, beamInwardByFlange,
                        detail.getSizedBeamNo(), detail.getFlangeNo());
                
                Double detailPlanShrinkage = resolvePlanShrinkage(planShrinkageMap,
                        detail.getWeavingContractId(),
                        detail.getSalesOrderId(),
                        detail.getWeavingVendorId());
  
                Double detailShrinkage = matched != null && matched.getShrinkage() != null
                        ? matched.getShrinkage()
                        : (detailPlanShrinkage != null ? detailPlanShrinkage : beamOutward.getShrinkage());
                outwardDetail.setShrinkage(detailShrinkage);
                outwardDetail.setWrapMeters(detail.getBeamMeters());
                outwardDetail.setExpectedFabricMeter(detail.getExpectedFabricMeter() != null
                        ? detail.getExpectedFabricMeter()
                        : detail.getBeamMeters());
                outwardDetail.setFabricPanelWidth(formatWidth(detail.getWidth()));
                Integer totalEndsCount = detail.getTotalEnds();
                outwardDetail.setTotalEnds(totalEndsCount != null ? totalEndsCount.longValue() : null);
                outwardDetail.setIsFactoryInward(1L);
                updatedDetails.add(outwardDetail);
            }

            outwardDetailsList.clear();
            outwardDetailsList.addAll(updatedDetails);

            beamOutward.setTotalNoOfBeams((long) outwardDetailsList.size());
            double totalMeters = outwardDetailsList.stream()
                    .map(BeamOutwardDetail::getWrapMeters)
                    .filter(Objects::nonNull)
                    .mapToDouble(Double::doubleValue)
                    .sum();
            beamOutward.setTotalBeamMeters(totalMeters > 0 ? totalMeters : null);
            beamOutward.setTotalExpectedMeters(totalMeters > 0 ? totalMeters : null);

            BeamOutward savedBeamOutward = beamOutwardRepository.save(beamOutward);
            updateIssuedToWeavingStatus(vendorDetails, 1L);

            // Group vendorDetails by weavingContractId — separate FactoryBeamInward per contract
            Map<String, List<SizingBeamOutwardDetail>> detailsByContract = vendorDetails.stream()
                    .collect(Collectors.groupingBy(d -> d.getWeavingContractId() != null ? String.valueOf(d.getWeavingContractId()) : ""));

            // Delete FactoryBeamInward entries whose contract is no longer present for this BeamOutward
            List<FactoryBeamInward> existingFactoryInwards = factoryBeamInwardRepository.findByBeamOutwardId(savedBeamOutward.getId());
            if (existingFactoryInwards != null) {
                existingFactoryInwards.stream()
                        .filter(f -> {
                            String fKey = f.getWeavingContractId() != null ? f.getWeavingContractId() : "";
                            return !detailsByContract.containsKey(fKey);
                        })
                        .forEach(factoryBeamInwardRepository::delete);
            }

            boolean anyFactorySync = false;
            for (Map.Entry<String, List<SizingBeamOutwardDetail>> contractEntry : detailsByContract.entrySet()) {
                String weavingContractIdStr = contractEntry.getKey();
                Long weavingContractId = !weavingContractIdStr.isEmpty() ? Long.parseLong(weavingContractIdStr) : null;
                List<SizingBeamOutwardDetail> contractDetails = contractEntry.getValue();
                if (syncFactoryBeamInwardForContract(savedBeamOutward, sizingPlan, vendor, entity.getBuyerId(), weavingContractId, contractDetails)) {
                    anyFactorySync = true;
                }
            }
            if (anyFactorySync) {
                updateFactoryInwardStatus(vendorDetails, 1L);
            }
        }
    }

    private String generateBeamOutwardNo(Long companyId) {
        String cmpPrefix = "";
        if (companyId != null) {
            Company company = companyRepository.findById(companyId).orElse(null);
            if (company != null) {
                if (StringUtils.hasText(company.getCompanyCode())) {
                    cmpPrefix = company.getCompanyCode() + "/";
                } else if (StringUtils.hasText(company.getCompanyName())) {
                    cmpPrefix = company.getCompanyName().substring(0, 1).toUpperCase() + "/";
                }
            }
        }
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
        return cmpPrefix + "BO-" + datePart + "-" + randomNum;
    }

    private String generateBeamInwardNo(Long companyId) {
        String cmpPrefix = "";
        if (companyId != null) {
            Company company = companyRepository.findById(companyId).orElse(null);
            if (company != null) {
                if (StringUtils.hasText(company.getCompanyCode())) {
                    cmpPrefix = company.getCompanyCode() + "/";
                } else if (StringUtils.hasText(company.getCompanyName())) {
                    cmpPrefix = company.getCompanyName().substring(0, 1).toUpperCase() + "/";
                }
            }
        }
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
        return cmpPrefix + "BI-" + datePart + "-" + randomNum;
    }

    private String resolveBeamInwardNo(Long beamInwardId) {
        if (beamInwardId == null) {
            return null;
        }
        return beamInwardRepository.findById(beamInwardId)
                .map(BeamInward::getBeamInwardNo)
                .orElse(null);
    }

    private Long resolveWeavingWorkOrderId(Long weavingContractId, Long currentValue) {
        if (currentValue != null) {
            return currentValue;
        }
        if (weavingContractId == null) {
            return null;
        }
        return weavingWorkOrderRepository.findFirstByWeavingContractIdOrderByIdDesc(weavingContractId)
                .map(WeavingWorkOrder::getId)
                .orElse(null);
    }

    private SizingPlan fetchSizingPlan(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return null;
        }
        return sizingPlanRepository.findById(sizingPlanId).orElse(null);
    }

    private SizingBeamOutwardDTO mapEntityToDto(SizingBeamOutward entity) {
        SizingBeamOutwardDTO dto = new SizingBeamOutwardDTO();
        dto.setId(entity.getId());
        dto.setSizingBeamOutwardNo(entity.getSizingBeamOutwardNo());
        dto.setOrderType(entity.getOrderType());
        dto.setSizingOrderId(entity.getSizingOrderId());
        dto.setSizingOrderNo(entity.getSizingOrderNo());
        dto.setSizingPlanId(entity.getSizingPlanId());
        dto.setSizingPlanNo(entity.getSizingPlanNo());
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setBuyerName(entity.getBuyerName());
        dto.setBuyerId(entity.getBuyerId());
        dto.setYarnName(entity.getYarnName());
        dto.setYarnId(entity.getYarnId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setOutwardDate(entity.getOutwardDate());
        List<String> delivery = entity.getDeliveryAt();
        dto.setDeliveryAt(delivery == null ? new ArrayList<>() : new ArrayList<>(delivery));
        dto.setSummary(buildSummary(entity));
        dto.setIsReturn(entity.getIsReturn());
        dto.setReturnDate(entity.getReturnDate());
        dto.setReturnNo(entity.getReturnNo());
        List<SizingBeamOutwardDetailDTO> detailDtos = detailRepository.findBySizingBeamOutwardId(entity.getId()).stream()
                .sorted(Comparator.comparing(SizingBeamOutwardDetail::getId))
                .map(this::mapDetailEntityToDto)
                .collect(Collectors.toList());
        dto.setBeamDetails(detailDtos);
        return dto;
    }

    private SizingBeamOutwardSummaryDTO buildSummary(SizingBeamOutward entity) {
        if (entity.getNoOfBeams() == null && entity.getTotalBeamMeters() == null && entity.getTotalEnds() == null) {
            return null;
        }
        SizingBeamOutwardSummaryDTO summary = new SizingBeamOutwardSummaryDTO();
        summary.setNoOfBeams(entity.getNoOfBeams());
        summary.setTotalBeamMeters(entity.getTotalBeamMeters());
        summary.setTotalEnds(entity.getTotalEnds());
        return summary;
    }

    private SizingBeamOutwardDetailDTO mapDetailEntityToDto(SizingBeamOutwardDetail entity) {
        SizingBeamOutwardDetailDTO dto = new SizingBeamOutwardDetailDTO();
        dto.setId(entity.getId());
        dto.setSizedBeamNo(entity.getSizedBeamNo());
        dto.setWidth(entity.getWidth());
        dto.setTotalEnds(entity.getTotalEnds());
        dto.setBeamMeters(entity.getBeamMeters());
        dto.setExpectedFabricMeter(entity.getExpectedFabricMeter());
        dto.setFlangeNo(entity.getFlangeNo());
        dto.setFlangeId(entity.getFlangeId());
        dto.setWeavingContractNo(entity.getWeavingContractNo());
        dto.setSalesOrderNo(entity.getSalesOrderNo());
        dto.setWeavingContractId(entity.getWeavingContractId());
        dto.setWeavingVendorId(entity.getWeavingVendorId());
        dto.setSalesOrderId(entity.getSalesOrderId());
        dto.setIssuedTradingCompanyId(entity.getIssuedTradingCompanyId());
        dto.setIsBeamOutward(entity.getIsBeamOutward());
        return dto;
    }

    private String calculateFinancialYear(LocalDate date) {
        LocalDate reference = date != null ? date : LocalDate.now();
        int year = reference.getYear();
        if (reference.getMonthValue() >= 4) {
            int nextYear = year + 1;
            return String.format("%d-%d", year, nextYear % 100);
        }
        int previousYear = year - 1;
        return String.format("%d-%d", previousYear, year % 100);
    }
}