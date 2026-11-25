package com.tech3.erp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employee_property_assignment", schema = "masters")
public class EmployeePropertyAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "department")
    private String department;

    @Column(name = "designation")
    private String designation;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = true)
    private ItemMaster item;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "property_category")
    private String propertyCategory;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity = BigDecimal.ONE;

    @Column(name = "uom")
    private String uom = "NOS";

    @Column(name = "assigned_date", nullable = false)
    private LocalDate assignedDate;

    @Column(name = "expected_return_date")
    private LocalDate expectedReturnDate;

    @Column(name = "condition_at_issue")
    private String conditionAtIssue = "GOOD"; // "NEW", "GOOD", "FAIR"

    @Column(name = "assigned_by")
    private String assignedBy;

    @Column(name = "status", nullable = false)
    private String status = "ASSIGNED"; // "ASSIGNED", "RETURNED", "DAMAGED", "LOST"

    @Column(name = "returned_date")
    private LocalDate returnedDate;

    @Column(name = "condition_at_return")
    private String conditionAtReturn; // "GOOD", "DAMAGED", "SCRAP"

    @Column(name = "received_by")
    private String receivedBy;

    @Column(name = "damage_deduction_amount")
    private BigDecimal damageDeductionAmount = BigDecimal.ZERO;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public EmployeePropertyAssignment() {}

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (assignedDate == null) {
            assignedDate = LocalDate.now();
        }
        if (status == null || status.trim().isEmpty()) {
            status = "ASSIGNED";
        }
        if (quantity == null) {
            quantity = BigDecimal.ONE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Employee getEmployee() {
        return employee;
    }

    @JsonProperty("employee")
    public void setEmployee(Employee employee) {
        this.employee = employee;
        if (employee != null) {
            if (this.employeeCode == null || this.employeeCode.isEmpty()) {
                this.employeeCode = employee.getEmployeeCode();
            }
            if (this.employeeName == null || this.employeeName.isEmpty()) {
                this.employeeName = employee.getEmployeeName();
            }
            if (this.department == null || this.department.isEmpty()) {
                this.department = employee.getDepartment();
            }
            if (this.designation == null || this.designation.isEmpty()) {
                this.designation = employee.getDesignation();
            }
        }
    }

    @JsonProperty("employeeId")
    public Long getEmployeeId() {
        return employee != null ? employee.getId() : null;
    }

    @JsonProperty("employeeId")
    public void setEmployeeId(Long employeeId) {
        if (employeeId != null && this.employee == null) {
            Employee emp = new Employee();
            emp.setId(employeeId);
            this.employee = emp;
        }
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @JsonIgnore
    public ItemMaster getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(ItemMaster item) {
        this.item = item;
        if (item != null) {
            if (this.itemCode == null || this.itemCode.isEmpty()) {
                this.itemCode = item.getItemCode();
            }
            if (this.itemName == null || this.itemName.isEmpty()) {
                this.itemName = item.getItemName();
            }
            if (this.propertyCategory == null || this.propertyCategory.isEmpty()) {
                this.propertyCategory = item.getItemCategory();
            }
            if (this.uom == null || this.uom.isEmpty()) {
                this.uom = item.getUomName();
            }
        }
    }

    @JsonProperty("itemId")
    public Long getItemId() {
        return item != null ? item.getId() : null;
    }

    @JsonProperty("itemId")
    public void setItemId(Long itemId) {
        if (itemId != null && this.item == null) {
            ItemMaster itm = new ItemMaster();
            itm.setId(itemId);
            this.item = itm;
        }
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPropertyCategory() {
        return propertyCategory;
    }

    public void setPropertyCategory(String propertyCategory) {
        this.propertyCategory = propertyCategory;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    @Transient
    @JsonIgnore
    private boolean expectedReturnDateSpecified = false;

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
        this.expectedReturnDateSpecified = true;
    }

    public boolean isExpectedReturnDateSpecified() {
        return expectedReturnDateSpecified;
    }

    public String getConditionAtIssue() {
        return conditionAtIssue;
    }

    public void setConditionAtIssue(String conditionAtIssue) {
        this.conditionAtIssue = conditionAtIssue;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public String getConditionAtReturn() {
        return conditionAtReturn;
    }

    public void setConditionAtReturn(String conditionAtReturn) {
        this.conditionAtReturn = conditionAtReturn;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public BigDecimal getDamageDeductionAmount() {
        return damageDeductionAmount;
    }

    public void setDamageDeductionAmount(BigDecimal damageDeductionAmount) {
        this.damageDeductionAmount = damageDeductionAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
