package com.tech3.erp.controller;

import com.tech3.erp.dto.ReportConfigurationDTO;
import com.tech3.erp.service.ReportConfigurationService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report-configurations")
@Tag(name = "Report Configuration Controller", description = "APIs for Report Configuration management")
@CrossOrigin(origins = "*")
public class ReportConfigurationController {

    private final ReportConfigurationService reportConfigService;

    public ReportConfigurationController(ReportConfigurationService reportConfigService) {
        this.reportConfigService = reportConfigService;
    }

    @PostMapping
    @Operation(summary = "Create Report Configuration")
    public ResponseEntity<Object> create(@Valid @RequestBody ReportConfigurationDTO dto) {
        return ResponseUtil.success("Report configuration created", 
                reportConfigService.createReport(dto));
    }

    @GetMapping
    @Operation(summary = "List all Report Configurations")
    public ResponseEntity<Object> getAll(@RequestParam(name = "companyId", required = false) Long companyId) {
        List<ReportConfigurationDTO> reports = reportConfigService.getAllReports(companyId);
        return ResponseUtil.success("Report configurations fetched", reports);
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get all Report Configurations by Company")
    public ResponseEntity<Object> getAllByCompany(@PathVariable Long companyId) {
        return ResponseUtil.success("Report configurations fetched", 
                reportConfigService.getAllReports(companyId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Report Configuration by ID")
    public ResponseEntity<Object> get(@PathVariable Long id, 
            @RequestParam(name = "companyId", required = false) Long companyId) {
        return ResponseUtil.success("Report configuration fetched", 
                reportConfigService.getReportById(id, companyId));
    }

    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Report Configuration by ID and Company")
    public ResponseEntity<Object> getByIdAndCompany(@PathVariable Long companyId, @PathVariable Long id) {
        return ResponseUtil.success("Report configuration fetched", 
                reportConfigService.getReportById(id, companyId));
    }

    @GetMapping("/company/{companyId}/by-slug")
    @Operation(summary = "Get Report Configuration by name slug and Company")
    public ResponseEntity<Object> getBySlug(@PathVariable Long companyId,
            @RequestParam(name = "slug") String slug) {
        return ResponseUtil.success("Report configuration fetched",
                reportConfigService.getReportBySlug(slug, companyId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Report Configuration")
    public ResponseEntity<Object> update(@PathVariable Long id, 
            @Valid @RequestBody ReportConfigurationDTO dto) {
        return ResponseUtil.success("Report configuration updated", 
                reportConfigService.updateReport(id, dto));
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Report Configuration by Company")
    public ResponseEntity<Object> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, 
            @Valid @RequestBody ReportConfigurationDTO dto) {
        dto.setCompanyId(companyId);
        return ResponseUtil.success("Report configuration updated", 
                reportConfigService.updateReport(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Report Configuration")
    public ResponseEntity<Object> delete(@PathVariable Long id, 
            @RequestParam(name = "companyId", required = false) Long companyId) {
        reportConfigService.deleteReport(id, companyId);
        return ResponseUtil.success("Report configuration deleted", null);
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Report Configuration by Company")
    public ResponseEntity<Object> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        reportConfigService.deleteReport(id, companyId);
        return ResponseUtil.success("Report configuration deleted", null);
    }

    @PostMapping("/{id}/template")
    @Operation(summary = "Save Report Template")
    public ResponseEntity<Object> saveTemplate(@PathVariable Long id, 
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestBody Map<String, Object> request) {
        Object templateContentObj = request.get("templateContent");
        String templateContent = templateContentObj != null ? templateContentObj.toString() : null;
        Object queryContentObj = request.get("queryContent");
        String queryContent = queryContentObj != null ? queryContentObj.toString() : null;
        Object queryDataObj = request.containsKey("queryData") ? request.get("queryData") : request.get("query_data");
        return ResponseUtil.success("Template saved", 
                reportConfigService.saveTemplate(id, companyId, templateContent, queryContent, queryDataObj));
    }

    @PostMapping("/{id}/execute")
    @Operation(summary = "Execute Report with Filters")
    public ResponseEntity<Object> executeReport(@PathVariable Long id, 
            @RequestParam(name = "companyId", required = false) Long companyId,
            @RequestBody Map<String, Object> filterParams) {
        return ResponseUtil.success("Report executed", 
                reportConfigService.executeReport(id, companyId, filterParams));
    }

    @GetMapping("/execute-weaving-live")
    @Operation(summary = "Execute Weaving Production Live Report directly")
    public ResponseEntity<Object> executeWeavingLive(@RequestParam(name = "companyId", required = false) Long companyId) {
        return ResponseUtil.success("Weaving live report executed",
                reportConfigService.executeWeavingLiveReport(companyId));
    }
}