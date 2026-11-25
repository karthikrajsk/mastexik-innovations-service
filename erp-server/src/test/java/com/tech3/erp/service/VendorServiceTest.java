package com.tech3.erp.service;

import com.tech3.erp.dto.VendorBankDetailDTO;
import com.tech3.erp.dto.VendorCertificateDTO;
import com.tech3.erp.dto.VendorDocumentDTO;
import com.tech3.erp.dto.VendorDTO;
import com.tech3.erp.entity.Vendor;
import com.tech3.erp.entity.VendorBankDetail;
import com.tech3.erp.entity.VendorCertificate;
import com.tech3.erp.entity.VendorDocument;
import com.tech3.erp.repository.AddressRepository;
import com.tech3.erp.repository.CityRepository;
import com.tech3.erp.repository.CountryRepository;
import com.tech3.erp.repository.StateRepository;
import com.tech3.erp.repository.VendorRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendorServiceTest {

    @Mock
    private VendorRepository vendorRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private StateRepository stateRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private EntityManager entityManager;

    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorService(
                vendorRepository,
                addressRepository,
                countryRepository,
                stateRepository,
                cityRepository,
                entityManager);
    }

    @Test
    void updateVendorPersistsOptionalDocumentsAndBankDetails() {
        Vendor vendor = existingVendor();
        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VendorDTO request = new VendorDTO();
        request.setVendorName("Vendor");
        request.setGstDocument(new VendorDocumentDTO(101L, "https://files.test/gst.pdf", "gst.pdf"));
        request.setBankDetails(List.of(new VendorBankDetailDTO(null, "Bank", "1234", "IFSC123")));
        request.setCertificates(List.of(new VendorCertificateDTO(
                null,
                "CERT-1",
                LocalDate.of(2028, 1, 31),
                "Certificate name",
                List.of(new VendorDocumentDTO(102L, "https://files.test/cert.pdf", "cert.pdf")))));

        VendorDTO response = vendorService.updateVendor(1L, request);

        assertEquals(101L, response.getGstDocument().getMediaDetailSno());
        assertEquals(1, response.getBankDetails().size());
        assertEquals("Bank", response.getBankDetails().get(0).getBankName());
        assertEquals(1, response.getCertificates().size());
        assertEquals("Certificate name", response.getCertificates().get(0).getType());
        assertEquals(102L, response.getCertificates().get(0).getFiles().get(0).getMediaDetailSno());
    }

    @Test
    void updateVendorPreservesOptionalFieldsWhenLegacyRequestOmitsThem() {
        Vendor vendor = existingVendor();
        vendor.setGstDocumentMediaDetailSno(201L);
        vendor.setGstDocumentMediaUrl("https://files.test/old-gst.pdf");
        vendor.setGstDocumentFileName("old-gst.pdf");

        VendorBankDetail bank = new VendorBankDetail();
        bank.setVendor(vendor);
        bank.setBankName("Existing Bank");
        vendor.getBankDetails().add(bank);

        VendorCertificate certificate = new VendorCertificate();
        certificate.setVendor(vendor);
        certificate.setType("Existing certificate");
        VendorDocument file = new VendorDocument();
        file.setMediaDetailSno(202L);
        file.setMediaUrl("https://files.test/old-cert.pdf");
        file.setFileName("old-cert.pdf");
        certificate.getFiles().add(file);
        vendor.getCertificates().add(certificate);

        when(vendorRepository.findById(1L)).thenReturn(Optional.of(vendor));
        when(vendorRepository.save(any(Vendor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VendorDTO request = new VendorDTO();
        request.setVendorName("Vendor");

        VendorDTO response = vendorService.updateVendor(1L, request);

        assertEquals(201L, response.getGstDocument().getMediaDetailSno());
        assertEquals("Existing Bank", response.getBankDetails().get(0).getBankName());
        assertEquals("Existing certificate", response.getCertificates().get(0).getType());
        assertEquals(202L, response.getCertificates().get(0).getFiles().get(0).getMediaDetailSno());
    }

    private Vendor existingVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(1L);
        vendor.setVendorName("Vendor");
        return vendor;
    }
}
