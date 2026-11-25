package com.tech3.erp.controller;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.YarnSalesDTO;
import com.tech3.erp.entity.YarnSales;
import com.tech3.erp.service.YarnSalesService;
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
@RequestMapping("/api/yarn-sales-orders")
@Tag(name = "Yarn Sales Controller", description = "APIs for Yarn Sales management")
public class YarnSalesController {

    @Autowired
    private YarnSalesService service;

    @PostMapping
    @Operation(summary = "Create a new yarn sales order")
    public ResponseEntity<?> create(@RequestBody YarnSalesDTO dto) {
        YarnSales saved = service.create(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    @Operation(summary = "Get all yarn sales orders, optionally filtered by companyId")
    public ResponseEntity<PaginatedResponse<YarnSales>> getAll(
            @RequestParam(value = "companyId", required = false) Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(service.getAllWithPagination(offset, limit, companyId));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get yarn sales orders by companyId")
    public ResponseEntity<List<YarnSales>> getAllByCompany(@PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok(service.getAllByCompanyId(companyId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get yarn sales order by ID, optionally filtered by companyId")
    public ResponseEntity<?> getById(@PathVariable("id") Long id,
                                     @RequestParam(value = "companyId", required = false) Long companyId) {
        return ResponseEntity.ok(service.getById(id, companyId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update yarn sales order by ID, filtered by companyId")
    public ResponseEntity<?> update(@PathVariable("id") Long id,
                                    @RequestBody YarnSalesDTO dto,
                                    @RequestParam(value = "companyId") Long companyId) {
        YarnSales updated = service.update(id, dto, companyId);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update yarn sales order by companyId and ID")
    public ResponseEntity<?> updateByCompanyAndId(@PathVariable("companyId") Long companyId,
                                                   @PathVariable("id") Long id,
                                                   @RequestBody YarnSalesDTO dto) {
        YarnSales updated = service.update(id, dto, companyId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete yarn sales order by ID, filtered by companyId")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id,
                                         @RequestParam(value = "companyId") Long companyId) {
        try {
            service.delete(id, companyId);
            return ResponseUtil.success("Yarn Sales Order deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Yarn Sales Order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete yarn sales order by companyId and ID")
    public ResponseEntity<Object> deleteByCompanyAndId(@PathVariable("companyId") Long companyId,
                                                        @PathVariable("id") Long id) {
        try {
            service.delete(id, companyId);
            return ResponseUtil.success("Yarn Sales Order deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Yarn Sales Order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
