package com.tech3.erp.controller;

import com.tech3.erp.entity.EmployeeProductionIncentive;
import com.tech3.erp.service.ProductionIncentiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-incentive")
@CrossOrigin(origins = "*")
public class ProductionIncentiveController {

    @Autowired
    private ProductionIncentiveService incentiveService;

    @GetMapping
    public List<EmployeeProductionIncentive> getIncentives(@RequestParam(required = false) String month) {
        return incentiveService.getIncentivesByMonth(month);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeProductionIncentive> getIncentiveById(@PathVariable Long id) {
        return incentiveService.getIncentiveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployeeProductionIncentive createIncentive(@RequestBody EmployeeProductionIncentive incentive) {
        return incentiveService.createIncentive(incentive);
    }

    @PostMapping("/bulk")
    public List<EmployeeProductionIncentive> createBulkIncentives(@RequestBody List<EmployeeProductionIncentive> incentives) {
        return incentiveService.createBulkIncentives(incentives);
    }

    @PutMapping("/{id}")
    public EmployeeProductionIncentive updateIncentive(@PathVariable Long id, @RequestBody EmployeeProductionIncentive incentive) {
        return incentiveService.updateIncentive(id, incentive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncentive(@PathVariable Long id) {
        incentiveService.deleteIncentive(id);
        return ResponseEntity.noContent().build();
    }
}
