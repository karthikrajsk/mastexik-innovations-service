package com.tech3.erp.service;

import com.tech3.erp.dto.OutwardDTO;
import com.tech3.erp.entity.LotOutward;
import com.tech3.erp.repository.LotOutwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LotOutwardService {

    @Autowired
    private LotOutwardRepository outwardRepository;

    // Create new outward entry
    public OutwardDTO createOutward(OutwardDTO outwardDTO) {
        LotOutward outward = convertToEntity(outwardDTO);
        outward.setActiveFlag(true);
        
        LotOutward savedOutward = outwardRepository.save(outward);
        return convertToDTO(savedOutward);
    }

    // Get all outward entries
    public List<OutwardDTO> getAllOutwards() {
        List<LotOutward> outwards = outwardRepository.findAll();
        return outwards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get outward entries by outward type
    public List<OutwardDTO> getOutwardsByOutwardType(String outwardType) {
        List<LotOutward> outwards = outwardRepository.findByOutwardType(outwardType);
        return outwards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get outward entries by lot entry ID
    public List<OutwardDTO> getOutwardsByLotEntry(Long lotEntryId) {
        List<LotOutward> outwards = outwardRepository.findByLotEntryId(lotEntryId);
        return outwards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get outward entries by reference type and reference ID
    public List<OutwardDTO> getOutwardsByReference(String referenceType, Long referenceId) {
        List<LotOutward> outwards = outwardRepository.findByReferenceTypeAndReferenceId(referenceType, referenceId);
        return outwards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get outward entries by reference type, reference ID and outward type
    public List<OutwardDTO> getOutwardsByReferenceAndType(String referenceType, Long referenceId, String outwardType) {
        List<LotOutward> outwards = outwardRepository.findByReferenceTypeAndReferenceIdAndOutwardType(referenceType, referenceId, outwardType);
        return outwards.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get total outward quantity by lot entry ID
    public BigDecimal getTotalOutwardQuantityByLotEntry(Long lotEntryId) {
        BigDecimal total = outwardRepository.getTotalOutwardQuantityByLotEntry(lotEntryId);
        return total != null ? total : BigDecimal.ZERO;
    }

    // Get outward entry by ID
    public OutwardDTO getOutwardById(Long id) {
        return outwardRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Deactivate outward entry
    public void deactivateOutward(Long id) {
        outwardRepository.findById(id).ifPresent(outward -> {
            outward.setActiveFlag(false);
            outwardRepository.save(outward);
        });
    }

    // Update outward entry
    public OutwardDTO updateOutward(Long id, OutwardDTO outwardDTO) {
        return outwardRepository.findById(id)
                .map(existingOutward -> {
                    updateEntityFromDTO(existingOutward, outwardDTO);
                    LotOutward updatedOutward = outwardRepository.save(existingOutward);
                    return convertToDTO(updatedOutward);
                })
                .orElse(null);
    }

    // Get active outward entries only
    public List<OutwardDTO> getActiveOutwards() {
        List<LotOutward> allOutwards = outwardRepository.findAll();
        return allOutwards.stream()
                .filter(outward -> Boolean.TRUE.equals(outward.getActiveFlag()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper method to convert DTO to Entity
    private LotOutward convertToEntity(OutwardDTO dto) {
        LotOutward outward = new LotOutward();
        outward.setLotEntryId(dto.getLotEntryId());
        outward.setOutwardType(dto.getOutwardType());
        outward.setReferenceId(dto.getReferenceId());
        outward.setReferenceType(dto.getReferenceType());
        outward.setReferenceTypeId(dto.getReferenceTypeId());
        outward.setQuantity(dto.getQuantity());
        outward.setOutwardDate(dto.getOutwardDate());
        outward.setRemarks(dto.getRemarks());
        outward.setCreatedBy(dto.getCreatedBy());
        return outward;
    }

    // Helper method to update entity from DTO
    private void updateEntityFromDTO(LotOutward outward, OutwardDTO dto) {
        outward.setOutwardType(dto.getOutwardType());
        outward.setReferenceId(dto.getReferenceId());
        outward.setReferenceType(dto.getReferenceType());
        outward.setReferenceTypeId(dto.getReferenceTypeId());
        outward.setQuantity(dto.getQuantity());
        outward.setOutwardDate(dto.getOutwardDate());
        outward.setRemarks(dto.getRemarks());
    }

    // Helper method to convert Entity to DTO
    private OutwardDTO convertToDTO(LotOutward outward) {
        OutwardDTO dto = new OutwardDTO();
        dto.setId(outward.getId());
        dto.setLotEntryId(outward.getLotEntryId());
        dto.setOutwardType(outward.getOutwardType());
        dto.setReferenceId(outward.getReferenceId());
        dto.setReferenceType(outward.getReferenceType());
        dto.setReferenceTypeId(outward.getReferenceTypeId());
        dto.setQuantity(outward.getQuantity());
        dto.setOutwardDate(outward.getOutwardDate());
        dto.setRemarks(outward.getRemarks());
        dto.setCreatedBy(outward.getCreatedBy());
        dto.setCreatedAt(outward.getCreatedAt());
        dto.setUpdatedAt(outward.getUpdatedAt());
        dto.setActiveFlag(outward.getActiveFlag());
        return dto;
    }
}