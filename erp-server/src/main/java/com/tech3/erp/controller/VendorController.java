package com.tech3.erp.controller;

import com.tech3.erp.dto.VendorDTO;
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

@RestController
@RequestMapping("/api/vendor")
@Tag(name = "Vendor Controller", description = "APIs for Vendor management")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    @Operation(summary = "Create Vendor")
    public ResponseEntity<Object> create(@Valid @RequestBody VendorDTO dto) {
        return ResponseUtil.success("Vendor created", vendorService.createVendor(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Vendor by ID")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return ResponseUtil.success("Vendor fetched", vendorService.getVendorById(id));
    }

    @GetMapping
    @Operation(summary = "List all Vendors")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseUtil.success("Vendors fetched", vendorService.getAllVendorsWithPagination(offset, limit));
    }

    @GetMapping("/names")
    @Operation(summary = "Get Vendor Names")
    public ResponseEntity<Object> getVendorNames() {
        return ResponseUtil.success("Vendor names fetched", vendorService.getAllVendorNames());
    }

    @GetMapping("/type/{vendorTypeId}")
    @Operation(summary = "Get Vendors by Vendor Type")
    public ResponseEntity<Object> getVendorsByType(@PathVariable Long vendorTypeId) {
        List<VendorDTO> vendors = vendorService.getVendorsByVendorType(vendorTypeId);
        if (vendors.isEmpty()) {
            return ResponseUtil.error("No vendors found for Vendor Type ID: " + vendorTypeId, HttpStatus.NOT_FOUND);
        }
        return ResponseUtil.success("Vendors fetched", vendors);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Vendor")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody VendorDTO dto) {
        return ResponseUtil.success("Vendor updated", vendorService.updateVendor(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Vendor", description = "Deletes a Vendor by ID.")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long id) {
        try {
        	vendorService.deleteVendor(id);
            return ResponseUtil.success("Vendor deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Vendor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
 // Add this method to your existing VendorController class
    @GetMapping("/by-po-type/{poTypeId}")
    @Operation(summary = "Get Vendors by PO Type")
    public ResponseEntity<Object> getVendorsByPoType(@PathVariable Long poTypeId) {
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
}
