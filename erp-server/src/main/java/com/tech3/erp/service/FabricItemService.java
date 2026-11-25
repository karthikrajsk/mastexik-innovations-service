package com.tech3.erp.service;

import com.tech3.erp.dto.FabricItemDto;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.FabricItemEntity;
import com.tech3.erp.entity.GradeMaster;
import com.tech3.erp.entity.OpeningBalanceEntity;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.repository.FabricItemRepository;
import com.tech3.erp.repository.GradeMasterRepository;
import com.tech3.erp.repository.OpeningBalanceRepository;
import com.tech3.erp.repository.PieceEntryRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FabricItemService {

    @Autowired
    private FabricItemRepository fabricItemRepository;

    @Autowired
    private GradeMasterRepository gradeMasterRepository;

    @Autowired
    private OpeningBalanceRepository openingBalanceRepository;

    @Autowired
    private PieceEntryRepository pieceEntryRepository;

    // ==========================================================
    // CREATE Fabric Item (DTO)
    // ==========================================================
    @Transactional
    public FabricItemDto create(FabricItemDto dto) {
        FabricItemEntity entity = createEntity(dto);
        return new FabricItemDto(entity);
    }

    // ==========================================================
    // CREATE Fabric Item (Entity) for OpeningBalanceService
    // ==========================================================
    @Transactional
    public FabricItemEntity createEntity(FabricItemDto dto) {
        validateDto(dto);

        FabricItemEntity entity = new FabricItemEntity();
        entity.setFabricCodeId(dto.getFabricCodeId());
        entity.setFabricCode(dto.getFabricCode());
        entity.setFabricTypeId(dto.getFabricTypeId());
        entity.setFabricMasterId(dto.getFabricMasterId());
        entity.setQuality(dto.getQuality());
        entity.setPackingNo(dto.getPackingNo());
        entity.setPieceNo(dto.getPieceNo());
        entity.setPieceMeter(dto.getPieceMeter());
        entity.setNetWeight(dto.getNetWeight());
        entity.setAvgWeight(dto.getAvgWeight());
        entity.setIsInspected(dto.getIsInspected() != null && dto.getIsInspected());

        // link grade
        GradeMaster grade = gradeMasterRepository.findById(dto.getGrade())
                .orElseThrow(() -> new EntityNotFoundException("Grade Master not found"));
        entity.setGradeId(grade.getId());

        // link opening balance
        OpeningBalanceEntity openingBalance = openingBalanceRepository.findById(dto.getOpeningBalanceId())
                .orElseThrow(() -> new EntityNotFoundException("Opening Balance not found"));
        entity.setOpeningBalance(openingBalance);

        FabricItemEntity saved = fabricItemRepository.save(entity);

        // create piece entry
        PieceEntry piece = new PieceEntry();
        piece.setInwardDate(openingBalance.getDate());
//        piece.setOpeningBalanceId(saved.getOpeningBalance().getId());
//        piece.setIsOpeningBalance(true);
//        piece.setFabricId(saved.getFabricCodeId());
        piece.setInspectedMtr(saved.getPieceMeter() != null ? BigDecimal.valueOf(saved.getPieceMeter()) : null);
        piece.setInspectedNtWt(saved.getNetWeight() != null ? BigDecimal.valueOf(saved.getNetWeight()) : null);
        piece.setInspectedAvgWt(saved.getAvgWeight() != null ? BigDecimal.valueOf(saved.getAvgWeight()) : null);
        piece.setFabricMasterId(saved.getFabricMasterId());
        piece.setIsInspected(saved.getIsInspected() != null && saved.getIsInspected() ? 1 : 0);
        piece.setIsPacked(openingBalance.getIsPacked() != null && openingBalance.getIsPacked() ? 1 : 0);
        try {
            if (saved.getPieceNo() != null) piece.setPieceNo(Integer.valueOf(saved.getPieceNo()));
        } catch (NumberFormatException ignored) {}

        savePieceEntry(piece);

        return saved;
    }

    // ==========================================================
    // Save PieceEntry
    // ==========================================================
    @Transactional
    public PieceEntry savePieceEntry(PieceEntry piece) {
        return pieceEntryRepository.save(piece);
    }

    // ==========================================================
    // GET ALL
    // ==========================================================
    public List<FabricItemDto> getAll() {
        return fabricItemRepository.findAll()
                .stream()
                .map(FabricItemDto::new)
                .collect(Collectors.toList());
    }

    // ==========================================================
    // GET ALL WITH PAGINATION
    // ==========================================================
    public PaginatedResponse<FabricItemDto> getAllWithPagination(Long offset, Long limit) {
        List<FabricItemDto> all = getAll();
        return PaginationHelper.paginate(all, offset, limit);
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================
    public FabricItemDto getById(Long id) {
        FabricItemEntity entity = fabricItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fabric Item not found"));
        return new FabricItemDto(entity);
    }

    // ==========================================================
    // GET BY OPENING BALANCE ID
    // ==========================================================
    public List<FabricItemDto> getByOpeningBalanceId(Long openingBalanceId) {
        return fabricItemRepository.findByOpeningBalanceId(openingBalanceId)
                .stream()
                .map(FabricItemDto::new)
                .collect(Collectors.toList());
    }

    // ==========================================================
    // UPDATE
    // ==========================================================
    @Transactional
    public FabricItemDto update(Long id, FabricItemDto dto) {
        FabricItemEntity entity = fabricItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fabric Item not found"));

        if (dto.getFabricCodeId() != null) entity.setFabricCodeId(dto.getFabricCodeId());
        if (dto.getFabricCode() != null) entity.setFabricCode(dto.getFabricCode());
        if (dto.getFabricTypeId() != null) entity.setFabricTypeId(dto.getFabricTypeId());
        if (dto.getFabricMasterId() != null) entity.setFabricMasterId(dto.getFabricMasterId());
        if (dto.getQuality() != null) entity.setQuality(dto.getQuality());
        if (dto.getPieceNo() != null) entity.setPieceNo(dto.getPieceNo());
        if (dto.getPieceMeter() != null) entity.setPieceMeter(dto.getPieceMeter());
        if (dto.getNetWeight() != null) entity.setNetWeight(dto.getNetWeight());
        if (dto.getAvgWeight() != null) entity.setAvgWeight(dto.getAvgWeight());
        if (dto.getPackingNo() != null) entity.setPackingNo(dto.getPackingNo());
        if (dto.getIsInspected() != null) entity.setIsInspected(dto.getIsInspected());
        if (dto.getGrade() != null) entity.setGradeId(dto.getGrade());

        FabricItemEntity updated = fabricItemRepository.save(entity);
        return new FabricItemDto(updated);
    }

    // ==========================================================
    // DELETE
    // ==========================================================
    public void delete(Long id) {
        if (!fabricItemRepository.existsById(id))
            throw new EntityNotFoundException("Fabric Item not found");
        fabricItemRepository.deleteById(id);
    }

    // ==========================================================
    // Validate DTO
    // ==========================================================
    private void validateDto(FabricItemDto dto) {
        if (dto.getFabricCodeId() == null) throw new IllegalArgumentException("'fabricCodeId' is required");
        if (dto.getQuality() == null) throw new IllegalArgumentException("'quality' is required");
        if (dto.getPieceNo() == null) throw new IllegalArgumentException("'pieceNo' is required");
        if (dto.getPieceMeter() == null) throw new IllegalArgumentException("'pieceMeter' is required");
        if (dto.getNetWeight() == null) throw new IllegalArgumentException("'netWeight' is required");
        if (dto.getAvgWeight() == null) throw new IllegalArgumentException("'avgWeight' is required");
        if (dto.getGrade() == null) throw new IllegalArgumentException("'grade' is required");
        if (dto.getOpeningBalanceId() == null) throw new IllegalArgumentException("'openingBalanceId' is required");
    }
}
