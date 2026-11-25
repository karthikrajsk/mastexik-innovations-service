package com.tech3.erp.service;

import com.tech3.erp.dto.NumberFormatConfigurationDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.NumberFormatConfiguration;
import com.tech3.erp.repository.NumberFormatConfigurationRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NumberFormatConfigurationService {

    private final NumberFormatConfigurationRepository configurationRepository;

    public NumberFormatConfigurationService(NumberFormatConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    public NumberFormatConfigurationDTO createConfiguration(NumberFormatConfigurationDTO configurationDTO) {
        // Validate unique tab name for screen within company scope
        String tabName = configurationDTO.getTabName();
        if (tabName != null) {
            tabName = tabName.trim();
        }
        if (tabName != null && !tabName.isEmpty()) {
            Long companyId = configurationDTO.getCompanyId();
            boolean exists;
            if (companyId != null) {
                exists = configurationRepository.existsByScreenNameAndTabNameAndCompanyId(
                        configurationDTO.getScreenName(), tabName, companyId);
            } else {
                exists = configurationRepository.existsByScreenNameAndTabName(
                        configurationDTO.getScreenName(), tabName);
            }
            if (exists) {
                throw new IllegalArgumentException("Tab name already exists for this screen");
            }
        }

        NumberFormatConfiguration configuration = new NumberFormatConfiguration();
        mapDtoToEntity(configurationDTO, configuration);

        // Generate format preview
        configuration.setFormatPreview(generateFormatPreview(configuration));

        return new NumberFormatConfigurationDTO(configurationRepository.save(configuration));
    }

    public NumberFormatConfigurationDTO getConfigurationById(Long id) {
        return getConfigurationById(id, null);
    }

    public NumberFormatConfigurationDTO getConfigurationById(Long id, Long companyId) {
        NumberFormatConfiguration configuration = findConfigurationByIdAndCompany(id, companyId);
        return new NumberFormatConfigurationDTO(configuration);
    }

    public NumberFormatConfigurationDTO getConfigurationByCompany(Long companyId, Long id) {
        return getConfigurationById(id, companyId);
    }

    public List<NumberFormatConfigurationDTO> getAllConfigurations() {
        return getAllConfigurations(null);
    }

    public List<NumberFormatConfigurationDTO> getAllConfigurations(Long companyId) {
        return getConfigurationsByCompany(companyId)
                .stream()
                .sorted(Comparator.comparing(NumberFormatConfiguration::getId, Comparator.reverseOrder()))
                .map(NumberFormatConfigurationDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<NumberFormatConfigurationDTO> getAllConfigurationsWithPagination(Long offset, Long limit) {
        return getAllConfigurationsWithPagination(offset, limit, null);
    }

    public PaginatedResponse<NumberFormatConfigurationDTO> getAllConfigurationsWithPagination(Long offset, Long limit, Long companyId) {
        List<NumberFormatConfigurationDTO> all = getAllConfigurations(companyId);
        return PaginationHelper.paginate(all, offset, limit);
    }

    public NumberFormatConfigurationDTO updateConfiguration(Long id, NumberFormatConfigurationDTO configurationDTO) {
        return updateConfiguration(id, configurationDTO, null);
    }

    public NumberFormatConfigurationDTO updateConfiguration(Long id, NumberFormatConfigurationDTO configurationDTO, Long companyId) {
        NumberFormatConfiguration configuration = findConfigurationByIdAndCompany(id, companyId);

        String tabName = configurationDTO.getTabName();
        if (tabName != null) {
            tabName = tabName.trim();
        }
        if (tabName != null && !tabName.isEmpty()) {
            Long effectiveCompanyId = configurationDTO.getCompanyId() != null ? configurationDTO.getCompanyId() : configuration.getCompanyId();
            Optional<NumberFormatConfiguration> existing;
            if (effectiveCompanyId != null) {
                existing = configurationRepository.findByScreenNameAndTabNameAndCompanyId(
                        configurationDTO.getScreenName(), tabName, effectiveCompanyId);
            } else {
                existing = configurationRepository.findByScreenNameAndTabName(configurationDTO.getScreenName(), tabName);
            }
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                throw new IllegalArgumentException("Tab name already exists for this screen");
            }
        }

        mapDtoToEntity(configurationDTO, configuration);

        configuration.setFormatPreview(generateFormatPreview(configuration));

        return new NumberFormatConfigurationDTO(configurationRepository.save(configuration));
    }

    public NumberFormatConfigurationDTO updateConfigurationByCompany(Long companyId, Long id, NumberFormatConfigurationDTO configurationDTO) {
        return updateConfiguration(id, configurationDTO, companyId);
    }

    public void deleteConfiguration(Long id) {
        deleteConfiguration(id, null);
    }

    public void deleteConfiguration(Long id, Long companyId) {
        NumberFormatConfiguration configuration = findConfigurationByIdAndCompany(id, companyId);

        configurationRepository.delete(configuration);
    }

    public void deleteConfigurationByCompany(Long companyId, Long id) {
        deleteConfiguration(id, companyId);
    }

    private List<NumberFormatConfiguration> getConfigurationsByCompany(Long companyId) {
        if (companyId != null) {
            return configurationRepository.findByCompanyId(companyId);
        }
        return configurationRepository.findAll();
    }

    private NumberFormatConfiguration findConfigurationByIdAndCompany(Long id, Long companyId) {
        if (companyId != null) {
            return configurationRepository.findByIdAndCompanyId(id, companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Configuration not found"));
        }
        return configurationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Configuration not found"));
    }

    private void mapDtoToEntity(NumberFormatConfigurationDTO dto, NumberFormatConfiguration entity) {
        entity.setScreenName(dto.getScreenName());
        entity.setTabName(dto.getTabName() != null && dto.getTabName().trim().isEmpty() ? null : dto.getTabName());
        entity.setPrefix(dto.getPrefix());
        entity.setFinancialYear(dto.getFinancialYear());
        entity.setSequenceNumber(dto.getSequenceNumber());
        entity.setSeparator(dto.getSeparator());
        entity.setActiveFlag(dto.getActiveFlag());
        entity.setCompanyId(dto.getCompanyId());
    }

    private String generateFormatPreview(NumberFormatConfiguration configuration) {
        // Enhanced format preview generation to support print context logic
        StringBuilder preview = new StringBuilder();

        // Add prefix
        if (configuration.getPrefix() != null && !configuration.getPrefix().trim().isEmpty()) {
            preview.append(configuration.getPrefix());
        }

        // Add separator if we have content
        if (preview.length() > 0) {
            preview.append(configuration.getSeparator());
        }

        // Add financial year
        if (configuration.getFinancialYear() != null && !configuration.getFinancialYear().trim().isEmpty()) {
            preview.append(configuration.getFinancialYear());
        }

        // Add separator if we have content and sequence number
        if (preview.length() > 0 && configuration.getSequenceNumber() != null && !configuration.getSequenceNumber().trim().isEmpty()) {
            preview.append(configuration.getSeparator());
        }

        // Add sequence number
        if (configuration.getSequenceNumber() != null && !configuration.getSequenceNumber().trim().isEmpty()) {
            preview.append(configuration.getSequenceNumber());
        }

        return preview.toString();
    }

    /**
     * Generate format preview for print context style configurations
     * Supports parameter-based formatting similar to the frontend print context
     */
    public String generatePrintContextPreview(String screenName, String prefix, String financialYear,
                                            String sequenceNumber, String separator) {
        NumberFormatConfiguration tempConfig = new NumberFormatConfiguration();
        tempConfig.setScreenName(screenName);
        tempConfig.setPrefix(prefix);
        tempConfig.setFinancialYear(financialYear);
        tempConfig.setSequenceNumber(sequenceNumber);
        tempConfig.setSeparator(separator != null ? separator : "-");

        return generateFormatPreview(tempConfig);
    }

    /**
     * Validate print context parameters
     */
    public void validatePrintContextParameters(String screenName, String parameter1, String parameter2,
                                             String parameter3, String separator) {
        if (screenName == null || screenName.trim().isEmpty()) {
            throw new IllegalArgumentException("Screen name is required");
        }

        if (parameter1 == null || !parameter1.equals("Prefix")) {
            throw new IllegalArgumentException("Parameter 1 must be Prefix");
        }

        if (parameter2 == null || parameter2.trim().isEmpty()) {
            throw new IllegalArgumentException("Parameter 2 is required");
        }

        if (separator == null || separator.trim().isEmpty()) {
            throw new IllegalArgumentException("Separator is required");
        }

        // Validate parameter combinations based on screen type
        boolean isOrderScreen = isOrderScreen(screenName);
        String expectedSequenceParam = isOrderScreen ? "Order Number" : "Invoice Number";

        if (parameter2.equals("Prefix") || parameter2.equals("Financial Year")) {
            // Valid
        } else if (!parameter2.equals(expectedSequenceParam)) {
            throw new IllegalArgumentException("Parameter 2 must be Prefix, Financial Year, or " + expectedSequenceParam);
        }

        if (parameter3 != null && !parameter3.trim().isEmpty()) {
            if (parameter3.equals("Financial Year") || parameter3.equals(expectedSequenceParam)) {
                // Valid
            } else {
                throw new IllegalArgumentException("Parameter 3 must be Financial Year or " + expectedSequenceParam);
            }
        }
    }

    private boolean isOrderScreen(String screenName) {
        return screenName.equals("SO") || screenName.equals("PO") ||
               screenName.equals("Weaving Contract") || screenName.equals("Sizing Order");
    }
}