// package com.tech3.erp.service;

// import com.tech3.erp.dto.CustomerDTO;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.entity.Address;
// import com.tech3.erp.entity.City;
// import com.tech3.erp.entity.CodesDetail;
// import com.tech3.erp.entity.Country;
// import com.tech3.erp.entity.Customer;
// import com.tech3.erp.entity.State;
// import com.tech3.erp.repository.AddressRepository;
// import com.tech3.erp.repository.CityRepository;
// import com.tech3.erp.repository.CodesDetailRepository;
// import com.tech3.erp.repository.CountryRepository;
// import com.tech3.erp.repository.CustomerRepository;
// import com.tech3.erp.repository.StateRepository;
// import com.tech3.erp.util.PaginationHelper;

// import jakarta.persistence.EntityNotFoundException;

// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.stereotype.Service;

// import java.util.Comparator;
// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class CustomerService {

//     private final CustomerRepository customerRepository;
//     private final AddressRepository addressRepository;
//     private final CountryRepository countryRepository;
//     private final StateRepository stateRepository;
//     private final CityRepository cityRepository;
//     private final CodesDetailRepository codesDetailRepository;

//     public CustomerService(CustomerRepository customerRepository,
//                            AddressRepository addressRepository,
//                            CountryRepository countryRepository,
//                            StateRepository stateRepository,
//                            CityRepository cityRepository,
//                            CodesDetailRepository codesDetailRepository) {
//         this.customerRepository = customerRepository;
//         this.addressRepository = addressRepository;
//         this.countryRepository = countryRepository;
//         this.stateRepository = stateRepository;
//         this.cityRepository = cityRepository;
//         this.codesDetailRepository = codesDetailRepository;
//     }

//     public CustomerDTO createCustomer(CustomerDTO dto) {
//         Address address = new Address();
//         address.setLine1(dto.getAddress().getLine1());
//         address.setLine2(dto.getAddress().getLine2());
//         address.setPinCode(dto.getAddress().getPinCode());
//         address.setCountry(getCountryById(dto.getAddress().getCountryId()));
//         address.setState(getStateById(dto.getAddress().getStateId()));
//         address.setCity(getCityById(dto.getAddress().getCityId()));

//         Address savedAddress = addressRepository.save(address);

//         Customer customer = new Customer();
//         customer.setCustomerName(dto.getCustomerName());
//         customer.setGstno(dto.getGstNo());
//         customer.setPancard(dto.getPanCard());
//         customer.setMobileno(dto.getMobileNo());
//         customer.setEmail(dto.getEmail());
//         customer.setIecCode(dto.getIecCode());
//         customer.setCinNo(dto.getCinNo());
//         customer.setTinNo(dto.getTinNo());
//         customer.setIrcNo(dto.getIrcNo());
//         customer.setBinNo(dto.getBinNo());
//         customer.setMsmeUdyam(dto.getMsmeUdyam());
//         customer.setContactPerson(dto.getContactPerson());
//         customer.setContactNumber(dto.getContactNumber());
//         customer.setAddress(savedAddress);
//         customer.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        
//         if (dto.getTradeTypeId() != null) {
//             CodesDetail tradeType = codesDetailRepository.findById(dto.getTradeTypeId())
//                     .orElseThrow(() -> new IllegalArgumentException("Invalid trade type ID"));
//             customer.setTradeType(tradeType);
//         }

//         Customer savedCustomer = customerRepository.save(customer);
//         return new CustomerDTO(savedCustomer);
//     }

//     public CustomerDTO getCustomerById(Long id) {
//         Customer customer = customerRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
//         return new CustomerDTO(customer);
//     }

//     public List<CustomerDTO> getAllCustomers() {
//         return customerRepository.findAll().stream()
//                 .sorted(Comparator.comparing(Customer::getId, Comparator.reverseOrder()))
//                 .map(CustomerDTO::new)
//                 .collect(Collectors.toList());
//     }

//     public PaginatedResponse<CustomerDTO> getAllCustomersWithPagination(Long offset, Long limit) {
//         List<CustomerDTO> allCustomers = customerRepository.findAll().stream()
//                 .sorted(Comparator.comparing(Customer::getId, Comparator.reverseOrder()))
//                 .map(CustomerDTO::new)
//                 .collect(Collectors.toList());
        
//         return PaginationHelper.paginate(allCustomers, offset, limit);
//     }

//     public PaginatedResponse<CustomerDTO> getCustomersByTradeTypeWithPagination(Long tradeTypeId, Long offset, Long limit) {
//         if (tradeTypeId == null) {
//             throw new IllegalArgumentException("Trade type ID is required");
//         }

