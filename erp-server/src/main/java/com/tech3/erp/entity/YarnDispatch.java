package com.tech3.erp.entity;

import com.tech3.erp.util.ProcessTypeUtil;

import jakarta.persistence.*;

@Entity
@Table(name = "yarn_dispatch", schema = "masters")
@PrimaryKeyJoinColumn(name = "id")
public class YarnDispatch extends FabricDispatchForDyeing {

    // Yarn-specific fields can be added here
    @Column(name = "yarn_type")
    private String yarnType;
    
    @Column(name = "yarn_count")
    private String yarnCount;
    
    @Column(name = "yarn_twist")
    private String yarnTwist;

    // Default Constructor
    public YarnDispatch() {
        this.setProcessType(ProcessTypeUtil.YARN);
    }

    // Getters and Setters for yarn-specific fields
    public String getYarnType() { return yarnType; }
    public void setYarnType(String yarnType) { this.yarnType = yarnType; }

    public String getYarnCount() { return yarnCount; }
    public void setYarnCount(String yarnCount) { this.yarnCount = yarnCount; }

    public String getYarnTwist() { return yarnTwist; }
    public void setYarnTwist(String yarnTwist) { this.yarnTwist = yarnTwist; }
}