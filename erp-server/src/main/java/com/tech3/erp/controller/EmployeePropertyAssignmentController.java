package com.tech3.erp.controller;

import com.tech3.erp.entity.EmployeePropertyAssignment;
import com.tech3.erp.service.EmployeePropertyAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employee-property-assignment")
@CrossOrigin(origins = "*")
public class EmployeePropertyAssignmentController {

    @Autowired
    private EmployeePropertyAssignmentService assignmentService;

    @GetMapping
    public List<EmployeePropertyAssignment> getAssignments(
            @RequestParam(required = false) Long companyId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Long employeeId) {
        return assignmentService.getAllAssignments(companyId, status, department, employeeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeePropertyAssignment> getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EmployeePropertyAssignment createAssignment(@RequestBody EmployeePropertyAssignment assignment) {
        return assignmentService.createAssignment(assignment);
    }

    @PostMapping("/bulk")
    public List<EmployeePropertyAssignment> createBulkAssignments(@RequestBody List<EmployeePropertyAssignment> assignments) {
        return assignmentService.bulkCreateAssignments(assignments);
    }

    @PutMapping("/{id}")
    public EmployeePropertyAssignment updateAssignment(@PathVariable Long id, @RequestBody EmployeePropertyAssignment assignment) {
        return assignmentService.updateAssignment(id, assignment);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<EmployeePropertyAssignment> markReturned(
            @PathVariable Long id,
            @RequestBody Map<String, Object> returnPayload) {
        LocalDate returnDate = LocalDate.now();
        if (returnPayload.containsKey("returnDate") && returnPayload.get("returnDate") != null) {
            returnDate = LocalDate.parse(String.valueOf(returnPayload.get("returnDate")));
        }
        String condition = (String) returnPayload.get("conditionAtReturn");
        String receivedBy = (String) returnPayload.get("receivedBy");
        String remarks = (String) returnPayload.get("remarks");
        BigDecimal deduction = null;
        if (returnPayload.containsKey("damageDeductionAmount") && returnPayload.get("damageDeductionAmount") != null) {
            deduction = new BigDecimal(String.valueOf(returnPayload.get("damageDeductionAmount")));
        }

        EmployeePropertyAssignment updated = assignmentService.markReturned(
                id, returnDate, condition, receivedBy, deduction, remarks);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
