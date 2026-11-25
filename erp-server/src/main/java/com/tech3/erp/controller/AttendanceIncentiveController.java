package com.tech3.erp.controller;

import com.tech3.erp.entity.EmployeeAttendanceIncentive;
import com.tech3.erp.service.AttendanceIncentiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance-incentive")
@CrossOrigin(origins = "*")
public class AttendanceIncentiveController {

    @Autowired
    private AttendanceIncentiveService incentiveService;

    @Autowired
    private com.tech3.erp.service.AttendanceIncentiveSlabMasterService slabMasterService;

    @GetMapping
    public List<EmployeeAttendanceIncentive> getIncentives(@RequestParam(required = false) String month) {
        return incentiveService.getIncentivesByMonth(month);
    }

    @GetMapping("/preview")
    public List<java.util.Map<String, Object>> previewIncentives(
            @RequestParam String month,
            @RequestParam(required = false) String designation) {
        return slabMasterService.previewIncentivesForMonth(month, designation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeAttendanceIncentive> getIncentiveById(@PathVariable Long id) {
        return incentiveService.getIncentiveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployeeAttendanceIncentive createIncentive(@RequestBody EmployeeAttendanceIncentive incentive) {
        return incentiveService.createIncentive(incentive);
    }

    @PostMapping("/bulk")
    public List<EmployeeAttendanceIncentive> createBulkIncentives(@RequestBody List<EmployeeAttendanceIncentive> incentives) {
        return incentiveService.createBulkIncentives(incentives);
    }

    @PutMapping("/{id}")
    public EmployeeAttendanceIncentive updateIncentive(@PathVariable Long id, @RequestBody EmployeeAttendanceIncentive incentive) {
        return incentiveService.updateIncentive(id, incentive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncentive(@PathVariable Long id) {
        incentiveService.deleteIncentive(id);
        return ResponseEntity.noContent().build();
    }
}
