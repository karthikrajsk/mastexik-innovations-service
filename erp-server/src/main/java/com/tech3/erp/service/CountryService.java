// package com.tech3.erp.service;

// import com.tech3.erp.dto.CountryDTO;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.entity.Country;
// import com.tech3.erp.entity.CurrencyMaster;
// import com.tech3.erp.repository.CountryRepository;
// import com.tech3.erp.util.PaginationHelper;

// import jakarta.persistence.EntityNotFoundException;

// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.stereotype.Service;

// import java.util.Comparator;
// import java.util.List;
// import java.util.stream.Collectors;

// @Service
// public class CountryService {

//     private final CountryRepository countryRepository;

//     public CountryService(CountryRepository countryRepository) {
//         this.countryRepository = countryRepository;
//     }

//     // public CountryDTO createCountry(CountryDTO countryDTO) {
//     //     Country country = new Country();
//     //     country.setCountryName(countryDTO.getCountryName());

//     //     return new CountryDTO(countryRepository.save(country));
//     // }
//     public CountryDTO createCountry(CountryDTO countryDTO) {
//         String trimmedCountryName = countryDTO.getCountryName().trim();
        
//         boolean duplicateExists = countryRepository.findAll().stream()
//                 .anyMatch(term -> term.getCountryName().trim().equalsIgnoreCase(trimmedCountryName));
        
//         if (duplicateExists) {
//             throw new IllegalArgumentException("Country with name '" + trimmedCountryName + "' already exists");
//         }
        
//         Country country = new Country();
//         country.setCountryName(trimmedCountryName);
//         return new CountryDTO(countryRepository.save(country));
//     }

// // public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
// //     Country country = countryRepository.findById(id)
// //             .orElseThrow(() -> new IllegalArgumentException("Country not found"));

// //     if (countryRepository.existsByCountryNameIgnoreCaseAndIdNot(
// //             countryDTO.getCountryName().trim(), id)) {
// //         throw new IllegalArgumentException("Country with this name already exists");
// //     }
    
// //     country.setCountryName(countryDTO.getCountryName().trim());
// //     country.setActiveFlag(countryDTO.getActiveFlag());
// //     return new CountryDTO(countryRepository.save(country));
// // }

// public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
//     Country country = countryRepository.findById(id)
//             .orElseThrow(() -> new IllegalArgumentException("Country not found"));

//     String trimmedCountryName = countryDTO.getCountryName().trim();
    
//     boolean duplicateExists = countryRepository.findAll().stream()
//             .filter(term -> !term.getId().equals(id))
//             .anyMatch(term -> term.getCountryName().trim().equalsIgnoreCase(trimmedCountryName));
    
//     if (duplicateExists) {
//         throw new IllegalArgumentException("Country with name '" + trimmedCountryName + "' already exists");
//     }
    
//     country.setCountryName(trimmedCountryName);
//     country.setActiveFlag(countryDTO.getActiveFlag());
//     return new CountryDTO(countryRepository.save(country));
// }


//     public CountryDTO getCountryById(Long id) {
//         Country country = countryRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Country not found"));
//         return new CountryDTO(country);
//     }

//     public List<CountryDTO> getAllCountries() {
//         return countryRepository.findAll()
//                 .stream()
//                 .sorted(Comparator.comparing(Country::getId).reversed())
//                 .map(CountryDTO::new)
//                 .collect(Collectors.toList());
//     }

//     public PaginatedResponse<CountryDTO> getAllCountriesWithPagination(Long offset, Long limit) {
//         List<CountryDTO> allCountries = countryRepository.findAll()
//                 .stream()
//                 .sorted(Comparator.comparing(Country::getId).reversed())
//                 .map(CountryDTO::new)
//                 .collect(Collectors.toList());
        
//         return PaginationHelper.paginate(allCountries, offset, limit);
//     }

//     // public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
//     //     Country country = countryRepository.findById(id)
//     //             .orElseThrow(() -> new IllegalArgumentException("Country not found"));

//     //     country.setCountryName(countryDTO.getCountryName());
//     //     country.setActiveFlag(countryDTO.getActiveFlag());

//     //     return new CountryDTO(countryRepository.save(country));
//     // }
    
// //    public void deleteCountry(Long id) {
// //        if (!countryRepository.existsById(id)) {
// //            throw new IllegalArgumentException("Country not found");
// //        }
// //        countryRepository.deleteById(id);
// //    }
    
