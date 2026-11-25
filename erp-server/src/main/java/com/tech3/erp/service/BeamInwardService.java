package com.tech3.erp.service;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.BeamInwardBeamDetailsDTO;
import com.tech3.erp.dto.BeamInwardDTO;
import com.tech3.erp.dto.BeamInwardQualityDetailsDTO;
import com.tech3.erp.dto.BeamOutwardDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.BeamInward;
import com.tech3.erp.entity.BeamInwardBeamDetails;
import com.tech3.erp.entity.BeamInwardQualityDetails;
import com.tech3.erp.entity.BeamOutward;
import com.tech3.erp.entity.BeamOutwardDetail;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.SizingBeamDetails;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.BeamInwardQualityDetailsRepository;
import com.tech3.erp.repository.BeamInwardRepository;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.SizingBeamDetailsRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.SizingQualityDetailsRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.util.PaginationHelper;

import com.tech3.erp.entity.BeamInwardInvoiceDetails;
import com.tech3.erp.dto.BeamInwardInvoiceDetailsDTO;
import com.tech3.erp.repository.BeamInwardInvoiceDetailsRepository;
import com.tech3.erp.entity.FactoryBeamInward;
import com.tech3.erp.entity.FactoryBeamInwardBeamDetails;
import com.tech3.erp.entity.FactoryBeamInwardQualityDetails;
import com.tech3.erp.repository.FactoryBeamInwardRepository;
import com.tech3.erp.repository.FactoryBeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.FactoryBeamInwardQualityDetailsRepository;
import com.tech3.erp.entity.SizingQualityDetails;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.service.SyncContext;
import com.tech3.erp.entity.WeftInward;
import com.tech3.erp.repository.WeftInwardRepository;
import com.tech3.erp.repository.SalesReturnBeamRepository;
import com.tech3.erp.entity.SalesReturnBeamEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.StringUtils;

@Service
public class BeamInwardService {

    private final BeamInwardRepository beamInwardRepository;
    private final BeamInwardBeamDetailsRepository beamDetailsRepository;
    private final BeamInwardQualityDetailsRepository qualityDetailsRepository;
    private final BeamOutwardRepository beamOutwardRepository;
    private final SizingBeamDetailsRepository sizingBeamDetailsRepository; // Add this
    private final SizingQualityDetailsRepository sizingQualityDetailsRepository;
    private final ReferenceValidationService referenceValidationService;
    private final SizingPlanRepository sizingPlanRepository;
    private final WeftInwardRepository weftInwardRepository;
    private final com.tech3.erp.repository.SizingProductionRepository sizingProductionRepository;
    private final BeamInwardInvoiceDetailsRepository invoiceDetailsRepository;
    private final VendorRepository vendorRepository;
    private final FactoryBeamInwardRepository factoryBeamInwardRepository;
    private final FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository;
    private final FactoryBeamInwardQualityDetailsRepository factoryBeamInwardQualityDetailsRepository;
    private final CompanyRepository companyRepository;
    private final CodesDetailRepository codesDetailRepository;

    @Lazy
    @Autowired
    private BeamOutwardService beamOutwardService;

    @Lazy
    @Autowired
    private FactoryBeamInwardService factoryBeamInwardService;

    @Lazy
    @Autowired
    private SizingProductionService sizingProductionService;

    @Autowired
    private SalesReturnBeamRepository salesReturnBeamRepository;


    public BeamInwardService(
    		BeamInwardRepository beamInwardRepository,
    		BeamInwardBeamDetailsRepository beamDetailsRepository,
    		BeamInwardQualityDetailsRepository qualityDetailsRepository,
            BeamOutwardRepository beamOutwardRepository,
            SizingBeamDetailsRepository sizingBeamDetailsRepository,
            SizingQualityDetailsRepository sizingQualityDetailsRepository,
            ReferenceValidationService referenceValidationService,
            SizingPlanRepository sizingPlanRepository,
            WeftInwardRepository weftInwardRepository,
            com.tech3.erp.repository.SizingProductionRepository sizingProductionRepository,
            BeamInwardInvoiceDetailsRepository invoiceDetailsRepository,
            VendorRepository vendorRepository,
            FactoryBeamInwardRepository factoryBeamInwardRepository,
            FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository,
            FactoryBeamInwardQualityDetailsRepository factoryBeamInwardQualityDetailsRepository,
            CompanyRepository companyRepository,
            CodesDetailRepository codesDetailRepository

    ) {
        this.beamInwardRepository = beamInwardRepository;
        this.beamDetailsRepository = beamDetailsRepository;
        this.qualityDetailsRepository = qualityDetailsRepository;
        this.beamOutwardRepository = beamOutwardRepository;
        this.sizingBeamDetailsRepository = sizingBeamDetailsRepository;
        this.sizingQualityDetailsRepository = sizingQualityDetailsRepository;
        this.referenceValidationService = referenceValidationService;
        this.sizingPlanRepository = sizingPlanRepository;
        this.weftInwardRepository = weftInwardRepository;
        this.sizingProductionRepository = sizingProductionRepository;
        this.invoiceDetailsRepository = invoiceDetailsRepository;
        this.vendorRepository = vendorRepository;
        this.factoryBeamInwardRepository = factoryBeamInwardRepository;
        this.factoryBeamInwardBeamDetailsRepository = factoryBeamInwardBeamDetailsRepository;
        this.factoryBeamInwardQualityDetailsRepository = factoryBeamInwardQualityDetailsRepository;
        this.companyRepository = companyRepository;
        this.codesDetailRepository = codesDetailRepository;
    }

