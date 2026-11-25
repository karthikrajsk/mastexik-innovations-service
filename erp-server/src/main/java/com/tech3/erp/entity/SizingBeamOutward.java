package com.tech3.erp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sizing_beam_outward", schema = "masters")
public class SizingBeamOutward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sizing_beam_outward_no")
    private String sizingBeamOutwardNo;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "sizing_order_id")
    private Long sizingOrderId;

    @Column(name = "sizing_order_no")
    private String sizingOrderNo;

    @Column(name = "sizing_plan_id")
    private Long sizingPlanId;

    @Column(name = "sizing_plan_no")
    private String sizingPlanNo;

    @Column(name = "order_type_id")
    private Long orderTypeId;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "buyer_id")
    private Long buyerId;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "sizing_beam_outward_delivery", schema = "masters", joinColumns = @JoinColumn(name = "sizing_beam_outward_id"))
    @Column(name = "delivery_location")
    private List<String> deliveryAt = new ArrayList<>();

    @Column(name = "yarn_name")
    private String yarnName;

    @Column(name = "yarn_id")
    private Long yarnId;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "no_of_beams")
    private Integer noOfBeams;

    @Column(name = "total_beam_meters")
    private Double totalBeamMeters;

    @Column(name = "total_ends")
    private Integer totalEnds;

    @Column(name = "outward_date")
    private LocalDate outwardDate;

    @Column(name = "is_return", nullable = false, columnDefinition = "int default 0")
    private Integer isReturn = 0;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "return_no")
    private String returnNo;

    @OneToMany(mappedBy = "sizingBeamOutward", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SizingBeamOutwardDetail> beamDetails = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSizingBeamOutwardNo() {
        return sizingBeamOutwardNo;
    }

    public void setSizingBeamOutwardNo(String sizingBeamOutwardNo) {
        this.sizingBeamOutwardNo = sizingBeamOutwardNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getSizingOrderId() {
        return sizingOrderId;
    }

    public void setSizingOrderId(Long sizingOrderId) {
        this.sizingOrderId = sizingOrderId;
    }

    public String getSizingOrderNo() {
        return sizingOrderNo;
    }

    public void setSizingOrderNo(String sizingOrderNo) {
        this.sizingOrderNo = sizingOrderNo;
    }

    public Long getSizingPlanId() {
        return sizingPlanId;
    }

    public void setSizingPlanId(Long sizingPlanId) {
        this.sizingPlanId = sizingPlanId;
    }

    public String getSizingPlanNo() {
        return sizingPlanNo;
    }

    public void setSizingPlanNo(String sizingPlanNo) {
        this.sizingPlanNo = sizingPlanNo;
    }

    public Long getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Long orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public List<String> getDeliveryAt() {
        return deliveryAt;
    }

    public void setDeliveryAt(List<String> deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public String getYarnName() {
        return yarnName;
    }

    public void setYarnName(String yarnName) {
        this.yarnName = yarnName;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Integer getNoOfBeams() {
        return noOfBeams;
    }

    public void setNoOfBeams(Integer noOfBeams) {
        this.noOfBeams = noOfBeams;
    }

    public Double getTotalBeamMeters() {
        return totalBeamMeters;
    }

    public void setTotalBeamMeters(Double totalBeamMeters) {
        this.totalBeamMeters = totalBeamMeters;
    }

    public Integer getTotalEnds() {
        return totalEnds;
    }

    public void setTotalEnds(Integer totalEnds) {
        this.totalEnds = totalEnds;
    }

    public LocalDate getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(LocalDate outwardDate) {
        this.outwardDate = outwardDate;
    }

    public List<SizingBeamOutwardDetail> getBeamDetails() {
        return beamDetails;
    }

    public void setBeamDetails(List<SizingBeamOutwardDetail> beamDetails) {
        this.beamDetails = beamDetails;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }
}
