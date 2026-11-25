//service



package com.tech3.erp.service;
import com.tech3.erp.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.tech3.erp.dto.OrderDetailDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.WeavingContractDTO;
import com.tech3.erp.dto.WeavingContractItemDTO;
import com.tech3.erp.dto.YarnRequirementDTO;
import com.tech3.erp.dto.WeavingWorkOrderDTO;
import com.tech3.erp.dto.WeavingWorkOrderDetailDTO;
import com.tech3.erp.dto.WeavingWorkOrderItemDTO;
import com.tech3.erp.dto.WeavingWorkOrderYarnRequirementDTO;
import com.tech3.erp.entity.OrderDetail;
import com.tech3.erp.entity.SalesOrder;
import com.tech3.erp.entity.WeavingContract;
import com.tech3.erp.entity.WeavingContractItem;
import com.tech3.erp.entity.WeavingWorkOrder;
import com.tech3.erp.entity.WeavingWorkOrderDetail;
import com.tech3.erp.entity.WeavingWorkOrderItem;
import com.tech3.erp.entity.WeavingWorkOrderYarnRequirement;
import com.tech3.erp.entity.YarnRequirement;
import com.tech3.erp.repository.BeamGatingDetailsRepository;
import com.tech3.erp.repository.BeamInwardBeamDetailsRepository;
import com.tech3.erp.repository.BeamOutwardRepository;
import com.tech3.erp.repository.BaleRollPackingRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.FabricInspectionRepository;
import com.tech3.erp.repository.GenerateInvoiceRepository;
import com.tech3.erp.repository.GeneratePackingRepository;
import com.tech3.erp.repository.JobworkFabricReceiveRepository;
import com.tech3.erp.repository.FactoryBeamInwardRepository;
import com.tech3.erp.repository.OrderDetailRepository;
import com.tech3.erp.repository.SalesOrderItemRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.repository.SalesReturnBeamRepository;
import com.tech3.erp.repository.SizingBeamDetailsRepository;
import com.tech3.erp.repository.SizingBeamOutwardDetailRepository;
import com.tech3.erp.repository.SizingBeamOutwardRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.WeavingYarnIssueRepository;
import com.tech3.erp.repository.WeavingYarnOutwardRepository;
import com.tech3.erp.repository.WeftInwardRepository;
import com.tech3.erp.repository.YarnInwardStockDetailsRepository;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.service.WeavingWorkOrderUpdatedEvent;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeavingContractService {
    private static final Long WEAVING_CONTRACT_ORDER_TYPE_ID = 1L;

    @Autowired
    private WeavingContractRepository contractRepo;
    
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private WeavingWorkOrderService weavingWorkOrderService;

  
    @Autowired
    private YarnInwardStockDetailsRepository yarnInwardStockDetailsRepository;

    @Autowired
    private YarnMasterRepository yarnMasterRepository;
    
    @Autowired
    private SalesOrderItemRepository salesOrderItemRepository;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private WeavingWorkOrderRepository weavingWorkOrderRepository;

    @Autowired
    private WeavingYarnIssueRepository weavingYarnIssueRepository;

    @Autowired
    private WeavingYarnOutwardRepository weavingYarnOutwardRepository;

    @Autowired
    private WeftInwardRepository weftInwardRepository;

    @Autowired
    private JobworkFabricReceiveRepository jobworkFabricReceiveRepository;

    @Autowired
    private FactoryBeamInwardRepository factoryBeamInwardRepository;

    @Autowired
    private BeamOutwardRepository beamOutwardRepository;

    @Autowired
    private BeamGatingDetailsRepository beamGatingDetailsRepository;

    @Autowired
    private SizingBeamOutwardDetailRepository sizingBeamOutwardDetailRepository;

    @Autowired
    private SalesReturnBeamRepository salesReturnBeamRepository;

     @Autowired
    private SizingBeamDetailsRepository sizingBeamDetailsRepository;

    @Autowired
    private SizingBeamOutwardRepository sizingBeamOutwardRepository;

    @Autowired
    private BeamInwardBeamDetailsRepository beamInwardBeamDetailsRepository;

    @Autowired
    private GenerateInvoiceRepository generateInvoiceRepository;

    @Autowired
    private GeneratePackingRepository generatePackingRepository;

    @Autowired
    private FabricInspectionRepository fabricInspectionRepository;

    @Autowired
    private BaleRollPackingRepository baleRollPackingRepository;

    @Autowired
    private ReferenceValidationService referenceValidationService;
    
    @Transactional
    public WeavingContract createWeavingContract(WeavingContractDTO dto) {
    	  validateYarnRequirementsForDuplicates(dto.getYarnRequirements());
        WeavingContract contract = new WeavingContract();
        Long companyId = dto.getCompanyId();
        if (companyId == null) {
            companyId = getDefaultCompanyId();
        }
        String weavingContractNo = dto.getWeavingContractNo();
        if (weavingContractNo == null || weavingContractNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Weaving contract number is required");
        }
        contract.setWeavingContractNo(weavingContractNo);
        contract.setCompanyId(companyId);
          // Set sales_order_no from the first order detail
        if (dto.getOrderDetails() != null && !dto.getOrderDetails().isEmpty()) {
            validatePlannedQuantities(dto.getOrderDetails(), null);
            contract.setSalesOrderNo(dto.getOrderDetails().get(0).getSalesOrderId());
        } else {
            throw new IllegalArgumentException("At least one order detail with valid sales order ID is required");
        }
        contract.setTradingCompanyId(dto.getTradingCompanyId());
        contract.setFactoryCompanyId(dto.getFactoryCompanyId());
        if (dto.getOrderDetails() != null && !dto.getOrderDetails().isEmpty()) {
            contract.setSalesOrderNo(dto.getOrderDetails().get(0).getSalesOrderId());
        } else {
            throw new IllegalArgumentException("At least one order detail with valid sales order ID is required");
        }
        contract.setContractDate(dto.getContractDate());
        contract.setTaxId(dto.getTaxId());
        contract.setVendorId(dto.getVendorId());
        contract.setTermsConditionsId(dto.getTermsConditionsId());
        contract.setPaymentTermsId(getValidatedPaymentTermsId(dto.getPaymentTermsId()));
        contract.setOrderTypeId(dto.getOrderTypeId());
        contract.setRemarks(dto.getRemarks());
        contract.setRpm(dto.getRpm());
        contract.setEfficiency(dto.getEfficiency());
        contract.setProductCalculation(dto.getProductCalculation());
        contract.setIsComplete(dto.getIsComplete());
        contract.setActiveFlag(true);
        List<WeavingContractItem> items = dto.getItems().stream()
            .filter(itemDTO -> itemDTO.getFabricCodeId() != null)
            .map(itemDTO -> {
                WeavingContractItem item = new WeavingContractItem();
                item.setFabricCodeId(itemDTO.getFabricCodeId());
                item.setQuantity(itemDTO.getQuantity());
                item.setPickCost(itemDTO.getPickCost());
                item.setPlannedStartDate(itemDTO.getPlannedStartDate());
                item.setPlannedEndDate(itemDTO.getPlannedEndDate());
                item.setDailyTarget(itemDTO.getDailyTarget());
                item.setNumberOfLooms(itemDTO.getNumberOfLooms());
                item.setWarpLength(itemDTO.getWarpLength());
                item.setWarpCrimpPercentage(itemDTO.getWarpCrimpPercentage());
                item.setPieceLength(itemDTO.getPieceLength());
                item.setNumberOfPieces(itemDTO.getNumberOfPieces());
                item.setPpi(itemDTO.getPpi());
                item.setNoOfDays(itemDTO.getNoOfDays());
                item.setLoomType(itemDTO.getLoomType());
                item.setWeavingContract(contract);
                item.setActiveFlag(true);
                return item;
            }).collect(Collectors.toList());
        List<YarnRequirementDTO> yarnDTOs = dto.getYarnRequirements() != null ? dto.getYarnRequirements() : new ArrayList<>();
        List<YarnRequirement> yarns = yarnDTOs.stream()
            .filter(yarnDTO -> yarnDTO.getYarnType() != null && !yarnDTO.getYarnType().isEmpty())
            .map(yarnDTO -> {
                YarnRequirement yr = new YarnRequirement();
                yr.setYarnId(yarnDTO.getYarnId());
                yr.setYarnType(yarnDTO.getYarnType());
                yr.setYarnCount(yarnDTO.getYarnCount());
                yr.setGramsPerMeter(yarnDTO.getGramsPerMeter());
                yr.setTotalWeavingOrderQty(yarnDTO.getTotalWeavingOrderQty());
                yr.setTotalRequiredQty(yarnDTO.getTotalRequiredQty());
                yr.setTotalAvailableQty(yarnDTO.getTotalAvailableQty());
                yr.setWeavingContract(contract);
                yr.setActiveFlag(yarnDTO.getActiveFlag() != null ? yarnDTO.getActiveFlag() : true);
                return yr;
            }).collect(Collectors.toList());
        List<OrderDetail> orderDetails = dto.getOrderDetails().stream()
            .filter(orderDTO -> orderDTO.getSalesOrderId() != null)
            .map(orderDTO -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setSalesOrderId(orderDTO.getSalesOrderId());
                 BigDecimal resolvedOrderQty = resolveOrderQuantity(orderDTO.getSalesOrderId(), orderDTO.getOrderQuantity());
                BigDecimal plannedQty = orderDTO.getPlannedQuantity();
                orderDetail.setOrderQuantity(orderDTO.getOrderQuantity());
                orderDetail.setPlannedQuantity(orderDTO.getPlannedQuantity());
                orderDetail.setTotalQuantity(orderDTO.getTotalQuantity());
                BigDecimal remainingForRow = orderDTO.getRemainingQuantity() != null
                        ? orderDTO.getRemainingQuantity()
                        : calculateRemainingQuantity(resolvedOrderQty, plannedQty);
                orderDetail.setRemainingQuantity(remainingForRow);
                orderDetail.setWeavingContract(contract);
                orderDetail.setActiveFlag(true);
                return orderDetail;
            }).collect(Collectors.toList());
        contract.setItems(items);
        contract.setYarnRequirements(yarns);
        contract.setOrderDetails(orderDetails);
        WeavingContract saved = contractRepo.save(contract);
        if (dto.getOrderTypeId() != null && dto.getOrderTypeId().equals(1L)) {
            WeavingWorkOrderDTO wo = new WeavingWorkOrderDTO();
            wo.setWeavingWorkOrderNo(null);
            wo.setOrderDate(saved.getContractDate());
            wo.setWeavingContractId(saved.getId());
            wo.setOrderTypeId(saved.getOrderTypeId());
            wo.setTermsConditionsId(saved.getTermsConditionsId());
            wo.setPaymentTermsId(saved.getPaymentTermsId());
            wo.setRemarks(saved.getRemarks());
            wo.setGstTypeId(saved.getTaxId());
            wo.setBuyerId(saved.getTradingCompanyId() != null ? saved.getTradingCompanyId() : saved.getCompanyId());
            wo.setConsigneeId(saved.getTradingCompanyId() != null ? saved.getTradingCompanyId() : saved.getCompanyId());
            wo.setFactoryCompanyId(saved.getFactoryCompanyId() != null ? saved.getFactoryCompanyId() : saved.getCompanyId());
            wo.setTradingCompanyId(saved.getTradingCompanyId() != null ? saved.getTradingCompanyId() : saved.getCompanyId());
            List<WeavingWorkOrderItemDTO> woItems = dto.getItems().stream()
                .filter(itemDTO -> itemDTO.getFabricCodeId() != null)
                .map(itemDTO -> {
                    WeavingWorkOrderItemDTO wi = new WeavingWorkOrderItemDTO();
                    wi.setFabricCodeId(itemDTO.getFabricCodeId());
                    wi.setQuantity(itemDTO.getQuantity());
                    wi.setPickCost(itemDTO.getPickCost());
                    wi.setPlannedStartDate(itemDTO.getPlannedStartDate());
                    wi.setPlannedEndDate(itemDTO.getPlannedEndDate());
                    wi.setDailyTarget(itemDTO.getDailyTarget());
                    wi.setNumberOfLooms(itemDTO.getNumberOfLooms());
                    wi.setWarpLength(itemDTO.getWarpLength());
                    wi.setWarpCrimpPercentage(itemDTO.getWarpCrimpPercentage());
                    wi.setPieceLength(itemDTO.getPieceLength());
                    wi.setNumberOfPieces(itemDTO.getNumberOfPieces());
                    wi.setPpi(itemDTO.getPpi());
                    return wi;
                }).collect(Collectors.toList());
            List<WeavingWorkOrderYarnRequirementDTO> woYarns = yarnDTOs.stream()
                .filter(y -> y.getYarnType() != null && !y.getYarnType().isEmpty())
                .map(y -> {
                    WeavingWorkOrderYarnRequirementDTO wy = new WeavingWorkOrderYarnRequirementDTO();
                    wy.setYarnType(y.getYarnType());
                    wy.setYarnCount(y.getYarnCount());
                    wy.setGramsPerMeter(y.getGramsPerMeter());
                    wy.setTotalWeavingOrderQty(y.getTotalWeavingOrderQty());
                    wy.setTotalRequiredQty(y.getTotalRequiredQty());
                    wy.setTotalAvailableQty(y.getTotalAvailableQty());
                    return wy;
                }).collect(Collectors.toList());
            List<WeavingWorkOrderDetailDTO> woOrders = dto.getOrderDetails().stream()
                .filter(o -> o.getSalesOrderId() != null)
                .map(o -> {
                    WeavingWorkOrderDetailDTO wd = new WeavingWorkOrderDetailDTO();
                    wd.setSalesOrderId(o.getSalesOrderId());
                    wd.setOrderQuantity(o.getOrderQuantity());
                    wd.setPlannedQuantity(o.getPlannedQuantity());
                    wd.setTotalQuantity(o.getTotalQuantity());
                    return wd;
                }).collect(Collectors.toList());
            wo.setItems(woItems);
            wo.setYarnRequirements(woYarns);
            wo.setOrderDetails(woOrders);
            weavingWorkOrderService.create(wo);
        }
        populateAllYarnAvailableQties(saved);
        return saved;
    }
    
    public List<WeavingContract> getAllWeavingContract() {
        List<WeavingContract> list = contractRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        list.forEach(this::populateAllYarnAvailableQties);
        return list;
    }
    

    // public PaginatedResponse<WeavingContract> getAllWeavingContractWithPagination(Long offset, Long limit) {
    //     List<WeavingContract> all = getAllWeavingContract();
    //     for (WeavingContract contract : all) {
    //         populateAllYarnAvailableQties(contract);
    //     }
    //     return PaginationHelper.paginate(all, offset, limit);
    // }
       public PaginatedResponse<WeavingContract> getAllWeavingContractWithPagination(Long offset, Long limit) {
        List<WeavingContract> all = getAllWeavingContract();
        for (WeavingContract contract : all) {
            populateAllLazyCollections(contract);
        }
        return PaginationHelper.paginate(all, offset, limit);
    }
    

    // Get a WeavingContract by ID
    // public WeavingContract getWeavingContractById(Long id) {
    //     WeavingContract contract = contractRepo.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("WeavingContract not found with id: " + id));
    //     populateAllYarnAvailableQties(contract);
    //     return contract;
    // }

       public WeavingContract getWeavingContractById(Long id) {
        WeavingContract contract = contractRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WeavingContract not found with id: " + id));
        populateAllLazyCollections(contract);
        return contract;
    }
    
    public List<WeavingContract> getAllWeavingContractByCompany(Long companyId) {
        List<WeavingContract> list = contractRepo.findByCompanyIdOrderByIdDesc(companyId);
        list.forEach(this::populateAllYarnAvailableQties);
        return list;
    }

    public PaginatedResponse<WeavingContract> getAllWeavingContractByCompanyWithPagination(Long companyId, Long offset, Long limit) {
        List<WeavingContract> all = getAllWeavingContractByCompany(companyId);
        for (WeavingContract contract : all) {
            populateAllLazyCollections(contract);
        }
        return PaginationHelper.paginate(all, offset, limit);
    }
    

    public WeavingContract getWeavingContractByIdAndCompany(Long id, Long companyId) {
        WeavingContract contract = contractRepo.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("WeavingContract not found with id: " + id + " and companyId: " + companyId));
        populateAllYarnAvailableQties(contract);
        return contract;
    }
    
    public WeavingContract updateWeavingContract(Long id, WeavingContractDTO dto) {
    	  validateYarnRequirementsForDuplicates(dto.getYarnRequirements());
        WeavingContract contract = contractRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("WeavingContract not found with id: " + id));
        applyContractUpdates(contract, dto);
        WeavingContract saved = contractRepo.save(contract);
        syncContractToWorkOrder(saved);
        populateAllYarnAvailableQties(saved);
        return saved;
    }
    
    public WeavingContract updateWeavingContractByCompany(Long id, Long companyId, WeavingContractDTO dto) {
    	  validateYarnRequirementsForDuplicates(dto.getYarnRequirements());
        WeavingContract contract = contractRepo.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("WeavingContract not found with id: " + id + " and companyId: " + companyId));
        applyContractUpdates(contract, dto);
        WeavingContract saved = contractRepo.save(contract);
        syncContractToWorkOrder(saved);
        populateAllYarnAvailableQties(saved);
        return saved;
    }

    private void applyContractUpdates(WeavingContract contract, WeavingContractDTO dto) {
        contract.setContractDate(dto.getContractDate());
        contract.setTaxId(dto.getTaxId());
        contract.setVendorId(dto.getVendorId());
        contract.setTermsConditionsId(dto.getTermsConditionsId());
        contract.setPaymentTermsId(getValidatedPaymentTermsId(dto.getPaymentTermsId()));
        contract.setOrderTypeId(dto.getOrderTypeId());
        contract.setRemarks(dto.getRemarks());
        contract.setRpm(dto.getRpm());
        contract.setEfficiency(dto.getEfficiency());
        contract.setProductCalculation(dto.getProductCalculation());
        if (dto.getIsComplete() != null) {
            contract.setIsComplete(dto.getIsComplete());
        }
        contract.setTradingCompanyId(dto.getTradingCompanyId());
        contract.setFactoryCompanyId(dto.getFactoryCompanyId());
        if (dto.getOrderDetails() != null && !dto.getOrderDetails().isEmpty()) {
            contract.setSalesOrderNo(dto.getOrderDetails().get(0).getSalesOrderId());
        }
           // Update sales_order_no from order details
        if (dto.getOrderDetails() != null && !dto.getOrderDetails().isEmpty()) {
            validatePlannedQuantities(dto.getOrderDetails(), contract.getId());
            contract.setSalesOrderNo(dto.getOrderDetails().get(0).getSalesOrderId());
        }

        updateItems(contract, dto.getItems());
        updateYarnRequirements(contract, dto.getYarnRequirements());
        updateOrderDetails(contract, dto.getOrderDetails());
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleWeavingWorkOrderUpdated(WeavingWorkOrderUpdatedEvent event) {
        if (event == null || event.getWeavingContractId() == null || event.getOrderTypeId() == null || !event.getOrderTypeId().equals(1L)) {
            return;
        }
        Optional<WeavingContract> contractOpt = contractRepo.findById(event.getWeavingContractId());
        Optional<WeavingWorkOrder> workOrderOpt = weavingWorkOrderRepository.findById(event.getWorkOrderId());
        if (!contractOpt.isPresent() || !workOrderOpt.isPresent()) {
            return;
        }
        WeavingContract contract = contractOpt.get();
        applyWorkOrderToContract(contract, workOrderOpt.get());
        contractRepo.save(contract);
    }

    public void syncWorkOrderToContract(WeavingWorkOrder workOrder) {
        if (workOrder == null || workOrder.getWeavingContractId() == null || workOrder.getOrderTypeId() == null || !workOrder.getOrderTypeId().equals(1L)) {
            return;
        }
        Optional<WeavingContract> contractOpt = contractRepo.findById(workOrder.getWeavingContractId());
        if (!contractOpt.isPresent()) {
            return;
        }
        WeavingContract contract = contractOpt.get();
        applyWorkOrderToContract(contract, workOrder);
        contractRepo.save(contract);
    }

    private void applyWorkOrderToContract(WeavingContract contract, WeavingWorkOrder workOrder) {
        replaceCollection(contract.getItems(), mapWorkOrderItemsToContract(contract, workOrder.getItems()));
        replaceCollection(contract.getYarnRequirements(), mapWorkOrderYarnsToContract(contract, workOrder.getYarnRequirements()));
        replaceCollection(contract.getOrderDetails(), mapWorkOrderDetailsToContract(contract, workOrder.getOrderDetails()));
        contract.setTermsConditionsId(workOrder.getTermsConditionsId());
        contract.setPaymentTermsId(workOrder.getPaymentTermsId());
        contract.setRemarks(workOrder.getRemarks());
        if (!contract.getOrderDetails().isEmpty()) {
            Long salesOrderId = contract.getOrderDetails().get(0).getSalesOrderId();
            contract.setSalesOrderNo(salesOrderId);
        }
    }

    private <T> void replaceCollection(List<T> current, List<T> replacement) {
        if (current == null) {
            return;
        }
        current.clear();
        if (replacement != null && !replacement.isEmpty()) {
            current.addAll(replacement);
        }
    }

    
    private List<WeavingContractItem> mapWorkOrderItemsToContract(WeavingContract contract, List<WeavingWorkOrderItem> workItems) {
        if (workItems == null) {
            return new ArrayList<>();
        }
        Map<String, Deque<WeavingContractItem>> contractItemMap = groupContractItems(contract.getItems());
        List<WeavingContractItem> mapped = new ArrayList<>();
        for (WeavingWorkOrderItem workItem : workItems) {
            if (workItem.getFabricCodeId() == null) {
                continue;
            }
            String key = buildItemKey(workItem.getFabricCodeId());
            Deque<WeavingContractItem> deque = contractItemMap.get(key);
            WeavingContractItem contractItem = (deque != null && !deque.isEmpty()) ? deque.poll() : null;
            if (contractItem == null) {
                contractItem = new WeavingContractItem();
                contractItem.setActiveFlag(workItem.getActiveFlag() != null ? workItem.getActiveFlag() : true);
            }
            contractItem.setFabricCodeId(workItem.getFabricCodeId());
            contractItem.setQuantity(workItem.getQuantity());
            contractItem.setPickCost(workItem.getPickCost());
            contractItem.setPlannedStartDate(workItem.getPlannedStartDate());
            contractItem.setPlannedEndDate(workItem.getPlannedEndDate());
            contractItem.setDailyTarget(workItem.getDailyTarget());
            contractItem.setNumberOfLooms(workItem.getNumberOfLooms());
            contractItem.setWarpLength(workItem.getWarpLength());
            contractItem.setWarpCrimpPercentage(workItem.getWarpCrimpPercentage());
            contractItem.setPieceLength(workItem.getPieceLength());
            contractItem.setNumberOfPieces(workItem.getNumberOfPieces());
            contractItem.setPpi(workItem.getPpi());
            contractItem.setWeavingContract(contract);
            mapped.add(contractItem);
        }
        return mapped;
    }

    private List<YarnRequirement> mapWorkOrderYarnsToContract(WeavingContract contract, List<WeavingWorkOrderYarnRequirement> workYarns) {
        if (workYarns == null) {
            return new ArrayList<>();
        }
        Map<String, Deque<YarnRequirement>> yarnMap = groupContractYarns(contract.getYarnRequirements());
        List<YarnRequirement> mapped = new ArrayList<>();
        for (WeavingWorkOrderYarnRequirement workYarn : workYarns) {
            if (workYarn.getYarnType() == null || workYarn.getYarnType().isEmpty()) {
                continue;
            }
            String key = buildYarnKey(workYarn.getYarnType(), workYarn.getYarnCount());
            Deque<YarnRequirement> deque = yarnMap.get(key);
            YarnRequirement requirement = (deque != null && !deque.isEmpty()) ? deque.poll() : null;
            if (requirement == null) {
                requirement = new YarnRequirement();
                requirement.setActiveFlag(workYarn.getActiveFlag() != null ? workYarn.getActiveFlag() : true);
            }
//            requirement.setYarnId(workYarn.getYarnId());
            requirement.setYarnType(workYarn.getYarnType());
            requirement.setYarnCount(workYarn.getYarnCount());
            requirement.setGramsPerMeter(workYarn.getGramsPerMeter());
            requirement.setTotalWeavingOrderQty(workYarn.getTotalWeavingOrderQty());
            requirement.setTotalRequiredQty(workYarn.getTotalRequiredQty());
            requirement.setTotalAvailableQty(workYarn.getTotalAvailableQty());
            requirement.setWeavingContract(contract);
            mapped.add(requirement);
        }
        return mapped;
    }

    private List<OrderDetail> mapWorkOrderDetailsToContract(WeavingContract contract, List<WeavingWorkOrderDetail> workDetails) {
        List<OrderDetail> mapped = new ArrayList<>();
        if (workDetails == null) {
            return mapped;
        }
        Map<Long, OrderDetail> existingBySalesOrderId = contract.getOrderDetails().stream()
                .filter(detail -> detail.getSalesOrderId() != null)
                .collect(Collectors.toMap(OrderDetail::getSalesOrderId, detail -> detail));
        for (WeavingWorkOrderDetail workDetail : workDetails) {
            if (workDetail.getSalesOrderId() == null) {
                continue;
            }
            OrderDetail contractDetail = existingBySalesOrderId.remove(workDetail.getSalesOrderId());
            if (contractDetail == null) {
                contractDetail = new OrderDetail();
                contractDetail.setActiveFlag(workDetail.getActiveFlag() != null ? workDetail.getActiveFlag() : true);
            }
            contractDetail.setSalesOrderId(workDetail.getSalesOrderId());
            contractDetail.setOrderQuantity(workDetail.getOrderQuantity());
            contractDetail.setPlannedQuantity(workDetail.getPlannedQuantity());
            contractDetail.setTotalQuantity(workDetail.getTotalQuantity());
            contractDetail.setWeavingContract(contract);
            mapped.add(contractDetail);
        }
        return mapped;
    }

    private void syncContractToWorkOrder(WeavingContract contract) {
        if (contract == null || contract.getId() == null || contract.getOrderTypeId() == null || !contract.getOrderTypeId().equals(1L)) {
            return;
        }
        Optional<WeavingWorkOrder> existingWoOpt = weavingWorkOrderRepository.findFirstByWeavingContractIdOrderByIdDesc(contract.getId());
        if (existingWoOpt.isPresent()) {
            WeavingWorkOrder workOrder = existingWoOpt.get();
            WeavingWorkOrderDTO dto = buildWorkOrderDtoFromContract(contract, workOrder);
            weavingWorkOrderService.update(workOrder.getId(), dto, contract.getFactoryCompanyId(), false);
        } else {
            WeavingWorkOrderDTO dto = buildWorkOrderDtoFromContract(contract, null);
            weavingWorkOrderService.create(dto);
        }
    }

    private WeavingWorkOrderDTO buildWorkOrderDtoFromContract(WeavingContract contract, WeavingWorkOrder workOrder) {
        WeavingWorkOrderDTO dto = new WeavingWorkOrderDTO();
        if (workOrder != null) {
            dto.setId(workOrder.getId());
            dto.setWeavingWorkOrderNo(workOrder.getWeavingWorkOrderNo());
            dto.setPackingTypeId(workOrder.getPackingTypeId());
            dto.setIsCompleted(workOrder.getIsCompleted());
            dto.setIsApproved(workOrder.getIsApproved());
            dto.setApproveDate(workOrder.getApproveDate());
            dto.setComments(workOrder.getComments());
            dto.setUserId(workOrder.getUserId());
        }
        dto.setWeavingContractId(contract.getId());
        dto.setOrderDate(contract.getContractDate());
        dto.setOrderTypeId(contract.getOrderTypeId());
        Long companyId = contract.getCompanyId();
        Long tradingCompanyId = contract.getTradingCompanyId() != null ? contract.getTradingCompanyId() : companyId;
        Long factoryCompanyId = contract.getFactoryCompanyId() != null ? contract.getFactoryCompanyId() : companyId;
        dto.setBuyerId(tradingCompanyId);
        dto.setConsigneeId(tradingCompanyId);
        dto.setFactoryCompanyId(factoryCompanyId);
        dto.setTradingCompanyId(tradingCompanyId);
        dto.setTermsConditionsId(contract.getTermsConditionsId());
        dto.setPaymentTermsId(contract.getPaymentTermsId());
        dto.setGstTypeId(contract.getTaxId());
        dto.setRemarks(contract.getRemarks());
        List<WeavingWorkOrderItem> workOrderItems = workOrder != null ? workOrder.getItems() : null;
        List<WeavingWorkOrderYarnRequirement> workOrderYarns = workOrder != null ? workOrder.getYarnRequirements() : null;
        List<WeavingWorkOrderDetail> workOrderDetails = workOrder != null ? workOrder.getOrderDetails() : null;
        dto.setItems(mapContractItemsToWorkOrderItems(contract.getItems(), workOrderItems));
        dto.setYarnRequirements(mapContractYarnsToWorkOrderYarns(contract.getYarnRequirements(), workOrderYarns));
        dto.setOrderDetails(mapContractDetailsToWorkOrderDetails(contract.getOrderDetails(), workOrderDetails));
        return dto;
    }

    private List<WeavingWorkOrderItemDTO> mapContractItemsToWorkOrderItems(List<WeavingContractItem> contractItems, List<WeavingWorkOrderItem> existingItems) {
        List<WeavingWorkOrderItemDTO> mapped = new ArrayList<>();
        if (contractItems == null) {
            return mapped;
        }
        Map<String, Deque<WeavingWorkOrderItem>> workOrderItemMap = groupWorkOrderItems(existingItems);
        for (WeavingContractItem item : contractItems) {
            if (item == null || item.getFabricCodeId() == null) {
                continue;
            }
            String key = buildItemKey(item.getFabricCodeId());
            Deque<WeavingWorkOrderItem> deque = workOrderItemMap.get(key);
            WeavingWorkOrderItem existing = (deque != null && !deque.isEmpty()) ? deque.poll() : null;
            mapped.add(elevateWorkOrderItemDto(item, existing));
        }
        return mapped;
    }

    private List<WeavingWorkOrderYarnRequirementDTO> mapContractYarnsToWorkOrderYarns(List<YarnRequirement> contractYarns, List<WeavingWorkOrderYarnRequirement> existingYarns) {
        List<WeavingWorkOrderYarnRequirementDTO> mapped = new ArrayList<>();
        if (contractYarns == null) {
            return mapped;
        }
        Map<String, Deque<WeavingWorkOrderYarnRequirement>> yarnMap = groupWorkOrderYarns(existingYarns);
        for (YarnRequirement requirement : contractYarns) {
            if (requirement == null || requirement.getYarnType() == null || requirement.getYarnType().isEmpty()) {
                continue;
            }
            String key = buildYarnKey(requirement.getYarnType(), requirement.getYarnCount());
            Deque<WeavingWorkOrderYarnRequirement> deque = yarnMap.get(key);
            WeavingWorkOrderYarnRequirement existing = (deque != null && !deque.isEmpty()) ? deque.poll() : null;
            mapped.add(elevateWorkOrderYarnDto(requirement, existing));
        }
        return mapped;
    }

    private List<WeavingWorkOrderDetailDTO> mapContractDetailsToWorkOrderDetails(List<OrderDetail> contractDetails, List<WeavingWorkOrderDetail> existingDetails) {
        List<WeavingWorkOrderDetailDTO> mapped = new ArrayList<>();
        if (contractDetails == null) {
            return mapped;
        }
        Map<Long, Deque<WeavingWorkOrderDetail>> detailMap = groupWorkOrderDetails(existingDetails);
        for (OrderDetail detail : contractDetails) {
            if (detail == null || detail.getSalesOrderId() == null) {
                continue;
            }
            Deque<WeavingWorkOrderDetail> deque = detailMap.get(detail.getSalesOrderId());
            WeavingWorkOrderDetail existing = (deque != null && !deque.isEmpty()) ? deque.poll() : null;
            mapped.add(buildWorkOrderDetailDto(detail, existing));
        }
        return mapped;
    }

    private Map<String, Deque<WeavingWorkOrderItem>> groupWorkOrderItems(List<WeavingWorkOrderItem> existingItems) {
        Map<String, Deque<WeavingWorkOrderItem>> map = new HashMap<>();
        if (existingItems == null) {
            return map;
        }
        for (WeavingWorkOrderItem item : existingItems) {
            String key = buildItemKey(item.getFabricCodeId());
            map.computeIfAbsent(key, k -> new ArrayDeque<>()).add(item);
        }
        return map;
    }

    private Map<String, Deque<WeavingWorkOrderYarnRequirement>> groupWorkOrderYarns(List<WeavingWorkOrderYarnRequirement> existingYarns) {
        Map<String, Deque<WeavingWorkOrderYarnRequirement>> map = new HashMap<>();
        if (existingYarns == null) {
            return map;
        }
        for (WeavingWorkOrderYarnRequirement yarn : existingYarns) {
            String key = buildYarnKey(yarn.getYarnType(), yarn.getYarnCount());
            map.computeIfAbsent(key, k -> new ArrayDeque<>()).add(yarn);
        }
        return map;
    }

    private Map<Long, Deque<WeavingWorkOrderDetail>> groupWorkOrderDetails(List<WeavingWorkOrderDetail> existingDetails) {
        Map<Long, Deque<WeavingWorkOrderDetail>> map = new HashMap<>();
        if (existingDetails == null) {
            return map;
        }
        for (WeavingWorkOrderDetail detail : existingDetails) {
            if (detail.getSalesOrderId() == null) {
                continue;
            }
            map.computeIfAbsent(detail.getSalesOrderId(), k -> new ArrayDeque<>()).add(detail);
        }
        return map;
    }

    private String buildItemKey(Long fabricCodeId) {
        return String.valueOf(fabricCodeId != null ? fabricCodeId : -1L);
    }

    private String buildYarnKey(String yarnType, String yarnCount) {
        String type = yarnType != null ? yarnType.trim() : "";
        String count = yarnCount != null ? yarnCount.trim() : "";
        return type + ":" + count;
    }

    private WeavingWorkOrderItemDTO elevateWorkOrderItemDto(WeavingContractItem contractItem, WeavingWorkOrderItem existing) {
        WeavingWorkOrderItemDTO dto = new WeavingWorkOrderItemDTO();
        if (existing != null) {
            dto.setId(existing.getId());
            dto.setUomId(existing.getUomId());
            dto.setRatePerUnit(existing.getRatePerUnit());
            dto.setTaxableAmount(existing.getTaxableAmount());
            dto.setGstPercent(existing.getGstPercent());
            dto.setTotalAmount(existing.getTotalAmount());
            dto.setActiveFlag(existing.getActiveFlag() != null ? existing.getActiveFlag() : true);
        } else {
            dto.setActiveFlag(contractItem.getActiveFlag() != null ? contractItem.getActiveFlag() : true);
        }
        dto.setFabricCodeId(contractItem.getFabricCodeId());
        dto.setQuantity(contractItem.getQuantity());
        dto.setPickCost(contractItem.getPickCost());
        dto.setPlannedStartDate(contractItem.getPlannedStartDate());
        dto.setPlannedEndDate(contractItem.getPlannedEndDate());
        dto.setDailyTarget(contractItem.getDailyTarget());
        dto.setNumberOfLooms(contractItem.getNumberOfLooms());
        dto.setWarpLength(contractItem.getWarpLength());
        dto.setWarpCrimpPercentage(contractItem.getWarpCrimpPercentage());
        dto.setPieceLength(contractItem.getPieceLength());
        dto.setNumberOfPieces(contractItem.getNumberOfPieces());
        dto.setPpi(contractItem.getPpi());
        return dto;
    }

    private WeavingWorkOrderYarnRequirementDTO elevateWorkOrderYarnDto(YarnRequirement requirement, WeavingWorkOrderYarnRequirement existing) {
        WeavingWorkOrderYarnRequirementDTO dto = new WeavingWorkOrderYarnRequirementDTO();
        if (existing != null) {
            dto.setId(existing.getId());
            dto.setActiveFlag(existing.getActiveFlag() != null ? existing.getActiveFlag() : true);
        } else {
            dto.setActiveFlag(requirement.getActiveFlag() != null ? requirement.getActiveFlag() : true);
        }
        dto.setYarnType(requirement.getYarnType());
        dto.setYarnCount(requirement.getYarnCount());
        dto.setGramsPerMeter(requirement.getGramsPerMeter());
        dto.setTotalWeavingOrderQty(requirement.getTotalWeavingOrderQty());
        dto.setTotalRequiredQty(requirement.getTotalRequiredQty());
        dto.setTotalAvailableQty(requirement.getTotalAvailableQty());
        return dto;
    }

    private WeavingWorkOrderDetailDTO buildWorkOrderDetailDto(OrderDetail detail, WeavingWorkOrderDetail existing) {
        WeavingWorkOrderDetailDTO dto = new WeavingWorkOrderDetailDTO();
        if (existing != null) {
            dto.setId(existing.getId());
            dto.setActiveFlag(existing.getActiveFlag() != null ? existing.getActiveFlag() : true);
        } else {
            dto.setActiveFlag(detail.getActiveFlag() != null ? detail.getActiveFlag() : true);
        }
        dto.setSalesOrderId(detail.getSalesOrderId());
        dto.setOrderQuantity(detail.getOrderQuantity());
        dto.setPlannedQuantity(detail.getPlannedQuantity());
        dto.setTotalQuantity(detail.getTotalQuantity());
        return dto;
    }

    private void validatePlannedQuantities(List<OrderDetailDTO> orderDetails, Long contractIdToExclude) {
        if (orderDetails == null || orderDetails.isEmpty()) {
            return;
        }
        Map<Long, SalesOrderPlanContext> contexts = new HashMap<>();
        for (OrderDetailDTO orderDTO : orderDetails) {
            if (orderDTO == null) {
                continue;
            }
            Long salesOrderId = orderDTO.getSalesOrderId();
            BigDecimal plannedQty = orderDTO.getPlannedQuantity();
            if (salesOrderId == null || plannedQty == null || plannedQty.compareTo(BigDecimal.ZERO) <= 0) {
                continue;
            }
            SalesOrderPlanContext context = contexts.computeIfAbsent(salesOrderId, id -> createSalesOrderPlanContext(id, contractIdToExclude));
            BigDecimal available = context.remaining;
            if (plannedQty.compareTo(available) > 0) {
                throw new IllegalArgumentException(buildRemainingQuantityMessage(context.salesOrderNo, available, plannedQty));
            }
            BigDecimal remainingAfterAllocation = available.subtract(plannedQty);
            if (remainingAfterAllocation.compareTo(BigDecimal.ZERO) < 0) {
                remainingAfterAllocation = BigDecimal.ZERO;
            }
            context.remaining = remainingAfterAllocation;
            orderDTO.setRemainingQuantity(remainingAfterAllocation);
        }
    }

     private SalesOrderPlanContext createSalesOrderPlanContext(Long salesOrderId, Long contractIdToExclude) {
        BigDecimal totalOrderQty = getTotalOrderQuantity(salesOrderId);
        BigDecimal alreadyPlanned = getExistingPlannedQuantity(salesOrderId, contractIdToExclude);
        BigDecimal remaining = totalOrderQty.subtract(alreadyPlanned);
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            remaining = BigDecimal.ZERO;
        }
        SalesOrder salesOrder = requireSalesOrderReference(salesOrderId);
        String salesOrderNo = salesOrder.getSalesOrderNo();
        if (!StringUtils.hasText(salesOrderNo)) {
            salesOrderNo = String.valueOf(salesOrderId);
        }
        return new SalesOrderPlanContext(salesOrderNo, remaining);
    }

    private SalesOrder requireSalesOrderReference(Long salesOrderId) {
        if (salesOrderId == null || salesOrderId <= 0) {
            return null;
        }
        Optional<SalesOrder> salesOrderOptional = salesOrderRepository.findById(salesOrderId);
        String displayIdentifier = salesOrderOptional
                .map(SalesOrder::getSalesOrderNo)
                .filter(StringUtils::hasText)
                .orElse("Sales Order ID " + salesOrderId);
        return referenceValidationService.requireReference(
                salesOrderId,
                () -> salesOrderOptional,
                "Sales Order",
                displayIdentifier);
    }

    
    
    private String buildRemainingQuantityMessage(String salesOrderNo, BigDecimal remaining, BigDecimal provided) {
        return salesOrderNo + " remaining order quantity is " + formatQuantity(remaining) + " but you provided " + formatQuantity(provided);
    }
    
    private BigDecimal resolveOrderQuantity(Long salesOrderId, BigDecimal providedOrderQuantity) {
        if (providedOrderQuantity != null) {
            return providedOrderQuantity;
        }
        if (salesOrderId == null) {
            return BigDecimal.ZERO;
        }
        return getTotalOrderQuantity(salesOrderId);
    }
    
    private BigDecimal calculateRemainingQuantity(BigDecimal orderQuantity, BigDecimal plannedQuantity) {
        BigDecimal orderQty = orderQuantity != null ? orderQuantity : BigDecimal.ZERO;
        BigDecimal plannedQty = plannedQuantity != null ? plannedQuantity : BigDecimal.ZERO;
        BigDecimal remaining = orderQty.subtract(plannedQty);
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        return remaining;
    }
    
    private BigDecimal getTotalOrderQuantity(Long salesOrderId) {
        Long total = salesOrderItemRepository.sumOrderQuantityBySalesOrderId(salesOrderId);
        if (total == null) {
            total = 0L;
        }
        return BigDecimal.valueOf(total);
    }
    
    private BigDecimal getExistingPlannedQuantity(Long salesOrderId, Long contractIdToExclude) {
        BigDecimal existing;
        if (contractIdToExclude == null) {
            existing = orderDetailRepository.sumPlannedQuantityBySalesOrderId(salesOrderId);
        } else {
            existing = orderDetailRepository.sumPlannedQuantityBySalesOrderIdExcludingContract(salesOrderId, contractIdToExclude);
        }
        return existing != null ? existing : BigDecimal.ZERO;
    }
      
    private String formatQuantity(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) == 0) {
            return "0";
        }
        return value.stripTrailingZeros().toPlainString();
    }
    
    private static class SalesOrderPlanContext {
        private final String salesOrderNo;
        private BigDecimal remaining;
        private SalesOrderPlanContext(String salesOrderNo, BigDecimal remaining) {
            this.salesOrderNo = salesOrderNo;
            this.remaining = remaining;
        }
    }

    private void updateItems(WeavingContract contract, List<WeavingContractItemDTO> itemDTOs) {
        if (itemDTOs == null) return;
        Map<Long, WeavingContractItem> existingItemsById = contract.getItems().stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(WeavingContractItem::getId, item -> item));
        Map<String, Deque<WeavingContractItem>> existingItemsByKey = groupContractItems(contract.getItems());
        
        List<WeavingContractItem> updatedItems = new ArrayList<>();
        for (WeavingContractItemDTO itemDTO : itemDTOs) {
            WeavingContractItem item = null;
            if (itemDTO.getId() != null) {
                item = existingItemsById.get(itemDTO.getId());
                if (item != null) {
                    existingItemsById.remove(itemDTO.getId());
                    String key = buildItemKey(item.getFabricCodeId());
                    Deque<WeavingContractItem> deque = existingItemsByKey.get(key);
                    if (deque != null) {
                        deque.remove(item);
                    }
                }
            }
            
            if (item == null && itemDTO.getFabricCodeId() != null) {
                String key = buildItemKey(itemDTO.getFabricCodeId());
                Deque<WeavingContractItem> deque = existingItemsByKey.get(key);
                if (deque != null && !deque.isEmpty()) {
                    item = deque.poll();
                    if (item != null && item.getId() != null) {
                        existingItemsById.remove(item.getId());
                    }
                }
            }
            
            if (item == null) {
                item = new WeavingContractItem();
                item.setWeavingContract(contract);
            }
            item.setFabricCodeId(itemDTO.getFabricCodeId());
            item.setQuantity(itemDTO.getQuantity());
            item.setPickCost(itemDTO.getPickCost());
            item.setPlannedStartDate(itemDTO.getPlannedStartDate());
            item.setPlannedEndDate(itemDTO.getPlannedEndDate());
            item.setDailyTarget(itemDTO.getDailyTarget());
            item.setNumberOfLooms(itemDTO.getNumberOfLooms());
            item.setWarpLength(itemDTO.getWarpLength());
            item.setWarpCrimpPercentage(itemDTO.getWarpCrimpPercentage());
            item.setPieceLength(itemDTO.getPieceLength());
            item.setNumberOfPieces(itemDTO.getNumberOfPieces());
            item.setPpi(itemDTO.getPpi());
            item.setNoOfDays(itemDTO.getNoOfDays());
            item.setLoomType(itemDTO.getLoomType());
            item.setActiveFlag(itemDTO.getActiveFlag() != null ? itemDTO.getActiveFlag() : true);
            updatedItems.add(item);
        }
        contract.getItems().clear();
        contract.getItems().addAll(updatedItems);
    }

    // private void updateYarnRequirements(WeavingContract contract, List<YarnRequirementDTO> yarnDTOs) {
    //     if (yarnDTOs == null) return;
    //     Map<Long, YarnRequirement> existingYarnsById = contract.getYarnRequirements().stream()
    //             .filter(y -> y.getId() != null)
    //             .collect(Collectors.toMap(YarnRequirement::getId, yarn -> yarn));
    //     Map<String, Deque<YarnRequirement>> existingYarnsByKey = groupContractYarns(contract.getYarnRequirements());
        
    //     List<YarnRequirement> updatedYarns = new ArrayList<>();
    //     for (YarnRequirementDTO yarnDTO : yarnDTOs) {
    //         if (yarnDTO.getYarnType() == null || yarnDTO.getYarnType().isEmpty()) {
    //             continue;
    //         }
    //         YarnRequirement yarn = null;
    //         if (yarnDTO.getId() != null) {
    //             yarn = existingYarnsById.get(yarnDTO.getId());
    //             if (yarn != null) {
    //                 existingYarnsById.remove(yarnDTO.getId());
    //                 // Also remove from deque to prevent double matching
    //                 String key = buildYarnKey(yarn.getYarnType(), yarn.getYarnCount());
    //                 Deque<YarnRequirement> deque = existingYarnsByKey.get(key);
    //                 if (deque != null) {
    //                     deque.remove(yarn);
    //                 }
    //             }
    //         }
            
    //         if (yarn == null) {
    //             String key = buildYarnKey(yarnDTO.getYarnType(), yarnDTO.getYarnCount());
    //             Deque<YarnRequirement> deque = existingYarnsByKey.get(key);
    //             if (deque != null && !deque.isEmpty()) {
    //                 yarn = deque.poll();
    //                 if (yarn != null && yarn.getId() != null) {
    //                     existingYarnsById.remove(yarn.getId());
    //                 }
    //             }
    //         }
            
    //         if (yarn == null) {
    //             yarn = new YarnRequirement();
    //             yarn.setWeavingContract(contract);
    //         }
    //         yarn.setYarnId(yarnDTO.getYarnId());
    //         yarn.setYarnType(yarnDTO.getYarnType());
    //         yarn.setYarnCount(yarnDTO.getYarnCount());
    //         yarn.setGramsPerMeter(yarnDTO.getGramsPerMeter());
    //         yarn.setTotalWeavingOrderQty(yarnDTO.getTotalWeavingOrderQty());
    //         yarn.setTotalRequiredQty(yarnDTO.getTotalRequiredQty());
    //         yarn.setTotalAvailableQty(yarnDTO.getTotalAvailableQty());
    //         yarn.setActiveFlag(yarnDTO.getActiveFlag() != null ? yarnDTO.getActiveFlag() : true);
    //         updatedYarns.add(yarn);
    //     }
    //     contract.getYarnRequirements().clear();
    //     contract.getYarnRequirements().addAll(updatedYarns);
    // }

    private void updateYarnRequirements(WeavingContract contract, List<YarnRequirementDTO> yarnDTOs) {
        if (yarnDTOs == null) {
            return;
        }

        Map<Long, YarnRequirement> existingYarnsById = contract.getYarnRequirements().stream()
                .filter(yarn -> yarn.getId() != null)
                .collect(Collectors.toMap(YarnRequirement::getId, yarn -> yarn));

        Map<String, Deque<YarnRequirement>> existingYarnsByKey = groupContractYarns(contract.getYarnRequirements());

        List<YarnRequirement> updatedYarns = new ArrayList<>();

        for (YarnRequirementDTO yarnDTO : yarnDTOs) {
            if (yarnDTO.getYarnType() == null || yarnDTO.getYarnType().trim().isEmpty()) {
                continue;
            }

            YarnRequirement yarn = null;

            if (yarnDTO.getId() != null) {
                yarn = existingYarnsById.remove(yarnDTO.getId());
                if (yarn != null) {
                    String key = buildYarnKey(yarn.getYarnType(), yarn.getYarnCount());
                    Deque<YarnRequirement> deque = existingYarnsByKey.get(key);
                    if (deque != null) {
                        deque.remove(yarn);
                    }
                }
            }

            if (yarn == null) {
                String key = buildYarnKey(yarnDTO.getYarnType(), yarnDTO.getYarnCount());
                Deque<YarnRequirement> deque = existingYarnsByKey.get(key);
                if (deque != null && !deque.isEmpty()) {
                    yarn = deque.poll();
                    if (yarn != null && yarn.getId() != null) {
                        existingYarnsById.remove(yarn.getId());
                    }
                }
            }

            if (yarn == null) {
                yarn = new YarnRequirement();
                yarn.setWeavingContract(contract);
            }

            yarn.setYarnId(yarnDTO.getYarnId());
            yarn.setYarnType(yarnDTO.getYarnType());
            yarn.setYarnCount(yarnDTO.getYarnCount());
            yarn.setGramsPerMeter(yarnDTO.getGramsPerMeter());
            yarn.setTotalWeavingOrderQty(yarnDTO.getTotalWeavingOrderQty());
            yarn.setTotalRequiredQty(yarnDTO.getTotalRequiredQty());
            yarn.setTotalAvailableQty(yarnDTO.getTotalAvailableQty());
            yarn.setActiveFlag(yarnDTO.getActiveFlag() != null ? yarnDTO.getActiveFlag() : true);

            updatedYarns.add(yarn);
        }

        contract.getYarnRequirements().clear();
        contract.getYarnRequirements().addAll(updatedYarns);
    }

    private void updateOrderDetails(WeavingContract contract, List<OrderDetailDTO> orderDTOs) {
        if (orderDTOs == null) return;
        Map<Long, OrderDetail> existingOrdersById = contract.getOrderDetails().stream()
                .filter(order -> order.getId() != null)
                .collect(Collectors.toMap(OrderDetail::getId, order -> order));
        Map<Long, Deque<OrderDetail>> existingOrdersByKey = groupContractOrderDetails(contract.getOrderDetails());
        
        List<OrderDetail> updatedOrders = new ArrayList<>();
        for (OrderDetailDTO orderDTO : orderDTOs) {
            OrderDetail order = null;
            if (orderDTO.getId() != null) {
                order = existingOrdersById.get(orderDTO.getId());
                if (order != null) {
                    existingOrdersById.remove(orderDTO.getId());
                    Deque<OrderDetail> deque = existingOrdersByKey.get(order.getSalesOrderId());
                    if (deque != null) {
                        deque.remove(order);
                    }
                }
            }
            
            if (order == null && orderDTO.getSalesOrderId() != null) {
                Deque<OrderDetail> deque = existingOrdersByKey.get(orderDTO.getSalesOrderId());
                if (deque != null && !deque.isEmpty()) {
                    order = deque.poll();
                    if (order != null && order.getId() != null) {
                        existingOrdersById.remove(order.getId());
                    }
                }
            }
            
            if (order == null) {
                order = new OrderDetail();
                order.setWeavingContract(contract);
            }
            order.setSalesOrderId(orderDTO.getSalesOrderId());
            order.setOrderQuantity(orderDTO.getOrderQuantity());
            order.setPlannedQuantity(orderDTO.getPlannedQuantity());
            order.setTotalQuantity(orderDTO.getTotalQuantity());
            order.setActiveFlag(orderDTO.getActiveFlag() != null ? orderDTO.getActiveFlag() : true);
            updatedOrders.add(order);
        }
        contract.getOrderDetails().clear();
        contract.getOrderDetails().addAll(updatedOrders);
    }

    public void deleteWeavingContract(Long id) {
        WeavingContract contract = contractRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Contract not found with id: " + id));
        // Keep original behavior: for global delete, validate references across all companies
        ensureContractNotReferenced(contract, null);
        deleteLinkedWeavingWorkOrders(contract);
        try {
            contractRepo.delete(contract);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Weaving Contract because it is referenced by other");
        }
    }
    
    public void deleteWeavingContractByCompany(Long id, Long companyId) {
        WeavingContract contract = contractRepo.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new EntityNotFoundException("Weaving Contract not found with id: " + id + " and companyId: " + companyId));
        // For company-scoped delete, validate references within this company only
        ensureContractNotReferenced(contract, companyId);
        deleteLinkedWeavingWorkOrders(contract);
        try {
            contractRepo.delete(contract);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Weaving Contract because it is referenced by other");
        }
    }
    
    public boolean isMapped(Long id) {
        if (id == null) return false;
        return weavingYarnIssueRepository.existsByWeavingContractId(id)
            || weavingYarnOutwardRepository.existsByWeavingContractId(id)
            || weftInwardRepository.existsByWeavingContractId(id)
            || jobworkFabricReceiveRepository.existsByWeavingContractId(id)
            || beamOutwardRepository.existsByWeavingContractId(id)
            || beamGatingDetailsRepository.existsByWeavingContractId(id)
            || sizingBeamOutwardDetailRepository.existsByWeavingContractId(id)
            || sizingBeamDetailsRepository.existsByWeavingContractId(id);
    }

    public boolean isWorkOrderMapped(Long weavingWorkOrderId) {
        if (weavingWorkOrderId == null) {
            return false;
        }
        return weavingWorkOrderRepository.findById(weavingWorkOrderId)
                .map(WeavingWorkOrder::getWeavingContractId)
                .map(this::isMapped)
                .orElse(false);
    }

    private void ensureContractNotReferenced(WeavingContract contract, Long referenceCompanyId) {
        if (contract == null) {
            return;
        }
        Long contractId = contract.getId();
        Long contractOrderTypeId = contract.getOrderTypeId();
        String contractNo = contract.getWeavingContractNo();
        String identifier = contractNo != null ? contractNo : "Weaving Contract #" + contractId;

        if (contractId != null) {
            if (weavingYarnIssueRepository.existsByWeavingContractId(contractId)) {
                throw new IllegalStateException(identifier + " already mapped in weaving yarn issue");
            }
            if (weavingYarnOutwardRepository.existsByWeavingContractId(contractId)) {
                throw new IllegalStateException(identifier + " already mapped in weaving yarn outward");
            }
            if (weftInwardRepository.existsByWeavingContractId(contractId)) {
                throw new IllegalStateException(identifier + " already mapped in weft inward");
            }
            if (jobworkFabricReceiveRepository.existsByWeavingContractId(contractId)) {
                throw new IllegalStateException(identifier + " already mapped in jobwork fabric receive");
            }
            // Beam-related modules are only applicable to internal weaving-contract flow.
            if (WEAVING_CONTRACT_ORDER_TYPE_ID.equals(contractOrderTypeId)) {
                boolean beamOutwardExists = referenceCompanyId != null
                    ? beamOutwardRepository.existsByWeavingContractIdAndCompanyIdAndOrderTypeId(
                        contractId,
                        referenceCompanyId,
                        WEAVING_CONTRACT_ORDER_TYPE_ID
                    )
                    : beamOutwardRepository.existsByWeavingContractIdAndOrderTypeId(
                        contractId,
                        WEAVING_CONTRACT_ORDER_TYPE_ID
                    );
                if (beamOutwardExists) {
                    throw new IllegalStateException(identifier + " already mapped in beam outward");
                }
                if (beamGatingDetailsRepository.existsByWeavingContractId(contractId)) {
                    throw new IllegalStateException(identifier + " already mapped in beam gating");
                }
                if (sizingBeamOutwardDetailRepository.existsByWeavingContractId(contractId)) {
                    throw new IllegalStateException(identifier + " already mapped in sizing beam outward detail");
                }
                if (sizingBeamDetailsRepository.existsByWeavingContractId(contractId)) {
                    throw new IllegalStateException("This transaction cannot be permanently deleted because it is linked to Sizing Beam Details.");
                }
            }
        }

        if (WEAVING_CONTRACT_ORDER_TYPE_ID.equals(contractOrderTypeId)
                && contractNo != null
                && factoryBeamInwardRepository.existsByWeavingContractId(contractNo)) {
            throw new IllegalStateException(identifier + " already mapped in factory beam inward");
        }

        if (contractNo != null && salesReturnBeamRepository.existsByWeavingContractNo(contractNo)) {
            throw new IllegalStateException(identifier + " already mapped in sales return beam");
        }
    }
    

    private void deleteLinkedWeavingWorkOrders(WeavingContract contract) {
        if (contract == null || contract.getId() == null) {
            return;
        }
        List<WeavingWorkOrder> workOrders = weavingWorkOrderRepository.findByWeavingContractId(contract.getId());
        if (workOrders.isEmpty()) {
            return;
        }
        for (WeavingWorkOrder workOrder : workOrders) {
            if (workOrder == null || workOrder.getId() == null) {
                continue;
            }
            if (weftInwardRepository.existsByWeavingJobWorkOrderId(workOrder.getId())) {
                String workOrderNo = workOrder.getWeavingWorkOrderNo();
                String identifier = workOrderNo != null ? workOrderNo : "Weaving Work Order #" + workOrder.getId();
                throw new IllegalStateException(identifier + " already mapped in weft inward");
            }
        }
        weavingWorkOrderRepository.deleteAll(workOrders);
    }

    private Long getValidatedPaymentTermsId(Long paymentTermsId) {
        if (paymentTermsId == null || paymentTermsId <= 0) {
            throw new IllegalArgumentException("Valid paymentTermsId is required");
        }
        return paymentTermsId;
    }
    private Long getDefaultCompanyId() {
        List<Company> companies = companyRepository.findAll();
        return companies.isEmpty() ? 1L : companies.get(0).getId();
    }
    
    // private void populateYarnAvailableQty(YarnRequirement yarn) {
    //     Long yarnId = yarn.getYarnId();
    //     if (yarnId == null && yarn.getYarnCount() != null && !yarn.getYarnCount().isEmpty()) {
    //         yarnId = yarnMasterRepository.findByYarnNameIgnoreCase(yarn.getYarnCount().trim())
    //                 .map(com.tech3.erp.entity.YarnMaster::getId)
    //                 .orElse(null);
    //         if (yarnId != null) {
    //             yarn.setYarnId(yarnId);
    //         }
    //     }
    //     if (yarnId != null) {
    //         java.math.BigDecimal availableQty = yarnInwardStockDetailsRepository.sumAvailableQtyByItemId(yarnId);
    //         yarn.setTotalAvailableQty(availableQty != null ? availableQty : java.math.BigDecimal.ZERO);
    //     } else {
    //         yarn.setTotalAvailableQty(java.math.BigDecimal.ZERO);
    //     }
    // }
    private Long getCurrentCompanyId() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null && attributes.getRequest() != null) {
                String companyIdStr = attributes.getRequest().getHeader("X-Company-Id");
                if (companyIdStr != null && !companyIdStr.isEmpty()) {
                    return Long.valueOf(companyIdStr);
                }
            }
        } catch (Exception e) {
            // Ignore if no request context or parsing error
        }
        return null;
    }

    private void populateYarnAvailableQty(YarnRequirement yarn, Long companyId) {
        if (yarn.getYarnId() != null) {
            Long effectiveCompanyId = companyId != null ? companyId : getCurrentCompanyId();
            if (effectiveCompanyId != null) {
                java.math.BigDecimal availableQty = yarnInwardStockDetailsRepository.sumAvailableQtyByItemIdAndCompanyId(yarn.getYarnId(), effectiveCompanyId);
                yarn.setTotalAvailableQty(availableQty != null ? availableQty : java.math.BigDecimal.ZERO);
            } else {
                yarn.setTotalAvailableQty(java.math.BigDecimal.ZERO);
            }
        }
    }

 private void populateAllLazyCollections(WeavingContract contract) {
        if (contract.getYarnRequirements() != null) {
            for (YarnRequirement yarn : contract.getYarnRequirements()) {
                populateYarnAvailableQty(yarn, contract.getCompanyId());
            }
        }
        // Force initialize items and orderDetails to prevent null in JSON
        if (contract.getItems() != null) {
            contract.getItems().size();
        }
        if (contract.getOrderDetails() != null) {
            contract.getOrderDetails().size();
        }
    }

    private void populateAllYarnAvailableQties(WeavingContract contract) {
        if (contract.getYarnRequirements() != null) {
            for (YarnRequirement yarn : contract.getYarnRequirements()) {
                populateYarnAvailableQty(yarn, contract.getCompanyId());
            }
        }
    }

    private void validateYarnRequirementsForDuplicates(List<YarnRequirementDTO> yarnRequirements) {
        if (yarnRequirements == null || yarnRequirements.isEmpty()) {
            return;
        }

        Map<String, YarnRequirementDTO> seenYarns = new HashMap<>();
        List<String> duplicates = new ArrayList<>();

        for (YarnRequirementDTO yarn : yarnRequirements) {
            if (yarn.getYarnId() == null || yarn.getYarnType() == null || yarn.getYarnType().isEmpty()) {
                continue;
            }

            String key = yarn.getYarnId() + "_" + yarn.getYarnType();
            
            if (seenYarns.containsKey(key)) {
                duplicates.add("Yarn ID: " + yarn.getYarnId() + ", Type: " + yarn.getYarnType());
            } else {
                seenYarns.put(key, yarn);
            }
        }

        if (!duplicates.isEmpty()) {
            throw new IllegalArgumentException("Duplicate yarn requirements found. Each yarn ID and type combination must be unique. Duplicates: " + String.join("; ", duplicates));
        }
    }

    private Map<Long, Deque<OrderDetail>> groupContractOrderDetails(List<OrderDetail> existingDetails) {
        Map<Long, Deque<OrderDetail>> map = new HashMap<>();
        if (existingDetails != null) {
            for (OrderDetail detail : existingDetails) {
                if (detail.getSalesOrderId() != null) {
                    map.computeIfAbsent(detail.getSalesOrderId(), k -> new ArrayDeque<>()).add(detail);
                }
            }
        }
        return map;
    }

    private Map<String, Deque<WeavingContractItem>> groupContractItems(List<WeavingContractItem> existingItems) {
        Map<String, Deque<WeavingContractItem>> map = new HashMap<>();
        if (existingItems != null) {
            for (WeavingContractItem item : existingItems) {
                if (item.getFabricCodeId() != null) {
                    String key = buildItemKey(item.getFabricCodeId());
                    map.computeIfAbsent(key, k -> new ArrayDeque<>()).add(item);
                }
            }
        }
        return map;
    }

    private Map<String, Deque<YarnRequirement>> groupContractYarns(List<YarnRequirement> existingYarns) {
        Map<String, Deque<YarnRequirement>> map = new HashMap<>();
        if (existingYarns != null) {
            for (YarnRequirement yarn : existingYarns) {
                if (yarn.getYarnType() != null && !yarn.getYarnType().isEmpty()) {
                    String key = buildYarnKey(yarn.getYarnType(), yarn.getYarnCount());
                    map.computeIfAbsent(key, k -> new ArrayDeque<>()).add(yarn);
                }
            }
        }
        return map;
    }

}