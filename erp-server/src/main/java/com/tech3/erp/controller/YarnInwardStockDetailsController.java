package com.tech3.erp.controller;

import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/yarn-inward-stock")
@CrossOrigin(origins = "*")
public class YarnInwardStockDetailsController {

    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;

    @GetMapping("/available-qty/{yarnId}")
    public ResponseEntity<BigDecimal> getAvailableQtyByYarnId(@PathVariable Long yarnId) {
        BigDecimal availableQty = yarnInwardStockDetailsRepository.sumAvailableQtyByItemId(yarnId);
        return ResponseEntity.ok(availableQty != null ? availableQty : BigDecimal.ZERO);
    }

    @GetMapping("/available-qty/{yarnId}/{companyId}")
    public ResponseEntity<BigDecimal> getAvailableQtyByYarnIdAndCompanyId(
            @PathVariable Long yarnId, 
            @PathVariable Long companyId) {
        BigDecimal availableQty = yarnInwardStockDetailsRepository.sumAvailableQtyByItemIdAndCompanyId(yarnId, companyId);
        return ResponseEntity.ok(availableQty != null ? availableQty : BigDecimal.ZERO);
    }

    @GetMapping("/available-qty-by-order")
    public ResponseEntity<Map<Long, BigDecimal>> getAvailableQtyByOrder(
            @RequestParam Long orderId,
            @RequestParam("itemIds") List<Long> itemIds,
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) Long inwardTypeId) {
        if (itemIds == null || itemIds.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyMap());
        }
        List<Object[]> rows = yarnInwardStockDetailsRepository.sumAvailableQtyByOrderIdAndItemIds(orderId, itemIds, inwardTypeId, companyId);
        Map<Long, BigDecimal> availability = new HashMap<>();
        if (rows != null) {
            for (Object[] row : rows) {
                if (row == null || row.length < 2) continue;
                Long itemId = row[0] instanceof Number ? ((Number) row[0]).longValue() : null;
                BigDecimal qty = row[1] instanceof BigDecimal ? (BigDecimal) row[1] : BigDecimal.ZERO;
                if (itemId != null) {
                    availability.put(itemId, qty != null ? qty : BigDecimal.ZERO);
                }
            }
        }
        return ResponseEntity.ok(availability);
    }
}
