package com.tech3.erp.controller;

import com.tech3.erp.dto.EmployeeDTO;
import com.tech3.erp.service.EmployeeService;
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
@RequestMapping("/api/employee")
@Tag(name = "Employee Controller", description = "APIs for managing Employee Master")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @Operation(summary = "Create Employee", description = "Creates a new Employee")
    public ResponseEntity<Object> create(@Valid @RequestBody EmployeeDTO dto) {
        try {
            EmployeeDTO created = employeeService.createEmployee(dto);
            return ResponseUtil.success("Employee created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Employee by ID", description = "Fetch an Employee by its ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            EmployeeDTO employee = employeeService.getEmployeeById(id);
            return ResponseUtil.success("Employee fetched successfully", employee);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List all Employees", description = "Returns all Employees with pagination support")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Employees fetched successfully", employeeService.getAllEmployeesWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/all")
    @Operation(summary = "List all Employees without pagination", description = "Returns all Employees (for dropdowns/suggestions)")
    public ResponseEntity<Object> getAllEmployees() {
        List<EmployeeDTO> list = employeeService.getAllEmployees();
        return ResponseUtil.success("Employees fetched successfully", list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Employee", description = "Updates an existing Employee by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        try {
            EmployeeDTO updated = employeeService.updateEmployee(id, dto);
            return ResponseUtil.success("Employee updated successfully", updated);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee", description = "Deletes an Employee by ID")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseUtil.success("Employee deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Employee", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoints for suggestions
    @GetMapping("/suggestions/names")
    @Operation(summary = "Get Employee Name Suggestions", description = "Returns distinct employee names for suggestions")
    public ResponseEntity<Object> getEmployeeNameSuggestions() {
        List<String> suggestions = employeeService.getEmployeeNameSuggestions();
        return ResponseUtil.success("Employee name suggestions fetched successfully", suggestions);
    }

    @GetMapping("/suggestions/designations")
    @Operation(summary = "Get Designation Suggestions", description = "Returns distinct designations for suggestions")
    public ResponseEntity<Object> getDesignationSuggestions() {
        List<String> suggestions = employeeService.getDesignationSuggestions();
        return ResponseUtil.success("Designation suggestions fetched successfully", suggestions);
    }

    @GetMapping("/suggestions/pf")
    @Operation(summary = "Get PF Suggestions", description = "Returns distinct PF values for suggestions")
    public ResponseEntity<Object> getPfSuggestions() {
        List<String> suggestions = employeeService.getPfSuggestions();
        return ResponseUtil.success("PF suggestions fetched successfully", suggestions);
    }

    @GetMapping("/suggestions/tax")
    @Operation(summary = "Get TAX Suggestions", description = "Returns distinct TAX values for suggestions")
    public ResponseEntity<Object> getTaxSuggestions() {
        List<String> suggestions = employeeService.getTaxSuggestions();
        return ResponseUtil.success("TAX suggestions fetched successfully", suggestions);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk Create Employees", description = "Creates multiple new Employees")
    public ResponseEntity<Object> createBulk(@RequestBody List<EmployeeDTO> dtos) {
        try {
            List<EmployeeDTO> created = employeeService.createEmployeesBulk(dtos);
            return ResponseUtil.success("Employees created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}