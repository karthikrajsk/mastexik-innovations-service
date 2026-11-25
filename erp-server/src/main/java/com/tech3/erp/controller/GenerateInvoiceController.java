package com.tech3.erp.controller;

import com.tech3.erp.dto.GenerateInvoiceDTO;
import com.tech3.erp.service.FabricInspectionService;
import com.tech3.erp.service.GenerateInvoiceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@Tag(name = "Generate Invoive Controller", description = "APIs for Generate Invoive management")

public class GenerateInvoiceController {	

	private final GenerateInvoiceService generateInvoiceService;
	
	public GenerateInvoiceController(GenerateInvoiceService generateInvoiceService) {
        this.generateInvoiceService = generateInvoiceService;
    }


    @PostMapping
    public ResponseEntity<GenerateInvoiceDTO> createInvoice(@RequestBody GenerateInvoiceDTO dto) {
        GenerateInvoiceDTO createdInvoice = generateInvoiceService.createInvoice(dto);
        return ResponseEntity.ok(createdInvoice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenerateInvoiceDTO> getInvoiceById(@PathVariable Long id) {
        GenerateInvoiceDTO invoice = generateInvoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping
    public ResponseEntity<List<GenerateInvoiceDTO>> getAllInvoices() {
        List<GenerateInvoiceDTO> invoices = generateInvoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenerateInvoiceDTO> updateInvoice(
            @PathVariable Long id, 
            @RequestBody GenerateInvoiceDTO dto) {
        GenerateInvoiceDTO updatedInvoice = generateInvoiceService.updateInvoice(id, dto);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        generateInvoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/dispatched-quantities/{salesOrderId}")
    public ResponseEntity<List<Object[]>> getDispatchedQuantities(@PathVariable Long salesOrderId) {
        List<Object[]> dispatchedQuantities = generateInvoiceService.getDispatchedQuantitiesBySalesOrder(salesOrderId);
        return ResponseEntity.ok(dispatchedQuantities);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<GenerateInvoiceDTO>> getAllInvoicesByCompanyId(@PathVariable Long companyId) {
        List<GenerateInvoiceDTO> invoices = generateInvoiceService.getAllInvoicesByCompanyId(companyId);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}/company/{companyId}")
    public ResponseEntity<GenerateInvoiceDTO> getInvoiceByIdAndCompanyId(
            @PathVariable Long id, 
            @PathVariable Long companyId) {
        GenerateInvoiceDTO invoice = generateInvoiceService.getInvoiceByIdAndCompanyId(id, companyId);
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{id}/company/{companyId}")
    public ResponseEntity<GenerateInvoiceDTO> updateInvoiceByCompanyId(
            @PathVariable Long id, 
            @PathVariable Long companyId,
            @RequestBody GenerateInvoiceDTO dto) {
        GenerateInvoiceDTO updatedInvoice = generateInvoiceService.updateInvoiceByCompanyId(id, companyId, dto);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}/company/{companyId}")
    public ResponseEntity<Void> deleteInvoiceByCompanyId(
            @PathVariable Long id, 
            @PathVariable Long companyId) {
        generateInvoiceService.deleteInvoiceByCompanyId(id, companyId);
        return ResponseEntity.noContent().build();
    }
}