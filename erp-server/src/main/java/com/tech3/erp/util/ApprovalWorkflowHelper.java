package com.tech3.erp.util;

import com.tech3.erp.dto.ApprovalDTO;
import com.tech3.erp.service.ApprovalWorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;

public class ApprovalWorkflowHelper {

    private static final Logger logger = LoggerFactory.getLogger(ApprovalWorkflowHelper.class);

    private final ApprovalWorkflowService approvalWorkflowService;

    public ApprovalWorkflowHelper(ApprovalWorkflowService approvalWorkflowService) {
        this.approvalWorkflowService = approvalWorkflowService;
    }

    public ApprovalDTO handleApprovalWorkflow(
            String entryNo,
            String screenCode,
            Long screenId,
            Long dataId,
            LocalDate entryDate,
            String editNote,
            boolean isUpdate,
            Long companyId,
            Long userId) {

        return handleApprovalWorkflow(entryNo, screenCode, screenId, dataId, entryDate, 
                                     editNote, null, isUpdate, companyId, userId);
    }

    public ApprovalDTO handleApprovalWorkflow(
            String entryNo,
            String screenCode,
            Long screenId,
            Long dataId,
            LocalDate entryDate,
            String editNote,
            String salesOrderNo,
            boolean isUpdate,
            Long companyId,
            Long userId) {

        if (!isUpdate) {
            logger.info("[ApprovalWorkflow] Skipping approval creation for new transaction: {}", entryNo);
            return null;
        }

        if (!approvalWorkflowService.shouldCreateApproval(isUpdate, editNote)) {
            logger.info("[ApprovalWorkflow] No edit note provided, skipping approval creation: {}", entryNo);
            return null;
        }

        try {
            approvalWorkflowService.validateApprovalInput(editNote, isUpdate);

            logger.info("[ApprovalWorkflow] Creating approval for transaction: {} on screen: {} with sales order no: {}", 
                       entryNo, screenCode, salesOrderNo);

            ApprovalDTO approval = approvalWorkflowService.createOrUpdateApprovalForTransaction(
                    entryNo,
                    screenCode,
                    screenId,
                    dataId,
                    entryDate,
                    editNote,
                    salesOrderNo,
                    null,
                    companyId,
                    userId
            );

            logger.info("[ApprovalWorkflow] Approval created successfully for entry: {} with ID: {}", entryNo, approval.getId());
            return approval;

        } catch (IllegalArgumentException e) {
            logger.warn("[ApprovalWorkflow] Validation error for entry {}: {}", entryNo, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("[ApprovalWorkflow] Error creating approval for entry: {}", entryNo, e);
            throw new RuntimeException("Failed to create approval record: " + e.getMessage(), e);
        }
    }

    public ApprovalDTO handleApprovalWorkflow(
            String entryNo,
            String screenCode,
            Long dataId,
            LocalDate entryDate,
            String editNote,
            boolean isUpdate,
            Long companyId,
            Long userId) {

        return handleApprovalWorkflow(
                entryNo,
                screenCode,
                null,
                dataId,
                entryDate,
                editNote,
                isUpdate,
                companyId,
                userId
        );
    }

    public boolean shouldSkipApprovalCreation(boolean isUpdate, String editNote) {
        return !isUpdate || editNote == null || editNote.trim().isEmpty();
    }
}
