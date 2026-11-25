package com.tech3.erp.dto;

import jakarta.validation.constraints.NotNull;

public class MediaUpdateRequest {

    @NotNull(message = "Media detail SNO is required")
    private Long mediaDetailSno;

    private String base64File;

    private String fileName;

    private String contentType;

    private String mediaDetailDescription;

    private Boolean replaceFile = false;

    public MediaUpdateRequest() {
    }

    public MediaUpdateRequest(Long mediaDetailSno, String base64File, String fileName, String contentType, String mediaDetailDescription, Boolean replaceFile) {
        this.mediaDetailSno = mediaDetailSno;
        this.base64File = base64File;
        this.fileName = fileName;
        this.contentType = contentType;
        this.mediaDetailDescription = mediaDetailDescription;
        this.replaceFile = replaceFile;
    }

    public Long getMediaDetailSno() {
        return mediaDetailSno;
    }

    public void setMediaDetailSno(Long mediaDetailSno) {
        this.mediaDetailSno = mediaDetailSno;
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

    public String getMediaDetailDescription() {
        return mediaDetailDescription;
    }

    public void setMediaDetailDescription(String mediaDetailDescription) {
        this.mediaDetailDescription = mediaDetailDescription;
    }

    public Boolean getReplaceFile() {
        return replaceFile;
    }

    public void setReplaceFile(Boolean replaceFile) {
        this.replaceFile = replaceFile;
    }
}
