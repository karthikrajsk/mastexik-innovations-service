package com.tech3.erp.dto;

import com.tech3.erp.entity.ApplicationUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class ApplicationUserDTO {

    private Long id;

    @NotBlank(message = "User Name is required")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "User Name must be alphanumeric")
    private String userName;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*[0-9]).{6,}$", 
             message = "Password must contain at least one uppercase, one special character, one number, and be at least 6 characters long")
    private String password;

    // @NotBlank(message = "Role is required")
    // @Pattern(regexp = "^(Admin|Sales|PPC|Inventory)$", message = "Role must be Admin, Sales, PPC, or Inventory")
    // private String role;
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(Admin|Sales|PPC|Inventory|Technical Team|Merchant|Sizing|Weaving|Brushing)$", 
             message = "Role must be Admin, Sales, PPC, Inventory, Technical Team, Merchant, Sizing, Weaving, or Brushing")
    private String role;

    @NotEmpty(message = "At least one company must be selected")
    private List<Long> companies;

    private Boolean activeFlag;

    private Long moduleId; // optional module identifier

    private Long userType;

private String roleDisplayName;  

    // Constructors
    public ApplicationUserDTO() {}

    public ApplicationUserDTO(ApplicationUser entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.password = entity.getPassword();
        this.role = entity.getRole();
        this.roleDisplayName = entity.getRoleDisplayName();  
        this.companies = entity.getCompanies();
        this.activeFlag = entity.getActiveFlag();
        this.moduleId = entity.getModuleId();
        this.userType = entity.getUserType();
    }

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
        this.userName = userName;
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
    public String getRoleDisplayName() {
    return roleDisplayName;
}

public void setRoleDisplayName(String roleDisplayName) {
    this.roleDisplayName = roleDisplayName;
}
}