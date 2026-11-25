package com.tech3.erp.controller;

import com.tech3.erp.dto.BeamStockDTO;
import com.tech3.erp.service.BeamStockService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beam-stock")
@Tag(name = "Beam Stock Controller", description = "APIs for managing Beam Opening Stock & bulk upload")
public class BeamStockController {

    private final BeamStockService beamStockService;

    public BeamStockController(BeamStockService beamStockService) {
        this.beamStockService = beamStockService;
    }

    @PostMapping
    @Operation(summary = "Create single Beam Stock", description = "Creates a single Beam Stock record.")
    public ResponseEntity<BeamStockDTO> createBeamStock(@Valid @RequestBody BeamStockDTO dto) {
        BeamStockDTO created = beamStockService.saveBeamStock(dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/bulk-upload")
    @Operation(summary = "Bulk Upload Beam Stock", description = "Uploads multiple Beam Opening Stock records from Excel.")
    public ResponseEntity<BeamStockDTO.BulkUploadResponse> bulkUploadBeamStock(@RequestBody BeamStockDTO.BulkUploadRequest request) {
        BeamStockDTO.BulkUploadResponse response = beamStockService.saveBulkBeamStock(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-on-loom-mtr")
    @Operation(summary = "Bulk Update On-Loom Mtr", description = "Updates On-Loom Mtr for existing Beam Stock and Beam Gating Details records from Excel.")
    public ResponseEntity<BeamStockDTO.BulkUploadResponse> updateOnLoomMtrBulk(@RequestBody BeamStockDTO.OnLoomMtrUpdateRequest request) {
        BeamStockDTO.BulkUploadResponse response = beamStockService.updateOnLoomMtrBulk(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all Beam Stock", description = "Fetches all Beam Stock records with optional pagination.")
    public ResponseEntity<Object> getAllBeamStock(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(beamStockService.getAllBeamStock());
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(beamStockService.getAllBeamStockWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Beam Stock by company", description = "Fetches Beam Stock records by company ID with optional pagination.")
    public ResponseEntity<Object> getAllBeamStockByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (companyId == null || companyId <= 0) {
            return getAllBeamStock(offset, limit);
        }
        if (offset == null && limit == null) {
            return ResponseEntity.ok(beamStockService.getAllBeamStockByCompany(companyId));
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(beamStockService.getAllBeamStockByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Beam Stock by ID", description = "Fetches a Beam Stock record by ID.")
    public ResponseEntity<BeamStockDTO> getBeamStockById(@PathVariable("id") Long id) {
        BeamStockDTO dto = beamStockService.getBeamStockById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Beam Stock", description = "Updates an existing Beam Stock record.")
    public ResponseEntity<BeamStockDTO> updateBeamStock(@PathVariable("id") Long id, @Valid @RequestBody BeamStockDTO dto) {
        BeamStockDTO updated = beamStockService.updateBeamStock(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Beam Stock", description = "Deletes a Beam Stock record by ID.")
    public ResponseEntity<Object> deleteBeamStock(@PathVariable("id") Long id) {
        try {
            beamStockService.deleteBeamStock(id);
            return ResponseUtil.success("Beam Stock deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Beam Stock", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
