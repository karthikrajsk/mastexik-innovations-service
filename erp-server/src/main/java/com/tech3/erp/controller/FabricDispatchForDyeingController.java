// package com.tech3.erp.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.*;

// import com.tech3.erp.dto.FabricDispatchForDyeingDTO;
// import com.tech3.erp.service.FabricDispatchForDyeingService;
// import com.tech3.erp.util.ResponseUtil;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.persistence.EntityNotFoundException;

// import java.util.List;
// import java.util.Optional;


// @RestController
// @RequestMapping("/api/fabric-dispatch")
// @Tag(name = "Fabric Dispatch For Dyeing Controller", description = "APIs for Fabric Dispatch For Dyeing management")
// @Validated
// public class FabricDispatchForDyeingController {

//     @Autowired
//     private FabricDispatchForDyeingService service;

//     // CREATE
//     @PostMapping
//     public ResponseEntity<FabricDispatchForDyeingDTO> createDispatch(@RequestBody FabricDispatchForDyeingDTO dto) {
//         return ResponseEntity.ok(service.createDispatch(dto));
//     }

//     // READ ALL
//     @GetMapping
//     public ResponseEntity<List<FabricDispatchForDyeingDTO>> getAllDispatches() {
//         return ResponseEntity.ok(service.getAllDispatches());
//     }

//     // READ BY ID
//     @GetMapping("/{id}")
//     public ResponseEntity<Optional<FabricDispatchForDyeingDTO>> getDispatchById(@PathVariable Long id) {
//         return ResponseEntity.ok(service.getDispatchById(id));
//     }

//     // UPDATE
//     @PutMapping("/{id}")
//     public ResponseEntity<FabricDispatchForDyeingDTO> updateDispatch(@PathVariable Long id, @RequestBody FabricDispatchForDyeingDTO dto) {
//         FabricDispatchForDyeingDTO updatedDto = service.updateDispatch(id, dto);
//         return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete Fabric Dispatch", description = "Deletes a Fabric Dispatch by ID.")
//     public ResponseEntity<Object> deleteDispatch(@PathVariable Long id) {
//         try {
//         	service.deleteDispatch(id);
//             return ResponseUtil.success("Fabric Dispatch deleted successfully", null);
//         } catch (EntityNotFoundException e) {
//             return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
//         } catch (IllegalStateException e) {
//             return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
//         } catch (Exception e) {
//             return ResponseUtil.error("An error occurred while deleting the Fabric Dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
//         }
//     }
    
// }


package com.tech3.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.tech3.erp.dto.FabricDispatchForDyeingDTO;
import com.tech3.erp.service.FabricDispatchForDyeingService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fabric-dispatch")
@Tag(name = "Fabric Dispatch For Dyeing Controller", description = "APIs for Fabric Dispatch For Dyeing management")
@Validated
public class FabricDispatchForDyeingController {

    @Autowired
    private FabricDispatchForDyeingService service;

