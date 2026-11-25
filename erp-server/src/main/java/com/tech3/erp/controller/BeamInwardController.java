package com.tech3.erp.controller;

import com.tech3.erp.dto.BeamInwardDTO;
import com.tech3.erp.service.BeamInwardService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/beam-inward")
@Tag(name = "Beam Inward Controller", description = "APIs for Beam Inward management")

public class BeamInwardController {

	private final BeamInwardService beamInwardService;

    public BeamInwardController(BeamInwardService beamInwardService) {
        this.beamInwardService = beamInwardService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<BeamInwardDTO> createBeamInward(@Valid @RequestBody BeamInwardDTO beamInwardDTO) {
    	BeamInwardDTO createdPlan = beamInwardService.saveBeamInward(beamInwardDTO);
        return ResponseEntity.ok(createdPlan);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBeamInward(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(beamInwardService.getAllBeamInward());
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(beamInwardService.getAllBeamInwardWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Object> getAllBeamInwardByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(beamInwardService.getAllBeamInwardByCompany(companyId));
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(beamInwardService.getAllBeamInwardByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BeamInwardDTO> getBeamInwardById(@PathVariable("id") Long id) {
    	BeamInwardDTO plan = beamInwardService.getBeamInwardById(id);
        return ResponseEntity.ok(plan);
    }
    
    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<BeamInwardDTO> getBeamInwardByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        BeamInwardDTO plan = beamInwardService.getBeamInwardByCompany(companyId, id);
        return ResponseEntity.ok(plan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeamInwardDTO> updateBeamInward(
            @PathVariable("id") Long id,
            @Valid @RequestBody BeamInwardDTO BeamInwardDTO) {
    	BeamInwardDTO updatedPlan = beamInwardService.updateBeamInward(id, BeamInwardDTO);
        return ResponseEntity.ok(updatedPlan);
    }
    
    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<BeamInwardDTO> updateBeamInwardByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @Valid @RequestBody BeamInwardDTO BeamInwardDTO) {
        BeamInwardDTO updatedPlan = beamInwardService.updateBeamInwardByCompany(companyId, id, BeamInwardDTO);
        return ResponseEntity.ok(updatedPlan);
    }

    // DELETE
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteSizingPlan(@PathVariable Long id) {
//        beamInwardService.deleteSizingPlan(id);
//        return ResponseEntity.noContent().build();
//    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Beam Inward", description = "Deletes a Beam Inward by ID.")
    public ResponseEntity<Object> deleteBeamInward(@PathVariable("id") Long id) {
        try {
        	beamInwardService.deleteBeamInward(id);
            return ResponseUtil.success("Beam Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Beam Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Beam Inward by company", description = "Deletes a Beam Inward by ID within company scope.")
    public ResponseEntity<Object> deleteBeamInwardByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            beamInwardService.deleteBeamInwardByCompany(companyId, id);
            return ResponseUtil.success("Beam Inward deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Beam Inward", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-beam/{beamNo}")
    @Operation(summary = "Get Beam Inward by Beam Number", description = "Retrieves Beam Inward records matching the given sized beam number")
    public ResponseEntity<Object> getBeamInwardByBeamNo(@PathVariable("beamNo") String beamNo) {
        try {
            String decodedBeamNo = java.net.URLDecoder.decode(beamNo, java.nio.charset.StandardCharsets.UTF_8);
            return ResponseEntity.ok(beamInwardService.getBeamInwardsByBeamNo(decodedBeamNo));
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while fetching Beam Inward: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}