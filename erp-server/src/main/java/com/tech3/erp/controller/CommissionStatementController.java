package com.tech3.erp.controller;

import com.tech3.erp.dto.CommissionStatementDTO;
import com.tech3.erp.service.CommissionStatementService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/agency-commission-statement")
@Tag(name = "Agency Commission Statement Controller", description = "APIs for Agency Commission Statement & Tax Invoicing")
@Validated
public class CommissionStatementController {

    private final CommissionStatementService statementService;

    public CommissionStatementController(CommissionStatementService statementService) {
        this.statementService = statementService;
    }

    public static class PaymentReceiptRequest {
        private LocalDate paymentReceivedDate;
        private String paymentMode;
        private String referenceNo;
        private String bankAccount;
        private String remarks;

        public LocalDate getPaymentReceivedDate() { return paymentReceivedDate; }
        public void setPaymentReceivedDate(LocalDate paymentReceivedDate) { this.paymentReceivedDate = paymentReceivedDate; }

        public String getPaymentMode() { return paymentMode; }
        public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

        public String getReferenceNo() { return referenceNo; }
        public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }

        public String getBankAccount() { return bankAccount; }
        public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }

        public String getRemarks() { return remarks; }
        public void setRemarks(String remarks) { this.remarks = remarks; }
    }

    @PostMapping
    @Operation(summary = "Create Commission Statement", description = "Generates a new Agency Commission Statement / Bill.")
    public ResponseEntity<Object> createStatement(@Valid @RequestBody CommissionStatementDTO dto) {
        try {
            CommissionStatementDTO created = statementService.createStatement(dto);
            return ResponseUtil.success("Commission Statement created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Commission Statement by ID", description = "Fetches a Commission Statement by ID.")
    public ResponseEntity<Object> getStatementById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Commission Statement fetched successfully", statementService.getStatementById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Commission Statements", description = "Retrieves all Commission Statements with optional pagination and filters.")
    public ResponseEntity<Object> getAllStatements(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "companyId", required = false) Long companyId,
            @RequestParam(value = "status", required = false) String status) {
        try {
            if (offset != null || limit != null) {
                Long finalOffset = offset != null ? offset : 0L;
                Long finalLimit = limit != null ? limit : 10L;
                return ResponseUtil.success("Commission Statements fetched successfully",
                        statementService.getAllStatementsWithPagination(finalOffset, finalLimit, companyId, status));
            }
            return ResponseUtil.success("Commission Statements fetched successfully",
                    statementService.getAllStatements(companyId, status));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Commission Statement", description = "Updates a Commission Statement by ID.")
    public ResponseEntity<Object> updateStatement(@PathVariable Long id, @Valid @RequestBody CommissionStatementDTO dto) {
        try {
            CommissionStatementDTO updated = statementService.updateStatement(id, dto);
            return ResponseUtil.success("Commission Statement updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/payment-receipt")
    @Operation(summary = "Record Commission Payment Receipt", description = "Records receipt of commission payment from Mill and marks status as Paid.")
    public ResponseEntity<Object> recordPaymentReceipt(
            @PathVariable Long id,
            @RequestBody PaymentReceiptRequest req) {
        try {
            CommissionStatementDTO updated = statementService.recordPaymentReceipt(
                    id,
                    req.getPaymentReceivedDate(),
                    req.getPaymentMode(),
                    req.getReferenceNo(),
                    req.getBankAccount(),
                    req.getRemarks()
            );
            return ResponseUtil.success("Commission payment receipt recorded successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Commission Statement", description = "Soft deletes a Commission Statement by ID.")
    public ResponseEntity<Object> deleteStatement(@PathVariable Long id) {
        try {
            statementService.deleteStatement(id);
            return ResponseUtil.success("Commission Statement deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
