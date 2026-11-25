package com.tech3.erp.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.FlangeDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Flange;
import com.tech3.erp.entity.GstMaster;
import com.tech3.erp.repository.FlangeRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlangeService {

    private final FlangeRepository flangeRepository;

    public FlangeService(FlangeRepository flangeRepository) {
        this.flangeRepository = flangeRepository;
    }

    public FlangeDTO createFlange(FlangeDTO flangeDTO) {
        String normalizedFlangeNo = normalizeFlangeNo(flangeDTO.getFlangeNo());
        if (normalizedFlangeNo == null) {
            throw new IllegalArgumentException("Flange number cannot be blank");
        }
        // ensureFlangeUnique(normalizedFlangeNo, null);
        Flange flange = new Flange();
        flange.setFlangeNo(normalizedFlangeNo);
        flange.setActiveFlag(flangeDTO.getActiveFlag());
        flange.setCompanyId(flangeDTO.getCompanyId());
        flange.setIssuedCompanyId(flangeDTO.getIssuedCompanyId());
        flange.setIssuedToInternal(flangeDTO.isIssuedToInternal());
        flange.setIsIssued(flangeDTO.getIsIssued());
        flange.setIsSizingReceive(flangeDTO.getIsSizingReceive());
        flange.setIsBeamGating(flangeDTO.getIsBeamGating());
        flange.setIsInwarded(flangeDTO.getIsInwarded());

        try {
            return new FlangeDTO(flangeRepository.save(flange));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Flange number '" + normalizedFlangeNo + "' already exists");
        }
    }

    public FlangeDTO getFlangeById(Long id) {
    	Flange flange = flangeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flange not found"));
        return new FlangeDTO(flange);
    }

    public List<FlangeDTO> getAllFlanges() {
        return flangeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Flange ::getId, Comparator.reverseOrder()))
                .map(FlangeDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FlangeDTO> getAllFlangesWithPagination(Long offset, Long limit) {
        List<FlangeDTO> flanges = getAllFlanges();
        return PaginationHelper.paginate(flanges, offset, limit);
    }

    public FlangeDTO updateFlange(Long id, FlangeDTO flangeDTO) {
    	Flange flange = flangeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flang not found"));

        String normalizedFlangeNo = normalizeFlangeNo(flangeDTO.getFlangeNo());
        if (normalizedFlangeNo == null) {
            throw new IllegalArgumentException("Flange number cannot be blank");
        }
        // ensureFlangeUnique(normalizedFlangeNo, id);
    	flange.setFlangeNo(normalizedFlangeNo);
    	flange.setActiveFlag(flangeDTO.getActiveFlag());
        flange.setCompanyId(flangeDTO.getCompanyId());
        flange.setIsIssued(flangeDTO.getIsIssued());
        flange.setIsSizingReceive(flangeDTO.getIsSizingReceive());
        flange.setIsBeamGating(flangeDTO.getIsBeamGating());
        flange.setIsInwarded(flangeDTO.getIsInwarded());

        try {
            return new FlangeDTO(flangeRepository.save(flange));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Flange number '" + normalizedFlangeNo + "' already exists");
        }
    }
    
//    public void deleteFlange(Long id) {
//        if (!flangeRepository.existsById(id)) {
//            throw new IllegalArgumentException("Flang not found");
//        }
//        flangeRepository.deleteById(id);
//    }
    
    public void deleteFlange(Long id) {
        // Check if category exists first
        if (!flangeRepository.existsById(id)) {
            throw new EntityNotFoundException("Flange not found with id: " + id);
        }
        
        try {
        	flangeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Flange because it is referenced by other");
        }
    }

    // private void ensureFlangeUnique(String flangeNo, Long currentId) {
    //     if (flangeNo == null) {
    //         return;
    //     }
    //     flangeRepository.findByFlangeNo(flangeNo)
    //             .filter(existing -> currentId == null || !existing.getId().equals(currentId))
    //             .ifPresent(existing -> {
    //                 String companyInfo = existing.getCompanyId() != null ? " for company ID " + existing.getCompanyId() : "";
    //                 throw new DataIntegrityViolationException("Flange number '" + flangeNo + "' already exists");
    //             });
    // }

    private String normalizeFlangeNo(String flangeNo) {
        if (flangeNo == null) {
            return null;
        }
        String trimmed = flangeNo.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    @Transactional
    public void deleteFlangeAndResetIssued(Long id, Long companyId) {
        Flange flange = flangeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flange not found with id: " + id));

        if (!flange.getCompanyId().equals(companyId)) {
            throw new EntityNotFoundException("Flange not found with id: " + id + " for company: " + companyId);
        }

        flangeRepository.resetFlagsById(id);

        try {
            flangeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Flange because it is referenced by other");
        }
    }
    
    public List<FlangeDTO> getAllFlangesByCompanyId(Long companyId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return flangeRepository.findByCompanyId(companyId, sort)
                .stream()
                .map(FlangeDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<FlangeDTO> getAllFlangesByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<FlangeDTO> flanges = getAllFlangesByCompanyId(companyId);
        return PaginationHelper.paginate(flanges, offset, limit);
    }

    public FlangeDTO getFlangeById(Long id, Long companyId) {
        Flange flange = flangeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flange not found with id: " + id));
        
        if (!flange.getCompanyId().equals(companyId)) {
            throw new EntityNotFoundException("Flange not found with id: " + id + " for company: " + companyId);
        }
        
        return new FlangeDTO(flange);
    }

    public FlangeDTO updateFlange(Long id, FlangeDTO flangeDTO, Long companyId) {
        Flange flange = flangeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flange not found with id: " + id));

        if (!flange.getCompanyId().equals(companyId)) {
            throw new EntityNotFoundException("Flange not found with id: " + id + " for company: " + companyId);
        }

        String normalizedFlangeNo = normalizeFlangeNo(flangeDTO.getFlangeNo());
        if (normalizedFlangeNo == null) {
            throw new IllegalArgumentException("Flange number cannot be blank");
        }
        // ensureFlangeUnique(normalizedFlangeNo, id);
        flange.setFlangeNo(normalizedFlangeNo);
        flange.setActiveFlag(flangeDTO.getActiveFlag());
        flange.setCompanyId(flangeDTO.getCompanyId());

        try {
            return new FlangeDTO(flangeRepository.save(flange));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Flange number '" + normalizedFlangeNo + "' already exists");
        }
    }

    public void deleteFlange(Long id, Long companyId) {
        Flange flange = flangeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flange not found with id: " + id));

        if (!flange.getCompanyId().equals(companyId)) {
            throw new EntityNotFoundException("Flange not found with id: " + id + " for company: " + companyId);
        }

        try {
            flangeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Flange because it is referenced by other");
        }
    }

}
