package com.tech3.erp.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StorageConfigVerification {

    private static final Logger log = LoggerFactory.getLogger(StorageConfigVerification.class);

    @Value("${storage.endpoint:NOT_SET}")
    private String endpoint;

    @Value("${storage.bucket-name:NOT_SET}")
    private String bucketName;

    @Value("${storage.region:NOT_SET}")
    private String region;

    @Value("${storage.access-key:NOT_SET}")
    private String accessKey;

    @Value("${storage.secret-key:NOT_SET}")
    private String secretKey;

    @PostConstruct
    public void verifyStorageConfiguration() {
        log.info("================== STORAGE CONFIGURATION VERIFICATION ==================");
        
        boolean isConfigured = true;

        if (endpoint.equals("NOT_SET") || endpoint.isEmpty()) {
            log.error("❌ STORAGE_ENDPOINT is not configured");
            isConfigured = false;
        } else {
            log.info("✅ Storage endpoint: {}", endpoint);
        }

        if (bucketName.equals("NOT_SET") || bucketName.isEmpty()) {
            log.error("❌ STORAGE_BUCKET_NAME is not configured");
            isConfigured = false;
        } else {
            log.info("✅ Storage bucket: {}", bucketName);
        }

        if (region.equals("NOT_SET") || region.isEmpty()) {
            log.error("❌ STORAGE_REGION is not configured");
            isConfigured = false;
        } else {
            log.info("✅ Storage region: {}", region);
        }

        if (accessKey.equals("NOT_SET") || accessKey.isEmpty()) {
            log.error("❌ STORAGE_ACCESS_KEY is not configured - Set environment variable STORAGE_ACCESS_KEY");
            isConfigured = false;
        } else {
            String masked = accessKey.substring(0, Math.min(8, accessKey.length())) + "***";
            log.info("✅ Access key configured: {}", masked);
        }

        if (secretKey.equals("NOT_SET") || secretKey.isEmpty()) {
            log.error("❌ STORAGE_SECRET_KEY is not configured - Set environment variable STORAGE_SECRET_KEY");
            isConfigured = false;
        } else {
            log.info("✅ Secret key configured (masked for security)");
        }

        log.info("=========================================================================");

        if (!isConfigured) {
            log.error("⚠️  STORAGE CONFIGURATION INCOMPLETE!");
            log.error("Set these environment variables before running:");
            log.error("  STORAGE_ACCESS_KEY");
            log.error("  STORAGE_SECRET_KEY");
            log.error("  STORAGE_ENDPOINT");
            log.error("  STORAGE_BUCKET_NAME");
            log.error("  STORAGE_REGION");
        } else {
            log.info("✅ All storage configurations verified successfully!");
        }
    }
}
