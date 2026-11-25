package com.tech3.erp.controller;

import com.tech3.erp.dto.SizingProductionDTO;
import com.tech3.erp.service.SizingProductionService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
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
@RequestMapping("/api/sizing-production")
@Tag(name = "Sizing Production Controller", description = "APIs for managing Sizing Production entries")
@Validated
public class SizingProductionController {

    private final SizingProductionService service;

    public SizingProductionController(SizingProductionService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create Sizing Production", description = "Create a new Sizing Production entry")
    public ResponseEntity<Object> create(@Valid @RequestBody SizingProductionDTO dto) {
        try {
            return ResponseUtil.success("Sizing production created successfully", service.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Sizing Production by ID", description = "Fetch a Sizing Production entry by ID")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        try {
            return ResponseUtil.success("Sizing production fetched successfully", service.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Sizing Productions", description = "List all Sizing Production entries with pagination")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Sizing productions fetched successfully", service.getAllWithPagination(offset, limit));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Sizing Productions by Company", description = "List Sizing Production entries for a company with pagination")
    public ResponseEntity<Object> getByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Sizing productions fetched successfully", service.getByCompanyIdWithPagination(companyId, offset, limit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Sizing Production", description = "Update Sizing Production entry by ID")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody SizingProductionDTO dto) {
        try {
            return ResponseUtil.success("Sizing production updated successfully", service.update(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Sizing Production by Company", description = "Update Sizing Production entry scoped to a company")
    public ResponseEntity<Object> updateByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @Valid @RequestBody SizingProductionDTO dto) {
        try {
            return ResponseUtil.success("Sizing production updated successfully", service.updateByCompanyId(id, companyId, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Sizing Production", description = "Delete Sizing Production entry by ID")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Sizing production deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Sizing production", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Sizing Production by Company", description = "Delete Sizing Production entry scoped to a company")
    public ResponseEntity<Object> deleteByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        try {
            service.deleteByCompanyId(id, companyId);
            return ResponseUtil.success("Sizing production deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Sizing production", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
