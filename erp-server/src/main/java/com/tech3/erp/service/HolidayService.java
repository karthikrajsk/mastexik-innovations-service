package com.tech3.erp.service;

import com.tech3.erp.entity.Holiday;
import com.tech3.erp.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    public Optional<Holiday> getHolidayById(Long id) {
        return holidayRepository.findById(id);
    }

    public Holiday createHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    public Holiday updateHoliday(Long id, Holiday updatedHoliday) {
        return holidayRepository.findById(id).map(existing -> {
            existing.setHolidayName(updatedHoliday.getHolidayName());
            existing.setHolidayDate(updatedHoliday.getHolidayDate());
            existing.setIsFestivalWageApplicable(updatedHoliday.getIsFestivalWageApplicable());
            existing.setDescription(updatedHoliday.getDescription());
            existing.setStatus(updatedHoliday.getStatus());
            return holidayRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Holiday not found with id " + id));
    }

    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
    }
}
