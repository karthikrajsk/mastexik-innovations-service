package com.tech3.erp.service;

import com.tech3.erp.dto.FlangeBeamInwardDTO;
import com.tech3.erp.dto.FlangeBeamInwardDetailDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Flange;
import com.tech3.erp.entity.FlangeBeamInward;
import com.tech3.erp.entity.FlangeBeamInwardDetail;
import com.tech3.erp.repository.FlangeBeamInwardRepository;
import com.tech3.erp.repository.FlangeRepository;
import com.tech3.erp.repository.SizingProductionDetailRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FlangeBeamInwardService {

    @PersistenceContext
    private EntityManager entityManager;

    private final FlangeBeamInwardRepository flangeBeamInwardRepository;
    private final FlangeRepository flangeRepository;
    private final SizingProductionDetailRepository sizingProductionDetailRepository;

    public FlangeBeamInwardService(FlangeBeamInwardRepository flangeBeamInwardRepository, FlangeRepository flangeRepository,
                                   SizingProductionDetailRepository sizingProductionDetailRepository) {
        this.flangeBeamInwardRepository = flangeBeamInwardRepository;
        this.flangeRepository = flangeRepository;
        this.sizingProductionDetailRepository = sizingProductionDetailRepository;
    }

    @Transactional
    public FlangeBeamInwardDTO createFlangeBeamInward(FlangeBeamInwardDTO dto) {
        FlangeBeamInward entity = mapDtoToEntity(dto, new FlangeBeamInward());
        applyDetailDtos(entity, dto.getFlangeDetails());
        FlangeBeamInward saved = flangeBeamInwardRepository.save(entity);
        markFlangesAsInwarded(saved);
        return mapEntityToDto(saved);
    }

    public List<FlangeBeamInwardDTO> getAllFlangeBeamInward() {
        return flangeBeamInwardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FlangeBeamInwardDTO> getAllFlangeBeamInwardWithPagination(Long offset, Long limit) {
        return PaginationHelper.paginate(getAllFlangeBeamInward(), offset, limit);
    }

    public List<FlangeBeamInwardDTO> getAllFlangeBeamInwardByCompany(Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return flangeBeamInwardRepository.findByCompanyIdOrderByIdDesc(companyId)
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FlangeBeamInwardDTO> getAllFlangeBeamInwardByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        return PaginationHelper.paginate(getAllFlangeBeamInwardByCompany(companyId), offset, limit);
    }

    public List<FlangeBeamInwardDTO> getAllFlangeBeamInwardByFactory(Long factoryCompanyId) {
        if (factoryCompanyId == null) {
            throw new IllegalArgumentException("Factory company ID is required");
        }
        return flangeBeamInwardRepository.findByFactoryCompanyIdOrderByIdDesc(factoryCompanyId)
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FlangeBeamInwardDTO> getAllFlangeBeamInwardByFactoryWithPagination(Long factoryCompanyId, Long offset, Long limit) {
        return PaginationHelper.paginate(getAllFlangeBeamInwardByFactory(factoryCompanyId), offset, limit);
    }

    public FlangeBeamInwardDTO getFlangeBeamInwardById(Long id) {
        FlangeBeamInward entity = flangeBeamInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id));
        return mapEntityToDto(entity);
    }

    public FlangeBeamInwardDTO getFlangeBeamInwardByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        FlangeBeamInward entity = flangeBeamInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id + " for company: " + companyId));
        return mapEntityToDto(entity);
    }

    public FlangeBeamInwardDTO getFlangeBeamInwardByFactory(Long factoryCompanyId, Long id) {
        if (factoryCompanyId == null) {
            throw new IllegalArgumentException("Factory company ID is required");
        }
        FlangeBeamInward entity = flangeBeamInwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id + " for factory company: " + factoryCompanyId));
        return mapEntityToDto(entity);
    }

    @Transactional
    public FlangeBeamInwardDTO updateFlangeBeamInward(Long id, FlangeBeamInwardDTO dto) {
        FlangeBeamInward existing = flangeBeamInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id));
        return updateEntity(existing, dto);
    }

    @Transactional
    public FlangeBeamInwardDTO updateFlangeBeamInwardByCompany(Long companyId, Long id, FlangeBeamInwardDTO dto) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        FlangeBeamInward existing = flangeBeamInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id + " for company: " + companyId));
        return updateEntity(existing, dto);
    }

    @Transactional
    public FlangeBeamInwardDTO updateFlangeBeamInwardByFactory(Long factoryCompanyId, Long id, FlangeBeamInwardDTO dto) {
        if (factoryCompanyId == null) {
            throw new IllegalArgumentException("Factory company ID is required");
        }
        FlangeBeamInward existing = flangeBeamInwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id + " for factory company: " + factoryCompanyId));
        return updateEntity(existing, dto);
    }

    @Transactional
    public void deleteFlangeBeamInward(Long id) {
        FlangeBeamInward entity = flangeBeamInwardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id));
        ensureFlangesNotMappedToSizingProduction(entity);
        List<Long> ids = extractFlangeIds(entity.getFlangeDetails());
        flangeBeamInwardRepository.delete(entity);
        unmarkFlanges(ids);
    }

    @Transactional
    public void deleteFlangeBeamInwardByCompany(Long companyId, Long id) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        FlangeBeamInward entity = flangeBeamInwardRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id + " for company: " + companyId));
        ensureFlangesNotMappedToSizingProduction(entity);
        List<Long> ids = extractFlangeIds(entity.getFlangeDetails());
        flangeBeamInwardRepository.delete(entity);
        unmarkFlanges(ids);
    }

    @Transactional
    public void deleteFlangeBeamInwardByFactory(Long factoryCompanyId, Long id) {
        if (factoryCompanyId == null) {
            throw new IllegalArgumentException("Factory company ID is required");
        }
        FlangeBeamInward entity = flangeBeamInwardRepository.findByIdAndFactoryCompanyId(id, factoryCompanyId)
                .orElseThrow(() -> new EntityNotFoundException("FlangeBeamInward not found with id: " + id + " for factory company: " + factoryCompanyId));
        ensureFlangesNotMappedToSizingProduction(entity);
        List<Long> ids = extractFlangeIds(entity.getFlangeDetails());
        flangeBeamInwardRepository.delete(entity);
        unmarkFlanges(ids);
    }

    private void ensureFlangesNotMappedToSizingProduction(FlangeBeamInward entity) {
        if (entity == null || entity.getFlangeDetails() == null) {
            return;
        }
        for (FlangeBeamInwardDetail detail : entity.getFlangeDetails()) {
            if (detail == null) {
                continue;
            }
            Long flangeId = detail.getFlangeId();
            String flangeNo = detail.getFlangeNo();
            if ((flangeNo == null || flangeNo.isBlank()) && flangeId != null) {
                flangeNo = flangeRepository.findById(flangeId)
                        .map(Flange::getFlangeNo)
                        .orElse(null);
            }
            String flangeReference = flangeNo != null && !flangeNo.isBlank()
                    ? flangeNo
                    : flangeId != null ? "ID " + flangeId : "Unknown flange";
            if (flangeId != null && sizingProductionDetailRepository.existsByFlangeId(flangeId)) {
                throw new IllegalStateException("Cannot delete flange inward: flange " + flangeReference + " already mapped in Sizing Production");
            }
            if (flangeNo != null && !flangeNo.isBlank() && sizingProductionDetailRepository.existsByFlangeNo(flangeNo)) {
                throw new IllegalStateException("Cannot delete flange inward: flange " + flangeReference + " already mapped in Sizing Production");
            }
        }
    }

    private FlangeBeamInwardDTO updateEntity(FlangeBeamInward existing, FlangeBeamInwardDTO dto) {
        List<Long> previousFlangeIds = extractFlangeIds(existing.getFlangeDetails());
        mapDtoToEntity(dto, existing);
        applyDetailDtos(existing, dto.getFlangeDetails());
        FlangeBeamInward saved = flangeBeamInwardRepository.save(existing);
        unmarkFlanges(previousFlangeIds);
        markFlangesAsInwarded(saved);
        return mapEntityToDto(saved);
    }

    private void applyDetailDtos(FlangeBeamInward entity, List<FlangeBeamInwardDetailDTO> detailDtos) {
        List<FlangeBeamInwardDetail> details = entity.getFlangeDetails();
        Map<Long, Integer> existingStatuses = details != null
                ? details.stream()
                        .filter(detail -> detail.getFlangeId() != null)
                        .collect(Collectors.toMap(FlangeBeamInwardDetail::getFlangeId,
                                FlangeBeamInwardDetail::getIsIssuedToSizingProduction,
                                (existing, replacement) -> existing))
                : Collections.emptyMap();
        if (details == null) {
            details = new ArrayList<>();
            entity.setFlangeDetails(details);
        } else {
            details.clear();
        }
        if (detailDtos != null) {
            for (FlangeBeamInwardDetailDTO detailDto : detailDtos) {
                FlangeBeamInwardDetail detail = new FlangeBeamInwardDetail();
                detail.setFlangeBeamInward(entity);
                detail.setFlangeId(detailDto.getFlangeId());
                detail.setFlangeNo(detailDto.getFlangeNo());
                Integer status = detailDto.getFlangeId() != null ? existingStatuses.get(detailDto.getFlangeId()) : null;
                detail.setIsIssuedToSizingProduction(status != null ? status : 0);
                details.add(detail);
            }
        }
        entity.setNoOfFlanges(details.size());
    }

    private void markFlangesAsInwarded(FlangeBeamInward entity) {
        List<Long> ids = extractFlangeIds(entity.getFlangeDetails());
        if (!ids.isEmpty()) {
            flangeRepository.markInwardedByIds(ids);
            entityManager.flush();
        }
    }

    private void unmarkFlanges(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            flangeRepository.unmarkInwardedByIds(ids);
            entityManager.flush();
        }
    }

    private List<Long> extractFlangeIds(List<FlangeBeamInwardDetail> details) {
        if (details == null) {
            return Collections.emptyList();
        }
        return details.stream()
                .map(FlangeBeamInwardDetail::getFlangeId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private FlangeBeamInward mapDtoToEntity(FlangeBeamInwardDTO dto, FlangeBeamInward entity) {
        entity.setFlangeInwardDate(dto.getFlangeInwardDate());
        entity.setFlangeInwardNo(dto.getFlangeInwardNo());
        entity.setOrderTypeId(dto.getOrderTypeId());
        entity.setBuyerId(dto.getBuyerId());
        entity.setSizingJobWorkOrderId(dto.getSizingJobWorkOrderId());
        if (dto.getFactoryCompanyId() != null) {
            entity.setFactoryCompanyId(dto.getFactoryCompanyId());
        }
        if (dto.getCompanyId() != null) {
            entity.setCompanyId(dto.getCompanyId());
        }
        if (dto.getNoOfFlanges() != null) {
            entity.setNoOfFlanges(dto.getNoOfFlanges());
        }
        return entity;
    }

    private FlangeBeamInwardDTO mapEntityToDto(FlangeBeamInward entity) {
        FlangeBeamInwardDTO dto = new FlangeBeamInwardDTO();
        dto.setId(entity.getId());
        dto.setFlangeInwardDate(entity.getFlangeInwardDate());
        dto.setFlangeInwardNo(entity.getFlangeInwardNo());
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setBuyerId(entity.getBuyerId());
        dto.setSizingJobWorkOrderId(entity.getSizingJobWorkOrderId());
        dto.setNoOfFlanges(entity.getNoOfFlanges());
        dto.setFactoryCompanyId(entity.getFactoryCompanyId());
        dto.setCompanyId(entity.getCompanyId());

        List<FlangeBeamInwardDetailDTO> detailDtos = new ArrayList<>();
        if (entity.getFlangeDetails() != null) {
            for (FlangeBeamInwardDetail detail : entity.getFlangeDetails()) {
                FlangeBeamInwardDetailDTO detailDto = new FlangeBeamInwardDetailDTO();
                detailDto.setId(detail.getId());
                detailDto.setFlangeId(detail.getFlangeId());
                detailDto.setFlangeNo(detail.getFlangeNo());
                detailDto.setIsIssuedToSizingProduction(detail.getIsIssuedToSizingProduction());
                detailDtos.add(detailDto);
            }
        }
        dto.setFlangeDetails(detailDtos);
        return dto;
    }
}
