package com.tech3.erp.service;

import com.tech3.erp.dto.ApprovalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;

@Service
public class ApprovalWorkflowService {

    private static final Logger logger = LoggerFactory.getLogger(ApprovalWorkflowService.class);

    private final ApprovalService approvalService;
    private final ScreenCodeService screenCodeService;

    @Autowired
    public ApprovalWorkflowService(ApprovalService approvalService, ScreenCodeService screenCodeService) {
        this.approvalService = approvalService;
        this.screenCodeService = screenCodeService;
    }

    @Transactional
    public ApprovalDTO createOrUpdateApprovalForTransaction(
            String entryNo,
            String screenCode,
            Long screenId,
            Long dataId,
            LocalDate entryDate,
            String editNote,
            String additionalComments,
            Long companyId,
            Long userId) {

        if (editNote == null || editNote.trim().isEmpty()) {
            throw new IllegalArgumentException("Edit note cannot be empty for approval workflow");
        }

        return approvalService.createApprovalFromTransactionUpdate(
                entryNo,
                screenCode,
                screenId,
                dataId,
                entryDate,
                additionalComments,
                editNote,
                companyId,
                userId
        );
    }

    @Transactional
    public ApprovalDTO createOrUpdateApprovalForTransaction(
            String entryNo,
            String screenCode,
            Long screenId,
            Long dataId,
            LocalDate entryDate,
            String editNote,
            Long companyId,
            Long userId) {

        return createOrUpdateApprovalForTransaction(
                entryNo,
                screenCode,
                screenId,
                dataId,
                entryDate,
                editNote,
                null,
                companyId,
                userId
        );
    }

    @Transactional
    public ApprovalDTO createOrUpdateApprovalForTransaction(
            String entryNo,
            String screenCode,
            Long screenId,
            Long dataId,
            LocalDate entryDate,
            String editNote,
            String salesOrderNo,
            String additionalComments,
            Long companyId,
            Long userId) {

        logger.info("createOrUpdateApprovalForTransaction called - entryNo: {}, screenCode: {}, dataId: {}, companyId: {}, userId: {}", 
            entryNo, screenCode, dataId, companyId, userId);

        if (editNote == null || editNote.trim().isEmpty()) {
            logger.warn("Edit note is empty, throwing exception");
            throw new IllegalArgumentException("Edit note cannot be empty for approval workflow");
        }

        logger.info("Calling approvalService.createApprovalFromTransactionUpdate with salesOrderNo: {}", salesOrderNo);
        ApprovalDTO result = approvalService.createApprovalFromTransactionUpdate(
                entryNo,
                screenCode,
                screenId,
                dataId,
                entryDate,
                additionalComments,
                editNote,
                salesOrderNo,
                companyId,
                userId
        );
        logger.info("Approval created successfully - ID: {}", result.getId());
        return result;
    }

    public boolean shouldCreateApproval(boolean isUpdate, String editNote) {
        return isUpdate && editNote != null && !editNote.trim().isEmpty();
    }

    public void validateApprovalInput(String editNote, boolean isUpdate) {
        if (isUpdate && (editNote == null || editNote.trim().isEmpty())) {
            throw new IllegalArgumentException("Edit note is required when updating a transaction");
        }

        if (editNote != null && editNote.length() > 1000) {
            throw new IllegalArgumentException("Edit note cannot exceed 1000 characters");
        }
    }

    public ApprovalDTO getApprovalForTransaction(String entryNo, String screenName) {
        return approvalService.getApprovalByEntryNoAndScreen(entryNo, screenName);
    }

    public ApprovalDTO getApprovalById(Long id) {
        return approvalService.getApprovalById(id);
    }
}
