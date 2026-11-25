package com.tech3.erp.service.impl;

import com.tech3.erp.dto.WeavingLiveProductionBulkUploadDTO;
import com.tech3.erp.dto.WeavingLiveProductionDTO;
import com.tech3.erp.entity.WeavingLiveProduction;
import com.tech3.erp.repository.WeavingLiveProductionRepository;
import com.tech3.erp.service.WeavingLiveProductionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeavingLiveProductionServiceImpl implements WeavingLiveProductionService {

    private final WeavingLiveProductionRepository repository;

    @Autowired
    public WeavingLiveProductionServiceImpl(WeavingLiveProductionRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<WeavingLiveProductionDTO> bulkUpload(WeavingLiveProductionBulkUploadDTO bulkUploadDTO) {
        if (bulkUploadDTO == null || bulkUploadDTO.getRecords() == null || bulkUploadDTO.getRecords().isEmpty()) {
            return new ArrayList<>();
        }

        Long companyId = bulkUploadDTO.getCompanyId();
        String fileName = bulkUploadDTO.getFileName();

        List<WeavingLiveProduction> entities = bulkUploadDTO.getRecords().stream().map(dto -> {
            WeavingLiveProduction entity = mapToEntity(dto);
            if (entity.getCompanyId() == null) {
                entity.setCompanyId(companyId);
            }
            if (entity.getFileName() == null || entity.getFileName().isEmpty()) {
                entity.setFileName(fileName);
            }
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            return entity;
        }).collect(Collectors.toList());

        List<WeavingLiveProduction> savedEntities = repository.saveAll(entities);

        return savedEntities.stream()
                .map(WeavingLiveProductionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<WeavingLiveProductionDTO> getByCompanyId(Long companyId) {
        List<WeavingLiveProduction> list = repository.findByCompanyId(companyId);
        return list.stream()
                .map(WeavingLiveProductionDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public WeavingLiveProductionDTO create(WeavingLiveProductionDTO dto) {
        WeavingLiveProduction entity = mapToEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        WeavingLiveProduction saved = repository.save(entity);
        return new WeavingLiveProductionDTO(saved);
    }

    @Override
    public WeavingLiveProductionDTO getById(Long id) {
        WeavingLiveProduction entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WeavingLiveProduction record not found with id: " + id));
        return new WeavingLiveProductionDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("WeavingLiveProduction record not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private WeavingLiveProduction mapToEntity(WeavingLiveProductionDTO dto) {
        WeavingLiveProduction entity = new WeavingLiveProduction();
        entity.setCompanyId(dto.getCompanyId());
        entity.setLoomId(dto.getLoomId());
        entity.setFabricMasterId(dto.getFabricMasterId());
        entity.setMachineName(dto.getMachineName());
        entity.setSortNo(dto.getSortNo());
        entity.setConstruction(dto.getConstruction());
        entity.setActualEfficiency(dto.getActualEfficiency());
        entity.setProductionEfficiency(dto.getProductionEfficiency());
        entity.setKiloPicks(dto.getKiloPicks());
        entity.setMeter(dto.getMeter());
        entity.setRpm(dto.getRpm());
        entity.setWarpLNo(dto.getWarpLNo());
        entity.setWarp(dto.getWarp());
        entity.setWarpSupplier(dto.getWarpSupplier());
        entity.setWarpTime(dto.getWarpTime());
        entity.setMinutesWarp(dto.getMinutesWarp());
        entity.setWeft(dto.getWeft());
        entity.setWeftLNo(dto.getWeftLNo());
        entity.setWeftSupplier(dto.getWeftSupplier());
        entity.setWeftTime(dto.getWeftTime());
        entity.setMinutesWeft(dto.getMinutesWeft());
        entity.setOthers(dto.getOthers());
        entity.setOthersStopTime(dto.getOthersStopTime());
        entity.setLongStops(dto.getLongStops());
        entity.setLongStopsTime(dto.getLongStopsTime());
        entity.setRunTime(dto.getRunTime());
        entity.setTotalStopTime(dto.getTotalStopTime());
        entity.setRunningCfm(dto.getRunningCfm());
        entity.setIdealCfm(dto.getIdealCfm());
        entity.setTcAndWeave(dto.getTcAndWeave());
        entity.setWpCmpx(dto.getWpCmpx());
        entity.setWtCmpx(dto.getWtCmpx());
        entity.setOtherStops(dto.getOtherStops());
        entity.setShortStops(dto.getShortStops());
        entity.setRunningHoursTime(dto.getRunningHoursTime());
        entity.setStopHoursTime(dto.getStopHoursTime());
        entity.setIncentive(dto.getIncentive());
        entity.setFileName(dto.getFileName());
        return entity;
    }
}
