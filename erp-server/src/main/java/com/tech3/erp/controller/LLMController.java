package com.tech3.erp.controller;

import com.tech3.erp.dto.LLMQueryRequestDTO;
import com.tech3.erp.dto.LLMQueryResponseDTO;
import com.tech3.erp.dto.ExecuteQueryRequestDTO;
import com.tech3.erp.dto.ExecuteQueryResponseDTO;
import com.tech3.erp.entity.LLMQueryResult;
import com.tech3.erp.repository.LLMQueryResultRepository;
import com.tech3.erp.service.LLMService;
import com.tech3.erp.util.ResponseUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/llm")
@Tag(name = "LLM Controller", description = "APIs for AI-powered query generation using Google Gemini")
public class LLMController {

    private static final Logger log = LoggerFactory.getLogger(LLMController.class);

    private final LLMService llmService;
    private final com.tech3.erp.service.LLMQueryExecutionService llmQueryExecutionService;
    private final LLMQueryResultRepository llmQueryResultRepository;

    public LLMController(LLMService llmService,
                         com.tech3.erp.service.LLMQueryExecutionService llmQueryExecutionService,
                         LLMQueryResultRepository llmQueryResultRepository) {
        this.llmService = llmService;
        this.llmQueryExecutionService = llmQueryExecutionService;
        this.llmQueryResultRepository = llmQueryResultRepository;
    }

    @PostMapping("/generate-query")
    @Operation(summary = "Generate PostgreSQL Query",
               description = "Generate a PostgreSQL SELECT query using Google Gemini AI based on report configuration")
    public ResponseEntity<Object> generateQuery(@Valid @RequestBody LLMQueryRequestDTO request) {
        try {
            log.info("Generating PostgreSQL query for report: {}", request.getReportName());

            LLMQueryResponseDTO response = llmService.generatePostgreSQLQuery(request);

            if (response.isSuccess()) {  // This is correct for boolean field
                log.info("Successfully generated query for report: {}", request.getReportName());
                return ResponseUtil.success("PostgreSQL query generated successfully", response);
            } else {
                log.error("Failed to generate query for report: {}. Error: {}",
                         request.getReportName(), response.getErrorMessage());  // Use getErrorMessage()
                return ResponseUtil.error(response.getErrorMessage(), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            log.error("Unexpected error while generating query for report: {}", request.getReportName(), e);
            return ResponseUtil.error("An unexpected error occurred while generating the query",
                                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/execute-query")
    @Operation(summary = "Execute SQL and store/update result",
               description = "Executes provided SELECT SQL, returns rows and stores the query with JSON result. If id is provided, updates that record; otherwise creates a new one.")
    public ResponseEntity<Object> executeQuery(@Valid @RequestBody ExecuteQueryRequestDTO request) {
        try {
            log.info("Executing ad-hoc SQL");
            ExecuteQueryResponseDTO result = llmQueryExecutionService.executeAndStore(request.getSql(), request.getId());
            return ResponseUtil.success("Query executed and stored successfully", result);
        } catch (com.tech3.erp.exception.NotFoundException e) {
            // Map NotFound to 404
            return ResponseUtil.error(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error executing SQL", e);
            return ResponseUtil.error("Failed to execute SQL: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/results/{id}")
    @Operation(summary = "Get a stored LLM query result by id")
    public ResponseEntity<Object> getResultById(@PathVariable Long id) {
        return llmQueryResultRepository.findById(id)
                .map(result -> ResponseUtil.success("Result fetched successfully", result))
                .orElseGet(() -> ResponseUtil.error("LLMQueryResult with id=" + id + " not found", HttpStatus.NOT_FOUND));
    }

    @GetMapping("/results")
    @Operation(summary = "List stored LLM query results", description = "Paginated list sorted by createdAt desc")
    public ResponseEntity<Object> listResults(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size) {
        int pageSize = Math.min(Math.max(size, 1), 200); // 1..200
        Page<LLMQueryResult> resultPage = llmQueryResultRepository.findAll(
                PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        Map<String, Object> data = new HashMap<>();
        data.put("items", resultPage.getContent());
        data.put("page", resultPage.getNumber());
        data.put("size", resultPage.getSize());
        data.put("totalElements", resultPage.getTotalElements());
        data.put("totalPages", resultPage.getTotalPages());

        return ResponseUtil.success("Results fetched successfully", data);
    }
}
