package com.tech3.erp.controller;

import com.tech3.erp.dto.BrushingStockDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.BrushingStockService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brushing-stock")
@Tag(name = "Brushing Stock Controller", description = "APIs for bulk uploading and listing brushing stock entries")
public class BrushingStockController {

    private final BrushingStockService brushingStockService;

    public BrushingStockController(BrushingStockService brushingStockService) {
        this.brushingStockService = brushingStockService;
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get all Brushing Stock by Company", description = "Retrieves brushing stock entries for a specific company.")
    public ResponseEntity<Object> getBrushingStockByCompany(
            @PathVariable Long companyId,
            @RequestParam(required = false) Long offset,
            @RequestParam(required = false) Long limit,
            @RequestParam(required = false) String status) {
        try {
            PaginatedResponse<BrushingStockDTO> paginated = brushingStockService.getBrushingStockByCompany(companyId, offset, limit, status);
            return ResponseUtil.success("Brushing stock entries fetched successfully", paginated);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch brushing stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bulk")
    @Operation(summary = "Save Brushing Stock in Bulk", description = "Saves list of parsed brushing stock entries.")
    public ResponseEntity<Object> saveBulk(@RequestBody List<BrushingStockDTO> dtos) {
        try {
            List<BrushingStockDTO> saved = brushingStockService.saveBulk(dtos);
            return ResponseUtil.success("Brushing stock uploaded successfully", saved);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to upload brushing stock: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
