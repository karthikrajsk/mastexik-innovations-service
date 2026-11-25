package com.tech3.erp.service;

import com.tech3.erp.dto.PermissionRequestDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.PermissionRequest;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.repository.PermissionRequestRepository;
import com.tech3.erp.repository.EmployeeRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionRequestService {

    @Autowired
    private PermissionRequestRepository permissionRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public PermissionRequestDTO createPermissionRequest(PermissionRequestDTO dto) {
        // Validate time range
        if (dto.getFromTime().isAfter(dto.getToTime()) || dto.getFromTime().equals(dto.getToTime())) {
            throw new IllegalArgumentException("From time must be before to time");
        }

        // Check for overlapping permission requests
        List<PermissionRequest> overlappingPermissions = permissionRequestRepository.findOverlappingPermissions(
                dto.getEmployeeId(), dto.getDate(), dto.getFromTime(), dto.getToTime());
        
        if (!overlappingPermissions.isEmpty()) {
            throw new IllegalArgumentException("Employee already has a permission request for this time period");
        }

        PermissionRequest entity = convertToEntity(dto);
        PermissionRequest savedEntity = permissionRequestRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    public PermissionRequestDTO updatePermissionRequest(Long id, PermissionRequestDTO dto) {
        PermissionRequest existingEntity = permissionRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PermissionRequest not found with id: " + id));

        // Validate time range
        if (dto.getFromTime().isAfter(dto.getToTime()) || dto.getFromTime().equals(dto.getToTime())) {
            throw new IllegalArgumentException("From time must be before to time");
        }

        // Check for overlapping permission requests (excluding current one)
        List<PermissionRequest> overlappingPermissions = permissionRequestRepository.findOverlappingPermissionsExcludingCurrent(
                dto.getEmployeeId(), dto.getDate(), dto.getFromTime(), dto.getToTime(), id);
        
        if (!overlappingPermissions.isEmpty()) {
            throw new IllegalArgumentException("Employee already has a permission request for this time period");
        }

        updateEntityFromDTO(existingEntity, dto);
        PermissionRequest updatedEntity = permissionRequestRepository.save(existingEntity);
        return convertToDTO(updatedEntity);
    }

    public PermissionRequestDTO getPermissionRequestById(Long id) {
        PermissionRequest entity = permissionRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PermissionRequest not found with id: " + id));
        return convertToDTO(entity);
    }

    public PaginatedResponse<PermissionRequestDTO> getAllPermissionRequests(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PermissionRequest> permissionPage = permissionRequestRepository.findAll(pageable);
        
        List<PermissionRequestDTO> dtos = permissionPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                permissionPage.getTotalElements(),
                permissionPage.hasNext(),
                permissionPage.hasPrevious(),
                page * size,
                size,
                dtos
        );
    }

    public List<PermissionRequestDTO> getPermissionRequestsByEmployee(Long employeeId) {
        List<PermissionRequest> permissions = permissionRequestRepository.findByEmployeeIdOrderByDateDesc(employeeId);
        return permissions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public PermissionRequestDTO approvePermissionRequest(Long id, String approvedBy) {
        PermissionRequest entity = permissionRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PermissionRequest not found with id: " + id));

        entity.setStatus(PermissionRequest.PermissionStatus.APPROVED);
        entity.setApprovedBy(approvedBy);
        PermissionRequest updatedEntity = permissionRequestRepository.save(entity);
        return convertToDTO(updatedEntity);
    }

    public PermissionRequestDTO rejectPermissionRequest(Long id, String rejectionReason, String rejectedBy) {
        PermissionRequest entity = permissionRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PermissionRequest not found with id: " + id));

        entity.setStatus(PermissionRequest.PermissionStatus.REJECTED);
        entity.setApprovedBy(rejectedBy);
        entity.setReason(entity.getReason() + "\n\nRejection reason: " + rejectionReason);
        PermissionRequest updatedEntity = permissionRequestRepository.save(entity);
        return convertToDTO(updatedEntity);
    }

    public void deletePermissionRequest(Long id) {
        PermissionRequest entity = permissionRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PermissionRequest not found with id: " + id));

        permissionRequestRepository.delete(entity);
    }

    // Helper methods
    private PermissionRequest convertToEntity(PermissionRequestDTO dto) {
        PermissionRequest entity = new PermissionRequest();
        entity.setId(dto.getId());
        
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
        entity.setEmployee(employee);
        
        entity.setDate(dto.getDate());
        entity.setFromTime(dto.getFromTime());
        entity.setToTime(dto.getToTime());
        entity.setReason(dto.getReason());
        entity.setApprovedBy(dto.getApprovedBy());
        
        if (dto.getStatus() != null) {
            entity.setStatus(PermissionRequest.PermissionStatus.valueOf(dto.getStatus().toUpperCase()));
        }
        
        return entity;
    }

    private void updateEntityFromDTO(PermissionRequest entity, PermissionRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
        entity.setEmployee(employee);
        
        entity.setDate(dto.getDate());
        entity.setFromTime(dto.getFromTime());
        entity.setToTime(dto.getToTime());
        entity.setReason(dto.getReason());
        entity.setApprovedBy(dto.getApprovedBy());
        
        if (dto.getStatus() != null) {
            entity.setStatus(PermissionRequest.PermissionStatus.valueOf(dto.getStatus().toUpperCase()));
        }
    }

    private PermissionRequestDTO convertToDTO(PermissionRequest entity) {
        PermissionRequestDTO dto = new PermissionRequestDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployee().getId());
        dto.setEmployeeName(entity.getEmployee().getEmployeeName());
        dto.setEmployeeCode(entity.getEmployee().getEmployeeCode());
        dto.setDate(entity.getDate());
        dto.setFromTime(entity.getFromTime());
        dto.setToTime(entity.getToTime());
        dto.setReason(entity.getReason());
        dto.setApprovedBy(entity.getApprovedBy());
        dto.setStatus(entity.getStatus().toString());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (entity.getCreatedAt() != null) {
            dto.setCreatedAt(entity.getCreatedAt().format(formatter));
        }
        if (entity.getUpdatedAt() != null) {
            dto.setUpdatedAt(entity.getUpdatedAt().format(formatter));
        }
        
        return dto;
    }
}
