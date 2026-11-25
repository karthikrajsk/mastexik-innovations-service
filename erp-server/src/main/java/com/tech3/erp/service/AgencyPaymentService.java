package com.tech3.erp.service;

import com.tech3.erp.dto.AgencyPaymentDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.AgencyDispatch;
import com.tech3.erp.entity.AgencyPayment;
import com.tech3.erp.repository.AgencyDispatchRepository;
import com.tech3.erp.repository.AgencyPaymentRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AgencyPaymentService {

    private final AgencyPaymentRepository paymentRepository;
    private final AgencyDispatchRepository dispatchRepository;

    public AgencyPaymentService(AgencyPaymentRepository paymentRepository, AgencyDispatchRepository dispatchRepository) {
        this.paymentRepository = paymentRepository;
        this.dispatchRepository = dispatchRepository;
    }

    public AgencyPaymentDTO createPayment(AgencyPaymentDTO dto) {
        AgencyPayment entity = new AgencyPayment();
        mapDtoToEntity(dto, entity);

        // Auto-calculate balance
        calculateAndSetBalanceAndStatus(entity);

        AgencyPayment saved = paymentRepository.save(entity);

        // Update dispatch payment status if found
        updateDispatchPaymentStatus(entity.getMillInvoiceNo());

        return new AgencyPaymentDTO(saved);
    }

    @Transactional(readOnly = true)
    public AgencyPaymentDTO getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(AgencyPaymentDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Agency Payment not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<AgencyPaymentDTO> getAllPayments(Long companyId) {
        List<AgencyPayment> list;
        if (companyId != null) {
            list = paymentRepository.findByCompanyId(companyId);
        } else {
            list = paymentRepository.findAll();
        }
        return list.stream()
                .filter(p -> p.getActiveFlag() == null || p.getActiveFlag())
                .sorted(Comparator.comparing(AgencyPayment::getId).reversed())
                .map(AgencyPaymentDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<AgencyPaymentDTO> getAllPaymentsWithPagination(long offset, long limit, Long companyId) {
        List<AgencyPaymentDTO> list = getAllPayments(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public AgencyPaymentDTO updatePayment(Long id, AgencyPaymentDTO dto) {
        AgencyPayment entity = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agency Payment not found with ID: " + id));

        mapDtoToEntity(dto, entity);
        calculateAndSetBalanceAndStatus(entity);

        AgencyPayment saved = paymentRepository.save(entity);

        // Update dispatch payment status if found
        updateDispatchPaymentStatus(entity.getMillInvoiceNo());

        return new AgencyPaymentDTO(saved);
    }

    public void deletePayment(Long id) {
        AgencyPayment entity = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agency Payment not found with ID: " + id));
        entity.setActiveFlag(false);
        paymentRepository.save(entity);

        updateDispatchPaymentStatus(entity.getMillInvoiceNo());
    }

    private void calculateAndSetBalanceAndStatus(AgencyPayment entity) {
        double totalVal = entity.getTotalInvoiceValue() != null ? entity.getTotalInvoiceValue() : 0.0;
        double tdsVal = entity.getTdsDeducted() != null ? entity.getTdsDeducted() : 0.0;
        double recVal = entity.getReceivedAmount() != null ? entity.getReceivedAmount() : 0.0;
        double debitVal = entity.getDebitNotes() != null ? entity.getDebitNotes() : 0.0;

        double balance = totalVal - (tdsVal + recVal + debitVal);
        balance = Math.round(balance * 100.0) / 100.0;
        if (balance < 0.0) {
            balance = 0.0;
        }
        entity.setBalanceAmount(balance);

        if (balance <= 0.0) {
            entity.setStatus("Fully Settled");
        } else {
            entity.setStatus("Partially Realized");
        }
    }

    private void updateDispatchPaymentStatus(String invoiceNo) {
        if (invoiceNo == null || invoiceNo.isBlank()) return;

        Optional<AgencyDispatch> optDispatch = dispatchRepository.findByInvoiceNo(invoiceNo);
        if (optDispatch.isPresent()) {
            AgencyDispatch dispatch = optDispatch.get();
            List<AgencyPayment> payments = paymentRepository.findByMillInvoiceNo(invoiceNo).stream()
                    .filter(p -> p.getActiveFlag() == null || p.getActiveFlag())
                    .collect(Collectors.toList());

            double totalReceived = payments.stream()
                    .mapToDouble(p -> (p.getReceivedAmount() != null ? p.getReceivedAmount() : 0.0) +
                                      (p.getTdsDeducted() != null ? p.getTdsDeducted() : 0.0) +
                                      (p.getDebitNotes() != null ? p.getDebitNotes() : 0.0))
                    .sum();

            double totalInvoice = dispatch.getTotalValue() != null ? dispatch.getTotalValue() : 0.0;
            if (totalInvoice > 0 && totalReceived >= totalInvoice - 0.01) {
                dispatch.setPaymentStatus("Fully Paid");
            } else if (totalReceived > 0) {
                dispatch.setPaymentStatus("Partially Paid");
            } else {
                dispatch.setPaymentStatus("Pending");
            }
            dispatchRepository.save(dispatch);
        }
    }

    private void mapDtoToEntity(AgencyPaymentDTO dto, AgencyPayment entity) {
        entity.setMillInvoiceNo(dto.getMillInvoiceNo());
        entity.setInvoiceDate(dto.getInvoiceDate());
        entity.setSeller(dto.getSeller());
        entity.setBuyerName(dto.getBuyerName());
        entity.setConstruction(dto.getConstruction());	
        entity.setTotalInvoiceValue(dto.getTotalInvoiceValue());
        entity.setTdsRate(dto.getTdsRate() != null ? dto.getTdsRate() : 0.001);
        entity.setTdsDeducted(dto.getTdsDeducted() != null ? dto.getTdsDeducted() : 0.0);
        entity.setReceivedAmount(dto.getReceivedAmount() != null ? dto.getReceivedAmount() : 0.0);
        entity.setDebitNotes(dto.getDebitNotes() != null ? dto.getDebitNotes() : 0.0);
        entity.setPaymentDate(dto.getPaymentDate());
        entity.setPaymentMode(dto.getPaymentMode() != null ? dto.getPaymentMode() : "RTGS / NEFT");
        entity.setReferenceNo(dto.getReferenceNo());
        entity.setRemarks(dto.getRemarks());
        entity.setCompanyId(dto.getCompanyId());
        if (dto.getActiveFlag() != null) {
            entity.setActiveFlag(dto.getActiveFlag());
        }
    }
}
