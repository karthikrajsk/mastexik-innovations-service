package com.tech3.erp.controller;

import com.tech3.erp.dto.WeavingContractDTO;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.service.WeavingContractService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weaving-contracts")
@Tag(name = "Weaving Contracts Controller", description = "APIs for Weaving Contracts management")


public class WeavingContractController {

    @Autowired
    private WeavingContractService contractService;

    @PostMapping
    public ResponseEntity<?> createWeavingContract(@RequestBody WeavingContractDTO dto) {
        WeavingContract saved = contractService.createWeavingContract(dto);
        return ResponseEntity.ok(saved);
    }

     @Operation(summary = "Get all weaving contracts")
     @GetMapping
     public ResponseEntity<Object> getAllWeavingContract(
             @RequestParam(value = "offset", required = false) Long offset,
             @RequestParam(value = "limit", required = false) Long limit) {
         if (offset == null && limit == null) {
             return ResponseEntity.ok(contractService.getAllWeavingContract());
         }
         Long finalOffset = offset != null ? offset : 0L;
         Long finalLimit = limit != null ? limit : 10L;
         return ResponseEntity.ok(contractService.getAllWeavingContractWithPagination(finalOffset, finalLimit));
     }

    @Operation(summary = "Get all weaving contracts by company")
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Object> getAllWeavingContractByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(contractService.getAllWeavingContractByCompany(companyId));
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(contractService.getAllWeavingContractByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }

    @Operation(summary = "Get weaving contract by ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getWeavingContractById(@PathVariable("id") Long id) {
        WeavingContract contract = contractService.getWeavingContractById(id);
        return ResponseEntity.ok(contract);
    }

    @Operation(summary = "Get weaving contract by ID and company")
    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<?> getWeavingContractByIdAndCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        WeavingContract contract = contractService.getWeavingContractByIdAndCompany(id, companyId);
        return ResponseEntity.ok(contract);
    }

//    @Operation(summary = "Delete weaving contract by ID")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteContract(@PathVariable Long id) {
//        contractService.deleteContract(id);
//        return ResponseEntity.ok("Contract deleted successfully with ID: " + id);
//    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Weaving Contract", description = "Deletes a Weaving Contract by ID.")
    public ResponseEntity<Object> deleteWeavingContract(@PathVariable("id") Long id) {
        try {
        	contractService.deleteWeavingContract(id);
            return ResponseUtil.success("Weaving Contract deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Weaving Contract", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Weaving Contract by company", description = "Deletes a Weaving Contract by ID within company scope.")
    public ResponseEntity<Object> deleteWeavingContractByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
        	contractService.deleteWeavingContractByCompany(id, companyId);
            return ResponseUtil.success("Weaving Contract deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Weaving Contract", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/{id}/mapping-status")
    @Operation(summary = "Check if Weaving Contract is mapped")
    public ResponseEntity<Object> getMappingStatus(@PathVariable("id") Long id) {
        try {
            boolean isMapped = contractService.isMapped(id);
            return ResponseUtil.success("", Map.of("isMapped", isMapped));
        } catch (Exception e) {
            return ResponseUtil.error("Error checking mapping status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update weaving contract by ID")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWeavingContract(@PathVariable("id") Long id, @RequestBody WeavingContractDTO dto) {
        WeavingContract updated = contractService.updateWeavingContract(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @Operation(summary = "Update weaving contract by ID and company")
    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<?> updateWeavingContractByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id, @RequestBody WeavingContractDTO dto) {
        WeavingContract updated = contractService.updateWeavingContractByCompany(id, companyId, dto);
        return ResponseEntity.ok(updated);
    }
}