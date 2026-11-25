package com.tech3.erp.controller;

import com.tech3.erp.dto.YarnItemDto;
import com.tech3.erp.service.YarnItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/yarn-item")
@CrossOrigin(origins = "*")
public class YarnItemController {
    @Autowired
    private YarnItemService yarnItemService;

    @PostMapping
    public ResponseEntity<YarnItemDto> create(@RequestBody YarnItemDto dto) {
        try {
            return ResponseEntity.ok(yarnItemService.create(dto));
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseEntity.ok(yarnItemService.getAllWithPagination(finalOffset, finalLimit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<YarnItemDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(yarnItemService.getById(id));
    }

    @GetMapping("/opening-balance/{openingBalanceId}")
    public ResponseEntity<List<YarnItemDto>> getByOpeningBalanceId(@PathVariable Long openingBalanceId) {
        return ResponseEntity.ok(yarnItemService.getByOpeningBalanceId(openingBalanceId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<YarnItemDto> update(@PathVariable Long id, @RequestBody YarnItemDto dto) {
        return ResponseEntity.ok(yarnItemService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        yarnItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/opening-balance/{openingBalanceId}")
    public ResponseEntity<Void> deleteByOpeningBalanceId(@PathVariable Long openingBalanceId) {
        yarnItemService.deleteByOpeningBalanceId(openingBalanceId);
        return ResponseEntity.noContent().build();
    }
}
