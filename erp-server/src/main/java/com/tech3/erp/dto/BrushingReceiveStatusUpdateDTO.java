package com.tech3.erp.dto;

import java.util.List;

public class BrushingReceiveStatusUpdateDTO {
    private String status;
    private List<BrushingReceiveDetailDTO> details;

    private java.time.LocalDate brushingCompletedDate;

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<BrushingReceiveDetailDTO> getDetails() {
        return details;
    }

    public void setDetails(List<BrushingReceiveDetailDTO> details) {
        this.details = details;
    }

    public java.time.LocalDate getBrushingCompletedDate() {
        return brushingCompletedDate;
    }

    public void setBrushingCompletedDate(java.time.LocalDate brushingCompletedDate) {
        this.brushingCompletedDate = brushingCompletedDate;
    }
}
