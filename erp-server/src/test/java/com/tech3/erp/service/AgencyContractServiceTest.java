package com.tech3.erp.service;

import com.tech3.erp.dto.AgencyContractDTO;
import com.tech3.erp.entity.AgencyContract;
import com.tech3.erp.repository.AgencyContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgencyContractServiceTest {

    @Mock
    private AgencyContractRepository contractRepository;

    @Mock
    private ApprovalService approvalService;

    @Mock
    private ScreenCodeService screenCodeService;

    @InjectMocks
    private AgencyContractService contractService;

    @Test
    void createsPendingContractWithPackingFieldsAndApprovalRecord() {
        LocalDate contractDate = LocalDate.of(2026, 11, 17);
        AgencyContractDTO request = new AgencyContractDTO();
        request.setContractNo("HFPL 001/17.11.26");
        request.setContractDate(contractDate);
        request.setCompanyId(10L);
        request.setSeller("Mill One");
        request.setBuyerName("Buyer One");
        request.setOrderedQty(1000.0);
        request.setPieceLength(50.0);
        request.setPacking("Roll");

        when(screenCodeService.getScreenIdByCode("AGENCY_CONTRACT")).thenReturn(25L);
        when(contractRepository.existsByContractNo("HFPL 001/17.11.26")).thenReturn(false);
        when(contractRepository.save(any(AgencyContract.class))).thenAnswer(invocation -> {
            AgencyContract saved = invocation.getArgument(0);
            saved.setId(41L);
            return saved;
        });

        AgencyContractDTO result = contractService.createContract(request);

        assertEquals((short) 0, result.getIsApproved());
        assertEquals(50.0, result.getPieceLength());
        assertEquals("Roll", result.getPacking());
        verify(approvalService).createApprovalFromTransactionUpdate(
                "HFPL 001/17.11.26",
                "AGENCY_CONTRACT",
                25L,
                41L,
                contractDate,
                "Agency contract submitted for approval",
                null,
                10L,
                null
        );
    }

    @Test
    void generatesNextAvailableContractNumberWhenClientOmitsPreview() {
        LocalDate contractDate = LocalDate.now();
        String dateSuffix = contractDate.format(DateTimeFormatter.ofPattern("dd.MM.yy"));
        AgencyContractDTO request = new AgencyContractDTO();
        request.setContractDate(contractDate);
        request.setCompanyId(10L);
        request.setSeller("Mill One");
        request.setBuyerName("Buyer One");
        request.setOrderedQty(1000.0);

        when(contractRepository.count()).thenReturn(0L);
        when(contractRepository.existsByContractNo("HFPL 001/" + dateSuffix)).thenReturn(true);
        when(contractRepository.existsByContractNo("HFPL 002/" + dateSuffix)).thenReturn(false);
        when(screenCodeService.getScreenIdByCode("AGENCY_CONTRACT")).thenReturn(25L);
        when(contractRepository.save(any(AgencyContract.class))).thenAnswer(invocation -> {
            AgencyContract saved = invocation.getArgument(0);
            saved.setId(42L);
            return saved;
        });

        AgencyContractDTO result = contractService.createContract(request);

        assertEquals("HFPL 002/" + dateSuffix, result.getContractNo());
    }

    @Test
    void rejectsDuplicateManuallyEnteredContractNumber() {
        AgencyContractDTO request = new AgencyContractDTO();
        request.setContractNo("HFPL 001/17.11.26");
        request.setCompanyId(10L);

        when(contractRepository.existsByContractNo(request.getContractNo())).thenReturn(true);

        IllegalArgumentException error = assertThrows(
                IllegalArgumentException.class,
                () -> contractService.createContract(request)
        );

        assertEquals("Contract No already exists. Please enter a unique number.", error.getMessage());
        verify(contractRepository, never()).save(any(AgencyContract.class));
    }
}
