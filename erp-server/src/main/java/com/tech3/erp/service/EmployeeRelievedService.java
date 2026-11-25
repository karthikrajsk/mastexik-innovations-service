// package com.tech3.erp.service;

// import com.tech3.erp.dto.EmployeeRelievedDTO;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.entity.EmployeeRelieved;
// import com.tech3.erp.entity.Employee;
// import com.tech3.erp.repository.EmployeeRelievedRepository;
// import com.tech3.erp.repository.EmployeeRepository;
// import com.tech3.erp.util.PaginationHelper;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import jakarta.persistence.EntityNotFoundException;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// @Transactional
// public class EmployeeRelievedService {

//     @Autowired
//     private EmployeeRelievedRepository employeeRelievedRepository;

//     @Autowired
//     private EmployeeRepository employeeRepository;

//     public EmployeeRelievedDTO createEmployeeRelieved(EmployeeRelievedDTO dto) {
//         EmployeeRelieved entity = convertToEntity(dto);
        
//         // Fetch current details from employee
//         Employee employee = employeeRepository.findById(dto.getEmployeeId())
//                 .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
        
//         if (employee.getEmploymentDetails() != null) {
//             entity.setDepartment(employee.getEmploymentDetails().getDepartment());
//             entity.setDesignation(employee.getEmploymentDetails().getDesignation());
//             entity.setJoiningDate(employee.getEmploymentDetails().getJoiningDate());
//         }

//         entity.setIsRelieved(1); // Set to 1 when creating
//         EmployeeRelieved savedEntity = employeeRelievedRepository.save(entity);
        
//         // Update Employee entity's isRelieved status
//         employee.setIsRelieved(1);
//         employeeRepository.save(employee);
        
//         return convertToDTO(savedEntity);
//     }

//     public EmployeeRelievedDTO updateEmployeeRelieved(Long id, EmployeeRelievedDTO dto) {
//         EmployeeRelieved existingEntity = employeeRelievedRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with id: " + id));

//         updateEntityFromDTO(existingEntity, dto);
        
//         // Ensure details are kept or updated from employee if needed
//         Employee employee = employeeRepository.findById(existingEntity.getEmployeeId())
//                 .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + existingEntity.getEmployeeId()));
        
//         if (employee.getEmploymentDetails() != null) {
//             existingEntity.setDepartment(employee.getEmploymentDetails().getDepartment());
//             existingEntity.setDesignation(employee.getEmploymentDetails().getDesignation());
//             existingEntity.setJoiningDate(employee.getEmploymentDetails().getJoiningDate());
//         }

//         existingEntity.setIsRelieved(1); // Always set to 1 when updating
//         EmployeeRelieved updatedEntity = employeeRelievedRepository.save(existingEntity);
        
//         // Update Employee entity's isRelieved status
//         employee.setIsRelieved(1);
//         employeeRepository.save(employee);
        
//         return convertToDTO(updatedEntity);
//     }

//     public EmployeeRelievedDTO getEmployeeRelievedById(Long id) {
//         EmployeeRelieved entity = employeeRelievedRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with id: " + id));
//         return convertToDTO(entity);
//     }

//     public EmployeeRelievedDTO getEmployeeRelievedByEmployeeId(Long employeeId) {
//         EmployeeRelieved entity = employeeRelievedRepository.findByEmployeeId(employeeId)
//                 .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with employeeId: " + employeeId));
//         return convertToDTO(entity);
//     }

//     public PaginatedResponse<EmployeeRelievedDTO> getAllEmployeeRelieved(Long offset, Long limit) {
//         List<EmployeeRelieved> entities = employeeRelievedRepository.findAll();
//         List<EmployeeRelievedDTO> dtoList = entities.stream().map(this::convertToDTO).collect(Collectors.toList());
//         return PaginationHelper.paginate(dtoList, offset != null ? offset : 0, limit != null ? limit : 10);
//     }

