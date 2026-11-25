package com.tech3.erp.config;

import com.tech3.erp.service.SequenceSyncService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class SequenceSyncConfig {

    private static final Logger logger = LoggerFactory.getLogger(SequenceSyncConfig.class);

    @Bean
    public CommandLineRunner syncSequencesAtStartup(EntityManager entityManager, SequenceSyncService sequenceSyncService) {
        return args -> {
            logger.info("Starting master table sequence synchronization...");
            try {
                Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
                for (EntityType<?> entity : entities) {
                    Class<?> javaType = entity.getJavaType();
                    if (javaType != null) {
                        sequenceSyncService.syncSequence(javaType);
                    }
                }
                logger.info("Master table sequence synchronization completed.");
            } catch (Exception e) {
                logger.error("Error during startup sequence synchronization: {}", e.getMessage());
            }
        };
    }
}
