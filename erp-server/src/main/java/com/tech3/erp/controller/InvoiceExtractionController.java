package com.tech3.erp.controller;

import com.tech3.erp.dto.invoice.InvoiceExtractionDTO;
import com.tech3.erp.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.tech3.erp.service.LLMService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/invoice")
@Tag(name = "Invoice Extraction Controller", description = "APIs for extracting data from invoice files")
public class InvoiceExtractionController {

    private static final Logger log = LoggerFactory.getLogger(InvoiceExtractionController.class);

    @Autowired
    private LLMService llmService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/extract")
    @Operation(summary = "Extract data from Invoice file", 
               description = "Extracts Invoice No, Date, Cost, GST and Net Total from PDF or Image. Uses Gemini for dynamic extraction.")
    public ResponseEntity<Object> extractInvoice(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Received request to extract invoice data from: {}", file.getOriginalFilename());
            
            String fileName = file.getOriginalFilename() != null ? file.getOriginalFilename().toLowerCase() : "";
            String mimeType = file.getContentType();
            
            // If mimeType is null, try to guess from fileName
            if (mimeType == null) {
                if (fileName.endsWith(".pdf")) mimeType = "application/pdf";
                else if (fileName.endsWith(".png")) mimeType = "image/png";
                else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) mimeType = "image/jpeg";
                else mimeType = "application/octet-stream";
            }

            String geminiJson = llmService.extractInvoiceData(file.getBytes(), mimeType);
            InvoiceExtractionDTO dto = objectMapper.readValue(geminiJson, InvoiceExtractionDTO.class);
            
            // Fallback for missing fields or if extraction failed to return any data
            if ((dto.getInvNo() == null || dto.getInvNo().isEmpty()) && 
                (dto.getInvDate() == null || dto.getInvDate().isEmpty()) && 
                (dto.getNetTotal() == null || dto.getNetTotal() == 0.0)) {
                
                log.warn("Gemini extraction returned empty data, trying local fallback for PDF if applicable");
                if (fileName.endsWith(".pdf")) {
                    String text = extractTextFromPDF(file);
                    InvoiceExtractionDTO fallbackDto = parseInvoiceText(text);
                    if (fallbackDto != null) dto = fallbackDto;
                }
            }

            // Final validation - if critical fields are still missing, return error
            if ((dto.getInvNo() == null || dto.getInvNo().isEmpty()) && 
                (dto.getNetTotal() == null || dto.getNetTotal() == 0.0)) {
                log.error("Failed to extract critical invoice data (No/Net Total)");
                return ResponseUtil.error("Could not find Invoice Number or Net Total in the uploaded file. Please ensure the file is a valid invoice.", 
                                        HttpStatus.BAD_REQUEST);
            }
            
            log.info("Successfully extracted invoice data: invNo={}, netTotal={}", dto.getInvNo(), dto.getNetTotal());
            return ResponseEntity.ok(dto);
            
        } catch (Exception e) {
            log.error("Unexpected error during invoice extraction", e);
            return ResponseUtil.error("Failed to extract data: " + e.getMessage(), 
                                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private InvoiceExtractionDTO parseInvoiceText(String text) {
        InvoiceExtractionDTO dto = new InvoiceExtractionDTO("", "", 0.0, 0.0, 0.0);
        if (text == null || text.trim().isEmpty()) return dto;

        log.info("Parsing invoice text...");

        // 1. Invoice No - Look for "No :" specifically. Usually 4-6 digits.
        // We look for "No :" that is NOT preceded by "SF" or "PH"
        Pattern noPattern = Pattern.compile("(?<!SF |PH |SF|PH)\\bNo\\s*[:]?\\s*(\\d{4,})", Pattern.CASE_INSENSITIVE);
        Matcher noMatcher = noPattern.matcher(text);
        if (noMatcher.find()) {
            dto.setInvNo(noMatcher.group(1));
        } else {
            // Fallback: search after TAX INVOICE
            Pattern fbNo = Pattern.compile("TAX INVOICE.*?No\\s*[:]?\\s*(\\d+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher m = fbNo.matcher(text);
            if (m.find()) dto.setInvNo(m.group(1));
        }

        // 2. Date - Format DD/MM/YYYY or YYYY-MM-DD
        Pattern datePattern = Pattern.compile("Date\\s*[:]?\\s*(\\d{2}[/-]\\d{2}[/-]\\d{4}|\\d{4}-\\d{2}-\\d{2})", Pattern.CASE_INSENSITIVE);
        Matcher dateMatcher = datePattern.matcher(text);
        if (dateMatcher.find()) {
            String dateVal = dateMatcher.group(1).replace("/", "-");
            if (dateVal.matches("\\d{2}-\\d{2}-\\d{4}")) {
                String[] parts = dateVal.split("-");
                dateVal = parts[2] + "-" + parts[1] + "-" + parts[0];
            }
            dto.setInvDate(dateVal);
        }

        // 3. Net Total - Look for the value after "NET TOTAL"
        Pattern netPattern = Pattern.compile("NET TOTAL\\s*(?:₹|Rs)?\\s*([\\d,]+\\.\\d{2})", Pattern.CASE_INSENSITIVE);
        Matcher netMatcher = netPattern.matcher(text);
        if (netMatcher.find()) {
            dto.setNetTotal(Double.parseDouble(netMatcher.group(1).replace(",", "")));
        }

        // 4. GST - Sum of CGST and SGST values (the amounts, not the percentages)
        double totalGst = 0.0;
        // Match value like 5,025.30 appearing after "CGST 2.50 %"
        Pattern gstValPattern = Pattern.compile("(?:CGST|SGST)\\s+\\d+(?:\\.\\d+)?\\s*%\\s+([\\d,]+\\.\\d{2})", Pattern.CASE_INSENSITIVE);
        Matcher gstValMatcher = gstValPattern.matcher(text);
        while (gstValMatcher.find()) {
            totalGst += Double.parseDouble(gstValMatcher.group(1).replace(",", ""));
        }
        dto.setGstAmount(totalGst);

        // 5. Sizing Cost (Amount) - This is the taxable value before GST
        // It's often the largest value before the GST lines or under SUB TOTAL
        Pattern amountPattern = Pattern.compile("(?:SUB TOTAL|AMOUNT)\\s*(?:₹|Rs)?\\s*([\\d,]+\\.\\d{2})", Pattern.CASE_INSENSITIVE);
        Matcher amountMatcher = amountPattern.matcher(text);
        if (amountMatcher.find()) {
            dto.setSizingCost(Double.parseDouble(amountMatcher.group(1).replace(",", "")));
        } else {
            // Fallback: look for value before CGST
            Pattern fbAmount = Pattern.compile("([\\d,]+\\.\\d{2})\\s+(?:CGST|SGST|NET TOTAL)", Pattern.CASE_INSENSITIVE);
            Matcher fbAmountMatcher = fbAmount.matcher(text);
            if (fbAmountMatcher.find()) {
                dto.setSizingCost(Double.parseDouble(fbAmountMatcher.group(1).replace(",", "")));
            } else if (dto.getNetTotal() > 0 && dto.getGstAmount() > 0) {
                dto.setSizingCost(dto.getNetTotal() - dto.getGstAmount());
            }
        }

        return dto;
    }
}
