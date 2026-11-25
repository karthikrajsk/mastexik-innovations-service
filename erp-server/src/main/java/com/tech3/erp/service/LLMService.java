package com.tech3.erp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech3.erp.dto.LLMQueryRequestDTO;
import com.tech3.erp.dto.LLMQueryResponseDTO;
import com.tech3.erp.dto.ReportColumnDTO;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class LLMService {

    private static final Logger log = LoggerFactory.getLogger(LLMService.class);

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final DatabaseSchemaService databaseSchemaService;
    
    @Value("${gemini.api.key:AIzaSyDIbfOMllSWY6JcfSx0qxq4sHYbddpJNeM}")
    private String geminiApiKey;
    
    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent}")
    private String geminiApiUrl;

    public LLMService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper, DatabaseSchemaService databaseSchemaService) {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        this.webClient = webClientBuilder
                .clientConnector(new JdkClientHttpConnector(httpClient))
                .build();
        this.objectMapper = objectMapper;
        this.databaseSchemaService = databaseSchemaService;
    }

    public LLMQueryResponseDTO generatePostgreSQLQuery(LLMQueryRequestDTO request) {
        try {
            String prompt = buildPrompt(request);
            String geminiResponse = callGeminiAPI(prompt);
            String extractedQuery = extractQueryFromResponse(geminiResponse);
            
            // Step 1: Correct JOIN table names based on FK relationships
            String correctedQuery = correctJoinTableNames(extractedQuery, request);
            
            // Step 2: Validate and correct ALL table names in the query
            correctedQuery = validateAndCorrectAllTableNames(correctedQuery);
            
            // Step 3: Correct column names for FK joins
            correctedQuery = correctJoinColumnNames(correctedQuery, request);
            
            return LLMQueryResponseDTO.success(correctedQuery);
        } catch (Exception e) {
            log.error("Error generating PostgreSQL query: ", e);
            return LLMQueryResponseDTO.error("Failed to generate query: " + e.getMessage());
        }
    }

    /**
     * Extracts invoice data using Gemini AI.
     */
    public String extractInvoiceData(byte[] fileBytes, String mimeType) {
        try {
            String base64File = java.util.Base64.getEncoder().encodeToString(fileBytes);
            
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> part1 = new HashMap<>();
            part1.put("text", "Extract the following fields from this invoice: " +
                    "invNo (Invoice No/Bill No), " +
                    "invDate (YYYY-MM-DD), " +
                    "sizingCost (Amount before tax), " +
                    "gstAmount (Sum of CGST and SGST, or total GST), " +
                    "netTotal (Final total amount). " +
                    "Return ONLY a valid JSON object with these keys. " +
                    "If a value is not found or the document is not an invoice, use null for strings and 0.0 for numbers.");
            
            Map<String, Object> part2 = new HashMap<>();
            Map<String, String> inlineData = new HashMap<>();
            inlineData.put("mime_type", mimeType);
            inlineData.put("data", base64File);
            part2.put("inline_data", inlineData);
            
            Map<String, Object> content = new HashMap<>();
            content.put("parts", List.of(part1, part2));
            requestBody.put("contents", List.of(content));

            String response = webClient.post()
                    .uri(geminiApiUrl + "?key=" + geminiApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return extractJsonFromResponse(response);
        } catch (Exception e) {
            log.error("Error extracting invoice data with Gemini: ", e);
            throw new RuntimeException("Failed to extract invoice data: " + e.getMessage());
        }
    }

    private String extractJsonFromResponse(String geminiResponse) {
        try {
            JsonNode responseJson = objectMapper.readTree(geminiResponse);
            JsonNode candidates = responseJson.get("candidates");
            if (candidates != null && candidates.isArray() && candidates.size() > 0) {
                String text = candidates.get(0).get("content").get("parts").get(0).get("text").asText();
                return cleanJsonResponse(text);
            }
            return "{}";
        } catch (Exception e) {
            return "{}";
        }
    }

    private String cleanJsonResponse(String text) {
        String cleaned = text.trim();
        if (cleaned.startsWith("```json")) cleaned = cleaned.substring(7);
        else if (cleaned.startsWith("```")) cleaned = cleaned.substring(3);
        if (cleaned.endsWith("```")) cleaned = cleaned.substring(0, cleaned.length() - 3);
        return cleaned.trim();
    }

    private String buildPrompt(LLMQueryRequestDTO request) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a PostgreSQL SELECT query based on the following requirements:\n\n");
        prompt.append("Report Name: ").append(request.getReportName()).append("\n\n");
        prompt.append("Database Schema: All tables are under the 'masters' schema.\n\n");
        
        // Get actual table names from database
        Set<String> actualTables = databaseSchemaService.getAllTableNames();
        prompt.append("IMPORTANT - Available tables in database (use ONLY these exact names):\n");
        for (String table : actualTables) {
            prompt.append("- masters.").append(table).append("\n");
        }
        prompt.append("\n");
        
        // Build FK to table mapping for column name guidance
        String mainTable = request.getColumns().stream()
                .map(ReportColumnDTO::getTableName)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        Map<String, String> fkToResolvedTable = new HashMap<>();
        if (mainTable != null) {
            Map<String, java.util.List<String>> rels = databaseSchemaService.getTableRelationships();
            java.util.List<String> mainRels = rels.getOrDefault(mainTable, java.util.List.of());
            for (String r : mainRels) {
                String[] parts = r.split("->");
                if (parts.length == 2) {
                    String col = parts[0].trim().replace(" ", "");
                    String ft = parts[1].trim();
                    String resolved = resolveJoinTableForFk(col, ft, actualTables);
                    fkToResolvedTable.put(col, resolved);
                }
            }
        }
        
        prompt.append("Columns to include:\n");
        
        for (ReportColumnDTO column : request.getColumns()) {
            prompt.append("- Column Name: '").append(column.getColumnName()).append("'\n");
            prompt.append("  Field Name: ").append(column.getFieldName()).append("\n");
            prompt.append("  Table Name: ").append(column.getTableName()).append("\n");
            prompt.append("  Database Column: ").append(column.getDatabaseColumnName()).append("\n");
            prompt.append("  Data Type: ").append(column.getDataType()).append("\n");
            prompt.append("  Section: ").append(column.getSectionName()).append("\n");
            
            // Add guidance for FK columns about which column to select from joined table
            if ("relationships".equals(column.getSectionName()) && column.getFieldName() != null && column.getFieldName().endsWith("_id")) {
                String resolvedTable = fkToResolvedTable.get(column.getFieldName());
                if (resolvedTable != null) {
                    String entityName = column.getFieldName().replace("_id", "");
                    prompt.append("  *** For this FK, join to masters.").append(resolvedTable)
                          .append(" and select the column named '").append(entityName).append("_name' if it exists, ")
                          .append("otherwise select 'name' ***\n");
                }
            }
            prompt.append("\n");
        }
        
        java.util.Set<String> requestedFkCols = new java.util.HashSet<>();
        for (ReportColumnDTO c : request.getColumns()) {
            String name = c.getDatabaseColumnName() != null ? c.getDatabaseColumnName() : c.getFieldName();
            if (name != null && name.endsWith("_id")) {
                requestedFkCols.add(name);
            }
        }
        if (mainTable != null && !fkToResolvedTable.isEmpty()) {
            prompt.append("Verified foreign key relationships with column guidance:\n");
            for (String fk : requestedFkCols) {
                String resolved = fkToResolvedTable.get(fk);
                if (resolved != null) {
                    String entityName = fk.replace("_id", "");
                    prompt.append("- ").append(mainTable).append(".").append(fk).append(" -> masters.").append(resolved)
                          .append(" (select '").append(entityName).append("_name' if exists, else 'name')\n");
                }
            }
            prompt.append("\n");
        }
        
        prompt.append("Requirements:\n");
        prompt.append("1. Use the 'masters' schema prefix for all tables\n");
        prompt.append("2. CRITICAL: Use ONLY the exact table names from the 'Available tables' list above. Do NOT use singular forms if plural exists (e.g., use 'companies' not 'company')\n");
        prompt.append("3. Use proper column aliases with the exact 'Column Name' values provided\n");
        prompt.append("4. CRITICAL FOR FK COLUMNS: For any field whose name ends with '_id' and is in the 'relationships' section:\n");
        prompt.append("   a. DO NOT select the raw id value\n");
        prompt.append("   b. Instead, join to the referenced master table using the verified relationships above\n");
        prompt.append("   c. From the joined table, select the column named '<entity>_name' (e.g., for vendor_id join to vendors and select vendor_name)\n");
        prompt.append("   d. If '<entity>_name' doesn't exist, then select 'name' as fallback\n");
        prompt.append("   e. Examples:\n");
        prompt.append("      - vendor_id: join to masters.vendors, select vendor_name (not 'name')\n");
        prompt.append("      - company_id: join to masters.companies, select company_name (not 'name')\n");
        prompt.append("      - tax_id: join to masters.tax_types, select tax_type_name or name\n");
        prompt.append("5. Generate a clean, well-formatted PostgreSQL SELECT query\n");
        prompt.append("6. Only return the SQL query, no explanations or additional text\n");
        prompt.append("7. Use appropriate table aliases for readability\n\n");
        prompt.append("Example format:\n");
        prompt.append("SELECT\n");
        prompt.append("    t.col1 AS \"Column 1\"\n");
        prompt.append("FROM masters.some_table t;\n\n");
        prompt.append("Generate the PostgreSQL query now:");
        
        return prompt.toString();
    }

    private String callGeminiAPI(String prompt) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> content = new HashMap<>();
            Map<String, Object> parts = new HashMap<>();
            parts.put("text", prompt);
            content.put("parts", List.of(parts));
            requestBody.put("contents", List.of(content));

            String response = webClient.post()
                    .uri(geminiApiUrl + "?key=" + geminiApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;
        } catch (WebClientResponseException e) {
            log.error("Gemini API error: Status={}, Body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Gemini API call failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error calling Gemini API: ", e);
            throw new RuntimeException("Failed to call Gemini API: " + e.getMessage());
        }
    }

    private String extractQueryFromResponse(String geminiResponse) {
        try {
            JsonNode responseJson = objectMapper.readTree(geminiResponse);
            JsonNode candidates = responseJson.get("candidates");
            
            if (candidates != null && candidates.isArray() && candidates.size() > 0) {
                JsonNode firstCandidate = candidates.get(0);
                JsonNode content = firstCandidate.get("content");
                
                if (content != null) {
                    JsonNode parts = content.get("parts");
                    if (parts != null && parts.isArray() && parts.size() > 0) {
                        JsonNode firstPart = parts.get(0);
                        String text = firstPart.get("text").asText();
                        
                        return cleanQuery(text);
                    }
                }
            }
            
            throw new RuntimeException("Invalid response format from Gemini API");
        } catch (Exception e) {
            log.error("Error extracting query from Gemini response: ", e);
            throw new RuntimeException("Failed to extract query from response: " + e.getMessage());
        }
    }

    private String cleanQuery(String rawQuery) {
        String cleaned = rawQuery.trim();
        
        if (cleaned.startsWith("```sql")) {
            cleaned = cleaned.substring(6);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }
        
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }
        
        return cleaned.trim();
    }

    /**
     * Validates and corrects all table names in the generated query
     * This ensures any table name (not just in JOINs) matches actual database tables
     */
    private String validateAndCorrectAllTableNames(String sql) {
        if (sql == null || sql.isEmpty()) return sql;
        
        Set<String> actualTables = databaseSchemaService.getAllTableNames();
        
        // Pattern to match masters.table_name (with optional alias)
        Pattern tablePattern = Pattern.compile("masters\\.(\\w+)(?:\\s+(?:AS\\s+)?(\\w+))?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = tablePattern.matcher(sql);
        StringBuffer sb = new StringBuffer();
        
        while (matcher.find()) {
            String tableName = matcher.group(1);
            String alias = matcher.group(2);
            
            // Check if table exists, if not, try to resolve it
            if (!actualTables.contains(tableName)) {
                String correctedTable = resolveToExistingTable(tableName, actualTables);
                log.info("Correcting table name: {} -> {}", tableName, correctedTable);
                
                String replacement = "masters." + correctedTable;
                if (alias != null && !alias.isEmpty()) {
                    replacement += " " + alias;
                }
                matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            } else {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(matcher.group(0)));
            }
        }
        matcher.appendTail(sb);
        
        return sb.toString();
    }

    /**
     * Corrects column names in SELECT clause for FK joins
     * Ensures we select <entity>_name instead of just 'name' from joined tables
     */
    private String correctJoinColumnNames(String sql, LLMQueryRequestDTO request) {
        if (sql == null || sql.isEmpty()) return sql;
        
        // Get FK columns from request
        Map<String, String> fkColumnToAlias = new HashMap<>();
        for (ReportColumnDTO column : request.getColumns()) {
            if ("relationships".equals(column.getSectionName()) && 
                column.getFieldName() != null && 
                column.getFieldName().endsWith("_id")) {
                fkColumnToAlias.put(column.getFieldName(), column.getColumnName());
            }
        }
        
        if (fkColumnToAlias.isEmpty()) return sql;
        
        // Build map of table alias to FK and expected column name
        Map<String, String> aliasToExpectedColumn = new HashMap<>();
        
        // Pattern to find JOIN clauses and extract alias and FK relationship
        Pattern joinPattern = Pattern.compile(
            "(?i)LEFT\\s+JOIN\\s+masters\\.(\\w+)\\s+(?:AS\\s+)?(\\w+)\\s+ON\\s+\\w+\\.(\\w+)\\s*=\\s*\\2\\.id",
            Pattern.CASE_INSENSITIVE
        );
        
        Matcher joinMatcher = joinPattern.matcher(sql);
        while (joinMatcher.find()) {
            String tableName = joinMatcher.group(1);
            String alias = joinMatcher.group(2);
            String fkColumn = joinMatcher.group(3);
            
            if (fkColumnToAlias.containsKey(fkColumn)) {
                // Derive expected column name from FK
                String entityName = fkColumn.replace("_id", "");
                String expectedColumn = entityName + "_name";
                aliasToExpectedColumn.put(alias, expectedColumn);
                log.info("Mapping alias '{}' to expected column '{}' (from FK '{}')", alias, expectedColumn, fkColumn);
            }
        }
        
        if (aliasToExpectedColumn.isEmpty()) return sql;
        
        // Now correct the SELECT clause
        // Pattern to find SELECT items like: alias.column_name AS "Alias Name"
        Pattern selectPattern = Pattern.compile(
            "(\\w+)\\.(\\w+)\\s+AS\\s+\"([^\"]+)\"",
            Pattern.CASE_INSENSITIVE
        );
        
        Matcher selectMatcher = selectPattern.matcher(sql);
        StringBuffer sb = new StringBuffer();
        
        while (selectMatcher.find()) {
            String alias = selectMatcher.group(1);
            String currentColumn = selectMatcher.group(2);
            String asClause = selectMatcher.group(3);
            
            // Check if this alias should have a corrected column name
            if (aliasToExpectedColumn.containsKey(alias)) {
                String expectedColumn = aliasToExpectedColumn.get(alias);
                
                // Only correct if current column is just 'name' and we expect entity_name
                if ("name".equalsIgnoreCase(currentColumn) && !expectedColumn.equalsIgnoreCase("name")) {
                    String replacement = alias + "." + expectedColumn + " AS \"" + asClause + "\"";
                    log.info("Correcting column: {}.{} -> {}.{}", alias, currentColumn, alias, expectedColumn);
                    selectMatcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
                } else {
                    selectMatcher.appendReplacement(sb, Matcher.quoteReplacement(selectMatcher.group(0)));
                }
            } else {
                selectMatcher.appendReplacement(sb, Matcher.quoteReplacement(selectMatcher.group(0)));
            }
        }
        selectMatcher.appendTail(sb);
        
        return sb.toString();
    }

    private String correctJoinTableNames(String sql, LLMQueryRequestDTO request) {
        if (sql == null || sql.isEmpty()) return sql;
        String mainTable = request.getColumns().stream()
                .map(ReportColumnDTO::getTableName)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (mainTable == null) return sql;
        Map<String, java.util.List<String>> rels = databaseSchemaService.getTableRelationships();
        java.util.List<String> mainRels = rels.get(mainTable);
        if (mainRels == null || mainRels.isEmpty()) return sql;
        java.util.Map<String, String> fkToTable = new java.util.HashMap<>();
        for (String r : mainRels) {
            String[] parts = r.split("->");
            if (parts.length == 2) {
                String col = parts[0].trim().replace(" ", "");
                String ft = parts[1].trim();
                fkToTable.put(col, ft);
            }
        }
        Matcher fromM = Pattern.compile("(?i)from\\s+masters\\.(\\w+)\\s+(\\w+)").matcher(sql);
        String mainAlias = null;
        if (fromM.find()) {
            mainAlias = fromM.group(2);
        }
        if (mainAlias == null) return sql;
        Matcher m = Pattern.compile("(?i)join\\s+masters\\.(\\w+)\\s+(\\w+)\\s+on\\s+([^\n;]+)").matcher(sql);
        StringBuffer sb = new StringBuffer();
        Set<String> actualTables = databaseSchemaService.getAllTableNames();
        while (m.find()) {
            String tbl = m.group(1);
            String alias = m.group(2);
            String on = m.group(3);
            String expected = null;
            for (Map.Entry<String, String> e : fkToTable.entrySet()) {
                String fk = e.getKey();
                if ((on.contains(mainAlias + "." + fk) && on.contains(alias + ".id")) || (on.contains(alias + ".id") && on.contains(mainAlias + "." + fk))) {
                    expected = resolveJoinTableForFk(fk, e.getValue(), actualTables);
                    break;
                }
            }
            if (expected != null && !tbl.equals(expected)) {
                String replacement = m.group(0).replaceFirst("(?i)masters\\." + Pattern.quote(tbl), "masters." + expected);
                m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
            } else {
                m.appendReplacement(sb, Matcher.quoteReplacement(m.group(0)));
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String resolveToExistingTable(String name, Set<String> actual) {
        if (name == null || name.isEmpty()) return name;
        if (actual.contains(name)) return name;
        for (String t : actual) {
            if (t.equalsIgnoreCase(name)) return t;
        }
        if (name.endsWith("y")) {
            String plural = name.substring(0, name.length() - 1) + "ies";
            if (actual.contains(plural)) return plural;
        }
        String withS = name + "s";
        if (actual.contains(withS)) return withS;
        String withEs = name + "es";
        if (actual.contains(withEs)) return withEs;
        if (name.endsWith("ies") && name.length() > 3) {
            String singular = name.substring(0, name.length() - 3) + "y";
            if (actual.contains(singular)) return singular;
        }
        if (name.endsWith("es") && name.length() > 2) {
            String singular = name.substring(0, name.length() - 2);
            if (actual.contains(singular)) return singular;
        }
        if (name.endsWith("s") && name.length() > 1) {
            String singular = name.substring(0, name.length() - 1);
            if (actual.contains(singular)) return singular;
        }
        for (String t : actual) {
            if (t.startsWith(name) || name.startsWith(t)) {
                return t;
            }
        }
        log.warn("Could not resolve table name '{}' to any existing table. Available tables: {}", name, actual);
        return name;
    }

    private String resolveJoinTableForFk(String fk, String schemaReported, Set<String> actual) {
        if (fk != null && fk.endsWith("_id")) {
            String base = fk.substring(0, fk.length() - 3);
            if (base.endsWith("y")) {
                String plural = base.substring(0, base.length() - 1) + "ies";
                if (actual.contains(plural)) return plural;
            }
            String withS = base + "s";
            if (actual.contains(withS)) return withS;
            String withEs = base + "es";
            if (actual.contains(withEs)) return withEs;
            if (actual.contains(base)) return base;
        }
        return resolveToExistingTable(schemaReported, actual);
    }
}
