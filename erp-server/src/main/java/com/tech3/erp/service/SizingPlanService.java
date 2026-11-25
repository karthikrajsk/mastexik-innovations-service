// package com.tech3.erp.service;

// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.dto.SizingPlanDTO;
// import com.tech3.erp.dto.SizingBeamDetailsDTO;
// import com.tech3.erp.dto.SizingQualityDetailsDTO;
// import com.tech3.erp.entity.SizingPlan;
// import com.tech3.erp.entity.SizingBeamDetails;
// import com.tech3.erp.entity.SizingQualityDetails;
// import com.tech3.erp.entity.Vendor;
// import com.tech3.erp.repository.SizingPlanRepository;
// import com.tech3.erp.repository.SizingBeamDetailsRepository;
// import com.tech3.erp.repository.SizingQualityDetailsRepository;
// import com.tech3.erp.repository.BeamInwardRepository;
// import com.tech3.erp.repository.BeamOutwardRepository;
// import com.tech3.erp.repository.FactoryBeamInwardRepository;
// import com.tech3.erp.repository.SalesReturnBeamRepository;
// import com.tech3.erp.repository.SizingBeamOutwardRepository;
// import com.tech3.erp.repository.SizingJobWorkOrderRepository;
// import com.tech3.erp.repository.SizingProductionRepository;
// import com.tech3.erp.repository.SizingYarnIssueEntryRepository;
// import com.tech3.erp.repository.SizingYarnOutwardRepository;
// import com.tech3.erp.repository.VendorRepository;
// import com.tech3.erp.repository.SalesOrderRepository;
// import com.tech3.erp.repository.WeavingContractRepository;
// import jakarta.persistence.EntityNotFoundException;

// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDate;
// import java.util.List;
// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.stream.Collectors;

// @Service
// public class SizingPlanService {

//     private final SizingPlanRepository sizingPlanRepository;
//     private final SizingBeamDetailsRepository beamDetailsRepository;
//     private final SizingQualityDetailsRepository qualityDetailsRepository;
//     private final BeamInwardRepository beamInwardRepository;
//     private final BeamOutwardRepository beamOutwardRepository;
//     private final FactoryBeamInwardRepository factoryBeamInwardRepository;
//     private final SalesReturnBeamRepository salesReturnBeamRepository;
//     private final SizingBeamOutwardRepository sizingBeamOutwardRepository;
//     private final SizingProductionRepository sizingProductionRepository;
//     private final SizingYarnOutwardRepository sizingYarnOutwardRepository;
//     private final SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository;
//     private final SizingJobWorkOrderService sizingJobWorkOrderService;
//     private final VendorRepository vendorRepository;
//     private final SalesOrderRepository salesOrderRepository;
//     private final WeavingContractRepository weavingContractRepository;
//     private final ReferenceValidationService referenceValidationService;
//     private final SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    

//     public SizingPlanService(
//         SizingPlanRepository sizingPlanRepository,
//         SizingBeamDetailsRepository beamDetailsRepository,
//         SizingQualityDetailsRepository qualityDetailsRepository,
//         BeamInwardRepository beamInwardRepository,
//         BeamOutwardRepository beamOutwardRepository,
//         FactoryBeamInwardRepository factoryBeamInwardRepository,
//         SalesReturnBeamRepository salesReturnBeamRepository,
//         SizingBeamOutwardRepository sizingBeamOutwardRepository,
//         SizingProductionRepository sizingProductionRepository,
//         SizingYarnOutwardRepository sizingYarnOutwardRepository,
//         SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository,
//         SizingJobWorkOrderService sizingJobWorkOrderService,
//         VendorRepository vendorRepository,
//         SalesOrderRepository salesOrderRepository,
//         WeavingContractRepository weavingContractRepository,
//         ReferenceValidationService referenceValidationService,
//         SizingJobWorkOrderRepository sizingJobWorkOrderRepository
//     ) {
//         this.sizingPlanRepository = sizingPlanRepository;
//         this.beamDetailsRepository = beamDetailsRepository;
//         this.qualityDetailsRepository = qualityDetailsRepository;
//         this.beamInwardRepository = beamInwardRepository;
//         this.beamOutwardRepository = beamOutwardRepository;
//         this.factoryBeamInwardRepository = factoryBeamInwardRepository;
//         this.salesReturnBeamRepository = salesReturnBeamRepository;
//         this.sizingBeamOutwardRepository = sizingBeamOutwardRepository;
//         this.sizingProductionRepository = sizingProductionRepository;
//         this.sizingYarnOutwardRepository = sizingYarnOutwardRepository;
//         this.sizingYarnIssueEntryRepository = sizingYarnIssueEntryRepository;
//         this.sizingJobWorkOrderService = sizingJobWorkOrderService;
//         this.vendorRepository = vendorRepository;
//         this.salesOrderRepository = salesOrderRepository;
//         this.weavingContractRepository = weavingContractRepository;
//         this.referenceValidationService = referenceValidationService;
//         this.sizingJobWorkOrderRepository = sizingJobWorkOrderRepository;
//     }

//     @Transactional
//     public SizingPlanDTO saveSizingPlan(SizingPlanDTO dto) {
//         SizingPlan plan = mapDtoToEntity(dto, new SizingPlan());
//         final SizingPlan savedPlan = sizingPlanRepository.save(plan);

//         List<SizingBeamDetails> beamEntities = dto.getSizingBeamDetails().stream().map(b -> {
//             SizingBeamDetails beam = new SizingBeamDetails();
//             beam.setSizingPlan(savedPlan);

//             Long weavingContractId = normalizeId(b.getWeavingContractId());
//             if (weavingContractId != null) {
//                 referenceValidationService.requireReference(
//                     weavingContractId,
//                     () -> weavingContractRepository.findById(weavingContractId),
//                     "Weaving Contract",
//                     null);
//             }
//             beam.setWeavingContractId(weavingContractId);

//             Long salesOrderId = normalizeId(b.getSalesOrderId());
//             if (salesOrderId != null) {
//                 referenceValidationService.requireReference(
//                     salesOrderId,
//                     () -> salesOrderRepository.findById(salesOrderId),
//                     "Sales Order",
//                     null);
//             }
//             beam.setSalesOrderId(salesOrderId);

//             beam.setWarpMeters(b.getWarpMeters());
//             beam.setShrinkage(b.getShrinkage());
//             beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
//             beam.setWeavingVendorId(b.getWeavingVendorId());
//             beam.setFabricId(b.getFabricId());
//             beam.setNoOfBeams(b.getNoOfBeams());
//             beam.setStatus(0); // Set default status to 0
//             return beam;
//         }).collect(Collectors.toList());
//         beamDetailsRepository.saveAll(beamEntities);

