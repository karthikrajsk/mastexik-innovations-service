package com.tech3.erp.controller;

import com.tech3.erp.dto.CostingTransactionDto;
import com.tech3.erp.service.CostingTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/costing-transaction")
@Tag(name = "Costing Transaction", description = "Costing Transaction API")
public class CostingTransactionController {

    @Autowired
    private CostingTransactionService costingTransactionService;

    @Operation(summary = "Create a new costing transaction")
    @PostMapping
    public CostingTransactionDto createCostingTransaction(@Valid @RequestBody CostingTransactionDto dto) {
        return costingTransactionService.createCostingTransaction(dto);
    }

    @Operation(summary = "Get all costing transactions")
    @GetMapping
    public Object getAllCostingTransactions(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return costingTransactionService.getAllCostingTransactionsWithPagination(finalOffset, finalLimit, companyId);
    }

    @Operation(summary = "Get costing transactions by company")
    @GetMapping("/company/{companyId}")
    public Object getCostingTransactionsByCompany(@PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return costingTransactionService.getAllCostingTransactionsWithPagination(finalOffset, finalLimit, companyId);
    }

    @Operation(summary = "Get costing transaction by ID")
    @GetMapping("/{id}")
    public CostingTransactionDto getCostingTransactionById(@PathVariable Long id,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        return costingTransactionService.getCostingTransactionById(id, companyId);
    }

    @Operation(summary = "Update costing transaction")
    @PutMapping("/{id}")
    public CostingTransactionDto updateCostingTransaction(@PathVariable Long id,
            @RequestParam(value = "companyId", required = false) Long companyId,
            @Valid @RequestBody CostingTransactionDto dto) {
        return costingTransactionService.updateCostingTransaction(id, dto, companyId);
    }

    @Operation(summary = "Update costing transaction by company")
    @PutMapping("/company/{companyId}/{id}")
    public CostingTransactionDto updateCostingTransactionByCompany(@PathVariable Long companyId,
            @PathVariable Long id,
            @Valid @RequestBody CostingTransactionDto dto) {
        return costingTransactionService.updateCostingTransaction(id, dto, companyId);
    }

    @Operation(summary = "Delete costing transaction (soft delete)")
    @DeleteMapping("/{id}")
    public void deleteCostingTransaction(@PathVariable Long id,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        costingTransactionService.deleteCostingTransaction(id, companyId);
    }

    @Operation(summary = "Delete costing transaction by company")
    @DeleteMapping("/company/{companyId}/{id}")
    public void deleteCostingTransactionByCompany(@PathVariable Long companyId,
            @PathVariable Long id) {
        costingTransactionService.deleteCostingTransaction(id, companyId);
    }

    @Operation(summary = "Copy costing transaction")
    @PostMapping("/{id}/copy")
    public CostingTransactionDto copyCostingTransaction(@PathVariable Long id) {
        return costingTransactionService.copyCostingTransaction(id);
    }
}