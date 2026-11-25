package com.tech3.erp.service;

import com.tech3.erp.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class ReferenceValidationService {

    public <T> T requireReference(Long referenceId,
                                  Supplier<Optional<T>> finder,
                                  String entityName,
                                  String displayIdentifier) {
        if (referenceId == null || referenceId <= 0) {
            return null;
        }

        Optional<T> reference = finder.get();
        return reference.orElseThrow(() -> new ApiException(
                HttpStatus.CONFLICT,
                formatMissingReferenceMessage(entityName, displayIdentifier, "ID " + referenceId)));
    }

    public <T> T requireReference(String referenceKey,
                                  Supplier<Optional<T>> finder,
                                  String entityName,
                                  String displayIdentifier) {
        if (!StringUtils.hasText(referenceKey)) {
            return null;
        }

        Optional<T> reference = finder.get();
        return reference.orElseThrow(() -> new ApiException(
                HttpStatus.CONFLICT,
                formatMissingReferenceMessage(entityName, displayIdentifier, referenceKey)));
    }

    private String formatMissingReferenceMessage(String entityName,
                                                 String displayIdentifier,
                                                 String fallbackLabel) {
        String referenceLabel = StringUtils.hasText(displayIdentifier)
                ? displayIdentifier
                : fallbackLabel;
        return String.format("This %s is currently not present in %s", referenceLabel, entityName);
    }
}
