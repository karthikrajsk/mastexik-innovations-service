package com.tech3.erp.controller;

import com.tech3.erp.dto.PurchaseReturnDTO;
import com.tech3.erp.entity.PurchaseReturn;
import com.tech3.erp.service.PurchaseReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-returns")
public class PurchaseReturnController {

    @Autowired
    private PurchaseReturnService service;

    @PostMapping
    public ResponseEntity<PurchaseReturnDTO> create(@RequestBody PurchaseReturnDTO dto) {
        PurchaseReturn created = service.create(dto);
        PurchaseReturnDTO response = service.get(created.getId());
        return ResponseEntity.created(URI.create("/api/purchase-returns/" + created.getId())).body(response);
    }

    @GetMapping
    public ResponseEntity<Object> list(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(service.listAll());
        } else {
            Long finalOffset = (offset != null) ? offset : 0L;
            Long finalLimit = (limit != null) ? limit : 10L;
            return ResponseEntity.ok(service.listAllWithPagination(finalOffset, finalLimit));
        }
    }
    
    @GetMapping("/company/{companyId}")
    public ResponseEntity<Object> listByCompany(
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(service.listAllByCompany(companyId));
        } else {
            Long finalOffset = (offset != null) ? offset : 0L;
            Long finalLimit = (limit != null) ? limit : 10L;
            return ResponseEntity.ok(service.listAllWithPaginationByCompany(companyId, finalOffset, finalLimit));
        }
    }

    @GetMapping("/{id}")
    public PurchaseReturnDTO get(@PathVariable Long id) {
        return service.get(id);
    }
    
    @GetMapping("/company/{companyId}/{id}")
    public PurchaseReturnDTO getByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        return service.getByCompany(id, companyId);
    }

    @PutMapping("/{id}")
    public PurchaseReturnDTO update(@PathVariable Long id, @RequestBody PurchaseReturnDTO dto) {
        return service.update(id, dto);
    }
    
    @PutMapping("/company/{companyId}/{id}")
    public PurchaseReturnDTO updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @RequestBody PurchaseReturnDTO dto) {
        return service.updateByCompany(id, companyId, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/company/{companyId}/{id}")
    public ResponseEntity<Void> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        service.deleteByCompany(id, companyId);
        return ResponseEntity.noContent().build();
    }
}