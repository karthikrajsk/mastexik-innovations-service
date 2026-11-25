package com.tech3.erp.service;

import com.tech3.erp.dto.*;
import com.tech3.erp.entity.*;
import com.tech3.erp.repository.EmployeeRelievedRepository;
import com.tech3.erp.repository.EmployeeRepository;
import com.tech3.erp.repository.MediaRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MediaRepository mediaRepository;
    private final EmployeeRelievedRepository employeeRelievedRepository;

    public EmployeeService(EmployeeRepository employeeRepository, MediaRepository mediaRepository, EmployeeRelievedRepository employeeRelievedRepository) {
        this.employeeRepository = employeeRepository;
        this.mediaRepository = mediaRepository;
        this.employeeRelievedRepository = employeeRelievedRepository;
    }
 // Helper method to round BigDecimal to nearest whole number
    private BigDecimal roundToWholeNumber(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.setScale(0, BigDecimal.ROUND_HALF_UP);
    }
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        // validatePfAndEsi(dto);
        // Validate if employee name already exists
        // if (employeeRepository.existsByEmployeeName(dto.getEmployeeName())) {
        //     throw new IllegalArgumentException("Employee Name already exists: " + dto.getEmployeeName());
        // }

        // Validate if employee code already exists
        if (employeeRepository.existsByEmployeeCode(dto.getEmployeeCode())) {
            throw new IllegalArgumentException("Employee Code already exists: " + dto.getEmployeeCode());
        }

        // Handle logo media if provided
        if (dto.getPhotoMediaDetailSno() != null) {
            Media photoMedia = mediaRepository.findByMediaDetailSno(dto.getPhotoMediaDetailSno())
                    .orElseThrow(() -> new EntityNotFoundException("Media not found for detail SNO: " + dto.getPhotoMediaDetailSno()));
            dto.setPhotoMediaSno(photoMedia.getMediaSno());
        }

        Employee entity = new Employee();
        mapDtoToEntity(dto, entity);

        Employee saved = employeeRepository.save(entity);

        if (saved.getPfWageCeilingHistory() != null && !saved.getPfWageCeilingHistory().isEmpty()) {
            boolean updatedCeiling = false;
            for (EmployeePfWageCeilingHistory hist : saved.getPfWageCeilingHistory()) {
                if (hist.getEmployeeId() == null) {
                    hist.setEmployeeId(saved.getId());
                    updatedCeiling = true;
                }
            }
            if (updatedCeiling) {
                saved = employeeRepository.save(saved);
            }
        }

        if (saved.getSalaryHistory() != null && !saved.getSalaryHistory().isEmpty()) {
            boolean updatedHistory = false;
            for (EmployeeSalaryHistory hist : saved.getSalaryHistory()) {
                if (hist.getEmployeeId() == null) {
                    hist.setEmployeeId(saved.getId());
                    updatedHistory = true;
                }
            }
            if (updatedHistory) {
                saved = employeeRepository.save(saved);
            }
        }

        syncIsRelieved(saved);
        return new EmployeeDTO(saved);
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee entity = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        return new EmployeeDTO(entity);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Employee::getId).reversed())
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<EmployeeDTO> getAllEmployeesWithPagination(Long offset, Long limit) {
        List<EmployeeDTO> allEmployees = employeeRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Employee::getId).reversed())
                .map(EmployeeDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allEmployees, offset, limit);
    }

    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        validatePfAndEsi(dto);
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));

        // Validate unique constraints (excluding current record)
        if (!existing.getEmployeeName().equals(dto.getEmployeeName()) 
                && employeeRepository.existsByEmployeeName(dto.getEmployeeName())) {
            throw new IllegalArgumentException("Employee Name already exists: " + dto.getEmployeeName());
        }

        if (dto.getEmployeeCode() != null && !dto.getEmployeeCode().equals(existing.getEmployeeCode()) 
                && employeeRepository.existsByEmployeeCode(dto.getEmployeeCode())) {
            throw new IllegalArgumentException("Employee Code already exists: " + dto.getEmployeeCode());
        }

        // Handle logo media if provided
        if (dto.getPhotoMediaDetailSno() != null) {
            Media photoMedia = mediaRepository.findByMediaDetailSno(dto.getPhotoMediaDetailSno())
                    .orElseThrow(() -> new EntityNotFoundException("Media not found for detail SNO: " + dto.getPhotoMediaDetailSno()));
            dto.setPhotoMediaSno(photoMedia.getMediaSno());
        }

        mapDtoToEntity(dto, existing);

        Employee updated = employeeRepository.save(existing);

        if (updated.getPfWageCeilingHistory() != null && !updated.getPfWageCeilingHistory().isEmpty()) {
            boolean updatedCeiling = false;
            for (EmployeePfWageCeilingHistory hist : updated.getPfWageCeilingHistory()) {
                if (hist.getEmployeeId() == null) {
                    hist.setEmployeeId(updated.getId());
                    updatedCeiling = true;
                }
            }
            if (updatedCeiling) {
                updated = employeeRepository.save(updated);
            }
        }

        if (updated.getSalaryHistory() != null && !updated.getSalaryHistory().isEmpty()) {
            boolean updatedHistory = false;
            for (EmployeeSalaryHistory hist : updated.getSalaryHistory()) {
                if (hist.getEmployeeId() == null) {
                    hist.setEmployeeId(updated.getId());
                    updatedHistory = true;
                }
            }
            if (updatedHistory) {
                updated = employeeRepository.save(updated);
            }
        }

        syncIsRelieved(updated);
        return new EmployeeDTO(updated);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        try {
            employeeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Employee because it is referenced by other entities");
        }
    }

    // Methods for suggestions
    public List<String> getEmployeeNameSuggestions() {
        return employeeRepository.findDistinctEmployeeNames();
    }

    public List<String> getDesignationSuggestions() {
        return employeeRepository.findDistinctDesignations();
    }

    public List<String> getPfSuggestions() {
        return employeeRepository.findDistinctPfValues();
    }

    public List<String> getTaxSuggestions() {
        return employeeRepository.findDistinctTaxValues();
    }

    private void validatePfAndEsi(EmployeeDTO dto) {
        if (Boolean.TRUE.equals(dto.getPfApplicable())) {
            if (dto.getPfNumber() == null || dto.getPfNumber().trim().isEmpty()) {
                throw new IllegalArgumentException("PF Number is mandatory when PF is applicable");
            }
        }
        if (Boolean.TRUE.equals(dto.getEsiApplicable())) {
            if (dto.getEsiNumber() == null || dto.getEsiNumber().trim().isEmpty()) {
                throw new IllegalArgumentException("ESI Number is mandatory when ESI is applicable");
            }
        }
    }

    private void mapDtoToEntity(EmployeeDTO dto, Employee entity) {
        entity.setEmployeeCode(dto.getEmployeeCode());
        entity.setEmployeeName(dto.getEmployeeName());
        entity.setGender(dto.getGender());
        entity.setDob(dto.getDob());
        entity.setFatherHusbandName(dto.getFatherHusbandName());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setAadhaarNumber(dto.getAadhaarNumber());
        entity.setPanNumber(dto.getPanNumber());
        entity.setMaritalStatus(dto.getMaritalStatus());
        entity.setUanNumber(dto.getUanNumber());
        entity.setPfNumber(dto.getPfNumber());
        entity.setPfJoiningDate(dto.getPfJoiningDate());
        entity.setEsiNumber(dto.getEsiNumber());
        entity.setPfApplicable(dto.getPfApplicable());
        entity.setEsiApplicable(dto.getEsiApplicable());
        // Convert PF and ESI percentages from user input (percentage) to decimal (divide by 100)
        entity.setPfPercent(dto.getPfPercent() != null ? dto.getPfPercent() / 100.0 : null);
        entity.setEsiPercent(dto.getEsiPercent() != null ? dto.getEsiPercent() / 100.0 : null);
        entity.setBasicDaPercent(dto.getBasicDaPercent() != null ? dto.getBasicDaPercent() / 100.0 : null);
        entity.setHraPercent(dto.getHraPercent() != null ? dto.getHraPercent() / 100.0 : null);
        entity.setTaPercent(dto.getTaPercent() != null ? dto.getTaPercent() / 100.0 : null);
        entity.setMedicalPercent(dto.getMedicalPercent() != null ? dto.getMedicalPercent() / 100.0 : null);
        entity.setPfWageCeiling(dto.getPfWageCeiling() != null ? dto.getPfWageCeiling() : 15000.0);
        entity.setIsRelieved(dto.getIsRelieved());
        entity.setIsEligibleFor28Days(dto.getSalaryDetails() != null && "28 days".equalsIgnoreCase(dto.getSalaryDetails().getSalaryType()));
        entity.setIsAllowance(dto.getIsAllowance() != null ? dto.getIsAllowance() : 0);
        entity.setIsAdvance(dto.getIsAdvance() != null ? dto.getIsAdvance() : 0);
        entity.setProductionIncentiveApplicable(dto.getProductionIncentiveApplicable() != null ? dto.getProductionIncentiveApplicable() : 0);
        entity.setAttendanceIncentiveApplicable(dto.getAttendanceIncentiveApplicable() != null ? dto.getAttendanceIncentiveApplicable() : 0);
        entity.setFoodAllowanceApplicable(dto.getFoodAllowanceApplicable() != null ? dto.getFoodAllowanceApplicable() : 0);
        
        Integer festWageVal = dto.getFestivalWagesApplicable();
        if ((festWageVal == null || festWageVal == 0) && dto.getEmploymentDetails() != null) {
            festWageVal = dto.getEmploymentDetails().getIsFestivalWageApplicable();
        }
        if (festWageVal == null) {
            festWageVal = 0;
        }
        entity.setFestivalWagesApplicable(festWageVal);

        Integer holDeductVal = dto.getHolidayDeductionApplicable();
        if ((holDeductVal == null || holDeductVal == 0) && dto.getEmploymentDetails() != null) {
            holDeductVal = dto.getEmploymentDetails().getIsHolidayDeductionApplicable();
        }
        if (holDeductVal == null) {
            holDeductVal = 0;
        }
        entity.setHolidayDeductionApplicable(holDeductVal);

        entity.setAttendanceIncentive(dto.getAttendanceIncentive() != null ? dto.getAttendanceIncentive() : 0.0);
        entity.setProductionIncentive(dto.getProductionIncentive() != null ? dto.getProductionIncentive() : 0.0);
        entity.setBloodGroup(dto.getBloodGroup());
        entity.setEmergencyContactNumber(dto.getEmergencyContactNumber());
        entity.setCategory(dto.getCategory());
        entity.setEmployeePhoto(dto.getEmployeePhoto());
        entity.setAadhaarCard(dto.getAadhaarCard());
        entity.setPhotoMediaDetailSno(dto.getPhotoMediaDetailSno());
        entity.setPhotoMediaSno(dto.getPhotoMediaSno());

        // Backward compatibility mapping
        // If nested DTOs are present, use them to update redundant top-level fields
        if (dto.getEmploymentDetails() != null) {
            entity.setDesignation(dto.getEmploymentDetails().getDesignation());
            entity.setDepartment(dto.getEmploymentDetails().getDepartment());
        } else {
            entity.setDesignation(dto.getDesignation());
            entity.setDepartment(dto.getDepartment());
        }

        if (dto.getSalaryDetails() != null && dto.getSalaryDetails().getBasicSalary() != null) {
            entity.setSalary(dto.getSalaryDetails().getBasicSalary().toString());
        } else {
            entity.setSalary(dto.getSalary());
        }

        entity.setPf(dto.getPf());
        entity.setTax(dto.getTax());

        // Map Nested Entities
        if (dto.getEmploymentDetails() != null) {
            EmploymentDetails ed = entity.getEmploymentDetails();
            if (ed == null) ed = new EmploymentDetails();
            ed.setEmployee(entity);
            ed.setDepartment(dto.getEmploymentDetails().getDepartment());
            ed.setDesignation(dto.getEmploymentDetails().getDesignation());
            ed.setQualification(dto.getEmploymentDetails().getQualification());
            ed.setJoiningDate(dto.getEmploymentDetails().getJoiningDate());
            ed.setEmployeeType(dto.getEmploymentDetails().getEmployeeType());
            ed.setStatus(dto.getEmploymentDetails().getStatus());
            ed.setHostelFamilyQuarters(dto.getEmploymentDetails().getHostelFamilyQuarters());
            ed.setHostelFamilyQuartersRoomNo(dto.getEmploymentDetails().getHostelFamilyQuartersRoomNo());
            ed.setHouseRent(dto.getEmploymentDetails().getHouseRent());
            
            Integer edFestWage = dto.getEmploymentDetails().getIsFestivalWageApplicable();
            if (edFestWage == null || edFestWage == 0) {
                edFestWage = festWageVal;
            }
            ed.setIsFestivalWageApplicable(edFestWage);

            Integer edHolDeduct = dto.getEmploymentDetails().getIsHolidayDeductionApplicable();
            if (edHolDeduct == null || edHolDeduct == 0) {
                edHolDeduct = holDeductVal;
            }
            ed.setIsHolidayDeductionApplicable(edHolDeduct);

            entity.setEmploymentDetails(ed);
            entity.setFestivalWagesApplicable(edFestWage);
            entity.setHolidayDeductionApplicable(edHolDeduct);
        } else {
            EmploymentDetails ed = entity.getEmploymentDetails();
            if (ed == null) ed = new EmploymentDetails();
            ed.setEmployee(entity);
            ed.setIsFestivalWageApplicable(festWageVal);
            ed.setIsHolidayDeductionApplicable(holDeductVal);
            entity.setEmploymentDetails(ed);
        }

        if (dto.getSalaryDetails() != null) {
            SalaryDetails sd = entity.getSalaryDetails();
            if (sd == null) sd = new SalaryDetails();
            sd.setEmployee(entity);
            sd.setSalaryStructure(dto.getSalaryDetails().getSalaryStructure());
            sd.setSalaryType(dto.getSalaryDetails().getSalaryType());
            sd.setBasicSalary(dto.getSalaryDetails().getBasicSalary());
            sd.setHra(dto.getSalaryDetails().getHra());
            sd.setConveyanceAllowance(dto.getSalaryDetails().getConveyanceAllowance());
            sd.setMedicalAllowance(dto.getSalaryDetails().getMedicalAllowance());
            sd.setSpecialAllowance(dto.getSalaryDetails().getSpecialAllowance());
            sd.setIncentive(dto.getSalaryDetails().getIncentive());
            sd.setPerHourSalary(dto.getSalaryDetails().getPerHourSalary());
            sd.setOtRatePerHour(dto.getSalaryDetails().getOtRatePerHour());
            sd.setOtSalary(dto.getSalaryDetails().getOtSalary());
            sd.setProductionIncentive(dto.getSalaryDetails().getProductionIncentive());
            sd.setIsCanteen(dto.getSalaryDetails().getIsCanteen());
            if (dto.getSalaryDetails().getEffectiveDate() != null && !dto.getSalaryDetails().getEffectiveDate().trim().isEmpty()) {
                sd.setEffectiveDate(java.time.LocalDate.parse(dto.getSalaryDetails().getEffectiveDate()));
            } else {
                sd.setEffectiveDate(null);
            }
            entity.setSalaryDetails(sd);
        }

        if (dto.getShiftDetails() != null) {
            ShiftDetails shd = entity.getShiftDetails();
            if (shd == null) shd = new ShiftDetails();
            shd.setEmployee(entity);
            shd.setShiftName(dto.getShiftDetails().getShiftName());
            shd.setShiftCode(dto.getShiftDetails().getShiftCode());
            shd.setShiftStartTime(dto.getShiftDetails().getShiftStartTime());
            shd.setShiftEndTime(dto.getShiftDetails().getShiftEndTime());
            shd.setBreakStartTime(dto.getShiftDetails().getBreakStartTime());
            shd.setBreakEndTime(dto.getShiftDetails().getBreakEndTime());
            shd.setLunchBreak(dto.getShiftDetails().getLunchBreak());
            shd.setTotalWorkingHours(dto.getShiftDetails().getTotalWorkingHours());
            shd.setWeeklyOffDay(dto.getShiftDetails().getWeeklyOffDay());
            shd.setOvertimeAllowed(dto.getShiftDetails().isOvertimeAllowed());
            shd.setLateEntryAllowedMinutes(dto.getShiftDetails().getLateEntryAllowedMinutes());
            shd.setShiftEffectiveFrom(dto.getShiftDetails().getShiftEffectiveFrom());
            shd.setNoOfLeave(dto.getShiftDetails().getNoOfLeave());
            entity.setShiftDetails(shd);
        }

        if (dto.getBankDetails() != null) {
            BankDetails bd = entity.getBankDetails();
            if (bd == null) bd = new BankDetails();
            bd.setEmployee(entity);
            bd.setBankName(dto.getBankDetails().getBankName());
            bd.setAccountNumber(dto.getBankDetails().getAccountNumber());
            bd.setIfscCode(dto.getBankDetails().getIfscCode());
            bd.setBranchName(dto.getBankDetails().getBranchName());
            
            // Handle bank document media using CompanyService pattern
            if (dto.getBankDetails().getBankDocumentsMediaDetailSno() != null) {
                Media bankDocumentsMedia = mediaRepository.findByMediaDetailSno(dto.getBankDetails().getBankDocumentsMediaDetailSno())
                        .orElseThrow(() -> new EntityNotFoundException("Media not found for detail SNO: " + dto.getBankDetails().getBankDocumentsMediaDetailSno()));
                dto.getBankDetails().setBankDocumentsMediaSno(bankDocumentsMedia.getMediaSno());
            }
            
            // Map bank document fields
            bd.setBankDocumentsUrl(dto.getBankDetails().getBankDocumentsUrl());
            bd.setBankDocumentsMediaDetailSno(dto.getBankDetails().getBankDocumentsMediaDetailSno());
            bd.setBankDocumentsMediaSno(dto.getBankDetails().getBankDocumentsMediaSno());
            
            entity.setBankDetails(bd);
        }

        // Map PF Wage Ceiling History
        if (dto.getPfWageCeilingHistory() != null) {
            List<EmployeePfWageCeilingHistory> ceilingHistoryList = entity.getPfWageCeilingHistory();
            if (ceilingHistoryList == null) {
                ceilingHistoryList = new java.util.ArrayList<>();
                entity.setPfWageCeilingHistory(ceilingHistoryList);
            }

            List<Long> submittedHistoryIds = dto.getPfWageCeilingHistory().stream()
                    .map(EmployeePfWageCeilingHistoryDTO::getId)
                    .filter(java.util.Objects::nonNull)
                    .collect(Collectors.toList());
            ceilingHistoryList.removeIf(hist -> hist.getId() == null || !submittedHistoryIds.contains(hist.getId()));

            for (EmployeePfWageCeilingHistoryDTO chDto : dto.getPfWageCeilingHistory()) {
                EmployeePfWageCeilingHistory hist = ceilingHistoryList.stream()
                        .filter(existing -> chDto.getId() != null && chDto.getId().equals(existing.getId()))
                        .findFirst()
                        .orElseGet(EmployeePfWageCeilingHistory::new);
                if (!ceilingHistoryList.contains(hist)) {
                    ceilingHistoryList.add(hist);
                }
                hist.setEmployee(entity);
                hist.setPfWageCeiling(chDto.getPfWageCeiling() != null ? chDto.getPfWageCeiling() : BigDecimal.valueOf(15000));
                hist.setEffectiveDate(chDto.getEffectiveDate());
                hist.setRemarks(chDto.getRemarks());
                if (chDto.getCreatedAt() != null) hist.setCreatedAt(chDto.getCreatedAt());
            }

            if (!dto.getPfWageCeilingHistory().isEmpty()) {
                BigDecimal latestCeiling = dto.getPfWageCeilingHistory().get(0).getPfWageCeiling();
                if (latestCeiling != null) {
                    entity.setPfWageCeiling(latestCeiling.doubleValue());
                }
            }
        }
        if ((dto.getPfWageCeilingHistory() == null || entity.getId() == null)
                && dto.getPfWageCeiling() != null && Boolean.TRUE.equals(dto.getPfApplicable())) {
            if (entity.getPfWageCeilingHistory() == null || entity.getPfWageCeilingHistory().isEmpty()) {
                EmployeePfWageCeilingHistory hist = new EmployeePfWageCeilingHistory();
                hist.setEmployee(entity);
                hist.setPfWageCeiling(BigDecimal.valueOf(dto.getPfWageCeiling()));
                hist.setEffectiveDate(dto.getPfJoiningDate() != null ? dto.getPfJoiningDate() : java.time.LocalDate.now());
                hist.setRemarks("Initial PF Ceiling Setup");
                if (entity.getPfWageCeilingHistory() == null) {
                    entity.setPfWageCeilingHistory(new java.util.ArrayList<>());
                }
                entity.getPfWageCeilingHistory().add(hist);
            }
        }

        // Map Salary History
        if (dto.getSalaryHistory() != null && !dto.getSalaryHistory().isEmpty()) {
            List<EmployeeSalaryHistory> historyList = new java.util.ArrayList<>();
            for (EmployeeSalaryHistoryDTO shDto : dto.getSalaryHistory()) {
                EmployeeSalaryHistory hist = new EmployeeSalaryHistory();
                if (shDto.getId() != null) hist.setId(shDto.getId());
                if (entity.getId() != null) hist.setEmployeeId(entity.getId());
                hist.setDepartment(shDto.getDepartment());
                hist.setDesignation(shDto.getDesignation());
                hist.setSalaryType(shDto.getSalaryType());
                hist.setBasicSalary(shDto.getBasicSalary());
                hist.setHra(shDto.getHra());
                hist.setConveyanceAllowance(shDto.getConveyanceAllowance());
                hist.setMedicalAllowance(shDto.getMedicalAllowance());
                hist.setSpecialAllowance(shDto.getSpecialAllowance());
                hist.setOtSalary(shDto.getOtSalary());
                hist.setPerHourSalary(shDto.getPerHourSalary());
                hist.setOtRatePerHour(shDto.getOtRatePerHour());
                hist.setProductionIncentive(shDto.getProductionIncentive());
                hist.setIsCanteen(shDto.getIsCanteen());
                hist.setEffectiveDate(shDto.getEffectiveDate());
                hist.setRemarks(shDto.getRemarks());
                hist.setPfWageCeiling(shDto.getPfWageCeiling() != null ? shDto.getPfWageCeiling() : (dto.getPfWageCeiling() != null ? BigDecimal.valueOf(dto.getPfWageCeiling()) : BigDecimal.valueOf(15000)));
                if (shDto.getCreatedAt() != null) hist.setCreatedAt(shDto.getCreatedAt());
                historyList.add(hist);
            }
            entity.getSalaryHistory().clear();
            entity.getSalaryHistory().addAll(historyList);

            // Sync latest revision department and designation to employment details if provided
            if (!historyList.isEmpty()) {
                EmployeeSalaryHistory latest = historyList.get(0);
                if (latest.getDepartment() != null && !latest.getDepartment().trim().isEmpty()) {
                    if (entity.getEmploymentDetails() == null) entity.setEmploymentDetails(new EmploymentDetails());
                    entity.getEmploymentDetails().setDepartment(latest.getDepartment());
                    entity.setDepartment(latest.getDepartment());
                }
                if (latest.getDesignation() != null && !latest.getDesignation().trim().isEmpty()) {
                    if (entity.getEmploymentDetails() == null) entity.setEmploymentDetails(new EmploymentDetails());
                    entity.getEmploymentDetails().setDesignation(latest.getDesignation());
                    entity.setDesignation(latest.getDesignation());
                }
                if (false) {
                    /* salary revision does not sync pf wage ceiling */
                }
            }
        } else if (dto.getSalaryDetails() != null && dto.getSalaryDetails().getEffectiveDate() != null && !dto.getSalaryDetails().getEffectiveDate().trim().isEmpty()) {
            // If no explicit salary history array provided, auto-create initial history record if empty
            if (entity.getSalaryHistory() == null || entity.getSalaryHistory().isEmpty()) {
                EmployeeSalaryHistory hist = new EmployeeSalaryHistory();
                if (entity.getId() != null) hist.setEmployeeId(entity.getId());
                if (entity.getEmploymentDetails() != null) {
                    hist.setDepartment(entity.getEmploymentDetails().getDepartment());
                    hist.setDesignation(entity.getEmploymentDetails().getDesignation());
                }
                hist.setSalaryType(dto.getSalaryDetails().getSalaryType());
                hist.setBasicSalary(dto.getSalaryDetails().getBasicSalary());
                hist.setHra(dto.getSalaryDetails().getHra());
                hist.setConveyanceAllowance(dto.getSalaryDetails().getConveyanceAllowance());
                hist.setMedicalAllowance(dto.getSalaryDetails().getMedicalAllowance());
                hist.setSpecialAllowance(dto.getSalaryDetails().getSpecialAllowance());
                hist.setOtSalary(entity.getSalaryDetails() != null ? entity.getSalaryDetails().getOtSalary() : dto.getSalaryDetails().getOtSalary());
                hist.setPerHourSalary(dto.getSalaryDetails().getPerHourSalary());
                hist.setOtRatePerHour(dto.getSalaryDetails().getOtRatePerHour());
                hist.setProductionIncentive(dto.getSalaryDetails().getProductionIncentive());
                hist.setIsCanteen(dto.getSalaryDetails().getIsCanteen());
                try {
                    hist.setEffectiveDate(java.time.LocalDate.parse(dto.getSalaryDetails().getEffectiveDate()));
                } catch (Exception ignored) {}
                hist.setRemarks("Initial Salary Setup");
                hist.setPfWageCeiling(dto.getPfWageCeiling() != null ? BigDecimal.valueOf(dto.getPfWageCeiling()) : BigDecimal.valueOf(15000));
                if (hist.getEffectiveDate() != null) {
                    if (entity.getSalaryHistory() == null) {
                        entity.setSalaryHistory(new java.util.ArrayList<>());
                    }
                    entity.getSalaryHistory().add(hist);
                }
            }
        }
    }

    private void syncIsRelieved(Employee employee) {
        if (employee.getIsRelieved() != null) {
            Optional<EmployeeRelieved> existing = employeeRelievedRepository.findByEmployeeId(employee.getId());
            if (existing.isPresent()) {
                EmployeeRelieved relieved = existing.get();
                relieved.setIsRelieved(employee.getIsRelieved());
                if (employee.getEmploymentDetails() != null) {
                    relieved.setDepartment(employee.getEmploymentDetails().getDepartment());
                    relieved.setDesignation(employee.getEmploymentDetails().getDesignation());
                    relieved.setJoiningDate(employee.getEmploymentDetails().getJoiningDate());
                }
                employeeRelievedRepository.save(relieved);
            } else if (employee.getIsRelieved() == 1) {
                EmployeeRelieved relieved = new EmployeeRelieved();
                relieved.setEmployeeId(employee.getId());
                relieved.setIsRelieved(1);
                if (employee.getEmploymentDetails() != null) {
                    relieved.setDepartment(employee.getEmploymentDetails().getDepartment());
                    relieved.setDesignation(employee.getEmploymentDetails().getDesignation());
                    relieved.setJoiningDate(employee.getEmploymentDetails().getJoiningDate());
                }
                employeeRelievedRepository.save(relieved);
            }
        }
    }
    
    @Transactional
    public List<EmployeeDTO> createEmployeesBulk(List<EmployeeDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new IllegalArgumentException("Employee list cannot be null or empty");
        }
        List<EmployeeDTO> result = new java.util.ArrayList<>();
        for (EmployeeDTO dto : dtos) {
            result.add(createEmployee(dto));
        }
        return result;
    }
}

