package com.tech3.erp.controller;

import com.tech3.erp.dto.AgencyContractDTO;
import com.tech3.erp.service.AgencyContractService;
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
@RequestMapping("/api/agency-contract")
@Tag(name = "Agency Contract Controller", description = "APIs for Agency Contract and Indent management")
@Validated
public class AgencyContractController {

    private final AgencyContractService contractService;

    public AgencyContractController(AgencyContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    @Operation(summary = "Create Agency Contract", description = "Adds a new Agency Contract.")
    public ResponseEntity<Object> createContract(@Valid @RequestBody AgencyContractDTO dto) {
        try {
            AgencyContractDTO created = contractService.createContract(dto);
            return ResponseUtil.success("Agency Contract created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Agency Contract by ID", description = "Fetches an Agency Contract by ID.")
    public ResponseEntity<Object> getContractById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Agency Contract fetched successfully", contractService.getContractById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Agency Contracts", description = "Retrieves all Agency Contracts with optional pagination and filters.")
    public ResponseEntity<Object> getAllContracts(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "companyId", required = false) Long companyId,
            @RequestParam(value = "status", required = false) String status) {
        try {
            if (offset != null || limit != null) {
                Long finalOffset = offset != null ? offset : 0L;
                Long finalLimit = limit != null ? limit : 10L;
                return ResponseUtil.success("Agency Contracts fetched successfully",
                        contractService.getAllContractsWithPagination(finalOffset, finalLimit, companyId, status));
            }
            return ResponseUtil.success("Agency Contracts fetched successfully",
                    contractService.getAllContracts(companyId, status));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Agency Contract", description = "Updates an Agency Contract by ID.")
    public ResponseEntity<Object> updateContract(@PathVariable Long id, @Valid @RequestBody AgencyContractDTO dto) {
        try {
            AgencyContractDTO updated = contractService.updateContract(id, dto);
            return ResponseUtil.success("Agency Contract updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update Contract Status", description = "Updates status of an Agency Contract.")
    public ResponseEntity<Object> updateContractStatus(@PathVariable Long id, @RequestParam("status") String status) {
        try {
            AgencyContractDTO updated = contractService.updateContractStatus(id, status);
            return ResponseUtil.success("Contract status updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Agency Contract", description = "Soft deletes an Agency Contract by ID.")
    public ResponseEntity<Object> deleteContract(@PathVariable Long id) {
        try {
            contractService.deleteContract(id);
            return ResponseUtil.success("Agency Contract deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
