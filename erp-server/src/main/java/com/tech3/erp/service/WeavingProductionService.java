package com.tech3.erp.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.WeavingProductionDTO;
import com.tech3.erp.dto.WeavingProductionDetailDTO;
import com.tech3.erp.entity.WeavingProduction;
import com.tech3.erp.entity.WeavingProductionDetail;
import com.tech3.erp.repository.WeavingProductionDetailRepository;
import com.tech3.erp.repository.WeavingProductionRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class WeavingProductionService {

    private final WeavingProductionRepository repository;
    private final WeavingProductionDetailRepository detailRepository;

    public WeavingProductionService(WeavingProductionRepository repository,
            WeavingProductionDetailRepository detailRepository) {
        this.repository = repository;
        this.detailRepository = detailRepository;
    }

    @Transactional
    public WeavingProductionDTO create(WeavingProductionDTO dto) {
        WeavingProduction entity = new WeavingProduction();
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        WeavingProduction saved = repository.save(entity);
        return mapEntityToDto(saved);
    }

    public WeavingProductionDTO getById(Long id) {
        WeavingProduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Weaving production not found"));
        return mapEntityToDto(entity);
    }

    public List<WeavingProductionDTO> getAll() {
        return repository.findAllByOrderByIdDesc().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WeavingProductionDTO> getAllWithPagination(Long offset, Long limit) {
        List<WeavingProductionDTO> list = getAll();
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<WeavingProductionDTO> getByCompanyId(Long companyId) {
        return repository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WeavingProductionDTO> getByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<WeavingProductionDTO> list = getByCompanyId(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    @Transactional
    public WeavingProductionDTO update(Long id, WeavingProductionDTO dto) {
        WeavingProduction entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Weaving production not found"));
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        repository.save(entity);
        return mapEntityToDto(entity);
    }

    @Transactional
    public WeavingProductionDTO updateByCompanyId(Long id, Long companyId, WeavingProductionDTO dto) {
        WeavingProduction entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Weaving production not found"));
        mapDtoToEntity(dto, entity);
        replaceDetails(entity, dto.getProductionDetails());
        repository.save(entity);
        return mapEntityToDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        WeavingProduction entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weaving production not found with id: " + id));
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Weaving production because it is referenced by other");
        }
    }

    @Transactional
    public void deleteByCompanyId(Long id, Long companyId) {
        WeavingProduction entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Weaving production not found with id: " + id));
        try {
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Weaving production because it is referenced by other");
        }
    }

    private void mapDtoToEntity(WeavingProductionDTO dto, WeavingProduction entity) {
        entity.setWeavingProductionDate(dto.getWeavingProductionDate());
        entity.setWeavingProductionNo(dto.getWeavingProductionNo());
        entity.setLoomId(dto.getLoomId());
        entity.setLoomNo(dto.getLoomNo());
        entity.setBeamNo(dto.getBeamNo());
        entity.setBeamId(dto.getBeamId());
        entity.setFlangeNo(dto.getFlangeNo());
        entity.setFlangeId(dto.getFlangeId());
        entity.setFabricCodeQuality(dto.getFabricCodeQuality());
        entity.setFabricMasterId(dto.getFabricMasterId());
        entity.setTotalPicks(dto.getTotalPicks());
        entity.setTotalMeters(dto.getTotalMeters());
        entity.setCompanyId(dto.getCompanyId());
    }

    private void replaceDetails(WeavingProduction entity, List<WeavingProductionDetailDTO> details) {
        entity.getProductionDetails().clear();
        if (details == null || details.isEmpty()) {
            return;
        }
        for (WeavingProductionDetailDTO dto : details) {
            WeavingProductionDetail detail = new WeavingProductionDetail();
            detail.setWeavingProduction(entity);
            detail.setShift(dto.getShift());
            detail.setProductionPicks(dto.getProductionPicks());
            detail.setProductionMeters(dto.getProductionMeters());
            entity.getProductionDetails().add(detail);
        }
    }

    private WeavingProductionDTO mapEntityToDto(WeavingProduction entity) {
        WeavingProductionDTO dto = new WeavingProductionDTO();
        dto.setId(entity.getId());
        dto.setWeavingProductionDate(entity.getWeavingProductionDate());
        dto.setWeavingProductionNo(entity.getWeavingProductionNo());
        dto.setLoomId(entity.getLoomId());
        dto.setLoomNo(entity.getLoomNo());
        dto.setBeamNo(entity.getBeamNo());
        dto.setBeamId(entity.getBeamId());
        dto.setFlangeNo(entity.getFlangeNo());
        dto.setFlangeId(entity.getFlangeId());
        dto.setFabricCodeQuality(entity.getFabricCodeQuality());
        dto.setFabricMasterId(entity.getFabricMasterId());
        dto.setTotalPicks(entity.getTotalPicks());
        dto.setTotalMeters(entity.getTotalMeters());
        dto.setCompanyId(entity.getCompanyId());

        List<WeavingProductionDetailDTO> detailDtos = detailRepository.findByWeavingProductionId(entity.getId()).stream()
                .sorted(Comparator.comparing(WeavingProductionDetail::getId))
                .map(this::mapDetailEntityToDto)
                .collect(Collectors.toList());
        dto.setProductionDetails(detailDtos);
        return dto;
    }

    private WeavingProductionDetailDTO mapDetailEntityToDto(WeavingProductionDetail entity) {
        WeavingProductionDetailDTO dto = new WeavingProductionDetailDTO();
        dto.setId(entity.getId());
        dto.setShift(entity.getShift());
        dto.setProductionPicks(entity.getProductionPicks());
        dto.setProductionMeters(entity.getProductionMeters());
        return dto;
    }
}