//         List<CustomerDTO> tradeTypeCustomers = customerRepository.findByTradeType_IdOrderByIdDesc(tradeTypeId).stream()
//                 .map(CustomerDTO::new)
//                 .collect(Collectors.toList());

//         return PaginationHelper.paginate(tradeTypeCustomers, offset, limit);
//     }

//     public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
//         Customer customer = customerRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

//         customer.setCustomerName(dto.getCustomerName());
//         customer.setGstno(dto.getGstNo());
//         customer.setPancard(dto.getPanCard());
//         customer.setMobileno(dto.getMobileNo());
//         customer.setEmail(dto.getEmail());
//         customer.setIecCode(dto.getIecCode());
//         customer.setCinNo(dto.getCinNo());
//         customer.setTinNo(dto.getTinNo());
//         customer.setIrcNo(dto.getIrcNo());
//         customer.setBinNo(dto.getBinNo());
//         customer.setMsmeUdyam(dto.getMsmeUdyam());
//         customer.setContactPerson(dto.getContactPerson());
//         customer.setContactNumber(dto.getContactNumber());
//         customer.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        
//         if (dto.getTradeTypeId() != null) {
//             CodesDetail tradeType = codesDetailRepository.findById(dto.getTradeTypeId())
//                     .orElseThrow(() -> new IllegalArgumentException("Invalid trade type ID"));
//             customer.setTradeType(tradeType);
//         } else {
//             customer.setTradeType(null);
//         }

//         if (dto.getAddress() != null) {
//             Address address = new Address();
//             address.setLine1(dto.getAddress().getLine1());
//             address.setLine2(dto.getAddress().getLine2());
//             address.setPinCode(dto.getAddress().getPinCode());
//             address.setCountry(getCountryById(dto.getAddress().getCountryId()));
//             address.setState(getStateById(dto.getAddress().getStateId()));
//             address.setCity(getCityById(dto.getAddress().getCityId()));

//             Address savedAddress = addressRepository.save(address);
//             customer.setAddress(savedAddress);
//         }

//         Customer updatedCustomer = customerRepository.save(customer);
//         return new CustomerDTO(updatedCustomer);
//     }

//     // Utility methods
//     private Country getCountryById(Long id) {
//         return countryRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Invalid country ID"));
//     }

//     private State getStateById(Long id) {
//         return stateRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Invalid state ID"));
//     }

//     private City getCityById(Long id) {
//         return cityRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Invalid city ID"));
//     }

//     public void deleteCustomer(Long id) {
//         if (!customerRepository.existsById(id)) {
//             throw new EntityNotFoundException("Customer not found with id: " + id);
//         }

//         try {
//             customerRepository.deleteById(id);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete Customer because it is referenced by other entities");
//         }
//     }
// }

package com.tech3.erp.service;

