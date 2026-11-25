package com.tech3.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "media_detail", schema = "masters")
public class MediaDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_detail_sno")
    private Long mediaDetailSno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "media_sno", nullable = false)
    private Media media;

    @Column(name = "azure_id")
    private String azureId;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "media_type", length = 100)
    private String mediaType;

    @Column(name = "content_type", length = 255)
    private String contentType;

    @Column(name = "media_size")
    private Long mediaSize;

    @Column(name = "media_detail_description", length = 500)
    private String mediaDetailDescription;

    @Column(name = "is_uploaded")
    private Boolean isUploaded = true;

    @Column(name = "created_at", insertable = true, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "is_compressed")
    private Boolean isCompressed = false;

    @Column(name = "original_file_size")
    private Long originalFileSize;

    @Column(name = "stored_file_size")
    private Long storedFileSize;

    public MediaDetail() {
    }

    public MediaDetail(Long mediaDetailSno, Media media, String azureId, String mediaUrl, String thumbnailUrl, String mediaType, String contentType, Long mediaSize, String mediaDetailDescription, Boolean isUploaded, Long createdAt, Long updatedAt, String originalFileName) {
        this.mediaDetailSno = mediaDetailSno;
        this.media = media;
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

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
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

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        if (this.isUploaded == null) {
            this.isUploaded = true;
        }
        if (this.isCompressed == null) {
            this.isCompressed = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}