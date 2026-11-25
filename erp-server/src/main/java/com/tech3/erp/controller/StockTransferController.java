package com.tech3.erp.controller;

import com.tech3.erp.dto.StockTransferDTO;
import com.tech3.erp.service.StockTransferService;
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
@RequestMapping("/api/stock-transfers")
@Tag(name = "Stock Transfer Controller", description = "APIs for Stock Transfer management")
@Validated
@CrossOrigin(origins = "*")
public class StockTransferController {

    private final StockTransferService stockTransferService;

    public StockTransferController(StockTransferService stockTransferService) {
        this.stockTransferService = stockTransferService;
    }

    @PostMapping
    @Operation(summary = "Create a new Stock Transfer", description = "Creates a new stock transfer.")
    public ResponseEntity<Object> createStockTransfer(@Valid @RequestBody StockTransferDTO stockTransferDTO) {
        try {
            StockTransferDTO createdTransfer = stockTransferService.createStockTransfer(stockTransferDTO);
            return ResponseUtil.success("Stock transfer created successfully", createdTransfer);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("Error creating stock transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Stock Transfer by ID", description = "Fetch a stock transfer using its unique ID.")
    public ResponseEntity<Object> getStockTransferById(@PathVariable Long id) {
        try {
            StockTransferDTO transfer = stockTransferService.getStockTransferById(id);
            return ResponseUtil.success("Stock transfer fetched successfully", transfer);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving stock transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Stock Transfers by Company", description = "Retrieve stock transfers for a company with pagination.")
    public ResponseEntity<Object> getAllStockTransfersByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        try {
            Long finalOffset = offset != null ? offset : 0L;
            Long finalLimit = limit != null ? limit : 10L;
            return ResponseUtil.success("Stock transfers fetched successfully",
                    stockTransferService.getAllStockTransfersByCompanyIdWithPagination(companyId, finalOffset, finalLimit));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving stock transfers: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Stock Transfer by ID and Company", description = "Fetch a stock transfer using its unique ID and company ID.")
    public ResponseEntity<Object> getStockTransferByIdAndCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            StockTransferDTO transfer = stockTransferService.getStockTransferByIdAndCompany(id, companyId);
            return ResponseUtil.success("Stock transfer fetched successfully", transfer);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving stock transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Stock Transfers", description = "Retrieve a list of all stock transfers.")
    public ResponseEntity<Object> getAllStockTransfers() {
        try {
            return ResponseUtil.success("Stock transfers fetched successfully", stockTransferService.getAllStockTransfers());
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving stock transfers: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Stock Transfer", description = "Update stock transfer details based on ID.")
    public ResponseEntity<Object> updateStockTransfer(@PathVariable Long id, @Valid @RequestBody StockTransferDTO stockTransferDTO) {
        try {
            StockTransferDTO updatedTransfer = stockTransferService.updateStockTransfer(id, stockTransferDTO);
            return ResponseUtil.success("Stock transfer updated successfully", updatedTransfer);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("Error updating stock transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Stock Transfer by Company", description = "Update stock transfer details based on ID and company ID.")
    public ResponseEntity<Object> updateStockTransferByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id, @Valid @RequestBody StockTransferDTO stockTransferDTO) {
        try {
            StockTransferDTO updatedTransfer = stockTransferService.updateStockTransferByCompany(id, companyId, stockTransferDTO);
            return ResponseUtil.success("Stock transfer updated successfully", updatedTransfer);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("Error updating stock transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Stock Transfer", description = "Deletes a stock transfer by ID.")
    public ResponseEntity<Object> deleteStockTransfer(@PathVariable Long id) {
        try {
            stockTransferService.deleteStockTransfer(id);
            return ResponseUtil.success("Stock transfer deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("Error deleting stock transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Stock Transfer by Company", description = "Deletes a stock transfer by ID within company scope.")
    public ResponseEntity<Object> deleteStockTransferByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            stockTransferService.deleteStockTransferByCompany(id, companyId);
            return ResponseUtil.success("Stock transfer deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("Error deleting stock transfer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}