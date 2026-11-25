package com.tech3.erp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

@Service
public class FileStorageService {
    
    private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);
    
    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024;
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of(
        "image/jpeg", "image/png", "image/gif", "image/webp", "image/svg+xml",
        "video/mp4", "video/quicktime", "video/x-msvideo",
        "application/pdf",
        "application/msword", 
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "text/plain",
        "text/csv",
        "application/csv",
        "text/x-csv",
        "application/x-csv",
        "text/comma-separated-values",
        "application/octet-stream",
        "application/x-zip-compressed"
    );

    @Autowired
    private S3Client s3Client;

    @Value("${storage.bucket-name}")
    private String bucketName;

    @Value("${storage.region:us-east-1}")
    private String region;

    @Value("${storage.use-cdn-url:true}")
    private boolean useCdnUrl;

    public String uploadFile(String base64File, String containerName, String originalFileName, String contentType) {
        return doUploadFile(base64File, containerName, originalFileName, contentType, false, true, null).getFileKey();
    }

    public UploadResult uploadFile(String base64File, String containerName, String originalFileName, String contentType, boolean isCompressed) {
        return doUploadFile(base64File, containerName, originalFileName, contentType, isCompressed, true, null);
    }

    public UploadResult uploadFileWithCompression(String base64File, String containerName, String originalFileName, String contentType) {
        return doUploadFile(base64File, containerName, originalFileName, contentType, true, true, null);
    }

    public UploadResult uploadFileWithCompression(String base64File, String containerName, String originalFileName, String contentType, String folderPath) {
        return doUploadFile(base64File, containerName, originalFileName, contentType, true, true, folderPath);
    }

    public UploadResult uploadFile(String base64File, String containerName, String originalFileName, String contentType, boolean shouldCompress, boolean enableCompression) {
        return doUploadFile(base64File, containerName, originalFileName, contentType, shouldCompress, enableCompression, null);
    }

    public UploadResult uploadFile(String base64File, String containerName, String originalFileName, String contentType, boolean shouldCompress, boolean enableCompression, String folderPath) {
        return doUploadFile(base64File, containerName, originalFileName, contentType, shouldCompress, enableCompression, folderPath);
    }

    private UploadResult doUploadFile(String base64File, String containerName, String originalFileName, String contentType, boolean shouldCompress, boolean enableCompression, String folderPath) {
        try {
            validateContentType(contentType);
            
            byte[] fileBytes = Base64.getDecoder().decode(base64File);
            long originalSize = fileBytes.length;
            boolean isCompressed = false;
            long compressedSize = originalSize;

            if (enableCompression && shouldCompress && isCompressible(contentType)) {
                try {
                    fileBytes = compressData(fileBytes);
                    isCompressed = true;
                    compressedSize = fileBytes.length;
                    double compressionRatio = ((originalSize - compressedSize) * 100.0) / originalSize;
                    log.info("File compressed successfully. Original: {} bytes, Compressed: {} bytes, Ratio: {:.2f}%", 
                            originalSize, compressedSize, compressionRatio);
                } catch (Exception e) {
                    log.warn("Compression failed for {}, uploading uncompressed: {}", originalFileName, e.getMessage());
                    fileBytes = Base64.getDecoder().decode(base64File);
                }
            }
            
            validateFileSize(fileBytes.length);
            
            String fileKey = generateFileKey(containerName, originalFileName, folderPath);
            
            log.info("Uploading file to S3. Bucket: {}, Key: {}, Size: {} bytes, Original: {} bytes, ContentType: {}, Compressed: {}", 
                    bucketName, fileKey, fileBytes.length, originalSize, contentType, isCompressed);
            
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileKey)
                            .contentType(contentType)
                            .contentDisposition("inline")
                            .contentEncoding(isCompressed ? "deflate" : null)
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .build(),
                    RequestBody.fromBytes(fileBytes)
            );
            
            log.info("File uploaded successfully with public-read ACL. Key: {}", fileKey);
            
            return new UploadResult(fileKey, isCompressed, originalSize, compressedSize);
        } catch (IllegalArgumentException e) {
            log.error("Validation error for file: {}", originalFileName, e);
            throw new RuntimeException("Validation error: " + e.getMessage());
        } catch (IllegalStateException e) {
            log.error("Invalid base64 format for file: {}", originalFileName, e);
            throw new RuntimeException("Invalid base64 format: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error uploading file to S3: {}", originalFileName, e);
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }
    
    private byte[] compressData(byte[] data) throws Exception {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            DeflaterOutputStream deflaterStream = new DeflaterOutputStream(outputStream, new Deflater(Deflater.DEFAULT_COMPRESSION, true));
            
            deflaterStream.write(data);
            deflaterStream.close();
            
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error compressing data", e);
            throw new RuntimeException("Failed to compress file: " + e.getMessage());
        }
    }
    
    private byte[] decompressData(byte[] compressedData) throws Exception {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InflaterInputStream inputStream = new InflaterInputStream(new ByteArrayInputStream(compressedData));
            
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            
            inputStream.close();
            return outputStream.toByteArray();
        } catch (Exception e) {
            log.error("Error decompressing data", e);
            throw new RuntimeException("Failed to decompress file: " + e.getMessage());
        }
    }

    private boolean isCompressible(String contentType) {
        if (contentType == null || contentType.isEmpty()) {
            return false;
        }
        
        String normalizedType = contentType.toLowerCase();
        
        return normalizedType.startsWith("text/") ||
               normalizedType.equals("application/json") ||
               normalizedType.equals("application/msword") ||
               normalizedType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
               normalizedType.equals("application/vnd.ms-excel") ||
               normalizedType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
               normalizedType.equals("image/png") ||
               normalizedType.equals("image/svg+xml");
    }

    private void validateFileSize(long fileSize) {
        if (fileSize <= 0) {
            throw new IllegalArgumentException("File size must be greater than 0 bytes");
        }
        if (fileSize > MAX_FILE_SIZE) {
            long maxSizeMB = MAX_FILE_SIZE / (1024 * 1024);
            long actualSizeMB = fileSize / (1024 * 1024);
            throw new IllegalArgumentException(
                String.format("File size %dMB exceeds maximum allowed size of %dMB", actualSizeMB, maxSizeMB)
            );
        }
    }
    
    private void validateContentType(String contentType) {
        if (contentType == null || contentType.trim().isEmpty()) {
            return;
        }
        
        String normalizedType = contentType.toLowerCase();
        boolean isAllowed = ALLOWED_CONTENT_TYPES.stream()
                .anyMatch(allowed -> normalizedType.equals(allowed) || (allowed.contains("/") && normalizedType.startsWith(allowed.substring(0, allowed.indexOf('/') + 1))));
        
        if (!isAllowed) {
            log.warn("Attempted upload with unrecognized content type: {}, allowing fallback", contentType);
        }
    }

    public void deleteFile(String fileKey) {
        try {
            log.info("Deleting file from S3. Bucket: {}, Key: {}", bucketName, fileKey);
            
            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileKey)
                            .build()
            );
            
            log.info("File deleted successfully. Key: {}", fileKey);
        } catch (Exception e) {
            log.error("Error deleting file from S3: {}", fileKey, e);
            throw new RuntimeException("Failed to delete file: " + e.getMessage());
        }
    }

    public String getFileUrl(String fileKey) {
        try {
            if (useCdnUrl && region != null && !region.isEmpty()) {
                return String.format("https://%s.%s.cdn.digitaloceanspaces.com/%s", bucketName, region, fileKey);
            }
            return s3Client.utilities()
                    .getUrl(GetUrlRequest.builder()
                            .bucket(bucketName)
                            .key(fileKey)
                            .build())
                    .toExternalForm();
        } catch (Exception e) {
            log.error("Error generating URL for file: {}", fileKey, e);
            throw new RuntimeException("Failed to generate file URL: " + e.getMessage());
        }
    }

    public boolean fileExists(String fileKey) {
        try {
            HeadObjectResponse response = s3Client.headObject(
                    HeadObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileKey)
                            .build()
            );
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            log.error("Error checking file existence: {}", fileKey, e);
            throw new RuntimeException("Failed to check file existence: " + e.getMessage());
        }
    }

    public Long getFileSize(String fileKey) {
        try {
            HeadObjectResponse response = s3Client.headObject(
                    HeadObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileKey)
                            .build()
            );
            return response.contentLength();
        } catch (Exception e) {
            log.error("Error getting file size: {}", fileKey, e);
            return 0L;
        }
    }

    private String generateFileKey(String containerName, String originalFileName) {
        return generateFileKey(containerName, originalFileName, null);
    }

    private String generateFileKey(String containerName, String originalFileName, String folderPath) {
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString() + (fileExtension.isEmpty() ? "" : "." + fileExtension);
        
        if (folderPath != null && !folderPath.isEmpty()) {
            String normalizedPath = folderPath.endsWith("/") ? folderPath : folderPath + "/";
            return normalizedPath + uniqueFileName;
        }
        
        return containerName + "/" + uniqueFileName;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    public static class UploadResult {
        private final String fileKey;
        private final boolean isCompressed;
        private final long originalSize;
        private final long compressedSize;

        public UploadResult(String fileKey, boolean isCompressed, long originalSize, long compressedSize) {
            this.fileKey = fileKey;
            this.isCompressed = isCompressed;
            this.originalSize = originalSize;
            this.compressedSize = compressedSize;
        }

        public String getFileKey() {
            return fileKey;
        }

        public boolean isCompressed() {
            return isCompressed;
        }

        public long getOriginalSize() {
            return originalSize;
        }

        public long getCompressedSize() {
            return compressedSize;
        }

        public double getCompressionRatio() {
            if (originalSize == 0) return 0;
            return ((originalSize - compressedSize) * 100.0) / originalSize;
        }
    }
}
