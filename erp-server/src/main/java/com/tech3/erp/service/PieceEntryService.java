package com.tech3.erp.service;

import com.tech3.erp.dto.PieceEntryDTO;
import com.tech3.erp.dto.DoffingEntryHistoryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.Warehouse;
import com.tech3.erp.entity.BeamGatingDetails;
import com.tech3.erp.entity.FabricInspection;
import com.tech3.erp.entity.Flange;
import com.tech3.erp.entity.PurchaseInward;
import com.tech3.erp.entity.PurchaseOrder;
import com.tech3.erp.entity.JobworkFabricReceive;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.WarehouseRepository;
import com.tech3.erp.repository.FactoryBeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.BeamGatingDetailsRepository;
import com.tech3.erp.repository.FlangeRepository;
import com.tech3.erp.repository.LoomMasterRepository;
import com.tech3.erp.repository.FabricInspectionRepository;
import com.tech3.erp.repository.PurchaseInwardRepository;
import com.tech3.erp.repository.PurchaseOrderRepository;
import com.tech3.erp.repository.JobworkFabricReceiveRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PieceEntryService {

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private FactoryBeamInwardBeamDetailsRepository factoryBeamInwardBeamDetailsRepository;

    @Autowired
    private BeamGatingDetailsRepository beamGatingDetailsRepository;

    @Autowired
    private FlangeRepository flangeRepository;

    @Autowired
    private LoomMasterRepository loomMasterRepository;

    @Autowired
    private FabricInspectionRepository fabricInspectionRepository;
    
    @Autowired
    private PurchaseInwardRepository purchaseInwardRepository;
    
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private JobworkFabricReceiveRepository jobworkFabricReceiveRepository;
    
    @Autowired
    private WeavingContractRepository weavingContractRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Transactional
    public List<PieceEntryDTO> bulkCreate(List<PieceEntryDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Collections.emptyList();
        }
        List<PieceEntryDTO> resultList = new java.util.ArrayList<>();
        for (PieceEntryDTO dto : dtos) {
            resultList.add(createOrUpdate(dto));
        }
        return resultList;
    }

    @Transactional
    public PieceEntryDTO createOrUpdate(PieceEntryDTO dto) {
        String pieceNumber = dto.getPieceNumber() != null ? dto.getPieceNumber().trim() : null;
        Long companyId = dto.getCompanyId();

        PieceEntry piece = null;
        if (pieceNumber != null && !pieceNumber.isEmpty() && companyId != null) {
            List<PieceEntry> existingList = pieceEntryRepository.findByPieceNumberAndCompanyId(pieceNumber, companyId);
            if (existingList != null && !existingList.isEmpty()) {
                piece = existingList.get(0);
            }
        }

        if (piece == null) {
            piece = new PieceEntry();
        }

        applyDtoToEntity(dto, piece);
        populateOrderAndContractNo(piece);
        PieceEntry saved = pieceEntryRepository.save(piece);
        saved = ensureDoffingNoForFactoryFabric(saved);
        PieceEntry finalSaved = saved;
        fabricInspectionRepository.findFirstByPieceEntryId(finalSaved.getId()).ifPresent(fi -> {
            fi.setIsFactoryFabric(finalSaved.getIsFactoryFabric());
            fabricInspectionRepository.save(fi);
        });
        markBeamGatingDetailsAsDoffed(finalSaved);

        if (saved.getIsFactoryFabric() != null && saved.getIsFactoryFabric() == 1) {
            FabricInspection fi = new FabricInspection();
            fi.setInspectionDate(LocalDate.now());
            fi.setLoomNo(saved.getLoomId() != null ? String.valueOf(saved.getLoomId()) : null);
            fi.setInspectorId(saved.getInspectorId());
            fi.setVendorId(null);
            fi.setCompanyId(saved.getCompanyId());
            fi.setFabricQuality(null);
            try {
                Double meters = null;
                if (saved.getPieceMeter() != null) {
                    meters = Double.valueOf(saved.getPieceMeter());
                }
                fi.setPieceMeters(meters);
            } catch (NumberFormatException ex) {
                fi.setPieceMeters(null);
            }
            fi.setNetWeight(saved.getWeight() != null ? saved.getWeight().doubleValue() : null);
            fi.setActiveFlag(true);
            fi.setSourceType(saved.getSourceType());
            fi.setPurchaseInwardId(null);
            fi.setJobworkFabricReceiveId(null);
            fi.setPieceNo(saved.getPieceNumber());
            fi.setFabricCode(null);
            fi.setIsFactoryFabric(saved.getIsFactoryFabric());
            fi.setLoomId(saved.getLoomId());
            fi.setFlangeId(saved.getFlangeId());
            fi.setFabricTypeId(saved.getFabricTypeId());
          
            fi.setFabricMasterId(saved.getFabricMasterId());
            fi.setBeamNo(saved.getBeamNo());
            fi.setBuyerId(null);
            fi.setWeavingWorkOrderId(null);
            fi.setIsNoDefectedPieces(0);
            fi.setPieceEntryId(saved.getId());
            fi.setIsInspected(saved.getIsInspected());
            fi.setOperationId(saved.getOperationId());
            fabricInspectionRepository.save(fi);
        }

        if (saved.getIsLastPiece() != null && saved.getIsLastPiece() == 1) {
            handleLastPieceCompletion(saved);
        } else if (saved.getIsLastPiece() != null && saved.getIsLastPiece() == 0) {
            handleLastPieceReversion(saved);
        }
        return toDto(saved);
    }

    @Transactional
    public PieceEntryDTO create(PieceEntryDTO dto) {
        return createOrUpdate(dto);
    }

    public PieceEntryDTO getById(Long id) {
        PieceEntry piece = pieceEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PieceEntry not found with id: " + id));
        return toDto(piece);
    }

    public PieceEntryDTO getByIdAndCompany(Long id, Long companyId) {
        PieceEntry piece = pieceEntryRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("PieceEntry not found with id: " + id + " and companyId: " + companyId));
        return toDto(piece);
    }

    public List<PieceEntryDTO> list() {
        return toDtoList(pieceEntryRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    public List<PieceEntryDTO> listByCompany(Long companyId) {
        return toDtoList(pieceEntryRepository.findByCompanyId(companyId));
    }

    public PaginatedResponse<PieceEntryDTO> listDoffingEntries(Long companyId, long offset, long limit, String search) {
        int safeLimit = (int) Math.max(1, Math.min(limit, 100));
        long safeOffset = Math.max(0, offset);
        int pageIndex = (int) Math.min(safeOffset / safeLimit, Integer.MAX_VALUE);
        Pageable pageable = PageRequest.of(pageIndex, safeLimit);
        String normalizedSearch = search == null ? "" : search.trim().toLowerCase();
        Page<PieceEntry> page;
        if (!normalizedSearch.isEmpty()) {
            page = pieceEntryRepository.searchDoffingEntries(companyId, "%" + normalizedSearch + "%", pageable);
        } else {
            page = companyId == null
                    ? pieceEntryRepository.findByIsFactoryFabricOrderByIdDesc(1, pageable)
                    : pieceEntryRepository.findByCompanyIdAndIsFactoryFabricOrderByIdDesc(companyId, 1, pageable);
        }
        List<PieceEntryDTO> data = toDtoList(page.getContent());
        return new PaginatedResponse<>(
                page.getTotalElements(),
                page.hasNext(),
                page.hasPrevious(),
                (long) page.getNumber() * page.getSize(),
                page.getSize(),
                data);
    }

    public List<DoffingEntryHistoryDTO> listDoffingHistory(Long companyId) {
        return companyId == null
                ? pieceEntryRepository.findDoffingHistory()
                : pieceEntryRepository.findDoffingHistoryByCompanyId(companyId);
    }
    
    public List<PieceEntryDTO> listByCompanyWithFilters(Long companyId, Integer isFactoryFabric, String sourceType) {
        List<PieceEntry> results;
        
        if (isFactoryFabric != null && sourceType != null && !sourceType.isEmpty()) {
            results = pieceEntryRepository.findByCompanyIdAndIsFactoryFabricAndSourceType(companyId, isFactoryFabric, sourceType);
        } else if (isFactoryFabric != null) {
            results = pieceEntryRepository.findByCompanyIdAndIsFactoryFabric(companyId, isFactoryFabric);
        } else if (sourceType != null && !sourceType.isEmpty()) {
            results = pieceEntryRepository.findByCompanyIdAndSourceType(companyId, sourceType);
        } else {
            results = pieceEntryRepository.findByCompanyId(companyId);
        }
        
        return toDtoList(results);
    }

    @Transactional
    public PieceEntryDTO update(Long id, PieceEntryDTO dto) {
        validatePieceNumber(dto, id);
        PieceEntry piece = pieceEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PieceEntry not found with id: " + id));
        Long previousFlangeId = piece.getFlangeId();
        Long previousLoomId = piece.getLoomId();
        String previousFlangeNo = piece.getFlangeNo();
        Integer savedIsJoined = piece.getIsJoined();
        Integer savedIsParentPiece = piece.getIsParentPiece();
        Long savedParentPieceId = piece.getParentPieceId();
        Long savedJoinedPieceId = piece.getJoinedPieceId();
        Integer savedIsPacked = piece.getIsPacked();
        Long savedPackingId = piece.getPackingId();
        Integer savedIsDispatched = piece.getIsDispatched();
        Long savedDispatchId = piece.getDispatchId();
        Long savedInvoiceId = piece.getInvoiceId();
        Integer savedIsInvoice = piece.getIsInvoice();
        applyDtoToEntity(dto, piece);
        piece.setIsJoined(savedIsJoined);
        piece.setIsParentPiece(savedIsParentPiece);
        piece.setParentPieceId(savedParentPieceId);
        piece.setJoinedPieceId(savedJoinedPieceId);
        piece.setIsPacked(savedIsPacked);
        piece.setPackingId(savedPackingId);
        piece.setIsDispatched(savedIsDispatched);
        piece.setDispatchId(savedDispatchId);
        piece.setInvoiceId(savedInvoiceId);
        piece.setIsInvoice(savedIsInvoice);
        revertLoomAndFlangeIfNeeded(piece, dto, previousLoomId, previousFlangeId, previousFlangeNo);
        populateOrderAndContractNo(piece);
        PieceEntry saved = pieceEntryRepository.save(piece);
        saved = ensureDoffingNoForFactoryFabric(saved);
        PieceEntry finalSaved = saved;
        fabricInspectionRepository.findFirstByPieceEntryId(finalSaved.getId()).ifPresent(fi -> {
            if (finalSaved.getIsFactoryFabric() != null) fi.setIsFactoryFabric(finalSaved.getIsFactoryFabric());
            if (finalSaved.getPieceNumber() != null) fi.setPieceNo(finalSaved.getPieceNumber());
            if (finalSaved.getLoomId() != null) {
                fi.setLoomId(finalSaved.getLoomId());
                fi.setLoomNo(String.valueOf(finalSaved.getLoomId()));
            }
            if (finalSaved.getInspectorId() != null) fi.setInspectorId(finalSaved.getInspectorId());
            if (finalSaved.getFlangeId() != null) fi.setFlangeId(finalSaved.getFlangeId());
            if (finalSaved.getFabricTypeId() != null) fi.setFabricTypeId(finalSaved.getFabricTypeId());
            if (finalSaved.getFabricMasterId() != null) fi.setFabricMasterId(finalSaved.getFabricMasterId());
            if (finalSaved.getBeamNo() != null) fi.setBeamNo(finalSaved.getBeamNo());
            if (finalSaved.getIsInspected() != null) fi.setIsInspected(finalSaved.getIsInspected());
            if (finalSaved.getOperationId() != null) fi.setOperationId(finalSaved.getOperationId());
            if (finalSaved.getSourceType() != null) fi.setSourceType(finalSaved.getSourceType());
            if (finalSaved.getPieceMeter() != null) {
                try {
                    fi.setPieceMeters(Double.valueOf(finalSaved.getPieceMeter()));
                } catch (NumberFormatException ex) {
                }
            }
            if (finalSaved.getWeight() != null) fi.setNetWeight(finalSaved.getWeight().doubleValue());
            fabricInspectionRepository.save(fi);
        });
        markBeamGatingDetailsAsDoffed(finalSaved);
        if (saved.getIsLastPiece() != null && saved.getIsLastPiece() == 1) {
            handleLastPieceCompletion(saved);
        } else if (saved.getIsLastPiece() != null && saved.getIsLastPiece() == 0) {
            handleLastPieceReversion(saved);
        }
        return toDto(saved);
    }

    @Transactional
    public PieceEntryDTO updateByCompany(Long id, Long companyId, PieceEntryDTO dto) {
        PieceEntry piece = pieceEntryRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("PieceEntry not found with id: " + id + " and companyId: " + companyId));
        Long previousFlangeId = piece.getFlangeId();
        Long previousLoomId = piece.getLoomId();
        String previousFlangeNo = piece.getFlangeNo();
        Integer savedIsJoined = piece.getIsJoined();
        Integer savedIsParentPiece = piece.getIsParentPiece();
        Long savedParentPieceId = piece.getParentPieceId();
        Long savedJoinedPieceId = piece.getJoinedPieceId();
        Integer savedIsPacked = piece.getIsPacked();
        Long savedPackingId = piece.getPackingId();
        Integer savedIsDispatched = piece.getIsDispatched();
        Long savedDispatchId = piece.getDispatchId();
        Long savedInvoiceId = piece.getInvoiceId();
        Integer savedIsInvoice = piece.getIsInvoice();
        applyDtoToEntity(dto, piece);
        piece.setIsJoined(savedIsJoined);
        piece.setIsParentPiece(savedIsParentPiece);
        piece.setParentPieceId(savedParentPieceId);
        piece.setJoinedPieceId(savedJoinedPieceId);
        piece.setIsPacked(savedIsPacked);
        piece.setPackingId(savedPackingId);
        piece.setIsDispatched(savedIsDispatched);
        piece.setDispatchId(savedDispatchId);
        piece.setInvoiceId(savedInvoiceId);
        piece.setIsInvoice(savedIsInvoice);
        revertLoomAndFlangeIfNeeded(piece, dto, previousLoomId, previousFlangeId, previousFlangeNo);
        populateOrderAndContractNo(piece);
        PieceEntry saved = pieceEntryRepository.save(piece);
        saved = ensureDoffingNoForFactoryFabric(saved);
        PieceEntry finalSaved = saved;
        fabricInspectionRepository.findFirstByPieceEntryId(finalSaved.getId()).ifPresent(fi -> {
            if (finalSaved.getIsFactoryFabric() != null) fi.setIsFactoryFabric(finalSaved.getIsFactoryFabric());
            if (finalSaved.getPieceNumber() != null) fi.setPieceNo(finalSaved.getPieceNumber());
            if (finalSaved.getLoomId() != null) {
                fi.setLoomId(finalSaved.getLoomId());
                fi.setLoomNo(String.valueOf(finalSaved.getLoomId()));
            }
            if (finalSaved.getInspectorId() != null) fi.setInspectorId(finalSaved.getInspectorId());
            if (finalSaved.getFlangeId() != null) fi.setFlangeId(finalSaved.getFlangeId());
            if (finalSaved.getFabricTypeId() != null) fi.setFabricTypeId(finalSaved.getFabricTypeId());
            if (finalSaved.getFabricMasterId() != null) fi.setFabricMasterId(finalSaved.getFabricMasterId());
            if (finalSaved.getBeamNo() != null) fi.setBeamNo(finalSaved.getBeamNo());
            if (finalSaved.getIsInspected() != null) fi.setIsInspected(finalSaved.getIsInspected());
            if (finalSaved.getOperationId() != null) fi.setOperationId(finalSaved.getOperationId());
            if (finalSaved.getSourceType() != null) fi.setSourceType(finalSaved.getSourceType());
            if (finalSaved.getPieceMeter() != null) {
                try {
                    fi.setPieceMeters(Double.valueOf(finalSaved.getPieceMeter()));
                } catch (NumberFormatException ex) {
                }
            }
            if (finalSaved.getWeight() != null) fi.setNetWeight(finalSaved.getWeight().doubleValue());
            fabricInspectionRepository.save(fi);
        });
        markBeamGatingDetailsAsDoffed(finalSaved);
        if (saved.getIsLastPiece() != null && saved.getIsLastPiece() == 1) {
            handleLastPieceCompletion(saved);
        } else if (saved.getIsLastPiece() != null && saved.getIsLastPiece() == 0) {
            handleLastPieceReversion(saved);
        }
        return toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        PieceEntry piece = pieceEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PieceEntry not found with id: " + id));
        validateDeleteAllowed(piece);
        try {
            resetBeamGatingDetailsDoffedStatus(piece);
            fabricInspectionRepository.deleteByPieceEntryId(id);
            pieceEntryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Piece Entry because it is referenced in another transaction");
        }
    }

    @Transactional
    public void deleteByCompany(Long id, Long companyId) {
        PieceEntry piece = pieceEntryRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("PieceEntry not found with id: " + id + " and companyId: " + companyId));
        validateDeleteAllowed(piece);
        try {
            resetBeamGatingDetailsDoffedStatus(piece);
            fabricInspectionRepository.deleteByPieceEntryId(id);
            pieceEntryRepository.deleteByIdAndCompanyId(id, companyId);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Piece Entry because it is referenced in another transaction");
        }
    }

    private void validateDeleteAllowed(PieceEntry piece) {
        if (piece == null) {
            return;
        }
        final Long pieceId = piece.getId();
        final String pieceLabel = (piece.getPieceNumber() != null && !piece.getPieceNumber().isBlank())
                ? piece.getPieceNumber()
                : String.valueOf(pieceId);

        if (piece.getIsJoined() != null && piece.getIsJoined() == 1) {
            throw new IllegalStateException("Cannot delete Piece " + pieceLabel + " because it is already joined");
        }

        if (pieceId != null && pieceEntryRepository.existsByParentPieceId(pieceId)) {
            throw new IllegalStateException("Cannot delete Piece " + pieceLabel + " because it is mapped as a parent piece in join records");
        }

        if (pieceId != null && pieceEntryRepository.existsByJoinedPieceId(pieceId)) {
            throw new IllegalStateException("Cannot delete Piece " + pieceLabel + " because it is mapped as a joined piece in join records");
        }

        if ((piece.getIsPacked() != null && piece.getIsPacked() == 1) || piece.getPackingId() != null) {
            throw new IllegalStateException("Cannot delete Piece " + pieceLabel + " because it is already packed");
        }
        if ((piece.getIsDispatched() != null && piece.getIsDispatched() == 1) || piece.getDispatchId() != null) {
            throw new IllegalStateException("Cannot delete Piece " + pieceLabel + " because it is already dispatched");
        }
        if ((piece.getIsInvoice() != null && piece.getIsInvoice() == 1) || piece.getInvoiceId() != null) {
            throw new IllegalStateException("Cannot delete Piece " + pieceLabel + " because it is already invoiced");
        }
    }

    private void applyDtoToEntity(PieceEntryDTO dto, PieceEntry piece) {
        piece.setPieceNumber(dto.getPieceNumber());
        String pieceMeter = dto.getPieceMeter();
        BigDecimal quantity = null;
        if (pieceMeter != null && !pieceMeter.trim().isEmpty()) {
            try {
                quantity = new BigDecimal(pieceMeter);
            } catch (NumberFormatException ex) {
                quantity = null;
            }
        }
        if (quantity == null) {
            quantity = dto.getQuantity();
        }
        
        piece.setQuantity(quantity);
        piece.setPieceMeter(pieceMeter);
        piece.setRemainingMtr(dto.getRemainingMtr());
        piece.setWeight(dto.getWeight());
        piece.setCost(dto.getCost());
        piece.setRemarks(dto.getRemarks());
        piece.setActiveFlag(dto.getActiveFlag());
        piece.setSourceType(dto.getSourceType());
        piece.setPoType(dto.getPoType());
        piece.setFabricTypeId(dto.getFabricTypeId());
        piece.setFabricId(dto.getFabricMasterId());
        piece.setFabricMasterId(dto.getFabricMasterId());
        piece.setNoOfFabricOutput(dto.getNoOfFabricOutput());
        if (dto.getInspectionStatus() != null) piece.setInspectionStatus(dto.getInspectionStatus());
        if (dto.getIsInspected() != null) piece.setIsInspected(dto.getIsInspected());
        if (dto.getInspectionId() != null) piece.setInspectionId(dto.getInspectionId());
        if (dto.getGradeId() != null) piece.setGradeId(dto.getGradeId());
        if (dto.getInspectedMtr() != null) piece.setInspectedMtr(dto.getInspectedMtr());
        if (dto.getInspectedNtWt() != null) piece.setInspectedNtWt(dto.getInspectedNtWt());
        if (dto.getInspectedAvgWt() != null) piece.setInspectedAvgWt(dto.getInspectedAvgWt());
        if (dto.getDefectType() != null) piece.setDefectType(dto.getDefectType());
        if (dto.getDefectPoints() != null) piece.setDefectPoints(dto.getDefectPoints());
        piece.setPieceNo(dto.getPieceNo());
        piece.setPiecePrefix(dto.getPiecePrefix());
        piece.setInspectorId(dto.getInspectorId());
        piece.setIsPacked(dto.getIsPacked());
        piece.setPackingId(dto.getPackingId());
        piece.setIsDispatched(dto.getIsDispatched());
        piece.setDispatchId(dto.getDispatchId());
        piece.setInvoiceId(dto.getInvoiceId());
        if (dto.getIsRejected() != null) piece.setIsRejected(dto.getIsRejected());
        if (dto.getRejectedReason() != null) piece.setRejectedReason(dto.getRejectedReason());
        if (dto.getPackLaterReason() != null) piece.setPackLaterReason(dto.getPackLaterReason());
        piece.setIsFactoryFabric(dto.getIsFactoryFabric());
        piece.setOrderNo(dto.getOrderNo());
        piece.setOrderId(dto.getOrderId());
        piece.setIsLastPiece(dto.getIsLastPiece());
        piece.setLoomId(dto.getLoomId());
        piece.setBeamNo(dto.getBeamNo());
        piece.setFlangeId(dto.getFlangeId());
        piece.setFlangeNo(dto.getFlangeNo());
        piece.setBeamId(dto.getBeamId());

        if (dto.getFlangeId() != null) {
            piece.setFlangeNo(
                flangeRepository.findById(dto.getFlangeId())
                        .map(Flange::getFlangeNo)
                        .orElse(null)
            );
        } else if (dto.getFlangeNo() == null) {
            piece.setFlangeNo(null);
        }
        piece.setDoffingPicks(dto.getDoffingPicks());
        piece.setCompanyId(dto.getCompanyId());
        piece.setDoffingDate(dto.getDoffingDate());
        piece.setDoffingNo(dto.getDoffingNo());
        piece.setIsFactoryFabric(dto.getIsFactoryFabric());
        piece.setParentPieceId(dto.getParentPieceId());
        piece.setJoinedPieceId(dto.getJoinedPieceId());
        piece.setIsParentPiece(dto.getIsParentPiece());
        piece.setIsJoined(dto.getIsJoined());
        if (dto.getOperationId() != null) {
            piece.setOperationId(dto.getOperationId());
        }
        if (dto.getModeId() != null) {
            piece.setModeId(dto.getModeId());
        }
        if (dto.getOrderTypeId() != null) {
            piece.setOrderTypeId(dto.getOrderTypeId());
        }
        if (dto.getInwardDate() != null) {
            piece.setInwardDate(dto.getInwardDate());
        }

        if (piece.getInwardDate() == null && (("doffing_entry".equalsIgnoreCase(dto.getSourceType())) || (dto.getIsFactoryFabric() != null && dto.getIsFactoryFabric() == 1)) && dto.getDoffingDate() != null) {
            try {
                piece.setInwardDate(LocalDate.parse(dto.getDoffingDate()));
            } catch (Exception e) {
                // Ignore parsing errors
            }
        }

        if (dto.getWarehouseId() != null) {
            Warehouse wh = warehouseRepository.findById(dto.getWarehouseId())
                    .orElseThrow(() -> new EntityNotFoundException("Warehouse not found with id: " + dto.getWarehouseId()));
            piece.setWarehouse(wh);
        } else {
            piece.setWarehouse(null);
        }

        piece.setDcNumber(dto.getDcNumber());
        piece.setDcDate(dto.getDcDate());

        if (dto.getWarpSetNo() != null) piece.setWarpSetNo(dto.getWarpSetNo());
        if (dto.getWarpCount() != null) piece.setWarpCount(dto.getWarpCount());
        if (dto.getWarpYarnMillName() != null) piece.setWarpYarnMillName(dto.getWarpYarnMillName());
        if (dto.getWarpYarnLotNo() != null) piece.setWarpYarnLotNo(dto.getWarpYarnLotNo());
        if (dto.getWeftCount() != null) piece.setWeftCount(dto.getWeftCount());
        if (dto.getWeftYarnMillName() != null) piece.setWeftYarnMillName(dto.getWeftYarnMillName());
        if (dto.getWeftYarnLotNo() != null) piece.setWeftYarnLotNo(dto.getWeftYarnLotNo());

        // New Fabric Stock & Excel fields mapping
        if (dto.getPassMtr() != null) piece.setPassMtr(dto.getPassMtr());
        if (dto.getInspectionDate() != null) piece.setInspectionDate(dto.getInspectionDate());
        if (dto.getRollingDate() != null) piece.setRollingDate(dto.getRollingDate());
        if (dto.getRollWeight() != null) piece.setRollWeight(dto.getRollWeight());
        if (dto.getGlm() != null) piece.setGlm(dto.getGlm());
        if (dto.getHfplRollNo() != null) piece.setHfplRollNo(dto.getHfplRollNo());
        if (dto.getDispatchMtr() != null) piece.setDispatchMtr(dto.getDispatchMtr());
        if (dto.getDispatchDate() != null) piece.setDispatchDate(dto.getDispatchDate());
        if (dto.getHfplInvoiceNo() != null) piece.setHfplInvoiceNo(dto.getHfplInvoiceNo());
        if (dto.getFabricStatus() != null) piece.setFabricStatus(dto.getFabricStatus());

        // Enforce inspectedMtr when isInspected = 1
        if (piece.getIsInspected() != null && piece.getIsInspected() == 1) {
            if (dto.getInspectedMtr() != null) {
                piece.setInspectedMtr(dto.getInspectedMtr());
            } else if (dto.getPassMtr() != null) {
                piece.setInspectedMtr(dto.getPassMtr());
            } else if (dto.getPieceMeter() != null) {
                try {
                    piece.setInspectedMtr(new BigDecimal(dto.getPieceMeter()));
                } catch (Exception e) {}
            }
        }
    }

    private void validatePieceNumber(PieceEntryDTO dto, Long currentId) {
        if (dto.getPieceNumber() != null && !dto.getPieceNumber().trim().isEmpty()) {
            String pieceNumber = dto.getPieceNumber().trim();
            Long companyId = dto.getCompanyId();
            if (companyId == null && currentId != null) {
                PieceEntry existing = pieceEntryRepository.findById(currentId).orElse(null);
                if (existing != null) companyId = existing.getCompanyId();
            }
            
            if (companyId != null) {
                List<PieceEntry> existingPieces = pieceEntryRepository.findByPieceNumberAndCompanyId(pieceNumber, companyId);
                for (PieceEntry existing : existingPieces) {
                    if (currentId == null || !existing.getId().equals(currentId)) {
                        throw new IllegalArgumentException("Piece Number '" + pieceNumber + "' already exists for this company.");
                    }
                }
            }
        }
    }

    private PieceEntry ensureDoffingNoForFactoryFabric(PieceEntry piece) {
        if (piece == null || piece.getId() == null || piece.getSourceType() == null) {
            return piece;
        }
        if (!"Factory-Inward".equalsIgnoreCase(piece.getSourceType())) {
            return piece;
        }
        String currentDoffingNo = piece.getDoffingNo();
        if (currentDoffingNo != null && !currentDoffingNo.trim().isEmpty()) {
            return piece;
        }
        String datePart = piece.getDoffingDate() != null ? piece.getDoffingDate().replace("-", "") : "";
        String computed = "DOFF-" + datePart + "-" + piece.getId();
        if (!computed.equals(piece.getDoffingNo())) {
            piece.setDoffingNo(computed);
            return pieceEntryRepository.save(piece);
        }
        return piece;
    }
    
    private void markBeamGatingDetailsAsDoffed(PieceEntry piece) {
        if (piece == null) {
            return;
        }
        Long beamId = piece.getBeamId();
        String beamNo = piece.getBeamNo();
        if (beamId == null && (beamNo == null || beamNo.isEmpty())) {
            return;
        }
        beamGatingDetailsRepository.markDoffedBeamByBeamIdOrBeamNo(beamId, beamNo);
    }
    
    private void resetBeamGatingDetailsDoffedStatus(PieceEntry piece) {
        if (piece == null) {
            return;
        }
        Long beamId = piece.getBeamId();
        String beamNo = piece.getBeamNo();
        if (beamId == null && (beamNo == null || beamNo.isEmpty())) {
            return;
        }
        beamGatingDetailsRepository.resetDoffedBeamByBeamIdOrBeamNo(beamId, beamNo);
    }
    
    private void handleLastPieceCompletion(PieceEntry saved) {
        if (saved == null) {
            return;
        }
        Long beamId = saved.getBeamId();
        Long flangeId = saved.getFlangeId();
        String beamNo = saved.getBeamNo();
        String flangeNo = saved.getFlangeNo();
        Long loomId = saved.getLoomId();

        if (beamId != null || flangeId != null || (beamNo != null && !beamNo.trim().isEmpty()) || (flangeNo != null && !flangeNo.trim().isEmpty())) {
            beamGatingDetailsRepository.markProductionCompleted(beamId, flangeId, beamNo, flangeNo);
            factoryBeamInwardBeamDetailsRepository.markProductionCompleted(flangeId, beamId, beamNo);
        }

        if (flangeId != null) {
            flangeRepository.resetFlagsById(flangeId);
        }

        if (loomId != null) {
            List<BeamGatingDetails> activeDetails = beamGatingDetailsRepository.findActiveDetailsByLoomId(loomId);
            if (activeDetails == null || activeDetails.isEmpty()) {
                resetBeamGatingStatus(loomId, flangeId);
            }
        }
    }

    private void handleLastPieceReversion(PieceEntry saved) {
        if (saved == null) {
            return;
        }
        Long beamId = saved.getBeamId();
        Long flangeId = saved.getFlangeId();
        String beamNo = saved.getBeamNo();
        String flangeNo = saved.getFlangeNo();

        if (beamId != null || flangeId != null || (beamNo != null && !beamNo.trim().isEmpty()) || (flangeNo != null && !flangeNo.trim().isEmpty())) {
            beamGatingDetailsRepository.resetProductionCompleted(beamId, flangeId, beamNo, flangeNo);
            factoryBeamInwardBeamDetailsRepository.resetProductionCompleted(flangeId, beamId, beamNo);
        }
    }

    private void resetBeamGatingStatus(Long loomId, Long flangeId) {
        if (loomId != null) {
            loomMasterRepository.resetBeamGatingById(loomId);
        }
        if (flangeId != null) {
            flangeRepository.resetBeamGatingById(flangeId);
        }
    }
    
    private void populateOrderAndContractNo(PieceEntry piece) {
        if (piece == null) {
            return;
        }
        
        if (piece.getSourceType() != null) {
            if ("purchase_inward".equalsIgnoreCase(piece.getSourceType())) {
                if (piece.getPurchaseInwardItem() != null && piece.getPurchaseInwardItem().getPurchaseInward() != null) {
                    PurchaseInward purchaseInward = piece.getPurchaseInwardItem().getPurchaseInward();
                    if (purchaseInward.getPurchaseOrderId() != null) {
                        purchaseOrderRepository.findById(purchaseInward.getPurchaseOrderId()).ifPresent(po -> {
                            piece.setOrderNo(po.getPoNo());
                            piece.setOrderId(po.getId());
                        });
                    }
                }

            } else if ("jobwork_fabric_receive".equalsIgnoreCase(piece.getSourceType())) {
                if (piece.getJobworkFabricReceiveItem() != null && piece.getJobworkFabricReceiveItem().getJobworkFabricReceive() != null) {
                    JobworkFabricReceive jobworkFabricReceive = piece.getJobworkFabricReceiveItem().getJobworkFabricReceive();
                    if (jobworkFabricReceive.getWeavingContractId() != null) {
                        weavingContractRepository.findById(jobworkFabricReceive.getWeavingContractId()).ifPresent(wc -> {
                            piece.setOrderNo(wc.getWeavingContractNo());
                            piece.setOrderId(wc.getId());
                        });
                    }
                }
            } else if ("process_receive".equalsIgnoreCase(piece.getSourceType()) || "PROCESS_RECEIVE".equalsIgnoreCase(piece.getSourceType())) {
                if (piece.getFinishFabricReceiveItem() != null && piece.getFinishFabricReceiveItem().getFinishFabricReceive() != null) {
                    piece.setOrderNo(piece.getFinishFabricReceiveItem().getFinishFabricReceive().getDyeingWorkOrderNo());
                    piece.setOrderId(piece.getFinishFabricReceiveItem().getFinishFabricReceive().getDyeingWorkOrderId());
                }

            } else if ("Factory-Fabric".equalsIgnoreCase(piece.getSourceType()) || (piece.getIsFactoryFabric() != null && piece.getIsFactoryFabric() == 1)) {
                List<BeamGatingDetails> details = beamGatingDetailsRepository.findMatchingDetails(
                    piece.getBeamId(),
                    piece.getFlangeId(),
                    piece.getBeamNo(),
                    piece.getFlangeNo()
                );
                if (details != null && !details.isEmpty()) {
                    BeamGatingDetails matchedDetail = details.stream()
                        .filter(d -> d.getIsProductionCompleted() == null || d.getIsProductionCompleted() != 1)
                        .findFirst()
                        .orElse(details.get(0));
                    
                    if (matchedDetail.getWeavingWorkOrderId() != null) {
                        piece.setOrderId(matchedDetail.getWeavingWorkOrderId());
                    }
                    if (matchedDetail.getWeavingWorkOrderNo() != null) {
                        piece.setOrderNo(matchedDetail.getWeavingWorkOrderNo());
                    } else if (matchedDetail.getWeavingContractNo() != null) {
                        piece.setOrderNo(matchedDetail.getWeavingContractNo());
                    }
                    if (matchedDetail.getOrderTypeId() != null) {
                        piece.setOrderTypeId(matchedDetail.getOrderTypeId());
                    }
                    if (piece.getWarpSetNo() == null && matchedDetail.getSizingSetNo() != null) {
                        piece.setWarpSetNo(matchedDetail.getSizingSetNo());
                    }
                    if (piece.getWarpCount() == null && matchedDetail.getWarpCount() != null) {
                        piece.setWarpCount(matchedDetail.getWarpCount());
                    }
                    if (piece.getWarpYarnMillName() == null && matchedDetail.getWarpYarnMillName() != null) {
                        piece.setWarpYarnMillName(matchedDetail.getWarpYarnMillName());
                    }
                    if (piece.getWarpYarnLotNo() == null && matchedDetail.getWarpYarnLotNo() != null) {
                        piece.setWarpYarnLotNo(matchedDetail.getWarpYarnLotNo());
                    }
                    if (piece.getWeftCount() == null && matchedDetail.getWeftCount() != null) {
                        piece.setWeftCount(matchedDetail.getWeftCount());
                    }
                    if (piece.getWeftYarnMillName() == null && matchedDetail.getWeftYarnMillName() != null) {
                        piece.setWeftYarnMillName(matchedDetail.getWeftYarnMillName());
                    }
                    if (piece.getWeftYarnLotNo() == null && matchedDetail.getWeftYarnLotNo() != null) {
                        piece.setWeftYarnLotNo(matchedDetail.getWeftYarnLotNo());
                    }
                }
            }
        }

        if ((piece.getOrderNo() == null || piece.getOrderNo().trim().isEmpty()) && piece.getOrderId() != null) {
            salesOrderRepository.findById(piece.getOrderId()).ifPresent(so -> {
                piece.setOrderNo(so.getSalesOrderNo());
            });
        }
    }

    private void revertLoomAndFlangeIfNeeded(PieceEntry piece, PieceEntryDTO dto, Long previousLoomId, Long previousFlangeId, String previousFlangeNo) {
        if (piece == null || dto == null) {
            return;
        }
        if (dto.getIsLastPiece() != null && dto.getIsLastPiece() == 0) {
            piece.setLoomId(previousLoomId);
            piece.setFlangeId(previousFlangeId);
            piece.setFlangeNo(previousFlangeNo);
        }
    }

    public List<PieceEntryDTO> listByCompanyWithWeavingContractNo(Long companyId, Integer isFactoryFabric, String sourceType, String weavingContractNo) {
        if (weavingContractNo == null || weavingContractNo.trim().isEmpty()) {
            return listByCompanyWithFilters(companyId, isFactoryFabric, sourceType);
        }

        String normalizedSourceType = (sourceType != null && sourceType.trim().isEmpty()) ? null : sourceType;

        List<PieceEntry> results = weavingContractRepository.findByWeavingContractNo(weavingContractNo)
                .map(contract -> pieceEntryRepository.findByCompanyIdAndIsFactoryFabricAndSourceTypeAndWeavingContractIdViaWorkOrder(
                        companyId,
                        isFactoryFabric,
                        normalizedSourceType,
                        contract.getId()))
                .orElse(Collections.emptyList());

        return toDtoList(results);
    }

    private PieceEntryDTO toDto(PieceEntry piece) {
        if (piece == null) {
            return null;
        }
        PieceEntryDTO dto = new PieceEntryDTO(piece);
        enrichOrderDetails(dto);
        return dto;
    }

    private void enrichOrderDetails(PieceEntryDTO dto) {
        if (dto == null) {
            return;
        }
        if ((dto.getOrderNo() == null || dto.getOrderNo().trim().isEmpty()) && dto.getOrderId() != null) {
            salesOrderRepository.findById(dto.getOrderId()).ifPresent(so -> {
                dto.setOrderNo(so.getSalesOrderNo());
            });
        } else if (dto.getOrderId() == null && dto.getOrderNo() != null && !dto.getOrderNo().trim().isEmpty()) {
            salesOrderRepository.findBySalesOrderNo(dto.getOrderNo().trim()).ifPresent(so -> {
                dto.setOrderId(so.getId());
            });
        }
    }

    private List<PieceEntryDTO> toDtoList(List<PieceEntry> pieces) {
        if (pieces == null || pieces.isEmpty()) {
            return Collections.emptyList();
        }

        Set<Long> missingOrderIds = pieces.stream()
                .filter(p -> (p.getOrderNo() == null || p.getOrderNo().trim().isEmpty()) && p.getOrderId() != null)
                .map(PieceEntry::getOrderId)
                .collect(Collectors.toSet());

        Map<Long, String> orderIdToNoMap = Collections.emptyMap();
        if (!missingOrderIds.isEmpty()) {
            try {
                List<SalesOrder> salesOrders = salesOrderRepository.findAllById(missingOrderIds);
                orderIdToNoMap = salesOrders.stream()
                        .collect(Collectors.toMap(SalesOrder::getId, SalesOrder::getSalesOrderNo, (a, b) -> a));
            } catch (Exception e) {
                // Ignore and fallback to per-item lookup if needed
            }
        }

        final Map<Long, String> finalOrderIdToNoMap = orderIdToNoMap;

        return pieces.stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .map(p -> {
                    PieceEntryDTO dto = new PieceEntryDTO(p);
                    if ((dto.getOrderNo() == null || dto.getOrderNo().trim().isEmpty()) && dto.getOrderId() != null) {
                        String orderNo = finalOrderIdToNoMap.get(dto.getOrderId());
                        if (orderNo != null) {
                            dto.setOrderNo(orderNo);
                        } else {
                            salesOrderRepository.findById(dto.getOrderId()).ifPresent(so -> dto.setOrderNo(so.getSalesOrderNo()));
                        }
                    } else if (dto.getOrderId() == null && dto.getOrderNo() != null && !dto.getOrderNo().trim().isEmpty()) {
                        salesOrderRepository.findBySalesOrderNo(dto.getOrderNo().trim()).ifPresent(so -> dto.setOrderId(so.getId()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
