package com.tech3.erp.service;

import com.tech3.erp.dto.CostingTransactionDto;
import com.tech3.erp.dto.CostingTransactionFabricDto;
import com.tech3.erp.dto.CostingTransactionYarnDto;
import com.tech3.erp.dto.FabricDetailsDto;
import com.tech3.erp.dto.FabricCostDataDto;
import com.tech3.erp.dto.FinalCostingDataDto;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.*;
import com.tech3.erp.repository.*;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CostingTransactionService {

    private final CostingTransactionRepository costingTransactionRepository;
    private final CostingTransactionYarnRepository costingTransactionYarnRepository;
    private final CostingTransactionFabricRepository costingTransactionFabricRepository;
    private final CustomerRepository customerRepository;
    private final FabricTypeRepository fabricTypeRepository;
    private final YarnMasterRepository yarnMasterRepository;

    @Autowired
    public CostingTransactionService(
            CostingTransactionRepository costingTransactionRepository,
            CostingTransactionYarnRepository costingTransactionYarnRepository,
            CostingTransactionFabricRepository costingTransactionFabricRepository,
            CustomerRepository customerRepository,
            FabricTypeRepository fabricTypeRepository,
            YarnMasterRepository yarnMasterRepository) {
        this.costingTransactionRepository = costingTransactionRepository;
        this.costingTransactionYarnRepository = costingTransactionYarnRepository;
        this.costingTransactionFabricRepository = costingTransactionFabricRepository;
        this.customerRepository = customerRepository;
        this.fabricTypeRepository = fabricTypeRepository;
        this.yarnMasterRepository = yarnMasterRepository;
    }

    public CostingTransactionDto createCostingTransaction(CostingTransactionDto dto) {
        validateMasterDataReferences(dto);

        CostingTransaction entity = convertToEntity(dto);
        entity.setCostingNo(dto.getCostingNo());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setActiveFlag(true);

        // Initialize totals to ZERO to prevent null issues
        entity.setTotalYarnCost(BigDecimal.ZERO);
        entity.setTotalYarnWeight(BigDecimal.ZERO);
        entity.setTotalFabricCost(BigDecimal.ZERO);
        entity.setTotalFabricWeight(BigDecimal.ZERO);
        entity.setTotalCost(BigDecimal.ZERO);

        // Save main entity first
        CostingTransaction savedEntity = costingTransactionRepository.save(entity);

        // Handle yarn details
        if (dto.getYarnDetails() != null && !dto.getYarnDetails().isEmpty()) {
            List<CostingTransactionYarn> yarnEntities = dto.getYarnDetails().stream()
                    .map(yarnDto -> convertYarnDtoToEntity(yarnDto, savedEntity))
                    .collect(Collectors.toList());
            costingTransactionYarnRepository.saveAll(yarnEntities);
        }

        // Compute totals and update
        updateComputedFields(savedEntity);

        return convertToDto(savedEntity);
    }

    public CostingTransactionDto getCostingTransactionById(Long id) {
        return getCostingTransactionById(id, null);
    }

    public CostingTransactionDto getCostingTransactionById(Long id, Long companyId) {
        CostingTransaction entity = findTransactionByIdAndCompany(id, companyId);
        return convertToDto(entity);
    }

    public List<CostingTransactionDto> getAllCostingTransactions() {
        return getAllCostingTransactions(null);
    }

    public List<CostingTransactionDto> getAllCostingTransactions(Long companyId) {
        return getTransactions(companyId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CostingTransactionDto> getAllCostingTransactionsWithPagination(Long offset, Long limit) {
        return getAllCostingTransactionsWithPagination(offset, limit, null);
    }

    public PaginatedResponse<CostingTransactionDto> getAllCostingTransactionsWithPagination(Long offset, Long limit, Long companyId) {
        List<CostingTransactionDto> allTransactions = getTransactions(companyId).stream()
                .sorted(Comparator.comparing(CostingTransaction::getId).reversed())
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allTransactions, offset, limit);
    }

    public CostingTransactionDto updateCostingTransaction(Long id, CostingTransactionDto dto) {
        return updateCostingTransaction(id, dto, null);
    }

    public CostingTransactionDto updateCostingTransaction(Long id, CostingTransactionDto dto, Long companyId) {
        CostingTransaction existingEntity = findTransactionByIdAndCompany(id, companyId);

        validateMasterDataReferences(dto);

        updateEntityFromDto(existingEntity, dto);
        existingEntity.setModifiedDate(LocalDateTime.now());

        costingTransactionYarnRepository.deleteByCostingTransactionId(id);
        if (dto.getYarnDetails() != null && !dto.getYarnDetails().isEmpty()) {
            List<CostingTransactionYarn> yarnEntities = dto.getYarnDetails().stream()
                    .map(yarnDto -> convertYarnDtoToEntity(yarnDto, existingEntity))
                    .collect(Collectors.toList());
            costingTransactionYarnRepository.saveAll(yarnEntities);
        }

        updateComputedFields(existingEntity);

        return convertToDto(existingEntity);
    }

    public void deleteCostingTransaction(Long id) {
        deleteCostingTransaction(id, null);
    }

    public void deleteCostingTransaction(Long id, Long companyId) {
        CostingTransaction entity = findTransactionByIdAndCompany(id, companyId);

        // Delete associated records first to maintain data integrity
        costingTransactionFabricRepository.deleteByCostingTransactionId(id);
        costingTransactionYarnRepository.deleteByCostingTransactionId(id);

        // Perform hard delete of the parent entity
        costingTransactionRepository.delete(entity);
    }

    public CostingTransactionDto copyCostingTransaction(Long id) {
        CostingTransaction originalEntity = costingTransactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Costing transaction not found with id: " + id));

        CostingTransactionDto copyDto = convertToDto(originalEntity);
        copyDto.setId(null);
        copyDto.setCostingNo(null);
        copyDto.setCreatedDate(null);
        copyDto.setModifiedDate(null);

        if (copyDto.getYarnDetails() != null) {
            copyDto.getYarnDetails().forEach(yarn -> yarn.setId(null));
        }

        return createCostingTransaction(copyDto);
    }

    private List<CostingTransaction> getTransactions(Long companyId) {
        if (companyId != null) {
            return costingTransactionRepository.findByCompanyIdOrderByIdDesc(companyId);
        }
        return costingTransactionRepository.findAllByOrderByIdDesc();
    }

    private CostingTransaction findTransactionByIdAndCompany(Long id, Long companyId) {
        if (companyId != null) {
            return costingTransactionRepository.findByIdAndCompanyId(id, companyId)
                    .orElseThrow(() -> new EntityNotFoundException("Costing transaction not found for company: " + companyId));
        }
        return costingTransactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Costing transaction not found with id: " + id));
    }

    private void validateMasterDataReferences(CostingTransactionDto dto) {
        // No validation needed for yarn details in this structure
    }

    /*
    private String generateCostingNo() {
        Optional<Integer> maxNumber = costingTransactionRepository.findMaxCostingNumber();
        int nextNumber = maxNumber.orElse(0) + 1;
        return String.format("COST%04d", nextNumber);
    }
    */

    private Long getDefaultFabricTypeId() {
        return fabricTypeRepository.findAll()
                .stream()
                .findFirst()
                .map(FabricType::getId)
                .orElse(1L);
    }

    private void updateComputedFields(CostingTransaction entity) {
        // Calculate yarn totals from yarn details
        List<CostingTransactionYarn> yarnDetails = costingTransactionYarnRepository
                .findByCostingTransaction_IdOrderById(entity.getId());
        
        BigDecimal totalYarnCost = BigDecimal.ZERO;
        BigDecimal totalYarnWeight = BigDecimal.ZERO;
        
        for (CostingTransactionYarn yarn : yarnDetails) {
            if (yarn.getAmount() != null) {
                totalYarnCost = totalYarnCost.add(yarn.getAmount());
            }
            if (yarn.getWeight() != null) {
                totalYarnWeight = totalYarnWeight.add(yarn.getWeight());
            }
        }

        // Calculate fabric totals
        BigDecimal totalFabricCost = costingTransactionFabricRepository.sumAmountByCostingTransactionId(entity.getId());
        BigDecimal totalFabricWeight = costingTransactionFabricRepository.sumWeightByCostingTransactionId(entity.getId());

        // Update entity with safe null handling
        entity.setTotalYarnCost(totalYarnCost);
        entity.setTotalYarnWeight(totalYarnWeight);
        entity.setTotalFabricCost(totalFabricCost != null ? totalFabricCost : BigDecimal.ZERO);
        entity.setTotalFabricWeight(totalFabricWeight != null ? totalFabricWeight : BigDecimal.ZERO);
        
        // Calculate total cost safely
        BigDecimal totalCost = (entity.getTotalYarnCost() != null ? entity.getTotalYarnCost() : BigDecimal.ZERO)
                .add(entity.getTotalFabricCost() != null ? entity.getTotalFabricCost() : BigDecimal.ZERO);
        entity.setTotalCost(totalCost);

        costingTransactionRepository.save(entity);
    }

    private CostingTransaction convertToEntity(CostingTransactionDto dto) {
        CostingTransaction entity = new CostingTransaction();
        updateEntityFromDto(entity, dto);
        return entity;
    }

    private void updateEntityFromDto(CostingTransaction entity, CostingTransactionDto dto) {
        entity.setDate(dto.getDate());
        entity.setCustomerName(dto.getCustomerName());
        entity.setAgentId(dto.getAgentId());
        entity.setFabricCodeQuality(dto.getFabricCodeQuality());
        entity.setStyleName(dto.getStyleName() != null ? dto.getStyleName() : dto.getFabricCodeQuality());
        entity.setUom(dto.getUom());
        entity.setUomFlag(dto.getUom() == 0 ? "Meter" : "Yard");
        entity.setBuyerId(dto.getBuyerId() != null ? dto.getBuyerId() : 1L);
        if (dto.getCompanyId() != null) {
            entity.setCompanyId(dto.getCompanyId());
        }
        
        if (dto.getFabricTypeId() != null) {
            if (fabricTypeRepository.existsById(dto.getFabricTypeId())) {
                entity.setFabricTypeId(dto.getFabricTypeId());
            } else {
                Long defaultFabricTypeId = getDefaultFabricTypeId();
                entity.setFabricTypeId(defaultFabricTypeId);
            }
        } else {
            Long defaultFabricTypeId = getDefaultFabricTypeId();
            entity.setFabricTypeId(defaultFabricTypeId);
        }
        
        entity.setActiveFlag(dto.isActiveFlag());

        // Set embedded objects
        if (dto.getFabricDetails() != null) {
            FabricDetails fabricDetails = new FabricDetails();
            fabricDetails.setGreyWidth(dto.getFabricDetails().getGreyWidth());
            fabricDetails.setEpiDifference(dto.getFabricDetails().getEpiDifference());
            fabricDetails.setEpiOnTable(dto.getFabricDetails().getEpiOnTable());
            fabricDetails.setReedSpace(dto.getFabricDetails().getReedSpace());
            fabricDetails.setTotalEnds(dto.getFabricDetails().getTotalEnds());
            fabricDetails.setWarpCrimp(dto.getFabricDetails().getWarpCrimp());
            entity.setFabricDetails(fabricDetails);
        }

        if (dto.getFabricCostData() != null) {
            FabricCostData fabricCostData = new FabricCostData();
            fabricCostData.setSizingCostPerKg(dto.getFabricCostData().getSizingCostPerKg());
            fabricCostData.setWeavingChargesPerPick(dto.getFabricCostData().getWeavingChargesPerPick());
            fabricCostData.setFabricProcessingCostPerKg(dto.getFabricCostData().getFabricProcessingCostPerKg());
            fabricCostData.setFreightCost(dto.getFabricCostData().getFreightCost());
            fabricCostData.setValueLossInterest(dto.getFabricCostData().getValueLossInterest());
            fabricCostData.setWastage(dto.getFabricCostData().getWastage());
            fabricCostData.setCommissionCD(dto.getFabricCostData().getCommissionCD());
            fabricCostData.setOthers(dto.getFabricCostData().getOthers());
            
            // Set calculated fields from frontend
            fabricCostData.setTotalCostWithoutOverheads(dto.getFabricCostData().getTotalCostWithoutOverheads());
            fabricCostData.setTotalOverheads(dto.getFabricCostData().getTotalOverheads());
            fabricCostData.setSubtotal(dto.getFabricCostData().getSubtotal());
            fabricCostData.setCommissionAmount(dto.getFabricCostData().getCommissionAmount());
            fabricCostData.setTotal(dto.getFabricCostData().getTotal());
            fabricCostData.setRatePerUnit(dto.getFabricCostData().getRatePerUnit());
            
            entity.setFabricCostData(fabricCostData);
        }

        if (dto.getFinalCostingData() != null) {
            FinalCostingData finalCostingData = new FinalCostingData();
            finalCostingData.setMarginPercent(dto.getFinalCostingData().getMarginPercent());
            finalCostingData.setOrderQty(dto.getFinalCostingData().getOrderQty());
            
            // Set calculated fields from frontend
            finalCostingData.setTargetPrice(dto.getFinalCostingData().getTargetPrice());
            finalCostingData.setWeavingChargesAsPerTP(dto.getFinalCostingData().getWeavingChargesAsPerTP());
            finalCostingData.setCoverFactor(dto.getFinalCostingData().getCoverFactor());
            
            entity.setFinalCostingData(finalCostingData);
        }

        entity.setCreatedBy(dto.getCreatedBy());
    }

    private CostingTransactionYarn convertYarnDtoToEntity(CostingTransactionYarnDto dto, CostingTransaction costingTransaction) {
        CostingTransactionYarn entity = new CostingTransactionYarn();
        entity.setCostingTransaction(costingTransaction);

        entity.setWarpWeft(dto.getWarpWeft());
        entity.setYarnCount(dto.getYarnCount());
        entity.setRatePerUnit(dto.getRatePerUnit() != null ? dto.getRatePerUnit() : BigDecimal.ZERO);
        entity.setYarnProcessingCost(dto.getYarnProcessingCost() != null ? dto.getYarnProcessingCost() : BigDecimal.ZERO);
        entity.setContent(dto.getContent());
        entity.setYarnType(dto.getYarnType());
        entity.setYarnTypeId(1L);
        entity.setEpi(dto.getEpi() != null ? dto.getEpi() : BigDecimal.ZERO);
        entity.setPpi(dto.getPpi() != null ? dto.getPpi() : BigDecimal.ZERO);
        entity.setActiveFlag(dto.isActiveFlag());

        // Calculate quantity, rate, amount, and weight
        entity.setQuantity(BigDecimal.ONE);
        entity.setRate(entity.getRatePerUnit());
        
        // Calculate weight: (yarnCount * density) / 1000
        BigDecimal density = "Warp".equals(dto.getWarpWeft()) ? 
            entity.getEpi() : entity.getPpi();
        
        BigDecimal yarnCountBD = BigDecimal.valueOf(dto.getYarnCount() != null ? dto.getYarnCount() : 0);
        BigDecimal weight = yarnCountBD.multiply(density).divide(BigDecimal.valueOf(1000), 3, RoundingMode.HALF_UP);
        entity.setWeight(weight);
        
        // Calculate amount: weight * ratePerUnit + yarnProcessingCost
        BigDecimal amount = weight.multiply(entity.getRatePerUnit()).add(entity.getYarnProcessingCost());
        entity.setAmount(amount);

        return entity;
    }

    private CostingTransactionFabric convertFabricDtoToEntity(CostingTransactionFabricDto dto, CostingTransaction costingTransaction) {
        CostingTransactionFabric entity = new CostingTransactionFabric();
        entity.setCostingTransaction(costingTransaction);

        FabricType fabricType = fabricTypeRepository.findById(dto.getFabricTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Fabric type not found"));
        entity.setFabricType(fabricType);

        entity.setConstruction(dto.getConstruction());
        entity.setGsm(dto.getGsm());
        entity.setWidth(dto.getWidth());
        entity.setQuantity(dto.getQuantity());
        entity.setRate(dto.getRate());
        entity.setActiveFlag(dto.isActiveFlag());

        // Compute amount
        BigDecimal amount = dto.getQuantity().multiply(dto.getRate()).setScale(2, RoundingMode.HALF_UP);
        entity.setAmount(amount);

        // Weight calculation for fabric: weight = (quantity * gsm * width) / 1000000 (converting to kg)
        BigDecimal weight = BigDecimal.ZERO;
        if (dto.getGsm() != null && dto.getWidth() != null) {
            weight = dto.getQuantity().multiply(dto.getGsm()).multiply(dto.getWidth())
                    .divide(BigDecimal.valueOf(1000000), 3, RoundingMode.HALF_UP);
        }
        entity.setWeight(weight);

        return entity;
    }

    private CostingTransactionDto convertToDto(CostingTransaction entity) {
        CostingTransactionDto dto = new CostingTransactionDto();
        dto.setId(entity.getId());
        dto.setCostingNo(entity.getCostingNo());
        dto.setDate(entity.getDate());
        dto.setCustomerName(entity.getCustomerName());
        dto.setFabricCodeQuality(entity.getFabricCodeQuality());
        dto.setStyleName(entity.getStyleName());
        dto.setUom(entity.getUom());
        dto.setUomFlag(entity.getUomFlag());
        dto.setBuyerId(entity.getBuyerId());
        dto.setAgentId(entity.getAgentId());
        dto.setCompanyId(entity.getCompanyId());
        dto.setFabricTypeId(entity.getFabricTypeId());
        dto.setTotalYarnWeight(entity.getTotalYarnWeight());
        dto.setTotalFabricWeight(entity.getTotalFabricWeight());
        dto.setTotalYarnCost(entity.getTotalYarnCost());
        dto.setTotalFabricCost(entity.getTotalFabricCost());
        dto.setTotalCost(entity.getTotalCost());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        dto.setActiveFlag(entity.getActiveFlag());

        // Set embedded objects
        if (entity.getFabricDetails() != null) {
            FabricDetailsDto fabricDetailsDto = new FabricDetailsDto();
            fabricDetailsDto.setGreyWidth(entity.getFabricDetails().getGreyWidth());
            fabricDetailsDto.setEpiDifference(entity.getFabricDetails().getEpiDifference());
            fabricDetailsDto.setEpiOnTable(entity.getFabricDetails().getEpiOnTable());
            fabricDetailsDto.setReedSpace(entity.getFabricDetails().getReedSpace());
            fabricDetailsDto.setTotalEnds(entity.getFabricDetails().getTotalEnds());
            fabricDetailsDto.setWarpCrimp(entity.getFabricDetails().getWarpCrimp());
            dto.setFabricDetails(fabricDetailsDto);
        }

        if (entity.getFabricCostData() != null) {
            FabricCostDataDto fabricCostDataDto = new FabricCostDataDto();
            fabricCostDataDto.setSizingCostPerKg(entity.getFabricCostData().getSizingCostPerKg());
            fabricCostDataDto.setWeavingChargesPerPick(entity.getFabricCostData().getWeavingChargesPerPick());
            fabricCostDataDto.setFabricProcessingCostPerKg(entity.getFabricCostData().getFabricProcessingCostPerKg());
            fabricCostDataDto.setFreightCost(entity.getFabricCostData().getFreightCost());
            fabricCostDataDto.setValueLossInterest(entity.getFabricCostData().getValueLossInterest());
            fabricCostDataDto.setWastage(entity.getFabricCostData().getWastage());
            fabricCostDataDto.setCommissionCD(entity.getFabricCostData().getCommissionCD());
            fabricCostDataDto.setOthers(entity.getFabricCostData().getOthers());
            
            // Include calculated fields
            fabricCostDataDto.setTotalCostWithoutOverheads(entity.getFabricCostData().getTotalCostWithoutOverheads());
            fabricCostDataDto.setTotalOverheads(entity.getFabricCostData().getTotalOverheads());
            fabricCostDataDto.setSubtotal(entity.getFabricCostData().getSubtotal());
            fabricCostDataDto.setCommissionAmount(entity.getFabricCostData().getCommissionAmount());
            fabricCostDataDto.setTotal(entity.getFabricCostData().getTotal());
            fabricCostDataDto.setRatePerUnit(entity.getFabricCostData().getRatePerUnit());
            
            dto.setFabricCostData(fabricCostDataDto);
        }

        if (entity.getFinalCostingData() != null) {
            FinalCostingDataDto finalCostingDataDto = new FinalCostingDataDto();
            finalCostingDataDto.setMarginPercent(entity.getFinalCostingData().getMarginPercent());
            finalCostingDataDto.setOrderQty(entity.getFinalCostingData().getOrderQty());
            
            // Include calculated fields
            finalCostingDataDto.setTargetPrice(entity.getFinalCostingData().getTargetPrice());
            finalCostingDataDto.setWeavingChargesAsPerTP(entity.getFinalCostingData().getWeavingChargesAsPerTP());
            finalCostingDataDto.setCoverFactor(entity.getFinalCostingData().getCoverFactor());
            
            dto.setFinalCostingData(finalCostingDataDto);
        }

        dto.setCreatedBy(entity.getCreatedBy());

        // Load yarn details
        List<CostingTransactionYarn> yarnEntities = costingTransactionYarnRepository
                .findByCostingTransaction_IdAndActiveFlagOrderById(entity.getId(), true);
        dto.setYarnDetails(yarnEntities.stream()
                .map(this::convertYarnEntityToDto)
                .collect(Collectors.toList()));

        return dto;
    }

    private CostingTransactionYarnDto convertYarnEntityToDto(CostingTransactionYarn entity) {
        CostingTransactionYarnDto dto = new CostingTransactionYarnDto();
        dto.setId(entity.getId());
        dto.setWarpWeft(entity.getWarpWeft());
        dto.setYarnCount(entity.getYarnCount());
        dto.setRatePerUnit(entity.getRatePerUnit());
        dto.setYarnProcessingCost(entity.getYarnProcessingCost());
        dto.setContent(entity.getContent());
        dto.setYarnType(entity.getYarnType());
        dto.setEpi(entity.getEpi());
        dto.setPpi(entity.getPpi());
        dto.setActiveFlag(entity.getActiveFlag());
        return dto;
    }

    private CostingTransactionFabricDto convertFabricEntityToDto(CostingTransactionFabric entity) {
        CostingTransactionFabricDto dto = new CostingTransactionFabricDto();
        dto.setId(entity.getId());
        dto.setFabricTypeId(entity.getFabricType().getId());
        dto.setFabricTypeName(entity.getFabricType().getFabricTypeName());
        dto.setConstruction(entity.getConstruction());
        dto.setGsm(entity.getGsm());
        dto.setWidth(entity.getWidth());
        dto.setQuantity(entity.getQuantity());
        dto.setRate(entity.getRate());
        dto.setAmount(entity.getAmount());
        dto.setWeight(entity.getWeight());
        dto.setActiveFlag(entity.getActiveFlag());
        return dto;
    }
}