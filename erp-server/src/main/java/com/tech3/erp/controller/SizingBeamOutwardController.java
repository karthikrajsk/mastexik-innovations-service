package com.tech3.erp.controller;

import com.tech3.erp.dto.SizingBeamOutwardDTO;
import com.tech3.erp.service.SizingBeamOutwardService;
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
@RequestMapping("/api/sizing-beam-outward")
@Tag(name = "Sizing Beam Outward Controller", description = "APIs for managing Sizing Beam Outward entries")
@Validated
public class SizingBeamOutwardController {

    private final SizingBeamOutwardService service;

    public SizingBeamOutwardController(SizingBeamOutwardService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create Sizing Beam Outward", description = "Create a new Sizing Beam Outward entry")
    public ResponseEntity<Object> create(@Valid @RequestBody SizingBeamOutwardDTO dto) {
        try {
            return ResponseUtil.success("Sizing beam outward created successfully", service.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Sizing Beam Outward by ID", description = "Fetch a Sizing Beam Outward entry by ID")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        try {
            return ResponseUtil.success("Sizing beam outward fetched successfully", service.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List Sizing Beam Outwards", description = "List Sizing Beam Outward entries with pagination")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Sizing beam outward entries fetched successfully", service.getAllWithPagination(offset, limit));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "List Sizing Beam Outwards by Company", description = "List Sizing Beam Outward entries for a company with pagination")
    public ResponseEntity<Object> getByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Sizing beam outward entries fetched successfully", service.getByCompanyIdWithPagination(companyId, offset, limit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Sizing Beam Outward", description = "Update a Sizing Beam Outward entry by ID")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody SizingBeamOutwardDTO dto) {
        try {
            return ResponseUtil.success("Sizing beam outward updated successfully", service.update(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Sizing Beam Outward by Company", description = "Update a Sizing Beam Outward entry scoped to a company")
    public ResponseEntity<Object> updateByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @Valid @RequestBody SizingBeamOutwardDTO dto) {
        try {
            return ResponseUtil.success("Sizing beam outward updated successfully", service.updateByCompanyId(companyId, id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Sizing Beam Outward", description = "Delete a Sizing Beam Outward entry by ID")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Sizing beam outward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Sizing Beam Outward by Company", description = "Delete a Sizing Beam Outward entry scoped to a company")
    public ResponseEntity<Object> deleteByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        try {
            service.deleteByCompanyId(companyId, id);
            return ResponseUtil.success("Sizing beam outward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
