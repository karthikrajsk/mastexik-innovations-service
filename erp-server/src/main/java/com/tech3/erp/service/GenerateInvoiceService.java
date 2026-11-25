package com.tech3.erp.service;

import com.tech3.erp.dto.AddDiscItemDTO;
import com.tech3.erp.dto.GenerateInvoiceDTO;
import com.tech3.erp.dto.InvoiceItemDTO;
import com.tech3.erp.dto.InvoiceSizingSalesDTO;
import com.tech3.erp.entity.AddDiscItem;
import com.tech3.erp.entity.GenerateInvoice;
import com.tech3.erp.entity.GeneratePacking;
import com.tech3.erp.entity.GeneratePackingItem;
import com.tech3.erp.entity.InvoiceItem;
import com.tech3.erp.entity.InvoiceSizingSalesEntity;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.entity.Consignee;
import com.tech3.erp.entity.YarnChallan;
import com.tech3.erp.repository.AddDiscItemRepository;
import com.tech3.erp.repository.GenerateInvoiceRepository;
import com.tech3.erp.repository.GeneratePackingItemRepository;
import com.tech3.erp.repository.GeneratePackingRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.ConsigneeRepository;
import com.tech3.erp.repository.InvoiceItemRepository;
import com.tech3.erp.repository.InvoiceSizingSalesRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.YarnChallanItemRepository;
import com.tech3.erp.repository.YarnChallanRepository;
import com.tech3.erp.entity.YarnChallan;
import com.tech3.erp.repository.ScanDispatchRepository;
import com.tech3.erp.repository.BrushingReceiveDetailRepository;
import com.tech3.erp.entity.BrushingReceiveDetail;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GenerateInvoiceService {

    private final GenerateInvoiceRepository generateInvoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final AddDiscItemRepository addDiscItemRepository;
    private final PieceEntryRepository pieceEntryRepository;
    private final GeneratePackingRepository generatePackingRepository;
    private final GeneratePackingItemRepository generatePackingItemRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final WeavingWorkOrderRepository weavingWorkOrderRepository;
    private final ConsigneeRepository consigneeRepository;
    private final YarnChallanItemRepository yarnChallanItemRepository;
    private final YarnChallanRepository yarnChallanRepository;
    private final ScanDispatchRepository scanDispatchRepository;
    private final ReferenceValidationService referenceValidationService;
    private final InvoiceSizingSalesRepository invoiceSizingSalesRepository;
    private final com.tech3.erp.repository.BaleRollPackingRepository baleRollPackingRepository;
    private final BrushingReceiveDetailRepository brushingReceiveDetailRepository;
    
    @Autowired
    public GenerateInvoiceService(GenerateInvoiceRepository generateInvoiceRepository, 
                                 InvoiceItemRepository invoiceItemRepository,
                                 AddDiscItemRepository addDiscItemRepository,
                                 PieceEntryRepository pieceEntryRepository,
                                 GeneratePackingRepository generatePackingRepository,
                                 GeneratePackingItemRepository generatePackingItemRepository,
                                 SalesOrderRepository salesOrderRepository,
                                 WeavingWorkOrderRepository weavingWorkOrderRepository,
                                 ConsigneeRepository consigneeRepository,
                                 YarnChallanItemRepository yarnChallanItemRepository,
                                 YarnChallanRepository yarnChallanRepository,
                                 ScanDispatchRepository scanDispatchRepository,
                                 ReferenceValidationService referenceValidationService,
                                 InvoiceSizingSalesRepository invoiceSizingSalesRepository,
                                 com.tech3.erp.repository.BaleRollPackingRepository baleRollPackingRepository,
                                 BrushingReceiveDetailRepository brushingReceiveDetailRepository) {
        this.generateInvoiceRepository = generateInvoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.addDiscItemRepository = addDiscItemRepository;
        this.pieceEntryRepository = pieceEntryRepository;
        this.generatePackingRepository = generatePackingRepository;
        this.generatePackingItemRepository = generatePackingItemRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.weavingWorkOrderRepository = weavingWorkOrderRepository;
        this.consigneeRepository = consigneeRepository;
        this.yarnChallanItemRepository = yarnChallanItemRepository;
        this.yarnChallanRepository = yarnChallanRepository;
        this.scanDispatchRepository = scanDispatchRepository;
        this.referenceValidationService = referenceValidationService;
        this.invoiceSizingSalesRepository = invoiceSizingSalesRepository;
        this.baleRollPackingRepository = baleRollPackingRepository;
        this.brushingReceiveDetailRepository = brushingReceiveDetailRepository;
    }

    @Transactional
    public GenerateInvoiceDTO createInvoice(GenerateInvoiceDTO dto) {
        GenerateInvoice invoice = new GenerateInvoice();
        mapDtoToEntity(dto, invoice);
        invoice.setIsInvoice(1);
        GenerateInvoice savedInvoice = generateInvoiceRepository.save(invoice);
        
        // Save invoice items
        if (dto.getItems() != null) {
            for (InvoiceItemDTO itemDto : dto.getItems()) {
                InvoiceItem item = new InvoiceItem();
                item.setInvoiceId(savedInvoice.getId());
                item.setFabricMasterId(itemDto.getFabricMasterId());
                item.setProductName(itemDto.getProductName());
                item.setProductDescription(itemDto.getProductDescription());
                item.setQuantity(itemDto.getQuantity());
                item.setNoOfRolls(itemDto.getNoOfRolls());
                item.setUom(itemDto.getUom());
                item.setRate(itemDto.getRate());
                item.setAmount(itemDto.getAmount());
                item.setGstPercent(itemDto.getGstPercent());
                item.setHsnCode(itemDto.getHsnCode());
                item.setTaxableAmount(itemDto.getTaxableAmount());
                item.setGstAmount(itemDto.getGstAmount());
                item.setTotalAmount(itemDto.getTotalAmount());
                item.setIsReadOnlyQuantity(itemDto.getIsReadOnlyQuantity());
                item.setIsInvoice(itemDto.getIsInvoice());
                item.setIsScannedPacking(itemDto.getIsScannedPacking() != null ? itemDto.getIsScannedPacking() : 0);
                item.setPackingListNo(itemDto.getPackingListNo());
                item.setYarnChallanId(itemDto.getYarnChallanId());
                item.setYarnChallanNo(itemDto.getYarnChallanNo());
                item.setYarnId(itemDto.getYarnId() != null ? itemDto.getYarnId() : fetchYarnIdFromChallan(itemDto.getYarnChallanNo(), itemDto.getYarnChallanId()));
                item.setActiveFlag(true);
                invoiceItemRepository.save(item); // Use instance variable
            }
        }
        
        // Save sizing sales items
        if (dto.getSizingSalesItems() != null) {
            for (InvoiceSizingSalesDTO sizingDto : dto.getSizingSalesItems()) {
                InvoiceSizingSalesEntity sizingEntity = new InvoiceSizingSalesEntity();
                sizingEntity.setInvoiceId(savedInvoice.getId());
                sizingEntity.setBeamOutwardNo(sizingDto.getBeamOutwardNo());
                sizingEntity.setYarnName(sizingDto.getYarnName());
                sizingEntity.setNoOfBeams(sizingDto.getNoOfBeams());
                sizingEntity.setBeamMeters(sizingDto.getBeamMeters());
                sizingEntity.setNetWeight(sizingDto.getNetWeight());
                sizingEntity.setTotalEnds(sizingDto.getTotalEnds());
                sizingEntity.setActiveFlag(true);
                invoiceSizingSalesRepository.save(sizingEntity);
            }
        }
        
        if (dto.getAddDiscItems() != null) {
            for (AddDiscItemDTO d : dto.getAddDiscItems()) {
                AddDiscItem e = new AddDiscItem();
                e.setInvoiceId(savedInvoice.getId());
                e.setDescription(d.getDescription());
                e.setType(d.getType());
                e.setMode(d.getMode());
                e.setPercValue(d.getPercValue());
                e.setAmount(d.getAmount());
                e.setIsDraft(false);
                e.setActiveFlag(true);
                addDiscItemRepository.save(e);
            }
        }
        if (dto.getAddDiscDrafts() != null) {
            for (AddDiscItemDTO d : dto.getAddDiscDrafts()) {
                AddDiscItem e = new AddDiscItem();
                e.setInvoiceId(savedInvoice.getId());
                e.setDescription(d.getDescription());
                e.setType(d.getType());
                e.setMode(d.getMode());
                e.setPercValue(d.getPercValue());
                e.setAmount(d.getAmount());
                e.setIsDraft(true);
                e.setActiveFlag(true);
                addDiscItemRepository.save(e);
            }
        }
        
        if (!"Sizing Sales".equals(dto.getInvoiceType())) {
            List<String> packingListNos = dto.getItems() != null ? dto.getItems().stream()
                    .map(InvoiceItemDTO::getPackingListNo)
                    .filter(packingListNo -> packingListNo != null && !packingListNo.isEmpty())
                    .distinct()
                    .collect(Collectors.toList()) : List.of();
            validatePackingListReferences(packingListNos);
            List<Long> yarnChallanIds = extractYarnChallanIds(dto.getItems());
            updateInvoiceStatusForPieces(savedInvoice.getId(), packingListNos, yarnChallanIds);
        }
        syncGeneratePackingDeliveryAt(savedInvoice, null);
        // Sync consignee/deliveryAt to linked Yarn Challans (two-way sync)
        syncInvoiceToYarnChallans(savedInvoice, null);
        return getInvoiceById(savedInvoice.getId());
    }

    public GenerateInvoiceDTO getInvoiceById(Long id) {
        GenerateInvoice invoice = generateInvoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        GenerateInvoiceDTO dto = mapEntityToDto(invoice);
        
        List<InvoiceItem> items = invoiceItemRepository.findByInvoiceIdAndActiveFlagTrue(id);
        List<InvoiceItemDTO> itemDTOs = items.stream()
                .map(this::mapItemEntityToDto)
                .collect(Collectors.toList());
        dto.setItems(itemDTOs);

        List<InvoiceSizingSalesEntity> sizingItems = invoiceSizingSalesRepository.findByInvoiceIdAndActiveFlagTrue(id);
        List<InvoiceSizingSalesDTO> sizingDTOs = sizingItems.stream()
                .map(this::mapSizingEntityToDto)
                .collect(Collectors.toList());
        dto.setSizingSalesItems(sizingDTOs);

        List<AddDiscItemDTO> addDTOs = addDiscItemRepository.findByInvoiceId(id).stream()
                .filter(a -> Boolean.TRUE.equals(a.getActiveFlag()))
                .filter(a -> a.getIsDraft() == null || Boolean.FALSE.equals(a.getIsDraft()))
                .map(this::mapAddDiscEntityToDto)
                .collect(Collectors.toList());
        dto.setAddDiscItems(addDTOs);

        return dto;
    }

    public List<GenerateInvoiceDTO> getAllInvoices() {
        List<GenerateInvoiceDTO> invoices = generateInvoiceRepository.findAll().stream()
                .sorted(Comparator.comparing(GenerateInvoice::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        for (GenerateInvoiceDTO invoice : invoices) {
            List<InvoiceItem> items = invoiceItemRepository.findByInvoiceIdAndActiveFlagTrue(invoice.getId());
            List<InvoiceItemDTO> itemDTOs = items.stream()
                    .map(this::mapItemEntityToDto)
                    .collect(Collectors.toList());
            invoice.setItems(itemDTOs);

            List<InvoiceSizingSalesEntity> sizingItemsForInvoice = invoiceSizingSalesRepository.findByInvoiceIdAndActiveFlagTrue(invoice.getId());
            List<InvoiceSizingSalesDTO> sizingDTOsForInvoice = sizingItemsForInvoice.stream()
                    .map(this::mapSizingEntityToDto)
                    .collect(Collectors.toList());
            invoice.setSizingSalesItems(sizingDTOsForInvoice);

            List<AddDiscItemDTO> addDTOs = addDiscItemRepository.findByInvoiceId(invoice.getId()).stream()
                    .filter(a -> Boolean.TRUE.equals(a.getActiveFlag()))
                    .filter(a -> a.getIsDraft() == null || Boolean.FALSE.equals(a.getIsDraft()))
                    .map(this::mapAddDiscEntityToDto)
                    .collect(Collectors.toList());
            invoice.setAddDiscItems(addDTOs);

        }
        
        return invoices;
    }

    public List<GenerateInvoiceDTO> getAllInvoicesByCompanyId(Long companyId) {
        List<GenerateInvoiceDTO> invoices = generateInvoiceRepository.findByCompanyId(companyId).stream()
                .sorted(Comparator.comparing(GenerateInvoice::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        for (GenerateInvoiceDTO invoice : invoices) {
            List<InvoiceItem> items = invoiceItemRepository.findByInvoiceIdAndActiveFlagTrue(invoice.getId());
            List<InvoiceItemDTO> itemDTOs = items.stream()
                    .map(this::mapItemEntityToDto)
                    .collect(Collectors.toList());
            invoice.setItems(itemDTOs);

            List<InvoiceSizingSalesEntity> sizingItemsForInvoice = invoiceSizingSalesRepository.findByInvoiceIdAndActiveFlagTrue(invoice.getId());
            List<InvoiceSizingSalesDTO> sizingDTOsForInvoice = sizingItemsForInvoice.stream()
                    .map(this::mapSizingEntityToDto)
                    .collect(Collectors.toList());
            invoice.setSizingSalesItems(sizingDTOsForInvoice);

            List<AddDiscItemDTO> addDTOs = addDiscItemRepository.findByInvoiceId(invoice.getId()).stream()
                    .filter(a -> Boolean.TRUE.equals(a.getActiveFlag()))
                    .filter(a -> a.getIsDraft() == null || Boolean.FALSE.equals(a.getIsDraft()))
                    .map(this::mapAddDiscEntityToDto)
                    .collect(Collectors.toList());
            invoice.setAddDiscItems(addDTOs);

        }
        
        return invoices;
    }

    public GenerateInvoiceDTO getInvoiceByIdAndCompanyId(Long id, Long companyId) {
        GenerateInvoice invoice = generateInvoiceRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id + " and companyId: " + companyId));
        GenerateInvoiceDTO dto = mapEntityToDto(invoice);
        
        List<InvoiceItem> items = invoiceItemRepository.findByInvoiceIdAndActiveFlagTrue(id);
        List<InvoiceItemDTO> itemDTOs = items.stream()
                .map(this::mapItemEntityToDto)
                .collect(Collectors.toList());
        dto.setItems(itemDTOs);

        List<InvoiceSizingSalesEntity> sizingItems = invoiceSizingSalesRepository.findByInvoiceIdAndActiveFlagTrue(id);
        List<InvoiceSizingSalesDTO> sizingDTOs = sizingItems.stream()
                .map(this::mapSizingEntityToDto)
                .collect(Collectors.toList());
        dto.setSizingSalesItems(sizingDTOs);

        List<AddDiscItemDTO> addDTOs = addDiscItemRepository.findByInvoiceId(id).stream()
                .filter(a -> Boolean.TRUE.equals(a.getActiveFlag()))
                .filter(a -> a.getIsDraft() == null || Boolean.FALSE.equals(a.getIsDraft()))
                .map(this::mapAddDiscEntityToDto)
                .collect(Collectors.toList());
        dto.setAddDiscItems(addDTOs);

        return dto;
    }

    @Transactional
    public GenerateInvoiceDTO updateInvoice(Long id, GenerateInvoiceDTO dto) {
        GenerateInvoice invoice = generateInvoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        Long oldConsigneeId = invoice.getConsgineeId();
        mapDtoToEntity(dto, invoice);
        invoice.setIsInvoice(1);
        GenerateInvoice updatedInvoice = generateInvoiceRepository.save(invoice);
        
        // Update invoice items
        if (dto.getItems() != null) {
            List<InvoiceItem> existingItems = invoiceItemRepository.findByInvoiceIdAndActiveFlagTrue(id);
            List<Long> dtoItemIds = dto.getItems().stream()
                    .map(InvoiceItemDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Mark items as inactive if not in the DTO
            for (InvoiceItem item : existingItems) {
                if (!dtoItemIds.contains(item.getId())) {
                    item.setActiveFlag(false);
                    invoiceItemRepository.save(item);
                }
            }

            for (InvoiceItemDTO itemDto : dto.getItems()) {
                InvoiceItem item;
                if (itemDto.getId() != null) {
                    item = invoiceItemRepository.findById(itemDto.getId())
                            .orElse(new InvoiceItem());
                } else {
                    item = new InvoiceItem();
                }
                item.setInvoiceId(id);
                item.setFabricMasterId(itemDto.getFabricMasterId());
                item.setProductName(itemDto.getProductName());
                item.setProductDescription(itemDto.getProductDescription());
                item.setQuantity(itemDto.getQuantity());
                item.setNoOfRolls(itemDto.getNoOfRolls());
                item.setUom(itemDto.getUom());
                item.setRate(itemDto.getRate());
                item.setAmount(itemDto.getAmount());
                item.setGstPercent(itemDto.getGstPercent());
                item.setHsnCode(itemDto.getHsnCode());
                item.setTaxableAmount(itemDto.getTaxableAmount());
                item.setGstAmount(itemDto.getGstAmount());
                item.setTotalAmount(itemDto.getTotalAmount());
                item.setIsReadOnlyQuantity(itemDto.getIsReadOnlyQuantity());
                item.setIsInvoice(itemDto.getIsInvoice());
                item.setIsScannedPacking(itemDto.getIsScannedPacking() != null ? itemDto.getIsScannedPacking() : 0);
                item.setPackingListNo(itemDto.getPackingListNo());
                item.setYarnChallanId(itemDto.getYarnChallanId());
                item.setYarnChallanNo(itemDto.getYarnChallanNo());
                item.setYarnId(itemDto.getYarnId() != null ? itemDto.getYarnId() : fetchYarnIdFromChallan(itemDto.getYarnChallanNo(), itemDto.getYarnChallanId()));
                item.setActiveFlag(true);
                invoiceItemRepository.save(item);
            }
        }

        // Update sizing sales items
        if (dto.getSizingSalesItems() != null) {
            List<InvoiceSizingSalesEntity> existingSizingItems = invoiceSizingSalesRepository.findByInvoiceIdAndActiveFlagTrue(id);
            List<Long> dtoSizingIds = dto.getSizingSalesItems().stream()
                    .map(InvoiceSizingSalesDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            for (InvoiceSizingSalesEntity item : existingSizingItems) {
                if (!dtoSizingIds.contains(item.getId())) {
                    item.setActiveFlag(false);
                    invoiceSizingSalesRepository.save(item);
                }
            }

            for (InvoiceSizingSalesDTO sizingDto : dto.getSizingSalesItems()) {
                InvoiceSizingSalesEntity sizingEntity;
                if (sizingDto.getId() != null) {
                    sizingEntity = invoiceSizingSalesRepository.findById(sizingDto.getId())
                            .orElse(new InvoiceSizingSalesEntity());
                } else {
                    sizingEntity = new InvoiceSizingSalesEntity();
                }
                sizingEntity.setInvoiceId(id);
                sizingEntity.setBeamOutwardNo(sizingDto.getBeamOutwardNo());
                sizingEntity.setYarnName(sizingDto.getYarnName());
                sizingEntity.setNoOfBeams(sizingDto.getNoOfBeams());
                sizingEntity.setBeamMeters(sizingDto.getBeamMeters());
                sizingEntity.setNetWeight(sizingDto.getNetWeight());
                sizingEntity.setTotalEnds(sizingDto.getTotalEnds());
                sizingEntity.setActiveFlag(true);
                invoiceSizingSalesRepository.save(sizingEntity);
            }
        }

        // Update AddDisc items
        if (dto.getAddDiscItems() != null || dto.getAddDiscDrafts() != null) {
            List<AddDiscItem> existingAddDiscs = addDiscItemRepository.findByInvoiceIdAndActiveFlagTrue(id);
            List<Long> dtoAddDiscIds = new ArrayList<>();
            if (dto.getAddDiscItems() != null) {
                dtoAddDiscIds.addAll(dto.getAddDiscItems().stream()
                        .map(AddDiscItemDTO::getId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
            }
            if (dto.getAddDiscDrafts() != null) {
                dtoAddDiscIds.addAll(dto.getAddDiscDrafts().stream()
                        .map(AddDiscItemDTO::getId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
            }

            for (AddDiscItem e : existingAddDiscs) {
                if (!dtoAddDiscIds.contains(e.getId())) {
                    e.setActiveFlag(false);
                    addDiscItemRepository.save(e);
                }
            }

            if (dto.getAddDiscItems() != null) {
                for (AddDiscItemDTO d : dto.getAddDiscItems()) {
                    AddDiscItem e;
                    if (d.getId() != null) {
                        e = addDiscItemRepository.findById(d.getId())
                                .orElse(new AddDiscItem());
                    } else {
                        e = new AddDiscItem();
                    }
                    e.setInvoiceId(id);
                    e.setDescription(d.getDescription());
                    e.setType(d.getType());
                    e.setMode(d.getMode());
                    e.setPercValue(d.getPercValue());
                    e.setAmount(d.getAmount());
                    e.setIsDraft(false);
                    e.setActiveFlag(true);
                    addDiscItemRepository.save(e);
                }
            }
            if (dto.getAddDiscDrafts() != null) {
                for (AddDiscItemDTO d : dto.getAddDiscDrafts()) {
                    AddDiscItem e;
                    if (d.getId() != null) {
                        e = addDiscItemRepository.findById(d.getId())
                                .orElse(new AddDiscItem());
                    } else {
                        e = new AddDiscItem();
                    }
                    e.setInvoiceId(id);
                    e.setDescription(d.getDescription());
                    e.setType(d.getType());
                    e.setMode(d.getMode());
                    e.setPercValue(d.getPercValue());
                    e.setAmount(d.getAmount());
                    e.setIsDraft(true);
                    e.setActiveFlag(true);
                    addDiscItemRepository.save(e);
                }
            }
        }
        
        resetInvoiceStatusForPieces(id);
        
        if (!"Sizing Sales".equals(dto.getInvoiceType())) {
            List<String> packingListNos = dto.getItems() != null ? dto.getItems().stream()
                    .map(InvoiceItemDTO::getPackingListNo)
                    .filter(packingListNo -> packingListNo != null && !packingListNo.isEmpty())
                    .distinct()
                    .collect(Collectors.toList()) : List.of();
            validatePackingListReferences(packingListNos);
            List<Long> yarnChallanIds = extractYarnChallanIds(dto.getItems());
            updateInvoiceStatusForPieces(updatedInvoice.getId(), packingListNos, yarnChallanIds);
        }
        
        syncGeneratePackingDeliveryAt(updatedInvoice, oldConsigneeId);
        // Sync consignee/deliveryAt to linked Yarn Challans (two-way sync)
        syncInvoiceToYarnChallans(updatedInvoice, oldConsigneeId);
        return getInvoiceById(updatedInvoice.getId());
    }

    @Transactional
    public GenerateInvoiceDTO updateInvoiceByCompanyId(Long id, Long companyId, GenerateInvoiceDTO dto) {
        GenerateInvoice invoice = generateInvoiceRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id + " and companyId: " + companyId));
        Long oldConsigneeId = invoice.getConsgineeId();
        mapDtoToEntity(dto, invoice);
        invoice.setIsInvoice(1);
        GenerateInvoice updatedInvoice = generateInvoiceRepository.save(invoice);
        
        if (dto.getItems() != null) {
            List<InvoiceItem> existingItems = invoiceItemRepository.findByInvoiceIdAndActiveFlagTrue(id);
            List<Long> dtoItemIds = dto.getItems().stream()
                    .map(InvoiceItemDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            // Mark items as inactive if not in the DTO
            for (InvoiceItem item : existingItems) {
                if (!dtoItemIds.contains(item.getId())) {
                    item.setActiveFlag(false);
                    invoiceItemRepository.save(item);
                }
            }
            
            for (InvoiceItemDTO itemDto : dto.getItems()) {
                InvoiceItem item;
                if (itemDto.getId() != null) {
                    item = invoiceItemRepository.findById(itemDto.getId())
                            .orElse(new InvoiceItem());
                } else {
                    item = new InvoiceItem();
                }
                item.setInvoiceId(id);
                item.setFabricMasterId(itemDto.getFabricMasterId());
                item.setProductName(itemDto.getProductName());
                item.setProductDescription(itemDto.getProductDescription());
                item.setQuantity(itemDto.getQuantity());
                item.setNoOfRolls(itemDto.getNoOfRolls());
                item.setUom(itemDto.getUom());
                item.setRate(itemDto.getRate());
                item.setAmount(itemDto.getAmount());
                item.setGstPercent(itemDto.getGstPercent());
                item.setHsnCode(itemDto.getHsnCode());
                item.setTaxableAmount(itemDto.getTaxableAmount());
                item.setGstAmount(itemDto.getGstAmount());
                item.setTotalAmount(itemDto.getTotalAmount());
                item.setIsReadOnlyQuantity(itemDto.getIsReadOnlyQuantity());
                item.setIsInvoice(itemDto.getIsInvoice());
                item.setIsScannedPacking(itemDto.getIsScannedPacking() != null ? itemDto.getIsScannedPacking() : 0);
                item.setPackingListNo(itemDto.getPackingListNo());
                item.setYarnChallanId(itemDto.getYarnChallanId());
                item.setYarnChallanNo(itemDto.getYarnChallanNo());
                item.setYarnId(itemDto.getYarnId() != null ? itemDto.getYarnId() : fetchYarnIdFromChallan(itemDto.getYarnChallanNo(), itemDto.getYarnChallanId()));
                item.setActiveFlag(true);
                invoiceItemRepository.save(item);
            }
        }

        // Update sizing sales items
        if (dto.getSizingSalesItems() != null) {
            List<InvoiceSizingSalesEntity> existingSizingItems = invoiceSizingSalesRepository.findByInvoiceIdAndActiveFlagTrue(id);
            List<Long> dtoSizingIds = dto.getSizingSalesItems().stream()
                    .map(InvoiceSizingSalesDTO::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            for (InvoiceSizingSalesEntity item : existingSizingItems) {
                if (!dtoSizingIds.contains(item.getId())) {
                    item.setActiveFlag(false);
                    invoiceSizingSalesRepository.save(item);
                }
            }

            for (InvoiceSizingSalesDTO sizingDto : dto.getSizingSalesItems()) {
                InvoiceSizingSalesEntity sizingEntity;
                if (sizingDto.getId() != null) {
                    sizingEntity = invoiceSizingSalesRepository.findById(sizingDto.getId())
                            .orElse(new InvoiceSizingSalesEntity());
                } else {
                    sizingEntity = new InvoiceSizingSalesEntity();
                }
                sizingEntity.setInvoiceId(id);
                sizingEntity.setBeamOutwardNo(sizingDto.getBeamOutwardNo());
                sizingEntity.setYarnName(sizingDto.getYarnName());
                sizingEntity.setNoOfBeams(sizingDto.getNoOfBeams());
                sizingEntity.setBeamMeters(sizingDto.getBeamMeters());
                sizingEntity.setNetWeight(sizingDto.getNetWeight());
                sizingEntity.setTotalEnds(sizingDto.getTotalEnds());
                sizingEntity.setActiveFlag(true);
                invoiceSizingSalesRepository.save(sizingEntity);
            }
        }

        // Update AddDisc items
        if (dto.getAddDiscItems() != null || dto.getAddDiscDrafts() != null) {
            List<AddDiscItem> existingAddDiscs = addDiscItemRepository.findByInvoiceIdAndActiveFlagTrue(id);
            List<Long> dtoAddDiscIds = new ArrayList<>();
            if (dto.getAddDiscItems() != null) {
                dtoAddDiscIds.addAll(dto.getAddDiscItems().stream()
                        .map(AddDiscItemDTO::getId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
            }
            if (dto.getAddDiscDrafts() != null) {
                dtoAddDiscIds.addAll(dto.getAddDiscDrafts().stream()
                        .map(AddDiscItemDTO::getId)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
            }

            for (AddDiscItem e : existingAddDiscs) {
                if (!dtoAddDiscIds.contains(e.getId())) {
                    e.setActiveFlag(false);
                    addDiscItemRepository.save(e);
                }
            }

            if (dto.getAddDiscItems() != null) {
                for (AddDiscItemDTO d : dto.getAddDiscItems()) {
                    AddDiscItem e;
                    if (d.getId() != null) {
                        e = addDiscItemRepository.findById(d.getId())
                                .orElse(new AddDiscItem());
                    } else {
                        e = new AddDiscItem();
                    }
                    e.setInvoiceId(id);
                    e.setDescription(d.getDescription());
                    e.setType(d.getType());
                    e.setMode(d.getMode());
                    e.setPercValue(d.getPercValue());
                    e.setAmount(d.getAmount());
                    e.setIsDraft(false);
                    e.setActiveFlag(true);
                    addDiscItemRepository.save(e);
                }
            }
            if (dto.getAddDiscDrafts() != null) {
                for (AddDiscItemDTO d : dto.getAddDiscDrafts()) {
                    AddDiscItem e;
                    if (d.getId() != null) {
                        e = addDiscItemRepository.findById(d.getId())
                                .orElse(new AddDiscItem());
                    } else {
                        e = new AddDiscItem();
                    }
                    e.setInvoiceId(id);
                    e.setDescription(d.getDescription());
                    e.setType(d.getType());
                    e.setMode(d.getMode());
                    e.setPercValue(d.getPercValue());
                    e.setAmount(d.getAmount());
                    e.setIsDraft(true);
                    e.setActiveFlag(true);
                    addDiscItemRepository.save(e);
                }
            }
        }
        
        resetInvoiceStatusForPieces(id);
        
        if (!"Sizing Sales".equals(dto.getInvoiceType())) {
            List<String> packingListNos = dto.getItems() != null ? dto.getItems().stream()
                    .map(InvoiceItemDTO::getPackingListNo)
                    .filter(packingListNo -> packingListNo != null && !packingListNo.isEmpty())
                    .distinct()
                    .collect(Collectors.toList()) : List.of();
            validatePackingListReferences(packingListNos);
            List<Long> yarnChallanIds = extractYarnChallanIds(dto.getItems());
            updateInvoiceStatusForPieces(updatedInvoice.getId(), packingListNos, yarnChallanIds);
        }
        
        syncGeneratePackingDeliveryAt(updatedInvoice, oldConsigneeId);
        // Sync consignee/deliveryAt to linked Yarn Challans (two-way sync)
        syncInvoiceToYarnChallans(updatedInvoice, oldConsigneeId);
        return getInvoiceById(updatedInvoice.getId());
    }

    /**
     * Conservative sync from Invoice -> YarnChallan.
     * Only updates YarnChallan delivery/consignee when the challan has no manual deliveryAt
     * or already references the same consignee id. This prevents overwriting manual changes.
     */
    private void syncInvoiceToYarnChallans(GenerateInvoice invoice, Long oldConsigneeId) {
        if (invoice == null || invoice.getId() == null) return;

        boolean forceConsigneeUpdate = invoice.getConsgineeId() != null && !Objects.equals(oldConsigneeId, invoice.getConsgineeId());

        // gather challans by invoiceId and by yarnSalesOrderId (if present)
        List<YarnChallan> challans = new ArrayList<>();
        List<YarnChallan> challansByInvoice = yarnChallanRepository.findByInvoiceId(invoice.getId());
        if (challansByInvoice != null) challans.addAll(challansByInvoice);

        if (invoice.getYarnSalesOrderId() != null) {
            List<YarnChallan> byOrder = yarnChallanRepository.findByYarnSalesOrderId(invoice.getYarnSalesOrderId());
            if (byOrder != null) {
                for (YarnChallan yc : byOrder) {
                    if (yc == null) continue;
                    boolean exists = false;
                    for (YarnChallan e : challans) {
                        if (e != null && e.getId() != null && e.getId().equals(yc.getId())) { exists = true; break; }
                    }
                    if (!exists) challans.add(yc);
                }
            }
        }

        if (challans.isEmpty()) return;

        String deliveryAt = null;
        if (invoice.getConsgineeId() != null) {
            deliveryAt = resolveDeliveryAtFromConsigneeId(invoice.getConsgineeId());
        }
        if (deliveryAt == null || deliveryAt.isBlank()) {
            deliveryAt = resolveDeliveryAtFromOrder(invoice);
        }

        List<YarnChallan> toSave = new ArrayList<>();
        for (YarnChallan yc : challans) {
            if (yc == null) continue;

            boolean shouldUpdate = false;

            if (invoice.getConsgineeId() != null) {
                // invoice explicitly sets consigneeId: update challan if it is blank, matches the same consignee,
                // or if the invoice consignee changed manually.
                if (forceConsigneeUpdate || yc.getConsigneeId() == null || yc.getConsigneeId().equals(invoice.getConsgineeId())) {
                    shouldUpdate = true;
                }
            } else {
                // invoice has no consignee id; only update challan when its deliveryAt is blank
                if (yc.getDeliveryAt() == null || yc.getDeliveryAt().isBlank()) {
                    shouldUpdate = true;
                }
            }

            if (!shouldUpdate) continue;

            if (invoice.getConsgineeId() != null) {
                yc.setConsigneeId(invoice.getConsgineeId());
            }
            if (deliveryAt != null && !deliveryAt.isBlank()) {
                if (forceConsigneeUpdate || yc.getDeliveryAt() == null || yc.getDeliveryAt().isBlank() || (invoice.getConsgineeId() != null && yc.getConsigneeId() != null && yc.getConsigneeId().equals(invoice.getConsgineeId()))) {
                    yc.setDeliveryAt(deliveryAt);
                }
            }
            toSave.add(yc);
        }

        if (!toSave.isEmpty()) yarnChallanRepository.saveAll(toSave);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        GenerateInvoice invoice = generateInvoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        
        ensureInvoiceNotDispatched(invoice);
        
        resetInvoiceStatusForPieces(id);
        
        invoiceItemRepository.deleteByInvoiceId(id);
        invoiceSizingSalesRepository.deleteByInvoiceId(id);
        addDiscItemRepository.deleteByInvoiceId(id);
        
        generateInvoiceRepository.delete(invoice);
    }

    @Transactional
    public void deleteInvoiceByCompanyId(Long id, Long companyId) {
        GenerateInvoice invoice = generateInvoiceRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id + " and companyId: " + companyId));
        
        ensureInvoiceNotDispatched(invoice);
        
        resetInvoiceStatusForPieces(id);
        
        invoiceItemRepository.deleteByInvoiceId(id);
        invoiceSizingSalesRepository.deleteByInvoiceId(id);
        addDiscItemRepository.deleteByInvoiceId(id);
        
        generateInvoiceRepository.delete(invoice);
    }

    // New method to get dispatched quantities by sales order
    public List<Object[]> getDispatchedQuantitiesBySalesOrder(Long salesOrderId) {
        return invoiceItemRepository.findDispatchedQuantityBySalesOrder(salesOrderId); // Use instance variable
    }

    @Transactional
    private void updateInvoiceStatusForPieces(Long invoiceId, List<String> packingListNos, List<Long> yarnChallanIds) {
        GenerateInvoice invoice = generateInvoiceRepository.findById(invoiceId).orElse(null);
        String invoiceNoVal = invoice != null ? invoice.getInvoiceNo() : null;
        java.time.LocalDate invoiceDateVal = invoice != null ? invoice.getInvoiceDate() : null;

        if (packingListNos != null && !packingListNos.isEmpty()) {
            // Find packing lists by their slip numbers
            List<GeneratePacking> packings = generatePackingRepository.findAll().stream()
                    .filter(p -> packingListNos.contains(p.getPackingSlipNo()))
                    .collect(Collectors.toList());

            if (!packings.isEmpty()) {
                // Set invoice details on the packing list entities
                for (GeneratePacking packing : packings) {
                    packing.setIsInvoice(1);
                    packing.setInvoiceNo(invoiceNoVal);
                    packing.setInvoiceDate(invoiceDateVal);
                }
                generatePackingRepository.saveAll(packings);

                List<String> allPieceNumbers = new java.util.ArrayList<>();
                List<String> rollNumbers = new java.util.ArrayList<>();

                for (GeneratePacking packing : packings) {
                    if (packing.getItems() != null) {
                        for (GeneratePackingItem item : packing.getItems()) {
                            if (item.getPieceNo() != null && !item.getPieceNo().isEmpty()) {
                                allPieceNumbers.add(item.getPieceNo());
                                for (String part : item.getPieceNo().split(",")) {
                                    String trimmed = part.trim();
                                    if (!trimmed.isEmpty()) {
                                        allPieceNumbers.add(trimmed);
                                    }
                                }
                            }
                            if (item.getRollNo() != null && !item.getRollNo().isEmpty()) {
                                rollNumbers.add(item.getRollNo());
                            }
                        }
                    }
                }

                // 1. Update by piece numbers
                List<String> distinctPieceNumbers = allPieceNumbers.stream().distinct().collect(Collectors.toList());
                if (!distinctPieceNumbers.isEmpty()) {
                    pieceEntryRepository.markInvoicedByPieceNumbers(distinctPieceNumbers, invoiceId);
                    generatePackingItemRepository.markInvoicedByPieceNumbers(distinctPieceNumbers, invoiceId);
                }

                // 2. Update by roll numbers (pieces inside rolls)
                List<String> distinctRollNumbers = rollNumbers.stream().distinct().collect(Collectors.toList());
                List<com.tech3.erp.entity.BaleRollPacking> rolls = List.of();
                if (!distinctRollNumbers.isEmpty()) {
                    rolls = baleRollPackingRepository.findByRollOrBaleNoIn(distinctRollNumbers);
                    if (!rolls.isEmpty()) {
                        Long salesOrderId = invoice != null ? invoice.getSalesOrderId() : null;
                        
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
                            pieceEntryRepository.markInvoicedByPackingIds(rollIds, invoiceId);
                        }
                    }
                }

                // 3. Update BrushingReceiveDetail statuses
                List<String> allPieceNos = new java.util.ArrayList<>();
                for (String pNo : allPieceNumbers) {
                    if (pNo != null && !pNo.isEmpty()) {
                        for (String part : pNo.split(",")) {
                            String trimmed = part.trim();
                            if (!trimmed.isEmpty()) {
                                allPieceNos.add(trimmed);
                            }
                        }
                    }
                }
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
                List<String> distinctAllPieceNos = allPieceNos.stream().distinct().collect(Collectors.toList());
                if (!distinctAllPieceNos.isEmpty()) {
                    List<BrushingReceiveDetail> brushedDetails = brushingReceiveDetailRepository.findByPieceNumberIn(distinctAllPieceNos);
                    if (!brushedDetails.isEmpty()) {
                        brushedDetails.forEach(detail -> {
                            detail.setIsInvoice(1);
                            if (invoiceNoVal != null) {
                                detail.setHfplInvoiceNo(invoiceNoVal);
                            }
                        });
                        brushingReceiveDetailRepository.saveAll(brushedDetails);
                    }
                }
            }
        }

        if (yarnChallanIds != null && !yarnChallanIds.isEmpty()) {
            yarnChallanItemRepository.markInvoicedByChallanIds(yarnChallanIds, invoiceId);
            yarnChallanRepository.markInvoiced(yarnChallanIds, invoiceId);
        }
    }

    private void syncGeneratePackingDeliveryAt(GenerateInvoice newInvoice, Long oldConsigneeId) {
        Long newConsigneeId = newInvoice.getConsgineeId();
        if (Objects.equals(oldConsigneeId, newConsigneeId)) {
            return;
        }

        // Resolve delivery at from consignee first, then fallback to order details
        String deliveryAt = null;
        
        if (newConsigneeId != null) {
            deliveryAt = resolveDeliveryAtFromConsigneeId(newConsigneeId);
        }
        
        // If not resolved from consignee (or consignee is null), try to resolve from order
        if (deliveryAt == null || deliveryAt.isBlank()) {
            deliveryAt = resolveDeliveryAtFromOrder(newInvoice);
        }
        
        updateDeliveryAtForGeneratePacking(newInvoice, deliveryAt);
    }

    private String resolveDeliveryAtFromConsigneeId(Long consigneeId) {
        if (consigneeId == null) {
            return "";
        }
        Consignee consignee = consigneeRepository.findById(consigneeId)
                .orElse(null);
        if (consignee == null) {
            return "";
        }
        String name = consignee.getConsigneeName();
        return name != null ? name.trim() : "";
    }

    private String resolveDeliveryAtFromOrder(GenerateInvoice invoice) {
        if (invoice.getSalesOrderId() != null) {
            return resolveDeliveryAtFromSalesOrderId(invoice.getSalesOrderId());
        }
        if (invoice.getWeavingWorkOrderId() != null) {
            return resolveDeliveryAtFromWeavingWorkOrderId(invoice.getWeavingWorkOrderId());
        }
        return "";
    }

    private String resolveDeliveryAtFromSalesOrderId(Long salesOrderId) {
        if (salesOrderId == null) {
            return "";
        }
        SalesOrder order = salesOrderRepository.findById(salesOrderId)
                .orElse(null);
        if (order == null || order.getDeliverToId() == null) {
            return "";
        }
        return resolveDeliveryAtFromConsigneeId(order.getDeliverToId());
    }

    private String resolveDeliveryAtFromWeavingWorkOrderId(Long weavingWorkOrderId) {
        if (weavingWorkOrderId == null) {
            return "";
        }
        WeavingWorkOrder workOrder = weavingWorkOrderRepository.findById(weavingWorkOrderId)
                .orElse(null);
        if (workOrder == null || workOrder.getConsigneeId() == null) {
            return "";
        }
        return resolveDeliveryAtFromConsigneeId(workOrder.getConsigneeId());
    }

    private void updateDeliveryAtForGeneratePacking(GenerateInvoice invoice, String deliveryAt) {
        // Only update if deliveryAt has a valid value
        if (deliveryAt == null || deliveryAt.isBlank()) {
            return;
        }

        Set<Long> processedIds = new HashSet<>();
        List<GeneratePacking> packings = new ArrayList<>();

        if (invoice.getSalesOrderId() != null) {
            List<GeneratePacking> salesOrderPackings = generatePackingRepository.findBySalesOrderId(invoice.getSalesOrderId());
            for (GeneratePacking packing : salesOrderPackings) {
                if (packing.getId() != null && processedIds.add(packing.getId())) {
                    packings.add(packing);
                }
            }
        }
        if (invoice.getWeavingWorkOrderId() != null) {
            List<GeneratePacking> weavingPackings = generatePackingRepository.findByWeavingWorkOrderId(invoice.getWeavingWorkOrderId());
            for (GeneratePacking packing : weavingPackings) {
                if (packing.getId() != null && processedIds.add(packing.getId())) {
                    packings.add(packing);
                }
            }
        }

        if (packings.isEmpty()) {
            return;
        }

        for (GeneratePacking packing : packings) {
            packing.setDeliveryAt(deliveryAt);
        }
        generatePackingRepository.saveAll(packings);
    }

    @Transactional
    private void resetInvoiceStatusForPieces(Long invoiceId) {
        // Reset isInvoice flag in GeneratePacking table for this invoice
        generatePackingRepository.resetInvoicedStatus(invoiceId);
        
        pieceEntryRepository.resetInvoiceStatus(invoiceId);
        generatePackingItemRepository.resetInvoiceStatus(invoiceId);
        yarnChallanItemRepository.resetInvoiceStatus(invoiceId);
        yarnChallanRepository.resetInvoiced(invoiceId);

        // Reset BrushingReceiveDetail statuses
        List<com.tech3.erp.entity.InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoiceId(invoiceId);
        if (invoiceItems != null && !invoiceItems.isEmpty()) {
            List<String> packingListNos = invoiceItems.stream()
                    .map(com.tech3.erp.entity.InvoiceItem::getPackingListNo)
                    .filter(p -> p != null && !p.isEmpty())
                    .distinct()
                    .collect(Collectors.toList());
            if (!packingListNos.isEmpty()) {
                List<GeneratePacking> packings = generatePackingRepository.findAll().stream()
                        .filter(p -> packingListNos.contains(p.getPackingSlipNo()))
                        .collect(Collectors.toList());
                List<String> pieceNumbersToReset = new java.util.ArrayList<>();
                List<String> rollNumbers = new java.util.ArrayList<>();
                for (GeneratePacking packing : packings) {
                    if (packing.getItems() != null) {
                        for (GeneratePackingItem item : packing.getItems()) {
                            if (item.getPieceNo() != null && !item.getPieceNo().isEmpty()) {
                                pieceNumbersToReset.add(item.getPieceNo());
                                for (String part : item.getPieceNo().split(",")) {
                                    String trimmed = part.trim();
                                    if (!trimmed.isEmpty()) {
                                        pieceNumbersToReset.add(trimmed);
                                    }
                                }
                            }
                            if (item.getRollNo() != null && !item.getRollNo().isEmpty()) {
                                rollNumbers.add(item.getRollNo());
                            }
                        }
                    }
                }
                if (!rollNumbers.isEmpty()) {
                    List<com.tech3.erp.entity.BaleRollPacking> rolls = baleRollPackingRepository.findByRollOrBaleNoIn(rollNumbers);
                    rolls.forEach(roll -> {
                        if (roll.getItems() != null) {
                            roll.getItems().forEach(item -> {
                                if (item.getPieceNo() != null && !item.getPieceNo().isEmpty()) {
                                    for (String part : item.getPieceNo().split(",")) {
                                        String trimmed = part.trim();
                                        if (!trimmed.isEmpty()) {
                                            pieceNumbersToReset.add(trimmed);
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
                
                List<String> distinctPieceNumbersToReset = pieceNumbersToReset.stream().distinct().collect(Collectors.toList());
                if (!distinctPieceNumbersToReset.isEmpty()) {
                    List<BrushingReceiveDetail> brushedDetails = brushingReceiveDetailRepository.findByPieceNumberIn(distinctPieceNumbersToReset);
                    if (!brushedDetails.isEmpty()) {
                        brushedDetails.forEach(detail -> {
                            detail.setIsInvoice(0);
                            detail.setHfplInvoiceNo(null);
                        });
                        brushingReceiveDetailRepository.saveAll(brushedDetails);
                    }
                }
            }
        }
    }

    private GenerateInvoiceDTO mapEntityToDto(GenerateInvoice invoice) {
        GenerateInvoiceDTO dto = new GenerateInvoiceDTO();
        dto.setId(invoice.getId());
        dto.setManufactureId(invoice.getManufactureId());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setInvoiceNo(invoice.getInvoiceNo());
        dto.setSalesOrderId(invoice.getSalesOrderId());
        dto.setSizingOrderId(invoice.getSizingOrderId());
        dto.setYarnSalesOrderId(invoice.getYarnSalesOrderId());
        dto.setCompanyBankId(invoice.getCompanyBankId());
        dto.setTermsConditionsId(invoice.getTermsConditionsId());
        dto.setPaymentTermsId(invoice.getPaymentTermsId());
        dto.setShipmentMode(invoice.getShipmentMode());
        dto.setCustomerId(invoice.getCustomerId());
        dto.setConsgineeId(invoice.getConsgineeId());
        dto.setSoNo(invoice.getSoNo());
        dto.setSjoNo(invoice.getSjoNo());
        dto.setAgentId(invoice.getAgentId());
        dto.setInvoiceTypeId(invoice.getInvoiceTypeId());
        dto.setInvoiceType(invoice.getInvoiceType());
        dto.setWarehouseId(invoice.getWarehouseId());
        dto.setGstType(invoice.getGstType());
        dto.setTaxableAmountTotal(invoice.getTaxableAmountTotal());
        dto.setTaxAmount(invoice.getTaxAmount());
        dto.setAdditionTotal(invoice.getAdditionTotal());
        dto.setDiscountTotal(invoice.getDiscountTotal());
        dto.setNetAdditionDiscount(invoice.getNetAdditionDiscount());
        dto.setRoundOff(invoice.getRoundOff());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setAdditional(invoice.getAdditional());
        dto.setLess(invoice.getLess());
        dto.setComments(invoice.getComments());
        dto.setActiveFlag(invoice.getActiveFlag());
        dto.setShipVia(invoice.getShipVia());
        dto.setCompanyBankName(invoice.getCompanyBankName());
        dto.setShipmentModeName(invoice.getShipmentModeName());
        dto.setTermsConditionsName(invoice.getTermsConditionsName());
        dto.setPaymentTermsName(invoice.getPaymentTermsName());
        dto.setAgentName(invoice.getAgentName());
        dto.setWarehouseName(invoice.getWarehouseName());
        dto.setBuyerStateCode(invoice.getBuyerStateCode());
        dto.setCompanyStateCode(invoice.getCompanyStateCode());
        dto.setGstPercent(invoice.getGstPercent());
        dto.setCgstPercent(invoice.getCgstPercent());
        dto.setSgstPercent(invoice.getSgstPercent());
        dto.setIgstPercent(invoice.getIgstPercent());
        dto.setCompanyId(invoice.getCompanyId());
        dto.setIsInvoice(invoice.getIsInvoice());
        dto.setIsJobwork(invoice.getIsJobwork());
        dto.setWeavingWorkOrderId(invoice.getWeavingWorkOrderId());
        dto.setWoNo(invoice.getWoNo());
        dto.setPackingListNo(invoice.getPackingListNo());
        dto.setTransportNameId(invoice.getTransportNameId());
        dto.setDeliveryTermsId(invoice.getDeliveryTermsId());
        dto.setDeliveryNote(invoice.getDeliveryNote());
        dto.setLrNo(invoice.getLrNo());
        dto.setLrDate(invoice.getLrDate());
        dto.setVehicleNo(invoice.getVehicleNo());
        dto.setIsBrushedPiece(invoice.getIsBrushedPiece());
        dto.setDeliveryNoteDate(invoice.getDeliveryNoteDate());
        dto.setDestination(invoice.getDestination());
        return dto;
    }

    private InvoiceItemDTO mapItemEntityToDto(InvoiceItem item) {
        InvoiceItemDTO dto = new InvoiceItemDTO();
        dto.setId(item.getId());
        dto.setInvoiceId(item.getInvoiceId());
        dto.setFabricMasterId(item.getFabricMasterId());
        dto.setProductName(item.getProductName());
        dto.setProductDescription(item.getProductDescription());
        dto.setQuantity(item.getQuantity());
        dto.setNoOfRolls(item.getNoOfRolls());
        dto.setUom(item.getUom());
        dto.setRate(item.getRate());
        dto.setAmount(item.getAmount());
        dto.setGstPercent(item.getGstPercent());
        dto.setHsnCode(item.getHsnCode());
        dto.setTaxableAmount(item.getTaxableAmount());
        dto.setGstAmount(item.getGstAmount());
        dto.setTotalAmount(item.getTotalAmount());
        dto.setIsInvoice(item.getIsInvoice());
        dto.setIsScannedPacking(item.getIsScannedPacking());
        dto.setPackingListNo(item.getPackingListNo());
        dto.setYarnChallanId(item.getYarnChallanId());
        dto.setYarnChallanNo(item.getYarnChallanNo());
        dto.setYarnId(item.getYarnId());
        return dto;
    }

    private InvoiceSizingSalesDTO mapSizingEntityToDto(InvoiceSizingSalesEntity item) {
        InvoiceSizingSalesDTO dto = new InvoiceSizingSalesDTO();
        dto.setId(item.getId());
        dto.setInvoiceId(item.getInvoiceId());
        dto.setBeamOutwardNo(item.getBeamOutwardNo());
        dto.setYarnName(item.getYarnName());
        dto.setNoOfBeams(item.getNoOfBeams());
        dto.setBeamMeters(item.getBeamMeters());
        dto.setNetWeight(item.getNetWeight());
        dto.setTotalEnds(item.getTotalEnds());
        dto.setActiveFlag(item.getActiveFlag());
        return dto;
    }

    private AddDiscItemDTO mapAddDiscEntityToDto(AddDiscItem e) {
        AddDiscItemDTO d = new AddDiscItemDTO();
        d.setId(e.getId());
        d.setDescription(e.getDescription());
        d.setType(e.getType());
        d.setMode(e.getMode());
        d.setPercValue(e.getPercValue());
        d.setAmount(e.getAmount());
        return d;
    }

    private void mapDtoToEntity(GenerateInvoiceDTO dto, GenerateInvoice invoice) {
        invoice.setManufactureId(dto.getManufactureId());
        invoice.setInvoiceDate(dto.getInvoiceDate());
        invoice.setInvoiceNo(dto.getInvoiceNo());
        invoice.setSalesOrderId(dto.getSalesOrderId());
        invoice.setSizingOrderId(dto.getSizingOrderId());
        invoice.setYarnSalesOrderId(dto.getYarnSalesOrderId());
        invoice.setCompanyBankId(dto.getCompanyBankId());
        invoice.setTermsConditionsId(dto.getTermsConditionsId());
        invoice.setPaymentTermsId(dto.getPaymentTermsId());
        invoice.setShipToId(dto.getShipToId());
        invoice.setShipmentMode(dto.getShipmentMode());
        invoice.setCustomerId(dto.getCustomerId());
        invoice.setConsgineeId(dto.getConsgineeId());
        invoice.setSoNo(dto.getSoNo());
        invoice.setSjoNo(dto.getSjoNo());
        invoice.setAgentId(dto.getAgentId());
        invoice.setInvoiceTypeId(dto.getInvoiceTypeId());
        invoice.setInvoiceType(dto.getInvoiceType());
        invoice.setWarehouseId(dto.getWarehouseId());
        invoice.setGstType(dto.getGstType());
        invoice.setTaxableAmountTotal(dto.getTaxableAmountTotal());
        invoice.setTaxAmount(dto.getTaxAmount());
        invoice.setAdditionTotal(dto.getAdditionTotal());
        invoice.setDiscountTotal(dto.getDiscountTotal());
        invoice.setNetAdditionDiscount(dto.getNetAdditionDiscount());
        invoice.setRoundOff(dto.getRoundOff());
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setAdditional(dto.getAdditional());
        invoice.setLess(dto.getLess());
        invoice.setComments(dto.getComments());
        invoice.setActiveFlag(dto.getActiveFlag());
        invoice.setShipVia(dto.getShipVia());
        invoice.setCompanyBankName(dto.getCompanyBankName());
        invoice.setShipmentModeName(dto.getShipmentModeName());
        invoice.setTermsConditionsName(dto.getTermsConditionsName());
        invoice.setPaymentTermsName(dto.getPaymentTermsName());
        invoice.setAgentName(dto.getAgentName());
        invoice.setWarehouseName(dto.getWarehouseName());
        invoice.setBuyerStateCode(dto.getBuyerStateCode());
        invoice.setCompanyStateCode(dto.getCompanyStateCode());
        invoice.setGstPercent(dto.getGstPercent());
        invoice.setCgstPercent(dto.getCgstPercent());
        invoice.setSgstPercent(dto.getSgstPercent());
        invoice.setIgstPercent(dto.getIgstPercent());
        invoice.setCompanyId(dto.getCompanyId());
        invoice.setIsJobwork(dto.getIsJobwork());
        invoice.setWeavingWorkOrderId(dto.getWeavingWorkOrderId());
        invoice.setWoNo(dto.getWoNo());
        invoice.setPackingListNo(dto.getPackingListNo());
        invoice.setTransportNameId(dto.getTransportNameId());
        invoice.setDeliveryTermsId(dto.getDeliveryTermsId());
        invoice.setDeliveryNote(dto.getDeliveryNote());
        invoice.setLrNo(dto.getLrNo());
        invoice.setLrDate(dto.getLrDate());
        invoice.setVehicleNo(dto.getVehicleNo());
        invoice.setIsBrushedPiece(dto.getIsBrushedPiece());
        invoice.setDeliveryNoteDate(dto.getDeliveryNoteDate());
        invoice.setDestination(dto.getDestination());
    }

    private List<Long> extractYarnChallanIds(List<InvoiceItemDTO> items) {
        if (items == null) {
            return List.of();
        }
        return items.stream()
                .map(InvoiceItemDTO::getYarnChallanId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private void validatePackingListReferences(List<String> packingListNos) {
        if (packingListNos == null || packingListNos.isEmpty()) {
            return;
        }
        packingListNos.stream()
                .filter(packingListNo -> packingListNo != null && !packingListNo.isBlank())
                .distinct()
                .forEach(this::requirePackingListReference);
    }

    private void requirePackingListReference(String packingListNo) {
        referenceValidationService.requireReference(
                packingListNo,
                () -> generatePackingRepository.findByPackingSlipNo(packingListNo),
                "Packing List",
                packingListNo);
    }

    private void ensureInvoiceNotDispatched(GenerateInvoice invoice) {
        if (!scanDispatchRepository.existsByInvoiceId(invoice.getId())) {
            return;
        }
        String invoiceLabel = invoice.getInvoiceNo();
        if (invoiceLabel == null || invoiceLabel.isBlank()) {
            invoiceLabel = "invoice #" + invoice.getId();
        }
        throw new IllegalStateException("Cannot delete this entry because this " + invoiceLabel + " already scanned and dispatched");
    }

    private Long fetchYarnIdFromChallan(String yarnChallanNo, Long yarnChallanId) {
        try {
            YarnChallan yarnChallan = null;
            if (yarnChallanNo != null && !yarnChallanNo.isEmpty()) {
                yarnChallan = yarnChallanRepository.findByChallanNo(yarnChallanNo);
            }
            
            if (yarnChallan == null && yarnChallanId != null) {
                yarnChallan = yarnChallanRepository.findById(yarnChallanId).orElse(null);
            }

            if (yarnChallan != null && yarnChallan.getItems() != null && !yarnChallan.getItems().isEmpty()) {
                return yarnChallan.getItems().get(0).getYarnId();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}