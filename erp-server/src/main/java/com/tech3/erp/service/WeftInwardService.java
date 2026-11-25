package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.WeftInwardDTO;
import com.tech3.erp.dto.WeftInwardItemDTO;
import com.tech3.erp.entity.WeftInward;
import com.tech3.erp.entity.WeftInwardItem;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.WeftInwardRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.WeavingYarnOutwardDetailRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
import com.tech3.erp.service.WeavingWorkOrderService;
import com.tech3.erp.service.WeavingYarnIssueService;
import com.tech3.erp.service.NotificationService;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeftInwardService {

    @Autowired
    private WeftInwardRepository weftInwardRepository;

    @Autowired
    private WeavingYarnOutwardDetailRepository weavingYarnOutwardDetailRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Autowired
    private WeavingContractRepository weavingContractRepository;

    @Autowired
    private WeavingWorkOrderRepository weavingWorkOrderRepository;

    @Autowired
    private WeavingWorkOrderService weavingWorkOrderService;

    @Autowired
    private WeavingYarnIssueService weavingYarnIssueService;

    @Autowired
    private YarnInventoryRepository yarnInventoryRepository;

    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;

    @Autowired
    private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private NotificationService notificationService;

    @Transactional
    public WeftInward createWeftInward(WeftInwardDTO dto) {
        WeftInward entity = new WeftInward();
        entity.setInwardDate(parseDate(dto.getInwardDate(), LocalDate.now()));
        entity.setWeavingContractId(parseLong(dto.getWeavingContractId()));
        entity.setWeavingJobWorkOrderId(parseLong(dto.getWeavingJobWorkOrderId()));
        entity.setWeavingYarnIssueId(parseLong(dto.getWeavingYarnIssueId()));
        entity.setFabricCode(dto.getFabricCode());
        entity.setDcNo(dto.getDcNo());
        entity.setDcDate(parseDate(dto.getDcDate(), null));
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        entity.setOrderTypeId(parseLong(dto.getOrderTypeId()));
        entity.setCompanyId(parseLong(dto.getCompanyId()));
        entity.setBuyerId(parseLong(dto.getBuyerId()));
        entity.setSourceCompanyId(parseLong(dto.getSourceCompanyId()));
        entity.setDestinationCompanyId(parseLong(dto.getDestinationCompanyId()));
        entity.setLinkedBeamInwardId(parseLong(dto.getLinkedBeamInwardId()));
        entity.setTradingCompanyId(parseLong(dto.getTradingCompanyId()));
        entity.setFactoryCompanyId(parseLong(dto.getFactoryCompanyId()));
        entity.setInwardNo(dto.getInwardNo());

        List<WeftInwardItem> items = new ArrayList<>();
        if (dto.getYarnDetails() != null) {
            for (WeftInwardItemDTO itemDto : dto.getYarnDetails()) {
                WeftInwardItem item = new WeftInwardItem();
                item.setYarnId(itemDto.getYarnId());
                item.setYarnName(itemDto.getActualYarn());
                item.setActualYarnId(itemDto.getActualYarnId());
                item.setActualYarn(itemDto.getActualYarn());
                item.setNoOfBags(itemDto.getBags());
                item.setKgsPerBag(itemDto.getKgsPerBag());
                item.setTotalKgs(itemDto.getTotalKgs());
                item.setMillId(itemDto.getMillId());
                item.setMillName(itemDto.getMillName());
                item.setWarehouseId(itemDto.getWarehouseId());
                item.setLotNo(itemDto.getLotNo());
                item.setWeftInward(entity);
                items.add(item);
            }
        }
        entity.setYarnItems(items);

        // Derive companyId by business rule:
        // Internal (orderTypeId=1) → companyId = factoryCompanyId
        // External (orderTypeId=2) → companyId = dto.companyId (logged-in company)
        if (entity.getOrderTypeId() != null && entity.getOrderTypeId() == 1L) {
            entity.setCompanyId(entity.getFactoryCompanyId());
        }
        // orderTypeId=2: companyId already set from dto above, no change needed

        validateWeavingReferences(dto);
        WeftInward saved = weftInwardRepository.save(entity);
        if (saved.getOrderTypeId() != null && saved.getOrderTypeId() == 2L) {
            syncToYarnInventory(saved, dto);
        }
        markWeavingWorkOrderReference(saved.getWeavingJobWorkOrderId());
        if (saved.getFactoryCompanyId() != null && saved.getTradingCompanyId() != null) {
            try {
                double totalKgs = saved.getYarnItems() == null ? 0.0 :
                    saved.getYarnItems().stream()
                        .mapToDouble(i -> i.getTotalKgs() != null ? i.getTotalKgs() : 0.0)
                        .sum();
                BigDecimal totalQty = totalKgs > 0 ? BigDecimal.valueOf(totalKgs) : null;
                notificationService.createAndPush(
                    saved.getFactoryCompanyId(),
                    saved.getTradingCompanyId(),
                    NotificationService.EVENT_WEFT_INWARD,
                    saved.getId(),
                    totalQty,
                    "Kg"
                );
            } catch (Exception ignored) {}
        }
        return saved;
    }

    public List<WeftInwardDTO> getAllWeftInwards() {
        return weftInwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(WeftInwardDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WeftInwardDTO> getAllWeftInwardsWithPagination(Long offset, Long limit, Long factoryCompanyId) {
        List<WeftInwardDTO> list = getAllWeftInwards();
        if (factoryCompanyId != null) {
            String idStr = String.valueOf(factoryCompanyId);
            list = list.stream().filter(d -> idStr.equals(d.getFactoryCompanyId())).collect(Collectors.toList());
        }
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<WeftInwardDTO> getAllWeftInwardsByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return weftInwardRepository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(WeftInwardDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WeftInwardDTO> getAllWeftInwardsByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        long finalLimit = (limit != null && limit > 0) ? limit : PaginationHelper.getDefaultLimit();
        long finalOffset = (offset != null && offset > 0) ? offset : 0L;
        PageRequest pageRequest = PageRequest.of((int) (finalOffset / finalLimit), Math.toIntExact(finalLimit), Sort.by(Sort.Direction.DESC, "id"));
        Page<WeftInward> page = weftInwardRepository.findByCompanyId(companyId, pageRequest);
        return buildPaginatedResponse(page, finalOffset, finalLimit);
    }

    public List<WeftInwardDTO> getByDestinationCompany(Long destinationCompanyId) {
        return weftInwardRepository.findByDestinationCompanyId(destinationCompanyId).stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .map(WeftInwardDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WeftInwardDTO> getByFactoryCompanyIdWithPagination(Long factoryCompanyId, Long offset, Long limit) {
        List<WeftInwardDTO> list = getAllWeftInwards().stream()
                .filter(d -> factoryCompanyId == null || String.valueOf(factoryCompanyId).equals(d.getFactoryCompanyId()))
                .collect(Collectors.toList());
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<WeftInwardDTO> getByLinkedBeam(Long beamInwardId) {
        return weftInwardRepository.findByLinkedBeamInwardId(beamInwardId).stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .map(WeftInwardDTO::new)
                .collect(Collectors.toList());
    }

    public WeftInwardDTO getWeftInwardById(Long id) {
        Optional<WeftInward> weft = weftInwardRepository.findById(id);
        if (weft.isPresent()) {
            return new WeftInwardDTO(weft.get());
        } else {
            throw new EntityNotFoundException("Weft Inward not found with id: " + id);
        }
    }

    public WeftInwardDTO getWeftInwardByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        WeftInward entity = weftInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Weft Inward not found with id: " + id + " for company: " + companyId));
        return new WeftInwardDTO(entity);
    }

    @Transactional
    public void deleteWeftInward(Long id) {
        WeftInward entity = weftInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weft Inward not found with id: " + id));
        deleteWeftInwardEntity(entity);
    }

    @Transactional
    public WeftInward updateWeftInward(Long id, WeftInwardDTO dto, Long factoryCompanyId) {
        WeftInward entity = weftInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weft Inward not found with id: " + id));
        if (factoryCompanyId != null && (entity.getFactoryCompanyId() == null || !factoryCompanyId.equals(entity.getFactoryCompanyId()))) {
            throw new EntityNotFoundException("Weft Inward not found for the specified factoryCompanyId");
        }
        applyDtoToEntity(dto, entity);
        validateWeavingReferences(dto);
        WeftInward saved = weftInwardRepository.save(entity);
        if (saved.getWeavingYarnIssueId() != null) {
            weavingYarnIssueService.prepareOutwardReferencesForWeftInwardSync(saved.getWeavingYarnIssueId());
        }
        if (saved.getOrderTypeId() != null && saved.getOrderTypeId() == 2L) {
            syncToYarnInventory(saved, dto);
        }
        weavingYarnIssueService.syncYarnIssuesFromWeftInward(saved);
        markWeavingWorkOrderReference(saved.getWeavingJobWorkOrderId());
        return saved;
    }

    @Transactional
    public WeftInward updateWeftInwardByCompany(Long companyId, Long id, WeftInwardDTO dto) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        WeftInward entity = weftInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Weft Inward not found with id: " + id + " for company: " + companyId));
        applyDtoToEntity(dto, entity);
        validateWeavingReferences(dto);
        WeftInward saved = weftInwardRepository.save(entity);
        if (saved.getWeavingYarnIssueId() != null) {
            weavingYarnIssueService.prepareOutwardReferencesForWeftInwardSync(saved.getWeavingYarnIssueId());
        }
        if (saved.getOrderTypeId() != null && saved.getOrderTypeId() == 2L) {
            syncToYarnInventory(saved, dto);
        }
        weavingYarnIssueService.syncYarnIssuesFromWeftInward(saved);
        markWeavingWorkOrderReference(saved.getWeavingJobWorkOrderId());
        return saved;
    }

    @Transactional
    public void deleteWeftInward(Long id, Long factoryCompanyId) {
        WeftInward entity = weftInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weft Inward not found with id: " + id));
        if (factoryCompanyId != null && (entity.getFactoryCompanyId() == null || !factoryCompanyId.equals(entity.getFactoryCompanyId()))) {
            throw new EntityNotFoundException("Weft Inward not found for the specified factoryCompanyId");
        }
        deleteWeftInwardEntity(entity);
    }

    @Transactional
    public void deleteWeftInwardByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        WeftInward entity = weftInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Weft Inward not found with id: " + id + " for company: " + companyId));
        deleteWeftInwardEntity(entity);
    }

    public boolean isMapped(Long id) {
        if (id == null) {
            return false;
        }
        return weftInwardRepository.findById(id)
                .map(entity -> {
                    String inwardNo = StringUtils.hasText(entity.getInwardNo()) ? entity.getInwardNo().trim() : null;
                    if (inwardNo != null && weavingYarnOutwardDetailRepository.existsByWeftInwardNo(inwardNo)) {
                        return true;
                    }
                    Long inwardId = entity.getId();
                    return inwardId != null && weavingYarnOutwardDetailRepository.existsByWeftInwardId(inwardId);
                })
                .orElse(false);
    }

    private void deleteWeftInwardEntity(WeftInward entity) {
        ensureNoWeftInwardReferences(entity);
        try {
            deleteFromYarnInventory(entity);
            weftInwardRepository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Weft Inward because it is referenced by other entities", e);
        }
    }

    private void ensureNoWeftInwardReferences(WeftInward entity) {
        if (entity == null) {
            return;
        }
        Long id = entity.getId();
        String inwardNo = StringUtils.hasText(entity.getInwardNo()) ? entity.getInwardNo().trim() : null;
        if (inwardNo != null && weavingYarnOutwardDetailRepository.existsByWeftInwardNo(inwardNo)) {
            throw new IllegalStateException(inwardNo + " already mapped in Weaving Yarn Outward");
        }
        if (id != null && weavingYarnOutwardDetailRepository.existsByWeftInwardId(id)) {
            throw new IllegalStateException("Cannot delete Weft Inward because weaving_yarn_outward_detail references weft_inward_id " + id);
        }
    }

    private void applyDtoToEntity(WeftInwardDTO dto, WeftInward entity) {
        entity.setInwardDate(parseDate(dto.getInwardDate(), entity.getInwardDate()));
        entity.setWeavingContractId(parseLong(dto.getWeavingContractId()));
        entity.setWeavingJobWorkOrderId(parseLong(dto.getWeavingJobWorkOrderId()));
        if (dto.getWeavingYarnIssueId() != null) {
            entity.setWeavingYarnIssueId(parseLong(dto.getWeavingYarnIssueId()));
        }
        entity.setFabricCode(dto.getFabricCode());
        entity.setDcNo(dto.getDcNo());
        entity.setDcDate(parseDate(dto.getDcDate(), null));
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : entity.getActiveFlag());
        entity.setOrderTypeId(parseLong(dto.getOrderTypeId()));
        // companyId, factoryCompanyId, tradingCompanyId are immutable after creation — preserve DB values
        entity.setBuyerId(parseLong(dto.getBuyerId()));
        entity.setSourceCompanyId(parseLong(dto.getSourceCompanyId()));
        entity.setDestinationCompanyId(parseLong(dto.getDestinationCompanyId()));
        entity.setLinkedBeamInwardId(parseLong(dto.getLinkedBeamInwardId()));
        entity.setInwardNo(dto.getInwardNo());

        Map<Long, WeftInwardItem> existingItemsById = entity.getYarnItems().stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(WeftInwardItem::getId, item -> item, (first, second) -> first));
        Map<String, List<WeftInwardItem>> existingItemsByKey = entity.getYarnItems().stream()
                .collect(Collectors.groupingBy(this::buildWeftInwardItemKey));

        List<WeftInwardItem> updatedItems = new ArrayList<>();
        if (dto.getYarnDetails() != null) {
            for (WeftInwardItemDTO itemDto : dto.getYarnDetails()) {
                WeftInwardItem item = null;
                if (itemDto.getId() != null && itemDto.getId() != 0) {
                    item = existingItemsById.remove(itemDto.getId());
                }
                if (item == null) {
                    List<WeftInwardItem> matches = existingItemsByKey.get(buildWeftInwardItemKey(itemDto));
                    if (matches != null && !matches.isEmpty()) {
                        item = matches.remove(0);
                    }
                }
                if (item == null) {
                    item = new WeftInwardItem();
                }
                item.setWeftInward(entity);
                item.setYarnId(itemDto.getYarnId());
                item.setYarnName(itemDto.getActualYarn());
                item.setActualYarnId(itemDto.getActualYarnId());
                item.setActualYarn(itemDto.getActualYarn());
                item.setNoOfBags(itemDto.getBags());
                item.setKgsPerBag(itemDto.getKgsPerBag());
                item.setTotalKgs(itemDto.getTotalKgs());
                item.setMillId(itemDto.getMillId());
                item.setMillName(itemDto.getMillName() != null ? itemDto.getMillName() : item.getMillName());
                item.setWarehouseId(itemDto.getWarehouseId());
                item.setLotNo(itemDto.getLotNo());
                updatedItems.add(item);
            }
        }
        entity.getYarnItems().clear();
        entity.getYarnItems().addAll(updatedItems);
    }

    private void validateWeavingReferences(WeftInwardDTO dto) {
        if (dto == null) {
            return;
        }
        requireWeavingContractReference(dto.getWeavingContractId(), dto.getWeavingContractNo());
        requireWeavingWorkOrderReference(dto.getWeavingJobWorkOrderId(), dto.getWeavingWorkOrderNo());
    }

    private void requireWeavingContractReference(String weavingContractIdentifier, String weavingContractNo) {
        if (!StringUtils.hasText(weavingContractIdentifier)) {
            return;
        }
        Optional<WeavingContract> weavingContractOpt = findWeavingContract(weavingContractIdentifier);
        String displayIdentifier = StringUtils.hasText(weavingContractNo)
                ? weavingContractNo
                : weavingContractOpt.map(WeavingContract::getWeavingContractNo)
                        .filter(StringUtils::hasText)
                        .orElse(weavingContractIdentifier);
        referenceValidationService.requireReference(
                weavingContractIdentifier,
                () -> weavingContractOpt,
                "Weaving Contract",
                displayIdentifier);
    }

    private Optional<WeavingContract> findWeavingContract(String weavingContractIdentifier) {
        Long parsedId = parseLong(weavingContractIdentifier);
        if (parsedId != null) {
            Optional<WeavingContract> found = weavingContractRepository.findById(parsedId);
            if (found.isPresent()) {
                return found;
            }
        }
        return weavingContractRepository.findByWeavingContractNo(weavingContractIdentifier);
    }

    private void requireWeavingWorkOrderReference(String weavingWorkOrderIdentifier, String weavingWorkOrderNo) {
        if (!StringUtils.hasText(weavingWorkOrderIdentifier)) {
            return;
        }
        Optional<WeavingWorkOrder> weavingWorkOrderOpt = findWeavingWorkOrder(weavingWorkOrderIdentifier);
        String displayIdentifier = StringUtils.hasText(weavingWorkOrderNo)
                ? weavingWorkOrderNo
                : weavingWorkOrderOpt.map(WeavingWorkOrder::getWeavingWorkOrderNo)
                        .filter(StringUtils::hasText)
                        .orElse(weavingWorkOrderIdentifier);
        referenceValidationService.requireReference(
                weavingWorkOrderIdentifier,
                () -> weavingWorkOrderOpt,
                "Weaving Work Order",
                displayIdentifier);
    }

    private void markWeavingWorkOrderReference(Long weavingWorkOrderId) {
        if (weavingWorkOrderId != null) {
            weavingWorkOrderService.markAsOrderIdMapped(weavingWorkOrderId);
        }
    }

    private Optional<WeavingWorkOrder> findWeavingWorkOrder(String weavingWorkOrderIdentifier) {
        Long parsedId = parseLong(weavingWorkOrderIdentifier);
        if (parsedId != null) {
            Optional<WeavingWorkOrder> found = weavingWorkOrderRepository.findById(parsedId);
            if (found.isPresent()) {
                return found;
            }
        }
        return weavingWorkOrderRepository.findByWeavingWorkOrderNo(weavingWorkOrderIdentifier);
    }

    private PaginatedResponse<WeftInwardDTO> buildPaginatedResponse(Page<WeftInward> page, Long offset, Long limit) {
        List<WeftInwardDTO> content = page.getContent().stream()
                .map(WeftInwardDTO::new)
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

    private LocalDate parseDate(String value, LocalDate defaultVal) {
        if (value == null || value.trim().isEmpty()) return defaultVal;
        List<DateTimeFormatter> fmts = List.of(
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ISO_LOCAL_DATE
        );
        for (DateTimeFormatter f : fmts) {
            try { return LocalDate.parse(value, f); } catch (DateTimeParseException ignored) {}
        }
        return defaultVal;
    }

    private Long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        try { return Long.valueOf(value); } catch (NumberFormatException e) { return null; }
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

    private String buildWeftInwardItemKey(WeftInwardItem item) {
        if (item == null) {
            return "";
        }
        return String.join("|",
                String.valueOf(item.getYarnId() != null ? item.getYarnId() : 0L),
                String.valueOf(item.getMillId() != null ? item.getMillId() : 0L),
                String.valueOf(item.getWarehouseId() != null ? item.getWarehouseId() : 0L),
                normalizeLot(item.getLotNo()));
    }

    private String buildWeftInwardItemKey(WeftInwardItemDTO item) {
        if (item == null) {
            return "";
        }
        return String.join("|",
                String.valueOf(item.getYarnId() != null ? item.getYarnId() : 0L),
                String.valueOf(item.getMillId() != null ? item.getMillId() : 0L),
                String.valueOf(item.getWarehouseId() != null ? item.getWarehouseId() : 0L),
                normalizeLot(item.getLotNo()));
    }

    private String buildYarnInwardStockDetailKey(YarnInwardStockDetails detail) {
        if (detail == null) {
            return "";
        }
        return String.join("|",
                String.valueOf(detail.getItemId() != null ? detail.getItemId() : 0L),
                String.valueOf(detail.getMillId() != null ? detail.getMillId() : 0L),
                String.valueOf(detail.getWarehouseId() != null ? detail.getWarehouseId() : 0L),
                normalizeLot(detail.getLotNo()));
    }

    private String normalizeLot(String lotNo) {
        return lotNo == null ? "" : lotNo.trim().toLowerCase();
    }

    private void removeStockDetailFromMaps(YarnInwardStockDetails detail,
                                           Map<Long, YarnInwardStockDetails> detailsByOrderItemId,
                                           Map<Long, YarnInwardStockDetails> detailsById,
                                           Map<String, List<YarnInwardStockDetails>> detailsByKey) {
        if (detail == null) {
            return;
        }
        if (detail.getOrderItemId() != null) {
            detailsByOrderItemId.remove(detail.getOrderItemId());
        }
        if (detail.getId() != null) {
            detailsById.remove(detail.getId());
        }
        List<YarnInwardStockDetails> keyedDetails = detailsByKey.get(buildYarnInwardStockDetailKey(detail));
        if (keyedDetails != null) {
            keyedDetails.remove(detail);
        }
    }

    private void recalculateAvailableFromOutward(YarnInwardStockDetails detail, BigDecimal inwardQty, BigDecimal inwardBags) {
        BigDecimal totalIssuedQty = BigDecimal.ZERO;
        BigDecimal totalIssuedBags = BigDecimal.ZERO;
        BigDecimal totalIssuedCones = BigDecimal.ZERO;

        if (detail.getId() != null) {
            totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
            totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(detail.getId());
            totalIssuedCones = yarnOutwardStockDetailsRepository.getTotalIssuedConesByYarnInwardStockDetailsId(detail.getId());
        }

        BigDecimal conesPerBag = detail.getConesPerBag() != null ? detail.getConesPerBag() : BigDecimal.ZERO;
        BigDecimal totalInwardCones = inwardBags.multiply(conesPerBag);

        if (totalIssuedCones == null || totalIssuedCones.compareTo(BigDecimal.ZERO) == 0) {
            totalIssuedCones = (totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO).multiply(conesPerBag);
        }

        BigDecimal availableQty = inwardQty.subtract(totalIssuedQty != null ? totalIssuedQty : BigDecimal.ZERO);
        BigDecimal availableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : BigDecimal.ZERO);
        BigDecimal availableCones = totalInwardCones.subtract(totalIssuedCones != null ? totalIssuedCones : BigDecimal.ZERO);

        detail.setAvailableQty(availableQty.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableQty);
        detail.setAvailableBags(availableBags.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableBags);
        detail.setAvailableCones(availableCones.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : availableCones);
    }

    private void syncToYarnInventory(WeftInward weftInward, WeftInwardDTO dto) {
        if (weftInward == null || weftInward.getInwardNo() == null) {
            return;
        }

        Long inwardTypeId = codesDetailRepository.findByValue("weft-yarn-inward")
                .map(CodesDetail::getId)
                .orElseGet(() -> codesDetailRepository.findByValue("FACTORY_YARN_INWARD")
                        .map(CodesDetail::getId)
                        .orElse(2L));

        YarnInventory yarnInventory = yarnInventoryRepository.findByInwardNo(weftInward.getInwardNo())
                .orElse(new YarnInventory());

        yarnInventory.setInwardId(weftInward.getId());
        yarnInventory.setInwardNo(weftInward.getInwardNo());
        yarnInventory.setInwardTypeId(inwardTypeId);
        yarnInventory.setCompanyId(weftInward.getCompanyId());
        yarnInventory.setInwardDate(weftInward.getInwardDate() != null ? weftInward.getInwardDate().atStartOfDay() : LocalDateTime.now());
        yarnInventory.setFinancialYear(calculateFinancialYear(weftInward.getInwardDate()));
        yarnInventory.setDcNo(weftInward.getDcNo());
        yarnInventory.setDcDate(weftInward.getDcDate());
        yarnInventory.setOrderId(weftInward.getWeavingJobWorkOrderId());
        yarnInventory.setOrderNo(dto != null ? dto.getWeavingWorkOrderNo() : null);
        yarnInventory.setVendorId(weftInward.getSourceCompanyId());

        if (weftInward.getYarnItems() != null && !weftInward.getYarnItems().isEmpty()) {
            yarnInventory.setWarehouseId(weftInward.getYarnItems().get(0).getWarehouseId());
        }

        Map<Long, YarnInwardStockDetails> existingDetailsByOrderItemId = new HashMap<>();
        Map<Long, YarnInwardStockDetails> existingDetailsById = new HashMap<>();
        Map<String, List<YarnInwardStockDetails>> existingDetailsByKey = new HashMap<>();
        if (yarnInventory.getDetails() != null) {
            yarnInventory.getDetails().forEach(detail -> {
                if (detail.getOrderItemId() != null) {
                    existingDetailsByOrderItemId.put(detail.getOrderItemId(), detail);
                }
                if (detail.getId() != null) {
                    existingDetailsById.put(detail.getId(), detail);
                }
                existingDetailsByKey
                        .computeIfAbsent(buildYarnInwardStockDetailKey(detail), key -> new ArrayList<>())
                        .add(detail);
            });
        }

        List<YarnInwardStockDetails> stockDetailsList = new ArrayList<>();
        if (weftInward.getYarnItems() != null) {
            for (WeftInwardItem item : weftInward.getYarnItems()) {
                YarnInwardStockDetails detail = null;
                if (item.getId() != null) {
                    detail = existingDetailsByOrderItemId.remove(item.getId());
                }
                if (detail == null) {
                    List<YarnInwardStockDetails> matches = existingDetailsByKey.get(buildWeftInwardItemKey(item));
                    if (matches != null && !matches.isEmpty()) {
                        detail = matches.remove(0);
                    }
                }
                if (detail == null && item.getId() != null) {
                    detail = yarnInwardStockDetailsRepository.findByOrderItemIdAndInwardTypeId(item.getId(), inwardTypeId)
                            .orElse(null);
                }
                if (detail == null) {
                    detail = new YarnInwardStockDetails();
                }

                removeStockDetailFromMaps(detail, existingDetailsByOrderItemId, existingDetailsById, existingDetailsByKey);

                BigDecimal inwardQty = item.getTotalKgs() != null ? BigDecimal.valueOf(item.getTotalKgs()) : BigDecimal.ZERO;
                BigDecimal inwardBags = item.getNoOfBags() != null ? BigDecimal.valueOf(item.getNoOfBags()) : BigDecimal.ZERO;

                detail.setOrderItemId(item.getId());
                detail.setYarnInventory(yarnInventory);
                detail.setInwardTypeId(inwardTypeId);
                detail.setItemId(item.getYarnId());
                detail.setMillId(item.getMillId());
                detail.setLotNo(item.getLotNo());
                detail.setInwardQty(inwardQty);
                detail.setInwardBags(inwardBags);
                detail.setKgsPerBag(item.getKgsPerBag() != null ? BigDecimal.valueOf(item.getKgsPerBag()) : BigDecimal.ZERO);
                detail.setAcceptedQty(inwardQty);
                detail.setWarehouseId(item.getWarehouseId());
                recalculateAvailableFromOutward(detail, inwardQty, inwardBags);
                stockDetailsList.add(detail);
            }
        }
        yarnInventory.setDetails(stockDetailsList);
        yarnInventoryRepository.save(yarnInventory);
    }

    private void deleteFromYarnInventory(WeftInward entity) {
        if (entity != null && entity.getOrderTypeId() != null && entity.getOrderTypeId() == 2L && entity.getInwardNo() != null) {
            yarnInventoryRepository.findByInwardNo(entity.getInwardNo())
                    .ifPresent(inventory -> yarnInventoryRepository.delete(inventory));
        }
    }
}
