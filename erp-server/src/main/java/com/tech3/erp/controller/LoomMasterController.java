package com.tech3.erp.controller;

import com.tech3.erp.dto.LoomMasterDTO;
import com.tech3.erp.service.LoomMasterService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loom-master")
@Tag(name = "Loom Master Controller", description = "APIs for Loom Master management")
@Validated
public class LoomMasterController {

    private final LoomMasterService service;

    public LoomMasterController(LoomMasterService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create Loom", description = "Create a new Loom entry")
    public ResponseEntity<Object> create(@Valid @RequestBody LoomMasterDTO dto) {
        try {
            return ResponseUtil.success("Loom created successfully", service.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Loom by ID", description = "Fetch a Loom by ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Loom fetched successfully", service.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Looms", description = "List all Looms")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Looms fetched successfully", service.getAllWithPagination(offset, limit));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Looms by Company ID", description = "List Looms for a company")
    public ResponseEntity<Object> getByCompanyId(
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Looms fetched successfully", service.getByCompanyIdWithPagination(companyId, offset, limit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Loom", description = "Update Loom by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody LoomMasterDTO dto) {
        try {
            return ResponseUtil.success("Loom updated successfully", service.update(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Loom by Company ID", description = "Update Loom by ID scoped to a company")
    public ResponseEntity<Object> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody LoomMasterDTO dto) {
        try {
            return ResponseUtil.success("Loom updated successfully", service.updateByCompanyId(id, companyId, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Loom", description = "Delete Loom by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Loom deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Loom", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Loom by Company ID", description = "Delete Loom by ID scoped to a company")
    public ResponseEntity<Object> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        try {
            service.deleteByCompanyId(id, companyId);
            return ResponseUtil.success("Loom deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Loom", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
