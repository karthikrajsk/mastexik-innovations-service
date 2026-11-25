package com.tech3.erp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/old-fabric-stock")
@Tag(name = "Old Fabric Stock Controller", description = "APIs for Old Fabric Stock management and upload history")
public class OldFabricStockController {

    @GetMapping("/history")
    @Operation(summary = "Get Old Stock Upload History", description = "Returns history logs of uploaded old fabric stock batches")
    public ResponseEntity<List<Object>> getHistory() {
        // Return empty list by default if server-side history tracking table is not used
        return ResponseEntity.ok(new ArrayList<>());
    }
}
