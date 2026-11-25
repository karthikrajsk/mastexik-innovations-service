// CompanyController.java
package com.tech3.erp.controller;

import com.tech3.erp.dto.CompanyDTO;
import com.tech3.erp.service.CompanyService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Company Controller", description = "APIs for managing Company Master")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @Operation(summary = "Create Company", description = "Creates a new Company with addresses, print contexts, and bank details")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyDTO dto) {
        try {
            CompanyDTO created = companyService.createCompany(dto);
            return ResponseUtil.success("Company created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Company by ID", description = "Fetch a Company by its ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            CompanyDTO company = companyService.getCompanyById(id);
            return ResponseUtil.success("Company fetched successfully", company);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List all Companies", description = "Returns all Companies with pagination support")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Companies fetched successfully", companyService.getAllCompaniesWithPagination(finalOffset, finalLimit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Company", description = "Updates an existing Company by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody CompanyDTO dto) {
        try {
            CompanyDTO updated = companyService.updateCompany(id, dto);
            return ResponseUtil.success("Company updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Company", description = "Deletes a Company by ID")
    public ResponseEntity<Object> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
            return ResponseUtil.success("Company deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Company", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
