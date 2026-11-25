package com.tech3.erp.service;

import com.tech3.erp.dto.AddressDTO;
import com.tech3.erp.dto.ConsigneeDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Address;
import com.tech3.erp.entity.City;
import com.tech3.erp.entity.Consignee;
import com.tech3.erp.entity.Country;
import com.tech3.erp.entity.State;
import com.tech3.erp.repository.AddressRepository;
import com.tech3.erp.repository.CityRepository;
import com.tech3.erp.repository.ConsigneeRepository;
import com.tech3.erp.repository.CountryRepository;
import com.tech3.erp.repository.StateRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsigneeService {

    private final ConsigneeRepository consigneeRepository;
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public ConsigneeService(ConsigneeRepository consigneeRepository,
                           AddressRepository addressRepository,
                           CountryRepository countryRepository,
                           StateRepository stateRepository,
                           CityRepository cityRepository) {
        this.consigneeRepository = consigneeRepository;
        this.addressRepository = addressRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    public ConsigneeDTO createConsignee(ConsigneeDTO dto) {
        List<Address> savedAddresses = new ArrayList<>();
        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            for (AddressDTO addressDto : dto.getAddress()) {
                Address address = buildAddress(addressDto);
                savedAddresses.add(addressRepository.save(address));
            }
        }

        Consignee consignee = new Consignee();
        consignee.setConsigneeName(dto.getConsigneeName());
        consignee.setGstno(dto.getGstNo());
        consignee.setPancard(dto.getPanCard());
        consignee.setMobileno(dto.getMobileNo());
        consignee.setEmail(dto.getEmail());
        if (!savedAddresses.isEmpty()) {
            consignee.setAddress(savedAddresses.get(0));
            consignee.setAddresses(savedAddresses);
        }
        consignee.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        consignee.setContactPerson(sanitizeNullable(dto.getContactPerson()));
        consignee.setContactNumber(sanitizeNullable(dto.getContactNumber()));

        Consignee savedConsignee = consigneeRepository.save(consignee);
        return new ConsigneeDTO(savedConsignee);
    }

    public ConsigneeDTO getConsigneeById(Long id) {
        Consignee consignee = consigneeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consignee not found"));
        return new ConsigneeDTO(consignee);
    }

    public List<ConsigneeDTO> getAllConsignee() {
        return consigneeRepository.findAll().stream()
                .sorted(Comparator.comparing(Consignee::getId, Comparator.reverseOrder()))
                .map(ConsigneeDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<ConsigneeDTO> getAllConsigneeWithPagination(Long offset, Long limit) {
        List<ConsigneeDTO> allConsignees = consigneeRepository.findAll().stream()
                .sorted(Comparator.comparing(Consignee::getId, Comparator.reverseOrder()))
                .map(ConsigneeDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allConsignees, offset, limit);
    }

    public ConsigneeDTO updateConsignee(Long id, ConsigneeDTO dto) {
        Consignee consignee = consigneeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consignee not found"));

        consignee.setConsigneeName(dto.getConsigneeName());
        consignee.setGstno(dto.getGstNo());
        consignee.setPancard(dto.getPanCard());
        consignee.setMobileno(dto.getMobileNo());
        consignee.setEmail(dto.getEmail());
        consignee.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        consignee.setContactPerson(sanitizeNullable(dto.getContactPerson()));    // update contact person
        consignee.setContactNumber(sanitizeNullable(dto.getContactNumber()));    // update contact number

        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            List<Address> savedAddresses = new ArrayList<>();
            for (AddressDTO addressDto : dto.getAddress()) {
                savedAddresses.add(addressRepository.save(buildAddress(addressDto)));
            }
            consignee.setAddress(savedAddresses.get(0));
            consignee.setAddresses(savedAddresses);
        }

        Consignee updatedConsignee = consigneeRepository.save(consignee);
        return new ConsigneeDTO(updatedConsignee);
    }

    public void deleteConsignee(Long id) {
        if (!consigneeRepository.existsById(id)) {
            throw new EntityNotFoundException("Consignee not found with id: " + id);
        }

        try {
            consigneeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Consignee because it is referenced by other entities");
        }
    }

    private Address buildAddress(AddressDTO dto) {
        Address address = new Address();
        address.setLine1(dto.getLine1());
        address.setLine2(dto.getLine2());
        address.setCountry(getCountryById(dto.getCountryId()));
        address.setState(getStateById(dto.getStateId()));
        address.setCity(getCityById(dto.getCityId()));
        address.setPinCode(dto.getPinCode());
        address.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        return address;
    }

    private String sanitizeNullable(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    private Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid country ID"));
    }

    private State getStateById(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid state ID"));
    }

    private City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid city ID"));
    }
}
