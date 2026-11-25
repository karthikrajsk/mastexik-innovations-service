package com.tech3.erp.controller;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.ScanDispatchDTO;
import com.tech3.erp.service.ScanDispatchService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scan-dispatch")
@Tag(name = "Scan Dispatch", description = "Endpoints to capture scan dispatch information")
@Validated
public class ScanDispatchController {

    @Autowired
    private ScanDispatchService service;

    @PostMapping
    @Operation(summary = "Create scan dispatch", description = "Records the scanned rolls and summary for an invoice")
    public ResponseEntity<Object> createScanDispatch(@Valid @RequestBody ScanDispatchDTO dto) {
        try {
            ScanDispatchDTO created = service.createScanDispatch(dto);
            return ResponseUtil.success("Scan dispatch recorded", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Unable to persist scan dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update scan dispatch")
    public ResponseEntity<Object> updateScanDispatch(@PathVariable("id") Long id, @Valid @RequestBody ScanDispatchDTO dto) {
        try {
            ScanDispatchDTO updated = service.updateScanDispatch(id, dto);
            return ResponseUtil.success("Scan dispatch updated", updated);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Unable to update scan dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update scan dispatch by company")
    public ResponseEntity<Object> updateScanDispatchByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id, @Valid @RequestBody ScanDispatchDTO dto) {
        try {
            ScanDispatchDTO updated = service.updateScanDispatchByCompany(id, companyId, dto);
            return ResponseUtil.success("Scan dispatch updated", updated);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Unable to update scan dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete scan dispatch by company")
    public ResponseEntity<Object> deleteScanDispatchByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            service.deleteScanDispatchByCompany(id, companyId);
            return ResponseUtil.success("Scan dispatch deleted", null);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Unable to delete scan dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "List scan dispatches", description = "Fetches all scan dispatch records with pagination support")
    public ResponseEntity<Object> getAllScanDispatches(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        PaginatedResponse<ScanDispatchDTO> records = service.getAllScanDispatchesWithPagination(offset, limit);
        return ResponseUtil.success("Scan dispatches fetched", records);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "List scan dispatches by company", description = "Fetches scan dispatch records for the provided company with pagination support")
    public ResponseEntity<Object> getScanDispatchesByCompany(@PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        PaginatedResponse<ScanDispatchDTO> records = service.getScanDispatchesByCompanyIdWithPagination(companyId, offset, limit);
        return ResponseUtil.success("Scan dispatches fetched for company", records);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get scan dispatch by id")
    public ResponseEntity<Object> getScanDispatch(@PathVariable("id") Long id) {
        return service.getScanDispatchById(id)
                .map(record -> ResponseUtil.success("Scan dispatch retrieved", record))
                .orElseGet(() -> ResponseUtil.error("Scan dispatch not found", HttpStatus.NOT_FOUND));
    }
}