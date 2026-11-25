package com.tech3.erp.controller;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.PieceSplitDTO;
import com.tech3.erp.service.PieceSplitService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/piece-split")
@Tag(name = "Piece Split Controller", description = "APIs for Piece Split management")
public class PieceSplitController {

    private final PieceSplitService pieceSplitService;

    @Autowired
    public PieceSplitController(PieceSplitService pieceSplitService) {
        this.pieceSplitService = pieceSplitService;
    }

    @PostMapping
    @Operation(summary = "Create Piece Split", description = "Creates a new Piece Split")
    public ResponseEntity<Object> create(@Valid @RequestBody PieceSplitDTO dto) {
        try {
            return ResponseUtil.success("Piece Split created successfully", pieceSplitService.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PieceSplitDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pieceSplitService.getById(id));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<PieceSplitDTO>> list(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(pieceSplitService.listWithPagination(offset, limit, null));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<PaginatedResponse<PieceSplitDTO>> listByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(pieceSplitService.listWithPagination(offset, limit, companyId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Piece Split", description = "Updates a Piece Split by ID")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody PieceSplitDTO dto) {
        try {
            return ResponseUtil.success("Piece Split updated successfully", pieceSplitService.update(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Piece Split by company", description = "Updates a Piece Split by ID within company scope")
    public ResponseEntity<Object> updateByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id, @Valid @RequestBody PieceSplitDTO dto) {
        try {
            return ResponseUtil.success("Piece Split updated successfully", pieceSplitService.updateByCompany(id, companyId, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Piece Split", description = "Deletes a Piece Split by ID")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            pieceSplitService.delete(id);
            return ResponseUtil.success("Piece Split deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Piece Split by company", description = "Deletes a Piece Split by ID within company scope")
    public ResponseEntity<Object> deleteByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            pieceSplitService.deleteByCompany(id, companyId);
            return ResponseUtil.success("Piece Split deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
