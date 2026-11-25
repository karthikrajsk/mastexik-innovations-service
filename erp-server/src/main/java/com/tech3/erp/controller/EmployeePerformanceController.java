package com.tech3.erp.controller;

import com.tech3.erp.dto.EmployeeInsuranceDTO;
import com.tech3.erp.dto.EmployeePerformanceDTO;
import com.tech3.erp.dto.EmployeePromotionDTO;
import com.tech3.erp.service.EmployeeInsuranceService;
import com.tech3.erp.service.EmployeePerformanceService;
import com.tech3.erp.service.EmployeePromotionService;
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
@Tag(name = "Employee Performance Controller", description = "APIs for Employee Promotions, Performance Reviews, and Insurance")
public class EmployeePerformanceController {

    private final EmployeePromotionService promotionService;
    private final EmployeePerformanceService performanceService;
    private final EmployeeInsuranceService insuranceService;

    public EmployeePerformanceController(EmployeePromotionService promotionService, 
                                       EmployeePerformanceService performanceService,
                                       EmployeeInsuranceService insuranceService) {
        this.promotionService = promotionService;
        this.performanceService = performanceService;
        this.insuranceService = insuranceService;
    }

    // Promotions
    @GetMapping("/promotions")
    @Operation(summary = "Get all Promotions", description = "Returns all Employee Promotions")
    public ResponseEntity<Object> getAllPromotions() {
        List<EmployeePromotionDTO> list = promotionService.getAllPromotions();
        return ResponseUtil.success("Promotions fetched successfully", list);
    }

    @PostMapping("/promotions")
    @Operation(summary = "Create Promotion", description = "Creates a new Employee Promotion")
    public ResponseEntity<Object> createPromotion(@Valid @RequestBody EmployeePromotionDTO dto) {
        try {
            EmployeePromotionDTO created = promotionService.createPromotion(dto);
            return ResponseUtil.success("Promotion created successfully", created);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/promotions/{id}")
    @Operation(summary = "Update Promotion", description = "Updates an existing Employee Promotion")
    public ResponseEntity<Object> updatePromotion(@PathVariable Long id, @Valid @RequestBody EmployeePromotionDTO dto) {
        try {
            EmployeePromotionDTO updated = promotionService.updatePromotion(id, dto);
            return ResponseUtil.success("Promotion updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/promotions/{id}")
    @Operation(summary = "Delete Promotion", description = "Deletes an Employee Promotion by ID")
    public ResponseEntity<Object> deletePromotion(@PathVariable Long id) {
        try {
            promotionService.deletePromotion(id);
            return ResponseUtil.success("Promotion deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Performance Reviews
    @GetMapping("/performance")
    @Operation(summary = "Get all Performance Reviews", description = "Returns all Employee Performance Reviews")
    public ResponseEntity<Object> getAllPerformance() {
        List<EmployeePerformanceDTO> list = performanceService.getAllPerformance();
        return ResponseUtil.success("Performance reviews fetched successfully", list);
    }

    @PostMapping("/performance")
    @Operation(summary = "Create Performance Review", description = "Creates a new Employee Performance Review")
    public ResponseEntity<Object> createPerformance(@Valid @RequestBody EmployeePerformanceDTO dto) {
        try {
            EmployeePerformanceDTO created = performanceService.createPerformance(dto);
            return ResponseUtil.success("Performance review created successfully", created);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/performance/{id}")
    @Operation(summary = "Update Performance Review", description = "Updates an existing Employee Performance Review")
    public ResponseEntity<Object> updatePerformance(@PathVariable Long id, @Valid @RequestBody EmployeePerformanceDTO dto) {
        try {
            EmployeePerformanceDTO updated = performanceService.updatePerformance(id, dto);
            return ResponseUtil.success("Performance review updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/performance/{id}")
    @Operation(summary = "Delete Performance Review", description = "Deletes an Employee Performance Review by ID")
    public ResponseEntity<Object> deletePerformance(@PathVariable Long id) {
        try {
            performanceService.deletePerformance(id);
            return ResponseUtil.success("Performance review deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Insurance Policies
    @GetMapping("/insurance")
    @Operation(summary = "Get all Insurance Policies", description = "Returns all Employee Insurance Policies")
    public ResponseEntity<Object> getAllInsurance() {
        List<EmployeeInsuranceDTO> list = insuranceService.getAllInsurance();
        return ResponseUtil.success("Insurance policies fetched successfully", list);
    }

    @PostMapping("/insurance")
    @Operation(summary = "Create Insurance Policy", description = "Creates a new Employee Insurance Policy")
    public ResponseEntity<Object> createInsurance(@Valid @RequestBody EmployeeInsuranceDTO dto) {
        try {
            EmployeeInsuranceDTO created = insuranceService.createInsurance(dto);
            return ResponseUtil.success("Insurance policy created successfully", created);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/insurance/{id}")
    @Operation(summary = "Update Insurance Policy", description = "Updates an existing Employee Insurance Policy")
    public ResponseEntity<Object> updateInsurance(@PathVariable Long id, @Valid @RequestBody EmployeeInsuranceDTO dto) {
        try {
            EmployeeInsuranceDTO updated = insuranceService.updateInsurance(id, dto);
            return ResponseUtil.success("Insurance policy updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/insurance/{id}")
    @Operation(summary = "Delete Insurance Policy", description = "Deletes an Employee Insurance Policy by ID")
    public ResponseEntity<Object> deleteInsurance(@PathVariable Long id) {
        try {
            insuranceService.deleteInsurance(id);
            return ResponseUtil.success("Insurance policy deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
