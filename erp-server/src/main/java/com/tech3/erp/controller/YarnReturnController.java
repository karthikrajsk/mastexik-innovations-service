package com.tech3.erp.controller;

import com.tech3.erp.dto.YarnReturnDTO;
import com.tech3.erp.entity.YarnReturn;
import com.tech3.erp.service.YarnReturnService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/yarn-return")
@Tag(name = "Yarn Return Controller", description = "APIs for Yarn Return management")
@CrossOrigin(origins = "*")
public class YarnReturnController {

    @Autowired
    private YarnReturnService yarnReturnService;

    // Create a new yarn return entry
    @PostMapping
    @Operation(summary = "Create Yarn Return", description = "Creates a new yarn return entry")
    public ResponseEntity<Object> createYarnReturn(@RequestBody YarnReturnDTO requestDTO) {
        try {
            YarnReturn savedYarnReturn = yarnReturnService.createYarnReturn(requestDTO);
            return ResponseUtil.success("Yarn Return saved successfully", savedYarnReturn);
        } catch (Exception e) {
            return ResponseUtil.error("Error while saving Yarn Return: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all yarn returns
    @GetMapping
    @Operation(summary = "Get All Yarn Returns", description = "Retrieves all yarn returns with pagination")
    public ResponseEntity<Object> getAllYarnReturns(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        try {
            Long finalOffset = offset != null ? offset : 0L;
            Long finalLimit = limit != null ? limit : 10L;
            return ResponseUtil.success("Yarn returns retrieved successfully",
                    yarnReturnService.getAllYarnReturnsWithPagination(finalOffset, finalLimit));
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving yarn returns: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Yarn Returns by company", description = "Retrieves yarn returns for a company with pagination")
    public ResponseEntity<Object> getYarnReturnsByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        try {
            Long finalOffset = offset != null ? offset : 0L;
            Long finalLimit = limit != null ? limit : 10L;
            return ResponseUtil.success("Yarn returns retrieved successfully",
                    yarnReturnService.getYarnReturnsByCompanyWithPagination(companyId, finalOffset, finalLimit));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving yarn returns: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a yarn return by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get Yarn Return by ID", description = "Retrieves a yarn return by its ID")
    public ResponseEntity<Object> getYarnReturnById(@PathVariable("id") Long id) {
        try {
            YarnReturnDTO yarnReturn = yarnReturnService.getYarnReturnById(id);
            return ResponseUtil.success("Yarn return retrieved successfully", yarnReturn);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving yarn return: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Yarn Return by company", description = "Retrieves a yarn return within a company")
    public ResponseEntity<Object> getYarnReturnByIdAndCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            return ResponseUtil.success("Yarn return retrieved successfully",
                    yarnReturnService.getYarnReturnByIdAndCompany(id, companyId));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving yarn return: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a yarn return
    @PutMapping("/{id}")
    @Operation(summary = "Update Yarn Return", description = "Updates an existing yarn return")
    public ResponseEntity<Object> updateYarnReturn(@PathVariable("id") Long id, @RequestBody YarnReturnDTO requestDTO) {
        try {
            YarnReturn updatedYarnReturn = yarnReturnService.updateYarnReturn(id, requestDTO);
            return ResponseUtil.success("Yarn Return updated successfully", updatedYarnReturn);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error while updating Yarn Return: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Yarn Return by company", description = "Updates a yarn return scoped to a company")
    public ResponseEntity<Object> updateYarnReturnByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @RequestBody YarnReturnDTO requestDTO) {
        try {
            YarnReturn updatedYarnReturn = yarnReturnService.updateYarnReturnByCompany(id, companyId, requestDTO);
            return ResponseUtil.success("Yarn Return updated successfully", updatedYarnReturn);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error while updating Yarn Return: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a yarn return by ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Yarn Return", description = "Deletes a yarn return by ID")
    public ResponseEntity<Object> deleteYarnReturn(@PathVariable("id") Long id) {
        try {
            yarnReturnService.deleteYarnReturn(id);
            return ResponseUtil.success("Yarn Return deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Yarn Return", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Yarn Return by company", description = "Deletes a yarn return by ID within company scope")
    public ResponseEntity<Object> deleteYarnReturnByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            yarnReturnService.deleteYarnReturnByCompany(id, companyId);
            return ResponseUtil.success("Yarn Return deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Yarn Return", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Health check endpoint
    @GetMapping("/ping")
    public String ping() {
        return "Yarn Return API is working!";
    }
}