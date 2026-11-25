package com.tech3.erp.service;

import com.tech3.erp.dto.CommissionStatementDTO;
import com.tech3.erp.dto.CommissionStatementItemDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.CommissionStatement;
import com.tech3.erp.entity.CommissionStatementItem;
import com.tech3.erp.repository.CommissionStatementRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommissionStatementService {

    private final CommissionStatementRepository statementRepository;

    public CommissionStatementService(CommissionStatementRepository statementRepository) {
        this.statementRepository = statementRepository;
    }

    public CommissionStatementDTO createStatement(CommissionStatementDTO dto) {
        CommissionStatement entity = new CommissionStatement();
        mapDtoToEntity(dto, entity);

        CommissionStatement saved = statementRepository.save(entity);
        return new CommissionStatementDTO(saved);
    }

    @Transactional(readOnly = true)
    public CommissionStatementDTO getStatementById(Long id) {
        return statementRepository.findById(id)
                .map(CommissionStatementDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Commission Statement not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<CommissionStatementDTO> getAllStatements(Long companyId, String status) {
        List<CommissionStatement> list;
        if (companyId != null) {
            list = statementRepository.findByCompanyId(companyId);
        } else if (status != null && !status.isBlank()) {
            list = statementRepository.findByStatus(status);
        } else {
            list = statementRepository.findAll();
        }

        return list.stream()
                .filter(s -> s.getActiveFlag() == null || s.getActiveFlag())
                .filter(s -> status == null || status.isBlank() || status.equalsIgnoreCase(s.getStatus()))
                .sorted(Comparator.comparing(CommissionStatement::getId).reversed())
                .map(CommissionStatementDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<CommissionStatementDTO> getAllStatementsWithPagination(Long offset, Long limit, Long companyId, String status) {
        List<CommissionStatementDTO> list = getAllStatements(companyId, status);
        return PaginationHelper.paginate(list, offset, limit);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<CommissionStatementDTO> getAllStatementsWithPagination(int offset, int limit, Long companyId, String status) {
        return getAllStatementsWithPagination((long) offset, (long) limit, companyId, status);
    }

    public CommissionStatementDTO updateStatement(Long id, CommissionStatementDTO dto) {
        CommissionStatement entity = statementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commission Statement not found with ID: " + id));

        mapDtoToEntity(dto, entity);
        CommissionStatement saved = statementRepository.save(entity);
        return new CommissionStatementDTO(saved);
    }

    public CommissionStatementDTO recordPaymentReceipt(Long id, LocalDate paymentReceivedDate, String paymentMode,
                                                       String referenceNo, String bankAccount, String remarks) {
        CommissionStatement entity = statementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commission Statement not found with ID: " + id));

        entity.setPaymentReceivedDate(paymentReceivedDate != null ? paymentReceivedDate : LocalDate.now());
        entity.setPaymentMode(paymentMode);
        entity.setReferenceNo(referenceNo);
        entity.setBankAccount(bankAccount);
        if (remarks != null && !remarks.isBlank()) {
            entity.setRemarks((entity.getRemarks() != null ? entity.getRemarks() + " | " : "") + remarks);
        }
        entity.setStatus("Paid");

        CommissionStatement saved = statementRepository.save(entity);
        return new CommissionStatementDTO(saved);
    }

    public void deleteStatement(Long id) {
        CommissionStatement entity = statementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commission Statement not found with ID: " + id));
        entity.setActiveFlag(false);
        statementRepository.save(entity);
    }

    private void mapDtoToEntity(CommissionStatementDTO dto, CommissionStatement entity) {
        entity.setStatementNo(dto.getStatementNo());
        entity.setStatementDate(dto.getStatementDate());
        entity.setSeller(dto.getSeller());
        entity.setSellerAddress(dto.getSellerAddress());
        entity.setSellerGst(dto.getSellerGst());
        entity.setPeriod(dto.getPeriod());
        entity.setSacCode(dto.getSacCode() != null ? dto.getSacCode() : "996111");
        entity.setTotalTurnoverEligible(dto.getTotalTurnoverEligible());
        entity.setCommissionRate(dto.getCommissionRate() != null ? dto.getCommissionRate() : 0.01);
        entity.setGrossCommission(dto.getGrossCommission());
        entity.setGstRate(dto.getGstRate() != null ? dto.getGstRate() : 0.18);
        entity.setCgstAmount(dto.getCgstAmount() != null ? dto.getCgstAmount() : 0.0);
        entity.setSgstAmount(dto.getSgstAmount() != null ? dto.getSgstAmount() : 0.0);
        entity.setIgstAmount(dto.getIgstAmount() != null ? dto.getIgstAmount() : 0.0);
        entity.setTotalBillAmount(dto.getTotalBillAmount());
        entity.setTds194HRate(dto.getTds194HRate() != null ? dto.getTds194HRate() : 0.05);
        entity.setTds194HAmount(dto.getTds194HAmount() != null ? dto.getTds194HAmount() : 0.0);
        entity.setNetReceivable(dto.getNetReceivable());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "Billed");
        entity.setPaymentReceivedDate(dto.getPaymentReceivedDate());
        entity.setPaymentMode(dto.getPaymentMode());
        entity.setReferenceNo(dto.getReferenceNo());
        entity.setBankAccount(dto.getBankAccount());
        entity.setRemarks(dto.getRemarks());
        entity.setCompanyId(dto.getCompanyId());
        if (dto.getActiveFlag() != null) {
            entity.setActiveFlag(dto.getActiveFlag());
        }

        // Map items
        if (dto.getItems() != null) {
            entity.getItems().clear();
            for (CommissionStatementItemDTO itemDto : dto.getItems()) {
                CommissionStatementItem item = new CommissionStatementItem();
                item.setDispatchId(itemDto.getDispatchId());
                item.setMillInvoiceNo(itemDto.getMillInvoiceNo());
                item.setDispatchInvoiceNo(itemDto.getDispatchInvoiceNo());
                item.setInvoiceDate(itemDto.getInvoiceDate());
                item.setBuyerName(itemDto.getBuyerName());
                item.setBroker(itemDto.getBroker());
                item.setBrokerCommissionRate(itemDto.getBrokerCommissionRate() != null ? itemDto.getBrokerCommissionRate() : 0.0);
                item.setConstruction(itemDto.getConstruction());
                item.setMtrs(itemDto.getMtrs());
                item.setTaxableValue(itemDto.getTaxableValue());
                item.setCommissionRate(itemDto.getCommissionRate() != null ? itemDto.getCommissionRate() : entity.getCommissionRate());
                item.setCommissionAmount(itemDto.getCommissionAmount());
                item.setRemarks(itemDto.getRemarks());
                item.setStatement(entity);
                entity.getItems().add(item);
            }
        }
    }
}
