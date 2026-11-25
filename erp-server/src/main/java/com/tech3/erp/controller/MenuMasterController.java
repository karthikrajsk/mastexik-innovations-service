package com.tech3.erp.controller;

import com.tech3.erp.dto.MenuMasterDTO;
import com.tech3.erp.service.MenuMasterService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menu-masters")
@Tag(name = "Menu Master Controller", description = "APIs for Menu Master management")
public class MenuMasterController {

    private final MenuMasterService service;

    public MenuMasterController(MenuMasterService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create Menu Master")
    public ResponseEntity<Object> create(@Valid @RequestBody MenuMasterDTO dto) {
        try {
            return ResponseUtil.success("Created successfully", service.create(dto));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Menu Master by ID")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Fetched successfully", service.get(id));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List Menu Masters")
    public ResponseEntity<Object> list() {
        return ResponseUtil.success("Fetched successfully", service.list());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Menu Master")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody MenuMasterDTO dto) {
        try {
            return ResponseUtil.success("Updated successfully", service.update(id, dto));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Menu Master")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Deleted successfully", null);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}