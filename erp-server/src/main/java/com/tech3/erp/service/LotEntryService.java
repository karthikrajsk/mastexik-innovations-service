//package com.tech3.erp.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.tech3.erp.dto.LotEntryDTO;
//import com.tech3.erp.entity.LotEntry;
//import com.tech3.erp.repository.LotEntryRepository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class LotEntryService {
//
//    @Autowired
//    private LotEntryRepository lotEntryRepository;
//
//    public List<LotEntryDTO> getAllLotEntries() {
//        List<LotEntry> lotEntries = lotEntryRepository.findByQuantityNot(0);
//        return lotEntries.stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    private LotEntryDTO convertToDTO(LotEntry lotEntry) {
//        LotEntryDTO dto = new LotEntryDTO();
//        dto.setId(lotEntry.getId());
////        dto.getPurchaseInwardItem(lotEntry.getPurchaseInwardItem());
//        dto.setPoTypeId(lotEntry.getPoTypeId());
//        dto.setLotNumber(lotEntry.getLotNumber());
//        dto.setQuantity(lotEntry.getQuantity());
//        dto.setRejectedQuantity(lotEntry.getRejectedQuantity());
//        dto.setCost(lotEntry.getCost());
//        dto.setRemarks(lotEntry.getRemarks());
////        dto.setActiveFlag(lotEntry.isActiveFlag());
//        return dto;
//    }
//}


package com.tech3.erp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech3.erp.dto.LotEntryDTO;
import com.tech3.erp.entity.LotEntry;
import com.tech3.erp.repository.LotEntryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LotEntryService {

    @Autowired
    private LotEntryRepository lotEntryRepository;

    public List<LotEntryDTO> getAllLotEntries() {
        List<LotEntry> lotEntries = lotEntryRepository.findByQuantityNot(0);
        return lotEntries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // New method to get lots by poTypeId and fabricId
    public List<LotEntryDTO> getLotsByPoTypeAndFabric(Integer poTypeId, Integer fabricId) {
        List<LotEntry> lotEntries;
        
        if (poTypeId != null && fabricId != null) {
            // Filter by both poTypeId and fabricId
            lotEntries = lotEntryRepository.findByPoTypeIdAndFabricIdAndQuantityNot(poTypeId, fabricId, 0);
        } else if (poTypeId != null) {
            // Filter by poTypeId only
            lotEntries = lotEntryRepository.findByPoTypeIdAndQuantityNot(poTypeId, 0);
        } else if (fabricId != null) {
            // Filter by fabricId only
            lotEntries = lotEntryRepository.findByFabricIdAndQuantityNot(fabricId, 0);
        } else {
            // No filters, get all non-zero quantity lots
            lotEntries = lotEntryRepository.findByQuantityNot(0);
        }
        
        return lotEntries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // New method to get lots by multiple fabricIds
    public List<LotEntryDTO> getLotsByFabricIds(List<Integer> fabricIds) {
        List<LotEntry> lotEntries = lotEntryRepository.findByFabricIdInAndQuantityNot(fabricIds, 0);
        return lotEntries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LotEntryDTO convertToDTO(LotEntry lotEntry) {
        LotEntryDTO dto = new LotEntryDTO();
        dto.setId(lotEntry.getId());
        dto.setPoTypeId(lotEntry.getPoTypeId());
        dto.setFabricId(lotEntry.getFabricId()); // Make sure this exists in your DTO
        dto.setLotNumber(lotEntry.getLotNumber());
        dto.setQuantity(lotEntry.getQuantity());
        dto.setRejectedQuantity(lotEntry.getRejectedQuantity());
        dto.setCost(lotEntry.getCost());
        dto.setRemarks(lotEntry.getRemarks());
        return dto;
    }
}