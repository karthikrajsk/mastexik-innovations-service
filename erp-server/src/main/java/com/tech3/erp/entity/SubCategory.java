package com.tech3.erp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "sub_category", schema = "masters", uniqueConstraints = @UniqueConstraint(columnNames = {"category_sno", "sub_category_name"}))
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_sno", nullable = false)
    private Category category;

    @Column(name = "sub_category_name", nullable = false, length = 100)
    @NotBlank
    @Size(min = 1, max = 100)
    private String subCategoryName;

    @Column(name = "active_flag", nullable = false)
    private boolean activeFlag = true;

    public SubCategory() {}

    public SubCategory(Long id, Category category, String subCategoryName, boolean activeFlag) {
        this.id = id;
        this.category = category;
        this.subCategoryName = subCategoryName;
        this.activeFlag = activeFlag;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getSubCategoryName() { return subCategoryName; }
    public void setSubCategoryName(String subCategoryName) { this.subCategoryName = subCategoryName; }

    public boolean getActiveFlag() { return activeFlag; }
    public void setActiveFlag(boolean activeFlag) { this.activeFlag = activeFlag; }
}
