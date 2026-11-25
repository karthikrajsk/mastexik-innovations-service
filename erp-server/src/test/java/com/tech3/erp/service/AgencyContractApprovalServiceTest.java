package com.tech3.erp.service;

import com.tech3.erp.dto.ApprovalRequestDTO;
import com.tech3.erp.entity.AgencyContract;
import com.tech3.erp.entity.ApprovalEntity;
import com.tech3.erp.repository.AgencyContractRepository;
import com.tech3.erp.repository.ApprovalRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AgencyContractApprovalServiceTest {

    @Test
    void approvingAgencyContractUpdatesContractApprovalState() {
        ApprovalRepository approvalRepository = mock(ApprovalRepository.class);
        AgencyContractRepository contractRepository = mock(AgencyContractRepository.class);
        ApprovalService approvalService = new ApprovalService(approvalRepository, contractRepository);

        ApprovalEntity approval = new ApprovalEntity();
        approval.setId(15L);
        approval.setScreenName("Agency Contract");
        approval.setDataId(41L);

        AgencyContract contract = new AgencyContract();
        contract.setId(41L);

        ApprovalRequestDTO request = new ApprovalRequestDTO();
        request.setApprovedComments("Reviewed");

        when(approvalRepository.findById(15L)).thenReturn(Optional.of(approval));
        when(approvalRepository.save(approval)).thenReturn(approval);
        when(contractRepository.findById(41L)).thenReturn(Optional.of(contract));
        when(contractRepository.save(contract)).thenReturn(contract);

        approvalService.approveEntry(15L, request, 8L);

        assertEquals((short) 1, contract.getIsApproved());
        verify(contractRepository).save(contract);
    }
}
