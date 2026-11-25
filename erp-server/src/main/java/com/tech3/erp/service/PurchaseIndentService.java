package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.PurchaseIndentDTO;
import com.tech3.erp.dto.PurchaseIndentItemDTO;
import com.tech3.erp.entity.ApprovalEntity;
import com.tech3.erp.entity.PurchaseIndent;
import com.tech3.erp.entity.PurchaseIndentItem;
import com.tech3.erp.repository.ApprovalRepository;
import com.tech3.erp.repository.PurchaseIndentRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseIndentService {

    private static final String DRAFT = "Draft";
    private static final String PENDING_APPROVAL = "Pending approval";
    private static final String APPROVED = "Approved";
    private static final String REJECTED = "Rejected";

    private final PurchaseIndentRepository purchaseIndentRepository;

    @Autowired(required = false)
    private ApprovalRepository approvalRepository;

    public PurchaseIndentService(PurchaseIndentRepository purchaseIndentRepository) {
        this.purchaseIndentRepository = purchaseIndentRepository;
    }

    @Transactional
    public PurchaseIndentDTO create(PurchaseIndentDTO dto) {
        validateHeader(dto);
        boolean submit = isSubmission(dto);
        List<PurchaseIndentItem> items = toItems(dto.getItems());
        validateSubmission(submit, items);

        PurchaseIndent indent = new PurchaseIndent();
        mapHeader(dto, indent);
        indent.setCompanyId(dto.getCompanyId());
        indent.setStatus(submit ? PENDING_APPROVAL : DRAFT);
        indent.replaceItems(items);

        String requestedNumber = trimToNull(dto.getIndentNo());
        if (requestedNumber != null) {
            ensureNumberAvailable(indent.getCompanyId(), requestedNumber);
            indent.setIndentNo(requestedNumber);
        } else {
            indent.setIndentNo("PI-" + java.util.UUID.randomUUID());
        }
        PurchaseIndent saved = purchaseIndentRepository.save(indent);
        if (requestedNumber == null) {
            saved.setIndentNo(String.format(Locale.ROOT, "PI-%08d", saved.getId()));
            saved = purchaseIndentRepository.save(saved);
        }
        return new PurchaseIndentDTO(saved);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<PurchaseIndentDTO> list(Long companyId, Long offset, Long limit, String status) {
        requireCompany(companyId);
        long safeOffset = offset == null ? 0 : offset;
        long safeLimit = limit == null ? 10 : limit;
        if (safeOffset < 0) {
            throw new IllegalArgumentException("Offset cannot be negative");
        }
        if (safeLimit < 1 || safeLimit > 1000) {
            throw new IllegalArgumentException("Limit must be between 1 and 1000");
        }
        String normalizedStatus = normalizeStatus(status);
        List<PurchaseIndentDTO> data = purchaseIndentRepository
                .findPageByCompanyId(companyId, normalizedStatus, safeLimit, safeOffset)
                .stream().map(PurchaseIndentDTO::new).toList();
        long total = purchaseIndentRepository.countByCompanyIdAndStatus(companyId, normalizedStatus);
        return new PaginatedResponse<>(total, safeOffset + data.size() < total, safeOffset > 0,
                safeOffset, safeLimit, data);
    }

    @Transactional(readOnly = true)
    public PurchaseIndentDTO getById(Long id, Long companyId) {
        requireCompany(companyId);
        return new PurchaseIndentDTO(findScoped(id, companyId));
    }

    @Transactional
    public PurchaseIndentDTO update(Long id, PurchaseIndentDTO dto) {
        validateHeader(dto);
        requireCompany(dto.getCompanyId());

        if (APPROVED.equalsIgnoreCase(dto.getStatus())) {
            return approve(id, dto.getCompanyId(), dto.getApproveDate(),
                    dto.getApprovalComments() != null ? dto.getApprovalComments() : dto.getRemarks(),
                    dto.getApprovedBy());
        }

        PurchaseIndent indent = purchaseIndentRepository.findByIdAndCompanyIdWithItemsForUpdate(id, dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Purchase Indent not found"));

        ensureEditable(indent);
        String requestedStatus = trimToNull(dto.getStatus());
        if (requestedStatus != null
                && !DRAFT.equalsIgnoreCase(requestedStatus)
                && !PENDING_APPROVAL.equalsIgnoreCase(requestedStatus)) {
            throw new IllegalArgumentException("Purchase indents can only be saved as Draft or Pending approval");
        }

        boolean submit = PENDING_APPROVAL.equals(indent.getStatus()) || isSubmission(dto);
        List<PurchaseIndentItem> items = toItems(dto.getItems());
        validateSubmission(submit, items);

        mapHeader(dto, indent);
        indent.replaceItems(items);
        if (submit) {
            indent.setStatus(PENDING_APPROVAL);
        }
        PurchaseIndent saved = purchaseIndentRepository.save(indent);
        return new PurchaseIndentDTO(saved);
    }

    @Transactional
    public void delete(Long id, Long companyId) {
        requireCompany(companyId);
        PurchaseIndent indent = purchaseIndentRepository.findByIdAndCompanyIdWithItemsForUpdate(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Indent not found"));
        ensureDeletable(indent);
        if (PENDING_APPROVAL.equalsIgnoreCase(indent.getStatus()) && approvalRepository != null) {
            approvalRepository.deleteByDataIdAndScreenName(id, "Purchase Indent");
        }
        purchaseIndentRepository.delete(indent);
    }

    @Transactional
    public PurchaseIndentDTO approve(Long id, Long companyId, java.time.LocalDate approveDate, String comments, Long userId) {
        PurchaseIndent indent;
        if (companyId != null && companyId > 0) {
            indent = purchaseIndentRepository.findByIdAndCompanyIdWithItemsForUpdate(id, companyId)
                    .orElseThrow(() -> new EntityNotFoundException("Purchase Indent not found"));
        } else {
            indent = purchaseIndentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Purchase Indent not found: " + id));
        }

        indent.setStatus(APPROVED);
        indent.setApproveDate(approveDate != null ? approveDate : java.time.LocalDate.now());
        indent.setApprovalComments(trimToNull(comments));
        indent.setApprovedBy(userId);

        PurchaseIndent saved = purchaseIndentRepository.save(indent);
        syncApproval(saved, true, indent.getApproveDate(), comments, userId);
        return new PurchaseIndentDTO(saved);
    }

    private void syncApproval(PurchaseIndent indent, boolean isApproved, java.time.LocalDate approveDate, String comments, Long userId) {
        if (approvalRepository == null || indent == null) {
            return;
        }
        try {
            List<ApprovalEntity> existing = approvalRepository.findByEntryNoAndScreenName(indent.getIndentNo(), "Purchase Indent");
            if (!existing.isEmpty()) {
                ApprovalEntity approval = existing.get(0);
                if (isApproved) {
                    approval.setIsApproved((short) 1);
                    approval.setApprovedDate(approveDate != null ? approveDate : java.time.LocalDate.now());
                    approval.setApprovedComments(comments);
                    approval.setApprovedBy(userId);
                    approval.setUpdatedBy(userId);
                    approvalRepository.save(approval);
                }
            }
        } catch (Exception ignored) {
        }
    }

    private PurchaseIndent findScoped(Long id, Long companyId) {
        if (id == null) {
            throw new IllegalArgumentException("Purchase Indent ID is required");
        }
        return purchaseIndentRepository.findByIdAndCompanyIdWithItems(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Purchase Indent not found"));
    }

    private void ensureDeletable(PurchaseIndent indent) {
        if (!DRAFT.equalsIgnoreCase(indent.getStatus())
                && !PENDING_APPROVAL.equalsIgnoreCase(indent.getStatus())) {
            throw new IllegalStateException("Only Draft or Pending approval purchase indents can be deleted");
        }
    }

    private void ensureEditable(PurchaseIndent indent) {
        if (!DRAFT.equalsIgnoreCase(indent.getStatus())
                && !PENDING_APPROVAL.equalsIgnoreCase(indent.getStatus())) {
            throw new IllegalStateException("Only Draft or Pending approval purchase indents can be modified");
        }
    }

    private void validateHeader(PurchaseIndentDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Purchase Indent data is required");
        }
        requireCompany(dto.getCompanyId());
        if (dto.getIndentDate() == null) {
            throw new IllegalArgumentException("Indent date is required");
        }
        if (trimToNull(dto.getDepartment()) == null) {
            throw new IllegalArgumentException("Department is required");
        }
        if (trimToNull(dto.getRequestedBy()) == null) {
            throw new IllegalArgumentException("Requested by is required");
        }
        if (dto.getRequiredDate() == null) {
            throw new IllegalArgumentException("Required date is required");
        }
        normalizeStatus(dto.getStatus());
    }

    private void requireCompany(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new IllegalArgumentException("A valid company ID is required");
        }
    }

    private boolean isSubmission(PurchaseIndentDTO dto) {
        return Boolean.TRUE.equals(dto.getSubmitForApproval())
                || PENDING_APPROVAL.equalsIgnoreCase(dto.getStatus());
    }

    private void validateSubmission(boolean submit, List<PurchaseIndentItem> items) {
        if (submit && items.isEmpty()) {
            throw new IllegalArgumentException("At least one item is required to submit for approval");
        }
    }

    private List<PurchaseIndentItem> toItems(List<PurchaseIndentItemDTO> itemDtos) {
        List<PurchaseIndentItem> items = new ArrayList<>();
        if (itemDtos == null) {
            return items;
        }
        for (PurchaseIndentItemDTO dto : itemDtos) {
            if (dto == null
                    || dto.getItemId() == null
                    || dto.getItemId() <= 0
                    || dto.getUomId() == null
                    || dto.getUomId() <= 0
                    || dto.getRequiredDate() == null
                    || trimToNull(dto.getItemName()) == null
                    || dto.getQuantity() == null
                    || dto.getQuantity().compareTo(new java.math.BigDecimal("0.0001")) < 0
                    || trimToNull(dto.getUomName()) == null) {
                throw new IllegalArgumentException(
                        "Each item requires a material, positive quantity, unit of measure, and required date");
            }
            PurchaseIndentItem item = new PurchaseIndentItem();
            item.setItemId(dto.getItemId());
            item.setItemName(dto.getItemName().trim());
            item.setSpecification(trimToNull(dto.getSpecification()));
            item.setQuantity(dto.getQuantity());
            item.setUomId(dto.getUomId());
            item.setUomName(dto.getUomName().trim());
            item.setRequiredDate(dto.getRequiredDate());
            items.add(item);
        }
        return items;
    }

    private void mapHeader(PurchaseIndentDTO dto, PurchaseIndent indent) {
        indent.setIndentDate(dto.getIndentDate());
        indent.setDepartment(dto.getDepartment().trim());
        indent.setRequestedBy(dto.getRequestedBy().trim());
        indent.setRequiredDate(dto.getRequiredDate());
        String priority = trimToNull(dto.getPriority()) == null ? "Normal" : dto.getPriority().trim();
        if (!List.of("Low", "Normal", "High", "Urgent").contains(priority)) {
            throw new IllegalArgumentException("Priority must be Low, Normal, High, or Urgent");
        }
        indent.setPriority(priority);
        indent.setRemarks(trimToNull(dto.getRemarks()));
    }

    private void ensureNumberAvailable(Long companyId, String indentNo) {
        if (purchaseIndentRepository.existsByCompanyIdAndIndentNo(companyId, indentNo)) {
            throw new IllegalArgumentException("Indent number is already in use");
        }
    }

    private String normalizeStatus(String status) {
        String value = trimToNull(status);
        if (value == null) {
            return null;
        }
        if (DRAFT.equalsIgnoreCase(value)) {
            return DRAFT;
        }
        if (PENDING_APPROVAL.equalsIgnoreCase(value) || "Pending".equalsIgnoreCase(value)) {
            return PENDING_APPROVAL;
        }
        if (APPROVED.equalsIgnoreCase(value)) {
            return APPROVED;
        }
        if (REJECTED.equalsIgnoreCase(value)) {
            return REJECTED;
        }
        throw new IllegalArgumentException("Status must be Draft, Pending approval, Approved, or Rejected");
    }

    private String trimToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