    @Transactional
    public BeamInwardDTO saveBeamInward(BeamInwardDTO dto) {
        requireSizingPlanReference(dto.getSizingPlanId());
        BeamInward plan = mapDtoToEntity(dto, new BeamInward());
        final BeamInward savedBeamInward = beamInwardRepository.save(plan);

        if (savedBeamInward.getOrderTypeId() != null && savedBeamInward.getOrderTypeId().equals(1L)) {
            WeftInward weft = new WeftInward();
            weft.setInwardDate(savedBeamInward.getBeamInwardDate() != null ? savedBeamInward.getBeamInwardDate() : java.time.LocalDate.now());
            weft.setWeavingContractId(null);
            weft.setWeavingJobWorkOrderId(null);
            weft.setFabricCode(null);
            weft.setDcNo(null);
            weft.setDcDate(null);
            weft.setActiveFlag(true);
            weft.setOrderTypeId(savedBeamInward.getOrderTypeId());
            weft.setLinkedBeamInwardId(savedBeamInward.getId());
            weft.setCompanyId(savedBeamInward.getCompanyId());
            weftInwardRepository.save(weft);
        }

        final Long inwardTypeId;
        if (savedBeamInward.getOrderTypeId() != null) {
            String targetCode = savedBeamInward.getOrderTypeId().equals(1L) ? "251" : 
                                savedBeamInward.getOrderTypeId().equals(2L) ? "252" : null;
            if (targetCode != null) {
                Optional<CodesDetail> cd = codesDetailRepository.findByCode(targetCode);
                if (cd.isEmpty()) {
                    cd = codesDetailRepository.findByValue(savedBeamInward.getOrderTypeId().equals(1L) ? "Inhouse Beam" : "Trading Beam");
                }
                if (cd.isEmpty()) {
                    cd = codesDetailRepository.findByValue(savedBeamInward.getOrderTypeId().equals(1L) ? "INHOUSE BEAM" : "TRADING BEAM");
                }
                inwardTypeId = cd.map(CodesDetail::getId).orElse(null);
            } else {
                inwardTypeId = null;
            }
        } else {
            inwardTypeId = null;
        }

        // Save beam details and update status in sizing_beam_details
        List<BeamInwardBeamDetails> beamEntities = dto.getBeamInwardBeamDetails().stream().map(b -> {
            BeamInwardBeamDetails beam = new BeamInwardBeamDetails();
            beam.setBeamInward(savedBeamInward);
            beam.setWeavingContractId(b.getWeavingContractId());
            beam.setSalesOrderId(b.getSalesOrderId());
            beam.setSizedBeamNo(b.getSizedBeamNo());
            beam.setFlangeNo(b.getFlangeNo());
            beam.setFlangeId(b.getFlangeId());
            beam.setWrapMeters(b.getWrapMeters());
            beam.setShrinkage(b.getShrinkage());
            beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
            beam.setTotalEnds(b.getTotalEnds());
            beam.setFabricPanelWidth(b.getFabricPanelWidth());
            beam.setWeavingVendorId(b.getWeavingVendorId());
            beam.setFabricId(b.getFabricId());
            beam.setSizingPlanId(dto.getSizingPlanId());
            beam.setIsBeamOutwarded(b.getIsBeamOutwarded() != null ? b.getIsBeamOutwarded() : 0);
            beam.setIsReturn(b.getIsReturn());
            beam.setReturnDate(b.getReturnDate());
            beam.setReturnNo(b.getReturnNo());
            beam.setReturnWeaverId(b.getReturnWeaverId());
            beam.setInwardTypeId(inwardTypeId);
            beam.setWarpCount(b.getWarpCount());
            beam.setWarpYarnMillName(b.getWarpYarnMillName());
            beam.setWarpYarnLotNo(b.getWarpYarnLotNo());
            beam.setDcNumber(b.getDcNumber());
            beam.setDcDate(b.getDcDate());
            beam.setSizingSetNo(b.getSizingSetNo());
            beam.setSizingPickupPercent(b.getSizingPickupPercent());
            
            // Update status in sizing_beam_details if it exists
            updateSizingBeamStatus(dto.getSizingPlanId(), b);
            
            return beam;
        }).collect(Collectors.toList());
        beamDetailsRepository.saveAll(beamEntities);

        List<BeamInwardQualityDetails> qualityEntities = dto.getBeamInwardQualityDetails().stream().map(q -> {
            BeamInwardQualityDetails quality = new BeamInwardQualityDetails();
            quality.setBeamInward(savedBeamInward);
            quality.setQuality(q.getQuality());
            quality.setYarnId(q.getYarnId());
            quality.setSordEnds(q.getSordEnds());
            quality.setActualEnds(q.getActualEnds());
            quality.setParts(q.getParts());
            quality.setEndsPerPart(q.getEndsPerPart());
            quality.setWrapMeters(q.getWrapMeters());
            quality.setFabricPanelWidth(q.getFabricPanelWidth());
            quality.setFabricCode(q.getFabricCode());
            quality.setActualYarn(q.getActualYarn());
            quality.setWarpEnds(q.getWarpEnds());
            quality.setActualWarpEnds(q.getActualWarpEnds());
            quality.setTotalEnds(q.getTotalEnds());
            return quality;
        }).collect(Collectors.toList());
        qualityDetailsRepository.saveAll(qualityEntities);

        autoCreateBeamOutwardsFromInward(savedBeamInward, dto);

        if (dto.getInvoiceDetails() != null && !dto.getInvoiceDetails().isEmpty()) {
            List<BeamInwardInvoiceDetails> invoiceEntities = dto.getInvoiceDetails().stream().map(i -> {
                BeamInwardInvoiceDetails inv = new BeamInwardInvoiceDetails();
                inv.setBeamInward(savedBeamInward);
                inv.setInvNo(i.getInvNo());
                inv.setInvDate(i.getInvDate());
                inv.setSizingCost(i.getSizingCost());
                inv.setGstAmount(i.getGstAmount());
                inv.setNetTotal(i.getNetTotal());
                inv.setMediaDetailSno(i.getMediaDetailSno());
                inv.setMediaSno(i.getMediaSno());
                inv.setInvoiceUpload(i.getInvoiceUpload());
                return inv;
            }).collect(Collectors.toList());
            invoiceDetailsRepository.saveAll(invoiceEntities);
        }

        BeamInwardDTO savedDto = getBeamInwardById(savedBeamInward.getId());
        if (!SyncContext.isInProgress()) {
            try {
                SyncContext.setInProgress(true);
                syncToRelatedBeams(savedDto);
            } finally {
                SyncContext.clear();
            }
        }
        syncMissingFactoryBeamInwardOnUpdate(savedBeamInward, dto);
        return savedDto;
    }
    
