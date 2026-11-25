package com.tech3.erp.service;

import com.tech3.erp.dto.ApplicationUserDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.entity.ApplicationUser;
import com.tech3.erp.repository.ApplicationUserRepository;
import com.tech3.erp.util.PaginationHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationUserService {

    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

private String mapRoleToDisplayName(String role) {
    if (role == null) return null;
    switch (role) {
        case "PPC": 
        case "Technical Team": return "Technical Team";
        case "Inventory": 
        case "Merchant": return "Merchant";
        case "Sizing": return "Sizing";
        case "Weaving": return "Weaving";
        default: return role;
    }
}
    @Transactional
    public ApplicationUserDTO createApplicationUser(ApplicationUserDTO dto) {
        // Check if username already exists
        if (applicationUserRepository.existsByUserName(dto.getUserName().toUpperCase())) {
            throw new IllegalArgumentException("User Name already exists: " + dto.getUserName());
        }

        ApplicationUser entity = new ApplicationUser();
        mapDtoToEntityForCreate(dto, entity);
        
        ApplicationUser saved = applicationUserRepository.save(entity);
        return new ApplicationUserDTO(saved);
    }

    public ApplicationUserDTO getApplicationUserById(Long id) {
        ApplicationUser entity = applicationUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application User not found with ID: " + id));
        return new ApplicationUserDTO(entity);
    }

    public List<ApplicationUserDTO> getAllApplicationUsers() {
        return applicationUserRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ApplicationUser::getId).reversed())
                .map(ApplicationUserDTO::new)
                .collect(Collectors.toList());
    }

    public PaginatedResponse<ApplicationUserDTO> getAllApplicationUsersWithPagination(Long offset, Long limit) {
        List<ApplicationUserDTO> all = getAllApplicationUsers();
        return PaginationHelper.paginate(all, offset, limit);
    }

    public List<ApplicationUserDTO> searchApplicationUsers(String search) {
        if (search == null || search.trim().isEmpty()) {
            return getAllApplicationUsers();
        }
        return applicationUserRepository.searchByUserNameOrRole(search)
                .stream()
                .map(ApplicationUserDTO::new)
                .collect(Collectors.toList());
    }

    public List<ApplicationUserDTO> getApplicationUsersByRole(String role) {
        return applicationUserRepository.findByRole(role)
                .stream()
                .map(ApplicationUserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ApplicationUserDTO updateApplicationUser(Long id, ApplicationUserDTO dto) {
        ApplicationUser existing = applicationUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application User not found with ID: " + id));

        // Check if username is being changed and if new username already exists
        if (!existing.getUserName().equalsIgnoreCase(dto.getUserName()) &&
            applicationUserRepository.existsByUserName(dto.getUserName().toUpperCase())) {
            throw new IllegalArgumentException("User Name already exists: " + dto.getUserName());
        }

        mapDtoToEntityForUpdate(dto, existing);
        ApplicationUser updated = applicationUserRepository.save(existing);
        return new ApplicationUserDTO(updated);
    }

    @Transactional
    public void deleteApplicationUser(Long id) {
        if (!applicationUserRepository.existsById(id)) {
            throw new EntityNotFoundException("Application User not found with ID: " + id);
        }
        try {
            applicationUserRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Cannot delete Application User because it is referenced by other entities");
        }
    }

    /**
     * Maps DTO to Entity for CREATE operation
     * Password encryption is mandatory for new users
     * 
     */

    private void mapDtoToEntityForCreate(ApplicationUserDTO dto, ApplicationUser entity) {
        entity.setUserName(dto.getUserName());
        
        // Password is mandatory for create
        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required for new users");
        }
        
        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(encryptedPassword);
        
        entity.setRole(dto.getRole());
         entity.setRoleDisplayName(mapRoleToDisplayName(dto.getRole()));
        entity.setCompanies(dto.getCompanies());
        entity.setActiveFlag(dto.getActiveFlag() != null ? dto.getActiveFlag() : true);
        entity.setModuleId(dto.getModuleId());
        entity.setUserType(dto.getUserType());
    }

    /**
     * Maps DTO to Entity for UPDATE operation
     * Password encryption only happens if a new password is provided
     */
    private void mapDtoToEntityForUpdate(ApplicationUserDTO dto, ApplicationUser entity) {
        entity.setUserName(dto.getUserName());

        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            String newPassword = dto.getPassword();
            boolean isAlreadyEncrypted = newPassword.startsWith("$2a$")
                    || newPassword.startsWith("$2b$")
                    || newPassword.startsWith("$2y$");

            // Only encrypt if it's not already encrypted and it's not equal to current one
            if (!isAlreadyEncrypted && !passwordEncoder.matches(newPassword, entity.getPassword())) {
                String encryptedPassword = passwordEncoder.encode(newPassword);
                entity.setPassword(encryptedPassword);
                System.out.println("INFO: Password updated and encrypted for user: " + entity.getUserName());
            } else {
                // Keep existing password
                entity.setPassword(entity.getPassword());
                System.out.println("INFO: Existing password retained for user: " + entity.getUserName());
            }
        } else {
            // Password not provided → keep old one
            entity.setPassword(entity.getPassword());
        }

        entity.setRole(dto.getRole());
        entity.setRoleDisplayName(mapRoleToDisplayName(dto.getRole()));  // NEW

        entity.setCompanies(dto.getCompanies());

        if (dto.getActiveFlag() != null) {
            entity.setActiveFlag(dto.getActiveFlag());
        }

        if (dto.getModuleId() != null) {
            entity.setModuleId(dto.getModuleId());
        }

        if (dto.getUserType() != null) {
            entity.setUserType(dto.getUserType());
        }
    }

}