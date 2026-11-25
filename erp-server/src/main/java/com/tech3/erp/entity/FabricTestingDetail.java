package com.tech3.erp.entity;

import java.math.BigDecimal;

import com.tech3.erp.dto.FabricTestingDetailDTO;
import com.tech3.erp.dto.FabricWeftDetailDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fabric_testing_detail", schema = "masters")
public class FabricTestingDetail {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String testingParameter;
    private String stdValue;

    @ManyToOne
    @JoinColumn(name = "fabric_id", nullable = false)
    private WovenFabricMaster wovenFabricMaster;

    public FabricTestingDetail() {}
    
    public FabricTestingDetail(FabricTestingDetailDTO dto) {
    	this.testingParameter = dto.getTestingParameter();
        this.stdValue = dto.getStdValue();
    }
    
 // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestingParameter() {
        return testingParameter;
    }

    public void setTestingParameter(String testingParameter) {
        this.testingParameter = testingParameter;
    }

    public String getStdValue() {
        return stdValue;
    }

    public void setStdValue(String stdValue) {
        this.stdValue = stdValue;
    }
    
    public WovenFabricMaster getFabricMaster() { 
    	return wovenFabricMaster; 
    }
    
    public void setFabricMaster(WovenFabricMaster wovenFabricMaster) { 
    	this.wovenFabricMaster = wovenFabricMaster; 
    }

}
