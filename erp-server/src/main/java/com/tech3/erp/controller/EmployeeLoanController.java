package com.tech3.erp.controller;

import com.tech3.erp.dto.EmployeeLoanDTO;
import com.tech3.erp.service.EmployeeLoanService;
import com.tech3.erp.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/employee-loan")
@CrossOrigin(origins = "*")
public class EmployeeLoanController {

    @Autowired
    private EmployeeLoanService employeeLoanService;

    @GetMapping
    public ResponseEntity<Object> getLoans(
            @RequestParam(value = "employeeId", required = false) Long employeeId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;

        if (employeeId != null) {
            List<EmployeeLoanDTO> result = employeeLoanService.getLoans(employeeId);
            return ResponseUtil.success("Loans fetched successfully", result);
        } else {
            return ResponseUtil.success("Loans fetched successfully", employeeLoanService.getLoansWithPagination(finalOffset, finalLimit));
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveLoans(
            @RequestParam("employeeId") Long employeeId,
            @RequestBody List<EmployeeLoanDTO> dtos) {
        List<EmployeeLoanDTO> result = employeeLoanService.saveLoans(employeeId, dtos);
        return ResponseUtil.success("Loans saved successfully", result);
    }

    @PutMapping
    public ResponseEntity<Object> updateLoans(
            @RequestParam("employeeId") Long employeeId,
            @RequestBody List<EmployeeLoanDTO> dtos) {
        List<EmployeeLoanDTO> result = employeeLoanService.updateLoans(employeeId, dtos);
        return ResponseUtil.success("Loans updated successfully", result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLoan(@PathVariable Long id) {
        employeeLoanService.deleteLoan(id);
        return ResponseUtil.success("Loan deleted successfully", null);
    }
}
