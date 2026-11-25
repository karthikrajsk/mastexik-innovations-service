package com.tech3.erp.dto;

import com.tech3.erp.entity.ItemGoodsItemEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemGoodsItemDto {

    private Long id;
    private Long openingBalanceId;

    @JsonProperty("sNo")
    private Integer sNo;

    @JsonProperty("itemId")
    private Long itemId;

    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("noOfBags")
    private Integer noOfBags;

    @JsonProperty("kgsPerBag")
    private BigDecimal kgsPerBag;

    @JsonProperty("acceptedQty")
    private BigDecimal acceptedQty;

    @JsonProperty("lotNo")
    private String lotNo;

    public ItemGoodsItemDto() {}

    public ItemGoodsItemDto(ItemGoodsItemEntity entity) {
        this.id = entity.getId();
        this.openingBalanceId = entity.getOpeningBalanceId();
        this.sNo = entity.getSNo();
        this.itemId = entity.getItemId();
        this.itemName = entity.getItemName();
        this.noOfBags = entity.getNoOfBags();
        this.kgsPerBag = entity.getKgsPerBag();
        this.acceptedQty = entity.getAcceptedQty();
        this.lotNo = entity.getLotNo();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOpeningBalanceId() { return openingBalanceId; }
    public void setOpeningBalanceId(Long openingBalanceId) { this.openingBalanceId = openingBalanceId; }

    public Integer getSNo() { return sNo; }
    public void setSNo(Integer sNo) { this.sNo = sNo; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }

    public BigDecimal getKgsPerBag() { return kgsPerBag; }
    public void setKgsPerBag(BigDecimal kgsPerBag) { this.kgsPerBag = kgsPerBag; }

    public BigDecimal getAcceptedQty() { return acceptedQty; }
    public void setAcceptedQty(BigDecimal acceptedQty) { this.acceptedQty = acceptedQty; }

    public String getLotNo() { return lotNo; }
    public void setLotNo(String lotNo) { this.lotNo = lotNo; }
}
