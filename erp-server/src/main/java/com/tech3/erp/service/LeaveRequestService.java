package com.tech3.erp.service;

import com.tech3.erp.dto.LeaveRequestDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.LeaveRequest;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.repository.LeaveRequestRepository;
import com.tech3.erp.repository.EmployeeRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public LeaveRequestDTO createLeaveRequest(LeaveRequestDTO dto) {
        // Validate leave dates
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        // Check for overlapping leave requests
        List<LeaveRequest> overlappingLeaves = leaveRequestRepository.findOverlappingLeaveRequests(
                dto.getEmployeeId(), dto.getStartDate(), dto.getEndDate());
        
        if (!overlappingLeaves.isEmpty()) {
            throw new IllegalArgumentException("Employee already has a leave request for this period");
        }

        LeaveRequest entity = convertToEntity(dto);
        LeaveRequest savedEntity = leaveRequestRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    public LeaveRequestDTO updateLeaveRequest(Long id, LeaveRequestDTO dto) {
        LeaveRequest existingEntity = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeaveRequest not found with id: " + id));

        // // Only allow update if status is Pending
        // if (!"Pending".equals(existingEntity.getStatus())) {
        //     throw new IllegalArgumentException("Can only update leave requests with Pending status");
        // }

        // Validate leave dates
        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        // Check for overlapping leave requests (excluding current one)
        List<LeaveRequest> overlappingLeaves = leaveRequestRepository.findOverlappingLeaveRequests(
                dto.getEmployeeId(), dto.getStartDate(), dto.getEndDate());
        
        overlappingLeaves = overlappingLeaves.stream()
                .filter(leave -> !leave.getId().equals(id))
                .collect(Collectors.toList());
        
        if (!overlappingLeaves.isEmpty()) {
            throw new IllegalArgumentException("Employee already has a leave request for this period");
        }

        updateEntityFromDTO(existingEntity, dto);
        LeaveRequest updatedEntity = leaveRequestRepository.save(existingEntity);
        return convertToDTO(updatedEntity);
    }

    public LeaveRequestDTO getLeaveRequestById(Long id) {
        LeaveRequest entity = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeaveRequest not found with id: " + id));
        return convertToDTO(entity);
    }

    public PaginatedResponse<LeaveRequestDTO> getAllLeaveRequests(Long offset, Long limit) {
        List<LeaveRequest> entities = leaveRequestRepository.findAll();
        List<LeaveRequestDTO> dtoList = entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return PaginationHelper.paginate(dtoList, offset != null ? offset : 0, limit != null ? limit : 10);
    }

    public List<LeaveRequestDTO> getLeaveRequestsByEmployeeId(Long employeeId) {
        List<LeaveRequest> entities = leaveRequestRepository.findByEmployeeId(employeeId);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveRequestDTO> getLeaveRequestsByStatus(String status) {
        List<LeaveRequest> entities = leaveRequestRepository.findByStatus(status);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LeaveRequestDTO approveLeaveRequest(Long id, Long approvedBy) {
        LeaveRequest entity = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeaveRequest not found with id: " + id));

        if (!"Pending".equals(entity.getStatus())) {
            throw new IllegalArgumentException("Can only approve leave requests with Pending status");
        }

        entity.setStatus("Approved");
        entity.setApprovedBy(approvedBy);
        entity.setApprovedDate(LocalDateTime.now());
        
        LeaveRequest updatedEntity = leaveRequestRepository.save(entity);
        return convertToDTO(updatedEntity);
    }

    public LeaveRequestDTO rejectLeaveRequest(Long id, String rejectionReason, Long rejectedBy) {
        LeaveRequest entity = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeaveRequest not found with id: " + id));

        if (!"Pending".equals(entity.getStatus())) {
            throw new IllegalArgumentException("Can only reject leave requests with Pending status");
        }

        entity.setStatus("Rejected");
        entity.setRejectionReason(rejectionReason);
        entity.setApprovedBy(rejectedBy);
        entity.setApprovedDate(LocalDateTime.now());
        
        LeaveRequest updatedEntity = leaveRequestRepository.save(entity);
        return convertToDTO(updatedEntity);
    }

    public void deleteLeaveRequest(Long id) {
        LeaveRequest entity = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeaveRequest not found with id: " + id));

        // Only allow deletion if status is Pending
        if (!"Pending".equals(entity.getStatus())) {
            throw new IllegalArgumentException("Can only delete leave requests with Pending status");
        }

        leaveRequestRepository.deleteById(id);
    }

    public List<LeaveRequestDTO> searchLeaveRequests(String searchTerm) {
        List<LeaveRequest> entities = leaveRequestRepository.searchLeaveRequests(searchTerm);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LeaveRequest convertToEntity(LeaveRequestDTO dto) {
        LeaveRequest entity = new LeaveRequest();
        entity.setId(dto.getId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setLeaveType(dto.getLeaveType());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        entity.setStatus(dto.getStatus());
        entity.setApprovedBy(dto.getApprovedBy());
        entity.setApprovedDate(dto.getApprovedDate());
        entity.setRejectionReason(dto.getRejectionReason());
        return entity;
    }

    private LeaveRequestDTO convertToDTO(LeaveRequest entity) {
        LeaveRequestDTO dto = new LeaveRequestDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setLeaveType(entity.getLeaveType());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        dto.setApprovedBy(entity.getApprovedBy());
        dto.setApprovedDate(entity.getApprovedDate());
        dto.setRejectionReason(entity.getRejectionReason());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        // Fetch employee details
        if (entity.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(entity.getEmployeeId()).orElse(null);
            if (employee != null) {
                dto.setEmployeeName(employee.getEmployeeName());
                dto.setEmployeeCode(employee.getEmployeeCode());
            }
        }

        // Fetch approver details
        if (entity.getApprovedBy() != null) {
            Employee approver = employeeRepository.findById(entity.getApprovedBy()).orElse(null);
            if (approver != null) {
                dto.setApprovedByName(approver.getEmployeeName());
            }
        }

        return dto;
    }

    private void updateEntityFromDTO(LeaveRequest entity, LeaveRequestDTO dto) {
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setLeaveType(dto.getLeaveType());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        entity.setStatus(dto.getStatus());
        entity.setApprovedBy(dto.getApprovedBy());
        entity.setApprovedDate(dto.getApprovedDate());
        entity.setRejectionReason(dto.getRejectionReason());
    }
}
