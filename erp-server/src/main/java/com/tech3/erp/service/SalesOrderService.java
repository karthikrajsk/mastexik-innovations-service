package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.SalesOrderConsigneeAddressDTO;
import com.tech3.erp.dto.SalesOrderDTO;
import com.tech3.erp.entity.Address;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.Consignee;
import com.tech3.erp.dto.SalesOrderItemDTO;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.entity.SalesOrderItem;
import com.tech3.erp.repository.AddressRepository;
import com.tech3.erp.repository.ApprovalRepository;
import com.tech3.erp.repository.BaleRollPackingRepository;
import com.tech3.erp.repository.ConsigneeRepository;
import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.CustomerRepository;
import com.tech3.erp.repository.DyeingWorkOrderRepository;
import com.tech3.erp.repository.FabricCategoryRepository;
import com.tech3.erp.repository.FabricDispatchForDyeingRepository;
import com.tech3.erp.repository.FabricTypeRepository;
import com.tech3.erp.repository.GenerateInvoiceRepository;
import com.tech3.erp.repository.GeneratePackingRepository;
import com.tech3.erp.repository.PurchaseOrderItemRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.repository.SizingBeamDetailsRepository;
import com.tech3.erp.repository.WeavingContractRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SalesOrderService {


@Autowired
    private BeamOutwardRepository beamOutwardRepository;
    @Autowired
    private BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository;
    @Autowired
    private BaleRollPackingRepository baleRollPackingRepository;
    @Autowired
    private DyeingWorkOrderRepository dyeingWorkOrderRepository;
    @Autowired
    private FabricDispatchForDyeingRepository fabricDispatchForDyeingRepository;
    @Autowired
    private GeneratePackingRepository generatePackingRepository;
    @Autowired
    private GenerateInvoiceRepository generateInvoiceRepository;
    @Autowired
    private SizingBeamDetailsRepository sizingBeamDetailsRepository;
    @Autowired
    private WeavingContractRepository weavingContractRepository;
    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private com.tech3.erp.repository.ApplicationUserRepository applicationUserRepository;
    
    private final SalesOrderRepository salesOrderRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final FabricTypeRepository fabricTypeRepository;
    private final FabricCategoryRepository fabricCategoryRepository;
    private final ConsigneeRepository consigneeRepository;
    private final AddressRepository addressRepository;


    public SalesOrderService(
            SalesOrderRepository salesOrderRepository,
            CustomerRepository customerRepository,
            CompanyRepository companyRepository,
            FabricTypeRepository fabricTypeRepository,
            FabricCategoryRepository fabricCategoryRepository,
            ConsigneeRepository consigneeRepository,
            AddressRepository addressRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.fabricTypeRepository = fabricTypeRepository;
        this.fabricCategoryRepository = fabricCategoryRepository;
        this.consigneeRepository = consigneeRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public SalesOrderDTO saveSalesOrder(SalesOrderDTO dto) {
        SalesOrder order = mapDtoToEntity(dto, new SalesOrder());
        
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required for sales order generation");
        }

        order.setCompanyId(dto.getCompanyId());
        
        // if (dto.getSalesOrderNo() == null || dto.getSalesOrderNo().trim().isEmpty()) {
        //     String generatedSoNo = generateSalesOrderNo(dto.getCompanyId(), dto.getOrderDate());
        //     dto.setSalesOrderNo(generatedSoNo);
        // }

        if (dto.getInternalOrderNo() == null || dto.getInternalOrderNo().trim().isEmpty()) {
            dto.setInternalOrderNo(generateInternalOrderNoFallback(dto.getOrderDate()));
        }
        
        order = mapDtoToEntity(dto, order);
        
        List<SalesOrderItem> items = mapItemDtosToEntities(dto.getItems(), order);
        order.setItems(items);

        SalesOrder savedOrder = salesOrderRepository.save(order);

        return mapEntityToDto(savedOrder);
    }

    public List<SalesOrderDTO> getAllSalesOrders() {
        return salesOrderRepository.findAll().stream()
                 .sorted(Comparator.comparing(SalesOrder::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
    
    public PaginatedResponse<SalesOrderDTO> getAllSalesOrdersWithPagination(Long offset, Long limit, String department) {
        String filterDept = getCurrentUserDepartment();
        if (filterDept == null) {
            filterDept = department;
        }
        List<Long> ids;
        if (filterDept != null && !filterDept.trim().isEmpty()) {
            ids = salesOrderRepository.findIdsByDepartment(filterDept.trim());
        } else {
            ids = salesOrderRepository.findAllIds();
        }
        return buildPaginatedResponse(
                ids,
                offset,
                limit,
                allIds -> allIds.isEmpty() ? new ArrayList<>() : salesOrderRepository.findByIdsWithItems(allIds)
        );
    }

    public SalesOrderDTO getSalesOrderById(Long id) {
        SalesOrder order = salesOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SalesOrder not found with id: " + id));

        return mapEntityToDto(order);
    }

    @Transactional
    public SalesOrderDTO updateSalesOrder(Long id, SalesOrderDTO dto) {
        SalesOrder existingOrder = salesOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SalesOrder not found with id: " + id));
        
        dto.setSalesOrderNo(existingOrder.getSalesOrderNo());
        dto.setInternalOrderNo(existingOrder.getInternalOrderNo());

        if (dto.getCompanyId() != null) {
            existingOrder.setCompanyId(dto.getCompanyId());
        }

        mapDtoToEntity(dto, existingOrder);

        List<SalesOrderItem> updatedItems = mapItemDtosToEntities(dto.getItems(), existingOrder);
        existingOrder.getItems().clear();
        existingOrder.getItems().addAll(updatedItems);

        SalesOrder savedOrder = salesOrderRepository.save(existingOrder);
        return mapEntityToDto(savedOrder);
    }

    @Transactional
    public void deleteSalesOrder(Long id) {
        if (!salesOrderRepository.existsById(id)) {
            throw new EntityNotFoundException("SalesOrder not found with id: " + id);
        }
        
        try {
            salesOrderRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete SalesOrder because it is referenced by other");
        }
    }
    
    private String generateSalesOrderNo(Long companyId, Date orderDate) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + companyId));
        String cmpCode = company.getCompanyCode();

        LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String fy = getFinancialYear(localDate);

        String prefix = "SO-" + cmpCode + "-" + fy + "-";

        Integer maxNum = salesOrderRepository.findMaxOrderNumberByPrefix(prefix, companyId);
        int nextNum = (maxNum != null ? maxNum : 0) + 1;

        return prefix + String.format("%04d", nextNum);
    }

    private String generateInternalOrderNoFallback(Date orderDate) {
        LocalDate localDate = orderDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String datePart = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        java.util.Random random = new java.util.Random();
        int randomNum = random.nextInt(9000) + 1000;
        return "JVT-" + datePart + "-" + randomNum;
    }
    
    private String getFinancialYear(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int fyStartYear = (month >= 4) ? year : year - 1;
        int fy1 = fyStartYear % 100;
        int fy2 = (fyStartYear + 1) % 100;
        return String.format("%02d-%02d", fy1, fy2);
    }

    private PaginatedResponse<SalesOrderDTO> buildPaginatedResponse(List<Long> allIds, Long offset, Long limit, Function<List<Long>, List<SalesOrder>> fetcher) {
        long off = offset != null ? Math.max(0, offset) : 0;
        long lim = (limit != null && limit > 0) ? limit : 10;
        long total = allIds.size();
        int start = (int) Math.min(off, total);
        if (start >= total) {
            return new PaginatedResponse<>(total, false, off > 0 && total > 0, off, lim, new ArrayList<>());
        }
        int end = (int) Math.min(off + lim, total);
        List<Long> pageIds = allIds.subList(start, end);
        List<SalesOrder> orders = pageIds.isEmpty() ? new ArrayList<>() : fetcher.apply(pageIds);
        List<SalesOrderDTO> dtos = orders.stream()
                .map(this::mapEntityToDto)
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

    private SalesOrder mapDtoToEntity(SalesOrderDTO dto, SalesOrder order) {
        order.setSalesOrderNo(dto.getSalesOrderNo());
        order.setOrderDate(dto.getOrderDate());
        order.setBuyerCustomerId(dto.getBuyerCustomerId());
        order.setBuyerPoNo(dto.getBuyerPoNo());
        order.setDeliverToId(dto.getDeliverToId());
        order.setDeliverToAddressId(dto.getDeliverToAddressId());
        order.setCurrencyId(dto.getCurrencyId());
        order.setExchangeRate(dto.getExchangeRate());
        order.setModeOfShipmentId(dto.getModeOfShipmentId());
        order.setShipmentTermsId(dto.getShipmentTermsId());
        order.setPaymentTermsId(dto.getPaymentTermsId());
        order.setTermsConditionsId(dto.getTermsConditionsId());
        order.setActiveFlag(dto.getActiveFlag());
        order.setInternalOrderNo(dto.getInternalOrderNo());
        order.setPackingTypeId(dto.getPackingTypeId());
        order.setTaxId(dto.getTaxId());
        order.setExportType(dto.getExportType());
        order.setOrderTypeId(dto.getOrderTypeId());
        order.setCompanyId(dto.getCompanyId());
        order.setUserId(dto.getUserId());
        order.setIsApproved(dto.getIsApproved());
        order.setIsComplete(dto.getIsComplete());
        order.setInternalRefNo(dto.getInternalRefNo());
        if (dto.getApproveDate() != null) {
            order.setApproveDate(dto.getApproveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        if (dto.getReadyDate() != null) {
            order.setReadyDate(dto.getReadyDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        order.setComments(dto.getComments());
        order.setDepartment(dto.getDepartment());

        return order;
    }

    private List<SalesOrderItem> mapItemDtosToEntities(List<SalesOrderItemDTO> itemDtos, SalesOrder order) {
        List<SalesOrderItem> items = new ArrayList<>();
        if (itemDtos != null) {
            for (SalesOrderItemDTO itemDto : itemDtos) {
                SalesOrderItem item = new SalesOrderItem();
                item.setSalesOrder(order);
                item.setFabricTypeId(itemDto.getFabricTypeId());
                item.setQuality(itemDto.getQuality());
                item.setBuyerProduct(itemDto.getBuyerProduct());
                item.setOrderQty(itemDto.getOrderQty());
                item.setPricePerUnit(itemDto.getPricePerUnit());
                item.setUomId(itemDto.getUomId());
                item.setDeliveryDate(itemDto.getDeliveryDate());
                item.setRemarks(itemDto.getRemarks());
                item.setFabricMasterTypeId(itemDto.getFabricMasterTypeId());
                item.setFabricCategoryId(itemDto.getFabricCategoryId());
                item.setFabricMasterId(itemDto.getFabricMasterId());
                item.setActiveFlag(itemDto.getActiveFlag());
                item.setFabricCode(itemDto.getFabricCode());

                // Discount & Financial calculations
                double pricePerUnit = itemDto.getPricePerUnit() != null ? itemDto.getPricePerUnit() : 0.0;
                if (pricePerUnit < 0) {
                    throw new IllegalArgumentException("Price / Unit cannot be negative");
                }

                String discountType = itemDto.getDiscountType() != null ? itemDto.getDiscountType() : "Percentage";
                double discountValue = itemDto.getDiscountValue() != null ? itemDto.getDiscountValue() : 0.0;
                if (discountValue < 0) {
                    throw new IllegalArgumentException("Discount Value cannot be negative");
                }

                double discountAmount = 0.0;
                if (pricePerUnit > 0 && discountValue > 0) {
                    if ("Percentage".equalsIgnoreCase(discountType) || "%".equalsIgnoreCase(discountType)) {
                        if (discountValue > 100) {
                            throw new IllegalArgumentException("Discount Percentage cannot be greater than 100");
                        }
                        discountAmount = pricePerUnit * (discountValue / 100.0);
                    } else if ("Amount".equalsIgnoreCase(discountType) || "₹".equalsIgnoreCase(discountType)) {
                        if (discountValue > pricePerUnit) {
                            throw new IllegalArgumentException("Discount Amount per unit cannot be greater than Price / Unit");
                        }
                        discountAmount = discountValue;
                    }
                }

                double netPricePerUnit = Math.max(0.0, pricePerUnit - discountAmount);
                int orderQty = itemDto.getOrderQty() != null ? itemDto.getOrderQty() : 0;
                double totalAmount = netPricePerUnit * orderQty;

                double gstPercent = itemDto.getGstPercent() != null ? itemDto.getGstPercent() : 0.0;
                double gstAmount = totalAmount * (gstPercent / 100.0);
                double finalAmount = totalAmount + gstAmount;

                item.setDiscountType(discountType);
                item.setDiscountValue(discountValue);
                item.setDiscountAmount(discountAmount);
                item.setNetPricePerUnit(netPricePerUnit);
                item.setTotalAmount(totalAmount);
                item.setGstPercent(gstPercent);
                item.setGstAmount(gstAmount);
                item.setFinalAmount(finalAmount);

                items.add(item);
            }
        }
        return items;
    }

    private SalesOrderDTO mapEntityToDto(SalesOrder order) {
        SalesOrderDTO dto = new SalesOrderDTO();
        dto.setId(order.getId());
        dto.setSalesOrderNo(order.getSalesOrderNo());
        dto.setOrderDate(order.getOrderDate());
        dto.setBuyerCustomerId(order.getBuyerCustomerId());
        dto.setBuyerPoNo(order.getBuyerPoNo());
        dto.setDeliverToId(order.getDeliverToId());
        dto.setDeliverToAddressId(order.getDeliverToAddressId());
        dto.setCurrencyId(order.getCurrencyId());
        dto.setExchangeRate(order.getExchangeRate());
        dto.setModeOfShipmentId(order.getModeOfShipmentId());
        dto.setShipmentTermsId(order.getShipmentTermsId());
        dto.setPaymentTermsId(order.getPaymentTermsId());
        dto.setTermsConditionsId(order.getTermsConditionsId());
        dto.setActiveFlag(order.getActiveFlag());
        dto.setInternalOrderNo(order.getInternalOrderNo());
        dto.setPackingTypeId(order.getPackingTypeId());
        dto.setTaxId(order.getTaxId());
        dto.setExportType(order.getExportType());
        dto.setOrderTypeId(order.getOrderTypeId());
        dto.setCompanyId(order.getCompanyId());
        dto.setUserId(order.getUserId());
        dto.setIsApproved(order.getIsApproved() != null ? order.getIsApproved() : (short) 0);
        dto.setIsComplete(order.getIsComplete() != null ? order.getIsComplete() : (short) 0);
        dto.setInternalRefNo(order.getInternalRefNo());
        if (order.getApproveDate() != null) {
            dto.setApproveDate(Date.from(order.getApproveDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        if (order.getReadyDate() != null) {
            dto.setReadyDate(Date.from(order.getReadyDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        dto.setComments(order.getComments());
        dto.setDepartment(order.getDepartment());

        customerRepository.findById(order.getBuyerCustomerId())
        .ifPresent(customer -> dto.setBuyerCustomerName(customer.getCustomerName()));
        
        List<SalesOrderItemDTO> itemDtos = new ArrayList<>();
        if (order.getItems() != null) {
            for (SalesOrderItem item : order.getItems()) {
                SalesOrderItemDTO itemDto = new SalesOrderItemDTO();
                itemDto.setFabricTypeId(item.getFabricTypeId());
                itemDto.setQuality(item.getQuality());
                itemDto.setBuyerProduct(item.getBuyerProduct());
                itemDto.setOrderQty(item.getOrderQty());
                itemDto.setPricePerUnit(item.getPricePerUnit());
                itemDto.setDiscountType(item.getDiscountType() != null ? item.getDiscountType() : "Percentage");
                itemDto.setDiscountValue(item.getDiscountValue() != null ? item.getDiscountValue() : 0.0);
                itemDto.setDiscountAmount(item.getDiscountAmount() != null ? item.getDiscountAmount() : 0.0);
                itemDto.setNetPricePerUnit(item.getNetPricePerUnit() != null ? item.getNetPricePerUnit() : (item.getPricePerUnit() != null ? item.getPricePerUnit() : 0.0));
                itemDto.setUomId(item.getUomId());
                itemDto.setTotalAmount(item.getTotalAmount());
                itemDto.setGstPercent(item.getGstPercent());
                itemDto.setGstAmount(item.getGstAmount());
                itemDto.setFinalAmount(item.getFinalAmount());
                itemDto.setDeliveryDate(item.getDeliveryDate());
                itemDto.setRemarks(item.getRemarks());
                itemDto.setFabricMasterTypeId(item.getFabricMasterTypeId());
                itemDto.setFabricCategoryId(item.getFabricCategoryId());
                itemDto.setFabricMasterId(item.getFabricMasterId()); 
                itemDto.setActiveFlag(item.getActiveFlag());
                itemDto.setFabricCode(item.getFabricCode());

                itemDtos.add(itemDto);
            }
        }
        dto.setItems(itemDtos);
        return dto;
    }
    
    public List<SalesOrderDTO> getSalesOrdersByCustomerId(Long customerId) {
        String filterDept = getCurrentUserDepartment();
        List<SalesOrder> orders = salesOrderRepository.findByBuyerCustomerId(customerId);
        
        java.util.stream.Stream<SalesOrder> stream = orders.stream();
        if (filterDept != null && !filterDept.trim().isEmpty()) {
            stream = stream.filter(o -> filterDept.equalsIgnoreCase(o.getDepartment()));
        }

        return stream
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public List<SalesOrderDTO> getAllSalesOrdersByCompanyId(Long companyId) {
        String filterDept = getCurrentUserDepartment();
        List<SalesOrder> orders;
        if (filterDept != null && !filterDept.trim().isEmpty()) {
            orders = salesOrderRepository.findByCompanyIdAndDepartment(companyId, filterDept.trim());
        } else {
            orders = salesOrderRepository.findByCompanyId(companyId);
        }
        return orders.stream()
                .sorted(Comparator.comparing(SalesOrder::getId).reversed())
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
    
    public PaginatedResponse<SalesOrderDTO> getAllSalesOrdersByCompanyIdWithPagination(Long companyId, Long offset, Long limit, String department) {
        if (companyId == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        String filterDept = getCurrentUserDepartment();
        if (filterDept == null) {
            filterDept = department;
        }
        List<Long> ids;
        if (filterDept != null && !filterDept.trim().isEmpty()) {
            ids = salesOrderRepository.findIdsByCompanyIdAndDepartment(companyId, filterDept.trim());
        } else {
            ids = salesOrderRepository.findIdsByCompanyId(companyId);
        }
        return buildPaginatedResponse(
                ids,
                offset,
                limit,
                allIds -> allIds.isEmpty() ? new ArrayList<>() : salesOrderRepository.findByCompanyIdAndIds(companyId, allIds)
        );
    }
    
    public SalesOrderDTO getSalesOrderByIdAndCompany(Long id, Long companyId) {
        SalesOrder order = salesOrderRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("SalesOrder not found with id: " + id + " and companyId: " + companyId));
        return mapEntityToDto(order);
    }
    
    @Transactional
    public SalesOrderDTO updateSalesOrderByCompany(Long id, Long companyId, SalesOrderDTO dto) {
        SalesOrder existingOrder = salesOrderRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("SalesOrder not found with id: " + id + " and companyId: " + companyId));
        dto.setSalesOrderNo(existingOrder.getSalesOrderNo());
        dto.setInternalOrderNo(existingOrder.getInternalOrderNo());
        dto.setCompanyId(existingOrder.getCompanyId());

        mapDtoToEntity(dto, existingOrder);

        List<SalesOrderItem> updatedItems = mapItemDtosToEntities(dto.getItems(), existingOrder);
        existingOrder.getItems().clear();
        existingOrder.getItems().addAll(updatedItems);
        SalesOrder savedOrder = salesOrderRepository.save(existingOrder);
        return mapEntityToDto(savedOrder);
    }
    
    @Transactional
    public void deleteSalesOrderByCompany(Long id, Long companyId) {
        if (salesOrderRepository.findByIdAndCompanyId(id, companyId).isEmpty()) {
            throw new EntityNotFoundException("SalesOrder not found with id: " + id + " and companyId: " + companyId);
        }
        try {
            //  validateDependencies(id);
            salesOrderRepository.deleteByIdAndCompanyId(id, companyId);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete SalesOrder because it is referenced by other");
        }
    }
    
    // private void validateDependencies(Long id) {
    //     if (weavingContractRepository.existsBySalesOrderNo(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Weaving Contract.");
    //     }
    //     if (beamOutwardRepository.existsBySalesOrderId(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Beam Outward.");
    //     }
    //     if (beamInwardBeamDetailsRepository.existsBySalesOrderId(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Beam Inward.");
    //     }
    //     if (sizingBeamDetailsRepository.existsBySalesOrderId(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Sizing Beam Details.");
    //     }
    //     if (baleRollPackingRepository.existsBySalesOrderId(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Bale/Roll Packing.");
    //     }
    //     if (dyeingWorkOrderRepository.existsBySalesOrderNo(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Dyeing Work Order.");
    //     }
    //     if (fabricDispatchForDyeingRepository.existsBySalesOrderId(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Fabric Dispatch.");
    //     }
    //     if (generatePackingRepository.existsBySalesOrderId(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Packing List.");
    //     }
    //     if (generateInvoiceRepository.existsBySalesOrderId(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Invoice.");
    //     }
      
    //     if (purchaseOrderItemRepository.existsBySalesOrderId(id)) {
    //         throw new IllegalStateException("This Sales Order cannot be permanently deleted because it is linked to Purchase Order.");
    //     }
    // }

    public SalesOrderConsigneeAddressDTO getConsigneeAddressBySalesOrderId(Long salesOrderId) {
        SalesOrder order = salesOrderRepository.findById(salesOrderId)
                .orElseThrow(() -> new EntityNotFoundException("SalesOrder not found with id: " + salesOrderId));

        Long deliverToId = order.getDeliverToId();
        Long deliverToAddressId = order.getDeliverToAddressId();

        SalesOrderConsigneeAddressDTO result = new SalesOrderConsigneeAddressDTO();

        if (deliverToId == null) {
            return result;
        }

        Consignee consignee = consigneeRepository.findById(deliverToId).orElse(null);
        if (consignee == null) {
            return result;
        }

        result.setConsigneeId(consignee.getId());
        result.setConsigneeName(consignee.getConsigneeName());
        result.setGstNo(consignee.getGstno());

        Address address = null;
        List<Address> addresses = consignee.getAddresses();

        if (deliverToAddressId != null && addresses != null && !addresses.isEmpty()) {
            address = addresses.stream()
                    .filter(a -> deliverToAddressId.equals(a.getId()))
                    .findFirst()
                    .orElse(null);
        }

        if (address == null && addresses != null && !addresses.isEmpty()) {
            address = addresses.stream()
                    .filter(a -> Boolean.TRUE.equals(a.getActiveFlag()))
                    .findFirst()
                    .orElse(addresses.get(0));
        }

        if (address == null && deliverToAddressId != null) {
            address = addressRepository.findById(deliverToAddressId).orElse(null);
        }

        if (address != null) {
            result.setAddressId(address.getId());
            result.setAddressLine1(address.getLine1());
            result.setAddressLine2(address.getLine2());
            result.setPinCode(address.getPinCode());
            if (address.getCity() != null) {
                result.setCityName(address.getCity().getCityName());
            }
            if (address.getState() != null) {
                result.setStateName(address.getState().getStateName());
                result.setStateCode(address.getState().getStateCode());
            }
            if (address.getCountry() != null) {
                result.setCountryName(address.getCountry().getCountryName());
            }
        }

        return result;
    }

    private String getCurrentUserDepartment() {
        try {
            jakarta.servlet.http.HttpServletRequest request = 
                ((org.springframework.web.context.request.ServletRequestAttributes) 
                org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes()).getRequest();
            String userIdStr = request.getHeader("X-User-Id");
            if (userIdStr == null) {
                userIdStr = request.getParameter("userId");
            }
            if (userIdStr != null) {
                Long userId = Long.valueOf(userIdStr);
                java.util.Optional<com.tech3.erp.entity.ApplicationUser> userOpt = applicationUserRepository.findById(userId);
                if (userOpt.isPresent()) {
                    String role = userOpt.get().getRole();
                    if (role != null) {
                        String roleLower = role.trim().toLowerCase();
                        if (java.util.List.of("weaving", "brushing", "garments").contains(roleLower)) {
                            return roleLower.substring(0, 1).toUpperCase() + roleLower.substring(1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Log or ignore if outside request context
        }
        return null;
    }
}
