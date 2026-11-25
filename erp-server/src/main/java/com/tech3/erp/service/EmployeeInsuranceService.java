package com.tech3.erp.service;

import com.tech3.erp.dto.EmployeeInsuranceDTO;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeeInsurance;
import com.tech3.erp.repository.EmployeeInsuranceRepository;
import com.tech3.erp.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeInsuranceService {

    private final EmployeeInsuranceRepository insuranceRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeInsuranceService(EmployeeInsuranceRepository insuranceRepository, EmployeeRepository employeeRepository) {
        this.insuranceRepository = insuranceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeeInsuranceDTO createInsurance(EmployeeInsuranceDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

        EmployeeInsurance entity = new EmployeeInsurance();
        entity.setEmployee(employee);
        mapDtoToEntity(dto, entity);

        EmployeeInsurance saved = insuranceRepository.save(entity);
        return new EmployeeInsuranceDTO(saved);
    }

    public List<EmployeeInsuranceDTO> getAllInsurance() {
        return insuranceRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(EmployeeInsurance::getId).reversed())
                .map(EmployeeInsuranceDTO::new)
                .collect(Collectors.toList());
    }

    public EmployeeInsuranceDTO getInsuranceById(Long id) {
        EmployeeInsurance entity = insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insurance policy not found with ID: " + id));
        return new EmployeeInsuranceDTO(entity);
    }

    @Transactional
    public EmployeeInsuranceDTO updateInsurance(Long id, EmployeeInsuranceDTO dto) {
        EmployeeInsurance existing = insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insurance policy not found with ID: " + id));

        if (dto.getEmployeeId() != null && !dto.getEmployeeId().equals(existing.getEmployee().getId())) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));
            existing.setEmployee(employee);
        }

        mapDtoToEntity(dto, existing);
        EmployeeInsurance updated = insuranceRepository.save(existing);
        return new EmployeeInsuranceDTO(updated);
    }

    @Transactional
    public void deleteInsurance(Long id) {
        if (!insuranceRepository.existsById(id)) {
            throw new EntityNotFoundException("Insurance policy not found with ID: " + id);
        }
        insuranceRepository.deleteById(id);
    }

    private void mapDtoToEntity(EmployeeInsuranceDTO dto, EmployeeInsurance entity) {
        entity.setInsuranceProvider(dto.getInsuranceProvider());
        entity.setPolicyNumber(dto.getPolicyNumber());
        entity.setCoverageAmount(dto.getCoverageAmount());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setNominee(dto.getNominee());
    }
}
