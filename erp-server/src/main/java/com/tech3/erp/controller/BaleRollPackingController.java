package com.tech3.erp.controller;

import com.tech3.erp.dto.BaleRollPackingDTO;
import com.tech3.erp.service.BaleRollPackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bale-roll-packing")
public class BaleRollPackingController {

    private final BaleRollPackingService service;

    public BaleRollPackingController(BaleRollPackingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BaleRollPackingDTO> create(@RequestBody BaleRollPackingDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaleRollPackingDTO> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<BaleRollPackingDTO> getByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getByIdAndCompany(companyId, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaleRollPackingDTO> update(@PathVariable("id") Long id, @RequestBody BaleRollPackingDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<BaleRollPackingDTO> updateByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id, @RequestBody BaleRollPackingDTO dto) {
        return ResponseEntity.ok(service.updateByCompany(companyId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/company/{companyId}/{id}")
    public ResponseEntity<Void> deleteByCompany(@PathVariable("companyId") Long companyId, @PathVariable("id") Long id) {
        service.deleteByCompany(companyId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BaleRollPackingDTO>> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<BaleRollPackingDTO>> listByCompany(@PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok(service.listByCompany(companyId));
    }
}