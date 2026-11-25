package com.tech3.erp.service;

import com.tech3.erp.dto.VendorSizingRateDTO;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.entity.VendorSizingRate;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.VendorSizingRateRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendorSizingRateService {

    private final VendorSizingRateRepository rateRepository;
    private final VendorRepository vendorRepository;

    public VendorSizingRateService(VendorSizingRateRepository rateRepository, VendorRepository vendorRepository) {
        this.rateRepository = rateRepository;
        this.vendorRepository = vendorRepository;
    }

    public List<VendorSizingRateDTO> getActiveRatesForVendor(Long vendorId) {
        String vendorName = getVendorName(vendorId);
        return rateRepository.findByVendorIdAndActiveFlagTrueOrderByYarnCountAsc(vendorId).stream()
                .map(entity -> {
                    VendorSizingRateDTO dto = new VendorSizingRateDTO(entity);
                    dto.setVendorName(vendorName);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<VendorSizingRateDTO> getRateHistoryForVendor(Long vendorId, String yarnCount) {
        String vendorName = getVendorName(vendorId);
        List<VendorSizingRate> history;
        if (yarnCount != null && !yarnCount.trim().isEmpty()) {
            history = rateRepository.findByVendorIdAndYarnCountOrderByCreatedAtDesc(vendorId, yarnCount.trim());
        } else {
            history = rateRepository.findByVendorIdOrderByCreatedAtDesc(vendorId);
        }

        return history.stream()
                .map(entity -> {
                    VendorSizingRateDTO dto = new VendorSizingRateDTO(entity);
                    dto.setVendorName(vendorName);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public VendorSizingRateDTO saveOrReviseRate(Long vendorId, VendorSizingRateDTO dto) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new EntityNotFoundException("Vendor not found with id: " + vendorId));

        if (dto.getYarnCount() == null || dto.getYarnCount().trim().isEmpty()) {
            throw new IllegalArgumentException("Yarn Count is required");
        }
        if (dto.getSizingRate() == null) {
            throw new IllegalArgumentException("Sizing Rate is required");
        }

        String normalizedCount = dto.getYarnCount().trim();
        String user = dto.getCreatedBy() != null && !dto.getCreatedBy().trim().isEmpty() ? dto.getCreatedBy().trim() : "System";

        // Deactivate previous active rates for this vendor & yarn count
        Optional<VendorSizingRate> existingActiveOpt = rateRepository.findByVendorIdAndYarnCountAndActiveFlagTrue(vendorId, normalizedCount);
        if (existingActiveOpt.isPresent()) {
            VendorSizingRate existingActive = existingActiveOpt.get();
            existingActive.setActiveFlag(false);
            existingActive.setUpdatedBy(user);
            existingActive.setUpdatedAt(LocalDateTime.now());
            rateRepository.save(existingActive);
        }

        // Create new active rate record
        VendorSizingRate newRate = new VendorSizingRate();
        newRate.setVendorId(vendorId);
        newRate.setYarnCount(normalizedCount);
        newRate.setSizingRate(dto.getSizingRate());
        newRate.setEffectiveDate(dto.getEffectiveDate() != null ? dto.getEffectiveDate() : LocalDate.now());
        newRate.setActiveFlag(true);
        newRate.setCreatedBy(user);
        newRate.setCreatedAt(LocalDateTime.now());
        newRate.setUpdatedBy(user);
        newRate.setUpdatedAt(LocalDateTime.now());

        VendorSizingRate saved = rateRepository.save(newRate);
        VendorSizingRateDTO result = new VendorSizingRateDTO(saved);
        result.setVendorName(vendor.getVendorName());
        return result;
    }

    @Transactional
    public void deleteRate(Long id) {
        if (!rateRepository.existsById(id)) {
            throw new EntityNotFoundException("Sizing Rate record not found with id: " + id);
        }
        rateRepository.deleteById(id);
    }

    private String getVendorName(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .map(Vendor::getVendorName)
                .orElse("");
    }
}