import com.tech3.erp.dto.CustomerDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Address;
import com.tech3.erp.entity.City;
import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.entity.Country;
import com.tech3.erp.entity.Customer;
import com.tech3.erp.entity.State;
import com.tech3.erp.repository.AddressRepository;
import com.tech3.erp.repository.CityRepository;
import com.tech3.erp.repository.CodesDetailRepository;
import com.tech3.erp.repository.CountryRepository;
import com.tech3.erp.repository.CustomerRepository;
import com.tech3.erp.repository.StateRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final CodesDetailRepository codesDetailRepository;

    public CustomerService(CustomerRepository customerRepository,
                           AddressRepository addressRepository,
                           CountryRepository countryRepository,
                           StateRepository stateRepository,
                           CityRepository cityRepository,
                           CodesDetailRepository codesDetailRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.codesDetailRepository = codesDetailRepository;
    }

    public CustomerDTO createCustomer(CustomerDTO dto) {
        Address address = new Address();
        address.setLine1(dto.getAddress().getLine1());
        address.setLine2(dto.getAddress().getLine2());
        address.setPinCode(dto.getAddress().getPinCode());
        address.setCountry(getCountryById(dto.getAddress().getCountryId()));
        address.setState(getStateById(dto.getAddress().getStateId()));
        address.setCity(getCityById(dto.getAddress().getCityId()));

        Address savedAddress = addressRepository.save(address);

        Customer customer = new Customer();
        customer.setCustomerName(dto.getCustomerName());
        customer.setGstno(dto.getGstNo());
        customer.setPancard(dto.getPanCard());
        customer.setMobileno(dto.getMobileNo());
        customer.setEmail(dto.getEmail());
        customer.setIecCode(dto.getIecCode());
        customer.setCinNo(dto.getCinNo());
        customer.setTinNo(dto.getTinNo());
        customer.setIrcNo(dto.getIrcNo());
        customer.setBinNo(dto.getBinNo());
        customer.setMsmeUdyam(dto.getMsmeUdyam());
        customer.setContactPerson(normalizeForStorage(dto.getContactPerson()));
        customer.setContactNumber(normalizeForStorage(dto.getContactNumber()));
        customer.setAddress(savedAddress);
        customer.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        
        if (dto.getTradeTypeId() != null) {
            CodesDetail tradeType = codesDetailRepository.findById(dto.getTradeTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid trade type ID"));
            customer.setTradeType(tradeType);
        }

        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerDTO(savedCustomer);
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return new CustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .sorted(Comparator.comparing(Customer::getId, Comparator.reverseOrder()))
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CustomerDTO> getAllCustomersWithPagination(Long offset, Long limit, Long tradeTypeId) {
        long sanitizedOffset = Math.max(0, offset != null ? offset : 0L);
        long sanitizedLimit = PaginationHelper.sanitizeLimit(limit);
        long total = tradeTypeId == null ? customerRepository.count() : customerRepository.countWithOptionalTradeType(tradeTypeId);

        if (total == 0 || sanitizedOffset >= total) {
            boolean hasPrev = sanitizedOffset > 0 && total > 0;
            return new PaginatedResponse<>(total, false, hasPrev, sanitizedOffset, sanitizedLimit, List.of());
        }

        List<CustomerDTO> page = fetchPaginatedCustomerDtos(sanitizedOffset, sanitizedLimit, tradeTypeId);
        boolean hasNext = sanitizedOffset + sanitizedLimit < total;
        boolean hasPrev = sanitizedOffset > 0;

        return new PaginatedResponse<>(total, hasNext, hasPrev, sanitizedOffset, sanitizedLimit, page);
    }

    private List<CustomerDTO> fetchPaginatedCustomerDtos(long offset, long limit, Long tradeTypeId) {
        if (limit <= 0) {
            return List.of();
        }

        int pageSize = (int) Math.min(limit, Integer.MAX_VALUE);
        long initialPage = offset / limit;
        int skipInPage = (int) (offset % limit);
        List<CustomerDTO> result = new ArrayList<>();
        int remaining = (int) limit;
        long currentPage = initialPage;

        while (remaining > 0) {
            if (currentPage > Integer.MAX_VALUE) {
                break;
            }

            PageRequest pageRequest = PageRequest.of((int) currentPage, pageSize, Sort.by(Sort.Direction.DESC, "id"));
            Page<Customer> page = tradeTypeId == null
                    ? customerRepository.findAll(pageRequest)
                    : customerRepository.findAllWithOptionalTradeType(tradeTypeId, pageRequest);

            if (page.isEmpty()) {
                break;
            }

            List<CustomerDTO> pageDtos = page.getContent()
                    .stream()
                    .map(CustomerDTO::new)
                    .collect(Collectors.toList());

            int startIndex = currentPage == initialPage ? skipInPage : 0;
            for (int i = startIndex; i < pageDtos.size() && remaining > 0; i++) {
                result.add(pageDtos.get(i));
                remaining--;
            }

            if (!page.hasNext()) {
                break;
            }

            currentPage++;
        }

        return result;
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.setCustomerName(dto.getCustomerName());
        customer.setGstno(dto.getGstNo());
        customer.setPancard(dto.getPanCard());
        customer.setMobileno(dto.getMobileNo());
        customer.setEmail(dto.getEmail());
        customer.setIecCode(dto.getIecCode());
        customer.setCinNo(dto.getCinNo());
        customer.setTinNo(dto.getTinNo());
        customer.setIrcNo(dto.getIrcNo());
        customer.setBinNo(dto.getBinNo());
        customer.setMsmeUdyam(dto.getMsmeUdyam());
        customer.setContactPerson(normalizeForStorage(dto.getContactPerson()));
        customer.setContactNumber(normalizeForStorage(dto.getContactNumber()));
        customer.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        
        if (dto.getTradeTypeId() != null) {
            CodesDetail tradeType = codesDetailRepository.findById(dto.getTradeTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid trade type ID"));
            customer.setTradeType(tradeType);
        } else {
            customer.setTradeType(null);
        }

        if (dto.getAddress() != null) {
            Address address = new Address();
            address.setLine1(dto.getAddress().getLine1());
            address.setLine2(dto.getAddress().getLine2());
            address.setPinCode(dto.getAddress().getPinCode());
            address.setCountry(getCountryById(dto.getAddress().getCountryId()));
            address.setState(getStateById(dto.getAddress().getStateId()));
            address.setCity(getCityById(dto.getAddress().getCityId()));

            Address savedAddress = addressRepository.save(address);
            customer.setAddress(savedAddress);
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return new CustomerDTO(updatedCustomer);
    }

    // Utility methods
    private String normalizeForStorage(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
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

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }

        try {
            customerRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Customer because it is referenced by other entities");
        }
    }
}