//     public List<EmployeeRelievedDTO> getEmployeeRelievedByStatus(Integer isRelieved) {
//         List<EmployeeRelieved> entities = employeeRelievedRepository.findByIsRelieved(isRelieved);
//         return entities.stream().map(this::convertToDTO).collect(Collectors.toList());
//     }

//     public EmployeeRelievedDTO relieveEmployee(Long id) {
//         EmployeeRelieved entity = employeeRelievedRepository.findById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with id: " + id));
        
//         entity.setIsRelieved(1);
//         EmployeeRelieved updatedEntity = employeeRelievedRepository.save(entity);
//         return convertToDTO(updatedEntity);
//     }

//     public void deleteEmployeeRelieved(Long id) {
//         if (!employeeRelievedRepository.existsById(id)) {
//             throw new EntityNotFoundException("EmployeeRelieved not found with id: " + id);
//         }
//         employeeRelievedRepository.deleteById(id);
//     }

//     public List<EmployeeRelievedDTO> searchEmployeeRelieved(String searchTerm) {
//         List<EmployeeRelieved> entities = employeeRelievedRepository.findByReasonOrRemarksContaining(searchTerm);
//         return entities.stream().map(this::convertToDTO).collect(Collectors.toList());
//     }

//     private EmployeeRelieved convertToEntity(EmployeeRelievedDTO dto) {
//         EmployeeRelieved entity = new EmployeeRelieved();
//         entity.setId(dto.getId());
//         entity.setEmployeeId(dto.getEmployeeId());
//         entity.setRelievingDate(dto.getRelievingDate());
//         entity.setReason(dto.getReason());
//         entity.setRemarks(dto.getRemarks());
//         entity.setDepartment(dto.getDepartment());
//         entity.setDesignation(dto.getDesignation());
//         entity.setJoiningDate(dto.getJoiningDate());
//         entity.setIsRelieved(dto.getIsRelieved());
//         return entity;
//     }

//     private EmployeeRelievedDTO convertToDTO(EmployeeRelieved entity) {
//         EmployeeRelievedDTO dto = new EmployeeRelievedDTO();
//         dto.setId(entity.getId());
//         dto.setEmployeeId(entity.getEmployeeId());
//         dto.setRelievingDate(entity.getRelievingDate());
//         dto.setReason(entity.getReason());
//         dto.setRemarks(entity.getRemarks());
//         dto.setDepartment(entity.getDepartment());
//         dto.setDesignation(entity.getDesignation());
//         dto.setJoiningDate(entity.getJoiningDate());
//         dto.setIsRelieved(entity.getIsRelieved());
//         dto.setCreatedAt(entity.getCreatedAt());
//         dto.setUpdatedAt(entity.getUpdatedAt());
//         return dto;
//     }

//     private void updateEntityFromDTO(EmployeeRelieved entity, EmployeeRelievedDTO dto) {
//         entity.setEmployeeId(dto.getEmployeeId());
//         entity.setRelievingDate(dto.getRelievingDate());
//         entity.setReason(dto.getReason());
//         entity.setRemarks(dto.getRemarks());
//         entity.setDepartment(dto.getDepartment());
//         entity.setDesignation(dto.getDesignation());
//         entity.setJoiningDate(dto.getJoiningDate());
//         entity.setIsRelieved(1); // Always set to 1 when updating from DTO
//     }
// }

package com.tech3.erp.service;

import com.tech3.erp.dto.EmployeeRelievedDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.EmployeeRelieved;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.repository.EmployeeRelievedRepository;
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
public class EmployeeRelievedService {

