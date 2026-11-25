package com.tech3.erp.service;

import com.tech3.erp.dto.BeamCutBeamDetailsDTO;
import com.tech3.erp.dto.BeamCutDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.BeamCut;
import com.tech3.erp.entity.BeamCutBeamDetails;
import com.tech3.erp.repository.BeamCutRepository;
import com.tech3.erp.repository.BeamGatingDetailsRepository;
import com.tech3.erp.repository.LoomMasterRepository;
import com.tech3.erp.repository.FlangeRepository;
import com.tech3.erp.repository.FactoryBeamInwardBeamDetailsRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeamCutService {

    private final BeamCutRepository repository;
    private final LoomMasterRepository loomMasterRepository;
    private final FlangeRepository flangeRepository;
    private final BeamGatingDetailsRepository beamGatingDetailsRepository;
    private final FactoryBeamInwardBeamDetailsRepository factoryBeamDetailsRepository;

    public BeamCutService(BeamCutRepository repository,
                          BeamGatingDetailsRepository beamGatingDetailsRepository,
                          LoomMasterRepository loomMasterRepository,
                          FlangeRepository flangeRepository,
                          FactoryBeamInwardBeamDetailsRepository factoryBeamDetailsRepository) {
        this.repository = repository;
        this.loomMasterRepository = loomMasterRepository;
        this.flangeRepository = flangeRepository;
        this.beamGatingDetailsRepository = beamGatingDetailsRepository;
        this.factoryBeamDetailsRepository = factoryBeamDetailsRepository;
    }

    @Transactional
    public BeamCutDTO create(BeamCutDTO dto) {
        BeamCut entity = mapDtoToEntity(dto, new BeamCut());
        BeamCut saved = repository.save(entity);
        if (saved.getLoomId() != null) {
            loomMasterRepository.resetBeamGatingById(saved.getLoomId());
        }
    
        markBeamGatingDetailsAsCut(saved);
        return getById(saved.getId());
    }

    public BeamCutDTO getById(Long id) {
        BeamCut entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("BeamCut not found"));
        return mapEntityToDto(entity);
    }

    public List<BeamCutDTO> getAll() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(BeamCut::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BeamCutDTO> getAllWithPagination(Long offset, Long limit) {
        List<BeamCutDTO> list = getAll();
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<BeamCutDTO> getByCompanyId(Long companyId) {
        return repository.findByCompanyId(companyId).stream()
                .sorted(Comparator.comparing(BeamCut::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BeamCutDTO> getByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<BeamCutDTO> list = getByCompanyId(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    @Transactional
    public BeamCutDTO update(Long id, BeamCutDTO dto) {
        BeamCut entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("BeamCut not found"));
        mapDtoToEntity(dto, entity);
        repository.save(entity);
        markBeamGatingDetailsAsCut(entity);
        return getById(id);
    }

    @Transactional
    public BeamCutDTO updateByCompanyId(Long id, Long companyId, BeamCutDTO dto) {
        BeamCut entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("BeamCut not found"));
        mapDtoToEntity(dto, entity);
        repository.save(entity);
        markBeamGatingDetailsAsCut(entity);
        return getById(id);
    }

    @Transactional
    public void delete(Long id) {
        BeamCut entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BeamCut not found with id: " + id));
        try {
            resetFactoryBeamCutStatus(entity);
            resetBeamGatingDetailsCutStatus(entity);
            resetLoomBeamGatingStatus(entity);
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete BeamCut because it is referenced by other");
        }
    }

    @Transactional
    public void deleteByCompanyId(Long id, Long companyId) {
        BeamCut entity = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("BeamCut not found with id: " + id));
        try {
            resetFactoryBeamCutStatus(entity);
            resetBeamGatingDetailsCutStatus(entity);
            resetLoomBeamGatingStatus(entity);
            repository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete BeamCut because it is referenced by other");
        }
    }

    private void markBeamGatingDetailsAsCut(BeamCut saved) {
        if (saved == null || saved.getBeamDetails() == null || saved.getBeamDetails().isEmpty()) {
            return;
        }
        for (BeamCutBeamDetails detail : saved.getBeamDetails()) {
            Long beamId = detail.getBeamId();
            String beamNo = detail.getBeamNo();
            if (beamId == null && (beamNo == null || beamNo.isEmpty())) {
                continue;
            }
            beamGatingDetailsRepository.markBeamCutByBeamIdOrBeamNo(beamId, beamNo);
            markFactoryBeamCutStatus(beamId, 1);
            if (detail.getRemainingMeters() != null) {
                beamGatingDetailsRepository.updateRemainingMetersByBeamIdOrBeamNo(beamId, beamNo, detail.getRemainingMeters());
            }
        }
    }

    private void markFactoryBeamCutStatus(Long beamId, Integer status) {
        if (beamId == null) {
            return;
        }
        factoryBeamDetailsRepository.updateBeamCutStatus(beamId, status);
    }

    private void resetFactoryBeamCutStatus(BeamCut entity) {
        if (entity == null || entity.getBeamDetails() == null || entity.getBeamDetails().isEmpty()) {
            return;
        }
        for (BeamCutBeamDetails detail : entity.getBeamDetails()) {
            markFactoryBeamCutStatus(detail.getBeamId(), 0);
        }
    }

    private void resetLoomBeamGatingStatus(BeamCut entity) {
        if (entity == null || entity.getLoomId() == null) {
            return;
        }
        loomMasterRepository.setBeamGatingById(entity.getLoomId());
    }

    private void resetBeamGatingDetailsCutStatus(BeamCut entity) {
        if (entity == null || entity.getBeamDetails() == null || entity.getBeamDetails().isEmpty()) {
            return;
        }
        for (BeamCutBeamDetails detail : entity.getBeamDetails()) {
            Long beamId = detail.getBeamId();
            String beamNo = detail.getBeamNo();
            if (beamId == null && (beamNo == null || beamNo.isEmpty())) {
                continue;
            }
            beamGatingDetailsRepository.resetBeamCutByBeamIdOrBeamNo(beamId, beamNo);
        }
    }

    private BeamCut mapDtoToEntity(BeamCutDTO dto, BeamCut entity) {
        if (dto.getBeamCutDate() != null) entity.setBeamCutDate(dto.getBeamCutDate());
        if (dto.getLoomId() != null) entity.setLoomId(dto.getLoomId());
        if (dto.getBeamCutNo() != null) entity.setBeamCutNo(dto.getBeamCutNo());
        if (dto.getCompanyId() != null) entity.setCompanyId(dto.getCompanyId());
        if (dto.getRemarks() != null) entity.setRemarks(dto.getRemarks());
        entity.getBeamDetails().clear();
        if (dto.getBeamDetails() != null) {
            for (BeamCutBeamDetailsDTO detailDto : dto.getBeamDetails()) {
                BeamCutBeamDetails detail = new BeamCutBeamDetails();
                detail.setBeamCut(entity);
                detail.setBeamNo(detailDto.getBeamNo());
                detail.setFlangeId(detailDto.getFlangeId());
                detail.setBeamId(detailDto.getBeamId());
                detail.setFabricMasterId(detailDto.getFabricMasterId());
                detail.setRemainingMeters(detailDto.getRemainingMeters());
                detail.setUsedMeters(detailDto.getUsedMeters());
                detail.setWidth(detailDto.getWidth());
                detail.setTotalEnds(detailDto.getTotalEnds());
                entity.getBeamDetails().add(detail);
            }
        }
        return entity;
    }

    private BeamCutDTO mapEntityToDto(BeamCut entity) {
        BeamCutDTO dto = new BeamCutDTO();
        dto.setId(entity.getId());
        dto.setBeamCutDate(entity.getBeamCutDate());
        dto.setLoomId(entity.getLoomId());
        dto.setBeamCutNo(entity.getBeamCutNo());
        dto.setCompanyId(entity.getCompanyId());
        dto.setRemarks(entity.getRemarks());
        if (entity.getBeamDetails() != null && !entity.getBeamDetails().isEmpty()) {
            dto.setBeamDetails(entity.getBeamDetails().stream().map(detail -> {
                BeamCutBeamDetailsDTO detailDto = new BeamCutBeamDetailsDTO();
                detailDto.setBeamNo(detail.getBeamNo());
                detailDto.setFlangeId(detail.getFlangeId());
                detailDto.setBeamId(detail.getBeamId());
                detailDto.setFabricMasterId(detail.getFabricMasterId());
                detailDto.setRemainingMeters(detail.getRemainingMeters());
                detailDto.setUsedMeters(detail.getUsedMeters());
                detailDto.setWidth(detail.getWidth());
                detailDto.setTotalEnds(detail.getTotalEnds());
                return detailDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }
}
