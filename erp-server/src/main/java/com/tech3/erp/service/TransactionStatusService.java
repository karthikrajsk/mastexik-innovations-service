package com.tech3.erp.service;

import com.tech3.erp.dto.TransactionStatusDTO;
import com.tech3.erp.entity.TransactionStatus;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.repository.TransactionStatusRepository;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.PurchaseOrderRepository;
import com.tech3.erp.repository.SalesOrderRepository;
import com.tech3.erp.repository.SizingJobWorkOrderRepository;
import com.tech3.erp.repository.SizingPlanRepository;
import com.tech3.erp.repository.WeavingWorkOrderRepository;
import com.tech3.erp.repository.WeavingContractRepository;
import com.tech3.erp.repository.DyeingWorkOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionStatusService {
    
    @Autowired
    private TransactionStatusRepository transactionStatusRepository;
    
    @Autowired
    private CodesDetailRepository codesDetailRepository;
    
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    
    @Autowired
    private SizingJobWorkOrderRepository sizingJobWorkOrderRepository;
    
    @Autowired
    private SizingPlanRepository sizingPlanRepository;
    
    @Autowired
    private WeavingWorkOrderRepository weavingWorkOrderRepository;
    
    @Autowired
    private WeavingContractRepository weavingContractRepository;
    
    @Autowired
    private DyeingWorkOrderRepository dyeingWorkOrderRepository;
    
    public TransactionStatusDTO createTransactionStatus(TransactionStatusDTO dto) {
        CodesDetail transactionType = codesDetailRepository.findById(dto.getTransactionTypeId())
                .orElseThrow(() -> new RuntimeException("Transaction Type not found with id: " + dto.getTransactionTypeId()));
        
        TransactionStatus transactionStatus = convertToEntity(dto, transactionType);
        TransactionStatus savedTransaction = transactionStatusRepository.save(transactionStatus);
        
        if (dto.getPrimaryTypeId() != null) {
            String typeValue = transactionType.getValue();
            if (typeValue != null && (typeValue.equalsIgnoreCase("sales-order") || (typeValue.toUpperCase().contains("SALES") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("SO"))) {
                salesOrderRepository.findById(dto.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsComplete((short) 1);
                    salesOrderRepository.save(order);
                });
            } else if (typeValue != null && (typeValue.equalsIgnoreCase("purchase-order") || (typeValue.toUpperCase().contains("PURCHASE") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("PO"))) {
                purchaseOrderRepository.findById(dto.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsComplete((short) 1);
                    purchaseOrderRepository.save(order);
                });
            } else if (typeValue != null && (typeValue.equalsIgnoreCase("sizing-job-work-order") || (typeValue.toUpperCase().contains("SIZING") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("SJWO"))) {
                sizingJobWorkOrderRepository.findById(dto.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsComplete((short) 1);
                    sizingJobWorkOrderRepository.save(order);
                });
            } else if (typeValue != null && (typeValue.equalsIgnoreCase("sizing-plan") || (typeValue.toUpperCase().contains("SIZING") && typeValue.toUpperCase().contains("PLAN")) || typeValue.equalsIgnoreCase("SP"))) {
                sizingPlanRepository.findById(dto.getPrimaryTypeId()).ifPresent(plan -> {
                    plan.setIsComplete((short) 1);
                    sizingPlanRepository.save(plan);
                });
            } else if (typeValue != null && (typeValue.equalsIgnoreCase("weaving-work-order") || (typeValue.toUpperCase().contains("WEAVING") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("WWO"))) {
                weavingWorkOrderRepository.findById(dto.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsCompleted(true);
                    weavingWorkOrderRepository.save(order);
                });
            }
            else if (typeValue != null && (typeValue.equalsIgnoreCase("weaving-contract") || (typeValue.toUpperCase().contains("WEAVING") && typeValue.toUpperCase().contains("CONTRACT")) || typeValue.equalsIgnoreCase("WC"))) {
                weavingContractRepository.findById(dto.getPrimaryTypeId()).ifPresent(contract -> {
                    contract.setIsComplete((short) 1);
                    weavingContractRepository.save(contract);
                });
            }
            else if (typeValue != null && (typeValue.equalsIgnoreCase("dyeing-work-order") || (typeValue.toUpperCase().contains("DYEING") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("DWO"))) {
                dyeingWorkOrderRepository.findById(dto.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsComplete((short) 1);
                    dyeingWorkOrderRepository.save(order);
                });
            }
        }
        
        return convertToDTO(savedTransaction);
    }
    
    public TransactionStatusDTO updateTransactionStatus(Long id, TransactionStatusDTO dto) {
        TransactionStatus existingTransaction = transactionStatusRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Transaction Status not found with id: " + id));
        
        if (dto.getTransactionTypeId() != null) {
            CodesDetail transactionType = codesDetailRepository.findById(dto.getTransactionTypeId())
                    .orElseThrow(() -> new RuntimeException("Transaction Type not found with id: " + dto.getTransactionTypeId()));
            existingTransaction.setTransactionType(transactionType);
        }
        
        if (dto.getPrimaryTypeId() != null) {
            existingTransaction.setPrimaryTypeId(dto.getPrimaryTypeId());
        }
        
        if (dto.getBuyerId() != null) {
            existingTransaction.setBuyerId(dto.getBuyerId());
        }
        if (dto.getVendorId() != null) {
            existingTransaction.setVendorId(dto.getVendorId());
        }
        if (dto.getDate() != null) {
            existingTransaction.setDate(dto.getDate());
        }
        if (dto.getDescription() != null) {
            existingTransaction.setDescription(dto.getDescription());
        }
        if (dto.getStatus() != null) {
            existingTransaction.setStatus(dto.getStatus());
        }
        if (dto.getIsActive() != null) {
            existingTransaction.setIsActive(dto.getIsActive());
        }
        
        TransactionStatus updatedTransaction = transactionStatusRepository.save(existingTransaction);
        
        if (updatedTransaction.getPrimaryTypeId() != null && updatedTransaction.getTransactionType() != null) {
            String typeValue = updatedTransaction.getTransactionType().getValue();
            if (typeValue != null && (typeValue.equalsIgnoreCase("sales-order") || (typeValue.toUpperCase().contains("SALES") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("SO"))) {
                salesOrderRepository.findById(updatedTransaction.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsComplete((short) 1);
                    salesOrderRepository.save(order);
                });
            } else if (typeValue != null && (typeValue.equalsIgnoreCase("purchase-order") || (typeValue.toUpperCase().contains("PURCHASE") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("PO"))) {
                purchaseOrderRepository.findById(updatedTransaction.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsComplete((short) 1);
                    purchaseOrderRepository.save(order);
                });
            } else if (typeValue != null && (typeValue.equalsIgnoreCase("sizing-job-work-order") || (typeValue.toUpperCase().contains("SIZING") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("SJWO"))) {
                sizingJobWorkOrderRepository.findById(updatedTransaction.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsComplete((short) 1);
                    sizingJobWorkOrderRepository.save(order);
                });
            } else if (typeValue != null && (typeValue.equalsIgnoreCase("sizing-plan") || (typeValue.toUpperCase().contains("SIZING") && typeValue.toUpperCase().contains("PLAN")) || typeValue.equalsIgnoreCase("SP"))) {
                sizingPlanRepository.findById(updatedTransaction.getPrimaryTypeId()).ifPresent(plan -> {
                    plan.setIsComplete((short) 1);
                    sizingPlanRepository.save(plan);
                });
            } else if (typeValue != null && (typeValue.equalsIgnoreCase("weaving-work-order") || (typeValue.toUpperCase().contains("WEAVING") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("WWO"))) {
                weavingWorkOrderRepository.findById(updatedTransaction.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsCompleted(true);
                    weavingWorkOrderRepository.save(order);
                });
            } 
            else if (typeValue != null && (typeValue.equalsIgnoreCase("weaving-contract") || (typeValue.toUpperCase().contains("WEAVING") && typeValue.toUpperCase().contains("CONTRACT")) || typeValue.equalsIgnoreCase("WC"))) {
                weavingContractRepository.findById(updatedTransaction.getPrimaryTypeId()).ifPresent(contract -> {
                    contract.setIsComplete((short) 1);
                    weavingContractRepository.save(contract);
                });
            }
            else if (typeValue != null && (typeValue.equalsIgnoreCase("dyeing-work-order") || (typeValue.toUpperCase().contains("DYEING") && typeValue.toUpperCase().contains("ORDER")) || typeValue.equalsIgnoreCase("DWO"))) {
                dyeingWorkOrderRepository.findById(updatedTransaction.getPrimaryTypeId()).ifPresent(order -> {
                    order.setIsComplete((short) 1);
                    dyeingWorkOrderRepository.save(order);
                });
            }
        }
        
        return convertToDTO(updatedTransaction);
    }
    
    @Transactional(readOnly = true)
    public TransactionStatusDTO getTransactionStatusById(Long id) {
        TransactionStatus transactionStatus = transactionStatusRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new RuntimeException("Transaction Status not found with id: " + id));
        return convertToDTO(transactionStatus);
    }
    
    @Transactional(readOnly = true)
    public List<TransactionStatusDTO> getAllTransactionStatuses() {
        return transactionStatusRepository.findAllWithDetails().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteTransactionStatus(Long id) {
        TransactionStatus transactionStatus = transactionStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction Status not found with id: " + id));
        transactionStatusRepository.delete(transactionStatus);
    }
    
    @Transactional(readOnly = true)
    public List<TransactionStatusDTO> getTransactionStatusByType(Long transactionTypeId) {
        return transactionStatusRepository.findByTransactionTypeIdWithDetails(transactionTypeId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TransactionStatusDTO> getTransactionStatusByBuyer(Long buyerId) {
        return transactionStatusRepository.findByBuyerId(buyerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TransactionStatusDTO> getTransactionStatusByVendor(Long vendorId) {
        return transactionStatusRepository.findByVendorId(vendorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TransactionStatusDTO> getTransactionStatusByStatus(String status) {
        return transactionStatusRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TransactionStatusDTO> getTransactionStatusByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionStatusRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<TransactionStatusDTO> getActiveTransactionStatuses() {
        return transactionStatusRepository.findByIsActive(true).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private TransactionStatusDTO convertToDTO(TransactionStatus entity) {
        TransactionStatusDTO dto = new TransactionStatusDTO();
        dto.setId(entity.getId());
        
        if (entity.getTransactionType() != null) {
            dto.setTransactionTypeId(entity.getTransactionType().getId());
            dto.setTransactionTypeName(entity.getTransactionType().getValue());
        }
        
        dto.setPrimaryTypeId(entity.getPrimaryTypeId());
        dto.setBuyerId(entity.getBuyerId());
        dto.setVendorId(entity.getVendorId());
        dto.setDate(entity.getDate());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus());
        dto.setIsActive(entity.getIsActive());
        
        return dto;
    }
    
    private TransactionStatus convertToEntity(TransactionStatusDTO dto, CodesDetail transactionType) {
        TransactionStatus entity = new TransactionStatus();
        entity.setTransactionType(transactionType);
        entity.setPrimaryTypeId(dto.getPrimaryTypeId());
        entity.setBuyerId(dto.getBuyerId());
        entity.setVendorId(dto.getVendorId());
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        
        return entity;
    }
}
