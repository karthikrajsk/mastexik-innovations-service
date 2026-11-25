package com.tech3.erp.controller;

import com.tech3.erp.dto.SalesReturnDTO;
import com.tech3.erp.entity.SalesReturn;
import com.tech3.erp.service.SalesReturnService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales-returns")
public class SalesReturnController {

    private static final Logger logger = LoggerFactory.getLogger(SalesReturnController.class);

    @Autowired
    private SalesReturnService salesReturnService;

  

    @PostMapping
    public ResponseEntity<SalesReturnDTO> create(@RequestBody SalesReturnDTO dto) {
        SalesReturn created = salesReturnService.create(dto);
        SalesReturnDTO response = salesReturnService.get(created.getId());
        return ResponseEntity.created(URI.create("/api/sales-returns/" + created.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<Object> listAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseEntity.ok(salesReturnService.listAllWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesReturnDTO> get(@PathVariable Long id) {
        SalesReturnDTO salesReturn = salesReturnService.get(id);
        return ResponseEntity.ok(salesReturn);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Object> getAllSalesReturnsByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(salesReturnService.getAllSalesReturnsByCompanyIdWithPagination(companyId, finalOffset, finalLimit));
    }

    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<SalesReturnDTO> getSalesReturnByIdAndCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(salesReturnService.getSalesReturnByIdAndCompany(id, companyId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalesReturnDTO> update(
            @PathVariable Long id,
            @RequestBody SalesReturnDTO dto,
            @RequestHeader(value = "X-Company-Id", required = false) Long companyId,
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        Long effectiveCompanyId = companyId != null ? companyId : dto.getCompanyId();
        Long effectiveUserId = userId;
        
        logger.info("updateSalesReturn called - ID: {}, EditNote: {}, CompanyId: {}, UserId: {}", 
            id, dto.getEditNote(), effectiveCompanyId, effectiveUserId);
        
        SalesReturnDTO updated = salesReturnService.update(id, dto, effectiveUserId);
        
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<Object> updateSalesReturnByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @RequestBody SalesReturnDTO dto) {
        try {
            SalesReturnDTO updated = salesReturnService.updateSalesReturnByCompany(id, companyId, dto);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salesReturnService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/company/{companyId}/{id}")
    public ResponseEntity<Object> deleteSalesReturnByCompany(
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        try {
            salesReturnService.deleteSalesReturnByCompany(id, companyId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
