package com.tech3.erp.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.WarpingProductionDTO;
import com.tech3.erp.dto.WarpingProductionDetailDTO;
import com.tech3.erp.dto.WarpingProductionSummaryDTO;
import com.tech3.erp.entity.SizingJobWorkOrder;
import com.tech3.erp.entity.SizingPlan;
import com.tech3.erp.entity.WarpingProduction;
import com.tech3.erp.entity.WarpingProductionDetail;
import com.tech3.erp.repository.SizingJobWorkOrderRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.WarpingProductionDetailRepository;
import com.tech3.erp.repository.WarpingProductionRepository;
import com.tech3.erp.service.ReferenceValidationService;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WarpingProductionService {

    private final WarpingProductionRepository repository;
    private final WarpingProductionDetailRepository detailRepository;
    private final SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    private final SizingPlanRepository sizingPlanRepository;
    private final ReferenceValidationService referenceValidationService;

    public WarpingProductionService(WarpingProductionRepository repository,
            WarpingProductionDetailRepository detailRepository,
            SizingJobWorkOrderRepository sizingJobWorkOrderRepository,
            SizingPlanRepository sizingPlanRepository,
            ReferenceValidationService referenceValidationService) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.sizingJobWorkOrderRepository = sizingJobWorkOrderRepository;
        this.sizingPlanRepository = sizingPlanRepository;
        this.referenceValidationService = referenceValidationService;
    }

    @Transactional
    public WarpingProductionDTO create(WarpingProductionDTO dto) {
        requireSizingOrderReference(dto.getSizingOrderId());
        requireSizingPlanReference(dto.getSizingPlanId());
        WarpingProduction entity = new WarpingProduction();
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        WarpingProduction saved = repository.save(entity);
        return mapEntityToDto(saved);
    }

    public WarpingProductionDTO getById(Long id) {
        WarpingProduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warping production not found"));
        return mapEntityToDto(entity);
    }

    public List<WarpingProductionDTO> getAll() {
        return repository.findAllByOrderByIdDesc().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WarpingProductionDTO> getAllWithPagination(Long offset, Long limit) {
        List<WarpingProductionDTO> list = getAll();
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<WarpingProductionDTO> getByCompanyId(Long companyId) {
        return repository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WarpingProductionDTO> getByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<WarpingProductionDTO> list = getByCompanyId(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    @Transactional
    public WarpingProductionDTO update(Long id, WarpingProductionDTO dto) {
        WarpingProduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Warping production not found"));
        requireSizingOrderReference(dto.getSizingOrderId());
        requireSizingPlanReference(dto.getSizingPlanId());
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        repository.save(entity);
        return mapEntityToDto(entity);
    }

    @Transactional
    public WarpingProductionDTO updateByCompanyId(Long id, Long companyId, WarpingProductionDTO dto) {
        WarpingProduction entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Warping production not found"));
        requireSizingOrderReference(dto.getSizingOrderId());
        requireSizingPlanReference(dto.getSizingPlanId());
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        repository.save(entity);
        return mapEntityToDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        WarpingProduction entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Warping production not found with id: " + id));
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Warping production because it is referenced by other");
        }
    }

    @Transactional
    public void deleteByCompanyId(Long id, Long companyId) {
        WarpingProduction entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Warping production not found with id: " + id));
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Warping production because it is referenced by other");
        }
    }

    private void mapDtoToEntity(WarpingProductionDTO dto, WarpingProduction entity) {
        entity.setWarpingDate(dto.getWarpingDate());
        entity.setSizingOrderId(dto.getSizingOrderId());
        entity.setSizingPlanId(dto.getSizingPlanId());
        entity.setBuyerName(dto.getBuyerName());
        entity.setBuyerId(dto.getBuyerId());
        entity.setManualSetNo(dto.getManualSetNo());
        entity.setWarpingProductionNo(dto.getWarpingProductionNo());
        entity.setParts(dto.getParts());
        entity.setEndsPerPart(dto.getEndsPerPart());
        entity.setMachineId(dto.getMachineId());
        entity.setYarnName(dto.getYarnName());
        entity.setYarnId(dto.getYarnId());
        entity.setTotalMeters(dto.getTotalMeters());
        entity.setNetWeight(dto.getNetWeight());
        entity.setCompanyId(dto.getCompanyId());

        WarpingProductionSummaryDTO summary = dto.getSummary();
        if (summary != null) {
            entity.setActualMetersAverage(summary.getActualMetersAverage());
            entity.setTotalActualMeters(summary.getTotalActualMeters());
            entity.setTotalEnds(summary.getTotalEnds());
            entity.setTotalNetWeight(summary.getTotalNetWeight());
            entity.setBreakagesPerMillion(summary.getBreakagesPerMillion());
            entity.setWarpCount(summary.getWarpCount());
        }
    }

    private void replaceDetails(WarpingProduction entity, List<WarpingProductionDetailDTO> details) {
        entity.getProductionDetails().clear();
        if (details == null || details.isEmpty()) {
            return;
        }
        for (WarpingProductionDetailDTO dto : details) {
            WarpingProductionDetail detail = new WarpingProductionDetail();
            detail.setWarpingProduction(entity);
            detail.setShift(dto.getShift());
            detail.setShiftName(dto.getShiftName());
            detail.setWarpBeamNo(dto.getWarpBeamNo());
            detail.setEndsPerPart(dto.getEndsPerPart());
            detail.setStartTime(dto.getStartTime());
            detail.setEndTime(dto.getEndTime());
            detail.setActualMeters(dto.getActualMeters());
            detail.setBreakages(dto.getBreakages());
            detail.setRpm(dto.getRpm());
            detail.setTareWt(dto.getTareWt());
            detail.setGrossWt(dto.getGrossWt());
            detail.setNetWt(dto.getNetWt());
            detail.setEmployeeId(dto.getEmployeeId());
            entity.getProductionDetails().add(detail);
        }
    }

    private void requireSizingOrderReference(Long sizingOrderId) {
        if (sizingOrderId == null) {
            return;
        }
        Optional<SizingJobWorkOrder> sizingOrder = sizingJobWorkOrderRepository.findById(sizingOrderId);
        String displayIdentifier = sizingOrder
                .map(SizingJobWorkOrder::getSizingWorkOrderNo)
                .filter(StringUtils::hasText)
                .orElse("Sizing Order ID " + sizingOrderId);
        referenceValidationService.requireReference(
                sizingOrderId,
                () -> sizingOrder,
                "Sizing Order",
                displayIdentifier);
    }

    private void requireSizingPlanReference(Long sizingPlanId) {
        if (sizingPlanId == null) {
            return;
        }
        Optional<SizingPlan> sizingPlan = sizingPlanRepository.findById(sizingPlanId);
        String displayIdentifier = sizingPlan
                .map(SizingPlan::getSizingPlanNo)
                .filter(StringUtils::hasText)
                .orElse("Sizing Plan ID " + sizingPlanId);
        referenceValidationService.requireReference(
                sizingPlanId,
                () -> sizingPlan,
                "Sizing Plan",
                displayIdentifier);
    }

    private WarpingProductionDTO mapEntityToDto(WarpingProduction entity) {
        WarpingProductionDTO dto = new WarpingProductionDTO();
        dto.setId(entity.getId());
        dto.setWarpingDate(entity.getWarpingDate());
        dto.setSizingOrderId(entity.getSizingOrderId());
        dto.setSizingPlanId(entity.getSizingPlanId());
        dto.setBuyerName(entity.getBuyerName());
        dto.setBuyerId(entity.getBuyerId());
        dto.setManualSetNo(entity.getManualSetNo());
        dto.setWarpingProductionNo(entity.getWarpingProductionNo());
        dto.setParts(entity.getParts());
        dto.setEndsPerPart(entity.getEndsPerPart());
        dto.setMachineId(entity.getMachineId());
        dto.setYarnName(entity.getYarnName());
        dto.setYarnId(entity.getYarnId());
        dto.setTotalMeters(entity.getTotalMeters());
        dto.setNetWeight(entity.getNetWeight());
        dto.setCompanyId(entity.getCompanyId());

        WarpingProductionSummaryDTO summary = buildSummary(entity);
        dto.setSummary(summary);

        List<WarpingProductionDetailDTO> detailDtos = detailRepository.findByWarpingProductionId(entity.getId()).stream()
                .sorted(Comparator.comparing(WarpingProductionDetail::getId))
                .map(this::mapDetailEntityToDto)
                .collect(Collectors.toList());
        dto.setProductionDetails(detailDtos);
        return dto;
    }

    private WarpingProductionSummaryDTO buildSummary(WarpingProduction entity) {
        WarpingProductionSummaryDTO summary = new WarpingProductionSummaryDTO();
        summary.setActualMetersAverage(entity.getActualMetersAverage());
        summary.setTotalActualMeters(entity.getTotalActualMeters());
        summary.setTotalEnds(entity.getTotalEnds());
        summary.setTotalNetWeight(entity.getTotalNetWeight());
        summary.setBreakagesPerMillion(entity.getBreakagesPerMillion());
        summary.setWarpCount(entity.getWarpCount());

        boolean hasData = summary.getActualMetersAverage() != null
                || summary.getTotalActualMeters() != null
                || summary.getTotalEnds() != null
                || summary.getTotalNetWeight() != null
                || summary.getBreakagesPerMillion() != null
                || summary.getWarpCount() != null;
        return hasData ? summary : null;
    }

    private WarpingProductionDetailDTO mapDetailEntityToDto(WarpingProductionDetail entity) {
        WarpingProductionDetailDTO dto = new WarpingProductionDetailDTO();
        dto.setId(entity.getId());
        dto.setShift(entity.getShift());
        dto.setShiftName(entity.getShiftName());
        dto.setWarpBeamNo(entity.getWarpBeamNo());
        dto.setEndsPerPart(entity.getEndsPerPart());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setActualMeters(entity.getActualMeters());
        dto.setBreakages(entity.getBreakages());
        dto.setRpm(entity.getRpm());
        dto.setTareWt(entity.getTareWt());
        dto.setGrossWt(entity.getGrossWt());
        dto.setNetWt(entity.getNetWt());
        dto.setEmployeeId(entity.getEmployeeId());
        return dto;
    }
}
