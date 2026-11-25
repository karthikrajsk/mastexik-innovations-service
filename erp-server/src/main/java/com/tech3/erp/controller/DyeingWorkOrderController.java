package com.tech3.erp.controller;

import com.tech3.erp.dto.DyeingWorkOrderDTO;
import com.tech3.erp.service.DyeingWorkOrderService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dyeing-work-orders")
@Tag(name = "Dyeing Work Order Controller", description = "APIs for Dyeing Work Order management")
public class DyeingWorkOrderController {

    @Autowired
    private DyeingWorkOrderService dyeingWorkOrderService;

    @PostMapping
    @Operation(summary = "Create Dyeing Work Order")
    public ResponseEntity<Object> createDyeingWorkOrder(@Valid @RequestBody DyeingWorkOrderDTO dto) {
        DyeingWorkOrderDTO created = dyeingWorkOrderService.createDyeingWorkOrder(dto);
        return ResponseUtil.success("Dyeing Work Order created successfully", created);
    }

    @GetMapping
    @Operation(summary = "Get all Dyeing Work Orders")
    public ResponseEntity<Object> getAllDyeingWorkOrders(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        
        return ResponseUtil.success("Dyeing Work Orders fetched successfully", dyeingWorkOrderService.getAllDyeingWorkOrdersWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Dyeing Work Orders by company")
    public ResponseEntity<Object> getDyeingWorkOrdersByCompany(
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Dyeing Work Orders fetched successfully",
                dyeingWorkOrderService.getAllDyeingWorkOrdersByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }

    @GetMapping("/type/{processType}")
    @Operation(summary = "Get Dyeing Work Orders by Process Type")
    public ResponseEntity<Object> getDyeingWorkOrdersByType(
            @PathVariable String processType,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        
        return ResponseUtil.success("Dyeing Work Orders fetched successfully", dyeingWorkOrderService.getAllDyeingWorkOrdersByTypeWithPagination(processType, finalOffset, finalLimit));
    }

    @GetMapping("/type/{processType}/company/{companyId}")
    @Operation(summary = "Get Dyeing Work Orders by Process Type and company")
    public ResponseEntity<Object> getDyeingWorkOrdersByTypeAndCompany(
            @PathVariable String processType,
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Dyeing Work Orders fetched successfully",
                dyeingWorkOrderService.getAllDyeingWorkOrdersByCompanyAndTypeWithPagination(companyId, processType, finalOffset, finalLimit));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Dyeing Work Order by ID")
    public ResponseEntity<Object> getDyeingWorkOrderById(@PathVariable Long id) {
        DyeingWorkOrderDTO dto = dyeingWorkOrderService.getDyeingWorkOrderById(id);
        if (dto != null) {
            return ResponseUtil.success("Dyeing Work Order fetched successfully", dto);
        } else {
            return ResponseUtil.error("Dyeing Work Order not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Dyeing Work Order by company")
    public ResponseEntity<Object> getDyeingWorkOrderByIdAndCompany(@PathVariable Long companyId, @PathVariable Long id) {
        try {
            return ResponseUtil.success("Dyeing Work Order fetched successfully",
                    dyeingWorkOrderService.getDyeingWorkOrderByIdAndCompany(id, companyId));
        } catch (RuntimeException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/type/{processType}/company/{companyId}/{id}")
    @Operation(summary = "Get Dyeing Work Order by Process Type and company")
    public ResponseEntity<Object> getDyeingWorkOrderByIdAndCompanyAndType(
            @PathVariable String processType,
            @PathVariable Long companyId,
            @PathVariable Long id) {
        try {
            return ResponseUtil.success("Dyeing Work Order fetched successfully",
                    dyeingWorkOrderService.getDyeingWorkOrderByIdAndCompanyAndType(id, companyId, processType));
        } catch (RuntimeException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Dyeing Work Order")
    public ResponseEntity<Object> updateDyeingWorkOrder(@PathVariable Long id, @Valid @RequestBody DyeingWorkOrderDTO dto) {
        try {
            DyeingWorkOrderDTO updated = dyeingWorkOrderService.updateDyeingWorkOrder(id, dto);
            return ResponseUtil.success("Dyeing Work Order updated successfully", updated);
        } catch (RuntimeException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Dyeing Work Order by company")
    public ResponseEntity<Object> updateDyeingWorkOrderByCompany(
            @PathVariable Long companyId,
            @PathVariable Long id,
            @Valid @RequestBody DyeingWorkOrderDTO dto) {
        try {
            return ResponseUtil.success("Dyeing Work Order updated successfully",
                    dyeingWorkOrderService.updateDyeingWorkOrderByCompany(id, companyId, dto));
        } catch (RuntimeException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/type/{processType}/company/{companyId}/{id}")
    @Operation(summary = "Update Dyeing Work Order by Process Type and company")
    public ResponseEntity<Object> updateDyeingWorkOrderByCompanyAndType(
            @PathVariable String processType,
            @PathVariable Long companyId,
            @PathVariable Long id,
            @Valid @RequestBody DyeingWorkOrderDTO dto) {
        try {
            return ResponseUtil.success("Dyeing Work Order updated successfully",
                    dyeingWorkOrderService.updateDyeingWorkOrderByCompanyAndType(id, companyId, processType, dto));
        } catch (RuntimeException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Dyeing Work Order by company")
    public ResponseEntity<Object> deleteDyeingWorkOrderByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        boolean deleted = dyeingWorkOrderService.deleteDyeingWorkOrderByCompany(id, companyId);
        if (deleted) {
            return ResponseUtil.success("Dyeing Work Order deleted successfully", null);
        } else {
            return ResponseUtil.error("Dyeing Work Order not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/type/{processType}/company/{companyId}/{id}")
    @Operation(summary = "Delete Dyeing Work Order by Process Type and company")
    public ResponseEntity<Object> deleteDyeingWorkOrderByCompanyAndType(
            @PathVariable String processType,
            @PathVariable Long companyId,
            @PathVariable Long id) {
        boolean deleted = dyeingWorkOrderService.deleteDyeingWorkOrderByCompanyAndType(id, companyId, processType);
        if (deleted) {
            return ResponseUtil.success("Dyeing Work Order deleted successfully", null);
        } else {
            return ResponseUtil.error("Dyeing Work Order not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Dyeing Work Order")
    public ResponseEntity<Object> deleteDyeingWorkOrder(@PathVariable Long id) {
        boolean deleted = dyeingWorkOrderService.deleteDyeingWorkOrder(id);
        if (deleted) {
            return ResponseUtil.success("Dyeing Work Order deleted successfully", null);
        } else {
            return ResponseUtil.error("Dyeing Work Order not found", HttpStatus.NOT_FOUND);
        }
    }
}