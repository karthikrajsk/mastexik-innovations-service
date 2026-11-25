package com.tech3.erp.controller;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.PieceJoinDTO;
import com.tech3.erp.service.PieceJoinService;
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
@RequestMapping("/api/piece-join")
@Tag(name = "Piece Join Controller", description = "APIs for Piece Join management")
public class PieceJoinController {

    private final PieceJoinService pieceJoinService;

    @Autowired
    public PieceJoinController(PieceJoinService pieceJoinService) {
        this.pieceJoinService = pieceJoinService;
    }

    @PostMapping
    @Operation(summary = "Create Piece Join", description = "Creates a new Piece Join")
    public ResponseEntity<Object> create(@Valid @RequestBody PieceJoinDTO dto) {
        try {
            return ResponseUtil.success("Piece Join created successfully", pieceJoinService.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PieceJoinDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pieceJoinService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PieceJoinDTO>> list() {
        return ResponseEntity.ok(pieceJoinService.list());
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<PaginatedResponse<PieceJoinDTO>> listByCompany(@PathVariable("companyId") Long companyId,
                                                                         @RequestParam(value = "offset", required = false) Long offset,
                                                                         @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(pieceJoinService.listByCompanyWithPagination(companyId, offset, limit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Piece Join", description = "Updates a Piece Join by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PieceJoinDTO dto) {
        try {
            return ResponseUtil.success("Piece Join updated successfully", pieceJoinService.update(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Piece Join by company", description = "Updates a Piece Join by ID within company scope")
    public ResponseEntity<Object> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody PieceJoinDTO dto) {
        try {
            return ResponseUtil.success("Piece Join updated successfully", pieceJoinService.updateByCompany(id, companyId, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Piece Join", description = "Deletes a Piece Join by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            pieceJoinService.delete(id);
            return ResponseUtil.success("Piece Join deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Piece Join by company", description = "Deletes a Piece Join by ID within company scope")
    public ResponseEntity<Object> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        try {
            pieceJoinService.deleteByCompany(id, companyId);
            return ResponseUtil.success("Piece Join deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
