package com.tech3.erp.service;

import com.tech3.erp.entity.AttendanceIncentiveSlabMaster;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.repository.AttendanceIncentiveSlabMasterRepository;
import com.tech3.erp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.*;

@Service
public class AttendanceIncentiveSlabMasterService {

    @Autowired
    private AttendanceIncentiveSlabMasterRepository slabRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryReportService salaryReportService;

    public List<AttendanceIncentiveSlabMaster> getAllActiveSlabs() {
        return slabRepository.findByIsActiveTrueOrderByWorkingHoursAscMonthTypeAscMinDaysAsc();
    }

    public List<AttendanceIncentiveSlabMaster> getAllSlabs() {
        return slabRepository.findAll();
    }

    /**
     * Returns slab master entries grouped by month in the structure:
     * [ { id: 1, month: "2026-08", data: [ ... ] } ]
     */
    public List<Map<String, Object>> getSlabsGroupedByMonth(Boolean activeOnly) {
        List<AttendanceIncentiveSlabMaster> allSlabs = Boolean.TRUE.equals(activeOnly)
                ? getAllActiveSlabs()
                : getAllSlabs();

        Map<String, List<AttendanceIncentiveSlabMaster>> groupedMap = new LinkedHashMap<>();

        for (AttendanceIncentiveSlabMaster slab : allSlabs) {
            String monthKey = (slab.getMonth() != null && !slab.getMonth().trim().isEmpty())
                    ? slab.getMonth().trim()
                    : "Default";
            groupedMap.computeIfAbsent(monthKey, k -> new ArrayList<>()).add(slab);
        }

        List<Map<String, Object>> result = new ArrayList<>();
        long idCounter = 1;

        for (Map.Entry<String, List<AttendanceIncentiveSlabMaster>> entry : groupedMap.entrySet()) {
            Map<String, Object> group = new LinkedHashMap<>();
            group.put("id", idCounter++);
            group.put("month", entry.getKey());
            group.put("data", entry.getValue());
            result.add(group);
        }

        return result;
    }

    @Transactional
    public List<AttendanceIncentiveSlabMaster> copyMonthSlabs(String sourceMonth, String targetMonth, String userName) {
        List<AttendanceIncentiveSlabMaster> allSlabs = getAllActiveSlabs();
        List<AttendanceIncentiveSlabMaster> sourceList = new ArrayList<>();

        for (AttendanceIncentiveSlabMaster slab : allSlabs) {
            String m = slab.getMonth() != null ? slab.getMonth().trim() : "Default";
            if (m.equalsIgnoreCase(sourceMonth.trim())) {
                sourceList.add(slab);
            }
        }

        if (sourceList.isEmpty() && !"Default".equalsIgnoreCase(sourceMonth)) {
            for (AttendanceIncentiveSlabMaster slab : allSlabs) {
                if (slab.getMonth() == null || slab.getMonth().trim().isEmpty() || "Default".equalsIgnoreCase(slab.getMonth())) {
                    sourceList.add(slab);
                }
            }
        }

        List<AttendanceIncentiveSlabMaster> copiedList = new ArrayList<>();
        for (AttendanceIncentiveSlabMaster src : sourceList) {
            AttendanceIncentiveSlabMaster copy = new AttendanceIncentiveSlabMaster();
            copy.setWorkingHours(src.getWorkingHours());
            copy.setMonthType(src.getMonthType());
            copy.setMonth(targetMonth);
            copy.setMinDays(src.getMinDays());
            copy.setMaxDays(src.getMaxDays());
            copy.setRevisedDailySalary(src.getRevisedDailySalary());
            copy.setIncentiveAmount(src.getIncentiveAmount());
            copy.setIsActive(true);
            copy.setCreatedBy(userName);
            copy.setUpdatedBy(userName);
            copiedList.add(slabRepository.save(copy));
        }

        return copiedList;
    }

    public Optional<AttendanceIncentiveSlabMaster> getSlabById(Long id) {
        return slabRepository.findById(id);
    }

    @Transactional
    public AttendanceIncentiveSlabMaster createSlab(AttendanceIncentiveSlabMaster slab) {
        validateSlab(slab);
        return slabRepository.save(slab);
    }

    @Transactional
    public AttendanceIncentiveSlabMaster updateSlab(Long id, AttendanceIncentiveSlabMaster details) {
        AttendanceIncentiveSlabMaster existing = slabRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance Incentive Slab Master record not found with id: " + id));

        existing.setWorkingHours(details.getWorkingHours());
        existing.setMonthType(details.getMonthType());
        existing.setMonth(details.getMonth());
        existing.setMinDays(details.getMinDays());
        existing.setMaxDays(details.getMaxDays());
        existing.setRevisedDailySalary(details.getRevisedDailySalary());
        existing.setIncentiveAmount(details.getIncentiveAmount());
        existing.setEffectiveFrom(details.getEffectiveFrom());
        existing.setEffectiveTo(details.getEffectiveTo());
        if (details.getUpdatedBy() != null) {
            existing.setUpdatedBy(details.getUpdatedBy());
        }
        if (details.getIsActive() != null) {
            existing.setIsActive(details.getIsActive());
        }

        validateSlab(existing);
        return slabRepository.save(existing);
    }