    // CREATE
    @PostMapping
    @Operation(summary = "Create a new Fabric Dispatch for Dyeing", description = "Adds a new Fabric Dispatch for Dyeing.")
    public ResponseEntity<Object> createDispatch(@Valid @RequestBody FabricDispatchForDyeingDTO dto) {
        try {
            FabricDispatchForDyeingDTO createDispatch = service.createDispatch(dto);
            return ResponseUtil.success("Fabric Dispatch for Dyeing created successfully", createDispatch);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<FabricDispatchForDyeingDTO>> getAllDispatches() {
        return ResponseEntity.ok(service.getAllDispatches());
    }

    @GetMapping("/type/{processType}")
    @Operation(summary = "Get Fabric Dispatches by process type")
    public ResponseEntity<Object> getDispatchesByType(
            @PathVariable String processType,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseUtil.success("Fabric Dispatches fetched successfully",
                service.getAllDispatchesByTypeWithPagination(processType, finalOffset, finalLimit));
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get Fabric Dispatches by company")
    public ResponseEntity<Object> getDispatchesByCompany(
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseUtil.success("Fabric Dispatches fetched successfully",
                service.getDispatchesByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }

    @GetMapping("/type/{processType}/company/{companyId}")
    @Operation(summary = "Get Fabric Dispatches by process type and company")
    public ResponseEntity<Object> getDispatchesByTypeAndCompany(
            @PathVariable String processType,
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseUtil.success("Fabric Dispatches fetched successfully",
                service.getDispatchesByCompanyAndTypeWithPagination(companyId, processType, finalOffset, finalLimit));
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDispatchById(@PathVariable Long id) {
        try {
            Optional<FabricDispatchForDyeingDTO> dispatch = service.getDispatchById(id);
            if (dispatch.isPresent()) {
                return ResponseUtil.success("Fabric Dispatch retrieved successfully", dispatch.get());
            } else {
                return ResponseUtil.error("Fabric Dispatch not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while retrieving the Fabric Dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{companyId}/{id}")
    @Operation(summary = "Get Fabric Dispatch by company")
    public ResponseEntity<Object> getDispatchByCompany(
            @PathVariable Long companyId,
            @PathVariable Long id) {
        try {
            return ResponseUtil.success("Fabric Dispatch retrieved successfully",
                    service.getDispatchByIdAndCompany(id, companyId));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/type/{processType}/company/{companyId}/{id}")
    @Operation(summary = "Get Fabric Dispatch by process type and company")
    public ResponseEntity<Object> getDispatchByCompanyAndType(
            @PathVariable String processType,
            @PathVariable Long companyId,
            @PathVariable Long id) {
        try {
            return ResponseUtil.success("Fabric Dispatch retrieved successfully",
                    service.getDispatchByIdAndCompanyAndType(id, companyId, processType));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDispatch(@PathVariable Long id, @RequestBody FabricDispatchForDyeingDTO dto) {
        try {
            FabricDispatchForDyeingDTO updatedDto = service.updateDispatch(id, dto);
            if (updatedDto != null) {
                return ResponseUtil.success("Fabric Dispatch updated successfully", updatedDto);
            } else {
                return ResponseUtil.error("Fabric Dispatch not found", HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while updating the Fabric Dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/company/{companyId}/{id}")
    @Operation(summary = "Update Fabric Dispatch by company")
    public ResponseEntity<Object> updateDispatchByCompany(
            @PathVariable Long companyId,
            @PathVariable Long id,
            @RequestBody FabricDispatchForDyeingDTO dto) {
        try {
            return ResponseUtil.success("Fabric Dispatch updated successfully",
                    service.updateDispatchByCompany(id, companyId, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/type/{processType}/company/{companyId}/{id}")
    @Operation(summary = "Update Fabric Dispatch by process type and company")
    public ResponseEntity<Object> updateDispatchByCompanyAndType(
            @PathVariable String processType,
            @PathVariable Long companyId,
            @PathVariable Long id,
            @RequestBody FabricDispatchForDyeingDTO dto) {
        try {
            return ResponseUtil.success("Fabric Dispatch updated successfully",
                    service.updateDispatchByCompanyAndType(id, companyId, processType, dto));
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Fabric Dispatch", description = "Deletes a Fabric Dispatch by ID.")
    public ResponseEntity<Object> deleteDispatch(@PathVariable Long id) {
        try {
            service.deleteDispatch(id);
            return ResponseUtil.success("Fabric Dispatch deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Fabric Dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/company/{companyId}/{id}")
    @Operation(summary = "Delete Fabric Dispatch by company")
    public ResponseEntity<Object> deleteDispatchByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        try {
            service.deleteDispatchByCompany(id, companyId);
            return ResponseUtil.success("Fabric Dispatch deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Fabric Dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/type/{processType}/company/{companyId}/{id}")
    @Operation(summary = "Delete Fabric Dispatch by process type and company")
    public ResponseEntity<Object> deleteDispatchByCompanyAndType(
            @PathVariable String processType,
            @PathVariable Long companyId,
            @PathVariable Long id) {
        try {
            service.deleteDispatchByCompanyAndType(id, companyId, processType);
            return ResponseUtil.success("Fabric Dispatch deleted successfully", null);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseUtil.error(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return ResponseUtil.error("An error occurred while deleting the Fabric Dispatch", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}