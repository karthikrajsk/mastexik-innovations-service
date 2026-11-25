package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.RequestFormDTO;
import com.tech3.erp.entity.RequestForm;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.RequestFormRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestFormService {

    private final RequestFormRepository requestFormRepository;
    private final CompanyRepository companyRepository;

    public RequestFormService(RequestFormRepository requestFormRepository, CompanyRepository companyRepository) {
        this.requestFormRepository = requestFormRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public RequestFormDTO saveRequestForm(RequestFormDTO dto) {
        validateRequestForm(dto);
        RequestForm requestForm = mapDtoToEntity(dto, new RequestForm());
        requestForm.setLastUpdated(LocalDateTime.now());
        RequestForm saved = requestFormRepository.save(requestForm);
        return mapEntityToDto(saved);
    }

    public PaginatedResponse<RequestFormDTO> getAllRequestFormsWithPagination(Long offset, Long limit) {
        List<RequestFormDTO> allRequests = requestFormRepository.findAll().stream()
                .filter(r -> r.getActiveFlag() == null || r.getActiveFlag())
                .sorted(Comparator.comparing(RequestForm::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());

        return PaginationHelper.paginate(allRequests, offset, limit);
    }

    public RequestFormDTO getRequestFormById(Long id) {
        RequestForm requestForm = requestFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RequestForm not found with id: " + id));
        return mapEntityToDto(requestForm);
    }

    @Transactional
    public RequestFormDTO updateRequestForm(Long id, RequestFormDTO dto) {
        validateRequestForm(dto);
        RequestForm existing = requestFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RequestForm not found with id: " + id));

        mapDtoToEntity(dto, existing);
        existing.setLastUpdated(LocalDateTime.now());
        RequestForm saved = requestFormRepository.save(existing);
        return mapEntityToDto(saved);
    }

    @Transactional
    public void deleteRequestForm(Long id) {
        RequestForm existing = requestFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RequestForm not found with id: " + id));
        existing.setActiveFlag(false);
        requestFormRepository.save(existing);
    }

    private void validateRequestForm(RequestFormDTO dto) {
        if (dto.getRequestType() != null && !dto.getRequestType().equals("Submission")) {
            if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity is mandatory for SMS and BULK types");
            }
        }
    }

    private RequestForm mapDtoToEntity(RequestFormDTO dto, RequestForm entity) {
        entity.setDate(dto.getDate());
        entity.setRequestNo(dto.getRequestNo());
        entity.setCustomerInquiryNo(dto.getCustomerInquiryNo());
        entity.setBuyerName(dto.getBuyerName());
        entity.setToMillId(dto.getToMillId());
        entity.setFabricItemCode(dto.getFabricItemCode());
        entity.setConstruction(dto.getConstruction());
        entity.setCategory(dto.getCategory());
        entity.setRequestType(dto.getRequestType());
        entity.setUom(dto.getUom());
        entity.setQuantity(dto.getQuantity());
        entity.setStatus(dto.getStatus());
        entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getActiveFlag() != null) {
            entity.setActiveFlag(dto.getActiveFlag());
        }

        // Map rounds
        if (dto.getRounds() != null) {
            if (entity.getRounds() == null) {
                entity.setRounds(new java.util.ArrayList<>());
            } else {
                entity.getRounds().clear();
            }
            for (com.tech3.erp.dto.RequestFormRoundDTO roundDto : dto.getRounds()) {
                com.tech3.erp.entity.RequestFormRound round = new com.tech3.erp.entity.RequestFormRound();
                round.setRequestForm(entity);
                round.setRoundNumber(roundDto.getRoundNumber());
                round.setReadyDate(roundDto.getReadyDate());
                round.setCourierName(roundDto.getCourierName());
                round.setCourierNumber(roundDto.getCourierNumber());
                round.setNoOfItems(roundDto.getNoOfItems());

                if (roundDto.getItems() != null) {
                    java.util.List<com.tech3.erp.entity.RequestFormRoundItem> items = new java.util.ArrayList<>();
                    for (com.tech3.erp.dto.RequestFormRoundItemDTO itemDto : roundDto.getItems()) {
                        com.tech3.erp.entity.RequestFormRoundItem item = new com.tech3.erp.entity.RequestFormRoundItem();
                        item.setRound(round);
                        item.setItem(itemDto.getItem());
                        item.setCustomerInstruction(itemDto.getCustomerInstruction());
                        item.setApprovalStatus(itemDto.getApprovalStatus());
                        items.add(item);
                    }
                    round.setItems(items);
                }
                entity.getRounds().add(round);
            }
        }

        return entity;
    }

    private RequestFormDTO mapEntityToDto(RequestForm entity) {
        RequestFormDTO dto = new RequestFormDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setRequestNo(entity.getRequestNo());
        dto.setCustomerInquiryNo(entity.getCustomerInquiryNo());
        dto.setBuyerName(entity.getBuyerName());
        dto.setToMillId(entity.getToMillId());
        dto.setFabricItemCode(entity.getFabricItemCode());
        dto.setConstruction(entity.getConstruction());
        dto.setCategory(entity.getCategory());
        dto.setRequestType(entity.getRequestType());
        dto.setUom(entity.getUom());
        dto.setQuantity(entity.getQuantity());
        dto.setStatus(entity.getStatus());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setLastUpdated(entity.getLastUpdated());
        dto.setActiveFlag(entity.getActiveFlag());

        if (entity.getRounds() != null) {
            java.util.List<com.tech3.erp.dto.RequestFormRoundDTO> roundDtos = new java.util.ArrayList<>();
            for (com.tech3.erp.entity.RequestFormRound round : entity.getRounds()) {
                com.tech3.erp.dto.RequestFormRoundDTO roundDto = new com.tech3.erp.dto.RequestFormRoundDTO();
                roundDto.setId(round.getId());
                roundDto.setRequestFormId(entity.getId());
                roundDto.setRoundNumber(round.getRoundNumber());
                roundDto.setReadyDate(round.getReadyDate());
                roundDto.setCourierName(round.getCourierName());
                roundDto.setCourierNumber(round.getCourierNumber());
                roundDto.setNoOfItems(round.getNoOfItems());

                if (round.getItems() != null) {
                    java.util.List<com.tech3.erp.dto.RequestFormRoundItemDTO> itemDtos = new java.util.ArrayList<>();
                    for (com.tech3.erp.entity.RequestFormRoundItem item : round.getItems()) {
                        com.tech3.erp.dto.RequestFormRoundItemDTO itemDto = new com.tech3.erp.dto.RequestFormRoundItemDTO();
                        itemDto.setId(item.getId());
                        itemDto.setRoundId(round.getId());
                        itemDto.setItem(item.getItem());
                        itemDto.setCustomerInstruction(item.getCustomerInstruction());
                        itemDto.setApprovalStatus(item.getApprovalStatus());
                        itemDtos.add(itemDto);
                    }
                    roundDto.setItems(itemDtos);
                }
                roundDtos.add(roundDto);
            }
            dto.setRounds(roundDtos);
        }

        if (entity.getToMillId() != null) {
            companyRepository.findById(entity.getToMillId())
                    .ifPresent(company -> dto.setToMillName(company.getCompanyName()));
        }

        return dto;
    }
}
