package com.tech3.erp.dto;

import com.tech3.erp.entity.CompanyPrintContext;
import jakarta.validation.constraints.*;

public class CompanyPrintContextDTO {

    private Long id;

    @NotBlank(message = "Screen Name is required")
    private String screenName;

    @NotBlank(message = "Parameter 1 is required")
    private String parameter1;

    @NotBlank(message = "Parameter 2 is required")
    private String parameter2;

    @NotBlank(message = "Parameter 3 is required")
    private String parameter3;

    @NotBlank(message = "Separator is required")
    @Pattern(regexp = "^[-/]$", message = "Separator must be - or /")
    private String separator;

    private String prefixName; // optional

    public CompanyPrintContextDTO() {}

    public CompanyPrintContextDTO(CompanyPrintContext printContext) {
        this.id = printContext.getId();
        this.screenName = printContext.getScreenName();
        this.parameter1 = printContext.getParameter1();
        this.parameter2 = printContext.getParameter2();
        this.parameter3 = printContext.getParameter3();
        this.separator = printContext.getSeparator();
        this.prefixName = printContext.getPrefixName();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getPrefixName() { return prefixName; }
    public void setPrefixName(String prefixName) { this.prefixName = prefixName; }

}