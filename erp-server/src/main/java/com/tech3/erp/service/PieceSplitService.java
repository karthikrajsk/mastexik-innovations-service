package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.PieceSplitDTO;
import com.tech3.erp.dto.PieceSplitDetailDTO;
import com.tech3.erp.entity.PieceJoinDetail;
import com.tech3.erp.entity.PieceSplit;
import com.tech3.erp.entity.PieceSplitDetail;
import com.tech3.erp.repository.PieceJoinDetailRepository;
import com.tech3.erp.repository.PieceSplitDetailRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.repository.PieceSplitRepository;
import com.tech3.erp.entity.FabricInspection;
import com.tech3.erp.repository.FabricInspectionRepository;
import com.tech3.erp.entity.Warehouse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.math.BigDecimal;
import java.util.Objects;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PieceSplitService {

    @Autowired
    private PieceSplitRepository pieceSplitRepository;

    @Autowired
    private PieceSplitDetailRepository pieceSplitDetailRepository;

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    @Autowired
    private FabricInspectionRepository fabricInspectionRepository;

    @Autowired
    private PieceJoinDetailRepository pieceJoinDetailRepository;

    @Transactional
    public PieceSplitDTO create(PieceSplitDTO dto) {
        PieceSplit entity = new PieceSplit();
        applyDtoToEntity(dto, entity);
        entity.setModeId(1L);
        if (dto.getSplitPiecesDetails() != null) {
            List<PieceSplitDetail> details = dto.getSplitPiecesDetails().stream()
                    .map(d -> {
                        PieceSplitDetail det = new PieceSplitDetail();
                        det.setPieceSplit(entity);
                        det.setPieceNo(d.getPieceNo());
                        det.setPieceMeter(d.getPieceMeter());
                        det.setNetWeight(d.getNetWeight());
                        det.setAvgWt(d.getAvgWt());
                        det.setGradeId(d.getGradeId());
                        det.setOperationId(d.getOperationId());
                        det.setFabricMasterId(d.getFabricMasterId());
                        det.setModeId(1L);
                        return det;
                    })
                    .collect(Collectors.toList());
            entity.setSplitPiecesDetails(details);
        }
        PieceSplit saved = pieceSplitRepository.save(entity);
        pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(dto.getParentPieceNo()).ifPresent(pe -> {
            Warehouse parentWarehouse = pe.getWarehouse();
            pe.setOperationId(1L);
            pe.setIsJoined(0);
            pe.setIsParentPiece(1);
            pe.setParentPieceId(0L);
            pe.setJoinedPieceId(0L);
            BigDecimal totalNet = saved.getSplitPiecesDetails() != null ?
                    saved.getSplitPiecesDetails().stream()
                            .map(PieceSplitDetail::getNetWeight)
                            .filter(Objects::nonNull)
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                    : BigDecimal.ZERO;
            pe.setWeight(totalNet);
            try {
                pe.setQuantity(saved.getParentPieceMeter() != null && !saved.getParentPieceMeter().isEmpty()
                        ? new BigDecimal(saved.getParentPieceMeter()) : null);
            } catch (Exception ex) {
                pe.setQuantity(null);
            }
            if (parentWarehouse != null) {
                pe.setWarehouse(parentWarehouse);
            }
            pieceEntryRepository.save(pe);
            if (saved.getSplitPiecesDetails() != null) {
                FabricInspection parentInspection = fabricInspectionRepository.findFirstByPieceEntryId(pe.getId()).orElse(null);
                for (PieceSplitDetail d : saved.getSplitPiecesDetails()) {
                    PieceEntry child = new PieceEntry();
                    child.setPieceNumber(d.getPieceNo());
                    child.setPieceMeter(d.getPieceMeter());
                    try {
                        child.setQuantity(d.getPieceMeter() != null && !d.getPieceMeter().isEmpty() ? new BigDecimal(d.getPieceMeter()) : null);
                    } catch (Exception ex) {
                        child.setQuantity(null);
                    }
                    child.setWeight(d.getNetWeight());
                    child.setGradeId(d.getGradeId());
                    child.setOperationId(0L);
                    child.setModeId(1L);
                    child.setIsJoined(0);
                    child.setIsParentPiece(0);
                    child.setParentPieceId(pe.getId());
                    child.setJoinedPieceId(0L);
                    child.setIsLastPiece(pe.getIsLastPiece());
                    child.setIsFactoryFabric(pe.getIsFactoryFabric());
                    child.setCompanyId(saved.getCompanyId());
                    child.setWarehouse(parentWarehouse);
                    child.setOrderNo(pe.getOrderNo());
                    child.setOrderId(pe.getOrderId());
                    child.setOrderTypeId(pe.getOrderTypeId());
                    if (d.getFabricMasterId() != null) {
                        child.setFabricMasterId(d.getFabricMasterId());
                    } else if (pe.getFabricMasterId() != null) {
                        child.setFabricMasterId(pe.getFabricMasterId());
                    } else {
                        child.setFabricMasterId(saved.getFabricMasterId());
                    }
                    child.setFabricTypeId(pe.getFabricTypeId());
                    child.setFabricId(child.getFabricMasterId());
                    child.setSourceType(pe.getSourceType());
                    child.setPoType(pe.getPoType());
                    child.setPiecePrefix(pe.getPiecePrefix());
                    child.setNoOfFabricOutput(pe.getNoOfFabricOutput());
                    child.setLoomId(saved.getLoomId());
                    child.setFlangeId(saved.getFlangeId());
                    child.setFlangeNo(pe.getFlangeNo());
                    child.setBeamId(saved.getBeamId());
                    child.setBeamNo(saved.getBeamNo());
                    child.setDoffingDate(pe.getDoffingDate());
                    child.setDoffingNo(pe.getDoffingNo());
                    child.setDoffingPicks(pe.getDoffingPicks());
                    child.setInwardDate(pe.getInwardDate());
                    child.setInwardId(saved.getId());
                    pieceEntryRepository.save(child);

                    FabricInspection fi = new FabricInspection();
                    fi.setCompanyId(child.getCompanyId());
                    fi.setPieceNo(child.getPieceNumber());
                    try {
                        fi.setPieceMeters(child.getQuantity() != null ? child.getQuantity().doubleValue() : null);
                    } catch (Exception e) {}
                    try {
                        fi.setNetWeight(child.getWeight() != null ? child.getWeight().doubleValue() : null);
                    } catch (Exception e) {}
                    fi.setIsFactoryFabric(child.getIsFactoryFabric());
                    fi.setPieceEntryId(child.getId());
                    fi.setOrderNo(child.getOrderNo());
                    fi.setOrderTypeId(child.getOrderTypeId());
                    fi.setFabricTypeId(child.getFabricTypeId());
                    fi.setFabricMasterId(child.getFabricMasterId());
                    fi.setLoomId(child.getLoomId());
                    fi.setFlangeId(child.getFlangeId());
                    fi.setBeamNo(child.getBeamNo());
                    fi.setSourceType(child.getSourceType());
                    fi.setOperationId(child.getOperationId());
                    copyInspectionSourceDetails(fi, parentInspection);
                    fabricInspectionRepository.save(fi);
                }
            }
        });
        return new PieceSplitDTO(saved);
    }

    private void copyInspectionSourceDetails(FabricInspection inspection, FabricInspection source) {
        if (source == null) {
            return;
        }
        inspection.setVendorId(source.getVendorId());
        inspection.setBuyerId(source.getBuyerId());
        inspection.setSourceType(source.getSourceType());
        inspection.setFabricCode(source.getFabricCode());
        inspection.setPurchaseInwardId(source.getPurchaseInwardId());
        inspection.setJobworkFabricReceiveId(source.getJobworkFabricReceiveId());
        inspection.setWeavingWorkOrderId(source.getWeavingWorkOrderId());
    }

    public PieceSplitDTO getById(Long id) {
        PieceSplit entity = pieceSplitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PieceSplit not found with id: " + id));
        return new PieceSplitDTO(entity);
    }

    public List<PieceSplitDTO> list() {
        return pieceSplitRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(PieceSplitDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<PieceSplitDTO> listWithPagination(Long offset, Long limit, Long companyId) {
        List<PieceSplitDTO> list = companyId == null ? list() : listByCompany(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<PieceSplitDTO> listByCompany(Long companyId) {
        return pieceSplitRepository.findByCompanyId(companyId).stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .map(PieceSplitDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PieceSplitDTO update(Long id, PieceSplitDTO dto) {
        PieceSplit entity = pieceSplitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PieceSplit not found with id: " + id));
        applyDtoToEntity(dto, entity);
        entity.setModeId(1L);
        entity.getSplitPiecesDetails().clear();
        if (dto.getSplitPiecesDetails() != null) {
            dto.getSplitPiecesDetails().forEach(d -> {
                PieceSplitDetail det = new PieceSplitDetail();
                det.setPieceSplit(entity);
                det.setPieceNo(d.getPieceNo());
                det.setPieceMeter(d.getPieceMeter());
                det.setNetWeight(d.getNetWeight());
                det.setAvgWt(d.getAvgWt());
                det.setGradeId(d.getGradeId());
                det.setOperationId(d.getOperationId());
                det.setFabricMasterId(d.getFabricMasterId());
                det.setModeId(1L);
                entity.getSplitPiecesDetails().add(det);
            });
        }
        PieceSplit saved = pieceSplitRepository.save(entity);
        return new PieceSplitDTO(saved);
    }

    @Transactional
    public PieceSplitDTO updateByCompany(Long id, Long companyId, PieceSplitDTO dto) {
        PieceSplit entity = pieceSplitRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("PieceSplit not found with id: " + id + " and companyId: " + companyId));
        applyDtoToEntity(dto, entity);
        entity.setModeId(1L);
        entity.getSplitPiecesDetails().clear();
        if (dto.getSplitPiecesDetails() != null) {
            dto.getSplitPiecesDetails().forEach(d -> {
                PieceSplitDetail det = new PieceSplitDetail();
                det.setPieceSplit(entity);
                det.setPieceNo(d.getPieceNo());
                det.setPieceMeter(d.getPieceMeter());
                det.setNetWeight(d.getNetWeight());
                det.setAvgWt(d.getAvgWt());
                det.setGradeId(d.getGradeId());
                det.setOperationId(d.getOperationId());
                det.setFabricMasterId(d.getFabricMasterId());
                det.setModeId(1L);
                entity.getSplitPiecesDetails().add(det);
            });
        }
        PieceSplit saved = pieceSplitRepository.save(entity);
        return new PieceSplitDTO(saved);
    }

    // @Transactional
    // public void delete(Long id) {
    //     if (!pieceSplitRepository.existsById(id)) {
    //         throw new EntityNotFoundException("PieceSplit not found with id: " + id);
    //     }
    //     try {
    //         pieceSplitDetailRepository.deleteByPieceSplitId(id);
    //         pieceSplitRepository.deleteById(id);
    //     } catch (DataIntegrityViolationException e) {
    //         throw new IllegalStateException("Cannot delete PieceSplit because it is referenced by other entities");
    //     }
    // }

    // @Transactional
    // public void deleteByCompany(Long id, Long companyId) {
    //     PieceSplit entity = pieceSplitRepository.findByIdAndCompanyId(id, companyId)
    //             .orElseThrow(() -> new EntityNotFoundException("PieceSplit not found with id: " + id + " and companyId: " + companyId));
    //     try {
    //         pieceSplitDetailRepository.deleteByPieceSplitId(entity.getId());
    //         pieceSplitRepository.deleteByIdAndCompanyId(id, companyId);
    //     } catch (DataIntegrityViolationException e) {
    //         throw new IllegalStateException("Cannot delete PieceSplit because it is referenced by other entities");
    //     }
    // }
@Transactional
public void delete(Long id) {
    PieceSplit entity = pieceSplitRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("PieceSplit not found with id: " + id));
    List<String> childPieceNumbers = entity.getSplitPiecesDetails() == null ? Collections.emptyList()
            : entity.getSplitPiecesDetails().stream()
                    .map(PieceSplitDetail::getPieceNo)
                    .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                    .collect(Collectors.toList());
    ensurePiecesNotJoined(childPieceNumbers, null);
    ensurePiecesNotConsumed(childPieceNumbers, null);
    
    // First, revert the parent piece entry to original state
    pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(entity.getParentPieceNo()).ifPresent(pe -> {
        // Revert parent piece flags
        pe.setOperationId(0L); // Set back to original operation
        pe.setIsParentPiece(0); // Remove parent flag
        pe.setIsJoined(0);
        // Restore original weight and quantity from parent piece meters
        try {
            if (entity.getParentPieceMeter() != null && !entity.getParentPieceMeter().isEmpty()) {
                pe.setQuantity(new BigDecimal(entity.getParentPieceMeter()));
            }
        } catch (Exception ex) {
            pe.setQuantity(null);
        }
        // Restore original net weight (should be sum of child pieces)
        if (entity.getSplitPiecesDetails() != null) {
            BigDecimal totalNet = entity.getSplitPiecesDetails().stream()
                    .map(PieceSplitDetail::getNetWeight)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            pe.setWeight(totalNet);
        }
        pieceEntryRepository.save(pe);
    });
    
    // Delete all child piece entries created by this split
    if (entity.getSplitPiecesDetails() != null) {
        for (PieceSplitDetail detail : entity.getSplitPiecesDetails()) {
            // Find and delete the child piece entry
            pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(detail.getPieceNo()).ifPresent(child -> {
                // First, delete associated fabric inspections
                fabricInspectionRepository.deleteByPieceEntryId(child.getId());
                // Then delete the child piece entry
                pieceEntryRepository.delete(child);
            });
        }
    }
    
    try {
        // Delete piece split details and the split itself
        pieceSplitDetailRepository.deleteByPieceSplitId(id);
        pieceSplitRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
        throw new IllegalStateException("Cannot delete PieceSplit because it is referenced by other entities");
    }
}

@Transactional
public void deleteByCompany(Long id, Long companyId) {
    PieceSplit entity = pieceSplitRepository.findByIdAndCompanyId(id, companyId)
            .orElseThrow(() -> new EntityNotFoundException("PieceSplit not found with id: " + id + " and companyId: " + companyId));
    List<String> childPieceNumbers = entity.getSplitPiecesDetails() == null ? Collections.emptyList()
            : entity.getSplitPiecesDetails().stream()
                    .map(PieceSplitDetail::getPieceNo)
                    .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                    .collect(Collectors.toList());
    ensurePiecesNotJoined(childPieceNumbers, companyId);
    ensurePiecesNotConsumed(childPieceNumbers, companyId);
    
    // First, revert the parent piece entry to original state
    pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(entity.getParentPieceNo()).ifPresent(pe -> {
        // Only revert if it belongs to the same company
        if (pe.getCompanyId().equals(companyId)) {
            // Revert parent piece flags
            pe.setOperationId(0L); // Set back to original operation
            pe.setIsParentPiece(0); // Remove parent flag
            pe.setIsJoined(0);
            // Restore original weight and quantity from parent piece meters
            try {
                if (entity.getParentPieceMeter() != null && !entity.getParentPieceMeter().isEmpty()) {
                    pe.setQuantity(new BigDecimal(entity.getParentPieceMeter()));
                }
            } catch (Exception ex) {
                pe.setQuantity(null);
            }
            // Restore original net weight (should be sum of child pieces)
            if (entity.getSplitPiecesDetails() != null) {
                BigDecimal totalNet = entity.getSplitPiecesDetails().stream()
                        .map(PieceSplitDetail::getNetWeight)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                pe.setWeight(totalNet);
            }
            pieceEntryRepository.save(pe);
        }
    });
    
    // Delete all child piece entries created by this split
    if (entity.getSplitPiecesDetails() != null) {
        for (PieceSplitDetail detail : entity.getSplitPiecesDetails()) {
            // Find and delete the child piece entry
            pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(detail.getPieceNo()).ifPresent(child -> {
                // Check company ID match
                if (child.getCompanyId().equals(companyId)) {
                    // First, delete associated fabric inspections
                    fabricInspectionRepository.deleteByPieceEntryId(child.getId());
                    // Then delete the child piece entry
                    pieceEntryRepository.delete(child);
                }
            });
        }
    }
    
    try {
        // Delete piece split details and the split itself
        pieceSplitDetailRepository.deleteByPieceSplitId(entity.getId());
        pieceSplitRepository.deleteByIdAndCompanyId(id, companyId);
    } catch (DataIntegrityViolationException e) {
        throw new IllegalStateException("Cannot delete PieceSplit because it is referenced by other entities");
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

    private void ensurePiecesNotJoined(List<String> pieceNumbers, Long companyId) {
        if (pieceNumbers == null || pieceNumbers.isEmpty()) {
            return;
        }
        PieceJoinDetail joinedPieceDetail = companyId != null
                ? pieceJoinDetailRepository.findFirstByPieceNoInAndPieceJoin_CompanyId(pieceNumbers, companyId).orElse(null)
                : pieceJoinDetailRepository.findFirstByPieceNoIn(pieceNumbers).orElse(null);
        if (joinedPieceDetail == null) {
            return;
        }
        String pieceNumber = joinedPieceDetail.getPieceNo();
        if (pieceNumber != null && !pieceNumber.isEmpty()) {
            throw new IllegalStateException("This Piece " + pieceNumber + " already Joined with other Pieces");
        }
        throw new IllegalStateException("This Piece already Joined with other Pieces");
    }

    private boolean isPieceEntryConsumed(PieceEntry entry) {
        return (entry.getIsInspected() != null && entry.getIsInspected() == 1)
                || (entry.getIsPacked() != null && entry.getIsPacked() == 1)
                || (entry.getIsDispatched() != null && entry.getIsDispatched() == 1)
                || (entry.getIsInvoice() != null && entry.getIsInvoice() == 1)
                || (entry.getInvoiceId() != null);
    }

    private void applyDtoToEntity(PieceSplitDTO dto, PieceSplit entity) {
        entity.setOperationId(dto.getOperationId());
        entity.setOperationDate(dto.getOperationDate());
        entity.setCompanyId(dto.getCompanyId());
        entity.setPieceSplitNo(dto.getPieceSplitNo());
        entity.setParentPieceNo(dto.getParentPieceNo());
        entity.setParentPieceMeter(dto.getParentPieceMeter());
        entity.setLoomId(dto.getLoomId());
        entity.setFlangeId(dto.getFlangeId());
        entity.setFlangeNo(dto.getFlangeNo());
        entity.setBeamId(dto.getBeamId());
        entity.setBeamNo(dto.getBeamNo());
        entity.setFabricMasterId(dto.getFabricMasterId());
        entity.setNoOfPieces(dto.getNoOfPieces());
    }
}
