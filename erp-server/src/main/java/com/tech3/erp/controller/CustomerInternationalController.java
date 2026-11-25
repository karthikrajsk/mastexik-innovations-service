package com.tech3.erp.controller;

import com.tech3.erp.dto.CustomerInternationalDTO;
import com.tech3.erp.dto.PaginatedResponse;
import com.tech3.erp.service.CustomerInternationalService;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Customer International Controller", description = "APIs for international customer management")
public class CustomerInternationalController {

    private final CustomerInternationalService customerInternationalService;

    public CustomerInternationalController(CustomerInternationalService customerInternationalService) {
        this.customerInternationalService = customerInternationalService;
    }

    @GetMapping("/customer_international")
    @Operation(summary = "List International Customers", description = "Returns records from the customer_international table with pagination.")
    public ResponseEntity<Object> getInternationalCustomers(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {

        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;

        PaginatedResponse<CustomerInternationalDTO> paginatedResponse =
                customerInternationalService.getCustomersWithPagination(finalOffset, finalLimit);

        return ResponseUtil.success("International customers fetched", paginatedResponse);
    }

    @PostMapping("/customer_international")
    @Operation(summary = "Create International Customer")
    public ResponseEntity<Object> createInternationalCustomer(@Valid @RequestBody CustomerInternationalDTO dto) {
        return ResponseUtil.success("International customer created",
                customerInternationalService.createCustomer(dto));
    }

    @PutMapping("/customer_international/{id}")
    @Operation(summary = "Update International Customer")
    public ResponseEntity<Object> updateInternationalCustomer(@PathVariable("id") Long id,
                                                              @Valid @RequestBody CustomerInternationalDTO dto) {
        return ResponseUtil.success("International customer updated",
                customerInternationalService.updateCustomer(id, dto));
    }

    @DeleteMapping("/customer_international/{id}")
    @Operation(summary = "Delete International Customer", description = "Deletes an international customer by ID.")
    public ResponseEntity<Object> deleteInternationalCustomer(@PathVariable("id") Long id) {
        try {
            customerInternationalService.deleteCustomer(id);
            return ResponseUtil.success("International customer deleted", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the international customer",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
