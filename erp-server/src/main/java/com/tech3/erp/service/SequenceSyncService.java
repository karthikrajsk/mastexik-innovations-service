package com.tech3.erp.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SequenceSyncService {

    private static final Logger logger = LoggerFactory.getLogger(SequenceSyncService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void syncSequence(Class<?> entityClass) {
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation != null) {
            String schema = tableAnnotation.schema();
            String tableName = tableAnnotation.name();
            
            // Sync any table that has an annotation, preferring schema if present
            sync(schema, tableName);
        }
    }

    private String getPrimaryKeyColumn(String fullTableName) {
        try {
            String sql = 
                "SELECT a.attname FROM pg_index i " +
                "JOIN pg_attribute a ON a.attrelid = i.indrelid AND a.attnum = ANY(i.indkey) " +
                "WHERE i.indrelid = CAST(?1 AS regclass) AND i.indisprimary";
            
            return (String) entityManager.createNativeQuery(sql)
                .setParameter(1, fullTableName)
                .getSingleResult();
        } catch (Exception e) {
            return "id"; // Fallback to 'id'
        }
    }



    private void sync(String schema, String tableName) {
        try {
            String fullTableName = (schema != null && !schema.isEmpty()) ? schema + "." + tableName : tableName;

        // Check table exists before any query that could abort the transaction
        String existsCheckSql = "SELECT COUNT(*) FROM information_schema.tables " +
            "WHERE table_schema = :schema AND table_name = :table";
        Number tableCount = (Number) entityManager.createNativeQuery(existsCheckSql)
            .setParameter("schema", schema != null && !schema.isEmpty() ? schema : "public")
            .setParameter("table", tableName)
            .getSingleResult();

        if (tableCount.intValue() == 0) {
            logger.debug("Table {} does not exist in database, skipping sequence sync", fullTableName);
            return;
        }
            String pkColumn = getPrimaryKeyColumn(fullTableName);
            
            // PostgreSQL specific query to find sequence name
            // Works for both SERIAL and IDENTITY columns
            String findSeqSql = String.format(
                "SELECT COALESCE(" +
                "  pg_get_serial_sequence('%s', '%s')," +
                "  (SELECT s.relname FROM pg_class s " +
                "   JOIN pg_depend d ON d.objid = s.oid " +
                "   JOIN pg_class t ON t.oid = d.refobjid " +
                "   JOIN pg_attribute a ON a.attrelid = t.oid AND a.attnum = d.refobjsubid " +
                "   WHERE t.relname = '%s' AND a.attname = '%s' AND s.relkind = 'S' LIMIT 1)" +
                ")", 
                fullTableName, pkColumn, tableName, pkColumn
            );
            
            Object seqName = null;
            try {
                seqName = entityManager.createNativeQuery(findSeqSql).getSingleResult();
            } catch (Exception e) {
                logger.debug("Sequence not found via standard queries for {}: {}", fullTableName, e.getMessage());
            }

            if (seqName != null) {
                // Sync the sequence using a single efficient query
                String syncSql = String.format(
                    "SELECT setval('%s', COALESCE((SELECT MAX(%s) FROM %s), 0) + 1, false)",
                    seqName.toString(), pkColumn, fullTableName
                );
                Object newValue = entityManager.createNativeQuery(syncSql).getSingleResult();
                logger.info("Successfully synchronized sequence {} for table {} to next value {}", seqName, fullTableName, newValue);
            } else {
                logger.debug("No sequence found for table {}. PK column: {}", fullTableName, pkColumn);
            }
        } catch (Exception e) {
            logger.error("Could not synchronize sequence for table {}: {}", tableName, e.getMessage());
        }
    }
}