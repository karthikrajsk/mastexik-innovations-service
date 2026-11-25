package com.tech3.erp.service.impl;

import com.tech3.erp.dto.BeamStockDTO;
import com.tech3.erp.entity.BeamStock;
import com.tech3.erp.entity.BeamGatingDetails;
import com.tech3.erp.repository.BeamGatingDetailsRepository;
import com.tech3.erp.repository.BeamStockRepository;
import com.tech3.erp.service.BeamStockService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BeamStockServiceImpl implements BeamStockService {

    private final BeamStockRepository beamStockRepository;
    private final BeamGatingDetailsRepository beamGatingDetailsRepository;

    public BeamStockServiceImpl(BeamStockRepository beamStockRepository,
                                BeamGatingDetailsRepository beamGatingDetailsRepository) {
        this.beamStockRepository = beamStockRepository;
        this.beamGatingDetailsRepository = beamGatingDetailsRepository;
    }

    @Override
    @Transactional
    public BeamStockDTO saveBeamStock(BeamStockDTO dto) {
        BeamStock entity = convertToEntity(dto);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        BeamStock saved = beamStockRepository.save(entity);
        return convertToDTO(saved);
    }

    @Override
    @Transactional
    public BeamStockDTO.BulkUploadResponse saveBulkBeamStock(BeamStockDTO.BulkUploadRequest request) {
        if (request == null || request.getRecords() == null || request.getRecords().isEmpty()) {
            return new BeamStockDTO.BulkUploadResponse(0, 0, "No records provided for upload.");
        }

        List<BeamStock> entitiesToSave = new ArrayList<>();
        int totalRows = request.getRecords().size();

        for (BeamStockDTO dto : request.getRecords()) {
            if (dto.getSizedBeamNo() == null || dto.getSizedBeamNo().trim().isEmpty()) {
                continue;
            }

            if (request.getCompanyId() != null && dto.getCompanyId() == null) {
                dto.setCompanyId(request.getCompanyId());
            }

            BeamStock entity = convertToEntity(dto);
            entity.setCreatedAt(LocalDateTime.now());
            entity.setUpdatedAt(LocalDateTime.now());
            entitiesToSave.add(entity);
        }

        List<BeamStock> savedEntities = beamStockRepository.saveAll(entitiesToSave);
        int successCount = savedEntities.size();

        return new BeamStockDTO.BulkUploadResponse(
                totalRows,
                successCount,
                "Successfully uploaded " + successCount + " beam stock records to database."
        );
    }

    @Override
    @Transactional
    public BeamStockDTO.BulkUploadResponse updateOnLoomMtrBulk(BeamStockDTO.OnLoomMtrUpdateRequest request) {
        if (request == null || request.getRecords() == null || request.getRecords().isEmpty()) {
            return new BeamStockDTO.BulkUploadResponse(0, 0, "No records provided for update.");
        }

        int totalRows = request.getRecords().size();
        int updatedCount = 0;

        for (BeamStockDTO.OnLoomMtrRow row : request.getRecords()) {
            if (row.getSizedBeamNo() == null || row.getSizedBeamNo().trim().isEmpty()) {
                continue;
            }
            String sizedBeamNo = row.getSizedBeamNo().trim();
            Double onLoomMtr = row.getOnLoomMtr();

            List<BeamStock> stockList;
            if (request.getCompanyId() != null && request.getCompanyId() > 0) {
                stockList = beamStockRepository.findBySizedBeamNoAndCompanyId(sizedBeamNo, request.getCompanyId());
            } else {
                stockList = beamStockRepository.findBySizedBeamNo(sizedBeamNo);
            }

            if (stockList != null && !stockList.isEmpty()) {
                for (BeamStock stock : stockList) {
                    stock.setOnLoomMtr(onLoomMtr);
                    stock.setUpdatedAt(LocalDateTime.now());
                }
                beamStockRepository.saveAll(stockList);
                updatedCount++;
            }

            List<BeamGatingDetails> gatingDetailsList = beamGatingDetailsRepository.findByBeamNo(sizedBeamNo);
            if (gatingDetailsList != null && !gatingDetailsList.isEmpty()) {
                for (BeamGatingDetails detail : gatingDetailsList) {
                    detail.setOnLoomMtr(onLoomMtr);
                }
                beamGatingDetailsRepository.saveAll(gatingDetailsList);
            }
        }

        return new BeamStockDTO.BulkUploadResponse(
                totalRows,
                updatedCount,
                "Successfully updated On-Loom Mtr for " + updatedCount + " beam stock records."
        );
    }

    @Override
    public List<BeamStockDTO> getAllBeamStock() {
        return beamStockRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Object getAllBeamStockWithPagination(Long offset, Long limit) {
        Long safeOffset = (offset == null || offset < 0) ? 0L : offset;
        Long safeLimit = (limit == null || limit <= 0) ? 10L : limit;
        int pageNumber = (int) (safeOffset / safeLimit);
        Pageable pageable = PageRequest.of(pageNumber, safeLimit.intValue());
        Page<BeamStock> page = beamStockRepository.findAllByOrderByIdDesc(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", page.getContent().stream().map(this::convertToDTO).collect(Collectors.toList()));
        response.put("total", page.getTotalElements());
        response.put("offset", safeOffset);
        response.put("limit", safeLimit);

        return response;
    }

    @Override
    public List<BeamStockDTO> getAllBeamStockByCompany(Long companyId) {
        return beamStockRepository.findByCompanyIdOrderByIdDesc(companyId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Object getAllBeamStockByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        Long safeOffset = (offset == null || offset < 0) ? 0L : offset;
        Long safeLimit = (limit == null || limit <= 0) ? 10L : limit;
        int pageNumber = (int) (safeOffset / safeLimit);
        Pageable pageable = PageRequest.of(pageNumber, safeLimit.intValue());
        Page<BeamStock> page = beamStockRepository.findByCompanyIdOrderByIdDesc(companyId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", page.getContent().stream().map(this::convertToDTO).collect(Collectors.toList()));
        response.put("total", page.getTotalElements());
        response.put("offset", safeOffset);
        response.put("limit", safeLimit);

        return response;
    }

    @Override
    public BeamStockDTO getBeamStockById(Long id) {
        BeamStock entity = beamStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Beam stock not found with ID: " + id));
        return convertToDTO(entity);
    }

    @Override
    @Transactional
    public BeamStockDTO updateBeamStock(Long id, BeamStockDTO dto) {
        BeamStock existing = beamStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Beam stock not found with ID: " + id));

        BeanUtils.copyProperties(dto, existing, "id", "createdAt");
        existing.setUpdatedAt(LocalDateTime.now());

        BeamStock updated = beamStockRepository.save(existing);
        return convertToDTO(updated);
    }

    @Override
    @Transactional
    public void deleteBeamStock(Long id) {
        if (!beamStockRepository.existsById(id)) {
            throw new EntityNotFoundException("Beam stock not found with ID: " + id);
        }
        beamStockRepository.deleteById(id);
    }

    private BeamStock convertToEntity(BeamStockDTO dto) {
        BeamStock entity = new BeamStock();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    private BeamStockDTO convertToDTO(BeamStock entity) {
        BeamStockDTO dto = new BeamStockDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
