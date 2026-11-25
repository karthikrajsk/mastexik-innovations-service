package com.tech3.erp.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "yarn_master", schema = "masters")
public class YarnMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "yarn_name", unique = true, nullable = false, length = 50)
    @NotBlank(message = "Yarn name is required")
    @Size(max = 50, message = "Yarn name must be up to 50 characters")
    private String yarnName;

    @Column(name = "count_sno", nullable = false)
    @NotNull(message = "Count SNO is required")
    private Short countSno;

    @Column(name = "unit_sno", nullable = false)
    private Short unitSno;

    @Column(name = "types", nullable = false, length = 50)
    @NotBlank(message = "Types are required")
    @Size(max = 50, message = "Types must be up to 50 characters")
    private String types;
    
    @Column(name = "content", length = 50)
    @Size(max = 50, message = "Content must be up to 50 characters") // No @NotBlank since it's optional
    private String content;
    
    
    @Column(name = "color_sno")
    private Short colorSno;

    @Column(name = "conversion", nullable = false)
    @NotNull(message = "Conversion value is required")
    private Double conversion;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;
    
    @Column(name = "ply_sno")
    private Short plySno;
    
    @Column(name = "filaments", length = 50)
    @Size(max = 50, message = "Filaments must be up to 50 characters")
    private String filaments;
    
    
    @Column(name = "tax_type")
    private Short taxType; // 1=CGST/SGST, 2=IGST
    
    @Column(name = "gst_percent", precision = 5, scale = 2)
    @NotNull(message = "GST percent is required")
    private BigDecimal gstPercent;
    
    @Column(name = "igst", precision = 5, scale = 2)
    private BigDecimal igst;
    
    @Column(name = "cgst", precision = 5, scale = 2)
    private BigDecimal cgst;
    
    @Column(name = "sgst", precision = 5, scale = 2)
    private BigDecimal sgst;

    // Default constructor
    public YarnMaster() {}

    // Constructor with parameters
    public YarnMaster(String yarnName, Short countSno, Short unitSno, String types, 
    		Double conversion, boolean activeFlag, String content, Short colorSno,
    		Short plySno, String filaments) {
        this.yarnName = yarnName;
        this.countSno = countSno;
        this.unitSno = unitSno;
        this.types = types;
        this.conversion = conversion;
        this.activeFlag = activeFlag;
        this.content = content;
        this.colorSno = colorSno;
        this.plySno = plySno;
        this.filaments = filaments;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getYarnName() { return yarnName; }
    public void setYarnName(String yarnName) { this.yarnName = yarnName; }

    public Short getCountSno() { return countSno; }
    public void setCountSno(Short countSno) { this.countSno = countSno; }

    public Short getUnitSno() { return unitSno; }
    public void setUnitSno(Short unitSno) { this.unitSno = unitSno; }
    
    public Short getColorSno() { return colorSno; }
    public void setColorSno(Short colorSno) { this.colorSno = colorSno; }

    public String getTypes() { return types; }
    public void setTypes(String types) { this.types = types; }

    public Double getConversion() { return conversion; }
    public void setConversion(Double conversion) { this.conversion = conversion; }

    public boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public Short getPlySno() { return plySno; }
    public void setPlySno(Short plySno) { this.plySno = plySno; }

    public String getFilaments() { return filaments; }
    public void setFilaments(String filaments) { this.filaments = filaments; }
    
    public Short getTaxType() { return taxType; }
    public void setTaxType(Short taxType) { this.taxType = taxType; }
    
    public BigDecimal getGstPercent() { return gstPercent; }
    public void setGstPercent(BigDecimal gstPercent) { this.gstPercent = gstPercent; }

    public BigDecimal getIgst() { return igst; }
    public void setIgst(BigDecimal igst) { this.igst = igst; }

    public BigDecimal getCgst() { return cgst; }
    public void setCgst(BigDecimal cgst) { this.cgst = cgst; }

    public BigDecimal getSgst() { return sgst; }
    public void setSgst(BigDecimal sgst) { this.sgst = sgst; }
}