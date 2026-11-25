package com.tech3.erp.service;

import com.tech3.erp.dto.CategoryDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.Category;
import com.tech3.erp.entity.PaymentTerms;
import com.tech3.erp.repository.CategoryRepository;
import com.tech3.erp.util.PaginationHelper;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        String normalizedCategoryName = normalizeCategoryName(categoryDTO.getCategoryName());
        if (normalizedCategoryName == null) {
            throw new IllegalArgumentException("Category name cannot be blank");
        }
        validateCategoryNameUnique(normalizedCategoryName, null);
        Category category = new Category();
        category.setCategoryName(normalizedCategoryName);
        category.setActiveFlag(categoryDTO.getActiveFlag());

        return new CategoryDTO(categoryRepository.save(category));
    }

    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return new CategoryDTO(category);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category ::getId, Comparator.reverseOrder()))
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<CategoryDTO> getAllCategoriesWithPagination(Long offset, Long limit) {
        List<CategoryDTO> allCategories = categoryRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Category ::getId, Comparator.reverseOrder()))
                .map(CategoryDTO::new)
                .collect(Collectors.toList());
        
        return PaginationHelper.paginate(allCategories, offset, limit);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        String normalizedCategoryName = normalizeCategoryName(categoryDTO.getCategoryName());
        if (normalizedCategoryName == null) {
            throw new IllegalArgumentException("Category name cannot be blank");
        }
        validateCategoryNameUnique(normalizedCategoryName, id);
        category.setCategoryName(normalizedCategoryName);
        category.setActiveFlag(categoryDTO.getActiveFlag());

        return new CategoryDTO(categoryRepository.save(category));
    }

    private void validateCategoryNameUnique(String categoryName, Long currentId) {
        if (categoryName == null) {
            return;
        }
        categoryRepository.findByCategoryName(categoryName)
                .filter(existing -> currentId == null || !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new DataIntegrityViolationException("Category name '" + categoryName + "' already exists");
                });
    }

    private String normalizeCategoryName(String categoryName) {
        if (categoryName == null) {
            return null;
        }
        String trimmed = categoryName.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    
//    public void deleteCategory(Long id) {
//        if (!categoryRepository.existsById(id)) {
//            throw new IllegalArgumentException("Category not found");
//        }
//        categoryRepository.deleteById(id);
//    }
    
    public void deleteCategory(Long id) {
        // Check if category exists first
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete category because it is referenced by other entities");
        }
    }
    
}
