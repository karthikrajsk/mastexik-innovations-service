package com.tech3.erp.controller;

import com.tech3.erp.dto.MachineMasterDTO;
import com.tech3.erp.service.MachineMasterService;
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
@RequestMapping("/api/machine-master")
@Tag(name = "Machine Master Controller", description = "APIs for Machine Master management")
@Validated
public class MachineMasterController {

    private final MachineMasterService service;

    public MachineMasterController(MachineMasterService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create Machine", description = "Create a new Machine entry")
    public ResponseEntity<Object> create(@Valid @RequestBody MachineMasterDTO dto) {
        try {
            return ResponseUtil.success("Machine created successfully", service.create(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Machine by ID", description = "Fetch a Machine by ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Machine fetched successfully", service.getById(id));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Machines", description = "List all Machines")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Machines fetched successfully", service.getAllWithPagination(offset, limit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Machine", description = "Update Machine by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody MachineMasterDTO dto) {
        try {
            return ResponseUtil.success("Machine updated successfully", service.update(id, dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Machine", description = "Delete Machine by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Machine deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Machine", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
