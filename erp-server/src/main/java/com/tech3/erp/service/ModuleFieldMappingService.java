package com.tech3.erp.service;

import com.tech3.erp.dto.ModuleFieldMappingDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.ModuleFieldMapping;
import com.tech3.erp.repository.ModuleFieldMappingRepository;
import com.tech3.erp.util.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModuleFieldMappingService {

    private static final Logger log = LoggerFactory.getLogger(ModuleFieldMappingService.class);

    private final ModuleFieldMappingRepository repository;
    private final JdbcTemplate jdbcTemplate;
    private final DatabaseSchemaService schemaService;

    public ModuleFieldMappingService(ModuleFieldMappingRepository repository,
                                   JdbcTemplate jdbcTemplate,
                                   DatabaseSchemaService schemaService) {
        this.repository = repository;
        this.jdbcTemplate = jdbcTemplate;
        this.schemaService = schemaService;
    }

    @Transactional(readOnly = true)
    public List<String> getAllModuleNames() {
        try {
            log.debug("Starting to fetch module names...");

            // Method 1: Try to get from module_field_mapping table
            List<String> modulesFromMapping = repository.findAllModuleNames();
            log.debug("Modules from mapping table: {} - {}", modulesFromMapping.size(), modulesFromMapping);

            if (!modulesFromMapping.isEmpty()) {
                return modulesFromMapping;
            }

            // Method 2: Try dynamic schema discovery
            try {
                List<String> dynamicModules = getDynamicModuleNames();
                log.debug("Modules from dynamic schema: {} - {}", dynamicModules.size(), dynamicModules);

                if (!dynamicModules.isEmpty()) {
                    return dynamicModules;
                }
            } catch (Exception e) {
                log.warn("Failed to get dynamic modules: {}", e.getMessage());
            }

            // Method 3: Fallback to predefined modules
            log.debug("Using fallback modules");
            return getFallbackModules();

        } catch (Exception e) {
            log.error("Error in getAllModuleNames: {}", e.getMessage(), e);
            return getFallbackModules();
        }
    }

    @Transactional(readOnly = true)
    public List<String> getSectionsByModule(String moduleName) {
        try {
            log.debug("Getting sections for module: {}", moduleName);
            List<String> sections = repository.findSectionsByModuleName(moduleName);
            log.debug("Sections from DB: {} - {}", sections.size(), sections);

            if (sections.isEmpty()) {
                sections = getDynamicSectionsForModule(moduleName);
                log.debug("Using dynamic sections: {}", sections);
            }

            return sections;
        } catch (Exception e) {
            log.error("Error in getSectionsByModule for {}: {}", moduleName, e.getMessage(), e);
            return getDynamicSectionsForModule(moduleName);
        }
    }

    @Transactional(readOnly = true)
    public List<ModuleFieldMappingDTO> getFieldsByModuleAndSection(String moduleName, String sectionName) {
        try {
            log.debug("Getting fields for {}.{}", moduleName, sectionName);
            List<ModuleFieldMappingDTO> fields = repository.findByModuleNameAndSectionName(moduleName, sectionName)
                    .stream()
                    .map(ModuleFieldMappingDTO::new)
                    .collect(Collectors.toList());

            log.debug("Fields from DB: {}", fields.size());

            if (fields.isEmpty()) {
                fields = getDynamicFieldsForModuleAndSection(moduleName, sectionName);
                log.debug("Using dynamic fields: {}", fields.size());
            }

            return fields;
        } catch (Exception e) {
            log.error("Error in getFieldsByModuleAndSection for {}.{}: {}", moduleName, sectionName, e.getMessage(), e);
            return getDynamicFieldsForModuleAndSection(moduleName, sectionName);
        }
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getModuleStructure() {
        try {
            log.info("Building complete module structure...");

            Map<String, Object> structure = new HashMap<>();

            // Get all module names
            List<String> moduleNames = getAllModuleNames();
            log.info("Total modules found: {}", moduleNames.size());

            Map<String, List<String>> sectionsByModule = new HashMap<>();
            Map<String, Map<String, List<ModuleFieldMappingDTO>>> fieldsByModuleAndSection = new HashMap<>();

            // Build structure for each module
            for (String moduleName : moduleNames) {
                log.debug("Processing module: {}", moduleName);

                // Get sections for this module
                List<String> sections = getSectionsByModule(moduleName);
                sectionsByModule.put(moduleName, sections);
                log.debug("Sections: {} - {}", sections.size(), sections);

                // Get fields for each section
                Map<String, List<ModuleFieldMappingDTO>> sectionFields = new HashMap<>();
                for (String section : sections) {
                    List<ModuleFieldMappingDTO> fields = getFieldsByModuleAndSection(moduleName, section);
                    sectionFields.put(section, fields);
                    log.debug("Fields for {}: {}", section, fields.size());
                }
                fieldsByModuleAndSection.put(moduleName, sectionFields);
            }

            // Build final structure
            structure.put("modules", moduleNames);
            structure.put("sections", sectionsByModule);
            structure.put("fields", fieldsByModuleAndSection);

            log.info("Module structure built successfully!");
            log.debug("Modules: {}, Sections map: {}, Fields map: {}", moduleNames.size(), sectionsByModule.size(), fieldsByModuleAndSection.size());

            return structure;

        } catch (Exception e) {
            log.error("Critical error in getModuleStructure: {}", e.getMessage(), e);

            // Return emergency fallback structure
            return createEmergencyFallbackStructure();
        }
    }

    // ==================== DYNAMIC SCHEMA METHODS ====================

    private List<String> getDynamicModuleNames() {
        String sql =
            "SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema = 'masters' " +
            "AND table_type = 'BASE TABLE' " +
            "AND table_name NOT LIKE '%_aud' " +
            "AND table_name NOT LIKE '%_history' " +
            "ORDER BY table_name";

        List<String> tableNames = jdbcTemplate.queryForList(sql, String.class);

        // Convert table names to module names
        return tableNames.stream()
                .map(this::convertTableToModuleName)
                .collect(Collectors.toList());
    }

    private List<String> getDynamicSectionsForModule(String moduleName) {
        String tableName = convertModuleToTableName(moduleName);

        // Get all columns for this table
        List<Map<String, Object>> columns = getTableColumns(tableName);

        // Group columns into logical sections
        Set<String> sections = new LinkedHashSet<>();

        for (Map<String, Object> column : columns) {
            String columnName = (String) column.get("column_name");
            String section = determineSectionForColumn(columnName, (String) column.get("data_type"));
            sections.add(section);
        }

        return new ArrayList<>(sections);
    }

    private List<ModuleFieldMappingDTO> getDynamicFieldsForModuleAndSection(String moduleName, String sectionName) {
        String tableName = convertModuleToTableName(moduleName);
        List<Map<String, Object>> columns = getTableColumns(tableName);

        List<ModuleFieldMappingDTO> fields = new ArrayList<>();

        for (Map<String, Object> column : columns) {
            String columnName = (String) column.get("column_name");
            String dataType = (String) column.get("data_type");
            String determinedSection = determineSectionForColumn(columnName, dataType);

            if (determinedSection.equals(sectionName)) {
                ModuleFieldMappingDTO field = new ModuleFieldMappingDTO();
                field.setModuleName(moduleName);
                field.setSectionName(sectionName);
                field.setFieldName(columnName);
                field.setFieldLabel(convertColumnToLabel(columnName));
                field.setTableName(tableName);
                field.setTableSchema("masters");
                field.setDatabaseColumnName(columnName);
                field.setDataType(dataType);
                field.setIsRelation(isForeignKeyColumn(columnName));
                field.setActiveFlag(true);

                fields.add(field);
            }
        }

        return fields;
    }

    private List<Map<String, Object>> getTableColumns(String tableName) {
        String sql =
            "SELECT column_name, data_type, is_nullable " +
            "FROM information_schema.columns " +
            "WHERE table_schema = 'masters' " +
            "AND table_name = ? " +
            "ORDER BY ordinal_position";

        return jdbcTemplate.queryForList(sql, tableName);
    }

    private String determineSectionForColumn(String columnName, String dataType) {
        // Determine section based on column name patterns
        if (columnName.matches("(?i).*(_id|_fk)$")) {
            return "relationships";
        } else if (columnName.matches("(?i).*(created|modified|updated|active|status).*")) {
            return "auditInfo";
        } else if (columnName.matches("(?i).*(name|description|title|note|remark).*")) {
            return "basicInfo";
        } else if (columnName.matches("(?i).*(quantity|amount|price|rate|total|qty).*")) {
            return "quantities";
        } else if (columnName.matches("(?i).*(date|time|year|month).*")) {
            return "dates";
        } else if (dataType.matches("(?i).*(text|json|xml).*")) {
            return "details";
        } else {
            return "main";
        }
    }

    private boolean isForeignKeyColumn(String columnName) {
        return columnName.endsWith("_id") || columnName.matches(".*[A-Za-z]+Id$");
    }

    private String convertTableToModuleName(String tableName) {
        return Arrays.stream(tableName.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .reduce((a, b) -> a + " " + b)
                .orElse(tableName);
    }

    private String convertModuleToTableName(String moduleName) {
        return moduleName.toLowerCase().replace(" ", "_");
    }

    private String convertColumnToLabel(String columnName) {
        return Arrays.stream(columnName.split("_"))
                .map(word -> {
                    if (word.equalsIgnoreCase("id")) return "ID";
                    if (word.equalsIgnoreCase("fk")) return "Foreign Key";
                    return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                })
                .reduce((a, b) -> a + " " + b)
                .orElse(columnName);
    }

    // ==================== DYNAMIC STRUCTURE ENDPOINT ====================

    @Transactional(readOnly = true)
    public Map<String, Object> getDynamicModuleStructure() {
        try {
            log.info("Building dynamic module structure from database schema...");
            return schemaService.getDynamicModuleStructure();
        } catch (Exception e) {
            log.error("Error building dynamic structure: {}", e.getMessage(), e);
            return getModuleStructure(); // fallback to existing method
        }
    }

    private List<Map<String, Object>> getDynamicFieldsAsMap(String moduleName, String sectionName) {
        String tableName = convertModuleToTableName(moduleName);
        List<Map<String, Object>> columns = getTableColumns(tableName);

        List<Map<String, Object>> fields = new ArrayList<>();

        for (Map<String, Object> column : columns) {
            String columnName = (String) column.get("column_name");
            String dataType = (String) column.get("data_type");
            String determinedSection = determineSectionForColumn(columnName, dataType);

            if (determinedSection.equals(sectionName)) {
                Map<String, Object> field = new HashMap<>();
                field.put("fieldName", columnName);
                field.put("fieldLabel", convertColumnToLabel(columnName));
                field.put("tableName", tableName);
                field.put("databaseColumnName", columnName);
                field.put("dataType", dataType);
                field.put("isRelation", isForeignKeyColumn(columnName));
                field.put("activeFlag", true);

                fields.add(field);
            }
        }

        return fields;
    }

    // ==================== HELPER METHODS ====================

    private List<String> getFallbackModules() {
        return Arrays.asList(
            "Sales Order - Domestic",
            "Sales Order - Export",
            "Purchase Order",
            "Purchase Inward",
            "Weaving Contract",
            "Sizing Plan",
            "Yarn Issue",
            "Yarn Return",
            "Fabric Inspection",
            "Processing Issue",
            "Processing Yarn Issue",
            "Process Contract",
            "Invoice",
            "Empty Beam Issue",
            "Purchase Return",
            "Finish Fabric Receive",
            "Jobwork Fabric Receive",
            "Bale Roll Packing",
            "Generate Packing List",
            "Beam Inward",
            "Weaving Yarn Issue",
            "Weaving Issue"
        );
    }

    private Map<String, Object> createEmergencyFallbackStructure() {
        log.warn("Creating emergency fallback structure");

        Map<String, Object> structure = new HashMap<>();
        List<String> fallbackModules = getFallbackModules();

        Map<String, List<String>> sectionsByModule = new HashMap<>();
        Map<String, Map<String, List<ModuleFieldMappingDTO>>> fieldsByModuleAndSection = new HashMap<>();

        for (String moduleName : fallbackModules) {
            List<String> sections = Arrays.asList("main", "details", "auditInfo");
            sectionsByModule.put(moduleName, sections);

            Map<String, List<ModuleFieldMappingDTO>> sectionFields = new HashMap<>();
            for (String section : sections) {
                List<ModuleFieldMappingDTO> fields = createSimpleFields(moduleName, section);
                sectionFields.put(section, fields);
            }
            fieldsByModuleAndSection.put(moduleName, sectionFields);
        }

        structure.put("modules", fallbackModules);
        structure.put("sections", sectionsByModule);
        structure.put("fields", fieldsByModuleAndSection);

        return structure;
    }

    private List<ModuleFieldMappingDTO> createSimpleFields(String moduleName, String sectionName) {
        List<ModuleFieldMappingDTO> fields = new ArrayList<>();
        String tableName = moduleName.toLowerCase().replace(" ", "_");

        fields.add(createFieldDTO(moduleName, sectionName, "id", "ID", tableName, "id", "bigint"));
        fields.add(createFieldDTO(moduleName, sectionName, "name", "Name", tableName, "name", "varchar"));
        fields.add(createFieldDTO(moduleName, sectionName, "created_date", "Created Date", tableName, "created_date", "timestamp"));

        return fields;
    }

    private ModuleFieldMappingDTO createFieldDTO(String moduleName, String sectionName, String fieldName,
                                               String fieldLabel, String tableName, String columnName, String dataType) {
        ModuleFieldMappingDTO dto = new ModuleFieldMappingDTO();
        dto.setModuleName(moduleName);
        dto.setSectionName(sectionName);
        dto.setFieldName(fieldName);
        dto.setFieldLabel(fieldLabel);
        dto.setTableName(tableName);
        dto.setTableSchema("masters");
        dto.setDatabaseColumnName(columnName);
        dto.setDataType(dataType);
        dto.setIsRelation(false);
        dto.setActiveFlag(true);
        return dto;
    }

    // ==================== CRUD OPERATIONS ====================

    @Transactional
    public ModuleFieldMappingDTO createMapping(ModuleFieldMappingDTO dto) {
        try {
            ModuleFieldMapping entity = new ModuleFieldMapping();
            updateEntityFromDTO(entity, dto);
            entity.setActiveFlag(true);

            ModuleFieldMapping saved = repository.save(entity);
            log.info("Created new mapping: {}", saved.getId());
            return new ModuleFieldMappingDTO(saved);
        } catch (Exception e) {
            log.error("Error creating mapping: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public ModuleFieldMappingDTO updateMapping(Long id, ModuleFieldMappingDTO dto) {
        try {
            ModuleFieldMapping entity = repository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Mapping not found with id: " + id));

            updateEntityFromDTO(entity, dto);
            ModuleFieldMapping saved = repository.save(entity);
            log.info("Updated mapping: {}", saved.getId());
            return new ModuleFieldMappingDTO(saved);
        } catch (Exception e) {
            log.error("Error updating mapping: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public void deleteMapping(Long id) {
        try {
            if (!repository.existsById(id)) {
                throw new IllegalArgumentException("Mapping not found with id: " + id);
            }
            repository.deleteById(id);
            log.info("Deleted mapping: {}", id);
        } catch (Exception e) {
            log.error("Error deleting mapping: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<ModuleFieldMappingDTO> getAllMappings() {
        try {
            List<ModuleFieldMappingDTO> mappings = repository.findAll().stream()
                    .map(ModuleFieldMappingDTO::new)
                    .collect(Collectors.toList());
            log.info("Retrieved all mappings: {}", mappings.size());
            return mappings;
        } catch (Exception e) {
            log.error("Error getting all mappings: {}", e.getMessage(), e);
            throw e;
        }
    }

    public PaginatedResponse<ModuleFieldMappingDTO> getAllMappingsWithPagination(Long offset, Long limit) {
        List<ModuleFieldMappingDTO> all = getAllMappings();
        return PaginationHelper.paginate(all, offset, limit);
    }

    @Transactional
    public List<ModuleFieldMappingDTO> bulkCreateMappings(List<ModuleFieldMappingDTO> dtos) {
        try {
            List<ModuleFieldMapping> entities = dtos.stream()
                    .map(dto -> {
                        ModuleFieldMapping entity = new ModuleFieldMapping();
                        updateEntityFromDTO(entity, dto);
                        entity.setActiveFlag(true);
                        return entity;
                    })
                    .collect(Collectors.toList());

            List<ModuleFieldMapping> saved = repository.saveAll(entities);
            log.info("Bulk created mappings: {}", saved.size());
            return saved.stream()
                    .map(ModuleFieldMappingDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error in bulk create mappings: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void updateEntityFromDTO(ModuleFieldMapping entity, ModuleFieldMappingDTO dto) {
        entity.setModuleName(dto.getModuleName());
        entity.setSectionName(dto.getSectionName());
        entity.setFieldName(dto.getFieldName());
        entity.setFieldLabel(dto.getFieldLabel());
        entity.setTableName(dto.getTableName());
        entity.setTableSchema(dto.getTableSchema());
        entity.setDatabaseColumnName(dto.getDatabaseColumnName());
        entity.setDataType(dto.getDataType());
        entity.setIsRelation(dto.getIsRelation() != null ? dto.getIsRelation() : false);
        entity.setRelationTableName(dto.getRelationTableName());
        entity.setRelationColumnName(dto.getRelationColumnName());
        entity.setRelationDisplayColumn(dto.getRelationDisplayColumn());
    }
}