//         List<SizingQualityDetails> qualityEntities = dto.getSizingQualityDetails().stream().map(q -> {
//             SizingQualityDetails quality = new SizingQualityDetails();
//             quality.setSizingPlan(savedPlan);
//             quality.setQuality(q.getQuality());
//             quality.setFabricCode(q.getFabricCode());
//             quality.setFabricId(q.getFabricId());
//             quality.setYarnId(q.getYarnId());
//             quality.setActualYarn(q.getActualYarn());
//             quality.setWarpEnds(q.getWarpEnds());
//             quality.setActualWarpEnds(q.getActualWarpEnds());
//             quality.setSordEnds(q.getSordEnds());
//             quality.setActualEnds(q.getActualEnds());
//             quality.setParts(q.getParts());
//             quality.setEndsPerPart(q.getEndsPerPart());
//             quality.setWarpMeters(q.getWarpMeters());
//             quality.setFabricPanelWidth(q.getFabricPanelWidth());
//             return quality;
//         }).collect(Collectors.toList());
//         qualityDetailsRepository.saveAll(qualityEntities);
//         syncSizingJobWorkOrder(savedPlan);

//         return getSizingPlanById(savedPlan.getId());
//     }


//     public List<SizingPlanDTO> getAllSizingPlans() {
//         List<SizingPlan> plans = sizingPlanRepository.findAll();
//         return plans.stream()
//                 .sorted(Comparator.comparing(SizingPlan::getId).reversed())
//                 .map(this::mapEntityToDto)
//                 .collect(Collectors.toList());
//     }
    
//     public PaginatedResponse<SizingPlanDTO> getAllSizingPlansWithPagination(Long offset, Long limit) {
//         long finalLimit = (limit != null && limit > 0) ? limit : 10L;
//         long finalOffset = (offset != null && offset > 0) ? offset : 0L;
//         Page<SizingPlan> page = sizingPlanRepository.findAll(
//                 PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"))
//         );
//         return buildPaginatedResponse(page, finalOffset, finalLimit);
//     }
    
//     public List<SizingPlanDTO> getAllSizingPlansByCompany(Long companyId) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         return sizingPlanRepository.findByCompanyIdOrderByIdDesc(companyId).stream()
//                 .map(this::mapEntityToDto)
//                 .collect(Collectors.toList());
//     }
    
//     public PaginatedResponse<SizingPlanDTO> getAllSizingPlansByCompanyWithPagination(Long companyId, Long offset, Long limit) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         long finalLimit = (limit != null && limit > 0) ? limit : 10L;
//         long finalOffset = (offset != null && offset > 0) ? offset : 0L;
//         Page<SizingPlan> page = sizingPlanRepository.findByCompanyId(
//                 companyId,
//                 PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"))
//         );
//         return buildPaginatedResponse(page, finalOffset, finalLimit);
//     }

//     public SizingPlanDTO getSizingPlanById(Long id) {
//         SizingPlan plan = sizingPlanRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
//         return mapEntityToDto(plan);
//     }
    
//     public SizingPlanDTO getSizingPlanByIdAndCompany(Long companyId, Long id) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         SizingPlan plan = sizingPlanRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
//         return mapEntityToDto(plan);
//     }

//     @Transactional
//     public SizingPlanDTO updateSizingPlan(Long id, SizingPlanDTO dto) {
//         SizingPlan existingPlan = sizingPlanRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));

//         mapDtoToEntity(dto, existingPlan);
//         sizingPlanRepository.save(existingPlan);

//         beamDetailsRepository.deleteBySizingPlanId(id);
//         qualityDetailsRepository.deleteBySizingPlanId(id);

//         List<SizingBeamDetails> beamEntities = dto.getSizingBeamDetails().stream().map(b -> {
//             SizingBeamDetails beam = new SizingBeamDetails();
//             beam.setSizingPlan(existingPlan);

//             Long weavingContractId = normalizeId(b.getWeavingContractId());
//             if (weavingContractId != null) {
//                 referenceValidationService.requireReference(
//                     weavingContractId,
//                     () -> weavingContractRepository.findById(weavingContractId),
//                     "Weaving Contract",
//                     null);
//             }
//             beam.setWeavingContractId(weavingContractId);

//             Long salesOrderId = normalizeId(b.getSalesOrderId());
//             if (salesOrderId != null) {
//                 referenceValidationService.requireReference(
//                     salesOrderId,
//                     () -> salesOrderRepository.findById(salesOrderId),
//                     "Sales Order",
//                     null);
//             }
//             beam.setSalesOrderId(salesOrderId);

//             beam.setWarpMeters(b.getWarpMeters());
//             beam.setShrinkage(b.getShrinkage());
//             beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
//             beam.setWeavingVendorId(b.getWeavingVendorId());
//             beam.setFabricId(b.getFabricId());
//             beam.setStatus(b.getStatus() != null ? b.getStatus() : 0); // Preserve status or default to 0
//             return beam;
//         }).collect(Collectors.toList());
//         beamDetailsRepository.saveAll(beamEntities);

//         List<SizingQualityDetails> qualityEntities = dto.getSizingQualityDetails().stream().map(q -> {
//             SizingQualityDetails quality = new SizingQualityDetails();
//             quality.setSizingPlan(existingPlan);
//             quality.setQuality(q.getQuality());
//             quality.setFabricCode(q.getFabricCode());
//             quality.setFabricId(q.getFabricId());
//             quality.setYarnId(q.getYarnId());
//             quality.setActualYarn(q.getActualYarn());
//             quality.setWarpEnds(q.getWarpEnds());
//             quality.setActualWarpEnds(q.getActualWarpEnds());
//             quality.setSordEnds(q.getSordEnds());
//             quality.setActualEnds(q.getActualEnds());
//             quality.setParts(q.getParts());
//             quality.setEndsPerPart(q.getEndsPerPart());
//             quality.setWarpMeters(q.getWarpMeters());
//             quality.setFabricPanelWidth(q.getFabricPanelWidth());
//             return quality;
//         }).collect(Collectors.toList());
//         qualityDetailsRepository.saveAll(qualityEntities);
//         syncSizingJobWorkOrder(existingPlan);

//         return getSizingPlanById(existingPlan.getId());
//     }
    
