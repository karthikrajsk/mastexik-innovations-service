package com.tech3.erp.controller;

import com.tech3.erp.dto.ApplicationUserDTO;
import com.tech3.erp.service.ApplicationUserService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application-users")
@Tag(name = "Application User Controller", description = "APIs for managing Application Users")
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;

    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping
    @Operation(summary = "Create Application User", description = "Creates a new Application User")
    public ResponseEntity<Object> create(@Valid @RequestBody ApplicationUserDTO dto) {
        try {
            ApplicationUserDTO created = applicationUserService.createApplicationUser(dto);
            return ResponseUtil.success("Application User created successfully", created);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Application User by ID", description = "Fetch an Application User by its ID")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            ApplicationUserDTO user = applicationUserService.getApplicationUserById(id);
            return ResponseUtil.success("Application User fetched successfully", user);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "List all Application Users", description = "Returns all Application Users with pagination support.")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseUtil.success("Application Users fetched successfully", applicationUserService.getAllApplicationUsersWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/search")
    @Operation(summary = "Search Application Users", description = "Search by User Name or Role")
    public ResponseEntity<Object> search(@RequestParam(required = false) String query) {
        List<ApplicationUserDTO> list = applicationUserService.searchApplicationUsers(query);
        return ResponseUtil.success("Application Users fetched successfully", list);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get Application Users by Role", description = "Fetch Application Users by Role")
    public ResponseEntity<Object> getByRole(@PathVariable String role) {
        try {
            List<ApplicationUserDTO> users = applicationUserService.getApplicationUsersByRole(role);
            return ResponseUtil.success("Application Users fetched successfully", users);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Application User", description = "Updates an existing Application User by ID")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ApplicationUserDTO dto) {
        try {
            ApplicationUserDTO updated = applicationUserService.updateApplicationUser(id, dto);
            return ResponseUtil.success("Application User updated successfully", updated);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Application User", description = "Deletes an Application User by ID")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            applicationUserService.deleteApplicationUser(id);
            return ResponseUtil.success("Application User deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Application User", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}