    @Autowired
    private EmployeeRelievedRepository employeeRelievedRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeRelievedDTO createEmployeeRelieved(EmployeeRelievedDTO dto) {
        EmployeeRelieved entity = convertToEntity(dto);
        
        // Fetch current details from employee
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dto.getEmployeeId()));
        
        if (employee.getEmploymentDetails() != null) {
            entity.setDepartment(employee.getEmploymentDetails().getDepartment());
            entity.setDesignation(employee.getEmploymentDetails().getDesignation());
            entity.setJoiningDate(employee.getEmploymentDetails().getJoiningDate());
        }

        int relievedStatus = dto.getIsRelieved() != null ? dto.getIsRelieved() : 1;
        entity.setIsRelieved(relievedStatus);
        entity.setActiveFlag(relievedStatus == 0);
        EmployeeRelieved savedEntity = employeeRelievedRepository.save(entity);
        
        // Update Employee entity's isRelieved status
        employee.setIsRelieved(relievedStatus);
        employeeRepository.save(employee);
        
        return convertToDTO(savedEntity);
    }

   public EmployeeRelievedDTO updateEmployeeRelieved(Long id, EmployeeRelievedDTO dto) {
    EmployeeRelieved existingEntity = employeeRelievedRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with id: " + id));

    updateEntityFromDTO(existingEntity, dto);

    Employee employee = employeeRepository.findById(existingEntity.getEmployeeId())
            .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + existingEntity.getEmployeeId()));

    if (employee.getEmploymentDetails() != null) {
        existingEntity.setDepartment(employee.getEmploymentDetails().getDepartment());
        existingEntity.setDesignation(employee.getEmploymentDetails().getDesignation());
        existingEntity.setJoiningDate(employee.getEmploymentDetails().getJoiningDate());
    }

    // When Active checkbox is unchecked (activeFlag = false), set isRelieved = 0
    if (dto.getActiveFlag() != null && !dto.getActiveFlag()) {
        existingEntity.setIsRelieved(0);
        existingEntity.setActiveFlag(true);
    } else if (dto.getIsRelieved() != null) {
        existingEntity.setIsRelieved(dto.getIsRelieved());
    }

    EmployeeRelieved updatedEntity = employeeRelievedRepository.save(existingEntity);

    employee.setIsRelieved(updatedEntity.getIsRelieved() != null ? updatedEntity.getIsRelieved() : 0);
    employeeRepository.save(employee);

    return convertToDTO(updatedEntity);
}
    public EmployeeRelievedDTO getEmployeeRelievedById(Long id) {
        EmployeeRelieved entity = employeeRelievedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with id: " + id));
        return convertToDTO(entity);
    }

    public EmployeeRelievedDTO getEmployeeRelievedByEmployeeId(Long employeeId) {
        EmployeeRelieved entity = employeeRelievedRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with employeeId: " + employeeId));
        return convertToDTO(entity);
    }

    public PaginatedResponse<EmployeeRelievedDTO> getAllEmployeeRelieved(Long offset, Long limit) {
        List<EmployeeRelieved> entities = employeeRelievedRepository.findAll();
        List<EmployeeRelievedDTO> dtoList = entities.stream().map(this::convertToDTO).collect(Collectors.toList());
        return PaginationHelper.paginate(dtoList, offset != null ? offset : 0, limit != null ? limit : 10);
    }

    public List<EmployeeRelievedDTO> getEmployeeRelievedByStatus(Integer isRelieved) {
        List<EmployeeRelieved> entities = employeeRelievedRepository.findByIsRelieved(isRelieved);
        return entities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EmployeeRelievedDTO relieveEmployee(Long id) {
        EmployeeRelieved entity = employeeRelievedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with id: " + id));
        
        entity.setIsRelieved(1);
        entity.setActiveFlag(false);
        EmployeeRelieved updatedEntity = employeeRelievedRepository.save(entity);

        if (entity.getEmployeeId() != null) {
            employeeRepository.findById(entity.getEmployeeId()).ifPresent(emp -> {
                emp.setIsRelieved(1);
                employeeRepository.save(emp);
            });
        }

        return convertToDTO(updatedEntity);
    }

    public void deleteEmployeeRelieved(Long id) {
        EmployeeRelieved entity = employeeRelievedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmployeeRelieved not found with id: " + id));

        // Revert isRelieved back to 0 on Employee entity when deleted
        if (entity.getEmployeeId() != null) {
            employeeRepository.findById(entity.getEmployeeId()).ifPresent(emp -> {
                emp.setIsRelieved(0);
                employeeRepository.save(emp);
            });
        }

        employeeRelievedRepository.deleteById(id);
    }

    public void migrateActiveFlag() {
        List<EmployeeRelieved> entities = employeeRelievedRepository.findAll();
        for (EmployeeRelieved entity : entities) {
            if (entity.getActiveFlag() == null) {
                // Set activeFlag based on isRelieved: if isRelieved is 1, activeFlag is false; if isRelieved is 0, activeFlag is true
                entity.setActiveFlag(entity.getIsRelieved() == 0);
                employeeRelievedRepository.save(entity);
            }
        }
    }

    public List<EmployeeRelievedDTO> searchEmployeeRelieved(String searchTerm) {
        List<EmployeeRelieved> entities = employeeRelievedRepository.findByReasonOrRemarksContaining(searchTerm);
        return entities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EmployeeRelievedDTO updateEmployeeRelievedStatus(Long employeeId, Integer isRelieved) {
        EmployeeRelieved entity = employeeRelievedRepository.findByEmployeeId(employeeId)
                .orElse(null);
        
        if (entity != null) {
            entity.setIsRelieved(isRelieved);
            // Set activeFlag based on isRelieved: if isRelieved is 1, activeFlag is false; if isRelieved is 0, activeFlag is true
            entity.setActiveFlag(isRelieved == 0);
            EmployeeRelieved updatedEntity = employeeRelievedRepository.save(entity);
            return convertToDTO(updatedEntity);
        }
        
        return null;
    }

    private EmployeeRelieved convertToEntity(EmployeeRelievedDTO dto) {
        EmployeeRelieved entity = new EmployeeRelieved();
        entity.setId(dto.getId());
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setRelievingDate(dto.getRelievingDate());
        entity.setReason(dto.getReason());
        entity.setRemarks(dto.getRemarks());
        entity.setDepartment(dto.getDepartment());
        entity.setDesignation(dto.getDesignation());
        entity.setJoiningDate(dto.getJoiningDate());
        if (dto.getIsRelieved() != null) {
            entity.setIsRelieved(dto.getIsRelieved());
            // Default activeFlag based on isRelieved if activeFlag is not in DTO
            if (dto.getActiveFlag() == null) {
                entity.setActiveFlag(dto.getIsRelieved() == 0);
            } else {
                entity.setActiveFlag(dto.getActiveFlag());
            }
        }
        return entity;
    }

    private EmployeeRelievedDTO convertToDTO(EmployeeRelieved entity) {
        EmployeeRelievedDTO dto = new EmployeeRelievedDTO();
        dto.setId(entity.getId());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setRelievingDate(entity.getRelievingDate());
        dto.setReason(entity.getReason());
        dto.setRemarks(entity.getRemarks());
        dto.setDepartment(entity.getDepartment());
        dto.setDesignation(entity.getDesignation());
        dto.setJoiningDate(entity.getJoiningDate());
        dto.setIsRelieved(entity.getIsRelieved());
        dto.setActiveFlag(entity.getActiveFlag());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private void updateEntityFromDTO(EmployeeRelieved entity, EmployeeRelievedDTO dto) {
        entity.setEmployeeId(dto.getEmployeeId());
        entity.setRelievingDate(dto.getRelievingDate());
        entity.setReason(dto.getReason());
        entity.setRemarks(dto.getRemarks());
        entity.setDepartment(dto.getDepartment());
        entity.setDesignation(dto.getDesignation());
        entity.setJoiningDate(dto.getJoiningDate());
        
        if (dto.getIsRelieved() != null) {
            entity.setIsRelieved(dto.getIsRelieved());
        }
        if (dto.getActiveFlag() != null) {
            entity.setActiveFlag(dto.getActiveFlag());
        }
    }

    
}