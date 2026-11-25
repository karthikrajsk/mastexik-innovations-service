package com.tech3.erp.exception;

/**
 * Simple runtime exception to indicate a resource was not found.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}