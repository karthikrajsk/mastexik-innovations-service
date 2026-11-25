package com.tech3.erp.service;

import com.tech3.erp.dto.CodesHeaderDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.CodesHeader;
import com.tech3.erp.repository.CodesHeaderRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodesHeaderService {
    @Autowired
    private CodesHeaderRepository codesHeaderRepository;

    public CodesHeaderDTO create(CodesHeaderDTO dto) {
        validateDto(dto);

        if (codesHeaderRepository.findByCodeGroup(dto.getCodeGroup()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code Group already exists");
        }

        CodesHeader entity = new CodesHeader();
        entity.setCodeGroup(dto.getCodeGroup());
        entity.setDescription(dto.getDescription());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

        CodesHeader saved = codesHeaderRepository.save(entity);
        return new CodesHeaderDTO(saved);
    }

    public List<CodesHeaderDTO> getAll() {
        return codesHeaderRepository.findAllWithDetails().stream()
                .map(CodesHeaderDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CodesHeaderDTO> getAllWithPagination(Long offset, Long limit) {
        List<CodesHeaderDTO> allCodes = codesHeaderRepository.findAllWithDetails().stream()
                .sorted(Comparator.comparing(CodesHeader::getId).reversed())
                .map(CodesHeaderDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allCodes, offset, limit);
    }

    private void validateDto(CodesHeaderDTO dto) {
        if (dto.getCodeGroup() == null || dto.getCodeGroup().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code Group is required");
        }
    }
}
