package com.tech3.erp.controller;

import com.tech3.erp.dto.PurchaseOrderDTO;
import com.tech3.erp.dto.VendorDTO;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.service.PurchaseOrderService;
import com.tech3.erp.service.VendorService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchase-orders")
@Tag(name = "Purchase Order Controller", description = "APIs for managing Purchase Orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final VendorService vendorService; 

    // Constructor injection
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService,VendorService vendorService) {
        this.purchaseOrderService = purchaseOrderService;
        this.vendorService = vendorService;
    }

    @PostMapping
    @Operation(summary = "Create Purchase Order", description = "Creates a new Purchase Order with items")
    public ResponseEntity<Object> create(@Valid @RequestBody PurchaseOrderDTO dto) {
        try {
            PurchaseOrderDTO created = purchaseOrderService.createPurchaseOrder(dto);
            return ResponseUtil.success("Purchase Order created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Purchase Order by ID", description = "Fetch a Purchase Order by its ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            PurchaseOrderDTO order = purchaseOrderService.getPurchaseOrderById(id);
            return ResponseUtil.success("Purchase Order fetched successfully", order);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Purchase Order by company", description = "Fetch a Purchase Order by ID within company scope")
    public ResponseEntity<Object> getByCompanyAndId(
            @PathVariable Long companyId, 
            @PathVariable Long id,
            @RequestParam(required = false) Long excludeInwardId) {
        try {
            PurchaseOrderDTO order = purchaseOrderService.getPurchaseOrderByIdAndCompany(id, companyId, excludeInwardId);
            return ResponseUtil.success("Purchase Order fetched successfully", order);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List all Purchase Orders", description = "Returns all Purchase Orders with pagination support")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Purchase Orders fetched successfully", purchaseOrderService.getAllPurchaseOrdersWithPagination(finalOffset, finalLimit));
    }
    
    @GetMapping("/company/{companyId}")
    @Operation(summary = "List Purchase Orders by company", description = "Returns Purchase Orders for a company with pagination support")
    public ResponseEntity<Object> getAllByCompany(
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        try {
            return ResponseUtil.success("Purchase Orders fetched successfully",
                    purchaseOrderService.getAllPurchaseOrdersByCompanyIdWithPagination(companyId, finalOffset, finalLimit));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Purchase Order", description = "Updates an existing Purchase Order by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PurchaseOrderDTO dto) {
        try {
            PurchaseOrderDTO updated = purchaseOrderService.updatePurchaseOrder(id, dto);
            return ResponseUtil.success("Purchase Order updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Purchase Order by company", description = "Updates a Purchase Order by ID within company scope")
    public ResponseEntity<Object> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody PurchaseOrderDTO dto) {
        try {
            PurchaseOrderDTO updated = purchaseOrderService.updatePurchaseOrderByCompany(id, companyId, dto);
            return ResponseUtil.success("Purchase Order updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Purchase Order", description = "Deletes a Purchase Order by ID.")
    public ResponseEntity<Object> deletePurchaseOrder(@PathVariable Long id) {
        try {
        	purchaseOrderService.deletePurchaseOrder(id);
            return ResponseUtil.success("Purchase Order deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Purchase Order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Purchase Order by company", description = "Deletes a Purchase Order by ID within company scope.")
    public ResponseEntity<Object> deletePurchaseOrderByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        try {
            purchaseOrderService.deletePurchaseOrderByCompany(id, companyId);
            return ResponseUtil.success("Purchase Order deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Purchase Order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/vendor/{vendorId}")
    @Operation(summary = "Get Purchase Orders by Company and Vendor", description = "Fetch Purchase Orders for a Vendor within a specific Company")
    public ResponseEntity<Object> getPurchaseOrdersByCompanyAndVendor(@PathVariable Long companyId, @PathVariable Long vendorId) {
        try {
            List<PurchaseOrderDTO> orders = purchaseOrderService.getPurchaseOrdersByVendorIdAndCompany(vendorId, companyId);
            return ResponseUtil.success("Purchase Orders fetched successfully", orders);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/company/{companyId}/vendor/{vendorId}/pending")
    @Operation(summary = "Get Pending Purchase Orders by Vendor ID and Company", description = "Fetch Purchase Orders with remaining quantity by Vendor ID and Company ID")
    public ResponseEntity<Object> getPendingPurchaseOrderByVendorIdAndCompany(
            @PathVariable Long companyId,
            @PathVariable Long vendorId, 
            @RequestParam(required = false) Long excludeInwardId) {
        try {
            List<PurchaseOrderDTO> orders = purchaseOrderService.getPendingPurchaseOrdersByVendorIdAndCompany(vendorId, companyId, excludeInwardId);
            return ResponseUtil.success("Pending Purchase Orders fetched successfully", orders);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vendor/{vendorId}/pending")
    @Operation(summary = "Get Pending Purchase Orders by Vendor ID", description = "Fetch Purchase Orders with remaining quantity by Vendor ID")
    public ResponseEntity<Object> getPendingPurchaseOrderByVendorId(
            @PathVariable Long vendorId, 
            @RequestParam(required = false) Long excludeInwardId) {
        try {
            List<PurchaseOrderDTO> orders = purchaseOrderService.getPendingPurchaseOrdersByVendorId(vendorId, excludeInwardId);
            return ResponseUtil.success("Pending Purchase Orders fetched successfully", orders);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/vendor/{vendorId}")
    @Operation(summary = "Get Purchase Order by Vendor ID", description = "Fetch Purchase Orders by Vendor ID")
    public ResponseEntity<Object> getPurchaseOrderByVendorId(@PathVariable Long vendorId) {
        try {
            List<PurchaseOrderDTO> orders = purchaseOrderService.getPurchaseOrdersByVendorId(vendorId);
            return ResponseUtil.success("Purchase Orders fetched successfully", orders);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
 // In VendorController.java - add this if not exists
    @GetMapping("/by-po-type/{poTypeId}")
    @Operation(summary = "Get Vendors by PO Type")
    public ResponseEntity<Object> getVendorsByPoType1(@PathVariable Long poTypeId) {
        try {
            List<VendorDTO> vendorDTOs = vendorService.getVendorsByPoType(poTypeId);
            
            if (vendorDTOs.isEmpty()) {
                return ResponseUtil.error("No vendors found for PO Type ID: " + poTypeId, HttpStatus.NOT_FOUND);
            }
                    
            return ResponseUtil.success("Vendors fetched successfully", vendorDTOs);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching vendors: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/vendors-by-po-type/{poTypeId}")
    public ResponseEntity<List<VendorDTO>> getVendorsByPoType(@PathVariable Long poTypeId) {
        return ResponseEntity.ok(purchaseOrderService.getVendorsByPoType(poTypeId));
    }
    
    @GetMapping("/{id}/can-delete")
    @Operation(summary = "Check if Purchase Order can be deleted", description = "Returns true if PO has no references")
    public ResponseEntity<Object> canDeletePurchaseOrder(@PathVariable Long id) {
        try {
            boolean canDelete = purchaseOrderService.canDeletePurchaseOrder(id);
            return ResponseUtil.success("", java.util.Map.of("canDelete", canDelete));
        } catch (Exception e) {
            return ResponseUtil.error("Error checking deletion status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}/can-edit")
    @Operation(summary = "Check if Purchase Order can be edited", description = "Returns true if PO has no references")
    public ResponseEntity<Object> canEditPurchaseOrder(@PathVariable Long id) {
        try {
            boolean canEdit = purchaseOrderService.canEditPurchaseOrder(id);
            return ResponseUtil.success("", java.util.Map.of("canEdit", canEdit));
        } catch (Exception e) {
            return ResponseUtil.error("Error checking edit status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{id}/items-deletion-status")
    @Operation(summary = "Check deletion status for each Purchase Order Item", description = "Returns map of item IDs to deletion status")
    public ResponseEntity<Object> getPurchaseOrderItemsDeletionStatus(@PathVariable Long id) {
        try {
            java.util.Map<Long, Boolean> itemsDeletionStatus = purchaseOrderService.canDeletePurchaseOrderItems(id);
            return ResponseUtil.success("", itemsDeletionStatus);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching deletion status: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
