package com.tech3.erp.controller;

import com.tech3.erp.dto.FlangeBeamInwardDTO;
import com.tech3.erp.service.FlangeBeamInwardService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flange-inward")
@Tag(name = "Flange Beam Inward Controller", description = "APIs for Flange Beam Inward management")
public class FlangeBeamInwardController {

    private final FlangeBeamInwardService flangeBeamInwardService;

    public FlangeBeamInwardController(FlangeBeamInwardService flangeBeamInwardService) {
        this.flangeBeamInwardService = flangeBeamInwardService;
    }

    @PostMapping
    public ResponseEntity<FlangeBeamInwardDTO> create(@Valid @RequestBody FlangeBeamInwardDTO dto) {
        return ResponseEntity.ok(flangeBeamInwardService.createFlangeBeamInward(dto));
    }

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(flangeBeamInwardService.getAllFlangeBeamInward());
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(flangeBeamInwardService.getAllFlangeBeamInwardWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Object> getAllByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(flangeBeamInwardService.getAllFlangeBeamInwardByCompany(companyId));
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(flangeBeamInwardService.getAllFlangeBeamInwardByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }

    @GetMapping("/factory/{factoryCompanyId}")
    public ResponseEntity<Object> getAllByFactory(
            @PathVariable("factoryCompanyId") Long factoryCompanyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(flangeBeamInwardService.getAllFlangeBeamInwardByFactory(factoryCompanyId));
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(flangeBeamInwardService.getAllFlangeBeamInwardByFactoryWithPagination(factoryCompanyId, finalOffset, finalLimit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlangeBeamInwardDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(flangeBeamInwardService.getFlangeBeamInwardById(id));
    }

    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<FlangeBeamInwardDTO> getByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        return ResponseEntity.ok(flangeBeamInwardService.getFlangeBeamInwardByCompany(companyId, id));
    }

    @GetMapping("/factory/{factoryCompanyId}/{id}")
    public ResponseEntity<FlangeBeamInwardDTO> getByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId, @PathVariable("id") Long id) {
        return ResponseEntity.ok(flangeBeamInwardService.getFlangeBeamInwardByFactory(factoryCompanyId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlangeBeamInwardDTO> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody FlangeBeamInwardDTO dto) {
        return ResponseEntity.ok(flangeBeamInwardService.updateFlangeBeamInward(id, dto));
    }

    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<FlangeBeamInwardDTO> updateByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @Valid @RequestBody FlangeBeamInwardDTO dto) {
        return ResponseEntity.ok(flangeBeamInwardService.updateFlangeBeamInwardByCompany(companyId, id, dto));
    }

    @PutMapping("/factory/{factoryCompanyId}/{id}")
    public ResponseEntity<FlangeBeamInwardDTO> updateByFactory(
            @PathVariable("factoryCompanyId") Long factoryCompanyId,
            @PathVariable("id") Long id,
            @Valid @RequestBody FlangeBeamInwardDTO dto) {
        return ResponseEntity.ok(flangeBeamInwardService.updateFlangeBeamInwardByFactory(factoryCompanyId, id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Flange Beam Inward", description = "Deletes a Flange Beam Inward by ID.")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            flangeBeamInwardService.deleteFlangeBeamInward(id);
            return ResponseUtil.success("Flange Beam Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Flange Beam Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Flange Beam Inward by company", description = "Deletes a Flange Beam Inward by ID within company scope.")
    public ResponseEntity<Object> deleteByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            flangeBeamInwardService.deleteFlangeBeamInwardByCompany(companyId, id);
            return ResponseUtil.success("Flange Beam Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Flange Beam Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/factory/{factoryCompanyId}/{id}")
    @Operation(summary = "Delete Flange Beam Inward by factory", description = "Deletes a Flange Beam Inward by ID within factory company scope.")
    public ResponseEntity<Object> deleteByFactory(@PathVariable("factoryCompanyId") Long factoryCompanyId, @PathVariable("id") Long id) {
        try {
            flangeBeamInwardService.deleteFlangeBeamInwardByFactory(factoryCompanyId, id);
            return ResponseUtil.success("Flange Beam Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Flange Beam Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
