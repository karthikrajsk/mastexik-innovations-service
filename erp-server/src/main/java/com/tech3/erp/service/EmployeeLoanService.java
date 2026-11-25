package com.tech3.erp.service;

import com.tech3.erp.dto.EmployeeLoanDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeeLoan;
import com.tech3.erp.repository.EmployeeRepository;
import com.tech3.erp.repository.EmployeeLoanRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeLoanService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeLoanRepository employeeLoanRepository;

    public List<EmployeeLoanDTO> getLoans(Long employeeId) {
        if (employeeId != null) {
            return employeeLoanRepository.findByEmployeeId(employeeId).stream()
                    .map(EmployeeLoanDTO::new)
                    .collect(Collectors.toList());
        }
        return employeeLoanRepository.findAll().stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .map(EmployeeLoanDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<EmployeeLoanDTO> getLoansWithPagination(Long offset, Long limit) {
        List<EmployeeLoanDTO> allLoans = employeeLoanRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .map(EmployeeLoanDTO::new)
                .collect(Collectors.toList());
        return PaginationHelper.paginate(allLoans, offset, limit);
    }

    @Transactional
    public List<EmployeeLoanDTO> saveLoans(Long employeeId, List<EmployeeLoanDTO> dtos) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        List<EmployeeLoan> savedEntities = new ArrayList<>();
        for (EmployeeLoanDTO dto : dtos) {
            EmployeeLoan entity = new EmployeeLoan();
            copyDtoToEntity(dto, entity, employee);
            savedEntities.add(employeeLoanRepository.save(entity));
        }
        return savedEntities.stream().map(EmployeeLoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public List<EmployeeLoanDTO> updateLoans(Long employeeId, List<EmployeeLoanDTO> dtos) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        
        // Delete loans that are not in incoming dtos (for this employee)
        List<EmployeeLoan> existing = employeeLoanRepository.findByEmployeeId(employeeId);
        Set<Long> incomingIds = dtos.stream().map(EmployeeLoanDTO::getId).filter(Objects::nonNull).collect(Collectors.toSet());
        for (EmployeeLoan old : existing) {
            if (!incomingIds.contains(old.getId())) {
                employeeLoanRepository.delete(old);
            }
        }

        List<EmployeeLoan> updatedEntities = new ArrayList<>();
        for (EmployeeLoanDTO dto : dtos) {
            EmployeeLoan entity;
            if (dto.getId() != null) {
                entity = employeeLoanRepository.findById(dto.getId()).orElse(new EmployeeLoan());
            } else {
                entity = new EmployeeLoan();
            }
            copyDtoToEntity(dto, entity, employee);
            updatedEntities.add(employeeLoanRepository.save(entity));
        }
        return updatedEntities.stream().map(EmployeeLoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void deleteLoan(Long id) {
        employeeLoanRepository.deleteById(id);
    }

    private void copyDtoToEntity(EmployeeLoanDTO dto, EmployeeLoan entity, Employee employee) {
        entity.setEmployee(employee);
        entity.setLoanAmount(dto.getLoanAmount());
        entity.setTenureMonths(dto.getTenureMonths());
        entity.setInstallmentAmount(dto.getInstallmentAmount());
        entity.setFromMonth(dto.getFromMonth());
        entity.setToMonth(dto.getToMonth());
        entity.setDisbursementDate(dto.getDisbursementDate() != null ? dto.getDisbursementDate() : LocalDate.now());
        entity.setPurpose(dto.getPurpose());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "Active");
        entity.setSkippedMonths(dto.getSkippedMonths());
        entity.setEmployeeCode(employee.getEmployeeCode());
        entity.setEmployeeName(employee.getEmployeeName());
        entity.setVoucherNo(dto.getVoucherNo());
        entity.setVoucherDate(dto.getVoucherDate() != null ? dto.getVoucherDate() : LocalDate.now());
        entity.setPaymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : LocalDate.now());
    }
}
