// package com.tech3.erp.service;

// import com.tech3.erp.dto.SubCategoryDTO;
// import com.tech3.erp.dto.PaginatedResponse;
// import com.tech3.erp.entity.Category;
// import com.tech3.erp.entity.SubCategory;
// import com.tech3.erp.repository.CategoryRepository;
// import com.tech3.erp.repository.SubCategoryRepository;
// import com.tech3.erp.util.PaginationHelper;

// import jakarta.persistence.EntityNotFoundException;

// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.stereotype.Service;

// import java.util.Comparator;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class SubCategoryService {

//     private final SubCategoryRepository subCategoryRepository;
//     private final CategoryRepository categoryRepository;

//     public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
//         this.subCategoryRepository = subCategoryRepository;
//         this.categoryRepository = categoryRepository;
//     }

//     public SubCategoryDTO createSubCategory(SubCategoryDTO subCategoryDTO) {
//         Optional<Category> category = categoryRepository.findById(subCategoryDTO.getCategorySno());
//         if (category.isEmpty()) {
//             throw new IllegalArgumentException("Category not found");
//         }

//         // Check if combination already exists
//         boolean combinationExists = subCategoryRepository.existsByCategoryIdAndSubCategoryNameAndIdNot(
//             subCategoryDTO.getCategorySno(), 
//             subCategoryDTO.getSubCategoryName(), 
//             -1L  // Use -1 for create since no ID exists yet
//         );
        
//         if (combinationExists) {
//             throw new DataIntegrityViolationException(
//                 "Sub-category name '" + subCategoryDTO.getSubCategoryName() + 
//                 "' already exists for this category"
//             );
//         }

//         SubCategory subCategory = new SubCategory();
//         subCategory.setCategory(category.get());
//         subCategory.setSubCategoryName(subCategoryDTO.getSubCategoryName());
//         subCategory.setActiveFlag(subCategoryDTO.getActiveFlag());

//         subCategory = subCategoryRepository.save(subCategory);
//         return new SubCategoryDTO(subCategory);
//     }

//     public SubCategoryDTO getSubCategoryById(Long id) {
//         SubCategory subCategory = subCategoryRepository.findByIdWithCategory(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Sub-category not found"));
//         return new SubCategoryDTO(subCategory);
//     }

//     public List<SubCategoryDTO> getAllSubCategories() {
//         return subCategoryRepository.findAllWithCategory()
//                 .stream()
//                 .map(SubCategoryDTO::new)
//                 .collect(Collectors.toList());
//     }

//     public PaginatedResponse<SubCategoryDTO> getAllSubCategoriesWithPagination(Long offset, Long limit) {
//         org.springframework.data.domain.Page<SubCategory> page = 
//             subCategoryRepository.findAllWithCategoryPaginated(
//                 org.springframework.data.domain.PageRequest.of(
//                     offset.intValue() / limit.intValue(), 
//                     limit.intValue()
//                 )
//             );
//         List<SubCategoryDTO> content = page.getContent().stream()
//             .map(SubCategoryDTO::new)
//             .collect(Collectors.toList());
//         return new PaginatedResponse<>(
//             page.getTotalElements(),
//             !page.isLast(),
//             page.getNumber() > 0,
//             offset,
//             limit,
//             content
//         );
//     }

//     public SubCategoryDTO updateSubCategory(Long id, SubCategoryDTO subCategoryDTO) {
//         SubCategory subCategory = subCategoryRepository.findById(id)
//                 .orElseThrow(() -> new IllegalArgumentException("Sub-category not found"));

//         // Check if category is being changed
//         Long newCategoryId = subCategoryDTO.getCategorySno();
//         Category newCategory = null;
        
//         if (newCategoryId != null) {
//             newCategory = categoryRepository.findById(newCategoryId)
//                     .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            
//             // If category is changing, use the new category ID for duplicate check
//             Long categoryIdForCheck = newCategory.getId();
            
//             // Check if the combination already exists (excluding current record)
//             boolean combinationExists = subCategoryRepository.existsByCategoryIdAndSubCategoryNameAndIdNot(
//                 categoryIdForCheck, 
//                 subCategoryDTO.getSubCategoryName(), 
//                 id
//             );
            
