package com.tech3.erp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DigitalOceanSpacesTest {

    private static final Logger log = LoggerFactory.getLogger(DigitalOceanSpacesTest.class);

    @Autowired
    private S3Client s3Client;

    @Value("${storage.bucket-name}")
    private String bucketName;

    @Value("${storage.endpoint}")
    private String endpoint;

    @Test
    public void testFileUpload() {
        log.info("🔍 Testing connection to DigitalOcean Spaces...");
        log.info("Endpoint: {}", endpoint);
        log.info("Bucket: {}", bucketName);

        try {
            testListBuckets();
            testFileUploadAndDownload();
            testListFiles();
            testImageUpload();
            log.info("✨ All tests completed successfully!");
        } catch (Exception e) {
            log.error("❌ Test failed: {}", e.getMessage(), e);
            fail("DigitalOcean Spaces test failed: " + e.getMessage());
        }
    }

    private void testListBuckets() {
        log.info("\n📂 Listing all spaces/buckets...");
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            log.info("✅ Connected successfully! Found {} space(s)", response.buckets().size());
            response.buckets().forEach(bucket ->
                log.info("   - {}", bucket.name())
            );
        } catch (Exception e) {
            log.warn("⚠️ List buckets failed (this might be normal): {}", e.getMessage());
        }
    }

    private void testFileUploadAndDownload() {
        log.info("\n📤 Testing file upload and download...");

        String testContent = "Hello from DigitalOcean Spaces! This is a test file.";
        String testFilename = "test-upload.txt";
        String testKey = "test/" + testFilename;

        try {
            log.info("Uploading test file: {}", testFilename);

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(testKey)
                            .contentType("text/plain")
                            .build(),
                    RequestBody.fromString(testContent)
            );

            log.info("✅ File uploaded successfully!");
            log.info("🌐 Public URL: {}/{}/{}", endpoint, bucketName, testKey);

            log.info("📥 Downloading {} to verify...", testFilename);

            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(testKey)
                            .build()
            );

            String downloadedContent = new String(response.readAllBytes(), StandardCharsets.UTF_8);

            if (downloadedContent.equals(testContent)) {
                log.info("✅ Download verification successful!");
            } else {
                throw new AssertionError("Downloaded content doesn't match uploaded content");
            }

            deleteTestFile(testKey);

        } catch (Exception e) {
            log.error("❌ File upload/download test failed: {}", e.getMessage(), e);
            throw new RuntimeException("File upload/download test failed", e);
        }
    }

    private void testListFiles() {
        log.info("\n📂 Listing files in '{}' bucket...", bucketName);
        try {
            ListObjectsV2Response response = s3Client.listObjectsV2(
                    ListObjectsV2Request.builder()
                            .bucket(bucketName)
                            .maxKeys(10)
                            .build()
            );

            if (response.contents() != null && !response.contents().isEmpty()) {
                log.info("Found {} objects:", response.contents().size());
                response.contents().forEach(obj ->
                    log.info("   - {} ({} bytes)", obj.key(), obj.size())
                );
            } else {
                log.info("   (Bucket exists but is empty or no accessible files)");
            }
        } catch (NoSuchBucketException e) {
            log.error("❌ Bucket '{}' does not exist", bucketName);
            throw new RuntimeException("Bucket does not exist: " + bucketName, e);
        } catch (Exception e) {
            log.error("❌ Error listing files: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to list files", e);
        }
    }

    private void testImageUpload() {
        log.info("\n🖼️  Testing image upload...");

        String imageFilename = "test-image.png";
        String imageKey = "test/" + imageFilename;

        byte[] pngData = Base64.getDecoder().decode(
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="
        );

        try {
            log.info("Uploading test image: {}", imageFilename);

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(imageKey)
                            .contentType("image/png")
                            .build(),
                    RequestBody.fromBytes(pngData)
            );

            log.info("✅ Image uploaded successfully!");
            log.info("🌐 Image URL: {}/{}/{}", endpoint, bucketName, imageKey);

            deleteTestFile(imageKey);

        } catch (Exception e) {
            log.error("❌ Image upload test failed: {}", e.getMessage(), e);
            throw new RuntimeException("Image upload test failed", e);
        }
    }

    private void deleteTestFile(String key) {
        try {
            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build()
            );
            log.info("🗑️  Cleaned up test file: {}", key);
        } catch (Exception e) {
            log.warn("⚠️  Failed to delete test file {}: {}", key, e.getMessage());
        }
    }
}
