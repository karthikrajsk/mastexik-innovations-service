package com.tech3.erp.controller;

import com.tech3.erp.dto.ItemMasterDto;
import com.tech3.erp.service.ItemMasterService;
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
@RequestMapping("/api/item-master")
@Tag(name = "Item Master Controller", description = "APIs for Item Master management")
@Validated
public class ItemMasterController {

    private final ItemMasterService itemMasterService;

    public ItemMasterController(ItemMasterService itemMasterService) {
        this.itemMasterService = itemMasterService;
    }

    @PostMapping
    @Operation(summary = "Create a new Item Master", description = "Creates a new item master record")
    public ResponseEntity<Object> createItemMaster(@Valid @RequestBody ItemMasterDto dto) {
        try {
            ItemMasterDto createdItemMaster = itemMasterService.createItemMaster(dto);
            return ResponseUtil.success("Item Master created successfully", createdItemMaster);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while creating Item Master: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Item Master by ID", description = "Fetches an item master record using its unique ID")
    public ResponseEntity<Object> getItemMasterById(@PathVariable Long id) {
        try {
            ItemMasterDto itemMaster = itemMasterService.getItemMasterById(id);
            return ResponseUtil.success("Item Master fetched successfully", itemMaster);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while fetching Item Master: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Item Masters", description = "Retrieves a list of all item master records with pagination support")
    public ResponseEntity<Object> getAllItemMasters(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        try {
            Long finalOffset = (offset != null) ? offset : 0L;
            Long finalLimit = (limit != null) ? limit : 10L;
            return ResponseUtil.success("Item Masters fetched successfully", 
                    itemMasterService.getAllItemMastersWithPagination(finalOffset, finalLimit));
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while fetching Item Masters: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Item Master", description = "Updates an item master record based on ID")
    public ResponseEntity<Object> updateItemMaster(@PathVariable Long id, @Valid @RequestBody ItemMasterDto dto) {
        try {
            ItemMasterDto updatedItemMaster = itemMasterService.updateItemMaster(id, dto);
            return ResponseUtil.success("Item Master updated successfully", updatedItemMaster);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while updating Item Master: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Item Master", description = "Deletes an item master record by ID")
    public ResponseEntity<Object> deleteItemMaster(@PathVariable Long id) {
        try {
            itemMasterService.deleteItemMaster(id);
            return ResponseUtil.success("Item Master deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Item Master: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

