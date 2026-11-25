package com.tech3.erp.service;

import com.tech3.erp.dto.WovenFabricMasterDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.FabricTestingDetailDTO;
import com.tech3.erp.dto.FabricWarpDetailDTO;
import com.tech3.erp.dto.FabricWeftDetailDTO;
import com.tech3.erp.util.PaginationHelper;
import com.tech3.erp.entity.WovenFabricMaster;
import com.tech3.erp.entity.FabricTestingDetail;
import com.tech3.erp.entity.FabricWarpDetail;
import com.tech3.erp.entity.FabricWeftDetail;
import com.tech3.erp.entity.State;
import com.tech3.erp.repository.WovenFabricMasterRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;

import com.tech3.erp.repository.FabricTestingDetailRepository;
import com.tech3.erp.repository.FabricWarpDetailRepository;
import com.tech3.erp.repository.FabricWeftDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WovenFabricMasterService {

    private final WovenFabricMasterRepository wovenFabricMasterRepository;
    private final FabricWarpDetailRepository warpDetailRepository;
    private final FabricWeftDetailRepository weftDetailRepository;
    private final FabricTestingDetailRepository testingDetailRepository;
    private final EntityManager entityManager;

    @Autowired(required = false)
    private MediaService mediaService;

    public WovenFabricMasterService(WovenFabricMasterRepository wovenFabricMasterRepository,
                               FabricWarpDetailRepository warpDetailRepository,
                               FabricWeftDetailRepository weftDetailRepository,
                               FabricTestingDetailRepository testingDetailRepository,
                               EntityManager entityManager) {
        this.wovenFabricMasterRepository = wovenFabricMasterRepository;
        this.warpDetailRepository = warpDetailRepository;
        this.weftDetailRepository = weftDetailRepository;
        this.testingDetailRepository = testingDetailRepository;
        this.entityManager = entityManager;
    }

 @Transactional
    public WovenFabricMasterDTO createFabricMaster(WovenFabricMasterDTO dto) {

    	calculateGstValues(dto);
    	validateCutWidth(dto);
    	validateAndSetGreigeCode(dto);

        WovenFabricMaster entity = new WovenFabricMaster();
        mapDtoToEntity(dto, entity);
        
        // Set audit fields for creation
        entity.setCreatedOn(LocalDateTime.now());
        entity.setLastModified(LocalDateTime.now());
        // createdBy and lastModifiedBy will be set from frontend payload
        
        // Map Warp Details
        if (dto.getWarpDetails() != null) {
            List<FabricWarpDetail> warps = dto.getWarpDetails().stream()
                .filter(w -> w.getYarnId() != null)
                .map(w -> {
                    FabricWarpDetail detail = new FabricWarpDetail(w);
                    detail.setFabricMaster(entity);
                    return detail;
                }).collect(Collectors.toList());
            entity.setWarpDetails(warps);
        }

        // Map Weft Details
        if (dto.getWeftDetails() != null) {
            List<FabricWeftDetail> wefts = dto.getWeftDetails().stream()
                .filter(w -> w.getYarnId() != null)
                .map(w -> {
                    FabricWeftDetail detail = new FabricWeftDetail(w);
                    detail.setFabricMaster(entity);
                    return detail;
                }).collect(Collectors.toList());
            entity.setWeftDetails(wefts);
        }
        
        // Map Testing Details
        if (dto.getTestingDetails() != null) {
            List<FabricTestingDetail> testing = dto.getTestingDetails().stream().map(w -> {
                FabricTestingDetail detail = new FabricTestingDetail(w);
                detail.setFabricMaster(entity);
                return detail;
            }).collect(Collectors.toList());
            entity.setTestingDetails(testing);
        }

        WovenFabricMaster saved = wovenFabricMasterRepository.save(entity);
        wovenFabricMasterRepository.flush();

        entityManager.clear();
        WovenFabricMaster reloadedEntity = wovenFabricMasterRepository.findById(saved.getId()).orElse(saved);
        WovenFabricMasterDTO resultDto = new WovenFabricMasterDTO(reloadedEntity);
        enrichDtoWithMediaDetails(resultDto);
        return resultDto;
    }


    // @Transactional
    // public WovenFabricMasterDTO createFabricMaster(WovenFabricMasterDTO dto) {

    // 	calculateGstValues(dto);
    // 	validateCutWidth(dto);
    // 	validateAndSetGreigeCode(dto);

    //     WovenFabricMaster entity = new WovenFabricMaster();
    //     mapDtoToEntity(dto, entity);
    //     WovenFabricMaster saved = wovenFabricMasterRepository.save(entity);

    //     // Ensure ID is generated and flushed to DB
    //     wovenFabricMasterRepository.flush();

    //     // Save warp details
    //     if (dto.getWarpDetails() != null) {
    //         List<FabricWarpDetail> warps = dto.getWarpDetails().stream().map(w -> {
    //             FabricWarpDetail detail = new FabricWarpDetail(w);
    //             detail.setFabricMaster(saved);
    //             return detail;
    //         }).collect(Collectors.toList());
    //         warpDetailRepository.saveAll(warps);
    //     }

    //     // Save weft details
    //     if (dto.getWeftDetails() != null) {
    //         List<FabricWeftDetail> wefts = dto.getWeftDetails().stream().map(w -> {
    //             FabricWeftDetail detail = new FabricWeftDetail(w);
    //             detail.setFabricMaster(saved);
    //             return detail;
    //         }).collect(Collectors.toList());
    //         weftDetailRepository.saveAll(wefts);
    //     }
        
    //  // Save testing details
    //     if (dto.getTestingDetails() != null) {
    //         List<FabricTestingDetail> testing = dto.getTestingDetails().stream().map(w -> {
    //             FabricTestingDetail detail = new FabricTestingDetail(w);
    //             detail.setFabricMaster(saved);
    //             return detail;
    //         }).collect(Collectors.toList());
    //         testingDetailRepository.saveAll(testing);
    //     }

    //     entityManager.clear();
    //     WovenFabricMaster reloadedEntity = wovenFabricMasterRepository.findById(saved.getId()).orElse(saved);
    //     WovenFabricMasterDTO resultDto = new WovenFabricMasterDTO(reloadedEntity);
    //     enrichDtoWithMediaDetails(resultDto);
    //     return resultDto;
    // }

    public WovenFabricMasterDTO getFabricMasterById(Long id) {
        WovenFabricMaster entity = wovenFabricMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FabricMaster not found with ID: " + id));
        WovenFabricMasterDTO dto = new WovenFabricMasterDTO(entity);
        enrichDtoWithMediaDetails(dto);
        return dto;
    }

    public List<WovenFabricMasterDTO> getAllFabricMasters() {
        return wovenFabricMasterRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(WovenFabricMaster ::getId, Comparator.reverseOrder()))
                .map(WovenFabricMasterDTO::new)
                .peek(this::enrichDtoWithMediaDetails)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<WovenFabricMasterDTO> getAllFabricMastersWithPagination(Long offset, Long limit) {
        List<WovenFabricMasterDTO> all = getAllFabricMasters();
        return PaginationHelper.paginate(all, offset, limit);
    }

    // public WovenFabricMasterDTO updateFabricMaster(Long id, WovenFabricMasterDTO dto) {

    //     calculateGstValues(dto);
    //     validateCutWidth(dto);
    //     validateAndSetGreigeCode(dto);

    //     WovenFabricMaster existing = wovenFabricMasterRepository.findById(id)
    //             .orElseThrow(() -> new IllegalArgumentException("FabricMaster not found with ID: " + id));
    //     mapDtoToEntity(dto, existing);
    //     WovenFabricMaster updated = wovenFabricMasterRepository.save(existing);
        
    //     if (dto.getWarpDetails() != null) {
    //         // Get existing warp details from the entity itself
    //         List<FabricWarpDetail> existingWarps = updated.getWarpDetails();
    //         if (existingWarps == null) {
    //             existingWarps = new ArrayList<>();
    //         }
            
    //         Map<Long, FabricWarpDetail> existingWarpsMap = existingWarps.stream()
    //             .collect(Collectors.toMap(FabricWarpDetail::getId, w -> w));
            
    //         List<FabricWarpDetail> warpsToSave = new ArrayList<>();
    //         List<Long> incomingWarpIds = new ArrayList<>();
            
    //         for (FabricWarpDetailDTO warpDTO : dto.getWarpDetails()) {
    //             if (warpDTO.getYarnId() == null) continue; // Skip if no yarn selected
                
    //             if (warpDTO.getId() != null && existingWarpsMap.containsKey(warpDTO.getId())) {
    //                 // Update existing warp detail
    //                 FabricWarpDetail existingWarp = existingWarpsMap.get(warpDTO.getId());
    //                 existingWarp.setYarnId(warpDTO.getYarnId());
    //                 existingWarp.setPattern(warpDTO.getPattern());
    //                 existingWarp.setShrinkagePercent(warpDTO.getShrinkagePercent());
    //                 existingWarp.setGramsPerMeter(warpDTO.getGramsPerMeter());
                    
    //                 // Update epi/color
    //                 if (warpDTO.getEpi() != null) {
    //                     existingWarp.setColor(warpDTO.getEpi().shortValue());
    //                 } else if (warpDTO.getColor() != null) {
    //                     existingWarp.setColor(warpDTO.getColor());
    //                 }
                    
    //                 warpsToSave.add(existingWarp);
    //                 incomingWarpIds.add(warpDTO.getId());
    //             } else {
    //                 // Create new warp detail
    //                 FabricWarpDetail newWarp = new FabricWarpDetail(warpDTO);
    //                 newWarp.setFabricMaster(updated);
    //                 warpsToSave.add(newWarp);
    //             }
    //         }
            
    //         // Remove warps that are not in the incoming DTO
    //         List<FabricWarpDetail> warpsToRemove = existingWarps.stream()
    //             .filter(w -> !incomingWarpIds.contains(w.getId()))
    //             .collect(Collectors.toList());
            
    //         if (!warpsToRemove.isEmpty()) {
    //             warpDetailRepository.deleteAll(warpsToRemove);
    //             existingWarps.removeAll(warpsToRemove);
    //         }
            
    //         warpDetailRepository.saveAll(warpsToSave);
    //     }

    //     // Handle weft details - update existing and add new ones
    //     // if (dto.getWeftDetails() != null) {
    //     //     // Get existing weft details from the entity itself
    //     //     List<FabricWeftDetail> existingWefts = updated.getWeftDetails();
    //     //     if (existingWefts == null) {
    //     //         existingWefts = new ArrayList<>();
    //     //     }
            
    //     //     Map<Long, FabricWeftDetail> existingWeftsMap = existingWefts.stream()
    //     //         .collect(Collectors.toMap(FabricWeftDetail::getId, w -> w));
            
    //     //     List<FabricWeftDetail> weftsToSave = new ArrayList<>();
            
    //     //     for (FabricWeftDetailDTO weftDTO : dto.getWeftDetails()) {
    //     //         if (weftDTO.getYarnId() == null) continue; // Skip if no yarn selected
                
    //     //         if (weftDTO.getId() != null && existingWeftsMap.containsKey(weftDTO.getId())) {
    //     //             // Update existing weft detail
    //     //             FabricWeftDetail existingWeft = existingWeftsMap.get(weftDTO.getId());
    //     //             existingWeft.setYarnId(weftDTO.getYarnId());
    //     //             existingWeft.setPattern(weftDTO.getPattern());
    //     //             existingWeft.setShrinkagePercent(weftDTO.getShrinkagePercent());
    //     //             existingWeft.setGramsPerMeter(weftDTO.getGramsPerMeter());
    //     //             weftsToSave.add(existingWeft);
    //     //         } else {
    //     //             // Create new weft detail
    //     //             FabricWeftDetail newWeft = new FabricWeftDetail(weftDTO);
    //     //             newWeft.setFabricMaster(updated);
    //     //             weftsToSave.add(newWeft);
    //     //         }
    //     //     }
            
    //     //     weftDetailRepository.saveAll(weftsToSave);
    //     // }
    //     if (dto.getWeftDetails() != null) {
    //         // Get existing weft details from the entity itself
    //         List<FabricWeftDetail> existingWefts = updated.getWeftDetails();
    //         if (existingWefts == null) {
    //             existingWefts = new ArrayList<>();
    //         }
            
    //         Map<Long, FabricWeftDetail> existingWeftsMap = existingWefts.stream()
    //             .collect(Collectors.toMap(FabricWeftDetail::getId, w -> w));
            
    //         List<FabricWeftDetail> weftsToSave = new ArrayList<>();
    //         List<Long> incomingWeftIds = new ArrayList<>();
            
    //         for (FabricWeftDetailDTO weftDTO : dto.getWeftDetails()) {
    //             if (weftDTO.getYarnId() == null) continue; // Skip if no yarn selected
                
    //             if (weftDTO.getId() != null && existingWeftsMap.containsKey(weftDTO.getId())) {
    //                 // Update existing weft detail
    //                 FabricWeftDetail existingWeft = existingWeftsMap.get(weftDTO.getId());
    //                 existingWeft.setYarnId(weftDTO.getYarnId());
    //                 existingWeft.setPattern(weftDTO.getPattern());
    //                 existingWeft.setShrinkagePercent(weftDTO.getShrinkagePercent());
    //                 existingWeft.setGramsPerMeter(weftDTO.getGramsPerMeter());
                    
    //                 // Update ppi/color
    //                 if (weftDTO.getPpi() != null) {
    //                     existingWeft.setColor(weftDTO.getPpi().shortValue());
    //                 } else if (weftDTO.getColor() != null) {
    //                     existingWeft.setColor(weftDTO.getColor());
    //                 }
                    
    //                 weftsToSave.add(existingWeft);
    //                 incomingWeftIds.add(weftDTO.getId());
    //             } else {
    //                 // Create new weft detail
    //                 FabricWeftDetail newWeft = new FabricWeftDetail(weftDTO);
    //                 newWeft.setFabricMaster(updated);
    //                 weftsToSave.add(newWeft);
    //             }
    //         }
            
    //         // Remove wefts that are not in the incoming DTO
    //         List<FabricWeftDetail> weftsToRemove = existingWefts.stream()
    //             .filter(w -> !incomingWeftIds.contains(w.getId()))
    //             .collect(Collectors.toList());
            
    //         if (!weftsToRemove.isEmpty()) {
    //             weftDetailRepository.deleteAll(weftsToRemove);
    //             existingWefts.removeAll(weftsToRemove);
    //         }
            
    //         weftDetailRepository.saveAll(weftsToSave);
    //     }

    //     // Handle testing details - update existing and add new ones
    //     if (dto.getTestingDetails() != null) {
    //         // Get existing testing details from the entity itself
    //         List<FabricTestingDetail> existingTesting = updated.getTestingDetails();
    //         if (existingTesting == null) {
    //             existingTesting = new ArrayList<>();
    //         }
            
    //         Map<Long, FabricTestingDetail> existingTestingMap = existingTesting.stream()
    //             .collect(Collectors.toMap(FabricTestingDetail::getId, t -> t));
            
    //         List<FabricTestingDetail> testingToSave = new ArrayList<>();
            
    //         for (FabricTestingDetailDTO testingDTO : dto.getTestingDetails()) {
    //             if (testingDTO.getId() != null && existingTestingMap.containsKey(testingDTO.getId())) {
    //                 // Update existing testing detail
    //                 FabricTestingDetail existingTestingDetail = existingTestingMap.get(testingDTO.getId());
    //                 existingTestingDetail.setTestingParameter(testingDTO.getTestingParameter());
    //                 existingTestingDetail.setStdValue(testingDTO.getStdValue());
    //                 testingToSave.add(existingTestingDetail);
    //             } else {
    //                 // Create new testing detail
    //                 FabricTestingDetail newTesting = new FabricTestingDetail(testingDTO);
    //                 newTesting.setFabricMaster(updated);
    //                 testingToSave.add(newTesting);
    //             }
    //         }
            
    //         testingDetailRepository.saveAll(testingToSave);
    //     }

    //     // Optional: Update warp and weft details if needed

    //     WovenFabricMasterDTO resultDto = new WovenFabricMasterDTO(updated);
    //     enrichDtoWithMediaDetails(resultDto);
    //     return resultDto;
    // }
    @Transactional
    public WovenFabricMasterDTO updateFabricMaster(Long id, WovenFabricMasterDTO dto) {

        calculateGstValues(dto);
        validateCutWidth(dto);
        validateAndSetGreigeCode(dto);

        WovenFabricMaster existing = wovenFabricMasterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FabricMaster not found with ID: " + id));
        mapDtoToEntity(dto, existing);
        
        // Set audit fields for update
        existing.setLastModified(LocalDateTime.now());
        // lastModifiedBy will be set from frontend payload
        
        // Handle warp details - update existing, add new ones, and remove deleted ones
        if (dto.getWarpDetails() != null) {
            List<FabricWarpDetail> existingWarps = existing.getWarpDetails();
            if (existingWarps == null) {
                existingWarps = new ArrayList<>();
                existing.setWarpDetails(existingWarps);
            }
            
            Map<Long, FabricWarpDetail> existingWarpsMap = existingWarps.stream()
                .collect(Collectors.toMap(FabricWarpDetail::getId, w -> w));
            
            List<Long> incomingWarpIds = new ArrayList<>();
            List<FabricWarpDetail> newWarps = new ArrayList<>();
            
            for (FabricWarpDetailDTO warpDTO : dto.getWarpDetails()) {
                if (warpDTO.getYarnId() == null) continue;
                
                if (warpDTO.getId() != null && existingWarpsMap.containsKey(warpDTO.getId())) {
                    FabricWarpDetail existingWarp = existingWarpsMap.get(warpDTO.getId());
                    existingWarp.setYarnId(warpDTO.getYarnId());
                    existingWarp.setPattern(warpDTO.getPattern());
                    existingWarp.setShrinkagePercent(warpDTO.getShrinkagePercent());
                    existingWarp.setWarpWastePercentage(warpDTO.getWarpWastePercentage());
                    existingWarp.setGramsPerMeter(warpDTO.getGramsPerMeter());
                    
                    if (warpDTO.getEpi() != null) {
                        existingWarp.setColor(warpDTO.getEpi().shortValue());
                    } else if (warpDTO.getColor() != null) {
                        existingWarp.setColor(warpDTO.getColor());
                    }
                    incomingWarpIds.add(warpDTO.getId());
                } else {
                    FabricWarpDetail newWarp = new FabricWarpDetail(warpDTO);
                    newWarp.setFabricMaster(existing);
                    newWarps.add(newWarp);
                }
            }
            
            // Remove those not in incoming
            existingWarps.removeIf(w -> w.getId() != null && !incomingWarpIds.contains(w.getId()));
            // Add new ones
            existingWarps.addAll(newWarps);
        }

        // Handle weft details
        if (dto.getWeftDetails() != null) {
            List<FabricWeftDetail> existingWefts = existing.getWeftDetails();
            if (existingWefts == null) {
                existingWefts = new ArrayList<>();
                existing.setWeftDetails(existingWefts);
            }
            
            Map<Long, FabricWeftDetail> existingWeftsMap = existingWefts.stream()
                .collect(Collectors.toMap(FabricWeftDetail::getId, w -> w));
            
            List<Long> incomingWeftIds = new ArrayList<>();
            List<FabricWeftDetail> newWefts = new ArrayList<>();
            
            for (FabricWeftDetailDTO weftDTO : dto.getWeftDetails()) {
                if (weftDTO.getYarnId() == null) continue;
                
                if (weftDTO.getId() != null && existingWeftsMap.containsKey(weftDTO.getId())) {
                    FabricWeftDetail existingWeft = existingWeftsMap.get(weftDTO.getId());
                    existingWeft.setYarnId(weftDTO.getYarnId());
                    existingWeft.setPattern(weftDTO.getPattern());
                    existingWeft.setShrinkagePercent(weftDTO.getShrinkagePercent());
                    existingWeft.setWastePercentage(weftDTO.getWastePercentage());
                    existingWeft.setGramsPerMeter(weftDTO.getGramsPerMeter());
                    
                    if (weftDTO.getPpi() != null) {
                        existingWeft.setColor(weftDTO.getPpi().shortValue());
                    } else if (weftDTO.getColor() != null) {
                        existingWeft.setColor(weftDTO.getColor());
                    }
                    incomingWeftIds.add(weftDTO.getId());
                } else {
                    FabricWeftDetail newWeft = new FabricWeftDetail(weftDTO);
                    newWeft.setFabricMaster(existing);
                    newWefts.add(newWeft);
                }
            }
            
            existingWefts.removeIf(w -> w.getId() != null && !incomingWeftIds.contains(w.getId()));
            existingWefts.addAll(newWefts);
        }
        
        // Handle testing details
        if (dto.getTestingDetails() != null) {
            List<FabricTestingDetail> existingTesting = existing.getTestingDetails();
            if (existingTesting == null) {
                existingTesting = new ArrayList<>();
                existing.setTestingDetails(existingTesting);
            }
            
            Map<Long, FabricTestingDetail> existingTestingMap = existingTesting.stream()
                .collect(Collectors.toMap(FabricTestingDetail::getId, t -> t));
            
            List<Long> incomingTestingIds = new ArrayList<>();
            List<FabricTestingDetail> newTestingDetails = new ArrayList<>();
            
            for (FabricTestingDetailDTO testingDTO : dto.getTestingDetails()) {
                if (testingDTO.getId() != null && existingTestingMap.containsKey(testingDTO.getId())) {
                    FabricTestingDetail existingTestingDetail = existingTestingMap.get(testingDTO.getId());
                    existingTestingDetail.setTestingParameter(testingDTO.getTestingParameter());
                    existingTestingDetail.setStdValue(testingDTO.getStdValue());
                    incomingTestingIds.add(testingDTO.getId());
                } else {
                    FabricTestingDetail newTesting = new FabricTestingDetail(testingDTO);
                    newTesting.setFabricMaster(existing);
                    newTestingDetails.add(newTesting);
                }
            }
            
            existingTesting.removeIf(t -> t.getId() != null && !incomingTestingIds.contains(t.getId()));
            existingTesting.addAll(newTestingDetails);
        }

        WovenFabricMaster updated = wovenFabricMasterRepository.save(existing);
        WovenFabricMasterDTO resultDto = new WovenFabricMasterDTO(updated);
        enrichDtoWithMediaDetails(resultDto);
        return resultDto;
    }


//    public void deleteFabricMaster(Long id) {
//        if (!wovenFabricMasterRepository.existsById(id)) {
//            throw new IllegalArgumentException("FabricMaster not found with ID: " + id);
//        }
//        wovenFabricMasterRepository.deleteById(id);
//    }
    
    private void calculateGstValues(WovenFabricMasterDTO dto) {
        if (dto.getTaxType() != null && dto.getGstPercent() != null) {
            BigDecimal gstPercent = dto.getGstPercent();
            
            if (dto.getTaxType() == 2) { // IGST
                // For IGST, set IGST to full percentage and CGST/SGST to zero
                dto.setIgst(gstPercent);
                dto.setCgst(BigDecimal.ZERO);
                dto.setSgst(BigDecimal.ZERO);
            } else if (dto.getTaxType() == 1) { // CGST/SGST
                // For CGST/SGST, split equally and set IGST to zero
                BigDecimal halfPercent = gstPercent.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
                dto.setCgst(halfPercent);
                dto.setSgst(halfPercent);
                dto.setIgst(BigDecimal.ZERO);
            }
        }
        
        // If manual values are provided, they will override the calculated values
        // This maintains backward compatibility with existing flows
    }

    private void validateCutWidth(WovenFabricMasterDTO dto) {
        if (dto.getCutWidth() != null && !dto.getCutWidth().trim().isEmpty() && dto.getFabricWidth() != null) {
            try {
                // Try to parse cutWidth as BigDecimal for comparison
                BigDecimal cutWidthValue = new BigDecimal(dto.getCutWidth().trim());
                if (cutWidthValue.compareTo(dto.getFabricWidth()) > 0) {
                    throw new IllegalArgumentException("Cut Width cannot exceed Fabric Width");
                }
            } catch (NumberFormatException e) {
                // If cutWidth is not a valid number, skip validation (since it's alpha-numerical)
                // This allows values like "50.5 cm" or other descriptive text
            }
        }
    }

    private void validateAndSetGreigeCode(WovenFabricMasterDTO dto) {
        Short fabricType = dto.getFabricType();
        
        if (fabricType == null) {
            throw new IllegalArgumentException("Fabric Type is required");
        }
        
        // Fabric Type: 1 = Greige, 2 = Finished, 3 = Finished Shade
        if (fabricType == 1) {
            // For Greige fabric, greigeCode should be null
            dto.setGreigeCode(null);
            dto.setWovenFabricId(null);
        } else if (fabricType == 2 || fabricType == 3) {
            // For Finished/Finished Shade, greigeCode must have the selected greige fabric ID
            if (dto.getWovenFabricId() == null) {
                throw new IllegalArgumentException("Greige fabric selection is required for Finished/Finished Shade fabric types");
            }
            // Set greigeCode to the wovenFabricId (the selected greige fabric's ID)
            dto.setGreigeCode(String.valueOf(dto.getWovenFabricId()));
        } else {
            throw new IllegalArgumentException("Invalid Fabric Type. Expected 1 (Greige), 2 (Finished), or 3 (Finished Shade)");
        }
    }

    private void mapDtoToEntity(WovenFabricMasterDTO dto, WovenFabricMaster entity) {
        entity.setFabricType(dto.getFabricType());
        entity.setFabricCategoryId(dto.getFabricCategoryId());
        entity.setWovenFabricId(dto.getWovenFabricId());
        entity.setFabricCode(dto.getFabricCode());
        entity.setFabricName(StringUtils.hasText(dto.getFabricName()) ? dto.getFabricName().trim() : null);
        entity.setContent(dto.getContent());
        entity.setWeave(dto.getWeave());
        entity.setFabricQuality(dto.getFabricQuality());
//        entity.setUom(dto.getUom());
        entity.setReedspace(dto.getReedspace());

        
        entity.setFabricWidth(dto.getFabricWidth());
        entity.setCutWidth(dto.getCutWidth());
        entity.setOnTableEpi(dto.getOnTableEpi());
        entity.setOnLoomEpi(dto.getOnLoomEpi());
        entity.setSelvedgeName(dto.getSelvedgeName());
        entity.setSelvedgeDents(dto.getSelvedgeDents());
        entity.setSelvedgeEndsPerDent(dto.getSelvedgeEndsPerDent());
        entity.setSelvedgeEnds(dto.getSelvedgeEnds());
        
        entity.setEpi(dto.getEpi());
        entity.setPpi(dto.getPpi());
        entity.setGreigeCode(dto.getGreigeCode());
        entity.setTotalEnds(dto.getTotalEnds());
        entity.setGsm(dto.getGsm());
        entity.setGlm(dto.getGlm());
        
        entity.setTaxType(dto.getTaxType());
        entity.setGstPercent(dto.getGstPercent());

        entity.setIgst(dto.getIgst() != null ? dto.getIgst() : BigDecimal.ZERO);
        entity.setCgst(dto.getCgst() != null ? dto.getCgst() : BigDecimal.ZERO);
        entity.setSgst(dto.getSgst() != null ? dto.getSgst() : BigDecimal.ZERO);      

        entity.setWarpCount(dto.getWarpCount());
        entity.setWarpConversionCount(dto.getWarpConversionCount());
        entity.setWeftCount(dto.getWeftCount());
        entity.setWeftConversionCount(dto.getWeftConversionCount());
        entity.setTc(dto.getTc());
        entity.setNoOfInsertion(dto.getNoOfInsertion());

        entity.setStdValue(dto.getStdValue());
        entity.setWarpGlmApproved(dto.getWarpGlmApproved());
        entity.setWeftGlmApproved(dto.getWeftGlmApproved());
        entity.setFabricImageUrl(dto.getFabricImageUrl());
        entity.setFabricImage(dto.getFabricImage());
        entity.setDesignPaperImage(dto.getDesignPaperImage());
        
        // Map new fields
        entity.setCreatedOn(dto.getCreatedOn());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setTcMix(dto.getTcMix());
        entity.setLastModified(dto.getLastModified());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        entity.setSingleBeamDbf(dto.getSingleBeamDbf());
        entity.setTwinBeamDbf(dto.getTwinBeamDbf());
        entity.setPaperTuberTareWeight(dto.getPaperTuberTareWeight());
        entity.setHdpeWeight(dto.getHdpeWeight());
        entity.setRemarks(dto.getRemarks());
        entity.setReedCount(dto.getReedCount());
        entity.setDrawingOrder(dto.getDrawingOrder());
        entity.setSetPlan(dto.getSetPlan());
        entity.setEndsPerDent(dto.getEndsPerDent());
        
        // Map new crimp and GLM fields
        entity.setCrimp(dto.getCrimp());
        entity.setApprovedCrimp(dto.getApprovedCrimp());
        entity.setWarpGlmCalculated(dto.getWarpGlmCalculated());
        entity.setWeftGlmCalculated(dto.getWeftGlmCalculated());
        entity.setActualCrimp(dto.getActualCrimp());
        entity.setWarpGlmActual(dto.getWarpGlmActual());
        entity.setWeftGlmActual(dto.getWeftGlmActual());
        entity.setWastagePercentage(dto.getWastagePercentage());
    }

    private void enrichDtoWithMediaDetails(WovenFabricMasterDTO dto) {
        if (mediaService == null) {
            return;
        }

        if (dto.getFabricImage() != null) {
            try {
                dto.setFabricImageDetail(mediaService.getFirstMediaDetailByMediaSno(dto.getFabricImage().longValue()));
            } catch (Exception e) {
                // logger.debug("Failed to fetch media details for fabricImage mediaSno: {}", dto.getFabricImage(), e);
            }
        }

        if (dto.getDesignPaperImage() != null) {
            try {
                dto.setDesignPaperImageDetail(mediaService.getFirstMediaDetailByMediaSno(dto.getDesignPaperImage().longValue()));
            } catch (Exception e) {
                // logger.debug("Failed to fetch media details for designPaperImage mediaSno: {}", dto.getDesignPaperImage(), e);
            }
        }
    }

   
    public String getFabricDetails(Long id) {
        return wovenFabricMasterRepository.getFabricDetailsById(id);
    }
    
//    public List<WovenFabricMasterDTO> getFabricByFabricTypeId(Short fabricTypeId) {
//        List<WovenFabricMaster> subCategories = wovenFabricMasterRepository.findByFabricDetailsFabricTypeId(fabricTypeId);
//        return subCategories.stream()
//                .map(WovenFabricMasterDTO::new)
//                .collect(Collectors.toList());
//    }
    public void deleteFabricMaster(Long id) {
        // Check if category exists first
        if (!wovenFabricMasterRepository.existsById(id)) {
            throw new EntityNotFoundException("Woven Fabric not found with id: " + id);
        }
        
        try {
        	wovenFabricMasterRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Woven Fabric because it is referenced by other");
        }
    }
    

}

