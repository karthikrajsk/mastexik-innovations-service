package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.WeavingYarnOutwardDTO;
import com.tech3.erp.dto.WeavingYarnOutwardDetailDTO;
import com.tech3.erp.dto.WeavingYarnOutwardSummaryDTO;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.WeavingYarnOutward;
import com.tech3.erp.entity.WeavingYarnOutwardDetail;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnItemEntity;
import com.tech3.erp.entity.YarnReturn;
import com.tech3.erp.entity.YarnReturnItem;
import com.tech3.erp.entity.Uom;
import com.tech3.erp.entity.WeftInward;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.TransportRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.WeavingYarnOutwardRepository;
import com.tech3.erp.repository.WeavingYarnRequirementRepository;
import com.tech3.erp.repository.WeftInwardRepository;
import com.tech3.erp.repository.YarnReturnRequirementRepository;
import com.tech3.erp.entity.YarnReturnRequirement;
import com.tech3.erp.entity.WeavingYarnRequirement;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnItemRepository;
import com.tech3.erp.repository.YarnReturnItemRepository;
import com.tech3.erp.repository.YarnReturnRepository;
import com.tech3.erp.repository.UomRepository;
import com.tech3.erp.entity.YarnOutward;
import com.tech3.erp.entity.YarnOutwardStockDetails;
import com.tech3.erp.repository.YarnOutwardRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
import com.tech3.erp.util.PaginationHelper;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class WeavingYarnOutwardService {

    private final WeavingYarnOutwardRepository weavingYarnOutwardRepository;
    private final TransportRepository transportRepository;
    private final YarnOutwardRepository yarnOutwardRepository;
    private final YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;
        
    private final YarnReturnRepository yarnReturnRepository;
    private final YarnReturnItemRepository yarnReturnItemRepository;
    private final YarnReturnRequirementRepository yarnReturnRequirementRepository;
    private final VendorRepository vendorRepository;
    private final WeavingContractRepository weavingContractRepository;
    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
    private final WeftInwardRepository weftInwardRepository;
    private final WeavingYarnRequirementRepository weavingYarnRequirementRepository;
    private final YarnInventoryRepository yarnInventoryRepository;
    private final YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    private final YarnItemRepository yarnItemRepository;
    private final UomRepository uomRepository;
    private final CodesDetailRepository codesDetailRepository;
    private final ReferenceValidationService referenceValidationService;
    private final WeavingWorkOrderService weavingWorkOrderService;

    public WeavingYarnOutwardService(
            WeavingYarnOutwardRepository weavingYarnOutwardRepository,
            YarnReturnRepository yarnReturnRepository,
            YarnReturnItemRepository yarnReturnItemRepository,
            YarnReturnRequirementRepository yarnReturnRequirementRepository,
            VendorRepository vendorRepository,
            WeavingContractRepository weavingContractRepository,
            WeavingWorkOrderRepository weavingWorkOrderRepository,
            WeftInwardRepository weftInwardRepository,
            WeavingYarnRequirementRepository weavingYarnRequirementRepository,
            TransportRepository transportRepository,
            YarnOutwardRepository yarnOutwardRepository,
            YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository,
            YarnInventoryRepository yarnInventoryRepository,
            YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository,
            YarnItemRepository yarnItemRepository,
            UomRepository uomRepository,
            CodesDetailRepository codesDetailRepository,
            ReferenceValidationService referenceValidationService,
            WeavingWorkOrderService weavingWorkOrderService) {
        this.weavingYarnOutwardRepository = weavingYarnOutwardRepository;
        this.yarnReturnRepository = yarnReturnRepository;
        this.yarnReturnItemRepository = yarnReturnItemRepository;
        this.yarnReturnRequirementRepository = yarnReturnRequirementRepository;
        this.vendorRepository = vendorRepository;
        this.weavingContractRepository = weavingContractRepository;
        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
        this.weftInwardRepository = weftInwardRepository;
        this.weavingYarnRequirementRepository = weavingYarnRequirementRepository;
        this.transportRepository = transportRepository;
        this.yarnOutwardRepository = yarnOutwardRepository;
        this.yarnOutwardStockDetailsRepository = yarnOutwardStockDetailsRepository;
        this.yarnInventoryRepository = yarnInventoryRepository;
        this.yarnInwardStockDetailsRepository = yarnInwardStockDetailsRepository;
        this.yarnItemRepository = yarnItemRepository;
        this.uomRepository = uomRepository;
        this.codesDetailRepository = codesDetailRepository;
        this.referenceValidationService = referenceValidationService;
        this.weavingWorkOrderService = weavingWorkOrderService;
    }

    @Transactional
    public WeavingYarnOutwardDTO createWeavingYarnOutward(WeavingYarnOutwardDTO dto) {
        validateWeavingYarnOutward(dto);
        WeavingYarnOutward outward = mapDtoToEntity(dto, new WeavingYarnOutward());
        WeavingYarnOutward saved = weavingYarnOutwardRepository.save(outward);
        syncWeavingYarnReturn(saved);
        syncWeavingInventory(saved);
        syncYarnOutward(saved);
        applyStockReductions(saved.getOutwardNo());
        markWeavingWorkOrderReference(saved.getWeavingJobWorkOrderId());
        return mapEntityToDto(saved);
    }

    @Transactional
    public WeavingYarnOutwardDTO updateWeavingYarnOutward(Long id, WeavingYarnOutwardDTO dto) {
        validateWeavingYarnOutward(dto);
        WeavingYarnOutward existing = weavingYarnOutwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Outward not found with id: " + id));
        reverseStockReductions(existing.getOutwardNo());
        mapDtoToEntity(dto, existing);
        WeavingYarnOutward saved = weavingYarnOutwardRepository.save(existing);
        syncWeavingYarnReturn(saved);
        syncWeavingInventory(saved);
        syncYarnOutward(saved);
        applyStockReductions(saved.getOutwardNo());
        markWeavingWorkOrderReference(saved.getWeavingJobWorkOrderId());
        return mapEntityToDto(saved);
    }

    @Transactional
    public WeavingYarnOutwardDTO updateWeavingYarnOutwardByCompany(Long factoryCompanyId, Long id, WeavingYarnOutwardDTO dto) {
        validateWeavingYarnOutward(dto);
        WeavingYarnOutward existing = weavingYarnOutwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Outward not found for company"));
        reverseStockReductions(existing.getOutwardNo());
        dto.setFactoryCompanyId(factoryCompanyId);
        mapDtoToEntity(dto, existing);
        WeavingYarnOutward saved = weavingYarnOutwardRepository.save(existing);
        syncWeavingYarnReturn(saved);
        syncWeavingInventory(saved);
        syncYarnOutward(saved);
        applyStockReductions(saved.getOutwardNo());
        markWeavingWorkOrderReference(saved.getWeavingJobWorkOrderId());
        return mapEntityToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<WeavingYarnOutwardDTO> getAllWeavingYarnOutwards() {
        return weavingYarnOutwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<WeavingYarnOutwardDTO> getAllWeavingYarnOutwardsWithPagination(Long offset, Long limit) {
        return PaginationHelper.paginate(getAllWeavingYarnOutwards(), offset, limit);
    }

    @Transactional(readOnly = true)
    public List<WeavingYarnOutwardDTO> getWeavingYarnOutwardsByCompany(Long factoryCompanyId) {
        if (factoryCompanyId == null) {
            throw new IllegalArgumentException("companyId is required");
        }
        return weavingYarnOutwardRepository.findByFactoryCompanyIdOrderByIdDesc(factoryCompanyId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<WeavingYarnOutwardDTO> getWeavingYarnOutwardsByCompanyWithPagination(Long factoryCompanyId, Long offset, Long limit) {
        return PaginationHelper.paginate(getWeavingYarnOutwardsByCompany(factoryCompanyId), offset, limit);
    }

    @Transactional(readOnly = true)
    public WeavingYarnOutwardDTO getWeavingYarnOutwardById(Long id) {
        return weavingYarnOutwardRepository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Outward not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public WeavingYarnOutwardDTO getWeavingYarnOutwardByCompany(Long factoryCompanyId, Long id) {
        return weavingYarnOutwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .map(this::mapEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Outward not found for company"));
    }

    @Transactional
    public void deleteWeavingYarnOutward(Long id) {
        WeavingYarnOutward existing = weavingYarnOutwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Outward not found with id: " + id));
        String outwardNo = existing.getOutwardNo();
        reverseStockReductions(outwardNo);
        weavingYarnOutwardRepository.delete(existing);
        deleteSyncYarnOutward(outwardNo);
    }

    @Transactional
    public void deleteWeavingYarnOutwardByCompany(Long factoryCompanyId, Long id) {
        WeavingYarnOutward existing = weavingYarnOutwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Yarn Outward not found for company"));
        String outwardNo = existing.getOutwardNo();
        reverseStockReductions(outwardNo);
        weavingYarnOutwardRepository.delete(existing);
        deleteSyncYarnOutward(outwardNo);
    }

    private WeavingYarnOutward mapDtoToEntity(WeavingYarnOutwardDTO dto, WeavingYarnOutward entity) {
        if (StringUtils.hasText(dto.getOutwardNo())) {
            entity.setOutwardNo(dto.getOutwardNo());
        }
        if (dto.getOutwardDate() != null) {
            entity.setOutwardDate(dto.getOutwardDate());
        }
        if (dto.getOrderTypeId() != null) {
            entity.setOrderTypeId(dto.getOrderTypeId());
        }
        if (dto.getWeavingJobWorkOrderId() != null) {
            entity.setWeavingJobWorkOrderId(dto.getWeavingJobWorkOrderId());
        }
        if (dto.getWeavingContractId() != null) {
            entity.setWeavingContractId(dto.getWeavingContractId());
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
        if (StringUtils.hasText(dto.getVehicleNo())) {
            entity.setVehicleNo(dto.getVehicleNo());
        }
        if (dto.getTransportId() != null) {
            entity.setTransportId(dto.getTransportId());
            transportRepository.findById(dto.getTransportId()).ifPresent(transport -> {
                entity.setTransportName(transport.getTransportName());
            });
        } else if (StringUtils.hasText(dto.getTransportName())) {
            entity.setTransportName(dto.getTransportName());
        }
        if (StringUtils.hasText(dto.getRemarks())) {
            entity.setRemarks(dto.getRemarks());
        }
        if (StringUtils.hasText(dto.getInwardType())) {
            entity.setInwardType(dto.getInwardType());
        } else if (!StringUtils.hasText(entity.getInwardType())) {
            entity.setInwardType("WEFT_YARN_RETURN");
        }
        if (dto.getSummary() != null) {
            entity.setIssuedBags(dto.getSummary().getIssuedBags());
            entity.setIssuedQuantity(dto.getSummary().getIssuedQuantity());
        }

        List<WeavingYarnOutwardDetail> currentDetails = entity.getYarnIssueDetails();
        Map<Long, WeavingYarnOutwardDetail> existingById = currentDetails.stream()
                .filter(detail -> detail.getId() != null)
                .collect(Collectors.toMap(WeavingYarnOutwardDetail::getId, detail -> detail, (a, b) -> a, HashMap::new));

        currentDetails.clear();

        if (!CollectionUtils.isEmpty(dto.getYarnIssueDetails())) {
            for (WeavingYarnOutwardDetailDTO detailDTO : dto.getYarnIssueDetails()) {
                WeavingYarnOutwardDetail detail = detailDTO.getId() != null ? existingById.get(detailDTO.getId()) : null;
                if (detail == null) {
                    detail = new WeavingYarnOutwardDetail();
                }
                mapDetailDtoToEntity(detailDTO, detail);
                entity.addDetail(detail);
            }
        }

        return entity;
    }

    private void mapDetailDtoToEntity(WeavingYarnOutwardDetailDTO dto, WeavingYarnOutwardDetail entity) {
        if (dto.getWeftInwardId() != null) {
            entity.setWeftInwardId(dto.getWeftInwardId());
            weftInwardRepository.findById(dto.getWeftInwardId())
                    .ifPresent(weftInward -> entity.setWeavingYarnIssueId(weftInward.getWeavingYarnIssueId()));
        } else if (StringUtils.hasText(dto.getWeftInwardNo())) {
            weftInwardRepository.findByInwardNo(dto.getWeftInwardNo())
                    .ifPresent(weftInward -> entity.setWeavingYarnIssueId(weftInward.getWeavingYarnIssueId()));
        }
        if (StringUtils.hasText(dto.getWeftInwardNo())) {
            entity.setWeftInwardNo(dto.getWeftInwardNo());
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
        entity.setMillId(dto.getMillId());
        if (StringUtils.hasText(dto.getMillName())) {
            entity.setMillName(dto.getMillName());
        }
        if (StringUtils.hasText(dto.getLotNo())) {
            entity.setLotNo(dto.getLotNo());
        }
        entity.setWarehouseId(dto.getWarehouseId());
    }

    private WeavingYarnOutwardDTO mapEntityToDto(WeavingYarnOutward entity) {
        WeavingYarnOutwardDTO dto = new WeavingYarnOutwardDTO(entity);
        if (dto.getSummary() == null) {
            dto.setSummary(new WeavingYarnOutwardSummaryDTO(entity.getIssuedBags(), entity.getIssuedQuantity()));
        }
        List<WeavingYarnOutwardDetail> details = entity.getYarnIssueDetails();
        if (details == null) {
            dto.setYarnIssueDetails(new java.util.ArrayList<>());
        } else {
            dto.setYarnIssueDetails(details.stream()
                    .sorted(Comparator.comparing(detail -> detail.getId() == null ? 0L : detail.getId()))
                    .map(WeavingYarnOutwardDetailDTO::new)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private void syncWeavingYarnReturn(WeavingYarnOutward outward) {
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
        Long warehouseId = resolveWeavingWarehouseId(outward.getYarnIssueDetails());

        yarnReturn.setInwardNumber(inwardNumber);
        yarnReturn.setInwardDate(outward.getOutwardDate() != null ? outward.getOutwardDate() : LocalDate.now());
        yarnReturn.setInwardType(StringUtils.hasText(outward.getInwardType()) ? outward.getInwardType() : "WEFT_YARN_RETURN");
        yarnReturn.setOrderNo(resolveWeavingOrderNo(outward));
        yarnReturn.setVendorId(vendorId);
        yarnReturn.setVendorName(vendorName);
        yarnReturn.setMillId(resolveWeavingMillId(outward.getYarnIssueDetails()));
        yarnReturn.setWarehouseId(warehouseId);
        Long weavingYarnIssueId = resolveWeavingYarnIssueId(outward.getYarnIssueDetails());
        List<WeavingYarnRequirement> weavingRequirements = weavingYarnIssueId != null
                ? weavingYarnRequirementRepository.findByWeavingYarnIssueId(weavingYarnIssueId)
                : Collections.emptyList();

        double issuedQty;
        if (!weavingRequirements.isEmpty()) {
            issuedQty = weavingRequirements.stream()
                    .filter(r -> r.getTotalIssueQty() != null)
                    .mapToDouble(r -> r.getTotalIssueQty().doubleValue())
                    .sum();
        } else {
            issuedQty = outward.getIssuedQuantity() != null ? outward.getIssuedQuantity().doubleValue() : 0d;
        }

        yarnReturn.setIssuedQty(issuedQty);
        yarnReturn.setRemarks(null);
        yarnReturn.setDcNumber(null);
        yarnReturn.setDcDate(null);
        yarnReturn.setCompanyId(companyId);
        yarnReturn.setActiveFlag(Boolean.TRUE);

        if (existing && !CollectionUtils.isEmpty(yarnReturn.getYarnReturnItems())) {
            yarnReturnItemRepository.deleteAll(yarnReturn.getYarnReturnItems());
            yarnReturn.getYarnReturnItems().clear();
        }

        if (existing && !CollectionUtils.isEmpty(yarnReturn.getYarnReturnRequirements())) {
            yarnReturnRequirementRepository.deleteAll(yarnReturn.getYarnReturnRequirements());
            yarnReturn.getYarnReturnRequirements().clear();
        }

        yarnReturn.setYarnReturnItems(buildWeavingYarnReturnItems(outward, yarnReturn));
        yarnReturn.setYarnReturnRequirements(buildYarnReturnRequirements(weavingRequirements, yarnReturn));
        yarnReturnRepository.save(yarnReturn);
    }

    private void syncWeavingInventory(WeavingYarnOutward outward) {
        if (outward == null || !StringUtils.hasText(outward.getOutwardNo())) {
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
        inventory.setInwardId(outward.getId());
        inventory.setCompanyId(companyId);
        inventory.setInwardTypeId(inwardTypeId);
        LocalDate date = outward.getOutwardDate() != null ? outward.getOutwardDate() : LocalDate.now();
        LocalDateTime dateTime = date.atStartOfDay();
        inventory.setInwardDate(dateTime);
        inventory.setFinancialYear(calculateFinancialYear(date));
        inventory.setOrderId(resolveWeavingInventoryOrderId(outward));
        inventory.setOrderNo(resolveWeavingOrderNo(outward));
        inventory.setVendorId(resolveVendorId(outward));
        Long warehouseId = resolveWeavingWarehouseId(outward.getYarnIssueDetails());
        inventory.setWarehouseId(warehouseId);
        inventory.setDcNo(null);
        inventory.setDcDate(null);

        if (inventory.getDetails() == null) {
            inventory.setDetails(new ArrayList<>());
        }

        updateWeavingInventoryDetails(outward, inventory, inwardTypeId, warehouseId);

        if (inventory.getDetails().isEmpty()) {
            deleteInventoryByInwardNo(outward.getOutwardNo());
            return;
        }

        yarnInventoryRepository.save(inventory);
    }

    private void updateWeavingInventoryDetails(WeavingYarnOutward outward, YarnInventory inventory, Long inwardTypeId, Long defaultWarehouseId) {
        List<YarnInwardStockDetails> existingDetails = inventory.getDetails();
        Map<String, YarnInwardStockDetails> existingMap = new HashMap<>();
        for (YarnInwardStockDetails d : existingDetails) {
            String key = d.getItemId() + "-" + d.getLotNo() + "-" + d.getMillId() + "-" + d.getWarehouseId();
            existingMap.put(key, d);
        }

        List<YarnInwardStockDetails> updatedDetails = new ArrayList<>();
        Map<Long, Long> warehouseCache = new HashMap<>();

        for (WeavingYarnOutwardDetail detail : outward.getYarnIssueDetails()) {
            if (detail == null || detail.getYarnId() == null) {
                continue;
            }
            BigDecimal issuedQty = defaultZero(detail.getIssuedQuantity());
            if (issuedQty.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }

            Long resolvedWarehouseId = resolveWeavingDetailWarehouseId(detail, warehouseCache);
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
                stock.setOrderItemId(resolveYarnOrderItemId(detail.getWeftInwardId()));
                
                stock.setInwardQty(issuedQty);
                stock.setInwardBags(defaultZero(detail.getIssuingBags()));
                stock.setAcceptedQty(issuedQty);
                stock.setAvailableQty(issuedQty);
                stock.setAvailableBags(defaultZero(detail.getIssuingBags()));
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

    private void syncYarnOutward(WeavingYarnOutward outward) {
        if (outward == null || !StringUtils.hasText(outward.getOutwardNo())) {
            return;
        }

        YarnOutward yarnOutward = yarnOutwardRepository.findByOutwardNo(outward.getOutwardNo())
                .orElseGet(YarnOutward::new);

        Long outwardTypeId = codesDetailRepository.findByValue("WEAVING_YARN_OUTWARD")
                .map(CodesDetail::getId)
                .orElseGet(() -> codesDetailRepository.findByValue("WEAVING_YARN_OUTWARD")
                        .map(CodesDetail::getId)
                        .orElse(56L));

        yarnOutward.setOutwardNo(outward.getOutwardNo());
        yarnOutward.setOutwardDate(outward.getOutwardDate() != null ? outward.getOutwardDate() : LocalDate.now());
        yarnOutward.setOutwardTypeId(outwardTypeId);
        yarnOutward.setCompanyId(resolveCompanyId(outward));
        yarnOutward.setOrderId(resolveWeavingInventoryOrderId(outward));
        yarnOutward.setOrderNo(resolveWeavingOrderNo(outward));
        yarnOutward.setVendorId(resolveVendorId(outward));
        yarnOutward.setRemarks(outward.getRemarks());
        yarnOutward.setActiveFlag(true);
        yarnOutward.setFinancialYearId(calculateFinancialYear(outward.getOutwardDate()));

        if (yarnOutward.getYarnOutwardStockDetails() != null) {
            yarnOutward.getYarnOutwardStockDetails().clear();
        }

        List<YarnOutwardStockDetails> stockDetails = buildYarnOutwardStockDetails(outward, yarnOutward, outwardTypeId);
        if (!CollectionUtils.isEmpty(stockDetails)) {
            if (yarnOutward.getYarnOutwardStockDetails() == null) {
                yarnOutward.setYarnOutwardStockDetails(new ArrayList<>(stockDetails));
            } else {
                yarnOutward.getYarnOutwardStockDetails().addAll(stockDetails);
            }
        }

        yarnOutwardRepository.save(yarnOutward);
    }

    private List<YarnOutwardStockDetails> buildYarnOutwardStockDetails(WeavingYarnOutward outward, YarnOutward yarnOutward, Long outwardTypeId) {
        if (CollectionUtils.isEmpty(outward.getYarnIssueDetails())) {
            return new ArrayList<>();
        }

        List<YarnOutwardStockDetails> details = new ArrayList<>();
        for (WeavingYarnOutwardDetail detail : outward.getYarnIssueDetails()) {
            YarnOutwardStockDetails stock = new YarnOutwardStockDetails();
            stock.setYarnOutward(yarnOutward);
            
            boolean idsFetched = false;
            if (StringUtils.hasText(detail.getWeftInwardNo())) {
                Optional<YarnInventory> inventoryOpt = yarnInventoryRepository.findByInwardNo(detail.getWeftInwardNo());
                if (inventoryOpt.isPresent()) {
                    YarnInventory inventory = inventoryOpt.get();
                    List<YarnInwardStockDetails> stockDetailsList = yarnInwardStockDetailsRepository.findByYarnInventoryAndItemIdAndLotNo(
                            inventory, detail.getYarnId(), detail.getLotNo());
                    
                    if (stockDetailsList.isEmpty()) {
                        stockDetailsList = yarnInwardStockDetailsRepository.findByYarnInventoryIdAndItemIdAndWarehouseId(
                                inventory.getId(), detail.getYarnId(), detail.getWarehouseId());
                    }
                    
                    if (stockDetailsList.isEmpty()) {
                        stockDetailsList = yarnInwardStockDetailsRepository.findByYarnInventoryId(inventory.getId()).stream()
                                .filter(d -> Objects.equals(d.getItemId(), detail.getYarnId()))
                                .collect(Collectors.toList());
                    }

                    if (!stockDetailsList.isEmpty()) {
                        YarnInwardStockDetails matchedDetail = stockDetailsList.get(0);
                        stock.setInwardId(matchedDetail.getInwardId());
                        stock.setOrderItemId(matchedDetail.getOrderItemId());
                        stock.setYarnInwardStockDetailsId(matchedDetail.getId());
                        idsFetched = true;
                    }
                }
            }

            if (!idsFetched) {
                stock.setInwardId(detail.getWeftInwardId());
                stock.setOrderItemId(detail.getWeftInwardId());
                stock.setYarnInwardStockDetailsId(resolveYarnInwardStockDetailsId(detail.getWeftInwardId()));
            }

            stock.setOutwardTypeId(outwardTypeId);
            stock.setActualItemId(detail.getYarnId());
            stock.setMillId(detail.getMillId());
            stock.setWarehouseId(detail.getWarehouseId() != null ? detail.getWarehouseId() : resolveWeavingWarehouseId(outward.getYarnIssueDetails()));
            stock.setIssuedQty(detail.getIssuedQuantity());
            stock.setIssuedBags(detail.getIssuingBags());
            stock.setKgsPerBag(detail.getKgsPerBag());
            stock.setConesWeight(detail.getConeWeight());
            details.add(stock);
        }
        return details;
    }

    private Long resolveYarnInwardStockDetailsId(Long weftInwardId) {
        if (weftInwardId == null) {
            return null;
        }
        return yarnInwardStockDetailsRepository.findFirstByOrderItemId(weftInwardId)
                .map(YarnInwardStockDetails::getId)
                .orElse(null);
    }

    private void deleteSyncYarnOutward(String outwardNo) {
        if (!StringUtils.hasText(outwardNo)) {
            return;
        }
        yarnOutwardRepository.findByOutwardNo(outwardNo).ifPresent(existing -> {
            yarnOutwardRepository.delete(existing);
        });
    }

    private Long resolveWeavingWarehouseId(List<WeavingYarnOutwardDetail> details) {
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        Map<Long, Long> warehouseCache = new HashMap<>();
        for (WeavingYarnOutwardDetail detail : details) {
            Long resolved = resolveWeavingDetailWarehouseId(detail, warehouseCache);
            if (resolved != null) {
                return resolved;
            }
        }
        return null;
    }

    private Long resolveWeavingDetailWarehouseId(WeavingYarnOutwardDetail detail, Map<Long, Long> warehouseCache) {
        if (detail == null) {
            return null;
        }
        if (detail.getWarehouseId() != null) {
            return detail.getWarehouseId();
        }
        Long orderItemId = detail.getWeftInwardId();
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
        return codesDetailRepository.findByValue("WEAVING_YARN_RETURN")
                .map(detail -> detail.getId())
                .orElse(6L);
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

    private Long resolveCompanyId(WeavingYarnOutward outward) {
        return outward.getBuyerId();
    }

    private Long resolveVendorId(WeavingYarnOutward outward) {
        Long vendorId = outward.getVendorId();
        if (vendorId == null) {
            vendorId = outward.getBuyerId();
        }
        if (vendorId == null) {
            vendorId = outward.getTradingCompanyId() != null ? outward.getTradingCompanyId() : outward.getFactoryCompanyId();
        }
        return vendorId;
    }

    private Long resolveWeavingInventoryOrderId(WeavingYarnOutward outward) {
        if (outward.getWeavingContractId() != null) {
            return outward.getWeavingContractId();
        }
        if (outward.getWeavingJobWorkOrderId() != null) {
            return outward.getWeavingJobWorkOrderId();
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

    private Long resolveWeavingYarnIssueId(List<WeavingYarnOutwardDetail> details) {
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        for (WeavingYarnOutwardDetail detail : details) {
            if (detail.getWeftInwardId() != null) {
                Optional<WeftInward> weftInward = weftInwardRepository.findById(detail.getWeftInwardId());
                if (weftInward.isPresent() && weftInward.get().getWeavingYarnIssueId() != null) {
                    return weftInward.get().getWeavingYarnIssueId();
                }
            }
        }
        return null;
    }

    private List<YarnReturnRequirement> buildYarnReturnRequirements(
            List<WeavingYarnRequirement> weavingRequirements, YarnReturn yarnReturn) {
        if (CollectionUtils.isEmpty(weavingRequirements)) {
            return new ArrayList<>();
        }
        List<YarnReturnRequirement> result = new ArrayList<>();
        for (WeavingYarnRequirement req : weavingRequirements) {
            YarnReturnRequirement requirement = new YarnReturnRequirement();
            requirement.setYarnReturn(yarnReturn);
            requirement.setYarnName(req.getYarnName());
            requirement.setActualYarnId(req.getActualYarnId());
            requirement.setActualYarnName(req.getActualYarn());
            requirement.setIssuedQty(req.getTotalIssueQty() != null ? req.getTotalIssueQty().doubleValue() : null);
            result.add(requirement);
        }
        return result;
    }

    private List<YarnReturnItem> buildWeavingYarnReturnItems(WeavingYarnOutward outward, YarnReturn yarnReturn) {
        if (CollectionUtils.isEmpty(outward.getYarnIssueDetails())) {
            return new ArrayList<>();
        }
        return outward.getYarnIssueDetails().stream()
                .map(detail -> mapWeavingDetailToYarnReturnItem(detail, outward, yarnReturn))
                .collect(Collectors.toList());
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

    private String resolveWeavingContractNo(Long weavingContractId) {
        if (weavingContractId == null) {
            return null;
        }
        return weavingContractRepository.findById(weavingContractId)
                .map(contract -> contract.getWeavingContractNo())
                .orElse(null);
    }

    private YarnReturnItem mapWeavingDetailToYarnReturnItem(
            WeavingYarnOutwardDetail detail, WeavingYarnOutward outward, YarnReturn yarnReturn) {
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
        item.setOrderItemId(detail.getWeftInwardId());
        item.setYarnOutwardId(outward.getId());
        item.setYarnOutwardStockdetailsId(detail.getId());
        item.setWeavingYarnIssueId(detail.getWeavingYarnIssueId());
        return item;
    }

    private String resolveWeavingOrderNo(WeavingYarnOutward outward) {
        String contractNo = resolveWeavingContractNo(outward.getWeavingContractId());
        if (StringUtils.hasText(contractNo)) {
            return contractNo;
        }
        if (outward.getWeavingContractId() != null) {
            return outward.getWeavingContractId().toString();
        }
        if (outward.getWeavingJobWorkOrderId() != null) {
            return outward.getWeavingJobWorkOrderId().toString();
        }
        if (outward.getOrderTypeId() != null) {
            return outward.getOrderTypeId().toString();
        }
        return StringUtils.hasText(outward.getOutwardNo()) ? outward.getOutwardNo() : "WEAVING";
    }

    private Long resolveWeavingMillId(List<WeavingYarnOutwardDetail> details) {
        if (CollectionUtils.isEmpty(details)) {
            return null;
        }
        return details.stream()
                .map(WeavingYarnOutwardDetail::getMillId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private void requireWeavingContractReference(Long weavingContractId) {
        if (weavingContractId == null) {
            return;
        }
        Optional<WeavingContract> weavingContractOpt = weavingContractRepository.findById(weavingContractId);
        referenceValidationService.requireReference(
                weavingContractId,
                () -> weavingContractOpt,
                "Weaving Contract",
                weavingContractOpt.map(WeavingContract::getWeavingContractNo)
                        .filter(StringUtils::hasText)
                        .orElse(String.valueOf(weavingContractId)));
    }

    private void requireWeavingWorkOrderReference(Long weavingWorkOrderId) {
        if (weavingWorkOrderId == null) {
            return;
        }
        Optional<WeavingWorkOrder> weavingWorkOrderOpt = weavingWorkOrderRepository.findById(weavingWorkOrderId);
        referenceValidationService.requireReference(
                weavingWorkOrderId,
                () -> weavingWorkOrderOpt,
                "Weaving Work Order",
                weavingWorkOrderOpt.map(WeavingWorkOrder::getWeavingWorkOrderNo)
                        .filter(StringUtils::hasText)
                        .orElse(String.valueOf(weavingWorkOrderId)));
    }

    private void markWeavingWorkOrderReference(Long weavingWorkOrderId) {
        if (weavingWorkOrderId != null) {
            weavingWorkOrderService.markAsOrderIdMapped(weavingWorkOrderId);
        }
    }

    private void requireWeftInwardReferences(List<WeavingYarnOutwardDetailDTO> details) {
        if (CollectionUtils.isEmpty(details)) {
            return;
        }
        for (WeavingYarnOutwardDetailDTO detail : details) {
            requireWeftInwardReference(detail);
        }
    }

    private void requireWeftInwardReference(WeavingYarnOutwardDetailDTO detail) {
        if (detail == null) {
            return;
        }
        if (detail.getWeftInwardId() != null) {
            Optional<WeftInward> weftInwardOpt = weftInwardRepository.findById(detail.getWeftInwardId());
            referenceValidationService.requireReference(
                    detail.getWeftInwardId(),
                    () -> weftInwardOpt,
                    "Weft Inward",
                    weftInwardOpt.map(WeftInward::getInwardNo)
                            .filter(StringUtils::hasText)
                            .orElse(detail.getWeftInwardNo()));
            return;
        }
        if (StringUtils.hasText(detail.getWeftInwardNo())) {
            Optional<WeftInward> weftInwardOpt = weftInwardRepository.findByInwardNo(detail.getWeftInwardNo());
            referenceValidationService.requireReference(
                    detail.getWeftInwardNo(),
                    () -> weftInwardOpt,
                    "Weft Inward",
                    detail.getWeftInwardNo());
        }
    }

    private void validateWeavingYarnOutward(WeavingYarnOutwardDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Weaving Yarn Outward payload is required");
        }
        if (dto.getOutwardDate() == null) {
            throw new IllegalArgumentException("Outward date is required");
        }
        if (dto.getSummary() == null) {
            throw new IllegalArgumentException("Summary is required");
        }
        if (CollectionUtils.isEmpty(dto.getYarnIssueDetails())) {
            throw new IllegalArgumentException("At least one yarn issue detail is required");
        }
        requireWeavingContractReference(dto.getWeavingContractId());
        requireWeavingWorkOrderReference(dto.getWeavingJobWorkOrderId());
        requireWeftInwardReferences(dto.getYarnIssueDetails());
    }

    private void applyStockReductions(String outwardNo) {
        if (!StringUtils.hasText(outwardNo)) return;
        yarnOutwardRepository.findByOutwardNo(outwardNo).ifPresent(outward -> {
            if (outward.getYarnOutwardStockDetails() != null) {
                for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
                    if (detail.getYarnInwardStockDetailsId() != null) {
                        yarnInwardStockDetailsRepository.findById(detail.getYarnInwardStockDetailsId()).ifPresent(inwardDetail -> {
                            BigDecimal qtyToReduce = detail.getIssuedQty() != null ? detail.getIssuedQty() : BigDecimal.ZERO;
                            BigDecimal bagsToReduce = detail.getIssuedBags() != null ? detail.getIssuedBags() : BigDecimal.ZERO;
                            inwardDetail.setAvailableQty(defaultZero(inwardDetail.getAvailableQty()).subtract(qtyToReduce));
                            inwardDetail.setAvailableBags(defaultZero(inwardDetail.getAvailableBags()).subtract(bagsToReduce));
                            yarnInwardStockDetailsRepository.save(inwardDetail);
                        });
                    }
                }
            }
        });
    }

    private void reverseStockReductions(String outwardNo) {
        if (!StringUtils.hasText(outwardNo)) return;
        yarnOutwardRepository.findByOutwardNo(outwardNo).ifPresent(outward -> {
            if (outward.getYarnOutwardStockDetails() != null) {
                for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
                    if (detail.getYarnInwardStockDetailsId() != null) {
                        yarnInwardStockDetailsRepository.findById(detail.getYarnInwardStockDetailsId()).ifPresent(inwardDetail -> {
                            BigDecimal qtyToRestore = detail.getIssuedQty() != null ? detail.getIssuedQty() : BigDecimal.ZERO;
                            BigDecimal bagsToRestore = detail.getIssuedBags() != null ? detail.getIssuedBags() : BigDecimal.ZERO;
                            inwardDetail.setAvailableQty(defaultZero(inwardDetail.getAvailableQty()).add(qtyToRestore));
                            inwardDetail.setAvailableBags(defaultZero(inwardDetail.getAvailableBags()).add(bagsToRestore));
                            yarnInwardStockDetailsRepository.save(inwardDetail);
                        });
                    }
                }
            }
        });
    }
}
