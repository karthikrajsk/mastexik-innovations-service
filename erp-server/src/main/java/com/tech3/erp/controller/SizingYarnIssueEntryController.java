package com.tech3.erp.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingYarnIssueEntryDTO;
import com.tech3.erp.dto.YarnInwardStockDetailsDto;
import com.tech3.erp.service.SizingYarnIssueEntryService;
import com.tech3.erp.service.StockTransferService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/sizing-yarn-issues")
@Tag(name = "Sizing Yarn Issues", description = "APIs for Sizing Yarn Issues management")

public class SizingYarnIssueEntryController {

    @Autowired
    private SizingYarnIssueEntryService service;
    
    @Autowired
    private StockTransferService stockTransferService;

    @GetMapping
    public ResponseEntity<?> getAllSizingYarnIssueEntrys(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset != null || limit != null) {
            long finalOffset = offset != null ? offset : 0L;
            long finalLimit = limit != null ? limit : 10L;
            return ResponseEntity.ok(service.getAllSizingYarnIssueEntrysWithPagination(finalOffset, finalLimit));
        }
        List<SizingYarnIssueEntryDTO> issues = service.getAllSizingYarnIssueEntrys();
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/paginated")
    public ResponseEntity<PaginatedResponse<SizingYarnIssueEntryDTO>> getAllSizingYarnIssueEntrysWithPagination(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        PaginatedResponse<SizingYarnIssueEntryDTO> response =
                service.getAllSizingYarnIssueEntrysWithPagination(finalOffset, finalLimit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<PaginatedResponse<SizingYarnIssueEntryDTO>> getSizingYarnIssueEntrysByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        PaginatedResponse<SizingYarnIssueEntryDTO> response =
                service.getAllSizingYarnIssueEntrysByCompanyWithPagination(companyId, finalOffset, finalLimit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizingYarnIssueEntryDTO> getSizingYarnIssueEntryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getSizingYarnIssueEntryById(id));
    }

    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<SizingYarnIssueEntryDTO> getSizingYarnIssueEntryByIdAndCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getSizingYarnIssueEntryByIdAndCompany(id, companyId));
    }

    @GetMapping("/{id}/mapping-status")
    @Operation(summary = "Check if Sizing Yarn Issue Entry is mapped")
    public ResponseEntity<Object> getMappingStatus(@PathVariable("id") Long id) {
        try {
            boolean isMapped = service.isMapped(id);
            return ResponseUtil.success("", java.util.Map.of("isMapped", isMapped));
        } catch (Exception e) {
            return ResponseUtil.error("Error checking mapping status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<SizingYarnIssueEntryDTO> createSizingYarnIssueEntry(@RequestBody SizingYarnIssueEntryDTO dto) {
    	SizingYarnIssueEntryDTO createdIssue = service.createSizingYarnIssueEntry(dto);
        return ResponseEntity.ok(createdIssue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SizingYarnIssueEntryDTO> updateSizingYarnIssueEntry(
            @PathVariable("id") Long id,
            @RequestBody SizingYarnIssueEntryDTO dto) {
        return ResponseEntity.ok(service.updateSizingYarnIssueEntry(id, dto));
    }

    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<SizingYarnIssueEntryDTO> updateSizingYarnIssueEntryByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @RequestBody SizingYarnIssueEntryDTO dto) {
        return ResponseEntity.ok(service.updateSizingYarnIssueEntryByCompany(id, companyId, dto));
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSizingYarnIssueEntry(@PathVariable("id") Long id) {
        try {
            service.deleteSizingYarnIssueEntry(id);
            return ResponseUtil.success("Sizing Yarn Issue Entry deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the sizing yarn issue entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    public ResponseEntity<Object> deleteSizingYarnIssueEntryByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        try {
            service.deleteSizingYarnIssueEntryByCompany(id, companyId);
            return ResponseUtil.success("Sizing Yarn Issue Entry deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the sizing yarn issue entry", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @Operation(summary = "Get yarn inward stock details", description = "Retrieve yarn inward stock details filtered by item ID(s) and optionally by outward type")
    // @ApiResponse(responseCode = "200", description = "Successfully retrieved yarn inward stock details")
    // @ApiResponse(responseCode = "400", description = "Bad request - missing or invalid parameters")
    // @GetMapping("/yarn-inward-stock")
    // public ResponseEntity<List<YarnInwardStockDetailsDto>> getYarnInwardStockByItemId(
    //         @Parameter(description = "Item ID or comma-separated Item IDs", required = true, example = "15,16")
    //         @RequestParam(required = true) String itemId,
    //         @Parameter(description = "Outward type ID (optional filter)", required = false, example = "52")
    //         @RequestParam(required = false, defaultValue = "") String outwardTypeId) {
    //     try {
    //         List<Long> itemIdList = java.util.Arrays.stream(itemId.split(","))
    //             .map(String::trim)
    //             .map(Long::parseLong)
    //             .collect(java.util.stream.Collectors.toList());
    //         Long outwardTypeIdLong = outwardTypeId.isEmpty() ? null : Long.parseLong(outwardTypeId);
    //         List<YarnInwardStockDetailsDto> result = service.getYarnInwardStockDetailsByItemIds(itemIdList, outwardTypeIdLong);
    //         return ResponseEntity.ok(result);
    //     } catch (NumberFormatException e) {
    //         return ResponseEntity.badRequest().build();
    //     }
    // }
     @Operation(summary = "Get yarn inward stock details", description = "Retrieve yarn inward stock details filtered by item ID(s), purchase order, or outward type")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved yarn inward stock details")
    @ApiResponse(responseCode = "400", description = "Bad request - missing or invalid parameters")
    @GetMapping("/yarn-inward-stock")
    public ResponseEntity<List<YarnInwardStockDetailsDto>> getYarnInwardStockByItemId(
            @Parameter(description = "Item ID or comma-separated Item IDs", required = false, example = "15,16")
            @RequestParam(value = "itemId", required = false) String itemId,
            @Parameter(description = "Purchase order ID (optional filter)", required = false, example = "15")
            @RequestParam(value = "purchaseOrderId", required = false) Long purchaseOrderId,
            @Parameter(description = "Purchase order type ID / inward type (optional filter)", required = false, example = "1")
            @RequestParam(value = "poTypeId", required = false) Long poTypeId,
            @Parameter(description = "Outward type ID (optional filter)", required = false, example = "52")
            @RequestParam(value = "outwardTypeId", required = false, defaultValue = "") String outwardTypeId,
            @Parameter(description = "Warehouse ID (optional filter)", required = false, example = "5")
            @RequestParam(value = "warehouseId", required = false) Long warehouseId,
            @Parameter(description = "Company ID (optional filter)", required = false, example = "3")
            @RequestParam(value = "companyId", required = false) Long companyId) {
        try {
            List<Long> itemIdList = java.util.Collections.emptyList();
            if (itemId != null && !itemId.trim().isEmpty()) {
                itemIdList = java.util.Arrays.stream(itemId.split(","))
                    .map(String::trim)
                    .filter(str -> !str.isEmpty())
                    .map(Long::parseLong)
                    .collect(java.util.stream.Collectors.toList());
            }
            if (itemIdList.isEmpty() && purchaseOrderId == null) {
                return ResponseEntity.badRequest().build();
            }
            Long outwardTypeIdLong = (outwardTypeId != null && !outwardTypeId.isEmpty()) ? Long.parseLong(outwardTypeId) : null;
            List<YarnInwardStockDetailsDto> result;
            if (warehouseId != null) {
                result = stockTransferService.getYarnInwardStockDetailsByItemIdsAndWarehouse(itemIdList, outwardTypeIdLong, warehouseId, companyId, purchaseOrderId, poTypeId);
            } else {
                result = service.getYarnInwardStockDetailsByItemIds(itemIdList, outwardTypeIdLong, companyId, purchaseOrderId, poTypeId);
            }
            return ResponseEntity.ok(result);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // You can uncomment and use this if you need a separate endpoint for creating with requirements
    /*
    @PostMapping("/with-requirements")
    public ResponseEntity<SizingYarnIssueEntryDTO> createWithRequirements(
            @RequestBody SizingYarnIssueEntryDTO dto) {
        SizingYarnIssueEntryDTO createdIssue = service.createWeavingYarnIssue(dto);
        return ResponseEntity.ok(createdIssue);
    }
    */
}