//     @Transactional
//     public SizingPlanDTO updateSizingPlanByCompany(Long companyId, Long id, SizingPlanDTO dto) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         SizingPlan existingPlan = sizingPlanRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
//         dto.setCompanyId(existingPlan.getCompanyId());
//         mapDtoToEntity(dto, existingPlan);
//         sizingPlanRepository.save(existingPlan);
//         beamDetailsRepository.deleteBySizingPlanId(id);
//         qualityDetailsRepository.deleteBySizingPlanId(id);
//         List<SizingBeamDetails> beamEntities = dto.getSizingBeamDetails().stream().map(b -> {
//             SizingBeamDetails beam = new SizingBeamDetails();
//             beam.setSizingPlan(existingPlan);

//             Long weavingContractId = normalizeId(b.getWeavingContractId());
//             if (weavingContractId != null) {
//                 referenceValidationService.requireReference(
//                     weavingContractId,
//                     () -> weavingContractRepository.findById(weavingContractId),
//                     "Weaving Contract",
//                     null);
//             }
//             beam.setWeavingContractId(weavingContractId);

//             Long salesOrderId = normalizeId(b.getSalesOrderId());
//             if (salesOrderId != null) {
//                 referenceValidationService.requireReference(
//                     salesOrderId,
//                     () -> salesOrderRepository.findById(salesOrderId),
//                     "Sales Order",
//                     null);
//             }
//             beam.setSalesOrderId(salesOrderId);

//             beam.setWarpMeters(b.getWarpMeters());
//             beam.setShrinkage(b.getShrinkage());
//             beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
//             beam.setWeavingVendorId(b.getWeavingVendorId());
//             beam.setFabricId(b.getFabricId());
//             beam.setStatus(b.getStatus() != null ? b.getStatus() : 0);
//             return beam;
//         }).collect(Collectors.toList());
//         beamDetailsRepository.saveAll(beamEntities);
//         List<SizingQualityDetails> qualityEntities = dto.getSizingQualityDetails().stream().map(q -> {
//             SizingQualityDetails quality = new SizingQualityDetails();
//             quality.setSizingPlan(existingPlan);
//             quality.setQuality(q.getQuality());
//             quality.setFabricCode(q.getFabricCode());
//             quality.setFabricId(q.getFabricId());
//             quality.setYarnId(q.getYarnId());
//             quality.setActualYarn(q.getActualYarn());
//             quality.setWarpEnds(q.getWarpEnds());
//             quality.setActualWarpEnds(q.getActualWarpEnds());
//             quality.setSordEnds(q.getSordEnds());
//             quality.setActualEnds(q.getActualEnds());
//             quality.setParts(q.getParts());
//             quality.setEndsPerPart(q.getEndsPerPart());
//             quality.setWarpMeters(q.getWarpMeters());
//             quality.setFabricPanelWidth(q.getFabricPanelWidth());
//             return quality;
//         }).collect(Collectors.toList());
//         qualityDetailsRepository.saveAll(qualityEntities);
//         syncSizingJobWorkOrder(existingPlan);
//         return getSizingPlanByIdAndCompany(companyId, existingPlan.getId());
//     }

//     public void deleteSizingPlan(Long id) {
//         SizingPlan plan = sizingPlanRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
//         ensureNoSizingPlanReferences(plan);
//         sizingJobWorkOrderService.deleteBySizingPlanId(id);
//         try {
//             sizingPlanRepository.delete(plan);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete SizingPlan because it is referenced by other", e);
//         }
//     }

//     public boolean isMapped(Long id) {
//         return sizingPlanRepository.findById(id).map(plan -> {
//             Long planId = plan.getId();
//             String planNo = plan.getSizingPlanNo();
//             if (beamInwardRepository.existsBySizingPlanId(planId)) return true;
//             if (beamOutwardRepository.existsBySizingPlanId(planId)) return true;
//             if (factoryBeamInwardRepository.existsBySizingPlanId(planId)) return true;
//             if (sizingBeamOutwardRepository.existsBySizingPlanId(planId)) return true;
//             if (sizingProductionRepository.existsBySizingPlanId(planId)) return true;
//             if (sizingYarnIssueEntryRepository.existsBySizingPlanId(planId)) return true;
//             if (sizingYarnOutwardRepository.existsBySizingPlanId(planId)) return true;
//            if (sizingJobWorkOrderRepository.existsBySizingPlanId(planId)) return true;
//             if (planNo != null) {
//                 String trimmedPlanNo = planNo.trim();
//                 if (!trimmedPlanNo.isEmpty()) {
//                     if (salesReturnBeamRepository.existsBySizingPlanNo(trimmedPlanNo)) return true;
//                     if (sizingBeamOutwardRepository.existsBySizingPlanNo(trimmedPlanNo)) return true;
//                     if (sizingProductionRepository.existsBySizingPlanNo(trimmedPlanNo)) return true;
//                 }
//             }
//             return false;
//         }).orElse(false);
//     }
    
//     public void deleteSizingPlanByCompany(Long companyId, Long id) {
//         if (companyId == null) {
//             throw new IllegalArgumentException("Company ID is required");
//         }
//         SizingPlan plan = sizingPlanRepository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
//         ensureNoSizingPlanReferences(plan);
//         sizingJobWorkOrderService.deleteBySizingPlanId(id);
//         try {
//             sizingPlanRepository.delete(plan);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete SizingPlan because it is referenced by other", e);
//         }
//     }

//     private void ensureNoSizingPlanReferences(SizingPlan plan) {
//         if (plan == null || plan.getId() == null) {
//             return;
//         }
//         Long planId = plan.getId();
//         String planNo = plan.getSizingPlanNo();
//         if (beamInwardRepository.existsBySizingPlanId(planId)) {
//             throw new IllegalStateException("Cannot delete SizingPlan because one or more beam inward records reference it");
//         }
//         if (beamOutwardRepository.existsBySizingPlanId(planId)) {
//             throw new IllegalStateException("Cannot delete SizingPlan because one or more beam outward records reference it");
//         }
//         if (factoryBeamInwardRepository.existsBySizingPlanId(planId)) {
//             throw new IllegalStateException("Cannot delete SizingPlan because factory beam inward records reference it");
//         }
//         if (sizingBeamOutwardRepository.existsBySizingPlanId(planId)) {
//             throw new IllegalStateException("Cannot delete SizingPlan because sizing beam outward records reference it");
//         }
//         if (sizingProductionRepository.existsBySizingPlanId(planId)) {
//             String identifier = planNo != null ? planNo : "SizingPlan #" + planId;
//             throw new IllegalStateException("this " + identifier + " is already mapped in sizing production");
//         }
//         if (sizingYarnIssueEntryRepository.existsBySizingPlanId(planId)) {
//             throw new IllegalStateException("Cannot delete SizingPlan because sizing yarn issue entries reference it");
//         }
//         if (sizingYarnOutwardRepository.existsBySizingPlanId(planId)) {
//             throw new IllegalStateException("Cannot delete SizingPlan because yarn outward records reference it");
//         }

