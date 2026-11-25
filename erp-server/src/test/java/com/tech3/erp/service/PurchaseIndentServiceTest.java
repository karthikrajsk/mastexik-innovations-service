package com.tech3.erp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tech3.erp.dto.PurchaseIndentDTO;
import com.tech3.erp.dto.PurchaseIndentItemDTO;
import com.tech3.erp.entity.PurchaseIndent;
import com.tech3.erp.repository.ApprovalRepository;
import com.tech3.erp.repository.PurchaseIndentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Validation;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class PurchaseIndentServiceTest {

    @Mock
    private PurchaseIndentRepository repository;

    @Mock
    private ApprovalRepository approvalRepository;

    private PurchaseIndentService service;

    @BeforeEach
    void setUp() {
        service = new PurchaseIndentService(repository);
    }

    @Test
    void createDraftGeneratesNumberAndKeepsCompanyScope() {
        when(repository.save(any(PurchaseIndent.class))).thenAnswer(invocation -> {
            PurchaseIndent indent = invocation.getArgument(0);
            if (indent.getId() == null) {
                indent.setId(42L);
            }
            return indent;
        });

        PurchaseIndentDTO result = service.create(validRequest(false, false));

        ArgumentCaptor<PurchaseIndent> captor = ArgumentCaptor.forClass(PurchaseIndent.class);
        verify(repository, org.mockito.Mockito.times(2)).save(captor.capture());
        assertEquals(42L, result.getId());
        assertEquals("PI-00000042", result.getIndentNo());
        assertEquals("Draft", result.getStatus());
        assertEquals(17L, result.getCompanyId());
        assertEquals("Normal", result.getPriority());
        assertEquals(1, result.getItems().size());
    }

    @Test
    void submitRequiresAtLeastOneLineAndDoesNotPersistInvalidRequest() {
        PurchaseIndentDTO request = validRequest(true, false);
        request.setItems(List.of());

        assertThrows(IllegalArgumentException.class, () -> service.create(request));
        verify(repository, never()).save(any(PurchaseIndent.class));
    }

    @Test
    void createSubmissionPersistsPendingApproval() {
        when(repository.save(any(PurchaseIndent.class))).thenAnswer(invocation -> {
            PurchaseIndent indent = invocation.getArgument(0);
            if (indent.getId() == null) {
                indent.setId(8L);
            }
            return indent;
        });

        PurchaseIndentDTO result = service.create(validRequest(true, false));

        assertEquals("Pending approval", result.getStatus());
        assertEquals("PI-00000008", result.getIndentNo());
    }

    @Test
    void getByIdRequiresMatchingCompany() {
        when(repository.findByIdAndCompanyIdWithItems(9L, 17L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(9L, 17L));
        verify(repository).findByIdAndCompanyIdWithItems(9L, 17L);
    }

    @Test
    void updateRejectsApprovedIndent() {
        PurchaseIndent approved = new PurchaseIndent();
        approved.setStatus("Approved");
        when(repository.findByIdAndCompanyIdWithItemsForUpdate(9L, 17L)).thenReturn(Optional.of(approved));

        assertThrows(IllegalStateException.class, () -> service.update(9L, validRequest(false, false)));
        verify(repository, never()).save(any(PurchaseIndent.class));
    }

    @Test
    void updatePendingIndentKeepsItPending() {
        PurchaseIndent pending = new PurchaseIndent();
        pending.setStatus("Pending approval");
        pending.setCompanyId(17L);
        when(repository.findByIdAndCompanyIdWithItemsForUpdate(9L, 17L)).thenReturn(Optional.of(pending));
        when(repository.save(any(PurchaseIndent.class))).thenAnswer(invocation -> invocation.getArgument(0));
        PurchaseIndentDTO request = validRequest(false, false);

        PurchaseIndentDTO result = service.update(9L, request);

        assertEquals("Pending approval", result.getStatus());
        verify(repository).save(pending);
    }

    @Test
    void updatePendingIndentCannotBeDemotedToDraft() {
        PurchaseIndent pending = new PurchaseIndent();
        pending.setStatus("Pending approval");
        pending.setCompanyId(17L);
        when(repository.findByIdAndCompanyIdWithItemsForUpdate(9L, 17L)).thenReturn(Optional.of(pending));
        when(repository.save(any(PurchaseIndent.class))).thenAnswer(invocation -> invocation.getArgument(0));
        PurchaseIndentDTO request = validRequest(false, false);
        request.setStatus("Draft");

        PurchaseIndentDTO result = service.update(9L, request);

        assertEquals("Pending approval", result.getStatus());
    }

    @Test
    void deleteDraftUsesCompanyScopedIndent() {
        PurchaseIndent draft = new PurchaseIndent();
        draft.setStatus("Draft");
        when(repository.findByIdAndCompanyIdWithItemsForUpdate(9L, 17L)).thenReturn(Optional.of(draft));

        service.delete(9L, 17L);

        verify(repository).delete(draft);
    }

    @Test
    void deletePendingIndentAlsoRemovesApprovalEntry() {
        PurchaseIndent pending = new PurchaseIndent();
        pending.setStatus("Pending approval");
        when(repository.findByIdAndCompanyIdWithItemsForUpdate(9L, 17L)).thenReturn(Optional.of(pending));
        ReflectionTestUtils.setField(service, "approvalRepository", approvalRepository);

        service.delete(9L, 17L);

        verify(approvalRepository).deleteByDataIdAndScreenName(9L, "Purchase Indent");
        verify(repository).delete(pending);
    }

    @Test
    void deleteRejectsApprovedIndent() {
        PurchaseIndent approved = new PurchaseIndent();
        approved.setStatus("Approved");
        when(repository.findByIdAndCompanyIdWithItemsForUpdate(9L, 17L)).thenReturn(Optional.of(approved));

        assertThrows(IllegalStateException.class, () -> service.delete(9L, 17L));

        verify(repository, never()).delete(any(PurchaseIndent.class));
        verify(approvalRepository, never()).deleteByDataIdAndScreenName(any(), any());
    }

    @Test
    void deleteRequiresMatchingCompany() {
        when(repository.findByIdAndCompanyIdWithItemsForUpdate(9L, 17L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(9L, 17L));
        verify(repository, never()).delete(any(PurchaseIndent.class));
    }

    @Test
    void updateDraftCanSubmitForApproval() {
        PurchaseIndent draft = new PurchaseIndent();
        draft.setStatus("Draft");
        draft.setCompanyId(17L);
        when(repository.findByIdAndCompanyIdWithItemsForUpdate(9L, 17L)).thenReturn(Optional.of(draft));
        when(repository.save(any(PurchaseIndent.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseIndentDTO result = service.update(9L, validRequest(true, false));

        assertEquals("Pending approval", result.getStatus());
        verify(repository).findByIdAndCompanyIdWithItemsForUpdate(9L, 17L);
    }

    @Test
    void listUsesCompanyAndExactRowOffset() {
        when(repository.findPageByCompanyId(17L, "Draft", 10, 20)).thenReturn(List.of());
        when(repository.countByCompanyIdAndStatus(17L, "Draft")).thenReturn(21L);

        var result = service.list(17L, 20L, 10L, "draft");

        assertEquals(21L, result.getTotal());
        assertEquals(20L, result.getOffset());
        assertEquals(10L, result.getLimit());
        assertEquals(true, result.isPrev());
        verify(repository).findPageByCompanyId(eq(17L), eq("Draft"), eq(10L), eq(20L));
    }

    @Test
    void dtoValidationRequiresHeaderFields() {
        try (var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Set<String> fields = validatorFactory.getValidator().validate(new PurchaseIndentDTO())
                    .stream().map(error -> error.getPropertyPath().toString()).collect(Collectors.toSet());
            org.junit.jupiter.api.Assertions.assertTrue(fields.containsAll(
                    Set.of("companyId", "indentDate", "department", "requestedBy", "requiredDate")));
        }
    }

    @Test
    void dtoValidationRequiresSelectedItemUomAndRequiredDate() {
        PurchaseIndentDTO request = validRequest(false, false);
        request.getItems().get(0).setItemId(null);
        request.getItems().get(0).setUomId(null);
        request.getItems().get(0).setRequiredDate(null);

        try (var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Set<String> fields = validatorFactory.getValidator().validate(request).stream()
                    .map(error -> error.getPropertyPath().toString())
                    .collect(Collectors.toSet());
            org.junit.jupiter.api.Assertions.assertTrue(fields.containsAll(Set.of(
                    "items[0].itemId", "items[0].uomId", "items[0].requiredDate")));
        }
    }

    private PurchaseIndentDTO validRequest(boolean submit, boolean noItems) {
        PurchaseIndentDTO dto = new PurchaseIndentDTO();
        dto.setCompanyId(17L);
        dto.setIndentDate(LocalDate.of(2026, 10, 24));
        dto.setDepartment("Purchasing");
        dto.setRequestedBy("Casey");
        dto.setRequiredDate(LocalDate.of(2026, 11, 1));
        dto.setSubmitForApproval(submit);
        if (!noItems) {
            PurchaseIndentItemDTO item = new PurchaseIndentItemDTO();
            item.setItemId(5L);
            item.setItemName("Packing material");
            item.setQuantity(new BigDecimal("3"));
            item.setUomId(2L);
            item.setUomName("Box");
            item.setRequiredDate(LocalDate.of(2026, 11, 1));
            dto.setItems(List.of(item));
        }
        return dto;
    }
}
