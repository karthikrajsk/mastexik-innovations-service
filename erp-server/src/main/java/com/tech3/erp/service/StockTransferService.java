
package com.tech3.erp.service;

import com.tech3.erp.dto.StockTransferDTO;
import com.tech3.erp.dto.StockFabricTransferDTO;
import com.tech3.erp.dto.StockYarnTransferDTO;
import com.tech3.erp.dto.StockItemTransferDTO;
import com.tech3.erp.dto.YarnInwardStockDetailsDto;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.*;
import com.tech3.erp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class StockTransferService {

    @Autowired
    private StockTransferRepository stockTransferRepository;
    
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    @Autowired
    private BaleRollPackingItemRepository baleRollPackingItemRepository;

    @Autowired
    private WovenFabricMasterRepository wovenFabricMasterRepository;

    @Autowired
    private KnittedFabricMasterRepository knittedFabricMasterRepository;

    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;
    
    @Autowired
    private YarnMasterRepository yarnMasterRepository;

    @Autowired
    private YarnInventoryRepository yarnInventoryRepository;
    
    @Autowired
    private YarnOutwardStockDetailsRepository yarnOutwardStockDetailsRepository;
    
    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @Autowired
    private ItemInventoryRepository itemInventoryRepository;

    @Autowired
    private ItemInwardStockDetailsRepository itemInwardStockDetailsRepository;

    @Autowired
    private PurchaseInwardRepository purchaseInwardRepository;


    public StockTransferDTO createStockTransfer(StockTransferDTO stockTransferDTO) {
        if (stockTransferDTO.getFromGodownId().equals(stockTransferDTO.getToGodownId())) {
            throw new IllegalArgumentException("From godown and To godown cannot be the same");
        }
        
        StockTransferEntity stockTransfer = convertToEntity(stockTransferDTO);

        if (stockTransfer.getFabricItems() != null) {
            for (StockFabricTransferEntity item : stockTransfer.getFabricItems()) {
                item.setStockTransfer(stockTransfer);
            }
        }

        if (stockTransfer.getYarnItems() != null) {
            for (StockYarnTransferEntity item : stockTransfer.getYarnItems()) {
                item.setStockTransfer(stockTransfer);
            }
        }

        if (stockTransfer.getItemItems() != null) {
            for (StockItemTransferEntity item : stockTransfer.getItemItems()) {
                item.setStockTransfer(stockTransfer);
            }
        }

        StockTransferEntity savedTransfer = stockTransferRepository.save(stockTransfer);

        if ("Fabric".equalsIgnoreCase(stockTransferDTO.getGoodsType()) &&
            stockTransferDTO.getFabricItems() != null &&
            !stockTransferDTO.getFabricItems().isEmpty()) {

            updateRelatedEntities(savedTransfer, stockTransferDTO.getFabricItems());
        }

        if ("Yarn".equalsIgnoreCase(stockTransferDTO.getGoodsType()) &&
            stockTransferDTO.getYarnItems() != null &&
            !stockTransferDTO.getYarnItems().isEmpty()) {

            updateYarnRelatedEntities(savedTransfer, savedTransfer.getYarnItems());
        }

        if ("Item".equalsIgnoreCase(stockTransferDTO.getGoodsType()) &&
            savedTransfer.getItemItems() != null &&
            !savedTransfer.getItemItems().isEmpty()) {

            updateItemRelatedEntities(savedTransfer, savedTransfer.getItemItems());
        }

        return convertToDTO(savedTransfer);
    }

    public StockTransferDTO updateStockTransfer(Long id, StockTransferDTO stockTransferDTO) {
        StockTransferEntity existingTransfer = stockTransferRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Stock transfer not found with id: " + id));
        
        // Revert old inventory effects before updating fields
        if ("Yarn".equalsIgnoreCase(existingTransfer.getGoodsType())) {
            revertYarnRelatedEntities(existingTransfer);
        } else if ("Fabric".equalsIgnoreCase(existingTransfer.getGoodsType())) {
            removeStockTransferReferences(existingTransfer);
        } else if ("Item".equalsIgnoreCase(existingTransfer.getGoodsType())) {
            revertItemRelatedEntities(existingTransfer);
        }

        if (stockTransferDTO.getFromGodownId() != null && 
            stockTransferDTO.getToGodownId() != null &&
            stockTransferDTO.getFromGodownId().equals(stockTransferDTO.getToGodownId())) {
            throw new IllegalArgumentException("From godown and To godown cannot be the same");
        }
        
        if (stockTransferDTO.getDate() != null) {
            existingTransfer.setDate(stockTransferDTO.getDate());
        }
        if (stockTransferDTO.getGoodsType() != null) {
            existingTransfer.setGoodsType(stockTransferDTO.getGoodsType());
        }
        if (stockTransferDTO.getItemId() != null) {
            existingTransfer.setItemId(stockTransferDTO.getItemId());
            String itemName = fetchItemName(stockTransferDTO.getGoodsType(), stockTransferDTO.getItemId());
            existingTransfer.setItemName(itemName);
        } else if (stockTransferDTO.getItemName() != null) {
            existingTransfer.setItemName(stockTransferDTO.getItemName());
        }
        if (stockTransferDTO.getQuantity() != null) {
            existingTransfer.setQuantity(stockTransferDTO.getQuantity());
        }
        
        if (stockTransferDTO.getStockTransferNo() != null) {
            existingTransfer.setStockTransferNo(stockTransferDTO.getStockTransferNo());
        }
        
        if (stockTransferDTO.getFromGodownId() != null) {
            Warehouse fromGodown = warehouseRepository.findById(stockTransferDTO.getFromGodownId())
                    .orElseThrow(() -> new RuntimeException("From godown not found with id: " + stockTransferDTO.getFromGodownId()));
            existingTransfer.setFromGodown(fromGodown);
        }
        
        if (stockTransferDTO.getToGodownId() != null) {
            Warehouse toGodown = warehouseRepository.findById(stockTransferDTO.getToGodownId())
                    .orElseThrow(() -> new RuntimeException("To godown not found with id: " + stockTransferDTO.getToGodownId()));
            existingTransfer.setToGodown(toGodown);
        }
        
        if (stockTransferDTO.getFabricItems() != null) {
            updateFabricItems(existingTransfer, stockTransferDTO.getFabricItems());
        }

        if (stockTransferDTO.getYarnItems() != null) {
            updateYarnItems(existingTransfer, stockTransferDTO.getYarnItems());
        }

        if (stockTransferDTO.getItemItems() != null) {
            updateItemItems(existingTransfer, stockTransferDTO.getItemItems());
        }

        StockTransferEntity updatedTransfer = stockTransferRepository.save(existingTransfer);
        
        if ("Fabric".equalsIgnoreCase(stockTransferDTO.getGoodsType()) && 
            stockTransferDTO.getFabricItems() != null && 
            !stockTransferDTO.getFabricItems().isEmpty()) {
            
            updateRelatedEntities(updatedTransfer, stockTransferDTO.getFabricItems());
        }

        if ("Yarn".equalsIgnoreCase(stockTransferDTO.getGoodsType()) && 
            stockTransferDTO.getYarnItems() != null && 
            !stockTransferDTO.getYarnItems().isEmpty()) {
            
            updateYarnRelatedEntities(updatedTransfer, updatedTransfer.getYarnItems());
        }

        if ("Item".equalsIgnoreCase(stockTransferDTO.getGoodsType()) && 
            updatedTransfer.getItemItems() != null && 
            !updatedTransfer.getItemItems().isEmpty()) {
            
            updateItemRelatedEntities(updatedTransfer, updatedTransfer.getItemItems());
        }
        
        return convertToDTO(updatedTransfer);
    }

    @Transactional(readOnly = true)
    public List<StockTransferDTO> getAllStockTransfers() {
        List<StockTransferEntity> transfers = stockTransferRepository.findAllWithDetails();
        
        for (StockTransferEntity transfer : transfers) {
            transfer.getYarnItems().size();
        }
        
        return transfers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StockTransferDTO getStockTransferById(Long id) {
        StockTransferEntity stockTransfer = stockTransferRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Stock transfer not found with id: " + id));
        
        stockTransfer.getYarnItems().size();
        
        return convertToDTO(stockTransfer);
    }

    public void deleteStockTransfer(Long id) {
        StockTransferEntity stockTransfer = stockTransferRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Stock transfer not found with id: " + id));
        
        if (stockTransfer != null) {
            if ("Yarn".equalsIgnoreCase(stockTransfer.getGoodsType())) {
                revertYarnRelatedEntities(stockTransfer);
            } else if ("Fabric".equalsIgnoreCase(stockTransfer.getGoodsType())) {
                removeStockTransferReferences(stockTransfer);
            } else if ("Item".equalsIgnoreCase(stockTransfer.getGoodsType())) {
                revertItemRelatedEntities(stockTransfer);
            }
            
            stockTransferRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public List<StockTransferDTO> getStockTransfersByDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        List<StockTransferEntity> transfers = stockTransferRepository.findByDateBetween(start, end);
        
        for (StockTransferEntity transfer : transfers) {
            transfer.getYarnItems().size();
        }
        
        return transfers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StockTransferDTO> getStockTransfersByGodown(Long godownId) {
        List<StockTransferEntity> fromTransfers = stockTransferRepository.findByFromGodownId(godownId);
        List<StockTransferEntity> toTransfers = stockTransferRepository.findByToGodownId(godownId);
        
        fromTransfers.addAll(toTransfers);
        
        for (StockTransferEntity transfer : fromTransfers) {
            transfer.getYarnItems().size();
        }
        
        return fromTransfers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StockTransferDTO> getStockTransfersByGoodsType(String goodsType) {
        List<StockTransferEntity> transfers = stockTransferRepository.findByGoodsType(goodsType);
        
        for (StockTransferEntity transfer : transfers) {
            transfer.getYarnItems().size();
        }
        
        return transfers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private String fetchItemName(String goodsType, Long itemId) {
        if (itemId == null) {
            return null;
        }
        
        if ("Fabric".equalsIgnoreCase(goodsType)) {
            var wovenFabric = wovenFabricMasterRepository.findById(itemId);
            if (wovenFabric.isPresent()) {
                String code = wovenFabric.get().getFabricCode();
                String quality = wovenFabric.get().getFabricQuality();
                if (code != null && quality != null) {
                    return code + " - " + quality;
                } else if (code != null) {
                    return code;
                } else if (quality != null) {
                    return quality;
                }
                return wovenFabric.get().getFabricName();
            }
            
            var knittedFabric = knittedFabricMasterRepository.findById(itemId);
            if (knittedFabric.isPresent()) {
                return knittedFabric.get().getFabricCode();
            }
        } else if ("Yarn".equalsIgnoreCase(goodsType)) {
            var yarnMaster = yarnMasterRepository.findById(itemId);
            if (yarnMaster.isPresent()) {
                return yarnMaster.get().getYarnName();
            }
        }
        
        return null;
    }

    private void updateRelatedEntities(StockTransferEntity stockTransfer, List<StockFabricTransferDTO> fabricItems) {
        Warehouse toGodown = stockTransfer.getToGodown();
        Long companyId = stockTransfer.getCompanyId();
        
        for (StockFabricTransferDTO fabricItem : fabricItems) {
            if (fabricItem.getPieceNo() != null) {
                List<PieceEntry> pieceEntries;
                if (companyId != null) {
                    pieceEntries = pieceEntryRepository.findByPieceNumberAndCompanyId(fabricItem.getPieceNo(), companyId);
                } else {
                    pieceEntries = pieceEntryRepository.findByPieceNumber(fabricItem.getPieceNo());
                }

                for (PieceEntry pieceEntry : pieceEntries) {
                    if ((pieceEntry.getIsPacked() != null && pieceEntry.getIsPacked() == 1) ||
                        (pieceEntry.getIsDispatched() != null && pieceEntry.getIsDispatched() == 1) ||
                        (pieceEntry.getIsInvoice() != null && pieceEntry.getIsInvoice() == 1)) {
                        throw new IllegalStateException(
                                "Cannot transfer piece " + pieceEntry.getPieceNumber()
                                + " as it is already packed/dispatched/invoiced.");
                    }

                    pieceEntry.setIsStockTransfer(true);
                    pieceEntry.setStockTransferId(stockTransfer.getId());
                    if (toGodown != null) {
                        pieceEntry.setWarehouse(toGodown);
                    }
                    pieceEntryRepository.save(pieceEntry);
                }
            }
        }
    }

    private Long getStockTransferTypeId() {
        return codesDetailRepository.findByValue("Stock Transfer")
                .map(CodesDetail::getId)
                .orElse(130L);
    }

    // private void revertYarnRelatedEntities(StockTransferEntity stockTransfer) {
    //     if (stockTransfer.getYarnItems() != null) {
    //         for (StockYarnTransferEntity yarnItem : stockTransfer.getYarnItems()) {
    //             if (yarnItem.getInwardNo() != null && yarnItem.getIssuedQty() != null) {
    //                 var yarnInventoryOpt = yarnInventoryRepository.findById(yarnItem.getInwardNo());
                    
    //                 if (yarnInventoryOpt.isPresent()) {
    //                     YarnInventory yarnInventory = yarnInventoryOpt.get();
    //                     List<YarnInwardStockDetails> details = yarnInventory.getDetails();
                        
    //                     if (details != null) {
    //                         for (YarnInwardStockDetails detail : details) {
    //                             Long fromWarehouseId = stockTransfer.getFromGodown() != null ? stockTransfer.getFromGodown().getId() : null;
    //                             if (detail.getItemId() != null && detail.getItemId().equals(stockTransfer.getItemId()) &&
    //                                 detail.getWarehouseId() != null && detail.getWarehouseId().equals(fromWarehouseId)) {
    //                                 if (detail.getAvailableQty() != null) {
    //                                     java.math.BigDecimal currentAvailable = detail.getAvailableQty();
    //                                     java.math.BigDecimal issued = yarnItem.getIssuedQty();
    //                                     detail.setAvailableQty(currentAvailable.add(issued));
                                        
    //                                     if (detail.getAvailableBags() != null && yarnItem.getNoOfBags() != null) {
    //                                         java.math.BigDecimal currentBags = detail.getAvailableBags();
    //                                         java.math.BigDecimal issuedBags = new java.math.BigDecimal(yarnItem.getNoOfBags());
    //                                         detail.setAvailableBags(currentBags.add(issuedBags));
    //                                     }
    //                                 }
    //                             }
    //                         }
    //                     }
    //                     yarnInventoryRepository.save(yarnInventory);
    //                 }
    //             }
                
    //             // Delete destination details created for this item
    //             List<YarnInwardStockDetails> destDetails = yarnInwardStockDetailsRepository.findAllByYarnInwardItemId(yarnItem.getId());
    //             if (destDetails != null && !destDetails.isEmpty()) {
    //                 for (YarnInwardStockDetails destDetail : destDetails) {
    //                     YarnInventory destInventory = destDetail.getYarnInventory();
    //                     // If this detail belongs to the original inventory (we moved the original row),
    //                     // restore its warehouse back to the from-godown and clear the link instead of deleting.
    //                     if (destInventory != null && stockTransfer.getFromGodown() != null &&
    //                             destInventory.getId() != null && destInventory.getId().equals(yarnItem.getInwardNo())) {
    //                         destDetail.setYarnInwardItemId(null);
    //                         destDetail.setWarehouseId(stockTransfer.getFromGodown().getId());
    //                         yarnInwardStockDetailsRepository.save(destDetail);
    //                     } else {
    //                         yarnInwardStockDetailsRepository.delete(destDetail);
    //                     }
    //                 }
    //             } else {
    //                 // No destDetails found by yarnInwardItemId. Maybe we only updated warehouse_id on existing row.
    //                 // Find details by yarnInventory id and lotNo and belonging to the to-godown, then restore warehouse.
    //                 if (yarnItem.getInwardNo() != null && stockTransfer.getFromGodown() != null && stockTransfer.getToGodown() != null) {
    //                     List<YarnInwardStockDetails> possible = yarnInwardStockDetailsRepository.findByYarnInventoryId(yarnItem.getInwardNo());
    //                     for (YarnInwardStockDetails pd : possible) {
    //                         if (pd.getItemId() != null && pd.getItemId().equals(stockTransfer.getItemId()) &&
    //                                 pd.getWarehouseId() != null && pd.getWarehouseId().equals(stockTransfer.getToGodown().getId())) {
    //                             pd.setWarehouseId(stockTransfer.getFromGodown().getId());
    //                             yarnInwardStockDetailsRepository.save(pd);
    //                         }
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }
    private void updateYarnRelatedEntities(StockTransferEntity stockTransfer, java.util.Collection<StockYarnTransferEntity> yarnItems) {
        Long stockTransferTypeId = getStockTransferTypeId();

        for (StockYarnTransferEntity yarnItem : yarnItems) {
            if (yarnItem.getInwardNo() == null) {
                continue;
            }

            Long inwardId = yarnItem.getInwardNo();
            var yarnInventoryOpt = yarnInventoryRepository.findById(inwardId);

            if (!yarnInventoryOpt.isPresent()) {
                continue;
            }

            YarnInventory sourceInventory = yarnInventoryOpt.get();
            Long fromWarehouseId = stockTransfer.getFromGodown() != null
                    ? stockTransfer.getFromGodown().getId() : null;

            List<YarnInwardStockDetails> sourceDetails =
                    yarnInwardStockDetailsRepository.findByYarnInventoryIdAndItemIdAndWarehouseId(
                            sourceInventory.getId(), stockTransfer.getItemId(), fromWarehouseId);

            java.math.BigDecimal transferredQty = yarnItem.getIssuedQty();
            if (transferredQty == null) transferredQty = yarnItem.getTotalQuantity();
            if (transferredQty == null) transferredQty = java.math.BigDecimal.ZERO;

            java.math.BigDecimal transferredBags = yarnItem.getNoOfBags() != null
                    ? new java.math.BigDecimal(yarnItem.getNoOfBags())
                    : java.math.BigDecimal.ZERO;

            // Reduce source available stock
            YarnInwardStockDetails sourceDetail = null;
            if (sourceDetails != null && !sourceDetails.isEmpty()) {
                sourceDetail = sourceDetails.get(0);
                sourceDetail.setAvailableQty(
                        sourceDetail.getAvailableQty() != null
                                ? sourceDetail.getAvailableQty().subtract(transferredQty).max(java.math.BigDecimal.ZERO)
                                : java.math.BigDecimal.ZERO);
                sourceDetail.setAvailableBags(
                        sourceDetail.getAvailableBags() != null
                                ? sourceDetail.getAvailableBags().subtract(transferredBags).max(java.math.BigDecimal.ZERO)
                                : java.math.BigDecimal.ZERO);
                yarnInwardStockDetailsRepository.save(sourceDetail);
            }

            // Find existing destination detail scoped strictly to stock-transfer type to avoid
            // cross-module orderItemId namespace collisions that caused null fields in other tables.
            List<YarnInwardStockDetails> existingDestDetails =
                    yarnInwardStockDetailsRepository.findAllByOrderItemIdAndInwardTypeId(
                            yarnItem.getId(), stockTransferTypeId);

            YarnInwardStockDetails destDetail;
            if (existingDestDetails != null && !existingDestDetails.isEmpty()) {
                destDetail = existingDestDetails.get(0);
                // Ensure the inventory FK fields are always populated on updates too
                if (destDetail.getYarnInventory() == null) {
                    List<YarnInventory> existingInv = yarnInventoryRepository
                            .findByInwardIdAndInwardTypeId(stockTransfer.getId(), stockTransferTypeId);
                    if (!existingInv.isEmpty()) {
                        destDetail.setYarnInventory(existingInv.get(0));
                    }
                }
                // Always keep orderItemId in sync
                destDetail.setOrderItemId(yarnItem.getId());
            } else {
                // Create a new destination YarnInventory + YarnInwardStockDetails
                YarnInventory destInventory = new YarnInventory();
                destInventory.setCompanyId(sourceInventory.getCompanyId());
                destInventory.setInwardDate(stockTransfer.getDate().atStartOfDay());
                destInventory.setInwardTypeId(stockTransferTypeId);
                destInventory.setFinancialYear(calculateFinancialYear(stockTransfer.getDate()));
                destInventory.setInwardId(stockTransfer.getId());
                destInventory.setOrderNo(stockTransfer.getStockTransferNo());
                // Use the return value so destInventory.id is guaranteed set after INSERT
                destInventory = yarnInventoryRepository.save(destInventory);

                destDetail = new YarnInwardStockDetails();
                destDetail.setYarnInventory(destInventory);   // sets inward_id FK
                destDetail.setOrderItemId(yarnItem.getId());  // sets order_item_id
            }

            // Populate all transferable fields
            destDetail.setInwardTypeId(stockTransferTypeId);
            destDetail.setItemId(stockTransfer.getItemId());
            destDetail.setWarehouseId(stockTransfer.getToGodown() != null
                    ? stockTransfer.getToGodown().getId() : null);
            destDetail.setLotNo(yarnItem.getLotNo());
            destDetail.setKgsPerBag(yarnItem.getKgsPerBag());
            destDetail.setAvailableQty(transferredQty);
            destDetail.setInwardQty(transferredQty);
            destDetail.setAcceptedQty(transferredQty);
            destDetail.setAvailableBags(transferredBags);
            destDetail.setInwardBags(transferredBags);

            // Copy supplementary fields from source detail when available
            if (sourceDetail != null) {
                destDetail.setMillId(sourceDetail.getMillId());
                if (destDetail.getKgsPerBag() == null) {
                    destDetail.setKgsPerBag(sourceDetail.getKgsPerBag());
                }
            }

            yarnInwardStockDetailsRepository.save(destDetail);
        }
    }

    private void revertYarnRelatedEntities(StockTransferEntity stockTransfer) {
        Long stockTransferTypeId = getStockTransferTypeId();

        // Step 1: Find all destination YarnInventory records created for this stock transfer.
        List<YarnInventory> destInventories = yarnInventoryRepository
                .findByInwardIdAndInwardTypeId(stockTransfer.getId(), stockTransferTypeId);

        // Step 2: Safety check — ensure no stock from the destination has been issued out.
        // Check by inventories found
        for (YarnInventory destInventory : destInventories) {
            List<YarnInwardStockDetails> details =
                    yarnInwardStockDetailsRepository.findByYarnInventoryId(destInventory.getId());
            for (YarnInwardStockDetails detail : details) {
                java.math.BigDecimal outwardQty =
                        yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
                if (outwardQty != null && outwardQty.compareTo(java.math.BigDecimal.ZERO) > 0) {
                    throw new RuntimeException(
                            "Cannot revert stock transfer. Item has already been issued out from destination warehouse (Detail ID: "
                                    + detail.getId() + ")");
                }
            }
        }
        
        // Safety check by order item ID as well to catch any details that might not be correctly linked in memory
        if (stockTransfer.getYarnItems() != null) {
            for (StockYarnTransferEntity yarnItem : stockTransfer.getYarnItems()) {
                List<YarnInwardStockDetails> existingDestDetails =
                        yarnInwardStockDetailsRepository.findAllByOrderItemIdAndInwardTypeId(
                                yarnItem.getId(), stockTransferTypeId);
                for (YarnInwardStockDetails detail : existingDestDetails) {
                    java.math.BigDecimal outwardQty =
                            yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
                    if (outwardQty != null && outwardQty.compareTo(java.math.BigDecimal.ZERO) > 0) {
                        throw new RuntimeException(
                                "Cannot revert stock transfer. Item has already been issued out from destination warehouse (Detail ID: "
                                        + detail.getId() + ")");
                    }
                }
            }
        }

        // Step 3: Restore source available stock for every yarn item in the transfer.
        if (stockTransfer.getYarnItems() != null) {
            for (StockYarnTransferEntity yarnItem : stockTransfer.getYarnItems()) {
                java.math.BigDecimal transferredQty = yarnItem.getIssuedQty();
                if (transferredQty == null) transferredQty = yarnItem.getTotalQuantity();
                if (transferredQty == null) transferredQty = java.math.BigDecimal.ZERO;

                java.math.BigDecimal transferredBags = yarnItem.getNoOfBags() != null
                        ? new java.math.BigDecimal(yarnItem.getNoOfBags())
                        : java.math.BigDecimal.ZERO;

                if (yarnItem.getInwardNo() != null) {
                    var yarnInventoryOpt = yarnInventoryRepository.findById(yarnItem.getInwardNo());
                    if (yarnInventoryOpt.isPresent()) {
                        YarnInventory sourceInventory = yarnInventoryOpt.get();
                        List<YarnInwardStockDetails> sourceDetails =
                                yarnInwardStockDetailsRepository.findByYarnInventoryIdAndItemIdAndWarehouseId(
                                        sourceInventory.getId(),
                                        stockTransfer.getItemId(),
                                        stockTransfer.getFromGodown() != null
                                                ? stockTransfer.getFromGodown().getId()
                                                : null);

                        if (sourceDetails != null && !sourceDetails.isEmpty()) {
                            YarnInwardStockDetails sourceDetail = sourceDetails.get(0);
                            sourceDetail.setAvailableQty(
                                    sourceDetail.getAvailableQty() != null
                                            ? sourceDetail.getAvailableQty().add(transferredQty)
                                            : transferredQty);
                            sourceDetail.setAvailableBags(
                                    sourceDetail.getAvailableBags() != null
                                            ? sourceDetail.getAvailableBags().add(transferredBags)
                                            : transferredBags);
                            yarnInwardStockDetailsRepository.save(sourceDetail);
                        }
                    }
                }
                
                // Explicitly delete destination details by order_item_id to ensure complete removal
                yarnInwardStockDetailsRepository.deleteByOrderItemIdAndInwardTypeId(yarnItem.getId(), stockTransferTypeId);
            }
        }

        // Step 4: Delete destination inventories. CascadeType.ALL on YarnInventory.details
        // would normally handle this, but explicit deletion of details above ensures 
        // that even orphaned or incorrectly linked records are removed.
        for (YarnInventory destInventory : destInventories) {
            yarnInventoryRepository.delete(destInventory);
        }
    }

    private String calculateFinancialYear(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        
        int year = date.getYear();
        int month = date.getMonthValue();
        
        // Indian financial year: April to March
        // If month is April (4) or later, FY is currentYear-nextYear
        // If month is January-March, FY is previousYear-currentYear
        if (month >= 4) {
            // FY: currentYear - nextYear (e.g., 2024-25 for dates between Apr 2024 - Mar 2025)
            int nextYear = year + 1;
            return String.format("%d-%d", year, nextYear % 100);
        } else {
            // FY: previousYear - currentYear (e.g., 2023-24 for dates between Jan 2024 - Mar 2024)
            int previousYear = year - 1;
            return String.format("%d-%d", previousYear, year % 100);
        }
    }

    private void removeStockTransferReferences(StockTransferEntity stockTransfer) {
        Long stockTransferId = stockTransfer.getId();
        Warehouse fromGodown = stockTransfer.getFromGodown();
        
        List<PieceEntry> pieceEntries = pieceEntryRepository.findByStockTransferId(stockTransferId);
        for (PieceEntry pieceEntry : pieceEntries) {
            // Check if piece has been packed, dispatched or invoiced
            if ((pieceEntry.getIsPacked() != null && pieceEntry.getIsPacked() == 1) ||
                (pieceEntry.getIsDispatched() != null && pieceEntry.getIsDispatched() == 1) ||
                (pieceEntry.getIsInvoice() != null && pieceEntry.getIsInvoice() == 1)) {
                throw new RuntimeException("Cannot revert stock transfer for piece " + pieceEntry.getPieceNumber() + " as it is already packed/dispatched/invoiced.");
            }

            pieceEntry.setIsStockTransfer(false);
            pieceEntry.setStockTransferId(null);
            if (fromGodown != null) {
                pieceEntry.setWarehouse(fromGodown);
            }
            pieceEntryRepository.save(pieceEntry);
        }
    }

    private void updateFabricItems(StockTransferEntity stockTransfer, List<StockFabricTransferDTO> itemDTOs) {
        Set<StockFabricTransferEntity> currentItems = stockTransfer.getFabricItems();
        if (currentItems == null) {
            currentItems = new HashSet<>();
            stockTransfer.setFabricItems(currentItems);
        }

        if (itemDTOs == null || itemDTOs.isEmpty()) {
            currentItems.clear();
            return;
        }

        java.util.Map<Long, StockFabricTransferEntity> existingItemsMap = currentItems.stream()
                .filter(item -> item.getId() != null)
                .collect(java.util.stream.Collectors.toMap(StockFabricTransferEntity::getId, java.util.function.Function.identity()));

        java.util.Map<Long, StockFabricTransferDTO> dtoItemsMap = itemDTOs.stream()
                .filter(dto -> dto.getId() != null)
                .collect(java.util.stream.Collectors.toMap(StockFabricTransferDTO::getId, java.util.function.Function.identity()));

        currentItems.removeIf(existingItem -> existingItem.getId() != null && !dtoItemsMap.containsKey(existingItem.getId()));

        for (StockFabricTransferDTO itemDto : itemDTOs) {
            if (itemDto.getId() != null && existingItemsMap.containsKey(itemDto.getId())) {
                StockFabricTransferEntity existingItem = existingItemsMap.get(itemDto.getId());
                updateFabricItemFromDto(existingItem, itemDto);
            } else {
                StockFabricTransferEntity newItem = convertFabricDTOToEntity(itemDto);
                newItem.setStockTransfer(stockTransfer);
                currentItems.add(newItem);
            }
        }
    }

    private void updateFabricItemFromDto(StockFabricTransferEntity item, StockFabricTransferDTO dto) {
        item.setRollNo(dto.getRollNo());
        item.setPieceNo(dto.getPieceNo());
        item.setMeter(dto.getMeter());
        item.setNetWeight(dto.getNetWeight());
        item.setAvgWeight(dto.getAvgWeight());
    }

    private void updateYarnItems(StockTransferEntity stockTransfer, List<StockYarnTransferDTO> itemDTOs) {
        Set<StockYarnTransferEntity> currentItems = stockTransfer.getYarnItems();
        if (currentItems == null) {
            currentItems = new HashSet<>();
            stockTransfer.setYarnItems(currentItems);
        }

        if (itemDTOs == null || itemDTOs.isEmpty()) {
            currentItems.clear();
            return;
        }

        java.util.Map<Long, StockYarnTransferEntity> existingItemsMap = currentItems.stream()
                .filter(item -> item.getId() != null)
                .collect(java.util.stream.Collectors.toMap(StockYarnTransferEntity::getId, java.util.function.Function.identity()));

        java.util.Map<Long, StockYarnTransferDTO> dtoItemsMap = itemDTOs.stream()
                .filter(dto -> dto.getId() != null)
                .collect(java.util.stream.Collectors.toMap(StockYarnTransferDTO::getId, java.util.function.Function.identity()));

        currentItems.removeIf(existingItem -> existingItem.getId() != null && !dtoItemsMap.containsKey(existingItem.getId()));

        for (StockYarnTransferDTO itemDto : itemDTOs) {
            StockYarnTransferEntity existingItem = null;
            if (itemDto.getId() != null && existingItemsMap.containsKey(itemDto.getId())) {
                existingItem = existingItemsMap.get(itemDto.getId());
            } else {
                // Try to match by inwardNo and lotNo to prevent recreation if ID is missing from frontend
                Long resolvedInwardNo = null;
                if (itemDto.getInwardId() != null) {
                    resolvedInwardNo = itemDto.getInwardId();
                } else if (itemDto.getInwardNo() != null) {
                    try {
                        resolvedInwardNo = Long.parseLong(itemDto.getInwardNo());
                    } catch (NumberFormatException e) {
                        final String searchNo = itemDto.getInwardNo();
                        var invOpt = yarnInventoryRepository.findAll().stream()
                            .filter(inv -> searchNo.equals(inv.getInwardNo()))
                            .findFirst();
                        if (invOpt.isPresent()) {
                            resolvedInwardNo = invOpt.get().getId();
                        }
                    }
                }
                
                if (resolvedInwardNo != null) {
                    final Long finalInwardNo = resolvedInwardNo;
                    final String lotNo = itemDto.getLotNo();
                    existingItem = currentItems.stream()
                        .filter(item -> finalInwardNo.equals(item.getInwardNo()) && 
                                        lotNo != null && lotNo.equals(item.getLotNo()))
                        .findFirst().orElse(null);
                }
            }

            if (existingItem != null) {
                updateYarnItemFromDto(existingItem, itemDto);
            } else {
                StockYarnTransferEntity newItem = convertYarnDTOToEntity(itemDto);
                newItem.setStockTransfer(stockTransfer);
                currentItems.add(newItem);
            }
        }
    }

    private void updateYarnItemFromDto(StockYarnTransferEntity item, StockYarnTransferDTO dto) {
        item.setMillName(dto.getMillName());
        item.setLotNo(dto.getLotNo());
        item.setNoOfBags(dto.getNoOfBags());
        item.setKgsPerBag(dto.getKgsPerBag());
        item.setConesPerBag(dto.getConesPerBag());
        item.setConesWeight(dto.getConesWeight());
        item.setIssuedQty(dto.getIssuedQty());
        
        if (dto.getTotalQuantity() != null) {
            item.setTotalQuantity(dto.getTotalQuantity());
        }
        
        if (dto.getInwardId() != null) {
            item.setInwardNo(dto.getInwardId());
        } else if (dto.getInwardNo() != null) {
            try {
                item.setInwardNo(Long.parseLong(dto.getInwardNo()));
            } catch (NumberFormatException e) {
                var yarnInventory = yarnInventoryRepository.findAll().stream()
                    .filter(inv -> inv.getInwardNo() != null && inv.getInwardNo().equals(dto.getInwardNo()))
                    .findFirst();
                if (yarnInventory.isPresent()) {
                    item.setInwardNo(yarnInventory.get().getId());
                }
            }
        }
    }

    private StockTransferDTO convertToDTO(StockTransferEntity entity) {
        StockTransferDTO dto = new StockTransferDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setGoodsType(entity.getGoodsType());
        dto.setItemId(entity.getItemId());
        
        if (entity.getItemId() != null) {
            String itemName = fetchItemName(entity.getGoodsType(), entity.getItemId());
            dto.setItemName(itemName);
        } else {
            dto.setItemName(entity.getItemName());
        }
        
        dto.setQuantity(entity.getQuantity());
        dto.setCompanyId(entity.getCompanyId());
        dto.setStockTransferNo(entity.getStockTransferNo());

        if (entity.getFromGodown() != null) {
            dto.setFromGodownId(entity.getFromGodown().getId());
            dto.setFromGodownName(entity.getFromGodown().getWarehouseName());
        }

        if (entity.getToGodown() != null) {
            dto.setToGodownId(entity.getToGodown().getId());
            dto.setToGodownName(entity.getToGodown().getWarehouseName());
        }

        if (entity.getFabricItems() != null) {
            List<StockFabricTransferDTO> fabricItems = entity.getFabricItems().stream()
                    .map(this::convertFabricEntityToDTO)
                    .collect(Collectors.toList());
            dto.setFabricItems(fabricItems);
        }

        if (entity.getYarnItems() != null) {
            List<StockYarnTransferDTO> yarnItems = entity.getYarnItems().stream()
                    .map(this::convertYarnEntityToDTO)
                    .collect(Collectors.toList());
            dto.setYarnItems(yarnItems);
        }

        if (entity.getItemItems() != null) {
            List<StockItemTransferDTO> itemItems = entity.getItemItems().stream()
                    .map(this::convertItemEntityToDTO)
                    .collect(Collectors.toList());
            
            // Only populate itemGoods field for Item goodsType, not itemItems
            if ("Item".equalsIgnoreCase(entity.getGoodsType())) {
                dto.setItemGoods(itemItems);
            } else {
                dto.setItemItems(itemItems);
            }
        }

        return dto;
    }

    private StockTransferEntity convertToEntity(StockTransferDTO dto) {
        StockTransferEntity entity = new StockTransferEntity();
        entity.setId(dto.getId());
        entity.setDate(dto.getDate());
        entity.setGoodsType(dto.getGoodsType());
        entity.setItemId(dto.getItemId());
        
        if (dto.getItemId() != null) {
            String itemName = fetchItemName(dto.getGoodsType(), dto.getItemId());
            entity.setItemName(itemName);
        } else {
            entity.setItemName(dto.getItemName());
        }
        
        entity.setQuantity(dto.getQuantity());
        entity.setCompanyId(dto.getCompanyId());
        entity.setStockTransferNo(dto.getStockTransferNo());

        if (dto.getFromGodownId() != null) {
            Warehouse fromGodown = warehouseRepository.findById(dto.getFromGodownId())
                    .orElseThrow(() -> new RuntimeException("From godown not found with id: " + dto.getFromGodownId()));
            entity.setFromGodown(fromGodown);
        }

        if (dto.getToGodownId() != null) {
            Warehouse toGodown = warehouseRepository.findById(dto.getToGodownId())
                    .orElseThrow(() -> new RuntimeException("To godown not found with id: " + dto.getToGodownId()));
            entity.setToGodown(toGodown);
        }

        if (dto.getFabricItems() != null && !dto.getFabricItems().isEmpty()) {
            Set<StockFabricTransferEntity> fabricEntities = dto.getFabricItems().stream()
                    .map(this::convertFabricDTOToEntity)
                    .collect(Collectors.toSet());
            for (StockFabricTransferEntity item : fabricEntities) {
                item.setStockTransfer(entity);
            }
            entity.setFabricItems(fabricEntities);
        }

        if (dto.getYarnItems() != null && !dto.getYarnItems().isEmpty()) {
            Set<StockYarnTransferEntity> yarnEntities = dto.getYarnItems().stream()
                    .map(this::convertYarnDTOToEntity)
                    .collect(Collectors.toSet());
            for (StockYarnTransferEntity item : yarnEntities) {
                item.setStockTransfer(entity);
            }
            entity.setYarnItems(yarnEntities);
        }

        // Handle itemGoods mapping for Item goodsType
        List<StockItemTransferDTO> itemSource = dto.getItemItems();
        if ("Item".equalsIgnoreCase(dto.getGoodsType()) && 
            (dto.getItemGoods() != null && !dto.getItemGoods().isEmpty())) {
            itemSource = dto.getItemGoods();
        }
        
        if (itemSource != null && !itemSource.isEmpty()) {
            Set<StockItemTransferEntity> itemEntities = itemSource.stream()
                    .map(this::convertItemDTOToEntity)
                    .collect(Collectors.toSet());
            for (StockItemTransferEntity item : itemEntities) {
                item.setStockTransfer(entity);
            }
            entity.setItemItems(itemEntities);
        }

        return entity;
    }

    private StockFabricTransferDTO convertFabricEntityToDTO(StockFabricTransferEntity entity) {
        StockFabricTransferDTO dto = new StockFabricTransferDTO();
        dto.setId(entity.getId());
        dto.setStockTransferId(entity.getStockTransfer() != null ? entity.getStockTransfer().getId() : null);
        dto.setRollNo(entity.getRollNo());
        dto.setPieceNo(entity.getPieceNo());
        dto.setMeter(entity.getMeter());
        dto.setNetWeight(entity.getNetWeight());
        dto.setAvgWeight(entity.getAvgWeight());
        return dto;
    }

    private StockFabricTransferEntity convertFabricDTOToEntity(StockFabricTransferDTO dto) {
        StockFabricTransferEntity entity = new StockFabricTransferEntity();
        entity.setId(dto.getId());
        entity.setRollNo(dto.getRollNo());
        entity.setPieceNo(dto.getPieceNo());
        entity.setMeter(dto.getMeter());
        entity.setNetWeight(dto.getNetWeight());
        entity.setAvgWeight(dto.getAvgWeight());
        return entity;
    }

    private StockYarnTransferDTO convertYarnEntityToDTO(StockYarnTransferEntity entity) {
        StockYarnTransferDTO dto = new StockYarnTransferDTO();
        dto.setId(entity.getId());
        dto.setInwardId(entity.getInwardNo());
        dto.setInwardNo(String.valueOf(entity.getInwardNo()));
        
        if (entity.getInwardNo() != null) {
            var yarnInventory = yarnInventoryRepository.findById(entity.getInwardNo());
            if (yarnInventory.isPresent()) {
                dto.setInwardNoStr(yarnInventory.get().getInwardNo());
            }
        }
        
        dto.setMillName(entity.getMillName());
        dto.setLotNo(entity.getLotNo());
        dto.setNoOfBags(entity.getNoOfBags());
        dto.setKgsPerBag(entity.getKgsPerBag());
        dto.setConesPerBag(entity.getConesPerBag());
        dto.setConesWeight(entity.getConesWeight());
        dto.setTotalQuantity(entity.getTotalQuantity());
        dto.setIssuedQty(entity.getIssuedQty());
        return dto;
    }

    private StockYarnTransferEntity convertYarnDTOToEntity(StockYarnTransferDTO dto) {
        StockYarnTransferEntity entity = new StockYarnTransferEntity();
        entity.setId(dto.getId());
        entity.setMillName(dto.getMillName());
        entity.setLotNo(dto.getLotNo());
        entity.setNoOfBags(dto.getNoOfBags());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setConesPerBag(dto.getConesPerBag());
        entity.setConesWeight(dto.getConesWeight());
        entity.setIssuedQty(dto.getIssuedQty());
        
        if (dto.getTotalQuantity() != null) {
            entity.setTotalQuantity(dto.getTotalQuantity());
        }
        
        if (dto.getInwardId() != null) {
            entity.setInwardNo(dto.getInwardId());
        } else if (dto.getInwardNo() != null) {
            try {
                entity.setInwardNo(Long.parseLong(dto.getInwardNo()));
            } catch (NumberFormatException e) {
                var yarnInventory = yarnInventoryRepository.findAll().stream()
                    .filter(inv -> inv.getInwardNo() != null && inv.getInwardNo().equals(dto.getInwardNo()))
                    .findFirst();
                if (yarnInventory.isPresent()) {
                    entity.setInwardNo(yarnInventory.get().getId());
                }
            }
        }
        
        return entity;
    }

    private StockItemTransferDTO convertItemEntityToDTO(StockItemTransferEntity entity) {
        StockItemTransferDTO dto = new StockItemTransferDTO();
        dto.setId(entity.getId());
        dto.setStockTransferId(entity.getStockTransfer() != null ? entity.getStockTransfer().getId() : null);
        dto.setItemId(entity.getItemId());
        dto.setItemName(entity.getItemName());
        dto.setSNo(entity.getSNo());
        dto.setNoOfBags(entity.getNoOfBags());
        dto.setKgsPerBag(entity.getKgsPerBag());
        dto.setAcceptedQty(entity.getAcceptedQty());
        dto.setIssuingBags(entity.getIssuingBags());
        dto.setLotNo(entity.getLotNo());
        return dto;
    }

    private StockItemTransferEntity convertItemDTOToEntity(StockItemTransferDTO dto) {
        StockItemTransferEntity entity = new StockItemTransferEntity();
        entity.setId(dto.getId());
        entity.setItemId(dto.getItemId());
        entity.setItemName(dto.getItemName());
        entity.setSNo(dto.getSNo());
        entity.setNoOfBags(dto.getNoOfBags());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setAcceptedQty(dto.getAcceptedQty());
        entity.setIssuingBags(dto.getIssuingBags());
        entity.setLotNo(dto.getLotNo());
        return entity;
    }

    private void updateItemItems(StockTransferEntity stockTransfer, List<StockItemTransferDTO> itemDTOs) {
        Set<StockItemTransferEntity> currentItems = stockTransfer.getItemItems();
        if (currentItems == null) {
            currentItems = new HashSet<>();
            stockTransfer.setItemItems(currentItems);
        }

        if (itemDTOs == null || itemDTOs.isEmpty()) {
            currentItems.clear();
            return;
        }

        java.util.Map<Long, StockItemTransferEntity> existingMap = currentItems.stream()
                .filter(item -> item.getId() != null)
                .collect(java.util.stream.Collectors.toMap(StockItemTransferEntity::getId, java.util.function.Function.identity()));

        java.util.Set<Long> dtoIds = itemDTOs.stream()
                .filter(dto -> dto.getId() != null)
                .map(StockItemTransferDTO::getId)
                .collect(java.util.stream.Collectors.toSet());

        currentItems.removeIf(item -> item.getId() != null && !dtoIds.contains(item.getId()));

        for (StockItemTransferDTO itemDto : itemDTOs) {
            if (itemDto.getId() != null && existingMap.containsKey(itemDto.getId())) {
                StockItemTransferEntity existing = existingMap.get(itemDto.getId());
                existing.setItemId(itemDto.getItemId());
                existing.setItemName(itemDto.getItemName());
                existing.setSNo(itemDto.getSNo());
                existing.setNoOfBags(itemDto.getNoOfBags());
                existing.setKgsPerBag(itemDto.getKgsPerBag());
                existing.setAcceptedQty(itemDto.getAcceptedQty());
                existing.setIssuingBags(itemDto.getIssuingBags());
                existing.setLotNo(itemDto.getLotNo());
            } else {
                StockItemTransferEntity newItem = convertItemDTOToEntity(itemDto);
                newItem.setStockTransfer(stockTransfer);
                currentItems.add(newItem);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<YarnInwardStockDetailsDto> getYarnInwardStockDetailsByItemIdsAndWarehouse(
            List<Long> itemIds,
            Long outwardTypeId,
            Long warehouseId,
            Long companyId,
            Long purchaseOrderId,
            Long poTypeId) {
        List<YarnInwardStockDetailsDto> result = new java.util.ArrayList<>();
        List<com.tech3.erp.entity.YarnInwardStockDetails> searchDetails = new java.util.ArrayList<>();

        if (itemIds != null && !itemIds.isEmpty()) {
            for (Long itemId : itemIds) {
                searchDetails.addAll(yarnInwardStockDetailsRepository.findByItemId(itemId));
            }
        } else if (purchaseOrderId != null) {
            if (poTypeId != null) {
                searchDetails.addAll(yarnInwardStockDetailsRepository.findByPurchaseOrderIdAndPoTypeId(purchaseOrderId, poTypeId));
            } else {
                searchDetails.addAll(yarnInwardStockDetailsRepository.findByPurchaseOrderId(purchaseOrderId));
            }
        }

        for (com.tech3.erp.entity.YarnInwardStockDetails detail : searchDetails) {
            if (warehouseId != null) {
                if (detail.getWarehouseId() == null || !detail.getWarehouseId().equals(warehouseId)) {
                    continue;
                }
            }
            if (companyId != null) {
                Long detailCompanyId = detail.getYarnInventory() != null ? detail.getYarnInventory().getCompanyId() : null;
                if (!companyId.equals(detailCompanyId)) {
                    continue;
                }
            }
            if (purchaseOrderId != null) {
                Long detailOrderId = detail.getYarnInventory() != null ? detail.getYarnInventory().getOrderId() : null;
                if (!purchaseOrderId.equals(detailOrderId)) {
                    continue;
                }
            }
            if (poTypeId != null) {
                Long detailPoTypeId = detail.getYarnInventory() != null ? detail.getYarnInventory().getInwardTypeId() : null;
                if (!poTypeId.equals(detailPoTypeId)) {
                    continue;
                }
            }

            YarnInwardStockDetailsDto dto = new YarnInwardStockDetailsDto(detail);

            java.math.BigDecimal acceptedQty = detail.getAcceptedQty() != null ? detail.getAcceptedQty() : java.math.BigDecimal.ZERO;
            java.math.BigDecimal inwardBags = detail.getInwardBags() != null ? detail.getInwardBags() : java.math.BigDecimal.ZERO;

            java.math.BigDecimal totalIssuedQty = yarnOutwardStockDetailsRepository.getTotalIssuedQtyByYarnInwardStockDetailsId(detail.getId());
            java.math.BigDecimal totalIssuedBags = yarnOutwardStockDetailsRepository.getTotalIssuedBagsByYarnInwardStockDetailsId(detail.getId());

            java.math.BigDecimal realAvailableQty = acceptedQty.subtract(totalIssuedQty != null ? totalIssuedQty : java.math.BigDecimal.ZERO);
            java.math.BigDecimal realAvailableBags = inwardBags.subtract(totalIssuedBags != null ? totalIssuedBags : java.math.BigDecimal.ZERO);

            dto.setAvailableQty(realAvailableQty.compareTo(java.math.BigDecimal.ZERO) < 0 ? java.math.BigDecimal.ZERO : realAvailableQty);
            dto.setAvailableBags(realAvailableBags.compareTo(java.math.BigDecimal.ZERO) < 0 ? java.math.BigDecimal.ZERO : realAvailableBags);

            if (outwardTypeId != null) {
                dto.setOutwardTypeId(outwardTypeId);
            }
            Long purchaseInwardId = dto.getInwardId();
            if (purchaseInwardId != null) {
                purchaseInwardRepository.findById(purchaseInwardId).ifPresent(pi -> {
                    if (dto.getMillInvNo() == null) dto.setMillInvNo(pi.getMillInvNo());
                    if (dto.getMillDate() == null) dto.setMillDate(pi.getMillDate());
                });
            }
            if (dto.getAvailableQty() != null && dto.getAvailableQty().compareTo(java.math.BigDecimal.ZERO) > 0) {
                result.add(dto);
            }
        }

        return result;
    }

    @Transactional(readOnly = true)
    public StockTransferDTO getStockTransferByIdAndCompany(Long id, Long companyId) {
        StockTransferEntity stockTransfer = stockTransferRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("Stock transfer not found with id: " + id + " and companyId: " + companyId));
        stockTransfer.getYarnItems().size();
        return convertToDTO(stockTransfer);
    }

    public StockTransferDTO updateStockTransferByCompany(Long id, Long companyId, StockTransferDTO stockTransferDTO) {
        StockTransferEntity existingTransfer = stockTransferRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("Stock transfer not found with id: " + id + " and companyId: " + companyId));
        
        // Revert old inventory effects before updating fields
        if ("Yarn".equalsIgnoreCase(existingTransfer.getGoodsType())) {
            revertYarnRelatedEntities(existingTransfer);
        } else if ("Fabric".equalsIgnoreCase(existingTransfer.getGoodsType())) {
            removeStockTransferReferences(existingTransfer);
        } else if ("Item".equalsIgnoreCase(existingTransfer.getGoodsType())) {
            revertItemRelatedEntities(existingTransfer);
        }

        if (stockTransferDTO.getFromGodownId() != null && 
            stockTransferDTO.getToGodownId() != null &&
            stockTransferDTO.getFromGodownId().equals(stockTransferDTO.getToGodownId())) {
            throw new IllegalArgumentException("From godown and To godown cannot be the same");
        }
        
        if (stockTransferDTO.getDate() != null) {
            existingTransfer.setDate(stockTransferDTO.getDate());
        }
        if (stockTransferDTO.getGoodsType() != null) {
            existingTransfer.setGoodsType(stockTransferDTO.getGoodsType());
        }
        if (stockTransferDTO.getItemId() != null) {
            existingTransfer.setItemId(stockTransferDTO.getItemId());
            String itemName = fetchItemName(stockTransferDTO.getGoodsType(), stockTransferDTO.getItemId());
            existingTransfer.setItemName(itemName);
        } else if (stockTransferDTO.getItemName() != null) {
            existingTransfer.setItemName(stockTransferDTO.getItemName());
        }
        if (stockTransferDTO.getQuantity() != null) {
            existingTransfer.setQuantity(stockTransferDTO.getQuantity());
        }
        
        if (stockTransferDTO.getStockTransferNo() != null) {
            existingTransfer.setStockTransferNo(stockTransferDTO.getStockTransferNo());
        }
        
        if (stockTransferDTO.getFromGodownId() != null) {
            Warehouse fromGodown = warehouseRepository.findById(stockTransferDTO.getFromGodownId())
                    .orElseThrow(() -> new RuntimeException("From godown not found with id: " + stockTransferDTO.getFromGodownId()));
            existingTransfer.setFromGodown(fromGodown);
        }
        
        if (stockTransferDTO.getToGodownId() != null) {
            Warehouse toGodown = warehouseRepository.findById(stockTransferDTO.getToGodownId())
                    .orElseThrow(() -> new RuntimeException("To godown not found with id: " + stockTransferDTO.getToGodownId()));
            existingTransfer.setToGodown(toGodown);
        }
        
        if (stockTransferDTO.getFabricItems() != null) {
            updateFabricItems(existingTransfer, stockTransferDTO.getFabricItems());
        }

        if (stockTransferDTO.getYarnItems() != null) {
            updateYarnItems(existingTransfer, stockTransferDTO.getYarnItems());
        }

        if (stockTransferDTO.getItemItems() != null) {
            updateItemItems(existingTransfer, stockTransferDTO.getItemItems());
        }

        StockTransferEntity updatedTransfer = stockTransferRepository.save(existingTransfer);
        
        if ("Fabric".equalsIgnoreCase(stockTransferDTO.getGoodsType()) && 
            stockTransferDTO.getFabricItems() != null && 
            !stockTransferDTO.getFabricItems().isEmpty()) {
            
            updateRelatedEntities(updatedTransfer, stockTransferDTO.getFabricItems());
        }

        if ("Yarn".equalsIgnoreCase(stockTransferDTO.getGoodsType()) && 
            stockTransferDTO.getYarnItems() != null && 
            !stockTransferDTO.getYarnItems().isEmpty()) {
            
            updateYarnRelatedEntities(updatedTransfer, updatedTransfer.getYarnItems());
        }

        if ("Item".equalsIgnoreCase(stockTransferDTO.getGoodsType()) &&
            updatedTransfer.getItemItems() != null &&
            !updatedTransfer.getItemItems().isEmpty()) {

            updateItemRelatedEntities(updatedTransfer, updatedTransfer.getItemItems());
        }
        
        return convertToDTO(updatedTransfer);
    }

    public void deleteStockTransferByCompany(Long id, Long companyId) {
        StockTransferEntity stockTransfer = stockTransferRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new RuntimeException("Stock transfer not found with id: " + id + " and companyId: " + companyId));
        
        if (stockTransfer != null) {
            if ("Yarn".equalsIgnoreCase(stockTransfer.getGoodsType())) {
                revertYarnRelatedEntities(stockTransfer);
            } else if ("Fabric".equalsIgnoreCase(stockTransfer.getGoodsType())) {
                removeStockTransferReferences(stockTransfer);
            } else if ("Item".equalsIgnoreCase(stockTransfer.getGoodsType())) {
                revertItemRelatedEntities(stockTransfer);
            }
            
            stockTransferRepository.deleteByIdAndCompanyId(id, companyId);
        }
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<StockTransferDTO> getAllStockTransfersByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        return buildPaginatedResponse(
                stockTransferRepository.findIdsByCompanyId(companyId),
                offset,
                limit,
                ids -> ids.isEmpty() ? new ArrayList<>() : stockTransferRepository.findByCompanyIdAndIds(companyId, ids)
        );
    }

    private PaginatedResponse<StockTransferDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit, Function<List<Long>, List<StockTransferEntity>> fetcher) {
        long off = offset != null ? Math.max(0, offset) : 0;
        long lim = (limit != null && limit > 0) ? limit : 10;
        long total = allIds.size();
        int start = (int) Math.min(off, total);
        if (start >= total) {
            return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, new ArrayList<>());
        }
        int end = (int) Math.min(off + lim, total);
        List<Long> pageIds = allIds.subList(start, end);
        List<StockTransferEntity> transfers = pageIds.isEmpty() ? new ArrayList<>() : fetcher.apply(pageIds);
        List<StockTransferDTO> dtos = transfers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new PaginatedResponse<>(
                total,
                end < total,
                off > 0,
                off,
                lim,
                dtos
        );
    }

    private void updateItemRelatedEntities(StockTransferEntity stockTransfer, java.util.Collection<StockItemTransferEntity> itemItems) {
        for (StockItemTransferEntity item : itemItems) {
            if (item.getItemId() == null) {
                continue;
            }

            Long fromWarehouseId = stockTransfer.getFromGodown() != null
                    ? stockTransfer.getFromGodown().getId() : null;

            // Find source detail — prefer lotNo-scoped lookup to avoid picking the wrong record
            // when multiple inwards have deposited the same item into the same warehouse
            List<ItemInwardStockDetails> sourceDetails;
            if (item.getLotNo() != null && !item.getLotNo().isEmpty()) {
                sourceDetails = itemInwardStockDetailsRepository.findByItemIdAndWarehouseIdAndLotNo(
                        item.getItemId(), fromWarehouseId, item.getLotNo());
            } else {
                sourceDetails = itemInwardStockDetailsRepository.findByItemIdAndWarehouseId(
                        item.getItemId(), fromWarehouseId);
            }

            java.math.BigDecimal transferredQty = item.getAcceptedQty();
            if (transferredQty == null) transferredQty = java.math.BigDecimal.ZERO;

            java.math.BigDecimal transferredBags = item.getIssuingBags() != null
                    ? new java.math.BigDecimal(item.getIssuingBags())
                    : (item.getNoOfBags() != null ? new java.math.BigDecimal(item.getNoOfBags()) : java.math.BigDecimal.ZERO);

            // Reduce source available stock
            ItemInwardStockDetails sourceDetail = null;
            if (sourceDetails != null && !sourceDetails.isEmpty()) {
                sourceDetail = sourceDetails.get(0);
                sourceDetail.setAvailableQty(
                        sourceDetail.getAvailableQty() != null
                                ? sourceDetail.getAvailableQty().subtract(transferredQty).max(java.math.BigDecimal.ZERO)
                                : java.math.BigDecimal.ZERO);
                sourceDetail.setAvailableBags(
                        sourceDetail.getAvailableBags() != null
                                ? sourceDetail.getAvailableBags().subtract(transferredBags).max(java.math.BigDecimal.ZERO)
                                : java.math.BigDecimal.ZERO);
                itemInwardStockDetailsRepository.save(sourceDetail);
            }

            // Find existing destination detail scoped to this stock transfer's inventory
            // (mirrors the yarn approach of using inwardTypeId, but uses inwardId+orderItemId
            // since ItemInventory has no inwardTypeId column)
            List<ItemInventory> existingInvs = itemInventoryRepository
                    .findByInwardIdAndOrderItemId(stockTransfer.getId(), item.getId());

            List<ItemInwardStockDetails> existingDestDetails = new java.util.ArrayList<>();
            for (ItemInventory inv : existingInvs) {
                existingDestDetails.addAll(itemInwardStockDetailsRepository.findByItemInventoryId(inv.getId()));
            }

            ItemInwardStockDetails destDetail;
            if (!existingDestDetails.isEmpty()) {
                destDetail = existingDestDetails.get(0);
                if (destDetail.getItemInventory() == null && !existingInvs.isEmpty()) {
                    destDetail.setItemInventory(existingInvs.get(0));
                }
                destDetail.setOrderItemId(item.getId());
            } else {
                // Create a new destination ItemInventory + ItemInwardStockDetails
                // orderItemId on the inventory links this record to the specific transfer line,
                // enabling safe scoped lookups on update/revert without inwardTypeId
                ItemInventory destInventory = new ItemInventory();
                destInventory.setCompanyId(stockTransfer.getCompanyId());
                destInventory.setInwardDate(stockTransfer.getDate().atStartOfDay());
                destInventory.setFinancialYear(calculateFinancialYear(stockTransfer.getDate()));
                destInventory.setInwardId(stockTransfer.getId());
                destInventory.setOrderItemId(item.getId());
                destInventory.setOrderNo(stockTransfer.getStockTransferNo());
                destInventory = itemInventoryRepository.save(destInventory);

                destDetail = new ItemInwardStockDetails();
                destDetail.setItemInventory(destInventory);
                destDetail.setOrderItemId(item.getId());
            }

            destDetail.setItemId(item.getItemId());
            destDetail.setWarehouseId(stockTransfer.getToGodown() != null
                    ? stockTransfer.getToGodown().getId() : null);
            destDetail.setLotNo(item.getLotNo());
            destDetail.setKgsPerBag(item.getKgsPerBag());
            destDetail.setAvailableQty(transferredQty);
            destDetail.setInwardQty(transferredQty);
            destDetail.setAcceptedQty(transferredQty);
            destDetail.setAvailableBags(transferredBags);
            destDetail.setInwardBags(transferredBags);

            if (sourceDetail != null) {
                destDetail.setMillId(sourceDetail.getMillId());
                if (destDetail.getKgsPerBag() == null) {
                    destDetail.setKgsPerBag(sourceDetail.getKgsPerBag());
                }
            }

            itemInwardStockDetailsRepository.save(destDetail);
        }
    }

    private void revertItemRelatedEntities(StockTransferEntity stockTransfer) {
        // Step 1: Find all destination ItemInventory records created for this stock transfer.
        List<ItemInventory> destInventories = itemInventoryRepository
                .findByInwardId(stockTransfer.getId());

        // Step 2: Restore source available stock for every item in the transfer.
        if (stockTransfer.getItemItems() != null) {
            for (StockItemTransferEntity item : stockTransfer.getItemItems()) {
                java.math.BigDecimal transferredQty = item.getAcceptedQty();
                if (transferredQty == null) transferredQty = java.math.BigDecimal.ZERO;

                java.math.BigDecimal transferredBags = item.getIssuingBags() != null
                        ? new java.math.BigDecimal(item.getIssuingBags())
                        : (item.getNoOfBags() != null ? new java.math.BigDecimal(item.getNoOfBags()) : java.math.BigDecimal.ZERO);

                Long fromWarehouseId = stockTransfer.getFromGodown() != null
                        ? stockTransfer.getFromGodown().getId() : null;

                // Use lotNo-scoped lookup to restore the correct source record
                List<ItemInwardStockDetails> sourceDetails;
                if (item.getLotNo() != null && !item.getLotNo().isEmpty()) {
                    sourceDetails = itemInwardStockDetailsRepository.findByItemIdAndWarehouseIdAndLotNo(
                            item.getItemId(), fromWarehouseId, item.getLotNo());
                } else {
                    sourceDetails = itemInwardStockDetailsRepository.findByItemIdAndWarehouseId(
                            item.getItemId(), fromWarehouseId);
                }

                if (sourceDetails != null && !sourceDetails.isEmpty()) {
                    ItemInwardStockDetails sourceDetail = sourceDetails.get(0);
                    sourceDetail.setAvailableQty(
                            sourceDetail.getAvailableQty() != null
                                    ? sourceDetail.getAvailableQty().add(transferredQty)
                                    : transferredQty);
                    sourceDetail.setAvailableBags(
                            sourceDetail.getAvailableBags() != null
                                    ? sourceDetail.getAvailableBags().add(transferredBags)
                                    : transferredBags);
                    itemInwardStockDetailsRepository.save(sourceDetail);
                }
            }
        }

        // Step 3: Delete destination details scoped to this transfer's inventories
        // (avoids cross-module collisions from the unscoped deleteByOrderItemId)
        for (ItemInventory destInventory : destInventories) {
            itemInwardStockDetailsRepository.deleteByItemInventoryId(destInventory.getId());
            itemInventoryRepository.delete(destInventory);
        }
    }
}
