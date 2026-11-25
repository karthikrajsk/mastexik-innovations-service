package com.tech3.erp.controller;

import com.tech3.erp.entity.AttendanceIncentiveSlabMaster;
import com.tech3.erp.service.AttendanceIncentiveSlabMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance-incentive-slabs")
@CrossOrigin(origins = "*")
public class AttendanceIncentiveSlabMasterController {

    @Autowired
    private AttendanceIncentiveSlabMasterService slabMasterService;

    @GetMapping
    public List<AttendanceIncentiveSlabMaster> getAllSlabs(@RequestParam(required = false, defaultValue = "true") Boolean activeOnly) {
        if (Boolean.TRUE.equals(activeOnly)) {
            return slabMasterService.getAllActiveSlabs();
        }
        return slabMasterService.getAllSlabs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttendanceIncentiveSlabMaster> getSlabById(@PathVariable Long id) {
        return slabMasterService.getSlabById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AttendanceIncentiveSlabMaster> createSlab(@RequestBody AttendanceIncentiveSlabMaster slab) {
        return ResponseEntity.ok(slabMasterService.createSlab(slab));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceIncentiveSlabMaster> updateSlab(@PathVariable Long id, @RequestBody AttendanceIncentiveSlabMaster slab) {
        return ResponseEntity.ok(slabMasterService.updateSlab(id, slab));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSlab(@PathVariable Long id) {
        slabMasterService.deleteSlab(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/preview")
    public List<Map<String, Object>> previewIncentives(
            @RequestParam String month,
            @RequestParam(required = false) String designation) {
        return slabMasterService.previewIncentivesForMonth(month, designation);
    }

    @GetMapping("/grouped")
    public List<Map<String, Object>> getSlabsGroupedByMonth(
            @RequestParam(required = false, defaultValue = "true") Boolean activeOnly) {
        return slabMasterService.getSlabsGroupedByMonth(activeOnly);
    }

    @PostMapping("/copy-month")
    public List<AttendanceIncentiveSlabMaster> copyMonthSlabs(
            @RequestParam String sourceMonth,
            @RequestParam String targetMonth,
            @RequestParam(required = false, defaultValue = "ADMIN") String userName) {
        return slabMasterService.copyMonthSlabs(sourceMonth, targetMonth, userName);
    }
}

