package com.tech3.erp.controller;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SizingYarnInwardDTO;
import com.tech3.erp.service.SizingYarnInwardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/sizing-yarn-inward")
@Tag(name = "Sizing Yarn Inward", description = "APIs for Sizing Yarn Inward management")
public class SizingYarnInwardController {

    private final SizingYarnInwardService sizingYarnInwardService;

    public SizingYarnInwardController(SizingYarnInwardService sizingYarnInwardService) {
        this.sizingYarnInwardService = sizingYarnInwardService;
    }

    @GetMapping
    @Operation(summary = "Get all sizing yarn inward entries")
    public ResponseEntity<List<SizingYarnInwardDTO>> getAllSizingYarnInwards() {
        return ResponseEntity.ok(sizingYarnInwardService.getAllSizingYarnInwards());
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get paginated sizing yarn inward entries")
    public ResponseEntity<PaginatedResponse<SizingYarnInwardDTO>> getAllSizingYarnInwardsWithPagination(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(sizingYarnInwardService.getAllSizingYarnInwardsWithPagination(offset, limit));
    }

    @GetMapping("/factory/{factoryCompanyId}")
    @Operation(summary = "Get sizing yarn inward entries by factory")
    public ResponseEntity<List<SizingYarnInwardDTO>> getSizingYarnInwardsByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId) {
        return ResponseEntity.ok(sizingYarnInwardService.getSizingYarnInwardsByFactory(factoryCompanyId));
    }

    @GetMapping("/factory/{factoryCompanyId}/paginated")
    @Operation(summary = "Get paginated sizing yarn inward entries by factory")
    public ResponseEntity<PaginatedResponse<SizingYarnInwardDTO>> getSizingYarnInwardsByFactoryWithPagination(
            @PathVariable("factoryCompanyId") Long factoryCompanyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(sizingYarnInwardService.getSizingYarnInwardsByFactoryWithPagination(factoryCompanyId, offset, limit));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sizing yarn inward entry by id")
    public ResponseEntity<SizingYarnInwardDTO> getSizingYarnInwardById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sizingYarnInwardService.getSizingYarnInwardById(id));
    }

    @GetMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Get sizing yarn inward entry by factory and id")
    public ResponseEntity<SizingYarnInwardDTO> getSizingYarnInwardByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(sizingYarnInwardService.getSizingYarnInwardByFactory(factoryCompanyId, id));
    }

    @PostMapping
    @Operation(summary = "Create sizing yarn inward entry")
    public ResponseEntity<SizingYarnInwardDTO> createSizingYarnInward(@RequestBody SizingYarnInwardDTO dto) {
        return ResponseEntity.ok(sizingYarnInwardService.createSizingYarnInward(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update sizing yarn inward entry")
    public ResponseEntity<SizingYarnInwardDTO> updateSizingYarnInward(@PathVariable("id") Long id,
            @RequestBody SizingYarnInwardDTO dto) {
        return ResponseEntity.ok(sizingYarnInwardService.updateSizingYarnInward(id, dto));
    }

    @PutMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Update sizing yarn inward entry by factory")
    public ResponseEntity<SizingYarnInwardDTO> updateSizingYarnInwardByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId,
            @PathVariable("id") Long id, @RequestBody SizingYarnInwardDTO dto) {
        return ResponseEntity.ok(sizingYarnInwardService.updateSizingYarnInwardByFactory(factoryCompanyId, id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete sizing yarn inward entry")
    public ResponseEntity<Void> deleteSizingYarnInward(@PathVariable("id") Long id) {
        sizingYarnInwardService.deleteSizingYarnInward(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Delete sizing yarn inward entry by factory")
    public ResponseEntity<Void> deleteSizingYarnInwardByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId,
            @PathVariable("id") Long id) {
        sizingYarnInwardService.deleteSizingYarnInwardByFactory(factoryCompanyId, id);
        return ResponseEntity.noContent().build();
    }
}
