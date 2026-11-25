package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sales_return_beam", schema = "masters")
public class SalesReturnBeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_return_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sales_return_beam"))
    @JsonBackReference("salesReturnBeams")
    private SalesReturn salesReturn;

    @Column(name = "beam_outward_no", length = 100)
    private String beamOutwardNo;

    @Column(name = "outward_date")
    private LocalDate outwardDate;

    @Column(name = "weaving_contract_no", length = 100)
    private String weavingContractNo;

    @Column(name = "weaver_name", length = 255)
    private String weaverName;

    @Column(name = "sizing_plan_no", length = 100)
    private String sizingPlanNo;

    @Column(name = "sales_order_no", length = 100)
    private String salesOrderNo;

    @Column(name = "flange_no", length = 100)
    private String flangeNo;

    @Column(name = "sized_beam_no", length = 100)
    private String sizedBeamNo;

    @Column(name = "beam_meters", precision = 19, scale = 3)
    private BigDecimal beamMeters;

    @Column(name = "expected_fabric_meter", precision = 19, scale = 3)
    private BigDecimal expectedFabricMeter;

    @Column(name = "total_beams", precision = 19, scale = 3)
    private BigDecimal totalBeams;

    @Column(name = "weaver_id")
    private Long weaverId;

    @Column(name = "fabric_panel_width")
    private String fabricPanelWidth;

    @Column(name = "is_return")
    private Integer isReturn = 0;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "return_no", length = 100)
    private String returnNo;

    @Column(name = "return_weaver_id")
    private Long returnWeaverId;

    @Column(name = "shrinkage", precision = 19, scale = 3)
    private BigDecimal shrinkage;

    @Column(name = "beam_outward_id")
    private Long beamOutwardId;

    @Column(name = "beam_inward_id")
    private Long beamInwardId;

    @Column(name = "is_outwarded", nullable = false, columnDefinition = "int default 0")
    private Integer isOutwarded = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public SalesReturn getSalesReturn() { return salesReturn; }
    public void setSalesReturn(SalesReturn salesReturn) { this.salesReturn = salesReturn; }

    public String getBeamOutwardNo() { return beamOutwardNo; }
    public void setBeamOutwardNo(String beamOutwardNo) { this.beamOutwardNo = beamOutwardNo; }

    public LocalDate getOutwardDate() { return outwardDate; }
    public void setOutwardDate(LocalDate outwardDate) { this.outwardDate = outwardDate; }

    public String getWeavingContractNo() { return weavingContractNo; }
    public void setWeavingContractNo(String weavingContractNo) { this.weavingContractNo = weavingContractNo; }

    public String getWeaverName() { return weaverName; }
    public void setWeaverName(String weaverName) { this.weaverName = weaverName; }

    public String getSizingPlanNo() { return sizingPlanNo; }
    public void setSizingPlanNo(String sizingPlanNo) { this.sizingPlanNo = sizingPlanNo; }

    public String getSalesOrderNo() { return salesOrderNo; }
    public void setSalesOrderNo(String salesOrderNo) { this.salesOrderNo = salesOrderNo; }

    public String getFlangeNo() { return flangeNo; }
    public void setFlangeNo(String flangeNo) { this.flangeNo = flangeNo; }

    public String getSizedBeamNo() { return sizedBeamNo; }
    public void setSizedBeamNo(String sizedBeamNo) { this.sizedBeamNo = sizedBeamNo; }

    public BigDecimal getBeamMeters() { return beamMeters; }
    public void setBeamMeters(BigDecimal beamMeters) { this.beamMeters = beamMeters; }

    public BigDecimal getExpectedFabricMeter() { return expectedFabricMeter; }
    public void setExpectedFabricMeter(BigDecimal expectedFabricMeter) { this.expectedFabricMeter = expectedFabricMeter; }

    public BigDecimal getTotalBeams() { return totalBeams; }
    public void setTotalBeams(BigDecimal totalBeams) { this.totalBeams = totalBeams; }

    public Long getWeaverId() { return weaverId; }
    public void setWeaverId(Long weaverId) { this.weaverId = weaverId; }

    public String getFabricPanelWidth() { return fabricPanelWidth; }
    public void setFabricPanelWidth(String fabricPanelWidth) { this.fabricPanelWidth = fabricPanelWidth; }

    public Integer getIsReturn() { return isReturn; }
    public void setIsReturn(Integer isReturn) { this.isReturn = isReturn; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getReturnNo() { return returnNo; }
    public void setReturnNo(String returnNo) { this.returnNo = returnNo; }

    public Long getReturnWeaverId() { return returnWeaverId; }
    public void setReturnWeaverId(Long returnWeaverId) { this.returnWeaverId = returnWeaverId; }

    public BigDecimal getShrinkage() { return shrinkage; }
    public void setShrinkage(BigDecimal shrinkage) { this.shrinkage = shrinkage; }

    public Long getBeamOutwardId() { return beamOutwardId; }
    public void setBeamOutwardId(Long beamOutwardId) { this.beamOutwardId = beamOutwardId; }

    public Long getBeamInwardId() { return beamInwardId; }
    public void setBeamInwardId(Long beamInwardId) { this.beamInwardId = beamInwardId; }

    public Integer getIsOutwarded() { return isOutwarded != null ? isOutwarded : 0; }
    public void setIsOutwarded(Integer isOutwarded) { this.isOutwarded = isOutwarded; }
}
