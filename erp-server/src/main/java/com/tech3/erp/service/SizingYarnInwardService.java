package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingYarnInwardDTO;
import com.tech3.erp.dto.SizingYarnInwardDetailDTO;
import com.tech3.erp.dto.SizingYarnIssueDTO;
import com.tech3.erp.dto.SizingYarnIssueEntryDTO;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.SizingJobWorkOrder;
import com.tech3.erp.entity.SizingYarnInward;
import com.tech3.erp.entity.SizingYarnInwardDetail;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.SizingJobWorkOrderRepository;
import com.tech3.erp.repository.SizingYarnInwardRepository;
import com.tech3.erp.repository.SizingYarnOutwardDetailRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.tech3.erp.service.NotificationService;

@Service
public class SizingYarnInwardService {

    private final SizingYarnInwardRepository sizingYarnInwardRepository;
    private final SizingYarnOutwardDetailRepository sizingYarnOutwardDetailRepository;
    private final SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    private final ReferenceValidationService referenceValidationService;
    private final YarnInventoryRepository yarnInventoryRepository;
    private final YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    private final YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;
    private final CodesDetailRepository codesDetailRepository;
    private final CompanyRepository companyRepository;
    private final NotificationService notificationService;

    @Lazy
    @Autowired
    private SizingYarnIssueEntryService sizingYarnIssueEntryService;

    public SizingYarnInwardService(SizingYarnInwardRepository sizingYarnInwardRepository,
                                   SizingYarnOutwardDetailRepository sizingYarnOutwardDetailRepository,
                                   SizingJobWorkOrderRepository sizingJobWorkOrderRepository,
                                   ReferenceValidationService referenceValidationService,
                                   YarnInventoryRepository yarnInventoryRepository,
                                   YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository,
                                   YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository,
                                   CodesDetailRepository codesDetailRepository,
                                   CompanyRepository companyRepository,
                                   NotificationService notificationService) {
        this.sizingYarnInwardRepository = sizingYarnInwardRepository;
        this.sizingYarnOutwardDetailRepository = sizingYarnOutwardDetailRepository;
        this.sizingJobWorkOrderRepository = sizingJobWorkOrderRepository;
        this.referenceValidationService = referenceValidationService;
        this.yarnInventoryRepository = yarnInventoryRepository;
        this.yarnInwardStockDetailsRepository = yarnInwardStockDetailsRepository;
        this.yarnOutwardStockDetailsRepository = yarnOutwardStockDetailsRepository;
        this.codesDetailRepository = codesDetailRepository;
        this.companyRepository = companyRepository;
        this.notificationService = notificationService;
    }

    @Transactional
    public SizingYarnInwardDTO createSizingYarnInward(SizingYarnInwardDTO dto) {
        validateSizingReferences(dto);
        SizingYarnInward inward = mapDtoToEntity(dto, new SizingYarnInward());
        SizingYarnInward saved = sizingYarnInwardRepository.save(inward);
        saved = ensureInwardNo(saved);
        createYarnInventoryFromInward(saved);
        if (saved.getFactoryCompanyId() != null && saved.getTradingCompanyId() != null) {
            try {
                notificationService.createAndPush(
                    saved.getFactoryCompanyId(),
                    saved.getTradingCompanyId(),
                    NotificationService.EVENT_SIZING_YARN_INWARD,
                    saved.getId(),
                    null, null
                );
            } catch (Exception ignored) {}
        }
        return mapEntityToDto(saved);
    }

