package com.tech3.erp.controller;

import com.tech3.erp.dto.PurchaseInwardDTO;
import com.tech3.erp.dto.PurchaseOrderDTO;
import com.tech3.erp.dto.YarnInwardStockDetailsDto;
import com.tech3.erp.entity.PurchaseInward;
import com.tech3.erp.service.PurchaseInwardService;
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
@RequestMapping("/api")
@Tag(name = "Purchase Inward Controller", description = "APIs for Purchase Inward management")
@CrossOrigin(origins = "*")
public class PurchaseInwardController {

    @Autowired
    private PurchaseInwardService inwardService;

    // ============ CREATE ENDPOINTS ============
    
    // Create a new purchase inward entry (generic - OLD)
    @PostMapping("/purchase-inward")
    public ResponseEntity<?> createPurchaseInward(@RequestBody PurchaseInwardDTO requestDTO) {
        try {
            PurchaseInward savedInward = inwardService.createPurchaseInward(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(inwardService.convertToDetailedDTO(savedInward));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while saving Purchase Inward: " + e.getMessage());
        }
    }

    // ============ READ ENDPOINTS ============
    
    // Get all purchase inwards with pagination (OLD)
    @GetMapping("/purchase-inward")
    public ResponseEntity<Object> getAllPurchaseInwards(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "fabricTypeId", required = false) Long fabricTypeId,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        
        // If companyId is provided, use company-specific method
        if (companyId != null) {
            try {
                var result = inwardService.getAllPurchaseInwardsWithPaginationAndCompany(finalOffset, finalLimit, fabricTypeId, companyId);
                return ResponseUtil.success("Purchase Inwards fetched successfully", result);
            } catch (Exception e) {
                return ResponseUtil.error("Error fetching Purchase Inwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        // Otherwise use the old method
        return ResponseUtil.success("Purchase Inwards fetched successfully", inwardService.getAllPurchaseInwardsWithPagination(finalOffset, finalLimit, fabricTypeId));
    }


    // Get purchase inwards by company only (NEW)
    @GetMapping("/purchase-inward/company/{companyId}")
    @Operation(summary = "Get Purchase Inwards by Company", description = "Returns all Purchase Inwards for a specific company")
    public ResponseEntity<Object> getPurchaseInwardsByCompany(@PathVariable("companyId") Long companyId) {
        try {
            List<PurchaseInwardDTO> inwards = inwardService.getPurchaseInwardsByCompany(companyId);
            return ResponseUtil.success("Purchase Inwards fetched successfully", inwards);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching Purchase Inwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a purchase inward by ID (OLD)
    @GetMapping("/purchase-inward/{id}")
    public ResponseEntity<?> getPurchaseInwardById(@PathVariable("id") Long id) {
        try {
            PurchaseInward inward = inwardService.getPurchaseInwardById(id);
            if (inward != null) {
                return ResponseEntity.ok(inwardService.convertToDetailedDTO(inward));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Purchase Inward not found for ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // Get purchase inward by ID and company (NEW)
    @GetMapping("/purchase-inward/company/{companyId}/{id}")
    @Operation(summary = "Get Purchase Inward by ID and Company", description = "Returns a Purchase Inward by ID within company scope")
    public ResponseEntity<Object> getPurchaseInwardByIdAndCompany(
            @PathVariable("companyId") Long companyId, 
            @PathVariable("id") Long id) {
        try {
            PurchaseInward inward = inwardService.getPurchaseInwardByIdAndCompany(id, companyId);
            return ResponseUtil.success("Purchase Inward fetched successfully", inwardService.convertToDetailedDTO(inward));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ============ UPDATE ENDPOINTS ============
    
    // Update a purchase inward by ID (OLD)
    @PutMapping("/purchase-inward/{id}")
    @Operation(summary = "Update Purchase Inward", description = "Updates a Purchase Inward by ID.")
    public ResponseEntity<?> updatePurchaseInward(@PathVariable("id") Long id, @RequestBody PurchaseInwardDTO requestDTO) {
        try {
            PurchaseInward updatedInward = inwardService.updatePurchaseInward(id, requestDTO);
            return ResponseEntity.ok(inwardService.convertToDetailedDTO(updatedInward));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Purchase Inward not found for ID: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating Purchase Inward: " + e.getMessage());
        }
    }

    // Update purchase inward by company (NEW)
    @PutMapping("/purchase-inward/company/{companyId}/{id}")
    @Operation(summary = "Update Purchase Inward by Company", description = "Updates a Purchase Inward by ID within company scope")
    public ResponseEntity<Object> updatePurchaseInwardByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @RequestBody PurchaseInwardDTO requestDTO) {
        try {
            PurchaseInward updatedInward = inwardService.updatePurchaseInwardByCompany(id, companyId, requestDTO);
            return ResponseUtil.success("Purchase Inward updated successfully", inwardService.convertToDetailedDTO(updatedInward));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Error while updating Purchase Inward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update accounting details for a purchase inward
    @PutMapping("/purchase-inward/{id}/accounting")
    @Operation(summary = "Update Accounting Details", description = "Updates accounting details and marks Purchase Inward as accounted.")
    public ResponseEntity<Object> updateAccountingDetails(
            @PathVariable("id") Long id,
            @RequestBody PurchaseInwardDTO requestDTO) {
        try {
            PurchaseInwardDTO updated = inwardService.updateAccountingDetails(id, requestDTO);
            return ResponseUtil.success("Accounting details updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error while updating accounting details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/purchase-inward/company/{companyId}/{id}/accounting")
    @Operation(summary = "Update Accounting Details by Company", description = "Updates accounting details and marks Purchase Inward as accounted within company scope.")
    public ResponseEntity<Object> updateAccountingDetailsByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @RequestBody PurchaseInwardDTO requestDTO) {
        try {
            PurchaseInwardDTO updated = inwardService.updateAccountingDetailsByCompany(id, companyId, requestDTO);
            return ResponseUtil.success("Accounting details updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Error while updating accounting details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ============ VALIDATION ENDPOINTS ============
    
    // Check if purchase inward can be deleted (OLD)
    @GetMapping("/purchase-inward/{id}/can-delete")
    @Operation(summary = "Check if Purchase Inward can be deleted", description = "Returns true if inward has no references")
    public ResponseEntity<Object> canDeletePurchaseInward(@PathVariable("id") Long id) {
        try {
            boolean canDelete = inwardService.canDeletePurchaseInward(id);
            return ResponseUtil.success("", java.util.Map.of("canDelete", canDelete));
        } catch (Exception e) {
            return ResponseUtil.error("Error checking deletion status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Check if purchase inward can be edited (OLD)
    @GetMapping("/purchase-inward/{id}/can-edit")
    @Operation(summary = "Check if Purchase Inward can be edited", description = "Returns true if inward has no references")
    public ResponseEntity<Object> canEditPurchaseInward(@PathVariable("id") Long id) {
        try {
            boolean canEdit = inwardService.canEditPurchaseInward(id);
            return ResponseUtil.success("", java.util.Map.of("canEdit", canEdit));
        } catch (Exception e) {
            return ResponseUtil.error("Error checking edit status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Check deletion status for each Purchase Inward Item (OLD)
    @GetMapping("/purchase-inward/{id}/items-deletion-status")
    @Operation(summary = "Check deletion status for each Purchase Inward Item", description = "Returns map of item IDs to deletion status")
    public ResponseEntity<Object> getPurchaseInwardItemsDeletionStatus(@PathVariable("id") Long id) {
        try {
            java.util.Map<Long, Boolean> itemsDeletionStatus = inwardService.canDeletePurchaseInwardItems(id);
            return ResponseUtil.success("", itemsDeletionStatus);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching deletion status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ============ YARN STOCK ENDPOINTS ============
    
    // Get YarnInwardStockDetails filtered by various criteria for yarn issue popup (OLD)
    @GetMapping("/yarn-stock-details/filter")
    public ResponseEntity<List<YarnInwardStockDetailsDto>> getYarnInwardStockDetailsByFilters(
            @RequestParam(required = false) Long itemId,
            @RequestParam(required = false) Long inwardTypeId,
            @RequestParam(required = false) Long purchaseOrderId,
            @RequestParam(required = false) Long poTypeId,
            @RequestParam(required = false) Long companyId) {
        List<YarnInwardStockDetailsDto> details = inwardService.getYarnInwardStockDetailsByFilters(itemId, inwardTypeId, purchaseOrderId, poTypeId, companyId);
        return ResponseEntity.ok(details);
    }

    // ============ HEALTH CHECK ============
    
    // Health check endpoint (OLD)
    @GetMapping("/purchase-inward/ping")
    public String ping() {
        return "Purchase Inward API is working!";
    }

    @DeleteMapping("/purchase-inward/{id}/hard")
    @Operation(summary = "Permanently Delete Purchase Inward", description = "Permanently deletes a Purchase Inward and all related stock records by ID.")
    public ResponseEntity<Object> hardDeletePurchaseInward(@PathVariable("id") Long id) {
        try {
            inwardService.hardDeletePurchaseInward(id);
            return ResponseUtil.success("Purchase Inward permanently deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while permanently deleting the Purchase Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/purchase-inward/company/{companyId}/{id}/hard")
    @Operation(summary = "Permanently Delete Purchase Inward by Company", description = "Permanently deletes a Purchase Inward and all related stock records within a company.")
    public ResponseEntity<Object> hardDeletePurchaseInwardByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        try {
            inwardService.hardDeletePurchaseInwardByCompany(id, companyId);
            return ResponseUtil.success("Purchase Inward permanently deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while permanently deleting the Purchase Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    // ============ DELETE ENDPOINTS ============
    
    // Delete a purchase inward by ID (OLD)
//    @DeleteMapping("/purchase-inward/{id}")
//    @Operation(summary = "Delete Purchase Inward", description = "Deletes a Purchase Inward by ID.")
//    public ResponseEntity<Object> deletePurchaseInward(@PathVariable Long id) {
//        try {
//            inwardService.deletePurchaseInward(id);
//            return ResponseUtil.success("Purchase Inward deleted successfully", null);
//        } catch (EntityNotFoundException e) {
//            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (IllegalStateException e) {
//            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
//        } catch (Exception e) {
//            return ResponseUtil.error("An error occurred while deleting the Purchase Inward", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // Delete purchase inward by company (NEW)
//    @DeleteMapping("/purchase-inward/company/{companyId}/{id}")
//    @Operation(summary = "Delete Purchase Inward by Company", description = "Deletes a Purchase Inward by ID within company scope")
//    public ResponseEntity<Object> deletePurchaseInwardByCompany(
//            @PathVariable Long companyId,
//            @PathVariable Long id) {
//        try {
//            inwardService.deletePurchaseInwardByCompany(id, companyId);
//            return ResponseUtil.success("Purchase Inward deleted successfully", null);
//        } catch (EntityNotFoundException e) {
//            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (IllegalStateException e) {
//            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
//        } catch (Exception e) {
//            return ResponseUtil.error("An error occurred while deleting the Purchase Inward", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
