
// package com.tech3.erp.service;

// import com.tech3.erp.dto.AttendanceDTO;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.entity.Attendance;
// import com.tech3.erp.entity.Employee;
// import com.tech3.erp.repository.AttendanceRepository;
// import com.tech3.erp.repository.EmployeeRepository;
// import com.tech3.erp.util.PaginationHelper;
// org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.time.ZoneId;
// import java.util.Comparator;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class AttendanceService {

//     private final AttendanceRepository attendanceRepository;
//     private final EmployeeRepository employeeRepository;

//     public AttendanceService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
//         this.attendanceRepository = attendanceRepository;
//         this.employeeRepository = employeeRepository;
//     }

//     @Transactional
//     public AttendanceDTO createAttendance(AttendanceDTO dto) {
//         // Validation: Employee not found
//         Employee employee;
//         if (dto.getEmployee_code() != null) {
//             employee = employeeRepository.findByEmployeeCode(dto.getEmployee_code())
//                     .orElseThrow(() -> new IllegalArgumentException("Employee code not found"));
//         } else if (dto.getEmployee_id() != null) {
//             employee = employeeRepository.findById(dto.getEmployee_id())
//                     .orElseThrow(() -> new IllegalArgumentException("Employee ID not found"));
//         } else {
//             throw new IllegalArgumentException("Employee code or ID must be provided");
//         }

//         // Validation: Name check if provided
//         if (dto.getEmployee_name() != null && !employee.getEmployeeName().equalsIgnoreCase(dto.getEmployee_name())) {
//             String identifier = dto.getEmployee_code() != null ? dto.getEmployee_code() : String.valueOf(dto.getEmployee_id());
//             throw new IllegalArgumentException("This " + identifier + " not for this " + dto.getEmployee_name());
//         }

//         // Use date and time from DTO if provided, otherwise defaults (always IST to UTC for storage)
//         LocalDate date = dto.getDate() != null ? dto.getDate() : LocalDate.now(ZoneId.of("Asia/Kolkata"));
//         LocalDateTime inputTime = dto.getTime() != null ? dto.getTime() : LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
//         LocalDateTime time = inputTime.atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

//         // If date is provided but time is not, ensure time uses that date in IST context then convert to UTC
//         if (dto.getDate() != null && dto.getTime() == null) {
//             LocalDateTime localIST = date.atTime(LocalTime.now(ZoneId.of("Asia/Kolkata")));
//             time = localIST.atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
//         } else if (dto.getTime() != null) {
//             // Ensure date field matches the date in time field in IST
//             date = inputTime.toLocalDate();
//         }

//         String typeStr = (dto.getType() != null && dto.getType() == 1) ? "check_in" : "check_out";

//         // Find existing record for this employee and date
//         Optional<Attendance> existingRecord = attendanceRepository.findByEmployeeAndDate(employee, date)
//                 .stream().findFirst();

//         Attendance entity;
//         if (existingRecord.isPresent()) {
//             entity = existingRecord.get();
            
//             // Check if employee already completed both check-in and check-out for today
//             if (entity.getCheckIn() != null && entity.getCheckOut() != null) {
//                 throw new IllegalArgumentException("Employee already checked in and out for today");
//             }
            
//             if ("check_in".equalsIgnoreCase(typeStr)) {
//                 if (entity.getCheckIn() != null) {
//                     throw new IllegalArgumentException("Employee already checked in for today");
//                 }
//             } else { // check_out
//                 if (entity.getCheckIn() == null) {
//                     throw new IllegalArgumentException("Employee not checked in today");
//                 }
//                 if (entity.getCheckOut() != null) {
//                     throw new IllegalArgumentException("Employee already checked out for today");
//                 }
//             }
//         } else {
//             // No record for today
//             if ("check_out".equalsIgnoreCase(typeStr)) {
//                 throw new IllegalArgumentException("Employee not checked in today");
//             }
//             entity = new Attendance();
//             entity.setEmployee(employee);
//             entity.setEmployeeName(employee.getEmployeeName());
//             entity.setEmployeeCode(employee.getEmployeeCode());
//             entity.setDate(date);
//             entity.setOvertimeHours("--");
//         }

//         // Update common fields for the latest action
//         entity.setTime(time);
//         entity.setType(typeStr);
//         entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "Present");
        
