package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockItemTransferDTO {

    private Long id;
    private Long stockTransferId;

    @JsonProperty("itemId")
    private Long itemId;

    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("sNo")
    private Integer sNo;

    @JsonProperty("noOfBags")
    private Integer noOfBags;

    @JsonProperty("kgsPerBag")
    private BigDecimal kgsPerBag;

    @JsonProperty("acceptedQty")
    private BigDecimal acceptedQty;

    @JsonProperty("issuingBags")
    private Integer issuingBags;

    @JsonProperty("lotNo")
    private String lotNo;

    public StockItemTransferDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStockTransferId() { return stockTransferId; }
    public void setStockTransferId(Long stockTransferId) { this.stockTransferId = stockTransferId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getSNo() { return sNo; }
    public void setSNo(Integer sNo) { this.sNo = sNo; }

    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }

    public BigDecimal getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(BigDecimal kgsPerBag) { this.kgsPerBag = kgsPerBag; }

    public BigDecimal getAcceptedQty() { return acceptedQty; }
    public void setAcceptedQty(BigDecimal acceptedQty) { this.acceptedQty = acceptedQty; }

    public Integer getIssuingBags() { return issuingBags; }
    public void setIssuingBags(Integer issuingBags) { this.issuingBags = issuingBags; }

    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }
}
