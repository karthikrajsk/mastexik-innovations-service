package com.tech3.erp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tech3.erp.dto.BrushingReceiveDTO;
import com.tech3.erp.dto.BrushingReceiveStatusUpdateDTO;
import com.tech3.erp.dto.BrushingStockReportDTO;
import com.tech3.erp.dto.BrushingUnitFabricStatusDTO;
import com.tech3.erp.dto.PieceEntryDTO;
import com.tech3.erp.entity.PieceEntry;
import com.tech3.erp.service.BrushingService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/brushing")
@Tag(name = "Brushing Controller", description = "APIs for Brushing module management (Issue, Receive, and Inspections)")
public class BrushingController {

    private final BrushingService brushingService;

    @Autowired
    public BrushingController(BrushingService brushingService) {
        this.brushingService = brushingService;
    }

    // ==========================================
    // BRUSHING RECEIVE ENDPOINTS
    // ==========================================

    @PostMapping("/receive")
    @Operation(summary = "Create a new Brushing Receive", description = "Receives brushed pieces and updates dimensions.")
    public ResponseEntity<BrushingReceiveDTO> createBrushingReceive(@RequestBody @Valid BrushingReceiveDTO dto) {
        BrushingReceiveDTO saved = brushingService.createBrushingReceive(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/receive/company/{companyId}")
    @Operation(summary = "Get all Brushing Receives by Company", description = "Retrieves receives for a specific company.")
    public ResponseEntity<List<BrushingReceiveDTO>> getBrushingReceivesByCompany(@PathVariable Long companyId) {
        List<BrushingReceiveDTO> list = brushingService.getBrushingReceivesByCompany(companyId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/receive/{id}")
    @Operation(summary = "Get Brushing Receive by ID", description = "Retrieves a brushing receive by its ID.")
    public ResponseEntity<BrushingReceiveDTO> getBrushingReceiveById(@PathVariable Long id) {
        BrushingReceiveDTO receive = brushingService.getBrushingReceiveById(id);
        return ResponseEntity.ok(receive);
    }

    // ==========================================
    // UTILITY UTILITY PIECE QUERIES
    // ==========================================

    @GetMapping("/available-inspection-pieces/company/{companyId}")
    @Operation(summary = "Get Pieces Available for Brushing Inspection", description = "Retrieves brushed pieces in stock that require inspection.")
    public ResponseEntity<List<PieceEntryDTO>> getPiecesAvailableForBrushingInspection(@PathVariable Long companyId) {
        List<PieceEntry> pieces = brushingService.getPiecesAvailableForBrushingInspection(companyId);
        List<PieceEntryDTO> dtos = pieces.stream().map(PieceEntryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/receive/{id}")
    @Operation(summary = "Delete Brushing Receive", description = "Soft deletes a brushing receive.")
    public ResponseEntity<Void> deleteBrushingReceive(@PathVariable Long id) {
        brushingService.deleteBrushingReceive(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/receive/{id}")
    @Operation(summary = "Update Brushing Receive", description = "Updates an existing brushing receive.")
    public ResponseEntity<BrushingReceiveDTO> updateBrushingReceive(@PathVariable Long id, @RequestBody @Valid BrushingReceiveDTO dto) {
        BrushingReceiveDTO updated = brushingService.updateBrushingReceive(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/receive/{id}/status")
    @Operation(summary = "Update Brushing Receive Status", description = "Updates the status and piece-level brushed details of a brushing receive.")
    public ResponseEntity<BrushingReceiveDTO> updateBrushingReceiveStatus(
            @PathVariable Long id,
            @RequestBody @Valid BrushingReceiveStatusUpdateDTO statusDto) {
        BrushingReceiveDTO updated = brushingService.updateBrushingReceiveStatus(id, statusDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/next-roll-no")
    @Operation(summary = "Get the next HFPL Roll Number", description = "Generates the next sequential roll number based on financial year.")
    public ResponseEntity<String> getNextHfplRollNo() {
        String nextRollNo = brushingService.generateNextHfplRollNo();
        return ResponseEntity.ok(nextRollNo);
    }

    @GetMapping("/stock-ledger/company/{companyId}")
    @Operation(summary = "Get Brushing Stock Ledger Report", description = "Retrieves the daily stock movements and running balances.")
    public ResponseEntity<List<BrushingStockReportDTO>> getBrushingStockLedgerReport(
            @PathVariable Long companyId,
            @RequestParam(required = false) String status) {
        List<BrushingStockReportDTO> report = brushingService.getBrushingStockReport(companyId, status);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/unit-fabric-status/company/{companyId}")
    @Operation(summary = "Get Brushing Unit Fabric Status Report", description = "Retrieves Brushed Fabric Stock and Waiting for Brushing stock by Construction.")
    public ResponseEntity<List<BrushingUnitFabricStatusDTO>> getBrushingUnitFabricStatusReport(
            @PathVariable Long companyId,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String construction) {
        LocalDate targetDate = null;
        if (date != null && !date.trim().isEmpty()) {
            try {
                targetDate = LocalDate.parse(date.trim());
            } catch (Exception ignored) {}
        }
        List<BrushingUnitFabricStatusDTO> report = brushingService.getBrushingUnitFabricStatusReport(companyId, targetDate, construction);
        return ResponseEntity.ok(report);
    }
}
