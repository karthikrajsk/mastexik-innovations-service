package com.tech3.erp.controller;

import com.tech3.erp.dto.JobworkFabricReceiveDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.JobworkFabricReceive;
import com.tech3.erp.service.JobworkFabricReceiveService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobwork-fabric-receive")
@Tag(name = "Jobwork Fabric Receive Controller", description = "APIs for Jobwork Jobwork Fabric Receive management")

public class JobworkFabricReceiveController {

    @Autowired
    private JobworkFabricReceiveService jobworkFabricReceiveService;

    @PostMapping
    @Operation(summary = "Create Jobwork Fabric Receive", description = "Create a new Jobwork Fabric Receive entry.")
    public ResponseEntity<Object> createJobworkFabricReceive(
            @Valid @RequestBody JobworkFabricReceiveDTO dto) {
        try {
            JobworkFabricReceive created = jobworkFabricReceiveService.createJobworkFabricReceive(dto);
            return ResponseUtil.success("Jobwork Fabric Receive created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get all Jobwork Fabric Receives", description = "Get all Jobwork Fabric Receive entries.")
    public ResponseEntity<Object> getAllJobworkFabricReceive(
            @PathVariable("companyId") Long companyId,
            @RequestParam(name = "offset", required = false) Long offset,
            @RequestParam(name = "limit", required = false) Long limit) {
        try {
            if (offset == null && limit == null) {
                List<JobworkFabricReceiveDTO> list = jobworkFabricReceiveService.getAllJobworkFabricReceive(companyId);
                return ResponseUtil.success("Jobwork Fabric Receives fetched successfully", list);
            }
            PaginatedResponse<JobworkFabricReceiveDTO> paginated = jobworkFabricReceiveService.getAllJobworkFabricReceiveWithPagination(companyId, offset, limit);
            return ResponseUtil.success("Jobwork Fabric Receives fetched successfully", paginated);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching Jobwork Fabric Receives: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/company/{companyId}")
    @Operation(summary = "Get Jobwork Fabric Receive by ID and Company", description = "Fetch a Jobwork Fabric Receive by its ID and companyId.")
    public ResponseEntity<Object> getJobworkFabricReceiveById(
            @PathVariable("id") Long id,
            @PathVariable("companyId") Long companyId) {
        JobworkFabricReceive receive = jobworkFabricReceiveService.getJobworkFabricReceiveById(id, companyId);
        if (receive != null) {
            return ResponseUtil.success("Jobwork Fabric Receive fetched successfully", receive);
        } else {
            return ResponseUtil.error("Jobwork Fabric Receive not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/company/{companyId}")
    @Operation(summary = "Update Jobwork Fabric Receive", description = "Update a Jobwork Fabric Receive entry by its ID and companyId.")
    public ResponseEntity<Object> updateJobworkFabricReceive(
            @PathVariable("id") Long id,
            @PathVariable("companyId") Long companyId,
            @Valid @RequestBody JobworkFabricReceiveDTO dto) {
        try {
            JobworkFabricReceive updated = jobworkFabricReceiveService.updateJobworkFabricReceive(id, dto, companyId);
            return ResponseUtil.success("Jobwork Fabric Receive updated successfully", updated);
        } catch (RuntimeException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/company/{companyId}")
    @Operation(summary = "Delete JobworkFabricReceive by Company", description = "Deletes a JobworkFabricReceive by ID scoped to a company.")
    public ResponseEntity<Object> deleteJobworkFabricReceive(
            @PathVariable("id") Long id,
            @PathVariable("companyId") Long companyId) {
        try {
        	jobworkFabricReceiveService.deleteJobworkFabricReceive(id, companyId);
            return ResponseUtil.success("JobworkFabricReceive deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the JobworkFabricReceive", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    @DeleteMapping("/{id}/company/{companyId}/hard")
    @Operation(summary = "Permanently Delete JobworkFabricReceive", description = "Permanently deletes a JobworkFabricReceive by ID and its related piece entries.")
    public ResponseEntity<Object> hardDeleteJobworkFabricReceive( @PathVariable("id") Long id,
            @PathVariable("companyId") Long companyId) {
        return hardDeleteJobworkFabricReceiveByCompany(id, companyId);
    }

    @DeleteMapping("/company/{companyId}/{id}/hard")
    @Operation(summary = "Permanently Delete JobworkFabricReceive (alternate)", description = "Permanently deletes a JobworkFabricReceive by company- scoped path.")
    public ResponseEntity<Object> hardDeleteJobworkFabricReceiveByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        try {
            jobworkFabricReceiveService.hardDeleteJobworkFabricReceive(id, companyId);
            return ResponseUtil.success("JobworkFabricReceive permanently deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while permanently deleting the JobworkFabricReceive", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
