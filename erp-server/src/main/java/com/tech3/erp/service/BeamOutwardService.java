// package com.tech3.erp.service;

// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.concurrent.ConcurrentHashMap;
// import java.util.stream.Collectors;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Sort;

// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.util.PaginationHelper;

// import org.hibernate.validator.internal.util.stereotypes.Lazy;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.tech3.erp.dto.BeamOutwardDTO;
// import com.tech3.erp.dto.BeamOutwardDetailDTO;
// import com.tech3.erp.entity.BeamOutward;
// import com.tech3.erp.entity.BeamOutwardDetail;
// import com.tech3.erp.entity.BeamInward;
// import com.tech3.erp.entity.Company;
// import com.tech3.erp.entity.FactoryBeamInward;
// import com.tech3.erp.entity.FactoryBeamInwardBeamDetails;
// import com.tech3.erp.entity.FactoryBeamInwardQualityDetails;
// import com.tech3.erp.entity.SizingPlan;
// import com.tech3.erp.entity.SizingProductionDetail;
// import com.tech3.erp.entity.Vendor;
// import com.tech3.erp.entity.WeavingWorkOrder;
// import com.tech3.erp.repository.BeamOutwardRepository;
// import com.tech3.erp.repository.BeamInwardRepository;
// import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
// import com.tech3.erp.repository.BeamInwardQualityDetailsRepository;
// import com.tech3.erp.repository.CompanyRepository;
// import com.tech3.erp.repository.FactoryBeamInwardBeamDetailsRepository;
// import com.tech3.erp.repository.FactoryBeamInwardQualityDetailsRepository;
// import com.tech3.erp.repository.FactoryBeamInwardRepository;
// import com.tech3.erp.repository.FlangeBeamInwardDetailRepository;
// import com.tech3.erp.repository.SizingPlanRepository;
// import com.tech3.erp.repository.SizingProductionDetailRepository;
// import com.tech3.erp.repository.VendorRepository;
// import com.tech3.erp.repository.WeavingWorkOrderRepository;

// import jakarta.persistence.EntityNotFoundException;

// @Service
// @Transactional
// public class BeamOutwardService {

//     private static final Logger logger = LoggerFactory.getLogger(BeamOutwardService.class);

//     private final BeamOutwardRepository repository;
//     private final BeamInwardRepository beamInwardRepository;
//     private final BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository;
//     private final BeamInwardQualityDetailsRepository beamInwardQualityDetailsRepository;
//     private final FactoryBeamInwardRepository factoryBeamInwardRepository;
//     private final FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository;
//     private final FactoryBeamInwardQualityDetailsRepository factoryBeamInwardQualityDetailsRepository;
//     private final SizingPlanRepository sizingPlanRepository;
//     private final SizingProductionDetailRepository sizingProductionDetailRepository;
//     private final WeavingWorkOrderRepository weavingWorkOrderRepository;
//     private final VendorRepository vendorRepository;
//     private final CompanyRepository companyRepository;
//     private final FlangeBeamInwardDetailRepository flangeBeamInwardDetailRepository;
//     private final Map<String, Long> companyNameLookup = new ConcurrentHashMap<>();

//     @Lazy
//     @Autowired
//     private BeamInwardService beamInwardService;

//     public BeamOutwardService(
//             BeamOutwardRepository repository,
//             BeamInwardRepository beamInwardRepository,
//             BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository,
//             BeamInwardQualityDetailsRepository beamInwardQualityDetailsRepository,
//             FactoryBeamInwardRepository factoryBeamInwardRepository,
//             FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository,
//             FactoryBeamInwardQualityDetailsRepository factoryBeamInwardQualityDetailsRepository,
//             SizingPlanRepository sizingPlanRepository,
//             SizingProductionDetailRepository sizingProductionDetailRepository,
//             WeavingWorkOrderRepository weavingWorkOrderRepository,
//             VendorRepository vendorRepository,
//             CompanyRepository companyRepository,
//             FlangeBeamInwardDetailRepository flangeBeamInwardDetailRepository) {
//         this.repository = repository;
//         this.beamInwardRepository = beamInwardRepository;
//         this.beamInwardBeamDetailsRepository = beamInwardBeamDetailsRepository;
//         this.beamInwardQualityDetailsRepository = beamInwardQualityDetailsRepository;
//         this.factoryBeamInwardRepository = factoryBeamInwardRepository;
//         this.factoryBeamInwardBeamDetailsRepository = factoryBeamInwardBeamDetailsRepository;
//         this.factoryBeamInwardQualityDetailsRepository = factoryBeamInwardQualityDetailsRepository;
//         this.sizingPlanRepository = sizingPlanRepository;
//         this.sizingProductionDetailRepository = sizingProductionDetailRepository;
//         this.weavingWorkOrderRepository = weavingWorkOrderRepository;
//         this.vendorRepository = vendorRepository;
//         this.companyRepository = companyRepository;
//         this.flangeBeamInwardDetailRepository = flangeBeamInwardDetailRepository;
//     }

//     public BeamOutwardDTO create(BeamOutwardDTO dto) {
//         BeamOutward entity = toEntity(dto, new BeamOutward());
//         BeamOutward saved = repository.save(entity);
//         markBeamDetailsOutwarded(saved.getBeamInwardId(), saved.getSizedBeamNo(), saved.getFlangeNo());
//         if (saved.getOutwardDetails() != null) {
//             for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
//                 markBeamDetailsOutwarded(saved.getBeamInwardId(), d.getSizedBeamNo(), d.getFlangeNo());
//             }
//         }
//         updateSizingProductionIssuedStatus(saved.getSizingPlanId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getIsIssuedToWeaving(), saved.getIsFactoryInward());
//         if (saved.getOutwardDetails() != null) {
//             for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
//                 updateSizingProductionIssuedStatus(saved.getSizingPlanId(), d.getSizedBeamNo(), d.getFlangeNo(), d.getIsIssuedToWeaving(), d.getIsFactoryInward());
//             }
//         }
        
//         if (!SyncContext.isInProgress()) {
//             try {
//                 SyncContext.setInProgress(true);
//                 syncToFactoryBeamInward(saved.getId());
//                 syncToBeamInward(saved.getId());
//             } finally {
//                 SyncContext.clear();
//             }
//         }
//         // beamStockService.syncFromBeamOutward(saved);
//         return toDto(saved);
//     }

//     public List<BeamOutwardDTO> getAll() {
//         return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
//     }

//     public List<BeamOutwardDTO> getAllByCompany(Long companyId) {
//         return repository.findByCompanyId(companyId).stream()
//                 .sorted(Comparator.comparing(BeamOutward::getId).reversed())
//                 .map(this::toDto)
//                 .collect(Collectors.toList());
//     }

//     public PaginatedResponse<BeamOutwardDTO> getAllWithPagination(Long offset, Long limit) {
//         long finalLimit = (limit != null && limit > 0) ? limit : PaginationHelper.getDefaultLimit();
//         long finalOffset = (offset != null && offset > 0) ? offset : 0L;
//         PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
//         Page<BeamOutward> page = repository.findAll(pageRequest);
//         return buildPaginatedResponse(page, finalOffset, finalLimit);
//     }

//     public PaginatedResponse<BeamOutwardDTO> getAllByCompanyWithPagination(Long companyId, Long offset, Long limit) {
//         long finalLimit = (limit != null && limit > 0) ? limit : PaginationHelper.getDefaultLimit();
//         long finalOffset = (offset != null && offset > 0) ? offset : 0L;
//         PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
//         Page<BeamOutward> page = repository.findByCompanyId(companyId, pageRequest);
//         return buildPaginatedResponse(page, finalOffset, finalLimit);
//     }

//     private PaginatedResponse<BeamOutwardDTO> buildPaginatedResponse(Page<BeamOutward> page, long offset, long limit) {
//         List<BeamOutwardDTO> content = page.getContent().stream()
//                 .map(this::toDto)
//                 .collect(Collectors.toList());
//         return new PaginatedResponse<>(
//                 page.getTotalElements(),
//                 page.hasNext(),
//                 page.getNumber() > 0,
//                 offset,
//                 limit,
//                 content
//         );
//     }

//     public BeamOutwardDTO getById(Long id) {
//         BeamOutward entity = repository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("BeamOutward not found with id: " + id));
//         return toDto(entity);
//     }

//     public List<BeamOutwardDTO> getByBeamInwardId(Long beamInwardId) {
//         return repository.findByBeamInwardId(beamInwardId).stream().map(this::toDto).collect(Collectors.toList());
//     }

//     public BeamOutwardDTO getByIdAndCompany(Long companyId, Long id) {
//         BeamOutward entity = repository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("BeamOutward not found with id: " + id + " for companyId: " + companyId));
//         return toDto(entity);
//     }

//     public BeamOutwardDTO update(Long id, BeamOutwardDTO dto) {
//         if (SyncContext.isInProgress()) {
//             return doUpdate(id, dto);
//         }
//         try {
//             SyncContext.setInProgress(true);
//             BeamOutwardDTO updated = doUpdate(id, dto);
//             syncToFactoryBeamInward(id);
//             syncToBeamInward(id);
//             return updated;
//         } finally {
//             SyncContext.clear();
//         }
//     }

//     private BeamOutwardDTO doUpdate(Long id, BeamOutwardDTO dto) {
//         BeamOutward existing = repository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("BeamOutward not found with id: " + id));
//         Long existingCompanyId = existing.getCompanyId();
//         BeamOutward updated = toEntity(dto, existing);
//         if (updated.getCompanyId() == null) {
//             updated.setCompanyId(existingCompanyId);
//         }
//         updated.setId(id);
//         BeamOutward saved = repository.save(updated);
//         markBeamDetailsOutwarded(saved.getBeamInwardId(), saved.getSizedBeamNo(), saved.getFlangeNo());
//         if (saved.getOutwardDetails() != null) {
//             for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
//                 markBeamDetailsOutwarded(saved.getBeamInwardId(), d.getSizedBeamNo(), d.getFlangeNo());
//             }
//         }
//         updateSizingProductionIssuedStatus(saved.getSizingPlanId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getIsIssuedToWeaving(), saved.getIsFactoryInward());
//         if (saved.getOutwardDetails() != null) {
//             for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
//                 updateSizingProductionIssuedStatus(saved.getSizingPlanId(), d.getSizedBeamNo(), d.getFlangeNo(), d.getIsIssuedToWeaving(), d.getIsFactoryInward());
//             }
//         }
//         // beamStockService.syncFromBeamOutward(saved);
//         return toDto(saved);
//     }