    private void autoCreateBeamOutwardsFromInward(BeamInward savedBeamInward, BeamInwardDTO dto) {
        if (savedBeamInward.getOrderTypeId() != null && savedBeamInward.getOrderTypeId().equals(1L)) {
            return;
        }
        if (dto.getBeamInwardBeamDetails() == null || dto.getBeamInwardBeamDetails().isEmpty()) {
            return;
        }

        // Group by both weavingVendorId and weavingContractId
        Map<String, List<BeamInwardBeamDetailsDTO>> groupedBeams = new LinkedHashMap<>();
        for (BeamInwardBeamDetailsDTO b : dto.getBeamInwardBeamDetails()) {
            Long vId = b.getWeavingVendorId();
            Long cId = b.getWeavingContractId();
            String key = (vId != null ? vId : "null") + "_" + (cId != null ? cId : "null");
            groupedBeams.computeIfAbsent(key, k -> new ArrayList<>()).add(b);
        }

        for (List<BeamInwardBeamDetailsDTO> group : groupedBeams.values()) {
            if (group.isEmpty()) continue;

            BeamInwardBeamDetailsDTO first = group.get(0);
            Long vendorId = first.getWeavingVendorId();
            Long contractId = first.getWeavingContractId();
            boolean internal = isInternalVendor(vendorId);

            BeamOutward outward = new BeamOutward();
            outward.setBeamInwardId(savedBeamInward.getId());
            outward.setBeamInwardNo(savedBeamInward.getBeamInwardNo());
            outward.setSizingVendorId(savedBeamInward.getSizingVendorId());
            outward.setSizingPlanId(savedBeamInward.getSizingPlanId());
            outward.setOrderTypeId(savedBeamInward.getOrderTypeId());
            outward.setCompanyId(savedBeamInward.getCompanyId());
            outward.setWeavingVendorId(vendorId);
            outward.setWeavingContractId(contractId);
            outward.setOutwardDate(savedBeamInward.getBeamInwardDate());
            outward.setTotalNoOfBeams((long) group.size());

            double totalBeamMeters = group.stream()
                    .mapToDouble(b -> b.getWrapMeters() != null ? b.getWrapMeters() : 0.0)
                    .sum();
            double totalExpectedMeters = group.stream()
                    .mapToDouble(b -> b.getExpectedFabricMeter() != null ? b.getExpectedFabricMeter() : 0.0)
                    .sum();
            outward.setTotalBeamMeters(totalBeamMeters > 0 ? totalBeamMeters : null);
            outward.setTotalExpectedMeters(totalExpectedMeters > 0 ? totalExpectedMeters : null);

            outward.setIsIssuedToWeaving(1L);
            outward.setIsFactoryInward(internal ? 1L : 0L);
            outward.setOutwardNo(generateBeamOutwardNo(savedBeamInward.getCompanyId()));

            List<BeamOutwardDetail> details = new ArrayList<>();
            for (BeamInwardBeamDetailsDTO b : group) {
                BeamOutwardDetail detail = new BeamOutwardDetail();
                detail.setBeamOutward(outward);
                detail.setWeavingContractId(b.getWeavingContractId());
                detail.setWeavingVendorId(b.getWeavingVendorId());
                detail.setSalesOrderId(b.getSalesOrderId());
                detail.setSizedBeamNo(b.getSizedBeamNo());
                detail.setFlangeNo(b.getFlangeNo());
                detail.setFlangeId(b.getFlangeId());
                detail.setWrapMeters(b.getWrapMeters());
                detail.setShrinkage(b.getShrinkage());
                detail.setExpectedFabricMeter(b.getExpectedFabricMeter());
                detail.setFabricPanelWidth(b.getFabricPanelWidth() != null ? String.valueOf(b.getFabricPanelWidth()) : null);
                detail.setTotalEnds(b.getTotalEnds() != null ? b.getTotalEnds().longValue() : null);
                detail.setIsIssuedToWeaving(1L);
                detail.setIsFactoryInward(internal ? 1L : 0L);
                details.add(detail);
            }
            outward.setOutwardDetails(details);

            BeamOutward savedOutward = beamOutwardRepository.save(outward);
            markVendorBeamsAsOutwarded(savedBeamInward.getId(), group);

            if (internal) {
                beamOutwardService.syncToFactoryBeamInward(savedOutward.getId());
            }
        }
    }

