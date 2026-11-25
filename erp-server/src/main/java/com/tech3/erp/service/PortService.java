package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.PortDTO;
import com.tech3.erp.entity.City;
import com.tech3.erp.entity.Country;
import com.tech3.erp.entity.Port;
import com.tech3.erp.entity.SubCategory;
import com.tech3.erp.repository.*;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortService {

    private final PortRepository portRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public PortService(PortRepository portRepository,
                       SubCategoryRepository subCategoryRepository,
                       CountryRepository countryRepository,
                       CityRepository cityRepository) {
        this.portRepository = portRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
    }

    public PortDTO createPort(PortDTO dto) {
        Port port = new Port();
        port.setPortName(dto.getPortName());
        port.setActiveFlag(dto.getActiveFlag());

        if (dto.getPortTypeId() != null) {
            port.setPortType(subCategoryRepository.findById(dto.getPortTypeId()).orElse(null));
        }
        if (dto.getCountryId() != null) {
            port.setCountry(countryRepository.findById(dto.getCountryId()).orElse(null));
        }
        if (dto.getCityId() != null) {
            port.setCity(cityRepository.findById(dto.getCityId()).orElse(null));
        }

        Port savedPort = portRepository.save(port);
        return new PortDTO(savedPort);
    }

    public PortDTO updatePort(Long id, PortDTO dto) {
        Port port = portRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + id));

        port.setPortName(dto.getPortName());
        port.setActiveFlag(dto.getActiveFlag());

        if (dto.getPortTypeId() != null) {
            port.setPortType(subCategoryRepository.findById(dto.getPortTypeId()).orElse(null));
        } else {
            port.setPortType(null);
        }

        if (dto.getCountryId() != null) {
            port.setCountry(countryRepository.findById(dto.getCountryId()).orElse(null));
        } else {
            port.setCountry(null);
        }

        if (dto.getCityId() != null) {
            port.setCity(cityRepository.findById(dto.getCityId()).orElse(null));
        } else {
            port.setCity(null);
        }

        Port updatedPort = portRepository.save(port);
        return new PortDTO(updatedPort);
    }

    public PortDTO getPortById(Long id) {
        Port port = portRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Port not found with id: " + id));
        return new PortDTO(port);
    }

    public PaginatedResponse<PortDTO> getAllPortsWithPagination(Long offset, Long limit) {
        List<PortDTO> allPorts = portRepository.findAll().stream()
                .sorted(Comparator.comparing(Port::getId, Comparator.reverseOrder()))
                .map(PortDTO::new)
                .collect(Collectors.toList());

        return PaginationHelper.paginate(allPorts, offset, limit);
    }

    public List<PortDTO> getAllPorts() {
        return portRepository.findAll().stream()
                .sorted(Comparator.comparing(Port::getId, Comparator.reverseOrder()))
                .map(PortDTO::new)
                .collect(Collectors.toList());
    }

    public void deletePort(Long id) {
        if (!portRepository.existsById(id)) {
            throw new EntityNotFoundException("Port not found with id: " + id);
        }
        try {
            portRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Port because it is referenced by other entities");
        }
    }
}