    @Transactional
    public SizingYarnInwardDTO updateSizingYarnInward(Long id, SizingYarnInwardDTO dto) {
        if (SyncContext.isInProgress()) {
            return doUpdateSizingYarnInward(id, dto);
        }
        try {
            SyncContext.setInProgress(true);
            SizingYarnInwardDTO updated = doUpdateSizingYarnInward(id, dto);
            syncToIssueEntry(updated);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private SizingYarnInwardDTO doUpdateSizingYarnInward(Long id, SizingYarnInwardDTO dto) {
        SizingYarnInward existing = sizingYarnInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SizingYarnInward not found with id: " + id));
        validateSizingReferences(dto);
        mapDtoToEntity(dto, existing);
        SizingYarnInward saved = sizingYarnInwardRepository.save(existing);
        saved = ensureInwardNo(saved);
        createYarnInventoryFromInward(saved);
        return mapEntityToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SizingYarnInwardDTO> getAllSizingYarnInwards() {
        return sizingYarnInwardRepository.findAll().stream()
                .sorted(Comparator.comparing(SizingYarnInward::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SizingYarnInwardDTO> getAllSizingYarnInwardsWithPagination(Long offset, Long limit) {
        List<SizingYarnInwardDTO> all = getAllSizingYarnInwards();
        return PaginationHelper.paginate(all, offset, limit);
    }

    @Transactional(readOnly = true)
    public List<SizingYarnInwardDTO> getSizingYarnInwardsByFactory(Long factoryCompanyId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "inwardDate").and(Sort.by(Sort.Direction.DESC, "id"));
        return sizingYarnInwardRepository.findByFactoryCompanyId(factoryCompanyId, sort).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SizingYarnInwardDTO> getSizingYarnInwardsByFactoryWithPagination(Long factoryCompanyId, Long offset, Long limit) {
        List<SizingYarnInwardDTO> all = getSizingYarnInwardsByFactory(factoryCompanyId);
        return PaginationHelper.paginate(all, offset, limit);
    }

    @Transactional(readOnly = true)
    public SizingYarnInwardDTO getSizingYarnInwardById(Long id) {
        return sizingYarnInwardRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("SizingYarnInward not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public SizingYarnInwardDTO getSizingYarnInwardByFactory(Long factoryCompanyId, Long id) {
        return sizingYarnInwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("SizingYarnInward not found with id: " + id));
    }

    @Transactional
    public void deleteSizingYarnInward(Long id) {
        SizingYarnInward existing = sizingYarnInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SizingYarnInward not found with id: " + id));
        deleteSizingYarnInwardEntity(existing);
    }

    @Transactional
    public void deleteSizingYarnInwardByFactory(Long factoryCompanyId, Long id) {
        SizingYarnInward existing = sizingYarnInwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("SizingYarnInward not found with id: " + id));
        deleteSizingYarnInwardEntity(existing);
    }

    @Transactional
    public SizingYarnInwardDTO updateSizingYarnInwardByFactory(Long factoryCompanyId, Long id, SizingYarnInwardDTO dto) {
        if (SyncContext.isInProgress()) {
            return doUpdateSizingYarnInwardByFactory(factoryCompanyId, id, dto);
        }
        try {
            SyncContext.setInProgress(true);
            SizingYarnInwardDTO updated = doUpdateSizingYarnInwardByFactory(factoryCompanyId, id, dto);
            syncToIssueEntry(updated);
            return updated;
        } finally {
            SyncContext.clear();
        }
    }

    private SizingYarnInwardDTO doUpdateSizingYarnInwardByFactory(Long factoryCompanyId, Long id, SizingYarnInwardDTO dto) {
        SizingYarnInward existing = sizingYarnInwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("SizingYarnInward not found with id: " + id));
        dto.setFactoryCompanyId(factoryCompanyId);
        validateSizingReferences(dto);
        mapDtoToEntity(dto, existing);
        existing.setFactoryCompanyId(factoryCompanyId);
        SizingYarnInward saved = sizingYarnInwardRepository.save(existing);
        saved = ensureInwardNo(saved);
        createYarnInventoryFromInward(saved);
        return mapEntityToDto(saved);
    }

    private void syncToIssueEntry(SizingYarnInwardDTO inwardDTO) {
        if (inwardDTO == null || !StringUtils.hasText(inwardDTO.getInwardNo())) {
            return;
        }

        // Match pattern: SZYI-{financialYear}-{issueEntryId}
        Pattern pattern = Pattern.compile("SZYI-.*-(\\d+)");
        Matcher matcher = pattern.matcher(inwardDTO.getInwardNo());
        
        if (matcher.find()) {
            Long issueEntryId = Long.parseLong(matcher.group(1));
            try {
                SizingYarnIssueEntryDTO issueDTO = sizingYarnIssueEntryService.getSizingYarnIssueEntryById(issueEntryId);
                
                // Map inward data to issue data
                issueDTO.setSizingYarnIssueDate(inwardDTO.getInwardDate());
                
                if (inwardDTO.getYarnDetails() != null) {
                    List<SizingYarnIssueDTO> updatedIssues = inwardDTO.getYarnDetails().stream()
                        .map(inwardDetail -> {
                            // Find corresponding issue detail by inwardItemId or yarnId+lotNo
                            SizingYarnIssueDTO existingIssue = issueDTO.getSizingYarnIssue().stream()
                                .filter(i -> {
                                    boolean orderItemMatch = i.getOrderItemId() != null && i.getOrderItemId().equals(inwardDetail.getId());
                                    if (orderItemMatch) return true;
                                    
                                    Long currentActualId = i.getActualItemId() != null ? i.getActualItemId() : i.getYarnId();
                                    return currentActualId != null && 
                                           currentActualId.equals(inwardDetail.getYarnId()) && 
                                           ((i.getLotNo() == null && inwardDetail.getLotNo() == null) || 
                                            (i.getLotNo() != null && i.getLotNo().equals(inwardDetail.getLotNo())));
                                })
                                .findFirst()
                                .orElse(null);
                            
                            SizingYarnIssueDTO targetIssue = existingIssue != null ? existingIssue : new SizingYarnIssueDTO();
                            
                            targetIssue.setYarnName(inwardDetail.getYarnName());
                            targetIssue.setYarnId(inwardDetail.getYarnId());
                            targetIssue.setNoOfBags(inwardDetail.getNoOfBags());
                            targetIssue.setKgsPerBag(inwardDetail.getKgsPerBag());
                            targetIssue.setConesPerBag(inwardDetail.getConesPerBag());
                            targetIssue.setConesWeight(inwardDetail.getConesWeight());
                            targetIssue.setWarehouseId(inwardDetail.getWarehouseId());
                            targetIssue.setMillId(inwardDetail.getMillId());
                            targetIssue.setIssueQty(inwardDetail.getIssueQty());
                            targetIssue.setLotNo(inwardDetail.getLotNo());
                            targetIssue.setOrderItemId(inwardDetail.getId());
                            
                            return targetIssue;
                        })
                        .collect(Collectors.toList());
                    
                    issueDTO.setSizingYarnIssue(updatedIssues);

                    // Update requirements' actualYarnId so sizing_quality_details gets updated via SizingYarnIssueEntryService
                    if (issueDTO.getRequirements() != null) {
                        for (SizingYarnInwardDetailDTO detail : inwardDTO.getYarnDetails()) {
                            issueDTO.getRequirements().stream()
                                .filter(req -> req.getYarnName() != null && req.getYarnName().equals(detail.getYarnName()))
                                .forEach(req -> req.setActualYarnId(detail.getYarnId()));
                        }
                    }
                }
                
                sizingYarnIssueEntryService.updateSizingYarnIssueEntry(issueEntryId, issueDTO);
            } catch (Exception e) {
                // If issue entry not found or other error, just skip sync
            }
        }
    }

    private void deleteSizingYarnInwardEntity(SizingYarnInward entity) {
        ensureNoSizingYarnInwardReferences(entity);
        if (StringUtils.hasText(entity.getInwardNo())) {
            yarnInventoryRepository.deleteByInwardNo(entity.getInwardNo());
        }
        sizingYarnInwardRepository.delete(entity);
    }

    private void ensureNoSizingYarnInwardReferences(SizingYarnInward entity) {
        if (entity == null) {
            return;
        }
        Long id = entity.getId();
        String inwardNo = StringUtils.hasText(entity.getInwardNo()) ? entity.getInwardNo().trim() : null;
        if (inwardNo != null && sizingYarnOutwardDetailRepository.existsBySizingYarnInwardNo(inwardNo)) {
            throw new IllegalStateException(inwardNo + " already mapped in Sizing Yarn Outward");
        }
        if (id != null && sizingYarnOutwardDetailRepository.existsBySizingYarnInwardId(id)) {
            throw new IllegalStateException("Cannot delete Sizing Yarn Inward because sizing_yarn_outward_detail references sizing_yarn_inward_id " + id);
        }
    }

    private SizingYarnInward ensureInwardNo(SizingYarnInward entity) {
        if (entity != null && !StringUtils.hasText(entity.getInwardNo()) && entity.getId() != null) {
            Long companyId = entity.getTradingCompanyId() != null ? entity.getTradingCompanyId() : entity.getCompanyId();
            entity.setInwardNo(generateInwardNo(entity.getId(), companyId));
            return sizingYarnInwardRepository.save(entity);
        }
        return entity;
    }

    private String generateInwardNo(Long id, Long companyId) {
        if (id == null) {
            return null;
        }
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
        return String.format("%sSZI-%06d", cmpPrefix, id);
    }

    private void validateSizingReferences(SizingYarnInwardDTO dto) {
        if (dto == null) {
            return;
        }
        requireSizingJobWorkOrderReference(dto.getSizingJobWorkOrderId(), dto.getSizingJobWorkOrderNo());
    }

    private void requireSizingJobWorkOrderReference(Long sizingJobWorkOrderId, String sizingJobWorkOrderNo) {
        if (sizingJobWorkOrderId == null) {
            return;
        }
        Optional<SizingJobWorkOrder> sizingJobWorkOrderOpt = sizingJobWorkOrderRepository.findById(sizingJobWorkOrderId);
        String displayIdentifier = StringUtils.hasText(sizingJobWorkOrderNo)
                ? sizingJobWorkOrderNo
                : sizingJobWorkOrderOpt.map(SizingJobWorkOrder::getSizingWorkOrderNo)
                        .filter(StringUtils::hasText)
                        .orElse(String.valueOf(sizingJobWorkOrderId));
        referenceValidationService.requireReference(
                sizingJobWorkOrderId,
                () -> sizingJobWorkOrderOpt,
                "Sizing Jobwork Order",
                displayIdentifier);
    }

    private SizingYarnInward mapDtoToEntity(SizingYarnInwardDTO dto, SizingYarnInward entity) {
        if (StringUtils.hasText(dto.getInwardNo())) {
            entity.setInwardNo(dto.getInwardNo());
        } else if (entity.getId() == null) {
            entity.setInwardNo(null);
        }
        if (dto.getInwardDate() != null) {
            entity.setInwardDate(dto.getInwardDate());
        }
        if (dto.getOrderTypeId() != null) {
            entity.setOrderTypeId(dto.getOrderTypeId());
        }
        if (dto.getSizingJobWorkOrderId() != null) {
            entity.setSizingJobWorkOrderId(dto.getSizingJobWorkOrderId());
        }
        if (dto.getBuyerId() != null) {
            entity.setBuyerId(dto.getBuyerId());
        }
        if (dto.getFactoryCompanyId() != null) {
            entity.setFactoryCompanyId(dto.getFactoryCompanyId());
        }
        if (dto.getCompanyId() != null) {
            entity.setCompanyId(dto.getCompanyId());
        }
        if (dto.getTradingCompanyId() != null) {
            entity.setTradingCompanyId(dto.getTradingCompanyId());
        }
        if (dto.getSizingYarnIssueId() != null) {
            entity.setSizingYarnIssueId(dto.getSizingYarnIssueId());
        }
        if (entity.getYarnDetails() == null) {
            entity.setYarnDetails(new java.util.ArrayList<>());
        }
        if (dto.getYarnDetails() != null) {
            Map<Long, SizingYarnInwardDetail> existingById = entity.getYarnDetails().stream()
                    .filter(detail -> detail.getId() != null)
                    .collect(Collectors.toMap(SizingYarnInwardDetail::getId, detail -> detail));
            entity.getYarnDetails().clear();
            dto.getYarnDetails().forEach(detailDTO -> {
                SizingYarnInwardDetail detail = detailDTO.getId() != null ? existingById.get(detailDTO.getId()) : null;
                if (detail == null) {
                    detail = new SizingYarnInwardDetail();
                }
                mapDetailDtoToEntity(detailDTO, detail);
                entity.addYarnDetail(detail);
            });
        }
        return entity;
    }

    private SizingYarnInwardDetail mapDetailDtoToEntity(SizingYarnInwardDetailDTO dto, SizingYarnInwardDetail entity) {
        if (StringUtils.hasText(dto.getYarnName())) {
            entity.setYarnName(dto.getYarnName());
        }
        if (dto.getYarnId() != null) {
            entity.setYarnId(dto.getYarnId());
        }
        if (dto.getNoOfBags() != null) {
            entity.setNoOfBags(dto.getNoOfBags());
        }
        if (dto.getKgsPerBag() != null) {
            entity.setKgsPerBag(dto.getKgsPerBag());
        }
        if (dto.getConesPerBag() != null) {
            entity.setConesPerBag(dto.getConesPerBag());
        }
        if (dto.getConesWeight() != null) {
            entity.setConesWeight(dto.getConesWeight());
        }
        if (dto.getWarehouseId() != null) {
            entity.setWarehouseId(dto.getWarehouseId());
        }
        if (dto.getMillId() != null) {
            entity.setMillId(dto.getMillId());
        }
        if (dto.getIssueQty() != null) {
            entity.setIssueQty(dto.getIssueQty());
        }
        if (StringUtils.hasText(dto.getLotNo())) {
            entity.setLotNo(dto.getLotNo());
        }
        return entity;
    }

    private SizingYarnInwardDTO mapEntityToDto(SizingYarnInward entity) {
        return new SizingYarnInwardDTO(entity);
    }

    private void createYarnInventoryFromInward(SizingYarnInward inward) {
        if (inward == null) return;

        // Fetch inwardTypeId from CodesDetail for "Sizing-Yarn-Inward" - handling as 3
        Long inwardTypeId = codesDetailRepository.findByValue("Sizing-Yarn-Inward")
                .map(CodesDetail::getId)
                .orElseGet(() -> codesDetailRepository.findByValue("Sizing_Yarn_Inward")
                        .map(CodesDetail::getId)
                        .orElse(3L));

        YarnInventory inventory = yarnInventoryRepository.findByInwardNo(inward.getInwardNo())
                .orElse(new YarnInventory());
        inventory.setInwardId(inward.getId());
        inventory.setInwardNo(inward.getInwardNo());
        inventory.setInwardTypeId(inwardTypeId);
        inventory.setCompanyId(inward.getCompanyId());
        inventory.setInwardDate(inward.getInwardDate() != null ? inward.getInwardDate().atStartOfDay() : LocalDateTime.now());
        inventory.setFinancialYear(calculateFinancialYear(inward.getInwardDate()));
        
        if (inward.getSizingJobWorkOrderId() != null) {
            sizingJobWorkOrderRepository.findById(inward.getSizingJobWorkOrderId())
                    .ifPresent(wo -> {
                        if (Long.valueOf(2).equals(inward.getOrderTypeId())) {
                            inventory.setOrderId(inward.getSizingJobWorkOrderId());
                        } else {
                            inventory.setOrderId(wo.getSizingPlanId());
                        }
                        inventory.setOrderNo(wo.getSizingWorkOrderNo());
                    });
        }

        if (Long.valueOf(2).equals(inward.getOrderTypeId())) {
            inventory.setVendorId(inward.getBuyerId());
            if (inward.getYarnDetails() != null && !inward.getYarnDetails().isEmpty()) {
                inventory.setWarehouseId(inward.getYarnDetails().get(0).getWarehouseId());
            }
        }

        // Match existing stock details to preserve IDs and avoid stale references
        Map<Long, YarnInwardStockDetails> existingDetailsMap = new HashMap<>();
        if (inventory.getDetails() != null) {
            inventory.getDetails().forEach(d -> {
                if (d.getOrderItemId() != null) {
                    existingDetailsMap.put(d.getOrderItemId(), d);
                }
            });
        }

        List<YarnInwardStockDetails> updatedDetails = new ArrayList<>();
        if (inward.getYarnDetails() != null) {
            for (SizingYarnInwardDetail detail : inward.getYarnDetails()) {
                YarnInwardStockDetails stockDetail = existingDetailsMap.remove(detail.getId());
                if (stockDetail == null) {
                    stockDetail = new YarnInwardStockDetails();
                    stockDetail.setYarnInventory(inventory);
                }
                
                stockDetail.setOrderItemId(detail.getId());
                stockDetail.setItemId(detail.getYarnId());
                stockDetail.setLotNo(detail.getLotNo());
                stockDetail.setInwardQty(detail.getIssueQty());
                stockDetail.setAcceptedQty(detail.getIssueQty());

                BigDecimal inwardBags = detail.getNoOfBags() != null
                        ? BigDecimal.valueOf(detail.getNoOfBags())
                        : BigDecimal.ZERO;
                stockDetail.setInwardBags(inwardBags);
                
                stockDetail.setKgsPerBag(detail.getKgsPerBag());
                stockDetail.setConesPerBag(detail.getConesPerBag());
                stockDetail.setConesWeight(detail.getConesWeight());
                stockDetail.setWarehouseId(detail.getWarehouseId());
                stockDetail.setMillId(detail.getMillId());
                stockDetail.setInwardTypeId(inwardTypeId);
                recalculateAvailableFromOutward(stockDetail, inwardBags);
                
                updatedDetails.add(stockDetail);
            }
        }
        
        if (inventory.getDetails() != null) {
            inventory.getDetails().clear();
            inventory.getDetails().addAll(updatedDetails);
        } else {
            inventory.setDetails(updatedDetails);
        }
        yarnInventoryRepository.save(inventory);
    }
    

    private void recalculateAvailableFromOutward(YarnInwardStockDetails stockDetail, BigDecimal inwardBags) {
        BigDecimal inwardQty = stockDetail.getInwardQty() != null ? stockDetail.getInwardQty() : BigDecimal.ZERO;
        BigDecimal totalIssuedQty = BigDecimal.ZERO;
        BigDecimal totalIssuedBags = BigDecimal.ZERO;
        BigDecimal totalIssuedCones = BigDecimal.ZERO;

        if (stockDetail.getId() != null) {
            totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(stockDetail.getId());
            totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(stockDetail.getId());
            totalIssuedCones = yarnOutwardStockDetailsRepository.getTotalIssuedConesByYarnInwardStockDetailsId(stockDetail.getId());
        }

        BigDecimal conesPerBag = stockDetail.getConesPerBag() != null ? stockDetail.getConesPerBag() : BigDecimal.ZERO;
        BigDecimal totalInwardCones = inwardBags.multiply(conesPerBag);

        if (totalIssuedCones == null || totalIssuedCones.compareTo(BigDecimal.ZERO) == 0) {
            totalIssuedCones = (totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO).multiply(conesPerBag);
        }

        BigDecimal availableQty = inwardQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
        BigDecimal availableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);
        BigDecimal availableCones = totalInwardCones.subtract(totalIssuedCones != null ? totalIssuedCones : BigDecimal.ZERO);

        stockDetail.setAvailableQty(availableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableQty);
        stockDetail.setAvailableBags(availableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableBags);
        stockDetail.setAvailableCones(availableCones.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableCones);
    }

    
    private String calculateFinancialYear(LocalDate date) {
        LocalDate reference = date != null ? date : LocalDate.now();
        int year = reference.getYear();
        int month = reference.getMonthValue();
        if (month >= 4) {
            int nextYear = year + 1;
            return String.format("%d-%d", year, nextYear % 100);
        }
        int previousYear = year - 1;
        return String.format("%d-%d", previousYear, year % 100);
    }
}
