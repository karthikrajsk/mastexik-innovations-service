package com.tech3.erp.dto;

import java.time.LocalDate;
import java.util.List;

public class RequestFormRoundDTO {

    private Long id;
    private Long requestFormId;
    private Integer roundNumber;
    private LocalDate readyDate;
    private String courierName;
    private String courierNumber;
    private Integer noOfItems;
    private List<RequestFormRoundItemDTO> items;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRequestFormId() { return requestFormId; }
    public void setRequestFormId(Long requestFormId) { this.requestFormId = requestFormId; }

    public Integer getRoundNumber() { return roundNumber; }
    public void setRoundNumber(Integer roundNumber) { this.roundNumber = roundNumber; }

    public LocalDate getReadyDate() { return readyDate; }
    public void setReadyDate(LocalDate readyDate) { this.readyDate = readyDate; }

    public String getCourierName() { return courierName; }
    public void setCourierName(String courierName) { this.courierName = courierName; }

    public String getCourierNumber() { return courierNumber; }
    public void setCourierNumber(String courierNumber) { this.courierNumber = courierNumber; }

    public Integer getNoOfItems() { return noOfItems; }
    public void setNoOfItems(Integer noOfItems) { this.noOfItems = noOfItems; }

    public List<RequestFormRoundItemDTO> getItems() { return items; }
    public void setItems(List<RequestFormRoundItemDTO> items) { this.items = items; }
}
