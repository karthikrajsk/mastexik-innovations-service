package com.tech3.erp.exception;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Pattern FK_KEY_PATTERN = Pattern.compile("Key \\(([^)]+)\\)=\\(([^)]+)\\)");
    private static final Pattern FK_TABLE_PATTERN = Pattern.compile("table \"([^\"]+)\"");
    private static final Pattern DUPLICATE_KEY_PATTERN = Pattern.compile(
            "duplicate key value violates unique constraint \"([^\"]+)\"[\\s\\S]*Key \\(([^)]+)\\)=\\(([^)]+)\\)",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern NOT_NULL_PATTERN = Pattern.compile(
            "null value in column \"([^\"]+)\"[\\s\\S]*violates not-null constraint",
            Pattern.CASE_INSENSITIVE);
    private static final String DEFAULT_DATA_INTEGRITY_MESSAGE = "Data integrity violation";

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new ErrorResponse(ex.getMessage(), ex.getStatus().value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage, 400));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String friendlyMessage = buildFriendlyDataIntegrityMessage(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(friendlyMessage, HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage(), 400));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage(), 500));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Internal Server Error", 500));
    }

@ExceptionHandler(Throwable.class)
public ResponseEntity<ErrorResponse> handleThrowable(Throwable ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("Unexpected error occurred", 500));
}

    private String buildFriendlyDataIntegrityMessage(DataIntegrityViolationException ex) {
        String dbMessage = extractDbMessage(ex);
        String duplicateMessage = formatDuplicateKeyMessage(dbMessage);
        if (duplicateMessage != null) {
            return duplicateMessage;
        }

        String friendlyFkMessage = formatFriendlyFkMessage(dbMessage);
        if (friendlyFkMessage != null) {
            return friendlyFkMessage;
        }

        String friendlyNotNullMessage = formatFriendlyNotNullMessage(dbMessage);
        if (friendlyNotNullMessage != null) {
            return friendlyNotNullMessage;
        }

        if (dbMessage != null && !dbMessage.isBlank()) {
            return dbMessage;
        }
        return DEFAULT_DATA_INTEGRITY_MESSAGE;
    }

    private String formatFriendlyNotNullMessage(String dbMessage) {
        if (dbMessage == null) {
            return null;
        }
        Matcher matcher = NOT_NULL_PATTERN.matcher(dbMessage);
        if (!matcher.find()) {
            return null;
        }
        String columnName = matcher.group(1);
        if ("weaving_contract_id".equalsIgnoreCase(columnName)) {
            return "Weaving Contract is required for all Beam Details";
        }
        String fieldLabel = Arrays.stream(columnName.replace("_id", "").split("_"))
                .filter(part -> !part.isBlank())
                .map(this::capitalizeWord)
                .collect(Collectors.joining(" "));
        return fieldLabel + " is required";
    }

    private String extractDbMessage(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getRootCause();
        if (rootCause != null && rootCause.getMessage() != null && !rootCause.getMessage().isBlank()) {
            return rootCause.getMessage();
        }
        if (ex.getMessage() != null && !ex.getMessage().isBlank()) {
            return ex.getMessage();
        }
        return null;
    }

    private String formatFriendlyFkMessage(String dbMessage) {
        if (dbMessage == null) {
            return null;
        }
        Matcher tableMatcher = FK_TABLE_PATTERN.matcher(dbMessage);
        if (!tableMatcher.find()) {
            return null;
        }
        String entityLabel = toEntityLabel(tableMatcher.group(1));
        Matcher keyMatcher = FK_KEY_PATTERN.matcher(dbMessage);
        String referenceLabel = "reference";
        if (keyMatcher.find()) {
            String keyValue = sanitizeKeyValue(keyMatcher.group(2));
            if (keyValue != null && !keyValue.isBlank()) {
                referenceLabel = keyValue.matches("\\d+") ? "ID " + keyValue : keyValue;
            }
        }
        if ("reference".equals(referenceLabel)) {
            return String.format("This reference is currently not present in %s", entityLabel);
        }
        return String.format("This %s is currently not present in %s", referenceLabel, entityLabel);
    }

    private String formatDuplicateKeyMessage(String dbMessage) {
        if (dbMessage == null) {
            return null;
        }
        Matcher duplicateMatcher = DUPLICATE_KEY_PATTERN.matcher(dbMessage);
        if (!duplicateMatcher.find()) {
            return null;
        }
        String columns = duplicateMatcher.group(2);
        String values = duplicateMatcher.group(3);
        String columnLabel = Arrays.stream(columns.split(","))
                .map(String::trim)
                .filter(part -> !part.isBlank())
                .map(this::capitalizeWord)
                .collect(Collectors.joining(", "));
        if (columnLabel.isBlank()) {
            columnLabel = "Value";
        }
        return String.format("%s '%s' already exists", columnLabel, values);
    }

    private String toEntityLabel(String tableName) {
        if (tableName == null || tableName.isBlank()) {
            return "reference";
        }
        String singular = singularize(tableName);
        return Arrays.stream(singular.split("_"))
                .filter(part -> !part.isBlank())
                .map(this::capitalizeWord)
                .collect(Collectors.joining(" "));
    }

    private String singularize(String tableName) {
        if (tableName == null || tableName.isBlank()) {
            return tableName;
        }
        String normalized = tableName.toLowerCase();
        if (normalized.endsWith("ies")) {
            return tableName.substring(0, tableName.length() - 3) + "y";
        }
        if (normalized.endsWith("ses")) {
            return tableName.substring(0, tableName.length() - 2);
        }
        if (normalized.endsWith("s") && !normalized.endsWith("ss")) {
            return tableName.substring(0, tableName.length() - 1);
        }
        return tableName;
    }

    private String capitalizeWord(String part) {
        if (part == null || part.isBlank()) {
            return part;
        }
        return part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
    }

    private String sanitizeKeyValue(String keyValue) {
        if (keyValue == null) {
            return null;
        }
        return keyValue.replace("'", "").replace("\"", "").trim();
    }
}