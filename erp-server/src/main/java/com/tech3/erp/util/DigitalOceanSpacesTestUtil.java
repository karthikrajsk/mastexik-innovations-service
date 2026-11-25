
package com.tech3.erp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DigitalOceanSpacesTestUtil {

    private static final Logger log = LoggerFactory.getLogger(DigitalOceanSpacesTestUtil.class);

    private final String accessKey;
    private final String secretKey;
    private final String endpointUrl;
    private final String bucketName;
    private final S3Client s3Client;

    public DigitalOceanSpacesTestUtil(String accessKey, String secretKey, String endpointUrl, String bucketName) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.endpointUrl = endpointUrl;
        this.bucketName = bucketName;

        this.s3Client = S3Client.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create(endpointUrl))
                .credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                    )
                )
                .build();
    }

    public boolean testConnection() {
        log.info("\n🔍 Testing connection to DigitalOcean Spaces...");
        log.info("Endpoint: {}", endpointUrl);
        log.info("Bucket: {}", bucketName);

        try {
            listBuckets();
            fileUploadAndDownload();
            listFiles();
            imageUpload();
            log.info("\n✨ All tests completed successfully!");
            return true;
        } catch (Exception e) {
            log.error("\n❌ Test failed: {}", e.getMessage(), e);
            return false;
        } finally {
            s3Client.close();
        }
    }

    private void listBuckets() {
        log.info("\n📂 Listing all spaces/buckets...");
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            log.info("✅ Connected successfully! Found {} space(s)", response.buckets().size());
            response.buckets().forEach(bucket ->
                log.info("   - {}", bucket.name())
            );
        } catch (Exception e) {
            log.warn("⚠️  List buckets failed (this might be normal): {}", e.getMessage());
        }
    }

    private void fileUploadAndDownload() {
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
            log.info("🌐 Public URL: {}/{}/{}", endpointUrl, bucketName, testKey);

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

    private void listFiles() {
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

    private void imageUpload() {
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
            log.info("🌐 Image URL: {}/{}/{}", endpointUrl, bucketName, imageKey);

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

    public static void main(String[] args) {
        String accessKey = "DO8018GEF6QAAZHENPX4";
        String secretKey = "6B7QKSYlAc7V66nCkR+mSs+9UV0N0p/M067/ocDbYYI";
        String endpointUrl = "https://tech3.sfo3.digitaloceanspaces.com";
        String bucketName = "ERP";

        DigitalOceanSpacesTestUtil testUtil = new DigitalOceanSpacesTestUtil(
            accessKey,
            secretKey,
            endpointUrl,
            bucketName
        );

        System.out.println("\n🚀 DigitalOcean Spaces Upload Test");
        System.out.println("=".repeat(50));

        boolean success = testUtil.testConnection();

        System.out.println("\n" + "=".repeat(50));
        if (success) {
            System.out.println("✨ Test completed successfully!");
        } else {
            System.out.println("❌ Test completed with errors!");
        }
    }
}