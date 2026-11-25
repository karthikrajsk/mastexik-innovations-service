package com.tech3.erp.service;

import com.tech3.erp.dto.AgencyContractDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.AgencyContract;
import com.tech3.erp.repository.AgencyContractRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AgencyContractService {

    private static final String AGENCY_CONTRACT_SCREEN_CODE = "AGENCY_CONTRACT";

    private final AgencyContractRepository contractRepository;
    private final ApprovalService approvalService;
    private final ScreenCodeService screenCodeService;

    public AgencyContractService(
            AgencyContractRepository contractRepository,
            ApprovalService approvalService,
            ScreenCodeService screenCodeService) {
        this.contractRepository = contractRepository;
        this.approvalService = approvalService;
        this.screenCodeService = screenCodeService;
    }

    public AgencyContractDTO createContract(AgencyContractDTO dto) {
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required to submit an agency contract for approval");
        }

        AgencyContract entity = new AgencyContract();

        String requestedContractNo = dto.getContractNo() == null ? "" : dto.getContractNo().trim();
        if (requestedContractNo.isEmpty()) {
            entity.setContractNo(generateContractNo());
        } else {
            if (contractRepository.existsByContractNo(requestedContractNo)) {
                throw new IllegalArgumentException("Contract No already exists. Please enter a unique number.");
            }
            entity.setContractNo(requestedContractNo);
        }

        mapDtoToEntity(dto, entity);

        if (entity.getSuppliedQty() == null) {
            entity.setSuppliedQty(0.0);
        }
        if (entity.getBalanceQty() == null) {
            entity.setBalanceQty(entity.getOrderedQty() - entity.getSuppliedQty());
        }

        AgencyContract saved = contractRepository.save(entity);
        approvalService.createApprovalFromTransactionUpdate(
                saved.getContractNo(),
                AGENCY_CONTRACT_SCREEN_CODE,
                screenCodeService.getScreenIdByCode(AGENCY_CONTRACT_SCREEN_CODE),
                saved.getId(),
                saved.getContractDate(),
                "Agency contract submitted for approval",
                null,
                saved.getCompanyId(),
                null
        );
        return new AgencyContractDTO(saved);
    }

    public AgencyContractDTO getContractById(Long id) {
        return contractRepository.findById(id)
                .map(AgencyContractDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Agency Contract not found with id: " + id));
    }

    public List<AgencyContractDTO> getAllContracts(Long companyId, String status) {
        List<AgencyContract> list;
        if (companyId != null) {
            list = contractRepository.findByCompanyId(companyId);
        } else if (status != null && !status.isEmpty() && !"ALL".equalsIgnoreCase(status)) {
            list = contractRepository.findByStatus(status.toUpperCase());
        } else {
            list = contractRepository.findAll();
        }

        return list.stream()
                .sorted(Comparator.comparing(AgencyContract::getId, Comparator.reverseOrder()))
                .map(AgencyContractDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<AgencyContractDTO> getAllContractsWithPagination(Long offset, Long limit, Long companyId, String status) {
        List<AgencyContractDTO> all = getAllContracts(companyId, status);
        return PaginationHelper.paginate(all, offset, limit);
    }

    public AgencyContractDTO updateContract(Long id, AgencyContractDTO dto) {
        AgencyContract entity = contractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agency Contract not found with id: " + id));

        if (dto.getContractNo() != null && !dto.getContractNo().trim().isEmpty()) {
            String requestedContractNo = dto.getContractNo().trim();
            if (!requestedContractNo.equals(entity.getContractNo())
                    && contractRepository.existsByContractNoAndIdNot(requestedContractNo, id)) {
                throw new IllegalArgumentException("Contract No already exists. Please enter a unique number.");
            }
            entity.setContractNo(requestedContractNo);
        }

        mapDtoToEntity(dto, entity);

        if (dto.getSuppliedQty() != null) {
            entity.setSuppliedQty(dto.getSuppliedQty());
        }
        if (dto.getBalanceQty() != null) {
            entity.setBalanceQty(dto.getBalanceQty());
        } else if (entity.getOrderedQty() != null && entity.getSuppliedQty() != null) {
            entity.setBalanceQty(entity.getOrderedQty() - entity.getSuppliedQty());
        }

        AgencyContract updated = contractRepository.save(entity);
        return new AgencyContractDTO(updated);
    }

    public AgencyContractDTO updateContractStatus(Long id, String status) {
        AgencyContract entity = contractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agency Contract not found with id: " + id));
        entity.setStatus(status);
        return new AgencyContractDTO(contractRepository.save(entity));
    }

    public void deleteContract(Long id) {
        if (!contractRepository.existsById(id)) {
            throw new EntityNotFoundException("Agency Contract not found with id: " + id);
        }
        try {
            contractRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Contract because it is referenced by other records");
        }
    }

    private void mapDtoToEntity(AgencyContractDTO dto, AgencyContract entity) {
        entity.setContractDate(dto.getContractDate() != null ? dto.getContractDate() : LocalDate.now());
        entity.setSeller(dto.getSeller());
        entity.setBuyerName(dto.getBuyerName());
        entity.setConstruction(dto.getConstruction());
        entity.setPieceLength(dto.getPieceLength());
        entity.setPacking(dto.getPacking());
        entity.setOrderedQty(dto.getOrderedQty());
        entity.setDespatchSchedule(dto.getDespatchSchedule());
        entity.setPaymentTerms(dto.getPaymentTerms());
        entity.setRatePerMtr(dto.getRatePerMtr());
        entity.setNumericRate(dto.getNumericRate());
        entity.setCdRate(dto.getCdRate());
        entity.setPoNo(dto.getPoNo());
        entity.setPoDate(dto.getPoDate());
        entity.setIcilConfirm(dto.getIcilConfirm() != null ? dto.getIcilConfirm() : "Confirmed");
        entity.setLcOpen(dto.getLcOpen() != null ? dto.getLcOpen() : "PENDING");
        entity.setCommissionRate(dto.getCommissionRate() != null ? dto.getCommissionRate() : 0.01);
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        if (dto.getIsApproved() != null) {
            entity.setIsApproved(dto.getIsApproved());
        }
        entity.setDeliveryLocation(dto.getDeliveryLocation());
        entity.setRemarks(dto.getRemarks());
        entity.setCompanyId(dto.getCompanyId());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
    }

    private String generateContractNo() {
        long sequence = contractRepository.count() + 1;
        String dateSuffix = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yy"));
        String contractNo;
        do {
            contractNo = String.format("HFPL %03d/%s", sequence++, dateSuffix);
        } while (contractRepository.existsByContractNo(contractNo));
        return contractNo;
    }
}
