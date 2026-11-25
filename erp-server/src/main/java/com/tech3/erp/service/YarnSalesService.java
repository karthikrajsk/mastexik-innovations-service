package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.YarnSalesDTO;
import com.tech3.erp.dto.YarnSalesItemDTO;
import com.tech3.erp.entity.YarnSales;
import com.tech3.erp.entity.YarnSalesItem;
import com.tech3.erp.repository.GenerateInvoiceRepository;
import com.tech3.erp.repository.YarnChallanRepository;
import com.tech3.erp.repository.YarnSalesRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class YarnSalesService {

    private static final Logger logger = LoggerFactory.getLogger(YarnSalesService.class);

    @Autowired
    private YarnSalesRepository yarnSalesRepo;

    @Autowired
    private YarnChallanRepository yarnChallanRepo;

    @Autowired
    private GenerateInvoiceRepository generateInvoiceRepo;

    public YarnSales create(YarnSalesDTO dto) {
        YarnSales yarnSales = new YarnSales();

        yarnSales.setOrderDate(dto.getOrderDate());
        yarnSales.setBuyerId(dto.getBuyerId());
        yarnSales.setDeliveryAtId(dto.getDeliveryAtId());
        yarnSales.setGstTypeId(dto.getGstTypeId());
        yarnSales.setShipmentTermsId(dto.getShipmentTermsId());
        yarnSales.setPaymentTermsId(dto.getPaymentTermsId());
        yarnSales.setTermsConditionsId(dto.getTermsConditionsId());
        yarnSales.setModeOfShipmentId(dto.getModeOfShipmentId());
        yarnSales.setCompanyId(dto.getCompanyId());
        yarnSales.setActiveFlag(true);

        String yarnSalesNoFromDto = dto.getYarnSalesNo();
        boolean hasYarnSalesNo = yarnSalesNoFromDto != null && !yarnSalesNoFromDto.trim().isEmpty();
        yarnSales.setYarnSalesNo(hasYarnSalesNo ? yarnSalesNoFromDto : null);

        List<YarnSalesItem> items = (dto.getItems() == null ? new ArrayList<YarnSalesItemDTO>() : dto.getItems())
                .stream()
                .filter((YarnSalesItemDTO i) -> i.getYarnId() != null)
                .map((YarnSalesItemDTO i) -> {
                    YarnSalesItem item = new YarnSalesItem();
                    item.setYarnId(i.getYarnId());
                    item.setMillId(i.getMillId());
                    item.setOrderQuantity(i.getOrderQuantity());
                    item.setRatePerUnit(i.getRatePerUnit());
                    item.setUomId(i.getUomId());
                    item.setTaxableValue(i.getTaxableValue());
                    item.setGstPercentId(i.getGstPercentId());
                    item.setGstAmount(i.getGstAmount());
                    item.setTotalAmount(i.getTotalAmount());
                    item.setYarnSales(yarnSales);
                    item.setActiveFlag(i.getActiveFlag() != null ? i.getActiveFlag() : true);
                    return item;
                }).collect(Collectors.toList());

        yarnSales.setItems(items);
        YarnSales saved = yarnSalesRepo.save(yarnSales);
        
        if (!hasYarnSalesNo) {
            String yarnSalesNo = generateYarnSalesNo(dto.getOrderDate(), saved.getId());
            saved.setYarnSalesNo(yarnSalesNo);
            saved = yarnSalesRepo.save(saved);
        }
        
        return saved;
    }

    private String generateYarnSalesNo(LocalDate orderDate, Long id) {
        String fy = getFinancialYear(orderDate);
        String prefix = "YS/" + fy + "/";
        String sequence = String.format("%04d", id);
        return prefix + sequence;
    }

    private String getFinancialYear(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int fyStartYear = (month >= 4) ? year : year - 1;
        int fy1 = fyStartYear % 100;
        int fy2 = (fyStartYear + 1) % 100;
        return String.format("%02d-%02d", fy1, fy2);
    }

    public List<YarnSales> getAll() {
        return yarnSalesRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public PaginatedResponse<YarnSales> getAllWithPagination(Long offset, Long limit, Long companyId) {
        List<YarnSales> list = companyId == null ? getAll() : getAllByCompanyId(companyId);
        return PaginationHelper.paginate(list, offset, limit);
    }

    public List<YarnSales> getAllByCompanyId(Long companyId) {
        return yarnSalesRepo.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id"));
    }

    public YarnSales getById(Long id) {
        return getById(id, null);
    }

    public YarnSales getById(Long id, Long companyId) {
        YarnSales yarnSales = yarnSalesRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("YarnSales not found with id: " + id));
        if (companyId != null && (yarnSales.getCompanyId() == null || !companyId.equals(yarnSales.getCompanyId()))) {
            throw new EntityNotFoundException("YarnSales not found for the specified companyId");
        }
        return yarnSales;
    }

    public YarnSales update(Long id, YarnSalesDTO dto) {
        return update(id, dto, null);
    }

    public YarnSales update(Long id, YarnSalesDTO dto, Long companyId) {
        YarnSales yarnSales = yarnSalesRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("YarnSales not found with id: " + id));
        if (companyId != null && (yarnSales.getCompanyId() == null || !companyId.equals(yarnSales.getCompanyId()))) {
            throw new EntityNotFoundException("YarnSales not found for the specified companyId");
        }

        yarnSales.setOrderDate(dto.getOrderDate());
        yarnSales.setBuyerId(dto.getBuyerId());
        yarnSales.setDeliveryAtId(dto.getDeliveryAtId());
        yarnSales.setGstTypeId(dto.getGstTypeId());
        yarnSales.setShipmentTermsId(dto.getShipmentTermsId());
        yarnSales.setPaymentTermsId(dto.getPaymentTermsId());
        yarnSales.setTermsConditionsId(dto.getTermsConditionsId());
        yarnSales.setModeOfShipmentId(dto.getModeOfShipmentId());
        yarnSales.setCompanyId(dto.getCompanyId());

        updateItems(yarnSales, dto.getItems());

        return yarnSalesRepo.save(yarnSales);
    }

    private void updateItems(YarnSales yarnSales, List<YarnSalesItemDTO> itemDTOs) {
        if (itemDTOs == null) return;
        Map<Long, YarnSalesItem> existing = yarnSales.getItems().stream().collect(Collectors.toMap(YarnSalesItem::getId, it -> it));
        List<YarnSalesItem> updated = new ArrayList<>();
        for (YarnSalesItemDTO dto : itemDTOs) {
            if (dto.getYarnId() == null) continue;
            YarnSalesItem item = (dto.getId() != null && existing.containsKey(dto.getId())) ? existing.remove(dto.getId()) : new YarnSalesItem();
            item.setYarnSales(yarnSales);
            item.setYarnId(dto.getYarnId());
            item.setMillId(dto.getMillId());
            item.setOrderQuantity(dto.getOrderQuantity());
            item.setRatePerUnit(dto.getRatePerUnit());
            item.setUomId(dto.getUomId());
            item.setTaxableValue(dto.getTaxableValue());
            item.setGstPercentId(dto.getGstPercentId());
            item.setGstAmount(dto.getGstAmount());
            item.setTotalAmount(dto.getTotalAmount());
            item.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
            updated.add(item);
        }
        yarnSales.getItems().clear();
        yarnSales.getItems().addAll(updated);
    }

    public void delete(Long id) {
        YarnSales entity = yarnSalesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("YarnSales not found with id: " + id));
        
        // Check if mapped in YarnChallan or GenerateInvoice
        if (yarnChallanRepo.existsByYarnSalesOrderId(id) || generateInvoiceRepo.existsByYarnSalesOrderId(id)) {
            String errorMsg = "Order No " + entity.getYarnSalesNo() + " is already mapped and cannot be deleted";
            logger.warn(errorMsg);
            throw new IllegalStateException(errorMsg);
        }
        
        yarnSalesRepo.delete(entity);
    }

    public void delete(Long id, Long companyId) {
        YarnSales entity = yarnSalesRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("YarnSales not found with id: " + id));
        if (companyId != null && (entity.getCompanyId() == null || !companyId.equals(entity.getCompanyId()))) {
            throw new EntityNotFoundException("YarnSales not found for the specified companyId");
        }
        
        // Check if mapped in YarnChallan or GenerateInvoice
        if (yarnChallanRepo.existsByYarnSalesOrderId(id) || generateInvoiceRepo.existsByYarnSalesOrderId(id)) {
            String errorMsg = "Order No " + entity.getYarnSalesNo() + " is already mapped and cannot be deleted";
            logger.warn(errorMsg);
            throw new IllegalStateException(errorMsg);
        }
        
        yarnSalesRepo.delete(entity);
    }
}