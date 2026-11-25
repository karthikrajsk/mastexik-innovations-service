package com.tech3.erp.controller;

import com.tech3.erp.dto.ShiftMasterDTO;
import com.tech3.erp.service.ShiftMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping({"/api/shifts", "/api/shift", "/shifts", "/shift", "/api/shift-master", "/shift-master"})
@CrossOrigin(origins = "*")
public class ShiftMasterController {

    private final ShiftMasterService shiftMasterService;

    @Autowired
    public ShiftMasterController(ShiftMasterService shiftMasterService) {
        this.shiftMasterService = shiftMasterService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllShifts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String shiftType,
            @RequestParam(required = false) Boolean activeFlag,
            @RequestParam(defaultValue = "10000") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) {
        try {
            Map<String, Object> result = shiftMasterService.getAllShifts(search, shiftType, activeFlag, limit, offset);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("success", true);
            response.put("message", "Shifts fetched successfully");
            response.put("data", result.get("data"));
            response.put("total", result.get("total"));
            response.put("limit", result.get("limit"));
            response.put("offset", result.get("offset"));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage() != null ? e.getMessage() : "Error executing query");
            errorResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShiftById(@PathVariable Long id) {
        try {
            ShiftMasterDTO shift = shiftMasterService.getShiftById(id);
            return ResponseEntity.ok(shift);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PostMapping
    public ResponseEntity<?> createShift(@RequestBody ShiftMasterDTO dto) {
        try {
            ShiftMasterDTO created = shiftMasterService.createShift(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShift(@PathVariable Long id, @RequestBody ShiftMasterDTO dto) {
        try {
            ShiftMasterDTO updated = shiftMasterService.updateShift(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShift(@PathVariable Long id) {
        try {
            shiftMasterService.deleteShift(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @RequestMapping(value = "/{id}/status", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<?> toggleStatus(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, Boolean> body
    ) {
        try {
            Boolean activeFlag = body != null ? body.get("activeFlag") : null;
            ShiftMasterDTO updated = shiftMasterService.toggleStatus(id, activeFlag);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
