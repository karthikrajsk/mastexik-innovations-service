package com.tech3.erp.controller;

import com.tech3.erp.dto.ModuleFieldMappingDTO;
import com.tech3.erp.service.DatabaseSchemaService;
import com.tech3.erp.service.ModuleFieldMappingService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/module-field-mappings")
@Tag(name = "Module Field Mapping Controller", description = "APIs for Module Field Mapping management")
public class ModuleFieldMappingController {

    private final ModuleFieldMappingService mappingService;
    private final JdbcTemplate jdbcTemplate;
    private final DatabaseSchemaService schemaService;

    public ModuleFieldMappingController(ModuleFieldMappingService mappingService, 
                                      JdbcTemplate jdbcTemplate,
                                      DatabaseSchemaService schemaService) {
        this.mappingService = mappingService;
        this.jdbcTemplate = jdbcTemplate;
        this.schemaService = schemaService;
    }
    
    
    @GetMapping("/modules")
    @Operation(summary = "Get all module names")
    public ResponseEntity<Object> getAllModules() {
        return ResponseUtil.success("Modules fetched", 
                mappingService.getAllModuleNames());
    }

    @GetMapping("/modules/{moduleName}/sections")
    @Operation(summary = "Get sections by module")
    public ResponseEntity<Object> getSectionsByModule(@PathVariable String moduleName) {
        return ResponseUtil.success("Sections fetched", 
                mappingService.getSectionsByModule(moduleName));
    }

    @GetMapping("/modules/{moduleName}/sections/{sectionName}/fields")
    @Operation(summary = "Get fields by module and section")
    public ResponseEntity<Object> getFieldsByModuleAndSection(
            @PathVariable String moduleName, 
            @PathVariable String sectionName) {
        return ResponseUtil.success("Fields fetched", 
                mappingService.getFieldsByModuleAndSection(moduleName, sectionName));
    }
    
    @GetMapping("/dynamic-structure")
    @Operation(summary = "Get dynamic module structure from database schema")
    public ResponseEntity<Object> getDynamicModuleStructure() {
        try {
            Map<String, Object> structure = mappingService.getDynamicModuleStructure();
            return ResponseUtil.success("Dynamic module structure fetched", structure);
        } catch (Exception e) {
            // Fix: Use proper error method with status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.error("Failed to fetch dynamic structure: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/database-schema")
    @Operation(summary = "Get database schema information")
    public ResponseEntity<Object> getDatabaseSchema() {
        try {
            // Get all tables
            String tablesSql = 
                "SELECT table_name, table_type " +
                "FROM information_schema.tables " +
                "WHERE table_schema = 'masters' " +
                "ORDER BY table_name";
            
            List<Map<String, Object>> tables = jdbcTemplate.queryForList(tablesSql);
            
            Map<String, Object> schemaInfo = new HashMap<>();
            schemaInfo.put("tables", tables);
            schemaInfo.put("tableCount", tables.size());
            
            return ResponseUtil.success("Database schema fetched", schemaInfo);
        } catch (Exception e) {
            // Fix: Use proper error method with status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtil.error("Failed to fetch database schema: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }
    
    
    @GetMapping("/structure")
    @Operation(summary = "Get complete module structure")
    public ResponseEntity<Object> getModuleStructure() {
        return ResponseUtil.success("Module structure fetched", 
                mappingService.getModuleStructure());
    }

    @PostMapping
    @Operation(summary = "Create Module Field Mapping")
    public ResponseEntity<Object> create(@Valid @RequestBody ModuleFieldMappingDTO dto) {
        return ResponseUtil.success("Mapping created", 
                mappingService.createMapping(dto));
    }

    @PostMapping("/bulk")
    @Operation(summary = "Bulk Create Module Field Mappings")
    public ResponseEntity<Object> bulkCreate(@Valid @RequestBody List<ModuleFieldMappingDTO> dtos) {
        return ResponseUtil.success("Mappings created", 
                mappingService.bulkCreateMappings(dtos));
    }

    @GetMapping
    @Operation(summary = "Get all Module Field Mappings")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        
        return ResponseUtil.success("Mappings fetched", mappingService.getAllMappingsWithPagination(finalOffset, finalLimit));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Module Field Mapping")
    public ResponseEntity<Object> update(@PathVariable Long id, 
            @Valid @RequestBody ModuleFieldMappingDTO dto) {
        return ResponseUtil.success("Mapping updated", 
                mappingService.updateMapping(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Module Field Mapping")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        mappingService.deleteMapping(id);
        return ResponseUtil.success("Mapping deleted", null);
    }
}