//     public BeamOutwardDTO updateByCompany(Long companyId, Long id, BeamOutwardDTO dto) {
//         if (SyncContext.isInProgress()) {
//             return doUpdateByCompany(companyId, id, dto);
//         }
//         try {
//             SyncContext.setInProgress(true);
//             BeamOutwardDTO updated = doUpdateByCompany(companyId, id, dto);
//             syncToFactoryBeamInward(id);
//             syncToBeamInward(id);
//             return updated;
//         } finally {
//             SyncContext.clear();
//         }
//     }

//     private BeamOutwardDTO doUpdateByCompany(Long companyId, Long id, BeamOutwardDTO dto) {
//         BeamOutward existing = repository.findByIdAndCompanyId(id, companyId)
//                 .orElseThrow(() -> new EntityNotFoundException("BeamOutward not found with id: " + id + " for companyId: " + companyId));
//         Long existingCompanyId = existing.getCompanyId();
//         BeamOutward updated = toEntity(dto, existing);
//         if (updated.getCompanyId() == null) {
//             updated.setCompanyId(existingCompanyId);
//         }
//         updated.setId(id);
//         BeamOutward saved = repository.save(updated);
//         markBeamDetailsOutwarded(saved.getBeamInwardId(), saved.getSizedBeamNo(), saved.getFlangeNo());
//         if (saved.getOutwardDetails() != null) {
//             for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
//                 markBeamDetailsOutwarded(saved.getBeamInwardId(), d.getSizedBeamNo(), d.getFlangeNo());
//             }
//         }
//         updateSizingProductionIssuedStatus(saved.getSizingPlanId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getIsIssuedToWeaving(), saved.getIsFactoryInward());
//         if (saved.getOutwardDetails() != null) {
//             for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
//                 updateSizingProductionIssuedStatus(saved.getSizingPlanId(), d.getSizedBeamNo(), d.getFlangeNo(), d.getIsIssuedToWeaving(), d.getIsFactoryInward());
//             }
//         }
//         // beamStockService.syncFromBeamOutward(saved);
//         return toDto(saved);
//     }

//     public void delete(Long id) {
//         if (!repository.existsById(id)) {
//             throw new EntityNotFoundException("BeamOutward not found with id: " + id);
//         }
//         repository.deleteById(id);
//     }

//     public void deleteByCompany(Long companyId, Long id) {
//         if (!repository.existsByIdAndCompanyId(id, companyId)) {
//             throw new EntityNotFoundException("BeamOutward not found with id: " + id + " for companyId: " + companyId);
//         }
//         repository.deleteByIdAndCompanyId(id, companyId);
//     }

//     private void markBeamDetailsOutwarded(Long beamInwardId, String sizedBeamNo, String flangeNo) {
//         if (beamInwardId == null) return;
//         List<com.tech3.erp.entity.BeamInwardBeamDetails> details;
//         boolean hasSizedBeamNo = sizedBeamNo != null && !sizedBeamNo.isEmpty();
//         boolean hasFlangeNo = flangeNo != null && !flangeNo.isEmpty();
//         if (hasSizedBeamNo && hasFlangeNo) {
//             details = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNoAndFlangeNo(beamInwardId, sizedBeamNo, flangeNo);
//         } else if (hasSizedBeamNo) {
//             details = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNo(beamInwardId, sizedBeamNo);
//         } else {
//             details = beamInwardBeamDetailsRepository.findByBeamInwardId(beamInwardId);
//         }
//         if (details == null || details.isEmpty()) return;
//         List<Long> flangeIds = new ArrayList<>();
//         for (com.tech3.erp.entity.BeamInwardBeamDetails d : details) {
//             d.setIsBeamOutwarded(1);
//             if (d.getFlangeId() != null) {
//                 flangeIds.add(d.getFlangeId());
//             }
//         }
//         beamInwardBeamDetailsRepository.saveAll(details);
      
//     }

//     private void updateSizingProductionIssuedStatus(Long sizingPlanId, String sizedBeamNo, String flangeNo, Long isIssuedToWeaving, Long isFactoryInward) {
//         if (sizingPlanId == null || sizedBeamNo == null) return;
//         List<SizingProductionDetail> details;
//         if (flangeNo != null && !flangeNo.isEmpty()) {
//             details = sizingProductionDetailRepository.findBySizingProductionSizingPlanIdAndSizedBeamNoAndFlangeNo(sizingPlanId, sizedBeamNo, flangeNo);
//         } else {
//             details = sizingProductionDetailRepository.findBySizingProductionSizingPlanIdAndSizedBeamNo(sizingPlanId, sizedBeamNo);
//         }
//         if (details != null && !details.isEmpty()) {
//             for (SizingProductionDetail d : details) {
//                 d.setIsIssuedToWeaving(isIssuedToWeaving != null ? isIssuedToWeaving : 0L);
//                 d.setIsFactoryInward(isFactoryInward != null ? isFactoryInward : 0L);
//             }
//             sizingProductionDetailRepository.saveAll(details);
//         }
//     }

//     public void syncToFactoryBeamInward(Long beamOutwardId) {
//         if (beamOutwardId == null) return;
//         BeamOutward saved = repository.findById(beamOutwardId).orElse(null);
//         if (saved == null) return;

//         Long sizingPlanId = saved.getSizingPlanId();
//         if (sizingPlanId == null) return;
//         Optional<SizingPlan> optPlan = sizingPlanRepository.findById(sizingPlanId);
//         if (optPlan.isEmpty()) return;
//         SizingPlan plan = optPlan.get();

//         // Load qualities once (shared across all contracts)
//         java.util.List<com.tech3.erp.entity.BeamInwardQualityDetails> qualities = null;
//         Long totalActualEndsFromQualities = null;
//         if (saved.getBeamInwardId() != null) {
//             qualities = beamInwardQualityDetailsRepository.findByBeamInwardId(saved.getBeamInwardId());
//             if (qualities != null && !qualities.isEmpty()) {
//                 long sum = 0L;
//                 boolean hasAny = false;
//                 for (var q : qualities) {
//                     if (q.getActualEnds() != null) {
//                         sum += q.getActualEnds();
//                         hasAny = true;
//                     }
//                 }
//                 if (hasAny) totalActualEndsFromQualities = sum;
//             }
//         }
//         final Long finalTotalActualEnds = totalActualEndsFromQualities;
//         final java.util.List<com.tech3.erp.entity.BeamInwardQualityDetails> finalQualities = qualities;

//         // Group beams by weavingContractId — separate FactoryBeamInward per contract
//         // Only sync if the weaver is an internal vendor
//         Map<String, List<BeamOutwardDetail>> beamsByContract = new java.util.LinkedHashMap<>();
//         List<BeamOutwardDetail> outwardDetails = saved.getOutwardDetails();
//         if (outwardDetails == null || outwardDetails.isEmpty()) {
//             if (isInternalVendor(saved.getWeavingVendorId())) {
//                 String contractKey = saved.getWeavingContractId() != null ? String.valueOf(saved.getWeavingContractId()) : "";
//                 beamsByContract.computeIfAbsent(contractKey, k -> new ArrayList<>()).add(null);
//             }
//         } else {
//             for (BeamOutwardDetail d : outwardDetails) {
//                 Long vId = d.getWeavingVendorId() != null ? d.getWeavingVendorId() : saved.getWeavingVendorId();
//                 if (isInternalVendor(vId)) {
//                     String contractKey = d.getWeavingContractId() != null ? String.valueOf(d.getWeavingContractId()) : "";
//                     beamsByContract.computeIfAbsent(contractKey, k -> new ArrayList<>()).add(d);
//                 }
//             }
//         }

//         // Delete FactoryBeamInward entries for contracts no longer present
//         List<FactoryBeamInward> existingFactoryInwards = factoryBeamInwardRepository.findByBeamOutwardId(saved.getId());
//         if (existingFactoryInwards != null) {
//             existingFactoryInwards.stream()
//                     .filter(fi -> {
//                         String fKey = fi.getWeavingContractId() != null ? fi.getWeavingContractId() : "";
//                         return !beamsByContract.containsKey(fKey);
//                     })
//                     .forEach(factoryBeamInwardRepository::delete);
//         }

//         for (Map.Entry<String, List<BeamOutwardDetail>> contractEntry : beamsByContract.entrySet()) {
//             String contractIdStr = contractEntry.getKey();
//             List<BeamOutwardDetail> contractBeams = contractEntry.getValue();

//             FactoryBeamInward f = (!contractIdStr.isEmpty()
//                     ? factoryBeamInwardRepository.findFirstByBeamOutwardIdAndWeavingContractId(saved.getId(), contractIdStr)
//                     : factoryBeamInwardRepository.findFirstByBeamOutwardId(saved.getId()))
//                     .orElse(new FactoryBeamInward());

//             f.setSizingVendorId(plan.getVendorId());
//             f.setSizingPlanId(saved.getSizingPlanId());
//             f.setSizingSetNo(plan.getSizingPlanNo());
//             f.setOrderTypeId(1L);
//             f.setWeavingContractId(!contractIdStr.isEmpty() ? contractIdStr : null);
//             f.setTradingCompanyId(saved.getTradingCompanyId());
//             f.setFactoryCompanyId(saved.getFactoryCompanyId());
//             Long resolvedWorkOrderId = !contractIdStr.isEmpty()
//                     ? weavingWorkOrderRepository.findFirstByWeavingContractIdOrderByIdDesc(Long.parseLong(contractIdStr))
//                             .map(WeavingWorkOrder::getId)
//                             .orElse(saved.getWeavingWorkOrderId())
//                     : saved.getWeavingWorkOrderId();
//             f.setWeavingWorkOrderId(resolvedWorkOrderId);
//             f.setBeamInwardDate(saved.getOutwardDate() != null ? saved.getOutwardDate() : LocalDate.now());
//             f.setBeamInwardNo(resolveBeamInwardNo(saved.getBeamInwardId()));
//             f.setBeamInwardId(saved.getBeamInwardId());
//             f.setBeamOutwardId(saved.getId());
//             f.setIsBeamPending(1);

//             FactoryBeamInward savedHeader = factoryBeamInwardRepository.save(f);

