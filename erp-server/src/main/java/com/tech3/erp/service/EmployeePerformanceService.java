package com.tech3.erp.service;

import com.tech3.erp.dto.EmployeePerformanceDTO;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeePerformanceRecord;
import com.tech3.erp.repository.EmployeePerformanceRepository;
import com.tech3.erp.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeePerformanceService {

    private final EmployeePerformanceRepository performanceRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeePerformanceService(EmployeePerformanceRepository performanceRepository, EmployeeRepository employeeRepository) {
        this.performanceRepository = performanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeePerformanceDTO createPerformance(EmployeePerformanceDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

        EmployeePerformanceRecord entity = new EmployeePerformanceRecord();
        entity.setEmployee(employee);
        mapDtoToEntity(dto, entity);

        EmployeePerformanceRecord saved = performanceRepository.save(entity);
        return new EmployeePerformanceDTO(saved);
    }

    public List<EmployeePerformanceDTO> getAllPerformance() {
        return performanceRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(EmployeePerformanceRecord::getId).reversed())
                .map(EmployeePerformanceDTO::new)
                .collect(Collectors.toList());
    }

    public EmployeePerformanceDTO getPerformanceById(Long id) {
        EmployeePerformanceRecord entity = performanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Performance review not found with ID: " + id));
        return new EmployeePerformanceDTO(entity);
    }

    @Transactional
    public EmployeePerformanceDTO updatePerformance(Long id, EmployeePerformanceDTO dto) {
        EmployeePerformanceRecord existing = performanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Performance review not found with ID: " + id));

        if (dto.getEmployeeId() != null && !dto.getEmployeeId().equals(existing.getEmployee().getId())) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));
            existing.setEmployee(employee);
        }

        mapDtoToEntity(dto, existing);
        EmployeePerformanceRecord updated = performanceRepository.save(existing);
        return new EmployeePerformanceDTO(updated);
    }

    @Transactional
    public void deletePerformance(Long id) {
        if (!performanceRepository.existsById(id)) {
            throw new EntityNotFoundException("Performance review not found with ID: " + id);
        }
        performanceRepository.deleteById(id);
    }

    private void mapDtoToEntity(EmployeePerformanceDTO dto, EmployeePerformanceRecord entity) {
        entity.setReviewPeriod(dto.getReviewPeriod());
        entity.setRating(dto.getRating());
        entity.setPerformanceComments(dto.getPerformanceComments());
        entity.setReviewerName(dto.getReviewerName());
        entity.setReviewDate(dto.getReviewDate());
    }
}
