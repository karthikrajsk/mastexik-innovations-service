package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendor_certificates", schema = "masters")
public class VendorCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;

    @Column(name = "certificate_no")
    private String certificateNo;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "certificate_type")
    private String type;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "vendor_certificate_files",
        schema = "masters",
        joinColumns = @JoinColumn(name = "certificate_id")
    )
    private List<VendorDocument> files = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Vendor getVendor() { return vendor; }
    public void setVendor(Vendor vendor) { this.vendor = vendor; }

    public String getCertificateNo() { return certificateNo; }
    public void setCertificateNo(String certificateNo) { this.certificateNo = certificateNo; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public List<VendorDocument> getFiles() { return files; }
    public void setFiles(List<VendorDocument> files) { this.files = files; }
}
