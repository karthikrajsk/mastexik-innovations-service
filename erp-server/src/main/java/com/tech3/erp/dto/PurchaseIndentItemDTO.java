package com.tech3.erp.dto;

import com.tech3.erp.entity.PurchaseIndentItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseIndentItemDTO {

    private Long id;

    @NotNull(message = "Item is required")
    private Long itemId;

    @NotBlank(message = "Item name is required")
    private String itemName;
    private String specification;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.0001", message = "Quantity must be at least 0.0001")
    private BigDecimal quantity;

    @NotNull(message = "UOM is required")
    private Long uomId;

    @NotBlank(message = "UOM name is required")
    private String uomName;

    @NotNull(message = "Item required date is required")
    private LocalDate requiredDate;

    public PurchaseIndentItemDTO() {
    }

    public PurchaseIndentItemDTO(PurchaseIndentItem item) {
        this.id = item.getId();
        this.itemId = item.getItemId();
        this.itemName = item.getItemName();
        this.specification = item.getSpecification();
        this.quantity = item.getQuantity();
        this.uomId = item.getUomId();
        this.uomName = item.getUomName();
        this.requiredDate = item.getRequiredDate();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getSpecification() { return specification; }
    public void setSpecification(String specification) { this.specification = specification; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public Long getUomId() { return uomId; }
    public void setUomId(Long uomId) { this.uomId = uomId; }
    public String getUomName() { return uomName; }
    public void setUomName(String uomName) { this.uomName = uomName; }
    public LocalDate getRequiredDate() { return requiredDate; }
    public void setRequiredDate(LocalDate requiredDate) { this.requiredDate = requiredDate; }
}
