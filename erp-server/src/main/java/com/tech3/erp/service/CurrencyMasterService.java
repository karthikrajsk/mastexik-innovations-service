package com.tech3.erp.service;

import com.tech3.erp.dto.CurrencyMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.CurrencyMaster;
import com.tech3.erp.repository.CurrencyMasterRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyMasterService {

    private final CurrencyMasterRepository currencyMasterRepository;

    public CurrencyMasterService(CurrencyMasterRepository currencyMasterRepository) {
        this.currencyMasterRepository = currencyMasterRepository;
    }

    public CurrencyMasterDTO createCurrency(CurrencyMasterDTO currencyMasterDTO) {
        String trimmedCode = currencyMasterDTO.getCurrencyCode().trim();
        String trimmedName = currencyMasterDTO.getCurrencyName().trim();
        
        boolean duplicateCodeExists = currencyMasterRepository.findAll().stream()
                .anyMatch(term -> term.getCurrencyCode().trim().equalsIgnoreCase(trimmedCode));
        
        boolean duplicateNameExists = currencyMasterRepository.findAll().stream()
                .anyMatch(term -> term.getCurrencyName().trim().equalsIgnoreCase(trimmedName));
        
        if (duplicateCodeExists) {
            throw new IllegalArgumentException("Currency with code '" + trimmedCode + "' already exists");
        }
        
        if (duplicateNameExists) {
            throw new IllegalArgumentException("Currency with name '" + trimmedName + "' already exists");
        }
        
        CurrencyMaster currency = new CurrencyMaster();
        currency.setCurrencyCode(trimmedCode);
        currency.setCurrencyName(trimmedName);
        currency.setSymbol(currencyMasterDTO.getSymbol());
        currency.setActiveFlag(currencyMasterDTO.getActiveFlag());

        return new CurrencyMasterDTO(currencyMasterRepository.save(currency));
    }

    public CurrencyMasterDTO getCurrencyById(Long id) {
        CurrencyMaster currency = currencyMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));
        return new CurrencyMasterDTO(currency);
    }

    public List<CurrencyMasterDTO> getAllCurrencies() {
        return currencyMasterRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(CurrencyMaster::getId).reversed())
                .map(CurrencyMasterDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CurrencyMasterDTO> getAllCurrenciesWithPagination(Long offset, Long limit) {
        List<CurrencyMasterDTO> allCurrencies = currencyMasterRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(CurrencyMaster::getId).reversed())
                .map(CurrencyMasterDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allCurrencies, offset, limit);
    }

    public CurrencyMasterDTO updateCurrency(Long id, CurrencyMasterDTO currencyMasterDTO) {
        CurrencyMaster currency = currencyMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));

        String trimmedCode = currencyMasterDTO.getCurrencyCode().trim();
        String trimmedName = currencyMasterDTO.getCurrencyName().trim();
        
        boolean duplicateCodeExists = currencyMasterRepository.findAll().stream()
                .filter(term -> !term.getId().equals(id))
                .anyMatch(term -> term.getCurrencyCode().trim().equalsIgnoreCase(trimmedCode));
        
        boolean duplicateNameExists = currencyMasterRepository.findAll().stream()
                .filter(term -> !term.getId().equals(id))
                .anyMatch(term -> term.getCurrencyName().trim().equalsIgnoreCase(trimmedName));
        
        if (duplicateCodeExists) {
            throw new IllegalArgumentException("Currency with code '" + trimmedCode + "' already exists");
        }
        
        if (duplicateNameExists) {
            throw new IllegalArgumentException("Currency with name '" + trimmedName + "' already exists");
        }

        currency.setCurrencyCode(trimmedCode);
        currency.setCurrencyName(trimmedName);
        currency.setSymbol(currencyMasterDTO.getSymbol());
        currency.setActiveFlag(currencyMasterDTO.getActiveFlag());

        return new CurrencyMasterDTO(currencyMasterRepository.save(currency));
    }

//    public void deleteCurrency(Long id) {
//        if (!currencyMasterRepository.existsById(id)) {
//            throw new IllegalArgumentException("Currency not found");
//        }
//        currencyMasterRepository.deleteById(id);
//    }
    public void deleteCurrency(Long id) {
        // Check if category exists first
        if (!currencyMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Currency not found with id: " + id);
        }
        
        try {
        	currencyMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Currency because it is referenced by other entities");
        }
    }
    
    private String normalize(String value) {
    if (value == null) return null;
    return value.trim()
                .replaceAll("\\s+", "")   // remove ALL spaces
                .toLowerCase();           // case-insensitive compare
}
}
