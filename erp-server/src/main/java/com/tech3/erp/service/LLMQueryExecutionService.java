package com.tech3.erp.service;

import com.tech3.erp.dto.ExecuteQueryResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LLMQueryExecutionService {

    private static final Logger log = LoggerFactory.getLogger(LLMQueryExecutionService.class);

    private final JdbcTemplate jdbcTemplate;

    public LLMQueryExecutionService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Execute a SELECT SQL and return the result rows without persisting them.
     */
    public ExecuteQueryResponseDTO executeAndStore(String sql, Long id) {
        try {
            String sqlTrimmed = sql == null ? "" : sql.trim();
            if (!sqlTrimmed.toLowerCase().startsWith("select")) {
                throw new IllegalArgumentException("Only SELECT queries are allowed");
            }

            // Execute query and collect rows as List<Map<String, Object>>
            List<Map<String, Object>> rows = jdbcTemplate.query(sqlTrimmed, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> map = new HashMap<>();
                    ResultSetMetaData meta = rs.getMetaData();
                    int columnCount = meta.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnLabel = meta.getColumnLabel(i);
                        Object value = rs.getObject(i);
                        map.put(columnLabel, value);
                    }
                    return map;
                }
            });

            Long responseId = id != null ? id : null;
            return new ExecuteQueryResponseDTO(responseId, rows);
        } catch (DataAccessException ex) {
            log.error("SQL execution failed", ex);
            throw new RuntimeException("SQL execution failed: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Unexpected error executing query", ex);
            throw new RuntimeException("Unexpected error: " + ex.getMessage());
        }
    }

}