//         if ("check_in".equalsIgnoreCase(typeStr)) {
//             // If checkIn is provided in DTO, it's IST -> convert to UTC
//             LocalDateTime checkInDateTime = dto.getCheckIn() != null ? 
//                 dto.getCheckIn().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime() : time;
//             entity.setCheckIn(checkInDateTime);
//         } else {
//             // If checkOut is provided in DTO, it's IST -> convert to UTC
//             LocalDateTime checkOutDateTime = dto.getCheckOut() != null ? 
//                 dto.getCheckOut().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime() : time;
//             entity.setCheckOut(checkOutDateTime);
            
//             LocalDateTime checkInDateTime = entity.getCheckIn();
//             if (checkInDateTime != null) {
//                 // Duration calculation using UTC times
//                 if (checkOutDateTime.isAfter(checkInDateTime)) {
//                     long minutes = java.time.Duration.between(checkInDateTime, checkOutDateTime).toMinutes();
//                     double hours = minutes / 60.0;
//                     entity.setWorkingHours(String.format("%.2f", hours));
                    
//                     // Overtime calculation: workingHours - 8.00 (Standard Hours)
//                     if (hours > 8.0) {
//                         double otHours = hours - 8.0;
//                         entity.setOvertimeHours(String.format("%.2f", otHours));
//                     } else {
//                         entity.setOvertimeHours("--");
//                     }
//                 } else {
//                     entity.setWorkingHours("0.00");
//                     entity.setOvertimeHours("--");
//                 }
//             }
//         }

//         Attendance saved = attendanceRepository.save(entity);
//         return new AttendanceDTO(saved);
//     }

//     public PaginatedResponse<AttendanceDTO> getAllAttendancesWithPagination(Long offset, Long limit) {
//         List<AttendanceDTO> all = attendanceRepository.findAll()
//                 .stream()
//                 .sorted(Comparator.comparing(Attendance::getId).reversed())
//                 .map(AttendanceDTO::new)
//                 .collect(Collectors.toList());
        
//         return PaginationHelper.paginate(all, offset, limit);
//     }

//     @Transactional
//     public AttendanceDTO updateAttendance(Long id, AttendanceDTO dto) {
//         Attendance existing = attendanceRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Attendance record not found"));
        
//         // Basic update mapping
//         existing.setStatus(dto.getStatus());
        
//         // Input from DTO is IST -> Store as UTC for DB
//         if (dto.getCheckIn() != null) {
//             existing.setCheckIn(dto.getCheckIn().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
//         }
//         if (dto.getCheckOut() != null) {
//             existing.setCheckOut(dto.getCheckOut().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
//         }
        
//         existing.setWorkingHours(dto.getWorkingHours());
//         existing.setOvertimeHours(dto.getOvertimeHours());
        
//         Attendance updated = attendanceRepository.save(existing);
//         return new AttendanceDTO(updated);
//     }

//     @Transactional
//     public void deleteAttendance(Long id) {
//         if (!attendanceRepository.existsById(id)) {
//             throw new IllegalArgumentException("Attendance record not found");
//         }
//         attendanceRepository.deleteById(id);
//     }
// }

//attendance service

package com.tech3.erp.service;

