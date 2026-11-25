package com.tech3.erp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tech3.erp.dto.LatestPurchaseSummaryDTO;
import com.tech3.erp.dto.PurchaseHistoryRecordDTO;
import com.tech3.erp.dto.PurchaseHistoryResponseDTO;
import com.tech3.erp.entity.ItemMaster;
import com.tech3.erp.entity.PurchaseOrder;
import com.tech3.erp.entity.PurchaseOrderItem;
import com.tech3.erp.entity.Uom;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.entity.WovenFabricMaster;
import com.tech3.erp.repository.ItemMasterRepository;
import com.tech3.erp.repository.PurchaseOrderItemRepository;
import com.tech3.erp.repository.SubCategoryRepository;
import com.tech3.erp.repository.UomRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.repository.WovenFabricMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class PurchaseHistoryServiceTest {

    @Mock
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Mock
    private ItemMasterRepository itemMasterRepository;

    @Mock
    private VendorRepository vendorRepository;

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private WovenFabricMasterRepository wovenFabricMasterRepository;

    @Mock
    private UomRepository uomRepository;

    private PurchaseHistoryService service;

    @BeforeEach
    void setUp() {
        service = new PurchaseHistoryService(purchaseOrderItemRepository, itemMasterRepository, vendorRepository);
        service.setSubCategoryRepository(subCategoryRepository);
        service.setWovenFabricMasterRepository(wovenFabricMasterRepository);
        service.setUomRepository(uomRepository);
    }

    private void setEntityId(Object entity, Long id) {
        try {
            Method setter = entity.getClass().getMethod("setId", Long.class);
            setter.invoke(entity, id);
        } catch (Exception ignored) {
            try {
                Field field = entity.getClass().getDeclaredField("id");
                field.setAccessible(true);
                field.set(entity, id);
            } catch (Exception e) {
                throw new RuntimeException("Could not set ID on entity", e);
            }
        }
    }

    private ItemMaster createMockItem(Long id, String name, String code, String uom) {
        ItemMaster item = new ItemMaster();
        setEntityId(item, id);
        item.setItemName(name);
        item.setItemCode(code);
        item.setUomName(uom);
        return item;
    }

    private PurchaseOrderItem createMockOrderItem(Long id, PurchaseOrder po, Long itemId, BigDecimal qty,
                                                  String unit, BigDecimal price, BigDecimal netAmount,
                                                  BigDecimal gstAmount, BigDecimal finalAmount) {
        PurchaseOrderItem item = new PurchaseOrderItem();
        setEntityId(item, id);
        item.setPurchaseOrder(po);
        item.setItemId(itemId);
        item.setQuantity(qty);
        item.setUnit(unit);
        item.setPrice(price);
        item.setNetAmount(netAmount);
        item.setGstAmount(gstAmount);
        item.setFinalAmount(finalAmount);
        item.setActiveFlag(true);
        return item;
    }

    private PurchaseOrder createMockOrder(Long id, String poNo, LocalDate poDate, Long vendorId, Long companyId, Short isApproved) {
        PurchaseOrder po = new PurchaseOrder();
        setEntityId(po, id);
        po.setPoNo(poNo);
        po.setPoDate(poDate);
        po.setVendorId(vendorId);
        po.setCompanyId(companyId);
        po.setIsApproved(isApproved);
        po.setActiveFlag(true);
        return po;
    }

    @Test
    void getPurchaseHistoryThrowsWhenItemIdIsMissing() {
        assertThrows(IllegalArgumentException.class, () -> service.getPurchaseHistory(null, 1L, 0L, 10L));
        assertThrows(IllegalArgumentException.class, () -> service.getPurchaseHistory(-5L, 1L, 0L, 10L));
    }

    @Test
    void getPurchaseHistoryThrowsWhenItemNotFound() {
        when(itemMasterRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getPurchaseHistory(999L, 1L, 0L, 10L));
    }

    @Test
    void getPurchaseHistoryReturnsEmptyWhenNoHistoryFound() {
        ItemMaster item = createMockItem(10L, "Cotton Yarn 40s", "YARN-40S", "Kgs");
        when(itemMasterRepository.findById(10L)).thenReturn(Optional.of(item));
        when(purchaseOrderItemRepository.countPurchaseHistoryByItemId(10L, 1L)).thenReturn(0L);

        PurchaseHistoryResponseDTO result = service.getPurchaseHistory(10L, 1L, 0L, 10L);

        assertNotNull(result);
        assertEquals(10L, result.getItemId());
        assertEquals("Cotton Yarn 40s", result.getItemName());
        assertEquals(0L, result.getTotal());
        assertNull(result.getLatestPurchase());
        assertTrue(result.getRecords().isEmpty());
        assertFalse(result.isHasMore());
    }

    @Test
    void getPurchaseHistoryReturnsPopulatedSummaryAndSortedRecords() {
        ItemMaster item = createMockItem(15L, "Polyester Thread White", "POLY-WHT", "Cones");
        when(itemMasterRepository.findById(15L)).thenReturn(Optional.of(item));
        when(purchaseOrderItemRepository.countPurchaseHistoryByItemId(15L, 2L)).thenReturn(2L);

        PurchaseOrder po1 = createMockOrder(101L, "PO-2026-00101", LocalDate.of(2026, 10, 20), 501L, 2L, (short) 1);
        PurchaseOrderItem item1 = createMockOrderItem(1001L, po1, 15L, new BigDecimal("100.00"), "Cones",
                new BigDecimal("150.00"), new BigDecimal("15000.00"), new BigDecimal("750.00"), new BigDecimal("15750.00"));

        PurchaseOrder po2 = createMockOrder(95L, "PO-2026-00095", LocalDate.of(2026, 9, 15), 502L, 2L, (short) 1);
        PurchaseOrderItem item2 = createMockOrderItem(951L, po2, 15L, new BigDecimal("80.00"), "Cones",
                new BigDecimal("145.00"), new BigDecimal("11600.00"), new BigDecimal("580.00"), new BigDecimal("12180.00"));

        when(purchaseOrderItemRepository.findPurchaseHistoryByItemId(eq(15L), eq(2L), any(PageRequest.class)))
                .thenReturn(List.of(item1, item2));

        Vendor vendor1 = new Vendor();
        setEntityId(vendor1, 501L);
        vendor1.setVendorName("Reliable Threads Ltd");

        Vendor vendor2 = new Vendor();
        setEntityId(vendor2, 502L);
        vendor2.setVendorName("Apex Spun Mills");

        when(vendorRepository.findAllById(any())).thenReturn(List.of(vendor1, vendor2));

        PurchaseHistoryResponseDTO result = service.getPurchaseHistory(15L, 2L, 0L, 10L);

        assertNotNull(result);
        assertEquals(2L, result.getTotal());
        assertEquals(2, result.getRecords().size());

        // Latest purchase verification
        LatestPurchaseSummaryDTO summary = result.getLatestPurchase();
        assertNotNull(summary);
        assertEquals("PO-2026-00101", summary.getLastPoNumber());
        assertEquals(LocalDate.of(2026, 10, 20), summary.getLastPurchaseDate());
        assertEquals("Reliable Threads Ltd", summary.getLastVendorName());
        assertEquals(new BigDecimal("100.00"), summary.getLastPurchaseQuantity());
        assertEquals(new BigDecimal("150.00"), summary.getLastPurchaseUnitPrice());
        assertEquals(new BigDecimal("15750.00"), summary.getLastFinalAmount());

        // Records check
        PurchaseHistoryRecordDTO record1 = result.getRecords().get(0);
        assertEquals("PO-2026-00101", record1.getPoNo());
        assertEquals("Reliable Threads Ltd", record1.getVendorName());
        assertEquals("Approved", record1.getStatus());

        PurchaseHistoryRecordDTO record2 = result.getRecords().get(1);
        assertEquals("PO-2026-00095", record2.getPoNo());
        assertEquals("Apex Spun Mills", record2.getVendorName());
    }

    @Test
    void getPurchaseHistoryEnrichesFabricMasterSpecification() {
        ItemMaster item = createMockItem(25L, "Grey Greige Fabric 60x60", "FAB-6060", "Meters");
        when(itemMasterRepository.findById(25L)).thenReturn(Optional.of(item));
        when(purchaseOrderItemRepository.countPurchaseHistoryByItemId(25L, null)).thenReturn(1L);

        PurchaseOrder po = createMockOrder(200L, "PO-2026-00200", LocalDate.of(2026, 10, 25), 600L, 1L, (short) 1);
        PurchaseOrderItem poi = createMockOrderItem(2001L, po, 25L, new BigDecimal("5000.00"), "Meters",
                new BigDecimal("62.50"), new BigDecimal("312500.00"), new BigDecimal("15625.00"), new BigDecimal("328125.00"));
        poi.setFabricMasterId(88L);
        poi.setFabricCode("GC-6060");
        poi.setQuality("Cambric 60s");

        when(purchaseOrderItemRepository.findPurchaseHistoryByItemId(eq(25L), eq(null), any(PageRequest.class)))
                .thenReturn(List.of(poi));

        Vendor vendor = new Vendor();
        setEntityId(vendor, 600L);
        vendor.setVendorName("Global Weaving Corporation");
        when(vendorRepository.findAllById(any())).thenReturn(List.of(vendor));

        WovenFabricMaster fabric = new WovenFabricMaster();
        setEntityId(fabric, 88L);
        fabric.setFabricName("Cotton Cambric");
        fabric.setFabricQuality("60s Combed");
        fabric.setWarpCount(60);
        fabric.setWeftCount(60);
        fabric.setEpi(92);
        fabric.setPpi(88);
        when(wovenFabricMasterRepository.findAllById(any())).thenReturn(List.of(fabric));

        PurchaseHistoryResponseDTO result = service.getPurchaseHistory(25L, null, 0L, 10L);

        assertNotNull(result);
        assertEquals(1, result.getRecords().size());
        PurchaseHistoryRecordDTO record = result.getRecords().get(0);
        assertTrue(record.getSpecification().contains("Cotton Cambric"));
        assertTrue(record.getSpecification().contains("60x60"));
        assertTrue(record.getSpecification().contains("92/88"));
    }

    @Test
    void getPurchaseHistoryResolvesNumericUomIdToName() {
        ItemMaster item = createMockItem(30L, "Sample Accessory", "ACC-01", "5");
        when(itemMasterRepository.findById(30L)).thenReturn(Optional.of(item));
        when(purchaseOrderItemRepository.countPurchaseHistoryByItemId(30L, 1L)).thenReturn(1L);

        PurchaseOrder po = createMockOrder(301L, "PO-2026-00301", LocalDate.of(2026, 10, 26), 701L, 1L, (short) 1);
        PurchaseOrderItem item1 = createMockOrderItem(3001L, po, 30L, new BigDecimal("100.00"), "5",
                new BigDecimal("25.00"), new BigDecimal("2500.00"), new BigDecimal("125.00"), new BigDecimal("2625.00"));

        when(purchaseOrderItemRepository.findPurchaseHistoryByItemId(eq(30L), eq(1L), any(PageRequest.class)))
                .thenReturn(List.of(item1));

        Vendor vendor = new Vendor();
        setEntityId(vendor, 701L);
        vendor.setVendorName("Fastener Supplies Co");
        when(vendorRepository.findAllById(any())).thenReturn(List.of(vendor));

        Uom uom5 = new Uom();
        setEntityId(uom5, 5L);
        uom5.setUomName("Nos");
        uom5.setUomCode("NOS");
        when(uomRepository.findAllById(any())).thenReturn(List.of(uom5));

        PurchaseHistoryResponseDTO result = service.getPurchaseHistory(30L, 1L, 0L, 10L);

        assertNotNull(result);
        assertEquals("Nos", result.getUom());
        assertNotNull(result.getLatestPurchase());
        assertEquals("Nos", result.getLatestPurchase().getLastUom());
        assertEquals(1, result.getRecords().size());
        assertEquals("Nos", result.getRecords().get(0).getUom());
    }
}
