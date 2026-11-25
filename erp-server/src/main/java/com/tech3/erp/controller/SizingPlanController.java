package com.tech3.erp.controller;

import com.tech3.erp.dto.SizingPlanDTO;
import com.tech3.erp.service.SizingPlanService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sizing-plan")
@Tag(name = "Sizing Plan Controller", description = "APIs for Sizing Plan management")

public class SizingPlanController {

	private final SizingPlanService sizingPlanService;

    public SizingPlanController(SizingPlanService sizingPlanService) {
        this.sizingPlanService = sizingPlanService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<SizingPlanDTO> createSizingPlan(@Valid @RequestBody SizingPlanDTO sizingPlanDTO) {
        SizingPlanDTO createdPlan = sizingPlanService.saveSizingPlan(sizingPlanDTO);
        return ResponseEntity.ok(createdPlan);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<Object> getAllSizingPlans(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(sizingPlanService.getAllSizingPlans());
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(sizingPlanService.getAllSizingPlansWithPagination(finalOffset, finalLimit));
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Object> getAllSizingPlansByCompany(
            @PathVariable(name = "companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(sizingPlanService.getAllSizingPlansByCompany(companyId));
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(sizingPlanService.getAllSizingPlansByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<SizingPlanDTO> getSizingPlanById(@PathVariable(name = "id") Long id) {
        SizingPlanDTO plan = sizingPlanService.getSizingPlanById(id);
        return ResponseEntity.ok(plan);
    }
    
    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<SizingPlanDTO> getSizingPlanByIdAndCompany(@PathVariable(name = "companyId") Long companyId, @PathVariable(name = "id") Long id) {
        SizingPlanDTO plan = sizingPlanService.getSizingPlanByIdAndCompany(companyId, id);
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/{id}/mapping-status")
    @Operation(summary = "Check if Sizing Plan is mapped")
    public ResponseEntity<Object> getMappingStatus(@PathVariable("id") Long id) {
        try {
            boolean isMapped = sizingPlanService.isMapped(id);
            return ResponseUtil.success("", java.util.Map.of("isMapped", isMapped));
        } catch (Exception e) {
            return ResponseUtil.error("Error checking mapping status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SizingPlanDTO> updateSizingPlan(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody SizingPlanDTO sizingPlanDTO) {
        SizingPlanDTO updatedPlan = sizingPlanService.updateSizingPlan(id, sizingPlanDTO);
        return ResponseEntity.ok(updatedPlan);
    }
    
    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<SizingPlanDTO> updateSizingPlanByCompany(
            @PathVariable(name = "companyId") Long companyId,
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody SizingPlanDTO sizingPlanDTO) {
        SizingPlanDTO updatedPlan = sizingPlanService.updateSizingPlanByCompany(companyId, id, sizingPlanDTO);
        return ResponseEntity.ok(updatedPlan);
    }

    // DELETE
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSizingPlan(@PathVariable Long id) {
//        sizingPlanService.deleteSizingPlan(id);
//        return ResponseEntity.noContent().build();
//    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete SizingPlan", description = "Deletes a SizingPlan by ID.")
    public ResponseEntity<Object> deleteSizingPlan(@PathVariable(name = "id") Long id) {
        try {
        	sizingPlanService.deleteSizingPlan(id);
            return ResponseUtil.success("SizingPlan deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the SizingPlan: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete SizingPlan by company", description = "Deletes a SizingPlan by ID within company scope.")
    public ResponseEntity<Object> deleteSizingPlanByCompany(@PathVariable(name = "companyId") Long companyId, @PathVariable(name = "id") Long id) {
        try {
            sizingPlanService.deleteSizingPlanByCompany(companyId, id);
            return ResponseUtil.success("SizingPlan deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the SizingPlan: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
