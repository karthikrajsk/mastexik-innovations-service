package com.tech3.erp.controller;

import com.tech3.erp.dto.AttendanceDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.AttendanceService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance Controller", description = "APIs for managing Attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @Operation(summary = "Create Attendance", description = "Creates a new Attendance record (check_in or check_out)")
    public ResponseEntity<Object> create(@RequestBody AttendanceDTO dto) {
        try {
            AttendanceDTO created = attendanceService.createAttendance(dto);
            return ResponseUtil.success("Attendance record created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "List all Attendances", description = "Returns all Attendance records with pagination")
    public ResponseEntity<Object> getAll(
            @RequestParam(defaultValue = "0") Long offset,
            @RequestParam(defaultValue = "10") Long limit) {
        PaginatedResponse<AttendanceDTO> response = attendanceService.getAllAttendancesWithPagination(offset, limit);
        return ResponseUtil.success("Attendance records fetched successfully", response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Attendance", description = "Updates an existing Attendance record by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody AttendanceDTO dto) {
        try {
            AttendanceDTO updated = attendanceService.updateAttendance(id, dto);
            return ResponseUtil.success("Attendance record updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Attendance", description = "Deletes an Attendance record by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            attendanceService.deleteAttendance(id);
            return ResponseUtil.success("Attendance record deleted successfully", null);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk Create Attendance", description = "Creates multiple Attendance records")
    public ResponseEntity<Object> createBulk(@RequestBody List<AttendanceDTO> dtos) {
        try {
            List<AttendanceDTO> created = attendanceService.createAttendancesBulk(dtos);
            return ResponseUtil.success("Attendance records created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

