// package com.tech3.erp.service;

// import jakarta.persistence.EntityManager;
// import jakarta.persistence.EntityNotFoundException;
// import jakarta.persistence.PersistenceContext;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.tech3.erp.dto.FlangeIssueDTO;
// import com.tech3.erp.dto.FlangeIssueItemDTO;
// import com.tech3.erp.dto.IssuedFlangeDTO;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.entity.Flange;
// import com.tech3.erp.entity.FlangeIssue;
// import com.tech3.erp.entity.FlangeIssueItem;
// import com.tech3.erp.repository.FlangeIssueRepository;
// import com.tech3.erp.repository.FlangeRepository;
// import com.tech3.erp.util.PaginationHelper;
// import java.util.Objects;
// import java.util.stream.Collectors;

// import java.util.ArrayList;
// import java.util.List;
// import org.springframework.data.domain.Sort;

// @Service
// public class FlangeIssueService {

//     @PersistenceContext
//     private EntityManager entityManager;

//     private final FlangeIssueRepository flangeIssueRepository;
//     private final FlangeRepository flangeRepository;

//     public FlangeIssueService(FlangeIssueRepository flangeIssueRepository, FlangeRepository flangeRepository) {
//         this.flangeIssueRepository = flangeIssueRepository;
//         this.flangeRepository = flangeRepository;
//     }

//     @Transactional
//     public FlangeIssueDTO saveFlangeIssue(FlangeIssueDTO dto) {
//         FlangeIssue order = mapDtoToEntity(dto, new FlangeIssue());

//         List<FlangeIssueItem> items = mapItemDtosToEntities(dto.getItems(), order);
//         order.setItems(items);

//         FlangeIssue savedOrder = flangeIssueRepository.save(order);

//         markFlangesAsIssued(savedOrder);

//         return mapEntityToDto(savedOrder);
//     }

//     @Transactional(readOnly = true)
//     public List<FlangeIssueDTO> getAllFlangeIssues() {
//         List<FlangeIssue> orders = flangeIssueRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
//         List<FlangeIssueDTO> dtos = new ArrayList<>();
//         for (FlangeIssue order : orders) {
//             dtos.add(mapEntityToDto(order));
//         }
//         return dtos;
//     }

//     @Transactional(readOnly = true)
//     public PaginatedResponse<FlangeIssueDTO> getAllFlangeIssuesWithPagination(Long offset, Long limit) {
//         List<FlangeIssueDTO> dtos = getAllFlangeIssues();
//         return PaginationHelper.paginate(dtos, offset, limit);
//     }

//     @Transactional(readOnly = true)
//     public List<FlangeIssueDTO> getAllFlangeIssuesByCompanyId(Long companyId) {
//         List<FlangeIssue> orders = flangeIssueRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id"));
//         List<FlangeIssueDTO> dtos = new ArrayList<>();
//         for (FlangeIssue order : orders) {
//             dtos.add(mapEntityToDto(order));
//         }
//         return dtos;
//     }

//     @Transactional(readOnly = true)
//     public PaginatedResponse<FlangeIssueDTO> getAllFlangeIssuesByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
//         List<FlangeIssueDTO> dtos = getAllFlangeIssuesByCompanyId(companyId);
//         return PaginationHelper.paginate(dtos, offset, limit);
//     }

//     @Transactional(readOnly = true)
//     public FlangeIssueDTO getFlangeIssueById(Long id) {
//         return getFlangeIssueById(id, null);
//     }

//     @Transactional(readOnly = true)
//     public FlangeIssueDTO getFlangeIssueById(Long id, Long companyId) {
//         FlangeIssue order = flangeIssueRepository.findWithItemsById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("FlangeIssue not found with id: " + id));
        
//         if (companyId != null && (order.getCompanyId() == null || !companyId.equals(order.getCompanyId()))) {
//             throw new EntityNotFoundException("FlangeIssue not found for the specified companyId");
//         }

//         return mapEntityToDto(order);
//     }

