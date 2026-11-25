package com.tech3.erp.controller;

import com.tech3.erp.dto.PermissionRequestDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.PermissionRequestService;
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
@RequestMapping("/api/permission-request")
@Tag(name = "Permission Request Controller", description = "APIs for managing Employee Permission Requests")
public class PermissionRequestController {

    private final PermissionRequestService permissionRequestService;

    public PermissionRequestController(PermissionRequestService permissionRequestService) {
        this.permissionRequestService = permissionRequestService;
    }

    @PostMapping
    @Operation(summary = "Create Permission Request", description = "Creates a new Permission Request")
    public ResponseEntity<Object> create(@Valid @RequestBody PermissionRequestDTO dto) {
        try {
            PermissionRequestDTO created = permissionRequestService.createPermissionRequest(dto);
            return ResponseUtil.success("Permission request created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Permission Request by ID", description = "Fetch a Permission Request by its ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            PermissionRequestDTO permissionRequest = permissionRequestService.getPermissionRequestById(id);
            return ResponseUtil.success("Permission request fetched successfully", permissionRequest);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List all Permission Requests", description = "Returns all Permission Requests with pagination")
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PaginatedResponse<PermissionRequestDTO> response = permissionRequestService.getAllPermissionRequests(page, size);
        return ResponseUtil.success("Permission requests fetched successfully", response);
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get Permission Requests by Employee", description = "Fetch all Permission Requests for a specific employee")
    public ResponseEntity<Object> getByEmployee(@PathVariable Long employeeId) {
        List<PermissionRequestDTO> requests = permissionRequestService.getPermissionRequestsByEmployee(employeeId);
        return ResponseUtil.success("Permission requests for employee fetched successfully", requests);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Permission Request", description = "Updates an existing Permission Request by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PermissionRequestDTO dto) {
        try {
            PermissionRequestDTO updated = permissionRequestService.updatePermissionRequest(id, dto);
            return ResponseUtil.success("Permission request updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve Permission Request", description = "Approves a Permission Request by ID")
    public ResponseEntity<Object> approve(@PathVariable Long id, @RequestParam String approvedBy) {
        try {
            PermissionRequestDTO approved = permissionRequestService.approvePermissionRequest(id, approvedBy);
            return ResponseUtil.success("Permission request approved successfully", approved);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject Permission Request", description = "Rejects a Permission Request by ID")
    public ResponseEntity<Object> reject(
            @PathVariable Long id, 
            @RequestParam String rejectionReason,
            @RequestParam String rejectedBy) {
        try {
            PermissionRequestDTO rejected = permissionRequestService.rejectPermissionRequest(id, rejectionReason, rejectedBy);
            return ResponseUtil.success("Permission request rejected successfully", rejected);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Permission Request", description = "Deletes a Permission Request by ID")
    public ResponseEntity<Object> deletePermissionRequest(@PathVariable Long id) {
        try {
            permissionRequestService.deletePermissionRequest(id);
            return ResponseUtil.success("Permission request deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Permission Request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
