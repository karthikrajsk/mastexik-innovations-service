package com.tech3devs.composite_erp_server.controller;

import com.tech3devs.composite_erp_server.dto.ShiftMasterDTO;
import com.tech3devs.composite_erp_server.service.ShiftMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping({"/api/shifts", "/api/shift", "/shifts", "/shift", "/api/shift-master", "/shift-master"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ShiftMasterController {

    private final ShiftMasterService shiftMasterService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllShifts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String shiftType,
            @RequestParam(required = false) Boolean activeFlag,
            @RequestParam(defaultValue = "10000") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        Map<String, Object> response = shiftMasterService.getAllShifts(search, shiftType, activeFlag, limit, offset);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShiftMasterDTO> getShiftById(@PathVariable Long id) {
        ShiftMasterDTO shift = shiftMasterService.getShiftById(id);
        return ResponseEntity.ok(shift);
    }

    @PostMapping
    public ResponseEntity<ShiftMasterDTO> createShift(@RequestBody ShiftMasterDTO dto) {
        ShiftMasterDTO created = shiftMasterService.createShift(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShiftMasterDTO> updateShift(@PathVariable Long id, @RequestBody ShiftMasterDTO dto) {
        ShiftMasterDTO updated = shiftMasterService.updateShift(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        shiftMasterService.deleteShift(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/status", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<ShiftMasterDTO> toggleStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body
    ) {
        Boolean activeFlag = body != null ? body.get("activeFlag") : null;
        ShiftMasterDTO updated = shiftMasterService.toggleStatus(id, activeFlag);
        return ResponseEntity.ok(updated);
    }
}
