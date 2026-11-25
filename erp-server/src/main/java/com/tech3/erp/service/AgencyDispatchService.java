package com.tech3.erp.service;

import com.tech3.erp.dto.AgencyDispatchDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.AgencyContract;
import com.tech3.erp.entity.AgencyDispatch;
import com.tech3.erp.repository.AgencyContractRepository;
import com.tech3.erp.repository.AgencyDispatchRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AgencyDispatchService {

    private final AgencyDispatchRepository dispatchRepository;
    private final AgencyContractRepository contractRepository;

    public AgencyDispatchService(AgencyDispatchRepository dispatchRepository, AgencyContractRepository contractRepository) {
        this.dispatchRepository = dispatchRepository;
        this.contractRepository = contractRepository;
    }

    public AgencyDispatchDTO createDispatch(AgencyDispatchDTO dto) {
        AgencyDispatch entity = new AgencyDispatch();
        mapDtoToEntity(dto, entity);

        // Auto-calculate GST and Total if needed
        double gstRate = entity.getGstRate() != null ? entity.getGstRate() : 0.05;
        if (entity.getGstValue() == null || entity.getGstValue() == 0.0) {
            entity.setGstValue(Math.round(entity.getTaxableValue() * gstRate * 100.0) / 100.0);
        }
        if (entity.getTotalValue() == null || entity.getTotalValue() == 0.0) {
            entity.setTotalValue(Math.round((entity.getTaxableValue() + entity.getGstValue()) * 100.0) / 100.0);
        }
        if (entity.getCommissionEligibleValue() == null) {
            entity.setCommissionEligibleValue(entity.getTaxableValue());
        }

        AgencyDispatch saved = dispatchRepository.save(entity);

        // Auto-update linked Contract quantities
        if (entity.getContractNo() != null && !entity.getContractNo().trim().isEmpty()) {
            Optional<AgencyContract> contractOpt = contractRepository.findByContractNo(entity.getContractNo().trim());
            if (contractOpt.isPresent()) {
                AgencyContract contract = contractOpt.get();
                double newSupplied = (contract.getSuppliedQty() != null ? contract.getSuppliedQty() : 0.0) + entity.getDispatchedMtrs();
                double newBalance = (contract.getOrderedQty() != null ? contract.getOrderedQty() : 0.0) - newSupplied;
                contract.setSuppliedQty(newSupplied);
                contract.setBalanceQty(newBalance);
                if (newBalance <= 0) {
                    contract.setStatus("COMPLETED");
                } else {
                    contract.setStatus("PARTIAL");
                }
                contractRepository.save(contract);
            }
        }

        return new AgencyDispatchDTO(saved);
    }

    public AgencyDispatchDTO getDispatchById(Long id) {
        return dispatchRepository.findById(id)
                .map(AgencyDispatchDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Agency Dispatch not found with id: " + id));
    }

    public List<AgencyDispatchDTO> getAllDispatches(Long companyId) {
        return getAllDispatches(companyId, null);
    }

    public List<AgencyDispatchDTO> getAllDispatches(Long companyId, String paymentStatus) {
        List<AgencyDispatch> list = (companyId != null) 
                ? dispatchRepository.findByCompanyId(companyId) 
                : dispatchRepository.findAll();

        return list.stream()
                .filter(d -> d.getActiveFlag() == null || d.getActiveFlag())
                .filter(d -> paymentStatus == null || paymentStatus.isBlank() || "ALL".equalsIgnoreCase(paymentStatus) 
                        || (d.getPaymentStatus() != null && d.getPaymentStatus().equalsIgnoreCase(paymentStatus)))
                .sorted(Comparator.comparing(AgencyDispatch::getId, Comparator.reverseOrder()))
                .map(AgencyDispatchDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<AgencyDispatchDTO> getAllDispatchesWithPagination(Long offset, Long limit, Long companyId) {
        return getAllDispatchesWithPagination(offset, limit, companyId, null);
    }

    public PaginatedResponse<AgencyDispatchDTO> getAllDispatchesWithPagination(Long offset, Long limit, Long companyId, String paymentStatus) {
        List<AgencyDispatchDTO> all = getAllDispatches(companyId, paymentStatus);
        return PaginationHelper.paginate(all, offset, limit);
    }

    public AgencyDispatchDTO updateDispatch(Long id, AgencyDispatchDTO dto) {
        AgencyDispatch entity = dispatchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agency Dispatch not found with id: " + id));

        mapDtoToEntity(dto, entity);

        double gstRate = entity.getGstRate() != null ? entity.getGstRate() : 0.05;
        if (dto.getGstValue() != null) {
            entity.setGstValue(dto.getGstValue());
        } else {
            entity.setGstValue(Math.round(entity.getTaxableValue() * gstRate * 100.0) / 100.0);
        }

        if (dto.getTotalValue() != null) {
            entity.setTotalValue(dto.getTotalValue());
        } else {
            entity.setTotalValue(Math.round((entity.getTaxableValue() + entity.getGstValue()) * 100.0) / 100.0);
        }

        AgencyDispatch updated = dispatchRepository.save(entity);
        return new AgencyDispatchDTO(updated);
    }

    public void deleteDispatch(Long id) {
        if (!dispatchRepository.existsById(id)) {
            throw new EntityNotFoundException("Agency Dispatch not found with id: " + id);
        }
        dispatchRepository.deleteById(id);
    }

    private void mapDtoToEntity(AgencyDispatchDTO dto, AgencyDispatch entity) {
        entity.setMillInvoiceNo(dto.getMillInvoiceNo());
        entity.setInvoiceDate(dto.getInvoiceDate() != null ? dto.getInvoiceDate() : LocalDate.now());
        entity.setContractNo(dto.getContractNo());
        entity.setSeller(dto.getSeller());
        entity.setBuyerName(dto.getBuyerName());
        entity.setConstruction(dto.getConstruction());
        entity.setDispatchedMtrs(dto.getDispatchedMtrs());
        entity.setTaxableValue(dto.getTaxableValue());
        entity.setGstRate(dto.getGstRate() != null ? dto.getGstRate() : 0.05);
        entity.setGstValue(dto.getGstValue());
        entity.setTotalValue(dto.getTotalValue());
        entity.setLrNo(dto.getLrNo());
        entity.setTransporterName(dto.getTransporterName());
        entity.setVehicleNo(dto.getVehicleNo());
        entity.setCommissionEligibleValue(dto.getCommissionEligibleValue() != null ? dto.getCommissionEligibleValue() : dto.getTaxableValue());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "Delivered");
        if (dto.getPaymentStatus() != null) {
            entity.setPaymentStatus(dto.getPaymentStatus());
        }
        entity.setRemarks(dto.getRemarks());
        entity.setCompanyId(dto.getCompanyId());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
    }
}
