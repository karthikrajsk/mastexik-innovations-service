package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendor", schema = "masters")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_name", nullable = false, length = 100)
    private String vendorName;

    @Column(name = "vendor_type_id")
    private String vendorTypeId;

    @Column(nullable = false)
    private String gstno;

    @Column(nullable = false)
    private String pancard;

    @Column(nullable = false)
    private String mobileno;

    private String email;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_number")
    private String contactNumber;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "vendor_address",
        schema = "masters",
        joinColumns = @JoinColumn(name = "vendor_id"),
        inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    private List<Address> addresses = new ArrayList<>();

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "is_internal_vendor")
    private Integer isInternalVendor;

    @Column(name = "payment_terms_id")
    private Long paymentTermsId;

    @Column(name = "gst_document_media_detail_sno")
    private Long gstDocumentMediaDetailSno;

    @Column(name = "gst_document_media_url", length = 2048)
    private String gstDocumentMediaUrl;

    @Column(name = "gst_document_file_name", length = 512)
    private String gstDocumentFileName;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendorBankDetail> bankDetails = new ArrayList<>();

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VendorCertificate> certificates = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getVendorTypeId() { return vendorTypeId; }
    public void setVendorTypeId(String vendorTypeId) { this.vendorTypeId = vendorTypeId; }

    public String getGstno() { return gstno; }
    public void setGstno(String gstno) { this.gstno = gstno; }

    public String getPancard() { return pancard; }
    public void setPancard(String pancard) { this.pancard = pancard; }

    public String getMobileno() { return mobileno; }
    public void setMobileno(String mobileno) { this.mobileno = mobileno; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public Integer getIsInternalVendor() { return isInternalVendor; }
    public void setIsInternalVendor(Integer isInternalVendor) { this.isInternalVendor = isInternalVendor; }

    public Long getPaymentTermsId() { return paymentTermsId; }
    public void setPaymentTermsId(Long paymentTermsId) { this.paymentTermsId = paymentTermsId; }

    public Long getGstDocumentMediaDetailSno() { return gstDocumentMediaDetailSno; }
    public void setGstDocumentMediaDetailSno(Long gstDocumentMediaDetailSno) { this.gstDocumentMediaDetailSno = gstDocumentMediaDetailSno; }

    public String getGstDocumentMediaUrl() { return gstDocumentMediaUrl; }
    public void setGstDocumentMediaUrl(String gstDocumentMediaUrl) { this.gstDocumentMediaUrl = gstDocumentMediaUrl; }

    public String getGstDocumentFileName() { return gstDocumentFileName; }
    public void setGstDocumentFileName(String gstDocumentFileName) { this.gstDocumentFileName = gstDocumentFileName; }

    public List<VendorBankDetail> getBankDetails() { return bankDetails; }
    public void setBankDetails(List<VendorBankDetail> bankDetails) { this.bankDetails = bankDetails; }

    public List<VendorCertificate> getCertificates() { return certificates; }
    public void setCertificates(List<VendorCertificate> certificates) { this.certificates = certificates; }
}
