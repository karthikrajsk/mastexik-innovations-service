package com.tech3.erp.dto;

public class VendorDocumentDTO {

    private Long mediaDetailSno;
    private String mediaUrl;
    private String fileName;

    public VendorDocumentDTO() {}

    public VendorDocumentDTO(Long mediaDetailSno, String mediaUrl, String fileName) {
        this.mediaDetailSno = mediaDetailSno;
        this.mediaUrl = mediaUrl;
        this.fileName = fileName;
    }

    public Long getMediaDetailSno() { return mediaDetailSno; }
    public void setMediaDetailSno(Long mediaDetailSno) { this.mediaDetailSno = mediaDetailSno; }

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
