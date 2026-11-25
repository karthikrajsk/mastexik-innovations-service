package com.tech3.erp.service;

import com.tech3.erp.dto.AddressDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.MillDTO;
import com.tech3.erp.entity.Address;
import com.tech3.erp.entity.City;
import com.tech3.erp.entity.Country;
import com.tech3.erp.entity.State;
import com.tech3.erp.entity.SubCategory;
import com.tech3.erp.entity.Mill;
import com.tech3.erp.repository.AddressRepository;
import com.tech3.erp.repository.CityRepository;
import com.tech3.erp.repository.CountryRepository;
import com.tech3.erp.repository.StateRepository;
import com.tech3.erp.repository.SubCategoryRepository;
import com.tech3.erp.repository.MillRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MillService {

    private final MillRepository millRepository;
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final SubCategoryRepository subCategoryRepository;

    public MillService(MillRepository millRepository,
                       AddressRepository addressRepository,
                       CountryRepository countryRepository,
                       StateRepository stateRepository,
                       CityRepository cityRepository,
                       SubCategoryRepository subCategoryRepository) {
        this.millRepository = millRepository;
        this.addressRepository = addressRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Transactional
    public MillDTO createMill(MillDTO dto) {
        Address address = null;
        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            AddressDTO addressDto = dto.getAddress().get(0);
            address = new Address();
            address.setLine1(addressDto.getLine1());
            address.setLine2(addressDto.getLine2());
            address.setCountry(getCountryById(addressDto.getCountryId()));
            address.setState(getStateById(addressDto.getStateId()));
            address.setCity(getCityById(addressDto.getCityId()));
            address.setPinCode(addressDto.getPinCode());
            address.setActiveFlag(addressDto.getActiveFlag() != null ? addressDto.getActiveFlag() : true);
        }

        Address savedAddress = address != null ? addressRepository.save(address) : null;

        Mill mill = new Mill();
        mill.setMillName(dto.getMillName());
        if (dto.getMillTypeId() != null) {
            mill.setMillType(getSubCategoryById(dto.getMillTypeId()));
        }
        mill.setContactPerson(dto.getContactPerson());
        mill.setContactNumber(dto.getContactNumber());
        mill.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        if (savedAddress != null) {
            mill.setAddress(savedAddress);
        }

        Mill savedMill = millRepository.save(mill);
        return new MillDTO(savedMill);
    }

    public MillDTO getMillById(Long id) {
        Mill mill = millRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mill not found"));
        return new MillDTO(mill);
    }

    public List<MillDTO> getAllMills() {
        return millRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(MillDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<MillDTO> getAllMillsWithPagination(Long offset, Long limit) {
        List<MillDTO> mills = getAllMills();
        return PaginationHelper.paginate(mills, offset, limit);
    }

    public List<String> getAllMillNames() {
        return millRepository.findAllMillNames();
    }

    @Transactional
    public MillDTO updateMill(Long id, MillDTO dto) {
        Mill mill = millRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mill not found"));

        mill.setMillName(dto.getMillName());
        if (dto.getMillTypeId() != null) {
            mill.setMillType(getSubCategoryById(dto.getMillTypeId()));
        } else {
            mill.setMillType(null);
        }
        mill.setContactPerson(dto.getContactPerson());
        mill.setContactNumber(dto.getContactNumber());
        mill.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);

        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            AddressDTO addressDto = dto.getAddress().get(0);
            Address address = mill.getAddress();
            if (address == null) {
                address = new Address();
            }
            address.setLine1(addressDto.getLine1());
            address.setLine2(addressDto.getLine2());
            address.setCountry(getCountryById(addressDto.getCountryId()));
            address.setState(getStateById(addressDto.getStateId()));
            address.setCity(getCityById(addressDto.getCityId()));
            address.setPinCode(addressDto.getPinCode());
            address.setActiveFlag(addressDto.getActiveFlag() != null ? addressDto.getActiveFlag() : true);

            Address savedAddress = addressRepository.save(address);
            mill.setAddress(savedAddress);
        }

        Mill updatedMill = millRepository.save(mill);
        return new MillDTO(updatedMill);
    }

    @Transactional
    public void deleteMill(Long id) {
        if (!millRepository.existsById(id)) {
            throw new EntityNotFoundException("Mill not found");
        }
        millRepository.deleteById(id);
    }

    private Country getCountryById(Long id) {
        if (id == null) return null;
        return countryRepository.findById(id).orElse(null);
    }

    private State getStateById(Long id) {
        if (id == null) return null;
        return stateRepository.findById(id).orElse(null);
    }

    private City getCityById(Long id) {
        if (id == null) return null;
        return cityRepository.findById(id).orElse(null);
    }

    private SubCategory getSubCategoryById(Long id) {
        if (id == null) return null;
        return subCategoryRepository.findById(id).orElse(null);
    }
}
