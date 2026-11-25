package com.tech3.erp.controller;

import com.tech3.erp.dto.Level1MenuDTO;
import com.tech3.erp.service.Level1MenuService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/level1-menus")
@Tag(name = "Level1 Menu Controller", description = "APIs for Level1 Menu management")
public class Level1MenuController {

    private final Level1MenuService service;

    public Level1MenuController(Level1MenuService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Create Level1 Menu")
    public ResponseEntity<Object> create(@Valid @RequestBody Level1MenuDTO dto) {
        try {
            return ResponseUtil.success("Created successfully", service.create(dto));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Level1 Menu by ID")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Fetched successfully", service.get(id));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List Level1 Menus")
    public ResponseEntity<Object> list(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Fetched successfully", service.listWithPagination(finalOffset, finalLimit));
    }

    // Backward-compat by-user endpoints (existing frontend relies on these)
    @GetMapping("/by-user/{userId}")
    @Operation(summary = "Get Level1 Menu by User")
    public ResponseEntity<Object> getByUser(@PathVariable Long userId) {
        return ResponseUtil.success("Fetched successfully", service.getByUser(userId));
    }

    @PostMapping("/by-user")
    @Operation(summary = "Save Level1 Menu by User")
    public ResponseEntity<Object> saveByUser(@Valid @RequestBody Level1MenuDTO dto) {
        try {
            return ResponseUtil.success("Saved successfully", service.saveByUser(dto));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Level1 Menu")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody Level1MenuDTO dto) {
        try {
            return ResponseUtil.success("Updated successfully", service.update(id, dto));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Level1 Menu")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Deleted successfully", null);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
