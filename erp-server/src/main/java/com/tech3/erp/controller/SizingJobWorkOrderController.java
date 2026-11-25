package com.tech3.erp.controller;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingJobWorkOrderDTO;
import com.tech3.erp.service.SizingJobWorkOrderService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/sizing-jobwork-orders")
@Tag(name = "Sizing Jobwork Orders", description = "APIs for Sizing Jobwork Orders management")
public class SizingJobWorkOrderController {

    private final SizingJobWorkOrderService service;

    public SizingJobWorkOrderController(SizingJobWorkOrderService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create a new sizing jobwork order")
    public ResponseEntity<Object> create(@RequestBody SizingJobWorkOrderDTO dto) {
        try {
            return ResponseUtil.success("Sizing Jobwork Order created successfully", service.create(dto));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Get sizing jobwork orders with optional company filter")
    public ResponseEntity<Object> getAll(
        @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId,
        @RequestParam(value = "offset", required = false) Long offset,
        @RequestParam(value = "limit", required = false) Long limit
    ) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Sizing Jobwork Orders fetched successfully", service.getAll(factoryCompanyId, finalOffset, finalLimit));
    }

    @GetMapping("/factory/{factoryCompanyId}")
    @Operation(summary = "Get sizing jobwork orders by factoryCompanyId")
    public ResponseEntity<Object> getAllByFactory(
        @PathVariable("factoryCompanyId") Long factoryCompanyId,
        @RequestParam(value = "offset", required = false) Long offset,
        @RequestParam(value = "limit", required = false) Long limit
    ) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        try {
            return ResponseUtil.success("Sizing Jobwork Orders fetched successfully", service.getAllByFactoryCompanyId(factoryCompanyId, finalOffset, finalLimit));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sizing jobwork order by id")
    public ResponseEntity<Object> getById(
        @PathVariable("id") Long id,
        @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId
    ) {
        try {
            return ResponseUtil.success("Sizing Jobwork Order fetched successfully", service.getById(id, factoryCompanyId));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sizing jobwork order")
    public ResponseEntity<Object> update(
        @PathVariable("id") Long id,
        @RequestBody SizingJobWorkOrderDTO dto,
        @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId
    ) {
        try {
            return ResponseUtil.success("Sizing Jobwork Order updated successfully", service.update(id, dto, factoryCompanyId));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sizing jobwork order")
    public ResponseEntity<Object> delete(
        @PathVariable("id") Long id,
        @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId
    ) {
        try {
            service.delete(id, factoryCompanyId);
            return ResponseUtil.success("Sizing Jobwork Order deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to delete Sizing Jobwork Order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
