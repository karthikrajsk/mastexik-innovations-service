package com.tech3.erp.controller;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.WeavingWorkOrderDTO;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.service.WeavingWorkOrderService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weaving-work-orders")
@Tag(name = "Weaving Work Orders Controller", description = "APIs for Weaving Work Orders management")
public class WeavingWorkOrderController {

    @Autowired
    private WeavingWorkOrderService service;

    @PostMapping
    @Operation(summary = "Create a new weaving work order")
    public ResponseEntity<?> create(@RequestBody WeavingWorkOrderDTO dto) {
        WeavingWorkOrder saved = service.create(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    @Operation(summary = "Get all weaving work orders, optionally filtered by factoryCompanyId")
    public ResponseEntity<PaginatedResponse<WeavingWorkOrder>> getAll(
            @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(service.getAllWithPagination(offset, limit, factoryCompanyId));
    }

    @GetMapping("/factory/{factoryCompanyId}")
    @Operation(summary = "Get weaving work orders by factoryCompanyId")
    public ResponseEntity<PaginatedResponse<WeavingWorkOrder>> getAllByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId,
                                                                               @RequestParam(value = "offset", required = false) Long offset,
                                                                               @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(service.getAllByFactoryCompanyIdWithPagination(factoryCompanyId, offset, limit));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get weaving work order by ID")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update weaving work order by ID")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody WeavingWorkOrderDTO dto,
                                    @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId) {
        WeavingWorkOrder updated = service.update(id, dto, factoryCompanyId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete weaving work order by ID")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id,
                                         @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId) {
        try {
            if (factoryCompanyId != null) {
                service.delete(id, factoryCompanyId);
            } else {
                service.delete(id);
            }
            return ResponseUtil.success("Weaving Work Order deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Weaving Work Order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}