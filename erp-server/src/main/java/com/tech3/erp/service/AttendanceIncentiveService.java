package com.tech3.erp.service;

import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeeAttendanceIncentive;
import com.tech3.erp.repository.EmployeeAttendanceIncentiveRepository;
import com.tech3.erp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceIncentiveService {

    @Autowired
    private EmployeeAttendanceIncentiveRepository incentiveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeAttendanceIncentive> getAllIncentives() {
        return incentiveRepository.findAll();
    }

    public List<EmployeeAttendanceIncentive> getIncentivesByMonth(String month) {
        if (month == null || month.trim().isEmpty()) {
            return incentiveRepository.findAll();
        }
        return incentiveRepository.findByMonth(month.trim());
    }

    public Optional<EmployeeAttendanceIncentive> getIncentiveById(Long id) {
        return incentiveRepository.findById(id);
    }

    @Transactional
    public EmployeeAttendanceIncentive createIncentive(EmployeeAttendanceIncentive incentive) {
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
    public List<EmployeeAttendanceIncentive> createBulkIncentives(List<EmployeeAttendanceIncentive> incentives) {
        List<EmployeeAttendanceIncentive> savedList = new ArrayList<>();
        for (EmployeeAttendanceIncentive item : incentives) {
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
    public EmployeeAttendanceIncentive updateIncentive(Long id, EmployeeAttendanceIncentive details) {
        EmployeeAttendanceIncentive existing = incentiveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance Incentive record not found with id: " + id));

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
