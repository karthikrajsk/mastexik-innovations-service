package com.tech3.erp.controller;

import com.tech3.erp.dto.MillDTO;
import com.tech3.erp.service.MillService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mill")
@Tag(name = "Mill Controller", description = "APIs for Mill management")
public class MillController {

    private final MillService millService;

    public MillController(MillService millService) {
        this.millService = millService;
    }

    @PostMapping
    @Operation(summary = "Create Mill")
    public ResponseEntity<Object> create(@Valid @RequestBody MillDTO dto) {
        return ResponseUtil.success("Mill created successfully", millService.createMill(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Mill by ID")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return ResponseUtil.success("Mill fetched successfully", millService.getMillById(id));
    }

    @GetMapping
    @Operation(summary = "List all Mills")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Mills fetched successfully", millService.getAllMillsWithPagination(offset, limit));
    }

    @GetMapping("/names")
    @Operation(summary = "Get Mill Names")
    public ResponseEntity<Object> getMillNames() {
        return ResponseUtil.success("Mill names fetched successfully", millService.getAllMillNames());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Mill")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody MillDTO dto) {
        return ResponseUtil.success("Mill updated successfully", millService.updateMill(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Mill")
    public ResponseEntity<Object> deleteMill(@PathVariable Long id) {
        try {
            millService.deleteMill(id);
            return ResponseUtil.success("Mill deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Mill", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
