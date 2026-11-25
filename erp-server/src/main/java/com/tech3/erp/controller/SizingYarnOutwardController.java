package com.tech3.erp.controller;

import com.tech3.erp.dto.SizingYarnOutwardDTO;
import com.tech3.erp.service.SizingYarnOutwardService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sizing-yarn-outward")
@Tag(name = "Sizing Yarn Outward Controller", description = "APIs for managing sizing yarn outward entries")
@CrossOrigin(origins = "*")
public class SizingYarnOutwardController {

    private final SizingYarnOutwardService sizingYarnOutwardService;

    public SizingYarnOutwardController(SizingYarnOutwardService sizingYarnOutwardService) {
        this.sizingYarnOutwardService = sizingYarnOutwardService;
    }

    @PostMapping
    @Operation(summary = "Create Sizing Yarn Outward")
    public ResponseEntity<Object> createSizingYarnOutward(@RequestBody SizingYarnOutwardDTO dto) {
        try {
            return ResponseUtil.success("Sizing Yarn Outward created successfully", sizingYarnOutwardService.createSizingYarnOutward(dto));
        } catch (Exception e) {
            return ResponseUtil.error("Failed to create Sizing Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Sizing Yarn Outwards")
    public ResponseEntity<Object> getAllSizingYarnOutwards(@RequestParam(value = "offset", required = false) Long offset,
                                                           @RequestParam(value = "limit", required = false) Long limit) {
        try {
            return ResponseUtil.success("Sizing Yarn Outwards fetched successfully",
                    sizingYarnOutwardService.getAllSizingYarnOutwardsWithPagination(offset, limit));
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch Sizing Yarn Outwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/factory/{factoryCompanyId}")
    @Operation(summary = "Get Sizing Yarn Outwards by factory company")
    public ResponseEntity<Object> getByFactory(@PathVariable Long factoryCompanyId,
                                               @RequestParam(value = "offset", required = false) Long offset,
                                               @RequestParam(value = "limit", required = false) Long limit) {
        try {
            return ResponseUtil.success("Sizing Yarn Outwards fetched successfully",
                    sizingYarnOutwardService.getSizingYarnOutwardsByFactoryWithPagination(factoryCompanyId, offset, limit));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch Sizing Yarn Outwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Sizing Yarn Outward by ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Sizing Yarn Outward fetched successfully", sizingYarnOutwardService.getSizingYarnOutwardById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch Sizing Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Get Sizing Yarn Outward by factory company and ID")
    public ResponseEntity<Object> getByFactoryAndId(@PathVariable Long factoryCompanyId, @PathVariable Long id) {
        try {
            return ResponseUtil.success("Sizing Yarn Outward fetched successfully",
                    sizingYarnOutwardService.getSizingYarnOutwardByFactory(factoryCompanyId, id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch Sizing Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Sizing Yarn Outward")
    public ResponseEntity<Object> updateSizingYarnOutward(@PathVariable Long id, @RequestBody SizingYarnOutwardDTO dto) {
        try {
            return ResponseUtil.success("Sizing Yarn Outward updated successfully",
                    sizingYarnOutwardService.updateSizingYarnOutward(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to update Sizing Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Update Sizing Yarn Outward by factory company")
    public ResponseEntity<Object> updateSizingYarnOutwardByFactory(@PathVariable Long factoryCompanyId,
                                                                   @PathVariable Long id,
                                                                   @RequestBody SizingYarnOutwardDTO dto) {
        try {
            return ResponseUtil.success("Sizing Yarn Outward updated successfully",
                    sizingYarnOutwardService.updateSizingYarnOutwardByCompany(factoryCompanyId, id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to update Sizing Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Sizing Yarn Outward")
    public ResponseEntity<Object> deleteSizingYarnOutward(@PathVariable Long id) {
        try {
            sizingYarnOutwardService.deleteSizingYarnOutward(id);
            return ResponseUtil.success("Sizing Yarn Outward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to delete Sizing Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Delete Sizing Yarn Outward by factory company")
    public ResponseEntity<Object> deleteSizingYarnOutwardByFactory(@PathVariable Long factoryCompanyId, @PathVariable Long id) {
        try {
            sizingYarnOutwardService.deleteSizingYarnOutwardByFactory(factoryCompanyId, id);
            return ResponseUtil.success("Sizing Yarn Outward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to delete Sizing Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "Sizing Yarn Outward API is working!";
    }
}
