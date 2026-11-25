package com.tech3.erp.controller;

import com.tech3.erp.entity.EmployeeCanteenDeduction;
import com.tech3.erp.service.CanteenDeductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canteen-deduction")
@CrossOrigin(origins = "*")
public class CanteenDeductionController {

    @Autowired
    private CanteenDeductionService canteenService;

    @GetMapping
    public List<EmployeeCanteenDeduction> getCanteenDeductions(@RequestParam(required = false) String month) {
        return canteenService.getCanteenDeductionsByMonth(month);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeCanteenDeduction> getCanteenDeductionById(@PathVariable Long id) {
        return canteenService.getCanteenDeductionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployeeCanteenDeduction createCanteenDeduction(@RequestBody EmployeeCanteenDeduction deduction) {
        return canteenService.createCanteenDeduction(deduction);
    }

    @PostMapping("/bulk")
    public List<EmployeeCanteenDeduction> createBulkCanteenDeductions(@RequestBody List<EmployeeCanteenDeduction> deductions) {
        return canteenService.createBulkCanteenDeductions(deductions);
    }

    @PutMapping("/{id}")
    public EmployeeCanteenDeduction updateCanteenDeduction(@PathVariable Long id, @RequestBody EmployeeCanteenDeduction deduction) {
        return canteenService.updateCanteenDeduction(id, deduction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCanteenDeduction(@PathVariable Long id) {
        canteenService.deleteCanteenDeduction(id);
        return ResponseEntity.noContent().build();
    }
}