//         if (planNo != null) {
//             String trimmedPlanNo = planNo.trim();
//             if (!trimmedPlanNo.isEmpty()) {
//                 if (salesReturnBeamRepository.existsBySizingPlanNo(trimmedPlanNo)) {
//                     throw new IllegalStateException("Cannot delete SizingPlan because sales return beams reference sizing plan number " + trimmedPlanNo);
//                 }
//                 if (sizingBeamOutwardRepository.existsBySizingPlanNo(trimmedPlanNo)) {
//                     throw new IllegalStateException("Cannot delete SizingPlan because sizing beam outward records reference sizing plan number " + trimmedPlanNo);
//                 }
//                 if (sizingProductionRepository.existsBySizingPlanNo(trimmedPlanNo)) {
//                     throw new IllegalStateException("this " + trimmedPlanNo + " is already mapped in sizing production");
//                 }
//             }
//         }
//     }

//     private PaginatedResponse<SizingPlanDTO> buildPaginatedResponse(Page<SizingPlan> page, Long offset, Long limit) {
//         List<SizingPlanDTO> content = page.getContent().stream()
//                 .map(this::mapEntityToDto)
//                 .collect(Collectors.toList());
//         return new PaginatedResponse<>(
//                 page.getTotalElements(),
//                 !page.isLast(),
//                 page.getNumber() > 0,
//                 offset,
//                 limit,
//                 content
//         );
//     }

//     private void syncSizingJobWorkOrder(SizingPlan plan) {
//         if (plan == null || plan.getId() == null) {
//             return;
//         }

//         Integer isInternalVendor = 0;
//         if (plan.getVendorId() != null) {
//             isInternalVendor = vendorRepository.findById(plan.getVendorId())
//                     .map(Vendor::getIsInternalVendor)
//                     .orElse(0);
//         }

//         if (isInternalVendor != null && isInternalVendor == 1) {
//             List<SizingQualityDetails> details = qualityDetailsRepository.findBySizingPlanId(plan.getId());
//             sizingJobWorkOrderService.createOrUpdateFromSizingPlan(plan, details);
//         } else {
//             sizingJobWorkOrderService.deleteBySizingPlanId(plan.getId());
//         }
//     }

//     // Helper method to map SizingBeamDetails entity to DTO
//     private SizingBeamDetailsDTO mapBeamToDto(SizingBeamDetails b) {
//         SizingBeamDetailsDTO bdto = new SizingBeamDetailsDTO();
//         bdto.setId(b.getId());
//         bdto.setWeavingContractId(b.getWeavingContractId());
//         bdto.setSalesOrderId(b.getSalesOrderId());
//         bdto.setWarpMeters(b.getWarpMeters());
//         bdto.setShrinkage(b.getShrinkage());
//         bdto.setExpectedFabricMeter(b.getExpectedFabricMeter());
//         bdto.setWeavingVendorId(b.getWeavingVendorId());
//         bdto.setFabricId(b.getFabricId());
//         bdto.setStatus(b.getStatus()); // Add status field
//         bdto.setIsBeamOutward(b.getIsBeamOutward());
//         return bdto;
//     }

//     // Helpers
//     private SizingPlan mapDtoToEntity(SizingPlanDTO dto, SizingPlan plan) {
//         plan.setVendorId(dto.getVendorId());
//         plan.setTermsConditionsId(dto.getTermsConditionsId());
//         plan.setPaymentTermsId(dto.getPaymentTermsId());
//         plan.setSizingRate(dto.getSizingRate());
//         plan.setOrderTypeId(dto.getOrderTypeId());
//         plan.setEmptyBeamNo(dto.getEmptyBeamNo());
//         plan.setRemarks(dto.getRemarks());
//         plan.setSizingPlanNo(dto.getSizingPlanNo());
//         plan.setCompanyId(dto.getCompanyId());
//         plan.setTradingCompanyId(dto.getTradingCompanyId());
//         plan.setFactoryCompanyId(dto.getFactoryCompanyId());
//         if (dto.getSizingPlanDate() != null) {
//             plan.setSizingPlanDate(LocalDate.parse(dto.getSizingPlanDate()));
//         }
//         if (dto.getIsComplete() != null) {
//             plan.setIsComplete(dto.getIsComplete());
//         }
//         return plan;
//     }

//     private SizingPlanDTO mapEntityToDto(SizingPlan plan) {
//         SizingPlanDTO dto = new SizingPlanDTO();
//         dto.setId(plan.getId());
//         dto.setVendorId(plan.getVendorId());
//         dto.setTermsConditionsId(plan.getTermsConditionsId());
//         dto.setPaymentTermsId(plan.getPaymentTermsId());
//         dto.setSizingRate(plan.getSizingRate());
//         dto.setOrderTypeId(plan.getOrderTypeId());
//         dto.setEmptyBeamNo(plan.getEmptyBeamNo());
//         dto.setRemarks(plan.getRemarks());
//         dto.setSizingPlanNo(plan.getSizingPlanNo());
//         dto.setCompanyId(plan.getCompanyId());
//         dto.setTradingCompanyId(plan.getTradingCompanyId());
//         dto.setFactoryCompanyId(plan.getFactoryCompanyId());
//         if (plan.getSizingPlanDate() != null) {
//             dto.setSizingPlanDate(plan.getSizingPlanDate().toString());
//         }
//         dto.setIsComplete(plan.getIsComplete());

//         List<SizingBeamDetailsDTO> beamDtos = beamDetailsRepository.findBySizingPlanId(plan.getId())
//                 .stream()
//                 .map(this::mapBeamToDto)
//                 .collect(Collectors.toList());

//         List<SizingQualityDetailsDTO> qualityDtos = qualityDetailsRepository.findBySizingPlanId(plan.getId()).stream().map(q -> {
//             SizingQualityDetailsDTO qdto = new SizingQualityDetailsDTO();
//             qdto.setId(q.getId());
//             qdto.setQuality(q.getQuality());
//             qdto.setFabricCode(q.getFabricCode());
//             qdto.setFabricId(q.getFabricId());
//             qdto.setYarnId(q.getYarnId());
//             qdto.setActualYarn(q.getActualYarn());
//             qdto.setWarpEnds(q.getWarpEnds());
//             qdto.setActualWarpEnds(q.getActualWarpEnds());
//             qdto.setSordEnds(q.getSordEnds());
//             qdto.setActualEnds(q.getActualEnds());
//             qdto.setParts(q.getParts());
//             qdto.setEndsPerPart(q.getEndsPerPart());
//             qdto.setWarpMeters(q.getWarpMeters());
//             qdto.setFabricPanelWidth(q.getFabricPanelWidth());
//             return qdto;
//         }).collect(Collectors.toList());

