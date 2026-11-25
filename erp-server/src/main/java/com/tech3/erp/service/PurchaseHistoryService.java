package com.tech3.erp.service;

import com.tech3.erp.dto.LatestPurchaseSummaryDTO;
import com.tech3.erp.dto.PurchaseHistoryRecordDTO;
import com.tech3.erp.dto.PurchaseHistoryResponseDTO;
import com.tech3.erp.entity.ItemMaster;
import com.tech3.erp.entity.PurchaseOrder;
import com.tech3.erp.entity.PurchaseOrderItem;
import com.tech3.erp.entity.SubCategory;
import com.tech3.erp.entity.Uom;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.entity.WovenFabricMaster;
import com.tech3.erp.repository.ItemMasterRepository;
import com.tech3.erp.repository.PurchaseOrderItemRepository;
import com.tech3.erp.repository.SubCategoryRepository;
import com.tech3.erp.repository.UomRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.WovenFabricMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseHistoryService {

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;
    private final ItemMasterRepository itemMasterRepository;
    private final VendorRepository vendorRepository;

    @Autowired(required = false)
    private SubCategoryRepository subCategoryRepository;

    @Autowired(required = false)
    private WovenFabricMasterRepository wovenFabricMasterRepository;

    @Autowired(required = false)
    private UomRepository uomRepository;

    public PurchaseHistoryService(PurchaseOrderItemRepository purchaseOrderItemRepository,
                                  ItemMasterRepository itemMasterRepository,
                                  VendorRepository vendorRepository) {
        this.purchaseOrderItemRepository = purchaseOrderItemRepository;
        this.itemMasterRepository = itemMasterRepository;
        this.vendorRepository = vendorRepository;
    }

    public void setSubCategoryRepository(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public void setWovenFabricMasterRepository(WovenFabricMasterRepository wovenFabricMasterRepository) {
        this.wovenFabricMasterRepository = wovenFabricMasterRepository;
    }

    public void setUomRepository(UomRepository uomRepository) {
        this.uomRepository = uomRepository;
    }

    @Transactional(readOnly = true)
    public PurchaseHistoryResponseDTO getPurchaseHistory(Long itemId, Long companyId, Long offset, Long limit) {
        if (itemId == null || itemId <= 0) {
            throw new IllegalArgumentException("A valid material/item ID is required");
        }

        ItemMaster item = itemMasterRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Material / Item not found with ID: " + itemId));

        long safeOffset = offset != null ? Math.max(0, offset) : 0;
        long safeLimit = limit != null ? Math.min(Math.max(1, limit), 100) : 10;

        long total = purchaseOrderItemRepository.countPurchaseHistoryByItemId(itemId, companyId);

        PurchaseHistoryResponseDTO response = new PurchaseHistoryResponseDTO();
        response.setItemId(item.getId());
        response.setItemName(item.getItemName());
        response.setItemCode(item.getItemCode());
        response.setTotal(total);
        response.setOffset(safeOffset);
        response.setLimit(safeLimit);

        if (total == 0) {
            String resolvedItemUom = resolveUom(item.getUomName(), null, Collections.emptyMap());
            if (item.getUomName() != null && item.getUomName().trim().matches("\\d+") && uomRepository != null) {
                try {
                    resolvedItemUom = uomRepository.findById(Long.parseLong(item.getUomName().trim()))
                            .map(u -> (u.getUomName() != null && !u.getUomName().isBlank()) ? u.getUomName() : u.getUomCode())
                            .orElse(item.getUomName());
                } catch (Exception ignored) {}
            }
            response.setUom(resolvedItemUom != null && !resolvedItemUom.isBlank() ? resolvedItemUom : "—");
            response.setLatestPurchase(null);
            response.setRecords(Collections.emptyList());
            response.setHasMore(false);
            return response;
        }

        int page = (int) (safeOffset / safeLimit);
        Pageable pageable = PageRequest.of(page, (int) safeLimit);
        List<PurchaseOrderItem> items = purchaseOrderItemRepository.findPurchaseHistoryByItemId(itemId, companyId, pageable);

        List<PurchaseOrderItem> latestList = (safeOffset > 0)
                ? purchaseOrderItemRepository.findPurchaseHistoryByItemId(itemId, companyId, PageRequest.of(0, 1))
                : Collections.emptyList();

        Set<Long> uomIds = new HashSet<>();
        if (item.getUomName() != null && item.getUomName().trim().matches("\\d+")) {
            try {
                uomIds.add(Long.parseLong(item.getUomName().trim()));
            } catch (NumberFormatException ignored) {}
        }
        for (PurchaseOrderItem poi : items) {
            if (poi.getUnit() != null && poi.getUnit().trim().matches("\\d+")) {
                try {
                    uomIds.add(Long.parseLong(poi.getUnit().trim()));
                } catch (NumberFormatException ignored) {}
            }
        }
        if (!latestList.isEmpty()) {
            PurchaseOrderItem latestPoi = latestList.get(0);
            if (latestPoi.getUnit() != null && latestPoi.getUnit().trim().matches("\\d+")) {
                try {
                    uomIds.add(Long.parseLong(latestPoi.getUnit().trim()));
                } catch (NumberFormatException ignored) {}
            }
        }

        Map<Long, String> uomMap = (uomIds.isEmpty() || uomRepository == null) ? Collections.emptyMap() :
                uomRepository.findAllById(uomIds).stream()
                        .filter(Objects::nonNull)
                        .collect(Collectors.toMap(
                                Uom::getId,
                                u -> (u.getUomName() != null && !u.getUomName().isBlank()) ? u.getUomName() : u.getUomCode(),
                                (u1, u2) -> u1
                        ));

        String resolvedItemUom = resolveUom(item.getUomName(), null, uomMap);
        response.setUom(resolvedItemUom);

        Set<Long> vendorIds = items.stream()
                .map(i -> i.getPurchaseOrder() != null ? i.getPurchaseOrder().getVendorId() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, String> vendorNames = vendorIds.isEmpty() ? Collections.emptyMap() :
                vendorRepository.findAllById(vendorIds).stream()
                        .collect(Collectors.toMap(Vendor::getId, Vendor::getVendorName, (v1, v2) -> v1));

        Set<Long> millIds = items.stream()
                .map(PurchaseOrderItem::getMillNameId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, String> millNames = millIds.isEmpty() || subCategoryRepository == null ? Collections.emptyMap() :
                subCategoryRepository.findAllById(millIds).stream()
                        .collect(Collectors.toMap(SubCategory::getId, SubCategory::getSubCategoryName, (m1, m2) -> m1));

        Set<Long> fabricMasterIds = items.stream()
                .map(PurchaseOrderItem::getFabricMasterId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<Long, WovenFabricMaster> fabricMap = fabricMasterIds.isEmpty() || wovenFabricMasterRepository == null ? Collections.emptyMap() :
                wovenFabricMasterRepository.findAllById(fabricMasterIds).stream()
                        .collect(Collectors.toMap(WovenFabricMaster::getId, f -> f, (f1, f2) -> f1));

        List<PurchaseHistoryRecordDTO> records = new ArrayList<>();
        for (PurchaseOrderItem poi : items) {
            PurchaseHistoryRecordDTO record = new PurchaseHistoryRecordDTO();
            PurchaseOrder po = poi.getPurchaseOrder();

            record.setPoId(po != null ? po.getId() : null);
            record.setPoItemId(poi.getId());
            record.setPoNo(po != null ? po.getPoNo() : null);
            record.setPoDate(po != null ? po.getPoDate() : null);

            Long vendorId = po != null ? po.getVendorId() : null;
            record.setVendorId(vendorId);
            record.setVendorName(vendorId != null ? vendorNames.getOrDefault(vendorId, "Vendor #" + vendorId) : "—");

            record.setItemId(item.getId());
            record.setItemName(item.getItemName());
            record.setItemCode(item.getItemCode());
            record.setQuantity(poi.getQuantity());
            record.setUom(resolveUom(poi.getUnit(), resolvedItemUom, uomMap));
            record.setUnitPrice(poi.getPrice());
            record.setGstAmount(poi.getGstAmount());
            record.setNetAmount(poi.getNetAmount());
            record.setFinalAmount(poi.getFinalAmount());
            record.setGstPercent(poi.getGstPercent());
            record.setDiscountAmount(poi.getDiscountAmount());
            record.setFabricMasterId(poi.getFabricMasterId());
            record.setFabricCode(poi.getFabricCode());
            record.setQuality(poi.getQuality());
            record.setMillName(poi.getMillNameId() != null ? millNames.get(poi.getMillNameId()) : null);

            Short isApproved = po != null ? po.getIsApproved() : null;
            record.setIsApproved(isApproved);
            record.setStatus(isApproved != null && isApproved == 1 ? "Approved" : "Pending approval");

            record.setSpecification(buildSpecification(poi, fabricMap));

            records.add(record);
        }

        response.setRecords(records);
        response.setHasMore((safeOffset + records.size()) < total);

        if (safeOffset == 0 && !records.isEmpty()) {
            PurchaseHistoryRecordDTO first = records.get(0);
            response.setLatestPurchase(toLatestSummary(first));
        } else if (safeOffset > 0 && !latestList.isEmpty()) {
            PurchaseOrderItem latestPoi = latestList.get(0);
            PurchaseOrder latestPo = latestPoi.getPurchaseOrder();
            Long latestVendorId = latestPo != null ? latestPo.getVendorId() : null;
            String latestVendorName = latestVendorId != null
                    ? vendorRepository.findById(latestVendorId).map(Vendor::getVendorName).orElse("Vendor #" + latestVendorId)
                    : "—";
            LatestPurchaseSummaryDTO summary = new LatestPurchaseSummaryDTO(
                    latestPo != null ? latestPo.getPoDate() : null,
                    latestPo != null ? latestPo.getPoNo() : null,
                    latestPo != null ? latestPo.getId() : null,
                    latestVendorId,
                    latestVendorName,
                    latestPoi.getQuantity(),
                    resolveUom(latestPoi.getUnit(), resolvedItemUom, uomMap),
                    latestPoi.getPrice(),
                    latestPoi.getGstAmount(),
                    latestPoi.getNetAmount(),
                    latestPoi.getFinalAmount()
            );
            response.setLatestPurchase(summary);
        }

        return response;
    }

    private LatestPurchaseSummaryDTO toLatestSummary(PurchaseHistoryRecordDTO record) {
        return new LatestPurchaseSummaryDTO(
                record.getPoDate(),
                record.getPoNo(),
                record.getPoId(),
                record.getVendorId(),
                record.getVendorName(),
                record.getQuantity(),
                record.getUom(),
                record.getUnitPrice(),
                record.getGstAmount(),
                record.getNetAmount(),
                record.getFinalAmount()
        );
    }

    private String buildSpecification(PurchaseOrderItem poi, Map<Long, WovenFabricMaster> fabricMap) {
        StringBuilder spec = new StringBuilder();
        if (poi.getFabricMasterId() != null && fabricMap.containsKey(poi.getFabricMasterId())) {
            WovenFabricMaster fabric = fabricMap.get(poi.getFabricMasterId());
            if (fabric.getFabricName() != null && !fabric.getFabricName().isBlank()) {
                spec.append(fabric.getFabricName()).append(" ");
            }
            if (fabric.getFabricQuality() != null && !fabric.getFabricQuality().isBlank()) {
                spec.append("[").append(fabric.getFabricQuality()).append("] ");
            }
            if (fabric.getWarpCount() != null || fabric.getWeftCount() != null) {
                spec.append("Count: ").append(fabric.getWarpCount() != null ? fabric.getWarpCount() : "-")
                    .append("x").append(fabric.getWeftCount() != null ? fabric.getWeftCount() : "-").append(" ");
            }
            if (fabric.getEpi() != null || fabric.getPpi() != null) {
                spec.append("Reed/Pick: ").append(fabric.getEpi() != null ? fabric.getEpi() : "-")
                    .append("/").append(fabric.getPpi() != null ? fabric.getPpi() : "-").append(" ");
            }
        } else if (poi.getQuality() != null && !poi.getQuality().isBlank()) {
            spec.append(poi.getQuality());
            if (poi.getFabricCode() != null && !poi.getFabricCode().isBlank()) {
                spec.append(" (").append(poi.getFabricCode()).append(")");
            }
        } else if (poi.getRemarks() != null && !poi.getRemarks().isBlank()) {
            spec.append(poi.getRemarks());
        }
        String result = spec.toString().trim();
        return result.isEmpty() ? "—" : result;
    }

    private String resolveUom(String rawUnit, String fallbackItemUom, Map<Long, String> uomMap) {
        String candidate = (rawUnit != null && !rawUnit.isBlank()) ? rawUnit.trim() : fallbackItemUom;
        if (candidate == null || candidate.isBlank()) {
            return "—";
        }
        if (candidate.matches("\\d+")) {
            try {
                Long id = Long.parseLong(candidate);
                if (uomMap != null && uomMap.containsKey(id)) {
                    String name = uomMap.get(id);
                    if (name != null && !name.isBlank()) {
                        return name;
                    }
                }
            } catch (NumberFormatException ignored) {}
        }
        return candidate;
    }
}
