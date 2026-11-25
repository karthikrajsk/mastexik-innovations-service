package com.tech3.erp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class LenientLongDeserializer extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        JsonToken token = parser.currentToken();
        if (token == JsonToken.VALUE_NUMBER_INT || token == JsonToken.VALUE_NUMBER_FLOAT) {
            return parser.getLongValue();
        }
        String text = parser.getText();
        if (text == null || text.isBlank()) {
            return null;
        }
        String trimmed = text.trim();
        Long value = tryParseLong(trimmed);
        if (value != null) {
            return value;
        }
        String firstSegment = trimmed.split(",", 2)[0].trim();
        value = tryParseLong(firstSegment);
        if (value != null) {
            return value;
        }
        String withoutComma = trimmed.replace(",", "").replaceAll("\\s+", "");
        value = tryParseLong(withoutComma);
        if (value != null) {
            return value;
        }
        Double doubleValue = tryParseDouble(withoutComma);
        if (doubleValue != null) {
            return doubleValue.longValue();
        }
        return (Long) ctxt.handleWeirdStringValue(Long.class, text, "cannot parse lenient long");
    }

    private Long tryParseLong(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double tryParseDouble(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
