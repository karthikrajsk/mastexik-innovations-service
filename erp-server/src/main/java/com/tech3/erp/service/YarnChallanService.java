package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.YarnChallanDTO;
import com.tech3.erp.dto.YarnChallanItemDTO;
import com.tech3.erp.dto.YarnIssueDetailDTO;
import com.tech3.erp.entity.YarnChallan;
import com.tech3.erp.entity.YarnChallanItem;
import com.tech3.erp.entity.YarnIssueDetail;
import com.tech3.erp.entity.YarnOutward;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.YarnOutwardStockDetails;
import com.tech3.erp.entity.YarnSales;
import com.tech3.erp.repository.YarnChallanRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnOutwardRepository;
import com.tech3.erp.repository.YarnOutwardStockDetailsRepository;
import com.tech3.erp.repository.YarnSalesRepository;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.GenerateInvoiceRepository;
import com.tech3.erp.entity.GenerateInvoice;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.entity.CodesDetail;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class YarnChallanService {

    @Autowired
    private YarnChallanRepository yarnChallanRepo;

    @Autowired
    private YarnOutwardRepository yarnOutwardRepo;

    @Autowired
    private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepo;

    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepo;

    @Autowired
    private YarnSalesRepository yarnSalesRepo;

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private GenerateInvoiceRepository generateInvoiceRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Transactional
    public YarnChallan create(YarnChallanDTO dto) {
        YarnChallan yarnChallan = new YarnChallan();

        yarnChallan.setChallanDate(dto.getChallanDate());
        yarnChallan.setBuyerId(dto.getBuyerId());

        Long yarnSalesOrderId = normalizeId(dto.getYarnSalesOrderId());
        if (yarnSalesOrderId != null) {
            referenceValidationService.requireReference(
                    yarnSalesOrderId,
                    () -> yarnSalesRepo.findById(yarnSalesOrderId),
                    "Yarn Sales Order",
                    null);
        }
        yarnChallan.setYarnSalesOrderId(yarnSalesOrderId);

        yarnChallan.setTareWeight(dto.getTareWeight());
        yarnChallan.setGrossWeight(dto.getGrossWeight());
        yarnChallan.setChallanNo(dto.getChallanNo());
        yarnChallan.setRemarks(dto.getRemarks());
        yarnChallan.setDeliveryAt(dto.getDeliveryAt());
        yarnChallan.setConsigneeId(dto.getConsigneeId());
        yarnChallan.setCompanyId(dto.getCompanyId());
        yarnChallan.setInvoiceId(dto.getInvoiceId());
        yarnChallan.setIsInvoice(dto.getIsInvoice() != null ? dto.getIsInvoice() : 0);
        String challanNoFromDto = dto.getChallanNo();
        boolean hasChallanNo = challanNoFromDto != null && !challanNoFromDto.trim().isEmpty();
        yarnChallan.setChallanNo(hasChallanNo ? challanNoFromDto : null);
        yarnChallan.setVehicleNo(dto.getVehicleNo());
        yarnChallan.setTransportId(dto.getTransportId());
        yarnChallan.setActiveFlag(true);

        List<YarnChallanItem> items = (dto.getItems() == null ? new ArrayList<YarnChallanItemDTO>() : dto.getItems())
                .stream()
                .filter((YarnChallanItemDTO i) -> i.getYarnId() != null)
                .map((YarnChallanItemDTO i) -> {
                    YarnChallanItem item = new YarnChallanItem();
                    item.setYarnId(i.getYarnId());
                    item.setMillId(i.getMillId());
                    item.setWarehouseId(i.getWarehouseId());
                    item.setOrderQuantity(i.getOrderQuantity());
                    item.setRatePerUnit(i.getRatePerUnit());
                    item.setUomId(i.getUomId());
                    item.setTaxableValue(i.getTaxableValue());
                    item.setGstPercentId(i.getGstPercentId());
                    item.setGstAmount(i.getGstAmount());
                    item.setTotalAmount(i.getTotalAmount());
                    item.setIssueQty(i.getIssueQty());
                    item.setYarnChallan(yarnChallan);
                    item.setActiveFlag(i.getActiveFlag() != null ? i.getActiveFlag() : true);
                    return item;
                }).collect(Collectors.toList());

        yarnChallan.setItems(items);
        yarnChallan.setYarnIssueDetails(mapIssueDetails(dto.getYarnIssueDetails(), yarnChallan));
        YarnChallan saved = yarnChallanRepo.save(yarnChallan);
        
        if (!hasChallanNo) {
            String challanNo = generateChallanNo(dto.getChallanDate(), saved.getId());
            saved.setChallanNo(challanNo);
            saved = yarnChallanRepo.save(saved);
        }

        createYarnOutward(saved, dto);
        // Sync challan -> invoice (consignee/deliveryAt)
        syncChallanToInvoice(saved);
        
        return saved;
    }

    private void createYarnOutward(YarnChallan saved, YarnChallanDTO dto) {
        if (dto.getYarnIssueDetails() == null || dto.getYarnIssueDetails().isEmpty()) {
            return;
        }

        // Fetch outwardTypeId from CodesDetail for YARN_SALE
        Long outwardTypeId = codesDetailRepository.findByValue("YARN_SALE")
                .map(CodesDetail::getId)
                .orElseGet(() -> codesDetailRepository.findByValue("YARN_SALE")
                        .map(CodesDetail::getId)
                        .orElse(57L));

        // Find existing outward or create new
        YarnOutward outward = yarnOutwardRepo.findByOutwardNo(saved.getChallanNo()).orElse(new YarnOutward());
        
        Map<Long, Long> existingStockDetailsMap = new HashMap<>();
        if (outward.getId() != null) {
            restoreYarnInwardStockDetails(outward.getYarnOutwardStockDetails());
            for (YarnOutwardStockDetails detail : outward.getYarnOutwardStockDetails()) {
                if (detail.getOrderItemId() != null && detail.getYarnInwardStockDetailsId() != null) {
                    existingStockDetailsMap.put(detail.getOrderItemId(), detail.getYarnInwardStockDetailsId());
                }
            }
            outward.getYarnOutwardStockDetails().clear();
        }

        outward.setOutwardNo(saved.getChallanNo());
        outward.setOutwardDate(saved.getChallanDate());
        outward.setOrderId(saved.getYarnSalesOrderId());
        
        if (saved.getYarnSalesOrderId() != null) {
            YarnSales sales = yarnSalesRepo.findById(saved.getYarnSalesOrderId()).orElse(null);
            if (sales != null) {
                outward.setOrderNo(sales.getYarnSalesNo());
            }
        }
        
        outward.setVendorId(saved.getBuyerId());
        outward.setCompanyId(saved.getCompanyId());
        outward.setRemarks(saved.getRemarks());
        outward.setFinancialYearId(getFinancialYear(saved.getChallanDate()));
        outward.setActiveFlag(true);
        outward.setOutwardTypeId(outwardTypeId);

        YarnOutward savedOutward = yarnOutwardRepo.save(outward);

        List<YarnOutwardStockDetails> stockDetails = new ArrayList<>();
        List<YarnOutwardStockDetails> savedStockDetails = new ArrayList<>();
        
        for (YarnIssueDetailDTO detailDto : dto.getYarnIssueDetails()) {
            YarnOutwardStockDetails stockDetail = new YarnOutwardStockDetails();
            stockDetail.setYarnOutward(savedOutward);
            stockDetail.setInwardId(detailDto.getInwardId());
            stockDetail.setOrderItemId(detailDto.getOrderItemId());
            
            Long yarnInwardStockDetailsId = null;
            
            if (detailDto.getYarnInwardStockDetailsId() != null && detailDto.getYarnInwardStockDetailsId() > 0) {
                yarnInwardStockDetailsId = detailDto.getYarnInwardStockDetailsId();
            } else if (detailDto.getOrderItemId() != null && existingStockDetailsMap.containsKey(detailDto.getOrderItemId())) {
                yarnInwardStockDetailsId = existingStockDetailsMap.get(detailDto.getOrderItemId());
            }
            
            if (yarnInwardStockDetailsId == null && detailDto.getOrderItemId() != null) {
                Optional<YarnInwardStockDetails> inwardOpt = yarnInwardStockDetailsRepo.findFirstByOrderItemId(detailDto.getOrderItemId());
                if (inwardOpt.isPresent()) {
                    yarnInwardStockDetailsId = inwardOpt.get().getId();
                }
            }
            
            if (yarnInwardStockDetailsId != null) {
                stockDetail.setYarnInwardStockDetailsId(yarnInwardStockDetailsId);
            }
            
            stockDetail.setOutwardTypeId(outwardTypeId);
            stockDetail.setActualItemId(detailDto.getYarnId());
            
            stockDetail.setMillId(detailDto.getMillId());
            stockDetail.setWarehouseId(detailDto.getWarehouseId());
            stockDetail.setIssuedQty(detailDto.getIssueQty());
            stockDetail.setIssuedBags(detailDto.getNoOfBags());
            stockDetail.setKgsPerBag(detailDto.getKgsPerBag());
            stockDetail.setConesPerBag(detailDto.getConesPerBag());
            stockDetail.setConesWeight(detailDto.getConesWeight());
            
            stockDetails.add(stockDetail);
        }
        
        savedStockDetails = yarnOutwardStockDetailsRepo.saveAll(stockDetails);
        updateYarnInwardStockDetails(savedStockDetails);
    }
    
    private void updateYarnInwardStockDetails(List<YarnOutwardStockDetails> stockDetails) {
        if (stockDetails == null || stockDetails.isEmpty()) {
            return;
        }
        
        for (YarnOutwardStockDetails outwardDetail : stockDetails) {
            if (outwardDetail == null) {
                continue;
            }
            
            Long yarnInwardStockDetailsId = outwardDetail.getYarnInwardStockDetailsId();
            Long orderItemId = outwardDetail.getOrderItemId();
            
            Optional<YarnInwardStockDetails> inwardDetailOpt = findInwardStockDetail(yarnInwardStockDetailsId, orderItemId);
            
            if (!inwardDetailOpt.isPresent()) {
                continue;
            }
            
            YarnInwardStockDetails inwardStockDetail = inwardDetailOpt.get();
            
            if ((yarnInwardStockDetailsId == null || yarnInwardStockDetailsId == 0) && inwardStockDetail.getId() != null) {
                outwardDetail.setYarnInwardStockDetailsId(inwardStockDetail.getId());
                yarnOutwardStockDetailsRepo.save(outwardDetail);
            }
            
            BigDecimal totalIssuedQty = outwardDetail.getIssuedQty() != null ? outwardDetail.getIssuedQty() : BigDecimal.ZERO;
            BigDecimal totalIssuedBags = outwardDetail.getIssuedBags() != null ? outwardDetail.getIssuedBags() : BigDecimal.ZERO;
            
            if (inwardStockDetail.getAvailableQty() == null) {
                BigDecimal acceptedQty = inwardStockDetail.getAcceptedQty() != null ? inwardStockDetail.getAcceptedQty() : BigDecimal.ZERO;
                inwardStockDetail.setAvailableQty(acceptedQty);
            }
            
            if (inwardStockDetail.getAvailableBags() == null) {
                BigDecimal inwardBags = inwardStockDetail.getInwardBags() != null ? inwardStockDetail.getInwardBags() : BigDecimal.ZERO;
                inwardStockDetail.setAvailableBags(inwardBags);
            }
            
            inwardStockDetail.setAvailableQty(inwardStockDetail.getAvailableQty().subtract(totalIssuedQty));
            inwardStockDetail.setAvailableBags(inwardStockDetail.getAvailableBags().subtract(totalIssuedBags));
            
            if (inwardStockDetail.getAvailableQty().compareTo(BigDecimal.ZERO) < 0) {
                inwardStockDetail.setAvailableQty(BigDecimal.ZERO);
            }
            if (inwardStockDetail.getAvailableBags().compareTo(BigDecimal.ZERO) < 0) {
                inwardStockDetail.setAvailableBags(BigDecimal.ZERO);
            }
            
            yarnInwardStockDetailsRepo.save(inwardStockDetail);
        }
    }

    private void restoreYarnInwardStockDetails(List<YarnOutwardStockDetails> stockDetails) {
        if (stockDetails == null || stockDetails.isEmpty()) {
            return;
        }

        for (YarnOutwardStockDetails outwardDetail : stockDetails) {
            if (outwardDetail == null) {
                continue;
            }
            
            Long yarnInwardStockDetailsId = outwardDetail.getYarnInwardStockDetailsId();
            Long orderItemId = outwardDetail.getOrderItemId();
            
            Optional<YarnInwardStockDetails> inwardDetailOpt = findInwardStockDetail(yarnInwardStockDetailsId, orderItemId);
            
            if (!inwardDetailOpt.isPresent()) {
                continue;
            }
            
            YarnInwardStockDetails inwardStockDetail = inwardDetailOpt.get();
            
            BigDecimal totalIssuedQty = outwardDetail.getIssuedQty() != null ? outwardDetail.getIssuedQty() : BigDecimal.ZERO;
            BigDecimal totalIssuedBags = outwardDetail.getIssuedBags() != null ? outwardDetail.getIssuedBags() : BigDecimal.ZERO;
            
            if (inwardStockDetail.getAvailableQty() == null) {
                BigDecimal acceptedQty = inwardStockDetail.getAcceptedQty() != null ? inwardStockDetail.getAcceptedQty() : BigDecimal.ZERO;
                inwardStockDetail.setAvailableQty(acceptedQty);
            }
            
            if (inwardStockDetail.getAvailableBags() == null) {
                BigDecimal inwardBags = inwardStockDetail.getInwardBags() != null ? inwardStockDetail.getInwardBags() : BigDecimal.ZERO;
                inwardStockDetail.setAvailableBags(inwardBags);
            }
            
            inwardStockDetail.setAvailableQty(inwardStockDetail.getAvailableQty().add(totalIssuedQty));
            inwardStockDetail.setAvailableBags(inwardStockDetail.getAvailableBags().add(totalIssuedBags));
            
            yarnInwardStockDetailsRepo.save(inwardStockDetail);
        }
    }

    private String generateChallanNo(LocalDate challanDate, Long id) {
        String fy = getFinancialYear(challanDate);
        String prefix = "YC/" + fy + "/";
        String sequence = String.format("%04d", id);
        return prefix + sequence;
    }

    private String getFinancialYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        if (month >= 4) {
            int nextYear = year + 1;
            return String.format("%d-%d", year, nextYear % 100);
        } else {
            int previousYear = year - 1;
            return String.format("%d-%d", previousYear, year % 100);
        }
    }

    public List<YarnChallan> getAll() {
        return yarnChallanRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public PaginatedResponse<YarnChallan> getAllWithPagination(Long offset, Long limit, Long companyId) {
        List<YarnChallan> list = companyId == null ? getAll() : getAllByCompanyId(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<YarnChallan> getAllByCompanyId(Long companyId) {
        return yarnChallanRepo.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id"));
    }

    public YarnChallan getById(Long id) {
        return getById(id, null);
    }

    public YarnChallan getById(Long id, Long companyId) {
        YarnChallan yarnChallan = yarnChallanRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("YarnChallan not found with id: " + id));
        if (companyId != null && (yarnChallan.getCompanyId() == null || !companyId.equals(yarnChallan.getCompanyId()))) {
            throw new EntityNotFoundException("YarnChallan not found for the specified companyId");
        }
        return yarnChallan;
    }

    @Transactional
    public YarnChallan update(Long id, YarnChallanDTO dto) {
        return update(id, dto, null);
    }

    @Transactional
    public YarnChallan update(Long id, YarnChallanDTO dto, Long companyId) {
        YarnChallan yarnChallan = yarnChallanRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("YarnChallan not found with id: " + id));
        if (companyId != null && (yarnChallan.getCompanyId() == null || !companyId.equals(yarnChallan.getCompanyId()))) {
            throw new EntityNotFoundException("YarnChallan not found for the specified companyId");
        }

        yarnChallan.setChallanDate(dto.getChallanDate());
        yarnChallan.setBuyerId(dto.getBuyerId());

        Long yarnSalesOrderId = normalizeId(dto.getYarnSalesOrderId());
        if (yarnSalesOrderId != null) {
            referenceValidationService.requireReference(
                    yarnSalesOrderId,
                    () -> yarnSalesRepo.findById(yarnSalesOrderId),
                    "Yarn Sales Order",
                    null);
        }
        
        yarnChallan.setYarnSalesOrderId(yarnSalesOrderId);

        yarnChallan.setTareWeight(dto.getTareWeight());
        yarnChallan.setGrossWeight(dto.getGrossWeight());
        yarnChallan.setChallanNo(dto.getChallanNo());
        yarnChallan.setRemarks(dto.getRemarks());
        // Only update deliveryAt if it's not null or empty string
        if (dto.getDeliveryAt() != null && !dto.getDeliveryAt().trim().isEmpty()) {
            yarnChallan.setDeliveryAt(dto.getDeliveryAt());
        }
        yarnChallan.setConsigneeId(dto.getConsigneeId());
        yarnChallan.setCompanyId(dto.getCompanyId());
        yarnChallan.setInvoiceId(dto.getInvoiceId());
        yarnChallan.setIsInvoice(dto.getIsInvoice() != null ? dto.getIsInvoice() : yarnChallan.getIsInvoice());
        yarnChallan.setVehicleNo(dto.getVehicleNo());
        yarnChallan.setTransportId(dto.getTransportId());

        updateItems(yarnChallan, dto.getItems());
        updateYarnIssueDetails(yarnChallan, dto.getYarnIssueDetails());

        YarnChallan saved = yarnChallanRepo.save(yarnChallan);
        createYarnOutward(saved, dto);
        // Sync challan -> invoice (consignee/deliveryAt)
        syncChallanToInvoice(saved);
        return saved;
    }

    /**
     * Conservative sync from YarnChallan -> GenerateInvoice.
     * Only updates invoice consignee when invoice has no manual consignee or references same id.
     */
    private void syncChallanToInvoice(YarnChallan challan) {
        if (challan == null || challan.getInvoiceId() == null) return;
        GenerateInvoice invoice = generateInvoiceRepository.findById(challan.getInvoiceId()).orElse(null);
        if (invoice == null) return;

        boolean invoiceHasConsignee = invoice.getConsgineeId() != null;
        boolean invoiceConsigneeBlank = (invoice.getConsignee() == null || invoice.getConsignee().isBlank());

        // Update only if invoice has no explicit different manual consignee
        if (!invoiceHasConsignee || invoiceConsigneeBlank || (invoice.getConsgineeId() != null && invoice.getConsgineeId().equals(challan.getConsigneeId()))) {
            invoice.setConsgineeId(challan.getConsigneeId());
            // Store deliveryAt into consignee JSON/text field so UI can read latest display value
            if (challan.getDeliveryAt() != null && !challan.getDeliveryAt().isBlank()) {
                invoice.setConsignee(challan.getDeliveryAt());
            }
            generateInvoiceRepository.save(invoice);
        }
    }

    private void updateItems(YarnChallan yarnChallan, List<YarnChallanItemDTO> itemDTOs) {
        if (itemDTOs == null) return;
        Map<Long, YarnChallanItem> existing = yarnChallan.getItems().stream().collect(Collectors.toMap(YarnChallanItem::getId, it -> it));
        List<YarnChallanItem> updated = new ArrayList<>();
        for (YarnChallanItemDTO dto : itemDTOs) {
            if (dto.getYarnId() == null) continue;
            YarnChallanItem item = (dto.getId() != null && existing.containsKey(dto.getId())) ? existing.remove(dto.getId()) : new YarnChallanItem();
            item.setYarnChallan(yarnChallan);
            item.setYarnId(dto.getYarnId());
            item.setMillId(dto.getMillId());
            item.setWarehouseId(dto.getWarehouseId());
            item.setOrderQuantity(dto.getOrderQuantity());
            item.setRatePerUnit(dto.getRatePerUnit());
            item.setUomId(dto.getUomId());
            item.setTaxableValue(dto.getTaxableValue());
            item.setGstPercentId(dto.getGstPercentId());
            item.setGstAmount(dto.getGstAmount());
            item.setTotalAmount(dto.getTotalAmount());
            item.setIssueQty(dto.getIssueQty());
            item.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
            updated.add(item);
        }
        yarnChallan.getItems().clear();
        yarnChallan.getItems().addAll(updated);
    }

    private List<YarnIssueDetail> mapIssueDetails(List<YarnIssueDetailDTO> detailDTOs, YarnChallan yarnChallan) {
        if (detailDTOs == null) {
            return new ArrayList<>();
        }
        return detailDTOs.stream()
                .filter(detail -> detail != null)
                .map(detail -> {
                    YarnIssueDetail issueDetail = new YarnIssueDetail();
                    issueDetail.setYarnChallan(yarnChallan);
                    issueDetail.setYarnSalesItemId(detail.getYarnSalesItemId());
                    issueDetail.setYarnId(detail.getYarnId());
                    issueDetail.setMillId(detail.getMillId());
                    issueDetail.setWarehouseId(detail.getWarehouseId());
                    issueDetail.setLotNumber(detail.getLotNumber());
                    issueDetail.setAvailableQty(detail.getAvailableQty());
                    issueDetail.setAvailableBags(detail.getAvailableBags());
                    issueDetail.setNoOfBags(detail.getNoOfBags());
                    issueDetail.setKgsPerBag(detail.getKgsPerBag());
                    issueDetail.setConesPerBag(detail.getConesPerBag());
                    issueDetail.setConesWeight(detail.getConesWeight());
                    issueDetail.setIssueQty(detail.getIssueQty());
                    issueDetail.setActiveFlag(detail.getActiveFlag() != null ? detail.getActiveFlag() : true);
                    issueDetail.setInwardTypeId(detail.getInwardTypeId());
                    return issueDetail;
                })
                .collect(Collectors.toList());
    }

    private void updateYarnIssueDetails(YarnChallan yarnChallan, List<YarnIssueDetailDTO> detailDTOs) {
        if (detailDTOs == null) {
            return;
        }
        Map<Long, YarnIssueDetail> existing = yarnChallan.getYarnIssueDetails().stream()
                .filter(detail -> detail.getId() != null)
                .collect(Collectors.toMap(YarnIssueDetail::getId, detail -> detail));
        List<YarnIssueDetail> updated = new ArrayList<>();
        for (YarnIssueDetailDTO dto : detailDTOs) {
            if (dto == null) {
                continue;
            }
            YarnIssueDetail detail = (dto.getId() != null && existing.containsKey(dto.getId()))
                    ? existing.remove(dto.getId())
                    : new YarnIssueDetail();
            detail.setYarnChallan(yarnChallan);
            detail.setYarnSalesItemId(dto.getYarnSalesItemId());
            detail.setYarnId(dto.getYarnId());
            detail.setMillId(dto.getMillId());
            detail.setWarehouseId(dto.getWarehouseId());
            detail.setLotNumber(dto.getLotNumber());
            detail.setAvailableQty(dto.getAvailableQty());
            detail.setAvailableBags(dto.getAvailableBags());
            detail.setNoOfBags(dto.getNoOfBags());
            detail.setKgsPerBag(dto.getKgsPerBag());
            detail.setConesPerBag(dto.getConesPerBag());
            detail.setConesWeight(dto.getConesWeight());
            detail.setIssueQty(dto.getIssueQty());
            detail.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
            detail.setInwardTypeId(dto.getInwardTypeId());
            updated.add(detail);
        }
        yarnChallan.getYarnIssueDetails().clear();
        yarnChallan.getYarnIssueDetails().addAll(updated);
        
        // If there were any orphans in the 'existing' map, they are already removed from the collection
        // but we might want to ensure they are handled if orphanRemoval=true is NOT set.
        // Based on the pattern in other files, clear+addAll is what was there.
        // My change above (using existing.remove) already preserved the objects with their IDs.
    }

    @Transactional
    public void delete(Long id) {
        YarnChallan entity = yarnChallanRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("YarnChallan not found with id: " + id));
        
        // Find and delete associated YarnOutward
        yarnOutwardRepo.findByOutwardNo(entity.getChallanNo()).ifPresent(outward -> {
            // Restore stock levels before deleting outward details
            restoreYarnInwardStockDetails(outward.getYarnOutwardStockDetails());
            yarnOutwardRepo.delete(outward);
        });

        yarnChallanRepo.delete(entity);
    }

    @Transactional
    public void delete(Long id, Long companyId) {
        YarnChallan entity = yarnChallanRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("YarnChallan not found with id: " + id));
        if (companyId != null && (entity.getCompanyId() == null || !companyId.equals(entity.getCompanyId()))) {
            throw new EntityNotFoundException("YarnChallan not found for the specified companyId");
        }
        
        // Find and delete associated YarnOutward
        yarnOutwardRepo.findByOutwardNo(entity.getChallanNo()).ifPresent(outward -> {
            // Restore stock levels before deleting outward details
            restoreYarnInwardStockDetails(outward.getYarnOutwardStockDetails());
            yarnOutwardRepo.delete(outward);
        });

        yarnChallanRepo.delete(entity);
    }

    private Long normalizeId(Long id) {
        return (id == null || id <= 0) ? null : id;
    }
    
    private Optional<YarnInwardStockDetails> findInwardStockDetail(Long yarnInwardStockDetailsId, Long orderItemId) {
        Optional<YarnInwardStockDetails> result = Optional.empty();
        
        if (yarnInwardStockDetailsId != null && yarnInwardStockDetailsId > 0) {
            try {
                result = yarnInwardStockDetailsRepo.findById(yarnInwardStockDetailsId);
                if (result.isPresent()) {
                    return result;
                }
            } catch (Exception e) {
                result = Optional.empty();
            }
        }
        
        if (!result.isPresent() && orderItemId != null && orderItemId > 0) {
            try {
                result = yarnInwardStockDetailsRepo.findFirstByOrderItemId(orderItemId);
                if (result.isPresent()) {
                    return result;
                }
            } catch (Exception e) {
                result = Optional.empty();
            }
        }
        
        return result;
    }
}