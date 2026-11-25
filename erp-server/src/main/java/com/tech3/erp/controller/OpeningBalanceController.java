package com.tech3.erp.controller;

import com.tech3.erp.dto.OpeningBalanceDto;
import com.tech3.erp.entity.OpeningBalanceEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.tech3.erp.service.OpeningBalanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/opening-balance")
@CrossOrigin(origins = "*")
public class OpeningBalanceController {

    @Autowired
    private OpeningBalanceService openingBalanceService;

    @Autowired
    private ObjectMapper objectMapper;

    // CREATE - single or array
    @PostMapping
    public ResponseEntity<?> create(@RequestBody JsonNode payload) {
        try {
            if (payload.isArray()) {
                List<OpeningBalanceDto> dtos = objectMapper.convertValue(
                        payload, new TypeReference<List<OpeningBalanceDto>>() {});
                for (OpeningBalanceDto d : dtos) validateDto(d);
                
                List<OpeningBalanceDto> created = dtos.stream()
                        .<OpeningBalanceDto>map(d -> {  // Add explicit type here
                            OpeningBalanceEntity entity = openingBalanceService.createOpeningBalance(d);
                            return mapToDto(entity);
                        })
                        .collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.CREATED).body(created);
            } else {
                OpeningBalanceDto dto = objectMapper.convertValue(payload, OpeningBalanceDto.class);
                validateDto(dto);
                OpeningBalanceEntity entity = openingBalanceService.createOpeningBalance(dto);
                OpeningBalanceDto created = mapToDto(entity);
                return ResponseEntity.status(HttpStatus.CREATED).body(created);
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Invalid payload: " + ex.getMessage());
        }
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        var paginated = openingBalanceService.getAllOpeningBalancesWithPagination(finalOffset, finalLimit);
        return ResponseEntity.ok(paginated);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getAllByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        var paginated = openingBalanceService.getAllOpeningBalancesWithPaginationByCompany(companyId, finalOffset, finalLimit);
        return ResponseEntity.ok(paginated);
    }
    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<OpeningBalanceDto> getById(@PathVariable("id") Long id) {
        OpeningBalanceDto dto = mapToDto(openingBalanceService.getOpeningBalance(id));
        return ResponseEntity.ok(dto);
    }

    // UPDATE - FIXED VERSION
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody JsonNode payload) {
        System.out.println("Update request for ID: " + id);
        System.out.println("Update payload: " + payload.toString());
        
        try {
            OpeningBalanceDto dto = objectMapper.convertValue(payload, OpeningBalanceDto.class);
            validateDto(dto);
            OpeningBalanceDto updated = mapToDto(openingBalanceService.updateOpeningBalance(id, dto));
            System.out.println("Update successful: " + updated.toString());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            System.out.println("Update error: " + ex.getMessage());
            return ResponseEntity.badRequest().body("Invalid payload: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<?> updateByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id, @RequestBody JsonNode payload) {
        System.out.println("Update request for company: " + companyId + ", ID: " + id);
        System.out.println("Update payload: " + payload.toString());
        try {
            OpeningBalanceDto dto = objectMapper.convertValue(payload, OpeningBalanceDto.class);
            validateDto(dto);
            OpeningBalanceDto updated = mapToDto(openingBalanceService.updateOpeningBalanceByCompany(companyId, id, dto));
            System.out.println("Update successful: " + updated.toString());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            System.out.println("Update error: " + ex.getMessage());
            return ResponseEntity.badRequest().body("Invalid payload: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update failed: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        try {
            openingBalanceService.deleteOpeningBalance(id);
            return ResponseEntity.noContent().build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    public ResponseEntity<?> deleteByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        try {
            openingBalanceService.deleteOpeningBalanceByCompany(companyId, id);
            return ResponseEntity.noContent().build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Deletion failed: " + e.getMessage());
        }
    }

    // ===== Helper =====
    private void validateDto(OpeningBalanceDto dto) {
        if (dto == null) throw new IllegalArgumentException("OpeningBalance payload cannot be null");
        if (dto.getDate() == null) throw new IllegalArgumentException("'date' is required");
        if (dto.getGoodsType() == null || dto.getGoodsType().trim().isEmpty()) throw new IllegalArgumentException("'goodsType' is required");
    }

    // Add this method to ensure proper entity loading for response
    private OpeningBalanceDto mapToDto(OpeningBalanceEntity entity) {
        // Fetch the entity again with relationships loaded
        OpeningBalanceEntity fullEntity = openingBalanceService.getOpeningBalance(entity.getId());
        return new OpeningBalanceDto(fullEntity);
    }
}