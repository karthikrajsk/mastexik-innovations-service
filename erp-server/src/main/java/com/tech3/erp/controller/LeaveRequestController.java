package com.tech3.erp.controller;

import com.tech3.erp.dto.LeaveRequestDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.LeaveRequestService;
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
@RequestMapping("/api/leave-request")
@Tag(name = "Leave Request Controller", description = "APIs for managing Leave Request records")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    @PostMapping
    @Operation(summary = "Create Leave Request", description = "Creates a new Leave Request record")
    public ResponseEntity<Object> create(@Valid @RequestBody LeaveRequestDTO dto) {
        try {
            LeaveRequestDTO created = leaveRequestService.createLeaveRequest(dto);
            return ResponseUtil.success("Leave Request created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Leave Request by ID", description = "Fetch a Leave Request record by its ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            LeaveRequestDTO leaveRequest = leaveRequestService.getLeaveRequestById(id);
            return ResponseUtil.success("Leave Request fetched successfully", leaveRequest);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get Leave Requests by Employee ID", description = "Fetch Leave Request records by Employee ID")
    public ResponseEntity<Object> getByEmployeeId(@PathVariable Long employeeId) {
        try {
            List<LeaveRequestDTO> leaveRequestList = leaveRequestService.getLeaveRequestsByEmployeeId(employeeId);
            return ResponseUtil.success("Leave Requests fetched successfully", leaveRequestList);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get Leave Requests by Status", description = "Fetch Leave Request records by status")
    public ResponseEntity<Object> getByStatus(@PathVariable String status) {
        try {
            List<LeaveRequestDTO> leaveRequestList = leaveRequestService.getLeaveRequestsByStatus(status);
            return ResponseUtil.success("Leave Requests fetched successfully", leaveRequestList);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "List all Leave Requests", description = "Returns all Leave Request records with pagination")
    public ResponseEntity<Object> getAll(@RequestParam(required = false) Long offset, 
                                        @RequestParam(required = false) Long limit) {
        PaginatedResponse<LeaveRequestDTO> leaveRequestList = leaveRequestService.getAllLeaveRequests(offset, limit);
        return ResponseUtil.success("Leave Request records fetched successfully", leaveRequestList);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Leave Request", description = "Updates an existing Leave Request record")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody LeaveRequestDTO dto) {
        try {
            LeaveRequestDTO updated = leaveRequestService.updateLeaveRequest(id, dto);
            return ResponseUtil.success("Leave Request updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve Leave Request", description = "Approves a leave request")
    public ResponseEntity<Object> approveLeaveRequest(@PathVariable Long id, @RequestBody ApproveRequest request) {
        try {
            LeaveRequestDTO updated = leaveRequestService.approveLeaveRequest(id, request.getApprovedBy());
            return ResponseUtil.success("Leave Request approved successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Reject Leave Request", description = "Rejects a leave request")
    public ResponseEntity<Object> rejectLeaveRequest(@PathVariable Long id, @RequestBody RejectRequest request) {
        try {
            LeaveRequestDTO updated = leaveRequestService.rejectLeaveRequest(id, request.getRejectionReason(), request.getRejectedBy());
            return ResponseUtil.success("Leave Request rejected successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Leave Request", description = "Deletes a Leave Request record")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            leaveRequestService.deleteLeaveRequest(id);
            return ResponseUtil.success("Leave Request deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search Leave Requests", description = "Search Leave Request records by reason, leave type, or status")
    public ResponseEntity<Object> search(@RequestParam String searchTerm) {
        try {
            List<LeaveRequestDTO> leaveRequestList = leaveRequestService.searchLeaveRequests(searchTerm);
            return ResponseUtil.success("Leave Request records fetched successfully", leaveRequestList);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Inner classes for request bodies
    public static class ApproveRequest {
        private Long approvedBy;

        public Long getApprovedBy() {
            return approvedBy;
        }

        public void setApprovedBy(Long approvedBy) {
            this.approvedBy = approvedBy;
        }
    }

    public static class RejectRequest {
        private String rejectionReason;
        private Long rejectedBy;

        public String getRejectionReason() {
            return rejectionReason;
        }

        public void setRejectionReason(String rejectionReason) {
            this.rejectionReason = rejectionReason;
        }

        public Long getRejectedBy() {
            return rejectedBy;
        }

        public void setRejectedBy(Long rejectedBy) {
            this.rejectedBy = rejectedBy;
        }
    }
}
