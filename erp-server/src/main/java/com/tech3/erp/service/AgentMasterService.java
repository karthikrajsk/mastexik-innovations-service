package com.tech3.erp.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tech3.erp.dto.AgentMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.AgentMaster;
import com.tech3.erp.repository.AgentMasterRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentMasterService {

    private final AgentMasterRepository agentMasterRepository;

    public AgentMasterService(AgentMasterRepository agentMasterRepository) {
        this.agentMasterRepository = agentMasterRepository;
    }

    public AgentMasterDTO createAgentMaster(AgentMasterDTO dto) {
        AgentMaster entity = new AgentMaster();
        
        // Auto-generate code if not provided
        if (dto.getAgentCode() == null || dto.getAgentCode().trim().isEmpty()) {
            long count = agentMasterRepository.count() + 1;
            entity.setAgentCode(String.format("AGT-%03d", count));
        } else {
            entity.setAgentCode(dto.getAgentCode().trim().toUpperCase());
        }

        mapDtoToEntity(dto, entity);

        return new AgentMasterDTO(agentMasterRepository.save(entity));
    }

    public AgentMasterDTO getAgentMasterById(Long id) {
        return agentMasterRepository.findById(id)
                .map(AgentMasterDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Agent Master not found"));
    }

    public List<AgentMasterDTO> getAllAgentMasters() {
        return agentMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(AgentMaster::getId, Comparator.reverseOrder()))
                .map(AgentMasterDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<AgentMasterDTO> getAllAgentMastersWithPagination(Long offset, Long limit) {
        List<AgentMasterDTO> all = getAllAgentMasters();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public AgentMasterDTO updateAgentMaster(Long id, AgentMasterDTO dto) {
        AgentMaster entity = agentMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Agent Master not found with id: " + id));

        if (dto.getAgentCode() != null && !dto.getAgentCode().trim().isEmpty()) {
            entity.setAgentCode(dto.getAgentCode().trim().toUpperCase());
        }
        
        mapDtoToEntity(dto, entity);

        return new AgentMasterDTO(agentMasterRepository.save(entity));
    }

    public void deleteAgentMaster(Long id) {
        if (!agentMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Agent Master not found with id: " + id);
        }

        try {
            agentMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Agent Master because it is referenced by other records");
        }
    }

    private void mapDtoToEntity(AgentMasterDTO dto, AgentMaster entity) {
        entity.setAgentName(dto.getAgentName());
        entity.setAgentType(dto.getAgentType());
        entity.setAgentPercentage(dto.getAgentPercentage());
        entity.setCommissionType(dto.getCommissionType() != null ? dto.getCommissionType() : "PERCENTAGE");
        entity.setSacCode(dto.getSacCode() != null ? dto.getSacCode() : "996111");
        entity.setPanNo(dto.getPanNo() != null ? dto.getPanNo().trim().toUpperCase() : null);
        entity.setGstin(dto.getGstin() != null ? dto.getGstin().trim().toUpperCase() : null);
        entity.setContactNo(dto.getContactNo());
        entity.setAlternateContactNo(dto.getAlternateContactNo());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPincode(dto.getPincode());
        entity.setBankName(dto.getBankName());
        entity.setAccountNo(dto.getAccountNo());
        entity.setIfscCode(dto.getIfscCode() != null ? dto.getIfscCode().trim().toUpperCase() : null);
        entity.setBranchName(dto.getBranchName());
        entity.setActiveFlag(dto.getActiveFlag());
    }
}
