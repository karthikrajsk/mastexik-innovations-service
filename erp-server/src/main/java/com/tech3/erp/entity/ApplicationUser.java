package com.tech3.erp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application_users", schema = "masters")
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // Admin, Sales, PPC, Inventory

    @ElementCollection
    @CollectionTable(name = "user_companies", schema = "masters", 
                     joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "company_id")
    private List<Long> companies = new ArrayList<>();

    @Column(nullable = false)
    private Boolean activeFlag = true;

    @Column(name = "module_id")
    private Long moduleId; // optional module identifier

    @Column(name = "user_type")
    private Long userType; // 1: Data Entry Operator, 2: Department Head, 3: Approver

@Column(name = "role_display_name")
private String roleDisplayName;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName != null ? userName.toUpperCase() : null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Long> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Long> companies) {
        this.companies = companies;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getUserType() {
        return userType;
    }

    public void setUserType(Long userType) {
        this.userType = userType;
    }

    // Getter & Setter:
public String getRoleDisplayName() {
    return roleDisplayName;
}

public void setRoleDisplayName(String roleDisplayName) {
    this.roleDisplayName = roleDisplayName;
}

}