//package com.tech3.erp.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.tech3.erp.dto.LotEntryDTO;
//import com.tech3.erp.service.LotEntryService;
//
//import io.swagger.v3.oas.annotations.tags.Tag;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/lot_entry")
//@Tag(name = "Lot Entry Controller", description = "APIs for managing Lot Entry")
//
//public class LotEntryController {
//
//    @Autowired
//    private LotEntryService lotEntryService;
//
//    @GetMapping
//    public List<LotEntryDTO> getAllLotEntries() {
//        return lotEntryService.getAllLotEntries();
//    }
//}


package com.tech3.erp.controller;

import com.tech3.erp.dto.LotEntryDTO;
import com.tech3.erp.service.LotEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lot-entries")
@CrossOrigin(origins = "*")
public class LotEntryController {

    @Autowired
    private LotEntryService lotEntryService;

    // Get all lot entries with non-zero quantity
    @GetMapping
    public ResponseEntity<List<LotEntryDTO>> getAllLotEntries() {
        try {
            List<LotEntryDTO> lotEntries = lotEntryService.getAllLotEntries();
            return ResponseEntity.ok(lotEntries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get lots by poTypeId and/or fabricId
    @GetMapping("/filter")
    public ResponseEntity<List<LotEntryDTO>> getLotsByFilters(
            @RequestParam(required = false) Integer poTypeId,
            @RequestParam(required = false) Integer fabricId) {
        try {
            List<LotEntryDTO> lotEntries = lotEntryService.getLotsByPoTypeAndFabric(poTypeId, fabricId);
            return ResponseEntity.ok(lotEntries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get lots by multiple fabric IDs
    @GetMapping("/by-fabrics")
    public ResponseEntity<List<LotEntryDTO>> getLotsByFabricIds(
            @RequestParam List<Integer> fabricIds) {
        try {
            List<LotEntryDTO> lotEntries = lotEntryService.getLotsByFabricIds(fabricIds);
            return ResponseEntity.ok(lotEntries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Alternative endpoint using path variable for multiple fabric IDs
    @GetMapping("/fabrics/{fabricIds}")
    public ResponseEntity<List<LotEntryDTO>> getLotsByFabricIdsPath(
            @PathVariable List<Integer> fabricIds) {
        try {
            List<LotEntryDTO> lotEntries = lotEntryService.getLotsByFabricIds(fabricIds);
            return ResponseEntity.ok(lotEntries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get lots by specific fabric ID
    @GetMapping("/fabric/{fabricId}")
    public ResponseEntity<List<LotEntryDTO>> getLotsByFabricId(
            @PathVariable Integer fabricId) {
        try {
            List<LotEntryDTO> lotEntries = lotEntryService.getLotsByPoTypeAndFabric(null, fabricId);
            return ResponseEntity.ok(lotEntries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Get lots by specific poTypeId
    @GetMapping("/po-type/{poTypeId}")
    public ResponseEntity<List<LotEntryDTO>> getLotsByPoTypeId(
            @PathVariable Integer poTypeId) {
        try {
            List<LotEntryDTO> lotEntries = lotEntryService.getLotsByPoTypeAndFabric(poTypeId, null);
            return ResponseEntity.ok(lotEntries);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}