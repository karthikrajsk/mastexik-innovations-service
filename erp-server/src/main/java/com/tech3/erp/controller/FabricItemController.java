package com.tech3.erp.controller;

import com.tech3.erp.dto.FabricItemDto;
import com.tech3.erp.service.FabricItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fabric-item")
@CrossOrigin(origins = "*")
public class FabricItemController {

    @Autowired
    private FabricItemService fabricItemService;

    // GET ALL
    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseEntity.ok(fabricItemService.getAllWithPagination(finalOffset, finalLimit));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<FabricItemDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fabricItemService.getById(id));
    }

    // GET BY OPENING BALANCE ID
    @GetMapping("/opening-balance/{openingBalanceId}")
    public ResponseEntity<List<FabricItemDto>> getByOpeningBalanceId(@PathVariable Long openingBalanceId) {
        return ResponseEntity.ok(fabricItemService.getByOpeningBalanceId(openingBalanceId));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<FabricItemDto> update(@PathVariable Long id, @RequestBody FabricItemDto dto) {
        return ResponseEntity.ok(fabricItemService.update(id, dto));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fabricItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
