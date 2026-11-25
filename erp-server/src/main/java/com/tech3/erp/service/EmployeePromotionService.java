package com.tech3.erp.service;

import com.tech3.erp.dto.EmployeePromotionDTO;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeePromotion;
import com.tech3.erp.repository.EmployeePromotionRepository;
import com.tech3.erp.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeePromotionService {

    private final EmployeePromotionRepository promotionRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeePromotionService(EmployeePromotionRepository promotionRepository, EmployeeRepository employeeRepository) {
        this.promotionRepository = promotionRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public EmployeePromotionDTO createPromotion(EmployeePromotionDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

        EmployeePromotion entity = new EmployeePromotion();
        entity.setEmployee(employee);
        mapDtoToEntity(dto, entity);

        EmployeePromotion saved = promotionRepository.save(entity);
        return new EmployeePromotionDTO(saved);
    }

    public List<EmployeePromotionDTO> getAllPromotions() {
        return promotionRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(EmployeePromotion::getId).reversed())
                .map(EmployeePromotionDTO::new)
                .collect(Collectors.toList());
    }

    public EmployeePromotionDTO getPromotionById(Long id) {
        EmployeePromotion entity = promotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found with ID: " + id));
        return new EmployeePromotionDTO(entity);
    }

    @Transactional
    public EmployeePromotionDTO updatePromotion(Long id, EmployeePromotionDTO dto) {
        EmployeePromotion existing = promotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found with ID: " + id));

        if (dto.getEmployeeId() != null && !dto.getEmployeeId().equals(existing.getEmployee().getId())) {
            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));
            existing.setEmployee(employee);
        }

        mapDtoToEntity(dto, existing);
        EmployeePromotion updated = promotionRepository.save(existing);
        return new EmployeePromotionDTO(updated);
    }

    @Transactional
    public void deletePromotion(Long id) {
        if (!promotionRepository.existsById(id)) {
            throw new EntityNotFoundException("Promotion not found with ID: " + id);
        }
        promotionRepository.deleteById(id);
    }

    private void mapDtoToEntity(EmployeePromotionDTO dto, EmployeePromotion entity) {
        entity.setCurrentDesignation(dto.getCurrentDesignation());
        entity.setNewDesignation(dto.getNewDesignation());
        entity.setCurrentSalary(dto.getCurrentSalary());
        entity.setIncrementAmount(dto.getIncrementAmount());
        entity.setNewSalary(dto.getNewSalary());
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setRemarks(dto.getRemarks());
    }
}
