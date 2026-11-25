package com.tech3.erp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tech3.erp.dto.FlangeIssueDTO;
import com.tech3.erp.dto.IssuedFlangeDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.FlangeIssueService;

import java.util.List;

@RestController
@RequestMapping("/api/flange_issue")
@Tag(name = "Flange Issue Controller", description = "APIs for Flange Issue management")
public class FlangeIssueController {

    private final FlangeIssueService flangeIssueService;

    public FlangeIssueController(FlangeIssueService flangeIssueService) {
        this.flangeIssueService = flangeIssueService;
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Create a new FlangeIssue", description = "Add a new FlangeIssue.")
    public ResponseEntity<FlangeIssueDTO> createFlangeIssue(@RequestBody @Valid FlangeIssueDTO dto) {
        FlangeIssueDTO saved = flangeIssueService.saveFlangeIssue(dto);
        return ResponseEntity.ok(saved);
    }

    // READ ALL
    @GetMapping
    @Operation(summary = "Get all FlangeIssues", description = "Retrieve a list of all FlangeIssues.")
    public ResponseEntity<PaginatedResponse<FlangeIssueDTO>> getAllFlangeIssues(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(flangeIssueService.getAllFlangeIssuesWithPagination(offset, limit));
    }

    // READ ALL BY COMPANY
    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get all FlangeIssues by companyId", description = "Retrieve a list of all FlangeIssues for a specific company.")
    public ResponseEntity<PaginatedResponse<FlangeIssueDTO>> getAllFlangeIssuesByCompany(
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(flangeIssueService.getAllFlangeIssuesByCompanyIdWithPagination(companyId, offset, limit));
    }

    @GetMapping("/issued-flanges")
    @Operation(summary = "Get issued flange numbers by issuedCompanyId", description = "Retrieve flange numbers issued to the specified company.")
    public ResponseEntity<List<IssuedFlangeDTO>> getIssuedFlangeNos(@RequestParam("issuedCompanyId") Long issuedCompanyId) {
        return ResponseEntity.ok(flangeIssueService.getIssuedFlangeNosByIssuedCompanyId(issuedCompanyId));
    }

    // READ BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Get FlangeIssue by ID", description = "Retrieve a FlangeIssue by ID, optionally filtered by companyId.")
    public ResponseEntity<FlangeIssueDTO> getFlangeIssueById(@PathVariable Long id,
                                                              @RequestParam(value = "companyId", required = false) Long companyId) {
        return ResponseEntity.ok(flangeIssueService.getFlangeIssueById(id, companyId));
    }

    // UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Update FlangeIssue by ID", description = "Update a FlangeIssue by ID.")
    public ResponseEntity<FlangeIssueDTO> updateFlangeIssue(@PathVariable Long id, @RequestBody @Valid FlangeIssueDTO dto) {
        FlangeIssueDTO updated = flangeIssueService.updateFlangeIssue(id, dto);
        return ResponseEntity.ok(updated);
    }

    // UPDATE BY COMPANY AND ID
    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update FlangeIssue by companyId and ID", description = "Update a FlangeIssue for a specific company.")
    public ResponseEntity<FlangeIssueDTO> updateFlangeIssueByCompany(@PathVariable Long companyId,
                                                                      @PathVariable Long id,
                                                                      @RequestBody @Valid FlangeIssueDTO dto) {
        FlangeIssueDTO updated = flangeIssueService.updateFlangeIssue(id, dto, companyId);
        return ResponseEntity.ok(updated);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete FlangeIssue by ID", description = "Delete a FlangeIssue by ID.")
    public ResponseEntity<Void> deleteFlangeIssue(@PathVariable Long id) {
        flangeIssueService.deleteFlangeIssue(id);
        return ResponseEntity.noContent().build();
    }

    // DELETE BY COMPANY AND ID
    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete FlangeIssue by companyId and ID", description = "Delete a FlangeIssue for a specific company.")
    public ResponseEntity<Void> deleteFlangeIssueByCompany(@PathVariable Long companyId,
                                                            @PathVariable Long id) {
        flangeIssueService.deleteFlangeIssue(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/flange/company/{companyId}/{id}/release")
    @Operation(summary = "Delete flange and reset issued flag", description = "Delete flange master entry and reset issued flag for the specified company before removal.")
    public ResponseEntity<Void> deleteFlangeAndResetIssued(@PathVariable Long companyId,
                                                           @PathVariable Long id) {
        flangeIssueService.deleteFlangeAndResetIssued(id, companyId);
        return ResponseEntity.noContent().build();
    }
}
