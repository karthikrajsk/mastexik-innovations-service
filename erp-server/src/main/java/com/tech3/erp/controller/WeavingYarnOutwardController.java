package com.tech3.erp.controller;

import com.tech3.erp.dto.WeavingYarnOutwardDTO;
import com.tech3.erp.service.WeavingYarnOutwardService;
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
@RequestMapping("/api/weaving-yarn-outward")
@Tag(name = "Weaving Yarn Outward Controller", description = "APIs for managing weaving yarn outward entries")
@CrossOrigin(origins = "*")
public class WeavingYarnOutwardController {

    private final WeavingYarnOutwardService weavingYarnOutwardService;

    public WeavingYarnOutwardController(WeavingYarnOutwardService weavingYarnOutwardService) {
        this.weavingYarnOutwardService = weavingYarnOutwardService;
    }

    @PostMapping
    @Operation(summary = "Create Weaving Yarn Outward")
    public ResponseEntity<Object> createWeavingYarnOutward(@RequestBody WeavingYarnOutwardDTO dto) {
        try {
            return ResponseUtil.success("Weaving Yarn Outward created successfully", weavingYarnOutwardService.createWeavingYarnOutward(dto));
        } catch (Exception e) {
            return ResponseUtil.error("Failed to create Weaving Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Weaving Yarn Outwards")
    public ResponseEntity<Object> getAllWeavingYarnOutwards(@RequestParam(value = "offset", required = false) Long offset,
                                                            @RequestParam(value = "limit", required = false) Long limit) {
        try {
            return ResponseUtil.success("Weaving Yarn Outwards fetched successfully",
                    weavingYarnOutwardService.getAllWeavingYarnOutwardsWithPagination(offset, limit));
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch Weaving Yarn Outwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/factory/{factoryCompanyId}")
    @Operation(summary = "Get Weaving Yarn Outwards by company")
    public ResponseEntity<Object> getByCompany(@PathVariable("factoryCompanyId") Long factoryCompanyId,
                                               @RequestParam(value = "offset", required = false) Long offset,
                                               @RequestParam(value = "limit", required = false) Long limit) {
        try {
            return ResponseUtil.success("Weaving Yarn Outwards fetched successfully",
                    weavingYarnOutwardService.getWeavingYarnOutwardsByCompanyWithPagination(factoryCompanyId, offset, limit));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch Weaving Yarn Outwards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Weaving Yarn Outward by ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Weaving Yarn Outward fetched successfully", weavingYarnOutwardService.getWeavingYarnOutwardById(id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch Weaving Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Get Weaving Yarn Outward by company and ID")
    public ResponseEntity<Object> getByCompanyAndId(@PathVariable("factoryCompanyId") Long factoryCompanyId, @PathVariable Long id) {
        try {
            return ResponseUtil.success("Weaving Yarn Outward fetched successfully",
                    weavingYarnOutwardService.getWeavingYarnOutwardByCompany(factoryCompanyId, id));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to fetch Weaving Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Weaving Yarn Outward")
    public ResponseEntity<Object> updateWeavingYarnOutward(@PathVariable Long id, @RequestBody WeavingYarnOutwardDTO dto) {
        try {
            return ResponseUtil.success("Weaving Yarn Outward updated successfully",
                    weavingYarnOutwardService.updateWeavingYarnOutward(id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to update Weaving Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Update Weaving Yarn Outward by company")
    public ResponseEntity<Object> updateWeavingYarnOutwardByCompany(@PathVariable("factoryCompanyId") Long factoryCompanyId,
                                                                    @PathVariable Long id,
                                                                    @RequestBody WeavingYarnOutwardDTO dto) {
        try {
            return ResponseUtil.success("Weaving Yarn Outward updated successfully",
                    weavingYarnOutwardService.updateWeavingYarnOutwardByCompany(factoryCompanyId, id, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to update Weaving Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Weaving Yarn Outward")
    public ResponseEntity<Object> deleteWeavingYarnOutward(@PathVariable Long id) {
        try {
            weavingYarnOutwardService.deleteWeavingYarnOutward(id);
            return ResponseUtil.success("Weaving Yarn Outward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to delete Weaving Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Delete Weaving Yarn Outward by company")
    public ResponseEntity<Object> deleteWeavingYarnOutwardByCompany(@PathVariable("factoryCompanyId") Long factoryCompanyId, @PathVariable Long id) {
        try {
            weavingYarnOutwardService.deleteWeavingYarnOutwardByCompany(factoryCompanyId, id);
            return ResponseUtil.success("Weaving Yarn Outward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("Failed to delete Weaving Yarn Outward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ping")
    public String ping() {
        return "Weaving Yarn Outward API is working!";
    }
}
