// salary report service

package com.tech3.erp.service;

import com.tech3.erp.dto.SalaryReportDTO;
import com.tech3.erp.dto.EmployeeAllowanceAdvanceDTO;
import com.tech3.erp.entity.Attendance;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.EmployeeAllowanceAdvance;
import com.tech3.erp.entity.SalaryDetails;
import com.tech3.erp.entity.ShiftDetails;
import com.tech3.erp.entity.SalaryReportOverride;
import com.tech3.erp.entity.EmployeeLoan;
import com.tech3.erp.entity.EmployeeAttendanceIncentive;
import com.tech3.erp.entity.EmployeeProductionIncentive;
import com.tech3.erp.entity.EmployeeCanteenDeduction;
import com.tech3.erp.repository.EmployeeAttendanceIncentiveRepository;
import com.tech3.erp.repository.EmployeeProductionIncentiveRepository;
import com.tech3.erp.repository.EmployeeCanteenDeductionRepository;
import com.tech3.erp.entity.EmployeeSalaryHistory;
import com.tech3.erp.repository.EmployeeSalaryHistoryRepository;
import com.tech3.erp.entity.EmployeePfWageCeilingHistory;
import com.tech3.erp.repository.EmployeePfWageCeilingHistoryRepository;
import com.tech3.erp.repository.AttendanceRepository;
import com.tech3.erp.repository.EmployeeRepository;
import com.tech3.erp.repository.EmployeeAllowanceAdvanceRepository;
import com.tech3.erp.repository.SalaryReportOverrideRepository;
import com.tech3.erp.repository.EmployeeLoanRepository;
import com.tech3.erp.repository.EmployeeLoanSkipRequestRepository;
import com.tech3.erp.entity.EmployeeLoanSkipRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.*;

import com.tech3.erp.entity.Holiday;
import com.tech3.erp.repository.HolidayRepository;

