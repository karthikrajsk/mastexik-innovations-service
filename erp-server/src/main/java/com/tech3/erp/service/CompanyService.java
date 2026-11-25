package com.tech3.erp.service;

import com.tech3.erp.dto.CompanyDTO;
import com.tech3.erp.dto.CompanyAddressDTO;
import com.tech3.erp.dto.CompanyBankDTO;
import com.tech3.erp.dto.CompanyInvoiceDTO;
import com.tech3.erp.dto.CompanyLCBankDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Company;
import com.tech3.erp.entity.CompanyAddress;
import com.tech3.erp.entity.CompanyBank;
import com.tech3.erp.entity.CompanyInvoice;
import com.tech3.erp.entity.CompanyLCBank;
import com.tech3.erp.entity.Media;
import com.tech3.erp.entity.State;
import com.tech3.erp.repository.CompanyRepository;
import com.tech3.erp.repository.MediaRepository;
import com.tech3.erp.repository.StateRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final StateRepository stateRepository;
    private final MediaRepository mediaRepository;


    public CompanyService(CompanyRepository companyRepository, StateRepository stateRepository, MediaRepository mediaRepository) {
        this.companyRepository = companyRepository;
        this.stateRepository = stateRepository;
        this.mediaRepository = mediaRepository;
    }

    @Transactional
    public CompanyDTO createCompany(CompanyDTO dto) {
        String companyName = trimValue(dto.getCompanyName());
        if (!companyName.isEmpty() && companyRepository.existsByCompanyNameIgnoreCase(companyName)) {
            throw new IllegalArgumentException("Company Name already exists: " + companyName);
        }
        String companyCode = trimValue(dto.getCompanyCode());
        if (!companyCode.isEmpty() && companyRepository.existsByCompanyCodeIgnoreCase(companyCode)) {
            throw new IllegalArgumentException("Company Code already exists: " + companyCode);
        }
        String gstNo = trimValue(dto.getGstNo());
        if (!gstNo.isEmpty() && companyRepository.existsByGstNo(gstNo)) {
            throw new IllegalArgumentException("GST No already exists: " + gstNo);
        }

        if (gstNo.length() == 15) {
            String extractedPan = gstNo.substring(2, 12).toUpperCase();
            dto.setPan(extractedPan);
        }
 // Handle logo media if provided
        if (dto.getLogoMediaDetailSno() != null) {
            Media logoMedia = mediaRepository.findByMediaDetailSno(dto.getLogoMediaDetailSno())
                    .orElseThrow(() -> new EntityNotFoundException("Media not found for detail SNO: " + dto.getLogoMediaDetailSno()));
            dto.setLogoMediaSno(logoMedia.getMediaSno());
        }
        Company entity = new Company();
        mapDtoToEntity(dto, entity);

        // Set nested entities with back-references
        if (dto.getAddress() != null && !dto.getAddress().isEmpty()) {
            List<CompanyAddress> addresses = dto.getAddress().stream().map(addrDto -> {
                CompanyAddress addr = new CompanyAddress();
                addr.setLine1(addrDto.getLine1());
                if (addrDto.getStateId() != null) {
                    State state = stateRepository.findById(addrDto.getStateId())
                        .orElseThrow(() -> new IllegalArgumentException("State not found with ID: " + addrDto.getStateId()));
                    addr.setState(state);
                }
                
                addr.setStateCode(addrDto.getStateCode());
                addr.setPinCode(addrDto.getPinCode());
                addr.setAddressType(addrDto.getAddressType());
                addr.setIsDefault(addrDto.getIsDefault());
                addr.setCompany(entity); // 🔥 Set back-reference
                return addr;
            }).collect(Collectors.toList());
            entity.setAddresses(addresses);
        }



        if (dto.getBank() != null && !dto.getBank().isEmpty()) {
            List<CompanyBank> banks = dto.getBank().stream().map(bankDto -> {
                CompanyBank bank = new CompanyBank();
                bank.setBankName(bankDto.getBankName());
                bank.setAccountNo(bankDto.getAccountNo());
                bank.setIfscCode(bankDto.getIfscCode());
                bank.setCompany(entity); // 🔥 Set back-reference
                return bank;
            }).collect(Collectors.toList());
            entity.setBanks(banks);
        }

        if (dto.getLcBank() != null && !dto.getLcBank().isEmpty()) {
            List<CompanyLCBank> lcBanks = dto.getLcBank().stream().map(lcBankDto -> {
                CompanyLCBank lcBank = new CompanyLCBank();
                lcBank.setLcBankRefNo(lcBankDto.getLcBankRefNo());
                lcBank.setDateOfIssue(lcBankDto.getDateOfIssue());
                lcBank.setLutNo(lcBankDto.getLutNo());
                lcBank.setCompany(entity);
                return lcBank;
            }).collect(Collectors.toList());
            entity.setLcBanks(lcBanks);
        }

        if (dto.getInvoiceItems() != null && !dto.getInvoiceItems().isEmpty()) {
            List<CompanyInvoice> invoices = dto.getInvoiceItems().stream().map(invoiceDto -> {
                CompanyInvoice invoice = new CompanyInvoice();
                invoice.setInvoiceTypeId(invoiceDto.getInvoiceTypeId());
                invoice.setPrefix(invoiceDto.getPrefix());
                invoice.setFinancialYear(invoiceDto.getFinancialYear());
                invoice.setSequenceNumber(invoiceDto.getSequenceNumber());
                invoice.setPreview(invoiceDto.getPreview());
                invoice.setCompany(entity);
                return invoice;
            }).collect(Collectors.toList());
            entity.setInvoiceItems(invoices);
        }

        Company saved = companyRepository.save(entity);
        
        // Seed default reports and number format configurations for the new company
        companyRepository.seedDefaultReports(saved.getId());
        companyRepository.seedNumberFormatConfigurations(saved.getId());

        return new CompanyDTO(saved);
    }

    public CompanyDTO getCompanyById(Long id) {
        Company entity = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + id));
        return new CompanyDTO(entity);
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(CompanyDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CompanyDTO> getAllCompaniesWithPagination(Long offset, Long limit) {
        List<CompanyDTO> allCompanies = companyRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(CompanyDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allCompanies, offset, limit);
    }

    @Transactional
    public CompanyDTO updateCompany(Long id, CompanyDTO dto) {
        Company existing = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + id));

        String companyName = trimValue(dto.getCompanyName());
        String companyCode = trimValue(dto.getCompanyCode());
        String gstNo = trimValue(dto.getGstNo());

        String existingName = trimValue(existing.getCompanyName());
        if (!companyName.isEmpty() && !companyName.equalsIgnoreCase(existingName)
                && companyRepository.existsByCompanyNameIgnoreCase(companyName)) {
            throw new IllegalArgumentException("Company Name already exists: " + companyName);
        }
        String existingCode = trimValue(existing.getCompanyCode());
        if (!companyCode.isEmpty() && !companyCode.equalsIgnoreCase(existingCode)
                && companyRepository.existsByCompanyCodeIgnoreCase(companyCode)) {
            throw new IllegalArgumentException("Company Code already exists: " + companyCode);
        }
        String existingGst = trimValue(existing.getGstNo());
        if (!gstNo.isEmpty() && !gstNo.equalsIgnoreCase(existingGst)
                && companyRepository.existsByGstNo(gstNo)) {
            throw new IllegalArgumentException("GST No already exists: " + gstNo);
        }

        if (gstNo.length() == 15) {
            String extractedPan = gstNo.substring(2, 12).toUpperCase();
            dto.setPan(extractedPan);
        }
        // Handle logo media if provided
        if (dto.getLogoMediaDetailSno() != null) {
            Media logoMedia = mediaRepository.findByMediaDetailSno(dto.getLogoMediaDetailSno())
                    .orElseThrow(() -> new EntityNotFoundException("Media not found for detail SNO: " + dto.getLogoMediaDetailSno()));
            dto.setLogoMediaSno(logoMedia.getMediaSno());
        }

        mapDtoToEntity(dto, existing);

        // Update addresses (merge changes)
        if (dto.getAddress() != null) {
            existing.getAddresses().removeIf(addr -> dto.getAddress().stream().noneMatch(d -> d.getId() != null && d.getId().equals(addr.getId())));
            for (CompanyAddressDTO addrDto : dto.getAddress()) {
                if (addrDto.getId() != null) {
                    existing.getAddresses().stream()
                        .filter(a -> a.getId().equals(addrDto.getId()))
                        .findFirst()
                        .ifPresent(addr -> {
                            addr.setLine1(addrDto.getLine1());
                            if (addrDto.getStateId() != null) {
                                State state = stateRepository.findById(addrDto.getStateId())
                                    .orElseThrow(() -> new IllegalArgumentException("State not found with ID: " + addrDto.getStateId()));
                                addr.setState(state);
                            }
                            addr.setStateCode(addrDto.getStateCode());
                            addr.setPinCode(addrDto.getPinCode());
                            addr.setAddressType(addrDto.getAddressType());
                            addr.setIsDefault(addrDto.getIsDefault());
                        });
                } else {
                    CompanyAddress addr = new CompanyAddress();
                    addr.setLine1(addrDto.getLine1());
                    if (addrDto.getStateId() != null) {
                        State state = stateRepository.findById(addrDto.getStateId())
                            .orElseThrow(() -> new IllegalArgumentException("State not found with ID: " + addrDto.getStateId()));
                        addr.setState(state);
                    }
                    addr.setStateCode(addrDto.getStateCode());
                    addr.setPinCode(addrDto.getPinCode());
                    addr.setAddressType(addrDto.getAddressType());
                    addr.setIsDefault(addrDto.getIsDefault());
                    addr.setCompany(existing);
                    existing.getAddresses().add(addr);
                }
            }
        } else {
            existing.getAddresses().clear();
        }



        // Update banks
        if (dto.getBank() != null) {
            existing.getBanks().removeIf(bank -> dto.getBank().stream().noneMatch(d -> d.getId() != null && d.getId().equals(bank.getId())));
            for (CompanyBankDTO bankDto : dto.getBank()) {
                if (bankDto.getId() != null) {
                    existing.getBanks().stream()
                        .filter(b -> b.getId().equals(bankDto.getId()))
                        .findFirst()
                        .ifPresent(bank -> {
                            bank.setBankName(bankDto.getBankName());
                            bank.setAccountNo(bankDto.getAccountNo());
                            bank.setIfscCode(bankDto.getIfscCode());
                        });
                } else {
                    CompanyBank bank = new CompanyBank();
                    bank.setBankName(bankDto.getBankName());
                    bank.setAccountNo(bankDto.getAccountNo());
                    bank.setIfscCode(bankDto.getIfscCode());
                    bank.setCompany(existing);
                    existing.getBanks().add(bank);
                }
            }
        } else {
            existing.getBanks().clear();
        }

          // Update LC Banks
        if (dto.getLcBank() != null) {
            existing.getLcBanks().removeIf(lc -> dto.getLcBank().stream().noneMatch(d -> d.getId() != null && d.getId().equals(lc.getId())));
            for (CompanyLCBankDTO lcBankDto : dto.getLcBank()) {
                if (lcBankDto.getId() != null) {
                    existing.getLcBanks().stream()
                        .filter(l -> l.getId().equals(lcBankDto.getId()))
                        .findFirst()
                        .ifPresent(lcBank -> {
                            lcBank.setLcBankRefNo(lcBankDto.getLcBankRefNo());
                            lcBank.setDateOfIssue(lcBankDto.getDateOfIssue());
                            lcBank.setLutNo(lcBankDto.getLutNo());
                        });
                } else {
                    CompanyLCBank lcBank = new CompanyLCBank();
                    lcBank.setLcBankRefNo(lcBankDto.getLcBankRefNo());
                    lcBank.setDateOfIssue(lcBankDto.getDateOfIssue());
                    lcBank.setLutNo(lcBankDto.getLutNo());
                    lcBank.setCompany(existing);
                    existing.getLcBanks().add(lcBank);
                }
            }
        } else {
            existing.getLcBanks().clear();
        }

        if (dto.getInvoiceItems() != null) {
            existing.getInvoiceItems().removeIf(inv -> dto.getInvoiceItems().stream().noneMatch(d -> d.getId() != null && d.getId().equals(inv.getId())));
            for (CompanyInvoiceDTO invoiceDto : dto.getInvoiceItems()) {
                if (invoiceDto.getId() != null) {
                    existing.getInvoiceItems().stream()
                        .filter(i -> i.getId().equals(invoiceDto.getId()))
                        .findFirst()
                        .ifPresent(invoice -> {
                            invoice.setInvoiceTypeId(invoiceDto.getInvoiceTypeId());
                            invoice.setPrefix(invoiceDto.getPrefix());
                            invoice.setFinancialYear(invoiceDto.getFinancialYear());
                            invoice.setSequenceNumber(invoiceDto.getSequenceNumber());
                            invoice.setPreview(invoiceDto.getPreview());
                        });
                } else {
                    CompanyInvoice invoice = new CompanyInvoice();
                    invoice.setInvoiceTypeId(invoiceDto.getInvoiceTypeId());
                    invoice.setPrefix(invoiceDto.getPrefix());
                    invoice.setFinancialYear(invoiceDto.getFinancialYear());
                    invoice.setSequenceNumber(invoiceDto.getSequenceNumber());
                    invoice.setPreview(invoiceDto.getPreview());
                    invoice.setCompany(existing);
                    existing.getInvoiceItems().add(invoice);
                }
            }
        } else {
            existing.getInvoiceItems().clear();
        }



        Company updated = companyRepository.save(existing);
        return new CompanyDTO(updated);
    }

    @Transactional
    public void deleteCompany(Long id) {
        if (!companyRepository.existsById(id)) {
            throw new EntityNotFoundException("Company not found with id: " + id);
        }
        try {
            companyRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Company because it is referenced by other entities");
        }
    }

    private void mapDtoToEntity(CompanyDTO dto, Company entity) {
        entity.setCompanyName(dto.getCompanyName());
        entity.setCompanyType(dto.getCompanyType());
        entity.setCompanyCode(dto.getCompanyCode());
        entity.setGstNo(dto.getGstNo());
        entity.setPan(dto.getPan());
        entity.setCin(dto.getCin());
        entity.setTin(dto.getTin());
        entity.setIecCode(dto.getIecCode());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setWebsite(dto.getWebsite());
        entity.setMsmeType(dto.getMsmeType());
        entity.setMsmeNumber(dto.getMsmeNumber());
        entity.setLogoUrl(dto.getLogoUrl());
          entity.setLogoMediaDetailSno(dto.getLogoMediaDetailSno());
        entity.setLogoMediaSno(dto.getLogoMediaSno());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
    }

    private String trimValue(String value) {
        return value == null ? "" : value.trim();
    }
}