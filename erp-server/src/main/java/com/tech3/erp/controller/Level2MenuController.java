package com.tech3.erp.controller;

import com.tech3.erp.dto.Level2MenuDTO;
import com.tech3.erp.service.Level2MenuService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/level2-menus")
@Tag(name = "Level2 Menu Controller", description = "APIs for Level2 Menu management")
public class Level2MenuController {

    private final Level2MenuService service;

    public Level2MenuController(Level2MenuService service) { this.service = service; }

    @PostMapping
    @Operation(summary = "Create Level2 Menu")
    public ResponseEntity<Object> create(@Valid @RequestBody Level2MenuDTO dto) {
        try {
            return ResponseUtil.success("Created successfully", service.create(dto));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Level2 Menu by ID")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Fetched successfully", service.get(id));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List Level2 Menus")
    public ResponseEntity<Object> list(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Fetched successfully", service.listWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/by-user/{userId}")
    @Operation(summary = "List Level2 Menus by User")
    public ResponseEntity<Object> listByUser(@PathVariable Long userId) {
        return ResponseUtil.success("Fetched successfully", service.listByUser(userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Level2 Menu")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody Level2MenuDTO dto) {
        try {
            return ResponseUtil.success("Updated successfully", service.update(id, dto));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Level2 Menu")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Deleted successfully", null);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}