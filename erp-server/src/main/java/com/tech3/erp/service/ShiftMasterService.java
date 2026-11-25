package com.tech3.erp.service;

import com.tech3.erp.dto.ShiftMasterDTO;
import com.tech3.erp.dto.ShiftTimingDetailDTO;
import com.tech3.erp.entity.ShiftMaster;
import com.tech3.erp.entity.ShiftTimingDetail;
import com.tech3.erp.repository.ShiftMasterRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShiftMasterService {

    private final ShiftMasterRepository shiftMasterRepository;

    @Autowired
    public ShiftMasterService(ShiftMasterRepository shiftMasterRepository) {
        this.shiftMasterRepository = shiftMasterRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getAllShifts(String search, String shiftType, Boolean activeFlag, int limit, int offset) {
        int safeLimit = limit <= 0 ? 10000 : limit;
        int pageNumber = offset / safeLimit;
        Pageable pageable = PageRequest.of(pageNumber, safeLimit, Sort.by(Sort.Direction.DESC, "id"));

        Specification<ShiftMaster> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.trim().toLowerCase() + "%";
                Predicate codePred = cb.like(cb.lower(root.get("shiftCode")), searchPattern);
                Predicate namePred = cb.like(cb.lower(root.get("shiftName")), searchPattern);
                Predicate descPred = cb.like(cb.lower(cb.coalesce(root.get("description"), "")), searchPattern);
                predicates.add(cb.or(codePred, namePred, descPred));
            }

            if (shiftType != null && !shiftType.trim().isEmpty() && !"All".equalsIgnoreCase(shiftType.trim())) {
                predicates.add(cb.equal(cb.lower(root.get("shiftType")), shiftType.trim().toLowerCase()));
            }

            if (activeFlag != null) {
                predicates.add(cb.equal(root.get("activeFlag"), activeFlag));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<ShiftMaster> page = shiftMasterRepository.findAll(spec, pageable);

        List<ShiftMasterDTO> dtoList = page.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("total", page.getTotalElements());
        response.put("limit", safeLimit);
        response.put("offset", offset);
        response.put("data", dtoList);
        return response;
    }

    @Transactional(readOnly = true)
    public ShiftMasterDTO getShiftById(Long id) {
        ShiftMaster entity = shiftMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift Master not found with ID: " + id));
        return mapToDTO(entity);
    }

    @Transactional
    public ShiftMasterDTO createShift(ShiftMasterDTO dto) {
        String shiftCode = dto.getShiftCode() != null
                ? dto.getShiftCode().trim().toUpperCase()
                : "";
        if (shiftCode.isEmpty()) {
            shiftCode = null;
        } else if (shiftMasterRepository.existsByShiftCodeIgnoreCase(shiftCode)) {
            throw new IllegalArgumentException("Shift Code '" + shiftCode + "' already exists.");
        }

        ShiftMaster entity = ShiftMaster.builder()
                .shiftCode(shiftCode)
                .shiftName(dto.getShiftName() != null ? dto.getShiftName().trim() : "")
                .shiftType(dto.getShiftType() != null ? dto.getShiftType() : "Fixed")
                .description(dto.getDescription())
                .activeFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true)
                .companyId(dto.getCompanyId())
                .timingDetails(new ArrayList<>())
                .build();

        if (dto.getTimingDetails() != null) {
            for (ShiftTimingDetailDTO tdDto : dto.getTimingDetails()) {
                ShiftTimingDetail td = ShiftTimingDetail.builder()
                        .shiftMaster(entity)
                        .slotNumber(tdDto.getSlotNumber() != null ? tdDto.getSlotNumber() : 1)
                        .shiftInTime(tdDto.getInTime())
                        .shiftOutTime(tdDto.getOutTime())
                        .isNextDay(Boolean.TRUE.equals(tdDto.getIsNextDay()))
                        .breakDuration(tdDto.getBreakDuration() != null ? tdDto.getBreakDuration() : 0)
                        .breakStartTime(tdDto.getBreakStartTime())
                        .breakEndTime(tdDto.getBreakEndTime())
                        .build();
                td.calculateBreakMinutes();
                td.calculateWorkingHours();
                entity.addTimingDetail(td);
            }
        }

        entity.recalculateTotalHours();
        ShiftMaster saved = shiftMasterRepository.save(entity);
        return mapToDTO(saved);
    }

    @Transactional
    public ShiftMasterDTO updateShift(Long id, ShiftMasterDTO dto) {
        ShiftMaster entity = shiftMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift Master not found with ID: " + id));

        if (dto.getShiftCode() != null && !dto.getShiftCode().trim().isEmpty()) {
            String shiftCode = dto.getShiftCode().trim().toUpperCase();
            if (shiftMasterRepository.existsByShiftCodeIgnoreCaseAndIdNot(shiftCode, id)) {
                throw new IllegalArgumentException("Shift Code '" + shiftCode + "' is already in use by another shift.");
            }
            entity.setShiftCode(shiftCode);
        } else if (dto.getShiftCode() != null) {
            entity.setShiftCode(null);
        }

        if (dto.getShiftName() != null) entity.setShiftName(dto.getShiftName().trim());
        if (dto.getShiftType() != null) entity.setShiftType(dto.getShiftType());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getActiveFlag() != null) entity.setActiveFlag(dto.getActiveFlag());

        if (dto.getTimingDetails() != null) {
            entity.getTimingDetails().clear();
            for (ShiftTimingDetailDTO tdDto : dto.getTimingDetails()) {
                ShiftTimingDetail td = ShiftTimingDetail.builder()
                        .shiftMaster(entity)
                        .slotNumber(tdDto.getSlotNumber() != null ? tdDto.getSlotNumber() : 1)
                        .shiftInTime(tdDto.getInTime())
                        .shiftOutTime(tdDto.getOutTime())
                        .isNextDay(Boolean.TRUE.equals(tdDto.getIsNextDay()))
                        .breakDuration(tdDto.getBreakDuration() != null ? tdDto.getBreakDuration() : 0)
                        .breakStartTime(tdDto.getBreakStartTime())
                        .breakEndTime(tdDto.getBreakEndTime())
                        .build();
                td.calculateBreakMinutes();
                td.calculateWorkingHours();
                entity.addTimingDetail(td);
            }
        }

        entity.recalculateTotalHours();
        ShiftMaster updated = shiftMasterRepository.save(entity);
        return mapToDTO(updated);
    }

    @Transactional
    public void deleteShift(Long id) {
        if (!shiftMasterRepository.existsById(id)) {
            throw new RuntimeException("Shift Master not found with ID: " + id);
        }
        shiftMasterRepository.deleteById(id);
    }

    @Transactional
    public ShiftMasterDTO toggleStatus(Long id, Boolean activeFlag) {
        ShiftMaster entity = shiftMasterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift Master not found with ID: " + id));
        boolean targetStatus = activeFlag != null ? activeFlag : !Boolean.TRUE.equals(entity.getActiveFlag());
        entity.setActiveFlag(targetStatus);
        ShiftMaster saved = shiftMasterRepository.save(entity);
        return mapToDTO(saved);
    }

    private ShiftMasterDTO mapToDTO(ShiftMaster entity) {
        List<ShiftTimingDetailDTO> details = entity.getTimingDetails() != null
                ? entity.getTimingDetails().stream().map(d -> ShiftTimingDetailDTO.builder()
                .id(d.getId())
                .shiftMasterId(entity.getId())
                .slotNumber(d.getSlotNumber())
                .inTime(d.getShiftInTime())
                .outTime(d.getShiftOutTime())
                .isNextDay(d.getIsNextDay())
                .breakDuration(d.getBreakDuration())
                .breakStartTime(d.getBreakStartTime())
                .breakEndTime(d.getBreakEndTime())
                .totalBreakHours(d.getTotalBreakHours())
                .workingHours(d.getWorkingHours())
                .build()).collect(Collectors.toList())
                : new ArrayList<>();

        return ShiftMasterDTO.builder()
                .id(entity.getId())
                .shiftCode(entity.getShiftCode())
                .shiftName(entity.getShiftName())
                .shiftType(entity.getShiftType())
                .description(entity.getDescription())
                .activeFlag(entity.getActiveFlag())
                .totalWorkingHours(entity.getTotalWorkingHours())
                .timingDetails(details)
                .companyId(entity.getCompanyId())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null)
                .build();
    }
}