//     @Transactional
//     public FlangeIssueDTO updateFlangeIssue(Long id, FlangeIssueDTO dto) {
//         return updateFlangeIssue(id, dto, null);
//     }

//     @Transactional
//     public FlangeIssueDTO updateFlangeIssue(Long id, FlangeIssueDTO dto, Long companyId) {
//         FlangeIssue existingOrder = flangeIssueRepository.findWithItemsById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("FlangeIssue not found with id: " + id));
        
//         if (companyId != null && (existingOrder.getCompanyId() == null || !companyId.equals(existingOrder.getCompanyId()))) {
//             throw new EntityNotFoundException("FlangeIssue not found for the specified companyId");
//         }

//         mapDtoToEntity(dto, existingOrder);
        
//         if (companyId != null) {
//             existingOrder.setCompanyId(companyId);
//         }

//         unmarkFlangesByIds(dto.getRemovedFlangeIds());

//         List<FlangeIssueItem> updatedItems = mapItemDtosToEntities(dto.getItems(), existingOrder);
//         existingOrder.getItems().clear();
//         existingOrder.getItems().addAll(updatedItems);

//         FlangeIssue savedOrder = flangeIssueRepository.save(existingOrder);

//         markFlangesAsIssued(savedOrder);
//         return mapEntityToDto(savedOrder);
//     }

//     @Transactional
//     public void deleteFlangeIssue(Long id) {
//         deleteFlangeIssue(id, null);
//     }

//     @Transactional
//     public void deleteFlangeIssue(Long id, Long companyId) {
//         FlangeIssue order = flangeIssueRepository.findWithItemsById(id)
//                 .orElseThrow(() -> new EntityNotFoundException("FlangeIssue not found with id: " + id));
        
//         if (companyId != null && (order.getCompanyId() == null || !companyId.equals(order.getCompanyId()))) {
//             throw new EntityNotFoundException("FlangeIssue not found for the specified companyId");
//         }
        
//         if (order.getItems() != null) {
//             order.getItems().size();
//         }
        
//         unmarkFlangesAsIssued(order);
        
//         flangeIssueRepository.delete(order);
//     }

//     @Transactional
//     public void deleteFlangeAndResetIssued(Long targetId, Long companyId) {
//         FlangeIssue order = flangeIssueRepository.findWithItemsById(targetId)
//                 .orElseGet(() -> flangeIssueRepository.findByItemsFlangeIdAndCompanyId(targetId, companyId)
//                         .orElseThrow(() -> new EntityNotFoundException("FlangeIssue not found with id or flangeId: " + targetId)));

//         if (companyId == null || order.getCompanyId() == null || !companyId.equals(order.getCompanyId())) {
//             throw new EntityNotFoundException("FlangeIssue not found for the specified companyId");
//         }

//         if (order.getItems() != null) {
//             order.getItems().size();
//         }

//         unmarkFlangesAsIssued(order);
//         flangeIssueRepository.delete(order);
//     }

//     public List<IssuedFlangeDTO> getIssuedFlangeNosByIssuedCompanyId(Long issuedCompanyId) {
//         if (issuedCompanyId == null) {
//             throw new IllegalArgumentException("issuedCompanyId is required");
//         }
//         return flangeRepository.findByIssuedCompanyIdAndIsIssued(issuedCompanyId, 1)
//                 .stream()
//                 .map(flange -> new IssuedFlangeDTO(flange.getId(), flange.getId(), flange.getFlangeNo()))
//                 .collect(Collectors.toList());
//     }

//     // Helper: DTO to Entity Mapping
//     private FlangeIssue mapDtoToEntity(FlangeIssueDTO dto, FlangeIssue order) {
//         if (dto.getFlangeIssueNo() != null && !dto.getFlangeIssueNo().isEmpty()) {
//             order.setFlangeIssueNo(dto.getFlangeIssueNo());
//         }
//         order.setFlangeIssueDate(dto.getFlangeIssueDate());
//         order.setConsigneeId(dto.getConsigneeId());
//         order.setVendorId(dto.getVendorId());
//         order.setVechileNo(dto.getVechileNo());
        
