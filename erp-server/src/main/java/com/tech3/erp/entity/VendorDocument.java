package com.tech3.erp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class VendorDocument {

    @Column(name = "media_detail_sno")
    private Long mediaDetailSno;

    @Column(name = "media_url", length = 2048)
    private String mediaUrl;

    @Column(name = "file_name", length = 512)
    private String fileName;

    public Long getMediaDetailSno() { return mediaDetailSno; }
    public void setMediaDetailSno(Long mediaDetailSno) { this.mediaDetailSno = mediaDetailSno; }

    public String getMediaUrl() { return mediaUrl; }
    public void setMediaUrl(String mediaUrl) { this.mediaUrl = mediaUrl; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
