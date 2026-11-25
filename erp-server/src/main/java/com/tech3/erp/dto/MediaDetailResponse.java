package com.tech3.erp.dto;

import java.util.Objects;

public class MediaDetailResponse {

    private Long mediaDetailSno;

    private Long mediaSno;

    private String azureId;

    private String mediaUrl;

    private String thumbnailUrl;

    private String mediaType;

    private String contentType;

    private Long mediaSize;

    private String mediaDetailDescription;

    private Boolean isUploaded;

    private Long createdAt;

    private Long updatedAt;

    private String originalFileName;

    private Boolean isCompressed;

    private Long originalFileSize;

    private Long storedFileSize;

    public MediaDetailResponse() {
    }

    public MediaDetailResponse(Long mediaDetailSno, Long mediaSno, String azureId, String mediaUrl, String thumbnailUrl, String mediaType, String contentType, Long mediaSize, String mediaDetailDescription, Boolean isUploaded, Long createdAt, Long updatedAt, String originalFileName) {
        this.mediaDetailSno = mediaDetailSno;
        this.mediaSno = mediaSno;
        this.azureId = azureId;
        this.mediaUrl = mediaUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.mediaType = mediaType;
        this.contentType = contentType;
        this.mediaSize = mediaSize;
        this.mediaDetailDescription = mediaDetailDescription;
        this.isUploaded = isUploaded;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.originalFileName = originalFileName;
    }

    public Long getMediaDetailSno() {
        return mediaDetailSno;
    }

    public void setMediaDetailSno(Long mediaDetailSno) {
        this.mediaDetailSno = mediaDetailSno;
    }

    public Long getMediaSno() {
        return mediaSno;
    }

    public void setMediaSno(Long mediaSno) {
        this.mediaSno = mediaSno;
    }

    public String getAzureId() {
        return azureId;
    }

    public void setAzureId(String azureId) {
        this.azureId = azureId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getMediaSize() {
        return mediaSize;
    }

    public void setMediaSize(Long mediaSize) {
        this.mediaSize = mediaSize;
    }

    public String getMediaDetailDescription() {
        return mediaDetailDescription;
    }

    public void setMediaDetailDescription(String mediaDetailDescription) {
        this.mediaDetailDescription = mediaDetailDescription;
    }

    public Boolean getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(Boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Boolean getIsCompressed() {
        return isCompressed;
    }

    public void setIsCompressed(Boolean isCompressed) {
        this.isCompressed = isCompressed;
    }

    public Long getOriginalFileSize() {
        return originalFileSize;
    }

    public void setOriginalFileSize(Long originalFileSize) {
        this.originalFileSize = originalFileSize;
    }

    public Long getStoredFileSize() {
        return storedFileSize;
    }

    public void setStoredFileSize(Long storedFileSize) {
        this.storedFileSize = storedFileSize;
    }
}
