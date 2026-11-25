package com.tech3.erp.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.entity.VendorBankDetail;
import com.tech3.erp.entity.VendorCertificate;
import com.tech3.erp.entity.VendorDocument;
import com.tech3.erp.util.AddressListDeserializer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VendorDTO {
    private Long id;
    private String vendorName;
    private String vendorTypeId;
    private List<Long> vendorTypeIds;
    private String gstno;
    private String pancard;
    private String mobileno;
    private String email;
    private String contactPerson;
    private String contactNumber;
    private Boolean activeFlag;
    private String photoUrl;
    private Integer isInternalVendor;
    private Long paymentTermsId;
    @JsonDeserialize(using = AddressListDeserializer.class)
    private List<AddressDTO> address;
    private VendorDocumentDTO gstDocument;
    private Boolean clearGstDocument;
    private List<VendorBankDetailDTO> bankDetails;
    private List<VendorCertificateDTO> certificates;

    public VendorDTO() {}

    public VendorDTO(Vendor vendor) {
        this.id = vendor.getId();
        this.vendorName = vendor.getVendorName();
        this.vendorTypeId = vendor.getVendorTypeId();
        // Convert comma-separated string to list for multi-select support
        this.vendorTypeIds = parseVendorTypeIds(vendor.getVendorTypeId());
        this.gstno = vendor.getGstno();
        this.pancard = vendor.getPancard();
        this.mobileno = vendor.getMobileno();
        this.email = vendor.getEmail();
        this.contactPerson = vendor.getContactPerson();
        this.contactNumber = vendor.getContactNumber();
        this.activeFlag = vendor.getActiveFlag();
        this.photoUrl = vendor.getPhotoUrl();
        this.isInternalVendor = vendor.getIsInternalVendor();
        this.paymentTermsId = vendor.getPaymentTermsId();
        if (vendor.getGstDocumentMediaDetailSno() != null
                || vendor.getGstDocumentMediaUrl() != null
                || vendor.getGstDocumentFileName() != null) {
            this.gstDocument = new VendorDocumentDTO(
                    vendor.getGstDocumentMediaDetailSno(),
                    vendor.getGstDocumentMediaUrl(),
                    vendor.getGstDocumentFileName());
        }
        this.bankDetails = vendor.getBankDetails().stream()
                .map(bank -> new VendorBankDetailDTO(
                        bank.getId(),
                        bank.getBankName(),
                        bank.getAccountNo(),
                        bank.getIfscCode()))
                .collect(Collectors.toList());
        this.certificates = vendor.getCertificates().stream()
                .map(certificate -> new VendorCertificateDTO(
                        certificate.getId(),
                        certificate.getCertificateNo(),
                        certificate.getExpiryDate(),
                        certificate.getType(),
                        certificate.getFiles().stream()
                                .map(file -> new VendorDocumentDTO(
                                        file.getMediaDetailSno(),
                                        file.getMediaUrl(),
                                        file.getFileName()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
        if (vendor.getAddress() != null) {
            this.address = new ArrayList<>();
            this.address.add(new AddressDTO(vendor.getAddress()));
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    
    public String getVendorTypeId() { return vendorTypeId; }
    public void setVendorTypeId(String vendorTypeId) { this.vendorTypeId = vendorTypeId; }
    
    public List<Long> getVendorTypeIds() { return vendorTypeIds; }
    public void setVendorTypeIds(List<Long> vendorTypeIds) { this.vendorTypeIds = vendorTypeIds; }
    
    // Utility method to parse comma-separated vendor type IDs
    private List<Long> parseVendorTypeIds(String vendorTypeIdStr) {
        if (vendorTypeIdStr == null || vendorTypeIdStr.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return Arrays.stream(vendorTypeIdStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            // Handle backward compatibility for single values
            try {
                return Arrays.asList(Long.parseLong(vendorTypeIdStr.trim()));
            } catch (NumberFormatException ex) {
                return new ArrayList<>();
            }
        }
    }

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

    public Boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Boolean activeFlag) { this.activeFlag = activeFlag; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public Integer getIsInternalVendor() { return isInternalVendor; }
    public void setIsInternalVendor(Integer isInternalVendor) { this.isInternalVendor = isInternalVendor; }
    
    public Long getPaymentTermsId() { return paymentTermsId; }
    public void setPaymentTermsId(Long paymentTermsId) { this.paymentTermsId = paymentTermsId; }
    
    public List<AddressDTO> getAddress() { return address; }
    public void setAddress(List<AddressDTO> address) { this.address = address; }

    public VendorDocumentDTO getGstDocument() { return gstDocument; }
    public void setGstDocument(VendorDocumentDTO gstDocument) { this.gstDocument = gstDocument; }

    public Boolean getClearGstDocument() { return clearGstDocument; }
    public void setClearGstDocument(Boolean clearGstDocument) { this.clearGstDocument = clearGstDocument; }

    public List<VendorBankDetailDTO> getBankDetails() { return bankDetails; }
    public void setBankDetails(List<VendorBankDetailDTO> bankDetails) { this.bankDetails = bankDetails; }

    public List<VendorCertificateDTO> getCertificates() { return certificates; }
    public void setCertificates(List<VendorCertificateDTO> certificates) { this.certificates = certificates; }
}