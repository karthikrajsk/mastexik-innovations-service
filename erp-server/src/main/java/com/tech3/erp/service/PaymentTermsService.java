package com.tech3.erp.service;

import com.tech3.erp.dto.PaymentTermsDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.PaymentTerms;
import com.tech3.erp.entity.TermsConditions;
import com.tech3.erp.repository.PaymentTermsRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentTermsService {

    private final PaymentTermsRepository paymentTermsRepository;

    public PaymentTermsService(PaymentTermsRepository paymentTermsRepository) {
        this.paymentTermsRepository = paymentTermsRepository;
    }

    public PaymentTermsDTO createPaymentTerms(PaymentTermsDTO paymentTermsDTO) {
        String trimmedTermName = paymentTermsDTO.getTermName().trim();
        
        boolean duplicateExists = paymentTermsRepository.findAll().stream()
                .anyMatch(term -> term.getTermName().trim().equalsIgnoreCase(trimmedTermName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Payment term with name '" + trimmedTermName + "' already exists");
        }
        
        PaymentTerms paymentTerms = new PaymentTerms();
        paymentTerms.setTermName(trimmedTermName);
        paymentTerms.setDescription(paymentTermsDTO.getDescription());
        paymentTerms.setActiveFlag(paymentTermsDTO.getActiveFlag());

        paymentTerms = paymentTermsRepository.save(paymentTerms);
        return new PaymentTermsDTO(paymentTerms);
    }

    public PaymentTermsDTO getPaymentTermsById(Long id) {
        PaymentTerms paymentTerms = paymentTermsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment term not found"));
        return new PaymentTermsDTO(paymentTerms);
    }

    public List<PaymentTermsDTO> getAllPaymentTerms() {
        return paymentTermsRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(PaymentTerms ::getId, Comparator.reverseOrder()))
                .map(PaymentTermsDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<PaymentTermsDTO> getAllPaymentTermsWithPagination(Long offset, Long limit) {
        List<PaymentTermsDTO> all = getAllPaymentTerms();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public PaymentTermsDTO updatePaymentTerms(Long id, PaymentTermsDTO paymentTermsDTO) {
        PaymentTerms paymentTerms = paymentTermsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment term not found"));

        String trimmedTermName = paymentTermsDTO.getTermName().trim();
        
        boolean duplicateExists = paymentTermsRepository.findAll().stream()
                .filter(term -> !term.getId().equals(id))
                .anyMatch(term -> term.getTermName().trim().equalsIgnoreCase(trimmedTermName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Payment term with name '" + trimmedTermName + "' already exists");
        }

        paymentTerms.setTermName(trimmedTermName);
        paymentTerms.setDescription(paymentTermsDTO.getDescription());
        paymentTerms.setActiveFlag(paymentTermsDTO.getActiveFlag());

        paymentTerms = paymentTermsRepository.save(paymentTerms);
        return new PaymentTermsDTO(paymentTerms);
    }

//    public void deletePaymentTerms(Long id) {
//        PaymentTerms paymentTerms = paymentTermsRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Payment term not found"));
//        paymentTermsRepository.delete(paymentTerms);
//    }
    
    public void deletePaymentTerms(Long id) {
        // Check if category exists first
        if (!paymentTermsRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment term not found with id: " + id);
        }
        
        try {
        	paymentTermsRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Payment term because it is referenced by other");
        }
    }
}
