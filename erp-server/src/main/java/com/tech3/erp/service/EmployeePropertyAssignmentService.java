package com.tech3.erp.service;

import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeePropertyAssignment;
import com.tech3.erp.entity.ItemMaster;
import com.tech3.erp.repository.EmployeePropertyAssignmentRepository;
import com.tech3.erp.repository.EmployeeRepository;
import com.tech3.erp.repository.ItemMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeePropertyAssignmentService {

    @Autowired
    private EmployeePropertyAssignmentRepository assignmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ItemMasterRepository itemMasterRepository;

    public List<EmployeePropertyAssignment> getAllAssignments(Long companyId, String status, String department, Long employeeId) {
        String cleanStatus = (status != null && !status.trim().isEmpty() && !status.equalsIgnoreCase("ALL")) ? status.trim() : null;
        String cleanDept = (department != null && !department.trim().isEmpty() && !department.equalsIgnoreCase("ALL")) ? department.trim() : null;
        return assignmentRepository.searchAssignments(companyId, cleanStatus, cleanDept, employeeId);
    }

    public Optional<EmployeePropertyAssignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    @Transactional
    public EmployeePropertyAssignment createAssignment(EmployeePropertyAssignment assignment) {
        resolveRelationships(assignment);
        return assignmentRepository.save(assignment);
    }

    @Transactional
    public List<EmployeePropertyAssignment> bulkCreateAssignments(List<EmployeePropertyAssignment> assignments) {
        List<EmployeePropertyAssignment> saved = new ArrayList<>();
        for (EmployeePropertyAssignment item : assignments) {
            resolveRelationships(item);
            saved.add(assignmentRepository.save(item));
        }
        return saved;
    }

    @Transactional
    public EmployeePropertyAssignment updateAssignment(Long id, EmployeePropertyAssignment updated) {
        return assignmentRepository.findById(id).map(existing -> {
            resolveRelationships(updated);

            if (updated.getEmployee() != null) existing.setEmployee(updated.getEmployee());
            if (updated.getItem() != null) existing.setItem(updated.getItem());

            if (updated.getEmployeeCode() != null) existing.setEmployeeCode(updated.getEmployeeCode());
            if (updated.getEmployeeName() != null) existing.setEmployeeName(updated.getEmployeeName());
            if (updated.getDepartment() != null) existing.setDepartment(updated.getDepartment());
            if (updated.getDesignation() != null) existing.setDesignation(updated.getDesignation());

            if (updated.getItemCode() != null) existing.setItemCode(updated.getItemCode());
            if (updated.getItemName() != null) existing.setItemName(updated.getItemName());
            if (updated.getPropertyCategory() != null) existing.setPropertyCategory(updated.getPropertyCategory());
            if (updated.getSerialNumber() != null) existing.setSerialNumber(updated.getSerialNumber());
            if (updated.getQuantity() != null) existing.setQuantity(updated.getQuantity());
            if (updated.getUom() != null) existing.setUom(updated.getUom());

            if (updated.getAssignedDate() != null) existing.setAssignedDate(updated.getAssignedDate());
            if (updated.isExpectedReturnDateSpecified()) {
                existing.setExpectedReturnDate(updated.getExpectedReturnDate());
            } else if (updated.getExpectedReturnDate() != null) {
                existing.setExpectedReturnDate(updated.getExpectedReturnDate());
            }
            if (updated.getConditionAtIssue() != null) existing.setConditionAtIssue(updated.getConditionAtIssue());
            if (updated.getAssignedBy() != null) existing.setAssignedBy(updated.getAssignedBy());

            if (updated.getStatus() != null) existing.setStatus(updated.getStatus());
            if (updated.getReturnedDate() != null) existing.setReturnedDate(updated.getReturnedDate());
            if (updated.getConditionAtReturn() != null) existing.setConditionAtReturn(updated.getConditionAtReturn());
            if (updated.getReceivedBy() != null) existing.setReceivedBy(updated.getReceivedBy());
            if (updated.getDamageDeductionAmount() != null) existing.setDamageDeductionAmount(updated.getDamageDeductionAmount());
            if (updated.getRemarks() != null) existing.setRemarks(updated.getRemarks());
            if (updated.getCompanyId() != null) existing.setCompanyId(updated.getCompanyId());

            return assignmentRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Property Assignment not found with ID: " + id));
    }

    @Transactional
    public EmployeePropertyAssignment markReturned(Long id, LocalDate returnDate, String conditionAtReturn, String receivedBy, BigDecimal deduction, String remarks) {
        return assignmentRepository.findById(id).map(assignment -> {
            assignment.setStatus("RETURNED");
            assignment.setReturnedDate(returnDate != null ? returnDate : LocalDate.now());
            if (conditionAtReturn != null && !conditionAtReturn.isEmpty()) {
                assignment.setConditionAtReturn(conditionAtReturn);
            }
            if (receivedBy != null && !receivedBy.isEmpty()) {
                assignment.setReceivedBy(receivedBy);
            }
            if (deduction != null) {
                assignment.setDamageDeductionAmount(deduction);
            }
            if (remarks != null && !remarks.isEmpty()) {
                String existingRem = assignment.getRemarks() != null ? assignment.getRemarks() + " | " : "";
                assignment.setRemarks(existingRem + "Return: " + remarks);
            }
            return assignmentRepository.save(assignment);
        }).orElseThrow(() -> new RuntimeException("Property Assignment not found with ID: " + id));
    }

    @Transactional
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    private void resolveRelationships(EmployeePropertyAssignment item) {
        Long empId = item.getEmployee() != null ? item.getEmployee().getId() : item.getEmployeeId();
        if (empId != null) {
            Employee emp = employeeRepository.findById(empId).orElse(null);
            if (emp != null) {
                item.setEmployee(emp);
                if (item.getEmployeeCode() == null || item.getEmployeeCode().isEmpty()) {
                    item.setEmployeeCode(emp.getEmployeeCode());
                }
                if (item.getEmployeeName() == null || item.getEmployeeName().isEmpty()) {
                    item.setEmployeeName(emp.getEmployeeName());
                }
                if (item.getDepartment() == null || item.getDepartment().isEmpty()) {
                    item.setDepartment(emp.getDepartment());
                }
                if (item.getDesignation() == null || item.getDesignation().isEmpty()) {
                    item.setDesignation(emp.getDesignation());
                }
            }
        }

        Long itmId = item.getItem() != null ? item.getItem().getId() : item.getItemId();
        if (itmId != null) {
            ItemMaster itm = itemMasterRepository.findById(itmId).orElse(null);
            if (itm != null) {
                item.setItem(itm);
                if (item.getItemCode() == null || item.getItemCode().isEmpty()) {
                    item.setItemCode(itm.getItemCode());
                }
                if (item.getItemName() == null || item.getItemName().isEmpty()) {
                    item.setItemName(itm.getItemName());
                }
                if (item.getPropertyCategory() == null || item.getPropertyCategory().isEmpty()) {
                    item.setPropertyCategory(itm.getItemCategory());
                }
                if (item.getUom() == null || item.getUom().isEmpty()) {
                    item.setUom(itm.getUomName());
                }
            }
        }
    }
}