//             if (combinationExists) {
//                 throw new DataIntegrityViolationException(
//                     "Sub-category name '" + subCategoryDTO.getSubCategoryName() + 
//                     "' already exists for category ID: " + categoryIdForCheck
//                 );
//             }
            
//             subCategory.setCategory(newCategory);
//         } else {
//             // If category is not changing, use the existing category ID for duplicate check
//             boolean combinationExists = subCategoryRepository.existsByCategoryIdAndSubCategoryNameAndIdNot(
//                 subCategory.getCategory().getId(), 
//                 subCategoryDTO.getSubCategoryName(), 
//                 id
//             );
            
//             if (combinationExists) {
//                 throw new DataIntegrityViolationException(
//                     "Sub-category name '" + subCategoryDTO.getSubCategoryName() + 
//                     "' already exists for this category"
//                 );
//             }
//         }

//         subCategory.setSubCategoryName(subCategoryDTO.getSubCategoryName());
//         subCategory.setActiveFlag(subCategoryDTO.getActiveFlag());

//         try {
//             subCategory = subCategoryRepository.save(subCategory);
//             return new SubCategoryDTO(subCategory);
//         } catch (DataIntegrityViolationException e) {
//             throw new DataIntegrityViolationException(
//                 "Sub-category name '" + subCategoryDTO.getSubCategoryName() + 
//                 "' already exists for the selected category. Please choose a different name."
//             );
//         }
//     }

// //    public void deleteSubCategory(Long id) {
// //        SubCategory subCategory = subCategoryRepository.findById(id)
// //                .orElseThrow(() -> new IllegalArgumentException("Sub-category not found"));
// //        subCategoryRepository.delete(subCategory);
// //    }
    
//     public void deleteSubCategory(Long id) {
//         // Check if category exists first
//         if (!subCategoryRepository.existsById(id)) {
//             throw new EntityNotFoundException("SubCategory not found with id: " + id);
//         }
        
//         try {
//         	subCategoryRepository.deleteById(id);
//         } catch (DataIntegrityViolationException e) {
//             throw new IllegalStateException("Cannot delete SubCategory because it is referenced by other");
//         }
//     }
    
    
//     public List<SubCategoryDTO> getSubCategoriesByCategoryName(String categoryName) {
//         List<SubCategory> subCategories = subCategoryRepository.findByCategoryCategoryName(categoryName);
//         return subCategories.stream()
//                 .map(SubCategoryDTO::new)
//                 .collect(Collectors.toList());
//     }

    
// }
package com.tech3.erp.service;

import com.tech3.erp.dto.SubCategoryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Category;
import com.tech3.erp.entity.SubCategory;
import com.tech3.erp.repository.CategoryRepository;
import com.tech3.erp.repository.SubCategoryRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;

    // Add this helper method to SubCategoryService class
private boolean areNamesEqual(String name1, String name2) {
    if (name1 == null || name2 == null) {
        return name1 == name2;
    }
    
    String trimmed1 = name1.trim();
    String trimmed2 = name2.trim();
    
    if (trimmed1.equalsIgnoreCase(trimmed2)) {
        return true;
    }
    
    if (isNumeric(trimmed1) && isNumeric(trimmed2)) {
        return Long.parseLong(trimmed1) == Long.parseLong(trimmed2);
    }
    
    return false;
}

private boolean isNumeric(String str) {
    return str != null && str.matches("^\\d+$");
}

private boolean isDuplicateExists(Long categoryId, String subCategoryName, Long excludeId) {
    List<SubCategory> existingSubCategories = subCategoryRepository.findByCategory_IdAndIdNot(categoryId, excludeId);
    return existingSubCategories.stream()
            .anyMatch(sc -> areNamesEqual(sc.getSubCategoryName(), subCategoryName));
}