//     public void deleteCountry(Long id) {
//         // Check if category exists first
//         if (!countryRepository.existsById(id)) {
//             throw new EntityNotFoundException("Country not found with id: " + id);
//         }
        
//         try {
//         	countryRepository.deleteById(id);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete Country because it is referenced by other entities");
//         }
//     }

//     private String normalizeName(String name) {
//         return name == null ? "" : name.trim();
//     }

//     private void ensureUniqueCountryName(String countryName, Long currentId) {
//         if (countryName.isEmpty()) {
//             return;
//         }
//         countryRepository.findByCountryNameIgnoreCase(countryName).ifPresent(existing -> {
//             if (currentId == null || !existing.getId().equals(currentId)) {
//                 throw new IllegalArgumentException("Country '" + countryName + "' already exists.");
//             }
//         });
//     }
// }
package com.tech3.erp.service;

import com.tech3.erp.dto.CountryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Country;
import com.tech3.erp.entity.CurrencyMaster;
import com.tech3.erp.repository.CountryRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // public CountryDTO createCountry(CountryDTO countryDTO) {
    //     Country country = new Country();
    //     country.setCountryName(countryDTO.getCountryName());

    //     return new CountryDTO(countryRepository.save(country));
    // }
    public CountryDTO createCountry(CountryDTO countryDTO) {
        String trimmedCountryName = countryDTO.getCountryName().trim();
        
        boolean duplicateExists = countryRepository.findAll().stream()
                .anyMatch(term -> term.getCountryName().trim().equalsIgnoreCase(trimmedCountryName));
        
        if (duplicateExists) {
            throw new IllegalArgumentException("Country with name '" + trimmedCountryName + "' already exists");
        }
        
        Country country = new Country();
        country.setCountryName(trimmedCountryName);
        return new CountryDTO(countryRepository.save(country));
    }

// public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
//     Country country = countryRepository.findById(id)
//             .orElseThrow(() -> new IllegalArgumentException("Country not found"));

//     if (countryRepository.existsByCountryNameIgnoreCaseAndIdNot(
//             countryDTO.getCountryName().trim(), id)) {
//         throw new IllegalArgumentException("Country with this name already exists");
//     }
    
//     country.setCountryName(countryDTO.getCountryName().trim());
//     country.setActiveFlag(countryDTO.getActiveFlag());
//     return new CountryDTO(countryRepository.save(country));
// }

public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
    Country country = countryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Country not found"));

    String trimmedCountryName = countryDTO.getCountryName().trim();
    
    boolean duplicateExists = countryRepository.findAll().stream()
            .filter(term -> !term.getId().equals(id))
            .anyMatch(term -> term.getCountryName().trim().equalsIgnoreCase(trimmedCountryName));
    
    if (duplicateExists) {
        throw new IllegalArgumentException("Country with name '" + trimmedCountryName + "' already exists");
    }
    
    country.setCountryName(trimmedCountryName);
    country.setActiveFlag(countryDTO.getActiveFlag());
    return new CountryDTO(countryRepository.save(country));
}


    public CountryDTO getCountryById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));
        return new CountryDTO(country);
    }

    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Country::getId).reversed())
                .map(CountryDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CountryDTO> getAllCountriesWithPagination(Long offset, Long limit) {
        List<CountryDTO> allCountries = countryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Country::getId).reversed())
                .map(CountryDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allCountries, offset, limit);
    }

    // public CountryDTO updateCountry(Long id, CountryDTO countryDTO) {
    //     Country country = countryRepository.findById(id)
    //             .orElseThrow(() -> new IllegalArgumentException("Country not found"));

    //     country.setCountryName(countryDTO.getCountryName());
    //     country.setActiveFlag(countryDTO.getActiveFlag());

    //     return new CountryDTO(countryRepository.save(country));
    // }
    
//    public void deleteCountry(Long id) {
//        if (!countryRepository.existsById(id)) {
//            throw new IllegalArgumentException("Country not found");
//        }
//        countryRepository.deleteById(id);
//    }
    
    public void deleteCountry(Long id) {
        // Check if category exists first
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException("Country not found with id: " + id);
        }
        
        try {
        	countryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Country because it is referenced by other entities");
        }
    }

}