@Service
public class SalaryReportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeAllowanceAdvanceRepository allowanceAdvanceRepository;

    @Autowired
    private SalaryReportOverrideRepository overrideRepository;

    @Autowired
    private EmployeeLoanRepository employeeLoanRepository;

    @Autowired
    private EmployeeLoanSkipRequestRepository employeeLoanSkipRequestRepository;

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private EmployeeAttendanceIncentiveRepository incentiveRepository;

    @Autowired
    private EmployeeProductionIncentiveRepository productionIncentiveRepository;

    @Autowired
    private EmployeeCanteenDeductionRepository canteenDeductionRepository;

    @Autowired(required = false)
    private EmployeeSalaryHistoryRepository employeeSalaryHistoryRepository;

    @Autowired(required = false)
    private EmployeePfWageCeilingHistoryRepository pfWageCeilingHistoryRepository;

    // Helper method to round BigDecimal to nearest whole number
    private BigDecimal roundToWholeNumber(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.setScale(0, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal getEffectivePfWageCeiling(Employee employee, LocalDate targetDate) {
        if (employee == null) {
            return BigDecimal.valueOf(15000);
        }

        List<EmployeePfWageCeilingHistory> ceilingHistories = null;
        if (pfWageCeilingHistoryRepository != null && employee.getId() != null) {
            try {
                ceilingHistories = pfWageCeilingHistoryRepository.findByEmployeeIdOrderByEffectiveDateDesc(employee.getId());
            } catch (Exception ignored) {}
        }
        if ((ceilingHistories == null || ceilingHistories.isEmpty()) && employee.getPfWageCeilingHistory() != null) {
            ceilingHistories = employee.getPfWageCeilingHistory();
        }

        if (ceilingHistories != null && !ceilingHistories.isEmpty()) {
            if (targetDate != null) {
                for (EmployeePfWageCeilingHistory hist : ceilingHistories) {
                    if (hist.getEffectiveDate() != null && !hist.getEffectiveDate().isAfter(targetDate)) {
                        if (hist.getPfWageCeiling() != null && hist.getPfWageCeiling().compareTo(BigDecimal.ZERO) > 0) {
                            return hist.getPfWageCeiling();
                        }
                    }
                }
            }
            for (EmployeePfWageCeilingHistory hist : ceilingHistories) {
                if (hist.getPfWageCeiling() != null && hist.getPfWageCeiling().compareTo(BigDecimal.ZERO) > 0) {
                    return hist.getPfWageCeiling();
                }
            }
        }

        List<EmployeeSalaryHistory> histories = null;
        if (employeeSalaryHistoryRepository != null && employee.getId() != null) {
            try {
                histories = employeeSalaryHistoryRepository.findByEmployeeIdOrderByEffectiveDateDesc(employee.getId());
            } catch (Exception ignored) {}
        }
        if ((histories == null || histories.isEmpty()) && employee.getSalaryHistory() != null) {
            histories = employee.getSalaryHistory();
        }

        if (histories != null && !histories.isEmpty()) {
            if (targetDate != null) {
                for (EmployeeSalaryHistory hist : histories) {
                    if (hist.getEffectiveDate() != null && !hist.getEffectiveDate().isAfter(targetDate)) {
                        if (hist.getPfWageCeiling() != null && hist.getPfWageCeiling().compareTo(BigDecimal.ZERO) > 0) {
                            return hist.getPfWageCeiling();
                        }
                    }
                }
            }
            for (EmployeeSalaryHistory hist : histories) {
                if (hist.getPfWageCeiling() != null && hist.getPfWageCeiling().compareTo(BigDecimal.ZERO) > 0) {
                    return hist.getPfWageCeiling();
                }
            }
        }

        if (employee.getPfWageCeiling() != null && employee.getPfWageCeiling() > 0) {
            return BigDecimal.valueOf(employee.getPfWageCeiling());
        }

        return BigDecimal.valueOf(15000);
    }

    // Debug file writer to capture payloads
    private void writeDebugFile(Object data, String filename) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("d:/React Projects/Git Projects/Hyagreevas/" + filename), data);
        } catch (Exception e) {
            System.err.println("Error writing debug file: " + e.getMessage());
        }
    }

    @Transactional
    public List<SalaryReportDTO> getSalaryReport(String month) {
        YearMonth yearMonth = YearMonth.parse(month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return getSalaryReport(startDate, endDate, month, false);
    }

    @Transactional
    public List<SalaryReportDTO> getSalaryReport(String fromDateStr, String toDateStr) {
        LocalDate startDate = LocalDate.parse(fromDateStr);
        LocalDate endDate = LocalDate.parse(toDateStr);
        if (startDate.isAfter(endDate)) {
            LocalDate temp = startDate;
            startDate = endDate;
            endDate = temp;
        }

        String monthLabel = startDate.equals(endDate)
                ? startDate.toString()
                : (startDate.toString() + " to " + endDate.toString());

        return getSalaryReport(startDate, endDate, monthLabel, true);
    }

    @Transactional
    public List<SalaryReportDTO> getSalaryReport(LocalDate startDate, LocalDate endDate, String month) {
        return getSalaryReport(startDate, endDate, month, false);
    }

    @Transactional
    public List<SalaryReportDTO> getSalaryReport(LocalDate startDate, LocalDate endDate, String month, boolean isDateRange) {
        int monthInt = startDate.getMonthValue();
        int yearInt = startDate.getYear();

        List<Employee> employees = employeeRepository.findAllWithAssociations();
        // Sort employees by ID in descending order to show latest entries first
        employees.sort((e1, e2) -> e2.getId().compareTo(e1.getId()));
        List<SalaryReportDTO> report = new ArrayList<>();

        // Fetch active festival wage holidays in the date range
        List<Holiday> festivalHolidays = holidayRepository.findByHolidayDateBetween(startDate, endDate);
        Set<LocalDate> festivalWageDates = festivalHolidays.stream()
                .filter(h -> Boolean.TRUE.equals(h.getIsFestivalWageApplicable()) && (h.getStatus() == null || !"Inactive".equalsIgnoreCase(h.getStatus())))
                .map(Holiday::getHolidayDate)
                .collect(Collectors.toSet());

        // Fetch overrides for the month and year only if not in date range mode
        List<SalaryReportOverride> allOverrides = isDateRange
                ? Collections.emptyList()
                : overrideRepository.findByMonthAndYear(monthInt, yearInt);
        Map<Long, SalaryReportOverride> overrideMap = allOverrides.stream()
                .collect(Collectors.toMap(SalaryReportOverride::getEmployeeId, o -> o, (o1, o2) -> o1));

        for (Employee employee : employees) {
            SalaryReportOverride override = overrideMap.get(employee.getId());
            BigDecimal pfWageCeiling = getEffectivePfWageCeiling(employee, endDate);
            
            Double workingHoursPerDay = 8.0;
            if (employee.getShiftDetails() != null && employee.getShiftDetails().getTotalWorkingHours() != null) {
                workingHoursPerDay = employee.getShiftDetails().getTotalWorkingHours();
            }
            double standardShiftHrs = (workingHoursPerDay > 0) ? workingHoursPerDay : 8.0;

            List<Attendance> attendances = attendanceRepository.findByEmployeeAndDateBetween(employee, startDate, endDate);
            
            double totalWorkingHours = 0.0;
            double totalOtHours = 0.0;
            double totalFestivalHours = 0.0;
            double actualPresentDays = 0.0;
            double festivalPresentDays = 0.0;

            for (Attendance attendance : attendances) {
                double workHrs = parseHours(attendance.getWorkingHours());
                double otHrs = parseHours(attendance.getOvertimeHours());
                double attDays = (attendance.getWorkingDays() != null) 
                        ? attendance.getWorkingDays() 
                        : (attendance.getStatus() != null && attendance.getStatus().equalsIgnoreCase("PRESENT") 
                            ? 1.0 
                            : (attendance.getStatus() != null && (attendance.getStatus().equalsIgnoreCase("HALF DAY") || attendance.getStatus().equalsIgnoreCase("HALFDAY")) 
                                ? 0.5 
                                : 0.0));

                boolean isFestWageApplicable = (employee.getFestivalWagesApplicable() != null && employee.getFestivalWagesApplicable() == 1)
                        || (employee.getEmploymentDetails() != null && employee.getEmploymentDetails().getIsFestivalWageApplicable() != null && employee.getEmploymentDetails().getIsFestivalWageApplicable() == 1);
                
                boolean isFestivalDay = attendance.getDate() != null && festivalWageDates.contains(attendance.getDate());

                if (isFestWageApplicable && isFestivalDay) {
                    double festRegHrs = Math.min(workHrs, standardShiftHrs);
                    totalFestivalHours += festRegHrs;

                    double festOtHrs = (workHrs > standardShiftHrs) ? (workHrs - standardShiftHrs) : 0.0;
                    double effectiveOtForDay = Math.max(otHrs, festOtHrs);
                    totalOtHours += effectiveOtForDay;

                    if (attDays > 0) {
                        festivalPresentDays += attDays;
                    }
                } else {
                    totalWorkingHours += workHrs;
                    totalOtHours += otHrs;
                }

                if (attDays > 0) {
                    actualPresentDays += attDays;
                }
            }

            boolean overtimeAllowed = employee.getShiftDetails() != null && employee.getShiftDetails().isOvertimeAllowed();
            if (!overtimeAllowed) {
                totalOtHours = 0.0;
            }

            if (override != null) {
                if (override.getActualPresentDays() != null) {
                    actualPresentDays = override.getActualPresentDays().doubleValue();
                }
                if (override.getOtHoursWorked() != null) {
                    totalOtHours = override.getOtHoursWorked().doubleValue();
                }
                if (override.getFestivalHoursWorked() != null) {
                    totalFestivalHours = override.getFestivalHoursWorked().doubleValue();
                }
            }

            SalaryDetails salaryDetails = employee.getSalaryDetails();
            BigDecimal perHourSalary = (salaryDetails != null && salaryDetails.getPerHourSalary() != null) 
                                       ? salaryDetails.getPerHourSalary() : BigDecimal.ZERO;
            BigDecimal otRatePerHour = (salaryDetails != null && salaryDetails.getOtRatePerHour() != null)
                                       ? salaryDetails.getOtRatePerHour() : BigDecimal.ZERO;

            if (override != null) {
                SalaryReportDTO dto = new SalaryReportDTO();
                dto.setId(override.getId());
                dto.setEmployeeId(employee.getId());
                dto.setEmployeeCode(employee.getEmployeeCode());
                dto.setEmployeeName(employee.getEmployeeName());
                dto.setDepartment(override.getDepartment() != null && !override.getDepartment().trim().isEmpty() 
                    ? override.getDepartment() 
                    : (employee.getEmploymentDetails() != null ? employee.getEmploymentDetails().getDepartment() : employee.getDepartment()));
                dto.setDesignation(override.getDesignation() != null && !override.getDesignation().trim().isEmpty() 
                    ? override.getDesignation() 
                    : (employee.getDesignation() != null ? employee.getDesignation() : "Employee"));
                dto.setCategory(employee.getCategory());
                dto.setMonth(month);
                dto.setSalaryType(salaryDetails != null ? salaryDetails.getSalaryType() : null);
                dto.setCompanyId(override.getCompanyId());
                
                // Working days and hours
                dto.setDaysToBePresent(override.getDaysToBePresent());
                
                workingHoursPerDay = null;
                if (employee.getShiftDetails() != null && employee.getShiftDetails().getTotalWorkingHours() != null) {
                    workingHoursPerDay = employee.getShiftDetails().getTotalWorkingHours();
                }
                dto.setWorkingHoursPerDay(workingHoursPerDay);
                dto.setActualPresentDays(override.getActualPresentDays() != null ? override.getActualPresentDays().doubleValue() : 0.0);
                dto.setAdditionalWeekOffDays(null);
                dto.setOtHoursWorked(override.getOtHoursWorked() != null ? override.getOtHoursWorked().doubleValue() : 0.0);
                dto.setFestivalHoursWorked(override.getFestivalHoursWorked() != null ? override.getFestivalHoursWorked().doubleValue() : totalFestivalHours);
                
                // Earnings
                BigDecimal grossSalary = (salaryDetails != null && salaryDetails.getBasicSalary() != null) ? salaryDetails.getBasicSalary() : null;
                if ("Daily".equalsIgnoreCase(dto.getSalaryType())) {
                    grossSalary = null;
                }
                dto.setGrossSalary(grossSalary);
                
                BigDecimal perDaySalaryForDisplay = override.getPerDaySalary();
                if (perDaySalaryForDisplay == null) {
                    if (salaryDetails != null && salaryDetails.getBasicSalary() != null) {
                        String salaryType = salaryDetails.getSalaryType();
                        if ("Daily".equalsIgnoreCase(salaryType)) {
                            perDaySalaryForDisplay = salaryDetails.getBasicSalary();
                        } else if (override.getDaysToBePresent() != null && override.getDaysToBePresent() > 0) {
                            perDaySalaryForDisplay = salaryDetails.getBasicSalary().divide(BigDecimal.valueOf(override.getDaysToBePresent()), 2, BigDecimal.ROUND_HALF_UP);
                        }
                    }
                }
                dto.setPerDaySalary(roundToWholeNumber(perDaySalaryForDisplay));
                
                dto.setBasicSalary(roundToWholeNumber(override.getBasicSalary()));
                dto.setOtSalary(roundToWholeNumber(override.getOtSalary()));
                dto.setFestivalWageSalary(override.getFestivalWageSalary() != null ? roundToWholeNumber(override.getFestivalWageSalary()) : BigDecimal.ZERO);
                BigDecimal attIncVal = override.getAttIncentive() != null ? override.getAttIncentive() : override.getAttendanceIncentive();
                BigDecimal prodIncVal = override.getProdIncentive() != null ? override.getProdIncentive() : override.getProductionIncentive();

                dto.setAttendanceIncentive(override.getAttendanceIncentive() != null ? override.getAttendanceIncentive() : attIncVal);
                dto.setProductionIncentive(override.getProductionIncentive() != null ? override.getProductionIncentive() : prodIncVal);
                dto.setFoodAllowance(override.getFoodAllowance());
                dto.setAttIncentive(attIncVal);
                dto.setProdIncentive(prodIncVal);
                dto.setFoodAllowanceAmount(override.getFoodAllowanceAmount());
                
                BigDecimal totalEarnings = (override.getBasicDA() != null ? override.getBasicDA() : BigDecimal.ZERO)
                                          .add(override.getHra() != null ? override.getHra() : BigDecimal.ZERO)
                                          .add(override.getTa() != null ? override.getTa() : BigDecimal.ZERO)
                                          .add(override.getMedical() != null ? override.getMedical() : BigDecimal.ZERO);
                dto.setTotalEarnings(roundToWholeNumber(totalEarnings));
                
                // Allowances and HRA components
                dto.setBasicDA(roundToWholeNumber(override.getBasicDA()));
                dto.setHra(roundToWholeNumber(override.getHra()));
                dto.setTa(override.getTa());
                dto.setMedical(override.getMedical());
                BigDecimal overrideHouseRent = override.getHouseRent();
                if (overrideHouseRent == null && employee.getEmploymentDetails() != null && employee.getEmploymentDetails().getHouseRent() != null) {
                    overrideHouseRent = BigDecimal.valueOf(employee.getEmploymentDetails().getHouseRent());
                }
                dto.setHouseRent(roundToWholeNumber(overrideHouseRent));

                BigDecimal calculatedTotal = (override.getBasicSalary() != null ? override.getBasicSalary() : BigDecimal.ZERO)
                        .add(override.getOtSalary() != null ? override.getOtSalary() : BigDecimal.ZERO)
                        .add(override.getFestivalWageSalary() != null ? override.getFestivalWageSalary() : BigDecimal.ZERO)
                        .add(attIncVal != null ? attIncVal : BigDecimal.ZERO)
                        .add(prodIncVal != null ? prodIncVal : BigDecimal.ZERO)
                        .add(overrideHouseRent != null ? overrideHouseRent : BigDecimal.ZERO);

                dto.setTotal(roundToWholeNumber(calculatedTotal));

                BigDecimal totalEarningForPF = totalEarnings;
                if ((totalEarningForPF == null || totalEarningForPF.compareTo(BigDecimal.ZERO) == 0) && calculatedTotal != null) {
                    totalEarningForPF = calculatedTotal;
                }
                dto.setTotalEarningForPF(roundToWholeNumber(totalEarningForPF));
                
                // Deductions
                BigDecimal overrideAdvance = override.getSalaryAdvanceDeduction();
                if (overrideAdvance == null || overrideAdvance.compareTo(BigDecimal.ZERO) == 0) {
                    List<EmployeeAllowanceAdvanceDTO> allowanceAdvances = getAllowancesAdvances(employee.getId(), month);
                    BigDecimal sum = BigDecimal.ZERO;
                    for (EmployeeAllowanceAdvanceDTO advance : allowanceAdvances) {
                        if ("Salary Advance".equalsIgnoreCase(advance.getType()) && advance.getAmount() != null) {
                            if (advance.getEligibleForAdvance() == null || Boolean.TRUE.equals(advance.getEligibleForAdvance())) {
                                sum = sum.add(advance.getAmount());
                            }
                        }
                    }
                    if (sum.compareTo(BigDecimal.ZERO) > 0) {
                        overrideAdvance = sum;
                    }
                }
                dto.setSalaryAdvanceDeduction(overrideAdvance != null ? overrideAdvance : BigDecimal.ZERO);
                dto.setLoanDeduction(override.getLoanDeduction());
                dto.setPfDeduction(roundToWholeNumber(override.getPfDeduction()));
                dto.setPfWageCeiling(roundToWholeNumber(pfWageCeiling));
                dto.setEsiDeduction(roundToWholeNumber(override.getEsiDeduction()));
                BigDecimal overrideCanteen = override.getCanteenDeduction();
                if (overrideCanteen == null) {
                    Optional<EmployeeCanteenDeduction> empCanteenOpt = canteenDeductionRepository.findFirstByEmployeeIdAndMonthOrderByIdDesc(employee.getId(), month);
                    if (empCanteenOpt.isPresent()) {
                        EmployeeCanteenDeduction canteenRec = empCanteenOpt.get();
                        BigDecimal dailyRate = canteenRec.getDailyDeductionRate();
                        if (dailyRate == null || dailyRate.compareTo(BigDecimal.ZERO) <= 0) {
                            if (canteenRec.getCanteenAmount() != null) {
                                dailyRate = canteenRec.getCanteenAmount().divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP);
                            }
                        }
                        if (dailyRate != null) {
                            overrideCanteen = dailyRate.multiply(BigDecimal.valueOf(actualPresentDays)).setScale(2, RoundingMode.HALF_UP);
                        } else if (canteenRec.getCanteenAmount() != null) {
                            overrideCanteen = canteenRec.getCanteenAmount();
                        }
                    }
                }
                dto.setCanteenDeduction(overrideCanteen != null ? roundToWholeNumber(overrideCanteen) : null);
                dto.setTds(override.getTds());
                
                BigDecimal totalDeduction = (overrideAdvance != null ? overrideAdvance : BigDecimal.ZERO)
                    .add(override.getLoanDeduction() != null ? override.getLoanDeduction() : BigDecimal.ZERO)
                    .add(override.getPfDeduction() != null ? override.getPfDeduction() : BigDecimal.ZERO)
                    .add(override.getEsiDeduction() != null ? override.getEsiDeduction() : BigDecimal.ZERO)
                    .add(overrideCanteen != null ? overrideCanteen : BigDecimal.ZERO)
                    .add(override.getTds() != null ? override.getTds() : BigDecimal.ZERO);
                dto.setTotalDeduction(roundToWholeNumber(totalDeduction));
                
                // Final calculations
                BigDecimal netSalary = totalEarningForPF.subtract(totalDeduction);
                dto.setNetSalary(roundToWholeNumber(netSalary));
                dto.setRemarks(override.getRemarks() != null ? override.getRemarks() : "");
                dto.setIsSalaryGenerated(override.getIsSalaryGenerated() != null ? override.getIsSalaryGenerated() : 1);
                
                // Legacy fields
                dto.setTotalWorkingHours(totalWorkingHours);
                dto.setOtHours(override.getOtHoursWorked() != null ? override.getOtHoursWorked().doubleValue() : 0.0);
                dto.setRegularSalary(override.getBasicSalary());
                dto.setPerHourSalary(perHourSalary);
                dto.setOtRatePerHour(otRatePerHour);
                dto.setFood(null);
                
                report.add(dto);
                continue;
            }

            // Calculate salary components based on requirements
            int totalDaysInPeriod = (int) java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
            
            // Days to be Present calculation based on salary type
            // Calculate dynamic days to be present first
            Integer weekOffDays = 0;
            Integer noOfLeaves = 0;
            Integer calculatedDaysToBePresent = totalDaysInPeriod;
            
            if (isDateRange) {
                if (startDate.equals(endDate)) {
                    calculatedDaysToBePresent = 1;
                } else {
                    weekOffDays = calculateWeekOffDays(startDate, endDate, employee.getShiftDetails());
                    calculatedDaysToBePresent = Math.max(1, totalDaysInPeriod - weekOffDays);
                }
            } else if (employee.getIsEligibleFor28Days() != null && employee.getIsEligibleFor28Days()) {
                calculatedDaysToBePresent = 28;
            } else {
                if (employee.getShiftDetails() != null) {
                    // Get no of leaves from employee master
                    if (employee.getShiftDetails().getNoOfLeave() != null) {
                        noOfLeaves = employee.getShiftDetails().getNoOfLeave();
                        if (totalDaysInPeriod < 28) {
                            noOfLeaves = (int) Math.round((double) noOfLeaves * totalDaysInPeriod / 30.0);
                        }
                    }
                    
                    // Calculate weekoff days from employee shift details
                    weekOffDays = calculateWeekOffDays(startDate, endDate, employee.getShiftDetails());
                    
                    // Apply different logic based on salary type
                    if (salaryDetails != null && salaryDetails.getSalaryType() != null) {
                        String salaryType = salaryDetails.getSalaryType();
                        if ("Daily".equalsIgnoreCase(salaryType)) {
                            // For Daily: Days to be Present = total days in month - no of leaves
                            calculatedDaysToBePresent = totalDaysInPeriod - noOfLeaves;
                        } else {
                            // For Monthly and others: Days to be Present = total days in month - weekoff days
                            calculatedDaysToBePresent = totalDaysInPeriod - weekOffDays;
                        }
                    } else {
                        // Default to monthly logic if salary type is not available
                        calculatedDaysToBePresent = totalDaysInPeriod - weekOffDays;
                    }
                }
            }
            
            boolean isHolDeduct = (employee.getHolidayDeductionApplicable() != null && employee.getHolidayDeductionApplicable() == 1)
                    || (employee.getEmploymentDetails() != null && employee.getEmploymentDetails().getIsHolidayDeductionApplicable() != null && employee.getEmploymentDetails().getIsHolidayDeductionApplicable() == 1);
            if (isHolDeduct && (employee.getIsEligibleFor28Days() == null || !employee.getIsEligibleFor28Days())) {
                long activeHolidaysCount = festivalHolidays.stream()
                        .filter(h -> h.getStatus() == null || !"Inactive".equalsIgnoreCase(h.getStatus()))
                        .count();
                calculatedDaysToBePresent = Math.max(0, calculatedDaysToBePresent - (int) activeHolidaysCount);
            }

            Integer daysToBePresent = calculatedDaysToBePresent;
            
            if (override != null && override.getDaysToBePresent() != null && (employee.getIsEligibleFor28Days() == null || !employee.getIsEligibleFor28Days())) {
                int defaultSundayCount = calculateDefaultWeekOffDays(startDate, endDate);
                int oldSundayDefault = totalDaysInPeriod - defaultSundayCount;
                
                boolean employeeHasNoWeekOff = (employee.getShiftDetails() == null || 
                                                employee.getShiftDetails().getWeeklyOffDay() == null || 
                                                employee.getShiftDetails().getWeeklyOffDay().trim().isEmpty() || 
                                                "no".equalsIgnoreCase(employee.getShiftDetails().getWeeklyOffDay().trim()) || 
                                                "none".equalsIgnoreCase(employee.getShiftDetails().getWeeklyOffDay().trim()));
                
                if (employeeHasNoWeekOff && override.getDaysToBePresent().equals(oldSundayDefault)) {
                    // Ignore the old default Sunday count override and use the correct new calculation
                    daysToBePresent = calculatedDaysToBePresent;
                } else {
                    daysToBePresent = override.getDaysToBePresent();
                }
            }
            
            // Working hours per day from employee master (shift details)
            if (employee.getShiftDetails() != null && employee.getShiftDetails().getTotalWorkingHours() != null) {
                workingHoursPerDay = employee.getShiftDetails().getTotalWorkingHours();
            }
            
            // Per Day Salary from Basic Salary from employee master
            BigDecimal basicSalary = null; // Don't provide default, keep null if not available
            BigDecimal perDaySalary = null;
            BigDecimal grossSalary = null;
            
            if (salaryDetails != null && salaryDetails.getBasicSalary() != null) {
                basicSalary = salaryDetails.getBasicSalary();
                
                // Check salary type to determine Gross Salary and Per Day Salary logic
                String salaryType = salaryDetails.getSalaryType();
                if ("Monthly".equalsIgnoreCase(salaryType) || "28 days".equalsIgnoreCase(salaryType)) {
                    // For Monthly: Gross Salary = basicSalary, Per Day Salary = null
                    grossSalary = basicSalary;
                    perDaySalary = null;
                } else if ("Daily".equalsIgnoreCase(salaryType)) {
                    // For Daily: Gross Salary = null, Per Day Salary = basicSalary
                    grossSalary = null;
                    perDaySalary = basicSalary;
                } else {
                    // Default behavior for other salary types
                    perDaySalary = basicSalary;
                    grossSalary = null;
                }
            }
            
            // Calculate Basic Salary and OT based on salary type
            BigDecimal calculatedBasicSalary = null;
            BigDecimal calculatedOtSalary = null;
            BigDecimal perDaySalaryForDisplay = null; // For display in Per Day Salary column
            
            // Determine effective present days for basic salary and extra OT hours if daysToBePresent is 28
            double regularPresentDays = Math.max(0.0, actualPresentDays - festivalPresentDays);
            double basicDays = regularPresentDays;
            double extraOtHours = 0.0;
            double displayedPresentDays = actualPresentDays;
            if (!isDateRange && daysToBePresent != null && daysToBePresent == 28 && actualPresentDays > 28) {
                basicDays = Math.min(28.0, regularPresentDays);
                displayedPresentDays = 28.0;
                double hrsPerDay = (workingHoursPerDay != null && workingHoursPerDay > 0) ? workingHoursPerDay : 8.0;
                extraOtHours = (actualPresentDays - 28.0) * hrsPerDay;
            }
            
            double calculatedOtHours = totalOtHours + extraOtHours;

            if (salaryDetails != null && salaryDetails.getBasicSalary() != null) {
                String salaryType = salaryDetails.getSalaryType();
                
                if ("Daily".equalsIgnoreCase(salaryType)) {
                    // For Daily: Per Day Salary = basicSalary from emp master, Basic Salary = perDaySalary * actualPresentDays
                    perDaySalaryForDisplay = salaryDetails.getBasicSalary();
                    calculatedBasicSalary = perDaySalaryForDisplay.multiply(BigDecimal.valueOf(basicDays));
                    
                    // OT = (Per Day Salary / Working Hours Per Day) * OT Hours Worked
                    double hrsPerDay = (workingHoursPerDay != null && workingHoursPerDay > 0) ? workingHoursPerDay : 8.0;
                    BigDecimal dailyPerHourSalary = perDaySalaryForDisplay.divide(BigDecimal.valueOf(hrsPerDay), 2, BigDecimal.ROUND_HALF_UP);
                    calculatedOtSalary = dailyPerHourSalary.multiply(BigDecimal.valueOf(calculatedOtHours));
                } else if ("Monthly".equalsIgnoreCase(salaryType) || "28 days".equalsIgnoreCase(salaryType)) {
                    // For Monthly: Per Day Salary = gross salary / Days to be Present, Basic Salary = perDaySalary * actualPresentDays
                    if (grossSalary != null) {
                        YearMonth standardYearMonth = YearMonth.from(startDate);
                        int stdTotalDays = standardYearMonth.lengthOfMonth();
                        LocalDate stdStart = standardYearMonth.atDay(1);
                        LocalDate stdEnd = standardYearMonth.atEndOfMonth();
                        int stdWeekOffs = 0;
                        if (employee.getShiftDetails() != null) {
                            stdWeekOffs = calculateWeekOffDays(stdStart, stdEnd, employee.getShiftDetails());
                        }
                        int stdDaysToBePresent = stdTotalDays - stdWeekOffs;
                        if (employee.getIsEligibleFor28Days() != null && employee.getIsEligibleFor28Days()) {
                            stdDaysToBePresent = 28;
                        } else {
                            if (isHolDeduct) {
                                long activeHolidaysCount = festivalHolidays.stream()
                                        .filter(h -> h.getStatus() == null || !"Inactive".equalsIgnoreCase(h.getStatus()))
                                        .count();
                                stdDaysToBePresent -= (int) activeHolidaysCount;
                            }
                        }
                        if (stdDaysToBePresent <= 0) stdDaysToBePresent = 26; // Fallback
                        
                        perDaySalaryForDisplay = grossSalary.divide(BigDecimal.valueOf(stdDaysToBePresent), 2, BigDecimal.ROUND_HALF_UP);
                        calculatedBasicSalary = perDaySalaryForDisplay.multiply(BigDecimal.valueOf(basicDays));
                        
                        // OT = Gross Salary / stdDaysToBePresent / Working hours per Day * OT Hours Worked
                        if (workingHoursPerDay != null && workingHoursPerDay > 0) {
                            BigDecimal monthlyPerHourSalary = perDaySalaryForDisplay.divide(BigDecimal.valueOf(workingHoursPerDay), 2, BigDecimal.ROUND_HALF_UP);
                            calculatedOtSalary = monthlyPerHourSalary.multiply(BigDecimal.valueOf(calculatedOtHours));
                        }
                    }
                }
            }
            
            // Apply overrides for Earnings (ignore basic salary override if eligible for 28 days to prevent mismatch)
            if (override != null && (employee.getIsEligibleFor28Days() == null || !employee.getIsEligibleFor28Days())) {
                if (override.getBasicSalary() != null) calculatedBasicSalary = override.getBasicSalary();
            }
            
            // Use calculated OT salary - always use calculation as per requirements (ignore OT override if eligible for 28 days)
            BigDecimal otSalary = calculatedOtSalary;
            if (override != null && override.getOtSalary() != null && (employee.getIsEligibleFor28Days() == null || !employee.getIsEligibleFor28Days())) {
                otSalary = override.getOtSalary();
            }
            
            // Incentives & Allowances - Calculate based on applicable flags
            BigDecimal attendanceIncentive = null; // Empty ""
            BigDecimal productionIncentive = null; // Empty ""
            BigDecimal foodAllowance = null;
            BigDecimal food = null; // New food column - leave as empty
            
            // Basic Information section: Fetch attendance and production incentives from Employee table if applicable
            Integer attendanceIncentiveApplicable = (employee.getAttendanceIncentiveApplicable() != null) ? employee.getAttendanceIncentiveApplicable() : 0;
            Integer productionIncentiveApplicable = (employee.getProductionIncentiveApplicable() != null) ? employee.getProductionIncentiveApplicable() : 0;
            
            // Fetch incentive values directly from Employee table if applicable flags are 1
            if (attendanceIncentiveApplicable == 1 && employee.getAttendanceIncentive() != null) {
                attendanceIncentive = BigDecimal.valueOf(employee.getAttendanceIncentive());
            }
            
            if (productionIncentiveApplicable == 1 && employee.getProductionIncentive() != null) {
                productionIncentive = BigDecimal.valueOf(employee.getProductionIncentive());
            }
            
            // Check if food allowance is applicable (foodAllowanceApplicable = 1)
            Integer foodAllowanceApplicable = (employee.getFoodAllowanceApplicable() != null) ? employee.getFoodAllowanceApplicable() : 0;
            if (foodAllowanceApplicable == 1 && actualPresentDays > 0) {
                foodAllowance = BigDecimal.valueOf(actualPresentDays * 50);
            }
            
            // Actual Earnings section: Apply conditional logic for Att. Incentive and Prod. Incentive
            BigDecimal attIncentive = null; // Att. Incentive for Actual Earnings section
            BigDecimal prodIncentive = null; // Prod. Incentive for Actual Earnings section
            
            // Logic: IF ActualPresentDays < DaysToBePresent THEN incentives = 0, ELSE calculate as incentive * ActualPresentDays
            if (daysToBePresent != null && actualPresentDays > 0) {
                if (actualPresentDays < daysToBePresent) {
                    // ActualPresentDays < DaysToBePresent: incentives = 0
                    attIncentive = BigDecimal.ZERO;
                    prodIncentive = BigDecimal.ZERO;
                } else {
                    // ActualPresentDays >= DaysToBePresent: calculate incentives
                    if (attendanceIncentiveApplicable == 1 && employee.getAttendanceIncentive() != null) {
                        attIncentive = BigDecimal.valueOf(employee.getAttendanceIncentive()).multiply(BigDecimal.valueOf(actualPresentDays));
                    } else {
                        attIncentive = BigDecimal.ZERO;
                    }
                    if (productionIncentiveApplicable == 1 && employee.getProductionIncentive() != null) {
                        prodIncentive = BigDecimal.valueOf(employee.getProductionIncentive()).multiply(BigDecimal.valueOf(actualPresentDays));
                    } else {
                        prodIncentive = BigDecimal.ZERO;
                    }
                }
            } else {
                // Default case: if no valid days, set incentives to 0
                attIncentive = BigDecimal.ZERO;
                prodIncentive = BigDecimal.ZERO;
            }
            
            String queryMonth = String.format("%04d-%02d", startDate.getYear(), startDate.getMonthValue());

            // Check for Attendance Incentive entry from dynamic HR module
            Optional<EmployeeAttendanceIncentive> empIncentiveOpt = incentiveRepository.findFirstByEmployeeIdAndMonthOrderByIdDesc(employee.getId(), queryMonth);
            if (empIncentiveOpt.isPresent()) {
                EmployeeAttendanceIncentive incRec = empIncentiveOpt.get();
                if ("Daily".equalsIgnoreCase(incRec.getIncentiveType()) && incRec.getDailyRate() != null) {
                    attIncentive = incRec.getDailyRate().multiply(BigDecimal.valueOf(actualPresentDays)).setScale(2, RoundingMode.HALF_UP);
                } else if (incRec.getAmount() != null) {
                    attIncentive = isDateRange
                            ? incRec.getAmount().divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(actualPresentDays))
                            : incRec.getAmount();
                }
                attendanceIncentive = attIncentive;
            }

            // Check for Production Incentive entry from dynamic HR module
            Optional<EmployeeProductionIncentive> empProdIncentiveOpt = productionIncentiveRepository.findFirstByEmployeeIdAndMonthOrderByIdDesc(employee.getId(), queryMonth);
            if (empProdIncentiveOpt.isPresent()) {
                EmployeeProductionIncentive prodRec = empProdIncentiveOpt.get();
                if ("Daily".equalsIgnoreCase(prodRec.getIncentiveType()) && prodRec.getDailyRate() != null) {
                    prodIncentive = prodRec.getDailyRate().multiply(BigDecimal.valueOf(actualPresentDays)).setScale(2, RoundingMode.HALF_UP);
                } else if (prodRec.getAmount() != null) {
                    prodIncentive = isDateRange
                            ? prodRec.getAmount().divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(actualPresentDays))
                            : prodRec.getAmount();
                }
                productionIncentive = prodIncentive;
            }

            // Apply overrides only if allowance is applicable from employee master
            // This ensures that when employee master flags are changed, they take precedence
            if (override != null) {
                // Override attendance incentive unconditionally if present in override
                if (override.getAttendanceIncentive() != null) {
                    attendanceIncentive = override.getAttendanceIncentive();
                }
                // Override production incentive unconditionally if present in override
                if (override.getProductionIncentive() != null) {
                    productionIncentive = override.getProductionIncentive();
                }
                // Override Att. Incentive and Prod. Incentive for Actual Earnings section
                if (override.getAttIncentive() != null) {
                    attIncentive = override.getAttIncentive();
                }
                if (override.getProdIncentive() != null) {
                    prodIncentive = override.getProdIncentive();
                }
                // Override food allowance unconditionally if present in override
                if (override.getFoodAllowance() != null) {
                    foodAllowance = override.getFoodAllowance();
                }
            }
            
            // Calculate Festival Wage Salary: (Hourly Rate) * Festival Working Hours
            BigDecimal calculatedFestivalSalary = BigDecimal.ZERO;
            boolean isFestWageApplicableForCalc = (employee.getFestivalWagesApplicable() != null && employee.getFestivalWagesApplicable() == 1)
                    || (employee.getEmploymentDetails() != null && employee.getEmploymentDetails().getIsFestivalWageApplicable() != null && employee.getEmploymentDetails().getIsFestivalWageApplicable() == 1);
            if (isFestWageApplicableForCalc && totalFestivalHours > 0) {
                double hrsPerDay = (workingHoursPerDay != null && workingHoursPerDay > 0) ? workingHoursPerDay : 8.0;
                if (perDaySalaryForDisplay != null && perDaySalaryForDisplay.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal hourlyRate = perDaySalaryForDisplay.divide(BigDecimal.valueOf(hrsPerDay), 2, BigDecimal.ROUND_HALF_UP);
                    calculatedFestivalSalary = hourlyRate.multiply(BigDecimal.valueOf(totalFestivalHours));
                }
            }

            BigDecimal festivalWageSalary = calculatedFestivalSalary;
            if (override != null && override.getFestivalWageSalary() != null) {
                festivalWageSalary = override.getFestivalWageSalary();
            }

            BigDecimal houseRentVal = null;
            if (!isDateRange) {
                if (employee.getEmploymentDetails() != null && employee.getEmploymentDetails().getHouseRent() != null) {
                    houseRentVal = BigDecimal.valueOf(employee.getEmploymentDetails().getHouseRent());
                }
                if (override != null && override.getHouseRent() != null) {
                    houseRentVal = override.getHouseRent();
                }
            }

            // TOTAL Earnings (Intermediate) = Basic Salary + OT + Festival Wage Salary + AttIncentive + ProdIncentive + HouseRent
            BigDecimal total = (calculatedBasicSalary != null ? calculatedBasicSalary : BigDecimal.ZERO)
                    .add(otSalary != null ? otSalary : BigDecimal.ZERO)
                    .add(festivalWageSalary != null ? festivalWageSalary : BigDecimal.ZERO)
                    .add(attIncentive != null ? attIncentive : BigDecimal.ZERO)
                    .add(prodIncentive != null ? prodIncentive : BigDecimal.ZERO)
                    .add(houseRentVal != null ? houseRentVal : BigDecimal.ZERO);
            // Calculate basicSalaryRatio for pro-rating allowances
            BigDecimal basicSalaryRatio = BigDecimal.ONE;
            if (calculatedBasicSalary != null && salaryDetails != null && salaryDetails.getBasicSalary() != null && salaryDetails.getBasicSalary().compareTo(BigDecimal.ZERO) > 0) {
                basicSalaryRatio = calculatedBasicSalary.divide(salaryDetails.getBasicSalary(), 4, BigDecimal.ROUND_HALF_UP);
            }

            // Salary Components - Calculate based on requirements
            // 1. Basic+DA = (Basic salary * conveyanceAllowance / 100) * basicDaPercent
            BigDecimal basicDA = null;
            if (calculatedBasicSalary != null) {
                BigDecimal daValue = (salaryDetails != null && salaryDetails.getConveyanceAllowance() != null)
                    ? salaryDetails.getConveyanceAllowance()
                    : BigDecimal.ZERO;
                BigDecimal baseBasicDA = calculatedBasicSalary.multiply(daValue).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                
                Double basicDaPct = employee.getBasicDaPercent();
                if (basicDaPct != null && basicDaPct > 0) {
                    basicDA = baseBasicDA.multiply(BigDecimal.valueOf(basicDaPct)).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    basicDA = baseBasicDA;
                }
            }
            
            // 2. HRA = (HRA from Salary Details * basicSalaryRatio) * hraPercent
            BigDecimal hra = BigDecimal.ZERO;
            if (calculatedBasicSalary != null) {
                BigDecimal hraValue = (salaryDetails != null && salaryDetails.getHra() != null)
                    ? salaryDetails.getHra()
                    : BigDecimal.ZERO;
                BigDecimal baseHRA = hraValue.multiply(basicSalaryRatio);
                
                Double hraPct = employee.getHraPercent();
                if (hraPct != null && hraPct > 0) {
                    hra = baseHRA.multiply(BigDecimal.valueOf(hraPct)).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (salaryDetails != null && salaryDetails.getHra() != null) {
                    hra = calculatedBasicSalary.multiply(salaryDetails.getHra())
                                              .divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                }
            }

            // 3. TA = (TA from Salary Details * basicSalaryRatio) * taPercent
            BigDecimal ta = null;
            if (calculatedBasicSalary != null) {
                BigDecimal taValue = (salaryDetails != null && salaryDetails.getSpecialAllowance() != null)
                    ? salaryDetails.getSpecialAllowance()
                    : BigDecimal.ZERO;
                BigDecimal baseTA = taValue.multiply(basicSalaryRatio);
                
                Double taPct = employee.getTaPercent();
                if (taPct != null && taPct > 0) {
                    ta = baseTA.multiply(BigDecimal.valueOf(taPct)).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }

            // 4. Medical = (Medical Allowance from Salary Details * basicSalaryRatio) * medicalPercent
            BigDecimal medical = BigDecimal.ZERO;
            if (calculatedBasicSalary != null) {
                BigDecimal medicalValue = (salaryDetails != null && salaryDetails.getMedicalAllowance() != null)
                    ? salaryDetails.getMedicalAllowance()
                    : BigDecimal.ZERO;
                BigDecimal baseMedical = medicalValue.multiply(basicSalaryRatio);
                
                Double medicalPct = employee.getMedicalPercent();
                if (medicalPct != null && medicalPct > 0) {
                    medical = baseMedical.multiply(BigDecimal.valueOf(medicalPct)).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else if (salaryDetails != null && salaryDetails.getMedicalAllowance() != null) {
                    medical = salaryDetails.getMedicalAllowance();
                }
            }
            
            // Apply manual overrides for basicDA, hra, ta, and medical if present
            if (override != null) {
                if (override.getBasicDA() != null) basicDA = override.getBasicDA();
                if (override.getHra() != null) hra = override.getHra();
                if (override.getTa() != null) ta = override.getTa();
                if (override.getMedical() != null) medical = override.getMedical();
            }
            
            // Total Earnings = sum of basicDA + hra + ta + medical
            BigDecimal totalEarnings = (basicDA != null ? basicDA : BigDecimal.ZERO)
                                      .add(hra != null ? hra : BigDecimal.ZERO)
                                      .add(ta != null ? ta : BigDecimal.ZERO)
                                      .add(medical != null ? medical : BigDecimal.ZERO);
            
            // Total Earning for PF is always the sum of basicDA, hra, ta, and medical
            BigDecimal totalEarning = totalEarnings;
            if (totalEarning.compareTo(BigDecimal.ZERO) == 0 && total != null) {
                totalEarning = total;
            }
            
            // Salary Advance - Fetch from allowance-advance data for the employee and month or date range
            BigDecimal salaryAdvanceDeduction = BigDecimal.ZERO; // Default to 0
            List<EmployeeAllowanceAdvanceDTO> allowanceAdvances = isDateRange
                    ? getAllowancesAdvancesByDateRange(employee.getId(), startDate, endDate)
                    : getAllowancesAdvances(employee.getId(), month);

            // Sum up all "Salary Advance" amounts for this employee
            for (EmployeeAllowanceAdvanceDTO advance : allowanceAdvances) {
                if ("Salary Advance".equalsIgnoreCase(advance.getType()) && advance.getAmount() != null) {
                    if (advance.getEligibleForAdvance() == null || Boolean.TRUE.equals(advance.getEligibleForAdvance())) {
                        salaryAdvanceDeduction = salaryAdvanceDeduction.add(advance.getAmount());
                    }
                }
            }

            if (!isDateRange && override != null && override.getSalaryAdvanceDeduction() != null) salaryAdvanceDeduction = override.getSalaryAdvanceDeduction();
            
            // Loan Deduction - Fetch from employee loans active for this month (only for month view)
            BigDecimal loanDeduction = BigDecimal.ZERO;
            if (!isDateRange) {
                List<EmployeeLoan> employeeLoans = employeeLoanRepository.findByEmployeeId(employee.getId());
                for (EmployeeLoan loan : employeeLoans) {
                    if (loan.getInstallmentAmount() != null) {
                        if (isMonthActiveForLoan(loan, month)) {
                            if ("Active".equalsIgnoreCase(loan.getStatus())) {
                                loanDeduction = loanDeduction.add(loan.getInstallmentAmount());
                            }
                        }
                    }
                }
                if (override != null && override.getLoanDeduction() != null) loanDeduction = override.getLoanDeduction();
            }
            
            // Statutory Deductions - Calculate based on requirements and applicable flags
            // Check PF and ESI applicability from employee master
            Boolean pfApplicable = (employee.getPfApplicable() != null) ? employee.getPfApplicable() : false;
            Boolean esiApplicable = (employee.getEsiApplicable() != null) ? employee.getEsiApplicable() : false;
            
            // 3. PF - Dynamic percentage = If (Basic+DA) is less than 15000 then (Basic+DA)*pfPercent else 15000*pfPercent
            BigDecimal pfDeduction = null;
            if (pfApplicable && basicDA != null) {
                Double pfPercentage = employee.getPfPercent();
                if (pfPercentage != null && pfPercentage > 0) {
                    if (true) {
                        if (basicDA.compareTo(pfWageCeiling) < 0) {
                            pfDeduction = basicDA.multiply(BigDecimal.valueOf(pfPercentage)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        } else {
                            pfDeduction = pfWageCeiling.multiply(BigDecimal.valueOf(pfPercentage)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                    } else {
                        /* no-op */
                    }
                }
            }
            
            // Override PF deduction if present in override
            if (override != null && override.getPfDeduction() != null) {
                pfDeduction = override.getPfDeduction();
            }
            
            // 4. ESI - Fixed 0.75% = Case 1>> In emp master If Salary type is Monthly and that Basic salary is less than 21000 then Total Earning * 0.0075
            // case 2>>In emp master If Salary type is Daily then (26*Basic salary from emp master) is less than 21000 then Total Earning * 0.0075 ELSE" - "
            BigDecimal esiDeduction = null;
            if (esiApplicable && salaryDetails != null && salaryDetails.getSalaryType() != null && totalEarning != null) {
                String salaryType = salaryDetails.getSalaryType();
                BigDecimal esiPercentage = BigDecimal.valueOf(0.0075); // Fixed 0.75% rate
                
                if ("Monthly".equalsIgnoreCase(salaryType)) {
                    // Case 1: Monthly salary type
                    if (salaryDetails.getBasicSalary() != null && 
                        salaryDetails.getBasicSalary().compareTo(BigDecimal.valueOf(21000)) < 0) {
                        esiDeduction = totalEarning.multiply(esiPercentage).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                } else if ("Daily".equalsIgnoreCase(salaryType)) {
                    // Case 2: Daily salary type
                    if (salaryDetails.getBasicSalary() != null) {
                        BigDecimal monthlyBasicFromDaily = salaryDetails.getBasicSalary().multiply(BigDecimal.valueOf(26));
                        if (monthlyBasicFromDaily.compareTo(BigDecimal.valueOf(21000)) < 0) {
                            esiDeduction = totalEarning.multiply(esiPercentage).setScale(2, BigDecimal.ROUND_HALF_UP);
                        }
                    }
                }
            }
            
            // Override ESI deduction if present in override
            if (override != null && override.getEsiDeduction() != null) {
                esiDeduction = override.getEsiDeduction();
            }
            
            // Canteen Deduction - Handle based on EmployeeCanteenDeduction module or isCanteen flag
            BigDecimal canteenDeduction = null;

            String canteenMonth = String.format("%04d-%02d", startDate.getYear(), startDate.getMonthValue());
            Optional<EmployeeCanteenDeduction> empCanteenOpt = canteenDeductionRepository.findFirstByEmployeeIdAndMonthOrderByIdDesc(employee.getId(), canteenMonth);
            if (empCanteenOpt.isPresent()) {
                EmployeeCanteenDeduction canteenRec = empCanteenOpt.get();
                BigDecimal dailyRate = canteenRec.getDailyDeductionRate();
                if (dailyRate == null || dailyRate.compareTo(BigDecimal.ZERO) <= 0) {
                    if (canteenRec.getCanteenAmount() != null) {
                        dailyRate = canteenRec.getCanteenAmount().divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP);
                    }
                }
                if (dailyRate != null) {
                    // Only deduct for actual present days - leave days are not deducted!
                    canteenDeduction = dailyRate.multiply(BigDecimal.valueOf(actualPresentDays)).setScale(2, RoundingMode.HALF_UP);
                } else if (canteenRec.getCanteenAmount() != null) {
                    canteenDeduction = isDateRange
                            ? canteenRec.getCanteenAmount().divide(new BigDecimal("30"), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(actualPresentDays))
                            : canteenRec.getCanteenAmount();
                }
            } else {
                Integer isCanteenFlag = (salaryDetails != null && salaryDetails.getIsCanteen() != null) 
                                       ? salaryDetails.getIsCanteen() : 0; // Default to 0
                
                if (isCanteenFlag == 0 && foodAllowance != null && foodAllowanceApplicable == 1) {
                    // isCanteen = 0 means display Food Allowance data in Canteen column
                    canteenDeduction = foodAllowance;
                }
            }

            // TDS - Set as empty ""
            BigDecimal tds = null; // Empty ""
            
            if (!isDateRange && override != null) {
                // Override canteen deduction unconditionally if present in override
                if (override.getCanteenDeduction() != null) {
                    canteenDeduction = override.getCanteenDeduction();
                }
                if (override.getTds() != null) tds = override.getTds();
            }
            
            // Total Deduction = Salary Advance + Loan Deduction + PF + ESI + Canteen + TDS
            BigDecimal totalDeduction = null;
            // Use override totalDeduction if available, otherwise calculate
            if (override != null && override.getTotalDeduction() != null) {
                totalDeduction = override.getTotalDeduction();
            } else if (salaryAdvanceDeduction != null || loanDeduction != null || pfDeduction != null || esiDeduction != null || canteenDeduction != null || tds != null) {
                totalDeduction = (salaryAdvanceDeduction != null ? salaryAdvanceDeduction : BigDecimal.ZERO)
                                .add(loanDeduction != null ? loanDeduction : BigDecimal.ZERO)
                                .add(pfDeduction != null ? pfDeduction : BigDecimal.ZERO)
                                .add(esiDeduction != null ? esiDeduction : BigDecimal.ZERO)
                                .add(canteenDeduction != null ? canteenDeduction : BigDecimal.ZERO)
                                .add(tds != null ? tds : BigDecimal.ZERO);
            }
            // Net Salary = Total Earning - Total Deduction
            BigDecimal netSalary = null;
            // Use override netSalary if available, otherwise calculate
            if (override != null && override.getNetSalary() != null) {
                netSalary = override.getNetSalary();
            } else if (totalEarning != null && totalDeduction != null) {
                netSalary = totalEarning.subtract(totalDeduction);
            }

            // Populate DTO
            SalaryReportDTO dto = new SalaryReportDTO();
            dto.setEmployeeId(employee.getId());
            dto.setEmployeeCode(employee.getEmployeeCode());
            dto.setEmployeeName(employee.getEmployeeName());
            dto.setDepartment(employee.getEmploymentDetails() != null ? employee.getEmploymentDetails().getDepartment() : employee.getDepartment());
            dto.setDesignation(employee.getDesignation() != null ? employee.getDesignation() : "Employee");
            dto.setCategory(employee.getCategory());
            dto.setMonth(month);
            dto.setSalaryType(salaryDetails != null ? salaryDetails.getSalaryType() : null);
            dto.setCompanyId(null); // Default company ID
            
            // Working days and hours
            dto.setDaysToBePresent(daysToBePresent);
            dto.setWorkingHoursPerDay(workingHoursPerDay);
            dto.setActualPresentDays(displayedPresentDays);
            dto.setAdditionalWeekOffDays(null); // Empty "" as per requirements
            dto.setOtHoursWorked(calculatedOtHours);
            dto.setFestivalHoursWorked(totalFestivalHours);
            
            // Earnings - Apply rounding to specified columns
            dto.setGrossSalary(grossSalary);
            dto.setPerDaySalary(roundToWholeNumber(perDaySalaryForDisplay)); // Rounded
            dto.setBasicSalary(roundToWholeNumber(calculatedBasicSalary)); // Rounded
            dto.setOtSalary(roundToWholeNumber(otSalary)); // Rounded
            dto.setFestivalWageSalary(roundToWholeNumber(festivalWageSalary));
            dto.setAttendanceIncentive(override != null && override.getAttendanceIncentive() != null ? override.getAttendanceIncentive() : attendanceIncentive); 
            dto.setProductionIncentive(override != null && override.getProductionIncentive() != null ? override.getProductionIncentive() : productionIncentive); 
            dto.setFoodAllowance(foodAllowance); // Use calculated foodAllowance (already respects foodAllowanceApplicable) 
            dto.setAttIncentive(override != null && override.getAttIncentive() != null ? override.getAttIncentive() : attIncentive); 
            dto.setProdIncentive(override != null && override.getProdIncentive() != null ? override.getProdIncentive() : prodIncentive); 
            dto.setFoodAllowanceAmount(foodAllowance); // Use calculated foodAllowance (already respects foodAllowanceApplicable) 
            dto.setTotalEarnings(roundToWholeNumber(totalEarnings)); // Rounded
            dto.setTotal(roundToWholeNumber(total)); // TOTAL field for frontend - Rounded
            
            // Salary Components (Leave Empty) - Apply rounding to specified columns
            dto.setBasicDA(roundToWholeNumber(basicDA)); // Rounded
            dto.setHra(roundToWholeNumber(hra)); // Rounded
            dto.setTa(ta); // Empty ""
            dto.setMedical(medical); // Fetch from Employee Master
            
            dto.setHouseRent(roundToWholeNumber(houseRentVal));
            
            dto.setTotalEarningForPF(roundToWholeNumber(totalEarning)); // Total Earning
            
            // Deductions - Apply rounding to specified columns
            dto.setSalaryAdvanceDeduction(override != null && override.getSalaryAdvanceDeduction() != null ? override.getSalaryAdvanceDeduction() : salaryAdvanceDeduction); // Check override first
            dto.setLoanDeduction(override != null && override.getLoanDeduction() != null ? override.getLoanDeduction() : roundToWholeNumber(loanDeduction));
            dto.setPfDeduction(roundToWholeNumber(pfDeduction)); // Rounded
            dto.setPfWageCeiling(roundToWholeNumber(pfWageCeiling));
            dto.setEsiDeduction(roundToWholeNumber(esiDeduction)); // Rounded
            dto.setCanteenDeduction(canteenDeduction); // Actual Present Days × 50
            dto.setTds(tds); // Empty ""
            dto.setTotalDeduction(roundToWholeNumber(totalDeduction)); // Rounded

            // Final calculations - Apply rounding to specified columns
            dto.setNetSalary(roundToWholeNumber(netSalary)); // Rounded
            dto.setRemarks(override != null && override.getRemarks() != null ? override.getRemarks() : ""); // Use override if available
            dto.setIsSalaryGenerated(isDateRange ? 0 : (override != null ? (override.getIsSalaryGenerated() != null ? override.getIsSalaryGenerated() : 0) : 0));

            // Legacy fields for backward compatibility
            dto.setTotalWorkingHours(totalWorkingHours);
            dto.setOtHours(calculatedOtHours);
            dto.setRegularSalary(calculatedBasicSalary);
            dto.setPerHourSalary(perHourSalary);
            dto.setOtRatePerHour(otRatePerHour);
            dto.setFood(food); // Set the new food field as empty

            report.add(dto);
        }

        return report;
    }

    @Transactional
    public List<EmployeeAllowanceAdvanceDTO> saveAllowancesAdvances(List<EmployeeAllowanceAdvanceDTO> dtos) {
        List<EmployeeAllowanceAdvance> savedEntities = new ArrayList<>();
        for (EmployeeAllowanceAdvanceDTO dto : dtos) {
            EmployeeAllowanceAdvance entity = new EmployeeAllowanceAdvance();
            entity.setEmployee(employeeRepository.findById(dto.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found")));
            entity.setType(dto.getType());
            entity.setAmount(dto.getAmount());
            entity.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
            entity.setMonth(dto.getMonth());
            savedEntities.add(allowanceAdvanceRepository.save(entity));
        }
        return savedEntities.stream().map(EmployeeAllowanceAdvanceDTO::new).collect(Collectors.toList());
    }

    public List<EmployeeAllowanceAdvanceDTO> getAllowancesAdvances(Long employeeId, String month) {
        return allowanceAdvanceRepository.findByEmployeeIdAndMonth(employeeId, month).stream()
                .map(EmployeeAllowanceAdvanceDTO::new)
                .collect(Collectors.toList());
    }

    public List<EmployeeAllowanceAdvanceDTO> getAllowancesAdvancesByDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        return allowanceAdvanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate, endDate).stream()
                .map(EmployeeAllowanceAdvanceDTO::new)
                .collect(Collectors.toList());
    }

    // Check if salary report exists for employee, month, and year
    public boolean existsByEmployeeIdAndMonthAndYear(Long employeeId, Integer month, Integer year) {
        return overrideRepository.existsByEmployeeIdAndMonthAndYear(employeeId, month, year);
    }

    private void syncIncentiveFields(SalaryReportOverride source, SalaryReportOverride target) {
        BigDecimal attVal = source.getAttIncentive() != null ? source.getAttIncentive() : source.getAttendanceIncentive();
        if (attVal != null) {
            target.setAttendanceIncentive(attVal);
            target.setAttIncentive(attVal);
        } else {
            if (source.getAttendanceIncentive() != null) target.setAttendanceIncentive(source.getAttendanceIncentive());
            if (source.getAttIncentive() != null) target.setAttIncentive(source.getAttIncentive());
        }

        BigDecimal prodVal = source.getProdIncentive() != null ? source.getProdIncentive() : source.getProductionIncentive();
        if (prodVal != null) {
            target.setProductionIncentive(prodVal);
            target.setProdIncentive(prodVal);
        } else {
            if (source.getProductionIncentive() != null) target.setProductionIncentive(source.getProductionIncentive());
            if (source.getProdIncentive() != null) target.setProdIncentive(source.getProdIncentive());
        }
    }

    // Create new salary report record
    @Transactional
    public SalaryReportOverride createSalaryReportOverride(SalaryReportOverride override) {
        // Check if record already exists
        if (existsByEmployeeIdAndMonthAndYear(override.getEmployeeId(), override.getMonth(), override.getYear())) {
            throw new RuntimeException("Salary report already exists for employee " + override.getEmployeeId() + 
                                      " for month " + override.getMonth() + " and year " + override.getYear());
        }
        
        syncIncentiveFields(override, override);
        // Set isSalaryGenerated to 1 for new records
        override.setIsSalaryGenerated(1);
        return overrideRepository.save(override);
    }

    // Update existing salary report record
    @Transactional
    public SalaryReportOverride updateSalaryReportOverride(SalaryReportOverride override) {
        writeDebugFile(override, "backend_single_save_debug.json");
        Optional<SalaryReportOverride> existing = overrideRepository.findByEmployeeIdAndMonthAndYearAndCompanyId(
                override.getEmployeeId(), override.getMonth(), override.getYear(), override.getCompanyId());

        if (!existing.isPresent()) {
            throw new RuntimeException("No salary report found for employee " + override.getEmployeeId() + 
                                      " for month " + override.getMonth() + " and year " + override.getYear());
        }

        SalaryReportOverride entity = existing.get();
        // Update only non-null fields from override
        if (override.getBasicSalary() != null) entity.setBasicSalary(override.getBasicSalary());
        if (override.getOtSalary() != null) entity.setOtSalary(override.getOtSalary());
        if (override.getFestivalWageSalary() != null) entity.setFestivalWageSalary(override.getFestivalWageSalary());
        syncIncentiveFields(override, entity);
        if (override.getFoodAllowance() != null) entity.setFoodAllowance(override.getFoodAllowance());
        if (override.getFoodAllowanceAmount() != null) entity.setFoodAllowanceAmount(override.getFoodAllowanceAmount());
        if (override.getSalaryAdvanceDeduction() != null) entity.setSalaryAdvanceDeduction(override.getSalaryAdvanceDeduction());
        if (override.getPfDeduction() != null) entity.setPfDeduction(override.getPfDeduction());
        if (override.getEsiDeduction() != null) entity.setEsiDeduction(override.getEsiDeduction());
        if (override.getCanteenDeduction() != null) entity.setCanteenDeduction(override.getCanteenDeduction());
        if (override.getTds() != null) entity.setTds(override.getTds());
        if (override.getTotal() != null) entity.setTotal(override.getTotal());
        if (override.getTotalEarningForPF() != null) entity.setTotalEarningForPF(override.getTotalEarningForPF());
        if (override.getTotalDeduction() != null) entity.setTotalDeduction(override.getTotalDeduction());
        if (override.getNetSalary() != null) entity.setNetSalary(override.getNetSalary());
        if (override.getActualPresentDays() != null) entity.setActualPresentDays(override.getActualPresentDays());
        if (override.getOtHoursWorked() != null) entity.setOtHoursWorked(override.getOtHoursWorked());
        if (override.getFestivalHoursWorked() != null) entity.setFestivalHoursWorked(override.getFestivalHoursWorked());
        if (override.getDaysToBePresent() != null) entity.setDaysToBePresent(override.getDaysToBePresent());
        if (override.getPerDaySalary() != null) entity.setPerDaySalary(override.getPerDaySalary());
        if (override.getBasicDA() != null) entity.setBasicDA(override.getBasicDA());
        if (override.getHra() != null) entity.setHra(override.getHra());
        if (override.getTa() != null) entity.setTa(override.getTa());
        if (override.getMedical() != null) entity.setMedical(override.getMedical());
        if (override.getHouseRent() != null) entity.setHouseRent(override.getHouseRent());
        if (override.getRemarks() != null) entity.setRemarks(override.getRemarks());
        if (override.getDepartment() != null) entity.setDepartment(override.getDepartment());
        if (override.getDesignation() != null) entity.setDesignation(override.getDesignation());
        if (override.getUpdatedBy() != null) entity.setUpdatedBy(override.getUpdatedBy());
        // Set isSalaryGenerated to 1 when data is updated
        entity.setIsSalaryGenerated(1);
        return overrideRepository.save(entity);
    }

    // Bulk create multiple salary report records
    @Transactional
    public List<SalaryReportOverride> bulkCreateSalaryReportOverrides(List<SalaryReportOverride> overrides) {
        List<SalaryReportOverride> savedRecords = new ArrayList<>();
        
        for (SalaryReportOverride override : overrides) {
            // Check if record already exists
            if (existsByEmployeeIdAndMonthAndYear(override.getEmployeeId(), override.getMonth(), override.getYear())) {
                throw new RuntimeException("Salary report already exists for employee " + override.getEmployeeId() + 
                                          " for month " + override.getMonth() + " and year " + override.getYear());
            }
            
            syncIncentiveFields(override, override);
            // Set isSalaryGenerated to 1 for new records
            override.setIsSalaryGenerated(1);
            savedRecords.add(overrideRepository.save(override));
        }
        
        return savedRecords;
    }

    // Bulk update multiple salary report records
    @Transactional
    public List<SalaryReportOverride> bulkUpdateSalaryReportOverrides(List<SalaryReportOverride> overrides) {
        List<SalaryReportOverride> updatedRecords = new ArrayList<>();
        
        for (SalaryReportOverride override : overrides) {
            Optional<SalaryReportOverride> existing = overrideRepository.findByEmployeeIdAndMonthAndYearAndCompanyId(
                    override.getEmployeeId(), override.getMonth(), override.getYear(), override.getCompanyId());

            if (!existing.isPresent()) {
                throw new RuntimeException("No salary report found for employee " + override.getEmployeeId() + 
                                          " for month " + override.getMonth() + " and year " + override.getYear());
            }

            SalaryReportOverride entity = existing.get();
            // Update only non-null fields from override
            if (override.getBasicSalary() != null) entity.setBasicSalary(override.getBasicSalary());
            if (override.getOtSalary() != null) entity.setOtSalary(override.getOtSalary());
            if (override.getFestivalWageSalary() != null) entity.setFestivalWageSalary(override.getFestivalWageSalary());
            syncIncentiveFields(override, entity);
            if (override.getFoodAllowance() != null) entity.setFoodAllowance(override.getFoodAllowance());
            if (override.getFoodAllowanceAmount() != null) entity.setFoodAllowanceAmount(override.getFoodAllowanceAmount());
            if (override.getSalaryAdvanceDeduction() != null) entity.setSalaryAdvanceDeduction(override.getSalaryAdvanceDeduction());
            if (override.getLoanDeduction() != null) entity.setLoanDeduction(override.getLoanDeduction());
            if (override.getPfDeduction() != null) entity.setPfDeduction(override.getPfDeduction());
            if (override.getEsiDeduction() != null) entity.setEsiDeduction(override.getEsiDeduction());
            if (override.getCanteenDeduction() != null) entity.setCanteenDeduction(override.getCanteenDeduction());
            if (override.getTds() != null) entity.setTds(override.getTds());
            if (override.getTotal() != null) entity.setTotal(override.getTotal());
            if (override.getTotalEarningForPF() != null) entity.setTotalEarningForPF(override.getTotalEarningForPF());
            if (override.getTotalDeduction() != null) entity.setTotalDeduction(override.getTotalDeduction());
            if (override.getNetSalary() != null) entity.setNetSalary(override.getNetSalary());
            if (override.getActualPresentDays() != null) entity.setActualPresentDays(override.getActualPresentDays());
            if (override.getOtHoursWorked() != null) entity.setOtHoursWorked(override.getOtHoursWorked());
            if (override.getFestivalHoursWorked() != null) entity.setFestivalHoursWorked(override.getFestivalHoursWorked());
            if (override.getDaysToBePresent() != null) entity.setDaysToBePresent(override.getDaysToBePresent());
            if (override.getPerDaySalary() != null) entity.setPerDaySalary(override.getPerDaySalary());
            if (override.getBasicDA() != null) entity.setBasicDA(override.getBasicDA());
            if (override.getHra() != null) entity.setHra(override.getHra());
            if (override.getTa() != null) entity.setTa(override.getTa());
            if (override.getMedical() != null) entity.setMedical(override.getMedical());
            if (override.getHouseRent() != null) entity.setHouseRent(override.getHouseRent());
            if (override.getRemarks() != null) entity.setRemarks(override.getRemarks());
            if (override.getDepartment() != null) entity.setDepartment(override.getDepartment());
            if (override.getDesignation() != null) entity.setDesignation(override.getDesignation());
            if (override.getUpdatedBy() != null) entity.setUpdatedBy(override.getUpdatedBy());
            // Set isSalaryGenerated to 1 when data is updated
            entity.setIsSalaryGenerated(1);
            updatedRecords.add(overrideRepository.save(entity));
        }
        
        return updatedRecords;
    }

    // Bulk upsert (create or update) multiple salary report records
    @Transactional
    public List<SalaryReportOverride> bulkUpsertSalaryReportOverrides(List<SalaryReportOverride> overrides) {
        writeDebugFile(overrides, "backend_save_debug.json");
        if (overrides == null || overrides.isEmpty()) {
            throw new IllegalArgumentException("Overrides list cannot be null or empty");
        }
        
        List<SalaryReportOverride> upsertedRecords = new ArrayList<>();
        
        for (SalaryReportOverride override : overrides) {
            try {
                // Validate required fields
                if (override.getEmployeeId() == null) {
                    throw new IllegalArgumentException("Employee ID is required for all records");
                }
                if (override.getMonth() == null) {
                    throw new IllegalArgumentException("Month is required for all records");
                }
                if (override.getYear() == null) {
                    throw new IllegalArgumentException("Year is required for all records");
                }
                if (override.getCompanyId() == null) {
                    override.setCompanyId(1L); // Default company ID if not provided
                }
                
                Optional<SalaryReportOverride> existing = overrideRepository.findByEmployeeIdAndMonthAndYearAndCompanyId(
                        override.getEmployeeId(), override.getMonth(), override.getYear(), override.getCompanyId());

                if (existing.isPresent()) {
                    SalaryReportOverride entity = existing.get();
                    // Update only non-null fields from override
                    if (override.getBasicSalary() != null) entity.setBasicSalary(override.getBasicSalary());
                    if (override.getOtSalary() != null) entity.setOtSalary(override.getOtSalary());
                    if (override.getFestivalWageSalary() != null) entity.setFestivalWageSalary(override.getFestivalWageSalary());
                    syncIncentiveFields(override, entity);
                    if (override.getFoodAllowance() != null) entity.setFoodAllowance(override.getFoodAllowance());
                    if (override.getFoodAllowanceAmount() != null) entity.setFoodAllowanceAmount(override.getFoodAllowanceAmount());
                    if (override.getSalaryAdvanceDeduction() != null) entity.setSalaryAdvanceDeduction(override.getSalaryAdvanceDeduction());
                    if (override.getLoanDeduction() != null) entity.setLoanDeduction(override.getLoanDeduction());
                    if (override.getPfDeduction() != null) entity.setPfDeduction(override.getPfDeduction());
                    if (override.getEsiDeduction() != null) entity.setEsiDeduction(override.getEsiDeduction());
                    if (override.getCanteenDeduction() != null) entity.setCanteenDeduction(override.getCanteenDeduction());
                    if (override.getTds() != null) entity.setTds(override.getTds());
                    if (override.getTotal() != null) entity.setTotal(override.getTotal());
                    if (override.getTotalEarningForPF() != null) entity.setTotalEarningForPF(override.getTotalEarningForPF());
                    if (override.getTotalDeduction() != null) entity.setTotalDeduction(override.getTotalDeduction());
                    if (override.getNetSalary() != null) entity.setNetSalary(override.getNetSalary());
                    if (override.getActualPresentDays() != null) entity.setActualPresentDays(override.getActualPresentDays());
                    if (override.getOtHoursWorked() != null) entity.setOtHoursWorked(override.getOtHoursWorked());
                    if (override.getFestivalHoursWorked() != null) entity.setFestivalHoursWorked(override.getFestivalHoursWorked());
                    if (override.getDaysToBePresent() != null) entity.setDaysToBePresent(override.getDaysToBePresent());
                    if (override.getPerDaySalary() != null) entity.setPerDaySalary(override.getPerDaySalary());
                    if (override.getBasicDA() != null) entity.setBasicDA(override.getBasicDA());
                    if (override.getHra() != null) entity.setHra(override.getHra());
                    if (override.getTa() != null) entity.setTa(override.getTa());
                    if (override.getMedical() != null) entity.setMedical(override.getMedical());
                    if (override.getHouseRent() != null) entity.setHouseRent(override.getHouseRent());
                    if (override.getRemarks() != null) entity.setRemarks(override.getRemarks());
                    if (override.getDepartment() != null) entity.setDepartment(override.getDepartment());
                    if (override.getDesignation() != null) entity.setDesignation(override.getDesignation());
                    if (override.getUpdatedBy() != null) entity.setUpdatedBy(override.getUpdatedBy());
                    // Set isSalaryGenerated to 1 when data is updated
                    entity.setIsSalaryGenerated(1);
                    upsertedRecords.add(overrideRepository.save(entity));
                } else {
                    syncIncentiveFields(override, override);
                    // Set isSalaryGenerated to 1 for new records
                    override.setIsSalaryGenerated(1);
                    upsertedRecords.add(overrideRepository.save(override));
                }
            } catch (Exception e) {
                // Log the error and continue with other records
                System.err.println("Error processing employee " + override.getEmployeeId() + ": " + e.getMessage());
                throw new RuntimeException("Failed to process employee " + override.getEmployeeId() + ": " + e.getMessage(), e);
            }
        }
        
        return upsertedRecords;
    }

    @Transactional
    public SalaryReportOverride upsertSalaryReportOverride(SalaryReportOverride override) {
        Optional<SalaryReportOverride> existing = overrideRepository.findByEmployeeIdAndMonthAndYearAndCompanyId(
                override.getEmployeeId(), override.getMonth(), override.getYear(), override.getCompanyId());

        if (existing.isPresent()) {
            SalaryReportOverride entity = existing.get();
            // Update only non-null fields from override
            if (override.getBasicSalary() != null) entity.setBasicSalary(override.getBasicSalary());
            if (override.getOtSalary() != null) entity.setOtSalary(override.getOtSalary());
            if (override.getFestivalWageSalary() != null) entity.setFestivalWageSalary(override.getFestivalWageSalary());
            syncIncentiveFields(override, entity);
            if (override.getFoodAllowance() != null) entity.setFoodAllowance(override.getFoodAllowance());
            if (override.getFoodAllowanceAmount() != null) entity.setFoodAllowanceAmount(override.getFoodAllowanceAmount());
            if (override.getSalaryAdvanceDeduction() != null) entity.setSalaryAdvanceDeduction(override.getSalaryAdvanceDeduction());
            if (override.getLoanDeduction() != null) entity.setLoanDeduction(override.getLoanDeduction());
            if (override.getPfDeduction() != null) entity.setPfDeduction(override.getPfDeduction());
            if (override.getEsiDeduction() != null) entity.setEsiDeduction(override.getEsiDeduction());
            if (override.getCanteenDeduction() != null) entity.setCanteenDeduction(override.getCanteenDeduction());
            if (override.getTds() != null) entity.setTds(override.getTds());
            if (override.getTotal() != null) entity.setTotal(override.getTotal());
            if (override.getTotalEarningForPF() != null) entity.setTotalEarningForPF(override.getTotalEarningForPF());
            if (override.getTotalDeduction() != null) entity.setTotalDeduction(override.getTotalDeduction());
            if (override.getNetSalary() != null) entity.setNetSalary(override.getNetSalary());
            if (override.getActualPresentDays() != null) entity.setActualPresentDays(override.getActualPresentDays());
            if (override.getOtHoursWorked() != null) entity.setOtHoursWorked(override.getOtHoursWorked());
            if (override.getFestivalHoursWorked() != null) entity.setFestivalHoursWorked(override.getFestivalHoursWorked());
            if (override.getDaysToBePresent() != null) entity.setDaysToBePresent(override.getDaysToBePresent());
            if (override.getPerDaySalary() != null) entity.setPerDaySalary(override.getPerDaySalary());
            if (override.getBasicDA() != null) entity.setBasicDA(override.getBasicDA());
            if (override.getHra() != null) entity.setHra(override.getHra());
            if (override.getTa() != null) entity.setTa(override.getTa());
            if (override.getMedical() != null) entity.setMedical(override.getMedical());
            if (override.getHouseRent() != null) entity.setHouseRent(override.getHouseRent());
            if (override.getRemarks() != null) entity.setRemarks(override.getRemarks());
            if (override.getDepartment() != null) entity.setDepartment(override.getDepartment());
            if (override.getDesignation() != null) entity.setDesignation(override.getDesignation());
            if (override.getUpdatedBy() != null) entity.setUpdatedBy(override.getUpdatedBy());
            // Set isSalaryGenerated to 1 when data is edited
            entity.setIsSalaryGenerated(1);
            return overrideRepository.save(entity);
        } else {
            syncIncentiveFields(override, override);
            return overrideRepository.save(override);
        }
    }

    private double parseHours(String hourStr) {
        if (hourStr == null || hourStr.isEmpty() || hourStr.equals("0")) return 0.0;
        try {
            if (hourStr.contains(":")) {
                String[] parts = hourStr.split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                return hours + (minutes / 60.0);
            } else {
                return Double.parseDouble(hourStr);
            }
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

/**
 * Calculate the number of weekoff days in a date range based on employee shift details
 * @param startDate Start date of the month
 * @param endDate End date of the month  
 * @param shiftDetails Employee shift details containing weekly off day information
 * @return Number of weekoff days
 */
private Integer calculateWeekOffDays(LocalDate startDate, LocalDate endDate, ShiftDetails shiftDetails) {
    if (shiftDetails == null) {
        return 0;
    }
    
    String weeklyOffDay = shiftDetails.getWeeklyOffDay();
    if (weeklyOffDay == null || weeklyOffDay.trim().isEmpty() || "no".equalsIgnoreCase(weeklyOffDay.trim()) || "none".equalsIgnoreCase(weeklyOffDay.trim())) {
        return 0;
    }
    
    String weeklyOffDayUpper = weeklyOffDay.trim().toUpperCase();
    DayOfWeek targetDayOfWeek;
    
    // Map weekly off day string to DayOfWeek enum
    switch (weeklyOffDayUpper) {
        case "SUNDAY":
        case "SUN":
            targetDayOfWeek = DayOfWeek.SUNDAY;
            break;
        case "MONDAY":
        case "MON":
            targetDayOfWeek = DayOfWeek.MONDAY;
            break;
        case "TUESDAY":
        case "TUE":
            targetDayOfWeek = DayOfWeek.TUESDAY;
            break;
        case "WEDNESDAY":
        case "WED":
            targetDayOfWeek = DayOfWeek.WEDNESDAY;
            break;
        case "THURSDAY":
        case "THU":
            targetDayOfWeek = DayOfWeek.THURSDAY;
            break;
        case "FRIDAY":
        case "FRI":
            targetDayOfWeek = DayOfWeek.FRIDAY;
            break;
        case "SATURDAY":
        case "SAT":
            targetDayOfWeek = DayOfWeek.SATURDAY;
            break;
        default:
            return 0;
    }
    
    // Count occurrences of the specified day in the date range
    int weekOffCount = 0;
    LocalDate currentDate = startDate;
    
    while (!currentDate.isAfter(endDate)) {
        if (currentDate.getDayOfWeek() == targetDayOfWeek) {
            weekOffCount++;
        }
        currentDate = currentDate.plusDays(1);
    }
    
    return weekOffCount;
}

/**
 * Calculate default weekoff days (Sundays) when shift details are not available
 * @param startDate Start date
 * @param endDate End date
 * @return Number of Sundays in the range
 */
private Integer calculateDefaultWeekOffDays(LocalDate startDate, LocalDate endDate) {
    int sundayCount = 0;
    LocalDate currentDate = startDate;
    
    while (!currentDate.isAfter(endDate)) {
        if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            sundayCount++;
        }
        currentDate = currentDate.plusDays(1);
    }
    
    return sundayCount;
}

private boolean isMonthActiveForLoan(EmployeeLoan loan, String targetMonth) {
    if (loan == null || targetMonth == null) return false;
    String fromMonth = loan.getFromMonth();
    if (fromMonth == null) return false;
    
    // If targetMonth is before fromMonth, it can't be active
    if (targetMonth.compareTo(fromMonth) < 0) return false;
    
    // Load approved skipped months from skip requests repository
    List<String> skipped = new java.util.ArrayList<>();
    if (loan.getId() != null) {
        List<EmployeeLoanSkipRequest> approvedSkips = employeeLoanSkipRequestRepository.findByEmployeeLoanIdAndStatus(loan.getId(), "Approved");
        for (EmployeeLoanSkipRequest req : approvedSkips) {
            if (req.getSkipMonth() != null) {
                skipped.add(req.getSkipMonth().trim());
            }
        }
    }
    
    // Also load skipped months from the loan's comma-separated skippedMonths field
    if (loan.getSkippedMonths() != null && !loan.getSkippedMonths().trim().isEmpty()) {
        String[] months = loan.getSkippedMonths().split(",");
        for (String m : months) {
            if (m != null && !m.trim().isEmpty()) {
                skipped.add(m.trim());
            }
        }
    }
    
    // If targetMonth itself is skipped, deduction is 0
    if (skipped.contains(targetMonth)) return false;
    
    int tenure = loan.getTenureMonths() != null ? loan.getTenureMonths() : 0;
    if (tenure <= 0) return false;
    
    // Walk forward month-by-month starting from fromMonth, counting non-skipped months
    // until we reach tenure months or exceed targetMonth.
    String currentMonth = fromMonth;
    int nonSkippedCount = 0;
    
    // Safety limit of 100 years = 1200 months
    for (int i = 0; i < 1200; i++) {
        boolean isSkipped = skipped.contains(currentMonth);
        if (!isSkipped) {
            nonSkippedCount++;
        }
        
        if (currentMonth.equals(targetMonth)) {
            return nonSkippedCount <= tenure;
        }
        
        if (nonSkippedCount >= tenure) {
            return false;
        }
        
        currentMonth = incrementMonth(currentMonth);
    }
    return false;
}

private String incrementMonth(String monthStr) {
    String[] parts = monthStr.split("-");
    int year = Integer.parseInt(parts[0]);
    int month = Integer.parseInt(parts[1]);
    month++;
    if (month > 12) {
        month = 1;
        year++;
    }
    return String.format("%04d-%02d", year, month);
}
}
