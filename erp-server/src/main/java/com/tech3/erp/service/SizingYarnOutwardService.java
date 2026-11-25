package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingYarnOutwardDTO;
import com.tech3.erp.dto.SizingYarnOutwardDetailDTO;
import com.tech3.erp.dto.SizingYarnOutwardSummaryDTO;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.SizingJobWorkOrder;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.SizingYarnInward;
import com.tech3.erp.entity.SizingYarnOutward;
import com.tech3.erp.entity.SizingYarnOutwardDetail;
import com.tech3.erp.entity.Uom;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnItemEntity;
import com.tech3.erp.entity.YarnOutward;
import com.tech3.erp.entity.YarnOutwardStockDetails;
import com.tech3.erp.entity.SizingYarnIssueEntry;
import com.tech3.erp.entity.YarnReturn;
import com.tech3.erp.entity.YarnReturnItem;
import com.tech3.erp.entity.YarnReturnRequirement;
import com.tech3.erp.repository.SizingYarnIssueEntryRepository;
import com.tech3.erp.repository.YarnOutwardRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.SizingJobWorkOrderRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.SizingYarnInwardRepository;
import com.tech3.erp.repository.SizingYarnOutwardRepository;
import com.tech3.erp.repository.TransportRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnItemRepository;
import com.tech3.erp.repository.YarnReturnItemRepository;
import com.tech3.erp.repository.YarnReturnRepository;
import com.tech3.erp.repository.UomRepository;
import com.tech3.erp.util.PaginationHelper;