    @Transactional
    public void deleteSlab(Long id) {
        slabRepository.deleteById(id);
    }

    private void validateSlab(AttendanceIncentiveSlabMaster slab) {
        if (slab.getWorkingHours() == null || slab.getWorkingHours().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Working hours must be greater than 0");
        }
        if (slab.getMonthType() == null || slab.getMonthType().trim().isEmpty()) {
            throw new IllegalArgumentException("Month type is required ('FEB', 'DAYS_30', 'DAYS_31')");
        }
        if (slab.getMinDays() == null || slab.getMinDays().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Min days cannot be negative");
        }
        if (slab.getMaxDays() != null && slab.getMaxDays().compareTo(slab.getMinDays()) < 0) {
            throw new IllegalArgumentException("Max days cannot be less than Min days");
        }
    }

    public String determineMonthType(String monthStr) {
        if (monthStr == null || !monthStr.contains("-")) return "DAYS_30";
        try {
            YearMonth ym = YearMonth.parse(monthStr);
            int length = ym.lengthOfMonth();
            if (length <= 29) {
                return "FEB";
            } else if (length == 30) {
                return "DAYS_30";
            } else {
                return "DAYS_31";
            }
        } catch (Exception e) {
            return "DAYS_30";
        }
    }

    /**
     * Previews/Calculates attendance incentives for employees for a given target month based on Attendance Incentive Slab Master.
     */
    public List<Map<String, Object>> previewIncentivesForMonth(String month, String designationFilter) {
        String monthType = determineMonthType(month);
        List<Employee> employees = employeeRepository.findAllWithAssociations();

        // Fetch present days from salary report engine or calculate
        Map<Long, BigDecimal> workedDaysMap = new HashMap<>();
        try {
            var salaryReports = salaryReportService.getSalaryReport(month);
            if (salaryReports != null) {
                for (var rep : salaryReports) {
                    if (rep.getEmployeeId() != null) {
                        BigDecimal present = rep.getActualPresentDays() != null 
                                ? BigDecimal.valueOf(rep.getActualPresentDays()) 
                                : BigDecimal.ZERO;
                        workedDaysMap.put(rep.getEmployeeId(), present);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching salary report present days: " + e.getMessage());
        }

        List<Map<String, Object>> results = new ArrayList<>();

        for (Employee emp : employees) {
            if (designationFilter != null && !designationFilter.trim().isEmpty() && !"ALL".equalsIgnoreCase(designationFilter.trim())) {
                String desig = emp.getEmploymentDetails() != null ? emp.getEmploymentDetails().getDesignation() : emp.getDesignation();
                if (desig == null || !desig.trim().equalsIgnoreCase(designationFilter.trim())) {
                    continue;
                }
            }

            BigDecimal workingHours = new BigDecimal("12.00");
            if (emp.getShiftDetails() != null && emp.getShiftDetails().getTotalWorkingHours() != null) {
                try {
                    workingHours = BigDecimal.valueOf(emp.getShiftDetails().getTotalWorkingHours()).setScale(2, RoundingMode.HALF_UP);
                } catch (Exception ignored) {}
            }

            BigDecimal workedDays = workedDaysMap.getOrDefault(emp.getId(), BigDecimal.ZERO);

            List<AttendanceIncentiveSlabMaster> matchingSlabs = slabRepository.findMatchingSlab(workingHours, monthType, month, workedDays);
            if (matchingSlabs.isEmpty() && workingHours.compareTo(new BigDecimal("12.00")) != 0) {
                matchingSlabs = slabRepository.findMatchingSlab(new BigDecimal("12.00"), monthType, month, workedDays);
            }

            AttendanceIncentiveSlabMaster matchedSlab = matchingSlabs.isEmpty() ? null : matchingSlabs.get(0);

            Map<String, Object> item = new HashMap<>();
            item.put("employeeId", emp.getId());
            item.put("employeeCode", emp.getEmployeeCode());
            item.put("employeeName", emp.getEmployeeName());
            item.put("month", month);
            item.put("monthType", monthType);
            item.put("workingHours", workingHours);
            item.put("workedDays", workedDays);

            if (matchedSlab != null) {
                String label;
                if (matchedSlab.getMaxDays() == null) {
                    label = String.format("%.1f+ Days (₹%.0f)", matchedSlab.getMinDays().doubleValue(), matchedSlab.getIncentiveAmount().doubleValue());
                } else {
                    label = String.format("%.1f – %.1f Days (₹%.0f)", matchedSlab.getMinDays().doubleValue(), matchedSlab.getMaxDays().doubleValue(), matchedSlab.getIncentiveAmount().doubleValue());
                }
                item.put("matchedSlabId", matchedSlab.getId());
                item.put("matchedSlabLabel", label);
                item.put("calculatedIncentiveAmount", matchedSlab.getIncentiveAmount());
                item.put("revisedDailySalary", matchedSlab.getRevisedDailySalary());
            } else {
                item.put("matchedSlabId", null);
                item.put("matchedSlabLabel", "No Slab Matched (₹0)");
                item.put("calculatedIncentiveAmount", BigDecimal.ZERO);
                item.put("revisedDailySalary", BigDecimal.ZERO);
            }

            results.add(item);
        }

        return results;
    }
}
