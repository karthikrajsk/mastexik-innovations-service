package com.tech3.erp.controller;

import com.tech3.erp.dto.ItemOutwardDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.ItemOutwardService;
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
@RequestMapping("/api/item-outward")
@Tag(name = "Item Outward Controller", description = "APIs for Item Outward management")
public class ItemOutwardController {

    private final ItemOutwardService itemOutwardService;

    public ItemOutwardController(ItemOutwardService itemOutwardService) {
        this.itemOutwardService = itemOutwardService;
    }

    @PostMapping
    @Operation(summary = "Create Item Outward", description = "Create a new Item Outward entry")
    public ResponseEntity<Object> create(@Valid @RequestBody ItemOutwardDTO dto) {
        try {
            return ResponseUtil.success("Item Outward created successfully", itemOutwardService.createItemOutward(dto));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Item Outward by ID", description = "Fetch an Item Outward using its unique ID")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        try {
            return ResponseUtil.success("Item Outward fetched successfully", itemOutwardService.getItemOutwardById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Item Outward by company", description = "Fetch an Item Outward by ID within company scope")
    public ResponseEntity<Object> getByCompanyAndId(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            return ResponseUtil.success("Item Outward fetched successfully", itemOutwardService.getItemOutwardByIdAndCompany(id, companyId));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Item Outwards", description = "List all Item Outward records with pagination")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        
        PaginatedResponse<ItemOutwardDTO> response = itemOutwardService.getAllItemOutwardsWithPagination(finalOffset, finalLimit);
        return ResponseUtil.success("Item Outwards fetched successfully", response);
    }
    
    @GetMapping("/company/{companyId}")
    @Operation(summary = "List Item Outwards by company", description = "Returns Item Outwards for a company with pagination support")
    public ResponseEntity<Object> getAllByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        try {
            return ResponseUtil.success("Item Outwards fetched successfully",
                    itemOutwardService.getAllItemOutwardsByCompanyIdWithPagination(companyId, finalOffset, finalLimit));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all Item Outwards without pagination", description = "List all Item Outward records")
    public ResponseEntity<Object> getAllWithoutPagination() {
        List<ItemOutwardDTO> all = itemOutwardService.getAllItemOutwards();
        return ResponseUtil.success("Item Outwards fetched successfully", all);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Item Outward", description = "Update an existing Item Outward entry")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @Valid @RequestBody ItemOutwardDTO dto) {
        try {
            return ResponseUtil.success("Item Outward updated successfully", itemOutwardService.updateItemOutward(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Item Outward by company", description = "Updates an Item Outward by ID within company scope")
    public ResponseEntity<Object> updateByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id, @Valid @RequestBody ItemOutwardDTO dto) {
        try {
            return ResponseUtil.success("Item Outward updated successfully", itemOutwardService.updateItemOutwardByCompany(id, companyId, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Item Outward", description = "Delete an Item Outward entry")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            itemOutwardService.deleteItemOutward(id);
            return ResponseUtil.success("Item Outward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Item Outward by company", description = "Deletes an Item Outward by ID within company scope")
    public ResponseEntity<Object> deleteByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            itemOutwardService.deleteItemOutwardByCompany(id, companyId);
            return ResponseUtil.success("Item Outward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/suggestions/machine-names")
    @Operation(summary = "Get Machine Name Suggestions", description = "Get list of previously saved machine names for suggestions")
    public ResponseEntity<Object> getMachineNameSuggestions() {
        List<String> suggestions = itemOutwardService.getMachineNameSuggestions();
        return ResponseUtil.success("Machine name suggestions fetched successfully", suggestions);
    }

    // @GetMapping("/item-stock-details/filter")
    // @Operation(summary = "Get Item Stock Details by Filter", description = "Fetch item stock details filtered by vendorId, warehouseId, and companyId")
    // public ResponseEntity<Object> getItemStockDetailsByFilter(
    //         @RequestParam(value = "vendorId", required = false) Long vendorId,
    //         @RequestParam(value = "warehouseId", required = false) Long warehouseId,
    //         @RequestParam(value = "companyId", required = false) Long companyId) {
    //     try {
    //         List<java.util.Map<String, Object>> result = itemOutwardService.getItemStockDetailsByFilter(vendorId, warehouseId, companyId);
    //         return ResponseUtil.success("Item stock details fetched successfully", result);
    //     } catch (Exception e) {
    //         return ResponseUtil.error("Error fetching item stock details: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}

