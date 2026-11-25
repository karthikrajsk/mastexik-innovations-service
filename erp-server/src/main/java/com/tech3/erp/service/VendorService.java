package com.tech3.erp.service;

import com.tech3.erp.dto.AddressDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.dto.VendorBankDetailDTO;
import com.tech3.erp.dto.VendorCertificateDTO;
import com.tech3.erp.dto.VendorDocumentDTO;
import com.tech3.erp.dto.VendorDTO;
import com.tech3.erp.entity.Address;
import com.tech3.erp.entity.City;
import com.tech3.erp.entity.Country;
import com.tech3.erp.entity.Customer;
import com.tech3.erp.entity.State;
import com.tech3.erp.entity.VendorBankDetail;
import com.tech3.erp.entity.VendorCertificate;
import com.tech3.erp.entity.VendorDocument;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.repository.AddressRepository;
import com.tech3.erp.repository.CityRepository;
import com.tech3.erp.repository.CountryRepository;
import com.tech3.erp.repository.StateRepository;
import com.tech3.erp.repository.VendorRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final EntityManager entityManager;

    public VendorService(VendorRepository vendorRepository,
                         AddressRepository addressRepository,
                         CountryRepository countryRepository,
                         StateRepository stateRepository,
                         CityRepository cityRepository,
                         EntityManager entityManager) {
        this.vendorRepository = vendorRepository;
        this.addressRepository = addressRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.entityManager = entityManager;
    }

    public VendorDTO createVendor(VendorDTO dto) {
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

        Vendor vendor = new Vendor();
        vendor.setVendorName(dto.getVendorName());
        vendor.setVendorTypeId(convertVendorTypeIdsToString(dto.getVendorTypeIds()));
        vendor.setGstno(dto.getGstno());
        vendor.setPancard(dto.getPancard());
        vendor.setMobileno(dto.getMobileno());
        vendor.setEmail(dto.getEmail());
        vendor.setContactPerson(dto.getContactPerson());
        vendor.setContactNumber(dto.getContactNumber());
        vendor.setPhotoUrl(dto.getPhotoUrl());
        vendor.setIsInternalVendor(dto.getIsInternalVendor());
        vendor.setPaymentTermsId(dto.getPaymentTermsId());
        vendor.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        applyOptionalVendorDetails(dto, vendor);
        if (savedAddress != null) {
            vendor.setAddress(savedAddress);
        }

        try {
            Vendor savedVendor = vendorRepository.save(vendor);
            return new VendorDTO(savedVendor);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("vendor_pkey") && e.getMessage().contains("already exists")) {
                try {
                    fixVendorSequence();
                    Vendor savedVendor = vendorRepository.save(vendor);
                    return new VendorDTO(savedVendor);
                } catch (Exception seqException) {
                    throw new IllegalStateException(
                        "Cannot create vendor: Database sequence is out of sync and could not be automatically fixed. " +
                        "Please contact the database administrator to reset the vendor sequence. " +
                        "Error: " + e.getMessage()
                    );
                }
            }
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public VendorDTO getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));
        return new VendorDTO(vendor);
    }

    @Transactional(readOnly = true)
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .map(VendorDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PaginatedResponse<VendorDTO> getAllVendorsWithPagination(Long offset, Long limit) {
        List<VendorDTO> vendors = getAllVendors();
        return PaginationHelper.paginate(vendors, offset, limit);
    }
    
    public List<String> getAllVendorNames() {
        return vendorRepository.findAllVendorNames();
    }
    
    @Transactional(readOnly = true)
    public List<VendorDTO> getVendorsByVendorType(Long vendorTypeId) {
        // Convert Long to String for the repository query
        String vendorTypeIdStr = vendorTypeId != null ? String.valueOf(vendorTypeId) : null;
        return vendorRepository.findByVendorTypeId(vendorTypeIdStr).stream()
                .map(VendorDTO::new)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<VendorDTO> getVendorsByPoType(Long poTypeId) {
        List<Vendor> allVendors = vendorRepository.findAll();
        return allVendors.stream()
            .filter(vendor -> vendor.getActiveFlag() != null && vendor.getActiveFlag())
            .filter(vendor -> {
                String vendorTypeIdStr = vendor.getVendorTypeId();
                if (poTypeId == 1) {
                    return vendorTypeIdStr == null || vendorTypeIdStr.equals("1");
                } else if (poTypeId == 2) {
                    return vendorTypeIdStr == null || vendorTypeIdStr.equals("2");
                } else if (poTypeId == 3) {
                    return vendorTypeIdStr != null && vendorTypeIdStr.equals("3");
                } else {
                    return true;
                }
            })
            .map(VendorDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public VendorDTO updateVendor(Long id, VendorDTO dto) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vendor not found"));

        vendor.setVendorName(dto.getVendorName());
        vendor.setVendorTypeId(convertVendorTypeIdsToString(dto.getVendorTypeIds()));
        vendor.setGstno(dto.getGstno());
        vendor.setPancard(dto.getPancard());
        vendor.setMobileno(dto.getMobileno());
        vendor.setEmail(dto.getEmail());
        vendor.setContactPerson(dto.getContactPerson());
        vendor.setContactNumber(dto.getContactNumber());
        vendor.setPhotoUrl(dto.getPhotoUrl());
        vendor.setIsInternalVendor(dto.getIsInternalVendor());
        vendor.setPaymentTermsId(dto.getPaymentTermsId());
        vendor.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        applyOptionalVendorDetails(dto, vendor);

        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            AddressDTO addressDto = dto.getAddress().get(0);
            Address address = new Address();
            address.setLine1(addressDto.getLine1());
            address.setLine2(addressDto.getLine2());
            address.setCountry(getCountryById(addressDto.getCountryId()));
            address.setState(getStateById(addressDto.getStateId()));
            address.setCity(getCityById(addressDto.getCityId()));
            address.setPinCode(addressDto.getPinCode()); 
            address.setActiveFlag(addressDto.getActiveFlag() != null ? addressDto.getActiveFlag() : true);

            Address savedAddress = addressRepository.save(address);
            vendor.setAddress(savedAddress);
        }

        Vendor updatedVendor = vendorRepository.save(vendor);
        return new VendorDTO(updatedVendor);
    }

    private void applyOptionalVendorDetails(VendorDTO dto, Vendor vendor) {
        if (Boolean.TRUE.equals(dto.getClearGstDocument())) {
            vendor.setGstDocumentMediaDetailSno(null);
            vendor.setGstDocumentMediaUrl(null);
            vendor.setGstDocumentFileName(null);
        } else if (dto.getGstDocument() != null) {
            vendor.setGstDocumentMediaDetailSno(dto.getGstDocument().getMediaDetailSno());
            vendor.setGstDocumentMediaUrl(dto.getGstDocument().getMediaUrl());
            vendor.setGstDocumentFileName(dto.getGstDocument().getFileName());
        }

        if (dto.getBankDetails() != null) {
            vendor.getBankDetails().clear();
            for (VendorBankDetailDTO bankDto : dto.getBankDetails()) {
                VendorBankDetail bank = new VendorBankDetail();
                bank.setBankName(bankDto.getBankName());
                bank.setAccountNo(bankDto.getAccountNo());
                bank.setIfscCode(bankDto.getIfscCode());
                bank.setVendor(vendor);
                vendor.getBankDetails().add(bank);
            }
        }

        if (dto.getCertificates() != null) {
            vendor.getCertificates().clear();
            for (VendorCertificateDTO certificateDto : dto.getCertificates()) {
                VendorCertificate certificate = new VendorCertificate();
                certificate.setCertificateNo(certificateDto.getCertificateNo());
                certificate.setExpiryDate(certificateDto.getExpiryDate());
                certificate.setType(certificateDto.getType());
                certificate.setVendor(vendor);
                if (certificateDto.getFiles() != null) {
                    for (VendorDocumentDTO fileDto : certificateDto.getFiles()) {
                        VendorDocument file = new VendorDocument();
                        file.setMediaDetailSno(fileDto.getMediaDetailSno());
                        file.setMediaUrl(fileDto.getMediaUrl());
                        file.setFileName(fileDto.getFileName());
                        certificate.getFiles().add(file);
                    }
                }
                vendor.getCertificates().add(certificate);
            }
        }
    }

//    public void deleteVendor(Long id) {
//        if (!vendorRepository.existsById(id)) {
//            throw new IllegalArgumentException("Vendor not found");
//        }
//        vendorRepository.deleteById(id);
//    }
    public void deleteVendor(Long id) {
        // Check if category exists first
        if (!vendorRepository.existsById(id)) {
            throw new EntityNotFoundException("Vendor not found with id: " + id);
        }
        
        try {
        	vendorRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Vendor because it is referenced by other");
        }
    }

    @Transactional
    private void fixVendorSequence() {
        try {
            // Reset the vendor sequence to the maximum ID + 1
            // Using getSingleResult() since setval returns a value
            entityManager.createNativeQuery(
                "SELECT setval('masters.vendor_id_seq', COALESCE((SELECT MAX(id) FROM masters.vendor), 0) + 1, false)"
            ).getSingleResult();
        } catch (Exception e) {
            // If the sequence doesn't exist or has a different name, try alternative
            try {
                entityManager.createNativeQuery(
                    "SELECT setval('vendor_id_seq', COALESCE((SELECT MAX(id) FROM masters.vendor), 0) + 1, false)"
                ).getSingleResult();
            } catch (Exception e2) {
                throw new RuntimeException("Failed to fix vendor sequence: " + e2.getMessage());
            }
        }
    }

    // Utility methods to fetch referenced entities
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
    
    // Utility method to convert list of vendor type IDs to comma-separated string
    private String convertVendorTypeIdsToString(List<Long> vendorTypeIds) {
        if (vendorTypeIds == null || vendorTypeIds.isEmpty()) {
            return null;
        }
        return vendorTypeIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }
    
    // Utility method to convert comma-separated string to list of vendor type IDs
    private List<Long> convertStringToVendorTypeIds(String vendorTypeIdStr) {
        if (vendorTypeIdStr == null || vendorTypeIdStr.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return Arrays.stream(vendorTypeIdStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            // Handle backward compatibility for single values
            try {
                return Arrays.asList(Long.parseLong(vendorTypeIdStr.trim()));
            } catch (NumberFormatException ex) {
                return new ArrayList<>();
            }
        }
    }
}