//             // Sync Beam Details
//             factoryBeamInwardBeamDetailsRepository.deleteByBeamInwardId(savedHeader.getId());
//             for (BeamOutwardDetail d : contractBeams) {
//                 FactoryBeamInwardBeamDetails b = new FactoryBeamInwardBeamDetails();
//                 b.setBeamInward(savedHeader);
//                 if (d == null) {
//                     b.setSizedBeamNo(saved.getSizedBeamNo());
//                     b.setFlangeId(saved.getFlangeId());
//                     b.setFlangeNo(saved.getFlangeNo());
//                     b.setWrapMeters(saved.getWrapMeters());
//                     b.setShrinkage(saved.getShrinkage());
//                     b.setExpectedFabricMeter(saved.getExpectedFabricMeter());
//                     b.setFabricPanelWidth(saved.getFabricPanelWidth());
//                 } else {
//                     b.setSizedBeamNo(d.getSizedBeamNo());
//                     b.setFlangeId(d.getFlangeId());
//                     b.setFlangeNo(d.getFlangeNo());
//                     b.setWrapMeters(d.getWrapMeters());
//                     b.setShrinkage(d.getShrinkage());
//                     b.setExpectedFabricMeter(d.getExpectedFabricMeter());
//                     b.setFabricPanelWidth(d.getFabricPanelWidth());
//                 }
//                 b.setActualEnds(finalTotalActualEnds);
//                 factoryBeamInwardBeamDetailsRepository.save(b);
//             }

//             // Sync Quality Details
//             factoryBeamInwardQualityDetailsRepository.deleteByBeamInwardId(savedHeader.getId());
//             if (finalQualities != null && !finalQualities.isEmpty()) {
//                 for (var q : finalQualities) {
//                     FactoryBeamInwardQualityDetails fq = new FactoryBeamInwardQualityDetails();
//                     fq.setBeamInward(savedHeader);
//                     fq.setQuality(q.getQuality());
//                     fq.setYarnId(q.getYarnId());
//                     fq.setSordEnds(q.getSordEnds());
//                     fq.setActualEnds(q.getActualEnds());
//                     fq.setParts(q.getParts());
//                     fq.setEndsPerPart(q.getEndsPerPart());
//                     fq.setWrapMeters(q.getWrapMeters());
//                     fq.setFabricCode(q.getFabricCode());
//                     fq.setActualYarn(q.getActualYarn());
//                     fq.setWarpEnds(q.getWarpEnds());
//                     fq.setActualWarpEnds(q.getActualWarpEnds());
//                     factoryBeamInwardQualityDetailsRepository.save(fq);
//                 }
//             }
//         }
//     }

//     public void syncToBeamInward(Long beamOutwardId) {
//         if (beamOutwardId == null) return;
//         BeamOutward saved = repository.findById(beamOutwardId).orElse(null);
//         if (saved == null || saved.getBeamInwardId() == null) return;
        
//         try {
//             com.tech3.erp.dto.BeamInwardDTO inward = beamInwardService.getBeamInwardById(saved.getBeamInwardId());
//             if (inward != null && inward.getBeamInwardBeamDetails() != null) {
//                 boolean changed = false;
//                 for (com.tech3.erp.dto.BeamInwardBeamDetailsDTO d : inward.getBeamInwardBeamDetails()) {
//                     if (saved.getSizedBeamNo() != null && saved.getSizedBeamNo().equals(d.getSizedBeamNo())) {
//                         d.setWrapMeters(saved.getWrapMeters());
//                         d.setShrinkage(saved.getShrinkage());
//                         d.setExpectedFabricMeter(saved.getExpectedFabricMeter());
//                         d.setIsBeamOutwarded(1);
//                         if (saved.getWeavingVendorId() != null) d.setWeavingVendorId(saved.getWeavingVendorId());
//                         if (saved.getWeavingContractId() != null) d.setWeavingContractId(saved.getWeavingContractId());
//                         if (saved.getSalesOrderId() != null) d.setSalesOrderId(saved.getSalesOrderId());
//                         if (saved.getFlangeId() != null) d.setFlangeId(saved.getFlangeId());
//                         if (saved.getFlangeNo() != null) d.setFlangeNo(saved.getFlangeNo());
//                         changed = true;
//                     }
//                 }
//                 if (saved.getOutwardDetails() != null) {
//                     for (com.tech3.erp.entity.BeamOutwardDetail od : saved.getOutwardDetails()) {
//                         for (com.tech3.erp.dto.BeamInwardBeamDetailsDTO d : inward.getBeamInwardBeamDetails()) {
//                             if (od.getSizedBeamNo() != null && od.getSizedBeamNo().equals(d.getSizedBeamNo())) {
//                                 d.setWrapMeters(od.getWrapMeters());
//                                 d.setShrinkage(od.getShrinkage());
//                                 d.setExpectedFabricMeter(od.getExpectedFabricMeter());
//                                 d.setIsBeamOutwarded(1);
//                                 if (od.getWeavingVendorId() != null) d.setWeavingVendorId(od.getWeavingVendorId());
//                                 if (od.getWeavingContractId() != null) d.setWeavingContractId(od.getWeavingContractId());
//                                 if (od.getSalesOrderId() != null) d.setSalesOrderId(od.getSalesOrderId());
//                                 if (od.getFlangeId() != null) d.setFlangeId(od.getFlangeId());
//                                 if (od.getFlangeNo() != null) d.setFlangeNo(od.getFlangeNo());
//                                 changed = true;
//                             }
//                         }
//                     }
//                 }
//                 if (changed) {
//                     beamInwardService.updateBeamInward(inward.getId(), inward);
//                 }
//             }
//         } catch (Exception e) {
//             logger.error("Error syncing to BeamInward from BeamOutward: " + e.getMessage());
//         }
//     }

//     private String resolveBeamInwardNo(Long beamInwardId) {
//         if (beamInwardId == null) {
//             return null;
//         }
//         return beamInwardRepository.findById(beamInwardId)
//                 .map(BeamInward::getBeamInwardNo)
//                 .orElse(null);
//     }

//     private BeamOutward toEntity(BeamOutwardDTO dto, BeamOutward entity) {
//         entity.setBeamInwardId(dto.getBeamInwardId());
//         entity.setBeamInwardNo(dto.getBeamInwardNo());
//         entity.setWeavingContractId(dto.getWeavingContractId());
//         entity.setWeavingVendorId(dto.getWeavingVendorId());
//         entity.setSizingVendorId(dto.getSizingVendorId());
//         entity.setSalesOrderId(dto.getSalesOrderId());
//         entity.setSizedBeamNo(dto.getSizedBeamNo());
//         entity.setFlangeNo(dto.getFlangeNo());
//         entity.setFlangeId(dto.getFlangeId());
//         entity.setOutwardNo(dto.getOutwardNo());
//         entity.setWrapMeters(dto.getWrapMeters());
//         entity.setShrinkage(dto.getShrinkage());
//         entity.setExpectedFabricMeter(dto.getExpectedFabricMeter());
//         entity.setFabricMasterId(dto.getFabricMasterId());
//         entity.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
//         entity.setSizingPlanId(dto.getSizingPlanId());
// //        entity.setSizingBeamOutwardId(dto.getSizingBeamOutwardId());
//         entity.setOrderTypeId(dto.getOrderTypeId());
//         entity.setOutwardDate(dto.getOutwardDate());
//         entity.setFinancialYear(calculateFinancialYear(entity.getOutwardDate()));
//         entity.setFabricPanelWidth(dto.getFabricPanelWidth());
//         entity.setTotalNoOfBeams(dto.getTotalNoOfBeams());
//         entity.setTotalBeamMeters(dto.getTotalBeamMeters());
//         entity.setTotalExpectedMeters(dto.getTotalExpectedMeters());
//         entity.setTotalEnds(dto.getTotalEnds());
//         entity.setTransportId(dto.getTransportId());
//         // Set return properties on outward details instead of main outward
//         if (entity.getOutwardDetails() != null) {
//             for (BeamOutwardDetail detail : entity.getOutwardDetails()) {
//                 detail.setIsReturn(dto.getIsReturn());
//                 detail.setReturnDate(dto.getReturnDate());
//                 detail.setReturnNo(dto.getReturnNo());
//                 detail.setReturnWeaverId(dto.getReturnWeaverId());
//             }
//         }
//         entity.setVehicleNo(dto.getVehicleNo());
//         entity.setRemarks(dto.getRemarks());
//         entity.setIsIssuedToWeaving(dto.getIsIssuedToWeaving());
//         entity.setIsFactoryInward(dto.getIsFactoryInward());
//         syncOutwardDetails(dto.getOutwardDetails(), entity);
//         entity.setCompanyId(dto.getCompanyId());
//         entity.setTradingCompanyId(dto.getTradingCompanyId());
//         Long resolvedFactoryCompanyId = resolveFactoryCompanyId(dto.getFactoryCompanyId(), dto.getWeavingVendorId());
//         entity.setFactoryCompanyId(resolvedFactoryCompanyId);
//         return entity;
//     }

//     private BeamOutwardDTO toDto(BeamOutward entity) {
//         BeamOutwardDTO dto = new BeamOutwardDTO();
//         dto.setId(entity.getId());
//         dto.setBeamInwardId(entity.getBeamInwardId());
//         dto.setBeamInwardNo(entity.getBeamInwardNo());
//         dto.setWeavingContractId(entity.getWeavingContractId());
//         dto.setWeavingVendorId(entity.getWeavingVendorId());
//         dto.setSizingVendorId(entity.getSizingVendorId());
//         dto.setSalesOrderId(entity.getSalesOrderId());
//         dto.setSizedBeamNo(entity.getSizedBeamNo());
//         dto.setFlangeNo(entity.getFlangeNo());
//         dto.setFlangeId(entity.getFlangeId());
//         dto.setOutwardNo(entity.getOutwardNo());
//         dto.setWrapMeters(entity.getWrapMeters());
//         dto.setShrinkage(entity.getShrinkage());
//         dto.setExpectedFabricMeter(entity.getExpectedFabricMeter());
//         dto.setFabricMasterId(entity.getFabricMasterId());
//         dto.setWeavingWorkOrderId(entity.getWeavingWorkOrderId());
//         dto.setSizingPlanId(entity.getSizingPlanId());
//         dto.setOrderTypeId(entity.getOrderTypeId());
//         dto.setOutwardDate(entity.getOutwardDate());
//         dto.setFinancialYear(entity.getFinancialYear());
//         dto.setTotalNoOfBeams(entity.getTotalNoOfBeams());
//         dto.setTotalBeamMeters(entity.getTotalBeamMeters());
//         dto.setTotalExpectedMeters(entity.getTotalExpectedMeters());
//         dto.setTotalEnds(entity.getTotalEnds());
//         dto.setOutwardDetails(entity.getOutwardDetails() == null
//                 ? new ArrayList<>()
//                 : entity.getOutwardDetails().stream().map(this::toDetailDto).collect(Collectors.toList()));
//         dto.setFabricPanelWidth(entity.getFabricPanelWidth());
//         dto.setCompanyId(entity.getCompanyId());
//         dto.setTradingCompanyId(entity.getTradingCompanyId());
//         dto.setFactoryCompanyId(entity.getFactoryCompanyId());
//         dto.setTransportId(entity.getTransportId());
//         // Get return properties from outward details
//         Integer isReturn = null;
//         LocalDate returnDate = null;
//         String returnNo = null;
//         Long returnWeaverId = null;
        