    private boolean isInternalVendor(Long vendorId) {
        if (vendorId == null) return false;
        return vendorRepository.findById(vendorId)
                .map(v -> v.getIsInternalVendor() != null && v.getIsInternalVendor() == 1)
                .orElse(false);
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

    private void markVendorBeamsAsOutwarded(Long beamInwardId, List<BeamInwardBeamDetailsDTO> beams) {
        for (BeamInwardBeamDetailsDTO b : beams) {
            if (b.getSizedBeamNo() == null) continue;
            List<BeamInwardBeamDetails> matchingDetails = beamDetailsRepository
                    .findByBeamInwardIdAndSizedBeamNo(beamInwardId, b.getSizedBeamNo());
            for (BeamInwardBeamDetails detail : matchingDetails) {
                boolean flangeMatch = (detail.getFlangeNo() == null && b.getFlangeNo() == null)
                        || (detail.getFlangeNo() != null && detail.getFlangeNo().equals(b.getFlangeNo()));
                if (flangeMatch) {
                    detail.setIsBeamOutwarded(1);
                    beamDetailsRepository.save(detail);
                    break;
                }
            }
        }
    }

    private void updateSizingBeamStatus(Long sizingPlanId, BeamInwardBeamDetailsDTO beamDto) {
        if (sizingPlanId != null && beamDto.getWeavingContractId() != null && 
            beamDto.getSalesOrderId() != null) {
            
            // Find matching sizing beam details
            List<SizingBeamDetails> sizingBeams = sizingBeamDetailsRepository
                .findBySizingPlanId(sizingPlanId);
            
            for (SizingBeamDetails sizingBeam : sizingBeams) {
                if (sizingBeam.getWeavingContractId().equals(beamDto.getWeavingContractId()) &&
                    sizingBeam.getSalesOrderId().equals(beamDto.getSalesOrderId()) &&
                    (sizingBeam.getStatus() == null || sizingBeam.getStatus() == 0)) { // Only update if status is 0
                    
                    sizingBeam.setStatus(1); // Mark as inwarded
                    sizingBeamDetailsRepository.save(sizingBeam);
                    break; // Update only one matching beam
                }
            }
        }
    }

    private void revertSizingBeamStatus(Long sizingPlanId, List<BeamInwardBeamDetails> beamDetails) {
        if (sizingPlanId != null && beamDetails != null && !beamDetails.isEmpty()) {
            List<SizingBeamDetails> sizingBeams = sizingBeamDetailsRepository
                .findBySizingPlanId(sizingPlanId);
            
            for (BeamInwardBeamDetails beamDetail : beamDetails) {
                if (beamDetail.getWeavingContractId() != null && beamDetail.getSalesOrderId() != null) {
                    for (SizingBeamDetails sizingBeam : sizingBeams) {
                        if (sizingBeam.getWeavingContractId().equals(beamDetail.getWeavingContractId()) &&
                            sizingBeam.getSalesOrderId().equals(beamDetail.getSalesOrderId()) &&
                            (sizingBeam.getStatus() != null && sizingBeam.getStatus() == 1)) {
                            
                            sizingBeam.setStatus(0); // Reset back to available
                            sizingBeamDetailsRepository.save(sizingBeam);
                            break;
                        }
                    }
                }
            }
        }
    }


    public List<BeamInwardDTO> getAllBeamInward() {
        List<BeamInward> plans = beamInwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return plans.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BeamInwardDTO> getAllBeamInwardWithPagination(Long offset, Long limit) {
        long finalLimit = (limit != null && limit > 0) ? limit : PaginationHelper.getDefaultLimit();
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
        Page<BeamInward> page = beamInwardRepository.findAll(pageRequest);
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    public List<BeamInwardDTO> getAllBeamInwardByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return beamInwardRepository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BeamInwardDTO> getAllBeamInwardByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        long finalLimit = (limit != null && limit > 0) ? limit : PaginationHelper.getDefaultLimit();
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
        Page<BeamInward> page = beamInwardRepository.findByCompanyId(companyId, pageRequest);
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    public BeamInwardDTO getBeamInwardById(Long id) {
    	BeamInward plan = beamInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BeamInward not found with id: " + id));
        return mapEntityToDto(plan);
    }
    
    public BeamInwardDTO getBeamInwardByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        BeamInward plan = beamInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("BeamInward not found with id: " + id + " for company: " + companyId));
        return mapEntityToDto(plan);
    }

    @Transactional
    public BeamInwardDTO updateBeamInward(Long id, BeamInwardDTO dto) {
        if (SyncContext.isInProgress()) {
            return doUpdateBeamInward(id, dto);
        }
        try {
            SyncContext.setInProgress(true);
            BeamInwardDTO updated = doUpdateBeamInward(id, dto);
            syncToRelatedBeams(updated);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private BeamInwardDTO doUpdateBeamInward(Long id, BeamInwardDTO dto) {
        BeamInward existingPlan = beamInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BeamInward not found with id: " + id));
        requireSizingPlanReference(dto.getSizingPlanId());
        return updateBeamInwardEntity(existingPlan, dto);
    }

    @Transactional
    public BeamInwardDTO updateBeamInwardByCompany(Long companyId, Long id, BeamInwardDTO dto) {
        if (SyncContext.isInProgress()) {
            return doUpdateBeamInwardByCompany(companyId, id, dto);
        }
        try {
            SyncContext.setInProgress(true);
            BeamInwardDTO updated = doUpdateBeamInwardByCompany(companyId, id, dto);
            syncToRelatedBeams(updated);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private BeamInwardDTO doUpdateBeamInwardByCompany(Long companyId, Long id, BeamInwardDTO dto) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        BeamInward existingPlan = beamInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("BeamInward not found with id: " + id + " for company: " + companyId));
        requireSizingPlanReference(dto.getSizingPlanId());
        return updateBeamInwardEntity(existingPlan, dto);
    }

//    @Transactional
//    public void deleteSizingPlan(Long id) {
//        SizingPlan plan = beamInwardRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
//        beamDetailsRepository.deleteBySizingPlanId(id);
//        qualityDetailsRepository.deleteBySizingPlanId(id);
//        beamInwardRepository.delete(plan);
//    }
    @Transactional
    public void deleteBeamInward(Long id) {
        BeamInward plan = beamInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Beam Inward not found with id: " + id));
        ensureBeamNotMappedToFactoryBeamInward(plan);
        cleanUpLinkedBeamOutwards(plan.getId());
        revertSizingBeamStatus(plan.getSizingPlanId(), plan.getBeamDetails());
        try {
            beamInwardRepository.delete(plan);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Beam Inward because it is referenced by other");
        }
    }
    
    @Transactional
    public void deleteBeamInwardByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        BeamInward plan = beamInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Beam Inward not found with id: " + id + " for company: " + companyId));
        ensureBeamNotMappedToFactoryBeamInward(plan);
        cleanUpLinkedBeamOutwards(plan.getId());
        revertSizingBeamStatus(plan.getSizingPlanId(), plan.getBeamDetails());
        try {
            beamInwardRepository.delete(plan);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Beam Inward because it is referenced by other");
        }
    }

    private void cleanUpLinkedBeamOutwards(Long beamInwardId) {
        if (beamInwardId == null) return;
        List<BeamOutward> linkedOutwards = beamOutwardRepository.findByBeamInwardId(beamInwardId);
        if (linkedOutwards != null && !linkedOutwards.isEmpty()) {
            beamOutwardRepository.deleteAll(linkedOutwards);
        }
    }


    private BeamInwardDTO updateBeamInwardEntity(BeamInward existingPlan, BeamInwardDTO dto) {
        revertSizingBeamStatus(existingPlan.getSizingPlanId(), existingPlan.getBeamDetails());
        mapDtoToEntity(dto, existingPlan);

        if (existingPlan.getBeamDetails() != null) {
            existingPlan.getBeamDetails().clear();
        }
        if (existingPlan.getQualityDetails() != null) {
            existingPlan.getQualityDetails().clear();
        }
        if (existingPlan.getInvoiceDetails() != null) {
            existingPlan.getInvoiceDetails().clear();
        }
        beamInwardRepository.saveAndFlush(existingPlan);

        beamDetailsRepository.deleteByBeamInwardId(existingPlan.getId());
        qualityDetailsRepository.deleteByBeamInwardId(existingPlan.getId());
        invoiceDetailsRepository.deleteByBeamInwardId(existingPlan.getId());
        beamDetailsRepository.flush();
        qualityDetailsRepository.flush();
        invoiceDetailsRepository.flush();

        final Long inwardTypeId;
        if (existingPlan.getOrderTypeId() != null) {
            String targetCode = existingPlan.getOrderTypeId().equals(1L) ? "251" : 
                                existingPlan.getOrderTypeId().equals(2L) ? "252" : null;
            if (targetCode != null) {
                Optional<CodesDetail> cd = codesDetailRepository.findByCode(targetCode);
                if (cd.isEmpty()) {
                    cd = codesDetailRepository.findByValue(existingPlan.getOrderTypeId().equals(1L) ? "Inhouse Beam" : "Trading Beam");
                }
                if (cd.isEmpty()) {
                    cd = codesDetailRepository.findByValue(existingPlan.getOrderTypeId().equals(1L) ? "INHOUSE BEAM" : "TRADING BEAM");
                }
                inwardTypeId = cd.map(CodesDetail::getId).orElse(null);
            } else {
                inwardTypeId = null;
            }
        } else {
            inwardTypeId = null;
        }

        if (dto.getBeamInwardBeamDetails() != null && !dto.getBeamInwardBeamDetails().isEmpty()) {
            List<BeamInwardBeamDetails> beamEntities = dto.getBeamInwardBeamDetails().stream().map(b -> {
                BeamInwardBeamDetails beam = new BeamInwardBeamDetails();
                beam.setBeamInward(existingPlan);
                beam.setWeavingContractId(b.getWeavingContractId());
                beam.setSalesOrderId(b.getSalesOrderId());
                beam.setSizedBeamNo(b.getSizedBeamNo());
                beam.setFlangeNo(b.getFlangeNo());
                beam.setFlangeId(b.getFlangeId());
                beam.setWrapMeters(b.getWrapMeters());
                beam.setShrinkage(b.getShrinkage());
                beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
                beam.setTotalEnds(b.getTotalEnds());
                beam.setFabricPanelWidth(b.getFabricPanelWidth());
                beam.setWeavingVendorId(b.getWeavingVendorId());
                beam.setFabricId(b.getFabricId());
                beam.setSizingPlanId(dto.getSizingPlanId());
                beam.setIsBeamOutwarded(b.getIsBeamOutwarded() != null ? b.getIsBeamOutwarded() : 0);
                beam.setIsReturn(b.getIsReturn());
                beam.setReturnDate(b.getReturnDate());
                beam.setReturnNo(b.getReturnNo());
                beam.setReturnWeaverId(b.getReturnWeaverId());
                beam.setInwardTypeId(inwardTypeId);
                beam.setWarpCount(b.getWarpCount());
                beam.setWarpYarnMillName(b.getWarpYarnMillName());
                beam.setWarpYarnLotNo(b.getWarpYarnLotNo());
                beam.setDcNumber(b.getDcNumber());
                beam.setDcDate(b.getDcDate());
                beam.setSizingSetNo(b.getSizingSetNo());
                beam.setSizingPickupPercent(b.getSizingPickupPercent());

                updateSizingBeamStatus(dto.getSizingPlanId(), b);
                return beam;
            }).collect(Collectors.toList());
            if (!beamEntities.isEmpty()) {
                beamDetailsRepository.saveAll(beamEntities);
              
            }
        }

        if (dto.getBeamInwardQualityDetails() != null && !dto.getBeamInwardQualityDetails().isEmpty()) {
            List<BeamInwardQualityDetails> qualityEntities = dto.getBeamInwardQualityDetails().stream().map(q -> {
                BeamInwardQualityDetails quality = new BeamInwardQualityDetails();
                quality.setBeamInward(existingPlan);
                quality.setQuality(q.getQuality());
                quality.setYarnId(q.getYarnId());
                quality.setSordEnds(q.getSordEnds());
                quality.setActualEnds(q.getActualEnds());
                quality.setParts(q.getParts());
                quality.setEndsPerPart(q.getEndsPerPart());
                quality.setWrapMeters(q.getWrapMeters());
                quality.setFabricPanelWidth(q.getFabricPanelWidth());
                quality.setFabricCode(q.getFabricCode());
                quality.setActualYarn(q.getActualYarn());
                quality.setWarpEnds(q.getWarpEnds());
                quality.setActualWarpEnds(q.getActualWarpEnds());
                quality.setTotalEnds(q.getTotalEnds());
                return quality;
            }).collect(Collectors.toList());
            if (!qualityEntities.isEmpty()) {
                qualityDetailsRepository.saveAll(qualityEntities);
            }
        }

        if (dto.getInvoiceDetails() != null && !dto.getInvoiceDetails().isEmpty()) {
            List<BeamInwardInvoiceDetails> invoiceEntities = dto.getInvoiceDetails().stream().map(i -> {
                BeamInwardInvoiceDetails inv = new BeamInwardInvoiceDetails();
                inv.setBeamInward(existingPlan);
                inv.setInvNo(i.getInvNo());
                inv.setInvDate(i.getInvDate());
                inv.setSizingCost(i.getSizingCost());
                inv.setGstAmount(i.getGstAmount());
                inv.setNetTotal(i.getNetTotal());
                inv.setMediaDetailSno(i.getMediaDetailSno());
                inv.setMediaSno(i.getMediaSno());
                inv.setInvoiceUpload(i.getInvoiceUpload());
                return inv;
            }).collect(Collectors.toList());
            invoiceDetailsRepository.saveAll(invoiceEntities);
        }

        syncMissingFactoryBeamInwardOnUpdate(existingPlan, dto);

        return mapEntityToDto(existingPlan);
    }

    private void syncMissingFactoryBeamInwardOnUpdate(BeamInward existingPlan, BeamInwardDTO dto) {
        if (existingPlan.getOrderTypeId() != null && existingPlan.getOrderTypeId().equals(1L)) {
            return;
        }
        List<BeamOutward> linkedOutwards = beamOutwardRepository.findByBeamInwardId(existingPlan.getId());
        if (linkedOutwards == null || linkedOutwards.isEmpty()) {
            return;
        }
        for (BeamOutward outward : linkedOutwards) {
            if (outward.getOutwardNo() == null || outward.getOutwardNo().isEmpty()) {
                outward.setOutwardNo(generateBeamOutwardNo(outward.getCompanyId()));
                beamOutwardRepository.save(outward);
            }
            if (!Long.valueOf(1L).equals(outward.getIsFactoryInward())) {
                continue;
            }
            beamOutwardService.syncToFactoryBeamInward(outward.getId());
        }
    }

    private PaginatedResponse<BeamInwardDTO> buildPaginatedResponse(Page<BeamInward> page, Long offset, Long limit) {
        List<BeamInwardDTO> content = page.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(
                page.getTotalElements(),
                page.hasNext(),
                page.getNumber() > 0,
                offset,
                limit,
                content
        );
    }

    // Helpers
    private void ensureBeamNotMappedToFactoryBeamInward(BeamInward beamInward) {
        if (beamInward == null) {
            return;
        }
        boolean mappedById = beamInward.getId() != null && factoryBeamInwardRepository.existsByBeamInwardId(beamInward.getId());
        if (mappedById) {
            String beamInwardNo = beamInward.getBeamInwardNo();
            String identifier = (beamInwardNo != null && !beamInwardNo.trim().isEmpty())
                    ? beamInwardNo
                    : String.valueOf(beamInward.getId());
            throw new IllegalStateException(identifier + " already mapped in Factory Beam Inward");
        }
    }

    private void requireSizingPlanReference(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return;
        }
        Optional<SizingPlan> sizingPlan = sizingPlanRepository.findById(sizingPlanId);
        String displayIdentifier = sizingPlan
                .map(SizingPlan::getSizingPlanNo)
                .filter(no -> no != null && !no.isBlank())
                .orElse("Sizing Plan ID " + sizingPlanId);
        referenceValidationService.requireReference(
                sizingPlanId,
                () -> sizingPlan,
                "Sizing Plan",
                displayIdentifier);
    }

    private BeamInward mapDtoToEntity(BeamInwardDTO dto, BeamInward plan) {
        plan.setSizingVendorId(dto.getSizingVendorId());
        plan.setTermsConditionsId(dto.getTermsConditionsId());
        plan.setConsigneeId(dto.getConsigneeId());
        plan.setPaymentTermsId(dto.getPaymentTermsId());
        plan.setSizingRate(dto.getSizingRate());
        plan.setOrderTypeId(dto.getOrderTypeId());
        plan.setRemarks(dto.getRemarks());
        plan.setBeamInwardNo(dto.getBeamInwardNo());
        plan.setDcNumber(dto.getDcNumber());
        plan.setDcDate(dto.getDcDate());
        plan.setBeamInwardDate(dto.getBeamInwardDate());
        plan.setCompanyId(dto.getCompanyId());
        plan.setTotalBeams(dto.getTotalBeams());
        plan.setSizingSetNo(dto.getSizingSetNo());
        plan.setAvgBeamCount(dto.getAvgBeamCount());
        plan.setSizingPickup(dto.getSizingPickup());
        plan.setElongation(dto.getElongation());
        plan.setBreaksMillion(dto.getBreaksMillion());
        plan.setBabyConeWt(dto.getBabyConeWt());
        plan.setActualWeight(dto.getActualWeight());
        plan.setWarpingWeight(dto.getWarpingWeight());
        plan.setExcessShortage(dto.getExcessShortage());
        plan.setFreshCone(dto.getFreshCone());
        plan.setRewindingCone(dto.getRewindingCone());

        plan.setSizingPlanId(dto.getSizingPlanId());
//        plan.setSizingBeamOutwardId(dto.getSizingBeamOutwardId());
        return plan;
    }

    private BeamInwardDTO mapEntityToDto(BeamInward plan) {
    	BeamInwardDTO dto = new BeamInwardDTO();
        dto.setId(plan.getId());
        dto.setSizingVendorId(plan.getSizingVendorId());
        dto.setTermsConditionsId(plan.getTermsConditionsId());
        dto.setConsigneeId(plan.getConsigneeId());
        dto.setPaymentTermsId(plan.getPaymentTermsId());
        dto.setSizingRate(plan.getSizingRate());
        dto.setOrderTypeId(plan.getOrderTypeId());
        dto.setRemarks(plan.getRemarks());
        dto.setBeamInwardNo(plan.getBeamInwardNo());
        dto.setDcNumber(plan.getDcNumber());
        dto.setDcDate(plan.getDcDate());
        dto.setBeamInwardDate(plan.getBeamInwardDate());
        dto.setCompanyId(plan.getCompanyId());
        dto.setTotalBeams(plan.getTotalBeams());
        dto.setSizingSetNo(plan.getSizingSetNo());
        dto.setAvgBeamCount(plan.getAvgBeamCount());
        dto.setSizingPickup(plan.getSizingPickup());
        dto.setElongation(plan.getElongation());
        dto.setBreaksMillion(plan.getBreaksMillion());
        dto.setBabyConeWt(plan.getBabyConeWt());
        dto.setActualWeight(plan.getActualWeight());
        dto.setWarpingWeight(plan.getWarpingWeight());
        dto.setExcessShortage(plan.getExcessShortage());
        dto.setFreshCone(plan.getFreshCone());
        dto.setRewindingCone(plan.getRewindingCone());

        dto.setSizingPlanId(plan.getSizingPlanId());


        Long sizingPlanId = plan.getSizingPlanId();

        List<SizingBeamDetails> sizingPlanBeams = sizingPlanId != null
                ? sizingBeamDetailsRepository.findBySizingPlanId(sizingPlanId)
                : Collections.emptyList();
        Map<String, SizingBeamDetails> beamDetailByKey = sizingPlanBeams.isEmpty()
                ? Collections.emptyMap()
                : sizingPlanBeams.stream()
                        .collect(Collectors.toMap(
                                b -> buildBeamMatchKey(b.getWeavingContractId(), b.getSalesOrderId()),
                                Function.identity(),
                                (a, b) -> a));

        List<BeamInwardBeamDetailsDTO> beamDtos = beamDetailsRepository.findByBeamInwardId(plan.getId()).stream().map(b -> {
        	BeamInwardBeamDetailsDTO bdto = new BeamInwardBeamDetailsDTO();
            bdto.setId(b.getId());
            bdto.setWeavingContractId(b.getWeavingContractId());
            bdto.setSalesOrderId(b.getSalesOrderId());
//            bdto.setEmptyBeamId(b.getEmptyBeamId());
            bdto.setWrapMeters(b.getWrapMeters());
            bdto.setShrinkage(b.getShrinkage());
            bdto.setExpectedFabricMeter(b.getExpectedFabricMeter());
            bdto.setTotalEnds(b.getTotalEnds());
            bdto.setSizedBeamNo(b.getSizedBeamNo());
            bdto.setFlangeNo(b.getFlangeNo());
            bdto.setFlangeId(b.getFlangeId());
            bdto.setWeavingVendorId(b.getWeavingVendorId());
            bdto.setFabricId(b.getFabricId());
            bdto.setFabricPanelWidth(b.getFabricPanelWidth());
            bdto.setIsBeamOutwarded(b.getIsBeamOutwarded());
            bdto.setIsBeamGated(b.getIsBeamGated());
            bdto.setSizingPlanId(b.getSizingPlanId());
            bdto.setIsReturn(b.getIsReturn());
            bdto.setReturnDate(b.getReturnDate());
            bdto.setReturnNo(b.getReturnNo());
            bdto.setReturnWeaverId(b.getReturnWeaverId());
            bdto.setInwardTypeId(b.getInwardTypeId());
            bdto.setWarpCount(b.getWarpCount());
            bdto.setWarpYarnMillName(b.getWarpYarnMillName());
            bdto.setWarpYarnLotNo(b.getWarpYarnLotNo());
            bdto.setDcNumber(b.getDcNumber());
            bdto.setDcDate(b.getDcDate());
            bdto.setSizingSetNo(b.getSizingSetNo());
            bdto.setSizingPickupPercent(b.getSizingPickupPercent());
            if (b.getIsReturn() != null && b.getIsReturn() == 1
                    && b.getSizedBeamNo() != null && b.getReturnNo() != null) {
                List<SalesReturnBeamEntity> returnBeams = salesReturnBeamRepository
                        .findBySizedBeamNoAndReturnNo(b.getSizedBeamNo(), b.getReturnNo());
                if (!returnBeams.isEmpty() && returnBeams.get(0).getBeamMeters() != null) {
                    bdto.setWrapMeters(returnBeams.get(0).getBeamMeters().doubleValue());
                }
            }
            return bdto;
        }).collect(Collectors.toList());

        if (!beamDetailByKey.isEmpty()) {
            for (BeamInwardBeamDetailsDTO bdto : beamDtos) {
                SizingBeamDetails match = beamDetailByKey.get(buildBeamMatchKey(bdto.getWeavingContractId(), bdto.getSalesOrderId()));
                if (match != null) {
                    if (bdto.getWrapMeters() == null) {
                        bdto.setWrapMeters(match.getWarpMeters());
                    }
                    if (bdto.getShrinkage() == null) {
                        bdto.setShrinkage(match.getShrinkage());
                    }
                    if (bdto.getExpectedFabricMeter() == null) {
                        bdto.setExpectedFabricMeter(match.getExpectedFabricMeter());
                    }
                    if (bdto.getWeavingVendorId() == null) {
                        bdto.setWeavingVendorId(match.getWeavingVendorId());
                    }
                    if (bdto.getFabricId() == null) {
                        bdto.setFabricId(match.getFabricId());
                    }
                }
            }
        }

        // Map base quality details
        List<BeamInwardQualityDetailsDTO> qualityDtos = qualityDetailsRepository.findByBeamInwardId(plan.getId()).stream().map(q -> {
        	BeamInwardQualityDetailsDTO qdto = new BeamInwardQualityDetailsDTO();
            qdto.setId(q.getId());
            qdto.setQuality(q.getQuality());
            qdto.setYarnId(q.getYarnId());
            qdto.setSordEnds(q.getSordEnds());
            qdto.setActualEnds(q.getActualEnds());
            qdto.setParts(q.getParts());
            qdto.setEndsPerPart(q.getEndsPerPart());
            qdto.setWrapMeters(q.getWrapMeters());
            qdto.setFabricPanelWidth(q.getFabricPanelWidth());
            // Map newly persisted fields from entity
            qdto.setFabricCode(q.getFabricCode());
            qdto.setActualYarn(q.getActualYarn());
            qdto.setWarpEnds(q.getWarpEnds());
            qdto.setActualWarpEnds(q.getActualWarpEnds());
            qdto.setTotalEnds(q.getTotalEnds());
            return qdto;
        }).collect(Collectors.toList());

        List<SizingQualityDetails> sizingPlanQualities = sizingPlanId != null
                ? sizingQualityDetailsRepository.findBySizingPlanId(sizingPlanId)
                : Collections.emptyList();

        if (!sizingPlanQualities.isEmpty()) {
            if (qualityDtos.isEmpty()) {
                qualityDtos = sizingPlanQualities.stream()
                        .map(this::mapSizingQualityToDto)
                        .collect(Collectors.toList());
            } else {
                Map<String, SizingQualityDetails> byQuality = sizingPlanQualities.stream()
                        .filter(s -> s.getQuality() != null)
                        .collect(Collectors.toMap(SizingQualityDetails::getQuality, Function.identity(), (a, b) -> a));

                for (BeamInwardQualityDetailsDTO qdto : qualityDtos) {
                    if (qdto.getQuality() == null) {
                        continue;
                    }
                    SizingQualityDetails match = byQuality.get(qdto.getQuality());
                    if (match != null) {
                        applySizingQualityDefaults(qdto, match);
                    }
                }
            }
        }

        dto.setBeamInwardBeamDetails(beamDtos);
        dto.setBeamInwardQualityDetails(qualityDtos);

        List<BeamInwardInvoiceDetailsDTO> invoiceDtos = invoiceDetailsRepository.findByBeamInwardId(plan.getId()).stream().map(i -> {
            BeamInwardInvoiceDetailsDTO idto = new BeamInwardInvoiceDetailsDTO();
            idto.setId(i.getId());
            idto.setInvNo(i.getInvNo());
            idto.setInvDate(i.getInvDate());
            idto.setSizingCost(i.getSizingCost());
            idto.setGstAmount(i.getGstAmount());
            idto.setNetTotal(i.getNetTotal());
            idto.setMediaDetailSno(i.getMediaDetailSno());
            idto.setMediaSno(i.getMediaSno());
            idto.setInvoiceUpload(i.getInvoiceUpload());
            return idto;
        }).collect(Collectors.toList());
        dto.setInvoiceDetails(invoiceDtos);

        return dto;
    }

    private String buildBeamMatchKey(Long weavingContractId, Long salesOrderId) {
        Long contractKey = weavingContractId != null ? weavingContractId : 0L;
        Long orderKey = salesOrderId != null ? salesOrderId : 0L;
        return contractKey + ":" + orderKey;
    }

    private BeamInwardQualityDetailsDTO mapSizingQualityToDto(SizingQualityDetails source) {
        BeamInwardQualityDetailsDTO dto = new BeamInwardQualityDetailsDTO();
        dto.setQuality(source.getQuality());
        dto.setYarnId(source.getYarnId());
        dto.setSordEnds(source.getSordEnds());
        dto.setActualEnds(source.getActualEnds());
        dto.setParts(source.getParts());
        dto.setEndsPerPart(source.getEndsPerPart());
        dto.setWrapMeters(source.getWarpMeters());
        dto.setFabricPanelWidth(source.getFabricPanelWidth());
        dto.setFabricCode(source.getFabricCode());
        dto.setActualYarn(source.getActualYarn());
        dto.setWarpEnds(source.getWarpEnds());
        dto.setActualWarpEnds(source.getActualWarpEnds());
        return dto;
    }

    private void applySizingQualityDefaults(BeamInwardQualityDetailsDTO target, SizingQualityDetails source) {
        if (target.getFabricCode() == null) {
            target.setFabricCode(source.getFabricCode());
        }
        if (target.getActualYarn() == null) {
            target.setActualYarn(source.getActualYarn());
        }
        if (target.getWarpEnds() == null) {
            target.setWarpEnds(source.getWarpEnds());
        }
        if (target.getActualWarpEnds() == null) {
            target.setActualWarpEnds(source.getActualWarpEnds());
        }
        if (target.getFabricPanelWidth() == null) {
            target.setFabricPanelWidth(source.getFabricPanelWidth());
        }
        if (target.getSordEnds() == null) {
            target.setSordEnds(source.getSordEnds());
        }
        if (target.getActualEnds() == null) {
            target.setActualEnds(source.getActualEnds());
        }
        if (target.getParts() == null) {
            target.setParts(source.getParts());
        }
        if (target.getEndsPerPart() == null) {
            target.setEndsPerPart(source.getEndsPerPart());
        }
        if (target.getWrapMeters() == null) {
            target.setWrapMeters(source.getWarpMeters());
        }
    }

    private void syncToRelatedBeams(BeamInwardDTO inwardDTO) {
        if (inwardDTO == null || inwardDTO.getId() == null) {
            return;
        }

        // 1. Sync to BeamOutward
        List<BeamOutwardDTO> linkedOutwards = beamOutwardService.getByBeamInwardId(inwardDTO.getId());
        for (BeamOutwardDTO outward : linkedOutwards) {
            outward.setBeamInwardNo(inwardDTO.getBeamInwardNo());
            
            // Match beam details by sizedBeamNo and flangeNo
            if (outward.getSizedBeamNo() != null && inwardDTO.getBeamInwardBeamDetails() != null) {
                inwardDTO.getBeamInwardBeamDetails().stream()
                    .filter(d -> outward.getSizedBeamNo().equals(d.getSizedBeamNo()) && 
                                 (outward.getFlangeNo() == null || outward.getFlangeNo().equals(d.getFlangeNo())))
                    .findFirst()
                    .ifPresent(detail -> {
                        outward.setWrapMeters(detail.getWrapMeters());
                        outward.setShrinkage(detail.getShrinkage());
                        outward.setExpectedFabricMeter(detail.getExpectedFabricMeter());
                    });
            }

            if (outward.getOutwardDetails() != null && inwardDTO.getBeamInwardBeamDetails() != null) {
                for (com.tech3.erp.dto.BeamOutwardDetailDTO od : outward.getOutwardDetails()) {
                    inwardDTO.getBeamInwardBeamDetails().stream()
                        .filter(d -> od.getSizedBeamNo() != null && od.getSizedBeamNo().equals(d.getSizedBeamNo()) && 
                                     (od.getFlangeNo() == null || od.getFlangeNo().equals(d.getFlangeNo())))
                        .findFirst()
                        .ifPresent(detail -> {
                            od.setWrapMeters(detail.getWrapMeters());
                            od.setShrinkage(detail.getShrinkage());
                            od.setExpectedFabricMeter(detail.getExpectedFabricMeter());
                        });
                }
            }
            beamOutwardService.update(outward.getId(), outward);
        }

        // 2. Sync to SizingProduction
        if (inwardDTO.getSizingPlanId() != null) {
            List<com.tech3.erp.entity.SizingProduction> productions = sizingProductionRepository.findBySizingPlanId(inwardDTO.getSizingPlanId());
            for (com.tech3.erp.entity.SizingProduction prod : productions) {
                com.tech3.erp.dto.SizingProductionDTO prodDTO = sizingProductionService.getById(prod.getId());
                boolean prodChanged = false;
                if (prodDTO.getProductionDetails() != null && inwardDTO.getBeamInwardBeamDetails() != null) {
                    for (com.tech3.erp.dto.SizingProductionDetailDTO pd : prodDTO.getProductionDetails()) {
                        for (BeamInwardBeamDetailsDTO bd : inwardDTO.getBeamInwardBeamDetails()) {
                            if (bd.getSizedBeamNo() != null && bd.getSizedBeamNo().equals(pd.getSizedBeamNo()) && 
                                ((bd.getFlangeNo() == null && pd.getFlangeNo() == null) || 
                                 (bd.getFlangeNo() != null && bd.getFlangeNo().equals(pd.getFlangeNo())))) {
                                pd.setBeamMeters(bd.getWrapMeters());
                                pd.setExpectedFabricMeter(bd.getExpectedFabricMeter());
                                pd.setFabricPanelWidth(bd.getFabricPanelWidth());
                                pd.setTotalEnds(bd.getTotalEnds());
                                prodChanged = true;
                            }
                        }
                    }
                }
                if (prodChanged) {
                    sizingProductionService.update(prodDTO.getId(), prodDTO);
                }
            }
        }

        // 3. Sync actualYarn to SizingQualityDetails
        if (inwardDTO.getSizingPlanId() != null && inwardDTO.getBeamInwardQualityDetails() != null) {
            List<SizingQualityDetails> sizingQualities = sizingQualityDetailsRepository.findBySizingPlanId(inwardDTO.getSizingPlanId());
            boolean sqdChanged = false;
            for (SizingQualityDetails sqd : sizingQualities) {
                for (BeamInwardQualityDetailsDTO bqd : inwardDTO.getBeamInwardQualityDetails()) {
                    if (bqd.getQuality() != null && bqd.getQuality().equals(sqd.getQuality())) {
                        if (bqd.getActualYarn() != null && !bqd.getActualYarn().equals(sqd.getActualYarn())) {
                            sqd.setActualYarn(bqd.getActualYarn());
                            sqdChanged = true;
                        }
                    }
                }
            }
            if (sqdChanged) {
                sizingQualityDetailsRepository.saveAll(sizingQualities);
            }
        }
    }


    @Transactional(readOnly = true)
    public List<BeamInwardDTO> getBeamInwardsByBeamNo(String beamNo) {
        if (beamNo == null || beamNo.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }
        String trimmed = beamNo.trim();
        List<BeamInwardBeamDetails> details = beamDetailsRepository.findBySizedBeamNoAndIsReturn(trimmed);
        if (details.isEmpty() && trimmed.contains("/")) {
            String base = trimmed.split("/")[0].trim();
            details = beamDetailsRepository.findBySizedBeamNoAndIsReturn(base);
        }
        java.util.Set<Long> inwardIds = details.stream()
                .map(d -> d.getBeamInward() != null ? d.getBeamInward().getId() : null)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());

        return inwardIds.stream()
                .map(this::getBeamInwardById)
                .collect(Collectors.toList());
    }
}