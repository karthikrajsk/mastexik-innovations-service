package com.tech3.erp.controller;

import com.tech3.erp.dto.BeamGatingDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.exception.ApiException;
import com.tech3.erp.service.BeamGatingService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/beam-gating")
@Tag(name = "Beam Gating Controller", description = "APIs for Beam Gating management")
public class BeamGatingController {

    private final BeamGatingService service;

    public BeamGatingController(BeamGatingService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create Beam Gating", description = "Create a new Beam Gating entry")
    public ResponseEntity<Object> create(@Valid @RequestBody BeamGatingDTO dto) {
        try {
            return ResponseUtil.success("Beam Gating created successfully", service.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Beam Gatings", description = "List all Beam Gating records")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        PaginatedResponse<BeamGatingDTO> list = service.getAllWithPagination(offset, limit);
        return ResponseUtil.success("Beam Gatings fetched successfully", list);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Beam Gatings by Company ID", description = "List Beam Gating records for a company")
    public ResponseEntity<Object> getByCompany(@PathVariable("companyId") Long companyId,
                                               @RequestParam(value = "offset", required = false) Long offset,
                                               @RequestParam(value = "limit", required = false) Long limit) {
        PaginatedResponse<BeamGatingDTO> list = service.getByCompanyIdWithPagination(companyId, offset, limit);
        return ResponseUtil.success("Beam Gatings fetched successfully", list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Beam Gating by ID", description = "Fetch a Beam Gating by ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Beam Gating fetched successfully", service.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Beam Gating", description = "Update Beam Gating by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody BeamGatingDTO dto) {
        try {
            return ResponseUtil.success("Beam Gating updated successfully", service.update(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Beam Gating by Company ID", description = "Update Beam Gating by ID scoped to a company")
    public ResponseEntity<Object> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody BeamGatingDTO dto) {
        try {
            return ResponseUtil.success("Beam Gating updated successfully", service.updateByCompanyId(id, companyId, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Beam Gating", description = "Delete Beam Gating by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Beam Gating deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ApiException e) {
            return ResponseUtil.error(e.getMessage(), e.getStatus());
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Beam Gating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Beam Gating by Company ID", description = "Delete Beam Gating by ID scoped to a company")
    public ResponseEntity<Object> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        try {
            service.deleteByCompanyId(id, companyId);
            return ResponseUtil.success("Beam Gating deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ApiException e) {
            return ResponseUtil.error(e.getMessage(), e.getStatus());
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Beam Gating", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