//         if (entity.getOutwardDetails() != null && !entity.getOutwardDetails().isEmpty()) {
//             BeamOutwardDetail firstDetail = entity.getOutwardDetails().get(0);
//             isReturn = firstDetail.getIsReturn();
//             returnDate = firstDetail.getReturnDate();
//             returnNo = firstDetail.getReturnNo();
//             returnWeaverId = firstDetail.getReturnWeaverId();
//         }
        
//         dto.setIsReturn(isReturn);
//         dto.setReturnDate(returnDate);
//         dto.setReturnNo(returnNo);
//         dto.setReturnWeaverId(returnWeaverId);
//         dto.setVehicleNo(entity.getVehicleNo());
//         dto.setRemarks(entity.getRemarks());
//         dto.setIsIssuedToWeaving(entity.getIsIssuedToWeaving());
//         dto.setIsFactoryInward(entity.getIsFactoryInward());
//         return dto;
//     }

//     private void syncOutwardDetails(List<BeamOutwardDetailDTO> detailDtos, BeamOutward entity) {
//         List<BeamOutwardDetail> current = entity.getOutwardDetails();
//         if (current == null) {
//             current = new ArrayList<>();
//             entity.setOutwardDetails(current);
//         } else {
//             current.clear();
//         }
//         if (detailDtos == null || detailDtos.isEmpty()) {
//             return;
//         }
//         for (BeamOutwardDetailDTO dto : detailDtos) {
//             BeamOutwardDetail detail = new BeamOutwardDetail();
//             detail.setBeamOutward(entity);
//             detail.setWeavingContractId(dto.getWeavingContractId());
//             detail.setWeavingVendorId(dto.getWeavingVendorId());
//             detail.setSalesOrderId(dto.getSalesOrderId());
//             detail.setSizedBeamNo(dto.getSizedBeamNo());
//             detail.setFlangeNo(dto.getFlangeNo());
//             detail.setFlangeId(dto.getFlangeId());
//             detail.setWrapMeters(dto.getWrapMeters());
//             detail.setShrinkage(dto.getShrinkage());
//             detail.setExpectedFabricMeter(dto.getExpectedFabricMeter());
//             detail.setFabricPanelWidth(dto.getFabricPanelWidth());
//             detail.setTotalEnds(dto.getTotalEnds());
//             detail.setIsIssuedToWeaving(dto.getIsIssuedToWeaving());
//             detail.setIsFactoryInward(dto.getIsFactoryInward());
//             current.add(detail);
//         }
//     }

//     private BeamOutwardDetailDTO toDetailDto(BeamOutwardDetail detail) {
//         BeamOutwardDetailDTO dto = new BeamOutwardDetailDTO();
//         dto.setId(detail.getId());
//         dto.setBeamOutwardId(detail.getBeamOutward() != null ? detail.getBeamOutward().getId() : null);
//         dto.setWeavingContractId(detail.getWeavingContractId());
//         dto.setWeavingVendorId(detail.getWeavingVendorId());
//         dto.setSalesOrderId(detail.getSalesOrderId());
//         dto.setSizedBeamNo(detail.getSizedBeamNo());
//         dto.setFlangeNo(detail.getFlangeNo());
//         dto.setFlangeId(detail.getFlangeId());
//         dto.setWrapMeters(detail.getWrapMeters());
//         dto.setShrinkage(detail.getShrinkage());
//         dto.setExpectedFabricMeter(detail.getExpectedFabricMeter());
//         dto.setFabricPanelWidth(detail.getFabricPanelWidth());
//         dto.setTotalEnds(detail.getTotalEnds());
//         dto.setIsIssuedToWeaving(detail.getIsIssuedToWeaving());
//         dto.setIsFactoryInward(detail.getIsFactoryInward());
//         dto.setIsReturn(detail.getIsReturn());
//         dto.setReturnDate(detail.getReturnDate());
//         dto.setReturnNo(detail.getReturnNo());
//         dto.setReturnWeaverId(detail.getReturnWeaverId());
//         dto.setIsBeamGated(detail.getIsBeamGated());
//         return dto;
//     }

//     private Long resolveFactoryCompanyId(Long providedFactoryCompanyId, Long weavingVendorId) {
//         if (providedFactoryCompanyId != null) {
//             logFactoryCompanyMatch("payload", resolveCompanyName(providedFactoryCompanyId), providedFactoryCompanyId);
//             return providedFactoryCompanyId;
//         }
//         if (weavingVendorId == null) {
//             logFactoryCompanyMatch(null, null, null);
//             return null;
//         }
//         Vendor vendor = vendorRepository.findById(weavingVendorId).orElse(null);
//         if (vendor == null) {
//             logFactoryCompanyMatch(null, null, null);
//             return null;
//         }
//         String vendorName = vendor.getVendorName();
//         Long matchedCompanyId = findCompanyIdByName(vendorName);
//         logFactoryCompanyMatch(vendorName, resolveCompanyName(matchedCompanyId), matchedCompanyId);
//         return matchedCompanyId;
//     }

//     private Long findCompanyIdByName(String name) {
//         if (name == null) {
//             return null;
//         }
//         String normalizedKey = normalizeNameKey(name);
//         if (normalizedKey == null) {
//             return null;
//         }
//         Company matchedCompany = companyRepository.findFirstByCompanyNameIgnoreCase(name.trim())
//                 .orElse(null);
//         if (matchedCompany != null) {
//             String companyKey = normalizeNameKey(matchedCompany.getCompanyName());
//             if (companyKey != null) {
//                 companyNameLookup.putIfAbsent(companyKey, matchedCompany.getId());
//             }
//             return matchedCompany.getId();
//         }
//         Long cached = companyNameLookup.get(normalizedKey);
//         if (cached != null) {
//             return cached;
//         }
//         refreshCompanyNameLookup();
//         return companyNameLookup.get(normalizedKey);
//     }

//     private void refreshCompanyNameLookup() {
//         companyNameLookup.clear();
//         List<Company> companies = companyRepository.findAll();
//         if (companies == null || companies.isEmpty()) {
//             return;
//         }
//         for (Company company : companies) {
//             String key = normalizeNameKey(company.getCompanyName());
//             if (key != null) {
//                 companyNameLookup.put(key, company.getId());
//             }
//         }
//     }

//     private String resolveCompanyName(Long companyId) {
//         if (companyId == null) {
//             return null;
//         }
//         return companyRepository.findById(companyId)
//                 .map(Company::getCompanyName)
//                 .orElse(null);
//     }

//     private String normalizeNameKey(String value) {
//         return normalizeCompanyName(value);
//     }

//     private String normalizeCompanyName(String value) {
//         if (value == null) {
//             return null;
//         }
//         String canonical = value.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
//         return canonical.isEmpty() ? null : canonical;
//     }

//     private void logFactoryCompanyMatch(String vendorName, String companyName, Long companyId) {
//         if (logger.isInfoEnabled()) {
//             logger.info("BeamOutward factoryCompanyId resolution -> vendorName: {}, companyName: {}, factoryCompanyId: {}",
//                     vendorName, companyName, companyId);
//         }
//     }

//     private String calculateFinancialYear(LocalDate date) {
//         LocalDate reference = date != null ? date : LocalDate.now();
//         int year = reference.getYear();
//         int month = reference.getMonthValue();
//         if (month >= 4) {
//             int nextYear = year + 1;
//             return String.format("%d-%d", year, nextYear % 100);
//         } else {
//             int previousYear = year - 1;
//             return String.format("%d-%d", previousYear, year % 100);
//         }
//     }

//     private boolean isInternalVendor(Long vendorId) {
//         if (vendorId == null) {
//             return false;
//         }
//         return vendorRepository.findById(vendorId)
//                 .map(v -> v.getIsInternalVendor() != null && v.getIsInternalVendor() == 1)
//                 .orElse(false);
//     }

