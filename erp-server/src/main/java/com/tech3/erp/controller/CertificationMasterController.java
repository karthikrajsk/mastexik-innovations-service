package com.tech3.erp.controller;

import com.tech3.erp.dto.CertificationMasterDTO;
import com.tech3.erp.service.CertificationMasterService;
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
@RequestMapping("/api/masters/certifications")
@Tag(name = "Certification Master Controller", description = "APIs for Certification Master management")
@Validated
public class CertificationMasterController {

    private final CertificationMasterService certificationMasterService;

    public CertificationMasterController(CertificationMasterService certificationMasterService) {
        this.certificationMasterService = certificationMasterService;
    }

    @PostMapping
    @Operation(summary = "Create a new Certification Master", description = "Adds a new Certification Master with logo media.")
    public ResponseEntity<Object> createCertificationMaster(@Valid @RequestBody CertificationMasterDTO certificationMasterDTO) {
        try {
            CertificationMasterDTO createdCertification = certificationMasterService.createCertificationMaster(certificationMasterDTO);
            return ResponseUtil.success("Certification Master created successfully", createdCertification);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Certification Master by ID", description = "Fetch a Certification Master using its unique ID.")
    public ResponseEntity<Object> getCertificationMasterById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Certification Master fetched successfully", certificationMasterService.getCertificationMasterById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Certification Masters", description = "Retrieve a list of all Certification Masters with pagination support.")
    public ResponseEntity<Object> getAllCertificationMasters(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Certification Masters fetched successfully", 
                certificationMasterService.getAllCertificationMastersWithPagination(finalOffset, finalLimit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Certification Master", description = "Update Certification Master details based on ID.")
    public ResponseEntity<Object> updateCertificationMaster(@PathVariable Long id, @Valid @RequestBody CertificationMasterDTO certificationMasterDTO) {
        try {
            return ResponseUtil.success("Certification Master updated successfully", 
                    certificationMasterService.updateCertificationMaster(id, certificationMasterDTO));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Certification Master", description = "Deletes a Certification Master by ID.")
    public ResponseEntity<Object> deleteCertificationMaster(@PathVariable Long id) {
        try {
            certificationMasterService.deleteCertificationMaster(id);
            return ResponseUtil.success("Certification Master deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Certification Master", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
