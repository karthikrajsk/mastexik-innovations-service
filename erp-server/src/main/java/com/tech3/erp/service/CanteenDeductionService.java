package com.tech3.erp.service;

import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeeCanteenDeduction;
import com.tech3.erp.repository.EmployeeCanteenDeductionRepository;
import com.tech3.erp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CanteenDeductionService {

    @Autowired
    private EmployeeCanteenDeductionRepository canteenRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeCanteenDeduction> getAllCanteenDeductions() {
        return canteenRepository.findAll();
    }

    public List<EmployeeCanteenDeduction> getCanteenDeductionsByMonth(String month) {
        if (month == null || month.trim().isEmpty()) {
            return canteenRepository.findAll();
        }
        return canteenRepository.findByMonth(month.trim());
    }

    public Optional<EmployeeCanteenDeduction> getCanteenDeductionById(Long id) {
        return canteenRepository.findById(id);
    }

    @Transactional
    public EmployeeCanteenDeduction createCanteenDeduction(EmployeeCanteenDeduction deduction) {
        Long empId = deduction.getEmployee() != null ? deduction.getEmployee().getId() : deduction.getEmployeeId();
        if (empId != null) {
            Employee emp = employeeRepository.findById(empId).orElse(null);
            if (emp != null) {
                deduction.setEmployee(emp);
            }
        }
        // Auto-calculate daily deduction rate if not explicitly provided (assuming ~30 days or default rate)
        if (deduction.getDailyDeductionRate() == null || deduction.getDailyDeductionRate().compareTo(BigDecimal.ZERO) <= 0) {
            if (deduction.getCanteenAmount() != null) {
                deduction.setDailyDeductionRate(deduction.getCanteenAmount().divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP));
            }
        }
        return canteenRepository.save(deduction);
    }

    @Transactional
    public List<EmployeeCanteenDeduction> createBulkCanteenDeductions(List<EmployeeCanteenDeduction> deductions) {
        List<EmployeeCanteenDeduction> savedList = new ArrayList<>();
        for (EmployeeCanteenDeduction item : deductions) {
            Long empId = item.getEmployee() != null ? item.getEmployee().getId() : item.getEmployeeId();
            if (empId != null) {
                Employee emp = employeeRepository.findById(empId).orElse(null);
                if (emp != null) {
                    item.setEmployee(emp);
                }
            }
            if (item.getDailyDeductionRate() == null || item.getDailyDeductionRate().compareTo(BigDecimal.ZERO) <= 0) {
                if (item.getCanteenAmount() != null) {
                    item.setDailyDeductionRate(item.getCanteenAmount().divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP));
                }
            }
            savedList.add(canteenRepository.save(item));
        }
        return savedList;
    }

    @Transactional
    public EmployeeCanteenDeduction updateCanteenDeduction(Long id, EmployeeCanteenDeduction details) {
        EmployeeCanteenDeduction existing = canteenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canteen Deduction record not found with id: " + id));

        Long empId = details.getEmployee() != null ? details.getEmployee().getId() : details.getEmployeeId();
        if (empId != null) {
            Employee emp = employeeRepository.findById(empId).orElse(null);
            if (emp != null) {
                existing.setEmployee(emp);
            }
        }
        existing.setMonth(details.getMonth());
        existing.setEffectiveDate(details.getEffectiveDate());
        existing.setCanteenAmount(details.getCanteenAmount());
        existing.setDailyDeductionRate(details.getDailyDeductionRate());
        existing.setRemarks(details.getRemarks());

        return canteenRepository.save(existing);
    }

    @Transactional
    public void deleteCanteenDeduction(Long id) {
        canteenRepository.deleteById(id);
    }
}
