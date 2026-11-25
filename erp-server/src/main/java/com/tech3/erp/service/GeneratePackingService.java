package com.tech3.erp.service;

import com.tech3.erp.dto.GeneratePackingDTO;
import com.tech3.erp.dto.GeneratePackingItemDTO;
import com.tech3.erp.entity.GeneratePacking;
import com.tech3.erp.entity.GeneratePackingItem;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.repository.BaleRollPackingRepository;
import com.tech3.erp.repository.GeneratePackingItemRepository;
import com.tech3.erp.repository.GeneratePackingRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.BrushingReceiveDetailRepository;
import com.tech3.erp.entity.BrushingReceiveDetail;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeneratePackingService {

    private final GeneratePackingRepository packingRepository;
    private final GeneratePackingItemRepository itemRepository;
    private final PieceEntryRepository pieceEntryRepository;
    private final BaleRollPackingRepository baleRollPackingRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
    private final ReferenceValidationService referenceValidationService;
    private final WeavingWorkOrderService weavingWorkOrderService;
    private final BrushingReceiveDetailRepository brushingReceiveDetailRepository;

    @Autowired
    public GeneratePackingService(GeneratePackingRepository packingRepository, 
                                GeneratePackingItemRepository itemRepository,
                                PieceEntryRepository pieceEntryRepository,
                                BaleRollPackingRepository baleRollPackingRepository,
                                SalesOrderRepository salesOrderRepository,
                                WeavingWorkOrderRepository weavingWorkOrderRepository,
                                ReferenceValidationService referenceValidationService,
                                WeavingWorkOrderService weavingWorkOrderService,
                                BrushingReceiveDetailRepository brushingReceiveDetailRepository) {
        this.packingRepository = packingRepository;
        this.itemRepository = itemRepository;
        this.pieceEntryRepository = pieceEntryRepository;
        this.baleRollPackingRepository = baleRollPackingRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
        this.referenceValidationService = referenceValidationService;
        this.weavingWorkOrderService = weavingWorkOrderService;
        this.brushingReceiveDetailRepository = brushingReceiveDetailRepository;
    }

    @Transactional
    public GeneratePackingDTO createGeneratePacking(GeneratePackingDTO dto) {
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required for packing generation");
        }
        requireSalesOrderReference(dto.getSalesOrderId());
        requireWeavingWorkOrderReference(dto.getWeavingWorkOrderId());

        GeneratePacking packing = new GeneratePacking();
        packing.setCompanyId(dto.getCompanyId());
        packing.setPackingDate(dto.getPackingDate());
        packing.setBuyerId(dto.getBuyerId());
        packing.setSalesOrderId(dto.getSalesOrderId());
        packing.setWarehouseId(dto.getWarehouseId());
        packing.setTareWeight(dto.getTareWeight());
        packing.setGrossWeight(dto.getGrossWeight());
        packing.setPackingSlipNo(dto.getPackingSlipNo());
        packing.setFabricCode(dto.getFabricCode());
        packing.setFabricQuality(dto.getFabricQuality());
        packing.setRemarks(dto.getRemarks());
        packing.setOrderTypeId(dto.getOrderTypeId());
        packing.setDeliveryAt(dto.getDeliveryAt());
        packing.setIsJobwork(dto.getIsJobwork());
        packing.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
        packing.setIsInvoice(0); // Set isInvoice to 0 initially
        packing.setIsBrushChecked(dto.getIsBrushChecked());
        packing.setPoNo(dto.getPoNo());
        packing.setPoDate(dto.getPoDate());
        packing.setDcNo(dto.getDcNo());
        packing.setTransportId(dto.getTransportId());
        packing.setTruckNo(dto.getTruckNo());
        packing.setLrNo(dto.getLrNo());
        packing.setLrDate(dto.getLrDate());
        packing.setInvoiceNo(dto.getInvoiceNo());
        packing.setInvoiceDate(dto.getInvoiceDate());

        GeneratePacking savedPacking = packingRepository.save(packing);

        // Save packing items if they exist
        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<GeneratePackingItem> items = dto.getItems().stream()
                .map(itemDto -> {
                    GeneratePackingItem item = new GeneratePackingItem();
                    item.setGeneratePacking(savedPacking);
                    item.setRollNo(itemDto.getRollNo());
                    item.setPieceNo(itemDto.getPieceNo());
                    item.setLength(itemDto.getLength());
                    item.setYards(itemDto.getYards());
                    item.setUomId(itemDto.getUomId());
                    item.setPounds(itemDto.getPounds());
                    item.setAvgWt(itemDto.getAvgWt());
                    item.setGrossWt(itemDto.getGrossWt());
                    item.setLotId(itemDto.getLotId());
                    item.setRemarks(itemDto.getRemarks());
                    item.setIsScannedPacking(itemDto.getIsScannedPacking() != null ? itemDto.getIsScannedPacking() : 0);
                    item.setInvoiceNo(itemDto.getInvoiceNo());
                    return item;
                })
                .collect(Collectors.toList());
            
            itemRepository.saveAll(items);
            savedPacking.setItems(items);

            // Extract piece numbers and roll numbers
            List<String> pieceNos = dto.getItems().stream()
                    .map(GeneratePackingItemDTO::getPieceNo)
                    .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                    .collect(Collectors.toList());
            
            List<String> rollNos = dto.getItems().stream()
                    .map(GeneratePackingItemDTO::getRollNo)
                    .filter(rollNo -> rollNo != null && !rollNo.isEmpty())
                    .collect(Collectors.toList());

            if (dto.getIsBrushChecked() == null || dto.getIsBrushChecked() != 1) {
                validateRollReferences(rollNos);
            }
            // Update statuses
            updateStatusesForPiecesAndRolls(savedPacking.getId(), pieceNos, rollNos);
        }

        markWeavingWorkOrderReference(savedPacking.getWeavingWorkOrderId());
        return convertToDTO(savedPacking);
    }

    public GeneratePackingDTO getGeneratePackingById(Long id) {
        GeneratePacking packing = packingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Generate Packing not found with id: " + id));
        return convertToDTO(packing);
    }

    @Transactional
    public GeneratePackingDTO updateGeneratePacking(Long id, GeneratePackingDTO dto) {
        GeneratePacking packing = packingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Generate Packing not found with id: " + id));
        requireSalesOrderReference(dto.getSalesOrderId());
        requireWeavingWorkOrderReference(dto.getWeavingWorkOrderId());

        // Reset previous statuses before update
        resetStatusesForPiecesAndRolls(packing);

        // Update packing details
        packing.setPackingDate(dto.getPackingDate());
        packing.setBuyerId(dto.getBuyerId());
        packing.setSalesOrderId(dto.getSalesOrderId());
        packing.setWarehouseId(dto.getWarehouseId());
        packing.setTareWeight(dto.getTareWeight());
        packing.setGrossWeight(dto.getGrossWeight());
        packing.setPackingSlipNo(dto.getPackingSlipNo());
        packing.setFabricCode(dto.getFabricCode());
        packing.setFabricQuality(dto.getFabricQuality());
        packing.setRemarks(dto.getRemarks());
        packing.setOrderTypeId(dto.getOrderTypeId());
        packing.setDeliveryAt(dto.getDeliveryAt());
        packing.setIsJobwork(dto.getIsJobwork());
        packing.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
        packing.setIsBrushChecked(dto.getIsBrushChecked());
        packing.setPoNo(dto.getPoNo());
        packing.setPoDate(dto.getPoDate());
        packing.setDcNo(dto.getDcNo());
        packing.setTransportId(dto.getTransportId());
        packing.setTruckNo(dto.getTruckNo());
        packing.setLrNo(dto.getLrNo());
        packing.setLrDate(dto.getLrDate());
        packing.setInvoiceNo(dto.getInvoiceNo());
        packing.setInvoiceDate(dto.getInvoiceDate());
        if (dto.getCompanyId() != null) {
            packing.setCompanyId(dto.getCompanyId());
        }

        // Handle items update properly
        if (dto.getItems() != null) {
            // Clear existing items from the collection (this will trigger orphan removal)
            packing.getItems().clear();
            
            // Add all new items to the existing collection
            dto.getItems().forEach(itemDto -> {
                GeneratePackingItem item = new GeneratePackingItem();
                item.setGeneratePacking(packing);
                item.setRollNo(itemDto.getRollNo());
                item.setPieceNo(itemDto.getPieceNo());
                item.setLength(itemDto.getLength());
                item.setYards(itemDto.getYards());
                item.setUomId(itemDto.getUomId());
                item.setPounds(itemDto.getPounds());
                item.setAvgWt(itemDto.getAvgWt());
                item.setGrossWt(itemDto.getGrossWt());
                item.setLotId(itemDto.getLotId());
                item.setRemarks(itemDto.getRemarks());
                item.setIsScannedPacking(itemDto.getIsScannedPacking() != null ? itemDto.getIsScannedPacking() : 0);
                item.setInvoiceNo(itemDto.getInvoiceNo());
                packing.getItems().add(item);
            });
        } else {
            // If no items in DTO, clear all items
            packing.getItems().clear();
        }

        GeneratePacking updatedPacking = packingRepository.save(packing);

        // Extract piece numbers and roll numbers from updated items
        List<String> pieceNos = dto.getItems() != null ? dto.getItems().stream()
                .map(GeneratePackingItemDTO::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                .collect(Collectors.toList()) : List.of();
        
        List<String> rollNos = dto.getItems() != null ? dto.getItems().stream()
                .map(GeneratePackingItemDTO::getRollNo)
                .filter(rollNo -> rollNo != null && !rollNo.isEmpty())
                .collect(Collectors.toList()) : List.of();

        if (dto.getIsBrushChecked() == null || dto.getIsBrushChecked() != 1) {
            validateRollReferences(rollNos);
        }
        // Update statuses
        updateStatusesForPiecesAndRolls(updatedPacking.getId(), pieceNos, rollNos);
        markWeavingWorkOrderReference(updatedPacking.getWeavingWorkOrderId());

        return convertToDTO(updatedPacking);
    }

    @Transactional
    public void deleteGeneratePacking(Long id) {
        GeneratePacking packing = packingRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Generate Packing not found with id: " + id));
        try {
            // Reset statuses before deletion
            resetStatusesForPiecesAndRolls(packing);

            // Delete the packing (items will be deleted via CascadeType.ALL)
            packingRepository.delete(packing);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Generate Packing because it is referenced by other records");
        }
    }

    @Transactional
    private void updateStatusesForPiecesAndRolls(Long packingId, List<String> pieceNumbers, List<String> rollNumbers) {
        // Process and split pieceNumbers (which can be comma-separated lists of pieces)
        List<String> splitPieceNumbers = new java.util.ArrayList<>();
        if (pieceNumbers != null) {
            for (String pNo : pieceNumbers) {
                if (pNo != null && !pNo.isEmpty()) {
                    for (String part : pNo.split(",")) {
                        String trimmed = part.trim();
                        if (!trimmed.isEmpty()) {
                            splitPieceNumbers.add(trimmed);
                        }
                    }
                }
            }
        }

        // Update PieceEntry
        
        // 1. Pieces directly in the packing list
        if (!splitPieceNumbers.isEmpty()) {
            pieceEntryRepository.markDispatchedByPieceNumbers(splitPieceNumbers, packingId);
        }
        
        // 2. Pieces inside rolls in the packing list
        GeneratePacking packing = packingRepository.findById(packingId).orElse(null);
        Long salesOrderId = packing != null ? packing.getSalesOrderId() : null;

        List<com.tech3.erp.entity.BaleRollPacking> rolls = List.of();
        if (rollNumbers != null && !rollNumbers.isEmpty()) {
            rolls = baleRollPackingRepository.findByRollOrBaleNoIn(rollNumbers);
            if (!rolls.isEmpty()) {
                if (salesOrderId != null) {
                    rolls = rolls.stream()
                            .filter(r -> salesOrderId.equals(r.getSalesOrderId()))
                            .collect(Collectors.toList());
                }
                // Group by roll number and keep only the latest record (highest id)
                java.util.Map<String, com.tech3.erp.entity.BaleRollPacking> latestRollsMap = new java.util.HashMap<>();
                for (com.tech3.erp.entity.BaleRollPacking r : rolls) {
                    if (r.getRollOrBaleNo() != null) {
                        com.tech3.erp.entity.BaleRollPacking existing = latestRollsMap.get(r.getRollOrBaleNo());
                        if (existing == null || r.getId() > existing.getId()) {
                            latestRollsMap.put(r.getRollOrBaleNo(), r);
                        }
                    }
                }
                rolls = new java.util.ArrayList<>(latestRollsMap.values());

                if (!rolls.isEmpty()) {
                    List<Long> rollIds = rolls.stream()
                            .map(com.tech3.erp.entity.BaleRollPacking::getId)
                            .collect(Collectors.toList());
                    pieceEntryRepository.markDispatchedByPackingIds(rollIds, packingId);
                }
            }
        }
        
        // Update BaleRollPacking
        if ((rollNumbers != null && !rollNumbers.isEmpty()) || !splitPieceNumbers.isEmpty()) {
            if (salesOrderId != null) {
                baleRollPackingRepository.updateIsDispatchedForSalesOrder(
                    rollNumbers != null ? rollNumbers : List.of(), 
                    splitPieceNumbers,
                    salesOrderId
                );
            } else {
                baleRollPackingRepository.updateIsDispatched(
                    rollNumbers != null ? rollNumbers : List.of(), 
                    splitPieceNumbers
                );
            }
        }

        // 3. Update BrushingReceiveDetail statuses
        List<String> allPieceNos = new java.util.ArrayList<>();
        allPieceNos.addAll(splitPieceNumbers);
        if (!rolls.isEmpty()) {
            rolls.forEach(roll -> {
                if (roll.getItems() != null) {
                    roll.getItems().forEach(item -> {
                        if (item.getPieceNo() != null && !item.getPieceNo().isEmpty()) {
                            for (String part : item.getPieceNo().split(",")) {
                                String trimmed = part.trim();
                                if (!trimmed.isEmpty()) {
                                    allPieceNos.add(trimmed);
                                }
                            }
                        }
                    });
                }
            });
        }
        if (!allPieceNos.isEmpty()) {
            List<BrushingReceiveDetail> brushedDetails = brushingReceiveDetailRepository.findByPieceNumberIn(allPieceNos);
            if (!brushedDetails.isEmpty()) {
                brushedDetails.forEach(detail -> {
                    detail.setIsDispatched(1);
                    if (packing != null) {
                        java.time.LocalDate localDate = null;
                        if (packing.getPackingDate() != null) {
                            java.util.Date d = packing.getPackingDate();
                            if (d instanceof java.sql.Date) {
                                localDate = ((java.sql.Date) d).toLocalDate();
                            } else {
                                localDate = d.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                            }
                        }
                        detail.setDispatchDate(localDate);
                        detail.setPoNo(packing.getPoNo());
                        detail.setPoDate(packing.getPoDate());
                    }
                });
                brushingReceiveDetailRepository.saveAll(brushedDetails);
            }
        }
    }

    @Transactional
    private void resetStatusesForPiecesAndRolls(GeneratePacking packing) {
        if (packing == null) return;
        
        // Reset PieceEntry status
        pieceEntryRepository.resetPackingStatus(packing.getId());
        pieceEntryRepository.resetDispatchStatus(packing.getId());
        
        // Reset BaleRollPacking status
        List<String> rollNos = packing.getItems().stream()
                .map(GeneratePackingItem::getRollNo)
                .filter(rollNo -> rollNo != null && !rollNo.isEmpty())
                .collect(Collectors.toList());
        
        List<String> pieceNos = packing.getItems().stream()
                .map(GeneratePackingItem::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                .collect(Collectors.toList());

        // Process and split pieceNos
        List<String> splitPieceNos = new java.util.ArrayList<>();
        if (pieceNos != null) {
            for (String pNo : pieceNos) {
                if (pNo != null && !pNo.isEmpty()) {
                    for (String part : pNo.split(",")) {
                        String trimmed = part.trim();
                        if (!trimmed.isEmpty()) {
                            splitPieceNos.add(trimmed);
                        }
                    }
                }
            }
        }
        
        if (!rollNos.isEmpty() || !splitPieceNos.isEmpty()) {
            if (packing.getSalesOrderId() != null) {
                baleRollPackingRepository.resetIsDispatchedForSalesOrder(rollNos, splitPieceNos, packing.getSalesOrderId());
            } else {
                baleRollPackingRepository.resetIsDispatched(rollNos, splitPieceNos);
            }
        }

        // Reset BrushingReceiveDetail statuses
        List<String> resetPieceNos = new java.util.ArrayList<>();
        resetPieceNos.addAll(splitPieceNos);
        List<BrushingReceiveDetail> brushedDetails = new java.util.ArrayList<>();

        if (packing.getIsBrushChecked() != null && packing.getIsBrushChecked() == 1) {
            if (!resetPieceNos.isEmpty()) {
                brushedDetails.addAll(brushingReceiveDetailRepository.findByPieceNumberIn(resetPieceNos));
            }
            if (!rollNos.isEmpty()) {
                brushedDetails.addAll(brushingReceiveDetailRepository.findByHfplRollNoInAndBrushingReceiveCompanyId(rollNos, packing.getCompanyId()));
            }
        } else {
            if (!rollNos.isEmpty()) {
                List<com.tech3.erp.entity.BaleRollPacking> rolls = baleRollPackingRepository.findByRollOrBaleNoIn(rollNos);
                if (!rolls.isEmpty()) {
                    rolls.forEach(roll -> {
                        if (roll.getItems() != null) {
                            roll.getItems().forEach(item -> {
                                if (item.getPieceNo() != null && !item.getPieceNo().isEmpty()) {
                                    for (String part : item.getPieceNo().split(",")) {
                                        String trimmed = part.trim();
                                        if (!trimmed.isEmpty()) {
                                            resetPieceNos.add(trimmed);
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
            if (!resetPieceNos.isEmpty()) {
                brushedDetails.addAll(brushingReceiveDetailRepository.findByPieceNumberIn(resetPieceNos));
            }
        }

        List<BrushingReceiveDetail> uniqueBrushedDetails = brushedDetails.stream()
                .distinct()
                .collect(Collectors.toList());

        if (!uniqueBrushedDetails.isEmpty()) {
            uniqueBrushedDetails.forEach(detail -> {
                detail.setIsDispatched(0);
                detail.setDispatchDate(null);
                detail.setPoNo(null);
                detail.setPoDate(null);
            });
            brushingReceiveDetailRepository.saveAll(uniqueBrushedDetails);
        }
    }

    private void validateRollReferences(List<String> rollNos) {
        if (rollNos == null || rollNos.isEmpty()) {
            return;
        }
        rollNos.stream()
                .filter(rollNo -> rollNo != null && !rollNo.isBlank())
                .distinct()
                .forEach(this::requireBaleRollPackingReference);
    }

    private void requireBaleRollPackingReference(String rollNo) {
        referenceValidationService.requireReference(
                rollNo,
                () -> baleRollPackingRepository.findByRollOrBaleNo(rollNo),
                "Bale/Roll Packing",
                rollNo);
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
        if (weavingWorkOrderId == null) {
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
        if (weavingWorkOrderId != null) {
            weavingWorkOrderService.markAsOrderIdMapped(weavingWorkOrderId);
        }
    }

    private GeneratePackingDTO convertToDTO(GeneratePacking packing) {
        GeneratePackingDTO dto = new GeneratePackingDTO();
        dto.setId(packing.getId());
        dto.setCompanyId(packing.getCompanyId());
        dto.setPackingDate(packing.getPackingDate());
        dto.setBuyerId(packing.getBuyerId());
        dto.setSalesOrderId(packing.getSalesOrderId());
        dto.setWarehouseId(packing.getWarehouseId());
        dto.setTareWeight(packing.getTareWeight());
        dto.setGrossWeight(packing.getGrossWeight());
        dto.setPackingSlipNo(packing.getPackingSlipNo());
        dto.setFabricCode(packing.getFabricCode());
        dto.setFabricQuality(packing.getFabricQuality());
        dto.setRemarks(packing.getRemarks());
        dto.setOrderTypeId(packing.getOrderTypeId());
        dto.setDeliveryAt(packing.getDeliveryAt());
        dto.setIsJobwork(packing.getIsJobwork());
        dto.setWeavingWorkOrderId(packing.getWeavingWorkOrderId());
        dto.setIsInvoice(packing.getIsInvoice());
        dto.setIsBrushChecked(packing.getIsBrushChecked());
        dto.setPoNo(packing.getPoNo());
        dto.setPoDate(packing.getPoDate());
        dto.setDcNo(packing.getDcNo());
        dto.setTransportId(packing.getTransportId());
        dto.setTruckNo(packing.getTruckNo());
        dto.setLrNo(packing.getLrNo());
        dto.setLrDate(packing.getLrDate());
        dto.setInvoiceNo(packing.getInvoiceNo());
        dto.setInvoiceDate(packing.getInvoiceDate());

        if (packing.getItems() != null) {
            List<GeneratePackingItemDTO> itemDTOs = packing.getItems().stream()
                .map(item -> new GeneratePackingItemDTO(
                    item.getId(),
                    packing.getId(),
                    item.getRollNo(),
                    item.getPieceNo(),
                    item.getLength(),
                    item.getYards(),
                    item.getUomId(),
                    item.getPounds(),
                    item.getAvgWt(),
                    item.getGrossWt(),
                    item.getLotId(),
                    item.getRemarks(),
                    item.getInvoiceId(),
                    item.getIsInvoice(),
                    item.getIsScannedPacking(),
                    item.getInvoiceNo()
                ))
                .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        }
        return dto;
    }

    public List<GeneratePackingDTO> getAllGeneratePackings() {
        List<GeneratePacking> packings = packingRepository.findAll();
        
        return packings.stream()
            .sorted(Comparator.comparing(GeneratePacking::getId).reversed())    
            .map(packing -> {
                GeneratePackingDTO dto = new GeneratePackingDTO();
                dto.setId(packing.getId());
                dto.setCompanyId(packing.getCompanyId());
                dto.setPackingDate(packing.getPackingDate());
                dto.setBuyerId(packing.getBuyerId());
                dto.setSalesOrderId(packing.getSalesOrderId());
                dto.setWarehouseId(packing.getWarehouseId());
                dto.setTareWeight(packing.getTareWeight());
                dto.setGrossWeight(packing.getGrossWeight());
                dto.setPackingSlipNo(packing.getPackingSlipNo());
                dto.setFabricCode(packing.getFabricCode());
                dto.setFabricQuality(packing.getFabricQuality());
                dto.setRemarks(packing.getRemarks());
                dto.setOrderTypeId(packing.getOrderTypeId());
                dto.setDeliveryAt(packing.getDeliveryAt());
                dto.setIsJobwork(packing.getIsJobwork());
                dto.setWeavingWorkOrderId(packing.getWeavingWorkOrderId());
                dto.setIsInvoice(packing.getIsInvoice());
                dto.setIsBrushChecked(packing.getIsBrushChecked());
                dto.setPoNo(packing.getPoNo());
                dto.setPoDate(packing.getPoDate());
                dto.setDcNo(packing.getDcNo());
                dto.setTransportId(packing.getTransportId());
                dto.setTruckNo(packing.getTruckNo());
                dto.setLrNo(packing.getLrNo());
                dto.setLrDate(packing.getLrDate());
                dto.setInvoiceNo(packing.getInvoiceNo());
                dto.setInvoiceDate(packing.getInvoiceDate());
                
                if (packing.getItems() != null) {
                    List<GeneratePackingItemDTO> itemDTOs = packing.getItems().stream()
                        .map(item -> {
                            GeneratePackingItemDTO itemDto = new GeneratePackingItemDTO();
                            itemDto.setId(item.getId());
                            itemDto.setGeneratedPackingId(packing.getId());
                            itemDto.setRollNo(item.getRollNo());
                            itemDto.setPieceNo(item.getPieceNo());
                            itemDto.setLength(item.getLength());
                            itemDto.setYards(item.getYards());
                            itemDto.setUomId(item.getUomId());
                            itemDto.setPounds(item.getPounds());
                            itemDto.setAvgWt(item.getAvgWt());
                            itemDto.setGrossWt(item.getGrossWt());
                            itemDto.setLotId(item.getLotId());
                            itemDto.setRemarks(item.getRemarks());
                            itemDto.setInvoiceId(item.getInvoiceId());
                            itemDto.setIsInvoice(item.getIsInvoice());
                            itemDto.setIsScannedPacking(item.getIsScannedPacking());
                            itemDto.setInvoiceNo(item.getInvoiceNo());
                            return itemDto;
                        })
                        .collect(Collectors.toList());
                    dto.setItems(itemDTOs);
                }
                
                return dto;
            })
            .collect(Collectors.toList());
    }

    public List<GeneratePackingDTO> getGeneratePackingsByCompany(Long companyId) {
        List<GeneratePacking> packings = packingRepository.findByCompanyId(companyId);
        return packings.stream()
                .sorted(Comparator.comparing(GeneratePacking::getId).reversed())
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public GeneratePackingDTO getGeneratePackingByIdAndCompany(Long id, Long companyId) {
        GeneratePacking packing = packingRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Generate Packing not found with id: " + id + " and companyId: " + companyId));
        return convertToDTO(packing);
    }

    @Transactional
    public GeneratePackingDTO updateGeneratePackingByCompany(Long id, Long companyId, GeneratePackingDTO dto) {
        GeneratePacking packing = packingRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Generate Packing not found with id: " + id + " and companyId: " + companyId));
        requireSalesOrderReference(dto.getSalesOrderId());
        requireWeavingWorkOrderReference(dto.getWeavingWorkOrderId());

        // Reset previous statuses before update
        resetStatusesForPiecesAndRolls(packing);

        dto.setCompanyId(packing.getCompanyId());

        packing.setPackingDate(dto.getPackingDate());
        packing.setBuyerId(dto.getBuyerId());
        packing.setSalesOrderId(dto.getSalesOrderId());
        packing.setWarehouseId(dto.getWarehouseId());
        packing.setTareWeight(dto.getTareWeight());
        packing.setGrossWeight(dto.getGrossWeight());
        packing.setPackingSlipNo(dto.getPackingSlipNo());
        packing.setFabricCode(dto.getFabricCode());
        packing.setFabricQuality(dto.getFabricQuality());
        packing.setRemarks(dto.getRemarks());
        packing.setOrderTypeId(dto.getOrderTypeId());
        packing.setDeliveryAt(dto.getDeliveryAt());
        packing.setIsJobwork(dto.getIsJobwork());
        packing.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
        packing.setIsBrushChecked(dto.getIsBrushChecked());
        packing.setPoNo(dto.getPoNo());
        packing.setPoDate(dto.getPoDate());
        packing.setDcNo(dto.getDcNo());
        packing.setTransportId(dto.getTransportId());
        packing.setTruckNo(dto.getTruckNo());
        packing.setLrNo(dto.getLrNo());
        packing.setLrDate(dto.getLrDate());
        packing.setInvoiceNo(dto.getInvoiceNo());
        packing.setInvoiceDate(dto.getInvoiceDate());

        if (dto.getItems() != null) {
            packing.getItems().clear();
            dto.getItems().forEach(itemDto -> {
                GeneratePackingItem item = new GeneratePackingItem();
                item.setGeneratePacking(packing);
                item.setRollNo(itemDto.getRollNo());
                item.setPieceNo(itemDto.getPieceNo());
                item.setLength(itemDto.getLength());
                item.setYards(itemDto.getYards());
                item.setUomId(itemDto.getUomId());
                item.setPounds(itemDto.getPounds());
                item.setAvgWt(itemDto.getAvgWt());
                item.setGrossWt(itemDto.getGrossWt());
                item.setLotId(itemDto.getLotId());
                item.setRemarks(itemDto.getRemarks());
                item.setIsScannedPacking(itemDto.getIsScannedPacking() != null ? itemDto.getIsScannedPacking() : 0);
                item.setInvoiceNo(itemDto.getInvoiceNo());
                packing.getItems().add(item);
            });
        } else {
            packing.getItems().clear();
        }

        GeneratePacking updatedPacking = packingRepository.save(packing);

        List<String> pieceNos = dto.getItems() != null ? dto.getItems().stream()
                .map(GeneratePackingItemDTO::getPieceNo)
                .filter(pieceNo -> pieceNo != null && !pieceNo.isEmpty())
                .collect(Collectors.toList()) : List.of();
        
        List<String> rollNos = dto.getItems() != null ? dto.getItems().stream()
                .map(GeneratePackingItemDTO::getRollNo)
                .filter(rollNo -> rollNo != null && !rollNo.isEmpty())
                .collect(Collectors.toList()) : List.of();

        updateStatusesForPiecesAndRolls(updatedPacking.getId(), pieceNos, rollNos);
        markWeavingWorkOrderReference(updatedPacking.getWeavingWorkOrderId());

        return convertToDTO(updatedPacking);
    }

    @Transactional
    public void deleteGeneratePackingByCompany(Long id, Long companyId) {
        GeneratePacking packing = packingRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Generate Packing not found with id: " + id + " and companyId: " + companyId));
        
        resetStatusesForPiecesAndRolls(packing);
        // Delete the packing (items will be deleted via CascadeType.ALL)
        packingRepository.delete(packing);
    }
}
