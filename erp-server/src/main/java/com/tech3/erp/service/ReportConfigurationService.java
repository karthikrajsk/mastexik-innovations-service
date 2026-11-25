package com.tech3.erp.service;

import com.tech3.erp.dto.ReportConfigurationDTO;
import com.tech3.erp.dto.ModuleFieldMappingDTO;
import com.tech3.erp.dto.ReportColumnDTO;
import com.tech3.erp.entity.ReportConfiguration;
import com.tech3.erp.entity.ReportColumn;
import com.tech3.erp.entity.ModuleFieldMapping;
import com.tech3.erp.entity.Company;
import com.tech3.erp.repository.ReportConfigurationRepository;
import com.tech3.erp.repository.ReportColumnRepository;
import com.tech3.erp.repository.ModuleFieldMappingRepository;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.util.JsonUtils;
import com.tech3.erp.dto.ExecuteQueryResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportConfigurationService {

    private final ReportConfigurationRepository reportConfigRepository;
    private final ReportColumnRepository reportColumnRepository;
    private final ModuleFieldMappingRepository moduleFieldMappingRepository;
    private final LLMQueryExecutionService llmQueryExecutionService;
    private final CompanyRepository companyRepository;

    public ReportConfigurationService(
            ReportConfigurationRepository reportConfigRepository,
            ReportColumnRepository reportColumnRepository,
            ModuleFieldMappingRepository moduleFieldMappingRepository,
            LLMQueryExecutionService llmQueryExecutionService,
            CompanyRepository companyRepository) {
        this.reportConfigRepository = reportConfigRepository;
        this.reportColumnRepository = reportColumnRepository;
        this.moduleFieldMappingRepository = moduleFieldMappingRepository;
        this.llmQueryExecutionService = llmQueryExecutionService;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public ReportConfigurationDTO createReport(ReportConfigurationDTO dto) {
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        if (reportConfigRepository.existsByReportNameAndCompanyId(dto.getReportName(), dto.getCompanyId())) {
            throw new IllegalArgumentException("Report with this name already exists for this company");
        }

        ReportConfiguration entity = new ReportConfiguration();
        entity.setReportName(dto.getReportName());
        entity.setCompanyId(dto.getCompanyId());
        entity.setActiveFlag(true);
        if (dto.getTemplateContent() != null) {
            entity.setTemplateContent(dto.getTemplateContent());
        }
        String initialQueryData = normalizeQueryData(dto.getQueryData());
        if (initialQueryData != null) {
            entity.setQueryData(initialQueryData);
        }

        // Process and add columns with database mapping
        List<ReportColumn> columns = new ArrayList<>();
        int order = 1;
        for (ReportColumnDTO colDto : dto.getColumns()) {
            ReportColumn column = createColumnFromDTO(colDto);
            column.setColumnOrder(order++);
            columns.add(column);
        }
        entity.setColumns(columns);

        // Assign SQL query: prefer provided query, else generate
        String provided = dto.getQueryContent();
        if (provided != null && !provided.isBlank()) {
            entity.setQueryContent(provided);
        } else {
            String generatedQuery = generateSQLQuery(entity);
            entity.setQueryContent(generatedQuery);
        }

        ReportConfiguration saved = reportConfigRepository.save(entity);
        return new ReportConfigurationDTO(saved);
    }

    @Transactional(readOnly = true)
    public ReportConfigurationDTO getReportBySlug(String slug, Long companyId) {
        List<ReportConfiguration> reports = (companyId != null)
                ? reportConfigRepository.findByCompanyIdAndActiveFlagTrue(companyId)
                : reportConfigRepository.findAllByActiveFlagTrue();

        return reports.stream()
                .filter(r -> toSlug(r.getReportName()).equals(slug))
                .findFirst()
                .map(ReportConfigurationDTO::new)
                .orElseThrow(() -> new IllegalArgumentException("Report not found for slug: " + slug));
    }

    private String toSlug(String name) {
        if (name == null) return "";
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-+|-+$", "");
    }

    @Transactional(readOnly = true)
    public ReportConfigurationDTO getReportById(Long id, Long companyId) {
        ReportConfiguration entity = (companyId != null) 
                ? reportConfigRepository.findByIdAndCompanyId(id, companyId)
                    .filter(ReportConfiguration::getActiveFlag)
                    .orElseThrow(() -> new IllegalArgumentException("Active report configuration not found for this company"))
                : reportConfigRepository.findById(id)
                    .filter(ReportConfiguration::getActiveFlag)
                    .orElseThrow(() -> new IllegalArgumentException("Active report configuration not found"));
        return new ReportConfigurationDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<ReportConfigurationDTO> getAllReports(Long companyId) {
        List<ReportConfiguration> reports = (companyId != null)
                ? reportConfigRepository.findByCompanyIdAndActiveFlagTrue(companyId)
                : reportConfigRepository.findAllByActiveFlagTrue();
        
        return reports.stream()
                .map(ReportConfigurationDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReportConfigurationDTO updateReport(Long id, ReportConfigurationDTO dto) {
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required");
        }
        ReportConfiguration entity = reportConfigRepository.findByIdAndCompanyId(id, dto.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Report configuration not found for this company"));

        // Check for duplicate name if changed within the same company
        if (!entity.getReportName().equals(dto.getReportName()) 
                && reportConfigRepository.existsByReportNameAndCompanyId(dto.getReportName(), dto.getCompanyId())) {
            throw new IllegalArgumentException("Report with this name already exists for this company");
        }

        entity.setReportName(dto.getReportName());
        if (dto.getTemplateContent() != null) {
            entity.setTemplateContent(dto.getTemplateContent());
        }
        String updatedQueryData = normalizeQueryData(dto.getQueryData());
        if (updatedQueryData != null) {
            entity.setQueryData(updatedQueryData);
        }
        entity.getColumns().clear();

        // Update columns
        int order = 1;
        for (ReportColumnDTO colDto : dto.getColumns()) {
            ReportColumn column = createColumnFromDTO(colDto);
            column.setColumnOrder(order++);
            entity.addColumn(column);
        }

        // Assign SQL query: prefer provided query, else generate
        String providedUpdate = dto.getQueryContent();
        if (providedUpdate != null && !providedUpdate.isBlank()) {
            entity.setQueryContent(providedUpdate);
        } else {
            String generatedQuery = generateSQLQuery(entity);
            entity.setQueryContent(generatedQuery);
        }

        ReportConfiguration saved = reportConfigRepository.save(entity);
        return new ReportConfigurationDTO(saved);
    }

    @Transactional
    public void deleteReport(Long id, Long companyId) {
        ReportConfiguration entity = (companyId != null)
                ? reportConfigRepository.findByIdAndCompanyId(id, companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Report configuration not found for this company"))
                : reportConfigRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Report configuration not found"));
        
        reportConfigRepository.delete(entity);
    }

    @Transactional
    public ReportConfigurationDTO saveTemplate(Long id, Long companyId, String templateContent, String queryContent, Object queryData) {
        ReportConfiguration entity = (companyId != null)
                ? reportConfigRepository.findByIdAndCompanyId(id, companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Report configuration not found for this company"))
                : reportConfigRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Report configuration not found"));
        
        if (templateContent != null) {
            entity.setTemplateContent(templateContent);
        }
        if (queryContent != null && !queryContent.isBlank()) {
            entity.setQueryContent(queryContent);
        }
        String queryDataJson = normalizeQueryData(queryData);
        if (queryDataJson != null && !queryDataJson.isBlank()) {
            entity.setQueryData(queryDataJson);
        }
        ReportConfiguration saved = reportConfigRepository.save(entity);
        return new ReportConfigurationDTO(saved);
    }

    private String normalizeQueryData(Object queryData) {
        if (queryData == null) {
            return null;
        }
        if (queryData instanceof String) {
            String str = (String) queryData;
            return str.isBlank() ? null : str;
        }
        return JsonUtils.toJson(queryData);
    }

    private ReportColumn createColumnFromDTO(ReportColumnDTO dto) {
        ReportColumn column = new ReportColumn();
        column.setColumnName(dto.getColumnName());
        column.setScreenName(dto.getScreenName());
        column.setSectionName(dto.getSectionName());
        column.setFieldName(dto.getFieldName());
        column.setIsFormulaApplied(dto.getIsFormulaApplied());
        column.setFormulaType(dto.getFormulaType());
        column.setFormulaBar(dto.getFormulaBar());
        column.setIsFilterRequired(dto.getIsFilterRequired());
        column.setFilterType(dto.getFilterType());

        // Fetch database mapping if not a formula field
        if (!dto.getIsFormulaApplied() && dto.getFieldName() != null) {
            Optional<ModuleFieldMapping> mapping = moduleFieldMappingRepository
                    .findByModuleNameAndSectionNameAndFieldName(
                            dto.getScreenName(), 
                            dto.getSectionName(), 
                            dto.getFieldName()
                    );
            
            if (mapping.isPresent()) {
                ModuleFieldMapping fieldMapping = mapping.get();
                column.setTableName(fieldMapping.getTableName());
                column.setDatabaseColumnName(fieldMapping.getDatabaseColumnName());
                column.setDataType(fieldMapping.getDataType());
            }
        }

        return column;
    }

    private String generateSQLQuery(ReportConfiguration config) {
        StringBuilder query = new StringBuilder("SELECT ");
        Map<String, String> tableAliases = new HashMap<>();
        Set<String> fromTables = new HashSet<>();
        List<String> selectColumns = new ArrayList<>();
        
        int aliasCounter = 1;

        for (ReportColumn column : config.getColumns()) {
            if (column.getIsFormulaApplied()) {
                // Handle formula columns
                String formulaSQL = convertFormulaToSQL(column.getFormulaBar(), config.getColumns());
                selectColumns.add(formulaSQL + " AS " + sanitizeColumnName(column.getColumnName()));
            } else {
                // Handle regular columns
                String tableName = column.getTableName();
                if (tableName != null) {
                    String alias = tableAliases.get(tableName);
                    if (alias == null) {
                        alias = "t" + aliasCounter++;
                        tableAliases.put(tableName, alias);
                    }
                    fromTables.add(tableName + " " + alias);
                    selectColumns.add(alias + "." + column.getDatabaseColumnName()
                            + " AS " + sanitizeColumnName(column.getColumnName()));
                }
            }
        }

        query.append(String.join(", ", selectColumns));
        
        if (!fromTables.isEmpty()) {
            query.append("\nFROM ");
            query.append(String.join(", ", fromTables));
        }

        // Add WHERE clause for filters
        List<String> whereConditions = new ArrayList<>();
        for (ReportColumn column : config.getColumns()) {
            if (column.getIsFilterRequired() && !column.getIsFormulaApplied()) {
                String condition = generateFilterCondition(column, tableAliases);
                if (condition != null) {
                    whereConditions.add(condition);
                }
            }
        }

        if (!whereConditions.isEmpty()) {
            query.append("\nWHERE ");
            query.append(String.join(" AND ", whereConditions));
        }

        return query.toString();
    }

    private String convertFormulaToSQL(String formula, List<ReportColumn> allColumns) {
        if (formula == null) return "NULL";
        
        String sqlFormula = formula;
        
        // Replace column references [Column Name] with actual SQL column references
        for (ReportColumn col : allColumns) {
            String columnRef = "[" + col.getColumnName() + "]";
            if (sqlFormula.contains(columnRef)) {
                sqlFormula = sqlFormula.replace(columnRef, 
                        sanitizeColumnName(col.getColumnName()));
            }
        }
        
        return "(" + sqlFormula + ")";
    }

    private String generateFilterCondition(ReportColumn column, Map<String, String> tableAliases) {
        if (column.getTableName() == null) return null;
        
        String alias = tableAliases.get(column.getTableName());
        String columnRef = alias + "." + column.getDatabaseColumnName();
        
        switch (column.getFilterType()) {
            case "From & To":
                return columnRef + " BETWEEN ? AND ?";
            case "Field Name":
                return columnRef + " = ?";
            case "Dropdown":
                return columnRef + " IN (?)";
            default:
                return columnRef + " = ?";
        }
    }

    private String sanitizeColumnName(String columnName) {
        return columnName.replaceAll("[^a-zA-Z0-9_]", "_").toLowerCase();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> executeReport(Long id, Long companyId, Map<String, Object> filterParams) {
        ReportConfiguration config = (companyId != null)
                ? reportConfigRepository.findByIdAndCompanyId(id, companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Report configuration not found for this company"))
                : reportConfigRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Report configuration not found"));
        
        String sql = config.getQueryContent();
        if (sql == null || sql.isBlank()) {
            throw new IllegalArgumentException("No query defined for this report");
        }

        // Execute query
        ExecuteQueryResponseDTO response = llmQueryExecutionService.executeAndStore(sql, id);
        List<Map<String, Object>> rows = response.getRows();

        // Filter by companyId if provided
        if (companyId != null && rows != null && !rows.isEmpty()) {
            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new IllegalArgumentException("Company not found"));
            String companyName = company.getCompanyName();

            rows = rows.stream().filter(row -> {
                // Try different common column names for company filtering
                Object val = null;
                
                // Search for company identifying columns in a flexible way
                for (Map.Entry<String, Object> entry : row.entrySet()) {
                    String key = entry.getKey().toLowerCase().replace(" ", "").replace("_", "");
                    if (key.equals("companyid") || key.equals("companyname")) {
                        val = entry.getValue();
                        // If we found a companyid, it's usually more reliable than a name, so we can stop
                        if (key.equals("companyid")) break;
                    }
                }

                if (val == null) return true; // If no company column found, show everything

                if (val instanceof Number) {
                    return ((Number) val).longValue() == companyId;
                } else if (val instanceof String) {
                    return ((String) val).equalsIgnoreCase(companyName);
                }
                return true;
            }).collect(Collectors.toList());
            
            // Update the rows in response
            response = new ExecuteQueryResponseDTO(response.getSavedId(), rows);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("query", sql);

        // Wrap queryData to match expected format
        Map<String, Object> queryDataMap = new HashMap<>();
        queryDataMap.put("status", "success");
        queryDataMap.put("message", "Query executed and stored successfully");
        queryDataMap.put("data", response);

        result.put("queryData", queryDataMap);
        result.put("columns", config.getColumns().stream()
                .map(ReportColumn::getColumnName)
                .collect(Collectors.toList()));
        
        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> executeWeavingLiveReport(Long companyId) {
        String sql = "SELECT " +
            "ROW_NUMBER() OVER (PARTITION BY comp.company_name ORDER BY pe.doffing_date DESC) AS \"S.No\", " +
            "lg.shed AS \"Shed\", " +
            "lm.loom_no AS \"MC\", " +
            "fm.fabric_code AS \"Sort No\", " +
            "fm.fabric_quality AS \"Construction\", " +
            "COALESCE(fm.tc, '') || ' ' || COALESCE(CAST(fm.weave AS VARCHAR), '') AS \"TC & Weave\", " +
            "lg.warp_yarn_mill_name AS \"Warp Supplier\", " +
            "lg.warp_yarn_lot_no AS \"Warp L.No\", " +
            "lg.weft_yarn_mill_name AS \"Weft Supplier\", " +
            "lg.weft_yarn_lot_no AS \"Weft L.No\", " +
            "ROUND(CAST((CAST(pe.piece_meter AS NUMERIC) / NULLIF(lg.expected_fabric_meter, 0)) * 100 AS NUMERIC), 1) AS \"A %\", " +
            "ROUND(CAST((CAST(pe.doffing_picks AS NUMERIC) / NULLIF(lg.gating_picks, 0)) * 100 AS NUMERIC), 1) AS \"P %\", " +
            "ROUND(CAST(CAST(pe.doffing_picks AS NUMERIC) / 1000.0 AS NUMERIC), 1) AS \"KP\", " +
            "CAST(pe.piece_meter AS NUMERIC) AS \"Mtr\", " +
            "NULL::NUMERIC AS \"WP\", NULL::VARCHAR AS \"WPT\", NULL::VARCHAR AS \"WP/M\", " +
            "NULL::NUMERIC AS \"WT\", NULL::VARCHAR AS \"WTT\", NULL::VARCHAR AS \"WT/M\", " +
            "NULL::NUMERIC AS \"OS\", NULL::VARCHAR AS \"OST\", NULL::NUMERIC AS \"LS\", NULL::VARCHAR AS \"LST\", " +
            "NULL::VARCHAR AS \"R.Time\", NULL::VARCHAR AS \"T.S.Time\", " +
            "NULL::NUMERIC AS \"Running.CFM\", NULL::NUMERIC AS \"Ideal.CFM\", NULL::NUMERIC AS \"INCENTIVE\", " +
            "CASE WHEN pe.is_last_piece = 1 THEN 'Yes' ELSE 'No' END AS \"Is last piece\", " +
            "comp.company_name AS \"Company Name\" " +
            "FROM masters.piece_entry pe " +
            "LEFT JOIN masters.companies comp ON pe.company_id = comp.id " +
            "LEFT JOIN masters.loom_master lm ON pe.loom_id = lm.id " +
            "LEFT JOIN masters.woven_fabric_master fm ON pe.fabric_master_id = fm.id " +
            "LEFT JOIN masters.flange_master fl ON pe.flange_id = fl.id " +
            "LEFT JOIN ( " +
            "    SELECT DISTINCT ON (bg.loom_id) " +
            "        bg.loom_id, bg.shed AS shed, bgd.beam_no, bgd.flange_no, " +
            "        bgd.warp_yarn_mill_name, bgd.warp_yarn_lot_no, " +
            "        bgd.weft_yarn_mill_name, bgd.weft_yarn_lot_no, " +
            "        CAST(bgd.gating_picks AS NUMERIC) AS gating_picks, " +
            "        CAST(bgd.expected_fabric_meter AS NUMERIC) AS expected_fabric_meter " +
            "    FROM masters.beam_gating bg " +
            "    INNER JOIN masters.beam_gating_details bgd ON bgd.beam_gating_id = bg.id " +
            "    ORDER BY bg.loom_id, bg.id DESC " +
            ") lg ON pe.loom_id = lg.loom_id " +
            "WHERE pe.is_factory_fabric = 1 ";

        if (companyId != null) {
            sql += "AND pe.company_id = " + companyId + " ";
        }
        sql += "ORDER BY comp.company_name, pe.doffing_date DESC";

        ExecuteQueryResponseDTO response = llmQueryExecutionService.executeAndStore(sql, null);
        Map<String, Object> result = new HashMap<>();
        result.put("query", sql);

        Map<String, Object> queryDataMap = new HashMap<>();
        queryDataMap.put("status", "success");
        queryDataMap.put("message", "Query executed successfully");
        queryDataMap.put("data", response);

        result.put("queryData", queryDataMap);
        result.put("rows", response.getRows());
        return result;
    }
}