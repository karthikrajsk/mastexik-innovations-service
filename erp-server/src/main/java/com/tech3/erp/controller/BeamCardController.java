package com.tech3.erp.controller;

import com.tech3.erp.dto.BeamCardDto;
import com.tech3.erp.service.BeamCardService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/beam-card")
@Tag(name = "Beam Card Controller", description = "APIs for Beam Card management")
@Validated
public class BeamCardController {

    private final BeamCardService beamCardService;

    public BeamCardController(BeamCardService beamCardService) {
        this.beamCardService = beamCardService;
    }

    @PostMapping("/save")
    @Operation(summary = "Save or Update Beam Card", description = "Saves or updates a Beam Card data record")
    public ResponseEntity<Object> saveBeamCard(@RequestBody BeamCardDto dto) {
        try {
            BeamCardDto savedCard = beamCardService.saveBeamCard(dto);
            return ResponseUtil.success("Beam Card saved successfully", savedCard);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while saving Beam Card: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{beamNo}")
    @Operation(summary = "Get Beam Card by Beam Number", description = "Fetches a Beam Card record using beamNo")
    public ResponseEntity<Object> getBeamCardByBeamNo(@PathVariable String beamNo) {
        try {
            String decodedBeamNo = URLDecoder.decode(beamNo, StandardCharsets.UTF_8);
            BeamCardDto card = beamCardService.getBeamCardByBeamNo(decodedBeamNo);
            return ResponseUtil.success("Beam Card fetched successfully", card);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while fetching Beam Card: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Beam Cards", description = "Retrieves a paginated list of saved Beam Cards")
    public ResponseEntity<Object> getAllBeamCards(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        try {
            Long finalOffset = (offset != null) ? offset : 0L;
            Long finalLimit = (limit != null) ? limit : 10L;
            return ResponseUtil.success("Beam Cards fetched successfully",
                    beamCardService.getAllBeamCards(finalOffset, finalLimit));
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while fetching Beam Cards: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Beam Card", description = "Deletes a Beam Card record by ID")
    public ResponseEntity<Object> deleteBeamCard(@PathVariable Long id) {
        try {
            beamCardService.deleteBeamCard(id);
            return ResponseUtil.success("Beam Card deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting Beam Card: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
