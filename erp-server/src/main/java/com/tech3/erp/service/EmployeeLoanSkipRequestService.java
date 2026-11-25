package com.tech3.erp.service;

import com.tech3.erp.dto.EmployeeLoanSkipRequestDTO;
import com.tech3.erp.entity.EmployeeLoan;
import com.tech3.erp.entity.EmployeeLoanSkipRequest;
import com.tech3.erp.repository.EmployeeLoanRepository;
import com.tech3.erp.repository.EmployeeLoanSkipRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeLoanSkipRequestService {

    @Autowired
    private EmployeeLoanSkipRequestRepository skipRequestRepository;

    @Autowired
    private EmployeeLoanRepository employeeLoanRepository;

    public List<EmployeeLoanSkipRequestDTO> getRequests(Long loanId) {
        return skipRequestRepository.findByEmployeeLoanId(loanId).stream()
                .map(EmployeeLoanSkipRequestDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EmployeeLoanSkipRequestDTO createRequest(EmployeeLoanSkipRequestDTO dto) {
        EmployeeLoan loan = employeeLoanRepository.findById(dto.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        EmployeeLoanSkipRequest entity = new EmployeeLoanSkipRequest();
        entity.setEmployeeLoan(loan);
        entity.setSkipMonth(dto.getSkipMonth());
        entity.setReason(dto.getReason());
        entity.setStatus("Pending"); // Default to Pending
        entity.setRequestedDate(LocalDate.now());

        EmployeeLoanSkipRequest saved = skipRequestRepository.save(entity);
        return new EmployeeLoanSkipRequestDTO(saved);
    }

    @Transactional
    public EmployeeLoanSkipRequestDTO updateStatus(Long requestId, String status) {
        EmployeeLoanSkipRequest entity = skipRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Skip request not found"));

        entity.setStatus(status);
        entity.setActionedDate(LocalDate.now());
        EmployeeLoanSkipRequest updated = skipRequestRepository.save(entity);

        // Update parent cached approved skipped months list
        EmployeeLoan loan = entity.getEmployeeLoan();
        if (loan != null) {
            List<EmployeeLoanSkipRequest> approvedSkips = skipRequestRepository.findByEmployeeLoanIdAndStatus(loan.getId(), "Approved");
            String skippedStr = approvedSkips.stream()
                    .map(EmployeeLoanSkipRequest::getSkipMonth)
                    .sorted()
                    .collect(Collectors.joining(","));
            loan.setSkippedMonths(skippedStr);
            employeeLoanRepository.save(loan);
        }

        return new EmployeeLoanSkipRequestDTO(updated);
    }

    @Transactional
    public void deleteRequest(Long id) {
        EmployeeLoanSkipRequest entity = skipRequestRepository.findById(id).orElse(null);
        if (entity != null) {
            EmployeeLoan loan = entity.getEmployeeLoan();
            skipRequestRepository.deleteById(id);
            
            if (loan != null) {
                List<EmployeeLoanSkipRequest> approvedSkips = skipRequestRepository.findByEmployeeLoanIdAndStatus(loan.getId(), "Approved");
                String skippedStr = approvedSkips.stream()
                        .map(EmployeeLoanSkipRequest::getSkipMonth)
                        .sorted()
                        .collect(Collectors.joining(","));
                loan.setSkippedMonths(skippedStr);
                employeeLoanRepository.save(loan);
            }
        }
    }
}
