package com.tech3.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tech3.erp.dto.FinishFabricReceiveDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.FinishFabricReceive;
import com.tech3.erp.service.FinishFabricReceiveService;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/finish-fabric-receives")
@Tag(name = "Finish Fabric Receive Controller", description = "APIs for Finish Fabric Receive management (Supports both Fabric and Yarn)")
public class FinishFabricReceiveController {

    private final FinishFabricReceiveService receiveService;

    @Autowired
    public FinishFabricReceiveController(FinishFabricReceiveService receiveService) {
        this.receiveService = receiveService;
    }

    @PostMapping
    public ResponseEntity<FinishFabricReceive> createFinishFabricReceive(
            @RequestBody FinishFabricReceiveDTO dto) {
        return ResponseEntity.ok(receiveService.createFinishFabricReceive(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinishFabricReceive> getFinishFabricReceiveById(
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(receiveService.getFinishFabricReceiveById(id));
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<FinishFabricReceive>> getAllFinishFabricReceives(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(receiveService.getAllFinishFabricReceivesWithPagination(offset, limit));
    }

    @GetMapping("/type/{processType}")
    public ResponseEntity<PaginatedResponse<FinishFabricReceive>> getAllFinishFabricReceivesByType(
            @PathVariable("processType") String processType,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(receiveService.getAllFinishFabricReceivesByTypeWithPagination(processType, offset, limit));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<PaginatedResponse<FinishFabricReceive>> getFinishFabricReceivesByCompany(
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(receiveService.getFinishFabricReceivesByCompanyWithPagination(companyId, offset, limit));
    }

    @GetMapping("/type/{processType}/company/{companyId}")
    public ResponseEntity<PaginatedResponse<FinishFabricReceive>> getFinishFabricReceivesByTypeAndCompany(
            @PathVariable("processType") String processType,
            @PathVariable("companyId") Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        return ResponseEntity.ok(receiveService.getFinishFabricReceivesByTypeAndCompanyWithPagination(processType, companyId, offset, limit));
    }

    @GetMapping("/{id}/company/{companyId}")
    public ResponseEntity<FinishFabricReceive> getFinishFabricReceiveByIdAndCompany(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        return ResponseEntity.ok(receiveService.getFinishFabricReceiveByIdAndCompany(id, companyId));
    }

    @GetMapping("/type/{processType}/company/{companyId}/{id}")
    public ResponseEntity<FinishFabricReceive> getFinishFabricReceiveByIdCompanyAndType(
            @PathVariable("processType") String processType,
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(receiveService.getFinishFabricReceiveByIdAndCompanyAndType(id, companyId, processType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinishFabricReceive> updateFinishFabricReceive(
            @PathVariable("id") Long id, @RequestBody FinishFabricReceiveDTO dto) {
        return ResponseEntity.ok(receiveService.updateFinishFabricReceive(id, dto));
    }

    @PutMapping("/{id}/company/{companyId}")
    public ResponseEntity<FinishFabricReceive> updateFinishFabricReceiveByCompany(
            @PathVariable("id") Long id, @PathVariable("companyId") Long companyId, @RequestBody FinishFabricReceiveDTO dto) {
        return ResponseEntity.ok(receiveService.updateFinishFabricReceiveByCompany(id, companyId, dto));
    }

    @PutMapping("/type/{processType}/company/{companyId}/{id}")
    public ResponseEntity<FinishFabricReceive> updateFinishFabricReceiveByCompanyAndType(
            @PathVariable("processType") String processType,
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id,
            @RequestBody FinishFabricReceiveDTO dto) {
        return ResponseEntity.ok(receiveService.updateFinishFabricReceiveByCompanyAndType(id, companyId, processType, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinishFabricReceive(@PathVariable("id") Long id) {
        receiveService.deleteFinishFabricReceive(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/company/{companyId}")
    public ResponseEntity<Void> deleteFinishFabricReceiveByCompany(@PathVariable("id") Long id, @PathVariable("companyId") Long companyId) {
        receiveService.deleteFinishFabricReceiveByCompany(id, companyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/type/{processType}/company/{companyId}/{id}")
    public ResponseEntity<Void> deleteFinishFabricReceiveByCompanyAndType(
            @PathVariable("processType") String processType,
            @PathVariable("companyId") Long companyId,
            @PathVariable("id") Long id) {
        receiveService.deleteFinishFabricReceiveByCompanyAndType(id, companyId, processType);
        return ResponseEntity.noContent().build();
    }
}