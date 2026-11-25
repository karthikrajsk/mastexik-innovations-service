package com.tech3.erp.controller;

import com.tech3.erp.dto.RequestFormDTO;
import com.tech3.erp.service.RequestFormService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request-forms")
@Tag(name = "Request Form Controller", description = "APIs for Request Form management")
public class RequestFormController {

    private final RequestFormService requestFormService;

    public RequestFormController(RequestFormService requestFormService) {
        this.requestFormService = requestFormService;
    }

    @PostMapping
    @Operation(summary = "Create a new Request Form", description = "Adds a new Request Form.")
    public ResponseEntity<Object> createRequestForm(@Valid @RequestBody RequestFormDTO requestFormDTO) {
        try {
            RequestFormDTO created = requestFormService.saveRequestForm(requestFormDTO);
            return ResponseUtil.success("Request Form created successfully", created);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Request Forms", description = "Retrieve a list of all Request Forms with pagination support.")
    public ResponseEntity<Object> getAllRequestForms(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Request Forms fetched", requestFormService.getAllRequestFormsWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Request Form by ID", description = "Retrieve a single Request Form by its ID.")
    public ResponseEntity<Object> getRequestFormById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Request Form fetched", requestFormService.getRequestFormById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Request Form", description = "Update Request Form details based on ID.")
    public ResponseEntity<Object> updateRequestForm(@PathVariable Long id, @Valid @RequestBody RequestFormDTO requestFormDTO) {
        try {
            return ResponseUtil.success("Request Form updated successfully", requestFormService.updateRequestForm(id, requestFormDTO));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Request Form", description = "Deletes a Request Form by ID (soft delete).")
    public ResponseEntity<Object> deleteRequestForm(@PathVariable Long id) {
        try {
            requestFormService.deleteRequestForm(id);
            return ResponseUtil.success("Request Form deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Request Form", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
