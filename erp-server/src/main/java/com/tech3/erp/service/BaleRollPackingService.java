package com.tech3.erp.service;

import com.tech3.erp.dto.BaleRollPackingDTO;
import com.tech3.erp.dto.BaleRollPackingItemDTO;
import com.tech3.erp.entity.BaleRollPacking;
import com.tech3.erp.entity.BaleRollPackingItem;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.repository.BaleRollPackingItemRepository;
import com.tech3.erp.repository.BaleRollPackingRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.BrushingReceiveDetailRepository;
import com.tech3.erp.entity.BrushingReceiveDetail;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BaleRollPackingService {

    private final BaleRollPackingRepository packingRepository;
    private final BaleRollPackingItemRepository itemRepository;
    private final PieceEntryRepository pieceEntryRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
    private final ReferenceValidationService referenceValidationService;
    private final WeavingWorkOrderService weavingWorkOrderService;
    private final BrushingReceiveDetailRepository brushingReceiveDetailRepository;

    @Autowired
    public BaleRollPackingService(BaleRollPackingRepository packingRepository,
                                  BaleRollPackingItemRepository itemRepository,
                                  PieceEntryRepository pieceEntryRepository,
                                  SalesOrderRepository salesOrderRepository,
                                  WeavingWorkOrderRepository weavingWorkOrderRepository,
                                  ReferenceValidationService referenceValidationService,
                                  WeavingWorkOrderService weavingWorkOrderService,
                                  BrushingReceiveDetailRepository brushingReceiveDetailRepository) {
        this.packingRepository = packingRepository;
        this.itemRepository = itemRepository;
        this.pieceEntryRepository = pieceEntryRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
        this.referenceValidationService = referenceValidationService;
        this.weavingWorkOrderService = weavingWorkOrderService;
        this.brushingReceiveDetailRepository = brushingReceiveDetailRepository;
    }

    @Transactional
    public BaleRollPackingDTO create(BaleRollPackingDTO dto) {
        Long weavingWorkOrderId = dto.getWeavingWorkOrderId() != null && dto.getWeavingWorkOrderId() > 0
        ? dto.getWeavingWorkOrderId()
        : null;
        if (weavingWorkOrderId != null && !weavingWorkOrderRepository.existsById(weavingWorkOrderId)) {
            weavingWorkOrderId = null;
        }

        Long salesOrderId = dto.getSalesOrderId() != null && dto.getSalesOrderId() > 0
        ? dto.getSalesOrderId()
        : null;
        if (salesOrderId != null && !salesOrderRepository.existsById(salesOrderId)) {
            salesOrderId = null;
        }

        requireSalesOrderReference(salesOrderId);
        requireWeavingWorkOrderReference(weavingWorkOrderId);
        BaleRollPacking packing = new BaleRollPacking();
        packing.setPackingDate(dto.getPackingDate());
        packing.setPackingType(dto.getPackingType());
        packing.setOrderTypeId(dto.getOrderTypeId());
        packing.setBuyerId(dto.getBuyerId());
        packing.setWeavingWorkOrderId(weavingWorkOrderId);
        packing.setIsJobwork(dto.getIsJobwork());
        packing.setSalesOrderId(salesOrderId);
        packing.setFabricCodeId(dto.getFabricCodeId());
//        packing.setFabricQuality(dto.getFabricQuality());
        packing.setUomId(dto.getUomId());
        packing.setWarehouseId(dto.getWarehouseId());
        packing.setGradeId(dto.getGradeId());
        packing.setCompanyId(dto.getCompanyId());
        packing.setRemarks(dto.getRemarks());
        packing.setIsBrushChecked(dto.getIsBrushChecked());
        packing.setVendorId(dto.getVendorId());

        // Map additional header fields
        packing.setRollOrBaleNo(dto.getRollOrBaleNo());
        packing.setTotalPieces(dto.getTotalPieces());
        packing.setTotalMeters(dto.getTotalMeters());
        packing.setTotalYards(dto.getTotalYards());
        packing.setTotalNet(dto.getTotalNet());
        packing.setTotalAvg(dto.getTotalAvg());
        packing.setTotalGross(dto.getTotalGross());
        packing.setIsFactoryRoll(dto.getIsFactoryRoll());
        packing.setIsDispatched(0); // Set isDispatched to 0 when creating (not yet dispatched)

        BaleRollPacking saved = packingRepository.save(packing);

        // Add items to the existing collection (avoids "no longer referenced" exception)
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            dto.getItems().forEach(itemDto -> {
                BaleRollPackingItem item = toItemEntity(itemDto);
                item.setBaleRollPacking(saved);
                saved.getItems().add(item);
            });
            
            // Update PieceEntry statuses, hfplRollNo, and passMtr for packing integration
            updatePackingStatusForPieces(saved.getId(), saved.getRollOrBaleNo(), dto.getItems());
        }
        markWeavingWorkOrderReference(saved.getWeavingWorkOrderId());
        return toDTO(saved);
    }

    public BaleRollPackingDTO getById(Long id) {
        BaleRollPacking packing = packingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bale/Roll Packing not found: " + id));
        return toDTO(packing);
    }

    public BaleRollPackingDTO getByIdAndCompany(Long companyId, Long id) {
        BaleRollPacking packing = packingRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Bale/Roll Packing not found: " + id + " for companyId: " + companyId));
        return toDTO(packing);
    }

    @Transactional
    public BaleRollPackingDTO update(Long id, BaleRollPackingDTO dto) {
        BaleRollPacking packing = packingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bale/Roll Packing not found: " + id));
        requireSalesOrderReference(dto.getSalesOrderId());
        requireWeavingWorkOrderReference(dto.getWeavingWorkOrderId());

        packing.setPackingDate(dto.getPackingDate());
        packing.setPackingType(dto.getPackingType());
        packing.setOrderTypeId(dto.getOrderTypeId());
        packing.setBuyerId(dto.getBuyerId());
        packing.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
        packing.setIsJobwork(dto.getIsJobwork());
        packing.setSalesOrderId(dto.getSalesOrderId());
        packing.setFabricCodeId(dto.getFabricCodeId());
//        packing.setFabricQuality(dto.getFabricQuality());
        packing.setUomId(dto.getUomId());
        packing.setWarehouseId(dto.getWarehouseId());
        packing.setGradeId(dto.getGradeId());
        packing.setCompanyId(dto.getCompanyId());
        packing.setRemarks(dto.getRemarks());
        packing.setIsBrushChecked(dto.getIsBrushChecked());
        packing.setVendorId(dto.getVendorId());

        // Map additional header fields
        packing.setRollOrBaleNo(dto.getRollOrBaleNo());
        packing.setTotalPieces(dto.getTotalPieces());
        packing.setTotalMeters(dto.getTotalMeters());
        packing.setTotalYards(dto.getTotalYards());
        packing.setTotalNet(dto.getTotalNet());
        packing.setTotalAvg(dto.getTotalAvg());
        packing.setTotalGross(dto.getTotalGross());
        packing.setIsFactoryRoll(dto.getIsFactoryRoll());
        packing.setIsDispatched(0); // Set isDispatched to 0 when updating (not yet dispatched)

        // Get old piece numbers before clearing
        List<String> oldPieceNos = packing.getItems().stream()
                .map(BaleRollPackingItem::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                .collect(Collectors.toList());

        // Replace items using orphanRemoval
        packing.getItems().clear();
        if (dto.getItems() != null) {
            dto.getItems().forEach(itemDto -> {
                BaleRollPackingItem item = toItemEntity(itemDto);
                item.setBaleRollPacking(packing);
                packing.getItems().add(item);
            });
        }
        BaleRollPacking saved = packingRepository.save(packing);

        // Update PieceEntry statuses, hfplRollNo, and passMtr for packing integration
        List<String> pieceNos = dto.getItems() != null ? dto.getItems().stream()
                .map(BaleRollPackingItemDTO::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                .collect(Collectors.toList()) : List.of();
        updatePackingStatusForPieces(saved.getId(), saved.getRollOrBaleNo(), dto.getItems());

        // Revert isPacked flag to 0 for deleted pieces
        List<String> deletedPieceNos = oldPieceNos.stream()
                .filter(pieceNo -> !pieceNos.contains(pieceNo))
                .collect(Collectors.toList());
        resetPackingStatusForSpecificPieces(deletedPieceNos);

        markWeavingWorkOrderReference(saved.getWeavingWorkOrderId());

        return toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!packingRepository.existsById(id)) throw new EntityNotFoundException("Bale/Roll Packing not found: " + id);
        
        // Reset PieceEntry statuses before deletion
        resetPackingStatusForPieces(id);
        
        itemRepository.deleteByPackingId(id);
        packingRepository.deleteById(id);
    }

    @Transactional
    public BaleRollPackingDTO updateByCompany(Long companyId, Long id, BaleRollPackingDTO dto) {
        BaleRollPacking packing = packingRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Bale/Roll Packing not found: " + id + " for companyId: " + companyId));
        Long weavingWorkOrderId = dto.getWeavingWorkOrderId() != null && dto.getWeavingWorkOrderId() > 0
        ? dto.getWeavingWorkOrderId()
        : null;
        if (weavingWorkOrderId != null && !weavingWorkOrderRepository.existsById(weavingWorkOrderId)) {
            weavingWorkOrderId = null;
        }

        Long salesOrderId = dto.getSalesOrderId() != null && dto.getSalesOrderId() > 0
        ? dto.getSalesOrderId()
        : null;
        if (salesOrderId != null && !salesOrderRepository.existsById(salesOrderId)) {
            salesOrderId = null;
        }

        requireSalesOrderReference(salesOrderId);
        requireWeavingWorkOrderReference(weavingWorkOrderId);

        packing.setPackingDate(dto.getPackingDate());
        packing.setPackingType(dto.getPackingType());
        packing.setOrderTypeId(dto.getOrderTypeId());
        packing.setBuyerId(dto.getBuyerId());
        packing.setWeavingWorkOrderId(weavingWorkOrderId);
        packing.setIsJobwork(dto.getIsJobwork());
        packing.setSalesOrderId(salesOrderId);
        packing.setFabricCodeId(dto.getFabricCodeId());
        packing.setUomId(dto.getUomId());
        packing.setWarehouseId(dto.getWarehouseId());
        packing.setGradeId(dto.getGradeId());
        packing.setRemarks(dto.getRemarks());
        packing.setIsBrushChecked(dto.getIsBrushChecked());
        packing.setVendorId(dto.getVendorId());

        packing.setRollOrBaleNo(dto.getRollOrBaleNo());
        packing.setTotalPieces(dto.getTotalPieces());
        packing.setTotalMeters(dto.getTotalMeters());
        packing.setTotalYards(dto.getTotalYards());
        packing.setTotalNet(dto.getTotalNet());
        packing.setTotalAvg(dto.getTotalAvg());
        packing.setTotalGross(dto.getTotalGross());
        packing.setIsFactoryRoll(dto.getIsFactoryRoll());
        packing.setIsDispatched(0); // Set isDispatched to 0 when updating (not yet dispatched)

        // Get old piece numbers before clearing
        List<String> oldPieceNos = packing.getItems().stream()
                .map(BaleRollPackingItem::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                .collect(Collectors.toList());

        // Replace items using orphanRemoval
        packing.getItems().clear();
        if (dto.getItems() != null) {
            dto.getItems().forEach(itemDto -> {
                BaleRollPackingItem item = toItemEntity(itemDto);
                item.setBaleRollPacking(packing);
                packing.getItems().add(item);
            });
        }
        BaleRollPacking saved = packingRepository.save(packing);

        List<String> pieceNos = dto.getItems() != null ? dto.getItems().stream()
                .map(BaleRollPackingItemDTO::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                .collect(Collectors.toList()) : List.of();
        updatePackingStatusForPieces(saved.getId(), saved.getRollOrBaleNo(), dto.getItems());

        // Revert isPacked flag to 0 for deleted pieces
        List<String> deletedPieceNos = oldPieceNos.stream()
                .filter(pieceNo -> !pieceNos.contains(pieceNo))
                .collect(Collectors.toList());
        resetPackingStatusForSpecificPieces(deletedPieceNos);

        markWeavingWorkOrderReference(saved.getWeavingWorkOrderId());

        return toDTO(saved);
    }

    @Transactional
    public void deleteByCompany(Long companyId, Long id) {
        if (!packingRepository.existsByIdAndCompanyId(id, companyId)) {
            throw new EntityNotFoundException("Bale/Roll Packing not found: " + id + " for companyId: " + companyId);
        }
        resetPackingStatusForPieces(id);
        itemRepository.deleteByPackingId(id);
        packingRepository.deleteByIdAndCompanyId(id, companyId);
    }

    public java.util.List<BaleRollPackingDTO> listByCompany(Long companyId) {
        return packingRepository.findAllByCompanyId(companyId).stream()
                .sorted(Comparator.comparing(BaleRollPacking::getId).reversed())
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<BaleRollPackingDTO> list() {
        return packingRepository.findAll().stream()
                .sorted(Comparator.comparing(BaleRollPacking::getId).reversed())
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    private void updatePackingStatusForPieces(Long packingId, String rollOrBaleNo, List<BaleRollPackingItemDTO> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        List<String> pieceNumbers = items.stream()
                .map(BaleRollPackingItemDTO::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.trim().isEmpty())
                .collect(Collectors.toList());

        Map<String, BaleRollPackingItemDTO> pieceNoToItemMap = items.stream()
                .filter(i -> i.getPieceNo() != null && !i.getPieceNo().trim().isEmpty())
                .collect(Collectors.toMap(i -> i.getPieceNo().trim(), i -> i, (a, b) -> a));

        Map<Long, BaleRollPackingItemDTO> pieceIdToItemMap = items.stream()
                .filter(i -> i.getPieceId() != null)
                .collect(Collectors.toMap(BaleRollPackingItemDTO::getPieceId, i -> i, (a, b) -> a));

        if (!pieceNumbers.isEmpty()) {
            List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
            if (!pieces.isEmpty()) {
                pieces.forEach(piece -> {
                    piece.setIsPacked(1);
                    piece.setPackingId(packingId);

                    BaleRollPackingItemDTO itemDto = null;
                    if (piece.getPieceNumber() != null) {
                        itemDto = pieceNoToItemMap.get(piece.getPieceNumber().trim());
                    }
                    if (itemDto == null && piece.getId() != null) {
                        itemDto = pieceIdToItemMap.get(piece.getId());
                    }

                    if (itemDto != null && itemDto.getMeters() != null) {
                        piece.setPassMtr(itemDto.getMeters());
                    }
                    if (rollOrBaleNo != null && !rollOrBaleNo.trim().isEmpty()) {
                        piece.setHfplRollNo(rollOrBaleNo.trim());
                    }
                });
                pieceEntryRepository.saveAll(pieces);
            }
        }

        items.forEach(itemDto -> {
            if (itemDto.getPieceId() != null) {
                pieceEntryRepository.findById(itemDto.getPieceId()).ifPresent(piece -> {
                    boolean updated = false;
                    if (piece.getIsPacked() == null || piece.getIsPacked() != 1) {
                        piece.setIsPacked(1);
                        piece.setPackingId(packingId);
                        updated = true;
                    }
                    if (itemDto.getMeters() != null) {
                        piece.setPassMtr(itemDto.getMeters());
                        updated = true;
                    }
                    if (rollOrBaleNo != null && !rollOrBaleNo.trim().isEmpty()) {
                        piece.setHfplRollNo(rollOrBaleNo.trim());
                        updated = true;
                    }
                    if (updated) {
                        pieceEntryRepository.save(piece);
                    }
                });
            }
        });

        if (!pieceNumbers.isEmpty()) {
            List<BrushingReceiveDetail> brushedDetails = brushingReceiveDetailRepository.findByPieceNumberIn(pieceNumbers);
            if (!brushedDetails.isEmpty()) {
                brushedDetails.forEach(detail -> {
                    detail.setIsPacked(1);
                });
                brushingReceiveDetailRepository.saveAll(brushedDetails);
            }
        }
    }

    @Transactional
    private void resetPackingStatusForPieces(Long packingId) {
        List<PieceEntry> pieces = pieceEntryRepository.findByPackingIdIn(List.of(packingId));
        if (pieces != null && !pieces.isEmpty()) {
            pieces.forEach(piece -> {
                piece.setIsPacked(0);
                piece.setPackingId(null);
                piece.setHfplRollNo(null);
                piece.setPassMtr(null);
            });
            pieceEntryRepository.saveAll(pieces);
        }
        pieceEntryRepository.resetPackingStatus(packingId);

        List<BaleRollPackingItem> items = itemRepository.findByBaleRollPackingId(packingId);
        if (items != null && !items.isEmpty()) {
            List<String> pieceNos = items.stream()
                    .map(BaleRollPackingItem::getPieceNo)
                    .filter(p -> p != null && !p.isEmpty())
                    .collect(Collectors.toList());
            if (!pieceNos.isEmpty()) {
                List<BrushingReceiveDetail> brushedDetails = brushingReceiveDetailRepository.findByPieceNumberIn(pieceNos);
                if (!brushedDetails.isEmpty()) {
                    brushedDetails.forEach(detail -> {
                        detail.setIsPacked(0);
                    });
                    brushingReceiveDetailRepository.saveAll(brushedDetails);
                }
            }
        }
    }

    @Transactional
    private void resetPackingStatusForSpecificPieces(List<String> pieceNumbers) {
        if (pieceNumbers == null || pieceNumbers.isEmpty()) {
            return;
        }
        
        List<PieceEntry> pieces = pieceEntryRepository.findByPieceNumberIn(pieceNumbers);
        if (!pieces.isEmpty()) {
            pieces.forEach(piece -> {
                piece.setIsPacked(0);
                piece.setPackingId(null);
                piece.setHfplRollNo(null);
                piece.setPassMtr(null);
            });
            pieceEntryRepository.saveAll(pieces);
        }

        List<BrushingReceiveDetail> brushedDetails = brushingReceiveDetailRepository.findByPieceNumberIn(pieceNumbers);
        if (!brushedDetails.isEmpty()) {
            brushedDetails.forEach(detail -> {
                detail.setIsPacked(0);
            });
            brushingReceiveDetailRepository.saveAll(brushedDetails);
        }
    }

    private void requireSalesOrderReference(Long salesOrderId) {
        if (salesOrderId == null) {
            return;
        }
        Optional<SalesOrder> salesOrder = salesOrderRepository.findById(salesOrderId);
        String displayIdentifier = salesOrder
                .map(SalesOrder::getSalesOrderNo)
                .filter(no -> no != null && !no.isBlank())
                .orElse("Sales Order ID " + salesOrderId);
        referenceValidationService.requireReference(
                salesOrderId,
                () -> salesOrder,
                "Sales Order",
                displayIdentifier);
    }

private void requireWeavingWorkOrderReference(Long weavingWorkOrderId) {
    if (weavingWorkOrderId == null || weavingWorkOrderId <= 0) {
        return;
    }
    Optional<WeavingWorkOrder> workOrder = weavingWorkOrderRepository.findById(weavingWorkOrderId);
    String displayIdentifier = workOrder
            .map(WeavingWorkOrder::getWeavingWorkOrderNo)
            .filter(no -> no != null && !no.isBlank())
            .orElse("Weaving Work Order ID " + weavingWorkOrderId);
    referenceValidationService.requireReference(
            weavingWorkOrderId,
            () -> workOrder,
            "Weaving Work Order",
            displayIdentifier);
}

private void markWeavingWorkOrderReference(Long weavingWorkOrderId) {
    if (weavingWorkOrderId != null && weavingWorkOrderId > 0) {
        weavingWorkOrderService.markAsOrderIdMapped(weavingWorkOrderId);
    }
}

    // private void requireWeavingWorkOrderReference(Long weavingWorkOrderId) {
    //     if (weavingWorkOrderId == null) {
    //         return;
    //     }
    //     Optional<WeavingWorkOrder> workOrder = weavingWorkOrderRepository.findById(weavingWorkOrderId);
    //     String displayIdentifier = workOrder
    //             .map(WeavingWorkOrder::getWeavingWorkOrderNo)
    //             .filter(no -> no != null && !no.isBlank())
    //             .orElse("Weaving Work Order ID " + weavingWorkOrderId);
    //     referenceValidationService.requireReference(
    //             weavingWorkOrderId,
    //             () -> workOrder,
    //             "Weaving Work Order",
    //             displayIdentifier);
    // }

    // private void markWeavingWorkOrderReference(Long weavingWorkOrderId) {
    //     if (weavingWorkOrderId != null) {
    //         weavingWorkOrderService.markAsOrderIdMapped(weavingWorkOrderId);
    //     }
    // }

    private BaleRollPackingDTO toDTO(BaleRollPacking entity) {
        BaleRollPackingDTO dto = new BaleRollPackingDTO();
        dto.setId(entity.getId());
        dto.setPackingDate(entity.getPackingDate());
        dto.setPackingType(entity.getPackingType());
        dto.setOrderTypeId(entity.getOrderTypeId());
        dto.setBuyerId(entity.getBuyerId());
        dto.setWeavingWorkOrderId(entity.getWeavingWorkOrderId());
        dto.setIsJobwork(entity.getIsJobwork());
        dto.setSalesOrderId(entity.getSalesOrderId());
        dto.setFabricCodeId(entity.getFabricCodeId());
//        dto.setFabricQuality(entity.getFabricQuality());
        dto.setUomId(entity.getUomId());
        dto.setWarehouseId(entity.getWarehouseId());
        dto.setGradeId(entity.getGradeId());
        dto.setRemarks(entity.getRemarks());
        dto.setPackingNo(entity.getPackingNo());
        dto.setCompanyId(entity.getCompanyId());
        dto.setIsBrushChecked(entity.getIsBrushChecked());
        dto.setVendorId(entity.getVendorId());

        // Map additional header fields
        dto.setRollOrBaleNo(entity.getRollOrBaleNo());
        dto.setTotalPieces(entity.getTotalPieces());
        dto.setTotalMeters(entity.getTotalMeters());
        dto.setTotalYards(entity.getTotalYards());
        dto.setTotalNet(entity.getTotalNet());
        dto.setTotalAvg(entity.getTotalAvg());
        dto.setTotalGross(entity.getTotalGross());
        dto.setIsFactoryRoll(entity.getIsFactoryRoll());
        dto.setIsDispatched(entity.getIsDispatched());
        dto.setIsRollScanned(entity.getIsRollScanned());

        if (entity.getItems() != null) {
            dto.setItems(entity.getItems().stream().map(this::toItemDTO).collect(Collectors.toList()));
        }
        return dto;
    }

    private BaleRollPackingItemDTO toItemDTO(BaleRollPackingItem item) {
        BaleRollPackingItemDTO dto = new BaleRollPackingItemDTO();
        dto.setId(item.getId());
        dto.setPieceNo(item.getPieceNo());
        dto.setPieceId(item.getPieceId());
        dto.setMeters(item.getMeters());
        dto.setYards(item.getYards());
        dto.setNetWeight(item.getNetWeight());
        dto.setAvgWeight(item.getAvgWeight());
        dto.setGrossWeight(item.getGrossWeight());
        dto.setIsReturnedPiece(item.getisReturnedPiece());
        return dto;
    }

    private BaleRollPackingItem toItemEntity(BaleRollPackingItemDTO dto) {
        BaleRollPackingItem item = new BaleRollPackingItem();
        item.setPieceNo(dto.getPieceNo());
        if (dto.getPieceId() != null) {
            item.setPieceId(dto.getPieceId());
        } else if (dto.getPieceNo() != null && !dto.getPieceNo().isEmpty()) {
            pieceEntryRepository.findTopByPieceNumberOrderByIdDesc(dto.getPieceNo()).ifPresent(pe -> item.setPieceId(pe.getId()));
        }
        item.setMeters(dto.getMeters());
        item.setYards(dto.getYards());
        item.setNetWeight(dto.getNetWeight());
        item.setAvgWeight(dto.getAvgWeight());
        item.setGrossWeight(dto.getGrossWeight());
        if (dto.getIsReturnedPiece() != null) {
            item.setisReturnedPiece(dto.getIsReturnedPiece());
        }
        return item;
    }
}