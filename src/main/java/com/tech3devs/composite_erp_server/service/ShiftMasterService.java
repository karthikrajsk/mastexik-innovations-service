package com.tech3devs.composite_erp_server.service;

import com.tech3devs.composite_erp_server.dto.ShiftMasterDTO;
import com.tech3devs.composite_erp_server.dto.ShiftTimingDetailDTO;
import com.tech3devs.composite_erp_server.entity.ShiftMaster;
import com.tech3devs.composite_erp_server.entity.ShiftTimingDetail;
import com.tech3devs.composite_erp_server.repository.ShiftMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftMasterService {

    private final ShiftMasterRepository shiftMasterRepository;

    @Transactional(readOnly = true)
    public Map<String, Object> getAllShifts(String search, String shiftType, Boolean activeFlag, int limit, int offset) {
        int pageNumber = offset / Math.max(1, limit);
        Pageable pageable = PageRequest.of(pageNumber, limit, Sort.by(Sort.Direction.DESC, "updatedAt"));

        Page<ShiftMaster> page = shiftMasterRepository.searchShifts(
                search != null && !search.trim().isEmpty() ? search.trim() : null,
                shiftType != null && !"All".equalsIgnoreCase(shiftType) ? shiftType : null,
                activeFlag,
                pageable
        );

        List<ShiftMasterDTO> dtoList = page.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("total", page.getTotalElements());
        response.put("limit", limit);
        response.put("offset", offset);
        response.put("data", dtoList);
        return response;
    }

    @Transactional(readOnly = true)
    public ShiftMasterDTO getShiftById(Long id) {
        ShiftMaster entity = shiftMasterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Shift with ID " + id + " not found."));
        return mapToDTO(entity);
    }

    @Transactional
    public ShiftMasterDTO createShift(ShiftMasterDTO dto) {
        String shiftCode = dto.getShiftCode() != null ? dto.getShiftCode().trim().toUpperCase() : "";
        if (!shiftCode.isEmpty() && shiftMasterRepository.existsByShiftCodeIgnoreCase(shiftCode)) {
            throw new IllegalArgumentException("Shift Code \"" + shiftCode + "\" already exists.");
        }
        if (shiftCode.isEmpty()) shiftCode = null;

        ShiftMaster entity = ShiftMaster.builder()
                .shiftCode(shiftCode)
                .shiftName(dto.getShiftName() != null ? dto.getShiftName().trim() : "")
                .shiftType(dto.getShiftType() != null ? dto.getShiftType() : "Fixed")
                .description(dto.getDescription() != null ? dto.getDescription().trim() : "")
                .activeFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true)
                .companyId(dto.getCompanyId())
                .build();

        if (dto.getTimingDetails() != null) {
            for (ShiftTimingDetailDTO timingDTO : dto.getTimingDetails()) {
                ShiftTimingDetail timing = ShiftTimingDetail.builder()
                        .shiftMaster(entity)
                        .slotNumber(timingDTO.getSlotNumber())
                        .shiftInTime(timingDTO.getInTime())
                        .shiftOutTime(timingDTO.getOutTime())
                        .isNextDay(timingDTO.getIsNextDay() != null ? timingDTO.getIsNextDay() : false)
                        .breakDuration(timingDTO.getBreakDuration() != null ? timingDTO.getBreakDuration() : 0)
                        .build();
                timing.calculateWorkingHours();
                entity.addTimingDetail(timing);
            }
        }

        entity.recalculateTotalHours();
        ShiftMaster saved = shiftMasterRepository.save(entity);
        return mapToDTO(saved);
    }

    @Transactional
    public ShiftMasterDTO updateShift(Long id, ShiftMasterDTO dto) {
        ShiftMaster entity = shiftMasterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Shift with ID " + id + " not found."));

        String shiftCode = dto.getShiftCode() != null ? dto.getShiftCode().trim().toUpperCase() : null;
        if (shiftCode != null && !shiftCode.isEmpty()
                && shiftMasterRepository.existsByShiftCodeIgnoreCaseAndIdNot(shiftCode, id)) {
            throw new IllegalArgumentException("Shift Code \"" + shiftCode + "\" is already used by another shift.");
        }

        entity.setShiftCode(shiftCode == null || shiftCode.isEmpty() ? null : shiftCode);
        entity.setShiftName(dto.getShiftName() != null ? dto.getShiftName().trim() : entity.getShiftName());
        entity.setShiftType(dto.getShiftType() != null ? dto.getShiftType() : entity.getShiftType());
        entity.setDescription(dto.getDescription() != null ? dto.getDescription().trim() : entity.getDescription());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : entity.getActiveFlag());

        if (dto.getTimingDetails() != null) {
            entity.getTimingDetails().clear();
            for (ShiftTimingDetailDTO timingDTO : dto.getTimingDetails()) {
                ShiftTimingDetail timing = ShiftTimingDetail.builder()
                        .shiftMaster(entity)
                        .slotNumber(timingDTO.getSlotNumber())
                        .shiftInTime(timingDTO.getInTime())
                        .shiftOutTime(timingDTO.getOutTime())
                        .isNextDay(timingDTO.getIsNextDay() != null ? timingDTO.getIsNextDay() : false)
                        .breakDuration(timingDTO.getBreakDuration() != null ? timingDTO.getBreakDuration() : 0)
                        .build();
                timing.calculateWorkingHours();
                entity.addTimingDetail(timing);
            }
        }

        entity.recalculateTotalHours();
        ShiftMaster saved = shiftMasterRepository.save(entity);
        return mapToDTO(saved);
    }

    @Transactional
    public void deleteShift(Long id) {
        if (!shiftMasterRepository.existsById(id)) {
            throw new NoSuchElementException("Shift with ID " + id + " not found.");
        }
        shiftMasterRepository.deleteById(id);
    }

    @Transactional
    public ShiftMasterDTO toggleStatus(Long id, Boolean activeFlag) {
        ShiftMaster entity = shiftMasterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Shift with ID " + id + " not found."));

        entity.setActiveFlag(activeFlag != null ? activeFlag : !entity.getActiveFlag());
        ShiftMaster saved = shiftMasterRepository.save(entity);
        return mapToDTO(saved);
    }

    private ShiftMasterDTO mapToDTO(ShiftMaster entity) {
        List<ShiftTimingDetailDTO> timingDTOs = entity.getTimingDetails() != null
                ? entity.getTimingDetails().stream().map(t -> ShiftTimingDetailDTO.builder()
                        .id(t.getId())
                        .shiftMasterId(entity.getId())
                        .slotNumber(t.getSlotNumber())
                        .inTime(t.getShiftInTime())
                        .outTime(t.getShiftOutTime())
                        .isNextDay(t.getIsNextDay())
                        .breakDuration(t.getBreakDuration())
                        .workingHours(t.getWorkingHours())
                        .build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        return ShiftMasterDTO.builder()
                .id(entity.getId())
                .shiftCode(entity.getShiftCode())
                .shiftName(entity.getShiftName())
                .shiftType(entity.getShiftType())
                .description(entity.getDescription())
                .activeFlag(entity.getActiveFlag())
                .totalWorkingHours(entity.getTotalWorkingHours())
                .companyId(entity.getCompanyId())
                .timingDetails(timingDTOs)
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null)
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null)
                .build();
    }
}
