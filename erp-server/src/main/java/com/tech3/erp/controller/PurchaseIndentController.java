package com.tech3.erp.controller;

import com.tech3.erp.dto.PurchaseIndentDTO;
import com.tech3.erp.service.PurchaseIndentService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/purchase-indents")
@Tag(name = "Purchase Indent Controller", description = "APIs for purchase indent requests")
public class PurchaseIndentController {

    private final PurchaseIndentService purchaseIndentService;

    public PurchaseIndentController(PurchaseIndentService purchaseIndentService) {
        this.purchaseIndentService = purchaseIndentService;
    }

    @GetMapping
    @Operation(summary = "List purchase indents for a company")
    public ResponseEntity<Object> list(
            @RequestParam Long companyId,
            @RequestParam(required = false) Long offset,
            @RequestParam(required = false) Long limit,
            @RequestParam(required = false) String status) {
        try {
            return ResponseUtil.success("Purchase Indents fetched successfully",
                    purchaseIndentService.list(companyId, offset, limit, status));
        } catch (IllegalArgumentException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a purchase indent within a company")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam Long companyId) {
        try {
            return ResponseUtil.success("Purchase Indent fetched successfully",
                    purchaseIndentService.getById(id, companyId));
        } catch (EntityNotFoundException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @Operation(summary = "Create a draft or submit a purchase indent")
    public ResponseEntity<Object> create(@Valid @RequestBody PurchaseIndentDTO dto) {
        try {
            return ResponseUtil.success("Purchase Indent created successfully", purchaseIndentService.create(dto));
        } catch (IllegalArgumentException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a draft purchase indent")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PurchaseIndentDTO dto) {
        try {
            return ResponseUtil.success("Purchase Indent updated successfully", purchaseIndentService.update(id, dto));
        } catch (EntityNotFoundException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a draft purchase indent within a company")
    public ResponseEntity<Object> delete(@PathVariable Long id, @RequestParam Long companyId) {
        try {
            purchaseIndentService.delete(id, companyId);
            return ResponseUtil.success("Purchase Indent deleted successfully", null);
        } catch (EntityNotFoundException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Approve a purchase indent")
    public ResponseEntity<Object> approve(
            @PathVariable Long id,
            @RequestParam(required = false) String companyId,
            @RequestHeader(value = "X-Company-Id", required = false) String headerCompanyId,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestParam(required = false) String userId) {
        try {
            Long effectiveCompanyId = parseFirstLong(companyId);
            if (effectiveCompanyId == null) {
                effectiveCompanyId = parseFirstLong(headerCompanyId);
            }
            LocalDate approveDate = null;
            String comments = null;
            Long effectiveUserId = parseFirstLong(userId);
            if (body != null) {
                if (effectiveCompanyId == null && body.get("companyId") != null) {
                    effectiveCompanyId = parseFirstLong(body.get("companyId").toString());
                }
                if (body.get("approveDate") != null) {
                    try {
                        approveDate = LocalDate.parse(body.get("approveDate").toString().substring(0, 10));
                    } catch (Exception ignored) {
                        approveDate = LocalDate.now();
                    }
                }
                if (body.get("comments") != null) {
                    comments = body.get("comments").toString();
                } else if (body.get("approvalComments") != null) {
                    comments = body.get("approvalComments").toString();
                }
                if (effectiveUserId == null && body.get("userId") != null) {
                    effectiveUserId = parseFirstLong(body.get("userId").toString());
                }
            }
            return ResponseUtil.success("Purchase Indent approved successfully",
                    purchaseIndentService.approve(id, effectiveCompanyId, approveDate, comments, effectiveUserId));
        } catch (EntityNotFoundException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException ex) {
            return ResponseUtil.error(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    private Long parseFirstLong(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            String token = value.split(",")[0].trim();
            return Long.valueOf(token);
        } catch (Exception ignored) {
            return null;
        }
    }
}
