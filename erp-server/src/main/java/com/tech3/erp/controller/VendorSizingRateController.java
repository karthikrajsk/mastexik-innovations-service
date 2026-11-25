package com.tech3.erp.controller;

import com.tech3.erp.dto.VendorSizingRateDTO;
import com.tech3.erp.service.VendorSizingRateService;
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
@Tag(name = "Vendor Sizing Rate Controller", description = "APIs for Vendor Sizing Rate and Historical Revisions")
public class VendorSizingRateController {

    private final VendorSizingRateService rateService;

    public VendorSizingRateController(VendorSizingRateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/{vendorId}/sizing-rates")
    @Operation(summary = "Get current active Sizing Rates for Vendor")
    public ResponseEntity<Object> getActiveRates(@PathVariable Long vendorId) {
        try {
            List<VendorSizingRateDTO> rates = rateService.getActiveRatesForVendor(vendorId);
            return ResponseUtil.success("Vendor sizing rates fetched", rates);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching sizing rates: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{vendorId}/sizing-rates/history")
    @Operation(summary = "Get full Sizing Rate revision history for Vendor")
    public ResponseEntity<Object> getRateHistory(
            @PathVariable Long vendorId,
            @RequestParam(value = "yarnCount", required = false) String yarnCount) {
        try {
            List<VendorSizingRateDTO> history = rateService.getRateHistoryForVendor(vendorId, yarnCount);
            return ResponseUtil.success("Vendor sizing rate history fetched", history);
        } catch (Exception e) {
            return ResponseUtil.error("Error fetching rate history: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{vendorId}/sizing-rates")
    @Operation(summary = "Add or revise Sizing Rate for Vendor and Yarn Count")
    public ResponseEntity<Object> saveOrReviseRate(
            @PathVariable Long vendorId,
            @Valid @RequestBody VendorSizingRateDTO dto) {
        try {
            VendorSizingRateDTO saved = rateService.saveOrReviseRate(vendorId, dto);
            return ResponseUtil.success("Sizing rate saved/revised successfully", saved);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Error saving sizing rate: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/sizing-rates/{id}")
    @Operation(summary = "Delete Sizing Rate entry")
    public ResponseEntity<Object> deleteRate(@PathVariable Long id) {
        try {
            rateService.deleteRate(id);
            return ResponseUtil.success("Sizing rate deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error deleting sizing rate: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
