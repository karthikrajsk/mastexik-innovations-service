package com.tech3.erp.controller;

import com.tech3.erp.dto.TransactionStatusDTO;
import com.tech3.erp.service.TransactionStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction-status")
public class TransactionStatusController {
    
    @Autowired
    private TransactionStatusService transactionStatusService;
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> createTransactionStatus(@RequestBody TransactionStatusDTO dto) {
        try {
            TransactionStatusDTO createdTransaction = transactionStatusService.createTransactionStatus(dto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Status created successfully");
            response.put("data", createdTransaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error creating transaction status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTransactionStatusById(@PathVariable Long id) {
        try {
            TransactionStatusDTO transaction = transactionStatusService.getTransactionStatusById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Status retrieved successfully");
            response.put("data", transaction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving transaction status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTransactionStatuses() {
        try {
            List<TransactionStatusDTO> transactions = transactionStatusService.getAllTransactionStatuses();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Statuses retrieved successfully");
            response.put("data", transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving transaction statuses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTransactionStatus(@PathVariable Long id, @RequestBody TransactionStatusDTO dto) {
        try {
            TransactionStatusDTO updatedTransaction = transactionStatusService.updateTransactionStatus(id, dto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Status updated successfully");
            response.put("data", updatedTransaction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error updating transaction status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTransactionStatus(@PathVariable Long id) {
        try {
            transactionStatusService.deleteTransactionStatus(id);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Status deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error deleting transaction status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @GetMapping("/by-type/{transactionTypeId}")
    public ResponseEntity<Map<String, Object>> getTransactionStatusByType(@PathVariable Long transactionTypeId) {
        try {
            List<TransactionStatusDTO> transactions = transactionStatusService.getTransactionStatusByType(transactionTypeId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Statuses retrieved successfully");
            response.put("data", transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving transaction statuses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/by-buyer/{buyerId}")
    public ResponseEntity<Map<String, Object>> getTransactionStatusByBuyer(@PathVariable Long buyerId) {
        try {
            List<TransactionStatusDTO> transactions = transactionStatusService.getTransactionStatusByBuyer(buyerId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Statuses retrieved successfully");
            response.put("data", transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving transaction statuses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/by-vendor/{vendorId}")
    public ResponseEntity<Map<String, Object>> getTransactionStatusByVendor(@PathVariable Long vendorId) {
        try {
            List<TransactionStatusDTO> transactions = transactionStatusService.getTransactionStatusByVendor(vendorId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Statuses retrieved successfully");
            response.put("data", transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving transaction statuses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/by-status/{status}")
    public ResponseEntity<Map<String, Object>> getTransactionStatusByStatus(@PathVariable String status) {
        try {
            List<TransactionStatusDTO> transactions = transactionStatusService.getTransactionStatusByStatus(status);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Statuses retrieved successfully");
            response.put("data", transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving transaction statuses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/by-date-range")
    public ResponseEntity<Map<String, Object>> getTransactionStatusByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            List<TransactionStatusDTO> transactions = transactionStatusService.getTransactionStatusByDateRange(start, end);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Transaction Statuses retrieved successfully");
            response.put("data", transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving transaction statuses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveTransactionStatuses() {
        try {
            List<TransactionStatusDTO> transactions = transactionStatusService.getActiveTransactionStatuses();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Active Transaction Statuses retrieved successfully");
            response.put("data", transactions);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error retrieving active transaction statuses: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
