package com.tech3.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tech3.erp.dto.WeavingYarnIssueDTO;
import com.tech3.erp.service.WeavingYarnIssueService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weaving-yarn-issues")
@Tag(name = "Weaving Yarn Issues", description = "APIs for Weaving Yarn Issues management")

public class WeavingYarnIssueController {

    @Autowired
    private WeavingYarnIssueService service;

    @GetMapping
    public ResponseEntity<Object> getAllWeavingYarnIssues(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(service.getAllWeavingYarnIssues());
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(service.getAllWeavingYarnIssuesWithPagination(finalOffset, finalLimit));
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Object> getAllWeavingYarnIssuesByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(service.getAllWeavingYarnIssuesByCompany(companyId));
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(service.getAllWeavingYarnIssuesByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }
    
    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<WeavingYarnIssueDTO> getWeavingYarnIssueByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getWeavingYarnIssueByCompany(companyId, id));
    }

    @PostMapping
    public ResponseEntity<WeavingYarnIssueDTO> createWeavingYarnIssue(@RequestBody WeavingYarnIssueDTO dto) {
        WeavingYarnIssueDTO createdIssue = service.createWeavingYarnIssue(dto);
        return ResponseEntity.ok(createdIssue);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing weaving yarn issue")
    public ResponseEntity<WeavingYarnIssueDTO> updateWeavingYarnIssue(
            @PathVariable("id") Long id,
            @RequestBody WeavingYarnIssueDTO dto) {
        WeavingYarnIssueDTO updatedIssue = service.updateWeavingYarnIssue(id, dto);
        return ResponseEntity.ok(updatedIssue);
    }
    
    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update an existing weaving yarn issue for a company")
    public ResponseEntity<WeavingYarnIssueDTO> updateWeavingYarnIssueByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @RequestBody WeavingYarnIssueDTO dto) {
        WeavingYarnIssueDTO updatedIssue = service.updateWeavingYarnIssueByCompany(companyId, id, dto);
        return ResponseEntity.ok(updatedIssue);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a weaving yarn issue")
    public ResponseEntity<Object> deleteWeavingYarnIssue(@PathVariable("id") Long id) {
        try {
            service.deleteWeavingYarnIssue(id);
            return ResponseUtil.success("Weaving yarn issue deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the weaving yarn issue", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete a weaving yarn issue for a company")
    public ResponseEntity<Object> deleteWeavingYarnIssueByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            service.deleteWeavingYarnIssueByCompany(companyId, id);
            return ResponseUtil.success("Weaving yarn issue deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the weaving yarn issue", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/mapping-status")
    @Operation(summary = "Check if Weaving Yarn Issue is mapped", description = "Returns true if the issue is linked to any downstream module.")
    public ResponseEntity<Object> getMappingStatus(@PathVariable Long id) {
        try {
            boolean isMapped = service.isMapped(id);
            return ResponseUtil.success("", Map.of("isMapped", isMapped));
        } catch (Exception e) {
            return ResponseUtil.error("Error checking mapping status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // You can uncomment and use this if you need a separate endpoint for creating with requirements
    /*
    @PostMapping("/with-requirements")
    public ResponseEntity<WeavingYarnIssueDTO> createWithRequirements(
            @RequestBody WeavingYarnIssueDTO dto) {
        WeavingYarnIssueDTO createdIssue = service.createWeavingYarnIssue(dto);
        return ResponseEntity.ok(createdIssue);
    }
    */
}