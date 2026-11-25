package com.tech3.erp.controller;

import com.tech3.erp.dto.AgencyDispatchDTO;
import com.tech3.erp.service.AgencyDispatchService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agency-dispatch")
@Tag(name = "Agency Dispatch Controller", description = "APIs for Agency Mill Dispatch management")
@Validated
public class AgencyDispatchController {

    private final AgencyDispatchService dispatchService;

    public AgencyDispatchController(AgencyDispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @PostMapping
    @Operation(summary = "Create Agency Dispatch", description = "Adds a new Agency Mill Dispatch record.")
    public ResponseEntity<Object> createDispatch(@Valid @RequestBody AgencyDispatchDTO dto) {
        try {
            AgencyDispatchDTO created = dispatchService.createDispatch(dto);
            return ResponseUtil.success("Agency Dispatch created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Agency Dispatch by ID", description = "Fetches an Agency Mill Dispatch record by ID.")
    public ResponseEntity<Object> getDispatchById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Agency Dispatch fetched successfully", dispatchService.getDispatchById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Agency Dispatches", description = "Retrieves all Agency Mill Dispatches with optional pagination and filters.")
    public ResponseEntity<Object> getAllDispatches(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "companyId", required = false) Long companyId,
            @RequestParam(value = "paymentStatus", required = false) String paymentStatus) {
        try {
            if (offset != null || limit != null) {
                Long finalOffset = offset != null ? offset : 0L;
                Long finalLimit = limit != null ? limit : 10L;
                return ResponseUtil.success("Agency Dispatches fetched successfully",
                        dispatchService.getAllDispatchesWithPagination(finalOffset, finalLimit, companyId, paymentStatus));
            }
            return ResponseUtil.success("Agency Dispatches fetched successfully",
                    dispatchService.getAllDispatches(companyId, paymentStatus));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Agency Dispatch", description = "Updates an Agency Mill Dispatch record by ID.")
    public ResponseEntity<Object> updateDispatch(@PathVariable Long id, @Valid @RequestBody AgencyDispatchDTO dto) {
        try {
            AgencyDispatchDTO updated = dispatchService.updateDispatch(id, dto);
            return ResponseUtil.success("Agency Dispatch updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Agency Dispatch", description = "Soft deletes an Agency Mill Dispatch record by ID.")
    public ResponseEntity<Object> deleteDispatch(@PathVariable Long id) {
        try {
            dispatchService.deleteDispatch(id);
            return ResponseUtil.success("Agency Dispatch deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
