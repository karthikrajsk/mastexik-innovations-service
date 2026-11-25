package com.tech3.erp.service;

import com.tech3.erp.dto.ApprovalDTO;
import com.tech3.erp.dto.ApprovalRequestDTO;
import com.tech3.erp.dto.ApprovalSummaryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.ApprovalEntity;
import com.tech3.erp.repository.ApprovalRepository;
import com.tech3.erp.repository.AgencyContractRepository;
import com.tech3.erp.repository.PurchaseIndentRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApprovalService {

    private static final Logger logger = LoggerFactory.getLogger(ApprovalService.class);

    private final ApprovalRepository approvalRepository;
    private final AgencyContractRepository agencyContractRepository;
    
    @Autowired
    private ScreenCodeService screenCodeService;

    @Autowired(required = false)
    private PurchaseIndentRepository purchaseIndentRepository;

    @Autowired
    public ApprovalService(ApprovalRepository approvalRepository, AgencyContractRepository agencyContractRepository) {
        this.approvalRepository = approvalRepository;
        this.agencyContractRepository = agencyContractRepository;
    }

    @Transactional
    public ApprovalDTO createOrUpdateApproval(ApprovalDTO dto, Long companyId, Long userId) {
        String normalizedScreenName = screenCodeService.getScreenNameByCode(dto.getScreenName());
        
        List<ApprovalEntity> approvals = approvalRepository.findByEntryNoAndScreenName(dto.getEntryNo(), normalizedScreenName);
        ApprovalEntity approval = approvals.isEmpty() ? new ApprovalEntity() : approvals.get(0);

        approval.setEntryNo(dto.getEntryNo());
        approval.setScreenName(normalizedScreenName);
        approval.setScreenId(dto.getScreenId());
        approval.setDataId(dto.getDataId());
        approval.setEntryDate(dto.getEntryDate());
        approval.setSalesOrderNo(dto.getSalesOrderNo());
        approval.setComments(dto.getComments());
        approval.setActionComments(dto.getActionComments());
        approval.setCompanyId(companyId);
        
        if (approval.getId() == null) {
            approval.setCreatedBy(userId);
            approval.setIsApproved((short) 0);
        } else {
            approval.setUpdatedBy(userId);
        }

        ApprovalEntity saved = approvalRepository.save(approval);
        if (purchaseIndentRepository != null && approval.getScreenName() != null &&
                approval.getScreenName().toLowerCase().contains("indent")) {
            try {
                if (approval.getDataId() != null && approval.getCompanyId() != null) {
                    purchaseIndentRepository.findByIdAndCompanyIdWithItemsForUpdate(approval.getDataId(), approval.getCompanyId())
                            .ifPresent(indent -> {
                                indent.setStatus("Approved");
                                indent.setApproveDate(approval.getApprovedDate());
                                indent.setApprovalComments(approval.getApprovedComments());
                                indent.setApprovedBy(userId);
                                purchaseIndentRepository.save(indent);
                            });
                }
            } catch (Exception ignored) {}
        }
        return toDTO(saved);
    }

    @Transactional
    public ApprovalDTO approveEntry(Long id, ApprovalRequestDTO approvalRequest, Long userId) {
        ApprovalEntity approval = approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Approval record not found: " + id));

        if (approvalRequest.getApprovedComments() == null || approvalRequest.getApprovedComments().trim().isEmpty()) {
            throw new IllegalArgumentException("Approval comments are required");
        }

        approval.setIsApproved((short) 1);
        approval.setApprovedDate(approvalRequest.getApprovedDate() != null ? approvalRequest.getApprovedDate() : LocalDate.now());
        approval.setApprovedComments(approvalRequest.getApprovedComments());
        approval.setApprovedBy(userId);
        approval.setUpdatedBy(userId);

        ApprovalEntity saved = approvalRepository.save(approval);
        String normalizedScreenName = approval.getScreenName() == null
                ? ""
                : approval.getScreenName().trim().toLowerCase();
        if (approval.getDataId() != null
                && normalizedScreenName.contains("agency")
                && normalizedScreenName.contains("contract")) {
            agencyContractRepository.findById(approval.getDataId()).ifPresent(contract -> {
                contract.setIsApproved((short) 1);
                agencyContractRepository.save(contract);
            });
        }
        return toDTO(saved);
    }

    public ApprovalDTO getApprovalById(Long id) {
        ApprovalEntity approval = approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Approval record not found: " + id));
        return toDTO(approval);
    }

    public ApprovalDTO getApprovalByEntryNo(String entryNo) {
        List<ApprovalEntity> approvals = approvalRepository.findByEntryNo(entryNo);
        if (approvals.isEmpty()) {
            throw new EntityNotFoundException("Approval record not found for entry: " + entryNo);
        }
        return toDTO(approvals.get(0));
    }

    public ApprovalDTO getApprovalByEntryNoAndScreen(String entryNo, String screenName) {
        List<ApprovalEntity> approvals = approvalRepository.findByEntryNoAndScreenName(entryNo, screenName);
        return !approvals.isEmpty() ? toDTO(approvals.get(0)) : null;
    }

    public PaginatedResponse<ApprovalDTO> getApprovalsByCompanyId(Long companyId, int page, int size) {
        Pageable pageable = PaginationHelper.getPageable(page, size);
        Page<ApprovalEntity> approvals = approvalRepository.findAllByCompanyIdOrderByDateDesc(companyId, pageable);
        return PaginationHelper.toPaginatedResponse(approvals, this::toDTO);
    }

    public PaginatedResponse<ApprovalDTO> getApprovalsByCompanyIdAndDate(Long companyId, LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PaginationHelper.getPageable(page, size);
        Page<ApprovalEntity> approvals = approvalRepository.findByCompanyIdAndEntryDateBetween(companyId, startDate, endDate, pageable);
        return PaginationHelper.toPaginatedResponse(approvals, this::toDTO);
    }

    public PaginatedResponse<ApprovalDTO> getApprovalsByCompanyIdAndStatus(Long companyId, Integer isApproved, int page, int size) {
        Pageable pageable = PaginationHelper.getPageable(page, size);
        Page<ApprovalEntity> approvals = approvalRepository.findByCompanyIdAndIsApproved(companyId, (short) isApproved.intValue(), pageable);
        return PaginationHelper.toPaginatedResponse(approvals, this::toDTO);
    }

    public PaginatedResponse<ApprovalDTO> getApprovalsByCompanyIdAndScreenName(Long companyId, String screenName, int page, int size) {
        Pageable pageable = PaginationHelper.getPageable(page, size);
        Page<ApprovalEntity> approvals = approvalRepository.findByCompanyIdAndScreenName(companyId, screenName, pageable);
        return PaginationHelper.toPaginatedResponse(approvals, this::toDTO);
    }

    public PaginatedResponse<ApprovalDTO> getApprovalsByCompanyIdAndDateRangeAndStatus(Long companyId, LocalDate startDate, LocalDate endDate, Integer isApproved, int page, int size) {
        Pageable pageable = PaginationHelper.getPageable(page, size);
        Page<ApprovalEntity> approvals = approvalRepository.findByCompanyIdAndDateRangeAndApprovalStatus(companyId, startDate, endDate, (short) isApproved.intValue(), pageable);
        return PaginationHelper.toPaginatedResponse(approvals, this::toDTO);
    }

    public List<ApprovalDTO> getApprovalsByScreenName(String screenName) {
        List<ApprovalEntity> approvals = approvalRepository.findByScreenName(screenName);
        return approvals.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<ApprovalDTO> getPendingApprovals(Long companyId) {
        List<ApprovalEntity> approvals = approvalRepository.findByCompanyId(companyId);
        return approvals.stream()
                .filter(a -> a.getIsApproved() == 0)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<ApprovalSummaryDTO> getApprovalSummaryByCompanyId(Long companyId, int page, int size) {
        Pageable pageable = PaginationHelper.getPageable(page, size);
        Page<ApprovalEntity> allApprovals = approvalRepository.findAllByCompanyIdOrderByDateDesc(companyId, pageable);
        
        Map<String, List<ApprovalEntity>> groupedBySalesOrder = allApprovals.getContent().stream()
                .collect(Collectors.groupingBy(a -> a.getSalesOrderNo() != null ? a.getSalesOrderNo() : "Unassigned"));
        
        List<ApprovalSummaryDTO> summaryList = new ArrayList<>();
        for (Map.Entry<String, List<ApprovalEntity>> entry : groupedBySalesOrder.entrySet()) {
            String salesOrderNo = entry.getKey();
            List<ApprovalEntity> approvals = entry.getValue();
            
            long total = approvals.size();
            long pending = approvals.stream().filter(a -> a.getIsApproved() == 0).count();
            long approved = approvals.stream().filter(a -> a.getIsApproved() == 1).count();
            
            summaryList.add(new ApprovalSummaryDTO(salesOrderNo, total, pending, approved));
        }
        
        return new PaginatedResponse<ApprovalSummaryDTO>(
                allApprovals.getTotalElements(),
                allApprovals.hasNext(),
                allApprovals.hasPrevious(),
                (long) allApprovals.getNumber(),
                (long) allApprovals.getSize(),
                summaryList
        );
    }

    public PaginatedResponse<ApprovalDTO> getApprovalsByCompanyIdAndSalesOrderNo(Long companyId, String salesOrderNo, int page, int size) {
        Pageable pageable = PaginationHelper.getPageable(page, size);
        Page<ApprovalEntity> approvals = approvalRepository.findByCompanyIdAndSalesOrderNo(companyId, salesOrderNo, pageable);
        return PaginationHelper.toPaginatedResponse(approvals, this::toDTO);
    }

    @Transactional
    public void deleteApproval(Long id) {
        ApprovalEntity approval = approvalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Approval record not found: " + id));
        approvalRepository.delete(approval);
    }

    @Transactional
    public ApprovalDTO createApprovalFromTransactionUpdate(String entryNo, String screenCode, Long screenId, 
                                                           Long dataId, LocalDate entryDate, String comments, 
                                                           String actionComments, Long companyId, Long userId) {
        return createApprovalFromTransactionUpdate(entryNo, screenCode, screenId, dataId, entryDate, 
                                                   comments, actionComments, null, companyId, userId);
    }

    @Transactional
    public ApprovalDTO createApprovalFromTransactionUpdate(String entryNo, String screenCode, Long screenId, 
                                                           Long dataId, LocalDate entryDate, String comments, 
                                                           String actionComments, String salesOrderNo, 
                                                           Long companyId, Long userId) {
        logger.info("createApprovalFromTransactionUpdate - entryNo: {}, screenCode: {}, salesOrderNo: {}, companyId: {}, userId: {}", 
            entryNo, screenCode, salesOrderNo, companyId, userId);
        
        String normalizedScreenName = screenCodeService.getScreenNameByCode(screenCode);
        
        List<ApprovalEntity> approvals = approvalRepository.findByEntryNoAndScreenName(entryNo, normalizedScreenName);
        ApprovalEntity approval = approvals.isEmpty() ? new ApprovalEntity() : approvals.get(0);

        boolean isNew = approval.getId() == null;

        approval.setEntryNo(entryNo);
        approval.setScreenName(normalizedScreenName);
        approval.setScreenId(screenId);
        approval.setDataId(dataId);
        approval.setEntryDate(entryDate);
        approval.setComments(comments);
        approval.setActionComments(actionComments);
        approval.setSalesOrderNo(salesOrderNo);
        approval.setCompanyId(companyId);
        
        if (isNew) {
            approval.setCreatedBy(userId);
            approval.setIsApproved((short) 0);
        } else {
            approval.setUpdatedBy(userId);
        }

        ApprovalEntity saved = approvalRepository.save(approval);
        logger.info("Approval saved successfully - ID: {}", saved.getId());
        return toDTO(saved);
    }

    private ApprovalDTO toDTO(ApprovalEntity entity) {
        ApprovalDTO dto = new ApprovalDTO();
        dto.setId(entity.getId());
        dto.setEntryNo(entity.getEntryNo());
        dto.setScreenName(entity.getScreenName());
        dto.setScreenId(entity.getScreenId());
        dto.setDataId(entity.getDataId());
        dto.setEntryDate(entity.getEntryDate());
        dto.setSalesOrderNo(entity.getSalesOrderNo());
        dto.setComments(entity.getComments());
        dto.setIsApproved(entity.getIsApproved());
        dto.setApprovedDate(entity.getApprovedDate());
        dto.setApprovedComments(entity.getApprovedComments());
        dto.setApprovedBy(entity.getApprovedBy());
        dto.setCompanyId(entity.getCompanyId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setActionComments(entity.getActionComments());
        return dto;
    }

    private ApprovalEntity toEntity(ApprovalDTO dto) {
        ApprovalEntity entity = new ApprovalEntity();
        entity.setId(dto.getId());
        entity.setEntryNo(dto.getEntryNo());
        entity.setScreenName(dto.getScreenName());
        entity.setScreenId(dto.getScreenId());
        entity.setDataId(dto.getDataId());
        entity.setEntryDate(dto.getEntryDate());
        entity.setSalesOrderNo(dto.getSalesOrderNo());
        entity.setComments(dto.getComments());
        entity.setIsApproved(dto.getIsApproved());
        entity.setApprovedDate(dto.getApprovedDate());
        entity.setApprovedComments(dto.getApprovedComments());
        entity.setApprovedBy(dto.getApprovedBy());
        entity.setCompanyId(dto.getCompanyId());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        return entity;
    }
}
