package com.tech3.erp.dto;

import java.util.List;

public class MediaResponse {

    private Long mediaSno;

    private String containerName;

    private List<MediaDetailResponse> mediaDetails;

    private Long createdAt;

    private Long updatedAt;

    public MediaResponse() {
    }

    public MediaResponse(Long mediaSno, String containerName, List<MediaDetailResponse> mediaDetails, Long createdAt, Long updatedAt) {
        this.mediaSno = mediaSno;
        this.containerName = containerName;
        this.mediaDetails = mediaDetails;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getMediaSno() {
        return mediaSno;
    }

    public void setMediaSno(Long mediaSno) {
        this.mediaSno = mediaSno;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public List<MediaDetailResponse> getMediaDetails() {
        return mediaDetails;
    }

    public void setMediaDetails(List<MediaDetailResponse> mediaDetails) {
        this.mediaDetails = mediaDetails;
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
}
