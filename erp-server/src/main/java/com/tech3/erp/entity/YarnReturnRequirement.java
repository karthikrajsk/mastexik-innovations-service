package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "yarn_return_requirement", schema = "masters")
public class YarnReturnRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yarn_return_id", nullable = false)
    @JsonBackReference("yarn-return-requirements")
    private YarnReturn yarnReturn;

    @Column(name = "yarn_name", nullable = false)
    private String yarnName;

    @Column(name = "actual_yarn_id")
    private Long actualYarnId;

    @Column(name = "actual_yarn_name")
    private String actualYarnName;

    @Column(name = "greige_yarn_id")
    private Long greigeYarnId;

    @Column(name = "greige_yarn_name")
    private String greigeYarnName;

    @Column(name = "finished_yarn_id")
    private Long finishedYarnId;

    @Column(name = "finished_yarn_name")
    private String finishedYarnName;

    @Column(name = "issued_qty")
    private Double issuedQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public YarnReturn getYarnReturn() {
        return yarnReturn;
    }

    public void setYarnReturn(YarnReturn yarnReturn) {
        this.yarnReturn = yarnReturn;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Long getActualYarnId() {
        return actualYarnId;
    }

    public void setActualYarnId(Long actualYarnId) {
        this.actualYarnId = actualYarnId;
    }

    public String getActualYarnName() {
        return actualYarnName;
    }

    public void setActualYarnName(String actualYarnName) {
        this.actualYarnName = actualYarnName;
    }

    public Long getGreigeYarnId() {
        return greigeYarnId;
    }

    public void setGreigeYarnId(Long greigeYarnId) {
        this.greigeYarnId = greigeYarnId;
    }

    public String getGreigeYarnName() {
        return greigeYarnName;
    }

    public void setGreigeYarnName(String greigeYarnName) {
        this.greigeYarnName = greigeYarnName;
    }

    public Long getFinishedYarnId() {
        return finishedYarnId;
    }

    public void setFinishedYarnId(Long finishedYarnId) {
        this.finishedYarnId = finishedYarnId;
    }

    public String getFinishedYarnName() {
        return finishedYarnName;
    }

    public void setFinishedYarnName(String finishedYarnName) {
        this.finishedYarnName = finishedYarnName;
    }

    public Double getIssuedQty() {
        return issuedQty;
    }

    public void setIssuedQty(Double issuedQty) {
        this.issuedQty = issuedQty;
    }
}
