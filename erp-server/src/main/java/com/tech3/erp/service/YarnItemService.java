package com.tech3.erp.service;

import com.tech3.erp.dto.YarnItemDto;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.LotEntry;
import com.tech3.erp.entity.SubCategory;
import com.tech3.erp.entity.YarnItemEntity;
import com.tech3.erp.entity.YarnMaster;
import com.tech3.erp.repository.LotEntryRepository;
import com.tech3.erp.repository.SubCategoryRepository;
import com.tech3.erp.repository.YarnItemRepository;
import com.tech3.erp.repository.YarnMasterRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class YarnItemService {
    @Autowired
    private YarnItemRepository yarnItemRepository;
    @Autowired
    private YarnMasterRepository yarnMasterRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;
    @Autowired
    private LotEntryRepository lotEntryRepository;

    // =================== CREATE ===================
    public YarnItemDto create(YarnItemDto dto) {
        validateDto(dto);

        YarnItemEntity entity = convertDtoToEntity(dto);
        YarnItemEntity saved = yarnItemRepository.save(entity);

        LotEntry lot = new LotEntry();
        lot.setLotNumber(saved.getLotNumber());
        lot.setYarnId(saved.getYarnId());
        lot.setQuantity(saved.getTotalKg());
//        lot.setIsOpeningBalance(true);
//        lot.setOpeningBalanceId(saved.getOpeningBalanceId());
        lotEntryRepository.save(lot);

        return new YarnItemDto(saved);
    }

    // =================== DTO VALIDATION ===================
    private void validateDto(YarnItemDto dto) {
        if (dto.getOpeningBalanceId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Opening Balance ID is required");
        if (dto.getYarnId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Yarn ID is required");
        if (dto.getMillId() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mill/SubCategory ID is required");
        if (dto.getNoOfBags() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No of Bags is required");
        if (dto.getKgsPerBag() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Kgs per Bag is required");
        if (dto.getLotNumber() == null || dto.getLotNumber().trim().isEmpty()) dto.setLotNumber("NA");
    }

    // =================== HELPER: Convert DTO → ENTITY ===================
    private YarnItemEntity convertDtoToEntity(YarnItemDto dto) {
        YarnItemEntity entity = new YarnItemEntity();
        entity.setOpeningBalanceId(dto.getOpeningBalanceId());

        Integer maxSNo = yarnItemRepository.findMaxSNoByOpeningBalanceId(dto.getOpeningBalanceId());
        entity.setSNo((maxSNo != null ? maxSNo : 0) + 1);

        YarnMaster yarn = yarnMasterRepository.findById(dto.getYarnId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Yarn Master not found"));
        entity.setYarnId(dto.getYarnId());
        entity.setYarnName(yarn);

        SubCategory mill = subCategoryRepository.findById(dto.getMillId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mill/SubCategory not found"));
        entity.setMillId(dto.getMillId());
        entity.setMillName(mill);

        entity.setNoOfBags(dto.getNoOfBags());
        entity.setKgsPerBag(dto.getKgsPerBag());
        entity.setTotalKg(dto.getTotalKg() != null ? dto.getTotalKg() : dto.getKgsPerBag().multiply(BigDecimal.valueOf(dto.getNoOfBags())));
        entity.setLotNumber(dto.getLotNumber() != null && !dto.getLotNumber().trim().isEmpty() ? dto.getLotNumber() : "NA");

        return entity;
    }

    // =================== GET ALL ===================
    public List<YarnItemDto> getAll() {
        return yarnItemRepository.findAll().stream()
                .map(YarnItemDto::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<YarnItemDto> getAllWithPagination(Long offset, Long limit) {
        List<YarnItemDto> allItems = yarnItemRepository.findAll().stream()
                .map(YarnItemDto::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allItems, offset, limit);
    }

    // =================== GET BY ID ===================
    public YarnItemDto getById(Long id) {
        YarnItemEntity entity = yarnItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Yarn Item not found"));
        return new YarnItemDto(entity);
    }

    // =================== GET BY OPENING BALANCE ===================
    public List<YarnItemDto> getByOpeningBalanceId(Long openingBalanceId) {
        return yarnItemRepository.findByOpeningBalanceId(openingBalanceId).stream()
                .map(YarnItemDto::new)
                .collect(Collectors.toList());
    }

    // =================== UPDATE ===================
    public YarnItemDto update(Long id, YarnItemDto dto) {
        YarnItemEntity entity = yarnItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Yarn Item not found"));

        if (dto.getSNo() != null) entity.setSNo(dto.getSNo());

        if (dto.getYarnId() != null) {
            YarnMaster yarn = yarnMasterRepository.findById(dto.getYarnId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Yarn Master not found"));
            entity.setYarnId(dto.getYarnId());
            entity.setYarnName(yarn);
        }

        if (dto.getMillId() != null) {
            SubCategory mill = subCategoryRepository.findById(dto.getMillId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mill/SubCategory not found"));
            entity.setMillId(dto.getMillId());
            entity.setMillName(mill);
        }

        if (dto.getNoOfBags() != null) entity.setNoOfBags(dto.getNoOfBags());
        if (dto.getKgsPerBag() != null) entity.setKgsPerBag(dto.getKgsPerBag());

        entity.setTotalKg(dto.getTotalKg() != null ? dto.getTotalKg() : 
            (dto.getKgsPerBag() != null && dto.getNoOfBags() != null ? dto.getKgsPerBag().multiply(BigDecimal.valueOf(dto.getNoOfBags())) : entity.getTotalKg()));

        entity.setLotNumber(dto.getLotNumber() != null && !dto.getLotNumber().trim().isEmpty() ? dto.getLotNumber() : "NA");

        YarnItemEntity updated = yarnItemRepository.save(entity);
        return new YarnItemDto(updated);
    }

    // =================== DELETE ===================
    public void delete(Long id) {
        if (!yarnItemRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Yarn Item not found");
        yarnItemRepository.deleteById(id);
    }

    public void deleteByOpeningBalanceId(Long openingBalanceId) {
        List<YarnItemEntity> items = yarnItemRepository.findByOpeningBalanceId(openingBalanceId);
        yarnItemRepository.deleteAll(items);
    }

    // =================== HELPER METHODS FOR OPENING BALANCE ===================
    public YarnItemEntity createEntity(YarnItemDto dto) { return yarnItemRepository.save(convertDtoToEntity(dto)); }
    public LotEntry saveLotEntry(LotEntry lot) { return lotEntryRepository.save(lot); }
}
