package com.tech3.erp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech3.erp.dto.BeamCardDto;
import com.tech3.erp.entity.BeamCard;
import com.tech3.erp.repository.BeamCardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BeamCardService {

    private final BeamCardRepository beamCardRepository;
    private final ObjectMapper objectMapper;

    public BeamCardService(BeamCardRepository beamCardRepository, ObjectMapper objectMapper) {
        this.beamCardRepository = beamCardRepository;
        this.objectMapper = objectMapper;
    }

    public BeamCardDto saveBeamCard(BeamCardDto dto) {
        if (dto.getBeamNo() == null || dto.getBeamNo().trim().isEmpty()) {
            throw new IllegalArgumentException("Beam number is required");
        }

        Optional<BeamCard> existingOpt = beamCardRepository.findByBeamNo(dto.getBeamNo().trim());
        BeamCard beamCard = existingOpt.orElseGet(BeamCard::new);

        beamCard.setBeamNo(dto.getBeamNo().trim());
        if (dto.getLoomNo() != null) beamCard.setLoomNo(dto.getLoomNo());
        if (dto.getCircleNo() != null) beamCard.setCircleNo(dto.getCircleNo());
        if (dto.getStatus() != null) beamCard.setStatus(dto.getStatus());
        if (dto.getBeamCardTypeId() != null) beamCard.setBeamCardTypeId(dto.getBeamCardTypeId());
        if (dto.getRollWeight() != null) beamCard.setRollWeight(dto.getRollWeight());
        if (dto.getRollGlm() != null) beamCard.setRollGlm(dto.getRollGlm());

        if (dto.getData() != null) {
            if (dto.getData() instanceof String) {
                beamCard.setData((String) dto.getData());
            } else {
                try {
                    beamCard.setData(objectMapper.writeValueAsString(dto.getData()));
                } catch (JsonProcessingException e) {
                    beamCard.setData(dto.getData().toString());
                }
            }
        }

        BeamCard saved = beamCardRepository.save(beamCard);
        return convertToDto(saved);
    }

    @Transactional(readOnly = true)
    public BeamCardDto getBeamCardByBeamNo(String beamNo) {
        BeamCard beamCard = beamCardRepository.findByBeamNo(beamNo.trim())
                .orElseThrow(() -> new EntityNotFoundException("Beam Card not found with Beam No: " + beamNo));
        return convertToDto(beamCard);
    }

    @Transactional(readOnly = true)
    public List<BeamCardDto> getAllBeamCards(Long offset, Long limit) {
        int pageNumber = (int) (offset / (limit > 0 ? limit : 10));
        Pageable pageable = PageRequest.of(pageNumber, limit.intValue(), Sort.by("id").descending());
        Page<BeamCard> page = beamCardRepository.findAll(pageable);
        return page.getContent().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void deleteBeamCard(Long id) {
        if (!beamCardRepository.existsById(id)) {
            throw new EntityNotFoundException("Beam Card not found with id: " + id);
        }
        beamCardRepository.deleteById(id);
    }

    private BeamCardDto convertToDto(BeamCard entity) {
        Object parsedData = entity.getData();
        if (entity.getData() != null) {
            try {
                parsedData = objectMapper.readValue(entity.getData(), Object.class);
            } catch (Exception e) {
                parsedData = entity.getData();
            }
        }

        return new BeamCardDto(
                entity.getId(),
                entity.getBeamNo(),
                entity.getLoomNo(),
                entity.getCircleNo(),
                entity.getStatus(),
                entity.getBeamCardTypeId(),
                entity.getRollWeight(),
                entity.getRollGlm(),
                parsedData,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