import ch.qos.logback.classic.Logger;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class SizingYarnOutwardService {

    private final SizingYarnOutwardRepository sizingYarnOutwardRepository;
    private final TransportRepository transportRepository;

//    public SizingYarnOutwardService(SizingYarnOutwardRepository sizingYarnOutwardRepository, TransportRepository transportRepository) {
//        this.sizingYarnOutwardRepository = sizingYarnOutwardRepository;
//        this.transportRepository = transportRepository;
        
    private final YarnReturnRepository yarnReturnRepository;
    private final YarnReturnItemRepository yarnReturnItemRepository;
    private final VendorRepository vendorRepository;
    private final SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    private final SizingPlanRepository sizingPlanRepository;
    private final YarnInventoryRepository yarnInventoryRepository;
    private final YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    private final YarnItemRepository yarnItemRepository;
    private final UomRepository uomRepository;
    private final CodesDetailRepository codesDetailRepository;
    private final SizingYarnInwardRepository sizingYarnInwardRepository;
    private final ReferenceValidationService referenceValidationService;
    private final YarnOutwardRepository yarnOutwardRepository;
    private final YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;
    private final SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository;
//    private static final Logger log = LoggerFactory.getLogger(SizingYarnOutwardService.class);


    public SizingYarnOutwardService(
            SizingYarnOutwardRepository sizingYarnOutwardRepository,
            YarnReturnRepository yarnReturnRepository,
            YarnReturnItemRepository yarnReturnItemRepository,
            VendorRepository vendorRepository,
            SizingJobWorkOrderRepository sizingJobWorkOrderRepository,
            SizingPlanRepository sizingPlanRepository,
            TransportRepository transportRepository,
            YarnInventoryRepository yarnInventoryRepository,
            YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository,
            YarnItemRepository yarnItemRepository,
            UomRepository uomRepository,
            CodesDetailRepository codesDetailRepository,
            SizingYarnInwardRepository sizingYarnInwardRepository,
            ReferenceValidationService referenceValidationService,
            YarnOutwardRepository yarnOutwardRepository,
            YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository,
            SizingYarnIssueEntryRepository sizingYarnIssueEntryRepository) {
        this.sizingYarnOutwardRepository = sizingYarnOutwardRepository;
        this.yarnReturnRepository = yarnReturnRepository;
        this.yarnReturnItemRepository = yarnReturnItemRepository;
        this.vendorRepository = vendorRepository;
        this.sizingJobWorkOrderRepository = sizingJobWorkOrderRepository;
        this.sizingPlanRepository = sizingPlanRepository;
        this.transportRepository = transportRepository;
        this.yarnInventoryRepository = yarnInventoryRepository;
        this.yarnInwardStockDetailsRepository = yarnInwardStockDetailsRepository;
        this.yarnItemRepository = yarnItemRepository;
        this.uomRepository = uomRepository;
        this.codesDetailRepository = codesDetailRepository;
        this.sizingYarnInwardRepository = sizingYarnInwardRepository;
        this.referenceValidationService = referenceValidationService;
        this.yarnOutwardRepository = yarnOutwardRepository;
        this.yarnOutwardStockDetailsRepository = yarnOutwardStockDetailsRepository;
        this.sizingYarnIssueEntryRepository = sizingYarnIssueEntryRepository;
    }

    @Transactional
    public SizingYarnOutwardDTO createSizingYarnOutward(SizingYarnOutwardDTO dto) {
        validateSizingYarnOutward(dto);
        SizingYarnOutward outward = mapDtoToEntity(dto, new SizingYarnOutward());
        SizingYarnOutward saved = sizingYarnOutwardRepository.save(outward);
        syncSizingYarnReturn(saved);
        syncSizingInventory(saved);
        YarnOutward syncedOutward = syncToYarnOutward(saved);
        applyStockReductions(syncedOutward);
        return mapEntityToDto(saved);
    }

    @Transactional
    public SizingYarnOutwardDTO updateSizingYarnOutward(Long id, SizingYarnOutwardDTO dto) {
        validateSizingYarnOutward(dto);
        SizingYarnOutward existing = sizingYarnOutwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Outward not found with id: " + id));
        
        YarnOutward existingSynced = yarnOutwardRepository.findByOutwardNo(existing.getOutwardNo()).orElse(null);
        reverseStockReductions(existingSynced);
        
        mapDtoToEntity(dto, existing);
        SizingYarnOutward saved = sizingYarnOutwardRepository.save(existing);
        syncSizingYarnReturn(saved);
        syncSizingInventory(saved);
        YarnOutward syncedOutward = syncToYarnOutward(saved);
        applyStockReductions(syncedOutward);
        return mapEntityToDto(saved);
    }

    @Transactional
    public SizingYarnOutwardDTO updateSizingYarnOutwardByCompany(Long factoryCompanyId, Long id, SizingYarnOutwardDTO dto) {
        validateSizingYarnOutward(dto);
        SizingYarnOutward existing = sizingYarnOutwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Outward not found for company"));
        
        YarnOutward existingSynced = yarnOutwardRepository.findByOutwardNo(existing.getOutwardNo()).orElse(null);
        reverseStockReductions(existingSynced);
        
        dto.setFactoryCompanyId(factoryCompanyId);
        mapDtoToEntity(dto, existing);
        SizingYarnOutward saved = sizingYarnOutwardRepository.save(existing);
        syncSizingYarnReturn(saved);
        syncSizingInventory(saved);
        YarnOutward syncedOutward = syncToYarnOutward(saved);
        applyStockReductions(syncedOutward);
        return mapEntityToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SizingYarnOutwardDTO> getAllSizingYarnOutwards() {
        return sizingYarnOutwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SizingYarnOutwardDTO> getAllSizingYarnOutwardsWithPagination(Long offset, Long limit) {
        List<SizingYarnOutwardDTO> all = getAllSizingYarnOutwards();
        return PaginationHelper.paginate(all, offset, limit);
    }

    @Transactional(readOnly = true)
    public List<SizingYarnOutwardDTO> getSizingYarnOutwardsByFactory(Long factoryCompanyId) {
        if (factoryCompanyId == null) {
            throw new IllegalArgumentException("factoryCompanyId is required");
        }
        return sizingYarnOutwardRepository.findByFactoryCompanyIdOrderByIdDesc(factoryCompanyId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SizingYarnOutwardDTO> getSizingYarnOutwardsByFactoryWithPagination(Long factoryCompanyId, Long offset, Long limit) {
        List<SizingYarnOutwardDTO> all = getSizingYarnOutwardsByFactory(factoryCompanyId);
        return PaginationHelper.paginate(all, offset, limit);
    }

    @Transactional(readOnly = true)
    public SizingYarnOutwardDTO getSizingYarnOutwardById(Long id) {
        return sizingYarnOutwardRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Outward not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public SizingYarnOutwardDTO getSizingYarnOutwardByFactory(Long factoryCompanyId, Long id) {
        return sizingYarnOutwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Outward not found for company"));
    }

    @Transactional
    public void deleteSizingYarnOutward(Long id) {
        SizingYarnOutward existing = sizingYarnOutwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Outward not found with id: " + id));
        String outwardNo = existing.getOutwardNo();
        YarnOutward existingSynced = yarnOutwardRepository.findByOutwardNo(outwardNo).orElse(null);
        reverseStockReductions(existingSynced);
        sizingYarnOutwardRepository.delete(existing);
        deleteYarnOutwardByOutwardNo(outwardNo);
    }

    @Transactional
    public void deleteSizingYarnOutwardByFactory(Long factoryCompanyId, Long id) {
        SizingYarnOutward existing = sizingYarnOutwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("Sizing Yarn Outward not found for company"));
        String outwardNo = existing.getOutwardNo();
        YarnOutward existingSynced = yarnOutwardRepository.findByOutwardNo(outwardNo).orElse(null);
        reverseStockReductions(existingSynced);
        sizingYarnOutwardRepository.delete(existing);
        deleteYarnOutwardByOutwardNo(outwardNo);
    }

    private SizingYarnOutward mapDtoToEntity(SizingYarnOutwardDTO dto, SizingYarnOutward entity) {
        if (StringUtils.hasText(dto.getOutwardNo())) {
            entity.setOutwardNo(dto.getOutwardNo());
        }
        if (dto.getOutwardDate() != null) {
            entity.setOutwardDate(dto.getOutwardDate());
        }
        if (dto.getOrderTypeId() != null) {
            entity.setOrderTypeId(dto.getOrderTypeId());
        }
        if (dto.getSizingOrderId() != null) {
            entity.setSizingOrderId(dto.getSizingOrderId());
        }
        if (dto.getSizingPlanId() != null) {
            entity.setSizingPlanId(dto.getSizingPlanId());
        }
        if (dto.getBuyerId() != null) {
            entity.setBuyerId(dto.getBuyerId());
        }
        if (dto.getCompanyId() != null) {
            entity.setCompanyId(dto.getCompanyId());
        }
        if (dto.getTradingCompanyId() != null) {
            entity.setTradingCompanyId(dto.getTradingCompanyId());
        }
        if (dto.getFactoryCompanyId() != null) {
            entity.setFactoryCompanyId(dto.getFactoryCompanyId());
        }
        if (StringUtils.hasText(dto.getBuyerName())) {
            entity.setBuyerName(dto.getBuyerName());
        }
        if (dto.getVendorId() != null) {
            entity.setVendorId(dto.getVendorId());
        }
        if (StringUtils.hasText(dto.getVendorName())) {
            entity.setVendorName(dto.getVendorName());
        }
        if (entity.getVendorId() != null && !StringUtils.hasText(entity.getVendorName())) {
            entity.setVendorName(resolveVendorName(entity.getVendorId()));
        }
        if (dto.getYarnId() != null) {
            entity.setYarnId(dto.getYarnId());
        }
        if (StringUtils.hasText(dto.getYarnName())) {
            entity.setYarnName(dto.getYarnName());
        }
        if (dto.getTransportId() != null) {
            entity.setTransportId(dto.getTransportId());
            transportRepository.findById(dto.getTransportId()).ifPresent(transport -> {
                entity.setTransportName(transport.getTransportName());
            });
        } else if (StringUtils.hasText(dto.getTransportName())) {
            entity.setTransportName(dto.getTransportName());
        }
        if (StringUtils.hasText(dto.getVehicleNo())) {
            entity.setVehicleNo(dto.getVehicleNo());
        }
        if (StringUtils.hasText(dto.getRemarks())) {
            entity.setRemarks(dto.getRemarks());
        }
        if (StringUtils.hasText(dto.getInwardType())) {
            entity.setInwardType(dto.getInwardType());
        } else if (!StringUtils.hasText(entity.getInwardType())) {
            entity.setInwardType("SIZING_YARN_RETURN");
        }
        if (dto.getSummary() != null) {
            entity.setIssuedBags(dto.getSummary().getIssuedBags());
            entity.setIssuedQuantity(dto.getSummary().getIssuedQuantity());
        }

        List<SizingYarnOutwardDetail> currentDetails = entity.getYarnIssueDetails();

        Map<Long, SizingYarnOutwardDetail> existingById = currentDetails.stream()
                .filter(detail -> detail.getId() != null)
                .collect(Collectors.toMap(SizingYarnOutwardDetail::getId, detail -> detail, (a, b) -> a, HashMap::new));

        currentDetails.clear();

        if (!CollectionUtils.isEmpty(dto.getYarnIssueDetails())) {
            for (SizingYarnOutwardDetailDTO detailDTO : dto.getYarnIssueDetails()) {
                SizingYarnOutwardDetail detail = detailDTO.getId() != null ? existingById.get(detailDTO.getId()) : null;
                if (detail == null) {
                    detail = new SizingYarnOutwardDetail();
                }
                mapDetailDtoToEntity(detailDTO, detail);
                entity.addDetail(detail);
            }
        }

        return entity;
        
    }

    private void mapDetailDtoToEntity(SizingYarnOutwardDetailDTO dto, SizingYarnOutwardDetail entity) {
        if (dto.getSizingYarnInwardId() != null) {
            entity.setSizingYarnInwardId(dto.getSizingYarnInwardId());
            sizingYarnInwardRepository.findById(dto.getSizingYarnInwardId())
                    .ifPresent(sizingInward -> entity.setSizingYarnIssueId(sizingInward.getSizingYarnIssueId()));
        } else if (StringUtils.hasText(dto.getSizingYarnInwardNo())) {
            sizingYarnInwardRepository.findByInwardNo(dto.getSizingYarnInwardNo())
                    .ifPresent(sizingInward -> entity.setSizingYarnIssueId(sizingInward.getSizingYarnIssueId()));
        }
        if (StringUtils.hasText(dto.getSizingYarnInwardNo())) {
            entity.setSizingYarnInwardNo(dto.getSizingYarnInwardNo());
        }
        if (dto.getYarnId() != null) {
            entity.setYarnId(dto.getYarnId());
        }
        if (StringUtils.hasText(dto.getYarnName())) {
            entity.setYarnName(dto.getYarnName());
        }
        entity.setInwardBags(dto.getInwardBags());
        entity.setInwardQuantity(dto.getInwardQuantity());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setConeWeight(dto.getConeWeight());
        entity.setIssuingBags(dto.getIssuingBags());
        entity.setIssuedQuantity(dto.getIssuedQuantity());
        if (dto.getMillId() != null) {
            entity.setMillId(dto.getMillId());
        }
        if (StringUtils.hasText(dto.getMillName())) {
            entity.setMillName(dto.getMillName());
        }
        if (StringUtils.hasText(dto.getLotNo())) {
            entity.setLotNo(dto.getLotNo());
        }
        entity.setWarehouseId(dto.getWarehouseId());
    }

    private SizingYarnOutwardDTO mapEntityToDto(SizingYarnOutward entity) {
        SizingYarnOutwardDTO dto = new SizingYarnOutwardDTO(entity);
        if (dto.getSummary() == null) {
            dto.setSummary(new SizingYarnOutwardSummaryDTO(entity.getIssuedBags(), entity.getIssuedQuantity()));
        }
        List<SizingYarnOutwardDetail> details = entity.getYarnIssueDetails();
        if (details == null) {
            dto.setYarnIssueDetails(new java.util.ArrayList<>());
        } else {
            dto.setYarnIssueDetails(details.stream()
                    .sorted(Comparator.comparing(detail -> detail.getId() == null ? 0L : detail.getId()))
                    .map(SizingYarnOutwardDetailDTO::new)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private void syncSizingYarnReturn(SizingYarnOutward outward) {
        if (outward == null || !StringUtils.hasText(outward.getOutwardNo())) {
            return;
        }
        Long companyId = outward.getBuyerId();
        if (companyId == null) {
            return;
        }
        Long vendorId = outward.getVendorId();
        if (vendorId == null) {
            vendorId = outward.getBuyerId();
        }
        if (vendorId == null) {
            vendorId = outward.getTradingCompanyId() != null ? outward.getTradingCompanyId() : outward.getFactoryCompanyId();
        }
        String vendorName = StringUtils.hasText(outward.getVendorName()) ? outward.getVendorName() : null;
        if (vendorId != null && !StringUtils.hasText(vendorName)) {
            vendorName = resolveVendorName(vendorId);
        }
        if (!StringUtils.hasText(vendorName)) {
            vendorName = outward.getBuyerName();
        }
        if (vendorId == null || !StringUtils.hasText(vendorName)) {
            return;
        }
        String inwardNumber = outward.getOutwardNo();
        YarnReturn yarnReturn = yarnReturnRepository.findByInwardNumber(inwardNumber)
                .orElseGet(YarnReturn::new);
        boolean existing = yarnReturn.getId() != null;
        Long warehouseId = resolveSizingWarehouseId(outward.getYarnIssueDetails());

        yarnReturn.setInwardNumber(inwardNumber);
        yarnReturn.setInwardDate(outward.getOutwardDate() != null ? outward.getOutwardDate() : LocalDate.now());
        yarnReturn.setInwardType(StringUtils.hasText(outward.getInwardType()) ? outward.getInwardType() : "SIZING_YARN_RETURN");
        yarnReturn.setOrderNo(resolveSizingOrderNo(outward));
        yarnReturn.setVendorId(vendorId);
        yarnReturn.setVendorName(vendorName);
        yarnReturn.setMillId(resolveSizingMillId(outward.getYarnIssueDetails()));
        yarnReturn.setWarehouseId(warehouseId);
        yarnReturn.setIssuedQty(outward.getIssuedQuantity() != null ? outward.getIssuedQuantity().doubleValue() : 0d);
        yarnReturn.setRemarks(null);
        yarnReturn.setDcNumber(null);
        yarnReturn.setDcDate(null);
        yarnReturn.setCompanyId(companyId);
        yarnReturn.setActiveFlag(Boolean.TRUE);

        Long sizingYarnIssueEntryId = resolveSizingYarnIssueEntryId(outward.getYarnIssueDetails());
        if (sizingYarnIssueEntryId != null) {
            sizingYarnIssueEntryRepository.findById(sizingYarnIssueEntryId).ifPresent(issueEntry -> {
                double totalIssueQty = issueEntry.getSizingYarnIssues().stream()
                        .filter(issue -> issue.getIssueQty() != null)
                        .mapToDouble(issue -> issue.getIssueQty().doubleValue())
                        .sum();
                if (totalIssueQty > 0) {
                    yarnReturn.setIssuedQty(totalIssueQty);
                }
                List<YarnReturnRequirement> returnRequirements = issueEntry.getRequirements().stream()
                        .map(req -> {
                            YarnReturnRequirement returnReq = new YarnReturnRequirement();
                            returnReq.setYarnName(req.getYarnName());
                            returnReq.setActualYarnId(req.getActualYarnId());
                            returnReq.setActualYarnName(req.getActualYarn());
                            returnReq.setGreigeYarnId(req.getYarnId());
                            returnReq.setIssuedQty(req.getTotalIssuedQty() != null ? req.getTotalIssuedQty().doubleValue() : null);
                            return returnReq;
                        })
                        .collect(Collectors.toList());
                yarnReturn.setYarnReturnRequirements(returnRequirements);
            });
        }

        if (existing && !CollectionUtils.isEmpty(yarnReturn.getYarnReturnItems())) {
            yarnReturnItemRepository.deleteAll(yarnReturn.getYarnReturnItems());
            yarnReturn.getYarnReturnItems().clear();
        }

        yarnReturn.getYarnReturnItems().clear();
        yarnReturn.getYarnReturnItems().addAll(buildSizingYarnReturnItems(outward, yarnReturn));
        yarnReturnRepository.save(yarnReturn);
    }

    private void syncSizingInventory(SizingYarnOutward outward) {
        if (outward == null || !StringUtils.hasText(outward.getOutwardNo())) {
            return;
        }
        
        // Skip inventory creation for External orders (OrderTypeId = 2)
        if (outward.getOrderTypeId() != null && outward.getOrderTypeId().equals(2L)) {
            // Delete any existing inventory records for this outward number
            deleteInventoryByInwardNo(outward.getOutwardNo());
            return;
        }
        
        if (CollectionUtils.isEmpty(outward.getYarnIssueDetails())) {
            deleteInventoryByInwardNo(outward.getOutwardNo());
            return;
        }
        Long companyId = resolveCompanyId(outward);
        if (companyId == null) {
            deleteInventoryByInwardNo(outward.getOutwardNo());
            return;
        }
        YarnInventory inventory = yarnInventoryRepository.findByInwardNo(outward.getOutwardNo())
                .orElseGet(YarnInventory::new);
        Long inwardTypeId = resolveYarnReturnInwardTypeId(outward.getOrderTypeId());
        inventory.setInwardNo(outward.getOutwardNo());
        // Fix: Get the correct inwardId from YarnReturn record against the inwardNumber
        YarnReturn yarnReturn = yarnReturnRepository.findByInwardNumber(outward.getOutwardNo())
                .orElse(null);
        Long yarnReturnId = yarnReturn != null ? yarnReturn.getId() : null;
        inventory.setInwardId(yarnReturnId);
        inventory.setCompanyId(companyId);
        inventory.setInwardTypeId(inwardTypeId);
        LocalDate date = outward.getOutwardDate() != null ? outward.getOutwardDate() : LocalDate.now();
        LocalDateTime dateTime = date.atStartOfDay();
        inventory.setInwardDate(dateTime);
        inventory.setFinancialYear(calculateFinancialYear(date));
        inventory.setOrderId(resolveSizingInventoryOrderId(outward));
        inventory.setOrderNo(resolveSizingOrderNo(outward));
        inventory.setVendorId(resolveVendorId(outward));
        Long warehouseId = resolveSizingWarehouseId(outward.getYarnIssueDetails());
        inventory.setWarehouseId(warehouseId);
        inventory.setDcNo(null);
        inventory.setDcDate(null);

        if (inventory.getDetails() == null) {
            inventory.setDetails(new ArrayList<>());
        }

        updateSizingInventoryDetails(outward, inventory, inwardTypeId, warehouseId);

        if (inventory.getDetails().isEmpty()) {
            deleteInventoryByInwardNo(outward.getOutwardNo());
            return;
        }

        yarnInventoryRepository.save(inventory);
    }

    // private YarnOutward syncToYarnOutward(SizingYarnOutward outward) {
    //     if (outward == null || !StringUtils.hasText(outward.getOutwardNo())) {
    //         return null;
    //     }

    //     YarnOutward yarnOutward = yarnOutwardRepository.findByOutwardNo(outward.getOutwardNo())
    //             .orElseGet(YarnOutward::new);

    //     Long outwardTypeId = codesDetailRepository.findByValue("SIZING_YARN_OUTWARD")
    //             .map(CodesDetail::getId)
    //             .orElseGet(() -> codesDetailRepository.findByValue("Sizing_Yarn_Inward")
    //                     .map(CodesDetail::getId)
    //                     .orElse(55L));

    //     Long sizingInwardTypeId = codesDetailRepository.findByValue("Sizing-Yarn-Inward")
    //             .map(CodesDetail::getId)
    //             .orElseGet(() -> codesDetailRepository.findByValue("Sizing_Yarn_Inward")
    //                     .map(CodesDetail::getId)
    //                     .orElse(3L));
        
    //     yarnOutward.setOutwardNo(outward.getOutwardNo());
    //     yarnOutward.setOutwardDate(outward.getOutwardDate());
    //     yarnOutward.setOutwardTypeId(outwardTypeId);
    //     yarnOutward.setCompanyId(outward.getBuyerId());
    //     yarnOutward.setVendorId(resolveVendorId(outward));
    //     yarnOutward.setOrderId(resolveSizingInventoryOrderId(outward));
    //     yarnOutward.setOrderNo(resolveSizingOrderNo(outward));
    //     yarnOutward.setFinancialYearId(calculateFinancialYear(outward.getOutwardDate()));
    //     yarnOutward.setRemarks(outward.getRemarks());
    //     yarnOutward.setActiveFlag(true);

    //     if (yarnOutward.getId() != null && !CollectionUtils.isEmpty(yarnOutward.getYarnOutwardStockDetails())) {
    //         yarnOutwardStockDetailsRepository.deleteAll(yarnOutward.getYarnOutwardStockDetails());
    //         yarnOutward.getYarnOutwardStockDetails().clear();
    //     }

    //     if (!CollectionUtils.isEmpty(outward.getYarnIssueDetails())) {
    //         yarnOutward.getYarnOutwardStockDetails().clear();
    //         for (SizingYarnOutwardDetail detail : outward.getYarnIssueDetails()) {
    //             YarnOutwardStockDetails stockDetail = new YarnOutwardStockDetails();
    //             stockDetail.setYarnOutward(yarnOutward);
                
    //             // 1. Resolve YarnInwardStockDetailsId using sizingYarnInwardId (Detail ID)
    //             Long stockDetailId = resolveYarnInwardStockDetailsId(detail.getSizingYarnInwardId(), outward.getOutwardNo());
    //             stockDetail.setYarnInwardStockDetailsId(stockDetailId);
                
    //             // 2. Fetch inwardId from the corresponding YarnInwardStockDetails record
    //             if (stockDetailId != null) {
    //                 yarnInwardStockDetailsRepository.findById(stockDetailId).ifPresent(sourceStockDetail -> {
    //                     if (sourceStockDetail.getYarnInventory() != null) {
    //                         stockDetail.setInwardId(sourceStockDetail.getYarnInventory().getId());
    //                     } else {
    //                         // Fallback if YarnInventory is not linked
    //                         stockDetail.setInwardId(detail.getSizingYarnInwardId());
    //                     }
    //                 });
    //             } else {
    //                 // Fallback to sizingYarnInwardId if resolution fails
    //                 stockDetail.setInwardId(detail.getSizingYarnInwardId());
    //             }

    //             stockDetail.setOrderItemId(detail.getSizingYarnInwardId());
    //             stockDetail.setOutwardTypeId(outwardTypeId);
    //             stockDetail.setActualItemId(detail.getYarnId());
    //             stockDetail.setMillId(detail.getMillId());
    //             stockDetail.setWarehouseId(detail.getWarehouseId());
    //             stockDetail.setIssuedQty(detail.getIssuedQuantity());
    //             stockDetail.setIssuedBags(detail.getIssuingBags());
    //             stockDetail.setKgsPerBag(detail.getKgsPerBag());
    //             stockDetail.setConesWeight(detail.getConeWeight());
    //             yarnOutward.getYarnOutwardStockDetails().add(stockDetail);
    //         }
    //     }

    //     return yarnOutwardRepository.save(yarnOutward);
    // }

private YarnOutward syncToYarnOutward(SizingYarnOutward outward) {
    if (outward == null || !StringUtils.hasText(outward.getOutwardNo())) {
        return null;
    }

    YarnOutward yarnOutward = yarnOutwardRepository.findByOutwardNo(outward.getOutwardNo())
            .orElseGet(YarnOutward::new);

    Long outwardTypeId = codesDetailRepository.findByValue("SIZING_YARN_OUTWARD")
            .map(CodesDetail::getId)
            .orElseGet(() -> codesDetailRepository.findByValue("Sizing_Yarn_Inward")
                    .map(CodesDetail::getId)
                    .orElse(55L));
    
    yarnOutward.setOutwardNo(outward.getOutwardNo());
    yarnOutward.setOutwardDate(outward.getOutwardDate());
    yarnOutward.setOutwardTypeId(outwardTypeId);
    yarnOutward.setCompanyId(outward.getBuyerId());
    yarnOutward.setVendorId(resolveVendorId(outward));
    yarnOutward.setOrderId(resolveSizingInventoryOrderId(outward));
    yarnOutward.setOrderNo(resolveSizingOrderNo(outward));
    yarnOutward.setFinancialYearId(calculateFinancialYear(outward.getOutwardDate()));
    yarnOutward.setRemarks(outward.getRemarks());
    yarnOutward.setActiveFlag(true);

    if (yarnOutward.getId() != null && !CollectionUtils.isEmpty(yarnOutward.getYarnOutwardStockDetails())) {
        yarnOutwardStockDetailsRepository.deleteAll(yarnOutward.getYarnOutwardStockDetails());
        yarnOutward.getYarnOutwardStockDetails().clear();
    }

    if (!CollectionUtils.isEmpty(outward.getYarnIssueDetails())) {
        yarnOutward.getYarnOutwardStockDetails().clear();
        for (SizingYarnOutwardDetail detail : outward.getYarnIssueDetails()) {
            YarnOutwardStockDetails stockDetail = new YarnOutwardStockDetails();
            stockDetail.setYarnOutward(yarnOutward);
            
            // FIXED: Correct mapping for inwardId and yarnInwardStockDetailsId
            Long sizingYarnInwardId = detail.getSizingYarnInwardId();
            
            if (sizingYarnInwardId != null) {
                // Step 1: Find the YarnInventory record using sizingYarnInwardId
                Optional<SizingYarnInward> sizingInwardOpt = sizingYarnInwardRepository.findById(sizingYarnInwardId);
                
                if (sizingInwardOpt.isPresent()) {
                    SizingYarnInward sizingInward = sizingInwardOpt.get();
                    String inwardNo = sizingInward.getInwardNo();
                    
                    // Step 2: Find the corresponding YarnInventory record by inwardNo
                    Optional<YarnInventory> inventoryOpt = yarnInventoryRepository.findByInwardNo(inwardNo);
                    
                    if (inventoryOpt.isPresent()) {
                        YarnInventory inventory = inventoryOpt.get();
                        
                        // Step 3: Set inwardId from YarnInventory
                        stockDetail.setInwardId(inventory.getId());
                        
                        // Step 4: Find the corresponding YarnInwardStockDetails record
                        // This should match the yarn, mill, lot, etc. from the detail
                        Optional<YarnInwardStockDetails> stockDetailOpt = yarnInwardStockDetailsRepository
                                .findByYarnInventoryIdAndItemIdAndMillIdAndLotNo(
                                    inventory.getId(),
                                    detail.getYarnId(),
                                    detail.getMillId(),
                                    detail.getLotNo()
                                );
                        
                        if (stockDetailOpt.isPresent()) {
                            // Step 5: Set yarnInwardStockDetailsId
                            stockDetail.setYarnInwardStockDetailsId(stockDetailOpt.get().getId());
                        } else {
                            // Fallback: Try to find by orderItemId (which might be the sizingYarnInwardId)
                            stockDetailOpt = yarnInwardStockDetailsRepository
                                    .findFirstByOrderItemId(sizingYarnInwardId);
                            
                            stockDetailOpt.ifPresent(inwardStockDetail -> 
                                stockDetail.setYarnInwardStockDetailsId(inwardStockDetail.getId())
                            );
                        }
                    }
                }
            }
            
            // Fallback if resolution failed
            if (stockDetail.getYarnInwardStockDetailsId() == null) {
                // Try to find by orderItemId directly
                Optional<YarnInwardStockDetails> stockDetailOpt = yarnInwardStockDetailsRepository
                        .findFirstByOrderItemId(sizingYarnInwardId);
                
                stockDetailOpt.ifPresent(inwardStockDetail -> {
                    stockDetail.setYarnInwardStockDetailsId(inwardStockDetail.getId());
                    if (inwardStockDetail.getYarnInventory() != null) {
                        stockDetail.setInwardId(inwardStockDetail.getYarnInventory().getId());
                    }
                });
            }

            stockDetail.setOrderItemId(sizingYarnInwardId);
            stockDetail.setOutwardTypeId(outwardTypeId);
            stockDetail.setActualItemId(detail.getYarnId());
            stockDetail.setMillId(detail.getMillId());
            stockDetail.setWarehouseId(detail.getWarehouseId());
            stockDetail.setIssuedQty(detail.getIssuedQuantity());
            stockDetail.setIssuedBags(detail.getIssuingBags());
            stockDetail.setKgsPerBag(detail.getKgsPerBag());
            stockDetail.setConesWeight(detail.getConeWeight());
            
            yarnOutward.getYarnOutwardStockDetails().add(stockDetail);
        }
    }

    return yarnOutwardRepository.save(yarnOutward);
}
    private Long resolveYarnInwardStockDetailsId(Long sizingYarnInwardId, String currentOutwardNo) {
        if (sizingYarnInwardId == null) {
            return null;
        }
        List<YarnInwardStockDetails> details = yarnInwardStockDetailsRepository.findAllByOrderItemId(sizingYarnInwardId);
        if (details.isEmpty()) {
            return null;
        }

        // 1. Prefer the record with the original inward type ID (not a return)
        Long sizingInwardTypeId = codesDetailRepository.findByValue("Sizing-Yarn-Inward")
                .map(CodesDetail::getId)
                .orElseGet(() -> codesDetailRepository.findByValue("Sizing_Yarn_Inward")
                        .map(CodesDetail::getId)
                        .orElse(3L));

        Optional<YarnInwardStockDetails> originalRecord = details.stream()
                .filter(d -> Objects.equals(d.getInwardTypeId(), sizingInwardTypeId))
                .findFirst();

        if (originalRecord.isPresent()) {
            return originalRecord.get().getId();
        }

        // 2. Fallback: Exclude the record just created by syncSizingInventory (which has the current outwardNo)
        return details.stream()
                .filter(d -> d.getYarnInventory() == null || !currentOutwardNo.equals(d.getYarnInventory().getInwardNo()))
                .map(YarnInwardStockDetails::getId)
                .findFirst()
                .orElse(null);
    }

    private void updateSizingInventoryDetails(SizingYarnOutward outward, YarnInventory inventory, Long inwardTypeId, Long defaultWarehouseId) {
        List<YarnInwardStockDetails> existingDetails = inventory.getDetails();
        Map<String, YarnInwardStockDetails> existingMap = new HashMap<>();
        for (YarnInwardStockDetails d : existingDetails) {
            String key = d.getItemId() + "-" + d.getLotNo() + "-" + d.getMillId() + "-" + d.getWarehouseId();
            existingMap.put(key, d);
        }

        List<YarnInwardStockDetails> updatedDetails = new ArrayList<>();
        Map<Long, Long> warehouseCache = new HashMap<>();

        for (SizingYarnOutwardDetail detail : outward.getYarnIssueDetails()) {
            if (detail == null || detail.getYarnId() == null) {
                continue;
            }
            BigDecimal issuedQty = defaultZero(detail.getIssuedQuantity());
            if (issuedQty.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            Long resolvedWarehouseId = resolveSizingDetailWarehouseId(detail, warehouseCache);
            if (resolvedWarehouseId == null) {
                resolvedWarehouseId = defaultWarehouseId;
            }

            String key = detail.getYarnId() + "-" + detail.getLotNo() + "-" + detail.getMillId() + "-" + resolvedWarehouseId;
            YarnInwardStockDetails stock = existingMap.remove(key);

            if (stock == null) {
                stock = new YarnInwardStockDetails();
                stock.setYarnInventory(inventory);
                stock.setInwardTypeId(inwardTypeId);
                stock.setItemId(detail.getYarnId());
                stock.setUomId(resolveUomIdForYarn(detail.getYarnId()));
                stock.setMillId(detail.getMillId());
                stock.setLotNo(detail.getLotNo());
                stock.setWarehouseId(resolvedWarehouseId);
                stock.setOrderItemId(resolveYarnOrderItemId(detail.getSizingYarnInwardId()));
                
                stock.setInwardQty(issuedQty);
                stock.setInwardBags(defaultZero(detail.getIssuingBags()));
                stock.setAcceptedQty(issuedQty);
                stock.setAvailableQty(issuedQty);
                stock.setAvailableBags(defaultZero(detail.getIssuingBags()));
                stock.setRejectedQty(BigDecimal.ZERO);
            } else {
                BigDecimal oldInwardQty = defaultZero(stock.getInwardQty());
                BigDecimal oldInwardBags = defaultZero(stock.getInwardBags());
                BigDecimal newInwardQty = issuedQty;
                BigDecimal newInwardBags = defaultZero(detail.getIssuingBags());

                BigDecimal qtyDiff = newInwardQty.subtract(oldInwardQty);
                BigDecimal bagsDiff = newInwardBags.subtract(oldInwardBags);

                stock.setInwardQty(newInwardQty);
                stock.setInwardBags(newInwardBags);
                stock.setAcceptedQty(newInwardQty);
                stock.setAvailableQty(defaultZero(stock.getAvailableQty()).add(qtyDiff));
                stock.setAvailableBags(defaultZero(stock.getAvailableBags()).add(bagsDiff));
            }

            stock.setKgsPerBag(detail.getKgsPerBag());
            stock.setConesPerBag(null);
            stock.setConesWeight(detail.getConeWeight());
            stock.setRemark(outward.getRemarks());
            updatedDetails.add(stock);
        }

        if (!existingMap.isEmpty()) {
            yarnInwardStockDetailsRepository.deleteAll(existingMap.values());
        }

        existingDetails.clear();
        existingDetails.addAll(updatedDetails);
    }

    private Long resolveSizingWarehouseId(List<SizingYarnOutwardDetail> details) {
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        Map<Long, Long> warehouseCache = new HashMap<>();
        for (SizingYarnOutwardDetail detail : details) {
            Long resolved = resolveSizingDetailWarehouseId(detail, warehouseCache);
            if (resolved != null) {
                return resolved;
            }
        }
        return null;
    }

    private Long resolveSizingDetailWarehouseId(SizingYarnOutwardDetail detail, Map<Long, Long> warehouseCache) {
        if (detail == null) {
            return null;
        }
        if (detail.getWarehouseId() != null) {
            return detail.getWarehouseId();
        }
        Long orderItemId = detail.getSizingYarnInwardId();
        if (orderItemId == null) {
            return null;
        }
        if (warehouseCache.containsKey(orderItemId)) {
            return warehouseCache.get(orderItemId);
        }
        Long resolved = yarnInwardStockDetailsRepository.findFirstByOrderItemId(orderItemId)
                .map(YarnInwardStockDetails::getWarehouseId)
                .orElse(null);
        warehouseCache.put(orderItemId, resolved);
        return resolved;
    }

    private Long resolveYarnReturnInwardTypeId(Long fallback) {
        return codesDetailRepository.findByValue("SIZING_YARN_RETURN")
                .map(detail -> detail.getId())
                .orElse(5L); // Default to 124L if not found
    }

    

    private void deleteInventoryByInwardNo(String inwardNo) {
        if (!StringUtils.hasText(inwardNo)) {
            return;
        }
        yarnInventoryRepository.findByInwardNo(inwardNo).ifPresent(existing -> {
            List<YarnInwardStockDetails> details = existing.getDetails();
            if (!CollectionUtils.isEmpty(details)) {
                yarnInwardStockDetailsRepository.deleteAll(details);
            }
            yarnInventoryRepository.delete(existing);
        });
    }

    private void deleteYarnOutwardByOutwardNo(String outwardNo) {
        if (!StringUtils.hasText(outwardNo)) {
            return;
        }
        yarnOutwardRepository.findByOutwardNo(outwardNo).ifPresent(existing -> {
            if (!CollectionUtils.isEmpty(existing.getYarnOutwardStockDetails())) {
                yarnOutwardStockDetailsRepository.deleteAll(existing.getYarnOutwardStockDetails());
            }
            yarnOutwardRepository.delete(existing);
        });
    }

    private Long resolveCompanyId(SizingYarnOutward outward) {
        return outward.getBuyerId();
    }

    private Long resolveVendorId(SizingYarnOutward outward) {
        Long vendorId = outward.getVendorId();
        if (vendorId == null) {
            vendorId = outward.getBuyerId();
        }
        if (vendorId == null) {
            vendorId = outward.getTradingCompanyId() != null ? outward.getTradingCompanyId() : outward.getFactoryCompanyId();
        }
        return vendorId;
    }

    private Long resolveSizingInventoryOrderId(SizingYarnOutward outward) {
        if (outward.getSizingPlanId() != null) {
            return outward.getSizingPlanId();
        }
        if (outward.getSizingOrderId() != null) {
            return outward.getSizingOrderId();
        }
        return outward.getOrderTypeId();
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

    private Long resolveUomIdForYarn(Long yarnId) {
        if (yarnId == null) {
            return getDefaultUomId();
        }
        List<YarnItemEntity> yarnItems = yarnItemRepository.findByYarnId(yarnId);
        if (!yarnItems.isEmpty() && yarnItems.get(0).getUomId() != null) {
            return yarnItems.get(0).getUomId();
        }
        return getDefaultUomId();
    }

    private Long getDefaultUomId() {
        List<Uom> uoms = uomRepository.findAll();
        if (!uoms.isEmpty()) {
            return uoms.get(0).getId();
        }
        return null;
    }

    private BigDecimal defaultZero(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    private Long resolveYarnOrderItemId(Long requestedId) {
        if (requestedId != null) {
            boolean exists = yarnInwardStockDetailsRepository.findFirstByOrderItemId(requestedId).isPresent();
            if (!exists) {
                return requestedId;
            }
        }
        return yarnInwardStockDetailsRepository.getNextOrderItemIdValue();
    }

    private Long resolveSizingYarnIssueEntryId(List<SizingYarnOutwardDetail> details) {
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        for (SizingYarnOutwardDetail detail : details) {
            if (detail.getSizingYarnInwardId() != null) {
                Optional<SizingYarnInward> inwardOpt = sizingYarnInwardRepository.findById(detail.getSizingYarnInwardId());
                if (inwardOpt.isPresent() && inwardOpt.get().getSizingYarnIssueId() != null) {
                    return inwardOpt.get().getSizingYarnIssueId();
                }
            }
        }
        return null;
    }

    private List<YarnReturnItem> buildSizingYarnReturnItems(SizingYarnOutward outward, YarnReturn yarnReturn) {
        if (CollectionUtils.isEmpty(outward.getYarnIssueDetails())) {
            return new ArrayList<>();
        }
        return outward.getYarnIssueDetails().stream()
                .map(detail -> mapSizingDetailToYarnReturnItem(detail, outward, yarnReturn))
                .collect(Collectors.toList());
    }

    private YarnReturnItem mapSizingDetailToYarnReturnItem(
            SizingYarnOutwardDetail detail, SizingYarnOutward outward, YarnReturn yarnReturn) {
        YarnReturnItem item = new YarnReturnItem();
        item.setYarnReturn(yarnReturn);
        item.setYarnId(detail.getYarnId());
        item.setYarnName(detail.getYarnName());
        item.setNoOfBags(detail.getIssuingBags());
        item.setTotalKgs(detail.getIssuedQuantity() != null ? detail.getIssuedQuantity().doubleValue() : 0d);
        item.setConesPerBag(detail.getKgsPerBag());
        item.setConesWeight(detail.getConeWeight());
        item.setLotNo(detail.getLotNo());
        item.setActiveFlag(Boolean.TRUE);
        item.setOrderItemId(detail.getSizingYarnInwardId());
        item.setYarnOutwardId(outward.getId());
        item.setYarnOutwardStockdetailsId(detail.getId());
        item.setSizingYarnIssueId(detail.getSizingYarnIssueId());
        return item;
    }

    private String resolveSizingOrderNo(SizingYarnOutward outward) {
        if (outward.getSizingPlanId() != null) {
            String sizingPlanNo = resolveSizingPlanNo(outward.getSizingPlanId());
            if (StringUtils.hasText(sizingPlanNo)) {
                return sizingPlanNo;
            }
            return outward.getSizingPlanId().toString();
        }
        if (outward.getSizingOrderId() != null) {
            String sizingOrderNo = resolveSizingWorkOrderNo(outward.getSizingOrderId());
            if (StringUtils.hasText(sizingOrderNo)) {
                return sizingOrderNo;
            }
            return outward.getSizingOrderId().toString();
        }
        if (outward.getOrderTypeId() != null) {
            return outward.getOrderTypeId().toString();
        }
        return StringUtils.hasText(outward.getOutwardNo()) ? outward.getOutwardNo() : "SIZING";
    }

    private String resolveSizingWorkOrderNo(Long sizingOrderId) {
        if (sizingOrderId == null) {
            return null;
        }
        return sizingJobWorkOrderRepository.findById(sizingOrderId)
                .map(order -> order.getSizingWorkOrderNo())
                .filter(StringUtils::hasText)
                .orElse(null);
    }

    private String resolveSizingPlanNo(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return null;
        }
        return sizingPlanRepository.findById(sizingPlanId)
                .map(plan -> plan.getSizingPlanNo())
                .filter(StringUtils::hasText)
                .orElse(null);
    }

    private void requireSizingOrderReference(Long sizingOrderId, String sizingOrderNo) {
        if (sizingOrderId == null) {
            return;
        }
        Optional<SizingJobWorkOrder> sizingJobWorkOrderOpt = sizingJobWorkOrderRepository.findById(sizingOrderId);
        String displayIdentifier = StringUtils.hasText(sizingOrderNo)
                ? sizingOrderNo
                : sizingJobWorkOrderOpt.map(SizingJobWorkOrder::getSizingWorkOrderNo)
                        .filter(StringUtils::hasText)
                        .orElse(String.valueOf(sizingOrderId));
        referenceValidationService.requireReference(
                sizingOrderId,
                () -> sizingJobWorkOrderOpt,
                "Sizing Work Order",
                displayIdentifier);
    }

    private void requireSizingPlanReference(Long sizingPlanId, String sizingPlanNo) {
        if (sizingPlanId == null) {
            return;
        }
        Optional<SizingPlan> sizingPlanOpt = sizingPlanRepository.findById(sizingPlanId);
        String displayIdentifier = StringUtils.hasText(sizingPlanNo)
                ? sizingPlanNo
                : sizingPlanOpt.map(SizingPlan::getSizingPlanNo)
                        .filter(StringUtils::hasText)
                        .orElse(String.valueOf(sizingPlanId));
        referenceValidationService.requireReference(
                sizingPlanId,
                () -> sizingPlanOpt,
                "Sizing Plan",
                displayIdentifier);
    }

    private void requireSizingYarnInwardReferences(List<SizingYarnOutwardDetailDTO> details) {
        if (CollectionUtils.isEmpty(details)) {
            return;
        }
        for (SizingYarnOutwardDetailDTO detail : details) {
            requireSizingYarnInwardReference(detail);
        }
    }

    private void requireSizingYarnInwardReference(SizingYarnOutwardDetailDTO detail) {
        if (detail == null) {
            return;
        }
        if (detail.getSizingYarnInwardId() != null) {
            Optional<SizingYarnInward> sizingYarnInwardOpt = sizingYarnInwardRepository.findById(detail.getSizingYarnInwardId());
            String displayIdentifier = StringUtils.hasText(detail.getSizingYarnInwardNo())
                    ? detail.getSizingYarnInwardNo()
                    : sizingYarnInwardOpt.map(SizingYarnInward::getInwardNo)
                            .filter(StringUtils::hasText)
                            .orElse(detail.getSizingYarnInwardId().toString());
            referenceValidationService.requireReference(
                    detail.getSizingYarnInwardId(),
                    () -> sizingYarnInwardOpt,
                    "Sizing Yarn Inward",
                    displayIdentifier);
            return;
        }
        if (!StringUtils.hasText(detail.getSizingYarnInwardNo())) {
            return;
        }
        Optional<SizingYarnInward> sizingYarnInwardOpt = sizingYarnInwardRepository.findByInwardNo(detail.getSizingYarnInwardNo());
        referenceValidationService.requireReference(
                detail.getSizingYarnInwardNo(),
                () -> sizingYarnInwardOpt,
                "Sizing Yarn Inward",
                detail.getSizingYarnInwardNo());
    }

    private String resolveVendorName(Long vendorId) {
        if (vendorId == null) {
            return null;
        }
        return vendorRepository.findById(vendorId)
                .map(vendor -> vendor.getVendorName())
                .filter(StringUtils::hasText)
                .orElse(null);
    }

    private Long resolveSizingMillId(List<SizingYarnOutwardDetail> details) {
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        return details.stream()
                .map(SizingYarnOutwardDetail::getMillId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private void validateSizingYarnOutward(SizingYarnOutwardDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Sizing Yarn Outward payload is required");
        }
        if (dto.getOutwardDate() == null) {
            throw new IllegalArgumentException("Outward date is required");
        }
        if (CollectionUtils.isEmpty(dto.getYarnIssueDetails())) {
            throw new IllegalArgumentException("At least one yarn issue detail is required");
        }
        requireSizingOrderReference(dto.getSizingOrderId(), dto.getSizingOrderNo());
        requireSizingPlanReference(dto.getSizingPlanId(), dto.getSizingPlanNo());
        requireSizingYarnInwardReferences(dto.getYarnIssueDetails());
    }

    // private void applyStockReductions(YarnOutward outward) {
    //     if (outward == null || outward.getYarnOutwardStockDetails() == null) return;
    //     for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
    //         Long stockDetailId = detail.getYarnInwardStockDetailsId();
            
    //         // Fallback if yarnInwardStockDetailsId is null, use inwardId which stores SizingYarnInwardDetail ID
    //         if (stockDetailId == null && detail.getInwardId() != null) {
    //             stockDetailId = resolveYarnInwardStockDetailsId(detail.getInwardId(), outward.getOutwardNo());
    //         }
            
    //         if (stockDetailId != null) {
    //             yarnInwardStockDetailsRepository.findById(stockDetailId).ifPresent(inwardDetail -> {
    //                 BigDecimal qtyToReduce = detail.getIssuedQty() != null ? detail.getIssuedQty() : BigDecimal.ZERO;
    //                 BigDecimal bagsToReduce = detail.getIssuedBags() != null ? detail.getIssuedBags() : BigDecimal.ZERO;
    //                 inwardDetail.setAvailableQty(defaultZero(inwardDetail.getAvailableQty()).subtract(qtyToReduce));
    //                 inwardDetail.setAvailableBags(defaultZero(inwardDetail.getAvailableBags()).subtract(bagsToReduce));
    //                 yarnInwardStockDetailsRepository.save(inwardDetail);
    //             });
    //         }
    //     }
    // }

    private void applyStockReductions(YarnOutward outward) {
    if (outward == null || CollectionUtils.isEmpty(outward.getYarnOutwardStockDetails())) {
        return;
    }
    
    for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
        Long stockDetailId = detail.getYarnInwardStockDetailsId();
        
        if (stockDetailId != null) {
            yarnInwardStockDetailsRepository.findById(stockDetailId).ifPresent(inwardDetail -> {
                BigDecimal qtyToReduce = defaultZero(detail.getIssuedQty());
                BigDecimal bagsToReduce = defaultZero(detail.getIssuedBags());
                
                BigDecimal currentAvailableQty = defaultZero(inwardDetail.getAvailableQty());
                BigDecimal currentAvailableBags = defaultZero(inwardDetail.getAvailableBags());
                
                // Validate sufficient stock
                if (currentAvailableQty.compareTo(qtyToReduce) < 0) {
                    throw new IllegalStateException(
                        String.format("Insufficient available quantity for stock detail ID: %d. Available: %s, Required: %s",
                            stockDetailId, currentAvailableQty, qtyToReduce)
                    );
                }
                
                if (currentAvailableBags.compareTo(bagsToReduce) < 0) {
                    throw new IllegalStateException(
                        String.format("Insufficient available bags for stock detail ID: %d. Available: %s, Required: %s",
                            stockDetailId, currentAvailableBags, bagsToReduce)
                    );
                }
                
                inwardDetail.setAvailableQty(currentAvailableQty.subtract(qtyToReduce));
                inwardDetail.setAvailableBags(currentAvailableBags.subtract(bagsToReduce));
                
                yarnInwardStockDetailsRepository.save(inwardDetail);
                
//                // Log the reduction for debugging
//                log.debug("Reduced stock for stockDetailId: {} - Qty: {}, Bags: {}", 
//                    stockDetailId, qtyToReduce, bagsToReduce);
            });
        } else {
//            log.warn("Cannot apply stock reduction: yarnInwardStockDetailsId is null for outward detail");
        }
    }
}

    private void reverseStockReductions(YarnOutward outward) {
    if (outward == null || CollectionUtils.isEmpty(outward.getYarnOutwardStockDetails())) {
        return;
    }
    
    for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
        Long stockDetailId = detail.getYarnInwardStockDetailsId();
        
        if (stockDetailId != null) {
            yarnInwardStockDetailsRepository.findById(stockDetailId).ifPresent(inwardDetail -> {
                BigDecimal qtyToRestore = defaultZero(detail.getIssuedQty());
                BigDecimal bagsToRestore = defaultZero(detail.getIssuedBags());
                
                inwardDetail.setAvailableQty(defaultZero(inwardDetail.getAvailableQty()).add(qtyToRestore));
                inwardDetail.setAvailableBags(defaultZero(inwardDetail.getAvailableBags()).add(bagsToRestore));
                
                yarnInwardStockDetailsRepository.save(inwardDetail);
                
//                log.debug("Restored stock for stockDetailId: {} - Qty: {}, Bags: {}", 
//                    stockDetailId, qtyToRestore, bagsToRestore);
            });
        }
    }
}
}