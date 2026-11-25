package com.tech3.erp.controller;

import com.tech3.erp.dto.ApprovalDTO;
import com.tech3.erp.dto.ApprovalRequestDTO;
import com.tech3.erp.dto.ApprovalSummaryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.ApprovalService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/approval")
@Tag(name = "Approval Controller", description = "APIs for Approval management")
@Validated
public class ApprovalController {

    private final ApprovalService approvalService;

    @Autowired
    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PostMapping
    @Operation(summary = "Create or Update Approval Record", description = "Creates or updates an approval record for a transaction entry.")
    public ResponseEntity<Object> createOrUpdateApproval(
            @Valid @RequestBody ApprovalDTO approvalDTO,
            @RequestHeader(value = "X-Company-Id", required = false) Long companyId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        try {
            ApprovalDTO result = approvalService.createOrUpdateApproval(approvalDTO, companyId, userId);
            return ResponseUtil.success("Approval record created/updated successfully", result);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Error creating/updating approval record: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Approval by ID", description = "Fetch an approval record using its unique ID.")
    public ResponseEntity<Object> getApprovalById(@PathVariable Long id) {
        try {
            ApprovalDTO approval = approvalService.getApprovalById(id);
            return ResponseUtil.success("Approval record fetched successfully", approval);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/entry/{entryNo}")
    @Operation(summary = "Get Approval by Entry Number", description = "Fetch an approval record using entry number.")
    public ResponseEntity<Object> getApprovalByEntryNo(@PathVariable String entryNo) {
        try {
            ApprovalDTO approval = approvalService.getApprovalByEntryNo(entryNo);
            return ResponseUtil.success("Approval record fetched successfully", approval);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/entry/{entryNo}/screen/{screenName}")
    @Operation(summary = "Get Approval by Entry Number and Screen Name", description = "Fetch an approval record using entry number and screen name.")
    public ResponseEntity<Object> getApprovalByEntryNoAndScreen(
            @PathVariable String entryNo,
            @PathVariable String screenName) {
        try {
            ApprovalDTO approval = approvalService.getApprovalByEntryNoAndScreen(entryNo, screenName);
            if (approval == null) {
                return ResponseUtil.error("Approval record not found", HttpStatus.NOT_FOUND);
            }
            return ResponseUtil.success("Approval record fetched successfully", approval);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching approval record: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get All Approvals by Company", description = "Retrieve a list of all approval records for a company with pagination support.")
    public ResponseEntity<Object> getApprovalsByCompanyId(
            @PathVariable Long companyId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            PaginatedResponse<ApprovalDTO> response = approvalService.getApprovalsByCompanyId(companyId, page, size);
            return ResponseUtil.success("Approval records fetched successfully", response);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching approval records: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/date-range")
    @Operation(summary = "Get Approvals by Company and Date Range", description = "Retrieve approval records filtered by company and date range.")
    public ResponseEntity<Object> getApprovalsByCompanyIdAndDate(
            @PathVariable Long companyId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            PaginatedResponse<ApprovalDTO> response = approvalService.getApprovalsByCompanyIdAndDate(companyId, startDate, endDate, page, size);
            return ResponseUtil.success("Approval records fetched successfully", response);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching approval records: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/status/{isApproved}")
    @Operation(summary = "Get Approvals by Company and Status", description = "Retrieve approval records filtered by company and approval status.")
    public ResponseEntity<Object> getApprovalsByCompanyIdAndStatus(
            @PathVariable Long companyId,
            @PathVariable Integer isApproved,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            PaginatedResponse<ApprovalDTO> response = approvalService.getApprovalsByCompanyIdAndStatus(companyId, isApproved, page, size);
            return ResponseUtil.success("Approval records fetched successfully", response);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching approval records: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/screen/{screenName}")
    @Operation(summary = "Get Approvals by Company and Screen Name", description = "Retrieve approval records filtered by company and screen name.")
    public ResponseEntity<Object> getApprovalsByCompanyIdAndScreenName(
            @PathVariable Long companyId,
            @PathVariable String screenName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            PaginatedResponse<ApprovalDTO> response = approvalService.getApprovalsByCompanyIdAndScreenName(companyId, screenName, page, size);
            return ResponseUtil.success("Approval records fetched successfully", response);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching approval records: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/filter")
    @Operation(summary = "Get Approvals with Filters", description = "Retrieve approval records filtered by company, date range, and approval status.")
    public ResponseEntity<Object> getApprovalsByCompanyIdAndFilters(
            @PathVariable Long companyId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer isApproved,
            @RequestParam(required = false) String salesOrderNo,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            if (salesOrderNo != null && !salesOrderNo.isEmpty()) {
                PaginatedResponse<ApprovalDTO> response = approvalService.getApprovalsByCompanyIdAndSalesOrderNo(companyId, salesOrderNo, page, size);
                return ResponseUtil.success("Approval records fetched successfully", response);
            }
            
            PaginatedResponse<ApprovalDTO> response;
            if (startDate != null && endDate != null && isApproved != null) {
                response = approvalService.getApprovalsByCompanyIdAndDateRangeAndStatus(companyId, startDate, endDate, isApproved, page, size);
            } else if (startDate != null && endDate != null) {
                response = approvalService.getApprovalsByCompanyIdAndDate(companyId, startDate, endDate, page, size);
            } else if (isApproved != null) {
                response = approvalService.getApprovalsByCompanyIdAndStatus(companyId, isApproved, page, size);
            } else {
                response = approvalService.getApprovalsByCompanyId(companyId, page, size);
            }
            return ResponseUtil.success("Approval records fetched successfully", response);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching approval records: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/summary")
    @Operation(summary = "Get Approval Summary by Company", description = "Retrieve approval summary grouped by transaction number with approval statistics.")
    public ResponseEntity<Object> getApprovalSummaryByCompanyId(
            @PathVariable Long companyId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            PaginatedResponse<ApprovalSummaryDTO> response = approvalService.getApprovalSummaryByCompanyId(companyId, page, size);
            return ResponseUtil.success("Approval summary fetched successfully", response);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching approval summary: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve an Entry", description = "Marks an approval record as approved with comments.")
    public ResponseEntity<Object> approveEntry(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalRequestDTO approvalRequest,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        try {
            ApprovalDTO result = approvalService.approveEntry(id, approvalRequest, userId);
            return ResponseUtil.success("Entry approved successfully", result);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Error approving entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Approval Record", description = "Deletes an approval record by ID.")
    public ResponseEntity<Object> deleteApproval(@PathVariable Long id) {
        try {
            approvalService.deleteApproval(id);
            return ResponseUtil.success("Approval record deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error deleting approval record: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