import com.tech3.erp.dto.AttendanceDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Attendance;
import com.tech3.erp.entity.Employee;
import com.tech3.erp.entity.Holiday;
import com.tech3.erp.repository.AttendanceRepository;
import com.tech3.erp.repository.EmployeeRepository;
import com.tech3.erp.repository.HolidayRepository;
import com.tech3.erp.util.PaginationHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final HolidayRepository holidayRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository, HolidayRepository holidayRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
        this.holidayRepository = holidayRepository;
    }

    private void validateFestivalWageEligibility(Employee employee, LocalDate date) {
        if (date == null || employee == null) {
            return;
        }
        List<Holiday> holidaysOnDate = holidayRepository.findByHolidayDate(date);
        boolean isFestivalDay = holidaysOnDate != null && holidaysOnDate.stream()
                .anyMatch(h -> Boolean.TRUE.equals(h.getIsFestivalWageApplicable()) 
                            && (h.getStatus() == null || !"Inactive".equalsIgnoreCase(h.getStatus())));

        if (isFestivalDay) {
            boolean isEligible = (employee.getFestivalWagesApplicable() != null && employee.getFestivalWagesApplicable() == 1)
                    || (employee.getEmploymentDetails() != null && employee.getEmploymentDetails().getIsFestivalWageApplicable() != null && employee.getEmploymentDetails().getIsFestivalWageApplicable() == 1);

            if (!isEligible) {
                String empCode = employee.getEmployeeCode() != null ? employee.getEmployeeCode().trim() : "";
                String empName = employee.getEmployeeName() != null ? employee.getEmployeeName().trim() : "";
                String empIdentifier = !empCode.isEmpty() ? (empCode + " - " + empName) : empName;
                throw new IllegalArgumentException("Employee " + empIdentifier + " is not eligible for festival wage");
            }
        }
    }

    @Transactional
    public AttendanceDTO createAttendance(AttendanceDTO dto) {
        // Validation: Employee not found
        Employee employee;
        if (dto.getEmployee_code() != null) {
            employee = employeeRepository.findByEmployeeCode(dto.getEmployee_code())
                    .orElseThrow(() -> new IllegalArgumentException("Employee code not found"));
        } else if (dto.getEmployee_id() != null) {
            employee = employeeRepository.findById(dto.getEmployee_id())
                    .orElseThrow(() -> new IllegalArgumentException("Employee ID not found"));
        } else {
            throw new IllegalArgumentException("Employee code or ID must be provided");
        }

        // Validation: Name check if provided
        if (dto.getEmployee_name() != null && !employee.getEmployeeName().equalsIgnoreCase(dto.getEmployee_name())) {
            String identifier = dto.getEmployee_code() != null ? dto.getEmployee_code() : String.valueOf(dto.getEmployee_id());
            throw new IllegalArgumentException("This " + identifier + " not for this " + dto.getEmployee_name());
        }

        // Use date and time from DTO if provided, otherwise defaults (always IST to UTC for storage)
        LocalDate date = dto.getDate() != null ? dto.getDate() : LocalDate.now(ZoneId.of("Asia/Kolkata"));
        LocalDateTime inputTime = dto.getTime() != null ? dto.getTime() : LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        LocalDateTime time = inputTime.atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        // If date is provided but time is not, ensure time uses that date in IST context then convert to UTC
        if (dto.getDate() != null && dto.getTime() == null) {
            LocalDateTime localIST = date.atTime(LocalTime.now(ZoneId.of("Asia/Kolkata")));
            time = localIST.atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        } else if (dto.getTime() != null) {
            // Ensure date field matches the date in time field in IST
            date = inputTime.toLocalDate();
        }

        // Validate Festival Wage Eligibility on Attendance Entry
        validateFestivalWageEligibility(employee, date);

        String status = dto.getStatus() != null ? dto.getStatus() : "Present";
        boolean isAbsentOrLeave = "Absent".equalsIgnoreCase(status) || "Leave".equalsIgnoreCase(status);

        // Find existing record for this employee and date
        Optional<Attendance> existingRecord = attendanceRepository.findByEmployeeAndDate(employee, date)
                .stream().findFirst();

        Attendance entity;
        if (existingRecord.isPresent()) {
            entity = existingRecord.get();
        } else if (dto.getId() != null && attendanceRepository.findById(dto.getId()).isPresent()) {
            entity = attendanceRepository.findById(dto.getId()).get();
        } else {
            entity = new Attendance();
            entity.setEmployee(employee);
            entity.setEmployeeName(employee.getEmployeeName());
            entity.setEmployeeCode(employee.getEmployeeCode());
            entity.setDate(date);
            entity.setOvertimeHours("--");
        }

        entity.setTime(time);
        entity.setStatus(status);

        if (isAbsentOrLeave) {
            entity.setType(null);
            entity.setWorkingHours("0.00");
            entity.setWorkingDays(0.0);
            entity.setDifference(0.0);
            entity.setOvertimeHours("--");
            entity.setCheckIn(null);
            entity.setCheckOut(null);
            entity.setOtCheckIn(null);
            entity.setOtCheckOut(null);
            Attendance saved = attendanceRepository.save(entity);
            return new AttendanceDTO(saved);
        }

        // Punch type check when status is Present or type is specified
        String typeStr = null;
        if (dto.getType() != null) {
            typeStr = (dto.getType() == 1) ? "check_in" : "check_out";
            entity.setType(typeStr);
        }

        if (dto.getCheckIn() != null) {
            LocalDateTime checkInDateTime = dto.getCheckIn().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            entity.setCheckIn(checkInDateTime);
        } else if ("check_in".equalsIgnoreCase(typeStr)) {
            entity.setCheckIn(time);
        }

        if (dto.getCheckOut() != null) {
            LocalDateTime checkOutDateTime = dto.getCheckOut().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime checkInDateTime = entity.getCheckIn();
            if (checkInDateTime != null && checkOutDateTime.isBefore(checkInDateTime)) {
                checkOutDateTime = checkOutDateTime.plusDays(1);
            }
            entity.setCheckOut(checkOutDateTime);
            if (checkInDateTime != null) {
                calculateHoursAndDays(entity, checkInDateTime, checkOutDateTime, employee);
            }
        } else if ("check_out".equalsIgnoreCase(typeStr)) {
            LocalDateTime checkOutDateTime = time;
            LocalDateTime checkInDateTime = entity.getCheckIn();
            if (checkInDateTime != null && checkOutDateTime.isBefore(checkInDateTime)) {
                checkOutDateTime = checkOutDateTime.plusDays(1);
            }
            entity.setCheckOut(checkOutDateTime);
            if (checkInDateTime != null) {
                calculateHoursAndDays(entity, checkInDateTime, checkOutDateTime, employee);
            }
        }

        if (dto.getOtCheckIn() != null) {
            LocalDateTime otCheckInDateTime = dto.getOtCheckIn().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            entity.setOtCheckIn(otCheckInDateTime);
        }

        if (dto.getOtCheckOut() != null) {
            LocalDateTime otCheckOutDateTime = dto.getOtCheckOut().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime otIn = entity.getOtCheckIn();
            if (otIn != null && otCheckOutDateTime.isBefore(otIn)) {
                otCheckOutDateTime = otCheckOutDateTime.plusDays(1);
            }
            entity.setOtCheckOut(otCheckOutDateTime);
        }

        // Calculate OT hours from otCheckIn and otCheckOut if present
        if (entity.getOtCheckIn() != null && entity.getOtCheckOut() != null) {
            LocalDateTime otIn = entity.getOtCheckIn();
            LocalDateTime otOut = entity.getOtCheckOut();
            if (otOut.isBefore(otIn)) {
                otOut = otOut.plusDays(1);
            }
            if (otOut.isAfter(otIn)) {
                long minutes = java.time.Duration.between(otIn, otOut).toMinutes();
                double hours = minutes / 60.0;
                if (hours > 0) {
                    entity.setOvertimeHours(String.format("%.2f", hours));
                }
            }
        } else if (dto.getOvertimeHours() != null && !dto.getOvertimeHours().trim().isEmpty() && !dto.getOvertimeHours().trim().equals("--")) {
            // If user provided OT hours directly, save/include in overtimeHours column
            try {
                double parsedOt = Double.parseDouble(dto.getOvertimeHours().trim());
                if (parsedOt > 0) {
                    entity.setOvertimeHours(String.format("%.2f", parsedOt));
                }
            } catch (NumberFormatException e) {
                entity.setOvertimeHours(dto.getOvertimeHours().trim());
            }
        }

        Attendance saved = attendanceRepository.save(entity);
        return new AttendanceDTO(saved);
    }

    private void calculateHoursAndDays(Attendance entity, LocalDateTime checkIn, LocalDateTime checkOut, Employee employee) {

        if (checkOut != null && checkIn != null && checkOut.isAfter(checkIn)) {

            long minutes = java.time.Duration.between(checkIn, checkOut).toMinutes();
            double hours = minutes / 60.0;

            Double shiftHours = 8.0;

            if (employee != null && employee.getShiftDetails() != null
                    && employee.getShiftDetails().getTotalWorkingHours() != null
                    && employee.getShiftDetails().getTotalWorkingHours() > 0) {
                shiftHours = employee.getShiftDetails().getTotalWorkingHours();
            }

            double halfShift = shiftHours / 2.0;
            double workingDays = 0.0;

            if (hours >= shiftHours) {
                workingDays = 1.0;
            } else if (hours >= halfShift) {
                workingDays = 0.5;
            } else {
                workingDays = 0.0;
            }

            entity.setWorkingDays(workingDays);

            double regularHours = workingDays * shiftHours;
            entity.setWorkingHours(String.format("%.2f", regularHours));

            double otCalc = hours - regularHours;

            if (otCalc > 0) {
                entity.setOvertimeHours(String.format("%.2f", otCalc));
                entity.setDifference(0.0);
            } else {
                entity.setOvertimeHours("--");
                entity.setDifference(0.0);
            }

        } else {
            entity.setWorkingHours("0.00");
            entity.setOvertimeHours("--");
            entity.setWorkingDays(0.0);
            entity.setDifference(0.0);
        }
    }

    public PaginatedResponse<AttendanceDTO> getAllAttendancesWithPagination(Long offset, Long limit) {
        List<AttendanceDTO> all = attendanceRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Attendance::getId).reversed())
                .map(AttendanceDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(all, offset, limit);
    }

    @Transactional
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO dto) {
        Attendance existing = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance record not found"));
        
        // Basic update mapping
        existing.setStatus(dto.getStatus());
        
        // Input from DTO is IST -> Store as UTC for DB
        if (dto.getCheckIn() != null) {
            existing.setCheckIn(dto.getCheckIn().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
        }
        if (dto.getCheckOut() != null) {
            existing.setCheckOut(dto.getCheckOut().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());
        }
        
        // Trigger calculation if both are present
        if (existing.getCheckIn() != null && existing.getCheckOut() != null) {
            LocalDateTime checkInDateTime = existing.getCheckIn();
            LocalDateTime checkOutDateTime = existing.getCheckOut();
            if (checkOutDateTime.isBefore(checkInDateTime)) {
                // If checkOut is before checkIn (e.g., night shift checkin at 8 PM, checkout at 8 AM next day),
                // it implies check-out is on the next calendar day.
                checkOutDateTime = checkOutDateTime.plusDays(1);
                existing.setCheckOut(checkOutDateTime);
            }
            calculateHoursAndDays(existing, checkInDateTime, checkOutDateTime, existing.getEmployee());
        } else {
            existing.setWorkingHours(dto.getWorkingHours());
            existing.setOvertimeHours(dto.getOvertimeHours());
            existing.setWorkingDays(dto.getWorkingDays());
            existing.setDifference(dto.getDifference());
        }
        
        if (dto.getOtCheckIn() != null) {
            LocalDateTime otCheckInDateTime = dto.getOtCheckIn().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            existing.setOtCheckIn(otCheckInDateTime);
        }

        if (dto.getOtCheckOut() != null) {
            LocalDateTime otCheckOutDateTime = dto.getOtCheckOut().atZone(ZoneId.of("Asia/Kolkata")).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            LocalDateTime otIn = existing.getOtCheckIn();
            if (otIn != null && otCheckOutDateTime.isBefore(otIn)) {
                otCheckOutDateTime = otCheckOutDateTime.plusDays(1);
            }
            existing.setOtCheckOut(otCheckOutDateTime);
        }

        // Calculate OT hours from otCheckIn and otCheckOut if present
        if (existing.getOtCheckIn() != null && existing.getOtCheckOut() != null) {
            LocalDateTime otIn = existing.getOtCheckIn();
            LocalDateTime otOut = existing.getOtCheckOut();
            if (otOut.isBefore(otIn)) {
                otOut = otOut.plusDays(1);
            }
            if (otOut.isAfter(otIn)) {
                long minutes = java.time.Duration.between(otIn, otOut).toMinutes();
                double hours = minutes / 60.0;
                if (hours > 0) {
                    existing.setOvertimeHours(String.format("%.2f", hours));
                }
            }
        } else if (dto.getOvertimeHours() != null && !dto.getOvertimeHours().trim().isEmpty() && !dto.getOvertimeHours().trim().equals("--")) {
            // If user provided OT hours directly, save/include in overtimeHours column
            try {
                double parsedOt = Double.parseDouble(dto.getOvertimeHours().trim());
                if (parsedOt > 0) {
                    existing.setOvertimeHours(String.format("%.2f", parsedOt));
                }
            } catch (NumberFormatException e) {
                existing.setOvertimeHours(dto.getOvertimeHours().trim());
            }
        }

        Attendance updated = attendanceRepository.save(existing);
        return new AttendanceDTO(updated);
    }

    @Transactional
    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new IllegalArgumentException("Attendance record not found");
        }
        attendanceRepository.deleteById(id);
    }

    @Transactional
    public List<AttendanceDTO> createAttendancesBulk(List<AttendanceDTO> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new IllegalArgumentException("Attendance list cannot be null or empty");
        }
        List<AttendanceDTO> result = new java.util.ArrayList<>();
        for (AttendanceDTO dto : dtos) {
            result.add(createAttendance(dto));
            attendanceRepository.flush(); // Flush to database to let subsequent check-in/out updates find the existing record
        }
        return result;
    }
}