//         if (dto.getCompanyId() != null) {
//             order.setCompanyId(dto.getCompanyId());
//         }
        
//         order.setOrderTypeId(dto.getOrderTypeId());
//         order.setIssuedCompanyId(dto.getIssuedCompanyId());
//         order.setIssuedToInternal(dto.isIssuedToInternal());
//         order.setRemarks(dto.getRemarks());
//         return order;
//     }

//     private List<FlangeIssueItem> mapItemDtosToEntities(List<FlangeIssueItemDTO> itemDtos, FlangeIssue order) {
//         List<FlangeIssueItem> items = new ArrayList<>();
//         if (itemDtos != null) {
//             for (FlangeIssueItemDTO itemDto : itemDtos) {
//                 FlangeIssueItem item = new FlangeIssueItem();
//                 item.setFlangeIssue(order);
//                 item.setFlangeId(itemDto.getFlangeId());
//                 items.add(item);
//             }
//         }
//         return items;
//     }

//     private void markFlangesAsIssued(FlangeIssue order) {
//         if (order.getItems() == null || order.getItems().isEmpty()) return;
//         List<Long> ids = order.getItems().stream()
//                 .map(FlangeIssueItem::getFlangeId)
//                 .filter(Objects::nonNull)
//                 .distinct()
//                 .collect(Collectors.toList());
//         if (!ids.isEmpty()) {
//             flangeRepository.markIssuedByIds(ids, order.getIssuedCompanyId(), order.isIssuedToInternal());
//             entityManager.flush();
//         }
//     }

//     private void unmarkFlangesByIds(List<Long> flangeIds) {
//         if (flangeIds == null || flangeIds.isEmpty()) return;
//         List<Long> ids = flangeIds.stream()
//                 .filter(Objects::nonNull)
//                 .distinct()
//                 .collect(Collectors.toList());
//         if (!ids.isEmpty()) {
//             flangeRepository.unmarkIssuedByIds(ids);
//             entityManager.flush();
//         }
//     }

//     private void unmarkFlangesAsIssued(FlangeIssue order) {
//         if (order.getItems() == null || order.getItems().isEmpty()) return;
//         List<Long> ids = order.getItems().stream()
//                 .map(FlangeIssueItem::getFlangeId)
//                 .collect(Collectors.toList());
//         unmarkFlangesByIds(ids);
//     }

//     // Helper: Entity to DTO Mapping
//     private FlangeIssueDTO mapEntityToDto(FlangeIssue order) {
//         FlangeIssueDTO dto = new FlangeIssueDTO();
//         dto.setId(order.getId());
//         dto.setFlangeIssueNo(order.getFlangeIssueNo());
//         dto.setFlangeIssueDate(order.getFlangeIssueDate());
//         dto.setConsigneeId(order.getConsigneeId());
//         dto.setVendorId(order.getVendorId());
//         dto.setVechileNo(order.getVechileNo());
//         dto.setCompanyId(order.getCompanyId());
//         dto.setOrderTypeId(order.getOrderTypeId());
//         dto.setIssuedCompanyId(order.getIssuedCompanyId());
//         dto.setIssuedToInternal(order.isIssuedToInternal());
//         dto.setRemarks(order.getRemarks());

//         List<FlangeIssueItemDTO> itemDtos = new ArrayList<>();
//         if (order.getItems() != null) {
//             for (FlangeIssueItem item : order.getItems()) {
//                 FlangeIssueItemDTO itemDto = new FlangeIssueItemDTO();
//                 itemDto.setFlangeId(item.getFlangeId());
//                 itemDtos.add(itemDto);
//             }
//         }
//         dto.setItems(itemDtos);
//         return dto;
//     }
// }


package com.tech3.erp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tech3.erp.dto.FlangeIssueDTO;
import com.tech3.erp.dto.FlangeIssueItemDTO;
import com.tech3.erp.dto.IssuedFlangeDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Flange;
import com.tech3.erp.entity.FlangeIssue;
import com.tech3.erp.entity.FlangeIssueItem;
import com.tech3.erp.repository.FlangeBeamInwardDetailRepository;
import com.tech3.erp.repository.FlangeIssueRepository;
import com.tech3.erp.repository.FlangeRepository;
import com.tech3.erp.repository.SizingProductionDetailRepository;
import com.tech3.erp.util.PaginationHelper;
import java.util.Objects;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;

