package com.tech3.erp.controller;

import com.tech3.erp.dto.WeftInwardDTO;
import com.tech3.erp.entity.WeftInward;
import com.tech3.erp.service.WeftInwardService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/weft-inward")
@Tag(name = "Weft Inward Controller", description = "APIs for Weft Inward management")
@CrossOrigin(origins = "*")
public class WeftInwardController {

    @Autowired
    private WeftInwardService weftInwardService;

    @PostMapping
    @Operation(summary = "Create Weft Inward")
    public ResponseEntity<Object> createWeftInward(@RequestBody WeftInwardDTO requestDTO) {
        try {
            WeftInward saved = weftInwardService.createWeftInward(requestDTO);
            return ResponseUtil.success("Weft Inward saved successfully", saved);
        } catch (Exception e) {
            return ResponseUtil.error("Error while saving Weft Inward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get All Weft Inwards")
    public ResponseEntity<Object> getAllWeftInwards(
            @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        try {
            return ResponseUtil.success("Weft Inwards retrieved successfully",
                    weftInwardService.getAllWeftInwardsWithPagination(offset, limit, factoryCompanyId));
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving Weft Inwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/factory/{factoryCompanyId}")
    @Operation(summary = "Get Weft Inwards by factory company")
    public ResponseEntity<Object> getByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId,
                                               @RequestParam(value = "offset", required = false) Long offset,
                                               @RequestParam(value = "limit", required = false) Long limit) {
        try {
            return ResponseUtil.success("Weft Inwards retrieved successfully",
                    weftInwardService.getByFactoryCompanyIdWithPagination(factoryCompanyId, offset, limit));
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving Weft Inwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Weft Inwards by company")
    public ResponseEntity<Object> getByCompany(@PathVariable("companyId") Long companyId,
                                               @RequestParam(value = "offset", required = false) Long offset,
                                               @RequestParam(value = "limit", required = false) Long limit) {
        try {
            if (offset == null && limit == null) {
                return ResponseUtil.success("Weft Inwards retrieved successfully",
                        weftInwardService.getAllWeftInwardsByCompany(companyId));
            }
            Long finalOffset = offset != null ? offset : 0L;
            Long finalLimit = limit != null ? limit : 10L;
            return ResponseUtil.success("Weft Inwards retrieved successfully",
                    weftInwardService.getAllWeftInwardsByCompanyWithPagination(companyId, finalOffset, finalLimit));
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving Weft Inwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Weft Inward by ID")
    public ResponseEntity<Object> getWeftInwardById(@PathVariable("id") Long id) {
        try {
            WeftInwardDTO dto = weftInwardService.getWeftInwardById(id);
            return ResponseUtil.success("Weft Inward retrieved successfully", dto);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving Weft Inward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Weft Inward by company and ID")
    public ResponseEntity<Object> getWeftInwardByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            WeftInwardDTO dto = weftInwardService.getWeftInwardByCompany(companyId, id);
            return ResponseUtil.success("Weft Inward retrieved successfully", dto);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error retrieving Weft Inward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Weft Inward")
    public ResponseEntity<Object> updateWeftInward(@PathVariable("id") Long id,
                                                   @RequestBody WeftInwardDTO requestDTO,
                                                   @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId) {
        try {
            WeftInward updated = weftInwardService.updateWeftInward(id, requestDTO, factoryCompanyId);
            return ResponseUtil.success("Weft Inward updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error while updating Weft Inward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Weft Inward by company")
    public ResponseEntity<Object> updateWeftInwardByCompany(@PathVariable("companyId") Long companyId,
                                                            @PathVariable("id") Long id,
                                                            @RequestBody WeftInwardDTO requestDTO) {
        try {
            WeftInward updated = weftInwardService.updateWeftInwardByCompany(companyId, id, requestDTO);
            return ResponseUtil.success("Weft Inward updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Error while updating Weft Inward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Weft Inward")
    public ResponseEntity<Object> deleteWeftInward(@PathVariable("id") Long id,
                                                   @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId) {
        try {
            if (factoryCompanyId != null) {
                weftInwardService.deleteWeftInward(id, factoryCompanyId);
            } else {
                weftInwardService.deleteWeftInward(id);
            }
            return ResponseUtil.success("Weft Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Weft Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Weft Inward by company")
    public ResponseEntity<Object> deleteWeftInwardByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            weftInwardService.deleteWeftInwardByCompany(companyId, id);
            return ResponseUtil.success("Weft Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Weft Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "Weft Inward API is working!";
    }
}
