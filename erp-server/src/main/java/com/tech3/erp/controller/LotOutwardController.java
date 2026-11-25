package com.tech3.erp.controller;

import com.tech3.erp.dto.OutwardDTO;
import com.tech3.erp.service.LotOutwardService;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/outwards")
@Tag(name = "Outward Controller", description = "APIs for Outward management")
@CrossOrigin(origins = "*")
public class LotOutwardController {

    @Autowired
    private LotOutwardService outwardService;

    // Create new outward entry
    @PostMapping
    public ResponseEntity<OutwardDTO> createOutward(@RequestBody OutwardDTO outwardDTO) {
        OutwardDTO response = outwardService.createOutward(outwardDTO);
        return ResponseEntity.ok(response);
    }

    // Get all outward entries
    @GetMapping
    public ResponseEntity<List<OutwardDTO>> getAllOutwards() {
        List<OutwardDTO> outwards = outwardService.getAllOutwards();
        return ResponseEntity.ok(outwards);
    }

    // Get outward entries by outward type
    @GetMapping("/type/{outwardType}")
    public ResponseEntity<List<OutwardDTO>> getOutwardsByOutwardType(
            @PathVariable String outwardType) {
        List<OutwardDTO> outwards = outwardService.getOutwardsByOutwardType(outwardType);
        return ResponseEntity.ok(outwards);
    }

    // Get outward entries by lot entry ID
    @GetMapping("/lot-entry/{lotEntryId}")
    public ResponseEntity<List<OutwardDTO>> getOutwardsByLotEntry(
            @PathVariable Long lotEntryId) {
        List<OutwardDTO> outwards = outwardService.getOutwardsByLotEntry(lotEntryId);
        return ResponseEntity.ok(outwards);
    }

    // Get outward entries by reference type and reference ID
    @GetMapping("/reference/{referenceType}/{referenceId}")
    public ResponseEntity<List<OutwardDTO>> getOutwardsByReference(
            @PathVariable String referenceType,
            @PathVariable Long referenceId) {
        List<OutwardDTO> outwards = outwardService.getOutwardsByReference(referenceType, referenceId);
        return ResponseEntity.ok(outwards);
    }

    // Get outward entries by reference type, reference ID and outward type
    @GetMapping("/reference/{referenceType}/{referenceId}/type/{outwardType}")
    public ResponseEntity<List<OutwardDTO>> getOutwardsByReferenceAndType(
            @PathVariable String referenceType,
            @PathVariable Long referenceId,
            @PathVariable String outwardType) {
        List<OutwardDTO> outwards = outwardService.getOutwardsByReferenceAndType(referenceType, referenceId, outwardType);
        return ResponseEntity.ok(outwards);
    }

    // Get total outward quantity by lot entry ID
    @GetMapping("/lot-entry/{lotEntryId}/total-quantity")
    public ResponseEntity<BigDecimal> getTotalOutwardQuantity(
            @PathVariable Long lotEntryId) {
        BigDecimal totalQuantity = outwardService.getTotalOutwardQuantityByLotEntry(lotEntryId);
        return ResponseEntity.ok(totalQuantity);
    }

    // Get outward entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<OutwardDTO> getOutwardById(@PathVariable Long id) {
        OutwardDTO outward = outwardService.getOutwardById(id);
        if (outward != null) {
            return ResponseEntity.ok(outward);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update outward entry
    @PutMapping("/{id}")
    public ResponseEntity<OutwardDTO> updateOutward(
            @PathVariable Long id,
            @RequestBody OutwardDTO outwardDTO) {
        OutwardDTO updatedOutward = outwardService.updateOutward(id, outwardDTO);
        if (updatedOutward != null) {
            return ResponseEntity.ok(updatedOutward);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Deactivate outward entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateOutward(@PathVariable Long id) {
        outwardService.deactivateOutward(id);
        return ResponseEntity.noContent().build();
    }

    // Get active outward entries only
    @GetMapping("/active")
    public ResponseEntity<List<OutwardDTO>> getActiveOutwards() {
        List<OutwardDTO> outwards = outwardService.getActiveOutwards();
        return ResponseEntity.ok(outwards);
    }
}