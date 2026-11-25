package com.tech3.erp.controller;

import com.tech3.erp.dto.AgentMasterDTO;
import com.tech3.erp.service.AgentMasterService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agent-master")
@Tag(name = "Agent Master Controller", description = "APIs for Agent Master management")
@Validated
public class AgentMasterController {

    private final AgentMasterService agentMasterService;

    public AgentMasterController(AgentMasterService agentMasterService) {
        this.agentMasterService = agentMasterService;
    }

    @PostMapping
    @Operation(summary = "Create a new Agent Master", description = "Adds a new Agent Master.")
    public ResponseEntity<Object> createAgentMaster(@Valid @RequestBody AgentMasterDTO agentMasterDTO) {
        try {
            AgentMasterDTO createdAgent = agentMasterService.createAgentMaster(agentMasterDTO);
            return ResponseUtil.success("Agent Master created successfully", createdAgent);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Agent Master by ID", description = "Fetch an Agent Master using its unique ID.")
    public ResponseEntity<Object> getAgentMasterById(@PathVariable Long id) {
        try {
            return ResponseUtil.success("Agent Master fetched successfully", agentMasterService.getAgentMasterById(id));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Agent Masters", description = "Retrieve a list of all Agent Masters with pagination support.")
    public ResponseEntity<Object> getAllAgentMasters(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Agent Masters fetched successfully", agentMasterService.getAllAgentMastersWithPagination(finalOffset, finalLimit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Agent Master", description = "Update Agent Master details based on ID.")
    public ResponseEntity<Object> updateAgentMaster(@PathVariable Long id, @Valid @RequestBody AgentMasterDTO agentMasterDTO) {
        try {
            return ResponseUtil.success("Agent Master updated successfully", agentMasterService.updateAgentMaster(id, agentMasterDTO));
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Agent Master", description = "Deletes an Agent Master by ID.")
    public ResponseEntity<Object> deleteAgentMaster(@PathVariable Long id) {
        try {
            agentMasterService.deleteAgentMaster(id);
            return ResponseUtil.success("Agent Master deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Agent Master", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
