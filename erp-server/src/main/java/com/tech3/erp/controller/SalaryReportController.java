// salary report controller


package com.tech3.erp.controller;

import com.tech3.erp.dto.SalaryReportDTO;
import com.tech3.erp.dto.EmployeeAllowanceAdvanceDTO;
import com.tech3.erp.entity.SalaryReportOverride;
import com.tech3.erp.service.SalaryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salary-report")
@CrossOrigin(origins = "*")
public class SalaryReportController {

    @Autowired
    private SalaryReportService salaryReportService;

    @GetMapping
    public List<SalaryReportDTO> getSalaryReport(
            @RequestParam(required = false) String month,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String date) {
        if (date != null && !date.trim().isEmpty()) {
            return salaryReportService.getSalaryReport(date.trim(), date.trim());
        }
        if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
            return salaryReportService.getSalaryReport(fromDate.trim(), toDate.trim());
        }
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            return salaryReportService.getSalaryReport(fromDate.trim(), fromDate.trim());
        }
        if (toDate != null && !toDate.trim().isEmpty()) {
            return salaryReportService.getSalaryReport(toDate.trim(), toDate.trim());
        }
        if (month == null || month.trim().isEmpty()) {
            return new java.util.ArrayList<>();
        }
        return salaryReportService.getSalaryReport(month);
    }

    @GetMapping("/exists")
    public boolean checkSalaryReportExists(@RequestParam Long employeeId, @RequestParam Integer month, @RequestParam Integer year) {
        return salaryReportService.existsByEmployeeIdAndMonthAndYear(employeeId, month, year);
    }

    @PostMapping
    public SalaryReportOverride createSalaryReportOverride(@RequestBody SalaryReportOverride override) {
        return salaryReportService.createSalaryReportOverride(override);
    }

    @PostMapping("/bulk")
    public List<SalaryReportOverride> bulkCreateSalaryReportOverrides(@RequestBody List<SalaryReportOverride> overrides) {
        return salaryReportService.bulkCreateSalaryReportOverrides(overrides);
    }

    @PutMapping
    public SalaryReportOverride updateSalaryReportOverride(@RequestBody SalaryReportOverride override) {
        return salaryReportService.updateSalaryReportOverride(override);
    }

    @PutMapping("/bulk")
    public List<SalaryReportOverride> bulkUpdateSalaryReportOverrides(@RequestBody List<SalaryReportOverride> overrides) {
        return salaryReportService.bulkUpdateSalaryReportOverrides(overrides);
    }

    @PostMapping("/bulk-upsert")
    public List<SalaryReportOverride> bulkUpsertSalaryReportOverrides(@RequestBody List<SalaryReportOverride> overrides) {
        try {
            return salaryReportService.bulkUpsertSalaryReportOverrides(overrides);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Validation error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to process salary reports: " + e.getMessage(), e);
        }
    }

    @PatchMapping
    public SalaryReportOverride upsertSalaryReportOverride(@RequestBody SalaryReportOverride override) {
        return salaryReportService.upsertSalaryReportOverride(override);
    }

//   @PostMapping("/allowance-advance")
//   public List<EmployeeAllowanceAdvanceDTO> saveAllowancesAdvances(
//           @RequestParam Long employeeId,
//           @RequestParam String month,
//           @RequestBody List<EmployeeAllowanceAdvanceDTO> dtos) {
//       return salaryReportService.saveAllowancesAdvances(employeeId, month, dtos);
//   }

    @GetMapping("/allowance-advance")
    public List<EmployeeAllowanceAdvanceDTO> getAllowancesAdvances(@RequestParam Long employeeId, @RequestParam String month) {
        return salaryReportService.getAllowancesAdvances(employeeId, month);
    }
}
