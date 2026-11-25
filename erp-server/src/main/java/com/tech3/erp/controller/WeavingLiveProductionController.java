package com.tech3.erp.controller;

import com.tech3.erp.dto.WeavingLiveProductionBulkUploadDTO;
import com.tech3.erp.dto.WeavingLiveProductionDTO;
import com.tech3.erp.service.WeavingLiveProductionService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weaving-live-production")
@Tag(name = "Weaving Live Production Controller", description = "APIs for Weaving Live Production bulk Excel upload and management")
public class WeavingLiveProductionController {

    private final WeavingLiveProductionService service;

    @Autowired
    public WeavingLiveProductionController(WeavingLiveProductionService service) {
        this.service = service;
    }

    @PostMapping("/bulk-upload")
    @Operation(summary = "Bulk Upload Weaving Live Production Records", description = "Stores live production Excel records in database")
    public ResponseEntity<Object> bulkUpload(@RequestBody WeavingLiveProductionBulkUploadDTO bulkUploadDTO) {
        try {
            List<WeavingLiveProductionDTO> result = service.bulkUpload(bulkUploadDTO);
            return ResponseUtil.success("Weaving live production bulk upload processed successfully", result);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Weaving Live Production Records by Company", description = "Returns live production records for company")
    public ResponseEntity<Object> getByCompany(@PathVariable Long companyId) {
        try {
            List<WeavingLiveProductionDTO> result = service.getByCompanyId(companyId);
            return ResponseUtil.success("Weaving live production records retrieved successfully", result);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(summary = "Create Weaving Live Production Record", description = "Creates a single live production record")
    public ResponseEntity<Object> create(@RequestBody WeavingLiveProductionDTO dto) {
        try {
            WeavingLiveProductionDTO result = service.create(dto);
            return ResponseUtil.success("Weaving live production record created successfully", result);
        } catch (Exception e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Weaving Live Production Record by ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            WeavingLiveProductionDTO result = service.getById(id);
            return ResponseUtil.success("Weaving live production record retrieved successfully", result);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Weaving Live Production Record by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseUtil.success("Weaving live production record deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
