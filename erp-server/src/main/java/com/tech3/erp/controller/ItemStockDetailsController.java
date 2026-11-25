package com.tech3.erp.controller;

import com.tech3.erp.service.ItemOutwardService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-stock-details")
@Tag(name = "Item Stock Details Controller", description = "APIs for Item Stock Details filtering")
public class ItemStockDetailsController {

    private final ItemOutwardService itemOutwardService;

    public ItemStockDetailsController(ItemOutwardService itemOutwardService) {
        this.itemOutwardService = itemOutwardService;
    }

    @GetMapping("/filter")
    @Operation(summary = "Get Item Stock Details by Filter", description = "Fetch item stock details filtered by vendorId, warehouseId, and companyId")
    public ResponseEntity<Object> getItemStockDetailsByFilter(
            @RequestParam(value = "vendorId", required = false) Long vendorId,
            @RequestParam(value = "warehouseId", required = false) Long warehouseId,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        try {
            List<java.util.Map<String, Object>> result = itemOutwardService.getItemStockDetailsByFilter(vendorId, warehouseId, companyId);
            return ResponseUtil.success("Item stock details fetched successfully", result);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching item stock details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

