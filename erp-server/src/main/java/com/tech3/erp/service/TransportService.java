package com.tech3.erp.service;

import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.TransportDTO;
import com.tech3.erp.entity.Address;
import com.tech3.erp.entity.Transport;
import com.tech3.erp.repository.*;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportService {

    private final TransportRepository transportRepository;
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public TransportService(TransportRepository transportRepository,
                            AddressRepository addressRepository,
                            CountryRepository countryRepository,
                            StateRepository stateRepository,
                            CityRepository cityRepository) {
        this.transportRepository = transportRepository;
        this.addressRepository = addressRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    public TransportDTO createTransport(TransportDTO dto) {
        String normalizedTransportName = normalizeTransportName(dto.getTransportName());
        if (normalizedTransportName == null) {
            throw new IllegalArgumentException("Transport name cannot be blank");
        }
        String normalizedGstNo = normalizeGstNo(dto.getGstNo());

        ensureTransportNameUnique(normalizedTransportName, null);
        ensureGstNoUnique(normalizedGstNo, null);

        Transport transport = new Transport();
        transport.setTransportName(normalizedTransportName);
        transport.setGstNo(normalizedGstNo);
        transport.setActiveFlag(dto.getActiveFlag());

        if (dto.getAddress() != null) {
            Address address = new Address();
            mapAddressDtoToEntity(dto.getAddress(), address);
            transport.setAddress(address);
        }

        Transport savedTransport = transportRepository.save(transport);
        return new TransportDTO(savedTransport);
    }

    private void ensureTransportNameUnique(String transportName, Long currentId) {
        if (transportName == null) {
            return;
        }
        transportRepository.findByTransportName(transportName)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Transport name '" + transportName + "' already exists");
                });
    }

    private void ensureGstNoUnique(String gstNo, Long currentId) {
        if (gstNo == null) {
            return;
        }
        transportRepository.findByGstNo(gstNo)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException(gstNo + " already linked with " + existing.getTransportName());
                });
    }

    private String normalizeTransportName(String transportName) {
        if (transportName == null) {
            return null;
        }
        String trimmed = transportName.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String normalizeGstNo(String gstNo) {
        if (gstNo == null) {
            return null;
        }
        String trimmed = gstNo.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public TransportDTO updateTransport(Long id, TransportDTO dto) {
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transport not found with id: " + id));

        String normalizedTransportName = normalizeTransportName(dto.getTransportName());
        if (normalizedTransportName == null) {
            throw new IllegalArgumentException("Transport name cannot be blank");
        }
        String normalizedGstNo = normalizeGstNo(dto.getGstNo());

        ensureTransportNameUnique(normalizedTransportName, id);
        ensureGstNoUnique(normalizedGstNo, id);

        transport.setTransportName(normalizedTransportName);
        transport.setGstNo(normalizedGstNo);
        transport.setActiveFlag(dto.getActiveFlag());

        if (dto.getAddress() != null) {
            Address address = transport.getAddress();
            if (address == null) {
                address = new Address();
            }
            mapAddressDtoToEntity(dto.getAddress(), address);
            transport.setAddress(address);
        } else {
            transport.setAddress(null);
        }

        Transport updatedTransport = transportRepository.save(transport);
        return new TransportDTO(updatedTransport);
    }

    private void mapAddressDtoToEntity(com.tech3.erp.dto.AddressDTO dto, Address entity) {
        entity.setLine1(dto.getLine1());
        entity.setLine2(dto.getLine2());
        entity.setPinCode(dto.getPinCode());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        
        if (dto.getCountryId() != null) {
            entity.setCountry(countryRepository.findById(dto.getCountryId()).orElse(null));
        } else {
            entity.setCountry(null);
        }
        
        if (dto.getStateId() != null) {
            entity.setState(stateRepository.findById(dto.getStateId()).orElse(null));
        } else {
            entity.setState(null);
        }
        
        if (dto.getCityId() != null) {
            entity.setCity(cityRepository.findById(dto.getCityId()).orElse(null));
        } else {
            entity.setCity(null);
        }
    }

    public TransportDTO getTransportById(Long id) {
        Transport transport = transportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transport not found with id: " + id));
        return new TransportDTO(transport);
    }

    public PaginatedResponse<TransportDTO> getAllTransportsWithPagination(Long offset, Long limit) {
        List<TransportDTO> allTransports = transportRepository.findAll().stream()
                .sorted(Comparator.comparing(Transport::getId, Comparator.reverseOrder()))
                .map(TransportDTO::new)
                .collect(Collectors.toList());

        return PaginationHelper.paginate(allTransports, offset, limit);
    }

    public List<TransportDTO> getAllTransports() {
        return transportRepository.findAll().stream()
                .sorted(Comparator.comparing(Transport::getId, Comparator.reverseOrder()))
                .map(TransportDTO::new)
                .collect(Collectors.toList());
    }

    public void deleteTransport(Long id) {
        if (!transportRepository.existsById(id)) {
            throw new EntityNotFoundException("Transport not found with id: " + id);
        }
        try {
            transportRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Transport because it is referenced by other entities");
        }
    }
}
