package com.tech3.erp.controller;

import com.tech3.erp.dto.PortDTO;
import com.tech3.erp.service.PortService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/port")
@Tag(name = "Port Controller", description = "APIs for Port management")
public class PortController {

    private final PortService portService;

    public PortController(PortService portService) {
        this.portService = portService;
    }

    @PostMapping
    @Operation(summary = "Create Port")
    public ResponseEntity<Object> create(@Valid @RequestBody PortDTO dto) {
        return ResponseUtil.success("Port created", portService.createPort(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Port by ID")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return ResponseUtil.success("Port fetched", portService.getPortById(id));
    }

    @GetMapping
    @Operation(summary = "List all Ports")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit,
            @RequestParam(value = "fetchAll", required = false, defaultValue = "false") boolean fetchAll) {
        
        if (fetchAll) {
            return ResponseUtil.success("Ports fetched", portService.getAllPorts());
        }
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Ports fetched", portService.getAllPortsWithPagination(finalOffset, finalLimit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Port")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody PortDTO dto) {
        return ResponseUtil.success("Port updated", portService.updatePort(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Port")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            portService.deletePort(id);
            return ResponseUtil.success("Port deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Port", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
