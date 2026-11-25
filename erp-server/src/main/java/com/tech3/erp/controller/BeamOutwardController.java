// package com.tech3.erp.controller;

// import java.util.List;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.tech3.erp.dto.PaginatedResponse;

// import com.tech3.erp.dto.BeamOutwardDTO;
// import com.tech3.erp.service.BeamOutwardService;

// import io.swagger.v3.oas.annotations.tags.Tag;
// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api/beam-outward")
// @Tag(name = "Beam Outward Controller", description = "APIs for Beam Outward management")
// public class BeamOutwardController {

//     private final BeamOutwardService service;

//     public BeamOutwardController(BeamOutwardService service) {
//         this.service = service;
//     }

//     @PostMapping
//     public ResponseEntity<BeamOutwardDTO> create(@Valid @RequestBody BeamOutwardDTO dto) {
//         return ResponseEntity.ok(service.create(dto));
//     }

//     @GetMapping
//     public ResponseEntity<Object> getAll(
//             @RequestParam(value = "offset", required = false) Long offset,
//             @RequestParam(value = "limit", required = false) Long limit) {
//         if (offset == null && limit == null) {
//             return ResponseEntity.ok(service.getAll());
//         }
//         Long finalOffset = offset != null ? offset : 0L;
//         Long finalLimit = limit != null ? limit : 10L;
//         return ResponseEntity.ok(service.getAllWithPagination(finalOffset, finalLimit));
//     }

//     @GetMapping("/company/{companyId}")
//     public ResponseEntity<Object> getAllByCompany(
//             @PathVariable Long companyId,
//             @RequestParam(value = "offset", required = false) Long offset,
//             @RequestParam(value = "limit", required = false) Long limit) {
//         if (offset == null && limit == null) {
//             return ResponseEntity.ok(service.getAllByCompany(companyId));
//         }
//         Long finalOffset = offset != null ? offset : 0L;
//         Long finalLimit = limit != null ? limit : 10L;
//         return ResponseEntity.ok(service.getAllByCompanyWithPagination(companyId, finalOffset, finalLimit));
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<BeamOutwardDTO> getById(@PathVariable Long id) {
//         return ResponseEntity.ok(service.getById(id));
//     }

//     @GetMapping("/company/{companyId}/{id}")
//     public ResponseEntity<BeamOutwardDTO> getByIdAndCompany(@PathVariable Long companyId, @PathVariable Long id) {
//         return ResponseEntity.ok(service.getByIdAndCompany(companyId, id));
//     }

//     @GetMapping("/beam-inward/{beamInwardId}")
//     public ResponseEntity<List<BeamOutwardDTO>> getByBeamInward(@PathVariable Long beamInwardId) {
//         return ResponseEntity.ok(service.getByBeamInwardId(beamInwardId));
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<BeamOutwardDTO> update(@PathVariable Long id, @Valid @RequestBody BeamOutwardDTO dto) {
//         return ResponseEntity.ok(service.update(id, dto));
//     }

//     @PutMapping("/company/{companyId}/{id}")
//     public ResponseEntity<BeamOutwardDTO> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody BeamOutwardDTO dto) {
//         return ResponseEntity.ok(service.updateByCompany(companyId, id, dto));
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> delete(@PathVariable Long id) {
//         service.delete(id);
//         return ResponseEntity.noContent().build();
//     }

//     @DeleteMapping("/company/{companyId}/{id}")
//     public ResponseEntity<Void> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
//         service.deleteByCompany(companyId, id);
//         return ResponseEntity.noContent().build();
//     }
// }
package com.tech3.erp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tech3.erp.dto.PaginatedResponse;

import com.tech3.erp.dto.BeamOutwardDTO;
import com.tech3.erp.service.BeamOutwardService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/beam-outward")
@Tag(name = "Beam Outward Controller", description = "APIs for Beam Outward management")
public class BeamOutwardController {

    private final BeamOutwardService service;

    public BeamOutwardController(BeamOutwardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BeamOutwardDTO> create(@Valid @RequestBody BeamOutwardDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(service.getAll());
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(service.getAllWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<Object> getAllByCompany(
            @PathVariable Long companyId,
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        if (offset == null && limit == null) {
            return ResponseEntity.ok(service.getAllByCompany(companyId));
        }
        Long finalOffset = offset != null ? offset : 0L;
        Long finalLimit = limit != null ? limit : 10L;
        return ResponseEntity.ok(service.getAllByCompanyWithPagination(companyId, finalOffset, finalLimit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeamOutwardDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/company/{companyId}/{id}")
    public ResponseEntity<BeamOutwardDTO> getByIdAndCompany(@PathVariable Long companyId, @PathVariable Long id) {
        return ResponseEntity.ok(service.getByIdAndCompany(companyId, id));
    }

    @GetMapping("/beam-inward/{beamInwardId}")
    public ResponseEntity<List<BeamOutwardDTO>> getByBeamInward(@PathVariable Long beamInwardId) {
        return ResponseEntity.ok(service.getByBeamInwardId(beamInwardId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeamOutwardDTO> update(@PathVariable Long id, @Valid @RequestBody BeamOutwardDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PutMapping("/company/{companyId}/{id}")
    public ResponseEntity<BeamOutwardDTO> updateByCompany(@PathVariable Long companyId, @PathVariable Long id, @Valid @RequestBody BeamOutwardDTO dto) {
        return ResponseEntity.ok(service.updateByCompany(companyId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/company/{companyId}/{id}")
    public ResponseEntity<Void> deleteByCompany(@PathVariable Long companyId, @PathVariable Long id) {
        service.deleteByCompany(companyId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-return")
    public ResponseEntity<java.util.Map<String, Object>> checkReturnStatus(
            @RequestParam("beamOutwardId") Long beamOutwardId,
            @RequestParam("sizedBeamNo") String sizedBeamNo) {
        return ResponseEntity.ok(service.checkBeamReturnStatus(beamOutwardId, sizedBeamNo));
    }
}