@Service
public class FlangeIssueService {

    @PersistenceContext
    private EntityManager entityManager;

    private final FlangeIssueRepository flangeIssueRepository;
    private final FlangeRepository flangeRepository;
    private final FlangeBeamInwardDetailRepository flangeBeamInwardDetailRepository;
    private final SizingProductionDetailRepository sizingProductionDetailRepository;

    public FlangeIssueService(FlangeIssueRepository flangeIssueRepository,
            FlangeRepository flangeRepository,
            FlangeBeamInwardDetailRepository flangeBeamInwardDetailRepository,
            SizingProductionDetailRepository sizingProductionDetailRepository) {
        this.flangeIssueRepository = flangeIssueRepository;
        this.flangeRepository = flangeRepository;
        this.flangeBeamInwardDetailRepository = flangeBeamInwardDetailRepository;
        this.sizingProductionDetailRepository = sizingProductionDetailRepository;
    }

    @Transactional
    public FlangeIssueDTO saveFlangeIssue(FlangeIssueDTO dto) {
        FlangeIssue order = mapDtoToEntity(dto, new FlangeIssue());

        List<FlangeIssueItem> items = mapItemDtosToEntities(dto.getItems(), order);
        order.setItems(items);

        FlangeIssue savedOrder = flangeIssueRepository.save(order);

        markFlangesAsIssued(savedOrder);

        return mapEntityToDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<FlangeIssueDTO> getAllFlangeIssues() {
        List<FlangeIssue> orders = flangeIssueRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<FlangeIssueDTO> dtos = new ArrayList<>();
        for (FlangeIssue order : orders) {
            dtos.add(mapEntityToDto(order));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<FlangeIssueDTO> getAllFlangeIssuesWithPagination(Long offset, Long limit) {
        List<FlangeIssueDTO> dtos = getAllFlangeIssues();
        return PaginationHelper.paginate(dtos, offset, limit);
    }

    @Transactional(readOnly = true)
    public List<FlangeIssueDTO> getAllFlangeIssuesByCompanyId(Long companyId) {
        List<FlangeIssue> orders = flangeIssueRepository.findByCompanyId(companyId, Sort.by(Sort.Direction.DESC, "id"));
        List<FlangeIssueDTO> dtos = new ArrayList<>();
        for (FlangeIssue order : orders) {
            dtos.add(mapEntityToDto(order));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<FlangeIssueDTO> getAllFlangeIssuesByCompanyIdWithPagination(Long companyId, Long offset, Long limit) {
        List<FlangeIssueDTO> dtos = getAllFlangeIssuesByCompanyId(companyId);
        return PaginationHelper.paginate(dtos, offset, limit);
    }

    @Transactional(readOnly = true)
    public FlangeIssueDTO getFlangeIssueById(Long id) {
        return getFlangeIssueById(id, null);
    }

    @Transactional(readOnly = true)
    public FlangeIssueDTO getFlangeIssueById(Long id, Long companyId) {
        FlangeIssue order = flangeIssueRepository.findWithItemsById(id)
                .orElseThrow(() -> new EntityNotFoundException("FlangeIssue not found with id: " + id));
        
        if (companyId != null && (order.getCompanyId() == null || !companyId.equals(order.getCompanyId()))) {
            throw new EntityNotFoundException("FlangeIssue not found for the specified companyId");
        }

        return mapEntityToDto(order);
    }

    @Transactional
    public FlangeIssueDTO updateFlangeIssue(Long id, FlangeIssueDTO dto) {
        return updateFlangeIssue(id, dto, null);
    }

    @Transactional
    public FlangeIssueDTO updateFlangeIssue(Long id, FlangeIssueDTO dto, Long companyId) {
        FlangeIssue existingOrder = flangeIssueRepository.findWithItemsById(id)
                .orElseThrow(() -> new EntityNotFoundException("FlangeIssue not found with id: " + id));
        
        if (companyId != null && (existingOrder.getCompanyId() == null || !companyId.equals(existingOrder.getCompanyId()))) {
            throw new EntityNotFoundException("FlangeIssue not found for the specified companyId");
        }

        mapDtoToEntity(dto, existingOrder);
        
        if (companyId != null) {
            existingOrder.setCompanyId(companyId);
        }

        unmarkFlangesByIds(dto.getRemovedFlangeIds());

        List<FlangeIssueItem> updatedItems = mapItemDtosToEntities(dto.getItems(), existingOrder);
        existingOrder.getItems().clear();
        existingOrder.getItems().addAll(updatedItems);

        FlangeIssue savedOrder = flangeIssueRepository.save(existingOrder);

        markFlangesAsIssued(savedOrder);
        return mapEntityToDto(savedOrder);
    }

    @Transactional
    public void deleteFlangeIssue(Long id) {
        deleteFlangeIssue(id, null);
    }

    @Transactional
    public void deleteFlangeIssue(Long id, Long companyId) {
        FlangeIssue order = flangeIssueRepository.findWithItemsById(id)
                .orElseThrow(() -> new EntityNotFoundException("FlangeIssue not found with id: " + id));
        
        if (companyId != null && (order.getCompanyId() == null || !companyId.equals(order.getCompanyId()))) {
            throw new EntityNotFoundException("FlangeIssue not found for the specified companyId");
        }
        
        if (order.getItems() != null) {
            order.getItems().size();
        }
        
        ensureFlangeIssueDeletable(order);

        unmarkFlangesAsIssued(order);
        
        flangeIssueRepository.delete(order);
    }

    @Transactional
    public void deleteFlangeAndResetIssued(Long targetId, Long companyId) {
        FlangeIssue order = flangeIssueRepository.findWithItemsById(targetId)
                .orElseGet(() -> flangeIssueRepository.findByItemsFlangeIdAndCompanyId(targetId, companyId)
                        .orElseThrow(() -> new EntityNotFoundException("FlangeIssue not found with id or flangeId: " + targetId)));

        if (companyId == null || order.getCompanyId() == null || !companyId.equals(order.getCompanyId())) {
            throw new EntityNotFoundException("FlangeIssue not found for the specified companyId");
        }

        if (order.getItems() != null) {
            order.getItems().size();
        }

        ensureFlangeIssueDeletable(order);

        unmarkFlangesAsIssued(order);
        flangeIssueRepository.delete(order);
    }

    private void ensureFlangeIssueDeletable(FlangeIssue order) {
        if (order == null || order.getItems() == null) {
            return;
        }
        for (FlangeIssueItem item : order.getItems()) {
            if (item == null || item.getFlangeId() == null) {
                continue;
            }
            String flangeReference = describeFlange(item.getFlangeId());
            if (flangeBeamInwardDetailRepository.existsByFlangeId(item.getFlangeId())
                    || (flangeReference != null && flangeBeamInwardDetailRepository.existsByFlangeNo(flangeReference))) {
                throw new IllegalStateException("Cannot delete flange issue: flange " + flangeReference + " is already mapped in Sizing Flange Inward");
            }
            if (sizingProductionDetailRepository.existsByFlangeId(item.getFlangeId())
                    || (flangeReference != null && sizingProductionDetailRepository.existsByFlangeNo(flangeReference))) {
                throw new IllegalStateException("Cannot delete flange issue: flange " + flangeReference + " is already mapped in Sizing Production");
            }
        }
    }

    private String describeFlange(Long flangeId) {
        return flangeRepository.findById(flangeId)
                .map(Flange::getFlangeNo)
                .orElse("ID " + flangeId);
    }

    public List<IssuedFlangeDTO> getIssuedFlangeNosByIssuedCompanyId(Long issuedCompanyId) {
        if (issuedCompanyId == null) {
            throw new IllegalArgumentException("issuedCompanyId is required");
        }
        return flangeRepository.findByIssuedCompanyIdAndIsIssued(issuedCompanyId, 1)
                .stream()
                .map(flange -> new IssuedFlangeDTO(flange.getId(), flange.getId(), flange.getFlangeNo()))
                .collect(Collectors.toList());
    }

    // Helper: DTO to Entity Mapping
    private FlangeIssue mapDtoToEntity(FlangeIssueDTO dto, FlangeIssue order) {
        if (dto.getFlangeIssueNo() != null && !dto.getFlangeIssueNo().isEmpty()) {
            order.setFlangeIssueNo(dto.getFlangeIssueNo());
        }
        order.setFlangeIssueDate(dto.getFlangeIssueDate());
        order.setConsigneeId(dto.getConsigneeId());
        order.setVendorId(dto.getVendorId());
        order.setVechileNo(dto.getVechileNo());
        
        if (dto.getCompanyId() != null) {
            order.setCompanyId(dto.getCompanyId());
        }
        
        order.setOrderTypeId(dto.getOrderTypeId());
        order.setIssuedCompanyId(dto.getIssuedCompanyId());
        order.setIssuedToInternal(dto.isIssuedToInternal());
        order.setRemarks(dto.getRemarks());
        return order;
    }

    private List<FlangeIssueItem> mapItemDtosToEntities(List<FlangeIssueItemDTO> itemDtos, FlangeIssue order) {
        List<FlangeIssueItem> items = new ArrayList<>();
        if (itemDtos != null) {
            for (FlangeIssueItemDTO itemDto : itemDtos) {
                FlangeIssueItem item = new FlangeIssueItem();
                item.setFlangeIssue(order);
                item.setFlangeId(itemDto.getFlangeId());
                items.add(item);
            }
        }
        return items;
    }

    private void markFlangesAsIssued(FlangeIssue order) {
        if (order.getItems() == null || order.getItems().isEmpty()) return;
        List<Long> ids = order.getItems().stream()
                .map(FlangeIssueItem::getFlangeId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (!ids.isEmpty()) {
            flangeRepository.markIssuedByIds(ids, order.getIssuedCompanyId(), order.isIssuedToInternal());
            entityManager.flush();
        }
    }

    private void unmarkFlangesByIds(List<Long> flangeIds) {
        if (flangeIds == null || flangeIds.isEmpty()) return;
        List<Long> ids = flangeIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        if (!ids.isEmpty()) {
            flangeRepository.unmarkIssuedByIds(ids);
            entityManager.flush();
        }
    }

    private void unmarkFlangesAsIssued(FlangeIssue order) {
        if (order.getItems() == null || order.getItems().isEmpty()) return;
        List<Long> ids = order.getItems().stream()
                .map(FlangeIssueItem::getFlangeId)
                .collect(Collectors.toList());
        unmarkFlangesByIds(ids);
    }

    // Helper: Entity to DTO Mapping
    private FlangeIssueDTO mapEntityToDto(FlangeIssue order) {
        FlangeIssueDTO dto = new FlangeIssueDTO();
        dto.setId(order.getId());
        dto.setFlangeIssueNo(order.getFlangeIssueNo());
        dto.setFlangeIssueDate(order.getFlangeIssueDate());
        dto.setConsigneeId(order.getConsigneeId());
        dto.setVendorId(order.getVendorId());
        dto.setVechileNo(order.getVechileNo());
        dto.setCompanyId(order.getCompanyId());
        dto.setOrderTypeId(order.getOrderTypeId());
        dto.setIssuedCompanyId(order.getIssuedCompanyId());
        dto.setIssuedToInternal(order.isIssuedToInternal());
        dto.setRemarks(order.getRemarks());

        List<FlangeIssueItemDTO> itemDtos = new ArrayList<>();
        if (order.getItems() != null) {
            for (FlangeIssueItem item : order.getItems()) {
                FlangeIssueItemDTO itemDto = new FlangeIssueItemDTO();
                itemDto.setFlangeId(item.getFlangeId());
                itemDtos.add(itemDto);
            }
        }
        dto.setItems(itemDtos);
        return dto;
    }
}

