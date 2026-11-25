package com.tech3.erp.controller;

import com.tech3.erp.dto.NumberFormatConfigurationDTO;
import com.tech3.erp.service.NumberFormatConfigurationService;
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
@RequestMapping("/api/number-format-configurations")
@Tag(name = "Number Format Configuration Controller", description = "APIs for Number Format Configuration management")
@Validated
public class NumberFormatConfigurationController {

    private final NumberFormatConfigurationService configurationService;

    public NumberFormatConfigurationController(NumberFormatConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping
    @Operation(summary = "Create a new Number Format Configuration", description = "Adds a new number format configuration.")
    public ResponseEntity<Object> createConfiguration(@Valid @RequestBody NumberFormatConfigurationDTO configurationDTO) {
        try {
            NumberFormatConfigurationDTO createdConfiguration = configurationService.createConfiguration(configurationDTO);
            return ResponseUtil.success("Configuration created successfully", createdConfiguration);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Number Format Configuration by ID", description = "Fetch a configuration using its unique ID.")
    public ResponseEntity<Object> getConfigurationById(@PathVariable("id") Long id,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        try {
            return ResponseUtil.success("Configuration fetched successfully", configurationService.getConfigurationById(id, companyId));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Number Format Configurations", description = "Retrieve a list of all configurations.")
    public ResponseEntity<Object> getAllConfigurations(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        
        return ResponseUtil.success("Configurations fetched successfully", configurationService.getAllConfigurationsWithPagination(finalOffset, finalLimit, companyId));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Number Format Configurations by company", description = "Fetch configurations for a company with optional pagination.")
    public ResponseEntity<Object> getConfigurationsByCompany(@PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Configurations fetched successfully",
                configurationService.getAllConfigurationsWithPagination(finalOffset, finalLimit, companyId));
    }

    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Number Format Configuration by company and ID", description = "Fetch a configuration using company ID and configuration ID.")
    public ResponseEntity<Object> getConfigurationByCompanyAndId(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            return ResponseUtil.success("Configuration fetched successfully", configurationService.getConfigurationById(id, companyId));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Number Format Configuration", description = "Update configuration details based on ID.")
    public ResponseEntity<Object> updateConfiguration(@PathVariable("id") Long id,
            @RequestParam(value = "companyId", required = false) Long companyId,
            @Valid @RequestBody NumberFormatConfigurationDTO configurationDTO) {
        try {
            return ResponseUtil.success("Configuration updated successfully", configurationService.updateConfiguration(id, configurationDTO, companyId));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Number Format Configuration by company", description = "Update configuration details by ID within company scope.")
    public ResponseEntity<Object> updateConfigurationForCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id,
            @Valid @RequestBody NumberFormatConfigurationDTO configurationDTO) {
        try {
            return ResponseUtil.success("Configuration updated successfully", configurationService.updateConfigurationByCompany(companyId, id, configurationDTO));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Number Format Configuration", description = "Deletes a configuration by ID.")
    public ResponseEntity<Object> deleteConfiguration(@PathVariable("id") Long id,
            @RequestParam(value = "companyId", required = false) Long companyId) {
        try {
            configurationService.deleteConfiguration(id, companyId);
            return ResponseUtil.success("Configuration deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the configuration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Number Format Configuration by company", description = "Deletes a configuration by ID within company scope.")
    public ResponseEntity<Object> deleteConfigurationForCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            configurationService.deleteConfigurationByCompany(companyId, id);
            return ResponseUtil.success("Configuration deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the configuration", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/preview")
    @Operation(summary = "Generate Format Preview", description = "Generates a preview of the number format based on provided parameters.")
    public ResponseEntity<Object> generatePreview(@RequestBody PreviewRequest request) {
        try {
            String preview = configurationService.generatePrintContextPreview(
                request.getScreenName(),
                request.getPrefix(),
                request.getFinancialYear(),
                request.getSequenceNumber(),
                request.getSeparator()
            );
            return ResponseUtil.success("Preview generated successfully", preview);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validate-print-context")
    @Operation(summary = "Validate Print Context Parameters", description = "Validates print context parameters for format configuration.")
    public ResponseEntity<Object> validatePrintContext(@RequestBody PrintContextValidationRequest request) {
        try {
            configurationService.validatePrintContextParameters(
                request.getScreenName(),
                request.getParameter1(),
                request.getParameter2(),
                request.getParameter3(),
                request.getSeparator()
            );
            return ResponseUtil.success("Parameters are valid", null);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // DTO classes for requests
    public static class PreviewRequest {
        private String screenName;
        private String prefix;
        private String financialYear;
        private String sequenceNumber;
        private String separator;

        // Getters and setters
        public String getScreenName() { return screenName; }
        public void setScreenName(String screenName) { this.screenName = screenName; }

        public String getPrefix() { return prefix; }
        public void setPrefix(String prefix) { this.prefix = prefix; }

        public String getFinancialYear() { return financialYear; }
        public void setFinancialYear(String financialYear) { this.financialYear = financialYear; }

        public String getSequenceNumber() { return sequenceNumber; }
        public void setSequenceNumber(String sequenceNumber) { this.sequenceNumber = sequenceNumber; }

        public String getSeparator() { return separator; }
        public void setSeparator(String separator) { this.separator = separator; }
    }

    public static class PrintContextValidationRequest {
        private String screenName;
        private String parameter1;
        private String parameter2;
        private String parameter3;
        private String separator;

        // Getters and setters
        public String getScreenName() { return screenName; }
        public void setScreenName(String screenName) { this.screenName = screenName; }

        public String getParameter1() { return parameter1; }
        public void setParameter1(String parameter1) { this.parameter1 = parameter1; }

        public String getParameter2() { return parameter2; }
        public void setParameter2(String parameter2) { this.parameter2 = parameter2; }

        public String getParameter3() { return parameter3; }
        public void setParameter3(String parameter3) { this.parameter3 = parameter3; }

        public String getSeparator() { return separator; }
        public void setSeparator(String separator) { this.separator = separator; }
    }
}