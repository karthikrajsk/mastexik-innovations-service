package com.tech3.erp.controller;

import com.tech3.erp.dto.AgencyPaymentDTO;
import com.tech3.erp.service.AgencyPaymentService;
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
@RequestMapping("/api/agency-payment")
@Tag(name = "Agency Payment Controller", description = "APIs for Agency Payment Realization management")
@Validated
public class AgencyPaymentController {

    private final AgencyPaymentService paymentService;

    public AgencyPaymentController(AgencyPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @Operation(summary = "Create Agency Payment Realization", description = "Adds a new Agency Payment Realization entry.")
    public ResponseEntity<Object> createPayment(@Valid @RequestBody AgencyPaymentDTO dto) {
        try {
            AgencyPaymentDTO created = paymentService.createPayment(dto);
            return ResponseUtil.success("Agency Payment recorded successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Agency Payment by ID", description = "Fetches an Agency Payment entry by ID.")
    public ResponseEntity<Object> getPaymentById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Agency Payment fetched successfully", paymentService.getPaymentById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Agency Payments", description = "Retrieves all Agency Payment entries with optional pagination.")
    public ResponseEntity<Object> getAllPayments(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        try {
            if (offset != null || limit != null) {
                Long finalOffset = offset != null ? offset : 0L;
                Long finalLimit = limit != null ? limit : 10L;
                return ResponseUtil.success("Agency Payments fetched successfully",
                        paymentService.getAllPaymentsWithPagination(finalOffset, finalLimit, companyId));
            }
            return ResponseUtil.success("Agency Payments fetched successfully",
                    paymentService.getAllPayments(companyId));
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Agency Payment", description = "Updates an Agency Payment entry by ID.")
    public ResponseEntity<Object> updatePayment(@PathVariable Long id, @Valid @RequestBody AgencyPaymentDTO dto) {
        try {
            AgencyPaymentDTO updated = paymentService.updatePayment(id, dto);
            return ResponseUtil.success("Agency Payment updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Agency Payment", description = "Soft deletes an Agency Payment entry by ID.")
    public ResponseEntity<Object> deletePayment(@PathVariable Long id) {
        try {
            paymentService.deletePayment(id);
            return ResponseUtil.success("Agency Payment deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