private String normalizeSubCategoryName(String subCategoryName) {
    if (subCategoryName == null) {
        return null;
    }
    String trimmed = subCategoryName.trim();
    return trimmed.isEmpty() ? null : trimmed;
}


    public SubCategoryService(SubCategoryRepository subCategoryRepository, CategoryRepository categoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    // public SubCategoryDTO createSubCategory(SubCategoryDTO subCategoryDTO) {
    //     Optional<Category> category = categoryRepository.findById(subCategoryDTO.getCategorySno());
    //     if (category.isEmpty()) {
    //         throw new IllegalArgumentException("Category not found");
    //     }

    //     // Check if combination already exists
    //     boolean combinationExists = subCategoryRepository.existsByCategoryIdAndSubCategoryNameAndIdNot(
    //         subCategoryDTO.getCategorySno(), 
    //         subCategoryDTO.getSubCategoryName(), 
    //         -1L  // Use -1 for create since no ID exists yet
    //     );
        
    //     if (combinationExists) {
    //         throw new DataIntegrityViolationException(
    //             "Sub-category name '" + subCategoryDTO.getSubCategoryName() + 
    //             "' already exists for this category"
    //         );
    //     }

    //     SubCategory subCategory = new SubCategory();
    //     subCategory.setCategory(category.get());
    //     subCategory.setSubCategoryName(subCategoryDTO.getSubCategoryName());
    //     subCategory.setActiveFlag(subCategoryDTO.getActiveFlag());

    //     subCategory = subCategoryRepository.save(subCategory);
    //     return new SubCategoryDTO(subCategory);
    // }

public SubCategoryDTO createSubCategory(SubCategoryDTO subCategoryDTO) {
    Optional<Category> category = categoryRepository.findById(subCategoryDTO.getCategorySno());
    if (category.isEmpty()) {
        throw new IllegalArgumentException("Category not found");
    }

    String normalizedSubCategoryName = normalizeSubCategoryName(subCategoryDTO.getSubCategoryName());
    if (normalizedSubCategoryName == null) {
        throw new IllegalArgumentException("Sub-category name cannot be blank");
    }

    if (isDuplicateExists(category.get().getId(), normalizedSubCategoryName, -1L)) {
        throw new DataIntegrityViolationException(
            "Sub-category name '" + normalizedSubCategoryName + 
            "' already exists for this category"
        );
    }

    SubCategory subCategory = new SubCategory();
    subCategory.setCategory(category.get());
    subCategory.setSubCategoryName(normalizedSubCategoryName);
    subCategory.setActiveFlag(subCategoryDTO.getActiveFlag());

    subCategory = subCategoryRepository.save(subCategory);
    return new SubCategoryDTO(subCategory);
}

    public SubCategoryDTO getSubCategoryById(Long id) {
        SubCategory subCategory = subCategoryRepository.findByIdWithCategory(id)
                .orElseThrow(() -> new IllegalArgumentException("Sub-category not found"));
        return new SubCategoryDTO(subCategory);
    }

    public List<SubCategoryDTO> getAllSubCategories() {
        return subCategoryRepository.findAllWithCategory()
                .stream()
                .map(SubCategoryDTO::new)
                .collect(Collectors.toList());
    }

    public List<SubCategoryDTO> getActiveSubCategories(Long includeId) {
        List<SubCategory> subCategories;
        if (includeId != null) {
            subCategories = subCategoryRepository.findAllActiveOrId(includeId);
        } else {
            subCategories = subCategoryRepository.findByActiveFlagTrue();
        }
        return subCategories.stream()
                .sorted(Comparator.comparing(SubCategory::getId, Comparator.reverseOrder()))
                .map(SubCategoryDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<SubCategoryDTO> getAllSubCategoriesWithPagination(Long offset, Long limit) {
        org.springframework.data.domain.Page<SubCategory> page = 
            subCategoryRepository.findAllWithCategoryPaginated(
                org.springframework.data.domain.PageRequest.of(
                    offset.intValue() / limit.intValue(), 
                    limit.intValue()
                )
            );
        List<SubCategoryDTO> content = page.getContent().stream()
            .map(SubCategoryDTO::new)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(
            page.getTotalElements(),
            !page.isLast(),
            page.getNumber() > 0,
            offset,
            limit,
            content
        );
    }

    // public SubCategoryDTO updateSubCategory(Long id, SubCategoryDTO subCategoryDTO) {
    //     SubCategory subCategory = subCategoryRepository.findById(id)
    //             .orElseThrow(() -> new IllegalArgumentException("Sub-category not found"));

    //     // Check if category is being changed
    //     Long newCategoryId = subCategoryDTO.getCategorySno();
    //     Category newCategory = null;
        
    //     if (newCategoryId != null) {
    //         newCategory = categoryRepository.findById(newCategoryId)
    //                 .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            
    //         // If category is changing, use the new category ID for duplicate check
    //         Long categoryIdForCheck = newCategory.getId();
            
    //         // Check if the combination already exists (excluding current record)
    //         boolean combinationExists = subCategoryRepository.existsByCategoryIdAndSubCategoryNameAndIdNot(
    //             categoryIdForCheck, 
    //             subCategoryDTO.getSubCategoryName(), 
    //             id
    //         );
            
    //         if (combinationExists) {
    //             throw new DataIntegrityViolationException(
    //                 "Sub-category name '" + subCategoryDTO.getSubCategoryName() + 
    //                 "' already exists for category ID: " + categoryIdForCheck
    //             );
    //         }
            
    //         subCategory.setCategory(newCategory);
    //     } else {
    //         // If category is not changing, use the existing category ID for duplicate check
    //         boolean combinationExists = subCategoryRepository.existsByCategoryIdAndSubCategoryNameAndIdNot(
    //             subCategory.getCategory().getId(), 
    //             subCategoryDTO.getSubCategoryName(), 
    //             id
    //         );
            
    //         if (combinationExists) {
    //             throw new DataIntegrityViolationException(
    //                 "Sub-category name '" + subCategoryDTO.getSubCategoryName() + 
    //                 "' already exists for this category"
    //             );
    //         }
    //     }

    //     subCategory.setSubCategoryName(subCategoryDTO.getSubCategoryName());
    //     subCategory.setActiveFlag(subCategoryDTO.getActiveFlag());

    //     try {
    //         subCategory = subCategoryRepository.save(subCategory);
    //         return new SubCategoryDTO(subCategory);
    //     } catch (DataIntegrityViolationException e) {
    //         throw new DataIntegrityViolationException(
    //             "Sub-category name '" + subCategoryDTO.getSubCategoryName() + 
    //             "' already exists for the selected category. Please choose a different name."
    //         );
    //     }
    // }


    public SubCategoryDTO updateSubCategory(Long id, SubCategoryDTO subCategoryDTO) {
    SubCategory subCategory = subCategoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Sub-category not found"));

    String normalizedSubCategoryName = normalizeSubCategoryName(subCategoryDTO.getSubCategoryName());
    if (normalizedSubCategoryName == null) {
        throw new IllegalArgumentException("Sub-category name cannot be blank");
    }

    Long newCategoryId = subCategoryDTO.getCategorySno();
    Category newCategory = null;
    
    if (newCategoryId != null) {
        newCategory = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        
        if (isDuplicateExists(newCategory.getId(), normalizedSubCategoryName, id)) {
            throw new DataIntegrityViolationException(
                "Sub-category name '" + normalizedSubCategoryName + 
                "' already exists for category ID: " + newCategory.getId()
            );
        }
        
        subCategory.setCategory(newCategory);
    } else {
        if (isDuplicateExists(subCategory.getCategory().getId(), normalizedSubCategoryName, id)) {
            throw new DataIntegrityViolationException(
                "Sub-category name '" + normalizedSubCategoryName + 
                "' already exists for this category"
            );
        }
    }

    subCategory.setSubCategoryName(normalizedSubCategoryName);
    subCategory.setActiveFlag(subCategoryDTO.getActiveFlag());

    subCategory = subCategoryRepository.save(subCategory);
    return new SubCategoryDTO(subCategory);
}


//    public void deleteSubCategory(Long id) {
//        SubCategory subCategory = subCategoryRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Sub-category not found"));
//        subCategoryRepository.delete(subCategory);
//    }
    
    public void deleteSubCategory(Long id) {
        // Check if category exists first
        if (!subCategoryRepository.existsById(id)) {
            throw new EntityNotFoundException("SubCategory not found with id: " + id);
        }
        
        try {
        	subCategoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete SubCategory because it is referenced by other");
        }
    }
    
    
    public List<SubCategoryDTO> getSubCategoriesByCategoryName(String categoryName) {
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryCategoryName(categoryName);
        return subCategories.stream()
                .map(SubCategoryDTO::new)
                .collect(Collectors.toList());
    }

    public List<SubCategoryDTO> getActiveSubCategoriesByCategoryName(String categoryName) {
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryCategoryName(categoryName);
        return subCategories.stream()
                .filter(SubCategory::getActiveFlag)
                .map(SubCategoryDTO::new)
                .collect(Collectors.toList());
    }

    
}