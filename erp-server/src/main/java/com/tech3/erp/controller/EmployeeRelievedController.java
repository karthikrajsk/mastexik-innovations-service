package com.tech3.erp.controller;

import com.tech3.erp.dto.EmployeeRelievedDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.EmployeeRelievedService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-relieved")
@Tag(name = "Employee Relieved Controller", description = "APIs for managing Employee Relieved records")
public class EmployeeRelievedController {

    private final EmployeeRelievedService employeeRelievedService;

    public EmployeeRelievedController(EmployeeRelievedService employeeRelievedService) {
        this.employeeRelievedService = employeeRelievedService;
    }

    @PostMapping
    @Operation(summary = "Create Employee Relieved", description = "Creates a new Employee Relieved record")
    public ResponseEntity<Object> create(@Valid @RequestBody EmployeeRelievedDTO dto) {
        try {
            EmployeeRelievedDTO created = employeeRelievedService.createEmployeeRelieved(dto);
            return ResponseUtil.success("Employee Relieved created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Employee Relieved by ID", description = "Fetch an Employee Relieved record by its ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            EmployeeRelievedDTO employeeRelieved = employeeRelievedService.getEmployeeRelievedById(id);
            return ResponseUtil.success("Employee Relieved fetched successfully", employeeRelieved);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get Employee Relieved by Employee ID", description = "Fetch an Employee Relieved record by Employee ID")
    public ResponseEntity<Object> getByEmployeeId(@PathVariable Long employeeId) {
        try {
            EmployeeRelievedDTO employeeRelieved = employeeRelievedService.getEmployeeRelievedByEmployeeId(employeeId);
            return ResponseUtil.success("Employee Relieved fetched successfully", employeeRelieved);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List all Employee Relieved", description = "Returns all Employee Relieved records with pagination")
    public ResponseEntity<Object> getAll(@RequestParam(required = false) Long offset, 
                                        @RequestParam(required = false) Long limit) {
        PaginatedResponse<EmployeeRelievedDTO> employeeRelievedList = employeeRelievedService.getAllEmployeeRelieved(offset, limit);
        return ResponseUtil.success("Employee Relieved records fetched successfully", employeeRelievedList);
    }

    @GetMapping("/status/{isRelieved}")
    @Operation(summary = "Get Employee Relieved by Status", description = "Fetch Employee Relieved records by relieving status")
    public ResponseEntity<Object> getByStatus(@PathVariable Integer isRelieved) {
        List<EmployeeRelievedDTO> employeeRelievedList = employeeRelievedService.getEmployeeRelievedByStatus(isRelieved);
        return ResponseUtil.success("Employee Relieved records fetched successfully", employeeRelievedList);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Employee Relieved", description = "Updates an existing Employee Relieved record")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody EmployeeRelievedDTO dto) {
        try {
            EmployeeRelievedDTO updated = employeeRelievedService.updateEmployeeRelieved(id, dto);
            return ResponseUtil.success("Employee Relieved updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/relieve")
    @Operation(summary = "Relieve Employee", description = "Marks an employee as relieved")
    public ResponseEntity<Object> relieveEmployee(@PathVariable Long id) {
        try {
            EmployeeRelievedDTO updated = employeeRelievedService.relieveEmployee(id);
            return ResponseUtil.success("Employee relieved successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee Relieved", description = "Deletes an Employee Relieved record")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            employeeRelievedService.deleteEmployeeRelieved(id);
            return ResponseUtil.success("Employee Relieved deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search Employee Relieved", description = "Search Employee Relieved records by reason or remarks")
    public ResponseEntity<Object> search(@RequestParam String searchTerm) {
        List<EmployeeRelievedDTO> employeeRelievedList = employeeRelievedService.searchEmployeeRelieved(searchTerm);
        return ResponseUtil.success("Employee Relieved records fetched successfully", employeeRelievedList);
    }
}
