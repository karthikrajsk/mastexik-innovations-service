package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class VendorCertificateDTO {

    private Long id;
    private String certificateNo;
    private LocalDate expiryDate;
    private String type;
    private List<VendorDocumentDTO> files;

    public VendorCertificateDTO() {}

    public VendorCertificateDTO(Long id, String certificateNo, LocalDate expiryDate, String type, List<VendorDocumentDTO> files) {
        this.id = id;
        this.certificateNo = certificateNo;
        this.expiryDate = expiryDate;
        this.type = type;
        this.files = files;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCertificateNo() { return certificateNo; }
    public void setCertificateNo(String certificateNo) { this.certificateNo = certificateNo; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<VendorDocumentDTO> getFiles() { return files; }
    public void setFiles(List<VendorDocumentDTO> files) { this.files = files; }
}
