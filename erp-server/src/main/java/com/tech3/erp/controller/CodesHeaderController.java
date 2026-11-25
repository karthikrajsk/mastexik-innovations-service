package com.tech3.erp.controller;

import com.tech3.erp.dto.CodesHeaderDTO;
import com.tech3.erp.service.CodesHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/codes-header")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CodesHeaderController {

    @Autowired
    private CodesHeaderService codesHeaderService;

    @PostMapping
    public ResponseEntity<CodesHeaderDTO> create(@RequestBody CodesHeaderDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(codesHeaderService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "offset", required = false) Long offset,
            @RequestParam(value = "limit", required = false) Long limit) {
        
        Long finalOffset = (offset != null) ? offset : 0L;
        Long finalLimit = (limit != null) ? limit : 10L;
        return ResponseEntity.ok(codesHeaderService.getAllWithPagination(finalOffset, finalLimit));
    }
}
