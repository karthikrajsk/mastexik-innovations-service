package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "media", schema = "masters")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "media_sno")
    private Long mediaSno;

    @Column(name = "container_name", nullable = false)
    private String containerName;

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MediaDetail> mediaDetails = new ArrayList<>();

    @Column(name = "created_at", insertable = true, updatable = false)
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

    public Media() {
    }

    public Media(Long mediaSno, String containerName, List<MediaDetail> mediaDetails, Long createdAt, Long updatedAt) {
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

    public List<MediaDetail> getMediaDetails() {
        return mediaDetails;
    }

    public void setMediaDetails(List<MediaDetail> mediaDetails) {
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

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}