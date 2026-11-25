package com.tech3.erp.controller;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.YarnChallanDTO;
import com.tech3.erp.entity.YarnChallan;
import com.tech3.erp.service.YarnChallanService;
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
@RequestMapping("/api/yarn-challan")
@Tag(name = "Yarn Challan Controller", description = "APIs for Yarn Challan management")
public class YarnChallanController {

    @Autowired
    private YarnChallanService service;
@PostMapping
@Operation(summary = "Create a new yarn challan")
public ResponseEntity<?> create(@RequestBody YarnChallanDTO dto) {
    YarnChallan saved = service.create(dto);
    return ResponseUtil.success("Yarn Challan created successfully", saved);
}

@PutMapping("/{id}")
@Operation(summary = "Update yarn challan by ID, filtered by companyId")
public ResponseEntity<?> update(@PathVariable("id") Long id,
                                @RequestBody YarnChallanDTO dto,
                                @RequestParam(value = "companyId") Long companyId) {
    YarnChallan updated = service.update(id, dto, companyId);
    return ResponseUtil.success("Yarn Challan updated successfully", updated);
}

@PutMapping("/company/{companyId}/{id}")
@Operation(summary = "Update yarn challan by companyId and ID")
public ResponseEntity<?> updateByCompanyAndId(@PathVariable("companyId") Long companyId,
                                               @PathVariable("id") Long id,
                                               @RequestBody YarnChallanDTO dto) {
    YarnChallan updated = service.update(id, dto, companyId);
    return ResponseUtil.success("Yarn Challan updated successfully", updated);
}

    // @PostMapping
    // @Operation(summary = "Create a new yarn challan")
    // public ResponseEntity<?> create(@RequestBody YarnChallanDTO dto) {
    //     YarnChallan saved = service.create(dto);
    //     return ResponseEntity.ok(saved);
    // }

    @GetMapping
    @Operation(summary = "Get all yarn challans, optionally filtered by companyId")
    public ResponseEntity<PaginatedResponse<YarnChallan>> getAll(
            @RequestParam(value = "companyId", required = false) Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(service.getAllWithPagination(offset, limit, companyId));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get yarn challans by companyId")
    public ResponseEntity<List<YarnChallan>> getAllByCompany(@PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok(service.getAllByCompanyId(companyId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get yarn challan by ID, optionally filtered by companyId")
    public ResponseEntity<?> getById(@PathVariable("id") Long id,
                                     @RequestParam(value = "companyId", required = false) Long companyId) {
        return ResponseEntity.ok(service.getById(id, companyId));
    }

    // @PutMapping("/{id}")
    // @Operation(summary = "Update yarn challan by ID, filtered by companyId")
    // public ResponseEntity<?> update(@PathVariable Long id,
    //                                 @RequestBody YarnChallanDTO dto,
    //                                 @RequestParam(value = "companyId") Long companyId) {
    //     YarnChallan updated = service.update(id, dto, companyId);
    //     return ResponseEntity.ok(updated);
    // }

    // @PutMapping("/company/{companyId}/{id}")
    // @Operation(summary = "Update yarn challan by companyId and ID")
    // public ResponseEntity<?> updateByCompanyAndId(@PathVariable Long companyId,
    //                                                @PathVariable Long id,
    //                                                @RequestBody YarnChallanDTO dto) {
    //     YarnChallan updated = service.update(id, dto, companyId);
    //     return ResponseEntity.ok(updated);
    // }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete yarn challan by ID, filtered by companyId")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id,
                                         @RequestParam(value = "companyId") Long companyId) {
        try {
            service.delete(id, companyId);
            return ResponseUtil.success("Yarn Challan deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Yarn Challan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete yarn challan by companyId and ID")
    public ResponseEntity<Object> deleteByCompanyAndId(@PathVariable("companyId") Long companyId,
                                                        @PathVariable("id") Long id) {
        try {
            service.delete(id, companyId);
            return ResponseUtil.success("Yarn Challan deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Yarn Challan", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
