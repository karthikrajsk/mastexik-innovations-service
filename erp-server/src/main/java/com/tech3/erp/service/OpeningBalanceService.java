package com.tech3.erp.service;
import com.tech3.erp.dto.FabricItemDto;
import com.tech3.erp.dto.ItemGoodsItemDto;
import com.tech3.erp.dto.OpeningBalanceDto;
import com.tech3.erp.dto.OpeningBalanceSummaryItemDto;
import com.tech3.erp.dto.YarnItemDto;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.BaleRollPackingDTO;
import com.tech3.erp.dto.BaleRollPackingItemDTO;
import com.tech3.erp.entity.FabricItemEntity;
import com.tech3.erp.entity.ItemGoodsItemEntity;
import com.tech3.erp.entity.ItemInventory;
import com.tech3.erp.entity.ItemInwardStockDetails;
import com.tech3.erp.entity.OpeningBalanceEntity;
import com.tech3.erp.entity.OpeningBalanceSummaryItemEntity;
import com.tech3.erp.entity.YarnItemEntity;
import com.tech3.erp.entity.YarnInventory;
import com.tech3.erp.entity.YarnInwardStockDetails;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.repository.ItemInventoryRepository;
import com.tech3.erp.repository.OpeningBalanceRepository;
import com.tech3.erp.repository.WarehouseRepository;
import com.tech3.erp.repository.YarnInventoryRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.repository.YarnIssueRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class OpeningBalanceService {

    @Autowired
    private OpeningBalanceRepository openingBalanceRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private YarnInventoryRepository yarnInventoryRepository;

    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    @Autowired
    private YarnIssueRepository yarnIssueRepository;

    @Autowired
    private ItemInventoryRepository itemInventoryRepository;

    @Autowired
    private ApprovalWorkflowService approvalWorkflowService;

    @Autowired
    private FabricInspectionService fabricInspectionService;

    @Autowired
    private BaleRollPackingService baleRollPackingService;

    private static final Long INWARD_TYPE_OPENING_BALANCE = 0L;

    // ================= CREATE =================
    @Transactional
    public OpeningBalanceEntity createOpeningBalance(OpeningBalanceDto dto) {
        try {
            OpeningBalanceEntity ob = new OpeningBalanceEntity();
            ob.setDate(dto.getDate());
            ob.setIsPacked(dto.getIsPacked());
            ob.setGoodsType(dto.getGoodsType());
            ob.setUomId(dto.getUomId());
            ob.setCompanyId(dto.getCompanyId());
            ob.setOpeningBalanceNo(dto.getOpeningBalanceNo());
            applyYarnHeaderDetails(ob, dto);

            ob.setWarehouse(dto.getWarehouseId() != null ?
                    warehouseRepository.findById(dto.getWarehouseId())
                        .orElseThrow(() -> new EntityNotFoundException("Warehouse not found")) : null);

            if (ob.getFabricItems() == null) ob.setFabricItems(new ArrayList<>());
            if (ob.getYarnItems() == null) ob.setYarnItems(new ArrayList<>());
            if (ob.getOpeningBalanceSummaryItems() == null) ob.setOpeningBalanceSummaryItems(new ArrayList<>());
            if (ob.getItemGoodsItems() == null) ob.setItemGoodsItems(new ArrayList<>());

            System.out.println("✅ Creating OpeningBalance - goodsType: " + dto.getGoodsType());
            System.out.println("✅ Yarn items count in DTO: " + (dto.getYarnItems() != null ? dto.getYarnItems().size() : 0));
            System.out.println("✅ Fabric items count in DTO: " + (dto.getFabricItems() != null ? dto.getFabricItems().size() : 0));

            // Save first to get ID
            ob = openingBalanceRepository.save(ob);

            // Add items
            addItemsToOpeningBalance(ob, dto);
            
            // Save and return
            OpeningBalanceEntity savedEntity = openingBalanceRepository.save(ob);
            
            // ✅ Reload warehouse to ensure it's loaded (avoid lazy loading issues)
            if (savedEntity.getWarehouse() != null) {
                Long warehouseId = savedEntity.getWarehouse().getId();
                savedEntity.setWarehouse(warehouseRepository.findById(warehouseId).orElse(null));
                System.out.println("✅ Reloaded warehouse ID: " + warehouseId);
            }
            
            // ✅ Create yarn inventory records if there are yarn items (regardless of main goodsType)
            if (savedEntity.getYarnItems() != null && !savedEntity.getYarnItems().isEmpty()) {
                System.out.println("🎯 Creating yarn inventory for opening balance ID: " + savedEntity.getId() + 
                                 " with " + savedEntity.getYarnItems().size() + " yarn items");
                createYarnInventoryFromOpeningBalance(savedEntity);
            } else {
                System.out.println("⏭️ Skipping yarn inventory creation - no yarn items found for opening balance ID: " + savedEntity.getId());
            }
            
            if (savedEntity.getFabricItems() != null && !savedEntity.getFabricItems().isEmpty()) {
                System.out.println("🎯 Creating piece entries for opening balance ID: " + savedEntity.getId() + 
                                 " with " + savedEntity.getFabricItems().size() + " fabric items");
                createPieceEntriesFromOpeningBalance(savedEntity);

                if (Boolean.TRUE.equals(savedEntity.getIsPacked())) {
                    System.out.println("🎯 Creating bale/roll packing for packed opening balance ID: " + savedEntity.getId());
                    createBaleRollPackingFromOpeningBalance(savedEntity);
                }
            } else {
                System.out.println("⏭️ Skipping piece entry creation - no fabric items found for opening balance ID: " + savedEntity.getId());
            }

            if (savedEntity.getItemGoodsItems() != null && !savedEntity.getItemGoodsItems().isEmpty()) {
                System.out.println("🎯 Creating item inventory for opening balance ID: " + savedEntity.getId() +
                                 " with " + savedEntity.getItemGoodsItems().size() + " item goods");
                createItemInventoryFromOpeningBalance(savedEntity);
            }

            return savedEntity;
            
        } catch (Exception e) {
            System.err.println("❌ Error in createOpeningBalance: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Create piece entry records from opening balance fabric items
     */
    private void createPieceEntriesFromOpeningBalance(OpeningBalanceEntity openingBalance) {
        if (openingBalance.getFabricItems() == null || openingBalance.getFabricItems().isEmpty()) {
            System.out.println("⚠️ No fabric items found in opening balance, skipping piece entry creation");
            return;
        }

        try {
            List<PieceEntry> pieceEntries = new ArrayList<>();
            
            for (FabricItemEntity fabricItem : openingBalance.getFabricItems()) {
                PieceEntry pieceEntry = new PieceEntry();
                
                // Set basic information
                pieceEntry.setPieceNumber(fabricItem.getPieceNo());
                pieceEntry.setQuantity(BigDecimal.valueOf(fabricItem.getPieceMeter()));
                pieceEntry.setWeight(BigDecimal.valueOf(fabricItem.getNetWeight()));
                pieceEntry.setCost(BigDecimal.ZERO); // Set appropriate cost if available
                pieceEntry.setCompanyId(openingBalance.getCompanyId());
                
                // Set fabric information
//                pieceEntry.setFabricId(fabricItem.getFabricCodeId());
                pieceEntry.setFabricMasterId(fabricItem.getFabricCodeId());
                pieceEntry.setFabricId(fabricItem.getFabricCodeId());
                pieceEntry.setFabricTypeId(fabricItem.getFabricTypeId());
                pieceEntry.setRemarks(fabricItem.getQuality());
                
                // Set warehouse from opening balance
                pieceEntry.setWarehouse(openingBalance.getWarehouse());
                
                // Set inspection related fields
                pieceEntry.setIsInspected(fabricItem.getIsInspected() ? 1 : 0);
                pieceEntry.setGradeId(fabricItem.getGradeId());
                pieceEntry.setInspectedMtr(BigDecimal.valueOf(fabricItem.getPieceMeter()));
                pieceEntry.setInspectedNtWt(BigDecimal.valueOf(fabricItem.getNetWeight()));
                pieceEntry.setInspectedAvgWt(BigDecimal.valueOf(fabricItem.getAvgWeight()));
                
                // Set opening balance information
                pieceEntry.setIsOpeningBalance(true);
                pieceEntry.setOpeningBalanceId(openingBalance.getId());
                pieceEntry.setInwardId(openingBalance.getId());
                pieceEntry.setInwardDate(openingBalance.getDate());
                
                // Set source type and PO type for opening balance
                pieceEntry.setSourceType("opening_balance");
                pieceEntry.setPoType("Opening Balance");
                
                // Set inspection status based on isInspected
                pieceEntry.setInspectionStatus(fabricItem.getIsInspected() ? "COMPLETED" : "PENDING");
                
                // Set packing information if available from opening balance
                if (openingBalance.getIsPacked() != null) {
                    pieceEntry.setIsPacked(openingBalance.getIsPacked() ? 1 : 0);
                }
                
                // Set piece prefix and number
                pieceEntry.setPiecePrefix("OB"); // Opening Balance
                
                // Set remarks
                pieceEntry.setRemarks("Created from Opening Balance - " + openingBalance.getId());
                
                pieceEntries.add(pieceEntry);
                
                System.out.println("📦 Creating piece entry for fabric - Piece No: " + fabricItem.getPieceNo() + 
                                 ", Fabric Code: " + fabricItem.getFabricCodeId() + 
                                 ", Quality: " + fabricItem.getQuality());
            }

            // Save all piece entries
            List<PieceEntry> savedPieceEntries = pieceEntryRepository.saveAll(pieceEntries);
            
            for (PieceEntry savedPiece : savedPieceEntries) {
                fabricInspectionService.createFabricInspectionFromPieceEntry(savedPiece);
            }
            
            System.out.println("✅ SUCCESS: Created " + savedPieceEntries.size() + 
                             " piece entries for opening balance ID: " + openingBalance.getId());
                             
        } catch (Exception e) {
            System.err.println("❌ ERROR creating piece entries for opening balance ID: " + openingBalance.getId());
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to ensure transaction rollback
        }
    }

    private void createYarnInventoryFromOpeningBalance(OpeningBalanceEntity openingBalance) {
        if (openingBalance.getYarnItems() == null || openingBalance.getYarnItems().isEmpty()) {
            System.out.println("⚠️ No yarn items found in opening balance, skipping yarn inventory creation");
            return;
        }

        try {
            System.out.println("🔍 DEBUG: Opening Balance ID: " + openingBalance.getId() + 
                             ", Warehouse Object: " + openingBalance.getWarehouse() +
                             ", Warehouse: " + (openingBalance.getWarehouse() != null ? openingBalance.getWarehouse().getId() : "NULL"));
            
            // Create YarnInventory header
            YarnInventory yarnInventory = new YarnInventory();
            
            yarnInventory.setInwardId(openingBalance.getId());
            yarnInventory.setInwardTypeId(INWARD_TYPE_OPENING_BALANCE);
            yarnInventory.setCompanyId(openingBalance.getCompanyId());
            
            // Use opening balance date or current date
            LocalDateTime inwardDateTime = openingBalance.getDate() != null ? 
                    openingBalance.getDate().atStartOfDay() : LocalDateTime.now();
            yarnInventory.setInwardDate(inwardDateTime);
            
            // Calculate financial year
            LocalDate inwardDate = inwardDateTime.toLocalDate();
            String financialYear = calculateFinancialYear(inwardDate);
            yarnInventory.setFinancialYear(financialYear);
            
            // Generate opening balance specific inward number
            yarnInventory.setInwardNo(generateOpeningBalanceInwardNo(openingBalance.getId(), inwardDate));
            
            // Set DC fields (optional for opening balance)
            yarnInventory.setDcNo("OB-" + openingBalance.getId());
            yarnInventory.setDcDate(openingBalance.getDate());
            
            // Set order and vendor info - store openingBalanceId as orderId
            yarnInventory.setOrderId(openingBalance.getId());
            
            // ✅ Use openingBalanceNo if available, otherwise generate one
            String orderNo = (openingBalance.getOpeningBalanceNo() != null && !openingBalance.getOpeningBalanceNo().isEmpty()) 
                    ? openingBalance.getOpeningBalanceNo() 
                    : generateOpeningBalanceOrderNo(openingBalance.getId(), inwardDate);
            yarnInventory.setOrderNo(orderNo);
            
            yarnInventory.setVendorId(null);

            // Create YarnInwardStockDetails for each yarn item
            List<YarnInwardStockDetails> details = new ArrayList<>();
            for (YarnItemEntity yarnItem : openingBalance.getYarnItems()) {
                YarnInwardStockDetails detail = new YarnInwardStockDetails();
                
                detail.setInwardTypeId(INWARD_TYPE_OPENING_BALANCE);
                
                // Map yarn item fields to stock details
                detail.setItemId(yarnItem.getYarnId());
                detail.setOrderItemId(resolveOrderItemId(null));
                detail.setUomId(yarnItem.getUomId());
                detail.setMillId(yarnItem.getMillId());
                detail.setLotNo(yarnItem.getLotNumber());
                detail.setUomId(openingBalance.getUomId());
                detail.setMillInvNo(openingBalance.getMillInvNo());
                detail.setMillDate(openingBalance.getMillDate());

                // Set warehouse from opening balance
                if (openingBalance.getWarehouse() != null) {
                    detail.setWarehouseId(openingBalance.getWarehouse().getId());
                    System.out.println("✅ Setting warehouse ID: " + openingBalance.getWarehouse().getId() + " for yarn item");
                } else {
                    System.out.println("⚠️ WARNING: Warehouse is NULL for opening balance ID: " + openingBalance.getId());
                }
                
                // Convert values to BigDecimal using safe conversion methods
                BigDecimal noOfBags = convertToBigDecimal(yarnItem.getNoOfBags());
                BigDecimal kgsPerBag = convertToBigDecimal(yarnItem.getKgsPerBag());
                BigDecimal totalKg = convertToBigDecimal(yarnItem.getTotalKg());
                
                System.out.println("📦 Processing yarn item - YarnID: " + yarnItem.getYarnId() + 
                                 ", Bags: " + noOfBags + ", KgsPerBag: " + kgsPerBag + ", TotalKg: " + totalKg);
                
                // Set the converted values
                detail.setInwardQty(totalKg);
                detail.setInwardBags(noOfBags);
                detail.setKgsPerBag(kgsPerBag);
                detail.setYarnTypeId(yarnItem.getYarnTypeId());
                detail.setConesPerBag(yarnItem.getConesPerBag());
                detail.setConesWeight(yarnItem.getConesWeight());
                
                // For opening balance, accepted quantity equals inward quantity
                detail.setAcceptedQty(totalKg);
                detail.setRejectedQty(BigDecimal.ZERO);
                
                // Set available quantities (same as inward for opening balance)
                detail.setAvailableQty(totalKg);
                detail.setAvailableBags(noOfBags);
                BigDecimal conesPerBag = yarnItem.getConesPerBag() != null ? yarnItem.getConesPerBag() : BigDecimal.ZERO;
                detail.setAvailableCones(noOfBags.multiply(conesPerBag));
                
                detail.setYarnInventory(yarnInventory);

                details.add(detail);
            }

            yarnInventory.setDetails(details);

            // Save yarn inventory (this will cascade save the details)
            YarnInventory savedInventory = yarnInventoryRepository.save(yarnInventory);
            
            System.out.println("✅ SUCCESS: Created yarn inventory for opening balance ID: " + openingBalance.getId() + 
                             ", Inventory ID: " + savedInventory.getId() + 
                             ", Items count: " + details.size());
                             
        } catch (Exception e) {
            System.err.println("❌ ERROR creating yarn inventory for opening balance ID: " + openingBalance.getId());
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to ensure transaction rollback
        }
    }

    /**
     * Safe conversion of various number types to BigDecimal
     */
    private BigDecimal convertToBigDecimal(Object value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        
        try {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            } else if (value instanceof Integer) {
                return new BigDecimal((Integer) value);
            } else if (value instanceof Double) {
                return BigDecimal.valueOf((Double) value);
            } else if (value instanceof Long) {
                return BigDecimal.valueOf((Long) value);
            } else if (value instanceof String) {
                return new BigDecimal((String) value);
            } else {
                // Try to convert via string representation as fallback
                return new BigDecimal(value.toString());
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error converting value to BigDecimal: " + value + ", error: " + e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    /**
     * Create Bale/Roll Packing records from opening balance fabric items
     * Only runs when opening balance is marked as packed (isPacked = 1)
     * and the corresponding fabric items are marked as inspected (isInspected = 1).
     */
    private void createBaleRollPackingFromOpeningBalance(OpeningBalanceEntity openingBalance) {
        if (openingBalance == null ||
            openingBalance.getFabricItems() == null ||
            openingBalance.getFabricItems().isEmpty()) {
            System.out.println("⚠️ No fabric items found in opening balance, skipping bale/roll packing creation");
            return;
        }

        // Filter only inspected fabric items
        List<FabricItemEntity> inspectedItems = openingBalance.getFabricItems().stream()
                .filter(fi -> Boolean.TRUE.equals(fi.getIsInspected()))
                .collect(Collectors.toList());

        if (inspectedItems.isEmpty()) {
            System.out.println("⚠️ No inspected fabric items found in opening balance, skipping bale/roll packing creation");
            return;
        }

        try {
            BaleRollPackingDTO dto = new BaleRollPackingDTO();

            // Header fields
            dto.setPackingDate(openingBalance.getDate() != null ? java.sql.Date.valueOf(openingBalance.getDate()) : new Date());
            dto.setPackingType("Roll"); // Default packing type for opening balance
            dto.setOrderTypeId(1L);
            dto.setBuyerId(null);
            dto.setWeavingWorkOrderId(null);
            dto.setIsJobwork(0);
            dto.setSalesOrderId(null);

            // Use first inspected item for common fabric info
            FabricItemEntity first = inspectedItems.get(0);
            dto.setFabricCodeId(first.getFabricCodeId());
            dto.setFabricQuality(first.getQuality());
            dto.setUomId(openingBalance.getUomId());
            dto.setWarehouseId(openingBalance.getWarehouse() != null ? openingBalance.getWarehouse().getId() : null);
            dto.setCompanyId(openingBalance.getCompanyId());

            // Grade is mandatory in packing header – take from first item
            dto.setGradeId(first.getGradeId());
            dto.setRollOrBaleNo(first.getPackingNo());

            dto.setRemarks("Created from Opening Balance - " + openingBalance.getId());

            // Totals
            BigDecimal totalMeters = BigDecimal.ZERO;
            BigDecimal totalNet = BigDecimal.ZERO;
            BigDecimal totalAvg = BigDecimal.ZERO;

            List<BaleRollPackingItemDTO> itemDTOs = new ArrayList<>();

            for (FabricItemEntity fabricItem : inspectedItems) {
                BaleRollPackingItemDTO itemDTO = new BaleRollPackingItemDTO();
                itemDTO.setPieceNo(fabricItem.getPieceNo());
                itemDTO.setPieceId(null); // Will be resolved by BaleRollPackingService using pieceNo

                BigDecimal meters = convertToBigDecimal(fabricItem.getPieceMeter());
                BigDecimal net = convertToBigDecimal(fabricItem.getNetWeight());
                BigDecimal avg = convertToBigDecimal(fabricItem.getAvgWeight());

                itemDTO.setMeters(meters);
                itemDTO.setNetWeight(net);
                itemDTO.setAvgWeight(avg);
                BigDecimal yards = meters.multiply(new BigDecimal("1.094")).setScale(3, java.math.RoundingMode.HALF_UP);
                itemDTO.setYards(yards);
                itemDTO.setGrossWeight(null);

                itemDTOs.add(itemDTO);

                totalMeters = totalMeters.add(meters);
                totalNet = totalNet.add(net);
                totalAvg = totalAvg.add(avg);
            }

            dto.setItems(itemDTOs);
            dto.setTotalPieces((long) inspectedItems.size());
            dto.setTotalMeters(totalMeters);
            dto.setTotalNet(totalNet);
            dto.setTotalAvg(totalAvg);
            dto.setTotalYards(null);
            dto.setTotalGross(null);
            dto.setIsFactoryRoll(0);
            dto.setIsDispatched(0);
            dto.setIsRollScanned(0);

            System.out.println("📦 Creating Bale/Roll Packing for Opening Balance ID: " + openingBalance.getId() +
                    " with " + inspectedItems.size() + " inspected fabric items");

            // This will also set isPacked = 1 and packingId on corresponding PieceEntry records
            baleRollPackingService.create(dto);

        } catch (Exception e) {
            System.err.println("❌ ERROR creating bale/roll packing for opening balance ID: " + openingBalance.getId());
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Generate opening balance inward number
     */
    private String generateOpeningBalanceInwardNo(Long openingBalanceId, LocalDate date) {
        String datePart = date.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "OB-" + datePart + "-" + String.format("%04d", openingBalanceId);
    }

    /**
     * Generate opening balance order number
     */
    private String generateOpeningBalanceOrderNo(Long openingBalanceId, LocalDate date) {
        String datePart = date.format(java.time.format.DateTimeFormatter.ofPattern("yy-MM"));
        return "OB/" + datePart + "/" + String.format("%04d", openingBalanceId);
    }

    private Long resolveOrderItemId(Long requestedId) {
        return requestedId != null ? requestedId : yarnInwardStockDetailsRepository.getNextOrderItemIdValue();
    }

    /**
     * Calculate financial year based on the date (Indian FY: Apr-Mar)
     */
    private String calculateFinancialYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        
        int year = date.getYear();
        int month = date.getMonthValue();
        
        // Indian financial year: April to March
        if (month >= 4) {
            int nextYear = year + 1;
            return String.format("%d-%d", year, nextYear % 100);
        } else {
            int previousYear = year - 1;
            return String.format("%d-%d", previousYear, year % 100);
        }
    }

    // ================= HELPER: ADD ITEMS =================
    private void addItemsToOpeningBalance(OpeningBalanceEntity ob, OpeningBalanceDto dto) {
        final OpeningBalanceEntity finalOb = ob;

        // Handle Fabric Items
        if (dto.getFabricItems() != null && !dto.getFabricItems().isEmpty()) {
            List<FabricItemDto> fabricItemDtos = dto.getFabricItems();
            List<FabricItemEntity> fabrics = fabricItemDtos.stream()
                    .map(this::mapFabricDtoToEntity)
                    .collect(Collectors.toList());
            
            // ✅ Set the opening balance relationship for each fabric item
            fabrics.forEach(f -> f.setOpeningBalance(finalOb));
            
            ob.getFabricItems().addAll(fabrics);
            System.out.println("➕ Added " + fabrics.size() + " fabric items to opening balance ID: " + ob.getId());
        }

        // Handle Yarn Items
        if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
            List<YarnItemDto> yarnItemDtos = dto.getYarnItems();
            List<YarnItemEntity> yarns = yarnItemDtos.stream()
                    .map(this::mapYarnDtoToEntity)
                    .collect(Collectors.toList());
            yarns.forEach(y -> {
                y.setOpeningBalance(finalOb);
                y.setOpeningBalanceId(finalOb.getId());
            });
            ob.getYarnItems().addAll(yarns);
            System.out.println("➕ Added " + yarns.size() + " yarn items");
        }

        if (dto.getItemGoods() != null && !dto.getItemGoods().isEmpty()) {
            List<ItemGoodsItemDto> itemGoodsDtos = dto.getItemGoods();
            List<ItemGoodsItemEntity> itemGoodsList = itemGoodsDtos.stream()
                    .map(this::mapItemGoodsDtoToEntity)
                    .collect(Collectors.toList());
            itemGoodsList.forEach(ig -> {
                ig.setOpeningBalance(finalOb);
                ig.setOpeningBalanceId(finalOb.getId());
            });
            if (ob.getItemGoodsItems() == null) ob.setItemGoodsItems(new ArrayList<>());
            ob.getItemGoodsItems().addAll(itemGoodsList);
            System.out.println("➕ Added " + itemGoodsList.size() + " item goods to opening balance ID: " + ob.getId());
        }

        if (dto.getOpeningBalanceSummaryItems() != null && !dto.getOpeningBalanceSummaryItems().isEmpty()) {
            List<OpeningBalanceSummaryItemDto> summaryDtos = dto.getOpeningBalanceSummaryItems();
            List<OpeningBalanceSummaryItemEntity> summaries = summaryDtos.stream()
                    .map(this::mapSummaryDtoToEntity)
                    .collect(Collectors.toList());
            summaries.forEach(s -> s.setOpeningBalance(finalOb));
            ob.getOpeningBalanceSummaryItems().addAll(summaries);
            System.out.println("➕ Added " + summaries.size() + " opening balance summary items");
        }
    }

    // ================= GET =================
    public OpeningBalanceEntity getOpeningBalance(Long id) {
        return openingBalanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OpeningBalance not found with id: " + id));
    }

    public List<OpeningBalanceEntity> getAllOpeningBalances() {
        return openingBalanceRepository.findAll();
    }

    // ================= PAGINATION =================
    public PaginatedResponse<OpeningBalanceDto> getAllOpeningBalancesWithPagination(Long offset, Long limit) {
        List<OpeningBalanceEntity> all = getAllOpeningBalances();
        PaginatedResponse<OpeningBalanceEntity> paginated = PaginationHelper.paginate(all, offset, limit);

        List<OpeningBalanceDto> dtoData = paginated.getData().stream()
                .map(OpeningBalanceDto::new)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                paginated.getTotal(),
                paginated.isNext(),
                paginated.isPrev(),
                paginated.getOffset(),
                paginated.getLimit(),
                dtoData
        );
    }

    public PaginatedResponse<OpeningBalanceDto> getAllOpeningBalancesWithPaginationByCompany(Long companyId, Long offset, Long limit) {
        List<OpeningBalanceEntity> all = openingBalanceRepository.findAllByCompanyId(companyId);
        PaginatedResponse<OpeningBalanceEntity> paginated = PaginationHelper.paginate(all, offset, limit);

        List<OpeningBalanceDto> dtoData = paginated.getData().stream()
                .map(OpeningBalanceDto::new)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                paginated.getTotal(),
                paginated.isNext(),
                paginated.isPrev(),
                paginated.getOffset(),
                paginated.getLimit(),
                dtoData
        );
    }

    // ================= UPDATE =================
    @Transactional
    public OpeningBalanceEntity updateOpeningBalance(Long id, OpeningBalanceDto dto) {
        OpeningBalanceEntity ob = openingBalanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OpeningBalance not found with id: " + id));

        // Update basic fields
        ob.setDate(dto.getDate());
        ob.setIsPacked(dto.getIsPacked());
        ob.setGoodsType(dto.getGoodsType());
        ob.setUomId(dto.getUomId());
        ob.setCompanyId(dto.getCompanyId());
        ob.setOpeningBalanceNo(dto.getOpeningBalanceNo());
        applyYarnHeaderDetails(ob, dto);
        ob.setWarehouse(dto.getWarehouseId() != null ?
                warehouseRepository.findById(dto.getWarehouseId()).orElse(null) : null);

        ob.getFabricItems().clear();
        ob.getYarnItems().clear();
        ob.getOpeningBalanceSummaryItems().clear();
        if (ob.getItemGoodsItems() == null) ob.setItemGoodsItems(new ArrayList<>());
        ob.getItemGoodsItems().clear();
        addItemsToOpeningBalance(ob, dto);

         OpeningBalanceEntity updatedEntity = openingBalanceRepository.save(ob);

        // Update yarn inventory if there are yarn items
        if (updatedEntity.getYarnItems() != null && !updatedEntity.getYarnItems().isEmpty()) {
            updateYarnInventoryForOpeningBalance(id, updatedEntity);
        } else {
            deleteYarnInventoryForOpeningBalance(id, updatedEntity.getOpeningBalanceNo());
        }


        if (updatedEntity.getFabricItems() != null && !updatedEntity.getFabricItems().isEmpty()) {
            deletePieceEntriesForOpeningBalance(id);
            createPieceEntriesFromOpeningBalance(updatedEntity);

            if (Boolean.TRUE.equals(updatedEntity.getIsPacked())) {
                System.out.println("🎯 (Update) Creating bale/roll packing for packed opening balance ID: " + updatedEntity.getId());
                createBaleRollPackingFromOpeningBalance(updatedEntity);
            }
        } else {
            deletePieceEntriesForOpeningBalance(id);
        }

        if (updatedEntity.getItemGoodsItems() != null && !updatedEntity.getItemGoodsItems().isEmpty()) {
            deleteItemInventoryForOpeningBalance(id);
            createItemInventoryFromOpeningBalance(updatedEntity);
        } else {
            deleteItemInventoryForOpeningBalance(id);
        }

        return updatedEntity;
    }

    private void applyYarnHeaderDetails(OpeningBalanceEntity openingBalance, OpeningBalanceDto dto) {
        if ("Yarn".equalsIgnoreCase(dto.getGoodsType())) {
            openingBalance.setVendorId(dto.getVendorId());
            openingBalance.setDcNumber(dto.getDcNumber());
            openingBalance.setDcDate(dto.getDcDate());
            openingBalance.setMillInvNo(dto.getMillInvNo());
            openingBalance.setMillDate(dto.getMillDate());
            openingBalance.setIcilInvNo(dto.getIcilInvNo());
            openingBalance.setIcilInvDate(dto.getIcilInvDate());
            return;
        }

        openingBalance.setVendorId(null);
        openingBalance.setDcNumber(null);
        openingBalance.setDcDate(null);
        openingBalance.setMillInvNo(null);
        openingBalance.setMillDate(null);
        openingBalance.setIcilInvNo(null);
        openingBalance.setIcilInvDate(null);
    }

    public OpeningBalanceEntity updateOpeningBalanceByCompany(Long companyId, Long id, OpeningBalanceDto dto) {
        return updateOpeningBalance(id, dto);
    }

      private void updateYarnInventoryForOpeningBalance(Long openingBalanceId, OpeningBalanceEntity openingBalance) {
        try {
            // Find inventories by order_id AND inward_type_id for reliable updating (ONLY Opening Balance records)
            List<YarnInventory> inventories = yarnInventoryRepository.findByInwardIdAndInwardTypeId(openingBalanceId, INWARD_TYPE_OPENING_BALANCE);
            
            if (inventories.isEmpty() && openingBalance.getOpeningBalanceNo() != null) {
                inventories = yarnInventoryRepository.findByInwardNoAndCompanyIdAndInwardTypeId(
                    openingBalance.getOpeningBalanceNo(), openingBalance.getCompanyId(), INWARD_TYPE_OPENING_BALANCE);
            }

            // Fallback for older records - ONLY Opening Balance records
            if (inventories.isEmpty()) {
                List<YarnInventory> legacyInventories = yarnInventoryRepository.findByInwardNoStartingWith("OB-");
                String expectedSuffix = String.format("%04d", openingBalanceId);
                for (YarnInventory inventory : legacyInventories) {
                    if (inventory.getInwardNo().endsWith("-" + expectedSuffix) && 
                        INWARD_TYPE_OPENING_BALANCE.equals(inventory.getInwardTypeId())) {
                        inventories.add(inventory);
                    }
                }
            }
            
            // If no existing inventories found, create new ones
            if (inventories.isEmpty()) {
                System.out.println("No existing yarn inventory found for opening balance ID: " + openingBalanceId + 
                                 ", creating new inventory records");
                createYarnInventoryFromOpeningBalance(openingBalance);
                return;
            }
            
            for (YarnInventory inventory : inventories) {
                // Ensure order_id and order_no are updated if they were missing
                inventory.setOrderId(openingBalanceId);
                if (openingBalance.getOpeningBalanceNo() != null) {
                    inventory.setOrderNo(openingBalance.getOpeningBalanceNo());
                }
                
                inventory.setInwardDate(openingBalance.getDate() != null ? 
                        openingBalance.getDate().atStartOfDay() : LocalDateTime.now());
                    
                List<YarnInwardStockDetails> existingDetails = inventory.getDetails();
                if (existingDetails == null) {
                    existingDetails = new ArrayList<>();
                    inventory.setDetails(existingDetails);
                }
                
                // Create a map of existing details by yarnId for efficient lookup
                Map<Long, YarnInwardStockDetails> existingDetailsMap = new HashMap<>();
                for (YarnInwardStockDetails detail : existingDetails) {
                    if (detail.getItemId() != null) {
                        existingDetailsMap.put(detail.getItemId(), detail);
                    }
                }
                
                List<YarnInwardStockDetails> detailsToUpdate = new ArrayList<>();
                List<YarnInwardStockDetails> detailsToDelete = new ArrayList<>();
                
                // Process each yarn item from opening balance
                for (YarnItemEntity yarnItem : openingBalance.getYarnItems()) {
                    YarnInwardStockDetails detail;
                    boolean isNewRecord = false;
                    
                    // Try to find existing detail by yarnId
                    detail = existingDetailsMap.get(yarnItem.getYarnId());
                    
                    if (detail == null) {
                        // Create new detail if not found
                        detail = new YarnInwardStockDetails();
                        Long nextOrderItemId = yarnInwardStockDetailsRepository.getNextOrderItemIdValue();
                        detail.setOrderItemId(nextOrderItemId);
                        detail.setYarnInventory(inventory);
                        detail.setInwardTypeId(INWARD_TYPE_OPENING_BALANCE);
                        isNewRecord = true;
                        detailsToUpdate.add(detail);
                    } else {
                        // Use existing detail
                        detailsToUpdate.add(detail);
                        // Remove from map to track which ones are used
                        existingDetailsMap.remove(yarnItem.getYarnId());
                    }
                    
                    // Only set inwardTypeId for new records - existing records should already be correct
                    if (isNewRecord) {
                        detail.setInwardTypeId(INWARD_TYPE_OPENING_BALANCE);
                    } else {
                        // Verify existing record has correct inwardTypeId
                        if (!INWARD_TYPE_OPENING_BALANCE.equals(detail.getInwardTypeId())) {
                            System.err.println("⚠️ WARNING: Found non-opening balance record (inwardTypeId: " + 
                                             detail.getInwardTypeId() + ") in opening balance update - skipping");
                            continue; // Skip this record as it shouldn't be here
                        }
                    }
                    detail.setItemId(yarnItem.getYarnId());
//                        detail.setUomId(yarnItem.getUomId());
                    detail.setMillId(yarnItem.getMillId());
                    detail.setLotNo(yarnItem.getLotNumber());
                    detail.setMillInvNo(openingBalance.getMillInvNo());
                    detail.setMillDate(openingBalance.getMillDate());
                    
                    if (openingBalance.getWarehouse() != null) {
                        detail.setWarehouseId(openingBalance.getWarehouse().getId());
                    }
                    
                    BigDecimal noOfBags = convertToBigDecimal(yarnItem.getNoOfBags());
                    BigDecimal kgsPerBag = convertToBigDecimal(yarnItem.getKgsPerBag());
                    BigDecimal totalKg = convertToBigDecimal(yarnItem.getTotalKg());
                    
                    detail.setInwardQty(totalKg);
                    detail.setInwardBags(noOfBags);
                    detail.setKgsPerBag(kgsPerBag);
                    detail.setYarnTypeId(yarnItem.getYarnTypeId());
                    detail.setConesPerBag(yarnItem.getConesPerBag());
                    detail.setConesWeight(yarnItem.getConesWeight());
                    detail.setAcceptedQty(totalKg);
                    detail.setRejectedQty(BigDecimal.ZERO);

                    if (isNewRecord) {
                        detail.setAvailableQty(totalKg);
                        detail.setAvailableBags(noOfBags);
                    }
                    BigDecimal availableBags = detail.getAvailableBags() != null
                        ? detail.getAvailableBags()
                        : noOfBags;
                    BigDecimal conesPerBag = yarnItem.getConesPerBag() != null
                        ? yarnItem.getConesPerBag()
                        : BigDecimal.ZERO;
                    detail.setAvailableCones(availableBags.multiply(conesPerBag));
                }
                
                // Any remaining details in the map are no longer needed and should be deleted
                detailsToDelete.addAll(existingDetailsMap.values());
                
                if (!detailsToDelete.isEmpty()) {
                    yarnInwardStockDetailsRepository.deleteAll(detailsToDelete);
                    System.out.println("Deleted " + detailsToDelete.size() + " unused yarn stock details");
                }
                
                // Update the inventory's details list with the updated details
                inventory.setDetails(detailsToUpdate);
                
                yarnInventoryRepository.save(inventory);
                System.out.println("Updated yarn inventory for opening balance ID: " + openingBalanceId + 
                                 ", Updated items count: " + detailsToUpdate.size() + 
                                 ", Deleted items count: " + detailsToDelete.size());
            }
        } catch (Exception e) {
            System.err.println("❌ ERROR updating yarn inventory for opening balance ID: " + openingBalanceId);
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Delete yarn inventory records associated with an opening balance
     */
    private void deleteYarnInventoryForOpeningBalance(Long openingBalanceId, String orderNo) {
        try {
            // Find by order_id AND inward_type_id (ONLY Opening Balance records)
            List<YarnInventory> inventories = yarnInventoryRepository.findByInwardIdAndInwardTypeId(openingBalanceId, INWARD_TYPE_OPENING_BALANCE);
            
            // Also find by order_no AND inward_type_id if provided
            if (orderNo != null && !orderNo.isEmpty()) {
                List<YarnInventory> byOrderNo = yarnInventoryRepository.findByInwardNoAndCompanyIdAndInwardTypeId(
                    orderNo, null, INWARD_TYPE_OPENING_BALANCE);
                for (YarnInventory inv : byOrderNo) {
                    if (inventories.stream().noneMatch(i -> i.getId().equals(inv.getId()))) {
                        inventories.add(inv);
                    }
                }
            }

            if (inventories.isEmpty()) {
                // Fallback to legacy inward_no pattern matching - ONLY Opening Balance records
                List<YarnInventory> legacyInventories = yarnInventoryRepository.findByInwardNoStartingWith("OB-");
                for (YarnInventory inventory : legacyInventories) {
                    if (inventory.getInwardNo().contains("-" + openingBalanceId) && 
                        INWARD_TYPE_OPENING_BALANCE.equals(inventory.getInwardTypeId())) {
                        inventories.add(inventory);
                    }
                }
            }

            for (YarnInventory inventory : inventories) {
                // Verify this is actually an opening balance record before deleting
                if (!INWARD_TYPE_OPENING_BALANCE.equals(inventory.getInwardTypeId())) {
                    System.err.println("WARNING: Skipping non-opening balance record (inwardTypeId: " + 
                                     inventory.getInwardTypeId() + ") during deletion");
                    continue;
                }
                
                // Delete the details first (explicitly to be safe)
                if (inventory.getDetails() != null) {
                    yarnInwardStockDetailsRepository.deleteAll(inventory.getDetails());
                }
                // Then delete the inventory
                yarnInventoryRepository.delete(inventory);
                System.out.println("Deleted yarn inventory for order/id: " + (orderNo != null ? orderNo : openingBalanceId) + 
                                 " (inwardTypeId: " + inventory.getInwardTypeId() + ")");
            }
        } catch (Exception e) {
            System.err.println("Error deleting yarn inventory for opening balance: " + e.getMessage());
        }
    }

    /**
     * Delete piece entry records associated with an opening balance
     */
 private void deletePieceEntriesForOpeningBalance(Long openingBalanceId) {
        try {
            List<PieceEntry> pieceEntries = pieceEntryRepository.findByOpeningBalanceId(openingBalanceId);
            if (!pieceEntries.isEmpty()) {
                pieceEntryRepository.deleteAll(pieceEntries);
                System.out.println("🗑️ Deleted " + pieceEntries.size() + " piece entries for opening balance ID: " + openingBalanceId);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error deleting piece entries for opening balance: " + e.getMessage());
        }
    }
    private void createItemInventoryFromOpeningBalance(OpeningBalanceEntity openingBalance) {
        if (openingBalance.getItemGoodsItems() == null || openingBalance.getItemGoodsItems().isEmpty()) {
            return;
        }

        try {
            ItemInventory itemInventory = new ItemInventory();
            itemInventory.setInwardId(openingBalance.getId());
            itemInventory.setCompanyId(openingBalance.getCompanyId());

            LocalDateTime inwardDateTime = openingBalance.getDate() != null ?
                    openingBalance.getDate().atStartOfDay() : LocalDateTime.now();
            itemInventory.setInwardDate(inwardDateTime);

            LocalDate inwardDate = inwardDateTime.toLocalDate();
            itemInventory.setFinancialYear(calculateFinancialYear(inwardDate));
            itemInventory.setInwardNo(generateOpeningBalanceInwardNo(openingBalance.getId(), inwardDate));
            itemInventory.setDcNo("OB-" + openingBalance.getId());
            itemInventory.setDcDate(openingBalance.getDate());
            itemInventory.setOrderId(openingBalance.getId());

            String orderNo = (openingBalance.getOpeningBalanceNo() != null && !openingBalance.getOpeningBalanceNo().isEmpty())
                    ? openingBalance.getOpeningBalanceNo()
                    : generateOpeningBalanceOrderNo(openingBalance.getId(), inwardDate);
            itemInventory.setOrderNo(orderNo);
            itemInventory.setVendorId(null);

            List<ItemInwardStockDetails> details = new ArrayList<>();
            for (ItemGoodsItemEntity itemGoods : openingBalance.getItemGoodsItems()) {
                ItemInwardStockDetails detail = new ItemInwardStockDetails();
                detail.setItemId(itemGoods.getItemId());
                detail.setUomId(openingBalance.getUomId());
                detail.setLotNo(itemGoods.getLotNo());

                if (openingBalance.getWarehouse() != null) {
                    detail.setWarehouseId(openingBalance.getWarehouse().getId());
                }

                BigDecimal noOfBags = convertToBigDecimal(itemGoods.getNoOfBags());
                BigDecimal kgsPerBag = convertToBigDecimal(itemGoods.getKgsPerBag());
                BigDecimal acceptedQty = itemGoods.getAcceptedQty() != null ? itemGoods.getAcceptedQty() : BigDecimal.ZERO;

                detail.setInwardQty(acceptedQty);
                detail.setInwardBags(noOfBags);
                detail.setKgsPerBag(kgsPerBag);
                detail.setAcceptedQty(acceptedQty);
                detail.setRejectedQty(BigDecimal.ZERO);
                detail.setAvailableQty(acceptedQty);
                detail.setAvailableBags(noOfBags);
                detail.setItemInventory(itemInventory);

                details.add(detail);
            }

            itemInventory.setDetails(details);
            ItemInventory saved = itemInventoryRepository.save(itemInventory);
            System.out.println("✅ Created item inventory for opening balance ID: " + openingBalance.getId() +
                             ", Inventory ID: " + saved.getId() + ", Items: " + details.size());

        } catch (Exception e) {
            System.err.println("❌ ERROR creating item inventory for opening balance ID: " + openingBalance.getId());
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void deleteItemInventoryForOpeningBalance(Long openingBalanceId) {
        try {
            List<ItemInventory> inventories = itemInventoryRepository.findByInwardId(openingBalanceId);
            if (inventories.isEmpty()) {
                inventories = itemInventoryRepository.findByOrderId(openingBalanceId);
            }
            for (ItemInventory inventory : inventories) {
                itemInventoryRepository.delete(inventory);
                System.out.println("🗑️ Deleted item inventory ID: " + inventory.getId() +
                                 " for opening balance ID: " + openingBalanceId);
            }
        } catch (Exception e) {
            System.err.println("⚠️ Error deleting item inventory for opening balance: " + e.getMessage());
        }
    }

    // ================= DELETE =================
    @Transactional
    public void deleteOpeningBalance(Long id) {
        OpeningBalanceEntity ob = openingBalanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OpeningBalance not found with id: " + id));
        
        // Validation: Check if fabric pieces are used
        List<PieceEntry> usedPieces = pieceEntryRepository.findUsedPiecesByOpeningBalanceId(id);
        if (!usedPieces.isEmpty()) {
            String pieceNumbers = usedPieces.stream()
                    .map(PieceEntry::getPieceNumber)
                    .collect(Collectors.joining(", "));
            throw new IllegalStateException("Cannot delete: The following pieces are already inspected, packed, or invoiced: " + pieceNumbers);
        }

        deleteYarnInventoryForOpeningBalance(id, ob.getOpeningBalanceNo());
        deletePieceEntriesForOpeningBalance(id);
        deleteItemInventoryForOpeningBalance(id);

        try {
            openingBalanceRepository.delete(ob);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete OpeningBalance as it is referenced elsewhere");
        }
    }

    

    public void deleteOpeningBalanceByCompany(Long companyId, Long id) {
        deleteOpeningBalance(id);
    }

    // ================= HELPER: MAP Fabric DTO =================
    private FabricItemEntity mapFabricDtoToEntity(FabricItemDto dto) {
        FabricItemEntity item = new FabricItemEntity();
        item.setsNo(dto.getSNo());
        item.setFabricCodeId(dto.getFabricCodeId()); // Foreign key ID
        item.setFabricCode(dto.getFabricCode());
        item.setFabricTypeId(dto.getFabricTypeId());
        item.setQuality(dto.getQuality());
        item.setPackingNo(dto.getPackingNo());
        item.setPieceNo(dto.getPieceNo());
        item.setPieceMeter(dto.getPieceMeter());
        item.setNetWeight(dto.getNetWeight());
        item.setAvgWeight(dto.getAvgWeight());
        item.setGradeId(dto.getGrade()); // Foreign key ID
        item.setIsInspected(dto.getIsInspected());
        
        // ✅ The openingBalance relationship will be set in addItemsToOpeningBalance method
        // This ensures the fabric item is linked to the opening balance
        
        return item;
    }
    @Transactional
    public void hardDeleteOpeningBalance(Long id) {
        OpeningBalanceEntity entity = openingBalanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OpeningBalance not found with id: " + id));
        
        // Validation: Check if fabric pieces are used
        List<PieceEntry> usedPieces = pieceEntryRepository.findUsedPiecesByOpeningBalanceId(id);
        if (!usedPieces.isEmpty()) {
            String pieceNumbers = usedPieces.stream()
                    .map(PieceEntry::getPieceNumber)
                    .collect(Collectors.joining(", "));
            throw new IllegalStateException("Cannot permanently delete: The following pieces are already inspected, packed, or invoiced: " + pieceNumbers);
        }

        // Validation: Check if yarn is issued
        List<YarnInventory> inventories = yarnInventoryRepository.findByOrderId(id);
        if (inventories.isEmpty() && entity.getOpeningBalanceNo() != null) {
            inventories = yarnInventoryRepository.findByOrderNo(entity.getOpeningBalanceNo());
        }

        // Fallback for legacy records
        if (inventories.isEmpty()) {
            List<YarnInventory> legacyInventories = yarnInventoryRepository.findByInwardNoStartingWith("OB-");
            String expectedSuffix = String.format("%04d", id);
            for (YarnInventory inventory : legacyInventories) {
                if (inventory.getInwardNo().endsWith("-" + expectedSuffix)) {
                    inventories.add(inventory);
                }
            }
        }

        for (YarnInventory inventory : inventories) {
            List<YarnInwardStockDetails> details = inventory.getDetails();
            if (details != null) {
                for (YarnInwardStockDetails detail : details) {
                    if (detail.getOrderItemId() != null) {
                        List<com.tech3.erp.entity.YarnIssue> issues = yarnIssueRepository.findByOrderItemId(detail.getOrderItemId());
                        if (!issues.isEmpty()) {
                            throw new IllegalStateException("Cannot permanently delete: Yarn '" + issues.get(0).getYarnName() + "' has already been issued.");
                        }
                    }
                }
            }
        }

        deleteYarnInventoryForOpeningBalance(id, entity.getOpeningBalanceNo());
        deletePieceEntriesForOpeningBalance(id);
        deleteItemInventoryForOpeningBalance(id);
        
//        // Delete Approval entries
//        if (approvalWorkflowService != null) {
//            approvalWorkflowService.deleteApprovalsForTransaction(id, "OPENING_BALANCE");
//        }

        try {
            openingBalanceRepository.delete(entity);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete OpeningBalance as it is referenced elsewhere");
        }
    }

    // ================= HELPER: MAP Yarn DTO =================
    private YarnItemEntity mapYarnDtoToEntity(YarnItemDto dto) {
        YarnItemEntity item = new YarnItemEntity();
        item.setSNo(dto.getSNo());       // ✅ capital S
        item.setYarnId(dto.getYarnId()); // Foreign key ID	
        item.setMillId(dto.getMillId()); // Foreign key ID
        
        // Convert to appropriate types based on your entity
        item.setNoOfBags(dto.getNoOfBags()); // This should be Integer
        item.setKgsPerBag(dto.getKgsPerBag()); // This should be Double or BigDecimal
        item.setTotalKg(dto.getTotalKg()); // This should be Double or BigDecimal
        
        item.setLotNumber(dto.getLotNumber());
        item.setUomId(dto.getUomId());
        item.setYarnTypeId(dto.getYarnTypeId());
        item.setConesPerBag(dto.getConesPerBag());
        item.setConesWeight(dto.getConesWeight());
        return item;
    }

    private OpeningBalanceSummaryItemEntity mapSummaryDtoToEntity(OpeningBalanceSummaryItemDto dto) {
        OpeningBalanceSummaryItemEntity item = new OpeningBalanceSummaryItemEntity();
        item.setItemName(dto.getItemName());
        item.setQuantity(dto.getQuantity());
        item.setNoOfPieces(dto.getNoOfPieces());
        item.setNoOfBags(dto.getNoOfBags());
        return item;
    }

    private ItemGoodsItemEntity mapItemGoodsDtoToEntity(ItemGoodsItemDto dto) {
        ItemGoodsItemEntity item = new ItemGoodsItemEntity();
        item.setSNo(dto.getSNo());
        item.setItemId(dto.getItemId());
        item.setItemName(dto.getItemName());
        item.setNoOfBags(dto.getNoOfBags());
        item.setKgsPerBag(dto.getKgsPerBag());
        item.setAcceptedQty(dto.getAcceptedQty());
        item.setLotNo(dto.getLotNo());
        return item;
    }
}