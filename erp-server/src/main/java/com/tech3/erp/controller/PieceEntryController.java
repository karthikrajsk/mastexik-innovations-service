package com.tech3.erp.controller;

import com.tech3.erp.dto.PieceEntryDTO;
import com.tech3.erp.dto.DoffingEntryHistoryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.PieceEntryService;
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
@RequestMapping("/api/piece-entries")
@Tag(name = "Piece Entry Controller", description = "APIs for Piece Entry management")
public class PieceEntryController {

    private final PieceEntryService pieceEntryService;

    @Autowired
    public PieceEntryController(PieceEntryService pieceEntryService) {
        this.pieceEntryService = pieceEntryService;
    }

    @PostMapping
    @Operation(summary = "Create Piece Entry", description = "Creates a new Piece Entry")
    public ResponseEntity<Object> create(@Valid @RequestBody PieceEntryDTO dto) {
        try {
            return ResponseUtil.success("Piece Entry created successfully", pieceEntryService.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk Create/Update Piece Entries", description = "Creates or updates multiple Piece Entries in batch")
    public ResponseEntity<Object> bulkCreate(@RequestBody List<PieceEntryDTO> dtos) {
        try {
            return ResponseUtil.success("Piece Entries saved successfully", pieceEntryService.bulkCreate(dtos));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PieceEntryDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pieceEntryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PieceEntryDTO>> list() {
        return ResponseEntity.ok(pieceEntryService.list());
    }

    @GetMapping("/doffing")
    public ResponseEntity<PaginatedResponse<PieceEntryDTO>> listDoffingEntries(
            @RequestParam(required = false) Long companyId,
            @RequestParam(defaultValue = "0") long offset,
            @RequestParam(defaultValue = "50") long limit,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(pieceEntryService.listDoffingEntries(companyId, offset, limit, search));
    }

    @GetMapping("/doffing/history")
    public ResponseEntity<List<DoffingEntryHistoryDTO>> listDoffingHistory(
            @RequestParam(required = false) Long companyId) {
        return ResponseEntity.ok(pieceEntryService.listDoffingHistory(companyId));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<PieceEntryDTO>> listByCompany(
            @PathVariable Long companyId,
            @RequestParam(required = false) Integer isFactoryFabric,
            @RequestParam(required = false) String sourceType,
            @RequestParam(required = false) String weavingContractNo) {
        if (weavingContractNo != null && !weavingContractNo.trim().isEmpty()) {
            return ResponseEntity.ok(pieceEntryService.listByCompanyWithWeavingContractNo(companyId, isFactoryFabric, sourceType, weavingContractNo));
        }
        if (isFactoryFabric != null || sourceType != null) {
            return ResponseEntity.ok(pieceEntryService.listByCompanyWithFilters(companyId, isFactoryFabric, sourceType));
        }
        return ResponseEntity.ok(pieceEntryService.listByCompany(companyId));
    }

    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<PieceEntryDTO> getByIdAndCompany(@PathVariable Long companyId, @PathVariable Long id) {
        return ResponseEntity.ok(pieceEntryService.getByIdAndCompany(id, companyId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Piece Entry", description = "Updates a Piece Entry by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PieceEntryDTO dto) {
        try {
            return ResponseUtil.success("Piece Entry updated successfully", pieceEntryService.update(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Piece Entry by company", description = "Updates a Piece Entry by ID within company scope")
    public ResponseEntity<Object> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody PieceEntryDTO dto) {
        try {
            return ResponseUtil.success("Piece Entry updated successfully", pieceEntryService.updateByCompany(id, companyId, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Piece Entry", description = "Deletes a Piece Entry by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            pieceEntryService.delete(id);
            return ResponseUtil.success("Piece Entry deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Piece Entry by company", description = "Deletes a Piece Entry by ID within company scope")
    public ResponseEntity<Object> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        try {
            pieceEntryService.deleteByCompany(id, companyId);
            return ResponseUtil.success("Piece Entry deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
