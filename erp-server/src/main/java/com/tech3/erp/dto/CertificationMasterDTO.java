package com.tech3.erp.dto;

import com.tech3.erp.entity.CertificationMaster;
import com.tech3.erp.entity.MediaDetail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.stream.Collectors;

public class CertificationMasterDTO {
    private Long id;

    @NotBlank(message = "Certification Number is required")
    @Size(min = 1, max = 100, message = "Certification Number must be between 1 and 100 characters")
    private String certificationNo;

    @NotBlank(message = "Certification Name is required")
    @Size(min = 1, max = 255, message = "Certification Name must be between 1 and 255 characters")
    private String certificationName;

    @NotNull(message = "Logo Media Detail SNO is required")
    private Long logoMediaDetailSno;

    private Long logoMediaSno;

    private String logoUrl;

    private MediaDetailResponse logoUploadDetail;

    private boolean activeFlag = true;

    public CertificationMasterDTO() {
    }

    public CertificationMasterDTO(Long id, String certificationNo, String certificationName, Long logoMediaSno, boolean activeFlag) {
        this.id = id;
        this.certificationNo = certificationNo;
        this.certificationName = certificationName;
        this.logoMediaSno = logoMediaSno;
        this.activeFlag = activeFlag;
    }

    public CertificationMasterDTO(CertificationMaster entity) {
        this.id = entity.getId();
        this.certificationNo = entity.getCertificationNo();
        this.certificationName = entity.getCertificationName();
        this.logoMediaSno = entity.getLogoMediaSno();
        this.activeFlag = entity.getActiveFlag();
        
        if (entity.getLogoMedia() != null && entity.getLogoMedia().getMediaDetails() != null) {
            MediaDetail firstDetail = entity.getLogoMedia().getMediaDetails().stream()
                    .filter(md -> md.getIsUploaded() != null && md.getIsUploaded())
                    .findFirst()
                    .orElse(null);
            
            if (firstDetail != null) {
                this.logoUploadDetail = new MediaDetailResponse(
                    firstDetail.getMediaDetailSno(),
                    firstDetail.getMedia().getMediaSno(),
                    firstDetail.getAzureId(),
                    firstDetail.getMediaUrl(),
                    firstDetail.getThumbnailUrl(),
                    firstDetail.getMediaType(),
                    firstDetail.getContentType(),
                    firstDetail.getMediaSize(),
                    firstDetail.getMediaDetailDescription(),
                    firstDetail.getIsUploaded(),
                    firstDetail.getCreatedAt(),
                    firstDetail.getUpdatedAt(),
                    firstDetail.getOriginalFileName()
                );
                this.logoUrl = firstDetail.getMediaUrl();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertificationNo() {
        return certificationNo;
    }

    public void setCertificationNo(String certificationNo) {
        this.certificationNo = certificationNo;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public Long getLogoMediaDetailSno() {
        return logoMediaDetailSno;
    }

    public void setLogoMediaDetailSno(Long logoMediaDetailSno) {
        this.logoMediaDetailSno = logoMediaDetailSno;
    }

    public Long getLogoMediaSno() {
        return logoMediaSno;
    }

    public void setLogoMediaSno(Long logoMediaSno) {
        this.logoMediaSno = logoMediaSno;
    }

    public boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public MediaDetailResponse getLogoUploadDetail() {
        return logoUploadDetail;
    }

    public void setLogoUploadDetail(MediaDetailResponse logoUploadDetail) {
        this.logoUploadDetail = logoUploadDetail;
    }
}
