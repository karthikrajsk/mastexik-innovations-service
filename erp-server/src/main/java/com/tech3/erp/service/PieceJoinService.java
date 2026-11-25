package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.PieceJoinDTO;
import com.tech3.erp.dto.PieceJoinDetailDTO;
import com.tech3.erp.entity.PieceJoin;
import com.tech3.erp.entity.PieceJoinDetail;
import com.tech3.erp.repository.PieceJoinDetailRepository;
import com.tech3.erp.repository.PieceJoinRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.FabricInspection;
import com.tech3.erp.repository.FabricInspectionRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PieceJoinService {

    @Autowired
    private PieceJoinRepository pieceJoinRepository;

    @Autowired
    private PieceJoinDetailRepository pieceJoinDetailRepository;

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    @Autowired
    private FabricInspectionRepository fabricInspectionRepository;

    @Transactional
    public PieceJoinDTO create(PieceJoinDTO dto) {
        PieceJoin entity = new PieceJoin();
        applyDtoToEntity(dto, entity);
        entity.setModeId(2L);
        if (dto.getJoinPiecesDetails() != null) {
            List<PieceJoinDetail> details = dto.getJoinPiecesDetails().stream()
                    .map(d -> {
                        PieceJoinDetail det = new PieceJoinDetail();
                        det.setPieceJoin(entity);
                        det.setPieceNo(d.getPieceNo());
                        det.setPieceMeter(d.getPieceMeter());
                        det.setNetWeight(d.getNetWeight());
                        det.setAvgWt(d.getAvgWt());
                        det.setBeamId(d.getBeamId());
                        det.setOperationId(d.getOperationId());
                        det.setFabricMasterId(d.getFabricMasterId());
                        det.setModeId(2L);
                        return det;
                    })
                    .collect(Collectors.toList());
            entity.setJoinPiecesDetails(details);
        }
        PieceJoin saved = pieceJoinRepository.save(entity);

        FabricInspection referenceInspection = null;

        PieceEntry newEntry = new PieceEntry();
        newEntry.setCompanyId(saved.getCompanyId());
        newEntry.setLoomId(saved.getLoomId());
        newEntry.setFlangeId(saved.getFlangeId());
        newEntry.setBeamId(saved.getBeamId());
        newEntry.setBeamNo(saved.getBeamNo());
        newEntry.setFabricMasterId(saved.getFabricMasterId());
        newEntry.setFabricId(saved.getFabricMasterId());
        newEntry.setPieceNumber(saved.getJoinedPieceNo());
        newEntry.setPieceMeter(saved.getJoinedPieceMeter());
        newEntry.setJoinedPieceId(0L);
        newEntry.setIsJoined(1);
        newEntry.setParentPieceId(0L);
        newEntry.setOperationId(0L);
        newEntry.setModeId(2L);
        BigDecimal totalNet = saved.getJoinPiecesDetails() != null ?
                saved.getJoinPiecesDetails().stream()
                        .map(PieceJoinDetail::getNetWeight)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;
        newEntry.setWeight(totalNet);
        try {
            newEntry.setQuantity(saved.getJoinedPieceMeter() != null && !saved.getJoinedPieceMeter().isEmpty()
                    ? new BigDecimal(saved.getJoinedPieceMeter()) : null);
        } catch (Exception ex) {
            newEntry.setQuantity(null);
        }
        if (saved.getJoinPiecesDetails() != null && !saved.getJoinPiecesDetails().isEmpty()) {
            List<String> pieceNos = saved.getJoinPiecesDetails().stream()
                    .map(PieceJoinDetail::getPieceNo)
                    .filter(p -> p != null && !p.isEmpty())
                    .collect(Collectors.toList());
            if (!pieceNos.isEmpty()) {
                List<PieceEntry> srcEntries = pieceEntryRepository.findByPieceNumberIn(pieceNos);
                srcEntries.forEach(pe -> {
                    pe.setOperationId(2L);
                    pe.setIsJoined(1);
                    pe.setJoinedPieceId(pe.getId());
                    pe.setIsParentPiece(0);
                });
                pieceEntryRepository.saveAll(srcEntries);
                PieceEntry any = srcEntries.stream().findFirst().orElse(null);
                if (any != null) {
                    newEntry.setIsLastPiece(any.getIsLastPiece());
                    newEntry.setIsFactoryFabric(any.getIsFactoryFabric());
                    newEntry.setWarehouse(any.getWarehouse());
                    newEntry.setOrderNo(any.getOrderNo());
                    newEntry.setOrderId(any.getOrderId());
                    newEntry.setOrderTypeId(any.getOrderTypeId());
                    newEntry.setNoOfFabricOutput(any.getNoOfFabricOutput());
                    newEntry.setFlangeNo(any.getFlangeNo());
                    newEntry.setInwardDate(any.getInwardDate());
                    if (newEntry.getFabricMasterId() == null) {
                        newEntry.setFabricMasterId(any.getFabricMasterId());
                        newEntry.setFabricId(any.getFabricMasterId());
                    }
                }
                PieceEntry src = srcEntries.stream()
                        .filter(pe -> pe.getDoffingPicks() != null || pe.getDoffingNo() != null || pe.getDoffingDate() != null)
                        .findFirst().orElse(null);
                if (src != null) {
                    newEntry.setDoffingNo(src.getDoffingNo());
                    newEntry.setDoffingDate(src.getDoffingDate());
                    newEntry.setDoffingPicks(src.getDoffingPicks());
                }
            }
        }
        newEntry.setInwardId(saved.getId());
        newEntry.setSourceType("Factory-Fabric");
        pieceEntryRepository.save(newEntry);

        FabricInspection fi = new FabricInspection();
        fi.setCompanyId(newEntry.getCompanyId());
        fi.setPieceNo(newEntry.getPieceNumber());
        try { fi.setPieceMeters(newEntry.getQuantity() != null ? newEntry.getQuantity().doubleValue() : null); } catch (Exception e) {}
        try { fi.setNetWeight(newEntry.getWeight() != null ? newEntry.getWeight().doubleValue() : null); } catch (Exception e) {}
        fi.setIsFactoryFabric(newEntry.getIsFactoryFabric());
        fi.setFabricMasterId(newEntry.getFabricMasterId());
        fabricInspectionRepository.save(fi);

        if (saved.getJoinPiecesDetails() != null && !saved.getJoinPiecesDetails().isEmpty()) {
            List<String> pieceNos = saved.getJoinPiecesDetails().stream()
                    .map(PieceJoinDetail::getPieceNo)
                    .filter(p -> p != null && !p.isEmpty())
                    .collect(Collectors.toList());
            if (!pieceNos.isEmpty()) {
                List<PieceEntry> srcEntries = pieceEntryRepository.findByPieceNumberIn(pieceNos);
                srcEntries.forEach(pe -> {
                    pe.setOperationId(2L);
                    pe.setIsJoined(1);
                    pe.setJoinedPieceId(pe.getId());
                    pe.setIsParentPiece(0);
                });
                pieceEntryRepository.saveAll(srcEntries);
            }
        }
        return new PieceJoinDTO(saved);
    }

    public PieceJoinDTO getById(Long id) {
        PieceJoin entity = pieceJoinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PieceJoin not found with id: " + id));
        return new PieceJoinDTO(entity);
    }

    public List<PieceJoinDTO> list() {
        return pieceJoinRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(PieceJoinDTO::new)
                .collect(Collectors.toList());
    }

    public List<PieceJoinDTO> listByCompany(Long companyId) {
        return pieceJoinRepository.findByCompanyId(companyId).stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .map(PieceJoinDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<PieceJoinDTO> listByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        List<PieceJoinDTO> list = listByCompany(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    @Transactional
    public PieceJoinDTO update(Long id, PieceJoinDTO dto) {
        PieceJoin entity = pieceJoinRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PieceJoin not found with id: " + id));
        List<String> previousSourcePieceNos = entity.getJoinPiecesDetails() == null ? Collections.emptyList()
                : entity.getJoinPiecesDetails().stream()
                        .map(PieceJoinDetail::getPieceNo)
                        .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                        .collect(Collectors.toList());
        String previousJoinedPieceNo = entity.getJoinedPieceNo();
        applyDtoToEntity(dto, entity);
        entity.setModeId(2L);
        entity.getJoinPiecesDetails().clear();
        if (dto.getJoinPiecesDetails() != null) {
            dto.getJoinPiecesDetails().forEach(d -> {
                PieceJoinDetail det = new PieceJoinDetail();
                det.setPieceJoin(entity);
                det.setPieceNo(d.getPieceNo());
                det.setPieceMeter(d.getPieceMeter());
                det.setNetWeight(d.getNetWeight());
                det.setAvgWt(d.getAvgWt());
                det.setBeamId(d.getBeamId());
                det.setOperationId(d.getOperationId());
                det.setFabricMasterId(d.getFabricMasterId());
                det.setModeId(2L);
                entity.getJoinPiecesDetails().add(det);
            });
        }
        PieceJoin saved = pieceJoinRepository.save(entity);
        syncPieceEntriesAfterJoinUpdate(saved, previousSourcePieceNos, previousJoinedPieceNo, null);
        return new PieceJoinDTO(saved);
    }

    @Transactional
    public PieceJoinDTO updateByCompany(Long id, Long companyId, PieceJoinDTO dto) {
        PieceJoin entity = pieceJoinRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("PieceJoin not found with id: " + id + " and companyId: " + companyId));
        List<String> previousSourcePieceNos = entity.getJoinPiecesDetails() == null ? Collections.emptyList()
                : entity.getJoinPiecesDetails().stream()
                        .map(PieceJoinDetail::getPieceNo)
                        .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                        .collect(Collectors.toList());
        String previousJoinedPieceNo = entity.getJoinedPieceNo();
        applyDtoToEntity(dto, entity);
        entity.setModeId(2L);
        entity.getJoinPiecesDetails().clear();
        if (dto.getJoinPiecesDetails() != null) {
            dto.getJoinPiecesDetails().forEach(d -> {
                PieceJoinDetail det = new PieceJoinDetail();
                det.setPieceJoin(entity);
                det.setPieceNo(d.getPieceNo());
                det.setPieceMeter(d.getPieceMeter());
                det.setNetWeight(d.getNetWeight());
                det.setAvgWt(d.getAvgWt());
                det.setBeamId(d.getBeamId());
                det.setOperationId(d.getOperationId());
                det.setFabricMasterId(d.getFabricMasterId());
                det.setModeId(2L);
                entity.getJoinPiecesDetails().add(det);
            });
        }
        PieceJoin saved = pieceJoinRepository.save(entity);
        syncPieceEntriesAfterJoinUpdate(saved, previousSourcePieceNos, previousJoinedPieceNo, companyId);
        return new PieceJoinDTO(saved);
    }

    private void syncPieceEntriesAfterJoinUpdate(PieceJoin saved, List<String> previousSourcePieceNos, String previousJoinedPieceNo, Long companyId) {
        List<String> currentSourcePieceNos = saved.getJoinPiecesDetails() == null ? Collections.emptyList()
                : saved.getJoinPiecesDetails().stream()
                        .map(PieceJoinDetail::getPieceNo)
                        .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                        .collect(Collectors.toList());

        Set<String> previousSet = new LinkedHashSet<>(previousSourcePieceNos == null ? Collections.emptyList() : previousSourcePieceNos);
        Set<String> currentSet = new LinkedHashSet<>(currentSourcePieceNos);

        List<String> removedSourcePieces = previousSet.stream()
                .filter(pieceNo -> !currentSet.contains(pieceNo))
                .collect(Collectors.toList());
        if (!removedSourcePieces.isEmpty()) {
            List<PieceEntry> removedEntries = pieceEntryRepository.findByPieceNumberIn(removedSourcePieces);
            List<PieceEntry> filteredRemoved = filterByCompany(removedEntries, companyId);
            filteredRemoved.forEach(pe -> {
                pe.setOperationId(0L);
                pe.setIsJoined(0);
                pe.setJoinedPieceId(0L);
                pe.setIsParentPiece(0);
            });
            pieceEntryRepository.saveAll(filteredRemoved);
        }

        List<PieceEntry> sourceEntries = currentSourcePieceNos.isEmpty()
                ? new ArrayList<>()
                : pieceEntryRepository.findByPieceNumberIn(currentSourcePieceNos);
        sourceEntries = filterByCompany(sourceEntries, companyId);
        if (!sourceEntries.isEmpty()) {
            sourceEntries.forEach(pe -> {
                pe.setOperationId(2L);
                pe.setIsJoined(1);
                pe.setJoinedPieceId(pe.getId());
                pe.setIsParentPiece(0);
            });
            pieceEntryRepository.saveAll(sourceEntries);
        }

        PieceEntry joinedEntry = null;
        if (previousJoinedPieceNo != null && !previousJoinedPieceNo.isEmpty()) {
            joinedEntry = pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(previousJoinedPieceNo).orElse(null);
            if (joinedEntry != null && companyId != null && !companyId.equals(joinedEntry.getCompanyId())) {
                joinedEntry = null;
            }
        }
        if (joinedEntry == null && saved.getJoinedPieceNo() != null && !saved.getJoinedPieceNo().isEmpty()) {
            joinedEntry = pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(saved.getJoinedPieceNo()).orElse(null);
            if (joinedEntry != null && companyId != null && !companyId.equals(joinedEntry.getCompanyId())) {
                joinedEntry = null;
            }
        }
        if (joinedEntry == null) {
            joinedEntry = new PieceEntry();
        }

        joinedEntry.setCompanyId(saved.getCompanyId());
        joinedEntry.setLoomId(saved.getLoomId());
        joinedEntry.setFlangeId(saved.getFlangeId());
        joinedEntry.setBeamId(saved.getBeamId());
        joinedEntry.setBeamNo(saved.getBeamNo());
        joinedEntry.setFabricMasterId(saved.getFabricMasterId());
        joinedEntry.setFabricId(saved.getFabricMasterId());
        joinedEntry.setPieceNumber(saved.getJoinedPieceNo());
        joinedEntry.setPieceMeter(saved.getJoinedPieceMeter());
        joinedEntry.setJoinedPieceId(0L);
        joinedEntry.setIsJoined(1);
        joinedEntry.setParentPieceId(0L);
        joinedEntry.setOperationId(0L);
        joinedEntry.setModeId(2L);

        BigDecimal totalNet = saved.getJoinPiecesDetails() != null
                ? saved.getJoinPiecesDetails().stream()
                        .map(PieceJoinDetail::getNetWeight)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;
        joinedEntry.setWeight(totalNet);
        try {
            joinedEntry.setQuantity(saved.getJoinedPieceMeter() != null && !saved.getJoinedPieceMeter().isEmpty()
                    ? new BigDecimal(saved.getJoinedPieceMeter()) : null);
        } catch (Exception ex) {
            joinedEntry.setQuantity(null);
        }

        PieceEntry any = sourceEntries.stream().findFirst().orElse(null);
        if (any != null) {
            joinedEntry.setIsLastPiece(any.getIsLastPiece());
            joinedEntry.setIsFactoryFabric(any.getIsFactoryFabric());
            joinedEntry.setWarehouse(any.getWarehouse());
            joinedEntry.setOrderNo(any.getOrderNo());
            joinedEntry.setOrderId(any.getOrderId());
            joinedEntry.setOrderTypeId(any.getOrderTypeId());
            joinedEntry.setNoOfFabricOutput(any.getNoOfFabricOutput());
            joinedEntry.setFlangeNo(any.getFlangeNo());
            joinedEntry.setInwardDate(any.getInwardDate());
            if (joinedEntry.getFabricMasterId() == null) {
                joinedEntry.setFabricMasterId(any.getFabricMasterId());
                joinedEntry.setFabricId(any.getFabricMasterId());
            }
        }
        PieceEntry src = sourceEntries.stream()
                .filter(pe -> pe.getDoffingPicks() != null || pe.getDoffingNo() != null || pe.getDoffingDate() != null)
                .findFirst().orElse(null);
        if (src != null) {
            joinedEntry.setDoffingNo(src.getDoffingNo());
            joinedEntry.setDoffingDate(src.getDoffingDate());
            joinedEntry.setDoffingPicks(src.getDoffingPicks());
        }

        joinedEntry.setInwardId(saved.getId());
        joinedEntry.setSourceType("Factory-Fabric");
        PieceEntry savedJoinedEntry = pieceEntryRepository.save(joinedEntry);

        FabricInspection inspection = fabricInspectionRepository.findFirstByPieceEntryId(savedJoinedEntry.getId()).orElse(new FabricInspection());
        inspection.setPieceEntryId(savedJoinedEntry.getId());
        inspection.setCompanyId(savedJoinedEntry.getCompanyId());
        inspection.setPieceNo(savedJoinedEntry.getPieceNumber());
        try {
            inspection.setPieceMeters(savedJoinedEntry.getQuantity() != null ? savedJoinedEntry.getQuantity().doubleValue() : null);
        } catch (Exception ignore) {}
        try {
            inspection.setNetWeight(savedJoinedEntry.getWeight() != null ? savedJoinedEntry.getWeight().doubleValue() : null);
        } catch (Exception ignore) {}
        inspection.setIsFactoryFabric(savedJoinedEntry.getIsFactoryFabric());
        inspection.setFabricMasterId(savedJoinedEntry.getFabricMasterId());
        fabricInspectionRepository.save(inspection);
    }

    private List<PieceEntry> filterByCompany(List<PieceEntry> entries, Long companyId) {
        if (entries == null || entries.isEmpty() || companyId == null) {
            return entries == null ? new ArrayList<>() : entries;
        }
        return entries.stream()
                .filter(pe -> pe != null && companyId.equals(pe.getCompanyId()))
                .collect(Collectors.toList());
    }

    // @Transactional
    // public void delete(Long id) {
    //     if (!pieceJoinRepository.existsById(id)) {
    //         throw new EntityNotFoundException("PieceJoin not found with id: " + id);
    //     }
    //     try {
    //         pieceJoinDetailRepository.deleteByPieceJoinId(id);
    //         pieceJoinRepository.deleteById(id);
    //     } catch (DataIntegrityViolationException e) {
    //         throw new IllegalStateException("Cannot delete PieceJoin because it is referenced by other entities");
    //     }
    // }

    // @Transactional
    // public void deleteByCompany(Long id, Long companyId) {
    //     PieceJoin entity = pieceJoinRepository.findByIdAndCompanyId(id, companyId)
    //             .orElseThrow(() -> new EntityNotFoundException("PieceJoin not found with id: " + id + " and companyId: " + companyId));
    //     try {
    //         pieceJoinDetailRepository.deleteByPieceJoinId(entity.getId());
    //         pieceJoinRepository.deleteByIdAndCompanyId(id, companyId);
    //     } catch (DataIntegrityViolationException e) {
    //         throw new IllegalStateException("Cannot delete PieceJoin because it is referenced by other entities");
    //     }
    // }

@Transactional
public void delete(Long id) {
    PieceJoin entity = pieceJoinRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("PieceJoin not found with id: " + id));
    List<String> sourcePieceNos = entity.getJoinPiecesDetails() == null ? Collections.emptyList()
            : entity.getJoinPiecesDetails().stream()
                    .map(PieceJoinDetail::getPieceNo)
                    .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                    .collect(Collectors.toList());
    ensurePiecesNotConsumed(sourcePieceNos, null);
    String joinedPieceNo = entity.getJoinedPieceNo();
    if (joinedPieceNo != null && !joinedPieceNo.isEmpty()) {
        ensurePiecesNotConsumed(Collections.singletonList(joinedPieceNo), null);
    }
    
    // First, revert all source pieces to their original state
    if (!sourcePieceNos.isEmpty()) {
        List<PieceEntry> srcEntries = pieceEntryRepository.findByPieceNumberIn(sourcePieceNos);
        srcEntries.forEach(pe -> {
            // Revert source pieces to original state
            pe.setOperationId(0L); // Original operation
            pe.setIsJoined(0); // Not joined
            pe.setJoinedPieceId(0L); // Reset joined piece ID
            pe.setIsParentPiece(0);
        });
        pieceEntryRepository.saveAll(srcEntries);
    }
    
    // Delete the joined piece entry
    pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(joinedPieceNo).ifPresent(joinedEntry -> {
        // Delete associated fabric inspection first
        fabricInspectionRepository.deleteByPieceEntryId(joinedEntry.getId());
        // Then delete the joined piece entry
        pieceEntryRepository.delete(joinedEntry);
    });
    
    try {
        // Delete piece join details and the join itself
        pieceJoinDetailRepository.deleteByPieceJoinId(id);
        pieceJoinRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
        throw new IllegalStateException("Cannot delete PieceJoin because it is referenced by other entities");
    }
}

@Transactional
public void deleteByCompany(Long id, Long companyId) {
    PieceJoin entity = pieceJoinRepository.findByIdAndCompanyId(id, companyId)
            .orElseThrow(() -> new EntityNotFoundException("PieceJoin not found with id: " + id + " and companyId: " + companyId));
    List<String> sourcePieceNos = entity.getJoinPiecesDetails() == null ? Collections.emptyList()
            : entity.getJoinPiecesDetails().stream()
                    .map(PieceJoinDetail::getPieceNo)
                    .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                    .collect(Collectors.toList());
    ensurePiecesNotConsumed(sourcePieceNos, companyId);
    String joinedPieceNo = entity.getJoinedPieceNo();
    if (joinedPieceNo != null && !joinedPieceNo.isEmpty()) {
        ensurePiecesNotConsumed(Collections.singletonList(joinedPieceNo), companyId);
    }
    
    // First, revert all source pieces to their original state
    if (!sourcePieceNos.isEmpty()) {
        List<PieceEntry> srcEntries = pieceEntryRepository.findByPieceNumberIn(sourcePieceNos).stream()
                .filter(pe -> pe.getCompanyId().equals(companyId))
                .collect(Collectors.toList());
        
        srcEntries.forEach(pe -> {
            // Revert source pieces to original state
            pe.setOperationId(0L); // Original operation
            pe.setIsJoined(0); // Not joined
            pe.setJoinedPieceId(0L); // Reset joined piece ID
            pe.setIsParentPiece(0);
        });
        pieceEntryRepository.saveAll(srcEntries);
    }
    
    // Delete the joined piece entry
    pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(joinedPieceNo).ifPresent(joinedEntry -> {
        // Check company ID match
        if (joinedEntry.getCompanyId().equals(companyId)) {
            // Delete associated fabric inspection first
            fabricInspectionRepository.deleteByPieceEntryId(joinedEntry.getId());
            // Then delete the joined piece entry
            pieceEntryRepository.delete(joinedEntry);
        }
    });
    
    try {
        // Delete piece join details and the join itself
        pieceJoinDetailRepository.deleteByPieceJoinId(entity.getId());
        pieceJoinRepository.deleteByIdAndCompanyId(id, companyId);
    } catch (DataIntegrityViolationException e) {
        throw new IllegalStateException("Cannot delete PieceJoin because it is referenced by other entities");
    }
}

    private void ensurePiecesNotConsumed(List<String> pieceNumbers, Long companyId) {
        if (pieceNumbers == null || pieceNumbers.isEmpty()) {
            return;
        }
        List<PieceEntry> entries = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
        for (PieceEntry entry : entries) {
            if (entry == null) {
                continue;
            }
            if (companyId != null) {
                Long entryCompanyId = entry.getCompanyId();
                if (entryCompanyId == null || !entryCompanyId.equals(companyId)) {
                    continue;
                }
            }
            if (isPieceEntryConsumed(entry)) {
                String pieceNumber = entry.getPieceNumber();
                throw new IllegalStateException((pieceNumber != null ? pieceNumber : "Piece") + " already inspected, packed, dispatched or invoiced");
            }
        }
    }

    private boolean isPieceEntryConsumed(PieceEntry entry) {
        return (entry.getIsInspected() != null && entry.getIsInspected() == 1)
                || (entry.getIsPacked() != null && entry.getIsPacked() == 1)
                || (entry.getIsDispatched() != null && entry.getIsDispatched() == 1)
                || (entry.getIsInvoice() != null && entry.getIsInvoice() == 1)
                || (entry.getInvoiceId() != null);
    }

    private void applyDtoToEntity(PieceJoinDTO dto, PieceJoin entity) {
        entity.setOperationId(dto.getOperationId());
        entity.setOperationDate(dto.getOperationDate());
        entity.setCompanyId(dto.getCompanyId());
        entity.setPieceJoinNo(dto.getPieceJoinNo());
        entity.setLoomId(dto.getLoomId());
        entity.setFlangeId(dto.getFlangeId());
        entity.setFlangeNo(dto.getFlangeNo());
        entity.setBeamId(dto.getBeamId());
        entity.setBeamNo(dto.getBeamNo());
        entity.setFabricMasterId(dto.getFabricMasterId());
        entity.setJoinedPieceNo(dto.getJoinedPieceNo());
        entity.setJoinedPieceMeter(dto.getJoinedPieceMeter());
        entity.setIsFactoryFabric(dto.getIsFactoryFabric());
    }

}
