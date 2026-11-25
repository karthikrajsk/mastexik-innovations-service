package com.tech3.erp.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "flange_issue_item", schema = "masters")
public class FlangeIssueItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flange_issue_id", nullable = false)
    @JsonBackReference
    private FlangeIssue flangeIssue;

    private Long flangeId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlangeIssue getFlangeIssue() {
        return flangeIssue;
    }

    public void setFlangeIssue(FlangeIssue flangeIssue) {
        this.flangeIssue = flangeIssue;
    }

    public Long getFlangeId() {
        return flangeId;
    }

    public void setFlangeId(Long flangeId) {
        this.flangeId = flangeId;
    }
}