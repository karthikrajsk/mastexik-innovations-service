package com.tech3.erp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.Collections;
import java.util.List;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJson(Object value) {
        if (value == null) return null;
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object fromJson(String json) {
        if (json == null || json.isEmpty()) return null;
        try {
            return OBJECT_MAPPER.readValue(json, Object.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> fromJsonList(String json, Class<T> itemClass) {
        if (json == null || json.isEmpty()) return Collections.emptyList();
        try {
            CollectionType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, itemClass);
            return OBJECT_MAPPER.readValue(json, listType);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // Parse a single object from JSON
    public static <T> T fromJsonObject(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) return null;
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}