package com.tech3.erp.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatabaseSchemaService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSchemaService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getDynamicModuleStructure() {
        Map<String, Object> structure = new HashMap<>();
        
        // Get all tables from masters schema
        List<String> allTables = getAllTables();
        structure.put("modules", allTables);
        
        // Build sections and fields for each table
        Map<String, List<String>> sectionsByModule = new HashMap<>();
        Map<String, Map<String, List<Map<String, Object>>>> fieldsByModuleAndSection = new HashMap<>();

        for (String tableName : allTables) {
            // Convert table name to module name
            String moduleName = convertTableToModuleName(tableName);
            
            // Get table columns as fields
            List<Map<String, Object>> columns = getTableColumns(tableName);
            
            // Group columns into logical sections
            Map<String, List<Map<String, Object>>> sections = groupColumnsIntoSections(tableName, columns);
            
            // Build structure
            sectionsByModule.put(moduleName, new ArrayList<>(sections.keySet()));
            
            Map<String, List<Map<String, Object>>> moduleFields = new HashMap<>();
            for (Map.Entry<String, List<Map<String, Object>>> entry : sections.entrySet()) {
                moduleFields.put(entry.getKey(), entry.getValue());
            }
            fieldsByModuleAndSection.put(moduleName, moduleFields);
        }

        structure.put("sections", sectionsByModule);
        structure.put("fields", fieldsByModuleAndSection);
        
        return structure;
    }

    private List<String> getAllTables() {
        String sql = 
            "SELECT table_name " +
            "FROM information_schema.tables " +
            "WHERE table_schema = 'masters' " +
            "AND table_type = 'BASE TABLE' " +
            "AND table_name NOT LIKE '%_aud' " +
            "AND table_name NOT LIKE '%_history' " +
            "ORDER BY table_name";
        
        return jdbcTemplate.queryForList(sql, String.class);
    }
    
    public java.util.Set<String> getAllTableNames() {
        return new java.util.HashSet<>(getAllTables());
    }

    private List<Map<String, Object>> getTableColumns(String tableName) {
        String sql = 
            "SELECT " +
            "    column_name, " +
            "    data_type, " +
            "    is_nullable, " +
            "    column_default, " +
            "    character_maximum_length, " +
            "    numeric_precision, " +
            "    numeric_scale " +
            "FROM information_schema.columns " +
            "WHERE table_schema = 'masters' " +
            "AND table_name = ? " +
            "ORDER BY ordinal_position";
        
        return jdbcTemplate.queryForList(sql, tableName);
    }

    private Map<String, List<Map<String, Object>>> groupColumnsIntoSections(String tableName, List<Map<String, Object>> columns) {
        Map<String, List<Map<String, Object>>> sections = new LinkedHashMap<>();
        
        // Define section patterns
        List<Map<String, Object>> mainFields = new ArrayList<>();
        List<Map<String, Object>> detailFields = new ArrayList<>();
        List<Map<String, Object>> auditFields = new ArrayList<>();
        List<Map<String, Object>> relationshipFields = new ArrayList<>();

        for (Map<String, Object> column : columns) {
            String columnName = (String) column.get("column_name");
            String dataType = (String) column.get("data_type");
            
            Map<String, Object> field = new HashMap<>();
            field.put("fieldName", columnName);
            field.put("fieldLabel", convertColumnToLabel(columnName));
            field.put("tableName", tableName);
            field.put("databaseColumnName", columnName);
            field.put("dataType", dataType);
            field.put("isRelation", isForeignKeyColumn(columnName));
            
            // Categorize fields into sections
            if (isAuditColumn(columnName)) {
                auditFields.add(field);
            } else if (isForeignKeyColumn(columnName)) {
                relationshipFields.add(field);
            } else if (isDetailColumn(columnName, dataType)) {
                detailFields.add(field);
            } else {
                mainFields.add(field);
            }
        }

        // Add sections only if they have fields
        if (!mainFields.isEmpty()) {
            sections.put("mainDetails", mainFields);
        }
        if (!detailFields.isEmpty()) {
            sections.put("itemDetails", detailFields);
        }
        if (!relationshipFields.isEmpty()) {
            sections.put("relationships", relationshipFields);
        }
        if (!auditFields.isEmpty()) {
            sections.put("auditInfo", auditFields);
        }

        // If no specific sections, just put everything in main
        if (sections.isEmpty()) {
            sections.put("main", columns.stream().map(col -> {
                Map<String, Object> field = new HashMap<>();
                field.put("fieldName", col.get("column_name"));
                field.put("fieldLabel", convertColumnToLabel((String) col.get("column_name")));
                field.put("tableName", tableName);
                field.put("databaseColumnName", col.get("column_name"));
                field.put("dataType", col.get("data_type"));
                field.put("isRelation", isForeignKeyColumn((String) col.get("column_name")));
                return field;
            }).collect(ArrayList::new, ArrayList::add, ArrayList::addAll));
        }

        return sections;
    }

    private boolean isAuditColumn(String columnName) {
        return columnName.matches("(?i).*(created|modified|updated)_?(at|by|date|time).*") ||
               columnName.equalsIgnoreCase("version") ||
               columnName.equalsIgnoreCase("active_flag");
    }

    private boolean isForeignKeyColumn(String columnName) {
        return columnName.endsWith("_id") || 
               columnName.matches(".*[A-Za-z]+Id$") ||
               columnName.matches(".*_fk$");
    }

    private boolean isDetailColumn(String columnName, String dataType) {
        // Consider text, numeric, and descriptive fields as details
        return dataType.matches("(?i).*(text|varchar|character).*") && 
               columnName.matches("(?i).*(name|description|title|note|remark|comment).*") ||
               dataType.matches("(?i).*(numeric|decimal|integer).*") && 
               columnName.matches("(?i).*(quantity|amount|price|rate|total).*");
    }

    private String convertTableToModuleName(String tableName) {
        // Convert snake_case to Title Case with spaces
        return Arrays.stream(tableName.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .reduce((a, b) -> a + " " + b)
                .orElse(tableName);
    }

    private String convertColumnToLabel(String columnName) {
        // Convert snake_case to Title Case with spaces
        return Arrays.stream(columnName.split("_"))
                .map(word -> {
                    if (word.equalsIgnoreCase("id")) return "ID";
                    if (word.equalsIgnoreCase("fk")) return "Foreign Key";
                    return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                })
                .reduce((a, b) -> a + " " + b)
                .orElse(columnName);
    }

    // Get related tables for JOIN queries
    public Map<String, List<String>> getTableRelationships() {
        String sql = 
            "SELECT " +
            "    tc.table_name, " +
            "    kcu.column_name, " +
            "    ccu.table_name AS foreign_table_name, " +
            "    ccu.column_name AS foreign_column_name " +
            "FROM information_schema.table_constraints tc " +
            "JOIN information_schema.key_column_usage kcu " +
            "    ON tc.constraint_name = kcu.constraint_name " +
            "JOIN information_schema.constraint_column_usage ccu " +
            "    ON ccu.constraint_name = tc.constraint_name " +
            "WHERE tc.constraint_type = 'FOREIGN KEY' " +
            "AND tc.table_schema = 'masters' " +
            "ORDER BY tc.table_name, kcu.column_name";
        
        List<Map<String, Object>> relationships = jdbcTemplate.queryForList(sql);
        
        Map<String, List<String>> result = new HashMap<>();
        for (Map<String, Object> rel : relationships) {
            String tableName = (String) rel.get("table_name");
            String foreignTable = (String) rel.get("foreign_table_name");
            String columnName = (String) rel.get("column_name");
            
            String relationship = columnName + " -> " + foreignTable;
            result.computeIfAbsent(tableName, k -> new ArrayList<>()).add(relationship);
        }
        
        return result;
    }
}