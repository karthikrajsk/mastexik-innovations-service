package com.tech3.erp.controller;

import com.tech3.erp.dto.TransportDTO;
import com.tech3.erp.service.TransportService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transport")
@Tag(name = "Transport Controller", description = "APIs for Transport management")
public class TransportController {

    private final TransportService transportService;

    public TransportController(TransportService transportService) {
        this.transportService = transportService;
    }

    @PostMapping
    @Operation(summary = "Create Transport")
    public ResponseEntity<Object> create(@Valid @RequestBody TransportDTO dto) {
        return ResponseUtil.success("Transport created", transportService.createTransport(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Transport by ID")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return ResponseUtil.success("Transport fetched", transportService.getTransportById(id));
    }

    @GetMapping
    @Operation(summary = "List all Transports")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "fetchAll", required = false, defaultValue = "false") boolean fetchAll) {
        
        if (fetchAll) {
            return ResponseUtil.success("Transports fetched", transportService.getAllTransports());
        }
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Transports fetched", transportService.getAllTransportsWithPagination(finalOffset, finalLimit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Transport")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody TransportDTO dto) {
        return ResponseUtil.success("Transport updated", transportService.updateTransport(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Transport")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            transportService.deleteTransport(id);
            return ResponseUtil.success("Transport deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Transport", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
