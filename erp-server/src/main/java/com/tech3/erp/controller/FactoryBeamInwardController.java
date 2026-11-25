package com.tech3.erp.controller;

import com.tech3.erp.dto.FactoryBeamInwardDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.FactoryBeamInwardService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/factory-beam-inward")
@Tag(name = "Factory Beam Inward Controller", description = "APIs for Factory Beam Inward management")
public class FactoryBeamInwardController {

    private final FactoryBeamInwardService beamInwardService;

    public FactoryBeamInwardController(FactoryBeamInwardService beamInwardService) {
        this.beamInwardService = beamInwardService;
    }

    @PostMapping
    public ResponseEntity<FactoryBeamInwardDTO> create(@Valid @RequestBody FactoryBeamInwardDTO dto) {
        FactoryBeamInwardDTO created = beamInwardService.saveBeamInward(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<FactoryBeamInwardDTO>> getAll(
            @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(beamInwardService.getAllBeamInwardWithPagination(offset, limit, factoryCompanyId));
    }

    @GetMapping("/factory/{factoryCompanyId}")
    public ResponseEntity<PaginatedResponse<FactoryBeamInwardDTO>> getByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId,
                                                                                @RequestParam(value = "offset", required = false) Long offset,
                                                                                @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(beamInwardService.getByFactoryCompanyIdWithPagination(factoryCompanyId, offset, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactoryBeamInwardDTO> getById(@PathVariable Long id) {
        FactoryBeamInwardDTO plan = beamInwardService.getBeamInwardById(id);
        return ResponseEntity.ok(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FactoryBeamInwardDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody FactoryBeamInwardDTO dto,
                                                       @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId) {
        FactoryBeamInwardDTO updated = beamInwardService.updateBeamInward(id, dto, factoryCompanyId);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/factory/{factoryCompanyId}/{id}")
    public ResponseEntity<FactoryBeamInwardDTO> updateByFactory(@PathVariable Long factoryCompanyId,
                                                                @PathVariable Long id,
                                                                @Valid @RequestBody FactoryBeamInwardDTO dto) {
        FactoryBeamInwardDTO updated = beamInwardService.updateBeamInward(id, dto, factoryCompanyId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Factory Beam Inward", description = "Deletes a Factory Beam Inward by ID.")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         @RequestParam(value = "factoryCompanyId", required = false) Long factoryCompanyId) {
        try {
            if (factoryCompanyId != null) {
                beamInwardService.deleteBeamInward(id, factoryCompanyId);
            } else {
                beamInwardService.deleteBeamInward(id);
            }
            return ResponseUtil.success("Factory Beam Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Factory Beam Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Delete Factory Beam Inward by Factory", description = "Deletes a Factory Beam Inward by ID and factoryCompanyId.")
    public ResponseEntity<Object> deleteByFactory(@PathVariable Long factoryCompanyId,
                                                  @PathVariable Long id) {
        try {
            beamInwardService.deleteBeamInward(id, factoryCompanyId);
            return ResponseUtil.success("Factory Beam Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Factory Beam Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