//     public void triggerFactorySyncForOutward(Long beamOutwardId) {
//         syncToFactoryBeamInward(beamOutwardId);
//     }
// }
package com.tech3.erp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.util.PaginationHelper;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.BeamOutwardDTO;
import com.tech3.erp.dto.BeamOutwardDetailDTO;
import com.tech3.erp.entity.BeamOutward;
import com.tech3.erp.entity.BeamOutwardDetail;
import com.tech3.erp.entity.BeamInward;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.FactoryBeamInward;
import com.tech3.erp.entity.FactoryBeamInwardBeamDetails;
import com.tech3.erp.entity.FactoryBeamInwardQualityDetails;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.SizingProductionDetail;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.entity.SalesReturnBeamEntity;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.BeamInwardRepository;
import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.BeamInwardQualityDetailsRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.FactoryBeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.FactoryBeamInwardQualityDetailsRepository;
import com.tech3.erp.repository.FactoryBeamInwardRepository;
import com.tech3.erp.repository.FlangeBeamInwardDetailRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.SizingProductionDetailRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.SalesReturnBeamRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class BeamOutwardService {

    private static final Logger logger = LoggerFactory.getLogger(BeamOutwardService.class);

    private final BeamOutwardRepository repository;
    private final BeamInwardRepository beamInwardRepository;
    private final BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository;
    private final BeamInwardQualityDetailsRepository beamInwardQualityDetailsRepository;
    private final FactoryBeamInwardRepository factoryBeamInwardRepository;
    private final FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository;
    private final FactoryBeamInwardQualityDetailsRepository factoryBeamInwardQualityDetailsRepository;
    private final SizingPlanRepository sizingPlanRepository;
    private final SizingProductionDetailRepository sizingProductionDetailRepository;
    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
    private final VendorRepository vendorRepository;
    private final CompanyRepository companyRepository;
    private final FlangeBeamInwardDetailRepository flangeBeamInwardDetailRepository;
    private final SalesReturnBeamRepository salesReturnBeamRepository;
    private final Map<String, Long> companyNameLookup = new ConcurrentHashMap<>();

    @Lazy
    @Autowired
    private BeamInwardService beamInwardService;

    public BeamOutwardService(
            BeamOutwardRepository repository,
            BeamInwardRepository beamInwardRepository,
            BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository,
            BeamInwardQualityDetailsRepository beamInwardQualityDetailsRepository,
            FactoryBeamInwardRepository factoryBeamInwardRepository,
            FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository,
            FactoryBeamInwardQualityDetailsRepository factoryBeamInwardQualityDetailsRepository,
            SizingPlanRepository sizingPlanRepository,
            SizingProductionDetailRepository sizingProductionDetailRepository,
            WeavingWorkOrderRepository weavingWorkOrderRepository,
            VendorRepository vendorRepository,
            CompanyRepository companyRepository,
            FlangeBeamInwardDetailRepository flangeBeamInwardDetailRepository,
            SalesReturnBeamRepository salesReturnBeamRepository) {
        this.repository = repository;
        this.beamInwardRepository = beamInwardRepository;
        this.beamInwardBeamDetailsRepository = beamInwardBeamDetailsRepository;
        this.beamInwardQualityDetailsRepository = beamInwardQualityDetailsRepository;
        this.factoryBeamInwardRepository = factoryBeamInwardRepository;
        this.factoryBeamInwardBeamDetailsRepository = factoryBeamInwardBeamDetailsRepository;
        this.factoryBeamInwardQualityDetailsRepository = factoryBeamInwardQualityDetailsRepository;
        this.sizingPlanRepository = sizingPlanRepository;
        this.sizingProductionDetailRepository = sizingProductionDetailRepository;
        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
        this.vendorRepository = vendorRepository;
        this.companyRepository = companyRepository;
        this.flangeBeamInwardDetailRepository = flangeBeamInwardDetailRepository;
        this.salesReturnBeamRepository = salesReturnBeamRepository;
    }

    public BeamOutwardDTO create(BeamOutwardDTO dto) {
        BeamOutward entity = toEntity(dto, new BeamOutward());
        BeamOutward saved = repository.save(entity);
        markBeamDetailsOutwarded(saved.getBeamInwardId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getCompanyId());
        if (saved.getOutwardDetails() != null) {
            for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
                markBeamDetailsOutwarded(saved.getBeamInwardId(), d.getSizedBeamNo(), d.getFlangeNo(), saved.getCompanyId());
            }
        }
        updateSizingProductionIssuedStatus(saved.getSizingPlanId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getIsIssuedToWeaving(), saved.getIsFactoryInward());
        if (saved.getOutwardDetails() != null) {
            for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
                updateSizingProductionIssuedStatus(saved.getSizingPlanId(), d.getSizedBeamNo(), d.getFlangeNo(), d.getIsIssuedToWeaving(), d.getIsFactoryInward());
            }
        }
        
        if (!SyncContext.isInProgress()) {
            try {
                SyncContext.setInProgress(true);
                syncToFactoryBeamInward(saved.getId());
                syncToBeamInward(saved.getId());
            } finally {
                SyncContext.clear();
            }
        }
        // beamStockService.syncFromBeamOutward(saved);
        return toDto(saved);
    }

    public List<BeamOutwardDTO> getAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<BeamOutwardDTO> getAllByCompany(Long companyId) {
        return repository.findByCompanyId(companyId).stream()
                .sorted(Comparator.comparing(BeamOutward::getId).reversed())
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BeamOutwardDTO> getAllWithPagination(Long offset, Long limit) {
        long finalLimit = (limit != null && limit > 0) ? limit : PaginationHelper.getDefaultLimit();
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
        Page<BeamOutward> page = repository.findAll(pageRequest);
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    public PaginatedResponse<BeamOutwardDTO> getAllByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        long finalLimit = (limit != null && limit > 0) ? limit : PaginationHelper.getDefaultLimit();
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
        Page<BeamOutward> page = repository.findByCompanyId(companyId, pageRequest);
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    private PaginatedResponse<BeamOutwardDTO> buildPaginatedResponse(Page<BeamOutward> page, long offset, long limit) {
        List<BeamOutwardDTO> content = page.getContent().stream()
                .map(this::toDto)
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

    public BeamOutwardDTO getById(Long id) {
        BeamOutward entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BeamOutward not found with id: " + id));
        return toDto(entity);
    }

    public List<BeamOutwardDTO> getByBeamInwardId(Long beamInwardId) {
        return repository.findByBeamInwardId(beamInwardId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public BeamOutwardDTO getByIdAndCompany(Long companyId, Long id) {
        BeamOutward entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("BeamOutward not found with id: " + id + " for companyId: " + companyId));
        return toDto(entity);
    }

    public BeamOutwardDTO update(Long id, BeamOutwardDTO dto) {
        if (SyncContext.isInProgress()) {
            return doUpdate(id, dto);
        }
        try {
            SyncContext.setInProgress(true);
            BeamOutwardDTO updated = doUpdate(id, dto);
            syncToFactoryBeamInward(id);
            syncToBeamInward(id);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private BeamOutwardDTO doUpdate(Long id, BeamOutwardDTO dto) {
        BeamOutward existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BeamOutward not found with id: " + id));
        List<BeamOutwardDetail> oldDetails = existing.getOutwardDetails() != null ? new ArrayList<>(existing.getOutwardDetails()) : new ArrayList<>();
        Long existingCompanyId = existing.getCompanyId();
        BeamOutward updated = toEntity(dto, existing);
        if (updated.getCompanyId() == null) {
            updated.setCompanyId(existingCompanyId);
        }
        updated.setId(id);
        BeamOutward saved = repository.save(updated);
        revertRemovedBeamsStatus(saved.getBeamInwardId(), oldDetails, dto.getOutwardDetails(), saved.getCompanyId(), saved.getSizingPlanId());
        markBeamDetailsOutwarded(saved.getBeamInwardId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getCompanyId());
        if (saved.getOutwardDetails() != null) {
            for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
                markBeamDetailsOutwarded(saved.getBeamInwardId(), d.getSizedBeamNo(), d.getFlangeNo(), saved.getCompanyId());
            }
        }
        updateSizingProductionIssuedStatus(saved.getSizingPlanId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getIsIssuedToWeaving(), saved.getIsFactoryInward());
        if (saved.getOutwardDetails() != null) {
            for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
                updateSizingProductionIssuedStatus(saved.getSizingPlanId(), d.getSizedBeamNo(), d.getFlangeNo(), d.getIsIssuedToWeaving(), d.getIsFactoryInward());
            }
        }
        // beamStockService.syncFromBeamOutward(saved);
        return toDto(saved);
    }

    public BeamOutwardDTO updateByCompany(Long companyId, Long id, BeamOutwardDTO dto) {
        if (SyncContext.isInProgress()) {
            return doUpdateByCompany(companyId, id, dto);
        }
        try {
            SyncContext.setInProgress(true);
            BeamOutwardDTO updated = doUpdateByCompany(companyId, id, dto);
            syncToFactoryBeamInward(id);
            syncToBeamInward(id);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private BeamOutwardDTO doUpdateByCompany(Long companyId, Long id, BeamOutwardDTO dto) {
        BeamOutward existing = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("BeamOutward not found with id: " + id + " for companyId: " + companyId));
        List<BeamOutwardDetail> oldDetails = existing.getOutwardDetails() != null ? new ArrayList<>(existing.getOutwardDetails()) : new ArrayList<>();
        Long existingCompanyId = existing.getCompanyId();
        BeamOutward updated = toEntity(dto, existing);
        if (updated.getCompanyId() == null) {
            updated.setCompanyId(existingCompanyId);
        }
        updated.setId(id);
        BeamOutward saved = repository.save(updated);
        revertRemovedBeamsStatus(saved.getBeamInwardId(), oldDetails, dto.getOutwardDetails(), saved.getCompanyId(), saved.getSizingPlanId());
        markBeamDetailsOutwarded(saved.getBeamInwardId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getCompanyId());
        if (saved.getOutwardDetails() != null) {
            for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
                markBeamDetailsOutwarded(saved.getBeamInwardId(), d.getSizedBeamNo(), d.getFlangeNo(), saved.getCompanyId());
            }
        }
        updateSizingProductionIssuedStatus(saved.getSizingPlanId(), saved.getSizedBeamNo(), saved.getFlangeNo(), saved.getIsIssuedToWeaving(), saved.getIsFactoryInward());
        if (saved.getOutwardDetails() != null) {
            for (com.tech3.erp.entity.BeamOutwardDetail d : saved.getOutwardDetails()) {
                updateSizingProductionIssuedStatus(saved.getSizingPlanId(), d.getSizedBeamNo(), d.getFlangeNo(), d.getIsIssuedToWeaving(), d.getIsFactoryInward());
            }
        }
        // beamStockService.syncFromBeamOutward(saved);
        return toDto(saved);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("BeamOutward not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public void deleteByCompany(Long companyId, Long id) {
        if (!repository.existsByIdAndCompanyId(id, companyId)) {
            throw new EntityNotFoundException("BeamOutward not found with id: " + id + " for companyId: " + companyId);
        }
        repository.deleteByIdAndCompanyId(id, companyId);
    }

    private void revertRemovedBeamsStatus(Long beamInwardId, List<BeamOutwardDetail> oldDetails, List<BeamOutwardDetailDTO> newDetailDtos, Long companyId, Long sizingPlanId) {
        if (oldDetails == null || oldDetails.isEmpty()) {
            return;
        }
        List<String> newBeamNos = new ArrayList<>();
        if (newDetailDtos != null) {
            for (BeamOutwardDetailDTO d : newDetailDtos) {
                if (d.getSizedBeamNo() != null) {
                    newBeamNos.add(d.getSizedBeamNo());
                }
            }
        }
        for (BeamOutwardDetail oldD : oldDetails) {
            if (oldD.getSizedBeamNo() != null && !newBeamNos.contains(oldD.getSizedBeamNo())) {
                revertBeamDetailsOutwarded(beamInwardId, oldD.getSizedBeamNo(), oldD.getFlangeNo(), companyId, sizingPlanId);
            }
        }
    }

    private void revertBeamDetailsOutwarded(Long beamInwardId, String sizedBeamNo, String flangeNo, Long companyId, Long sizingPlanId) {
        boolean hasSizedBeamNo = sizedBeamNo != null && !sizedBeamNo.isEmpty();
        boolean hasFlangeNo = flangeNo != null && !flangeNo.isEmpty();

        // 1. Revert BeamInwardBeamDetails
        List<com.tech3.erp.entity.BeamInwardBeamDetails> details = new ArrayList<>();
        if (beamInwardId != null) {
            if (hasSizedBeamNo && hasFlangeNo) {
                details = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNoAndFlangeNo(beamInwardId, sizedBeamNo, flangeNo);
            } else if (hasSizedBeamNo) {
                details = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNo(beamInwardId, sizedBeamNo);
            } else {
                details = beamInwardBeamDetailsRepository.findByBeamInwardId(beamInwardId);
            }
        }
        
        if (details.isEmpty() && hasSizedBeamNo) {
            if (hasFlangeNo) {
                List<com.tech3.erp.entity.BeamInwardBeamDetails> allBeams = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(List.of(sizedBeamNo));
                details = allBeams.stream()
                        .filter(d -> flangeNo.equals(d.getFlangeNo()))
                        .collect(Collectors.toList());
            } else {
                details = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(List.of(sizedBeamNo));
            }
        }

        if (details != null && !details.isEmpty()) {
            for (com.tech3.erp.entity.BeamInwardBeamDetails d : details) {
                d.setIsBeamOutwarded(0);
            }
            beamInwardBeamDetailsRepository.saveAll(details);
            logger.info("Reverted {} BeamInwardBeamDetails as NOT outwarded for sizedBeamNo: {}", details.size(), sizedBeamNo);
        }

        // 2. Revert SalesReturnBeamEntity
        if (hasSizedBeamNo && companyId != null) {
            try {
                String baseSizedBeamNo = sizedBeamNo;
                if (sizedBeamNo.endsWith("-R")) {
                    baseSizedBeamNo = sizedBeamNo.substring(0, sizedBeamNo.length() - 2);
                }

                List<SalesReturnBeamEntity> matchingBeams = salesReturnBeamRepository.findMatchingBeams(sizedBeamNo, baseSizedBeamNo, companyId);
                if (hasFlangeNo && matchingBeams != null && !matchingBeams.isEmpty()) {
                    matchingBeams = matchingBeams.stream()
                            .filter(b -> flangeNo.equals(b.getFlangeNo()))
                            .collect(Collectors.toList());
                }

                if (matchingBeams != null && !matchingBeams.isEmpty()) {
                    for (SalesReturnBeamEntity beam : matchingBeams) {
                        beam.setIsOutwarded(0);
                        salesReturnBeamRepository.save(beam);
                        logger.info("Automatically reverted SalesReturnBeamEntity ID {} as NOT outwarded for sizedBeamNo {}", beam.getId(), sizedBeamNo);
                    }
                }
            } catch (Exception e) {
                logger.error("Error reverting SalesReturnBeamEntity status for sizedBeamNo {}", sizedBeamNo, e);
            }
        }

        // 3. Revert SizingProductionDetail
        if (sizingPlanId != null && hasSizedBeamNo) {
            try {
                updateSizingProductionIssuedStatus(sizingPlanId, sizedBeamNo, flangeNo, 0L, 0L);
                logger.info("Reverted SizingProductionDetail issued status for sizingPlanId: {}, sizedBeamNo: {}", sizingPlanId, sizedBeamNo);
            } catch (Exception e) {
                logger.error("Error reverting SizingProductionDetail status for sizedBeamNo {}", sizedBeamNo, e);
            }
        }
    }

    private void markBeamDetailsOutwarded(Long beamInwardId, String sizedBeamNo, String flangeNo, Long companyId) {
        boolean hasSizedBeamNo = sizedBeamNo != null && !sizedBeamNo.isEmpty();
        boolean hasFlangeNo = flangeNo != null && !flangeNo.isEmpty();

        // 1. Update BeamInwardBeamDetails
        List<com.tech3.erp.entity.BeamInwardBeamDetails> details = new ArrayList<>();
        if (beamInwardId != null) {
            if (hasSizedBeamNo && hasFlangeNo) {
                details = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNoAndFlangeNo(beamInwardId, sizedBeamNo, flangeNo);
            } else if (hasSizedBeamNo) {
                details = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNo(beamInwardId, sizedBeamNo);
            } else {
                details = beamInwardBeamDetailsRepository.findByBeamInwardId(beamInwardId);
            }
        }
        
        // If details not found by beamInwardId, or beamInwardId is null, search across any inwards by sizedBeamNo
        if (details.isEmpty() && hasSizedBeamNo) {
            if (hasFlangeNo) {
                List<com.tech3.erp.entity.BeamInwardBeamDetails> allBeams = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(List.of(sizedBeamNo));
                details = allBeams.stream()
                        .filter(d -> flangeNo.equals(d.getFlangeNo()))
                        .collect(Collectors.toList());
            } else {
                details = beamInwardBeamDetailsRepository.findBySizedBeamNoIn(List.of(sizedBeamNo));
            }
        }

        if (details != null && !details.isEmpty()) {
            for (com.tech3.erp.entity.BeamInwardBeamDetails d : details) {
                if (d.getIsBeamOutwarded() != 1) {
                    d.setIsBeamOutwarded(1);
                }
            }
            beamInwardBeamDetailsRepository.saveAll(details);
            logger.info("Marked {} BeamInwardBeamDetails as outwarded for sizedBeamNo: {}", details.size(), sizedBeamNo);
        }

        // 2. Update SalesReturnBeamEntity
        if (hasSizedBeamNo && companyId != null) {
            try {
                String baseSizedBeamNo = sizedBeamNo;
                if (sizedBeamNo.endsWith("-R")) {
                    baseSizedBeamNo = sizedBeamNo.substring(0, sizedBeamNo.length() - 2);
                }

                List<SalesReturnBeamEntity> matchingBeams = salesReturnBeamRepository.findMatchingBeams(sizedBeamNo, baseSizedBeamNo, companyId);
                if (hasFlangeNo && matchingBeams != null && !matchingBeams.isEmpty()) {
                    matchingBeams = matchingBeams.stream()
                            .filter(b -> flangeNo.equals(b.getFlangeNo()))
                            .collect(Collectors.toList());
                }

                if (matchingBeams != null && !matchingBeams.isEmpty()) {
                    for (SalesReturnBeamEntity beam : matchingBeams) {
                        if (beam.getIsOutwarded() != 1) {
                            beam.setIsOutwarded(1);
                            salesReturnBeamRepository.save(beam);
                            logger.info("Automatically marked SalesReturnBeamEntity ID {} as outwarded for sizedBeamNo {}", beam.getId(), sizedBeamNo);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error updating SalesReturnBeamEntity status for sizedBeamNo {}", sizedBeamNo, e);
            }
        }
    }

    private void updateSizingProductionIssuedStatus(Long sizingPlanId, String sizedBeamNo, String flangeNo, Long isIssuedToWeaving, Long isFactoryInward) {
        if (sizingPlanId == null || sizedBeamNo == null) return;
        List<SizingProductionDetail> details;
        if (flangeNo != null && !flangeNo.isEmpty()) {
            details = sizingProductionDetailRepository.findBySizingProductionSizingPlanIdAndSizedBeamNoAndFlangeNo(sizingPlanId, sizedBeamNo, flangeNo);
        } else {
            details = sizingProductionDetailRepository.findBySizingProductionSizingPlanIdAndSizedBeamNo(sizingPlanId, sizedBeamNo);
        }
        if (details != null && !details.isEmpty()) {
            for (SizingProductionDetail d : details) {
                d.setIsIssuedToWeaving(isIssuedToWeaving != null ? isIssuedToWeaving : 0L);
                d.setIsFactoryInward(isFactoryInward != null ? isFactoryInward : 0L);
            }
            sizingProductionDetailRepository.saveAll(details);
        }
    }

    public void syncToFactoryBeamInward(Long beamOutwardId) {
        if (beamOutwardId == null) return;
        BeamOutward saved = repository.findById(beamOutwardId).orElse(null);
        if (saved == null) return;

        Long sizingPlanId = saved.getSizingPlanId();
        if (sizingPlanId == null) return;
        Optional<SizingPlan> optPlan = sizingPlanRepository.findById(sizingPlanId);
        if (optPlan.isEmpty()) return;
        SizingPlan plan = optPlan.get();

        // Load qualities once (shared across all contracts)
        java.util.List<com.tech3.erp.entity.BeamInwardQualityDetails> qualities = null;
        Long totalActualEndsFromQualities = null;
        if (saved.getBeamInwardId() != null) {
            qualities = beamInwardQualityDetailsRepository.findByBeamInwardId(saved.getBeamInwardId());
            if (qualities != null && !qualities.isEmpty()) {
                long sum = 0L;
                boolean hasAny = false;
                for (var q : qualities) {
                    if (q.getActualEnds() != null) {
                        sum += q.getActualEnds();
                        hasAny = true;
                    }
                }
                if (hasAny) totalActualEndsFromQualities = sum;
            }
        }
        final Long finalTotalActualEnds = totalActualEndsFromQualities;
        final java.util.List<com.tech3.erp.entity.BeamInwardQualityDetails> finalQualities = qualities;

        // Group beams by weavingContractId — separate FactoryBeamInward per contract
        // Only sync if the weaver is an internal vendor
        Map<String, List<BeamOutwardDetail>> beamsByContract = new java.util.LinkedHashMap<>();
        List<BeamOutwardDetail> outwardDetails = saved.getOutwardDetails();
        if (outwardDetails == null || outwardDetails.isEmpty()) {
            if (isInternalVendor(saved.getWeavingVendorId())) {
                String contractKey = saved.getWeavingContractId() != null ? String.valueOf(saved.getWeavingContractId()) : "";
                beamsByContract.computeIfAbsent(contractKey, k -> new ArrayList<>()).add(null);
            }
        } else {
            for (BeamOutwardDetail d : outwardDetails) {
                Long vId = d.getWeavingVendorId() != null ? d.getWeavingVendorId() : saved.getWeavingVendorId();
                if (isInternalVendor(vId)) {
                    String contractKey = d.getWeavingContractId() != null ? String.valueOf(d.getWeavingContractId()) : "";
                    beamsByContract.computeIfAbsent(contractKey, k -> new ArrayList<>()).add(d);
                }
            }
        }

        // Delete FactoryBeamInward entries for contracts no longer present
        List<FactoryBeamInward> existingFactoryInwards = factoryBeamInwardRepository.findByBeamOutwardId(saved.getId());
        if (existingFactoryInwards != null) {
            existingFactoryInwards.stream()
                    .filter(fi -> {
                        String fKey = fi.getWeavingContractId() != null ? fi.getWeavingContractId() : "";
                        return !beamsByContract.containsKey(fKey);
                    })
                    .forEach(factoryBeamInwardRepository::delete);
        }

        for (Map.Entry<String, List<BeamOutwardDetail>> contractEntry : beamsByContract.entrySet()) {
            String contractIdStr = contractEntry.getKey();
            List<BeamOutwardDetail> contractBeams = contractEntry.getValue();

            FactoryBeamInward f = (!contractIdStr.isEmpty()
                    ? factoryBeamInwardRepository.findFirstByBeamOutwardIdAndWeavingContractId(saved.getId(), contractIdStr)
                    : factoryBeamInwardRepository.findFirstByBeamOutwardId(saved.getId()))
                    .orElse(new FactoryBeamInward());

            f.setSizingVendorId(plan.getVendorId());
            f.setSizingPlanId(saved.getSizingPlanId());
            String setNo = null;
            if (saved.getBeamInwardId() != null) {
                setNo = beamInwardRepository.findById(saved.getBeamInwardId())
                        .map(BeamInward::getSizingSetNo)
                        .orElse(null);
            }
            f.setSizingSetNo((setNo != null && !setNo.isBlank()) ? setNo : plan.getSizingPlanNo());
            f.setOrderTypeId(1L);

            f.setWeavingContractId(!contractIdStr.isEmpty() ? contractIdStr : null);
            f.setTradingCompanyId(saved.getTradingCompanyId());

            Long groupWeavingVendorId = null;
            if (contractBeams != null && !contractBeams.isEmpty()) {
                BeamOutwardDetail firstDetail = contractBeams.get(0);
                groupWeavingVendorId = (firstDetail != null && firstDetail.getWeavingVendorId() != null)
                        ? firstDetail.getWeavingVendorId()
                        : saved.getWeavingVendorId();
            } else {
                groupWeavingVendorId = saved.getWeavingVendorId();
            }
            f.setFactoryCompanyId(resolveFactoryCompanyId(saved.getFactoryCompanyId(), groupWeavingVendorId));
            Long resolvedWorkOrderId = !contractIdStr.isEmpty()
                    ? weavingWorkOrderRepository.findFirstByWeavingContractIdOrderByIdDesc(Long.parseLong(contractIdStr))
                            .map(WeavingWorkOrder::getId)
                            .orElse(saved.getWeavingWorkOrderId())
                    : saved.getWeavingWorkOrderId();
            f.setWeavingWorkOrderId(resolvedWorkOrderId);
            f.setBeamInwardDate(saved.getOutwardDate() != null ? saved.getOutwardDate() : LocalDate.now());
            f.setBeamInwardNo(resolveBeamInwardNo(saved.getBeamInwardId()));
            f.setBeamInwardId(saved.getBeamInwardId());
            f.setBeamOutwardId(saved.getId());
            f.setIsBeamPending(1);

            FactoryBeamInward savedHeader = factoryBeamInwardRepository.save(f);

            // Sync Beam Details
            factoryBeamInwardBeamDetailsRepository.deleteByBeamInwardId(savedHeader.getId());
            for (BeamOutwardDetail d : contractBeams) {
                FactoryBeamInwardBeamDetails b = new FactoryBeamInwardBeamDetails();
                b.setBeamInward(savedHeader);
                if (d == null) {
                    b.setSizedBeamNo(saved.getSizedBeamNo());
                    b.setFlangeId(saved.getFlangeId());
                    b.setFlangeNo(saved.getFlangeNo());
                    b.setWrapMeters(saved.getWrapMeters());
                    b.setShrinkage(saved.getShrinkage());
                    b.setExpectedFabricMeter(saved.getExpectedFabricMeter());
                    b.setFabricPanelWidth(saved.getFabricPanelWidth());
                } else {
                    b.setSizedBeamNo(d.getSizedBeamNo());
                    b.setFlangeId(d.getFlangeId());
                    b.setFlangeNo(d.getFlangeNo());
                    b.setWrapMeters(d.getWrapMeters());
                    b.setShrinkage(d.getShrinkage());
                    b.setExpectedFabricMeter(d.getExpectedFabricMeter());
                    b.setFabricPanelWidth(d.getFabricPanelWidth());
                }
                b.setActualEnds(finalTotalActualEnds);
                if (saved.getBeamInwardId() != null && b.getSizedBeamNo() != null) {
                    List<com.tech3.erp.entity.BeamInwardBeamDetails> bibdList = beamInwardBeamDetailsRepository.findByBeamInwardIdAndSizedBeamNo(saved.getBeamInwardId(), b.getSizedBeamNo());
                    if (bibdList != null && !bibdList.isEmpty()) {
                        com.tech3.erp.entity.BeamInwardBeamDetails bibd = bibdList.get(0);
                        b.setWarpCount(bibd.getWarpCount());
                        b.setWarpYarnMillName(bibd.getWarpYarnMillName());
                        b.setWarpYarnLotNo(bibd.getWarpYarnLotNo());
                    }
                }
                factoryBeamInwardBeamDetailsRepository.save(b);
            }

            // Sync Quality Details
            factoryBeamInwardQualityDetailsRepository.deleteByBeamInwardId(savedHeader.getId());
            if (finalQualities != null && !finalQualities.isEmpty()) {
                for (var q : finalQualities) {
                    FactoryBeamInwardQualityDetails fq = new FactoryBeamInwardQualityDetails();
                    fq.setBeamInward(savedHeader);
                    fq.setQuality(q.getQuality());
                    fq.setYarnId(q.getYarnId());
                    fq.setSordEnds(q.getSordEnds());
                    fq.setActualEnds(q.getActualEnds());
                    fq.setParts(q.getParts());
                    fq.setEndsPerPart(q.getEndsPerPart());
                    fq.setWrapMeters(q.getWrapMeters());
                    fq.setFabricCode(q.getFabricCode());
                    fq.setActualYarn(q.getActualYarn());
                    fq.setWarpEnds(q.getWarpEnds());
                    fq.setActualWarpEnds(q.getActualWarpEnds());
                    factoryBeamInwardQualityDetailsRepository.save(fq);
                }
            }
        }
    }

    public void syncToBeamInward(Long beamOutwardId) {
        if (beamOutwardId == null) return;
        BeamOutward saved = repository.findById(beamOutwardId).orElse(null);
        if (saved == null || saved.getBeamInwardId() == null) return;
        
        try {
            List<com.tech3.erp.entity.BeamInwardBeamDetails> dbDetails = beamInwardBeamDetailsRepository.findByBeamInwardId(saved.getBeamInwardId());
            if (dbDetails != null && !dbDetails.isEmpty()) {
                boolean changed = false;
                for (com.tech3.erp.entity.BeamInwardBeamDetails d : dbDetails) {
                    if (saved.getSizedBeamNo() != null && saved.getSizedBeamNo().equals(d.getSizedBeamNo())) {
                        d.setWrapMeters(saved.getWrapMeters());
                        d.setShrinkage(saved.getShrinkage());
                        d.setExpectedFabricMeter(saved.getExpectedFabricMeter());
                        d.setIsBeamOutwarded(1);
                        if (saved.getWeavingVendorId() != null) d.setWeavingVendorId(saved.getWeavingVendorId());
                        if (saved.getWeavingContractId() != null) d.setWeavingContractId(saved.getWeavingContractId());
                        if (saved.getSalesOrderId() != null) d.setSalesOrderId(saved.getSalesOrderId());
                        if (saved.getFlangeId() != null) d.setFlangeId(saved.getFlangeId());
                        if (saved.getFlangeNo() != null) d.setFlangeNo(saved.getFlangeNo());
                        changed = true;
                    }
                }
                if (saved.getOutwardDetails() != null) {
                    for (com.tech3.erp.entity.BeamOutwardDetail od : saved.getOutwardDetails()) {
                        for (com.tech3.erp.entity.BeamInwardBeamDetails d : dbDetails) {
                            if (od.getSizedBeamNo() != null && od.getSizedBeamNo().equals(d.getSizedBeamNo())) {
                                d.setWrapMeters(od.getWrapMeters());
                                d.setShrinkage(od.getShrinkage());
                                d.setExpectedFabricMeter(od.getExpectedFabricMeter());
                                d.setIsBeamOutwarded(1);
                                if (od.getWeavingVendorId() != null) d.setWeavingVendorId(od.getWeavingVendorId());
                                if (od.getWeavingContractId() != null) d.setWeavingContractId(od.getWeavingContractId());
                                if (od.getSalesOrderId() != null) d.setSalesOrderId(od.getSalesOrderId());
                                if (od.getFlangeId() != null) d.setFlangeId(od.getFlangeId());
                                if (od.getFlangeNo() != null) d.setFlangeNo(od.getFlangeNo());
                                changed = true;
                            }
                        }
                    }
                }
                if (changed) {
                    beamInwardBeamDetailsRepository.saveAll(dbDetails);
                }
            }
        } catch (Exception e) {
            logger.error("Error syncing to BeamInward from BeamOutward: " + e.getMessage());
        }
    }

    private String resolveBeamInwardNo(Long beamInwardId) {
        if (beamInwardId == null) {
            return null;
        }
        return beamInwardRepository.findById(beamInwardId)
                .map(BeamInward::getBeamInwardNo)
                .orElse(null);
    }

    private BeamOutward toEntity(BeamOutwardDTO dto, BeamOutward entity) {
        entity.setBeamInwardId(dto.getBeamInwardId());
        entity.setBeamInwardNo(dto.getBeamInwardNo());
        entity.setWeavingContractId(dto.getWeavingContractId());
        entity.setWeavingVendorId(dto.getWeavingVendorId());
        entity.setSizingVendorId(dto.getSizingVendorId());
        entity.setSalesOrderId(dto.getSalesOrderId());
        entity.setSizedBeamNo(dto.getSizedBeamNo());
        entity.setFlangeNo(dto.getFlangeNo());
        entity.setFlangeId(dto.getFlangeId());
        entity.setOutwardNo(dto.getOutwardNo());
        entity.setWrapMeters(dto.getWrapMeters());
        entity.setShrinkage(dto.getShrinkage());
        entity.setExpectedFabricMeter(dto.getExpectedFabricMeter());
        entity.setFabricMasterId(dto.getFabricMasterId());
        entity.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
        entity.setSizingPlanId(dto.getSizingPlanId());
//        entity.setSizingBeamOutwardId(dto.getSizingBeamOutwardId());
        entity.setOrderTypeId(dto.getOrderTypeId());
        entity.setOutwardDate(dto.getOutwardDate());
        entity.setFinancialYear(calculateFinancialYear(entity.getOutwardDate()));
        entity.setFabricPanelWidth(dto.getFabricPanelWidth());
        entity.setTotalNoOfBeams(dto.getTotalNoOfBeams());
        entity.setTotalBeamMeters(dto.getTotalBeamMeters());
        entity.setTotalExpectedMeters(dto.getTotalExpectedMeters());
        entity.setTotalEnds(dto.getTotalEnds());
        entity.setTransportId(dto.getTransportId());
        // Set return properties on outward details instead of main outward
        if (entity.getOutwardDetails() != null) {
            for (BeamOutwardDetail detail : entity.getOutwardDetails()) {
                detail.setIsReturn(dto.getIsReturn());
                detail.setReturnDate(dto.getReturnDate());
                detail.setReturnNo(dto.getReturnNo());
                detail.setReturnWeaverId(dto.getReturnWeaverId());
            }
        }
        entity.setVehicleNo(dto.getVehicleNo());
        entity.setRemarks(dto.getRemarks());
        entity.setIsIssuedToWeaving(dto.getIsIssuedToWeaving());
        entity.setIsFactoryInward(dto.getIsFactoryInward());
        syncOutwardDetails(dto.getOutwardDetails(), entity);
        entity.setCompanyId(dto.getCompanyId());
        entity.setTradingCompanyId(dto.getTradingCompanyId());
        Long resolvedFactoryCompanyId = resolveFactoryCompanyId(dto.getFactoryCompanyId(), dto.getWeavingVendorId());
        entity.setFactoryCompanyId(resolvedFactoryCompanyId);
        return entity;
    }

    private BeamOutwardDTO toDto(BeamOutward entity) {
        BeamOutwardDTO dto = new BeamOutwardDTO();
        dto.setId(entity.getId());
        dto.setBeamInwardId(entity.getBeamInwardId());
        dto.setBeamInwardNo(entity.getBeamInwardNo());
        dto.setWeavingContractId(entity.getWeavingContractId());
        dto.setWeavingVendorId(entity.getWeavingVendorId());
        dto.setSizingVendorId(entity.getSizingVendorId());
        dto.setSalesOrderId(entity.getSalesOrderId());
        dto.setSizedBeamNo(entity.getSizedBeamNo());
        dto.setFlangeNo(entity.getFlangeNo());
        dto.setFlangeId(entity.getFlangeId());
        dto.setOutwardNo(entity.getOutwardNo());
        dto.setWrapMeters(entity.getWrapMeters());
        dto.setShrinkage(entity.getShrinkage());
        dto.setExpectedFabricMeter(entity.getExpectedFabricMeter());
        dto.setFabricMasterId(entity.getFabricMasterId());
        dto.setWeavingWorkOrderId(entity.getWeavingWorkOrderId());
        dto.setSizingPlanId(entity.getSizingPlanId());
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setOutwardDate(entity.getOutwardDate());
        dto.setFinancialYear(entity.getFinancialYear());
        dto.setTotalNoOfBeams(entity.getTotalNoOfBeams());
        dto.setTotalBeamMeters(entity.getTotalBeamMeters());
        dto.setTotalExpectedMeters(entity.getTotalExpectedMeters());
        dto.setTotalEnds(entity.getTotalEnds());
        dto.setOutwardDetails(entity.getOutwardDetails() == null
                ? new ArrayList<>()
                : entity.getOutwardDetails().stream().map(this::toDetailDto).collect(Collectors.toList()));
        dto.setFabricPanelWidth(entity.getFabricPanelWidth());
        dto.setCompanyId(entity.getCompanyId());
        dto.setTradingCompanyId(entity.getTradingCompanyId());
        dto.setFactoryCompanyId(entity.getFactoryCompanyId());
        dto.setTransportId(entity.getTransportId());
        // Get return properties from outward details
        Integer isReturn = null;
        LocalDate returnDate = null;
        String returnNo = null;
        Long returnWeaverId = null;
        
        if (entity.getOutwardDetails() != null && !entity.getOutwardDetails().isEmpty()) {
            BeamOutwardDetail firstDetail = entity.getOutwardDetails().get(0);
            isReturn = firstDetail.getIsReturn();
            returnDate = firstDetail.getReturnDate();
            returnNo = firstDetail.getReturnNo();
            returnWeaverId = firstDetail.getReturnWeaverId();
        }
        
        dto.setIsReturn(isReturn);
        dto.setReturnDate(returnDate);
        dto.setReturnNo(returnNo);
        dto.setReturnWeaverId(returnWeaverId);
        dto.setVehicleNo(entity.getVehicleNo());
        dto.setRemarks(entity.getRemarks());
        dto.setIsIssuedToWeaving(entity.getIsIssuedToWeaving());
        dto.setIsFactoryInward(entity.getIsFactoryInward());
        return dto;
    }

    private void syncOutwardDetails(List<BeamOutwardDetailDTO> detailDtos, BeamOutward entity) {
        List<BeamOutwardDetail> current = entity.getOutwardDetails();
        if (current == null) {
            current = new ArrayList<>();
            entity.setOutwardDetails(current);
        } else {
            current.clear();
        }
        if (detailDtos == null || detailDtos.isEmpty()) {
            return;
        }
        for (BeamOutwardDetailDTO dto : detailDtos) {
            BeamOutwardDetail detail = new BeamOutwardDetail();
            detail.setBeamOutward(entity);
            detail.setWeavingContractId(dto.getWeavingContractId());
            detail.setWeavingVendorId(dto.getWeavingVendorId());
            detail.setSalesOrderId(dto.getSalesOrderId());
            detail.setSizedBeamNo(dto.getSizedBeamNo());
            detail.setFlangeNo(dto.getFlangeNo());
            detail.setFlangeId(dto.getFlangeId());
            detail.setWrapMeters(dto.getWrapMeters());
            detail.setShrinkage(dto.getShrinkage());
            detail.setExpectedFabricMeter(dto.getExpectedFabricMeter());
            detail.setFabricPanelWidth(dto.getFabricPanelWidth());
            detail.setTotalEnds(dto.getTotalEnds());
            detail.setIsIssuedToWeaving(dto.getIsIssuedToWeaving());
            detail.setIsFactoryInward(dto.getIsFactoryInward());
            current.add(detail);
        }
    }

    private BeamOutwardDetailDTO toDetailDto(BeamOutwardDetail detail) {
        BeamOutwardDetailDTO dto = new BeamOutwardDetailDTO();
        dto.setId(detail.getId());
        dto.setBeamOutwardId(detail.getBeamOutward() != null ? detail.getBeamOutward().getId() : null);
        dto.setWeavingContractId(detail.getWeavingContractId());
        dto.setWeavingVendorId(detail.getWeavingVendorId());
        dto.setSalesOrderId(detail.getSalesOrderId());
        dto.setSizedBeamNo(detail.getSizedBeamNo());
        dto.setFlangeNo(detail.getFlangeNo());
        dto.setFlangeId(detail.getFlangeId());
        dto.setWrapMeters(detail.getWrapMeters());
        dto.setShrinkage(detail.getShrinkage());
        dto.setExpectedFabricMeter(detail.getExpectedFabricMeter());
        dto.setFabricPanelWidth(detail.getFabricPanelWidth());
        dto.setTotalEnds(detail.getTotalEnds());
        dto.setIsIssuedToWeaving(detail.getIsIssuedToWeaving());
        dto.setIsFactoryInward(detail.getIsFactoryInward());
        dto.setIsReturn(detail.getIsReturn());
        dto.setReturnDate(detail.getReturnDate());
        dto.setReturnNo(detail.getReturnNo());
        dto.setReturnWeaverId(detail.getReturnWeaverId());
        dto.setIsBeamGated(detail.getIsBeamGated());
        return dto;
    }

    private Long resolveFactoryCompanyId(Long providedFactoryCompanyId, Long weavingVendorId) {
        if (providedFactoryCompanyId != null) {
            logFactoryCompanyMatch("payload", resolveCompanyName(providedFactoryCompanyId), providedFactoryCompanyId);
            return providedFactoryCompanyId;
        }
        if (weavingVendorId == null) {
            logFactoryCompanyMatch(null, null, null);
            return null;
        }
        Vendor vendor = vendorRepository.findById(weavingVendorId).orElse(null);
        if (vendor == null) {
            logFactoryCompanyMatch(null, null, null);
            return null;
        }
        String vendorName = vendor.getVendorName();
        Long matchedCompanyId = findCompanyIdByName(vendorName);
        logFactoryCompanyMatch(vendorName, resolveCompanyName(matchedCompanyId), matchedCompanyId);
        return matchedCompanyId;
    }

    private Long findCompanyIdByName(String name) {
        if (name == null) {
            return null;
        }
        String normalizedKey = normalizeNameKey(name);
        if (normalizedKey == null) {
            return null;
        }
        Company matchedCompany = companyRepository.findFirstByCompanyNameIgnoreCase(name.trim())
                .orElse(null);
        if (matchedCompany != null) {
            String companyKey = normalizeNameKey(matchedCompany.getCompanyName());
            if (companyKey != null) {
                companyNameLookup.putIfAbsent(companyKey, matchedCompany.getId());
            }
            return matchedCompany.getId();
        }
        Long cached = companyNameLookup.get(normalizedKey);
        if (cached != null) {
            return cached;
        }
        refreshCompanyNameLookup();
        return companyNameLookup.get(normalizedKey);
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

    private String resolveCompanyName(Long companyId) {
        if (companyId == null) {
            return null;
        }
        return companyRepository.findById(companyId)
                .map(Company::getCompanyName)
                .orElse(null);
    }

    private String normalizeNameKey(String value) {
        return normalizeCompanyName(value);
    }

    private String normalizeCompanyName(String value) {
        if (value == null) {
            return null;
        }
        String canonical = value.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
        return canonical.isEmpty() ? null : canonical;
    }

    private void logFactoryCompanyMatch(String vendorName, String companyName, Long companyId) {
        if (logger.isInfoEnabled()) {
            logger.info("BeamOutward factoryCompanyId resolution -> vendorName: {}, companyName: {}, factoryCompanyId: {}",
                    vendorName, companyName, companyId);
        }
    }

    private String calculateFinancialYear(LocalDate date) {
        LocalDate reference = date != null ? date : LocalDate.now();
        int year = reference.getYear();
        int month = reference.getMonthValue();
        if (month >= 4) {
            int nextYear = year + 1;
            return String.format("%d-%d", year, nextYear % 100);
        } else {
            int previousYear = year - 1;
            return String.format("%d-%d", previousYear, year % 100);
        }
    }

    private boolean isInternalVendor(Long vendorId) {
        if (vendorId == null) {
            return false;
        }
        return vendorRepository.findById(vendorId)
                .map(v -> v.getIsInternalVendor() != null && v.getIsInternalVendor() == 1)
                .orElse(false);
    }

    public java.util.Map<String, Object> checkBeamReturnStatus(Long beamOutwardId, String sizedBeamNo) {
        java.util.Map<String, Object> result = new java.util.HashMap<>();
        result.put("isReturned", false);
        result.put("salesReturnNo", "");

        if (beamOutwardId == null || sizedBeamNo == null || sizedBeamNo.isEmpty()) {
            return result;
        }

        List<SalesReturnBeamEntity> matching = salesReturnBeamRepository.findByBeamOutwardId(beamOutwardId);
        if (matching != null && !matching.isEmpty()) {
            for (SalesReturnBeamEntity srb : matching) {
                if (sizedBeamNo.equals(srb.getSizedBeamNo())) {
                    result.put("isReturned", true);
                    if (srb.getSalesReturn() != null) {
                        result.put("salesReturnNo", srb.getSalesReturn().getSalesReturnNo());
                    }
                    break;
                }
            }
        }
        return result;
    }

    public void triggerFactorySyncForOutward(Long beamOutwardId) {
        syncToFactoryBeamInward(beamOutwardId);
    }
}
