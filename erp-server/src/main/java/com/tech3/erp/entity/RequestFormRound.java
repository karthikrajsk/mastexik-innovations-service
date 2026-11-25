package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "request_form_round", schema = "masters")
public class RequestFormRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_form_id")
    @JsonBackReference
    private RequestForm requestForm;

    @Column(name = "round_number")
    private Integer roundNumber;

    @Column(name = "ready_date")
    private LocalDate readyDate;

    @Column(name = "courier_name")
    private String courierName;

    @Column(name = "courier_number")
    private String courierNumber;

    @Column(name = "no_of_items")
    private Integer noOfItems;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<RequestFormRoundItem> items;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public RequestForm getRequestForm() { return requestForm; }
    public void setRequestForm(RequestForm requestForm) { this.requestForm = requestForm; }

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

    public List<RequestFormRoundItem> getItems() { return items; }
    public void setItems(List<RequestFormRoundItem> items) { this.items = items; }
}
