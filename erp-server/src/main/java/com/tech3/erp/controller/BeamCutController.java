package com.tech3.erp.controller;

import com.tech3.erp.dto.BeamCutDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.BeamCutService;
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
@RequestMapping("/api/beam-cut")
@Tag(name = "Beam Cut Controller", description = "APIs for Beam Cut management")
public class BeamCutController {

    private final BeamCutService service;

    public BeamCutController(BeamCutService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create Beam Cut", description = "Create a new Beam Cut entry")
    public ResponseEntity<Object> create(@Valid @RequestBody BeamCutDTO dto) {
        try {
            return ResponseUtil.success("Beam Cut created successfully", service.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Beam Cuts", description = "List all Beam Cut records")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        PaginatedResponse<BeamCutDTO> list = service.getAllWithPagination(offset, limit);
        return ResponseUtil.success("Beam Cuts fetched successfully", list);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Beam Cuts by Company ID", description = "List Beam Cut records for a company")
    public ResponseEntity<Object> getByCompany(@PathVariable("companyId") Long companyId,
                                               @RequestParam(value = "offset", required = false) Long offset,
                                               @RequestParam(value = "limit", required = false) Long limit) {
        PaginatedResponse<BeamCutDTO> list = service.getByCompanyIdWithPagination(companyId, offset, limit);
        return ResponseUtil.success("Beam Cuts fetched successfully", list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Beam Cut by ID", description = "Fetch a Beam Cut by ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Beam Cut fetched successfully", service.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Beam Cut", description = "Update Beam Cut by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody BeamCutDTO dto) {
        try {
            return ResponseUtil.success("Beam Cut updated successfully", service.update(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Beam Cut by Company ID", description = "Update Beam Cut by ID scoped to a company")
    public ResponseEntity<Object> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody BeamCutDTO dto) {
        try {
            return ResponseUtil.success("Beam Cut updated successfully", service.updateByCompanyId(id, companyId, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Beam Cut", description = "Delete Beam Cut by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Beam Cut deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Beam Cut", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Beam Cut by Company ID", description = "Delete Beam Cut by ID scoped to a company")
    public ResponseEntity<Object> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        try {
            service.deleteByCompanyId(id, companyId);
            return ResponseUtil.success("Beam Cut deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Beam Cut", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
