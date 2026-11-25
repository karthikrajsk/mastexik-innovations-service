package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.ScanDispatchDTO;
import com.tech3.erp.dto.ScannedRollDTO;
import com.tech3.erp.dto.SummaryRollDTO;
import com.tech3.erp.entity.ScanDispatch;
import com.tech3.erp.entity.ScannedRoll;
import com.tech3.erp.entity.SummaryRoll;
import com.tech3.erp.repository.BaleRollPackingRepository;
import com.tech3.erp.repository.GenerateInvoiceRepository;
import com.tech3.erp.repository.GeneratePackingItemRepository;
import com.tech3.erp.repository.InvoiceItemRepository;
import com.tech3.erp.repository.ScanDispatchRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScanDispatchService {

    @Autowired
    private ScanDispatchRepository repository;

    @Autowired
    private GenerateInvoiceRepository generateInvoiceRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;

    @Autowired
    private GeneratePackingItemRepository packingItemRepository;

    @Autowired
    private BaleRollPackingRepository baleRollPackingRepository;

    public ScanDispatchDTO createScanDispatch(ScanDispatchDTO dto) {
        validateInvoiceReference(dto);
        ScanDispatch entity = new ScanDispatch();
        applyDtoToEntity(entity, dto);
        ScanDispatch saved = repository.save(entity);
        markPackingItemsScanned(dto.getInvoiceId(), dto.getRolls());
        return convertToDTO(saved);
    }

    private void markPackingItemsScanned(Long invoiceId, List<ScannedRollDTO> rolls) {
        if (rolls == null || rolls.isEmpty()) {
            return;
        }
        List<String> rollNos = extractLoadedValues(rolls, ScannedRollDTO::getIsLoadedPacking, ScannedRollDTO::getRollNo);
        if (!rollNos.isEmpty()) {
            packingItemRepository.markScannedByRollNumbers(rollNos);
            baleRollPackingRepository.markRollScannedByRollNumbers(rollNos);
        }
        List<String> packingListNos = extractLoadedValues(rolls, ScannedRollDTO::getIsLoadedPacking, ScannedRollDTO::getPackingListNo);
        if (invoiceId != null && !packingListNos.isEmpty()) {
            invoiceItemRepository.markPackingScanned(invoiceId, packingListNos);
        }
    }

    private void unmarkPackingItemsScanned(Long invoiceId, List<ScannedRoll> rolls) {
        if (rolls == null || rolls.isEmpty()) {
            return;
        }
        List<String> rollNos = extractLoadedValues(rolls, ScannedRoll::getIsLoadedPacking, ScannedRoll::getRollNo);
        if (!rollNos.isEmpty()) {
            packingItemRepository.unmarkScannedByRollNumbers(rollNos);
            baleRollPackingRepository.unmarkRollScannedByRollNumbers(rollNos);
        }
        List<String> packingListNos = extractLoadedValues(rolls, ScannedRoll::getIsLoadedPacking, ScannedRoll::getPackingListNo);
        if (invoiceId != null && !packingListNos.isEmpty()) {
            invoiceItemRepository.unmarkPackingScanned(invoiceId, packingListNos);
        }
    }

    public PaginatedResponse<ScanDispatchDTO> getAllScanDispatchesWithPagination(Long offset, Long limit) {
        return buildPaginatedResponse(
                repository.findAllIdsDesc(),
                offset,
                limit,
                ids -> ids.isEmpty() ? List.of() : repository.findByIds(ids)
        );
    }

    public Optional<ScanDispatchDTO> getScanDispatchById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    public PaginatedResponse<ScanDispatchDTO> getScanDispatchesByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        return buildPaginatedResponse(
                repository.findIdsByCompanyId(companyId),
                offset,
                limit,
                ids -> ids.isEmpty() ? List.of() : repository.findByCompanyIdAndIds(companyId, ids)
        );
    }

    private PaginatedResponse<ScanDispatchDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit,
                                                                    Function<List<Long>, List<ScanDispatch>> fetcher) {
        long off = offset != null ? Math.max(0, offset) : 0;
        long lim = (limit != null && limit > 0) ? limit : PaginationHelper.getDefaultLimit();
        long total = allIds.size();
        int start = (int) Math.min(off, total);
        if (start >= total) {
            boolean hasPrev = off > 0 && total > 0;
            return new PaginatedResponse<>(total, false, hasPrev, off, lim, List.of());
        }
        int end = (int) Math.min(off + lim, total);
        List<Long> pageIds = allIds.subList(start, end);
        List<ScanDispatch> dispatches = pageIds.isEmpty() ? List.of() : fetcher.apply(pageIds);
        List<ScanDispatchDTO> dtoList = dispatches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(
                total,
                end < total,
                off > 0,
                off,
                lim,
                dtoList
        );
    }

    private void validateInvoiceReference(ScanDispatchDTO dto) {
        referenceValidationService.requireReference(
                dto.getInvoiceId(),
                () -> generateInvoiceRepository.findById(dto.getInvoiceId()),
                "Invoices",
                dto.getInvoiceNo());
    }

    private <T> List<String> extractLoadedValues(List<T> rolls,
                                                 Function<T, Integer> loadedGetter,
                                                 Function<T, String> valueGetter) {
        if (rolls == null || rolls.isEmpty()) {
            return List.of();
        }
        return rolls.stream()
                .filter(roll -> Objects.equals(loadedGetter.apply(roll), 1))
                .map(valueGetter)
                .filter(value -> value != null && !value.isBlank())
                .distinct()
                .collect(Collectors.toList());
    }

    public ScanDispatchDTO updateScanDispatch(Long id, ScanDispatchDTO dto) {
        validateInvoiceReference(dto);
        ScanDispatch existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Scan dispatch not found with id: " + id));
        applyDtoToEntity(existing, dto);
        ScanDispatch saved = repository.save(existing);
        markPackingItemsScanned(dto.getInvoiceId(), dto.getRolls());
        return convertToDTO(saved);
    }

    public ScanDispatchDTO updateScanDispatchByCompany(Long id, Long companyId, ScanDispatchDTO dto) {
        validateInvoiceReference(dto);
        ScanDispatch existing = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Scan dispatch not found for company " + companyId + " and id: " + id));
        dto.setCompanyId(companyId);
        applyDtoToEntity(existing, dto);
        ScanDispatch saved = repository.save(existing);
        markPackingItemsScanned(dto.getInvoiceId(), dto.getRolls());
        return convertToDTO(saved);
    }

    public void deleteScanDispatchByCompany(Long id, Long companyId) {
        ScanDispatch existing = repository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new IllegalArgumentException("Scan dispatch not found for company " + companyId + " and id: " + id));
        unmarkPackingItemsScanned(existing.getInvoiceId(), existing.getRolls());
        repository.delete(existing);
    }

    private void applyDtoToEntity(ScanDispatch entity, ScanDispatchDTO dto) {
        entity.setInvoiceId(dto.getInvoiceId());
        entity.setInvoiceNo(dto.getInvoiceNo());
        entity.setOrderId(dto.getOrderId());
        entity.setOrderNo(dto.getOrderNo());
        entity.setBuyerId(dto.getBuyerId());
        entity.setBuyerName(dto.getBuyerName());
        entity.setCompanyId(dto.getCompanyId());
        entity.setFabricMasterId(dto.getFabricMasterId());
        entity.setRolls(convertToScannedRolls(dto.getRolls()));
        entity.setSummary(convertToSummary(dto.getSummary()));
    }

    private List<ScannedRoll> convertToScannedRolls(List<ScannedRollDTO> dtoList) {
        return dtoList == null ? List.of() : dtoList.stream()
                .map(item -> new ScannedRoll(item.getPackingListNo(), item.getRollNo(), item.getIsLoadedPacking()))
                .collect(Collectors.toList());
    }

    private List<SummaryRoll> convertToSummary(List<SummaryRollDTO> dtoList) {
        return dtoList == null ? List.of() : dtoList.stream()
                .map(item -> new SummaryRoll(item.getPackingListNo(), item.getNoOfRolls(), item.getTotalMeters(), item.getNetWeight(), item.getGrossWeight()))
                .collect(Collectors.toList());
    }

    private ScanDispatchDTO convertToDTO(ScanDispatch entity) {
        ScanDispatchDTO dto = new ScanDispatchDTO();
        dto.setId(entity.getId());
        dto.setInvoiceId(entity.getInvoiceId());
        dto.setInvoiceNo(entity.getInvoiceNo());
        dto.setOrderId(entity.getOrderId());
        dto.setOrderNo(entity.getOrderNo());
        dto.setBuyerId(entity.getBuyerId());
        dto.setBuyerName(entity.getBuyerName());
        dto.setCompanyId(entity.getCompanyId());
        dto.setFabricMasterId(entity.getFabricMasterId());
        dto.setRolls(convertToScannedRollDTOs(entity.getRolls()));
        dto.setSummary(convertToSummaryDTOs(entity.getSummary()));
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    private List<ScannedRollDTO> convertToScannedRollDTOs(List<ScannedRoll> entities) {
        return entities == null ? List.of() : entities.stream()
                .map(item -> {
                    ScannedRollDTO dto = new ScannedRollDTO();
                    dto.setPackingListNo(item.getPackingListNo());
                    dto.setRollNo(item.getRollNo());
                    dto.setIsLoadedPacking(item.getIsLoadedPacking());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private List<SummaryRollDTO> convertToSummaryDTOs(List<SummaryRoll> entities) {
        return entities == null ? List.of() : entities.stream()
                .map(item -> {
                    SummaryRollDTO dto = new SummaryRollDTO();
                    dto.setPackingListNo(item.getPackingListNo());
                    dto.setNoOfRolls(item.getNoOfRolls());
                    dto.setTotalMeters(item.getTotalMeters());
                    dto.setNetWeight(item.getNetWeight());
                    dto.setGrossWeight(item.getGrossWeight());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}