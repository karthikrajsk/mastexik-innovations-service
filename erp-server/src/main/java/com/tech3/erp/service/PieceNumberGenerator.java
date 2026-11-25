package com.tech3.erp.service;

import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PieceNumberGenerator {  // Renamed from RollNumberGenerator
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private final AtomicInteger sequence = new AtomicInteger(1);
    
    /**
     * Generate piece number
     * Note: This is kept for backward compatibility
     * New inspections get piece numbers from Purchase Inward or Jobwork Fabric Receive
     */
    public String generatePieceNumber() {
        String datePart = LocalDate.now().format(DATE_FORMATTER);
        int seq = sequence.getAndIncrement();
        return "PN" + datePart + String.format("%04d", seq);  // Changed prefix from RN to PN
    }
    
    // Keep old method name for backward compatibility
    @Deprecated
    public String generateRollNumber() {
        return generatePieceNumber();
    }
}