package com.tech3.erp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SchemaAutoInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SchemaAutoInitializer.class);
    private final JdbcTemplate jdbcTemplate;

    public SchemaAutoInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        logger.info("Initializing database schema migrations...");

        try {
            jdbcTemplate.execute("ALTER TABLE masters.item_master ADD COLUMN IF NOT EXISTS image_url TEXT;");
            logger.info("Checked/added 'image_url' column to masters.item_master");
        } catch (Exception e) {
            logger.warn("Could not alter masters.item_master for image_url column: {}", e.getMessage());
        }

        try {
            jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS masters.beam_card (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "beam_no VARCHAR(100) UNIQUE NOT NULL, " +
                "loom_no VARCHAR(100), " +
                "circle_no VARCHAR(100), " +
                "status VARCHAR(50), " +
                "data TEXT, " +
                "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                "updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");"
            );
            logger.info("Checked/created 'masters.beam_card' table.");
        } catch (Exception e) {
            logger.warn("Could not create masters.beam_card table: {}", e.getMessage());
        }
    }
}
