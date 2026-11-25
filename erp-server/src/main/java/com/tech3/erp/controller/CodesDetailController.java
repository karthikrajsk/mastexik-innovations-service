package com.tech3.erp.controller;

import com.tech3.erp.entity.CodesDetail;
import com.tech3.erp.repository.CodesDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/codes_details")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CodesDetailController {

    @Autowired
    private CodesDetailRepository codesDetailRepository;

    @GetMapping
    public ResponseEntity<List<CodesDetail>> getByHeader(@RequestParam("hdr") String header) {
        try {
            Long id = Long.parseLong(header);
            return ResponseEntity.ok(codesDetailRepository.findByCodesHeaderId(id));
        } catch (NumberFormatException e) {
            return ResponseEntity.ok(codesDetailRepository.findByCodesHeaderCodeGroupAndIsActiveTrue(header));
        }
    }
}