//         dto.setSizingBeamDetails(beamDtos);
//         dto.setSizingQualityDetails(qualityDtos);

//         return dto;
//     }

//     private Long normalizeId(Long id) {
//         return (id == null || id <= 0) ? null : id;
//     }
// }

package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingPlanDTO;
import com.tech3.erp.dto.SizingBeamDetailsDTO;
import com.tech3.erp.dto.SizingQualityDetailsDTO;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.SizingBeamDetails;
import com.tech3.erp.entity.SizingQualityDetails;
import com.tech3.erp.entity.SizingYarnIssueEntry;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.SizingBeamDetailsRepository;
import com.tech3.erp.repository.SizingQualityDetailsRepository;
import com.tech3.erp.repository.BeamInwardRepository;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.FactoryBeamInwardRepository;
import com.tech3.erp.repository.SalesReturnBeamRepository;
import com.tech3.erp.repository.SizingBeamOutwardRepository;
import com.tech3.erp.repository.SizingJobWorkOrderRepository;
import com.tech3.erp.repository.SizingProductionRepository;
import com.tech3.erp.repository.SizingYarnIssueEntryRepository;
import com.tech3.erp.repository.SizingYarnOutwardRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class SizingPlanService {

    private final SizingPlanRepository sizingPlanRepository;
    private final SizingBeamDetailsRepository beamDetailsRepository;
    private final SizingQualityDetailsRepository qualityDetailsRepository;
    private final BeamInwardRepository beamInwardRepository;
    private final BeamOutwardRepository beamOutwardRepository;
    private final FactoryBeamInwardRepository factoryBeamInwardRepository;
    private final SalesReturnBeamRepository salesReturnBeamRepository;
    private final SizingBeamOutwardRepository sizingBeamOutwardRepository;
    private final SizingProductionRepository sizingProductionRepository;
    private final SizingYarnOutwardRepository sizingYarnOutwardRepository;
    private final SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository;
    private final SizingJobWorkOrderService sizingJobWorkOrderService;
    private final VendorRepository vendorRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final WeavingContractRepository weavingContractRepository;
    private final ReferenceValidationService referenceValidationService;
    private final SizingJobWorkOrderRepository sizingJobWorkOrderRepository;

    @Lazy
    @Autowired
    private SizingYarnIssueEntryService sizingYarnIssueEntryService;

    

    public SizingPlanService(
        SizingPlanRepository sizingPlanRepository,
        SizingBeamDetailsRepository beamDetailsRepository,
        SizingQualityDetailsRepository qualityDetailsRepository,
        BeamInwardRepository beamInwardRepository,
        BeamOutwardRepository beamOutwardRepository,
        FactoryBeamInwardRepository factoryBeamInwardRepository,
        SalesReturnBeamRepository salesReturnBeamRepository,
        SizingBeamOutwardRepository sizingBeamOutwardRepository,
        SizingProductionRepository sizingProductionRepository,
        SizingYarnOutwardRepository sizingYarnOutwardRepository,
        SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository,
        SizingJobWorkOrderService sizingJobWorkOrderService,
        VendorRepository vendorRepository,
        SalesOrderRepository salesOrderRepository,
        WeavingContractRepository weavingContractRepository,
        ReferenceValidationService referenceValidationService,
        SizingJobWorkOrderRepository sizingJobWorkOrderRepository
    ) {
        this.sizingPlanRepository = sizingPlanRepository;
        this.beamDetailsRepository = beamDetailsRepository;
        this.qualityDetailsRepository = qualityDetailsRepository;
        this.beamInwardRepository = beamInwardRepository;
        this.beamOutwardRepository = beamOutwardRepository;
        this.factoryBeamInwardRepository = factoryBeamInwardRepository;
        this.salesReturnBeamRepository = salesReturnBeamRepository;
        this.sizingBeamOutwardRepository = sizingBeamOutwardRepository;
        this.sizingProductionRepository = sizingProductionRepository;
        this.sizingYarnOutwardRepository = sizingYarnOutwardRepository;
        this.sizingYarnIssueEntryRepository = sizingYarnIssueEntryRepository;
        this.sizingJobWorkOrderService = sizingJobWorkOrderService;
        this.vendorRepository = vendorRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.weavingContractRepository = weavingContractRepository;
        this.referenceValidationService = referenceValidationService;
        this.sizingJobWorkOrderRepository = sizingJobWorkOrderRepository;
    }

    @Transactional
    public SizingPlanDTO saveSizingPlan(SizingPlanDTO dto) {
        SizingPlan plan = mapDtoToEntity(dto, new SizingPlan());
        final SizingPlan savedPlan = sizingPlanRepository.save(plan);

        List<SizingBeamDetails> beamEntities = dto.getSizingBeamDetails().stream().map(b -> {
            SizingBeamDetails beam = new SizingBeamDetails();
            beam.setSizingPlan(savedPlan);

            Long weavingContractId = normalizeId(b.getWeavingContractId());
            if (weavingContractId == null) {
                throw new IllegalArgumentException("Weaving Contract is required for all Beam Details");
            }
            referenceValidationService.requireReference(
                weavingContractId,
                () -> weavingContractRepository.findById(weavingContractId),
                "Weaving Contract",
                null);
            beam.setWeavingContractId(weavingContractId);

            Long salesOrderId = normalizeId(b.getSalesOrderId());
            if (salesOrderId != null) {
                referenceValidationService.requireReference(
                    salesOrderId,
                    () -> salesOrderRepository.findById(salesOrderId),
                    "Sales Order",
                    null);
            }
            beam.setSalesOrderId(salesOrderId);

            beam.setWarpMeters(b.getWarpMeters());
            beam.setShrinkage(b.getShrinkage());
            beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
            beam.setWeavingVendorId(b.getWeavingVendorId());
            beam.setFabricId(b.getFabricId());
            beam.setBeamTypeId(normalizeId(b.getBeamTypeId()));
            beam.setNoOfBeams(b.getNoOfBeams());
            beam.setStatus(0); // Set default status to 0
            return beam;
        }).collect(Collectors.toList());
        beamDetailsRepository.saveAll(beamEntities);

        List<SizingQualityDetails> qualityEntities = dto.getSizingQualityDetails().stream().map(q -> {
            SizingQualityDetails quality = new SizingQualityDetails();
            quality.setSizingPlan(savedPlan);
            quality.setQuality(q.getQuality());
            quality.setFabricCode(q.getFabricCode());
            quality.setFabricId(q.getFabricId());
            quality.setYarnId(q.getYarnId());
            quality.setActualYarn(q.getActualYarn());
            quality.setWarpEnds(q.getWarpEnds());
            quality.setActualWarpEnds(q.getActualWarpEnds());
            quality.setSordEnds(q.getSordEnds());
            quality.setActualEnds(q.getActualEnds());
            quality.setParts(q.getParts());
            quality.setEndsPerPart(q.getEndsPerPart());
            quality.setWarpMeters(q.getWarpMeters());
            quality.setFabricPanelWidth(q.getFabricPanelWidth());
            quality.setNoOfCreels(q.getNoOfCreels());
            quality.setNoReqCones(q.getNoReqCones());
            return quality;
        }).collect(Collectors.toList());
        qualityDetailsRepository.saveAll(qualityEntities);
        syncSizingJobWorkOrder(savedPlan);

        return getSizingPlanById(savedPlan.getId());
    }


    public List<SizingPlanDTO> getAllSizingPlans() {
        List<SizingPlan> plans = sizingPlanRepository.findAll();
        return plans.stream()
                .sorted(Comparator.comparing(SizingPlan::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
    
    public PaginatedResponse<SizingPlanDTO> getAllSizingPlansWithPagination(Long offset, Long limit) {
        long finalLimit = (limit != null && limit > 0) ? limit : 10L;
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        Page<SizingPlan> page = sizingPlanRepository.findAll(
                PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"))
        );
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }
    
    public List<SizingPlanDTO> getAllSizingPlansByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return sizingPlanRepository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
    
    public PaginatedResponse<SizingPlanDTO> getAllSizingPlansByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        long finalLimit = (limit != null && limit > 0) ? limit : 10L;
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        Page<SizingPlan> page = sizingPlanRepository.findByCompanyId(
                companyId,
                PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"))
        );
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    public SizingPlanDTO getSizingPlanById(Long id) {
        SizingPlan plan = sizingPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
        return mapEntityToDto(plan);
    }
    
    public SizingPlanDTO getSizingPlanByIdAndCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        SizingPlan plan = sizingPlanRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
        return mapEntityToDto(plan);
    }

    @Transactional
    public SizingPlanDTO updateSizingPlan(Long id, SizingPlanDTO dto) {
        SizingPlan existingPlan = sizingPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));

        mapDtoToEntity(dto, existingPlan);
        sizingPlanRepository.save(existingPlan);

        beamDetailsRepository.deleteBySizingPlanId(id);
        qualityDetailsRepository.deleteBySizingPlanId(id);

        List<SizingBeamDetails> beamEntities = dto.getSizingBeamDetails().stream().map(b -> {
            SizingBeamDetails beam = new SizingBeamDetails();
            beam.setSizingPlan(existingPlan);

            Long weavingContractId = normalizeId(b.getWeavingContractId());
            if (weavingContractId == null) {
                throw new IllegalArgumentException("Weaving Contract is required for all Beam Details");
            }
            referenceValidationService.requireReference(
                weavingContractId,
                () -> weavingContractRepository.findById(weavingContractId),
                "Weaving Contract",
                null);
            beam.setWeavingContractId(weavingContractId);

            Long salesOrderId = normalizeId(b.getSalesOrderId());
            if (salesOrderId != null) {
                referenceValidationService.requireReference(
                    salesOrderId,
                    () -> salesOrderRepository.findById(salesOrderId),
                    "Sales Order",
                    null);
            }
            beam.setSalesOrderId(salesOrderId);

            beam.setWarpMeters(b.getWarpMeters());
            beam.setShrinkage(b.getShrinkage());
            beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
            beam.setWeavingVendorId(b.getWeavingVendorId());
            beam.setFabricId(b.getFabricId());
            beam.setBeamTypeId(normalizeId(b.getBeamTypeId()));
            beam.setStatus(b.getStatus() != null ? b.getStatus() : 0); // Preserve status or default to 0
            return beam;
        }).collect(Collectors.toList());
        beamDetailsRepository.saveAll(beamEntities);

        List<SizingQualityDetails> qualityEntities = dto.getSizingQualityDetails().stream().map(q -> {
            SizingQualityDetails quality = new SizingQualityDetails();
            quality.setSizingPlan(existingPlan);
            quality.setQuality(q.getQuality());
            quality.setFabricCode(q.getFabricCode());
            quality.setFabricId(q.getFabricId());
            quality.setYarnId(q.getYarnId());
            quality.setActualYarn(q.getActualYarn());
            quality.setWarpEnds(q.getWarpEnds());
            quality.setActualWarpEnds(q.getActualWarpEnds());
            quality.setSordEnds(q.getSordEnds());
            quality.setActualEnds(q.getActualEnds());
            quality.setParts(q.getParts());
            quality.setEndsPerPart(q.getEndsPerPart());
            quality.setWarpMeters(q.getWarpMeters());
            quality.setFabricPanelWidth(q.getFabricPanelWidth());
            quality.setNoOfCreels(q.getNoOfCreels());
            quality.setNoReqCones(q.getNoReqCones());
            return quality;
        }).collect(Collectors.toList());
        qualityDetailsRepository.saveAll(qualityEntities);
        syncSizingJobWorkOrder(existingPlan);

        return getSizingPlanById(existingPlan.getId());
    }
    
    @Transactional
    public SizingPlanDTO updateSizingPlanByCompany(Long companyId, Long id, SizingPlanDTO dto) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        SizingPlan existingPlan = sizingPlanRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
        dto.setCompanyId(existingPlan.getCompanyId());
        mapDtoToEntity(dto, existingPlan);
        sizingPlanRepository.save(existingPlan);
        beamDetailsRepository.deleteBySizingPlanId(id);
        qualityDetailsRepository.deleteBySizingPlanId(id);
        List<SizingBeamDetails> beamEntities = dto.getSizingBeamDetails().stream().map(b -> {
            SizingBeamDetails beam = new SizingBeamDetails();
            beam.setSizingPlan(existingPlan);

            Long weavingContractId = normalizeId(b.getWeavingContractId());
            if (weavingContractId == null) {
                throw new IllegalArgumentException("Weaving Contract is required for all Beam Details");
            }
            referenceValidationService.requireReference(
                weavingContractId,
                () -> weavingContractRepository.findById(weavingContractId),
                "Weaving Contract",
                null);
            beam.setWeavingContractId(weavingContractId);

            Long salesOrderId = normalizeId(b.getSalesOrderId());
            if (salesOrderId != null) {
                referenceValidationService.requireReference(
                    salesOrderId,
                    () -> salesOrderRepository.findById(salesOrderId),
                    "Sales Order",
                    null);
            }
            beam.setSalesOrderId(salesOrderId);

            beam.setWarpMeters(b.getWarpMeters());
            beam.setShrinkage(b.getShrinkage());
            beam.setExpectedFabricMeter(b.getExpectedFabricMeter());
            beam.setWeavingVendorId(b.getWeavingVendorId());
            beam.setFabricId(b.getFabricId());
            beam.setBeamTypeId(normalizeId(b.getBeamTypeId()));
            beam.setStatus(b.getStatus() != null ? b.getStatus() : 0);
            return beam;
        }).collect(Collectors.toList());
        beamDetailsRepository.saveAll(beamEntities);
        List<SizingQualityDetails> qualityEntities = dto.getSizingQualityDetails().stream().map(q -> {
            SizingQualityDetails quality = new SizingQualityDetails();
            quality.setSizingPlan(existingPlan);
            quality.setQuality(q.getQuality());
            quality.setFabricCode(q.getFabricCode());
            quality.setFabricId(q.getFabricId());
            quality.setYarnId(q.getYarnId());
            quality.setActualYarn(q.getActualYarn());
            quality.setWarpEnds(q.getWarpEnds());
            quality.setActualWarpEnds(q.getActualWarpEnds());
            quality.setSordEnds(q.getSordEnds());
            quality.setActualEnds(q.getActualEnds());
            quality.setParts(q.getParts());
            quality.setEndsPerPart(q.getEndsPerPart());
            quality.setWarpMeters(q.getWarpMeters());
            quality.setFabricPanelWidth(q.getFabricPanelWidth());
            quality.setNoOfCreels(q.getNoOfCreels());
            quality.setNoReqCones(q.getNoReqCones());
            return quality;
        }).collect(Collectors.toList());
        qualityDetailsRepository.saveAll(qualityEntities);
        syncSizingJobWorkOrder(existingPlan);
        return getSizingPlanByIdAndCompany(companyId, existingPlan.getId());
    }

    public void deleteSizingPlan(Long id) {
        SizingPlan plan = sizingPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
        ensureNoSizingPlanReferences(plan);
        sizingJobWorkOrderService.deleteBySizingPlanId(id);
        List<SizingYarnIssueEntry> yarnIssueEntries = sizingYarnIssueEntryRepository.findBySizingPlanId(id);
        if (yarnIssueEntries != null && !yarnIssueEntries.isEmpty()) {
            for (SizingYarnIssueEntry issueEntry : yarnIssueEntries) {
                sizingYarnIssueEntryService.deleteSizingYarnIssueEntry(issueEntry.getId());
            }
        }
        sizingYarnIssueEntryService.deleteYarnOutwardBySizingPlanId(id);
        try {
            sizingPlanRepository.delete(plan);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete SizingPlan because it is referenced by other", e);
        }
    }

    public boolean isMapped(Long id) {
        return sizingPlanRepository.findById(id).map(plan -> {
            Long planId = plan.getId();
            String planNo = plan.getSizingPlanNo();
            if (beamInwardRepository.existsBySizingPlanId(planId)) return true;
            if (beamOutwardRepository.existsBySizingPlanId(planId)) return true;
            if (factoryBeamInwardRepository.existsBySizingPlanId(planId)) return true;
            if (sizingBeamOutwardRepository.existsBySizingPlanId(planId)) return true;
            if (sizingProductionRepository.existsBySizingPlanId(planId)) return true;
            if (sizingYarnOutwardRepository.existsBySizingPlanId(planId)) return true;
           if (sizingJobWorkOrderRepository.existsBySizingPlanId(planId)) return true;
            if (planNo != null) {
                String trimmedPlanNo = planNo.trim();
                if (!trimmedPlanNo.isEmpty()) {
                    if (salesReturnBeamRepository.existsBySizingPlanNo(trimmedPlanNo)) return true;
                    if (sizingBeamOutwardRepository.existsBySizingPlanNo(trimmedPlanNo)) return true;
                    if (sizingProductionRepository.existsBySizingPlanNo(trimmedPlanNo)) return true;
                }
            }
            return false;
        }).orElse(false);
    }
    
    public void deleteSizingPlanByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        SizingPlan plan = sizingPlanRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("SizingPlan not found with id: " + id));
        ensureNoSizingPlanReferences(plan);
        sizingJobWorkOrderService.deleteBySizingPlanId(id);
        List<SizingYarnIssueEntry> yarnIssueEntries = sizingYarnIssueEntryRepository.findBySizingPlanId(id);
        if (yarnIssueEntries != null && !yarnIssueEntries.isEmpty()) {
            for (SizingYarnIssueEntry issueEntry : yarnIssueEntries) {
                sizingYarnIssueEntryService.deleteSizingYarnIssueEntry(issueEntry.getId());
            }
        }
        sizingYarnIssueEntryService.deleteYarnOutwardBySizingPlanId(id);
        try {
            sizingPlanRepository.delete(plan);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete SizingPlan because it is referenced by other", e);
        }
    }



    private void ensureNoSizingPlanReferences(SizingPlan plan) {
        if (plan == null || plan.getId() == null) {
            return;
        }
        Long planId = plan.getId();
        String planNo = plan.getSizingPlanNo();
        if (beamInwardRepository.existsBySizingPlanId(planId)) {
            throw new IllegalStateException("Cannot delete SizingPlan because one or more beam inward records reference it");
        }
        if (beamOutwardRepository.existsBySizingPlanId(planId)) {
            throw new IllegalStateException("Cannot delete SizingPlan because one or more beam outward records reference it");
        }
        if (factoryBeamInwardRepository.existsBySizingPlanId(planId)) {
            throw new IllegalStateException("Cannot delete SizingPlan because factory beam inward records reference it");
        }
        if (sizingBeamOutwardRepository.existsBySizingPlanId(planId)) {
            throw new IllegalStateException("Cannot delete SizingPlan because sizing beam outward records reference it");
        }
        if (sizingProductionRepository.existsBySizingPlanId(planId)) {
            String identifier = planNo != null ? planNo : "SizingPlan #" + planId;
            throw new IllegalStateException("this " + identifier + " is already mapped in sizing production");
        }
        if (sizingYarnOutwardRepository.existsBySizingPlanId(planId)) {
            throw new IllegalStateException("Cannot delete SizingPlan because yarn outward records reference it");
        }


        if (planNo != null) {
            String trimmedPlanNo = planNo.trim();
            if (!trimmedPlanNo.isEmpty()) {
                if (salesReturnBeamRepository.existsBySizingPlanNo(trimmedPlanNo)) {
                    throw new IllegalStateException("Cannot delete SizingPlan because sales return beams reference sizing plan number " + trimmedPlanNo);
                }
                if (sizingBeamOutwardRepository.existsBySizingPlanNo(trimmedPlanNo)) {
                    throw new IllegalStateException("Cannot delete SizingPlan because sizing beam outward records reference sizing plan number " + trimmedPlanNo);
                }
                if (sizingProductionRepository.existsBySizingPlanNo(trimmedPlanNo)) {
                    throw new IllegalStateException("this " + trimmedPlanNo + " is already mapped in sizing production");
                }
            }
        }
    }

    private PaginatedResponse<SizingPlanDTO> buildPaginatedResponse(Page<SizingPlan> page, Long offset, Long limit) {
        List<SizingPlanDTO> content = page.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(
                page.getTotalElements(),
                !page.isLast(),
                page.getNumber() > 0,
                offset,
                limit,
                content
        );
    }

    private void syncSizingJobWorkOrder(SizingPlan plan) {
        if (plan == null || plan.getId() == null) {
            return;
        }

        Integer isInternalVendor = 0;
        if (plan.getVendorId() != null) {
            isInternalVendor = vendorRepository.findById(plan.getVendorId())
                    .map(Vendor::getIsInternalVendor)
                    .orElse(0);
        }

        if (isInternalVendor != null && isInternalVendor == 1) {
            List<SizingQualityDetails> details = qualityDetailsRepository.findBySizingPlanId(plan.getId());
            sizingJobWorkOrderService.createOrUpdateFromSizingPlan(plan, details);
        } else {
            sizingJobWorkOrderService.deleteBySizingPlanId(plan.getId());
        }
    }

    // Helper method to map SizingBeamDetails entity to DTO
    private SizingBeamDetailsDTO mapBeamToDto(SizingBeamDetails b) {
        SizingBeamDetailsDTO bdto = new SizingBeamDetailsDTO();
        bdto.setId(b.getId());
        bdto.setWeavingContractId(b.getWeavingContractId());
        bdto.setSalesOrderId(b.getSalesOrderId());
        bdto.setWarpMeters(b.getWarpMeters());
        bdto.setShrinkage(b.getShrinkage());
        bdto.setExpectedFabricMeter(b.getExpectedFabricMeter());
        bdto.setWeavingVendorId(b.getWeavingVendorId());
        bdto.setFabricId(b.getFabricId());
        bdto.setBeamTypeId(b.getBeamTypeId());
        bdto.setStatus(b.getStatus()); // Add status field
        bdto.setIsBeamOutward(b.getIsBeamOutward());
        return bdto;
    }

    // Helpers
    private SizingPlan mapDtoToEntity(SizingPlanDTO dto, SizingPlan plan) {
        plan.setVendorId(dto.getVendorId());
        plan.setTermsConditionsId(dto.getTermsConditionsId());
        plan.setPaymentTermsId(dto.getPaymentTermsId());
        plan.setSizingRate(dto.getSizingRate());
        plan.setOrderTypeId(dto.getOrderTypeId());
        plan.setEmptyBeamNo(dto.getEmptyBeamNo());
        plan.setNoOfCreels(dto.getNoOfCreels());
        plan.setRemarks(dto.getRemarks());
        plan.setSizingPlanNo(dto.getSizingPlanNo());
        plan.setCompanyId(dto.getCompanyId());
        plan.setTradingCompanyId(dto.getTradingCompanyId());
        plan.setFactoryCompanyId(dto.getFactoryCompanyId());
        if (dto.getSizingPlanDate() != null) {
            plan.setSizingPlanDate(LocalDate.parse(dto.getSizingPlanDate()));
        }
        if (dto.getIsComplete() != null) {
            plan.setIsComplete(dto.getIsComplete());
        }
        return plan;
    }

    private SizingPlanDTO mapEntityToDto(SizingPlan plan) {
        SizingPlanDTO dto = new SizingPlanDTO();
        dto.setId(plan.getId());
        dto.setVendorId(plan.getVendorId());
        dto.setTermsConditionsId(plan.getTermsConditionsId());
        dto.setPaymentTermsId(plan.getPaymentTermsId());
        dto.setSizingRate(plan.getSizingRate());
        dto.setOrderTypeId(plan.getOrderTypeId());
        dto.setEmptyBeamNo(plan.getEmptyBeamNo());
        dto.setNoOfCreels(plan.getNoOfCreels());
        dto.setRemarks(plan.getRemarks());
        dto.setSizingPlanNo(plan.getSizingPlanNo());
        dto.setCompanyId(plan.getCompanyId());
        dto.setTradingCompanyId(plan.getTradingCompanyId());
        dto.setFactoryCompanyId(plan.getFactoryCompanyId());
        if (plan.getSizingPlanDate() != null) {
            dto.setSizingPlanDate(plan.getSizingPlanDate().toString());
        }
        dto.setIsComplete(plan.getIsComplete());

        List<SizingBeamDetailsDTO> beamDtos = beamDetailsRepository.findBySizingPlanId(plan.getId())
                .stream()
                .map(this::mapBeamToDto)
                .collect(Collectors.toList());

        List<SizingQualityDetailsDTO> qualityDtos = qualityDetailsRepository.findBySizingPlanId(plan.getId()).stream().map(q -> {
            SizingQualityDetailsDTO qdto = new SizingQualityDetailsDTO();
            qdto.setId(q.getId());
            qdto.setQuality(q.getQuality());
            qdto.setFabricCode(q.getFabricCode());
            qdto.setFabricId(q.getFabricId());
            qdto.setYarnId(q.getYarnId());
            qdto.setActualYarn(q.getActualYarn());
            qdto.setWarpEnds(q.getWarpEnds());
            qdto.setActualWarpEnds(q.getActualWarpEnds());
            qdto.setSordEnds(q.getSordEnds());
            qdto.setActualEnds(q.getActualEnds());
            qdto.setParts(q.getParts());
            qdto.setEndsPerPart(q.getEndsPerPart());
            qdto.setWarpMeters(q.getWarpMeters());
            qdto.setFabricPanelWidth(q.getFabricPanelWidth());
            qdto.setNoOfCreels(q.getNoOfCreels());
            qdto.setNoReqCones(q.getNoReqCones());
            return qdto;
        }).collect(Collectors.toList());

        dto.setSizingBeamDetails(beamDtos);
        dto.setSizingQualityDetails(qualityDtos);

        return dto;
    }

    private Long normalizeId(Long id) {
        return (id == null || id <= 0) ? null : id;
    }
}