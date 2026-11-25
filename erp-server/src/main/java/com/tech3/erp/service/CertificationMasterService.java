package com.tech3.erp.service;

import com.tech3.erp.dto.CertificationMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.CertificationMaster;
import com.tech3.erp.entity.Media;
import com.tech3.erp.repository.CertificationMasterRepository;
import com.tech3.erp.repository.MediaRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificationMasterService {

    private final CertificationMasterRepository certificationMasterRepository;
    private final MediaRepository mediaRepository;

    public CertificationMasterService(CertificationMasterRepository certificationMasterRepository, MediaRepository mediaRepository) {
        this.certificationMasterRepository = certificationMasterRepository;
        this.mediaRepository = mediaRepository;
    }

    public CertificationMasterDTO createCertificationMaster(CertificationMasterDTO dto) {
        validateCertificationMaster(dto);

        Media logoMedia = mediaRepository.findByMediaDetailSno(dto.getLogoMediaDetailSno())
                .orElseThrow(() -> new EntityNotFoundException("Media not found for detail SNO: " + dto.getLogoMediaDetailSno()));

        CertificationMaster entity = new CertificationMaster();
        entity.setCertificationNo(dto.getCertificationNo());
        entity.setCertificationName(dto.getCertificationName());
        entity.setLogoMedia(logoMedia);
        entity.setActiveFlag(dto.getActiveFlag());

        CertificationMaster savedEntity = certificationMasterRepository.save(entity);
        return new CertificationMasterDTO(savedEntity);
    }

    public CertificationMasterDTO getCertificationMasterById(Long id) {
        return certificationMasterRepository.findById(id)
                .map(CertificationMasterDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Certification Master not found"));
    }

    public List<CertificationMasterDTO> getAllCertificationMasters() {
        return certificationMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(CertificationMaster::getId, Comparator.reverseOrder()))
                .map(CertificationMasterDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CertificationMasterDTO> getAllCertificationMastersWithPagination(Long offset, Long limit) {
        List<CertificationMasterDTO> allCertifications = certificationMasterRepository.findAll().stream()
                .sorted(Comparator.comparing(CertificationMaster::getId, Comparator.reverseOrder()))
                .map(CertificationMasterDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allCertifications, offset, limit);
    }

    public CertificationMasterDTO updateCertificationMaster(Long id, CertificationMasterDTO dto) {
        validateCertificationMasterForUpdate(id, dto);

        Media logoMedia = mediaRepository.findByMediaDetailSno(dto.getLogoMediaDetailSno())
                .orElseThrow(() -> new EntityNotFoundException("Media not found for detail SNO: " + dto.getLogoMediaDetailSno()));

        CertificationMaster entity = certificationMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification Master not found"));

        entity.setCertificationNo(dto.getCertificationNo());
        entity.setCertificationName(dto.getCertificationName());
        entity.setLogoMedia(logoMedia);
        entity.setActiveFlag(dto.getActiveFlag());

        CertificationMaster savedEntity = certificationMasterRepository.save(entity);
        return new CertificationMasterDTO(savedEntity);
    }

    public void deleteCertificationMaster(Long id) {
        if (!certificationMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Certification Master not found with id: " + id);
        }

        try {
            certificationMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Certification Master because it is referenced by other records");
        }
    }

    private void validateCertificationMaster(CertificationMasterDTO dto) {
        if (dto.getCertificationNo() == null || dto.getCertificationNo().trim().isEmpty()) {
            throw new IllegalArgumentException("Certification Number cannot be empty");
        }

        if (certificationMasterRepository.existsByCertificationNo(dto.getCertificationNo())) {
            throw new IllegalArgumentException("Certification Number must be unique");
        }

        if (dto.getCertificationName() == null || dto.getCertificationName().trim().isEmpty()) {
            throw new IllegalArgumentException("Certification Name cannot be empty");
        }

        if (dto.getLogoMediaDetailSno() == null) {
            throw new IllegalArgumentException("Logo Media Detail SNO is required");
        }
    }

    private void validateCertificationMasterForUpdate(Long id, CertificationMasterDTO dto) {
        if (dto.getCertificationNo() == null || dto.getCertificationNo().trim().isEmpty()) {
            throw new IllegalArgumentException("Certification Number cannot be empty");
        }

        if (dto.getCertificationName() == null || dto.getCertificationName().trim().isEmpty()) {
            throw new IllegalArgumentException("Certification Name cannot be empty");
        }

        if (dto.getLogoMediaDetailSno() == null) {
            throw new IllegalArgumentException("Logo Media Detail SNO is required");
        }

        CertificationMaster existingEntity = certificationMasterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Certification Master not found"));

        if (!existingEntity.getCertificationNo().equals(dto.getCertificationNo())) {
            if (certificationMasterRepository.existsByCertificationNo(dto.getCertificationNo())) {
                throw new IllegalArgumentException("Certification Number must be unique");
            }
        }
    }
}
