package com.tech3.erp.controller;

import com.tech3.erp.dto.EmployeeLoanSkipRequestDTO;
import com.tech3.erp.service.EmployeeLoanSkipRequestService;
import com.tech3.erp.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-loan/skip-request")
@CrossOrigin(origins = "*")
public class EmployeeLoanSkipRequestController {

    @Autowired
    private EmployeeLoanSkipRequestService skipRequestService;

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestParam("loanId") Long loanId) {
        List<EmployeeLoanSkipRequestDTO> result = skipRequestService.getRequests(loanId);
        return ResponseUtil.success("Skip requests fetched successfully", result);
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestBody EmployeeLoanSkipRequestDTO dto) {
        EmployeeLoanSkipRequestDTO result = skipRequestService.createRequest(dto);
        return ResponseUtil.success("Skip request submitted successfully", result);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Object> updateStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") String status) {
        EmployeeLoanSkipRequestDTO result = skipRequestService.updateStatus(id, status);
        return ResponseUtil.success("Skip request status updated successfully", result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRequest(@PathVariable("id") Long id) {
        skipRequestService.deleteRequest(id);
        return ResponseUtil.success("Skip request deleted successfully", null);
    }
}
