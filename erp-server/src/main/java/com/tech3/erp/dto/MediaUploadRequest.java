package com.tech3.erp.dto;

import jakarta.validation.constraints.NotBlank;

public class MediaUploadRequest {

    @NotBlank(message = "Base64 file data is required")
    private String base64File;

    @NotBlank(message = "File name is required")
    private String fileName;

    @NotBlank(message = "Content type is required")
    private String contentType;

    @NotBlank(message = "Media type is required")
    private String mediaType;

    @NotBlank(message = "Container name is required")
    private String containerName;

    private String mediaDetailDescription;

    private Long mediaDetailSno;

    private Boolean replaceFile = false;

    private Boolean isCompressed = false;

    private Long originalFileSize;

    private Long compressedFileSize;

    private String folderPath;

    public MediaUploadRequest() {
    }

    public MediaUploadRequest(String base64File, String fileName, String contentType, String mediaType, String containerName, String mediaDetailDescription, Long mediaDetailSno, Boolean replaceFile, Boolean isCompressed, Long originalFileSize, Long compressedFileSize) {
        this.base64File = base64File;
        this.fileName = fileName;
        this.contentType = contentType;
        this.mediaType = mediaType;
        this.containerName = containerName;
        this.mediaDetailDescription = mediaDetailDescription;
        this.mediaDetailSno = mediaDetailSno;
        this.replaceFile = replaceFile;
        this.isCompressed = isCompressed;
        this.originalFileSize = originalFileSize;
        this.compressedFileSize = compressedFileSize;
    }

    public String getBase64File() {
        return base64File;
    }

    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getMediaDetailDescription() {
        return mediaDetailDescription;
    }

    public void setMediaDetailDescription(String mediaDetailDescription) {
        this.mediaDetailDescription = mediaDetailDescription;
    }

    public Long getMediaDetailSno() {
        return mediaDetailSno;
    }

    public void setMediaDetailSno(Long mediaDetailSno) {
        this.mediaDetailSno = mediaDetailSno;
    }

    public Boolean getReplaceFile() {
        return replaceFile;
    }

    public void setReplaceFile(Boolean replaceFile) {
        this.replaceFile = replaceFile;
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

    public Long getCompressedFileSize() {
        return compressedFileSize;
    }

    public void setCompressedFileSize(Long compressedFileSize) {
        this.compressedFileSize = compressedFileSize;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}
