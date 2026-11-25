package com.tech3.erp.controller;

import com.tech3.erp.dto.PurchaseHistoryResponseDTO;
import com.tech3.erp.service.PurchaseHistoryService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/purchase-history", "/api/purchase-orders/history"})
@Tag(name = "Purchase History Controller", description = "APIs for retrieving previous purchase order history")
public class PurchaseHistoryController {

    private final PurchaseHistoryService purchaseHistoryService;

    public PurchaseHistoryController(PurchaseHistoryService purchaseHistoryService) {
        this.purchaseHistoryService = purchaseHistoryService;
    }

    @GetMapping
    @Operation(summary = "Get previous purchase history by item ID",
               description = "Retrieves recent purchase orders and the latest purchase summary for a given item/material")
    public ResponseEntity<Object> getPurchaseHistory(
            @RequestParam(name = "itemId", required = false) Long itemId,
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestHeader(value = "X-Company-Id", required = false) String headerCompanyId,
            @RequestParam(name = "offset", required = false, defaultValue = "0") Long offset,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Long limit) {
        if (itemId == null || itemId <= 0) {
            return ResponseUtil.error("A valid itemId parameter is required", HttpStatus.BAD_REQUEST);
        }

        try {
            Long effectiveCompanyId = companyId != null ? companyId : parseCompanyId(headerCompanyId);
            PurchaseHistoryResponseDTO result = purchaseHistoryService.getPurchaseHistory(itemId, effectiveCompanyId, offset, limit);
            return ResponseUtil.success("Purchase history fetched successfully", result);
        } catch (EntityNotFoundException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return ResponseUtil.error("Failed to retrieve purchase history: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Long parseCompanyId(String headerValue) {
        if (headerValue == null || headerValue.isBlank()) {
            return null;
        }
        try {
            return Long.parseLong(headerValue.trim());
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
