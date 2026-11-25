package com.tech3.erp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tech3.erp.entity.OpeningBalanceSummaryItemEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpeningBalanceSummaryItemDto {

    private Long id;

    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("quantity")
    private Double quantity;

    @JsonProperty("noOfPieces")
    private Integer noOfPieces;

    @JsonProperty("noOfBags")
    private Integer noOfBags;

    public OpeningBalanceSummaryItemDto() {}

    public OpeningBalanceSummaryItemDto(OpeningBalanceSummaryItemEntity entity) {
        this.id = entity.getId();
        this.itemName = entity.getItemName();
        this.quantity = entity.getQuantity();
        this.noOfPieces = entity.getNoOfPieces();
        this.noOfBags = entity.getNoOfBags();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public Double getQuantity() { return quantity; }
    public void setQuantity(Double quantity) { this.quantity = quantity; }

    public Integer getNoOfPieces() { return noOfPieces; }
    public void setNoOfPieces(Integer noOfPieces) { this.noOfPieces = noOfPieces; }

    public Integer getNoOfBags() { return noOfBags; }
    public void setNoOfBags(Integer noOfBags) { this.noOfBags = noOfBags; }
}
