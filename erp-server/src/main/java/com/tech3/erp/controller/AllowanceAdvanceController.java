package com.tech3.erp.controller;

import com.tech3.erp.dto.EmployeeAllowanceAdvanceDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.AllowanceAdvanceService;
import com.tech3.erp.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/allowance-advance")
@CrossOrigin(origins = "*")
public class AllowanceAdvanceController {

    @Autowired
    private AllowanceAdvanceService allowanceAdvanceService;

    @PostMapping
    public List<EmployeeAllowanceAdvanceDTO> saveAllowancesAdvances(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("month") String month,
            @RequestBody List<EmployeeAllowanceAdvanceDTO> dtos) {
        return allowanceAdvanceService.saveAllowancesAdvances(employeeId, month, dtos);
    }

    @PutMapping
    public List<EmployeeAllowanceAdvanceDTO> updateAllowancesAdvances(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("month") String month,
            @RequestBody List<EmployeeAllowanceAdvanceDTO> dtos) {
        return allowanceAdvanceService.updateAllowancesAdvances(employeeId, month, dtos);
    }

    @GetMapping
    public ResponseEntity<Object> getAllowancesAdvances(
            @RequestParam(value = "employeeId", required = false) Long employeeId,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        
        if (employeeId != null && month != null) {
            // For specific employee/month, return non-paginated list
            List<EmployeeAllowanceAdvanceDTO> result = allowanceAdvanceService.getAllowancesAdvances(employeeId, month);
            return ResponseUtil.success("Allowance/Advance records fetched successfully", result);
        } else {
            // For all records, return paginated response
            return ResponseUtil.success("Allowance/Advance records fetched successfully", allowanceAdvanceService.getAllAllowancesAdvancesWithPagination(finalOffset, finalLimit));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAllowanceAdvance(@PathVariable Long id) {
        allowanceAdvanceService.deleteAllowanceAdvance(id);
    }
}
