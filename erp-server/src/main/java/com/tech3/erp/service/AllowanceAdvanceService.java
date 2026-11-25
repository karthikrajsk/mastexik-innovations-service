package com.tech3.erp.service;

import com.tech3.erp.dto.EmployeeAllowanceAdvanceDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeeAllowanceAdvance;
import com.tech3.erp.repository.EmployeeRepository;
import com.tech3.erp.repository.EmployeeAllowanceAdvanceRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllowanceAdvanceService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeAllowanceAdvanceRepository allowanceAdvanceRepository;

    @Transactional
    public List<EmployeeAllowanceAdvanceDTO> saveAllowancesAdvances(Long employeeId, String month, List<EmployeeAllowanceAdvanceDTO> dtos) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        List<EmployeeAllowanceAdvance> savedEntities = new ArrayList<>();
        for (EmployeeAllowanceAdvanceDTO dto : dtos) {
            EmployeeAllowanceAdvance entity = new EmployeeAllowanceAdvance();
            entity.setEmployee(employee);
            entity.setType(dto.getType());
            entity.setAmount(dto.getAmount());
            entity.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
            entity.setMonth(month);
            
            // Set additional fields from DTO or defaults
            entity.setEmployeeCode(employee.getEmployeeCode());
            entity.setEmployeeName(employee.getEmployeeName());
            entity.setPaymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : LocalDate.now());
            entity.setVoucherDate(dto.getVoucherDate() != null ? dto.getVoucherDate() : LocalDate.now());
            entity.setVoucherNo(dto.getVoucherNo());
            entity.setEligibleForAdvance(dto.getEligibleForAdvance() != null ? dto.getEligibleForAdvance() : true);
            entity.setReason(dto.getReason());
            
            savedEntities.add(allowanceAdvanceRepository.save(entity));
        }
        return savedEntities.stream().map(EmployeeAllowanceAdvanceDTO::new).collect(Collectors.toList());
    }

    public List<EmployeeAllowanceAdvanceDTO> getAllowancesAdvances(Long employeeId, String month) {
        return allowanceAdvanceRepository.findByEmployeeIdAndMonth(employeeId, month).stream()
                .map(EmployeeAllowanceAdvanceDTO::new)
                .collect(Collectors.toList());
    }

    public List<EmployeeAllowanceAdvanceDTO> getAllAllowancesAdvances() {
        return allowanceAdvanceRepository.findAll().stream()
                .map(EmployeeAllowanceAdvanceDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<EmployeeAllowanceAdvanceDTO> getAllAllowancesAdvancesWithPagination(Long offset, Long limit) {
        List<EmployeeAllowanceAdvanceDTO> allAllowancesAdvances = allowanceAdvanceRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId())) // Sort by ID descending
                .map(EmployeeAllowanceAdvanceDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allAllowancesAdvances, offset, limit);
    }

    @Transactional
    public void deleteAllowanceAdvance(Long id) {
        allowanceAdvanceRepository.deleteById(id);
    }

    @Transactional
    public List<EmployeeAllowanceAdvanceDTO> updateAllowancesAdvances(Long employeeId, String month, List<EmployeeAllowanceAdvanceDTO> dtos) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        List<EmployeeAllowanceAdvance> updatedEntities = new ArrayList<>();
        
        for (EmployeeAllowanceAdvanceDTO dto : dtos) {
            EmployeeAllowanceAdvance entity;
            if (dto.getId() != null) {
                entity = allowanceAdvanceRepository.findById(dto.getId())
                        .orElse(new EmployeeAllowanceAdvance());
            } else {
                entity = new EmployeeAllowanceAdvance();
            }
            
            entity.setEmployee(employee);
            entity.setType(dto.getType());
            entity.setAmount(dto.getAmount());
            entity.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
            entity.setMonth(month);
            
            entity.setEmployeeCode(employee.getEmployeeCode());
            entity.setEmployeeName(employee.getEmployeeName());
            entity.setPaymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : LocalDate.now());
            entity.setVoucherDate(dto.getVoucherDate() != null ? dto.getVoucherDate() : LocalDate.now());
            entity.setVoucherNo(dto.getVoucherNo());
            entity.setEligibleForAdvance(dto.getEligibleForAdvance() != null ? dto.getEligibleForAdvance() : true);
            entity.setReason(dto.getReason());
            
            updatedEntities.add(allowanceAdvanceRepository.save(entity));
        }
        return updatedEntities.stream().map(EmployeeAllowanceAdvanceDTO::new).collect(Collectors.toList());
    }
}
