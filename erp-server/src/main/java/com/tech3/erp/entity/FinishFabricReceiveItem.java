package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "finish_fabric_receive_items", schema = "masters")
public class FinishFabricReceiveItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "finished_fabric_code_quality")
    private String finishedFabricCodeQuality;

    @Column(name = "piece_roll_no")
    private String pieceRollNo;

    @Column(name = "meter")
    private Double meter;

    @Column(name = "yards")
    private Double yards;

    @Column(name = "net_weight")
    private Double netWeight;

    @Column(name = "average_weight")
    private Double averageWeight;

    @Column(name = "grade_id")
    private Long gradeId;

    @Column(name = "warehouse_id")
    private Long warehouseId;

    @Column(name = "fabric_master_id")
    private Long fabricMasterId;

    @Column(name = "active_flag")
    private Boolean activeFlag = true;

    @ManyToOne
    @JoinColumn(name = "finish_fabric_receive_id")
    @JsonIgnore
    private FinishFabricReceive finishFabricReceive;

    @OneToMany(mappedBy = "finishFabricReceiveItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<PieceEntry> pieceEntries;

    public FinishFabricReceiveItem() {}

    public FinishFabricReceiveItem(Long id, String finishedFabricCodeQuality, String pieceRollNo, 
                                  Double meter, Double yards, Double netWeight, Double averageWeight,
                                  Long gradeId, Long warehouseId, Long fabricMasterId,
                                  Boolean activeFlag, FinishFabricReceive finishFabricReceive) {
        this.id = id;
        this.finishedFabricCodeQuality = finishedFabricCodeQuality;
        this.pieceRollNo = pieceRollNo;
        this.meter = meter;
        this.yards = yards;
        this.netWeight = netWeight;
        this.averageWeight = averageWeight;
        this.gradeId = gradeId;
        this.warehouseId = warehouseId;
        this.fabricMasterId = fabricMasterId;
        this.activeFlag = activeFlag;
        this.finishFabricReceive = finishFabricReceive;
    }

    public Long getId() {
        return id;
    }

    public String getFinishedFabricCodeQuality() {
        return finishedFabricCodeQuality;
    }

    public String getPieceRollNo() {
        return pieceRollNo;
    }

    public Double getMeter() {
        return meter;
    }

    public Double getYards() {
        return yards;
    }

    public Double getNetWeight() {
        return netWeight;
    }

    public Double getAverageWeight() {
        return averageWeight;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public Long getFabricMasterId() {
        return fabricMasterId;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public FinishFabricReceive getFinishFabricReceive() {
        return finishFabricReceive;
    }

    public List<PieceEntry> getPieceEntries() {
        return pieceEntries;
    }

    public void setPieceEntries(List<PieceEntry> pieceEntries) {
        this.pieceEntries = pieceEntries;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFinishedFabricCodeQuality(String finishedFabricCodeQuality) {
        this.finishedFabricCodeQuality = finishedFabricCodeQuality;
    }

    public void setPieceRollNo(String pieceRollNo) {
        this.pieceRollNo = pieceRollNo;
    }

    public void setMeter(Double meter) {
        this.meter = meter;
    }

    public void setYards(Double yards) {
        this.yards = yards;
    }

    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    public void setAverageWeight(Double averageWeight) {
        this.averageWeight = averageWeight;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setFabricMasterId(Long fabricMasterId) {
        this.fabricMasterId = fabricMasterId;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public void setFinishFabricReceive(FinishFabricReceive finishFabricReceive) {
        this.finishFabricReceive = finishFabricReceive;
    }
}

