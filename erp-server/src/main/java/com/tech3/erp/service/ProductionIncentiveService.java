package com.tech3.erp.service;

import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeeProductionIncentive;
import com.tech3.erp.repository.EmployeeProductionIncentiveRepository;
import com.tech3.erp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductionIncentiveService {

    @Autowired
    private EmployeeProductionIncentiveRepository incentiveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeProductionIncentive> getAllIncentives() {
        return incentiveRepository.findAll();
    }

    public List<EmployeeProductionIncentive> getIncentivesByMonth(String month) {
        if (month == null || month.trim().isEmpty()) {
            return incentiveRepository.findAll();
        }
        return incentiveRepository.findByMonth(month.trim());
    }

    public Optional<EmployeeProductionIncentive> getIncentiveById(Long id) {
        return incentiveRepository.findById(id);
    }

    @Transactional
    public EmployeeProductionIncentive createIncentive(EmployeeProductionIncentive incentive) {
        Long empId = incentive.getEmployee() != null ? incentive.getEmployee().getId() : incentive.getEmployeeId();
        if (empId != null) {
            Employee emp = employeeRepository.findById(empId).orElse(null);
            if (emp != null) {
                incentive.setEmployee(emp);
            }
        }
        return incentiveRepository.save(incentive);
    }

    @Transactional
    public List<EmployeeProductionIncentive> createBulkIncentives(List<EmployeeProductionIncentive> incentives) {
        List<EmployeeProductionIncentive> savedList = new ArrayList<>();
        for (EmployeeProductionIncentive item : incentives) {
            Long empId = item.getEmployee() != null ? item.getEmployee().getId() : item.getEmployeeId();
            if (empId != null) {
                Employee emp = employeeRepository.findById(empId).orElse(null);
                if (emp != null) {
                    item.setEmployee(emp);
                }
            }
            savedList.add(incentiveRepository.save(item));
        }
        return savedList;
    }

    @Transactional
    public EmployeeProductionIncentive updateIncentive(Long id, EmployeeProductionIncentive details) {
        EmployeeProductionIncentive existing = incentiveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production Incentive record not found with id: " + id));

        Long empId = details.getEmployee() != null ? details.getEmployee().getId() : details.getEmployeeId();
        if (empId != null) {
            Employee emp = employeeRepository.findById(empId).orElse(null);
            if (emp != null) {
                existing.setEmployee(emp);
            }
        }
        existing.setMonth(details.getMonth());
        existing.setIncentiveDate(details.getIncentiveDate());
        existing.setIncentiveType(details.getIncentiveType());
        existing.setAmount(details.getAmount());
        existing.setDailyRate(details.getDailyRate());
        existing.setRemarks(details.getRemarks());

        return incentiveRepository.save(existing);
    }

    @Transactional
    public void deleteIncentive(Long id) {
        incentiveRepository.deleteById(id);
    }
}
